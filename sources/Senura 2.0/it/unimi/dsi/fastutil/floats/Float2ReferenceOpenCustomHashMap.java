/*      */ package it.unimi.dsi.fastutil.floats;
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
/*      */ public class Float2ReferenceOpenCustomHashMap<V>
/*      */   extends AbstractFloat2ReferenceMap<V>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient float[] key;
/*      */   protected transient V[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected FloatHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Float2ReferenceMap.FastEntrySet<V> entries;
/*      */   protected transient FloatSet keys;
/*      */   protected transient ReferenceCollection<V> values;
/*      */   
/*      */   public Float2ReferenceOpenCustomHashMap(int expected, float f, FloatHash.Strategy strategy) {
/*  106 */     this.strategy = strategy;
/*  107 */     if (f <= 0.0F || f > 1.0F)
/*  108 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  109 */     if (expected < 0)
/*  110 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  111 */     this.f = f;
/*  112 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  113 */     this.mask = this.n - 1;
/*  114 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  115 */     this.key = new float[this.n + 1];
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
/*      */   public Float2ReferenceOpenCustomHashMap(int expected, FloatHash.Strategy strategy) {
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
/*      */   public Float2ReferenceOpenCustomHashMap(FloatHash.Strategy strategy) {
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
/*      */   public Float2ReferenceOpenCustomHashMap(Map<? extends Float, ? extends V> m, float f, FloatHash.Strategy strategy) {
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
/*      */   public Float2ReferenceOpenCustomHashMap(Map<? extends Float, ? extends V> m, FloatHash.Strategy strategy) {
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
/*      */   public Float2ReferenceOpenCustomHashMap(Float2ReferenceMap<V> m, float f, FloatHash.Strategy strategy) {
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
/*      */   public Float2ReferenceOpenCustomHashMap(Float2ReferenceMap<V> m, FloatHash.Strategy strategy) {
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
/*      */   public Float2ReferenceOpenCustomHashMap(float[] k, V[] v, float f, FloatHash.Strategy strategy) {
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
/*      */   public Float2ReferenceOpenCustomHashMap(float[] k, V[] v, FloatHash.Strategy strategy) {
/*  235 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FloatHash.Strategy strategy() {
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
/*      */   public void putAll(Map<? extends Float, ? extends V> m) {
/*  279 */     if (this.f <= 0.5D) {
/*  280 */       ensureCapacity(m.size());
/*      */     } else {
/*  282 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  284 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(float k) {
/*  288 */     if (this.strategy.equals(k, 0.0F)) {
/*  289 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  291 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  294 */     if (Float.floatToIntBits(
/*  295 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  296 */       return -(pos + 1); 
/*  297 */     if (this.strategy.equals(k, curr)) {
/*  298 */       return pos;
/*      */     }
/*      */     while (true) {
/*  301 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  302 */         return -(pos + 1); 
/*  303 */       if (this.strategy.equals(k, curr))
/*  304 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, float k, V v) {
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
/*      */   public V put(float k, V v) {
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
/*  339 */     float[] key = this.key; while (true) {
/*      */       float curr; int last;
/*  341 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  343 */         if (Float.floatToIntBits(curr = key[pos]) == 0) {
/*  344 */           key[last] = 0.0F;
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
/*      */   public V remove(float k) {
/*  360 */     if (this.strategy.equals(k, 0.0F)) {
/*  361 */       if (this.containsNullKey)
/*  362 */         return removeNullEntry(); 
/*  363 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  366 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  369 */     if (Float.floatToIntBits(
/*  370 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  371 */       return this.defRetValue; 
/*  372 */     if (this.strategy.equals(k, curr))
/*  373 */       return removeEntry(pos); 
/*      */     while (true) {
/*  375 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  376 */         return this.defRetValue; 
/*  377 */       if (this.strategy.equals(k, curr)) {
/*  378 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public V get(float k) {
/*  384 */     if (this.strategy.equals(k, 0.0F)) {
/*  385 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  387 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  390 */     if (Float.floatToIntBits(
/*  391 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  392 */       return this.defRetValue; 
/*  393 */     if (this.strategy.equals(k, curr)) {
/*  394 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  397 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  398 */         return this.defRetValue; 
/*  399 */       if (this.strategy.equals(k, curr)) {
/*  400 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(float k) {
/*  406 */     if (this.strategy.equals(k, 0.0F)) {
/*  407 */       return this.containsNullKey;
/*      */     }
/*  409 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  412 */     if (Float.floatToIntBits(
/*  413 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  414 */       return false; 
/*  415 */     if (this.strategy.equals(k, curr)) {
/*  416 */       return true;
/*      */     }
/*      */     while (true) {
/*  419 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  420 */         return false; 
/*  421 */       if (this.strategy.equals(k, curr))
/*  422 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(Object v) {
/*  427 */     V[] value = this.value;
/*  428 */     float[] key = this.key;
/*  429 */     if (this.containsNullKey && value[this.n] == v)
/*  430 */       return true; 
/*  431 */     for (int i = this.n; i-- != 0;) {
/*  432 */       if (Float.floatToIntBits(key[i]) != 0 && value[i] == v)
/*  433 */         return true; 
/*  434 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V getOrDefault(float k, V defaultValue) {
/*  440 */     if (this.strategy.equals(k, 0.0F)) {
/*  441 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  443 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  446 */     if (Float.floatToIntBits(
/*  447 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  448 */       return defaultValue; 
/*  449 */     if (this.strategy.equals(k, curr)) {
/*  450 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  453 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  454 */         return defaultValue; 
/*  455 */       if (this.strategy.equals(k, curr)) {
/*  456 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public V putIfAbsent(float k, V v) {
/*  462 */     int pos = find(k);
/*  463 */     if (pos >= 0)
/*  464 */       return this.value[pos]; 
/*  465 */     insert(-pos - 1, k, v);
/*  466 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(float k, Object v) {
/*  472 */     if (this.strategy.equals(k, 0.0F)) {
/*  473 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  474 */         removeNullEntry();
/*  475 */         return true;
/*      */       } 
/*  477 */       return false;
/*      */     } 
/*      */     
/*  480 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  483 */     if (Float.floatToIntBits(
/*  484 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  485 */       return false; 
/*  486 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  487 */       removeEntry(pos);
/*  488 */       return true;
/*      */     } 
/*      */     while (true) {
/*  491 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  492 */         return false; 
/*  493 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  494 */         removeEntry(pos);
/*  495 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(float k, V oldValue, V v) {
/*  502 */     int pos = find(k);
/*  503 */     if (pos < 0 || oldValue != this.value[pos])
/*  504 */       return false; 
/*  505 */     this.value[pos] = v;
/*  506 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public V replace(float k, V v) {
/*  511 */     int pos = find(k);
/*  512 */     if (pos < 0)
/*  513 */       return this.defRetValue; 
/*  514 */     V oldValue = this.value[pos];
/*  515 */     this.value[pos] = v;
/*  516 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public V computeIfAbsent(float k, DoubleFunction<? extends V> mappingFunction) {
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
/*      */   public V computeIfPresent(float k, BiFunction<? super Float, ? super V, ? extends V> remappingFunction) {
/*  533 */     Objects.requireNonNull(remappingFunction);
/*  534 */     int pos = find(k);
/*  535 */     if (pos < 0)
/*  536 */       return this.defRetValue; 
/*  537 */     V newValue = remappingFunction.apply(Float.valueOf(k), this.value[pos]);
/*  538 */     if (newValue == null) {
/*  539 */       if (this.strategy.equals(k, 0.0F)) {
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
/*      */   public V compute(float k, BiFunction<? super Float, ? super V, ? extends V> remappingFunction) {
/*  551 */     Objects.requireNonNull(remappingFunction);
/*  552 */     int pos = find(k);
/*  553 */     V newValue = remappingFunction.apply(Float.valueOf(k), (pos >= 0) ? this.value[pos] : null);
/*  554 */     if (newValue == null) {
/*  555 */       if (pos >= 0)
/*  556 */         if (this.strategy.equals(k, 0.0F)) {
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
/*      */   public V merge(float k, V v, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
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
/*  584 */       if (this.strategy.equals(k, 0.0F)) {
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
/*  605 */     Arrays.fill(this.key, 0.0F);
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
/*      */     implements Float2ReferenceMap.Entry<V>, Map.Entry<Float, V>
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
/*      */     public float getFloatKey() {
/*  632 */       return Float2ReferenceOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public V getValue() {
/*  636 */       return Float2ReferenceOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public V setValue(V v) {
/*  640 */       V oldValue = Float2ReferenceOpenCustomHashMap.this.value[this.index];
/*  641 */       Float2ReferenceOpenCustomHashMap.this.value[this.index] = v;
/*  642 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float getKey() {
/*  652 */       return Float.valueOf(Float2ReferenceOpenCustomHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  657 */       if (!(o instanceof Map.Entry))
/*  658 */         return false; 
/*  659 */       Map.Entry<Float, V> e = (Map.Entry<Float, V>)o;
/*  660 */       return (Float2ReferenceOpenCustomHashMap.this.strategy.equals(Float2ReferenceOpenCustomHashMap.this.key[this.index], ((Float)e.getKey()).floatValue()) && Float2ReferenceOpenCustomHashMap.this.value[this.index] == e.getValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  664 */       return Float2ReferenceOpenCustomHashMap.this.strategy.hashCode(Float2ReferenceOpenCustomHashMap.this.key[this.index]) ^ (
/*  665 */         (Float2ReferenceOpenCustomHashMap.this.value[this.index] == null) ? 0 : System.identityHashCode(Float2ReferenceOpenCustomHashMap.this.value[this.index]));
/*      */     }
/*      */     
/*      */     public String toString() {
/*  669 */       return Float2ReferenceOpenCustomHashMap.this.key[this.index] + "=>" + Float2ReferenceOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  679 */     int pos = Float2ReferenceOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  686 */     int last = -1;
/*      */     
/*  688 */     int c = Float2ReferenceOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  692 */     boolean mustReturnNullKey = Float2ReferenceOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     FloatArrayList wrapped;
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
/*  707 */         return this.last = Float2ReferenceOpenCustomHashMap.this.n;
/*      */       } 
/*  709 */       float[] key = Float2ReferenceOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  711 */         if (--this.pos < 0) {
/*      */           
/*  713 */           this.last = Integer.MIN_VALUE;
/*  714 */           float k = this.wrapped.getFloat(-this.pos - 1);
/*  715 */           int p = HashCommon.mix(Float2ReferenceOpenCustomHashMap.this.strategy.hashCode(k)) & Float2ReferenceOpenCustomHashMap.this.mask;
/*  716 */           while (!Float2ReferenceOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  717 */             p = p + 1 & Float2ReferenceOpenCustomHashMap.this.mask; 
/*  718 */           return p;
/*      */         } 
/*  720 */         if (Float.floatToIntBits(key[this.pos]) != 0) {
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
/*  735 */       float[] key = Float2ReferenceOpenCustomHashMap.this.key; while (true) {
/*      */         float curr; int last;
/*  737 */         pos = (last = pos) + 1 & Float2ReferenceOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  739 */           if (Float.floatToIntBits(curr = key[pos]) == 0) {
/*  740 */             key[last] = 0.0F;
/*  741 */             Float2ReferenceOpenCustomHashMap.this.value[last] = null;
/*      */             return;
/*      */           } 
/*  744 */           int slot = HashCommon.mix(Float2ReferenceOpenCustomHashMap.this.strategy.hashCode(curr)) & Float2ReferenceOpenCustomHashMap.this.mask;
/*  745 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  747 */           pos = pos + 1 & Float2ReferenceOpenCustomHashMap.this.mask;
/*      */         } 
/*  749 */         if (pos < last) {
/*  750 */           if (this.wrapped == null)
/*  751 */             this.wrapped = new FloatArrayList(2); 
/*  752 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  754 */         key[last] = curr;
/*  755 */         Float2ReferenceOpenCustomHashMap.this.value[last] = Float2ReferenceOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  759 */       if (this.last == -1)
/*  760 */         throw new IllegalStateException(); 
/*  761 */       if (this.last == Float2ReferenceOpenCustomHashMap.this.n) {
/*  762 */         Float2ReferenceOpenCustomHashMap.this.containsNullKey = false;
/*  763 */         Float2ReferenceOpenCustomHashMap.this.value[Float2ReferenceOpenCustomHashMap.this.n] = null;
/*  764 */       } else if (this.pos >= 0) {
/*  765 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  768 */         Float2ReferenceOpenCustomHashMap.this.remove(this.wrapped.getFloat(-this.pos - 1));
/*  769 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  772 */       Float2ReferenceOpenCustomHashMap.this.size--;
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
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Float2ReferenceMap.Entry<V>> { private Float2ReferenceOpenCustomHashMap<V>.MapEntry entry;
/*      */     
/*      */     public Float2ReferenceOpenCustomHashMap<V>.MapEntry next() {
/*  788 */       return this.entry = new Float2ReferenceOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  792 */       super.remove();
/*  793 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Float2ReferenceMap.Entry<V>> { private FastEntryIterator() {
/*  797 */       this.entry = new Float2ReferenceOpenCustomHashMap.MapEntry();
/*      */     } private final Float2ReferenceOpenCustomHashMap<V>.MapEntry entry;
/*      */     public Float2ReferenceOpenCustomHashMap<V>.MapEntry next() {
/*  800 */       this.entry.index = nextEntry();
/*  801 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Float2ReferenceMap.Entry<V>> implements Float2ReferenceMap.FastEntrySet<V> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Float2ReferenceMap.Entry<V>> iterator() {
/*  807 */       return new Float2ReferenceOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Float2ReferenceMap.Entry<V>> fastIterator() {
/*  811 */       return new Float2ReferenceOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  816 */       if (!(o instanceof Map.Entry))
/*  817 */         return false; 
/*  818 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  819 */       if (e.getKey() == null || !(e.getKey() instanceof Float))
/*  820 */         return false; 
/*  821 */       float k = ((Float)e.getKey()).floatValue();
/*  822 */       V v = (V)e.getValue();
/*  823 */       if (Float2ReferenceOpenCustomHashMap.this.strategy.equals(k, 0.0F)) {
/*  824 */         return (Float2ReferenceOpenCustomHashMap.this.containsNullKey && Float2ReferenceOpenCustomHashMap.this.value[Float2ReferenceOpenCustomHashMap.this.n] == v);
/*      */       }
/*  826 */       float[] key = Float2ReferenceOpenCustomHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/*  829 */       if (Float.floatToIntBits(
/*  830 */           curr = key[pos = HashCommon.mix(Float2ReferenceOpenCustomHashMap.this.strategy.hashCode(k)) & Float2ReferenceOpenCustomHashMap.this.mask]) == 0)
/*  831 */         return false; 
/*  832 */       if (Float2ReferenceOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  833 */         return (Float2ReferenceOpenCustomHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  836 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2ReferenceOpenCustomHashMap.this.mask]) == 0)
/*  837 */           return false; 
/*  838 */         if (Float2ReferenceOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  839 */           return (Float2ReferenceOpenCustomHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  845 */       if (!(o instanceof Map.Entry))
/*  846 */         return false; 
/*  847 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  848 */       if (e.getKey() == null || !(e.getKey() instanceof Float))
/*  849 */         return false; 
/*  850 */       float k = ((Float)e.getKey()).floatValue();
/*  851 */       V v = (V)e.getValue();
/*  852 */       if (Float2ReferenceOpenCustomHashMap.this.strategy.equals(k, 0.0F)) {
/*  853 */         if (Float2ReferenceOpenCustomHashMap.this.containsNullKey && Float2ReferenceOpenCustomHashMap.this.value[Float2ReferenceOpenCustomHashMap.this.n] == v) {
/*  854 */           Float2ReferenceOpenCustomHashMap.this.removeNullEntry();
/*  855 */           return true;
/*      */         } 
/*  857 */         return false;
/*      */       } 
/*      */       
/*  860 */       float[] key = Float2ReferenceOpenCustomHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/*  863 */       if (Float.floatToIntBits(
/*  864 */           curr = key[pos = HashCommon.mix(Float2ReferenceOpenCustomHashMap.this.strategy.hashCode(k)) & Float2ReferenceOpenCustomHashMap.this.mask]) == 0)
/*  865 */         return false; 
/*  866 */       if (Float2ReferenceOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  867 */         if (Float2ReferenceOpenCustomHashMap.this.value[pos] == v) {
/*  868 */           Float2ReferenceOpenCustomHashMap.this.removeEntry(pos);
/*  869 */           return true;
/*      */         } 
/*  871 */         return false;
/*      */       } 
/*      */       while (true) {
/*  874 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2ReferenceOpenCustomHashMap.this.mask]) == 0)
/*  875 */           return false; 
/*  876 */         if (Float2ReferenceOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  877 */           Float2ReferenceOpenCustomHashMap.this.value[pos] == v) {
/*  878 */           Float2ReferenceOpenCustomHashMap.this.removeEntry(pos);
/*  879 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  886 */       return Float2ReferenceOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  890 */       Float2ReferenceOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Float2ReferenceMap.Entry<V>> consumer) {
/*  895 */       if (Float2ReferenceOpenCustomHashMap.this.containsNullKey)
/*  896 */         consumer.accept(new AbstractFloat2ReferenceMap.BasicEntry<>(Float2ReferenceOpenCustomHashMap.this.key[Float2ReferenceOpenCustomHashMap.this.n], Float2ReferenceOpenCustomHashMap.this.value[Float2ReferenceOpenCustomHashMap.this.n])); 
/*  897 */       for (int pos = Float2ReferenceOpenCustomHashMap.this.n; pos-- != 0;) {
/*  898 */         if (Float.floatToIntBits(Float2ReferenceOpenCustomHashMap.this.key[pos]) != 0)
/*  899 */           consumer.accept(new AbstractFloat2ReferenceMap.BasicEntry<>(Float2ReferenceOpenCustomHashMap.this.key[pos], Float2ReferenceOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Float2ReferenceMap.Entry<V>> consumer) {
/*  904 */       AbstractFloat2ReferenceMap.BasicEntry<V> entry = new AbstractFloat2ReferenceMap.BasicEntry<>();
/*  905 */       if (Float2ReferenceOpenCustomHashMap.this.containsNullKey) {
/*  906 */         entry.key = Float2ReferenceOpenCustomHashMap.this.key[Float2ReferenceOpenCustomHashMap.this.n];
/*  907 */         entry.value = Float2ReferenceOpenCustomHashMap.this.value[Float2ReferenceOpenCustomHashMap.this.n];
/*  908 */         consumer.accept(entry);
/*      */       } 
/*  910 */       for (int pos = Float2ReferenceOpenCustomHashMap.this.n; pos-- != 0;) {
/*  911 */         if (Float.floatToIntBits(Float2ReferenceOpenCustomHashMap.this.key[pos]) != 0) {
/*  912 */           entry.key = Float2ReferenceOpenCustomHashMap.this.key[pos];
/*  913 */           entry.value = Float2ReferenceOpenCustomHashMap.this.value[pos];
/*  914 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Float2ReferenceMap.FastEntrySet<V> float2ReferenceEntrySet() {
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
/*      */     implements FloatIterator
/*      */   {
/*      */     public float nextFloat() {
/*  939 */       return Float2ReferenceOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractFloatSet { private KeySet() {}
/*      */     
/*      */     public FloatIterator iterator() {
/*  945 */       return new Float2ReferenceOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/*  950 */       if (Float2ReferenceOpenCustomHashMap.this.containsNullKey)
/*  951 */         consumer.accept(Float2ReferenceOpenCustomHashMap.this.key[Float2ReferenceOpenCustomHashMap.this.n]); 
/*  952 */       for (int pos = Float2ReferenceOpenCustomHashMap.this.n; pos-- != 0; ) {
/*  953 */         float k = Float2ReferenceOpenCustomHashMap.this.key[pos];
/*  954 */         if (Float.floatToIntBits(k) != 0)
/*  955 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  960 */       return Float2ReferenceOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(float k) {
/*  964 */       return Float2ReferenceOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(float k) {
/*  968 */       int oldSize = Float2ReferenceOpenCustomHashMap.this.size;
/*  969 */       Float2ReferenceOpenCustomHashMap.this.remove(k);
/*  970 */       return (Float2ReferenceOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  974 */       Float2ReferenceOpenCustomHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public FloatSet keySet() {
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
/*  998 */       return Float2ReferenceOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public ReferenceCollection<V> values() {
/* 1003 */     if (this.values == null)
/* 1004 */       this.values = (ReferenceCollection<V>)new AbstractReferenceCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/* 1007 */             return new Float2ReferenceOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1011 */             return Float2ReferenceOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(Object v) {
/* 1015 */             return Float2ReferenceOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1019 */             Float2ReferenceOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(Consumer<? super V> consumer)
/*      */           {
/* 1024 */             if (Float2ReferenceOpenCustomHashMap.this.containsNullKey)
/* 1025 */               consumer.accept(Float2ReferenceOpenCustomHashMap.this.value[Float2ReferenceOpenCustomHashMap.this.n]); 
/* 1026 */             for (int pos = Float2ReferenceOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1027 */               if (Float.floatToIntBits(Float2ReferenceOpenCustomHashMap.this.key[pos]) != 0)
/* 1028 */                 consumer.accept(Float2ReferenceOpenCustomHashMap.this.value[pos]); 
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
/* 1096 */     float[] key = this.key;
/* 1097 */     V[] value = this.value;
/* 1098 */     int mask = newN - 1;
/* 1099 */     float[] newKey = new float[newN + 1];
/* 1100 */     V[] newValue = (V[])new Object[newN + 1];
/* 1101 */     int i = this.n;
/* 1102 */     for (int j = realSize(); j-- != 0; ) {
/* 1103 */       while (Float.floatToIntBits(key[--i]) == 0); int pos;
/* 1104 */       if (Float.floatToIntBits(newKey[
/* 1105 */             pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask]) != 0)
/* 1106 */         while (Float.floatToIntBits(newKey[pos = pos + 1 & mask]) != 0); 
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
/*      */   public Float2ReferenceOpenCustomHashMap<V> clone() {
/*      */     Float2ReferenceOpenCustomHashMap<V> c;
/*      */     try {
/* 1132 */       c = (Float2ReferenceOpenCustomHashMap<V>)super.clone();
/* 1133 */     } catch (CloneNotSupportedException cantHappen) {
/* 1134 */       throw new InternalError();
/*      */     } 
/* 1136 */     c.keys = null;
/* 1137 */     c.values = null;
/* 1138 */     c.entries = null;
/* 1139 */     c.containsNullKey = this.containsNullKey;
/* 1140 */     c.key = (float[])this.key.clone();
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
/* 1158 */       while (Float.floatToIntBits(this.key[i]) == 0)
/* 1159 */         i++; 
/* 1160 */       t = this.strategy.hashCode(this.key[i]);
/* 1161 */       if (this != this.value[i])
/* 1162 */         t ^= (this.value[i] == null) ? 0 : System.identityHashCode(this.value[i]); 
/* 1163 */       h += t;
/* 1164 */       i++;
/*      */     } 
/*      */     
/* 1167 */     if (this.containsNullKey)
/* 1168 */       h += (this.value[this.n] == null) ? 0 : System.identityHashCode(this.value[this.n]); 
/* 1169 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1172 */     float[] key = this.key;
/* 1173 */     V[] value = this.value;
/* 1174 */     MapIterator i = new MapIterator();
/* 1175 */     s.defaultWriteObject();
/* 1176 */     for (int j = this.size; j-- != 0; ) {
/* 1177 */       int e = i.nextEntry();
/* 1178 */       s.writeFloat(key[e]);
/* 1179 */       s.writeObject(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1184 */     s.defaultReadObject();
/* 1185 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1186 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1187 */     this.mask = this.n - 1;
/* 1188 */     float[] key = this.key = new float[this.n + 1];
/* 1189 */     V[] value = this.value = (V[])new Object[this.n + 1];
/*      */ 
/*      */     
/* 1192 */     for (int i = this.size; i-- != 0; ) {
/* 1193 */       int pos; float k = s.readFloat();
/* 1194 */       V v = (V)s.readObject();
/* 1195 */       if (this.strategy.equals(k, 0.0F)) {
/* 1196 */         pos = this.n;
/* 1197 */         this.containsNullKey = true;
/*      */       } else {
/* 1199 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1200 */         while (Float.floatToIntBits(key[pos]) != 0)
/* 1201 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1203 */       key[pos] = k;
/* 1204 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\Float2ReferenceOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */