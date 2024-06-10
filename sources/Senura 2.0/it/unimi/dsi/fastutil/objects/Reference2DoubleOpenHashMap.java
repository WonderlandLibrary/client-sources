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
/*      */ public class Reference2DoubleOpenHashMap<K>
/*      */   extends AbstractReference2DoubleMap<K>
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
/*      */   protected transient Reference2DoubleMap.FastEntrySet<K> entries;
/*      */   protected transient ReferenceSet<K> keys;
/*      */   protected transient DoubleCollection values;
/*      */   
/*      */   public Reference2DoubleOpenHashMap(int expected, float f) {
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
/*      */   public Reference2DoubleOpenHashMap(int expected) {
/*  118 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2DoubleOpenHashMap() {
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
/*      */   public Reference2DoubleOpenHashMap(Map<? extends K, ? extends Double> m, float f) {
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
/*      */   public Reference2DoubleOpenHashMap(Map<? extends K, ? extends Double> m) {
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
/*      */   public Reference2DoubleOpenHashMap(Reference2DoubleMap<K> m, float f) {
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
/*      */   public Reference2DoubleOpenHashMap(Reference2DoubleMap<K> m) {
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
/*      */   public Reference2DoubleOpenHashMap(K[] k, double[] v, float f) {
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
/*      */   public Reference2DoubleOpenHashMap(K[] k, double[] v) {
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
/*  254 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  255 */       return -(pos + 1); 
/*  256 */     if (k == curr) {
/*  257 */       return pos;
/*      */     }
/*      */     while (true) {
/*  260 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  261 */         return -(pos + 1); 
/*  262 */       if (k == curr)
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
/*  319 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) != null) {
/*      */         
/*  321 */         if (curr == k)
/*  322 */           return addToValue(pos, incr); 
/*  323 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  324 */           if (curr == k)
/*  325 */             return addToValue(pos, incr); 
/*      */         } 
/*      */       } 
/*  328 */     }  this.key[pos] = k;
/*  329 */     this.value[pos] = this.defRetValue + incr;
/*  330 */     if (this.size++ >= this.maxFill) {
/*  331 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  334 */     return this.defRetValue;
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
/*  347 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  349 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  351 */         if ((curr = key[pos]) == null) {
/*  352 */           key[last] = null;
/*      */           return;
/*      */         } 
/*  355 */         int slot = HashCommon.mix(System.identityHashCode(curr)) & this.mask;
/*  356 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  358 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  360 */       key[last] = curr;
/*  361 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public double removeDouble(Object k) {
/*  367 */     if (k == null) {
/*  368 */       if (this.containsNullKey)
/*  369 */         return removeNullEntry(); 
/*  370 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  373 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  376 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  377 */       return this.defRetValue; 
/*  378 */     if (k == curr)
/*  379 */       return removeEntry(pos); 
/*      */     while (true) {
/*  381 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  382 */         return this.defRetValue; 
/*  383 */       if (k == curr) {
/*  384 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public double getDouble(Object k) {
/*  390 */     if (k == null) {
/*  391 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  393 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  396 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  397 */       return this.defRetValue; 
/*  398 */     if (k == curr) {
/*  399 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  402 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  403 */         return this.defRetValue; 
/*  404 */       if (k == curr) {
/*  405 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  411 */     if (k == null) {
/*  412 */       return this.containsNullKey;
/*      */     }
/*  414 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  417 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  418 */       return false; 
/*  419 */     if (k == curr) {
/*  420 */       return true;
/*      */     }
/*      */     while (true) {
/*  423 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  424 */         return false; 
/*  425 */       if (k == curr)
/*  426 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(double v) {
/*  431 */     double[] value = this.value;
/*  432 */     K[] key = this.key;
/*  433 */     if (this.containsNullKey && Double.doubleToLongBits(value[this.n]) == Double.doubleToLongBits(v))
/*  434 */       return true; 
/*  435 */     for (int i = this.n; i-- != 0;) {
/*  436 */       if (key[i] != null && Double.doubleToLongBits(value[i]) == Double.doubleToLongBits(v))
/*  437 */         return true; 
/*  438 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double getOrDefault(Object k, double defaultValue) {
/*  444 */     if (k == null) {
/*  445 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  447 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  450 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  451 */       return defaultValue; 
/*  452 */     if (k == curr) {
/*  453 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  456 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  457 */         return defaultValue; 
/*  458 */       if (k == curr) {
/*  459 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public double putIfAbsent(K k, double v) {
/*  465 */     int pos = find(k);
/*  466 */     if (pos >= 0)
/*  467 */       return this.value[pos]; 
/*  468 */     insert(-pos - 1, k, v);
/*  469 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, double v) {
/*  475 */     if (k == null) {
/*  476 */       if (this.containsNullKey && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[this.n])) {
/*  477 */         removeNullEntry();
/*  478 */         return true;
/*      */       } 
/*  480 */       return false;
/*      */     } 
/*      */     
/*  483 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  486 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  487 */       return false; 
/*  488 */     if (k == curr && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
/*  489 */       removeEntry(pos);
/*  490 */       return true;
/*      */     } 
/*      */     while (true) {
/*  493 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  494 */         return false; 
/*  495 */       if (k == curr && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
/*  496 */         removeEntry(pos);
/*  497 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(K k, double oldValue, double v) {
/*  504 */     int pos = find(k);
/*  505 */     if (pos < 0 || Double.doubleToLongBits(oldValue) != Double.doubleToLongBits(this.value[pos]))
/*  506 */       return false; 
/*  507 */     this.value[pos] = v;
/*  508 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public double replace(K k, double v) {
/*  513 */     int pos = find(k);
/*  514 */     if (pos < 0)
/*  515 */       return this.defRetValue; 
/*  516 */     double oldValue = this.value[pos];
/*  517 */     this.value[pos] = v;
/*  518 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeDoubleIfAbsent(K k, ToDoubleFunction<? super K> mappingFunction) {
/*  524 */     Objects.requireNonNull(mappingFunction);
/*  525 */     int pos = find(k);
/*  526 */     if (pos >= 0)
/*  527 */       return this.value[pos]; 
/*  528 */     double newValue = mappingFunction.applyAsDouble(k);
/*  529 */     insert(-pos - 1, k, newValue);
/*  530 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeDoubleIfPresent(K k, BiFunction<? super K, ? super Double, ? extends Double> remappingFunction) {
/*  536 */     Objects.requireNonNull(remappingFunction);
/*  537 */     int pos = find(k);
/*  538 */     if (pos < 0)
/*  539 */       return this.defRetValue; 
/*  540 */     Double newValue = remappingFunction.apply(k, Double.valueOf(this.value[pos]));
/*  541 */     if (newValue == null) {
/*  542 */       if (k == null) {
/*  543 */         removeNullEntry();
/*      */       } else {
/*  545 */         removeEntry(pos);
/*  546 */       }  return this.defRetValue;
/*      */     } 
/*  548 */     this.value[pos] = newValue.doubleValue(); return newValue.doubleValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeDouble(K k, BiFunction<? super K, ? super Double, ? extends Double> remappingFunction) {
/*  554 */     Objects.requireNonNull(remappingFunction);
/*  555 */     int pos = find(k);
/*  556 */     Double newValue = remappingFunction.apply(k, (pos >= 0) ? Double.valueOf(this.value[pos]) : null);
/*  557 */     if (newValue == null) {
/*  558 */       if (pos >= 0)
/*  559 */         if (k == null) {
/*  560 */           removeNullEntry();
/*      */         } else {
/*  562 */           removeEntry(pos);
/*      */         }  
/*  564 */       return this.defRetValue;
/*      */     } 
/*  566 */     double newVal = newValue.doubleValue();
/*  567 */     if (pos < 0) {
/*  568 */       insert(-pos - 1, k, newVal);
/*  569 */       return newVal;
/*      */     } 
/*  571 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double mergeDouble(K k, double v, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/*  577 */     Objects.requireNonNull(remappingFunction);
/*  578 */     int pos = find(k);
/*  579 */     if (pos < 0) {
/*  580 */       insert(-pos - 1, k, v);
/*  581 */       return v;
/*      */     } 
/*  583 */     Double newValue = remappingFunction.apply(Double.valueOf(this.value[pos]), Double.valueOf(v));
/*  584 */     if (newValue == null) {
/*  585 */       if (k == null) {
/*  586 */         removeNullEntry();
/*      */       } else {
/*  588 */         removeEntry(pos);
/*  589 */       }  return this.defRetValue;
/*      */     } 
/*  591 */     this.value[pos] = newValue.doubleValue(); return newValue.doubleValue();
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
/*  602 */     if (this.size == 0)
/*      */       return; 
/*  604 */     this.size = 0;
/*  605 */     this.containsNullKey = false;
/*  606 */     Arrays.fill((Object[])this.key, (Object)null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  610 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  614 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Reference2DoubleMap.Entry<K>, Map.Entry<K, Double>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  626 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public K getKey() {
/*  632 */       return Reference2DoubleOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public double getDoubleValue() {
/*  636 */       return Reference2DoubleOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public double setValue(double v) {
/*  640 */       double oldValue = Reference2DoubleOpenHashMap.this.value[this.index];
/*  641 */       Reference2DoubleOpenHashMap.this.value[this.index] = v;
/*  642 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double getValue() {
/*  652 */       return Double.valueOf(Reference2DoubleOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double setValue(Double v) {
/*  662 */       return Double.valueOf(setValue(v.doubleValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  667 */       if (!(o instanceof Map.Entry))
/*  668 */         return false; 
/*  669 */       Map.Entry<K, Double> e = (Map.Entry<K, Double>)o;
/*  670 */       return (Reference2DoubleOpenHashMap.this.key[this.index] == e.getKey() && 
/*  671 */         Double.doubleToLongBits(Reference2DoubleOpenHashMap.this.value[this.index]) == Double.doubleToLongBits(((Double)e.getValue()).doubleValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  675 */       return System.identityHashCode(Reference2DoubleOpenHashMap.this.key[this.index]) ^ HashCommon.double2int(Reference2DoubleOpenHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  679 */       return (new StringBuilder()).append(Reference2DoubleOpenHashMap.this.key[this.index]).append("=>").append(Reference2DoubleOpenHashMap.this.value[this.index]).toString();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  689 */     int pos = Reference2DoubleOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  696 */     int last = -1;
/*      */     
/*  698 */     int c = Reference2DoubleOpenHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  702 */     boolean mustReturnNullKey = Reference2DoubleOpenHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     ReferenceArrayList<K> wrapped;
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
/*  717 */         return this.last = Reference2DoubleOpenHashMap.this.n;
/*      */       } 
/*  719 */       K[] key = Reference2DoubleOpenHashMap.this.key;
/*      */       while (true) {
/*  721 */         if (--this.pos < 0) {
/*      */           
/*  723 */           this.last = Integer.MIN_VALUE;
/*  724 */           K k = this.wrapped.get(-this.pos - 1);
/*  725 */           int p = HashCommon.mix(System.identityHashCode(k)) & Reference2DoubleOpenHashMap.this.mask;
/*  726 */           while (k != key[p])
/*  727 */             p = p + 1 & Reference2DoubleOpenHashMap.this.mask; 
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
/*  745 */       K[] key = Reference2DoubleOpenHashMap.this.key; while (true) {
/*      */         K curr; int last;
/*  747 */         pos = (last = pos) + 1 & Reference2DoubleOpenHashMap.this.mask;
/*      */         while (true) {
/*  749 */           if ((curr = key[pos]) == null) {
/*  750 */             key[last] = null;
/*      */             return;
/*      */           } 
/*  753 */           int slot = HashCommon.mix(System.identityHashCode(curr)) & Reference2DoubleOpenHashMap.this.mask;
/*  754 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  756 */           pos = pos + 1 & Reference2DoubleOpenHashMap.this.mask;
/*      */         } 
/*  758 */         if (pos < last) {
/*  759 */           if (this.wrapped == null)
/*  760 */             this.wrapped = new ReferenceArrayList<>(2); 
/*  761 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  763 */         key[last] = curr;
/*  764 */         Reference2DoubleOpenHashMap.this.value[last] = Reference2DoubleOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  768 */       if (this.last == -1)
/*  769 */         throw new IllegalStateException(); 
/*  770 */       if (this.last == Reference2DoubleOpenHashMap.this.n) {
/*  771 */         Reference2DoubleOpenHashMap.this.containsNullKey = false;
/*  772 */         Reference2DoubleOpenHashMap.this.key[Reference2DoubleOpenHashMap.this.n] = null;
/*  773 */       } else if (this.pos >= 0) {
/*  774 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  777 */         Reference2DoubleOpenHashMap.this.removeDouble(this.wrapped.set(-this.pos - 1, null));
/*  778 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  781 */       Reference2DoubleOpenHashMap.this.size--;
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
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Reference2DoubleMap.Entry<K>> { private Reference2DoubleOpenHashMap<K>.MapEntry entry;
/*      */     
/*      */     public Reference2DoubleOpenHashMap<K>.MapEntry next() {
/*  797 */       return this.entry = new Reference2DoubleOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  801 */       super.remove();
/*  802 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Reference2DoubleMap.Entry<K>> { private FastEntryIterator() {
/*  806 */       this.entry = new Reference2DoubleOpenHashMap.MapEntry();
/*      */     } private final Reference2DoubleOpenHashMap<K>.MapEntry entry;
/*      */     public Reference2DoubleOpenHashMap<K>.MapEntry next() {
/*  809 */       this.entry.index = nextEntry();
/*  810 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Reference2DoubleMap.Entry<K>> implements Reference2DoubleMap.FastEntrySet<K> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Reference2DoubleMap.Entry<K>> iterator() {
/*  816 */       return new Reference2DoubleOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Reference2DoubleMap.Entry<K>> fastIterator() {
/*  820 */       return new Reference2DoubleOpenHashMap.FastEntryIterator();
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
/*  833 */         return (Reference2DoubleOpenHashMap.this.containsNullKey && 
/*  834 */           Double.doubleToLongBits(Reference2DoubleOpenHashMap.this.value[Reference2DoubleOpenHashMap.this.n]) == Double.doubleToLongBits(v));
/*      */       }
/*  836 */       K[] key = Reference2DoubleOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  839 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2DoubleOpenHashMap.this.mask]) == null)
/*      */       {
/*  841 */         return false; } 
/*  842 */       if (k == curr) {
/*  843 */         return (Double.doubleToLongBits(Reference2DoubleOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v));
/*      */       }
/*      */       while (true) {
/*  846 */         if ((curr = key[pos = pos + 1 & Reference2DoubleOpenHashMap.this.mask]) == null)
/*  847 */           return false; 
/*  848 */         if (k == curr) {
/*  849 */           return (Double.doubleToLongBits(Reference2DoubleOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v));
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  855 */       if (!(o instanceof Map.Entry))
/*  856 */         return false; 
/*  857 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  858 */       if (e.getValue() == null || !(e.getValue() instanceof Double))
/*  859 */         return false; 
/*  860 */       K k = (K)e.getKey();
/*  861 */       double v = ((Double)e.getValue()).doubleValue();
/*  862 */       if (k == null) {
/*  863 */         if (Reference2DoubleOpenHashMap.this.containsNullKey && Double.doubleToLongBits(Reference2DoubleOpenHashMap.this.value[Reference2DoubleOpenHashMap.this.n]) == Double.doubleToLongBits(v)) {
/*  864 */           Reference2DoubleOpenHashMap.this.removeNullEntry();
/*  865 */           return true;
/*      */         } 
/*  867 */         return false;
/*      */       } 
/*      */       
/*  870 */       K[] key = Reference2DoubleOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  873 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2DoubleOpenHashMap.this.mask]) == null)
/*      */       {
/*  875 */         return false; } 
/*  876 */       if (curr == k) {
/*  877 */         if (Double.doubleToLongBits(Reference2DoubleOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
/*  878 */           Reference2DoubleOpenHashMap.this.removeEntry(pos);
/*  879 */           return true;
/*      */         } 
/*  881 */         return false;
/*      */       } 
/*      */       while (true) {
/*  884 */         if ((curr = key[pos = pos + 1 & Reference2DoubleOpenHashMap.this.mask]) == null)
/*  885 */           return false; 
/*  886 */         if (curr == k && 
/*  887 */           Double.doubleToLongBits(Reference2DoubleOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
/*  888 */           Reference2DoubleOpenHashMap.this.removeEntry(pos);
/*  889 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  896 */       return Reference2DoubleOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  900 */       Reference2DoubleOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Reference2DoubleMap.Entry<K>> consumer) {
/*  905 */       if (Reference2DoubleOpenHashMap.this.containsNullKey)
/*  906 */         consumer.accept(new AbstractReference2DoubleMap.BasicEntry<>(Reference2DoubleOpenHashMap.this.key[Reference2DoubleOpenHashMap.this.n], Reference2DoubleOpenHashMap.this.value[Reference2DoubleOpenHashMap.this.n])); 
/*  907 */       for (int pos = Reference2DoubleOpenHashMap.this.n; pos-- != 0;) {
/*  908 */         if (Reference2DoubleOpenHashMap.this.key[pos] != null)
/*  909 */           consumer.accept(new AbstractReference2DoubleMap.BasicEntry<>(Reference2DoubleOpenHashMap.this.key[pos], Reference2DoubleOpenHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Reference2DoubleMap.Entry<K>> consumer) {
/*  914 */       AbstractReference2DoubleMap.BasicEntry<K> entry = new AbstractReference2DoubleMap.BasicEntry<>();
/*  915 */       if (Reference2DoubleOpenHashMap.this.containsNullKey) {
/*  916 */         entry.key = Reference2DoubleOpenHashMap.this.key[Reference2DoubleOpenHashMap.this.n];
/*  917 */         entry.value = Reference2DoubleOpenHashMap.this.value[Reference2DoubleOpenHashMap.this.n];
/*  918 */         consumer.accept(entry);
/*      */       } 
/*  920 */       for (int pos = Reference2DoubleOpenHashMap.this.n; pos-- != 0;) {
/*  921 */         if (Reference2DoubleOpenHashMap.this.key[pos] != null) {
/*  922 */           entry.key = Reference2DoubleOpenHashMap.this.key[pos];
/*  923 */           entry.value = Reference2DoubleOpenHashMap.this.value[pos];
/*  924 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Reference2DoubleMap.FastEntrySet<K> reference2DoubleEntrySet() {
/*  930 */     if (this.entries == null)
/*  931 */       this.entries = new MapEntrySet(); 
/*  932 */     return this.entries;
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
/*  949 */       return Reference2DoubleOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractReferenceSet<K> { private KeySet() {}
/*      */     
/*      */     public ObjectIterator<K> iterator() {
/*  955 */       return new Reference2DoubleOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/*  960 */       if (Reference2DoubleOpenHashMap.this.containsNullKey)
/*  961 */         consumer.accept(Reference2DoubleOpenHashMap.this.key[Reference2DoubleOpenHashMap.this.n]); 
/*  962 */       for (int pos = Reference2DoubleOpenHashMap.this.n; pos-- != 0; ) {
/*  963 */         K k = Reference2DoubleOpenHashMap.this.key[pos];
/*  964 */         if (k != null)
/*  965 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  970 */       return Reference2DoubleOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(Object k) {
/*  974 */       return Reference2DoubleOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(Object k) {
/*  978 */       int oldSize = Reference2DoubleOpenHashMap.this.size;
/*  979 */       Reference2DoubleOpenHashMap.this.removeDouble(k);
/*  980 */       return (Reference2DoubleOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  984 */       Reference2DoubleOpenHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ReferenceSet<K> keySet() {
/*  989 */     if (this.keys == null)
/*  990 */       this.keys = new KeySet(); 
/*  991 */     return this.keys;
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
/* 1008 */       return Reference2DoubleOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public DoubleCollection values() {
/* 1013 */     if (this.values == null)
/* 1014 */       this.values = (DoubleCollection)new AbstractDoubleCollection()
/*      */         {
/*      */           public DoubleIterator iterator() {
/* 1017 */             return new Reference2DoubleOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1021 */             return Reference2DoubleOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(double v) {
/* 1025 */             return Reference2DoubleOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1029 */             Reference2DoubleOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(DoubleConsumer consumer)
/*      */           {
/* 1034 */             if (Reference2DoubleOpenHashMap.this.containsNullKey)
/* 1035 */               consumer.accept(Reference2DoubleOpenHashMap.this.value[Reference2DoubleOpenHashMap.this.n]); 
/* 1036 */             for (int pos = Reference2DoubleOpenHashMap.this.n; pos-- != 0;) {
/* 1037 */               if (Reference2DoubleOpenHashMap.this.key[pos] != null)
/* 1038 */                 consumer.accept(Reference2DoubleOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1041 */     return this.values;
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
/* 1058 */     return trim(this.size);
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
/* 1082 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1083 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1084 */       return true; 
/*      */     try {
/* 1086 */       rehash(l);
/* 1087 */     } catch (OutOfMemoryError cantDoIt) {
/* 1088 */       return false;
/*      */     } 
/* 1090 */     return true;
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
/* 1106 */     K[] key = this.key;
/* 1107 */     double[] value = this.value;
/* 1108 */     int mask = newN - 1;
/* 1109 */     K[] newKey = (K[])new Object[newN + 1];
/* 1110 */     double[] newValue = new double[newN + 1];
/* 1111 */     int i = this.n;
/* 1112 */     for (int j = realSize(); j-- != 0; ) {
/* 1113 */       while (key[--i] == null); int pos;
/* 1114 */       if (newKey[pos = HashCommon.mix(System.identityHashCode(key[i])) & mask] != null)
/*      */       {
/* 1116 */         while (newKey[pos = pos + 1 & mask] != null); } 
/* 1117 */       newKey[pos] = key[i];
/* 1118 */       newValue[pos] = value[i];
/*      */     } 
/* 1120 */     newValue[newN] = value[this.n];
/* 1121 */     this.n = newN;
/* 1122 */     this.mask = mask;
/* 1123 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1124 */     this.key = newKey;
/* 1125 */     this.value = newValue;
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
/*      */   public Reference2DoubleOpenHashMap<K> clone() {
/*      */     Reference2DoubleOpenHashMap<K> c;
/*      */     try {
/* 1142 */       c = (Reference2DoubleOpenHashMap<K>)super.clone();
/* 1143 */     } catch (CloneNotSupportedException cantHappen) {
/* 1144 */       throw new InternalError();
/*      */     } 
/* 1146 */     c.keys = null;
/* 1147 */     c.values = null;
/* 1148 */     c.entries = null;
/* 1149 */     c.containsNullKey = this.containsNullKey;
/* 1150 */     c.key = (K[])this.key.clone();
/* 1151 */     c.value = (double[])this.value.clone();
/* 1152 */     return c;
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
/* 1165 */     int h = 0;
/* 1166 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1167 */       while (this.key[i] == null)
/* 1168 */         i++; 
/* 1169 */       if (this != this.key[i])
/* 1170 */         t = System.identityHashCode(this.key[i]); 
/* 1171 */       t ^= HashCommon.double2int(this.value[i]);
/* 1172 */       h += t;
/* 1173 */       i++;
/*      */     } 
/*      */     
/* 1176 */     if (this.containsNullKey)
/* 1177 */       h += HashCommon.double2int(this.value[this.n]); 
/* 1178 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1181 */     K[] key = this.key;
/* 1182 */     double[] value = this.value;
/* 1183 */     MapIterator i = new MapIterator();
/* 1184 */     s.defaultWriteObject();
/* 1185 */     for (int j = this.size; j-- != 0; ) {
/* 1186 */       int e = i.nextEntry();
/* 1187 */       s.writeObject(key[e]);
/* 1188 */       s.writeDouble(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1193 */     s.defaultReadObject();
/* 1194 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1195 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1196 */     this.mask = this.n - 1;
/* 1197 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1198 */     double[] value = this.value = new double[this.n + 1];
/*      */ 
/*      */     
/* 1201 */     for (int i = this.size; i-- != 0; ) {
/* 1202 */       int pos; K k = (K)s.readObject();
/* 1203 */       double v = s.readDouble();
/* 1204 */       if (k == null) {
/* 1205 */         pos = this.n;
/* 1206 */         this.containsNullKey = true;
/*      */       } else {
/* 1208 */         pos = HashCommon.mix(System.identityHashCode(k)) & this.mask;
/* 1209 */         while (key[pos] != null)
/* 1210 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1212 */       key[pos] = k;
/* 1213 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Reference2DoubleOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */