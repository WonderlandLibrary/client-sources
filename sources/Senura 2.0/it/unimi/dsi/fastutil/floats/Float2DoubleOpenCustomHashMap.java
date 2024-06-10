/*      */ package it.unimi.dsi.fastutil.floats;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleCollection;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleIterator;
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
/*      */ public class Float2DoubleOpenCustomHashMap
/*      */   extends AbstractFloat2DoubleMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient float[] key;
/*      */   protected transient double[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected FloatHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Float2DoubleMap.FastEntrySet entries;
/*      */   protected transient FloatSet keys;
/*      */   protected transient DoubleCollection values;
/*      */   
/*      */   public Float2DoubleOpenCustomHashMap(int expected, float f, FloatHash.Strategy strategy) {
/*  107 */     this.strategy = strategy;
/*  108 */     if (f <= 0.0F || f > 1.0F)
/*  109 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  110 */     if (expected < 0)
/*  111 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  112 */     this.f = f;
/*  113 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  114 */     this.mask = this.n - 1;
/*  115 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  116 */     this.key = new float[this.n + 1];
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
/*      */   public Float2DoubleOpenCustomHashMap(int expected, FloatHash.Strategy strategy) {
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
/*      */   public Float2DoubleOpenCustomHashMap(FloatHash.Strategy strategy) {
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
/*      */   public Float2DoubleOpenCustomHashMap(Map<? extends Float, ? extends Double> m, float f, FloatHash.Strategy strategy) {
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
/*      */   public Float2DoubleOpenCustomHashMap(Map<? extends Float, ? extends Double> m, FloatHash.Strategy strategy) {
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
/*      */   public Float2DoubleOpenCustomHashMap(Float2DoubleMap m, float f, FloatHash.Strategy strategy) {
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
/*      */   public Float2DoubleOpenCustomHashMap(Float2DoubleMap m, FloatHash.Strategy strategy) {
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
/*      */   public Float2DoubleOpenCustomHashMap(float[] k, double[] v, float f, FloatHash.Strategy strategy) {
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
/*      */   public Float2DoubleOpenCustomHashMap(float[] k, double[] v, FloatHash.Strategy strategy) {
/*  236 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FloatHash.Strategy strategy() {
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
/*      */   public void putAll(Map<? extends Float, ? extends Double> m) {
/*  278 */     if (this.f <= 0.5D) {
/*  279 */       ensureCapacity(m.size());
/*      */     } else {
/*  281 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  283 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(float k) {
/*  287 */     if (this.strategy.equals(k, 0.0F)) {
/*  288 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  290 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  293 */     if (Float.floatToIntBits(
/*  294 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  295 */       return -(pos + 1); 
/*  296 */     if (this.strategy.equals(k, curr)) {
/*  297 */       return pos;
/*      */     }
/*      */     while (true) {
/*  300 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  301 */         return -(pos + 1); 
/*  302 */       if (this.strategy.equals(k, curr))
/*  303 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, float k, double v) {
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
/*      */   public double put(float k, double v) {
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
/*      */   public double addTo(float k, double incr) {
/*      */     int pos;
/*  350 */     if (this.strategy.equals(k, 0.0F)) {
/*  351 */       if (this.containsNullKey)
/*  352 */         return addToValue(this.n, incr); 
/*  353 */       pos = this.n;
/*  354 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  357 */       float[] key = this.key;
/*      */       float curr;
/*  359 */       if (Float.floatToIntBits(
/*  360 */           curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != 0) {
/*  361 */         if (this.strategy.equals(curr, k))
/*  362 */           return addToValue(pos, incr); 
/*  363 */         while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) {
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
/*  387 */     float[] key = this.key; while (true) {
/*      */       float curr; int last;
/*  389 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  391 */         if (Float.floatToIntBits(curr = key[pos]) == 0) {
/*  392 */           key[last] = 0.0F;
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
/*      */   public double remove(float k) {
/*  407 */     if (this.strategy.equals(k, 0.0F)) {
/*  408 */       if (this.containsNullKey)
/*  409 */         return removeNullEntry(); 
/*  410 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  413 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  416 */     if (Float.floatToIntBits(
/*  417 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  418 */       return this.defRetValue; 
/*  419 */     if (this.strategy.equals(k, curr))
/*  420 */       return removeEntry(pos); 
/*      */     while (true) {
/*  422 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  423 */         return this.defRetValue; 
/*  424 */       if (this.strategy.equals(k, curr)) {
/*  425 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public double get(float k) {
/*  431 */     if (this.strategy.equals(k, 0.0F)) {
/*  432 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  434 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  437 */     if (Float.floatToIntBits(
/*  438 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  439 */       return this.defRetValue; 
/*  440 */     if (this.strategy.equals(k, curr)) {
/*  441 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  444 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  445 */         return this.defRetValue; 
/*  446 */       if (this.strategy.equals(k, curr)) {
/*  447 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(float k) {
/*  453 */     if (this.strategy.equals(k, 0.0F)) {
/*  454 */       return this.containsNullKey;
/*      */     }
/*  456 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  459 */     if (Float.floatToIntBits(
/*  460 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  461 */       return false; 
/*  462 */     if (this.strategy.equals(k, curr)) {
/*  463 */       return true;
/*      */     }
/*      */     while (true) {
/*  466 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  467 */         return false; 
/*  468 */       if (this.strategy.equals(k, curr))
/*  469 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(double v) {
/*  474 */     double[] value = this.value;
/*  475 */     float[] key = this.key;
/*  476 */     if (this.containsNullKey && Double.doubleToLongBits(value[this.n]) == Double.doubleToLongBits(v))
/*  477 */       return true; 
/*  478 */     for (int i = this.n; i-- != 0;) {
/*  479 */       if (Float.floatToIntBits(key[i]) != 0 && 
/*  480 */         Double.doubleToLongBits(value[i]) == Double.doubleToLongBits(v))
/*  481 */         return true; 
/*  482 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double getOrDefault(float k, double defaultValue) {
/*  488 */     if (this.strategy.equals(k, 0.0F)) {
/*  489 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  491 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  494 */     if (Float.floatToIntBits(
/*  495 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  496 */       return defaultValue; 
/*  497 */     if (this.strategy.equals(k, curr)) {
/*  498 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  501 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  502 */         return defaultValue; 
/*  503 */       if (this.strategy.equals(k, curr)) {
/*  504 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public double putIfAbsent(float k, double v) {
/*  510 */     int pos = find(k);
/*  511 */     if (pos >= 0)
/*  512 */       return this.value[pos]; 
/*  513 */     insert(-pos - 1, k, v);
/*  514 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(float k, double v) {
/*  520 */     if (this.strategy.equals(k, 0.0F)) {
/*  521 */       if (this.containsNullKey && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[this.n])) {
/*  522 */         removeNullEntry();
/*  523 */         return true;
/*      */       } 
/*  525 */       return false;
/*      */     } 
/*      */     
/*  528 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  531 */     if (Float.floatToIntBits(
/*  532 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  533 */       return false; 
/*  534 */     if (this.strategy.equals(k, curr) && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
/*  535 */       removeEntry(pos);
/*  536 */       return true;
/*      */     } 
/*      */     while (true) {
/*  539 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  540 */         return false; 
/*  541 */       if (this.strategy.equals(k, curr) && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
/*  542 */         removeEntry(pos);
/*  543 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(float k, double oldValue, double v) {
/*  550 */     int pos = find(k);
/*  551 */     if (pos < 0 || Double.doubleToLongBits(oldValue) != Double.doubleToLongBits(this.value[pos]))
/*  552 */       return false; 
/*  553 */     this.value[pos] = v;
/*  554 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public double replace(float k, double v) {
/*  559 */     int pos = find(k);
/*  560 */     if (pos < 0)
/*  561 */       return this.defRetValue; 
/*  562 */     double oldValue = this.value[pos];
/*  563 */     this.value[pos] = v;
/*  564 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public double computeIfAbsent(float k, DoubleUnaryOperator mappingFunction) {
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
/*      */   public double computeIfAbsentNullable(float k, DoubleFunction<? extends Double> mappingFunction) {
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
/*      */   public double computeIfPresent(float k, BiFunction<? super Float, ? super Double, ? extends Double> remappingFunction) {
/*  596 */     Objects.requireNonNull(remappingFunction);
/*  597 */     int pos = find(k);
/*  598 */     if (pos < 0)
/*  599 */       return this.defRetValue; 
/*  600 */     Double newValue = remappingFunction.apply(Float.valueOf(k), Double.valueOf(this.value[pos]));
/*  601 */     if (newValue == null) {
/*  602 */       if (this.strategy.equals(k, 0.0F)) {
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
/*      */   public double compute(float k, BiFunction<? super Float, ? super Double, ? extends Double> remappingFunction) {
/*  614 */     Objects.requireNonNull(remappingFunction);
/*  615 */     int pos = find(k);
/*  616 */     Double newValue = remappingFunction.apply(Float.valueOf(k), (pos >= 0) ? Double.valueOf(this.value[pos]) : null);
/*  617 */     if (newValue == null) {
/*  618 */       if (pos >= 0)
/*  619 */         if (this.strategy.equals(k, 0.0F)) {
/*  620 */           removeNullEntry();
/*      */         } else {
/*  622 */           removeEntry(pos);
/*      */         }  
/*  624 */       return this.defRetValue;
/*      */     } 
/*  626 */     double newVal = newValue.doubleValue();
/*  627 */     if (pos < 0) {
/*  628 */       insert(-pos - 1, k, newVal);
/*  629 */       return newVal;
/*      */     } 
/*  631 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double merge(float k, double v, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/*  637 */     Objects.requireNonNull(remappingFunction);
/*  638 */     int pos = find(k);
/*  639 */     if (pos < 0) {
/*  640 */       insert(-pos - 1, k, v);
/*  641 */       return v;
/*      */     } 
/*  643 */     Double newValue = remappingFunction.apply(Double.valueOf(this.value[pos]), Double.valueOf(v));
/*  644 */     if (newValue == null) {
/*  645 */       if (this.strategy.equals(k, 0.0F)) {
/*  646 */         removeNullEntry();
/*      */       } else {
/*  648 */         removeEntry(pos);
/*  649 */       }  return this.defRetValue;
/*      */     } 
/*  651 */     this.value[pos] = newValue.doubleValue(); return newValue.doubleValue();
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
/*  662 */     if (this.size == 0)
/*      */       return; 
/*  664 */     this.size = 0;
/*  665 */     this.containsNullKey = false;
/*  666 */     Arrays.fill(this.key, 0.0F);
/*      */   }
/*      */   
/*      */   public int size() {
/*  670 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  674 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Float2DoubleMap.Entry, Map.Entry<Float, Double>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  686 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public float getFloatKey() {
/*  692 */       return Float2DoubleOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public double getDoubleValue() {
/*  696 */       return Float2DoubleOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public double setValue(double v) {
/*  700 */       double oldValue = Float2DoubleOpenCustomHashMap.this.value[this.index];
/*  701 */       Float2DoubleOpenCustomHashMap.this.value[this.index] = v;
/*  702 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float getKey() {
/*  712 */       return Float.valueOf(Float2DoubleOpenCustomHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double getValue() {
/*  722 */       return Double.valueOf(Float2DoubleOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double setValue(Double v) {
/*  732 */       return Double.valueOf(setValue(v.doubleValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  737 */       if (!(o instanceof Map.Entry))
/*  738 */         return false; 
/*  739 */       Map.Entry<Float, Double> e = (Map.Entry<Float, Double>)o;
/*  740 */       return (Float2DoubleOpenCustomHashMap.this.strategy.equals(Float2DoubleOpenCustomHashMap.this.key[this.index], ((Float)e.getKey()).floatValue()) && 
/*  741 */         Double.doubleToLongBits(Float2DoubleOpenCustomHashMap.this.value[this.index]) == Double.doubleToLongBits(((Double)e.getValue()).doubleValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  745 */       return Float2DoubleOpenCustomHashMap.this.strategy.hashCode(Float2DoubleOpenCustomHashMap.this.key[this.index]) ^ HashCommon.double2int(Float2DoubleOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  749 */       return Float2DoubleOpenCustomHashMap.this.key[this.index] + "=>" + Float2DoubleOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  759 */     int pos = Float2DoubleOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  766 */     int last = -1;
/*      */     
/*  768 */     int c = Float2DoubleOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  772 */     boolean mustReturnNullKey = Float2DoubleOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     FloatArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  779 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  782 */       if (!hasNext())
/*  783 */         throw new NoSuchElementException(); 
/*  784 */       this.c--;
/*  785 */       if (this.mustReturnNullKey) {
/*  786 */         this.mustReturnNullKey = false;
/*  787 */         return this.last = Float2DoubleOpenCustomHashMap.this.n;
/*      */       } 
/*  789 */       float[] key = Float2DoubleOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  791 */         if (--this.pos < 0) {
/*      */           
/*  793 */           this.last = Integer.MIN_VALUE;
/*  794 */           float k = this.wrapped.getFloat(-this.pos - 1);
/*  795 */           int p = HashCommon.mix(Float2DoubleOpenCustomHashMap.this.strategy.hashCode(k)) & Float2DoubleOpenCustomHashMap.this.mask;
/*  796 */           while (!Float2DoubleOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  797 */             p = p + 1 & Float2DoubleOpenCustomHashMap.this.mask; 
/*  798 */           return p;
/*      */         } 
/*  800 */         if (Float.floatToIntBits(key[this.pos]) != 0) {
/*  801 */           return this.last = this.pos;
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
/*  815 */       float[] key = Float2DoubleOpenCustomHashMap.this.key; while (true) {
/*      */         float curr; int last;
/*  817 */         pos = (last = pos) + 1 & Float2DoubleOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  819 */           if (Float.floatToIntBits(curr = key[pos]) == 0) {
/*  820 */             key[last] = 0.0F;
/*      */             return;
/*      */           } 
/*  823 */           int slot = HashCommon.mix(Float2DoubleOpenCustomHashMap.this.strategy.hashCode(curr)) & Float2DoubleOpenCustomHashMap.this.mask;
/*  824 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  826 */           pos = pos + 1 & Float2DoubleOpenCustomHashMap.this.mask;
/*      */         } 
/*  828 */         if (pos < last) {
/*  829 */           if (this.wrapped == null)
/*  830 */             this.wrapped = new FloatArrayList(2); 
/*  831 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  833 */         key[last] = curr;
/*  834 */         Float2DoubleOpenCustomHashMap.this.value[last] = Float2DoubleOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  838 */       if (this.last == -1)
/*  839 */         throw new IllegalStateException(); 
/*  840 */       if (this.last == Float2DoubleOpenCustomHashMap.this.n) {
/*  841 */         Float2DoubleOpenCustomHashMap.this.containsNullKey = false;
/*  842 */       } else if (this.pos >= 0) {
/*  843 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  846 */         Float2DoubleOpenCustomHashMap.this.remove(this.wrapped.getFloat(-this.pos - 1));
/*  847 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  850 */       Float2DoubleOpenCustomHashMap.this.size--;
/*  851 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  856 */       int i = n;
/*  857 */       while (i-- != 0 && hasNext())
/*  858 */         nextEntry(); 
/*  859 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Float2DoubleMap.Entry> { private Float2DoubleOpenCustomHashMap.MapEntry entry;
/*      */     
/*      */     public Float2DoubleOpenCustomHashMap.MapEntry next() {
/*  866 */       return this.entry = new Float2DoubleOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  870 */       super.remove();
/*  871 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Float2DoubleMap.Entry> { private FastEntryIterator() {
/*  875 */       this.entry = new Float2DoubleOpenCustomHashMap.MapEntry();
/*      */     } private final Float2DoubleOpenCustomHashMap.MapEntry entry;
/*      */     public Float2DoubleOpenCustomHashMap.MapEntry next() {
/*  878 */       this.entry.index = nextEntry();
/*  879 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Float2DoubleMap.Entry> implements Float2DoubleMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Float2DoubleMap.Entry> iterator() {
/*  885 */       return new Float2DoubleOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Float2DoubleMap.Entry> fastIterator() {
/*  889 */       return new Float2DoubleOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  894 */       if (!(o instanceof Map.Entry))
/*  895 */         return false; 
/*  896 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  897 */       if (e.getKey() == null || !(e.getKey() instanceof Float))
/*  898 */         return false; 
/*  899 */       if (e.getValue() == null || !(e.getValue() instanceof Double))
/*  900 */         return false; 
/*  901 */       float k = ((Float)e.getKey()).floatValue();
/*  902 */       double v = ((Double)e.getValue()).doubleValue();
/*  903 */       if (Float2DoubleOpenCustomHashMap.this.strategy.equals(k, 0.0F)) {
/*  904 */         return (Float2DoubleOpenCustomHashMap.this.containsNullKey && 
/*  905 */           Double.doubleToLongBits(Float2DoubleOpenCustomHashMap.this.value[Float2DoubleOpenCustomHashMap.this.n]) == Double.doubleToLongBits(v));
/*      */       }
/*  907 */       float[] key = Float2DoubleOpenCustomHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/*  910 */       if (Float.floatToIntBits(
/*  911 */           curr = key[pos = HashCommon.mix(Float2DoubleOpenCustomHashMap.this.strategy.hashCode(k)) & Float2DoubleOpenCustomHashMap.this.mask]) == 0)
/*  912 */         return false; 
/*  913 */       if (Float2DoubleOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  914 */         return (Double.doubleToLongBits(Float2DoubleOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v));
/*      */       }
/*      */       while (true) {
/*  917 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2DoubleOpenCustomHashMap.this.mask]) == 0)
/*  918 */           return false; 
/*  919 */         if (Float2DoubleOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  920 */           return (Double.doubleToLongBits(Float2DoubleOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v));
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  926 */       if (!(o instanceof Map.Entry))
/*  927 */         return false; 
/*  928 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  929 */       if (e.getKey() == null || !(e.getKey() instanceof Float))
/*  930 */         return false; 
/*  931 */       if (e.getValue() == null || !(e.getValue() instanceof Double))
/*  932 */         return false; 
/*  933 */       float k = ((Float)e.getKey()).floatValue();
/*  934 */       double v = ((Double)e.getValue()).doubleValue();
/*  935 */       if (Float2DoubleOpenCustomHashMap.this.strategy.equals(k, 0.0F)) {
/*  936 */         if (Float2DoubleOpenCustomHashMap.this.containsNullKey && Double.doubleToLongBits(Float2DoubleOpenCustomHashMap.this.value[Float2DoubleOpenCustomHashMap.this.n]) == Double.doubleToLongBits(v)) {
/*  937 */           Float2DoubleOpenCustomHashMap.this.removeNullEntry();
/*  938 */           return true;
/*      */         } 
/*  940 */         return false;
/*      */       } 
/*      */       
/*  943 */       float[] key = Float2DoubleOpenCustomHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/*  946 */       if (Float.floatToIntBits(
/*  947 */           curr = key[pos = HashCommon.mix(Float2DoubleOpenCustomHashMap.this.strategy.hashCode(k)) & Float2DoubleOpenCustomHashMap.this.mask]) == 0)
/*  948 */         return false; 
/*  949 */       if (Float2DoubleOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  950 */         if (Double.doubleToLongBits(Float2DoubleOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
/*  951 */           Float2DoubleOpenCustomHashMap.this.removeEntry(pos);
/*  952 */           return true;
/*      */         } 
/*  954 */         return false;
/*      */       } 
/*      */       while (true) {
/*  957 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2DoubleOpenCustomHashMap.this.mask]) == 0)
/*  958 */           return false; 
/*  959 */         if (Float2DoubleOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  960 */           Double.doubleToLongBits(Float2DoubleOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
/*  961 */           Float2DoubleOpenCustomHashMap.this.removeEntry(pos);
/*  962 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  969 */       return Float2DoubleOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  973 */       Float2DoubleOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Float2DoubleMap.Entry> consumer) {
/*  978 */       if (Float2DoubleOpenCustomHashMap.this.containsNullKey)
/*  979 */         consumer.accept(new AbstractFloat2DoubleMap.BasicEntry(Float2DoubleOpenCustomHashMap.this.key[Float2DoubleOpenCustomHashMap.this.n], Float2DoubleOpenCustomHashMap.this.value[Float2DoubleOpenCustomHashMap.this.n])); 
/*  980 */       for (int pos = Float2DoubleOpenCustomHashMap.this.n; pos-- != 0;) {
/*  981 */         if (Float.floatToIntBits(Float2DoubleOpenCustomHashMap.this.key[pos]) != 0)
/*  982 */           consumer.accept(new AbstractFloat2DoubleMap.BasicEntry(Float2DoubleOpenCustomHashMap.this.key[pos], Float2DoubleOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Float2DoubleMap.Entry> consumer) {
/*  987 */       AbstractFloat2DoubleMap.BasicEntry entry = new AbstractFloat2DoubleMap.BasicEntry();
/*  988 */       if (Float2DoubleOpenCustomHashMap.this.containsNullKey) {
/*  989 */         entry.key = Float2DoubleOpenCustomHashMap.this.key[Float2DoubleOpenCustomHashMap.this.n];
/*  990 */         entry.value = Float2DoubleOpenCustomHashMap.this.value[Float2DoubleOpenCustomHashMap.this.n];
/*  991 */         consumer.accept(entry);
/*      */       } 
/*  993 */       for (int pos = Float2DoubleOpenCustomHashMap.this.n; pos-- != 0;) {
/*  994 */         if (Float.floatToIntBits(Float2DoubleOpenCustomHashMap.this.key[pos]) != 0) {
/*  995 */           entry.key = Float2DoubleOpenCustomHashMap.this.key[pos];
/*  996 */           entry.value = Float2DoubleOpenCustomHashMap.this.value[pos];
/*  997 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Float2DoubleMap.FastEntrySet float2DoubleEntrySet() {
/* 1003 */     if (this.entries == null)
/* 1004 */       this.entries = new MapEntrySet(); 
/* 1005 */     return this.entries;
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
/*      */     implements FloatIterator
/*      */   {
/*      */     public float nextFloat() {
/* 1022 */       return Float2DoubleOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractFloatSet { private KeySet() {}
/*      */     
/*      */     public FloatIterator iterator() {
/* 1028 */       return new Float2DoubleOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/* 1033 */       if (Float2DoubleOpenCustomHashMap.this.containsNullKey)
/* 1034 */         consumer.accept(Float2DoubleOpenCustomHashMap.this.key[Float2DoubleOpenCustomHashMap.this.n]); 
/* 1035 */       for (int pos = Float2DoubleOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1036 */         float k = Float2DoubleOpenCustomHashMap.this.key[pos];
/* 1037 */         if (Float.floatToIntBits(k) != 0)
/* 1038 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1043 */       return Float2DoubleOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(float k) {
/* 1047 */       return Float2DoubleOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(float k) {
/* 1051 */       int oldSize = Float2DoubleOpenCustomHashMap.this.size;
/* 1052 */       Float2DoubleOpenCustomHashMap.this.remove(k);
/* 1053 */       return (Float2DoubleOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1057 */       Float2DoubleOpenCustomHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public FloatSet keySet() {
/* 1062 */     if (this.keys == null)
/* 1063 */       this.keys = new KeySet(); 
/* 1064 */     return this.keys;
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
/* 1081 */       return Float2DoubleOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public DoubleCollection values() {
/* 1086 */     if (this.values == null)
/* 1087 */       this.values = (DoubleCollection)new AbstractDoubleCollection()
/*      */         {
/*      */           public DoubleIterator iterator() {
/* 1090 */             return new Float2DoubleOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1094 */             return Float2DoubleOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(double v) {
/* 1098 */             return Float2DoubleOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1102 */             Float2DoubleOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(DoubleConsumer consumer)
/*      */           {
/* 1107 */             if (Float2DoubleOpenCustomHashMap.this.containsNullKey)
/* 1108 */               consumer.accept(Float2DoubleOpenCustomHashMap.this.value[Float2DoubleOpenCustomHashMap.this.n]); 
/* 1109 */             for (int pos = Float2DoubleOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1110 */               if (Float.floatToIntBits(Float2DoubleOpenCustomHashMap.this.key[pos]) != 0)
/* 1111 */                 consumer.accept(Float2DoubleOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1114 */     return this.values;
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
/* 1131 */     return trim(this.size);
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
/* 1155 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1156 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1157 */       return true; 
/*      */     try {
/* 1159 */       rehash(l);
/* 1160 */     } catch (OutOfMemoryError cantDoIt) {
/* 1161 */       return false;
/*      */     } 
/* 1163 */     return true;
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
/* 1179 */     float[] key = this.key;
/* 1180 */     double[] value = this.value;
/* 1181 */     int mask = newN - 1;
/* 1182 */     float[] newKey = new float[newN + 1];
/* 1183 */     double[] newValue = new double[newN + 1];
/* 1184 */     int i = this.n;
/* 1185 */     for (int j = realSize(); j-- != 0; ) {
/* 1186 */       while (Float.floatToIntBits(key[--i]) == 0); int pos;
/* 1187 */       if (Float.floatToIntBits(newKey[
/* 1188 */             pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask]) != 0)
/* 1189 */         while (Float.floatToIntBits(newKey[pos = pos + 1 & mask]) != 0); 
/* 1190 */       newKey[pos] = key[i];
/* 1191 */       newValue[pos] = value[i];
/*      */     } 
/* 1193 */     newValue[newN] = value[this.n];
/* 1194 */     this.n = newN;
/* 1195 */     this.mask = mask;
/* 1196 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1197 */     this.key = newKey;
/* 1198 */     this.value = newValue;
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
/*      */   public Float2DoubleOpenCustomHashMap clone() {
/*      */     Float2DoubleOpenCustomHashMap c;
/*      */     try {
/* 1215 */       c = (Float2DoubleOpenCustomHashMap)super.clone();
/* 1216 */     } catch (CloneNotSupportedException cantHappen) {
/* 1217 */       throw new InternalError();
/*      */     } 
/* 1219 */     c.keys = null;
/* 1220 */     c.values = null;
/* 1221 */     c.entries = null;
/* 1222 */     c.containsNullKey = this.containsNullKey;
/* 1223 */     c.key = (float[])this.key.clone();
/* 1224 */     c.value = (double[])this.value.clone();
/* 1225 */     c.strategy = this.strategy;
/* 1226 */     return c;
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
/* 1239 */     int h = 0;
/* 1240 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1241 */       while (Float.floatToIntBits(this.key[i]) == 0)
/* 1242 */         i++; 
/* 1243 */       t = this.strategy.hashCode(this.key[i]);
/* 1244 */       t ^= HashCommon.double2int(this.value[i]);
/* 1245 */       h += t;
/* 1246 */       i++;
/*      */     } 
/*      */     
/* 1249 */     if (this.containsNullKey)
/* 1250 */       h += HashCommon.double2int(this.value[this.n]); 
/* 1251 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1254 */     float[] key = this.key;
/* 1255 */     double[] value = this.value;
/* 1256 */     MapIterator i = new MapIterator();
/* 1257 */     s.defaultWriteObject();
/* 1258 */     for (int j = this.size; j-- != 0; ) {
/* 1259 */       int e = i.nextEntry();
/* 1260 */       s.writeFloat(key[e]);
/* 1261 */       s.writeDouble(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1266 */     s.defaultReadObject();
/* 1267 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1268 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1269 */     this.mask = this.n - 1;
/* 1270 */     float[] key = this.key = new float[this.n + 1];
/* 1271 */     double[] value = this.value = new double[this.n + 1];
/*      */ 
/*      */     
/* 1274 */     for (int i = this.size; i-- != 0; ) {
/* 1275 */       int pos; float k = s.readFloat();
/* 1276 */       double v = s.readDouble();
/* 1277 */       if (this.strategy.equals(k, 0.0F)) {
/* 1278 */         pos = this.n;
/* 1279 */         this.containsNullKey = true;
/*      */       } else {
/* 1281 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1282 */         while (Float.floatToIntBits(key[pos]) != 0)
/* 1283 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1285 */       key[pos] = k;
/* 1286 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\Float2DoubleOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */