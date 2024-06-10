/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleCollection;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleIterator;
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
/*      */ import java.util.function.ToDoubleFunction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Object2DoubleOpenCustomHashMap<K>
/*      */   extends AbstractObject2DoubleMap<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient double[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected Hash.Strategy<? super K> strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Object2DoubleMap.FastEntrySet<K> entries;
/*      */   protected transient ObjectSet<K> keys;
/*      */   protected transient DoubleCollection values;
/*      */   
/*      */   public Object2DoubleOpenCustomHashMap(int expected, float f, Hash.Strategy<? super K> strategy) {
/*  106 */     this.strategy = strategy;
/*  107 */     if (f <= 0.0F || f > 1.0F)
/*  108 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  109 */     if (expected < 0)
/*  110 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  111 */     this.f = f;
/*  112 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  113 */     this.mask = this.n - 1;
/*  114 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  115 */     this.key = (K[])new Object[this.n + 1];
/*  116 */     this.value = new double[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2DoubleOpenCustomHashMap(int expected, Hash.Strategy<? super K> strategy) {
/*  127 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2DoubleOpenCustomHashMap(Hash.Strategy<? super K> strategy) {
/*  138 */     this(16, 0.75F, strategy);
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
/*      */   public Object2DoubleOpenCustomHashMap(Map<? extends K, ? extends Double> m, float f, Hash.Strategy<? super K> strategy) {
/*  152 */     this(m.size(), f, strategy);
/*  153 */     putAll(m);
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
/*      */   public Object2DoubleOpenCustomHashMap(Map<? extends K, ? extends Double> m, Hash.Strategy<? super K> strategy) {
/*  166 */     this(m, 0.75F, strategy);
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
/*      */   public Object2DoubleOpenCustomHashMap(Object2DoubleMap<K> m, float f, Hash.Strategy<? super K> strategy) {
/*  180 */     this(m.size(), f, strategy);
/*  181 */     putAll(m);
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
/*      */   public Object2DoubleOpenCustomHashMap(Object2DoubleMap<K> m, Hash.Strategy<? super K> strategy) {
/*  193 */     this(m, 0.75F, strategy);
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
/*      */   public Object2DoubleOpenCustomHashMap(K[] k, double[] v, float f, Hash.Strategy<? super K> strategy) {
/*  211 */     this(k.length, f, strategy);
/*  212 */     if (k.length != v.length) {
/*  213 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  215 */     for (int i = 0; i < k.length; i++) {
/*  216 */       put(k[i], v[i]);
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
/*      */   public Object2DoubleOpenCustomHashMap(K[] k, double[] v, Hash.Strategy<? super K> strategy) {
/*  232 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Hash.Strategy<? super K> strategy() {
/*  240 */     return this.strategy;
/*      */   }
/*      */   private int realSize() {
/*  243 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  246 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  247 */     if (needed > this.n)
/*  248 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  251 */     int needed = (int)Math.min(1073741824L, 
/*  252 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  253 */     if (needed > this.n)
/*  254 */       rehash(needed); 
/*      */   }
/*      */   private double removeEntry(int pos) {
/*  257 */     double oldValue = this.value[pos];
/*  258 */     this.size--;
/*  259 */     shiftKeys(pos);
/*  260 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  261 */       rehash(this.n / 2); 
/*  262 */     return oldValue;
/*      */   }
/*      */   private double removeNullEntry() {
/*  265 */     this.containsNullKey = false;
/*  266 */     this.key[this.n] = null;
/*  267 */     double oldValue = this.value[this.n];
/*  268 */     this.size--;
/*  269 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  270 */       rehash(this.n / 2); 
/*  271 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends Double> m) {
/*  275 */     if (this.f <= 0.5D) {
/*  276 */       ensureCapacity(m.size());
/*      */     } else {
/*  278 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  280 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(K k) {
/*  284 */     if (this.strategy.equals(k, null)) {
/*  285 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  287 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  290 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  291 */       return -(pos + 1); 
/*  292 */     if (this.strategy.equals(k, curr)) {
/*  293 */       return pos;
/*      */     }
/*      */     while (true) {
/*  296 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  297 */         return -(pos + 1); 
/*  298 */       if (this.strategy.equals(k, curr))
/*  299 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, K k, double v) {
/*  303 */     if (pos == this.n)
/*  304 */       this.containsNullKey = true; 
/*  305 */     this.key[pos] = k;
/*  306 */     this.value[pos] = v;
/*  307 */     if (this.size++ >= this.maxFill) {
/*  308 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public double put(K k, double v) {
/*  314 */     int pos = find(k);
/*  315 */     if (pos < 0) {
/*  316 */       insert(-pos - 1, k, v);
/*  317 */       return this.defRetValue;
/*      */     } 
/*  319 */     double oldValue = this.value[pos];
/*  320 */     this.value[pos] = v;
/*  321 */     return oldValue;
/*      */   }
/*      */   private double addToValue(int pos, double incr) {
/*  324 */     double oldValue = this.value[pos];
/*  325 */     this.value[pos] = oldValue + incr;
/*  326 */     return oldValue;
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
/*      */   public double addTo(K k, double incr) {
/*      */     int pos;
/*  346 */     if (this.strategy.equals(k, null)) {
/*  347 */       if (this.containsNullKey)
/*  348 */         return addToValue(this.n, incr); 
/*  349 */       pos = this.n;
/*  350 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  353 */       K[] key = this.key;
/*      */       K curr;
/*  355 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != null) {
/*  356 */         if (this.strategy.equals(curr, k))
/*  357 */           return addToValue(pos, incr); 
/*  358 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  359 */           if (this.strategy.equals(curr, k))
/*  360 */             return addToValue(pos, incr); 
/*      */         } 
/*      */       } 
/*  363 */     }  this.key[pos] = k;
/*  364 */     this.value[pos] = this.defRetValue + incr;
/*  365 */     if (this.size++ >= this.maxFill) {
/*  366 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  369 */     return this.defRetValue;
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
/*  382 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  384 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  386 */         if ((curr = key[pos]) == null) {
/*  387 */           key[last] = null;
/*      */           return;
/*      */         } 
/*  390 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  391 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  393 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  395 */       key[last] = curr;
/*  396 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public double removeDouble(Object k) {
/*  402 */     if (this.strategy.equals(k, null)) {
/*  403 */       if (this.containsNullKey)
/*  404 */         return removeNullEntry(); 
/*  405 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  408 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  411 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  412 */       return this.defRetValue; 
/*  413 */     if (this.strategy.equals(k, curr))
/*  414 */       return removeEntry(pos); 
/*      */     while (true) {
/*  416 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  417 */         return this.defRetValue; 
/*  418 */       if (this.strategy.equals(k, curr)) {
/*  419 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public double getDouble(Object k) {
/*  425 */     if (this.strategy.equals(k, null)) {
/*  426 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  428 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  431 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  432 */       return this.defRetValue; 
/*  433 */     if (this.strategy.equals(k, curr)) {
/*  434 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  437 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  438 */         return this.defRetValue; 
/*  439 */       if (this.strategy.equals(k, curr)) {
/*  440 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  446 */     if (this.strategy.equals(k, null)) {
/*  447 */       return this.containsNullKey;
/*      */     }
/*  449 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  452 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  453 */       return false; 
/*  454 */     if (this.strategy.equals(k, curr)) {
/*  455 */       return true;
/*      */     }
/*      */     while (true) {
/*  458 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  459 */         return false; 
/*  460 */       if (this.strategy.equals(k, curr))
/*  461 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(double v) {
/*  466 */     double[] value = this.value;
/*  467 */     K[] key = this.key;
/*  468 */     if (this.containsNullKey && Double.doubleToLongBits(value[this.n]) == Double.doubleToLongBits(v))
/*  469 */       return true; 
/*  470 */     for (int i = this.n; i-- != 0;) {
/*  471 */       if (key[i] != null && Double.doubleToLongBits(value[i]) == Double.doubleToLongBits(v))
/*  472 */         return true; 
/*  473 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double getOrDefault(Object k, double defaultValue) {
/*  479 */     if (this.strategy.equals(k, null)) {
/*  480 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  482 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  485 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  486 */       return defaultValue; 
/*  487 */     if (this.strategy.equals(k, curr)) {
/*  488 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  491 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  492 */         return defaultValue; 
/*  493 */       if (this.strategy.equals(k, curr)) {
/*  494 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public double putIfAbsent(K k, double v) {
/*  500 */     int pos = find(k);
/*  501 */     if (pos >= 0)
/*  502 */       return this.value[pos]; 
/*  503 */     insert(-pos - 1, k, v);
/*  504 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, double v) {
/*  510 */     if (this.strategy.equals(k, null)) {
/*  511 */       if (this.containsNullKey && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[this.n])) {
/*  512 */         removeNullEntry();
/*  513 */         return true;
/*      */       } 
/*  515 */       return false;
/*      */     } 
/*      */     
/*  518 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  521 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  522 */       return false; 
/*  523 */     if (this.strategy.equals(k, curr) && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
/*  524 */       removeEntry(pos);
/*  525 */       return true;
/*      */     } 
/*      */     while (true) {
/*  528 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  529 */         return false; 
/*  530 */       if (this.strategy.equals(k, curr) && 
/*  531 */         Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
/*  532 */         removeEntry(pos);
/*  533 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(K k, double oldValue, double v) {
/*  540 */     int pos = find(k);
/*  541 */     if (pos < 0 || Double.doubleToLongBits(oldValue) != Double.doubleToLongBits(this.value[pos]))
/*  542 */       return false; 
/*  543 */     this.value[pos] = v;
/*  544 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public double replace(K k, double v) {
/*  549 */     int pos = find(k);
/*  550 */     if (pos < 0)
/*  551 */       return this.defRetValue; 
/*  552 */     double oldValue = this.value[pos];
/*  553 */     this.value[pos] = v;
/*  554 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeDoubleIfAbsent(K k, ToDoubleFunction<? super K> mappingFunction) {
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
/*      */   public double computeDoubleIfPresent(K k, BiFunction<? super K, ? super Double, ? extends Double> remappingFunction) {
/*  572 */     Objects.requireNonNull(remappingFunction);
/*  573 */     int pos = find(k);
/*  574 */     if (pos < 0)
/*  575 */       return this.defRetValue; 
/*  576 */     Double newValue = remappingFunction.apply(k, Double.valueOf(this.value[pos]));
/*  577 */     if (newValue == null) {
/*  578 */       if (this.strategy.equals(k, null)) {
/*  579 */         removeNullEntry();
/*      */       } else {
/*  581 */         removeEntry(pos);
/*  582 */       }  return this.defRetValue;
/*      */     } 
/*  584 */     this.value[pos] = newValue.doubleValue(); return newValue.doubleValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeDouble(K k, BiFunction<? super K, ? super Double, ? extends Double> remappingFunction) {
/*  590 */     Objects.requireNonNull(remappingFunction);
/*  591 */     int pos = find(k);
/*  592 */     Double newValue = remappingFunction.apply(k, (pos >= 0) ? Double.valueOf(this.value[pos]) : null);
/*  593 */     if (newValue == null) {
/*  594 */       if (pos >= 0)
/*  595 */         if (this.strategy.equals(k, null)) {
/*  596 */           removeNullEntry();
/*      */         } else {
/*  598 */           removeEntry(pos);
/*      */         }  
/*  600 */       return this.defRetValue;
/*      */     } 
/*  602 */     double newVal = newValue.doubleValue();
/*  603 */     if (pos < 0) {
/*  604 */       insert(-pos - 1, k, newVal);
/*  605 */       return newVal;
/*      */     } 
/*  607 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double mergeDouble(K k, double v, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/*  613 */     Objects.requireNonNull(remappingFunction);
/*  614 */     int pos = find(k);
/*  615 */     if (pos < 0) {
/*  616 */       insert(-pos - 1, k, v);
/*  617 */       return v;
/*      */     } 
/*  619 */     Double newValue = remappingFunction.apply(Double.valueOf(this.value[pos]), Double.valueOf(v));
/*  620 */     if (newValue == null) {
/*  621 */       if (this.strategy.equals(k, null)) {
/*  622 */         removeNullEntry();
/*      */       } else {
/*  624 */         removeEntry(pos);
/*  625 */       }  return this.defRetValue;
/*      */     } 
/*  627 */     this.value[pos] = newValue.doubleValue(); return newValue.doubleValue();
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
/*  638 */     if (this.size == 0)
/*      */       return; 
/*  640 */     this.size = 0;
/*  641 */     this.containsNullKey = false;
/*  642 */     Arrays.fill((Object[])this.key, (Object)null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  646 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  650 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Object2DoubleMap.Entry<K>, Map.Entry<K, Double>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  662 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public K getKey() {
/*  668 */       return Object2DoubleOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public double getDoubleValue() {
/*  672 */       return Object2DoubleOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public double setValue(double v) {
/*  676 */       double oldValue = Object2DoubleOpenCustomHashMap.this.value[this.index];
/*  677 */       Object2DoubleOpenCustomHashMap.this.value[this.index] = v;
/*  678 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double getValue() {
/*  688 */       return Double.valueOf(Object2DoubleOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double setValue(Double v) {
/*  698 */       return Double.valueOf(setValue(v.doubleValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  703 */       if (!(o instanceof Map.Entry))
/*  704 */         return false; 
/*  705 */       Map.Entry<K, Double> e = (Map.Entry<K, Double>)o;
/*  706 */       return (Object2DoubleOpenCustomHashMap.this.strategy.equals(Object2DoubleOpenCustomHashMap.this.key[this.index], e.getKey()) && 
/*  707 */         Double.doubleToLongBits(Object2DoubleOpenCustomHashMap.this.value[this.index]) == Double.doubleToLongBits(((Double)e.getValue()).doubleValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  711 */       return Object2DoubleOpenCustomHashMap.this.strategy.hashCode(Object2DoubleOpenCustomHashMap.this.key[this.index]) ^ HashCommon.double2int(Object2DoubleOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  715 */       return (new StringBuilder()).append(Object2DoubleOpenCustomHashMap.this.key[this.index]).append("=>").append(Object2DoubleOpenCustomHashMap.this.value[this.index]).toString();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  725 */     int pos = Object2DoubleOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  732 */     int last = -1;
/*      */     
/*  734 */     int c = Object2DoubleOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  738 */     boolean mustReturnNullKey = Object2DoubleOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     ObjectArrayList<K> wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  745 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  748 */       if (!hasNext())
/*  749 */         throw new NoSuchElementException(); 
/*  750 */       this.c--;
/*  751 */       if (this.mustReturnNullKey) {
/*  752 */         this.mustReturnNullKey = false;
/*  753 */         return this.last = Object2DoubleOpenCustomHashMap.this.n;
/*      */       } 
/*  755 */       K[] key = Object2DoubleOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  757 */         if (--this.pos < 0) {
/*      */           
/*  759 */           this.last = Integer.MIN_VALUE;
/*  760 */           K k = this.wrapped.get(-this.pos - 1);
/*  761 */           int p = HashCommon.mix(Object2DoubleOpenCustomHashMap.this.strategy.hashCode(k)) & Object2DoubleOpenCustomHashMap.this.mask;
/*  762 */           while (!Object2DoubleOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  763 */             p = p + 1 & Object2DoubleOpenCustomHashMap.this.mask; 
/*  764 */           return p;
/*      */         } 
/*  766 */         if (key[this.pos] != null) {
/*  767 */           return this.last = this.pos;
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
/*  781 */       K[] key = Object2DoubleOpenCustomHashMap.this.key; while (true) {
/*      */         K curr; int last;
/*  783 */         pos = (last = pos) + 1 & Object2DoubleOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  785 */           if ((curr = key[pos]) == null) {
/*  786 */             key[last] = null;
/*      */             return;
/*      */           } 
/*  789 */           int slot = HashCommon.mix(Object2DoubleOpenCustomHashMap.this.strategy.hashCode(curr)) & Object2DoubleOpenCustomHashMap.this.mask;
/*  790 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  792 */           pos = pos + 1 & Object2DoubleOpenCustomHashMap.this.mask;
/*      */         } 
/*  794 */         if (pos < last) {
/*  795 */           if (this.wrapped == null)
/*  796 */             this.wrapped = new ObjectArrayList<>(2); 
/*  797 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  799 */         key[last] = curr;
/*  800 */         Object2DoubleOpenCustomHashMap.this.value[last] = Object2DoubleOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  804 */       if (this.last == -1)
/*  805 */         throw new IllegalStateException(); 
/*  806 */       if (this.last == Object2DoubleOpenCustomHashMap.this.n) {
/*  807 */         Object2DoubleOpenCustomHashMap.this.containsNullKey = false;
/*  808 */         Object2DoubleOpenCustomHashMap.this.key[Object2DoubleOpenCustomHashMap.this.n] = null;
/*  809 */       } else if (this.pos >= 0) {
/*  810 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  813 */         Object2DoubleOpenCustomHashMap.this.removeDouble(this.wrapped.set(-this.pos - 1, null));
/*  814 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  817 */       Object2DoubleOpenCustomHashMap.this.size--;
/*  818 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  823 */       int i = n;
/*  824 */       while (i-- != 0 && hasNext())
/*  825 */         nextEntry(); 
/*  826 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Object2DoubleMap.Entry<K>> { private Object2DoubleOpenCustomHashMap<K>.MapEntry entry;
/*      */     
/*      */     public Object2DoubleOpenCustomHashMap<K>.MapEntry next() {
/*  833 */       return this.entry = new Object2DoubleOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  837 */       super.remove();
/*  838 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Object2DoubleMap.Entry<K>> { private FastEntryIterator() {
/*  842 */       this.entry = new Object2DoubleOpenCustomHashMap.MapEntry();
/*      */     } private final Object2DoubleOpenCustomHashMap<K>.MapEntry entry;
/*      */     public Object2DoubleOpenCustomHashMap<K>.MapEntry next() {
/*  845 */       this.entry.index = nextEntry();
/*  846 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Object2DoubleMap.Entry<K>> implements Object2DoubleMap.FastEntrySet<K> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Object2DoubleMap.Entry<K>> iterator() {
/*  852 */       return new Object2DoubleOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Object2DoubleMap.Entry<K>> fastIterator() {
/*  856 */       return new Object2DoubleOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  861 */       if (!(o instanceof Map.Entry))
/*  862 */         return false; 
/*  863 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  864 */       if (e.getValue() == null || !(e.getValue() instanceof Double))
/*  865 */         return false; 
/*  866 */       K k = (K)e.getKey();
/*  867 */       double v = ((Double)e.getValue()).doubleValue();
/*  868 */       if (Object2DoubleOpenCustomHashMap.this.strategy.equals(k, null)) {
/*  869 */         return (Object2DoubleOpenCustomHashMap.this.containsNullKey && 
/*  870 */           Double.doubleToLongBits(Object2DoubleOpenCustomHashMap.this.value[Object2DoubleOpenCustomHashMap.this.n]) == Double.doubleToLongBits(v));
/*      */       }
/*  872 */       K[] key = Object2DoubleOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  875 */       if ((curr = key[pos = HashCommon.mix(Object2DoubleOpenCustomHashMap.this.strategy.hashCode(k)) & Object2DoubleOpenCustomHashMap.this.mask]) == null)
/*  876 */         return false; 
/*  877 */       if (Object2DoubleOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  878 */         return (Double.doubleToLongBits(Object2DoubleOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v));
/*      */       }
/*      */       while (true) {
/*  881 */         if ((curr = key[pos = pos + 1 & Object2DoubleOpenCustomHashMap.this.mask]) == null)
/*  882 */           return false; 
/*  883 */         if (Object2DoubleOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  884 */           return (Double.doubleToLongBits(Object2DoubleOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v));
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  890 */       if (!(o instanceof Map.Entry))
/*  891 */         return false; 
/*  892 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  893 */       if (e.getValue() == null || !(e.getValue() instanceof Double))
/*  894 */         return false; 
/*  895 */       K k = (K)e.getKey();
/*  896 */       double v = ((Double)e.getValue()).doubleValue();
/*  897 */       if (Object2DoubleOpenCustomHashMap.this.strategy.equals(k, null)) {
/*  898 */         if (Object2DoubleOpenCustomHashMap.this.containsNullKey && Double.doubleToLongBits(Object2DoubleOpenCustomHashMap.this.value[Object2DoubleOpenCustomHashMap.this.n]) == Double.doubleToLongBits(v)) {
/*  899 */           Object2DoubleOpenCustomHashMap.this.removeNullEntry();
/*  900 */           return true;
/*      */         } 
/*  902 */         return false;
/*      */       } 
/*      */       
/*  905 */       K[] key = Object2DoubleOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  908 */       if ((curr = key[pos = HashCommon.mix(Object2DoubleOpenCustomHashMap.this.strategy.hashCode(k)) & Object2DoubleOpenCustomHashMap.this.mask]) == null)
/*  909 */         return false; 
/*  910 */       if (Object2DoubleOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  911 */         if (Double.doubleToLongBits(Object2DoubleOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
/*  912 */           Object2DoubleOpenCustomHashMap.this.removeEntry(pos);
/*  913 */           return true;
/*      */         } 
/*  915 */         return false;
/*      */       } 
/*      */       while (true) {
/*  918 */         if ((curr = key[pos = pos + 1 & Object2DoubleOpenCustomHashMap.this.mask]) == null)
/*  919 */           return false; 
/*  920 */         if (Object2DoubleOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  921 */           Double.doubleToLongBits(Object2DoubleOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
/*  922 */           Object2DoubleOpenCustomHashMap.this.removeEntry(pos);
/*  923 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  930 */       return Object2DoubleOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  934 */       Object2DoubleOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Object2DoubleMap.Entry<K>> consumer) {
/*  939 */       if (Object2DoubleOpenCustomHashMap.this.containsNullKey)
/*  940 */         consumer.accept(new AbstractObject2DoubleMap.BasicEntry<>(Object2DoubleOpenCustomHashMap.this.key[Object2DoubleOpenCustomHashMap.this.n], Object2DoubleOpenCustomHashMap.this.value[Object2DoubleOpenCustomHashMap.this.n])); 
/*  941 */       for (int pos = Object2DoubleOpenCustomHashMap.this.n; pos-- != 0;) {
/*  942 */         if (Object2DoubleOpenCustomHashMap.this.key[pos] != null)
/*  943 */           consumer.accept(new AbstractObject2DoubleMap.BasicEntry<>(Object2DoubleOpenCustomHashMap.this.key[pos], Object2DoubleOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Object2DoubleMap.Entry<K>> consumer) {
/*  948 */       AbstractObject2DoubleMap.BasicEntry<K> entry = new AbstractObject2DoubleMap.BasicEntry<>();
/*  949 */       if (Object2DoubleOpenCustomHashMap.this.containsNullKey) {
/*  950 */         entry.key = Object2DoubleOpenCustomHashMap.this.key[Object2DoubleOpenCustomHashMap.this.n];
/*  951 */         entry.value = Object2DoubleOpenCustomHashMap.this.value[Object2DoubleOpenCustomHashMap.this.n];
/*  952 */         consumer.accept(entry);
/*      */       } 
/*  954 */       for (int pos = Object2DoubleOpenCustomHashMap.this.n; pos-- != 0;) {
/*  955 */         if (Object2DoubleOpenCustomHashMap.this.key[pos] != null) {
/*  956 */           entry.key = Object2DoubleOpenCustomHashMap.this.key[pos];
/*  957 */           entry.value = Object2DoubleOpenCustomHashMap.this.value[pos];
/*  958 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Object2DoubleMap.FastEntrySet<K> object2DoubleEntrySet() {
/*  964 */     if (this.entries == null)
/*  965 */       this.entries = new MapEntrySet(); 
/*  966 */     return this.entries;
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
/*      */     implements ObjectIterator<K>
/*      */   {
/*      */     public K next() {
/*  983 */       return Object2DoubleOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractObjectSet<K> { private KeySet() {}
/*      */     
/*      */     public ObjectIterator<K> iterator() {
/*  989 */       return new Object2DoubleOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/*  994 */       if (Object2DoubleOpenCustomHashMap.this.containsNullKey)
/*  995 */         consumer.accept(Object2DoubleOpenCustomHashMap.this.key[Object2DoubleOpenCustomHashMap.this.n]); 
/*  996 */       for (int pos = Object2DoubleOpenCustomHashMap.this.n; pos-- != 0; ) {
/*  997 */         K k = Object2DoubleOpenCustomHashMap.this.key[pos];
/*  998 */         if (k != null)
/*  999 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1004 */       return Object2DoubleOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(Object k) {
/* 1008 */       return Object2DoubleOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(Object k) {
/* 1012 */       int oldSize = Object2DoubleOpenCustomHashMap.this.size;
/* 1013 */       Object2DoubleOpenCustomHashMap.this.removeDouble(k);
/* 1014 */       return (Object2DoubleOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1018 */       Object2DoubleOpenCustomHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ObjectSet<K> keySet() {
/* 1023 */     if (this.keys == null)
/* 1024 */       this.keys = new KeySet(); 
/* 1025 */     return this.keys;
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
/* 1042 */       return Object2DoubleOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public DoubleCollection values() {
/* 1047 */     if (this.values == null)
/* 1048 */       this.values = (DoubleCollection)new AbstractDoubleCollection()
/*      */         {
/*      */           public DoubleIterator iterator() {
/* 1051 */             return new Object2DoubleOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1055 */             return Object2DoubleOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(double v) {
/* 1059 */             return Object2DoubleOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1063 */             Object2DoubleOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(DoubleConsumer consumer)
/*      */           {
/* 1068 */             if (Object2DoubleOpenCustomHashMap.this.containsNullKey)
/* 1069 */               consumer.accept(Object2DoubleOpenCustomHashMap.this.value[Object2DoubleOpenCustomHashMap.this.n]); 
/* 1070 */             for (int pos = Object2DoubleOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1071 */               if (Object2DoubleOpenCustomHashMap.this.key[pos] != null)
/* 1072 */                 consumer.accept(Object2DoubleOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1075 */     return this.values;
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
/* 1092 */     return trim(this.size);
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
/* 1116 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1117 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1118 */       return true; 
/*      */     try {
/* 1120 */       rehash(l);
/* 1121 */     } catch (OutOfMemoryError cantDoIt) {
/* 1122 */       return false;
/*      */     } 
/* 1124 */     return true;
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
/* 1140 */     K[] key = this.key;
/* 1141 */     double[] value = this.value;
/* 1142 */     int mask = newN - 1;
/* 1143 */     K[] newKey = (K[])new Object[newN + 1];
/* 1144 */     double[] newValue = new double[newN + 1];
/* 1145 */     int i = this.n;
/* 1146 */     for (int j = realSize(); j-- != 0; ) {
/* 1147 */       while (key[--i] == null); int pos;
/* 1148 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != null)
/* 1149 */         while (newKey[pos = pos + 1 & mask] != null); 
/* 1150 */       newKey[pos] = key[i];
/* 1151 */       newValue[pos] = value[i];
/*      */     } 
/* 1153 */     newValue[newN] = value[this.n];
/* 1154 */     this.n = newN;
/* 1155 */     this.mask = mask;
/* 1156 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1157 */     this.key = newKey;
/* 1158 */     this.value = newValue;
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
/*      */   public Object2DoubleOpenCustomHashMap<K> clone() {
/*      */     Object2DoubleOpenCustomHashMap<K> c;
/*      */     try {
/* 1175 */       c = (Object2DoubleOpenCustomHashMap<K>)super.clone();
/* 1176 */     } catch (CloneNotSupportedException cantHappen) {
/* 1177 */       throw new InternalError();
/*      */     } 
/* 1179 */     c.keys = null;
/* 1180 */     c.values = null;
/* 1181 */     c.entries = null;
/* 1182 */     c.containsNullKey = this.containsNullKey;
/* 1183 */     c.key = (K[])this.key.clone();
/* 1184 */     c.value = (double[])this.value.clone();
/* 1185 */     c.strategy = this.strategy;
/* 1186 */     return c;
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
/* 1199 */     int h = 0;
/* 1200 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1201 */       while (this.key[i] == null)
/* 1202 */         i++; 
/* 1203 */       if (this != this.key[i])
/* 1204 */         t = this.strategy.hashCode(this.key[i]); 
/* 1205 */       t ^= HashCommon.double2int(this.value[i]);
/* 1206 */       h += t;
/* 1207 */       i++;
/*      */     } 
/*      */     
/* 1210 */     if (this.containsNullKey)
/* 1211 */       h += HashCommon.double2int(this.value[this.n]); 
/* 1212 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1215 */     K[] key = this.key;
/* 1216 */     double[] value = this.value;
/* 1217 */     MapIterator i = new MapIterator();
/* 1218 */     s.defaultWriteObject();
/* 1219 */     for (int j = this.size; j-- != 0; ) {
/* 1220 */       int e = i.nextEntry();
/* 1221 */       s.writeObject(key[e]);
/* 1222 */       s.writeDouble(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1227 */     s.defaultReadObject();
/* 1228 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1229 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1230 */     this.mask = this.n - 1;
/* 1231 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1232 */     double[] value = this.value = new double[this.n + 1];
/*      */ 
/*      */     
/* 1235 */     for (int i = this.size; i-- != 0; ) {
/* 1236 */       int pos; K k = (K)s.readObject();
/* 1237 */       double v = s.readDouble();
/* 1238 */       if (this.strategy.equals(k, null)) {
/* 1239 */         pos = this.n;
/* 1240 */         this.containsNullKey = true;
/*      */       } else {
/* 1242 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1243 */         while (key[pos] != null)
/* 1244 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1246 */       key[pos] = k;
/* 1247 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2DoubleOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */