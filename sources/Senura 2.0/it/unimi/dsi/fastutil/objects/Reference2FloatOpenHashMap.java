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
/*      */ public class Reference2FloatOpenHashMap<K>
/*      */   extends AbstractReference2FloatMap<K>
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
/*      */   protected transient Reference2FloatMap.FastEntrySet<K> entries;
/*      */   protected transient ReferenceSet<K> keys;
/*      */   protected transient FloatCollection values;
/*      */   
/*      */   public Reference2FloatOpenHashMap(int expected, float f) {
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
/*      */   public Reference2FloatOpenHashMap(int expected) {
/*  118 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2FloatOpenHashMap() {
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
/*      */   public Reference2FloatOpenHashMap(Map<? extends K, ? extends Float> m, float f) {
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
/*      */   public Reference2FloatOpenHashMap(Map<? extends K, ? extends Float> m) {
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
/*      */   public Reference2FloatOpenHashMap(Reference2FloatMap<K> m, float f) {
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
/*      */   public Reference2FloatOpenHashMap(Reference2FloatMap<K> m) {
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
/*      */   public Reference2FloatOpenHashMap(K[] k, float[] v, float f) {
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
/*      */   public Reference2FloatOpenHashMap(K[] k, float[] v) {
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
/*      */   public float removeFloat(Object k) {
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
/*      */   public float getFloat(Object k) {
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
/*      */   public boolean containsValue(float v) {
/*  431 */     float[] value = this.value;
/*  432 */     K[] key = this.key;
/*  433 */     if (this.containsNullKey && Float.floatToIntBits(value[this.n]) == Float.floatToIntBits(v))
/*  434 */       return true; 
/*  435 */     for (int i = this.n; i-- != 0;) {
/*  436 */       if (key[i] != null && Float.floatToIntBits(value[i]) == Float.floatToIntBits(v))
/*  437 */         return true; 
/*  438 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float getOrDefault(Object k, float defaultValue) {
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
/*      */   public float putIfAbsent(K k, float v) {
/*  465 */     int pos = find(k);
/*  466 */     if (pos >= 0)
/*  467 */       return this.value[pos]; 
/*  468 */     insert(-pos - 1, k, v);
/*  469 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, float v) {
/*  475 */     if (k == null) {
/*  476 */       if (this.containsNullKey && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[this.n])) {
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
/*  488 */     if (k == curr && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[pos])) {
/*  489 */       removeEntry(pos);
/*  490 */       return true;
/*      */     } 
/*      */     while (true) {
/*  493 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  494 */         return false; 
/*  495 */       if (k == curr && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[pos])) {
/*  496 */         removeEntry(pos);
/*  497 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(K k, float oldValue, float v) {
/*  504 */     int pos = find(k);
/*  505 */     if (pos < 0 || Float.floatToIntBits(oldValue) != Float.floatToIntBits(this.value[pos]))
/*  506 */       return false; 
/*  507 */     this.value[pos] = v;
/*  508 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public float replace(K k, float v) {
/*  513 */     int pos = find(k);
/*  514 */     if (pos < 0)
/*  515 */       return this.defRetValue; 
/*  516 */     float oldValue = this.value[pos];
/*  517 */     this.value[pos] = v;
/*  518 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public float computeFloatIfAbsent(K k, ToDoubleFunction<? super K> mappingFunction) {
/*  523 */     Objects.requireNonNull(mappingFunction);
/*  524 */     int pos = find(k);
/*  525 */     if (pos >= 0)
/*  526 */       return this.value[pos]; 
/*  527 */     float newValue = SafeMath.safeDoubleToFloat(mappingFunction.applyAsDouble(k));
/*  528 */     insert(-pos - 1, k, newValue);
/*  529 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float computeFloatIfPresent(K k, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
/*  535 */     Objects.requireNonNull(remappingFunction);
/*  536 */     int pos = find(k);
/*  537 */     if (pos < 0)
/*  538 */       return this.defRetValue; 
/*  539 */     Float newValue = remappingFunction.apply(k, Float.valueOf(this.value[pos]));
/*  540 */     if (newValue == null) {
/*  541 */       if (k == null) {
/*  542 */         removeNullEntry();
/*      */       } else {
/*  544 */         removeEntry(pos);
/*  545 */       }  return this.defRetValue;
/*      */     } 
/*  547 */     this.value[pos] = newValue.floatValue(); return newValue.floatValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float computeFloat(K k, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
/*  553 */     Objects.requireNonNull(remappingFunction);
/*  554 */     int pos = find(k);
/*  555 */     Float newValue = remappingFunction.apply(k, (pos >= 0) ? Float.valueOf(this.value[pos]) : null);
/*  556 */     if (newValue == null) {
/*  557 */       if (pos >= 0)
/*  558 */         if (k == null) {
/*  559 */           removeNullEntry();
/*      */         } else {
/*  561 */           removeEntry(pos);
/*      */         }  
/*  563 */       return this.defRetValue;
/*      */     } 
/*  565 */     float newVal = newValue.floatValue();
/*  566 */     if (pos < 0) {
/*  567 */       insert(-pos - 1, k, newVal);
/*  568 */       return newVal;
/*      */     } 
/*  570 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float mergeFloat(K k, float v, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/*  576 */     Objects.requireNonNull(remappingFunction);
/*  577 */     int pos = find(k);
/*  578 */     if (pos < 0) {
/*  579 */       insert(-pos - 1, k, v);
/*  580 */       return v;
/*      */     } 
/*  582 */     Float newValue = remappingFunction.apply(Float.valueOf(this.value[pos]), Float.valueOf(v));
/*  583 */     if (newValue == null) {
/*  584 */       if (k == null) {
/*  585 */         removeNullEntry();
/*      */       } else {
/*  587 */         removeEntry(pos);
/*  588 */       }  return this.defRetValue;
/*      */     } 
/*  590 */     this.value[pos] = newValue.floatValue(); return newValue.floatValue();
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
/*      */     implements Reference2FloatMap.Entry<K>, Map.Entry<K, Float>
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
/*  631 */       return Reference2FloatOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public float getFloatValue() {
/*  635 */       return Reference2FloatOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public float setValue(float v) {
/*  639 */       float oldValue = Reference2FloatOpenHashMap.this.value[this.index];
/*  640 */       Reference2FloatOpenHashMap.this.value[this.index] = v;
/*  641 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float getValue() {
/*  651 */       return Float.valueOf(Reference2FloatOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float setValue(Float v) {
/*  661 */       return Float.valueOf(setValue(v.floatValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  666 */       if (!(o instanceof Map.Entry))
/*  667 */         return false; 
/*  668 */       Map.Entry<K, Float> e = (Map.Entry<K, Float>)o;
/*  669 */       return (Reference2FloatOpenHashMap.this.key[this.index] == e.getKey() && 
/*  670 */         Float.floatToIntBits(Reference2FloatOpenHashMap.this.value[this.index]) == Float.floatToIntBits(((Float)e.getValue()).floatValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  674 */       return System.identityHashCode(Reference2FloatOpenHashMap.this.key[this.index]) ^ HashCommon.float2int(Reference2FloatOpenHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  678 */       return (new StringBuilder()).append(Reference2FloatOpenHashMap.this.key[this.index]).append("=>").append(Reference2FloatOpenHashMap.this.value[this.index]).toString();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  688 */     int pos = Reference2FloatOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  695 */     int last = -1;
/*      */     
/*  697 */     int c = Reference2FloatOpenHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  701 */     boolean mustReturnNullKey = Reference2FloatOpenHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     ReferenceArrayList<K> wrapped;
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
/*  716 */         return this.last = Reference2FloatOpenHashMap.this.n;
/*      */       } 
/*  718 */       K[] key = Reference2FloatOpenHashMap.this.key;
/*      */       while (true) {
/*  720 */         if (--this.pos < 0) {
/*      */           
/*  722 */           this.last = Integer.MIN_VALUE;
/*  723 */           K k = this.wrapped.get(-this.pos - 1);
/*  724 */           int p = HashCommon.mix(System.identityHashCode(k)) & Reference2FloatOpenHashMap.this.mask;
/*  725 */           while (k != key[p])
/*  726 */             p = p + 1 & Reference2FloatOpenHashMap.this.mask; 
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
/*  744 */       K[] key = Reference2FloatOpenHashMap.this.key; while (true) {
/*      */         K curr; int last;
/*  746 */         pos = (last = pos) + 1 & Reference2FloatOpenHashMap.this.mask;
/*      */         while (true) {
/*  748 */           if ((curr = key[pos]) == null) {
/*  749 */             key[last] = null;
/*      */             return;
/*      */           } 
/*  752 */           int slot = HashCommon.mix(System.identityHashCode(curr)) & Reference2FloatOpenHashMap.this.mask;
/*  753 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  755 */           pos = pos + 1 & Reference2FloatOpenHashMap.this.mask;
/*      */         } 
/*  757 */         if (pos < last) {
/*  758 */           if (this.wrapped == null)
/*  759 */             this.wrapped = new ReferenceArrayList<>(2); 
/*  760 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  762 */         key[last] = curr;
/*  763 */         Reference2FloatOpenHashMap.this.value[last] = Reference2FloatOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  767 */       if (this.last == -1)
/*  768 */         throw new IllegalStateException(); 
/*  769 */       if (this.last == Reference2FloatOpenHashMap.this.n) {
/*  770 */         Reference2FloatOpenHashMap.this.containsNullKey = false;
/*  771 */         Reference2FloatOpenHashMap.this.key[Reference2FloatOpenHashMap.this.n] = null;
/*  772 */       } else if (this.pos >= 0) {
/*  773 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  776 */         Reference2FloatOpenHashMap.this.removeFloat(this.wrapped.set(-this.pos - 1, null));
/*  777 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  780 */       Reference2FloatOpenHashMap.this.size--;
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
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Reference2FloatMap.Entry<K>> { private Reference2FloatOpenHashMap<K>.MapEntry entry;
/*      */     
/*      */     public Reference2FloatOpenHashMap<K>.MapEntry next() {
/*  796 */       return this.entry = new Reference2FloatOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  800 */       super.remove();
/*  801 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Reference2FloatMap.Entry<K>> { private FastEntryIterator() {
/*  805 */       this.entry = new Reference2FloatOpenHashMap.MapEntry();
/*      */     } private final Reference2FloatOpenHashMap<K>.MapEntry entry;
/*      */     public Reference2FloatOpenHashMap<K>.MapEntry next() {
/*  808 */       this.entry.index = nextEntry();
/*  809 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Reference2FloatMap.Entry<K>> implements Reference2FloatMap.FastEntrySet<K> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Reference2FloatMap.Entry<K>> iterator() {
/*  815 */       return new Reference2FloatOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Reference2FloatMap.Entry<K>> fastIterator() {
/*  819 */       return new Reference2FloatOpenHashMap.FastEntryIterator();
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
/*  832 */         return (Reference2FloatOpenHashMap.this.containsNullKey && 
/*  833 */           Float.floatToIntBits(Reference2FloatOpenHashMap.this.value[Reference2FloatOpenHashMap.this.n]) == Float.floatToIntBits(v));
/*      */       }
/*  835 */       K[] key = Reference2FloatOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  838 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2FloatOpenHashMap.this.mask]) == null)
/*      */       {
/*  840 */         return false; } 
/*  841 */       if (k == curr) {
/*  842 */         return (Float.floatToIntBits(Reference2FloatOpenHashMap.this.value[pos]) == Float.floatToIntBits(v));
/*      */       }
/*      */       while (true) {
/*  845 */         if ((curr = key[pos = pos + 1 & Reference2FloatOpenHashMap.this.mask]) == null)
/*  846 */           return false; 
/*  847 */         if (k == curr) {
/*  848 */           return (Float.floatToIntBits(Reference2FloatOpenHashMap.this.value[pos]) == Float.floatToIntBits(v));
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  854 */       if (!(o instanceof Map.Entry))
/*  855 */         return false; 
/*  856 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  857 */       if (e.getValue() == null || !(e.getValue() instanceof Float))
/*  858 */         return false; 
/*  859 */       K k = (K)e.getKey();
/*  860 */       float v = ((Float)e.getValue()).floatValue();
/*  861 */       if (k == null) {
/*  862 */         if (Reference2FloatOpenHashMap.this.containsNullKey && Float.floatToIntBits(Reference2FloatOpenHashMap.this.value[Reference2FloatOpenHashMap.this.n]) == Float.floatToIntBits(v)) {
/*  863 */           Reference2FloatOpenHashMap.this.removeNullEntry();
/*  864 */           return true;
/*      */         } 
/*  866 */         return false;
/*      */       } 
/*      */       
/*  869 */       K[] key = Reference2FloatOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  872 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2FloatOpenHashMap.this.mask]) == null)
/*      */       {
/*  874 */         return false; } 
/*  875 */       if (curr == k) {
/*  876 */         if (Float.floatToIntBits(Reference2FloatOpenHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
/*  877 */           Reference2FloatOpenHashMap.this.removeEntry(pos);
/*  878 */           return true;
/*      */         } 
/*  880 */         return false;
/*      */       } 
/*      */       while (true) {
/*  883 */         if ((curr = key[pos = pos + 1 & Reference2FloatOpenHashMap.this.mask]) == null)
/*  884 */           return false; 
/*  885 */         if (curr == k && 
/*  886 */           Float.floatToIntBits(Reference2FloatOpenHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
/*  887 */           Reference2FloatOpenHashMap.this.removeEntry(pos);
/*  888 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  895 */       return Reference2FloatOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  899 */       Reference2FloatOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Reference2FloatMap.Entry<K>> consumer) {
/*  904 */       if (Reference2FloatOpenHashMap.this.containsNullKey)
/*  905 */         consumer.accept(new AbstractReference2FloatMap.BasicEntry<>(Reference2FloatOpenHashMap.this.key[Reference2FloatOpenHashMap.this.n], Reference2FloatOpenHashMap.this.value[Reference2FloatOpenHashMap.this.n])); 
/*  906 */       for (int pos = Reference2FloatOpenHashMap.this.n; pos-- != 0;) {
/*  907 */         if (Reference2FloatOpenHashMap.this.key[pos] != null)
/*  908 */           consumer.accept(new AbstractReference2FloatMap.BasicEntry<>(Reference2FloatOpenHashMap.this.key[pos], Reference2FloatOpenHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Reference2FloatMap.Entry<K>> consumer) {
/*  913 */       AbstractReference2FloatMap.BasicEntry<K> entry = new AbstractReference2FloatMap.BasicEntry<>();
/*  914 */       if (Reference2FloatOpenHashMap.this.containsNullKey) {
/*  915 */         entry.key = Reference2FloatOpenHashMap.this.key[Reference2FloatOpenHashMap.this.n];
/*  916 */         entry.value = Reference2FloatOpenHashMap.this.value[Reference2FloatOpenHashMap.this.n];
/*  917 */         consumer.accept(entry);
/*      */       } 
/*  919 */       for (int pos = Reference2FloatOpenHashMap.this.n; pos-- != 0;) {
/*  920 */         if (Reference2FloatOpenHashMap.this.key[pos] != null) {
/*  921 */           entry.key = Reference2FloatOpenHashMap.this.key[pos];
/*  922 */           entry.value = Reference2FloatOpenHashMap.this.value[pos];
/*  923 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Reference2FloatMap.FastEntrySet<K> reference2FloatEntrySet() {
/*  929 */     if (this.entries == null)
/*  930 */       this.entries = new MapEntrySet(); 
/*  931 */     return this.entries;
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
/*  948 */       return Reference2FloatOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractReferenceSet<K> { private KeySet() {}
/*      */     
/*      */     public ObjectIterator<K> iterator() {
/*  954 */       return new Reference2FloatOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/*  959 */       if (Reference2FloatOpenHashMap.this.containsNullKey)
/*  960 */         consumer.accept(Reference2FloatOpenHashMap.this.key[Reference2FloatOpenHashMap.this.n]); 
/*  961 */       for (int pos = Reference2FloatOpenHashMap.this.n; pos-- != 0; ) {
/*  962 */         K k = Reference2FloatOpenHashMap.this.key[pos];
/*  963 */         if (k != null)
/*  964 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  969 */       return Reference2FloatOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(Object k) {
/*  973 */       return Reference2FloatOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(Object k) {
/*  977 */       int oldSize = Reference2FloatOpenHashMap.this.size;
/*  978 */       Reference2FloatOpenHashMap.this.removeFloat(k);
/*  979 */       return (Reference2FloatOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  983 */       Reference2FloatOpenHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ReferenceSet<K> keySet() {
/*  988 */     if (this.keys == null)
/*  989 */       this.keys = new KeySet(); 
/*  990 */     return this.keys;
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
/* 1007 */       return Reference2FloatOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public FloatCollection values() {
/* 1012 */     if (this.values == null)
/* 1013 */       this.values = (FloatCollection)new AbstractFloatCollection()
/*      */         {
/*      */           public FloatIterator iterator() {
/* 1016 */             return new Reference2FloatOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1020 */             return Reference2FloatOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(float v) {
/* 1024 */             return Reference2FloatOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1028 */             Reference2FloatOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(DoubleConsumer consumer)
/*      */           {
/* 1033 */             if (Reference2FloatOpenHashMap.this.containsNullKey)
/* 1034 */               consumer.accept(Reference2FloatOpenHashMap.this.value[Reference2FloatOpenHashMap.this.n]); 
/* 1035 */             for (int pos = Reference2FloatOpenHashMap.this.n; pos-- != 0;) {
/* 1036 */               if (Reference2FloatOpenHashMap.this.key[pos] != null)
/* 1037 */                 consumer.accept(Reference2FloatOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1040 */     return this.values;
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
/* 1057 */     return trim(this.size);
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
/* 1081 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1082 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1083 */       return true; 
/*      */     try {
/* 1085 */       rehash(l);
/* 1086 */     } catch (OutOfMemoryError cantDoIt) {
/* 1087 */       return false;
/*      */     } 
/* 1089 */     return true;
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
/* 1105 */     K[] key = this.key;
/* 1106 */     float[] value = this.value;
/* 1107 */     int mask = newN - 1;
/* 1108 */     K[] newKey = (K[])new Object[newN + 1];
/* 1109 */     float[] newValue = new float[newN + 1];
/* 1110 */     int i = this.n;
/* 1111 */     for (int j = realSize(); j-- != 0; ) {
/* 1112 */       while (key[--i] == null); int pos;
/* 1113 */       if (newKey[pos = HashCommon.mix(System.identityHashCode(key[i])) & mask] != null)
/*      */       {
/* 1115 */         while (newKey[pos = pos + 1 & mask] != null); } 
/* 1116 */       newKey[pos] = key[i];
/* 1117 */       newValue[pos] = value[i];
/*      */     } 
/* 1119 */     newValue[newN] = value[this.n];
/* 1120 */     this.n = newN;
/* 1121 */     this.mask = mask;
/* 1122 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1123 */     this.key = newKey;
/* 1124 */     this.value = newValue;
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
/*      */   public Reference2FloatOpenHashMap<K> clone() {
/*      */     Reference2FloatOpenHashMap<K> c;
/*      */     try {
/* 1141 */       c = (Reference2FloatOpenHashMap<K>)super.clone();
/* 1142 */     } catch (CloneNotSupportedException cantHappen) {
/* 1143 */       throw new InternalError();
/*      */     } 
/* 1145 */     c.keys = null;
/* 1146 */     c.values = null;
/* 1147 */     c.entries = null;
/* 1148 */     c.containsNullKey = this.containsNullKey;
/* 1149 */     c.key = (K[])this.key.clone();
/* 1150 */     c.value = (float[])this.value.clone();
/* 1151 */     return c;
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
/* 1164 */     int h = 0;
/* 1165 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1166 */       while (this.key[i] == null)
/* 1167 */         i++; 
/* 1168 */       if (this != this.key[i])
/* 1169 */         t = System.identityHashCode(this.key[i]); 
/* 1170 */       t ^= HashCommon.float2int(this.value[i]);
/* 1171 */       h += t;
/* 1172 */       i++;
/*      */     } 
/*      */     
/* 1175 */     if (this.containsNullKey)
/* 1176 */       h += HashCommon.float2int(this.value[this.n]); 
/* 1177 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1180 */     K[] key = this.key;
/* 1181 */     float[] value = this.value;
/* 1182 */     MapIterator i = new MapIterator();
/* 1183 */     s.defaultWriteObject();
/* 1184 */     for (int j = this.size; j-- != 0; ) {
/* 1185 */       int e = i.nextEntry();
/* 1186 */       s.writeObject(key[e]);
/* 1187 */       s.writeFloat(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1192 */     s.defaultReadObject();
/* 1193 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1194 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1195 */     this.mask = this.n - 1;
/* 1196 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1197 */     float[] value = this.value = new float[this.n + 1];
/*      */ 
/*      */     
/* 1200 */     for (int i = this.size; i-- != 0; ) {
/* 1201 */       int pos; K k = (K)s.readObject();
/* 1202 */       float v = s.readFloat();
/* 1203 */       if (k == null) {
/* 1204 */         pos = this.n;
/* 1205 */         this.containsNullKey = true;
/*      */       } else {
/* 1207 */         pos = HashCommon.mix(System.identityHashCode(k)) & this.mask;
/* 1208 */         while (key[pos] != null)
/* 1209 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1211 */       key[pos] = k;
/* 1212 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Reference2FloatOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */