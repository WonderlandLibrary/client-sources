/*      */ package it.unimi.dsi.fastutil.doubles;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*      */ import it.unimi.dsi.fastutil.objects.ReferenceCollection;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Double2ReferenceOpenCustomHashMap<V>
/*      */   extends AbstractDouble2ReferenceMap<V>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient double[] key;
/*      */   protected transient V[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected DoubleHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Double2ReferenceMap.FastEntrySet<V> entries;
/*      */   protected transient DoubleSet keys;
/*      */   protected transient ReferenceCollection<V> values;
/*      */   
/*      */   public Double2ReferenceOpenCustomHashMap(int expected, float f, DoubleHash.Strategy strategy) {
/*  106 */     this.strategy = strategy;
/*  107 */     if (f <= 0.0F || f > 1.0F)
/*  108 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  109 */     if (expected < 0)
/*  110 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  111 */     this.f = f;
/*  112 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  113 */     this.mask = this.n - 1;
/*  114 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  115 */     this.key = new double[this.n + 1];
/*  116 */     this.value = (V[])new Object[this.n + 1];
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
/*      */   public Double2ReferenceOpenCustomHashMap(int expected, DoubleHash.Strategy strategy) {
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
/*      */   public Double2ReferenceOpenCustomHashMap(DoubleHash.Strategy strategy) {
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
/*      */   public Double2ReferenceOpenCustomHashMap(Map<? extends Double, ? extends V> m, float f, DoubleHash.Strategy strategy) {
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
/*      */   public Double2ReferenceOpenCustomHashMap(Map<? extends Double, ? extends V> m, DoubleHash.Strategy strategy) {
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
/*      */   public Double2ReferenceOpenCustomHashMap(Double2ReferenceMap<V> m, float f, DoubleHash.Strategy strategy) {
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
/*      */   public Double2ReferenceOpenCustomHashMap(Double2ReferenceMap<V> m, DoubleHash.Strategy strategy) {
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
/*      */   public Double2ReferenceOpenCustomHashMap(double[] k, V[] v, float f, DoubleHash.Strategy strategy) {
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
/*      */   public Double2ReferenceOpenCustomHashMap(double[] k, V[] v, DoubleHash.Strategy strategy) {
/*  235 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleHash.Strategy strategy() {
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
/*      */   private V removeEntry(int pos) {
/*  260 */     V oldValue = this.value[pos];
/*  261 */     this.value[pos] = null;
/*  262 */     this.size--;
/*  263 */     shiftKeys(pos);
/*  264 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  265 */       rehash(this.n / 2); 
/*  266 */     return oldValue;
/*      */   }
/*      */   private V removeNullEntry() {
/*  269 */     this.containsNullKey = false;
/*  270 */     V oldValue = this.value[this.n];
/*  271 */     this.value[this.n] = null;
/*  272 */     this.size--;
/*  273 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  274 */       rehash(this.n / 2); 
/*  275 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Double, ? extends V> m) {
/*  279 */     if (this.f <= 0.5D) {
/*  280 */       ensureCapacity(m.size());
/*      */     } else {
/*  282 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  284 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(double k) {
/*  288 */     if (this.strategy.equals(k, 0.0D)) {
/*  289 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  291 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  294 */     if (Double.doubleToLongBits(
/*  295 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  296 */       return -(pos + 1); 
/*  297 */     if (this.strategy.equals(k, curr)) {
/*  298 */       return pos;
/*      */     }
/*      */     while (true) {
/*  301 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  302 */         return -(pos + 1); 
/*  303 */       if (this.strategy.equals(k, curr))
/*  304 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, double k, V v) {
/*  308 */     if (pos == this.n)
/*  309 */       this.containsNullKey = true; 
/*  310 */     this.key[pos] = k;
/*  311 */     this.value[pos] = v;
/*  312 */     if (this.size++ >= this.maxFill) {
/*  313 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public V put(double k, V v) {
/*  319 */     int pos = find(k);
/*  320 */     if (pos < 0) {
/*  321 */       insert(-pos - 1, k, v);
/*  322 */       return this.defRetValue;
/*      */     } 
/*  324 */     V oldValue = this.value[pos];
/*  325 */     this.value[pos] = v;
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
/*      */   protected final void shiftKeys(int pos) {
/*  339 */     double[] key = this.key; while (true) {
/*      */       double curr; int last;
/*  341 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  343 */         if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/*  344 */           key[last] = 0.0D;
/*  345 */           this.value[last] = null;
/*      */           return;
/*      */         } 
/*  348 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  349 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  351 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  353 */       key[last] = curr;
/*  354 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public V remove(double k) {
/*  360 */     if (this.strategy.equals(k, 0.0D)) {
/*  361 */       if (this.containsNullKey)
/*  362 */         return removeNullEntry(); 
/*  363 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  366 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  369 */     if (Double.doubleToLongBits(
/*  370 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  371 */       return this.defRetValue; 
/*  372 */     if (this.strategy.equals(k, curr))
/*  373 */       return removeEntry(pos); 
/*      */     while (true) {
/*  375 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  376 */         return this.defRetValue; 
/*  377 */       if (this.strategy.equals(k, curr)) {
/*  378 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public V get(double k) {
/*  384 */     if (this.strategy.equals(k, 0.0D)) {
/*  385 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  387 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  390 */     if (Double.doubleToLongBits(
/*  391 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  392 */       return this.defRetValue; 
/*  393 */     if (this.strategy.equals(k, curr)) {
/*  394 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  397 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  398 */         return this.defRetValue; 
/*  399 */       if (this.strategy.equals(k, curr)) {
/*  400 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(double k) {
/*  406 */     if (this.strategy.equals(k, 0.0D)) {
/*  407 */       return this.containsNullKey;
/*      */     }
/*  409 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  412 */     if (Double.doubleToLongBits(
/*  413 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  414 */       return false; 
/*  415 */     if (this.strategy.equals(k, curr)) {
/*  416 */       return true;
/*      */     }
/*      */     while (true) {
/*  419 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  420 */         return false; 
/*  421 */       if (this.strategy.equals(k, curr))
/*  422 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(Object v) {
/*  427 */     V[] value = this.value;
/*  428 */     double[] key = this.key;
/*  429 */     if (this.containsNullKey && value[this.n] == v)
/*  430 */       return true; 
/*  431 */     for (int i = this.n; i-- != 0;) {
/*  432 */       if (Double.doubleToLongBits(key[i]) != 0L && value[i] == v)
/*  433 */         return true; 
/*  434 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V getOrDefault(double k, V defaultValue) {
/*  440 */     if (this.strategy.equals(k, 0.0D)) {
/*  441 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  443 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  446 */     if (Double.doubleToLongBits(
/*  447 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  448 */       return defaultValue; 
/*  449 */     if (this.strategy.equals(k, curr)) {
/*  450 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  453 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  454 */         return defaultValue; 
/*  455 */       if (this.strategy.equals(k, curr)) {
/*  456 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public V putIfAbsent(double k, V v) {
/*  462 */     int pos = find(k);
/*  463 */     if (pos >= 0)
/*  464 */       return this.value[pos]; 
/*  465 */     insert(-pos - 1, k, v);
/*  466 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(double k, Object v) {
/*  472 */     if (this.strategy.equals(k, 0.0D)) {
/*  473 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  474 */         removeNullEntry();
/*  475 */         return true;
/*      */       } 
/*  477 */       return false;
/*      */     } 
/*      */     
/*  480 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  483 */     if (Double.doubleToLongBits(
/*  484 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  485 */       return false; 
/*  486 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  487 */       removeEntry(pos);
/*  488 */       return true;
/*      */     } 
/*      */     while (true) {
/*  491 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  492 */         return false; 
/*  493 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  494 */         removeEntry(pos);
/*  495 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(double k, V oldValue, V v) {
/*  502 */     int pos = find(k);
/*  503 */     if (pos < 0 || oldValue != this.value[pos])
/*  504 */       return false; 
/*  505 */     this.value[pos] = v;
/*  506 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public V replace(double k, V v) {
/*  511 */     int pos = find(k);
/*  512 */     if (pos < 0)
/*  513 */       return this.defRetValue; 
/*  514 */     V oldValue = this.value[pos];
/*  515 */     this.value[pos] = v;
/*  516 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public V computeIfAbsent(double k, DoubleFunction<? extends V> mappingFunction) {
/*  521 */     Objects.requireNonNull(mappingFunction);
/*  522 */     int pos = find(k);
/*  523 */     if (pos >= 0)
/*  524 */       return this.value[pos]; 
/*  525 */     V newValue = mappingFunction.apply(k);
/*  526 */     insert(-pos - 1, k, newValue);
/*  527 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V computeIfPresent(double k, BiFunction<? super Double, ? super V, ? extends V> remappingFunction) {
/*  533 */     Objects.requireNonNull(remappingFunction);
/*  534 */     int pos = find(k);
/*  535 */     if (pos < 0)
/*  536 */       return this.defRetValue; 
/*  537 */     V newValue = remappingFunction.apply(Double.valueOf(k), this.value[pos]);
/*  538 */     if (newValue == null) {
/*  539 */       if (this.strategy.equals(k, 0.0D)) {
/*  540 */         removeNullEntry();
/*      */       } else {
/*  542 */         removeEntry(pos);
/*  543 */       }  return this.defRetValue;
/*      */     } 
/*  545 */     this.value[pos] = newValue; return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V compute(double k, BiFunction<? super Double, ? super V, ? extends V> remappingFunction) {
/*  551 */     Objects.requireNonNull(remappingFunction);
/*  552 */     int pos = find(k);
/*  553 */     V newValue = remappingFunction.apply(Double.valueOf(k), (pos >= 0) ? this.value[pos] : null);
/*  554 */     if (newValue == null) {
/*  555 */       if (pos >= 0)
/*  556 */         if (this.strategy.equals(k, 0.0D)) {
/*  557 */           removeNullEntry();
/*      */         } else {
/*  559 */           removeEntry(pos);
/*      */         }  
/*  561 */       return this.defRetValue;
/*      */     } 
/*  563 */     V newVal = newValue;
/*  564 */     if (pos < 0) {
/*  565 */       insert(-pos - 1, k, newVal);
/*  566 */       return newVal;
/*      */     } 
/*  568 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V merge(double k, V v, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/*  574 */     Objects.requireNonNull(remappingFunction);
/*  575 */     int pos = find(k);
/*  576 */     if (pos < 0 || this.value[pos] == null) {
/*  577 */       if (v == null)
/*  578 */         return this.defRetValue; 
/*  579 */       insert(-pos - 1, k, v);
/*  580 */       return v;
/*      */     } 
/*  582 */     V newValue = remappingFunction.apply(this.value[pos], v);
/*  583 */     if (newValue == null) {
/*  584 */       if (this.strategy.equals(k, 0.0D)) {
/*  585 */         removeNullEntry();
/*      */       } else {
/*  587 */         removeEntry(pos);
/*  588 */       }  return this.defRetValue;
/*      */     } 
/*  590 */     this.value[pos] = newValue; return newValue;
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
/*  605 */     Arrays.fill(this.key, 0.0D);
/*  606 */     Arrays.fill((Object[])this.value, (Object)null);
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
/*      */     implements Double2ReferenceMap.Entry<V>, Map.Entry<Double, V>
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
/*      */     public double getDoubleKey() {
/*  632 */       return Double2ReferenceOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public V getValue() {
/*  636 */       return Double2ReferenceOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public V setValue(V v) {
/*  640 */       V oldValue = Double2ReferenceOpenCustomHashMap.this.value[this.index];
/*  641 */       Double2ReferenceOpenCustomHashMap.this.value[this.index] = v;
/*  642 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double getKey() {
/*  652 */       return Double.valueOf(Double2ReferenceOpenCustomHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  657 */       if (!(o instanceof Map.Entry))
/*  658 */         return false; 
/*  659 */       Map.Entry<Double, V> e = (Map.Entry<Double, V>)o;
/*  660 */       return (Double2ReferenceOpenCustomHashMap.this.strategy.equals(Double2ReferenceOpenCustomHashMap.this.key[this.index], ((Double)e.getKey()).doubleValue()) && Double2ReferenceOpenCustomHashMap.this.value[this.index] == e
/*  661 */         .getValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  665 */       return Double2ReferenceOpenCustomHashMap.this.strategy.hashCode(Double2ReferenceOpenCustomHashMap.this.key[this.index]) ^ (
/*  666 */         (Double2ReferenceOpenCustomHashMap.this.value[this.index] == null) ? 0 : System.identityHashCode(Double2ReferenceOpenCustomHashMap.this.value[this.index]));
/*      */     }
/*      */     
/*      */     public String toString() {
/*  670 */       return Double2ReferenceOpenCustomHashMap.this.key[this.index] + "=>" + Double2ReferenceOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  680 */     int pos = Double2ReferenceOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  687 */     int last = -1;
/*      */     
/*  689 */     int c = Double2ReferenceOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  693 */     boolean mustReturnNullKey = Double2ReferenceOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     DoubleArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  700 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  703 */       if (!hasNext())
/*  704 */         throw new NoSuchElementException(); 
/*  705 */       this.c--;
/*  706 */       if (this.mustReturnNullKey) {
/*  707 */         this.mustReturnNullKey = false;
/*  708 */         return this.last = Double2ReferenceOpenCustomHashMap.this.n;
/*      */       } 
/*  710 */       double[] key = Double2ReferenceOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  712 */         if (--this.pos < 0) {
/*      */           
/*  714 */           this.last = Integer.MIN_VALUE;
/*  715 */           double k = this.wrapped.getDouble(-this.pos - 1);
/*  716 */           int p = HashCommon.mix(Double2ReferenceOpenCustomHashMap.this.strategy.hashCode(k)) & Double2ReferenceOpenCustomHashMap.this.mask;
/*  717 */           while (!Double2ReferenceOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  718 */             p = p + 1 & Double2ReferenceOpenCustomHashMap.this.mask; 
/*  719 */           return p;
/*      */         } 
/*  721 */         if (Double.doubleToLongBits(key[this.pos]) != 0L) {
/*  722 */           return this.last = this.pos;
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
/*  736 */       double[] key = Double2ReferenceOpenCustomHashMap.this.key; while (true) {
/*      */         double curr; int last;
/*  738 */         pos = (last = pos) + 1 & Double2ReferenceOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  740 */           if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/*  741 */             key[last] = 0.0D;
/*  742 */             Double2ReferenceOpenCustomHashMap.this.value[last] = null;
/*      */             return;
/*      */           } 
/*  745 */           int slot = HashCommon.mix(Double2ReferenceOpenCustomHashMap.this.strategy.hashCode(curr)) & Double2ReferenceOpenCustomHashMap.this.mask;
/*  746 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  748 */           pos = pos + 1 & Double2ReferenceOpenCustomHashMap.this.mask;
/*      */         } 
/*  750 */         if (pos < last) {
/*  751 */           if (this.wrapped == null)
/*  752 */             this.wrapped = new DoubleArrayList(2); 
/*  753 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  755 */         key[last] = curr;
/*  756 */         Double2ReferenceOpenCustomHashMap.this.value[last] = Double2ReferenceOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  760 */       if (this.last == -1)
/*  761 */         throw new IllegalStateException(); 
/*  762 */       if (this.last == Double2ReferenceOpenCustomHashMap.this.n) {
/*  763 */         Double2ReferenceOpenCustomHashMap.this.containsNullKey = false;
/*  764 */         Double2ReferenceOpenCustomHashMap.this.value[Double2ReferenceOpenCustomHashMap.this.n] = null;
/*  765 */       } else if (this.pos >= 0) {
/*  766 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  769 */         Double2ReferenceOpenCustomHashMap.this.remove(this.wrapped.getDouble(-this.pos - 1));
/*  770 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  773 */       Double2ReferenceOpenCustomHashMap.this.size--;
/*  774 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  779 */       int i = n;
/*  780 */       while (i-- != 0 && hasNext())
/*  781 */         nextEntry(); 
/*  782 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Double2ReferenceMap.Entry<V>> { private Double2ReferenceOpenCustomHashMap<V>.MapEntry entry;
/*      */     
/*      */     public Double2ReferenceOpenCustomHashMap<V>.MapEntry next() {
/*  789 */       return this.entry = new Double2ReferenceOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  793 */       super.remove();
/*  794 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Double2ReferenceMap.Entry<V>> { private FastEntryIterator() {
/*  798 */       this.entry = new Double2ReferenceOpenCustomHashMap.MapEntry();
/*      */     } private final Double2ReferenceOpenCustomHashMap<V>.MapEntry entry;
/*      */     public Double2ReferenceOpenCustomHashMap<V>.MapEntry next() {
/*  801 */       this.entry.index = nextEntry();
/*  802 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Double2ReferenceMap.Entry<V>> implements Double2ReferenceMap.FastEntrySet<V> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Double2ReferenceMap.Entry<V>> iterator() {
/*  808 */       return new Double2ReferenceOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Double2ReferenceMap.Entry<V>> fastIterator() {
/*  812 */       return new Double2ReferenceOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  817 */       if (!(o instanceof Map.Entry))
/*  818 */         return false; 
/*  819 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  820 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/*  821 */         return false; 
/*  822 */       double k = ((Double)e.getKey()).doubleValue();
/*  823 */       V v = (V)e.getValue();
/*  824 */       if (Double2ReferenceOpenCustomHashMap.this.strategy.equals(k, 0.0D)) {
/*  825 */         return (Double2ReferenceOpenCustomHashMap.this.containsNullKey && Double2ReferenceOpenCustomHashMap.this.value[Double2ReferenceOpenCustomHashMap.this.n] == v);
/*      */       }
/*  827 */       double[] key = Double2ReferenceOpenCustomHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/*  830 */       if (Double.doubleToLongBits(
/*  831 */           curr = key[pos = HashCommon.mix(Double2ReferenceOpenCustomHashMap.this.strategy.hashCode(k)) & Double2ReferenceOpenCustomHashMap.this.mask]) == 0L)
/*  832 */         return false; 
/*  833 */       if (Double2ReferenceOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  834 */         return (Double2ReferenceOpenCustomHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  837 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2ReferenceOpenCustomHashMap.this.mask]) == 0L)
/*  838 */           return false; 
/*  839 */         if (Double2ReferenceOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  840 */           return (Double2ReferenceOpenCustomHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  846 */       if (!(o instanceof Map.Entry))
/*  847 */         return false; 
/*  848 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  849 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/*  850 */         return false; 
/*  851 */       double k = ((Double)e.getKey()).doubleValue();
/*  852 */       V v = (V)e.getValue();
/*  853 */       if (Double2ReferenceOpenCustomHashMap.this.strategy.equals(k, 0.0D)) {
/*  854 */         if (Double2ReferenceOpenCustomHashMap.this.containsNullKey && Double2ReferenceOpenCustomHashMap.this.value[Double2ReferenceOpenCustomHashMap.this.n] == v) {
/*  855 */           Double2ReferenceOpenCustomHashMap.this.removeNullEntry();
/*  856 */           return true;
/*      */         } 
/*  858 */         return false;
/*      */       } 
/*      */       
/*  861 */       double[] key = Double2ReferenceOpenCustomHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/*  864 */       if (Double.doubleToLongBits(
/*  865 */           curr = key[pos = HashCommon.mix(Double2ReferenceOpenCustomHashMap.this.strategy.hashCode(k)) & Double2ReferenceOpenCustomHashMap.this.mask]) == 0L)
/*  866 */         return false; 
/*  867 */       if (Double2ReferenceOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  868 */         if (Double2ReferenceOpenCustomHashMap.this.value[pos] == v) {
/*  869 */           Double2ReferenceOpenCustomHashMap.this.removeEntry(pos);
/*  870 */           return true;
/*      */         } 
/*  872 */         return false;
/*      */       } 
/*      */       while (true) {
/*  875 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2ReferenceOpenCustomHashMap.this.mask]) == 0L)
/*  876 */           return false; 
/*  877 */         if (Double2ReferenceOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  878 */           Double2ReferenceOpenCustomHashMap.this.value[pos] == v) {
/*  879 */           Double2ReferenceOpenCustomHashMap.this.removeEntry(pos);
/*  880 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  887 */       return Double2ReferenceOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  891 */       Double2ReferenceOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Double2ReferenceMap.Entry<V>> consumer) {
/*  896 */       if (Double2ReferenceOpenCustomHashMap.this.containsNullKey)
/*  897 */         consumer.accept(new AbstractDouble2ReferenceMap.BasicEntry<>(Double2ReferenceOpenCustomHashMap.this.key[Double2ReferenceOpenCustomHashMap.this.n], Double2ReferenceOpenCustomHashMap.this.value[Double2ReferenceOpenCustomHashMap.this.n])); 
/*  898 */       for (int pos = Double2ReferenceOpenCustomHashMap.this.n; pos-- != 0;) {
/*  899 */         if (Double.doubleToLongBits(Double2ReferenceOpenCustomHashMap.this.key[pos]) != 0L)
/*  900 */           consumer.accept(new AbstractDouble2ReferenceMap.BasicEntry<>(Double2ReferenceOpenCustomHashMap.this.key[pos], Double2ReferenceOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Double2ReferenceMap.Entry<V>> consumer) {
/*  905 */       AbstractDouble2ReferenceMap.BasicEntry<V> entry = new AbstractDouble2ReferenceMap.BasicEntry<>();
/*  906 */       if (Double2ReferenceOpenCustomHashMap.this.containsNullKey) {
/*  907 */         entry.key = Double2ReferenceOpenCustomHashMap.this.key[Double2ReferenceOpenCustomHashMap.this.n];
/*  908 */         entry.value = Double2ReferenceOpenCustomHashMap.this.value[Double2ReferenceOpenCustomHashMap.this.n];
/*  909 */         consumer.accept(entry);
/*      */       } 
/*  911 */       for (int pos = Double2ReferenceOpenCustomHashMap.this.n; pos-- != 0;) {
/*  912 */         if (Double.doubleToLongBits(Double2ReferenceOpenCustomHashMap.this.key[pos]) != 0L) {
/*  913 */           entry.key = Double2ReferenceOpenCustomHashMap.this.key[pos];
/*  914 */           entry.value = Double2ReferenceOpenCustomHashMap.this.value[pos];
/*  915 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Double2ReferenceMap.FastEntrySet<V> double2ReferenceEntrySet() {
/*  921 */     if (this.entries == null)
/*  922 */       this.entries = new MapEntrySet(); 
/*  923 */     return this.entries;
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
/*  940 */       return Double2ReferenceOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractDoubleSet { private KeySet() {}
/*      */     
/*      */     public DoubleIterator iterator() {
/*  946 */       return new Double2ReferenceOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/*  951 */       if (Double2ReferenceOpenCustomHashMap.this.containsNullKey)
/*  952 */         consumer.accept(Double2ReferenceOpenCustomHashMap.this.key[Double2ReferenceOpenCustomHashMap.this.n]); 
/*  953 */       for (int pos = Double2ReferenceOpenCustomHashMap.this.n; pos-- != 0; ) {
/*  954 */         double k = Double2ReferenceOpenCustomHashMap.this.key[pos];
/*  955 */         if (Double.doubleToLongBits(k) != 0L)
/*  956 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  961 */       return Double2ReferenceOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(double k) {
/*  965 */       return Double2ReferenceOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(double k) {
/*  969 */       int oldSize = Double2ReferenceOpenCustomHashMap.this.size;
/*  970 */       Double2ReferenceOpenCustomHashMap.this.remove(k);
/*  971 */       return (Double2ReferenceOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  975 */       Double2ReferenceOpenCustomHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public DoubleSet keySet() {
/*  980 */     if (this.keys == null)
/*  981 */       this.keys = new KeySet(); 
/*  982 */     return this.keys;
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
/*      */     implements ObjectIterator<V>
/*      */   {
/*      */     public V next() {
/*  999 */       return Double2ReferenceOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public ReferenceCollection<V> values() {
/* 1004 */     if (this.values == null)
/* 1005 */       this.values = (ReferenceCollection<V>)new AbstractReferenceCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/* 1008 */             return new Double2ReferenceOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1012 */             return Double2ReferenceOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(Object v) {
/* 1016 */             return Double2ReferenceOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1020 */             Double2ReferenceOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(Consumer<? super V> consumer)
/*      */           {
/* 1025 */             if (Double2ReferenceOpenCustomHashMap.this.containsNullKey)
/* 1026 */               consumer.accept(Double2ReferenceOpenCustomHashMap.this.value[Double2ReferenceOpenCustomHashMap.this.n]); 
/* 1027 */             for (int pos = Double2ReferenceOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1028 */               if (Double.doubleToLongBits(Double2ReferenceOpenCustomHashMap.this.key[pos]) != 0L)
/* 1029 */                 consumer.accept(Double2ReferenceOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1032 */     return this.values;
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
/* 1049 */     return trim(this.size);
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
/* 1073 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1074 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1075 */       return true; 
/*      */     try {
/* 1077 */       rehash(l);
/* 1078 */     } catch (OutOfMemoryError cantDoIt) {
/* 1079 */       return false;
/*      */     } 
/* 1081 */     return true;
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
/* 1097 */     double[] key = this.key;
/* 1098 */     V[] value = this.value;
/* 1099 */     int mask = newN - 1;
/* 1100 */     double[] newKey = new double[newN + 1];
/* 1101 */     V[] newValue = (V[])new Object[newN + 1];
/* 1102 */     int i = this.n;
/* 1103 */     for (int j = realSize(); j-- != 0; ) {
/* 1104 */       while (Double.doubleToLongBits(key[--i]) == 0L); int pos;
/* 1105 */       if (Double.doubleToLongBits(newKey[
/* 1106 */             pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask]) != 0L)
/* 1107 */         while (Double.doubleToLongBits(newKey[pos = pos + 1 & mask]) != 0L); 
/* 1108 */       newKey[pos] = key[i];
/* 1109 */       newValue[pos] = value[i];
/*      */     } 
/* 1111 */     newValue[newN] = value[this.n];
/* 1112 */     this.n = newN;
/* 1113 */     this.mask = mask;
/* 1114 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1115 */     this.key = newKey;
/* 1116 */     this.value = newValue;
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
/*      */   public Double2ReferenceOpenCustomHashMap<V> clone() {
/*      */     Double2ReferenceOpenCustomHashMap<V> c;
/*      */     try {
/* 1133 */       c = (Double2ReferenceOpenCustomHashMap<V>)super.clone();
/* 1134 */     } catch (CloneNotSupportedException cantHappen) {
/* 1135 */       throw new InternalError();
/*      */     } 
/* 1137 */     c.keys = null;
/* 1138 */     c.values = null;
/* 1139 */     c.entries = null;
/* 1140 */     c.containsNullKey = this.containsNullKey;
/* 1141 */     c.key = (double[])this.key.clone();
/* 1142 */     c.value = (V[])this.value.clone();
/* 1143 */     c.strategy = this.strategy;
/* 1144 */     return c;
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
/* 1157 */     int h = 0;
/* 1158 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1159 */       while (Double.doubleToLongBits(this.key[i]) == 0L)
/* 1160 */         i++; 
/* 1161 */       t = this.strategy.hashCode(this.key[i]);
/* 1162 */       if (this != this.value[i])
/* 1163 */         t ^= (this.value[i] == null) ? 0 : System.identityHashCode(this.value[i]); 
/* 1164 */       h += t;
/* 1165 */       i++;
/*      */     } 
/*      */     
/* 1168 */     if (this.containsNullKey)
/* 1169 */       h += (this.value[this.n] == null) ? 0 : System.identityHashCode(this.value[this.n]); 
/* 1170 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1173 */     double[] key = this.key;
/* 1174 */     V[] value = this.value;
/* 1175 */     MapIterator i = new MapIterator();
/* 1176 */     s.defaultWriteObject();
/* 1177 */     for (int j = this.size; j-- != 0; ) {
/* 1178 */       int e = i.nextEntry();
/* 1179 */       s.writeDouble(key[e]);
/* 1180 */       s.writeObject(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1185 */     s.defaultReadObject();
/* 1186 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1187 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1188 */     this.mask = this.n - 1;
/* 1189 */     double[] key = this.key = new double[this.n + 1];
/* 1190 */     V[] value = this.value = (V[])new Object[this.n + 1];
/*      */ 
/*      */     
/* 1193 */     for (int i = this.size; i-- != 0; ) {
/* 1194 */       int pos; double k = s.readDouble();
/* 1195 */       V v = (V)s.readObject();
/* 1196 */       if (this.strategy.equals(k, 0.0D)) {
/* 1197 */         pos = this.n;
/* 1198 */         this.containsNullKey = true;
/*      */       } else {
/* 1200 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1201 */         while (Double.doubleToLongBits(key[pos]) != 0L)
/* 1202 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1204 */       key[pos] = k;
/* 1205 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2ReferenceOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */