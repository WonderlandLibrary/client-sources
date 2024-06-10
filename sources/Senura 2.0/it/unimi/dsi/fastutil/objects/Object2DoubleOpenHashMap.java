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
/*      */ public class Object2DoubleOpenHashMap<K>
/*      */   extends AbstractObject2DoubleMap<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient double[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Object2DoubleMap.FastEntrySet<K> entries;
/*      */   protected transient ObjectSet<K> keys;
/*      */   protected transient DoubleCollection values;
/*      */   
/*      */   public Object2DoubleOpenHashMap(int expected, float f) {
/*  100 */     if (f <= 0.0F || f > 1.0F)
/*  101 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  102 */     if (expected < 0)
/*  103 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  104 */     this.f = f;
/*  105 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  106 */     this.mask = this.n - 1;
/*  107 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  108 */     this.key = (K[])new Object[this.n + 1];
/*  109 */     this.value = new double[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2DoubleOpenHashMap(int expected) {
/*  118 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2DoubleOpenHashMap() {
/*  126 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2DoubleOpenHashMap(Map<? extends K, ? extends Double> m, float f) {
/*  137 */     this(m.size(), f);
/*  138 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2DoubleOpenHashMap(Map<? extends K, ? extends Double> m) {
/*  148 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2DoubleOpenHashMap(Object2DoubleMap<K> m, float f) {
/*  159 */     this(m.size(), f);
/*  160 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2DoubleOpenHashMap(Object2DoubleMap<K> m) {
/*  170 */     this(m, 0.75F);
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
/*      */   public Object2DoubleOpenHashMap(K[] k, double[] v, float f) {
/*  185 */     this(k.length, f);
/*  186 */     if (k.length != v.length) {
/*  187 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  189 */     for (int i = 0; i < k.length; i++) {
/*  190 */       put(k[i], v[i]);
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
/*      */   public Object2DoubleOpenHashMap(K[] k, double[] v) {
/*  204 */     this(k, v, 0.75F);
/*      */   }
/*      */   private int realSize() {
/*  207 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  210 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  211 */     if (needed > this.n)
/*  212 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  215 */     int needed = (int)Math.min(1073741824L, 
/*  216 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  217 */     if (needed > this.n)
/*  218 */       rehash(needed); 
/*      */   }
/*      */   private double removeEntry(int pos) {
/*  221 */     double oldValue = this.value[pos];
/*  222 */     this.size--;
/*  223 */     shiftKeys(pos);
/*  224 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  225 */       rehash(this.n / 2); 
/*  226 */     return oldValue;
/*      */   }
/*      */   private double removeNullEntry() {
/*  229 */     this.containsNullKey = false;
/*  230 */     this.key[this.n] = null;
/*  231 */     double oldValue = this.value[this.n];
/*  232 */     this.size--;
/*  233 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  234 */       rehash(this.n / 2); 
/*  235 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends Double> m) {
/*  239 */     if (this.f <= 0.5D) {
/*  240 */       ensureCapacity(m.size());
/*      */     } else {
/*  242 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  244 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(K k) {
/*  248 */     if (k == null) {
/*  249 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  251 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  254 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  255 */       return -(pos + 1); 
/*  256 */     if (k.equals(curr)) {
/*  257 */       return pos;
/*      */     }
/*      */     while (true) {
/*  260 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  261 */         return -(pos + 1); 
/*  262 */       if (k.equals(curr))
/*  263 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, K k, double v) {
/*  267 */     if (pos == this.n)
/*  268 */       this.containsNullKey = true; 
/*  269 */     this.key[pos] = k;
/*  270 */     this.value[pos] = v;
/*  271 */     if (this.size++ >= this.maxFill) {
/*  272 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public double put(K k, double v) {
/*  278 */     int pos = find(k);
/*  279 */     if (pos < 0) {
/*  280 */       insert(-pos - 1, k, v);
/*  281 */       return this.defRetValue;
/*      */     } 
/*  283 */     double oldValue = this.value[pos];
/*  284 */     this.value[pos] = v;
/*  285 */     return oldValue;
/*      */   }
/*      */   private double addToValue(int pos, double incr) {
/*  288 */     double oldValue = this.value[pos];
/*  289 */     this.value[pos] = oldValue + incr;
/*  290 */     return oldValue;
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
/*  310 */     if (k == null) {
/*  311 */       if (this.containsNullKey)
/*  312 */         return addToValue(this.n, incr); 
/*  313 */       pos = this.n;
/*  314 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  317 */       K[] key = this.key;
/*      */       K curr;
/*  319 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) != null) {
/*  320 */         if (curr.equals(k))
/*  321 */           return addToValue(pos, incr); 
/*  322 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  323 */           if (curr.equals(k))
/*  324 */             return addToValue(pos, incr); 
/*      */         } 
/*      */       } 
/*  327 */     }  this.key[pos] = k;
/*  328 */     this.value[pos] = this.defRetValue + incr;
/*  329 */     if (this.size++ >= this.maxFill) {
/*  330 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  333 */     return this.defRetValue;
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
/*  346 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  348 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  350 */         if ((curr = key[pos]) == null) {
/*  351 */           key[last] = null;
/*      */           return;
/*      */         } 
/*  354 */         int slot = HashCommon.mix(curr.hashCode()) & this.mask;
/*  355 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  357 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  359 */       key[last] = curr;
/*  360 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public double removeDouble(Object k) {
/*  366 */     if (k == null) {
/*  367 */       if (this.containsNullKey)
/*  368 */         return removeNullEntry(); 
/*  369 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  372 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  375 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  376 */       return this.defRetValue; 
/*  377 */     if (k.equals(curr))
/*  378 */       return removeEntry(pos); 
/*      */     while (true) {
/*  380 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  381 */         return this.defRetValue; 
/*  382 */       if (k.equals(curr)) {
/*  383 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public double getDouble(Object k) {
/*  389 */     if (k == null) {
/*  390 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  392 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  395 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  396 */       return this.defRetValue; 
/*  397 */     if (k.equals(curr)) {
/*  398 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  401 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  402 */         return this.defRetValue; 
/*  403 */       if (k.equals(curr)) {
/*  404 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  410 */     if (k == null) {
/*  411 */       return this.containsNullKey;
/*      */     }
/*  413 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  416 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  417 */       return false; 
/*  418 */     if (k.equals(curr)) {
/*  419 */       return true;
/*      */     }
/*      */     while (true) {
/*  422 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  423 */         return false; 
/*  424 */       if (k.equals(curr))
/*  425 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(double v) {
/*  430 */     double[] value = this.value;
/*  431 */     K[] key = this.key;
/*  432 */     if (this.containsNullKey && Double.doubleToLongBits(value[this.n]) == Double.doubleToLongBits(v))
/*  433 */       return true; 
/*  434 */     for (int i = this.n; i-- != 0;) {
/*  435 */       if (key[i] != null && Double.doubleToLongBits(value[i]) == Double.doubleToLongBits(v))
/*  436 */         return true; 
/*  437 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double getOrDefault(Object k, double defaultValue) {
/*  443 */     if (k == null) {
/*  444 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  446 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  449 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  450 */       return defaultValue; 
/*  451 */     if (k.equals(curr)) {
/*  452 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  455 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  456 */         return defaultValue; 
/*  457 */       if (k.equals(curr)) {
/*  458 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public double putIfAbsent(K k, double v) {
/*  464 */     int pos = find(k);
/*  465 */     if (pos >= 0)
/*  466 */       return this.value[pos]; 
/*  467 */     insert(-pos - 1, k, v);
/*  468 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, double v) {
/*  474 */     if (k == null) {
/*  475 */       if (this.containsNullKey && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[this.n])) {
/*  476 */         removeNullEntry();
/*  477 */         return true;
/*      */       } 
/*  479 */       return false;
/*      */     } 
/*      */     
/*  482 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  485 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  486 */       return false; 
/*  487 */     if (k.equals(curr) && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
/*  488 */       removeEntry(pos);
/*  489 */       return true;
/*      */     } 
/*      */     while (true) {
/*  492 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  493 */         return false; 
/*  494 */       if (k.equals(curr) && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
/*  495 */         removeEntry(pos);
/*  496 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(K k, double oldValue, double v) {
/*  503 */     int pos = find(k);
/*  504 */     if (pos < 0 || Double.doubleToLongBits(oldValue) != Double.doubleToLongBits(this.value[pos]))
/*  505 */       return false; 
/*  506 */     this.value[pos] = v;
/*  507 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public double replace(K k, double v) {
/*  512 */     int pos = find(k);
/*  513 */     if (pos < 0)
/*  514 */       return this.defRetValue; 
/*  515 */     double oldValue = this.value[pos];
/*  516 */     this.value[pos] = v;
/*  517 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeDoubleIfAbsent(K k, ToDoubleFunction<? super K> mappingFunction) {
/*  523 */     Objects.requireNonNull(mappingFunction);
/*  524 */     int pos = find(k);
/*  525 */     if (pos >= 0)
/*  526 */       return this.value[pos]; 
/*  527 */     double newValue = mappingFunction.applyAsDouble(k);
/*  528 */     insert(-pos - 1, k, newValue);
/*  529 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeDoubleIfPresent(K k, BiFunction<? super K, ? super Double, ? extends Double> remappingFunction) {
/*  535 */     Objects.requireNonNull(remappingFunction);
/*  536 */     int pos = find(k);
/*  537 */     if (pos < 0)
/*  538 */       return this.defRetValue; 
/*  539 */     Double newValue = remappingFunction.apply(k, Double.valueOf(this.value[pos]));
/*  540 */     if (newValue == null) {
/*  541 */       if (k == null) {
/*  542 */         removeNullEntry();
/*      */       } else {
/*  544 */         removeEntry(pos);
/*  545 */       }  return this.defRetValue;
/*      */     } 
/*  547 */     this.value[pos] = newValue.doubleValue(); return newValue.doubleValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeDouble(K k, BiFunction<? super K, ? super Double, ? extends Double> remappingFunction) {
/*  553 */     Objects.requireNonNull(remappingFunction);
/*  554 */     int pos = find(k);
/*  555 */     Double newValue = remappingFunction.apply(k, (pos >= 0) ? Double.valueOf(this.value[pos]) : null);
/*  556 */     if (newValue == null) {
/*  557 */       if (pos >= 0)
/*  558 */         if (k == null) {
/*  559 */           removeNullEntry();
/*      */         } else {
/*  561 */           removeEntry(pos);
/*      */         }  
/*  563 */       return this.defRetValue;
/*      */     } 
/*  565 */     double newVal = newValue.doubleValue();
/*  566 */     if (pos < 0) {
/*  567 */       insert(-pos - 1, k, newVal);
/*  568 */       return newVal;
/*      */     } 
/*  570 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double mergeDouble(K k, double v, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/*  576 */     Objects.requireNonNull(remappingFunction);
/*  577 */     int pos = find(k);
/*  578 */     if (pos < 0) {
/*  579 */       insert(-pos - 1, k, v);
/*  580 */       return v;
/*      */     } 
/*  582 */     Double newValue = remappingFunction.apply(Double.valueOf(this.value[pos]), Double.valueOf(v));
/*  583 */     if (newValue == null) {
/*  584 */       if (k == null) {
/*  585 */         removeNullEntry();
/*      */       } else {
/*  587 */         removeEntry(pos);
/*  588 */       }  return this.defRetValue;
/*      */     } 
/*  590 */     this.value[pos] = newValue.doubleValue(); return newValue.doubleValue();
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
/*  601 */     if (this.size == 0)
/*      */       return; 
/*  603 */     this.size = 0;
/*  604 */     this.containsNullKey = false;
/*  605 */     Arrays.fill((Object[])this.key, (Object)null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  609 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  613 */     return (this.size == 0);
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
/*  625 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public K getKey() {
/*  631 */       return Object2DoubleOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public double getDoubleValue() {
/*  635 */       return Object2DoubleOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public double setValue(double v) {
/*  639 */       double oldValue = Object2DoubleOpenHashMap.this.value[this.index];
/*  640 */       Object2DoubleOpenHashMap.this.value[this.index] = v;
/*  641 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double getValue() {
/*  651 */       return Double.valueOf(Object2DoubleOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double setValue(Double v) {
/*  661 */       return Double.valueOf(setValue(v.doubleValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  666 */       if (!(o instanceof Map.Entry))
/*  667 */         return false; 
/*  668 */       Map.Entry<K, Double> e = (Map.Entry<K, Double>)o;
/*  669 */       return (Objects.equals(Object2DoubleOpenHashMap.this.key[this.index], e.getKey()) && 
/*  670 */         Double.doubleToLongBits(Object2DoubleOpenHashMap.this.value[this.index]) == Double.doubleToLongBits(((Double)e.getValue()).doubleValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  674 */       return ((Object2DoubleOpenHashMap.this.key[this.index] == null) ? 0 : Object2DoubleOpenHashMap.this.key[this.index].hashCode()) ^ 
/*  675 */         HashCommon.double2int(Object2DoubleOpenHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  679 */       return (new StringBuilder()).append(Object2DoubleOpenHashMap.this.key[this.index]).append("=>").append(Object2DoubleOpenHashMap.this.value[this.index]).toString();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  689 */     int pos = Object2DoubleOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  696 */     int last = -1;
/*      */     
/*  698 */     int c = Object2DoubleOpenHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  702 */     boolean mustReturnNullKey = Object2DoubleOpenHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     ObjectArrayList<K> wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  709 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  712 */       if (!hasNext())
/*  713 */         throw new NoSuchElementException(); 
/*  714 */       this.c--;
/*  715 */       if (this.mustReturnNullKey) {
/*  716 */         this.mustReturnNullKey = false;
/*  717 */         return this.last = Object2DoubleOpenHashMap.this.n;
/*      */       } 
/*  719 */       K[] key = Object2DoubleOpenHashMap.this.key;
/*      */       while (true) {
/*  721 */         if (--this.pos < 0) {
/*      */           
/*  723 */           this.last = Integer.MIN_VALUE;
/*  724 */           K k = this.wrapped.get(-this.pos - 1);
/*  725 */           int p = HashCommon.mix(k.hashCode()) & Object2DoubleOpenHashMap.this.mask;
/*  726 */           while (!k.equals(key[p]))
/*  727 */             p = p + 1 & Object2DoubleOpenHashMap.this.mask; 
/*  728 */           return p;
/*      */         } 
/*  730 */         if (key[this.pos] != null) {
/*  731 */           return this.last = this.pos;
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
/*  745 */       K[] key = Object2DoubleOpenHashMap.this.key; while (true) {
/*      */         K curr; int last;
/*  747 */         pos = (last = pos) + 1 & Object2DoubleOpenHashMap.this.mask;
/*      */         while (true) {
/*  749 */           if ((curr = key[pos]) == null) {
/*  750 */             key[last] = null;
/*      */             return;
/*      */           } 
/*  753 */           int slot = HashCommon.mix(curr.hashCode()) & Object2DoubleOpenHashMap.this.mask;
/*  754 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  756 */           pos = pos + 1 & Object2DoubleOpenHashMap.this.mask;
/*      */         } 
/*  758 */         if (pos < last) {
/*  759 */           if (this.wrapped == null)
/*  760 */             this.wrapped = new ObjectArrayList<>(2); 
/*  761 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  763 */         key[last] = curr;
/*  764 */         Object2DoubleOpenHashMap.this.value[last] = Object2DoubleOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  768 */       if (this.last == -1)
/*  769 */         throw new IllegalStateException(); 
/*  770 */       if (this.last == Object2DoubleOpenHashMap.this.n) {
/*  771 */         Object2DoubleOpenHashMap.this.containsNullKey = false;
/*  772 */         Object2DoubleOpenHashMap.this.key[Object2DoubleOpenHashMap.this.n] = null;
/*  773 */       } else if (this.pos >= 0) {
/*  774 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  777 */         Object2DoubleOpenHashMap.this.removeDouble(this.wrapped.set(-this.pos - 1, null));
/*  778 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  781 */       Object2DoubleOpenHashMap.this.size--;
/*  782 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  787 */       int i = n;
/*  788 */       while (i-- != 0 && hasNext())
/*  789 */         nextEntry(); 
/*  790 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Object2DoubleMap.Entry<K>> { private Object2DoubleOpenHashMap<K>.MapEntry entry;
/*      */     
/*      */     public Object2DoubleOpenHashMap<K>.MapEntry next() {
/*  797 */       return this.entry = new Object2DoubleOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  801 */       super.remove();
/*  802 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Object2DoubleMap.Entry<K>> { private FastEntryIterator() {
/*  806 */       this.entry = new Object2DoubleOpenHashMap.MapEntry();
/*      */     } private final Object2DoubleOpenHashMap<K>.MapEntry entry;
/*      */     public Object2DoubleOpenHashMap<K>.MapEntry next() {
/*  809 */       this.entry.index = nextEntry();
/*  810 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Object2DoubleMap.Entry<K>> implements Object2DoubleMap.FastEntrySet<K> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Object2DoubleMap.Entry<K>> iterator() {
/*  816 */       return new Object2DoubleOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Object2DoubleMap.Entry<K>> fastIterator() {
/*  820 */       return new Object2DoubleOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  825 */       if (!(o instanceof Map.Entry))
/*  826 */         return false; 
/*  827 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  828 */       if (e.getValue() == null || !(e.getValue() instanceof Double))
/*  829 */         return false; 
/*  830 */       K k = (K)e.getKey();
/*  831 */       double v = ((Double)e.getValue()).doubleValue();
/*  832 */       if (k == null) {
/*  833 */         return (Object2DoubleOpenHashMap.this.containsNullKey && 
/*  834 */           Double.doubleToLongBits(Object2DoubleOpenHashMap.this.value[Object2DoubleOpenHashMap.this.n]) == Double.doubleToLongBits(v));
/*      */       }
/*  836 */       K[] key = Object2DoubleOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  839 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2DoubleOpenHashMap.this.mask]) == null)
/*  840 */         return false; 
/*  841 */       if (k.equals(curr)) {
/*  842 */         return (Double.doubleToLongBits(Object2DoubleOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v));
/*      */       }
/*      */       while (true) {
/*  845 */         if ((curr = key[pos = pos + 1 & Object2DoubleOpenHashMap.this.mask]) == null)
/*  846 */           return false; 
/*  847 */         if (k.equals(curr)) {
/*  848 */           return (Double.doubleToLongBits(Object2DoubleOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v));
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  854 */       if (!(o instanceof Map.Entry))
/*  855 */         return false; 
/*  856 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  857 */       if (e.getValue() == null || !(e.getValue() instanceof Double))
/*  858 */         return false; 
/*  859 */       K k = (K)e.getKey();
/*  860 */       double v = ((Double)e.getValue()).doubleValue();
/*  861 */       if (k == null) {
/*  862 */         if (Object2DoubleOpenHashMap.this.containsNullKey && Double.doubleToLongBits(Object2DoubleOpenHashMap.this.value[Object2DoubleOpenHashMap.this.n]) == Double.doubleToLongBits(v)) {
/*  863 */           Object2DoubleOpenHashMap.this.removeNullEntry();
/*  864 */           return true;
/*      */         } 
/*  866 */         return false;
/*      */       } 
/*      */       
/*  869 */       K[] key = Object2DoubleOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  872 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2DoubleOpenHashMap.this.mask]) == null)
/*  873 */         return false; 
/*  874 */       if (curr.equals(k)) {
/*  875 */         if (Double.doubleToLongBits(Object2DoubleOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
/*  876 */           Object2DoubleOpenHashMap.this.removeEntry(pos);
/*  877 */           return true;
/*      */         } 
/*  879 */         return false;
/*      */       } 
/*      */       while (true) {
/*  882 */         if ((curr = key[pos = pos + 1 & Object2DoubleOpenHashMap.this.mask]) == null)
/*  883 */           return false; 
/*  884 */         if (curr.equals(k) && 
/*  885 */           Double.doubleToLongBits(Object2DoubleOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
/*  886 */           Object2DoubleOpenHashMap.this.removeEntry(pos);
/*  887 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  894 */       return Object2DoubleOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  898 */       Object2DoubleOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Object2DoubleMap.Entry<K>> consumer) {
/*  903 */       if (Object2DoubleOpenHashMap.this.containsNullKey)
/*  904 */         consumer.accept(new AbstractObject2DoubleMap.BasicEntry<>(Object2DoubleOpenHashMap.this.key[Object2DoubleOpenHashMap.this.n], Object2DoubleOpenHashMap.this.value[Object2DoubleOpenHashMap.this.n])); 
/*  905 */       for (int pos = Object2DoubleOpenHashMap.this.n; pos-- != 0;) {
/*  906 */         if (Object2DoubleOpenHashMap.this.key[pos] != null)
/*  907 */           consumer.accept(new AbstractObject2DoubleMap.BasicEntry<>(Object2DoubleOpenHashMap.this.key[pos], Object2DoubleOpenHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Object2DoubleMap.Entry<K>> consumer) {
/*  912 */       AbstractObject2DoubleMap.BasicEntry<K> entry = new AbstractObject2DoubleMap.BasicEntry<>();
/*  913 */       if (Object2DoubleOpenHashMap.this.containsNullKey) {
/*  914 */         entry.key = Object2DoubleOpenHashMap.this.key[Object2DoubleOpenHashMap.this.n];
/*  915 */         entry.value = Object2DoubleOpenHashMap.this.value[Object2DoubleOpenHashMap.this.n];
/*  916 */         consumer.accept(entry);
/*      */       } 
/*  918 */       for (int pos = Object2DoubleOpenHashMap.this.n; pos-- != 0;) {
/*  919 */         if (Object2DoubleOpenHashMap.this.key[pos] != null) {
/*  920 */           entry.key = Object2DoubleOpenHashMap.this.key[pos];
/*  921 */           entry.value = Object2DoubleOpenHashMap.this.value[pos];
/*  922 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Object2DoubleMap.FastEntrySet<K> object2DoubleEntrySet() {
/*  928 */     if (this.entries == null)
/*  929 */       this.entries = new MapEntrySet(); 
/*  930 */     return this.entries;
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
/*  947 */       return Object2DoubleOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractObjectSet<K> { private KeySet() {}
/*      */     
/*      */     public ObjectIterator<K> iterator() {
/*  953 */       return new Object2DoubleOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/*  958 */       if (Object2DoubleOpenHashMap.this.containsNullKey)
/*  959 */         consumer.accept(Object2DoubleOpenHashMap.this.key[Object2DoubleOpenHashMap.this.n]); 
/*  960 */       for (int pos = Object2DoubleOpenHashMap.this.n; pos-- != 0; ) {
/*  961 */         K k = Object2DoubleOpenHashMap.this.key[pos];
/*  962 */         if (k != null)
/*  963 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  968 */       return Object2DoubleOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(Object k) {
/*  972 */       return Object2DoubleOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(Object k) {
/*  976 */       int oldSize = Object2DoubleOpenHashMap.this.size;
/*  977 */       Object2DoubleOpenHashMap.this.removeDouble(k);
/*  978 */       return (Object2DoubleOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  982 */       Object2DoubleOpenHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ObjectSet<K> keySet() {
/*  987 */     if (this.keys == null)
/*  988 */       this.keys = new KeySet(); 
/*  989 */     return this.keys;
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
/* 1006 */       return Object2DoubleOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public DoubleCollection values() {
/* 1011 */     if (this.values == null)
/* 1012 */       this.values = (DoubleCollection)new AbstractDoubleCollection()
/*      */         {
/*      */           public DoubleIterator iterator() {
/* 1015 */             return new Object2DoubleOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1019 */             return Object2DoubleOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(double v) {
/* 1023 */             return Object2DoubleOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1027 */             Object2DoubleOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(DoubleConsumer consumer)
/*      */           {
/* 1032 */             if (Object2DoubleOpenHashMap.this.containsNullKey)
/* 1033 */               consumer.accept(Object2DoubleOpenHashMap.this.value[Object2DoubleOpenHashMap.this.n]); 
/* 1034 */             for (int pos = Object2DoubleOpenHashMap.this.n; pos-- != 0;) {
/* 1035 */               if (Object2DoubleOpenHashMap.this.key[pos] != null)
/* 1036 */                 consumer.accept(Object2DoubleOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1039 */     return this.values;
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
/* 1056 */     return trim(this.size);
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
/* 1080 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1081 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1082 */       return true; 
/*      */     try {
/* 1084 */       rehash(l);
/* 1085 */     } catch (OutOfMemoryError cantDoIt) {
/* 1086 */       return false;
/*      */     } 
/* 1088 */     return true;
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
/* 1104 */     K[] key = this.key;
/* 1105 */     double[] value = this.value;
/* 1106 */     int mask = newN - 1;
/* 1107 */     K[] newKey = (K[])new Object[newN + 1];
/* 1108 */     double[] newValue = new double[newN + 1];
/* 1109 */     int i = this.n;
/* 1110 */     for (int j = realSize(); j-- != 0; ) {
/* 1111 */       while (key[--i] == null); int pos;
/* 1112 */       if (newKey[pos = HashCommon.mix(key[i].hashCode()) & mask] != null)
/* 1113 */         while (newKey[pos = pos + 1 & mask] != null); 
/* 1114 */       newKey[pos] = key[i];
/* 1115 */       newValue[pos] = value[i];
/*      */     } 
/* 1117 */     newValue[newN] = value[this.n];
/* 1118 */     this.n = newN;
/* 1119 */     this.mask = mask;
/* 1120 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1121 */     this.key = newKey;
/* 1122 */     this.value = newValue;
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
/*      */   public Object2DoubleOpenHashMap<K> clone() {
/*      */     Object2DoubleOpenHashMap<K> c;
/*      */     try {
/* 1139 */       c = (Object2DoubleOpenHashMap<K>)super.clone();
/* 1140 */     } catch (CloneNotSupportedException cantHappen) {
/* 1141 */       throw new InternalError();
/*      */     } 
/* 1143 */     c.keys = null;
/* 1144 */     c.values = null;
/* 1145 */     c.entries = null;
/* 1146 */     c.containsNullKey = this.containsNullKey;
/* 1147 */     c.key = (K[])this.key.clone();
/* 1148 */     c.value = (double[])this.value.clone();
/* 1149 */     return c;
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
/* 1162 */     int h = 0;
/* 1163 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1164 */       while (this.key[i] == null)
/* 1165 */         i++; 
/* 1166 */       if (this != this.key[i])
/* 1167 */         t = this.key[i].hashCode(); 
/* 1168 */       t ^= HashCommon.double2int(this.value[i]);
/* 1169 */       h += t;
/* 1170 */       i++;
/*      */     } 
/*      */     
/* 1173 */     if (this.containsNullKey)
/* 1174 */       h += HashCommon.double2int(this.value[this.n]); 
/* 1175 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1178 */     K[] key = this.key;
/* 1179 */     double[] value = this.value;
/* 1180 */     MapIterator i = new MapIterator();
/* 1181 */     s.defaultWriteObject();
/* 1182 */     for (int j = this.size; j-- != 0; ) {
/* 1183 */       int e = i.nextEntry();
/* 1184 */       s.writeObject(key[e]);
/* 1185 */       s.writeDouble(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1190 */     s.defaultReadObject();
/* 1191 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1192 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1193 */     this.mask = this.n - 1;
/* 1194 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1195 */     double[] value = this.value = new double[this.n + 1];
/*      */ 
/*      */     
/* 1198 */     for (int i = this.size; i-- != 0; ) {
/* 1199 */       int pos; K k = (K)s.readObject();
/* 1200 */       double v = s.readDouble();
/* 1201 */       if (k == null) {
/* 1202 */         pos = this.n;
/* 1203 */         this.containsNullKey = true;
/*      */       } else {
/* 1205 */         pos = HashCommon.mix(k.hashCode()) & this.mask;
/* 1206 */         while (key[pos] != null)
/* 1207 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1209 */       key[pos] = k;
/* 1210 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2DoubleOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */