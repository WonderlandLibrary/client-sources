/*      */ package it.unimi.dsi.fastutil.ints;
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
/*      */ import java.util.function.IntConsumer;
/*      */ import java.util.function.IntFunction;
/*      */ import java.util.function.IntToDoubleFunction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Int2DoubleOpenCustomHashMap
/*      */   extends AbstractInt2DoubleMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient int[] key;
/*      */   protected transient double[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected IntHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Int2DoubleMap.FastEntrySet entries;
/*      */   protected transient IntSet keys;
/*      */   protected transient DoubleCollection values;
/*      */   
/*      */   public Int2DoubleOpenCustomHashMap(int expected, float f, IntHash.Strategy strategy) {
/*  107 */     this.strategy = strategy;
/*  108 */     if (f <= 0.0F || f > 1.0F)
/*  109 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  110 */     if (expected < 0)
/*  111 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  112 */     this.f = f;
/*  113 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  114 */     this.mask = this.n - 1;
/*  115 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  116 */     this.key = new int[this.n + 1];
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
/*      */   public Int2DoubleOpenCustomHashMap(int expected, IntHash.Strategy strategy) {
/*  128 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2DoubleOpenCustomHashMap(IntHash.Strategy strategy) {
/*  139 */     this(16, 0.75F, strategy);
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
/*      */   public Int2DoubleOpenCustomHashMap(Map<? extends Integer, ? extends Double> m, float f, IntHash.Strategy strategy) {
/*  153 */     this(m.size(), f, strategy);
/*  154 */     putAll(m);
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
/*      */   public Int2DoubleOpenCustomHashMap(Map<? extends Integer, ? extends Double> m, IntHash.Strategy strategy) {
/*  167 */     this(m, 0.75F, strategy);
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
/*      */   public Int2DoubleOpenCustomHashMap(Int2DoubleMap m, float f, IntHash.Strategy strategy) {
/*  181 */     this(m.size(), f, strategy);
/*  182 */     putAll(m);
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
/*      */   public Int2DoubleOpenCustomHashMap(Int2DoubleMap m, IntHash.Strategy strategy) {
/*  195 */     this(m, 0.75F, strategy);
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
/*      */   public Int2DoubleOpenCustomHashMap(int[] k, double[] v, float f, IntHash.Strategy strategy) {
/*  213 */     this(k.length, f, strategy);
/*  214 */     if (k.length != v.length) {
/*  215 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  217 */     for (int i = 0; i < k.length; i++) {
/*  218 */       put(k[i], v[i]);
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
/*      */   public Int2DoubleOpenCustomHashMap(int[] k, double[] v, IntHash.Strategy strategy) {
/*  235 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntHash.Strategy strategy() {
/*  243 */     return this.strategy;
/*      */   }
/*      */   private int realSize() {
/*  246 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  249 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  250 */     if (needed > this.n)
/*  251 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  254 */     int needed = (int)Math.min(1073741824L, 
/*  255 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  256 */     if (needed > this.n)
/*  257 */       rehash(needed); 
/*      */   }
/*      */   private double removeEntry(int pos) {
/*  260 */     double oldValue = this.value[pos];
/*  261 */     this.size--;
/*  262 */     shiftKeys(pos);
/*  263 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  264 */       rehash(this.n / 2); 
/*  265 */     return oldValue;
/*      */   }
/*      */   private double removeNullEntry() {
/*  268 */     this.containsNullKey = false;
/*  269 */     double oldValue = this.value[this.n];
/*  270 */     this.size--;
/*  271 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  272 */       rehash(this.n / 2); 
/*  273 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Integer, ? extends Double> m) {
/*  277 */     if (this.f <= 0.5D) {
/*  278 */       ensureCapacity(m.size());
/*      */     } else {
/*  280 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  282 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(int k) {
/*  286 */     if (this.strategy.equals(k, 0)) {
/*  287 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  289 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  292 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  293 */       return -(pos + 1); 
/*  294 */     if (this.strategy.equals(k, curr)) {
/*  295 */       return pos;
/*      */     }
/*      */     while (true) {
/*  298 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  299 */         return -(pos + 1); 
/*  300 */       if (this.strategy.equals(k, curr))
/*  301 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, int k, double v) {
/*  305 */     if (pos == this.n)
/*  306 */       this.containsNullKey = true; 
/*  307 */     this.key[pos] = k;
/*  308 */     this.value[pos] = v;
/*  309 */     if (this.size++ >= this.maxFill) {
/*  310 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public double put(int k, double v) {
/*  316 */     int pos = find(k);
/*  317 */     if (pos < 0) {
/*  318 */       insert(-pos - 1, k, v);
/*  319 */       return this.defRetValue;
/*      */     } 
/*  321 */     double oldValue = this.value[pos];
/*  322 */     this.value[pos] = v;
/*  323 */     return oldValue;
/*      */   }
/*      */   private double addToValue(int pos, double incr) {
/*  326 */     double oldValue = this.value[pos];
/*  327 */     this.value[pos] = oldValue + incr;
/*  328 */     return oldValue;
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
/*      */   public double addTo(int k, double incr) {
/*      */     int pos;
/*  348 */     if (this.strategy.equals(k, 0)) {
/*  349 */       if (this.containsNullKey)
/*  350 */         return addToValue(this.n, incr); 
/*  351 */       pos = this.n;
/*  352 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  355 */       int[] key = this.key;
/*      */       int curr;
/*  357 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != 0) {
/*  358 */         if (this.strategy.equals(curr, k))
/*  359 */           return addToValue(pos, incr); 
/*  360 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
/*  361 */           if (this.strategy.equals(curr, k))
/*  362 */             return addToValue(pos, incr); 
/*      */         } 
/*      */       } 
/*  365 */     }  this.key[pos] = k;
/*  366 */     this.value[pos] = this.defRetValue + incr;
/*  367 */     if (this.size++ >= this.maxFill) {
/*  368 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  371 */     return this.defRetValue;
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
/*  384 */     int[] key = this.key; while (true) {
/*      */       int curr, last;
/*  386 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  388 */         if ((curr = key[pos]) == 0) {
/*  389 */           key[last] = 0;
/*      */           return;
/*      */         } 
/*  392 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  393 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  395 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  397 */       key[last] = curr;
/*  398 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public double remove(int k) {
/*  404 */     if (this.strategy.equals(k, 0)) {
/*  405 */       if (this.containsNullKey)
/*  406 */         return removeNullEntry(); 
/*  407 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  410 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  413 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  414 */       return this.defRetValue; 
/*  415 */     if (this.strategy.equals(k, curr))
/*  416 */       return removeEntry(pos); 
/*      */     while (true) {
/*  418 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  419 */         return this.defRetValue; 
/*  420 */       if (this.strategy.equals(k, curr)) {
/*  421 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public double get(int k) {
/*  427 */     if (this.strategy.equals(k, 0)) {
/*  428 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  430 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  433 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  434 */       return this.defRetValue; 
/*  435 */     if (this.strategy.equals(k, curr)) {
/*  436 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  439 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  440 */         return this.defRetValue; 
/*  441 */       if (this.strategy.equals(k, curr)) {
/*  442 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(int k) {
/*  448 */     if (this.strategy.equals(k, 0)) {
/*  449 */       return this.containsNullKey;
/*      */     }
/*  451 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  454 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  455 */       return false; 
/*  456 */     if (this.strategy.equals(k, curr)) {
/*  457 */       return true;
/*      */     }
/*      */     while (true) {
/*  460 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  461 */         return false; 
/*  462 */       if (this.strategy.equals(k, curr))
/*  463 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(double v) {
/*  468 */     double[] value = this.value;
/*  469 */     int[] key = this.key;
/*  470 */     if (this.containsNullKey && Double.doubleToLongBits(value[this.n]) == Double.doubleToLongBits(v))
/*  471 */       return true; 
/*  472 */     for (int i = this.n; i-- != 0;) {
/*  473 */       if (key[i] != 0 && Double.doubleToLongBits(value[i]) == Double.doubleToLongBits(v))
/*  474 */         return true; 
/*  475 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double getOrDefault(int k, double defaultValue) {
/*  481 */     if (this.strategy.equals(k, 0)) {
/*  482 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  484 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  487 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  488 */       return defaultValue; 
/*  489 */     if (this.strategy.equals(k, curr)) {
/*  490 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  493 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  494 */         return defaultValue; 
/*  495 */       if (this.strategy.equals(k, curr)) {
/*  496 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public double putIfAbsent(int k, double v) {
/*  502 */     int pos = find(k);
/*  503 */     if (pos >= 0)
/*  504 */       return this.value[pos]; 
/*  505 */     insert(-pos - 1, k, v);
/*  506 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(int k, double v) {
/*  512 */     if (this.strategy.equals(k, 0)) {
/*  513 */       if (this.containsNullKey && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[this.n])) {
/*  514 */         removeNullEntry();
/*  515 */         return true;
/*      */       } 
/*  517 */       return false;
/*      */     } 
/*      */     
/*  520 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  523 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  524 */       return false; 
/*  525 */     if (this.strategy.equals(k, curr) && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
/*  526 */       removeEntry(pos);
/*  527 */       return true;
/*      */     } 
/*      */     while (true) {
/*  530 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  531 */         return false; 
/*  532 */       if (this.strategy.equals(k, curr) && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
/*  533 */         removeEntry(pos);
/*  534 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(int k, double oldValue, double v) {
/*  541 */     int pos = find(k);
/*  542 */     if (pos < 0 || Double.doubleToLongBits(oldValue) != Double.doubleToLongBits(this.value[pos]))
/*  543 */       return false; 
/*  544 */     this.value[pos] = v;
/*  545 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public double replace(int k, double v) {
/*  550 */     int pos = find(k);
/*  551 */     if (pos < 0)
/*  552 */       return this.defRetValue; 
/*  553 */     double oldValue = this.value[pos];
/*  554 */     this.value[pos] = v;
/*  555 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public double computeIfAbsent(int k, IntToDoubleFunction mappingFunction) {
/*  560 */     Objects.requireNonNull(mappingFunction);
/*  561 */     int pos = find(k);
/*  562 */     if (pos >= 0)
/*  563 */       return this.value[pos]; 
/*  564 */     double newValue = mappingFunction.applyAsDouble(k);
/*  565 */     insert(-pos - 1, k, newValue);
/*  566 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeIfAbsentNullable(int k, IntFunction<? extends Double> mappingFunction) {
/*  572 */     Objects.requireNonNull(mappingFunction);
/*  573 */     int pos = find(k);
/*  574 */     if (pos >= 0)
/*  575 */       return this.value[pos]; 
/*  576 */     Double newValue = mappingFunction.apply(k);
/*  577 */     if (newValue == null)
/*  578 */       return this.defRetValue; 
/*  579 */     double v = newValue.doubleValue();
/*  580 */     insert(-pos - 1, k, v);
/*  581 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeIfPresent(int k, BiFunction<? super Integer, ? super Double, ? extends Double> remappingFunction) {
/*  587 */     Objects.requireNonNull(remappingFunction);
/*  588 */     int pos = find(k);
/*  589 */     if (pos < 0)
/*  590 */       return this.defRetValue; 
/*  591 */     Double newValue = remappingFunction.apply(Integer.valueOf(k), Double.valueOf(this.value[pos]));
/*  592 */     if (newValue == null) {
/*  593 */       if (this.strategy.equals(k, 0)) {
/*  594 */         removeNullEntry();
/*      */       } else {
/*  596 */         removeEntry(pos);
/*  597 */       }  return this.defRetValue;
/*      */     } 
/*  599 */     this.value[pos] = newValue.doubleValue(); return newValue.doubleValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double compute(int k, BiFunction<? super Integer, ? super Double, ? extends Double> remappingFunction) {
/*  605 */     Objects.requireNonNull(remappingFunction);
/*  606 */     int pos = find(k);
/*  607 */     Double newValue = remappingFunction.apply(Integer.valueOf(k), 
/*  608 */         (pos >= 0) ? Double.valueOf(this.value[pos]) : null);
/*  609 */     if (newValue == null) {
/*  610 */       if (pos >= 0)
/*  611 */         if (this.strategy.equals(k, 0)) {
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
/*      */   public double merge(int k, double v, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/*  629 */     Objects.requireNonNull(remappingFunction);
/*  630 */     int pos = find(k);
/*  631 */     if (pos < 0) {
/*  632 */       insert(-pos - 1, k, v);
/*  633 */       return v;
/*      */     } 
/*  635 */     Double newValue = remappingFunction.apply(Double.valueOf(this.value[pos]), Double.valueOf(v));
/*  636 */     if (newValue == null) {
/*  637 */       if (this.strategy.equals(k, 0)) {
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
/*  658 */     Arrays.fill(this.key, 0);
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
/*      */     implements Int2DoubleMap.Entry, Map.Entry<Integer, Double>
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
/*      */     public int getIntKey() {
/*  684 */       return Int2DoubleOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public double getDoubleValue() {
/*  688 */       return Int2DoubleOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public double setValue(double v) {
/*  692 */       double oldValue = Int2DoubleOpenCustomHashMap.this.value[this.index];
/*  693 */       Int2DoubleOpenCustomHashMap.this.value[this.index] = v;
/*  694 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer getKey() {
/*  704 */       return Integer.valueOf(Int2DoubleOpenCustomHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double getValue() {
/*  714 */       return Double.valueOf(Int2DoubleOpenCustomHashMap.this.value[this.index]);
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
/*  731 */       Map.Entry<Integer, Double> e = (Map.Entry<Integer, Double>)o;
/*  732 */       return (Int2DoubleOpenCustomHashMap.this.strategy.equals(Int2DoubleOpenCustomHashMap.this.key[this.index], ((Integer)e.getKey()).intValue()) && 
/*  733 */         Double.doubleToLongBits(Int2DoubleOpenCustomHashMap.this.value[this.index]) == Double.doubleToLongBits(((Double)e.getValue()).doubleValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  737 */       return Int2DoubleOpenCustomHashMap.this.strategy.hashCode(Int2DoubleOpenCustomHashMap.this.key[this.index]) ^ HashCommon.double2int(Int2DoubleOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  741 */       return Int2DoubleOpenCustomHashMap.this.key[this.index] + "=>" + Int2DoubleOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  751 */     int pos = Int2DoubleOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  758 */     int last = -1;
/*      */     
/*  760 */     int c = Int2DoubleOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  764 */     boolean mustReturnNullKey = Int2DoubleOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     IntArrayList wrapped;
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
/*  779 */         return this.last = Int2DoubleOpenCustomHashMap.this.n;
/*      */       } 
/*  781 */       int[] key = Int2DoubleOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  783 */         if (--this.pos < 0) {
/*      */           
/*  785 */           this.last = Integer.MIN_VALUE;
/*  786 */           int k = this.wrapped.getInt(-this.pos - 1);
/*  787 */           int p = HashCommon.mix(Int2DoubleOpenCustomHashMap.this.strategy.hashCode(k)) & Int2DoubleOpenCustomHashMap.this.mask;
/*  788 */           while (!Int2DoubleOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  789 */             p = p + 1 & Int2DoubleOpenCustomHashMap.this.mask; 
/*  790 */           return p;
/*      */         } 
/*  792 */         if (key[this.pos] != 0) {
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
/*  807 */       int[] key = Int2DoubleOpenCustomHashMap.this.key; while (true) {
/*      */         int curr, last;
/*  809 */         pos = (last = pos) + 1 & Int2DoubleOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  811 */           if ((curr = key[pos]) == 0) {
/*  812 */             key[last] = 0;
/*      */             return;
/*      */           } 
/*  815 */           int slot = HashCommon.mix(Int2DoubleOpenCustomHashMap.this.strategy.hashCode(curr)) & Int2DoubleOpenCustomHashMap.this.mask;
/*  816 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  818 */           pos = pos + 1 & Int2DoubleOpenCustomHashMap.this.mask;
/*      */         } 
/*  820 */         if (pos < last) {
/*  821 */           if (this.wrapped == null)
/*  822 */             this.wrapped = new IntArrayList(2); 
/*  823 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  825 */         key[last] = curr;
/*  826 */         Int2DoubleOpenCustomHashMap.this.value[last] = Int2DoubleOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  830 */       if (this.last == -1)
/*  831 */         throw new IllegalStateException(); 
/*  832 */       if (this.last == Int2DoubleOpenCustomHashMap.this.n) {
/*  833 */         Int2DoubleOpenCustomHashMap.this.containsNullKey = false;
/*  834 */       } else if (this.pos >= 0) {
/*  835 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  838 */         Int2DoubleOpenCustomHashMap.this.remove(this.wrapped.getInt(-this.pos - 1));
/*  839 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  842 */       Int2DoubleOpenCustomHashMap.this.size--;
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
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Int2DoubleMap.Entry> { private Int2DoubleOpenCustomHashMap.MapEntry entry;
/*      */     
/*      */     public Int2DoubleOpenCustomHashMap.MapEntry next() {
/*  858 */       return this.entry = new Int2DoubleOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  862 */       super.remove();
/*  863 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Int2DoubleMap.Entry> { private FastEntryIterator() {
/*  867 */       this.entry = new Int2DoubleOpenCustomHashMap.MapEntry();
/*      */     } private final Int2DoubleOpenCustomHashMap.MapEntry entry;
/*      */     public Int2DoubleOpenCustomHashMap.MapEntry next() {
/*  870 */       this.entry.index = nextEntry();
/*  871 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Int2DoubleMap.Entry> implements Int2DoubleMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Int2DoubleMap.Entry> iterator() {
/*  877 */       return new Int2DoubleOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Int2DoubleMap.Entry> fastIterator() {
/*  881 */       return new Int2DoubleOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  886 */       if (!(o instanceof Map.Entry))
/*  887 */         return false; 
/*  888 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  889 */       if (e.getKey() == null || !(e.getKey() instanceof Integer))
/*  890 */         return false; 
/*  891 */       if (e.getValue() == null || !(e.getValue() instanceof Double))
/*  892 */         return false; 
/*  893 */       int k = ((Integer)e.getKey()).intValue();
/*  894 */       double v = ((Double)e.getValue()).doubleValue();
/*  895 */       if (Int2DoubleOpenCustomHashMap.this.strategy.equals(k, 0)) {
/*  896 */         return (Int2DoubleOpenCustomHashMap.this.containsNullKey && 
/*  897 */           Double.doubleToLongBits(Int2DoubleOpenCustomHashMap.this.value[Int2DoubleOpenCustomHashMap.this.n]) == Double.doubleToLongBits(v));
/*      */       }
/*  899 */       int[] key = Int2DoubleOpenCustomHashMap.this.key;
/*      */       
/*      */       int curr, pos;
/*  902 */       if ((curr = key[pos = HashCommon.mix(Int2DoubleOpenCustomHashMap.this.strategy.hashCode(k)) & Int2DoubleOpenCustomHashMap.this.mask]) == 0)
/*  903 */         return false; 
/*  904 */       if (Int2DoubleOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  905 */         return (Double.doubleToLongBits(Int2DoubleOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v));
/*      */       }
/*      */       while (true) {
/*  908 */         if ((curr = key[pos = pos + 1 & Int2DoubleOpenCustomHashMap.this.mask]) == 0)
/*  909 */           return false; 
/*  910 */         if (Int2DoubleOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  911 */           return (Double.doubleToLongBits(Int2DoubleOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v));
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  917 */       if (!(o instanceof Map.Entry))
/*  918 */         return false; 
/*  919 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  920 */       if (e.getKey() == null || !(e.getKey() instanceof Integer))
/*  921 */         return false; 
/*  922 */       if (e.getValue() == null || !(e.getValue() instanceof Double))
/*  923 */         return false; 
/*  924 */       int k = ((Integer)e.getKey()).intValue();
/*  925 */       double v = ((Double)e.getValue()).doubleValue();
/*  926 */       if (Int2DoubleOpenCustomHashMap.this.strategy.equals(k, 0)) {
/*  927 */         if (Int2DoubleOpenCustomHashMap.this.containsNullKey && Double.doubleToLongBits(Int2DoubleOpenCustomHashMap.this.value[Int2DoubleOpenCustomHashMap.this.n]) == Double.doubleToLongBits(v)) {
/*  928 */           Int2DoubleOpenCustomHashMap.this.removeNullEntry();
/*  929 */           return true;
/*      */         } 
/*  931 */         return false;
/*      */       } 
/*      */       
/*  934 */       int[] key = Int2DoubleOpenCustomHashMap.this.key;
/*      */       
/*      */       int curr, pos;
/*  937 */       if ((curr = key[pos = HashCommon.mix(Int2DoubleOpenCustomHashMap.this.strategy.hashCode(k)) & Int2DoubleOpenCustomHashMap.this.mask]) == 0)
/*  938 */         return false; 
/*  939 */       if (Int2DoubleOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  940 */         if (Double.doubleToLongBits(Int2DoubleOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
/*  941 */           Int2DoubleOpenCustomHashMap.this.removeEntry(pos);
/*  942 */           return true;
/*      */         } 
/*  944 */         return false;
/*      */       } 
/*      */       while (true) {
/*  947 */         if ((curr = key[pos = pos + 1 & Int2DoubleOpenCustomHashMap.this.mask]) == 0)
/*  948 */           return false; 
/*  949 */         if (Int2DoubleOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  950 */           Double.doubleToLongBits(Int2DoubleOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
/*  951 */           Int2DoubleOpenCustomHashMap.this.removeEntry(pos);
/*  952 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  959 */       return Int2DoubleOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  963 */       Int2DoubleOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Int2DoubleMap.Entry> consumer) {
/*  968 */       if (Int2DoubleOpenCustomHashMap.this.containsNullKey)
/*  969 */         consumer.accept(new AbstractInt2DoubleMap.BasicEntry(Int2DoubleOpenCustomHashMap.this.key[Int2DoubleOpenCustomHashMap.this.n], Int2DoubleOpenCustomHashMap.this.value[Int2DoubleOpenCustomHashMap.this.n])); 
/*  970 */       for (int pos = Int2DoubleOpenCustomHashMap.this.n; pos-- != 0;) {
/*  971 */         if (Int2DoubleOpenCustomHashMap.this.key[pos] != 0)
/*  972 */           consumer.accept(new AbstractInt2DoubleMap.BasicEntry(Int2DoubleOpenCustomHashMap.this.key[pos], Int2DoubleOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Int2DoubleMap.Entry> consumer) {
/*  977 */       AbstractInt2DoubleMap.BasicEntry entry = new AbstractInt2DoubleMap.BasicEntry();
/*  978 */       if (Int2DoubleOpenCustomHashMap.this.containsNullKey) {
/*  979 */         entry.key = Int2DoubleOpenCustomHashMap.this.key[Int2DoubleOpenCustomHashMap.this.n];
/*  980 */         entry.value = Int2DoubleOpenCustomHashMap.this.value[Int2DoubleOpenCustomHashMap.this.n];
/*  981 */         consumer.accept(entry);
/*      */       } 
/*  983 */       for (int pos = Int2DoubleOpenCustomHashMap.this.n; pos-- != 0;) {
/*  984 */         if (Int2DoubleOpenCustomHashMap.this.key[pos] != 0) {
/*  985 */           entry.key = Int2DoubleOpenCustomHashMap.this.key[pos];
/*  986 */           entry.value = Int2DoubleOpenCustomHashMap.this.value[pos];
/*  987 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Int2DoubleMap.FastEntrySet int2DoubleEntrySet() {
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
/*      */     implements IntIterator
/*      */   {
/*      */     public int nextInt() {
/* 1012 */       return Int2DoubleOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractIntSet { private KeySet() {}
/*      */     
/*      */     public IntIterator iterator() {
/* 1018 */       return new Int2DoubleOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer consumer) {
/* 1023 */       if (Int2DoubleOpenCustomHashMap.this.containsNullKey)
/* 1024 */         consumer.accept(Int2DoubleOpenCustomHashMap.this.key[Int2DoubleOpenCustomHashMap.this.n]); 
/* 1025 */       for (int pos = Int2DoubleOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1026 */         int k = Int2DoubleOpenCustomHashMap.this.key[pos];
/* 1027 */         if (k != 0)
/* 1028 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1033 */       return Int2DoubleOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(int k) {
/* 1037 */       return Int2DoubleOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(int k) {
/* 1041 */       int oldSize = Int2DoubleOpenCustomHashMap.this.size;
/* 1042 */       Int2DoubleOpenCustomHashMap.this.remove(k);
/* 1043 */       return (Int2DoubleOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1047 */       Int2DoubleOpenCustomHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public IntSet keySet() {
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
/* 1071 */       return Int2DoubleOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public DoubleCollection values() {
/* 1076 */     if (this.values == null)
/* 1077 */       this.values = (DoubleCollection)new AbstractDoubleCollection()
/*      */         {
/*      */           public DoubleIterator iterator() {
/* 1080 */             return new Int2DoubleOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1084 */             return Int2DoubleOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(double v) {
/* 1088 */             return Int2DoubleOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1092 */             Int2DoubleOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(DoubleConsumer consumer)
/*      */           {
/* 1097 */             if (Int2DoubleOpenCustomHashMap.this.containsNullKey)
/* 1098 */               consumer.accept(Int2DoubleOpenCustomHashMap.this.value[Int2DoubleOpenCustomHashMap.this.n]); 
/* 1099 */             for (int pos = Int2DoubleOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1100 */               if (Int2DoubleOpenCustomHashMap.this.key[pos] != 0)
/* 1101 */                 consumer.accept(Int2DoubleOpenCustomHashMap.this.value[pos]); 
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
/* 1169 */     int[] key = this.key;
/* 1170 */     double[] value = this.value;
/* 1171 */     int mask = newN - 1;
/* 1172 */     int[] newKey = new int[newN + 1];
/* 1173 */     double[] newValue = new double[newN + 1];
/* 1174 */     int i = this.n;
/* 1175 */     for (int j = realSize(); j-- != 0; ) {
/* 1176 */       while (key[--i] == 0); int pos;
/* 1177 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != 0)
/* 1178 */         while (newKey[pos = pos + 1 & mask] != 0); 
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
/*      */   public Int2DoubleOpenCustomHashMap clone() {
/*      */     Int2DoubleOpenCustomHashMap c;
/*      */     try {
/* 1204 */       c = (Int2DoubleOpenCustomHashMap)super.clone();
/* 1205 */     } catch (CloneNotSupportedException cantHappen) {
/* 1206 */       throw new InternalError();
/*      */     } 
/* 1208 */     c.keys = null;
/* 1209 */     c.values = null;
/* 1210 */     c.entries = null;
/* 1211 */     c.containsNullKey = this.containsNullKey;
/* 1212 */     c.key = (int[])this.key.clone();
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
/* 1230 */       while (this.key[i] == 0)
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
/* 1243 */     int[] key = this.key;
/* 1244 */     double[] value = this.value;
/* 1245 */     MapIterator i = new MapIterator();
/* 1246 */     s.defaultWriteObject();
/* 1247 */     for (int j = this.size; j-- != 0; ) {
/* 1248 */       int e = i.nextEntry();
/* 1249 */       s.writeInt(key[e]);
/* 1250 */       s.writeDouble(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1255 */     s.defaultReadObject();
/* 1256 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1257 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1258 */     this.mask = this.n - 1;
/* 1259 */     int[] key = this.key = new int[this.n + 1];
/* 1260 */     double[] value = this.value = new double[this.n + 1];
/*      */ 
/*      */     
/* 1263 */     for (int i = this.size; i-- != 0; ) {
/* 1264 */       int pos, k = s.readInt();
/* 1265 */       double v = s.readDouble();
/* 1266 */       if (this.strategy.equals(k, 0)) {
/* 1267 */         pos = this.n;
/* 1268 */         this.containsNullKey = true;
/*      */       } else {
/* 1270 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1271 */         while (key[pos] != 0)
/* 1272 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1274 */       key[pos] = k;
/* 1275 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\Int2DoubleOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */