/*      */ package it.unimi.dsi.fastutil.doubles;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectCollection;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Double2ObjectOpenCustomHashMap<V>
/*      */   extends AbstractDouble2ObjectMap<V>
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
/*      */   protected transient Double2ObjectMap.FastEntrySet<V> entries;
/*      */   protected transient DoubleSet keys;
/*      */   protected transient ObjectCollection<V> values;
/*      */   
/*      */   public Double2ObjectOpenCustomHashMap(int expected, float f, DoubleHash.Strategy strategy) {
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
/*      */   public Double2ObjectOpenCustomHashMap(int expected, DoubleHash.Strategy strategy) {
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
/*      */   public Double2ObjectOpenCustomHashMap(DoubleHash.Strategy strategy) {
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
/*      */   public Double2ObjectOpenCustomHashMap(Map<? extends Double, ? extends V> m, float f, DoubleHash.Strategy strategy) {
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
/*      */   public Double2ObjectOpenCustomHashMap(Map<? extends Double, ? extends V> m, DoubleHash.Strategy strategy) {
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
/*      */   public Double2ObjectOpenCustomHashMap(Double2ObjectMap<V> m, float f, DoubleHash.Strategy strategy) {
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
/*      */   public Double2ObjectOpenCustomHashMap(Double2ObjectMap<V> m, DoubleHash.Strategy strategy) {
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
/*      */   public Double2ObjectOpenCustomHashMap(double[] k, V[] v, float f, DoubleHash.Strategy strategy) {
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
/*      */   public Double2ObjectOpenCustomHashMap(double[] k, V[] v, DoubleHash.Strategy strategy) {
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
/*  429 */     if (this.containsNullKey && Objects.equals(value[this.n], v))
/*  430 */       return true; 
/*  431 */     for (int i = this.n; i-- != 0;) {
/*  432 */       if (Double.doubleToLongBits(key[i]) != 0L && Objects.equals(value[i], v))
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
/*  473 */       if (this.containsNullKey && Objects.equals(v, this.value[this.n])) {
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
/*  486 */     if (this.strategy.equals(k, curr) && Objects.equals(v, this.value[pos])) {
/*  487 */       removeEntry(pos);
/*  488 */       return true;
/*      */     } 
/*      */     while (true) {
/*  491 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  492 */         return false; 
/*  493 */       if (this.strategy.equals(k, curr) && Objects.equals(v, this.value[pos])) {
/*  494 */         removeEntry(pos);
/*  495 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(double k, V oldValue, V v) {
/*  502 */     int pos = find(k);
/*  503 */     if (pos < 0 || !Objects.equals(oldValue, this.value[pos]))
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
/*      */     implements Double2ObjectMap.Entry<V>, Map.Entry<Double, V>
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
/*  632 */       return Double2ObjectOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public V getValue() {
/*  636 */       return Double2ObjectOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public V setValue(V v) {
/*  640 */       V oldValue = Double2ObjectOpenCustomHashMap.this.value[this.index];
/*  641 */       Double2ObjectOpenCustomHashMap.this.value[this.index] = v;
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
/*  652 */       return Double.valueOf(Double2ObjectOpenCustomHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  657 */       if (!(o instanceof Map.Entry))
/*  658 */         return false; 
/*  659 */       Map.Entry<Double, V> e = (Map.Entry<Double, V>)o;
/*  660 */       return (Double2ObjectOpenCustomHashMap.this.strategy.equals(Double2ObjectOpenCustomHashMap.this.key[this.index], ((Double)e.getKey()).doubleValue()) && 
/*  661 */         Objects.equals(Double2ObjectOpenCustomHashMap.this.value[this.index], e.getValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  665 */       return Double2ObjectOpenCustomHashMap.this.strategy.hashCode(Double2ObjectOpenCustomHashMap.this.key[this.index]) ^ ((Double2ObjectOpenCustomHashMap.this.value[this.index] == null) ? 0 : Double2ObjectOpenCustomHashMap.this.value[this.index].hashCode());
/*      */     }
/*      */     
/*      */     public String toString() {
/*  669 */       return Double2ObjectOpenCustomHashMap.this.key[this.index] + "=>" + Double2ObjectOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  679 */     int pos = Double2ObjectOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  686 */     int last = -1;
/*      */     
/*  688 */     int c = Double2ObjectOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  692 */     boolean mustReturnNullKey = Double2ObjectOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     DoubleArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  699 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  702 */       if (!hasNext())
/*  703 */         throw new NoSuchElementException(); 
/*  704 */       this.c--;
/*  705 */       if (this.mustReturnNullKey) {
/*  706 */         this.mustReturnNullKey = false;
/*  707 */         return this.last = Double2ObjectOpenCustomHashMap.this.n;
/*      */       } 
/*  709 */       double[] key = Double2ObjectOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  711 */         if (--this.pos < 0) {
/*      */           
/*  713 */           this.last = Integer.MIN_VALUE;
/*  714 */           double k = this.wrapped.getDouble(-this.pos - 1);
/*  715 */           int p = HashCommon.mix(Double2ObjectOpenCustomHashMap.this.strategy.hashCode(k)) & Double2ObjectOpenCustomHashMap.this.mask;
/*  716 */           while (!Double2ObjectOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  717 */             p = p + 1 & Double2ObjectOpenCustomHashMap.this.mask; 
/*  718 */           return p;
/*      */         } 
/*  720 */         if (Double.doubleToLongBits(key[this.pos]) != 0L) {
/*  721 */           return this.last = this.pos;
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
/*  735 */       double[] key = Double2ObjectOpenCustomHashMap.this.key; while (true) {
/*      */         double curr; int last;
/*  737 */         pos = (last = pos) + 1 & Double2ObjectOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  739 */           if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/*  740 */             key[last] = 0.0D;
/*  741 */             Double2ObjectOpenCustomHashMap.this.value[last] = null;
/*      */             return;
/*      */           } 
/*  744 */           int slot = HashCommon.mix(Double2ObjectOpenCustomHashMap.this.strategy.hashCode(curr)) & Double2ObjectOpenCustomHashMap.this.mask;
/*  745 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  747 */           pos = pos + 1 & Double2ObjectOpenCustomHashMap.this.mask;
/*      */         } 
/*  749 */         if (pos < last) {
/*  750 */           if (this.wrapped == null)
/*  751 */             this.wrapped = new DoubleArrayList(2); 
/*  752 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  754 */         key[last] = curr;
/*  755 */         Double2ObjectOpenCustomHashMap.this.value[last] = Double2ObjectOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  759 */       if (this.last == -1)
/*  760 */         throw new IllegalStateException(); 
/*  761 */       if (this.last == Double2ObjectOpenCustomHashMap.this.n) {
/*  762 */         Double2ObjectOpenCustomHashMap.this.containsNullKey = false;
/*  763 */         Double2ObjectOpenCustomHashMap.this.value[Double2ObjectOpenCustomHashMap.this.n] = null;
/*  764 */       } else if (this.pos >= 0) {
/*  765 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  768 */         Double2ObjectOpenCustomHashMap.this.remove(this.wrapped.getDouble(-this.pos - 1));
/*  769 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  772 */       Double2ObjectOpenCustomHashMap.this.size--;
/*  773 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  778 */       int i = n;
/*  779 */       while (i-- != 0 && hasNext())
/*  780 */         nextEntry(); 
/*  781 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Double2ObjectMap.Entry<V>> { private Double2ObjectOpenCustomHashMap<V>.MapEntry entry;
/*      */     
/*      */     public Double2ObjectOpenCustomHashMap<V>.MapEntry next() {
/*  788 */       return this.entry = new Double2ObjectOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  792 */       super.remove();
/*  793 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Double2ObjectMap.Entry<V>> { private FastEntryIterator() {
/*  797 */       this.entry = new Double2ObjectOpenCustomHashMap.MapEntry();
/*      */     } private final Double2ObjectOpenCustomHashMap<V>.MapEntry entry;
/*      */     public Double2ObjectOpenCustomHashMap<V>.MapEntry next() {
/*  800 */       this.entry.index = nextEntry();
/*  801 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Double2ObjectMap.Entry<V>> implements Double2ObjectMap.FastEntrySet<V> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Double2ObjectMap.Entry<V>> iterator() {
/*  807 */       return new Double2ObjectOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Double2ObjectMap.Entry<V>> fastIterator() {
/*  811 */       return new Double2ObjectOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  816 */       if (!(o instanceof Map.Entry))
/*  817 */         return false; 
/*  818 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  819 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/*  820 */         return false; 
/*  821 */       double k = ((Double)e.getKey()).doubleValue();
/*  822 */       V v = (V)e.getValue();
/*  823 */       if (Double2ObjectOpenCustomHashMap.this.strategy.equals(k, 0.0D)) {
/*  824 */         return (Double2ObjectOpenCustomHashMap.this.containsNullKey && Objects.equals(Double2ObjectOpenCustomHashMap.this.value[Double2ObjectOpenCustomHashMap.this.n], v));
/*      */       }
/*  826 */       double[] key = Double2ObjectOpenCustomHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/*  829 */       if (Double.doubleToLongBits(
/*  830 */           curr = key[pos = HashCommon.mix(Double2ObjectOpenCustomHashMap.this.strategy.hashCode(k)) & Double2ObjectOpenCustomHashMap.this.mask]) == 0L)
/*  831 */         return false; 
/*  832 */       if (Double2ObjectOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  833 */         return Objects.equals(Double2ObjectOpenCustomHashMap.this.value[pos], v);
/*      */       }
/*      */       while (true) {
/*  836 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2ObjectOpenCustomHashMap.this.mask]) == 0L)
/*  837 */           return false; 
/*  838 */         if (Double2ObjectOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  839 */           return Objects.equals(Double2ObjectOpenCustomHashMap.this.value[pos], v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  845 */       if (!(o instanceof Map.Entry))
/*  846 */         return false; 
/*  847 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  848 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/*  849 */         return false; 
/*  850 */       double k = ((Double)e.getKey()).doubleValue();
/*  851 */       V v = (V)e.getValue();
/*  852 */       if (Double2ObjectOpenCustomHashMap.this.strategy.equals(k, 0.0D)) {
/*  853 */         if (Double2ObjectOpenCustomHashMap.this.containsNullKey && Objects.equals(Double2ObjectOpenCustomHashMap.this.value[Double2ObjectOpenCustomHashMap.this.n], v)) {
/*  854 */           Double2ObjectOpenCustomHashMap.this.removeNullEntry();
/*  855 */           return true;
/*      */         } 
/*  857 */         return false;
/*      */       } 
/*      */       
/*  860 */       double[] key = Double2ObjectOpenCustomHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/*  863 */       if (Double.doubleToLongBits(
/*  864 */           curr = key[pos = HashCommon.mix(Double2ObjectOpenCustomHashMap.this.strategy.hashCode(k)) & Double2ObjectOpenCustomHashMap.this.mask]) == 0L)
/*  865 */         return false; 
/*  866 */       if (Double2ObjectOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  867 */         if (Objects.equals(Double2ObjectOpenCustomHashMap.this.value[pos], v)) {
/*  868 */           Double2ObjectOpenCustomHashMap.this.removeEntry(pos);
/*  869 */           return true;
/*      */         } 
/*  871 */         return false;
/*      */       } 
/*      */       while (true) {
/*  874 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2ObjectOpenCustomHashMap.this.mask]) == 0L)
/*  875 */           return false; 
/*  876 */         if (Double2ObjectOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  877 */           Objects.equals(Double2ObjectOpenCustomHashMap.this.value[pos], v)) {
/*  878 */           Double2ObjectOpenCustomHashMap.this.removeEntry(pos);
/*  879 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  886 */       return Double2ObjectOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  890 */       Double2ObjectOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Double2ObjectMap.Entry<V>> consumer) {
/*  895 */       if (Double2ObjectOpenCustomHashMap.this.containsNullKey)
/*  896 */         consumer.accept(new AbstractDouble2ObjectMap.BasicEntry<>(Double2ObjectOpenCustomHashMap.this.key[Double2ObjectOpenCustomHashMap.this.n], Double2ObjectOpenCustomHashMap.this.value[Double2ObjectOpenCustomHashMap.this.n])); 
/*  897 */       for (int pos = Double2ObjectOpenCustomHashMap.this.n; pos-- != 0;) {
/*  898 */         if (Double.doubleToLongBits(Double2ObjectOpenCustomHashMap.this.key[pos]) != 0L)
/*  899 */           consumer.accept(new AbstractDouble2ObjectMap.BasicEntry<>(Double2ObjectOpenCustomHashMap.this.key[pos], Double2ObjectOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Double2ObjectMap.Entry<V>> consumer) {
/*  904 */       AbstractDouble2ObjectMap.BasicEntry<V> entry = new AbstractDouble2ObjectMap.BasicEntry<>();
/*  905 */       if (Double2ObjectOpenCustomHashMap.this.containsNullKey) {
/*  906 */         entry.key = Double2ObjectOpenCustomHashMap.this.key[Double2ObjectOpenCustomHashMap.this.n];
/*  907 */         entry.value = Double2ObjectOpenCustomHashMap.this.value[Double2ObjectOpenCustomHashMap.this.n];
/*  908 */         consumer.accept(entry);
/*      */       } 
/*  910 */       for (int pos = Double2ObjectOpenCustomHashMap.this.n; pos-- != 0;) {
/*  911 */         if (Double.doubleToLongBits(Double2ObjectOpenCustomHashMap.this.key[pos]) != 0L) {
/*  912 */           entry.key = Double2ObjectOpenCustomHashMap.this.key[pos];
/*  913 */           entry.value = Double2ObjectOpenCustomHashMap.this.value[pos];
/*  914 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Double2ObjectMap.FastEntrySet<V> double2ObjectEntrySet() {
/*  920 */     if (this.entries == null)
/*  921 */       this.entries = new MapEntrySet(); 
/*  922 */     return this.entries;
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
/*  939 */       return Double2ObjectOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractDoubleSet { private KeySet() {}
/*      */     
/*      */     public DoubleIterator iterator() {
/*  945 */       return new Double2ObjectOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/*  950 */       if (Double2ObjectOpenCustomHashMap.this.containsNullKey)
/*  951 */         consumer.accept(Double2ObjectOpenCustomHashMap.this.key[Double2ObjectOpenCustomHashMap.this.n]); 
/*  952 */       for (int pos = Double2ObjectOpenCustomHashMap.this.n; pos-- != 0; ) {
/*  953 */         double k = Double2ObjectOpenCustomHashMap.this.key[pos];
/*  954 */         if (Double.doubleToLongBits(k) != 0L)
/*  955 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  960 */       return Double2ObjectOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(double k) {
/*  964 */       return Double2ObjectOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(double k) {
/*  968 */       int oldSize = Double2ObjectOpenCustomHashMap.this.size;
/*  969 */       Double2ObjectOpenCustomHashMap.this.remove(k);
/*  970 */       return (Double2ObjectOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  974 */       Double2ObjectOpenCustomHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public DoubleSet keySet() {
/*  979 */     if (this.keys == null)
/*  980 */       this.keys = new KeySet(); 
/*  981 */     return this.keys;
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
/*  998 */       return Double2ObjectOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectCollection<V> values() {
/* 1003 */     if (this.values == null)
/* 1004 */       this.values = (ObjectCollection<V>)new AbstractObjectCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/* 1007 */             return new Double2ObjectOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1011 */             return Double2ObjectOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(Object v) {
/* 1015 */             return Double2ObjectOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1019 */             Double2ObjectOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(Consumer<? super V> consumer)
/*      */           {
/* 1024 */             if (Double2ObjectOpenCustomHashMap.this.containsNullKey)
/* 1025 */               consumer.accept(Double2ObjectOpenCustomHashMap.this.value[Double2ObjectOpenCustomHashMap.this.n]); 
/* 1026 */             for (int pos = Double2ObjectOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1027 */               if (Double.doubleToLongBits(Double2ObjectOpenCustomHashMap.this.key[pos]) != 0L)
/* 1028 */                 consumer.accept(Double2ObjectOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1031 */     return this.values;
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
/* 1048 */     return trim(this.size);
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
/* 1072 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1073 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1074 */       return true; 
/*      */     try {
/* 1076 */       rehash(l);
/* 1077 */     } catch (OutOfMemoryError cantDoIt) {
/* 1078 */       return false;
/*      */     } 
/* 1080 */     return true;
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
/* 1096 */     double[] key = this.key;
/* 1097 */     V[] value = this.value;
/* 1098 */     int mask = newN - 1;
/* 1099 */     double[] newKey = new double[newN + 1];
/* 1100 */     V[] newValue = (V[])new Object[newN + 1];
/* 1101 */     int i = this.n;
/* 1102 */     for (int j = realSize(); j-- != 0; ) {
/* 1103 */       while (Double.doubleToLongBits(key[--i]) == 0L); int pos;
/* 1104 */       if (Double.doubleToLongBits(newKey[
/* 1105 */             pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask]) != 0L)
/* 1106 */         while (Double.doubleToLongBits(newKey[pos = pos + 1 & mask]) != 0L); 
/* 1107 */       newKey[pos] = key[i];
/* 1108 */       newValue[pos] = value[i];
/*      */     } 
/* 1110 */     newValue[newN] = value[this.n];
/* 1111 */     this.n = newN;
/* 1112 */     this.mask = mask;
/* 1113 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1114 */     this.key = newKey;
/* 1115 */     this.value = newValue;
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
/*      */   public Double2ObjectOpenCustomHashMap<V> clone() {
/*      */     Double2ObjectOpenCustomHashMap<V> c;
/*      */     try {
/* 1132 */       c = (Double2ObjectOpenCustomHashMap<V>)super.clone();
/* 1133 */     } catch (CloneNotSupportedException cantHappen) {
/* 1134 */       throw new InternalError();
/*      */     } 
/* 1136 */     c.keys = null;
/* 1137 */     c.values = null;
/* 1138 */     c.entries = null;
/* 1139 */     c.containsNullKey = this.containsNullKey;
/* 1140 */     c.key = (double[])this.key.clone();
/* 1141 */     c.value = (V[])this.value.clone();
/* 1142 */     c.strategy = this.strategy;
/* 1143 */     return c;
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
/* 1156 */     int h = 0;
/* 1157 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1158 */       while (Double.doubleToLongBits(this.key[i]) == 0L)
/* 1159 */         i++; 
/* 1160 */       t = this.strategy.hashCode(this.key[i]);
/* 1161 */       if (this != this.value[i])
/* 1162 */         t ^= (this.value[i] == null) ? 0 : this.value[i].hashCode(); 
/* 1163 */       h += t;
/* 1164 */       i++;
/*      */     } 
/*      */     
/* 1167 */     if (this.containsNullKey)
/* 1168 */       h += (this.value[this.n] == null) ? 0 : this.value[this.n].hashCode(); 
/* 1169 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1172 */     double[] key = this.key;
/* 1173 */     V[] value = this.value;
/* 1174 */     MapIterator i = new MapIterator();
/* 1175 */     s.defaultWriteObject();
/* 1176 */     for (int j = this.size; j-- != 0; ) {
/* 1177 */       int e = i.nextEntry();
/* 1178 */       s.writeDouble(key[e]);
/* 1179 */       s.writeObject(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1184 */     s.defaultReadObject();
/* 1185 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1186 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1187 */     this.mask = this.n - 1;
/* 1188 */     double[] key = this.key = new double[this.n + 1];
/* 1189 */     V[] value = this.value = (V[])new Object[this.n + 1];
/*      */ 
/*      */     
/* 1192 */     for (int i = this.size; i-- != 0; ) {
/* 1193 */       int pos; double k = s.readDouble();
/* 1194 */       V v = (V)s.readObject();
/* 1195 */       if (this.strategy.equals(k, 0.0D)) {
/* 1196 */         pos = this.n;
/* 1197 */         this.containsNullKey = true;
/*      */       } else {
/* 1199 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1200 */         while (Double.doubleToLongBits(key[pos]) != 0L)
/* 1201 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1203 */       key[pos] = k;
/* 1204 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2ObjectOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */