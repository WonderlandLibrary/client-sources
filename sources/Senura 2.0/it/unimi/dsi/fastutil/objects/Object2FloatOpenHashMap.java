/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
/*      */ import it.unimi.dsi.fastutil.floats.FloatCollection;
/*      */ import it.unimi.dsi.fastutil.floats.FloatIterator;
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
/*      */ public class Object2FloatOpenHashMap<K>
/*      */   extends AbstractObject2FloatMap<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient float[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Object2FloatMap.FastEntrySet<K> entries;
/*      */   protected transient ObjectSet<K> keys;
/*      */   protected transient FloatCollection values;
/*      */   
/*      */   public Object2FloatOpenHashMap(int expected, float f) {
/*  100 */     if (f <= 0.0F || f > 1.0F)
/*  101 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  102 */     if (expected < 0)
/*  103 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  104 */     this.f = f;
/*  105 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  106 */     this.mask = this.n - 1;
/*  107 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  108 */     this.key = (K[])new Object[this.n + 1];
/*  109 */     this.value = new float[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2FloatOpenHashMap(int expected) {
/*  118 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2FloatOpenHashMap() {
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
/*      */   public Object2FloatOpenHashMap(Map<? extends K, ? extends Float> m, float f) {
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
/*      */   public Object2FloatOpenHashMap(Map<? extends K, ? extends Float> m) {
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
/*      */   public Object2FloatOpenHashMap(Object2FloatMap<K> m, float f) {
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
/*      */   public Object2FloatOpenHashMap(Object2FloatMap<K> m) {
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
/*      */   public Object2FloatOpenHashMap(K[] k, float[] v, float f) {
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
/*      */   public Object2FloatOpenHashMap(K[] k, float[] v) {
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
/*      */   private float removeEntry(int pos) {
/*  221 */     float oldValue = this.value[pos];
/*  222 */     this.size--;
/*  223 */     shiftKeys(pos);
/*  224 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  225 */       rehash(this.n / 2); 
/*  226 */     return oldValue;
/*      */   }
/*      */   private float removeNullEntry() {
/*  229 */     this.containsNullKey = false;
/*  230 */     this.key[this.n] = null;
/*  231 */     float oldValue = this.value[this.n];
/*  232 */     this.size--;
/*  233 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  234 */       rehash(this.n / 2); 
/*  235 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends Float> m) {
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
/*      */   private void insert(int pos, K k, float v) {
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
/*      */   public float put(K k, float v) {
/*  278 */     int pos = find(k);
/*  279 */     if (pos < 0) {
/*  280 */       insert(-pos - 1, k, v);
/*  281 */       return this.defRetValue;
/*      */     } 
/*  283 */     float oldValue = this.value[pos];
/*  284 */     this.value[pos] = v;
/*  285 */     return oldValue;
/*      */   }
/*      */   private float addToValue(int pos, float incr) {
/*  288 */     float oldValue = this.value[pos];
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
/*      */   public float addTo(K k, float incr) {
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
/*      */   public float removeFloat(Object k) {
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
/*      */   public float getFloat(Object k) {
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
/*      */   public boolean containsValue(float v) {
/*  430 */     float[] value = this.value;
/*  431 */     K[] key = this.key;
/*  432 */     if (this.containsNullKey && Float.floatToIntBits(value[this.n]) == Float.floatToIntBits(v))
/*  433 */       return true; 
/*  434 */     for (int i = this.n; i-- != 0;) {
/*  435 */       if (key[i] != null && Float.floatToIntBits(value[i]) == Float.floatToIntBits(v))
/*  436 */         return true; 
/*  437 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float getOrDefault(Object k, float defaultValue) {
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
/*      */   public float putIfAbsent(K k, float v) {
/*  464 */     int pos = find(k);
/*  465 */     if (pos >= 0)
/*  466 */       return this.value[pos]; 
/*  467 */     insert(-pos - 1, k, v);
/*  468 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, float v) {
/*  474 */     if (k == null) {
/*  475 */       if (this.containsNullKey && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[this.n])) {
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
/*  487 */     if (k.equals(curr) && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[pos])) {
/*  488 */       removeEntry(pos);
/*  489 */       return true;
/*      */     } 
/*      */     while (true) {
/*  492 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  493 */         return false; 
/*  494 */       if (k.equals(curr) && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[pos])) {
/*  495 */         removeEntry(pos);
/*  496 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(K k, float oldValue, float v) {
/*  503 */     int pos = find(k);
/*  504 */     if (pos < 0 || Float.floatToIntBits(oldValue) != Float.floatToIntBits(this.value[pos]))
/*  505 */       return false; 
/*  506 */     this.value[pos] = v;
/*  507 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public float replace(K k, float v) {
/*  512 */     int pos = find(k);
/*  513 */     if (pos < 0)
/*  514 */       return this.defRetValue; 
/*  515 */     float oldValue = this.value[pos];
/*  516 */     this.value[pos] = v;
/*  517 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public float computeFloatIfAbsent(K k, ToDoubleFunction<? super K> mappingFunction) {
/*  522 */     Objects.requireNonNull(mappingFunction);
/*  523 */     int pos = find(k);
/*  524 */     if (pos >= 0)
/*  525 */       return this.value[pos]; 
/*  526 */     float newValue = SafeMath.safeDoubleToFloat(mappingFunction.applyAsDouble(k));
/*  527 */     insert(-pos - 1, k, newValue);
/*  528 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float computeFloatIfPresent(K k, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
/*  534 */     Objects.requireNonNull(remappingFunction);
/*  535 */     int pos = find(k);
/*  536 */     if (pos < 0)
/*  537 */       return this.defRetValue; 
/*  538 */     Float newValue = remappingFunction.apply(k, Float.valueOf(this.value[pos]));
/*  539 */     if (newValue == null) {
/*  540 */       if (k == null) {
/*  541 */         removeNullEntry();
/*      */       } else {
/*  543 */         removeEntry(pos);
/*  544 */       }  return this.defRetValue;
/*      */     } 
/*  546 */     this.value[pos] = newValue.floatValue(); return newValue.floatValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float computeFloat(K k, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
/*  552 */     Objects.requireNonNull(remappingFunction);
/*  553 */     int pos = find(k);
/*  554 */     Float newValue = remappingFunction.apply(k, (pos >= 0) ? Float.valueOf(this.value[pos]) : null);
/*  555 */     if (newValue == null) {
/*  556 */       if (pos >= 0)
/*  557 */         if (k == null) {
/*  558 */           removeNullEntry();
/*      */         } else {
/*  560 */           removeEntry(pos);
/*      */         }  
/*  562 */       return this.defRetValue;
/*      */     } 
/*  564 */     float newVal = newValue.floatValue();
/*  565 */     if (pos < 0) {
/*  566 */       insert(-pos - 1, k, newVal);
/*  567 */       return newVal;
/*      */     } 
/*  569 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float mergeFloat(K k, float v, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/*  575 */     Objects.requireNonNull(remappingFunction);
/*  576 */     int pos = find(k);
/*  577 */     if (pos < 0) {
/*  578 */       insert(-pos - 1, k, v);
/*  579 */       return v;
/*      */     } 
/*  581 */     Float newValue = remappingFunction.apply(Float.valueOf(this.value[pos]), Float.valueOf(v));
/*  582 */     if (newValue == null) {
/*  583 */       if (k == null) {
/*  584 */         removeNullEntry();
/*      */       } else {
/*  586 */         removeEntry(pos);
/*  587 */       }  return this.defRetValue;
/*      */     } 
/*  589 */     this.value[pos] = newValue.floatValue(); return newValue.floatValue();
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
/*  600 */     if (this.size == 0)
/*      */       return; 
/*  602 */     this.size = 0;
/*  603 */     this.containsNullKey = false;
/*  604 */     Arrays.fill((Object[])this.key, (Object)null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  608 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  612 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Object2FloatMap.Entry<K>, Map.Entry<K, Float>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  624 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public K getKey() {
/*  630 */       return Object2FloatOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public float getFloatValue() {
/*  634 */       return Object2FloatOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public float setValue(float v) {
/*  638 */       float oldValue = Object2FloatOpenHashMap.this.value[this.index];
/*  639 */       Object2FloatOpenHashMap.this.value[this.index] = v;
/*  640 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float getValue() {
/*  650 */       return Float.valueOf(Object2FloatOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float setValue(Float v) {
/*  660 */       return Float.valueOf(setValue(v.floatValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  665 */       if (!(o instanceof Map.Entry))
/*  666 */         return false; 
/*  667 */       Map.Entry<K, Float> e = (Map.Entry<K, Float>)o;
/*  668 */       return (Objects.equals(Object2FloatOpenHashMap.this.key[this.index], e.getKey()) && 
/*  669 */         Float.floatToIntBits(Object2FloatOpenHashMap.this.value[this.index]) == Float.floatToIntBits(((Float)e.getValue()).floatValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  673 */       return ((Object2FloatOpenHashMap.this.key[this.index] == null) ? 0 : Object2FloatOpenHashMap.this.key[this.index].hashCode()) ^ 
/*  674 */         HashCommon.float2int(Object2FloatOpenHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  678 */       return (new StringBuilder()).append(Object2FloatOpenHashMap.this.key[this.index]).append("=>").append(Object2FloatOpenHashMap.this.value[this.index]).toString();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  688 */     int pos = Object2FloatOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  695 */     int last = -1;
/*      */     
/*  697 */     int c = Object2FloatOpenHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  701 */     boolean mustReturnNullKey = Object2FloatOpenHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     ObjectArrayList<K> wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  708 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  711 */       if (!hasNext())
/*  712 */         throw new NoSuchElementException(); 
/*  713 */       this.c--;
/*  714 */       if (this.mustReturnNullKey) {
/*  715 */         this.mustReturnNullKey = false;
/*  716 */         return this.last = Object2FloatOpenHashMap.this.n;
/*      */       } 
/*  718 */       K[] key = Object2FloatOpenHashMap.this.key;
/*      */       while (true) {
/*  720 */         if (--this.pos < 0) {
/*      */           
/*  722 */           this.last = Integer.MIN_VALUE;
/*  723 */           K k = this.wrapped.get(-this.pos - 1);
/*  724 */           int p = HashCommon.mix(k.hashCode()) & Object2FloatOpenHashMap.this.mask;
/*  725 */           while (!k.equals(key[p]))
/*  726 */             p = p + 1 & Object2FloatOpenHashMap.this.mask; 
/*  727 */           return p;
/*      */         } 
/*  729 */         if (key[this.pos] != null) {
/*  730 */           return this.last = this.pos;
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
/*  744 */       K[] key = Object2FloatOpenHashMap.this.key; while (true) {
/*      */         K curr; int last;
/*  746 */         pos = (last = pos) + 1 & Object2FloatOpenHashMap.this.mask;
/*      */         while (true) {
/*  748 */           if ((curr = key[pos]) == null) {
/*  749 */             key[last] = null;
/*      */             return;
/*      */           } 
/*  752 */           int slot = HashCommon.mix(curr.hashCode()) & Object2FloatOpenHashMap.this.mask;
/*  753 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  755 */           pos = pos + 1 & Object2FloatOpenHashMap.this.mask;
/*      */         } 
/*  757 */         if (pos < last) {
/*  758 */           if (this.wrapped == null)
/*  759 */             this.wrapped = new ObjectArrayList<>(2); 
/*  760 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  762 */         key[last] = curr;
/*  763 */         Object2FloatOpenHashMap.this.value[last] = Object2FloatOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  767 */       if (this.last == -1)
/*  768 */         throw new IllegalStateException(); 
/*  769 */       if (this.last == Object2FloatOpenHashMap.this.n) {
/*  770 */         Object2FloatOpenHashMap.this.containsNullKey = false;
/*  771 */         Object2FloatOpenHashMap.this.key[Object2FloatOpenHashMap.this.n] = null;
/*  772 */       } else if (this.pos >= 0) {
/*  773 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  776 */         Object2FloatOpenHashMap.this.removeFloat(this.wrapped.set(-this.pos - 1, null));
/*  777 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  780 */       Object2FloatOpenHashMap.this.size--;
/*  781 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  786 */       int i = n;
/*  787 */       while (i-- != 0 && hasNext())
/*  788 */         nextEntry(); 
/*  789 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Object2FloatMap.Entry<K>> { private Object2FloatOpenHashMap<K>.MapEntry entry;
/*      */     
/*      */     public Object2FloatOpenHashMap<K>.MapEntry next() {
/*  796 */       return this.entry = new Object2FloatOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  800 */       super.remove();
/*  801 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Object2FloatMap.Entry<K>> { private FastEntryIterator() {
/*  805 */       this.entry = new Object2FloatOpenHashMap.MapEntry();
/*      */     } private final Object2FloatOpenHashMap<K>.MapEntry entry;
/*      */     public Object2FloatOpenHashMap<K>.MapEntry next() {
/*  808 */       this.entry.index = nextEntry();
/*  809 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Object2FloatMap.Entry<K>> implements Object2FloatMap.FastEntrySet<K> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Object2FloatMap.Entry<K>> iterator() {
/*  815 */       return new Object2FloatOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Object2FloatMap.Entry<K>> fastIterator() {
/*  819 */       return new Object2FloatOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  824 */       if (!(o instanceof Map.Entry))
/*  825 */         return false; 
/*  826 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  827 */       if (e.getValue() == null || !(e.getValue() instanceof Float))
/*  828 */         return false; 
/*  829 */       K k = (K)e.getKey();
/*  830 */       float v = ((Float)e.getValue()).floatValue();
/*  831 */       if (k == null) {
/*  832 */         return (Object2FloatOpenHashMap.this.containsNullKey && 
/*  833 */           Float.floatToIntBits(Object2FloatOpenHashMap.this.value[Object2FloatOpenHashMap.this.n]) == Float.floatToIntBits(v));
/*      */       }
/*  835 */       K[] key = Object2FloatOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  838 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2FloatOpenHashMap.this.mask]) == null)
/*  839 */         return false; 
/*  840 */       if (k.equals(curr)) {
/*  841 */         return (Float.floatToIntBits(Object2FloatOpenHashMap.this.value[pos]) == Float.floatToIntBits(v));
/*      */       }
/*      */       while (true) {
/*  844 */         if ((curr = key[pos = pos + 1 & Object2FloatOpenHashMap.this.mask]) == null)
/*  845 */           return false; 
/*  846 */         if (k.equals(curr)) {
/*  847 */           return (Float.floatToIntBits(Object2FloatOpenHashMap.this.value[pos]) == Float.floatToIntBits(v));
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  853 */       if (!(o instanceof Map.Entry))
/*  854 */         return false; 
/*  855 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  856 */       if (e.getValue() == null || !(e.getValue() instanceof Float))
/*  857 */         return false; 
/*  858 */       K k = (K)e.getKey();
/*  859 */       float v = ((Float)e.getValue()).floatValue();
/*  860 */       if (k == null) {
/*  861 */         if (Object2FloatOpenHashMap.this.containsNullKey && Float.floatToIntBits(Object2FloatOpenHashMap.this.value[Object2FloatOpenHashMap.this.n]) == Float.floatToIntBits(v)) {
/*  862 */           Object2FloatOpenHashMap.this.removeNullEntry();
/*  863 */           return true;
/*      */         } 
/*  865 */         return false;
/*      */       } 
/*      */       
/*  868 */       K[] key = Object2FloatOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  871 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2FloatOpenHashMap.this.mask]) == null)
/*  872 */         return false; 
/*  873 */       if (curr.equals(k)) {
/*  874 */         if (Float.floatToIntBits(Object2FloatOpenHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
/*  875 */           Object2FloatOpenHashMap.this.removeEntry(pos);
/*  876 */           return true;
/*      */         } 
/*  878 */         return false;
/*      */       } 
/*      */       while (true) {
/*  881 */         if ((curr = key[pos = pos + 1 & Object2FloatOpenHashMap.this.mask]) == null)
/*  882 */           return false; 
/*  883 */         if (curr.equals(k) && 
/*  884 */           Float.floatToIntBits(Object2FloatOpenHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
/*  885 */           Object2FloatOpenHashMap.this.removeEntry(pos);
/*  886 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  893 */       return Object2FloatOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  897 */       Object2FloatOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Object2FloatMap.Entry<K>> consumer) {
/*  902 */       if (Object2FloatOpenHashMap.this.containsNullKey)
/*  903 */         consumer.accept(new AbstractObject2FloatMap.BasicEntry<>(Object2FloatOpenHashMap.this.key[Object2FloatOpenHashMap.this.n], Object2FloatOpenHashMap.this.value[Object2FloatOpenHashMap.this.n])); 
/*  904 */       for (int pos = Object2FloatOpenHashMap.this.n; pos-- != 0;) {
/*  905 */         if (Object2FloatOpenHashMap.this.key[pos] != null)
/*  906 */           consumer.accept(new AbstractObject2FloatMap.BasicEntry<>(Object2FloatOpenHashMap.this.key[pos], Object2FloatOpenHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Object2FloatMap.Entry<K>> consumer) {
/*  911 */       AbstractObject2FloatMap.BasicEntry<K> entry = new AbstractObject2FloatMap.BasicEntry<>();
/*  912 */       if (Object2FloatOpenHashMap.this.containsNullKey) {
/*  913 */         entry.key = Object2FloatOpenHashMap.this.key[Object2FloatOpenHashMap.this.n];
/*  914 */         entry.value = Object2FloatOpenHashMap.this.value[Object2FloatOpenHashMap.this.n];
/*  915 */         consumer.accept(entry);
/*      */       } 
/*  917 */       for (int pos = Object2FloatOpenHashMap.this.n; pos-- != 0;) {
/*  918 */         if (Object2FloatOpenHashMap.this.key[pos] != null) {
/*  919 */           entry.key = Object2FloatOpenHashMap.this.key[pos];
/*  920 */           entry.value = Object2FloatOpenHashMap.this.value[pos];
/*  921 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Object2FloatMap.FastEntrySet<K> object2FloatEntrySet() {
/*  927 */     if (this.entries == null)
/*  928 */       this.entries = new MapEntrySet(); 
/*  929 */     return this.entries;
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
/*  946 */       return Object2FloatOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractObjectSet<K> { private KeySet() {}
/*      */     
/*      */     public ObjectIterator<K> iterator() {
/*  952 */       return new Object2FloatOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/*  957 */       if (Object2FloatOpenHashMap.this.containsNullKey)
/*  958 */         consumer.accept(Object2FloatOpenHashMap.this.key[Object2FloatOpenHashMap.this.n]); 
/*  959 */       for (int pos = Object2FloatOpenHashMap.this.n; pos-- != 0; ) {
/*  960 */         K k = Object2FloatOpenHashMap.this.key[pos];
/*  961 */         if (k != null)
/*  962 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  967 */       return Object2FloatOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(Object k) {
/*  971 */       return Object2FloatOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(Object k) {
/*  975 */       int oldSize = Object2FloatOpenHashMap.this.size;
/*  976 */       Object2FloatOpenHashMap.this.removeFloat(k);
/*  977 */       return (Object2FloatOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  981 */       Object2FloatOpenHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ObjectSet<K> keySet() {
/*  986 */     if (this.keys == null)
/*  987 */       this.keys = new KeySet(); 
/*  988 */     return this.keys;
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
/*      */     implements FloatIterator
/*      */   {
/*      */     public float nextFloat() {
/* 1005 */       return Object2FloatOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public FloatCollection values() {
/* 1010 */     if (this.values == null)
/* 1011 */       this.values = (FloatCollection)new AbstractFloatCollection()
/*      */         {
/*      */           public FloatIterator iterator() {
/* 1014 */             return new Object2FloatOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1018 */             return Object2FloatOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(float v) {
/* 1022 */             return Object2FloatOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1026 */             Object2FloatOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(DoubleConsumer consumer)
/*      */           {
/* 1031 */             if (Object2FloatOpenHashMap.this.containsNullKey)
/* 1032 */               consumer.accept(Object2FloatOpenHashMap.this.value[Object2FloatOpenHashMap.this.n]); 
/* 1033 */             for (int pos = Object2FloatOpenHashMap.this.n; pos-- != 0;) {
/* 1034 */               if (Object2FloatOpenHashMap.this.key[pos] != null)
/* 1035 */                 consumer.accept(Object2FloatOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1038 */     return this.values;
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
/* 1055 */     return trim(this.size);
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
/* 1079 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1080 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1081 */       return true; 
/*      */     try {
/* 1083 */       rehash(l);
/* 1084 */     } catch (OutOfMemoryError cantDoIt) {
/* 1085 */       return false;
/*      */     } 
/* 1087 */     return true;
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
/* 1103 */     K[] key = this.key;
/* 1104 */     float[] value = this.value;
/* 1105 */     int mask = newN - 1;
/* 1106 */     K[] newKey = (K[])new Object[newN + 1];
/* 1107 */     float[] newValue = new float[newN + 1];
/* 1108 */     int i = this.n;
/* 1109 */     for (int j = realSize(); j-- != 0; ) {
/* 1110 */       while (key[--i] == null); int pos;
/* 1111 */       if (newKey[pos = HashCommon.mix(key[i].hashCode()) & mask] != null)
/* 1112 */         while (newKey[pos = pos + 1 & mask] != null); 
/* 1113 */       newKey[pos] = key[i];
/* 1114 */       newValue[pos] = value[i];
/*      */     } 
/* 1116 */     newValue[newN] = value[this.n];
/* 1117 */     this.n = newN;
/* 1118 */     this.mask = mask;
/* 1119 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1120 */     this.key = newKey;
/* 1121 */     this.value = newValue;
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
/*      */   public Object2FloatOpenHashMap<K> clone() {
/*      */     Object2FloatOpenHashMap<K> c;
/*      */     try {
/* 1138 */       c = (Object2FloatOpenHashMap<K>)super.clone();
/* 1139 */     } catch (CloneNotSupportedException cantHappen) {
/* 1140 */       throw new InternalError();
/*      */     } 
/* 1142 */     c.keys = null;
/* 1143 */     c.values = null;
/* 1144 */     c.entries = null;
/* 1145 */     c.containsNullKey = this.containsNullKey;
/* 1146 */     c.key = (K[])this.key.clone();
/* 1147 */     c.value = (float[])this.value.clone();
/* 1148 */     return c;
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
/* 1161 */     int h = 0;
/* 1162 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1163 */       while (this.key[i] == null)
/* 1164 */         i++; 
/* 1165 */       if (this != this.key[i])
/* 1166 */         t = this.key[i].hashCode(); 
/* 1167 */       t ^= HashCommon.float2int(this.value[i]);
/* 1168 */       h += t;
/* 1169 */       i++;
/*      */     } 
/*      */     
/* 1172 */     if (this.containsNullKey)
/* 1173 */       h += HashCommon.float2int(this.value[this.n]); 
/* 1174 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1177 */     K[] key = this.key;
/* 1178 */     float[] value = this.value;
/* 1179 */     MapIterator i = new MapIterator();
/* 1180 */     s.defaultWriteObject();
/* 1181 */     for (int j = this.size; j-- != 0; ) {
/* 1182 */       int e = i.nextEntry();
/* 1183 */       s.writeObject(key[e]);
/* 1184 */       s.writeFloat(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1189 */     s.defaultReadObject();
/* 1190 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1191 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1192 */     this.mask = this.n - 1;
/* 1193 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1194 */     float[] value = this.value = new float[this.n + 1];
/*      */ 
/*      */     
/* 1197 */     for (int i = this.size; i-- != 0; ) {
/* 1198 */       int pos; K k = (K)s.readObject();
/* 1199 */       float v = s.readFloat();
/* 1200 */       if (k == null) {
/* 1201 */         pos = this.n;
/* 1202 */         this.containsNullKey = true;
/*      */       } else {
/* 1204 */         pos = HashCommon.mix(k.hashCode()) & this.mask;
/* 1205 */         while (key[pos] != null)
/* 1206 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1208 */       key[pos] = k;
/* 1209 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2FloatOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */