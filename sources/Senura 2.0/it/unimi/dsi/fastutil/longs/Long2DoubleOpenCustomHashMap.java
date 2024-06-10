/*      */ package it.unimi.dsi.fastutil.longs;
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
/*      */ import java.util.function.LongConsumer;
/*      */ import java.util.function.LongFunction;
/*      */ import java.util.function.LongToDoubleFunction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Long2DoubleOpenCustomHashMap
/*      */   extends AbstractLong2DoubleMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient long[] key;
/*      */   protected transient double[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected LongHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Long2DoubleMap.FastEntrySet entries;
/*      */   protected transient LongSet keys;
/*      */   protected transient DoubleCollection values;
/*      */   
/*      */   public Long2DoubleOpenCustomHashMap(int expected, float f, LongHash.Strategy strategy) {
/*  107 */     this.strategy = strategy;
/*  108 */     if (f <= 0.0F || f > 1.0F)
/*  109 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  110 */     if (expected < 0)
/*  111 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  112 */     this.f = f;
/*  113 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  114 */     this.mask = this.n - 1;
/*  115 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  116 */     this.key = new long[this.n + 1];
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
/*      */   public Long2DoubleOpenCustomHashMap(int expected, LongHash.Strategy strategy) {
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
/*      */   public Long2DoubleOpenCustomHashMap(LongHash.Strategy strategy) {
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
/*      */   public Long2DoubleOpenCustomHashMap(Map<? extends Long, ? extends Double> m, float f, LongHash.Strategy strategy) {
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
/*      */   public Long2DoubleOpenCustomHashMap(Map<? extends Long, ? extends Double> m, LongHash.Strategy strategy) {
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
/*      */   public Long2DoubleOpenCustomHashMap(Long2DoubleMap m, float f, LongHash.Strategy strategy) {
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
/*      */   public Long2DoubleOpenCustomHashMap(Long2DoubleMap m, LongHash.Strategy strategy) {
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
/*      */   public Long2DoubleOpenCustomHashMap(long[] k, double[] v, float f, LongHash.Strategy strategy) {
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
/*      */   public Long2DoubleOpenCustomHashMap(long[] k, double[] v, LongHash.Strategy strategy) {
/*  236 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongHash.Strategy strategy() {
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
/*      */   public void putAll(Map<? extends Long, ? extends Double> m) {
/*  278 */     if (this.f <= 0.5D) {
/*  279 */       ensureCapacity(m.size());
/*      */     } else {
/*  281 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  283 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(long k) {
/*  287 */     if (this.strategy.equals(k, 0L)) {
/*  288 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  290 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  293 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  294 */       return -(pos + 1); 
/*  295 */     if (this.strategy.equals(k, curr)) {
/*  296 */       return pos;
/*      */     }
/*      */     while (true) {
/*  299 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  300 */         return -(pos + 1); 
/*  301 */       if (this.strategy.equals(k, curr))
/*  302 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, long k, double v) {
/*  306 */     if (pos == this.n)
/*  307 */       this.containsNullKey = true; 
/*  308 */     this.key[pos] = k;
/*  309 */     this.value[pos] = v;
/*  310 */     if (this.size++ >= this.maxFill) {
/*  311 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public double put(long k, double v) {
/*  317 */     int pos = find(k);
/*  318 */     if (pos < 0) {
/*  319 */       insert(-pos - 1, k, v);
/*  320 */       return this.defRetValue;
/*      */     } 
/*  322 */     double oldValue = this.value[pos];
/*  323 */     this.value[pos] = v;
/*  324 */     return oldValue;
/*      */   }
/*      */   private double addToValue(int pos, double incr) {
/*  327 */     double oldValue = this.value[pos];
/*  328 */     this.value[pos] = oldValue + incr;
/*  329 */     return oldValue;
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
/*      */   public double addTo(long k, double incr) {
/*      */     int pos;
/*  349 */     if (this.strategy.equals(k, 0L)) {
/*  350 */       if (this.containsNullKey)
/*  351 */         return addToValue(this.n, incr); 
/*  352 */       pos = this.n;
/*  353 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  356 */       long[] key = this.key;
/*      */       long curr;
/*  358 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != 0L) {
/*  359 */         if (this.strategy.equals(curr, k))
/*  360 */           return addToValue(pos, incr); 
/*  361 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0L) {
/*  362 */           if (this.strategy.equals(curr, k))
/*  363 */             return addToValue(pos, incr); 
/*      */         } 
/*      */       } 
/*  366 */     }  this.key[pos] = k;
/*  367 */     this.value[pos] = this.defRetValue + incr;
/*  368 */     if (this.size++ >= this.maxFill) {
/*  369 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  372 */     return this.defRetValue;
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
/*  385 */     long[] key = this.key; while (true) {
/*      */       long curr; int last;
/*  387 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  389 */         if ((curr = key[pos]) == 0L) {
/*  390 */           key[last] = 0L;
/*      */           return;
/*      */         } 
/*  393 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  394 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  396 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  398 */       key[last] = curr;
/*  399 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public double remove(long k) {
/*  405 */     if (this.strategy.equals(k, 0L)) {
/*  406 */       if (this.containsNullKey)
/*  407 */         return removeNullEntry(); 
/*  408 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  411 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  414 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  415 */       return this.defRetValue; 
/*  416 */     if (this.strategy.equals(k, curr))
/*  417 */       return removeEntry(pos); 
/*      */     while (true) {
/*  419 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  420 */         return this.defRetValue; 
/*  421 */       if (this.strategy.equals(k, curr)) {
/*  422 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public double get(long k) {
/*  428 */     if (this.strategy.equals(k, 0L)) {
/*  429 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  431 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  434 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  435 */       return this.defRetValue; 
/*  436 */     if (this.strategy.equals(k, curr)) {
/*  437 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  440 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  441 */         return this.defRetValue; 
/*  442 */       if (this.strategy.equals(k, curr)) {
/*  443 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(long k) {
/*  449 */     if (this.strategy.equals(k, 0L)) {
/*  450 */       return this.containsNullKey;
/*      */     }
/*  452 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  455 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  456 */       return false; 
/*  457 */     if (this.strategy.equals(k, curr)) {
/*  458 */       return true;
/*      */     }
/*      */     while (true) {
/*  461 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  462 */         return false; 
/*  463 */       if (this.strategy.equals(k, curr))
/*  464 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(double v) {
/*  469 */     double[] value = this.value;
/*  470 */     long[] key = this.key;
/*  471 */     if (this.containsNullKey && Double.doubleToLongBits(value[this.n]) == Double.doubleToLongBits(v))
/*  472 */       return true; 
/*  473 */     for (int i = this.n; i-- != 0;) {
/*  474 */       if (key[i] != 0L && Double.doubleToLongBits(value[i]) == Double.doubleToLongBits(v))
/*  475 */         return true; 
/*  476 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double getOrDefault(long k, double defaultValue) {
/*  482 */     if (this.strategy.equals(k, 0L)) {
/*  483 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  485 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  488 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  489 */       return defaultValue; 
/*  490 */     if (this.strategy.equals(k, curr)) {
/*  491 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  494 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  495 */         return defaultValue; 
/*  496 */       if (this.strategy.equals(k, curr)) {
/*  497 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public double putIfAbsent(long k, double v) {
/*  503 */     int pos = find(k);
/*  504 */     if (pos >= 0)
/*  505 */       return this.value[pos]; 
/*  506 */     insert(-pos - 1, k, v);
/*  507 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(long k, double v) {
/*  513 */     if (this.strategy.equals(k, 0L)) {
/*  514 */       if (this.containsNullKey && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[this.n])) {
/*  515 */         removeNullEntry();
/*  516 */         return true;
/*      */       } 
/*  518 */       return false;
/*      */     } 
/*      */     
/*  521 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  524 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  525 */       return false; 
/*  526 */     if (this.strategy.equals(k, curr) && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
/*  527 */       removeEntry(pos);
/*  528 */       return true;
/*      */     } 
/*      */     while (true) {
/*  531 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  532 */         return false; 
/*  533 */       if (this.strategy.equals(k, curr) && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
/*  534 */         removeEntry(pos);
/*  535 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(long k, double oldValue, double v) {
/*  542 */     int pos = find(k);
/*  543 */     if (pos < 0 || Double.doubleToLongBits(oldValue) != Double.doubleToLongBits(this.value[pos]))
/*  544 */       return false; 
/*  545 */     this.value[pos] = v;
/*  546 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public double replace(long k, double v) {
/*  551 */     int pos = find(k);
/*  552 */     if (pos < 0)
/*  553 */       return this.defRetValue; 
/*  554 */     double oldValue = this.value[pos];
/*  555 */     this.value[pos] = v;
/*  556 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public double computeIfAbsent(long k, LongToDoubleFunction mappingFunction) {
/*  561 */     Objects.requireNonNull(mappingFunction);
/*  562 */     int pos = find(k);
/*  563 */     if (pos >= 0)
/*  564 */       return this.value[pos]; 
/*  565 */     double newValue = mappingFunction.applyAsDouble(k);
/*  566 */     insert(-pos - 1, k, newValue);
/*  567 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeIfAbsentNullable(long k, LongFunction<? extends Double> mappingFunction) {
/*  573 */     Objects.requireNonNull(mappingFunction);
/*  574 */     int pos = find(k);
/*  575 */     if (pos >= 0)
/*  576 */       return this.value[pos]; 
/*  577 */     Double newValue = mappingFunction.apply(k);
/*  578 */     if (newValue == null)
/*  579 */       return this.defRetValue; 
/*  580 */     double v = newValue.doubleValue();
/*  581 */     insert(-pos - 1, k, v);
/*  582 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeIfPresent(long k, BiFunction<? super Long, ? super Double, ? extends Double> remappingFunction) {
/*  588 */     Objects.requireNonNull(remappingFunction);
/*  589 */     int pos = find(k);
/*  590 */     if (pos < 0)
/*  591 */       return this.defRetValue; 
/*  592 */     Double newValue = remappingFunction.apply(Long.valueOf(k), Double.valueOf(this.value[pos]));
/*  593 */     if (newValue == null) {
/*  594 */       if (this.strategy.equals(k, 0L)) {
/*  595 */         removeNullEntry();
/*      */       } else {
/*  597 */         removeEntry(pos);
/*  598 */       }  return this.defRetValue;
/*      */     } 
/*  600 */     this.value[pos] = newValue.doubleValue(); return newValue.doubleValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double compute(long k, BiFunction<? super Long, ? super Double, ? extends Double> remappingFunction) {
/*  606 */     Objects.requireNonNull(remappingFunction);
/*  607 */     int pos = find(k);
/*  608 */     Double newValue = remappingFunction.apply(Long.valueOf(k), (pos >= 0) ? Double.valueOf(this.value[pos]) : null);
/*  609 */     if (newValue == null) {
/*  610 */       if (pos >= 0)
/*  611 */         if (this.strategy.equals(k, 0L)) {
/*  612 */           removeNullEntry();
/*      */         } else {
/*  614 */           removeEntry(pos);
/*      */         }  
/*  616 */       return this.defRetValue;
/*      */     } 
/*  618 */     double newVal = newValue.doubleValue();
/*  619 */     if (pos < 0) {
/*  620 */       insert(-pos - 1, k, newVal);
/*  621 */       return newVal;
/*      */     } 
/*  623 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double merge(long k, double v, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/*  629 */     Objects.requireNonNull(remappingFunction);
/*  630 */     int pos = find(k);
/*  631 */     if (pos < 0) {
/*  632 */       insert(-pos - 1, k, v);
/*  633 */       return v;
/*      */     } 
/*  635 */     Double newValue = remappingFunction.apply(Double.valueOf(this.value[pos]), Double.valueOf(v));
/*  636 */     if (newValue == null) {
/*  637 */       if (this.strategy.equals(k, 0L)) {
/*  638 */         removeNullEntry();
/*      */       } else {
/*  640 */         removeEntry(pos);
/*  641 */       }  return this.defRetValue;
/*      */     } 
/*  643 */     this.value[pos] = newValue.doubleValue(); return newValue.doubleValue();
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
/*  654 */     if (this.size == 0)
/*      */       return; 
/*  656 */     this.size = 0;
/*  657 */     this.containsNullKey = false;
/*  658 */     Arrays.fill(this.key, 0L);
/*      */   }
/*      */   
/*      */   public int size() {
/*  662 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  666 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Long2DoubleMap.Entry, Map.Entry<Long, Double>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  678 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public long getLongKey() {
/*  684 */       return Long2DoubleOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public double getDoubleValue() {
/*  688 */       return Long2DoubleOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public double setValue(double v) {
/*  692 */       double oldValue = Long2DoubleOpenCustomHashMap.this.value[this.index];
/*  693 */       Long2DoubleOpenCustomHashMap.this.value[this.index] = v;
/*  694 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long getKey() {
/*  704 */       return Long.valueOf(Long2DoubleOpenCustomHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double getValue() {
/*  714 */       return Double.valueOf(Long2DoubleOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double setValue(Double v) {
/*  724 */       return Double.valueOf(setValue(v.doubleValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  729 */       if (!(o instanceof Map.Entry))
/*  730 */         return false; 
/*  731 */       Map.Entry<Long, Double> e = (Map.Entry<Long, Double>)o;
/*  732 */       return (Long2DoubleOpenCustomHashMap.this.strategy.equals(Long2DoubleOpenCustomHashMap.this.key[this.index], ((Long)e.getKey()).longValue()) && 
/*  733 */         Double.doubleToLongBits(Long2DoubleOpenCustomHashMap.this.value[this.index]) == Double.doubleToLongBits(((Double)e.getValue()).doubleValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  737 */       return Long2DoubleOpenCustomHashMap.this.strategy.hashCode(Long2DoubleOpenCustomHashMap.this.key[this.index]) ^ HashCommon.double2int(Long2DoubleOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  741 */       return Long2DoubleOpenCustomHashMap.this.key[this.index] + "=>" + Long2DoubleOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  751 */     int pos = Long2DoubleOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  758 */     int last = -1;
/*      */     
/*  760 */     int c = Long2DoubleOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  764 */     boolean mustReturnNullKey = Long2DoubleOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     LongArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  771 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  774 */       if (!hasNext())
/*  775 */         throw new NoSuchElementException(); 
/*  776 */       this.c--;
/*  777 */       if (this.mustReturnNullKey) {
/*  778 */         this.mustReturnNullKey = false;
/*  779 */         return this.last = Long2DoubleOpenCustomHashMap.this.n;
/*      */       } 
/*  781 */       long[] key = Long2DoubleOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  783 */         if (--this.pos < 0) {
/*      */           
/*  785 */           this.last = Integer.MIN_VALUE;
/*  786 */           long k = this.wrapped.getLong(-this.pos - 1);
/*  787 */           int p = HashCommon.mix(Long2DoubleOpenCustomHashMap.this.strategy.hashCode(k)) & Long2DoubleOpenCustomHashMap.this.mask;
/*  788 */           while (!Long2DoubleOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  789 */             p = p + 1 & Long2DoubleOpenCustomHashMap.this.mask; 
/*  790 */           return p;
/*      */         } 
/*  792 */         if (key[this.pos] != 0L) {
/*  793 */           return this.last = this.pos;
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
/*  807 */       long[] key = Long2DoubleOpenCustomHashMap.this.key; while (true) {
/*      */         long curr; int last;
/*  809 */         pos = (last = pos) + 1 & Long2DoubleOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  811 */           if ((curr = key[pos]) == 0L) {
/*  812 */             key[last] = 0L;
/*      */             return;
/*      */           } 
/*  815 */           int slot = HashCommon.mix(Long2DoubleOpenCustomHashMap.this.strategy.hashCode(curr)) & Long2DoubleOpenCustomHashMap.this.mask;
/*  816 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  818 */           pos = pos + 1 & Long2DoubleOpenCustomHashMap.this.mask;
/*      */         } 
/*  820 */         if (pos < last) {
/*  821 */           if (this.wrapped == null)
/*  822 */             this.wrapped = new LongArrayList(2); 
/*  823 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  825 */         key[last] = curr;
/*  826 */         Long2DoubleOpenCustomHashMap.this.value[last] = Long2DoubleOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  830 */       if (this.last == -1)
/*  831 */         throw new IllegalStateException(); 
/*  832 */       if (this.last == Long2DoubleOpenCustomHashMap.this.n) {
/*  833 */         Long2DoubleOpenCustomHashMap.this.containsNullKey = false;
/*  834 */       } else if (this.pos >= 0) {
/*  835 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  838 */         Long2DoubleOpenCustomHashMap.this.remove(this.wrapped.getLong(-this.pos - 1));
/*  839 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  842 */       Long2DoubleOpenCustomHashMap.this.size--;
/*  843 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  848 */       int i = n;
/*  849 */       while (i-- != 0 && hasNext())
/*  850 */         nextEntry(); 
/*  851 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Long2DoubleMap.Entry> { private Long2DoubleOpenCustomHashMap.MapEntry entry;
/*      */     
/*      */     public Long2DoubleOpenCustomHashMap.MapEntry next() {
/*  858 */       return this.entry = new Long2DoubleOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  862 */       super.remove();
/*  863 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Long2DoubleMap.Entry> { private FastEntryIterator() {
/*  867 */       this.entry = new Long2DoubleOpenCustomHashMap.MapEntry();
/*      */     } private final Long2DoubleOpenCustomHashMap.MapEntry entry;
/*      */     public Long2DoubleOpenCustomHashMap.MapEntry next() {
/*  870 */       this.entry.index = nextEntry();
/*  871 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Long2DoubleMap.Entry> implements Long2DoubleMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Long2DoubleMap.Entry> iterator() {
/*  877 */       return new Long2DoubleOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Long2DoubleMap.Entry> fastIterator() {
/*  881 */       return new Long2DoubleOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  886 */       if (!(o instanceof Map.Entry))
/*  887 */         return false; 
/*  888 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  889 */       if (e.getKey() == null || !(e.getKey() instanceof Long))
/*  890 */         return false; 
/*  891 */       if (e.getValue() == null || !(e.getValue() instanceof Double))
/*  892 */         return false; 
/*  893 */       long k = ((Long)e.getKey()).longValue();
/*  894 */       double v = ((Double)e.getValue()).doubleValue();
/*  895 */       if (Long2DoubleOpenCustomHashMap.this.strategy.equals(k, 0L)) {
/*  896 */         return (Long2DoubleOpenCustomHashMap.this.containsNullKey && 
/*  897 */           Double.doubleToLongBits(Long2DoubleOpenCustomHashMap.this.value[Long2DoubleOpenCustomHashMap.this.n]) == Double.doubleToLongBits(v));
/*      */       }
/*  899 */       long[] key = Long2DoubleOpenCustomHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/*  902 */       if ((curr = key[pos = HashCommon.mix(Long2DoubleOpenCustomHashMap.this.strategy.hashCode(k)) & Long2DoubleOpenCustomHashMap.this.mask]) == 0L)
/*  903 */         return false; 
/*  904 */       if (Long2DoubleOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  905 */         return (Double.doubleToLongBits(Long2DoubleOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v));
/*      */       }
/*      */       while (true) {
/*  908 */         if ((curr = key[pos = pos + 1 & Long2DoubleOpenCustomHashMap.this.mask]) == 0L)
/*  909 */           return false; 
/*  910 */         if (Long2DoubleOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  911 */           return (Double.doubleToLongBits(Long2DoubleOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v));
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  917 */       if (!(o instanceof Map.Entry))
/*  918 */         return false; 
/*  919 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  920 */       if (e.getKey() == null || !(e.getKey() instanceof Long))
/*  921 */         return false; 
/*  922 */       if (e.getValue() == null || !(e.getValue() instanceof Double))
/*  923 */         return false; 
/*  924 */       long k = ((Long)e.getKey()).longValue();
/*  925 */       double v = ((Double)e.getValue()).doubleValue();
/*  926 */       if (Long2DoubleOpenCustomHashMap.this.strategy.equals(k, 0L)) {
/*  927 */         if (Long2DoubleOpenCustomHashMap.this.containsNullKey && Double.doubleToLongBits(Long2DoubleOpenCustomHashMap.this.value[Long2DoubleOpenCustomHashMap.this.n]) == Double.doubleToLongBits(v)) {
/*  928 */           Long2DoubleOpenCustomHashMap.this.removeNullEntry();
/*  929 */           return true;
/*      */         } 
/*  931 */         return false;
/*      */       } 
/*      */       
/*  934 */       long[] key = Long2DoubleOpenCustomHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/*  937 */       if ((curr = key[pos = HashCommon.mix(Long2DoubleOpenCustomHashMap.this.strategy.hashCode(k)) & Long2DoubleOpenCustomHashMap.this.mask]) == 0L)
/*  938 */         return false; 
/*  939 */       if (Long2DoubleOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  940 */         if (Double.doubleToLongBits(Long2DoubleOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
/*  941 */           Long2DoubleOpenCustomHashMap.this.removeEntry(pos);
/*  942 */           return true;
/*      */         } 
/*  944 */         return false;
/*      */       } 
/*      */       while (true) {
/*  947 */         if ((curr = key[pos = pos + 1 & Long2DoubleOpenCustomHashMap.this.mask]) == 0L)
/*  948 */           return false; 
/*  949 */         if (Long2DoubleOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  950 */           Double.doubleToLongBits(Long2DoubleOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
/*  951 */           Long2DoubleOpenCustomHashMap.this.removeEntry(pos);
/*  952 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  959 */       return Long2DoubleOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  963 */       Long2DoubleOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Long2DoubleMap.Entry> consumer) {
/*  968 */       if (Long2DoubleOpenCustomHashMap.this.containsNullKey)
/*  969 */         consumer.accept(new AbstractLong2DoubleMap.BasicEntry(Long2DoubleOpenCustomHashMap.this.key[Long2DoubleOpenCustomHashMap.this.n], Long2DoubleOpenCustomHashMap.this.value[Long2DoubleOpenCustomHashMap.this.n])); 
/*  970 */       for (int pos = Long2DoubleOpenCustomHashMap.this.n; pos-- != 0;) {
/*  971 */         if (Long2DoubleOpenCustomHashMap.this.key[pos] != 0L)
/*  972 */           consumer.accept(new AbstractLong2DoubleMap.BasicEntry(Long2DoubleOpenCustomHashMap.this.key[pos], Long2DoubleOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Long2DoubleMap.Entry> consumer) {
/*  977 */       AbstractLong2DoubleMap.BasicEntry entry = new AbstractLong2DoubleMap.BasicEntry();
/*  978 */       if (Long2DoubleOpenCustomHashMap.this.containsNullKey) {
/*  979 */         entry.key = Long2DoubleOpenCustomHashMap.this.key[Long2DoubleOpenCustomHashMap.this.n];
/*  980 */         entry.value = Long2DoubleOpenCustomHashMap.this.value[Long2DoubleOpenCustomHashMap.this.n];
/*  981 */         consumer.accept(entry);
/*      */       } 
/*  983 */       for (int pos = Long2DoubleOpenCustomHashMap.this.n; pos-- != 0;) {
/*  984 */         if (Long2DoubleOpenCustomHashMap.this.key[pos] != 0L) {
/*  985 */           entry.key = Long2DoubleOpenCustomHashMap.this.key[pos];
/*  986 */           entry.value = Long2DoubleOpenCustomHashMap.this.value[pos];
/*  987 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Long2DoubleMap.FastEntrySet long2DoubleEntrySet() {
/*  993 */     if (this.entries == null)
/*  994 */       this.entries = new MapEntrySet(); 
/*  995 */     return this.entries;
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
/*      */     implements LongIterator
/*      */   {
/*      */     public long nextLong() {
/* 1012 */       return Long2DoubleOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractLongSet { private KeySet() {}
/*      */     
/*      */     public LongIterator iterator() {
/* 1018 */       return new Long2DoubleOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(LongConsumer consumer) {
/* 1023 */       if (Long2DoubleOpenCustomHashMap.this.containsNullKey)
/* 1024 */         consumer.accept(Long2DoubleOpenCustomHashMap.this.key[Long2DoubleOpenCustomHashMap.this.n]); 
/* 1025 */       for (int pos = Long2DoubleOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1026 */         long k = Long2DoubleOpenCustomHashMap.this.key[pos];
/* 1027 */         if (k != 0L)
/* 1028 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1033 */       return Long2DoubleOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(long k) {
/* 1037 */       return Long2DoubleOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(long k) {
/* 1041 */       int oldSize = Long2DoubleOpenCustomHashMap.this.size;
/* 1042 */       Long2DoubleOpenCustomHashMap.this.remove(k);
/* 1043 */       return (Long2DoubleOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1047 */       Long2DoubleOpenCustomHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public LongSet keySet() {
/* 1052 */     if (this.keys == null)
/* 1053 */       this.keys = new KeySet(); 
/* 1054 */     return this.keys;
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
/* 1071 */       return Long2DoubleOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public DoubleCollection values() {
/* 1076 */     if (this.values == null)
/* 1077 */       this.values = (DoubleCollection)new AbstractDoubleCollection()
/*      */         {
/*      */           public DoubleIterator iterator() {
/* 1080 */             return new Long2DoubleOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1084 */             return Long2DoubleOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(double v) {
/* 1088 */             return Long2DoubleOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1092 */             Long2DoubleOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(DoubleConsumer consumer)
/*      */           {
/* 1097 */             if (Long2DoubleOpenCustomHashMap.this.containsNullKey)
/* 1098 */               consumer.accept(Long2DoubleOpenCustomHashMap.this.value[Long2DoubleOpenCustomHashMap.this.n]); 
/* 1099 */             for (int pos = Long2DoubleOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1100 */               if (Long2DoubleOpenCustomHashMap.this.key[pos] != 0L)
/* 1101 */                 consumer.accept(Long2DoubleOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1104 */     return this.values;
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
/* 1121 */     return trim(this.size);
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
/* 1145 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1146 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1147 */       return true; 
/*      */     try {
/* 1149 */       rehash(l);
/* 1150 */     } catch (OutOfMemoryError cantDoIt) {
/* 1151 */       return false;
/*      */     } 
/* 1153 */     return true;
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
/* 1169 */     long[] key = this.key;
/* 1170 */     double[] value = this.value;
/* 1171 */     int mask = newN - 1;
/* 1172 */     long[] newKey = new long[newN + 1];
/* 1173 */     double[] newValue = new double[newN + 1];
/* 1174 */     int i = this.n;
/* 1175 */     for (int j = realSize(); j-- != 0; ) {
/* 1176 */       while (key[--i] == 0L); int pos;
/* 1177 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != 0L)
/* 1178 */         while (newKey[pos = pos + 1 & mask] != 0L); 
/* 1179 */       newKey[pos] = key[i];
/* 1180 */       newValue[pos] = value[i];
/*      */     } 
/* 1182 */     newValue[newN] = value[this.n];
/* 1183 */     this.n = newN;
/* 1184 */     this.mask = mask;
/* 1185 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1186 */     this.key = newKey;
/* 1187 */     this.value = newValue;
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
/*      */   public Long2DoubleOpenCustomHashMap clone() {
/*      */     Long2DoubleOpenCustomHashMap c;
/*      */     try {
/* 1204 */       c = (Long2DoubleOpenCustomHashMap)super.clone();
/* 1205 */     } catch (CloneNotSupportedException cantHappen) {
/* 1206 */       throw new InternalError();
/*      */     } 
/* 1208 */     c.keys = null;
/* 1209 */     c.values = null;
/* 1210 */     c.entries = null;
/* 1211 */     c.containsNullKey = this.containsNullKey;
/* 1212 */     c.key = (long[])this.key.clone();
/* 1213 */     c.value = (double[])this.value.clone();
/* 1214 */     c.strategy = this.strategy;
/* 1215 */     return c;
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
/* 1228 */     int h = 0;
/* 1229 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1230 */       while (this.key[i] == 0L)
/* 1231 */         i++; 
/* 1232 */       t = this.strategy.hashCode(this.key[i]);
/* 1233 */       t ^= HashCommon.double2int(this.value[i]);
/* 1234 */       h += t;
/* 1235 */       i++;
/*      */     } 
/*      */     
/* 1238 */     if (this.containsNullKey)
/* 1239 */       h += HashCommon.double2int(this.value[this.n]); 
/* 1240 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1243 */     long[] key = this.key;
/* 1244 */     double[] value = this.value;
/* 1245 */     MapIterator i = new MapIterator();
/* 1246 */     s.defaultWriteObject();
/* 1247 */     for (int j = this.size; j-- != 0; ) {
/* 1248 */       int e = i.nextEntry();
/* 1249 */       s.writeLong(key[e]);
/* 1250 */       s.writeDouble(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1255 */     s.defaultReadObject();
/* 1256 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1257 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1258 */     this.mask = this.n - 1;
/* 1259 */     long[] key = this.key = new long[this.n + 1];
/* 1260 */     double[] value = this.value = new double[this.n + 1];
/*      */ 
/*      */     
/* 1263 */     for (int i = this.size; i-- != 0; ) {
/* 1264 */       int pos; long k = s.readLong();
/* 1265 */       double v = s.readDouble();
/* 1266 */       if (this.strategy.equals(k, 0L)) {
/* 1267 */         pos = this.n;
/* 1268 */         this.containsNullKey = true;
/*      */       } else {
/* 1270 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1271 */         while (key[pos] != 0L)
/* 1272 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1274 */       key[pos] = k;
/* 1275 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\Long2DoubleOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */