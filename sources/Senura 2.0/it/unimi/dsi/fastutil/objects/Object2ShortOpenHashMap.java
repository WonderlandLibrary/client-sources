/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortCollection;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortIterator;
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
/*      */ import java.util.function.IntConsumer;
/*      */ import java.util.function.ToIntFunction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Object2ShortOpenHashMap<K>
/*      */   extends AbstractObject2ShortMap<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient short[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Object2ShortMap.FastEntrySet<K> entries;
/*      */   protected transient ObjectSet<K> keys;
/*      */   protected transient ShortCollection values;
/*      */   
/*      */   public Object2ShortOpenHashMap(int expected, float f) {
/*  100 */     if (f <= 0.0F || f > 1.0F)
/*  101 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  102 */     if (expected < 0)
/*  103 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  104 */     this.f = f;
/*  105 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  106 */     this.mask = this.n - 1;
/*  107 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  108 */     this.key = (K[])new Object[this.n + 1];
/*  109 */     this.value = new short[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ShortOpenHashMap(int expected) {
/*  118 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ShortOpenHashMap() {
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
/*      */   public Object2ShortOpenHashMap(Map<? extends K, ? extends Short> m, float f) {
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
/*      */   public Object2ShortOpenHashMap(Map<? extends K, ? extends Short> m) {
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
/*      */   public Object2ShortOpenHashMap(Object2ShortMap<K> m, float f) {
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
/*      */   public Object2ShortOpenHashMap(Object2ShortMap<K> m) {
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
/*      */   public Object2ShortOpenHashMap(K[] k, short[] v, float f) {
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
/*      */   public Object2ShortOpenHashMap(K[] k, short[] v) {
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
/*      */   private short removeEntry(int pos) {
/*  221 */     short oldValue = this.value[pos];
/*  222 */     this.size--;
/*  223 */     shiftKeys(pos);
/*  224 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  225 */       rehash(this.n / 2); 
/*  226 */     return oldValue;
/*      */   }
/*      */   private short removeNullEntry() {
/*  229 */     this.containsNullKey = false;
/*  230 */     this.key[this.n] = null;
/*  231 */     short oldValue = this.value[this.n];
/*  232 */     this.size--;
/*  233 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  234 */       rehash(this.n / 2); 
/*  235 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends Short> m) {
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
/*      */   private void insert(int pos, K k, short v) {
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
/*      */   public short put(K k, short v) {
/*  278 */     int pos = find(k);
/*  279 */     if (pos < 0) {
/*  280 */       insert(-pos - 1, k, v);
/*  281 */       return this.defRetValue;
/*      */     } 
/*  283 */     short oldValue = this.value[pos];
/*  284 */     this.value[pos] = v;
/*  285 */     return oldValue;
/*      */   }
/*      */   private short addToValue(int pos, short incr) {
/*  288 */     short oldValue = this.value[pos];
/*  289 */     this.value[pos] = (short)(oldValue + incr);
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
/*      */   public short addTo(K k, short incr) {
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
/*  328 */     this.value[pos] = (short)(this.defRetValue + incr);
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
/*      */   public short removeShort(Object k) {
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
/*      */   public short getShort(Object k) {
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
/*      */   public boolean containsValue(short v) {
/*  430 */     short[] value = this.value;
/*  431 */     K[] key = this.key;
/*  432 */     if (this.containsNullKey && value[this.n] == v)
/*  433 */       return true; 
/*  434 */     for (int i = this.n; i-- != 0;) {
/*  435 */       if (key[i] != null && value[i] == v)
/*  436 */         return true; 
/*  437 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short getOrDefault(Object k, short defaultValue) {
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
/*      */   public short putIfAbsent(K k, short v) {
/*  464 */     int pos = find(k);
/*  465 */     if (pos >= 0)
/*  466 */       return this.value[pos]; 
/*  467 */     insert(-pos - 1, k, v);
/*  468 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, short v) {
/*  474 */     if (k == null) {
/*  475 */       if (this.containsNullKey && v == this.value[this.n]) {
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
/*  487 */     if (k.equals(curr) && v == this.value[pos]) {
/*  488 */       removeEntry(pos);
/*  489 */       return true;
/*      */     } 
/*      */     while (true) {
/*  492 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  493 */         return false; 
/*  494 */       if (k.equals(curr) && v == this.value[pos]) {
/*  495 */         removeEntry(pos);
/*  496 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(K k, short oldValue, short v) {
/*  503 */     int pos = find(k);
/*  504 */     if (pos < 0 || oldValue != this.value[pos])
/*  505 */       return false; 
/*  506 */     this.value[pos] = v;
/*  507 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public short replace(K k, short v) {
/*  512 */     int pos = find(k);
/*  513 */     if (pos < 0)
/*  514 */       return this.defRetValue; 
/*  515 */     short oldValue = this.value[pos];
/*  516 */     this.value[pos] = v;
/*  517 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public short computeShortIfAbsent(K k, ToIntFunction<? super K> mappingFunction) {
/*  522 */     Objects.requireNonNull(mappingFunction);
/*  523 */     int pos = find(k);
/*  524 */     if (pos >= 0)
/*  525 */       return this.value[pos]; 
/*  526 */     short newValue = SafeMath.safeIntToShort(mappingFunction.applyAsInt(k));
/*  527 */     insert(-pos - 1, k, newValue);
/*  528 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short computeShortIfPresent(K k, BiFunction<? super K, ? super Short, ? extends Short> remappingFunction) {
/*  534 */     Objects.requireNonNull(remappingFunction);
/*  535 */     int pos = find(k);
/*  536 */     if (pos < 0)
/*  537 */       return this.defRetValue; 
/*  538 */     Short newValue = remappingFunction.apply(k, Short.valueOf(this.value[pos]));
/*  539 */     if (newValue == null) {
/*  540 */       if (k == null) {
/*  541 */         removeNullEntry();
/*      */       } else {
/*  543 */         removeEntry(pos);
/*  544 */       }  return this.defRetValue;
/*      */     } 
/*  546 */     this.value[pos] = newValue.shortValue(); return newValue.shortValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short computeShort(K k, BiFunction<? super K, ? super Short, ? extends Short> remappingFunction) {
/*  552 */     Objects.requireNonNull(remappingFunction);
/*  553 */     int pos = find(k);
/*  554 */     Short newValue = remappingFunction.apply(k, (pos >= 0) ? Short.valueOf(this.value[pos]) : null);
/*  555 */     if (newValue == null) {
/*  556 */       if (pos >= 0)
/*  557 */         if (k == null) {
/*  558 */           removeNullEntry();
/*      */         } else {
/*  560 */           removeEntry(pos);
/*      */         }  
/*  562 */       return this.defRetValue;
/*      */     } 
/*  564 */     short newVal = newValue.shortValue();
/*  565 */     if (pos < 0) {
/*  566 */       insert(-pos - 1, k, newVal);
/*  567 */       return newVal;
/*      */     } 
/*  569 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short mergeShort(K k, short v, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
/*  575 */     Objects.requireNonNull(remappingFunction);
/*  576 */     int pos = find(k);
/*  577 */     if (pos < 0) {
/*  578 */       insert(-pos - 1, k, v);
/*  579 */       return v;
/*      */     } 
/*  581 */     Short newValue = remappingFunction.apply(Short.valueOf(this.value[pos]), Short.valueOf(v));
/*  582 */     if (newValue == null) {
/*  583 */       if (k == null) {
/*  584 */         removeNullEntry();
/*      */       } else {
/*  586 */         removeEntry(pos);
/*  587 */       }  return this.defRetValue;
/*      */     } 
/*  589 */     this.value[pos] = newValue.shortValue(); return newValue.shortValue();
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
/*      */     implements Object2ShortMap.Entry<K>, Map.Entry<K, Short>
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
/*  630 */       return Object2ShortOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public short getShortValue() {
/*  634 */       return Object2ShortOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public short setValue(short v) {
/*  638 */       short oldValue = Object2ShortOpenHashMap.this.value[this.index];
/*  639 */       Object2ShortOpenHashMap.this.value[this.index] = v;
/*  640 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Short getValue() {
/*  650 */       return Short.valueOf(Object2ShortOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Short setValue(Short v) {
/*  660 */       return Short.valueOf(setValue(v.shortValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  665 */       if (!(o instanceof Map.Entry))
/*  666 */         return false; 
/*  667 */       Map.Entry<K, Short> e = (Map.Entry<K, Short>)o;
/*  668 */       return (Objects.equals(Object2ShortOpenHashMap.this.key[this.index], e.getKey()) && Object2ShortOpenHashMap.this.value[this.index] == ((Short)e
/*  669 */         .getValue()).shortValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  673 */       return ((Object2ShortOpenHashMap.this.key[this.index] == null) ? 0 : Object2ShortOpenHashMap.this.key[this.index].hashCode()) ^ Object2ShortOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/*  677 */       return (new StringBuilder()).append(Object2ShortOpenHashMap.this.key[this.index]).append("=>").append(Object2ShortOpenHashMap.this.value[this.index]).toString();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  687 */     int pos = Object2ShortOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  694 */     int last = -1;
/*      */     
/*  696 */     int c = Object2ShortOpenHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  700 */     boolean mustReturnNullKey = Object2ShortOpenHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     ObjectArrayList<K> wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  707 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  710 */       if (!hasNext())
/*  711 */         throw new NoSuchElementException(); 
/*  712 */       this.c--;
/*  713 */       if (this.mustReturnNullKey) {
/*  714 */         this.mustReturnNullKey = false;
/*  715 */         return this.last = Object2ShortOpenHashMap.this.n;
/*      */       } 
/*  717 */       K[] key = Object2ShortOpenHashMap.this.key;
/*      */       while (true) {
/*  719 */         if (--this.pos < 0) {
/*      */           
/*  721 */           this.last = Integer.MIN_VALUE;
/*  722 */           K k = this.wrapped.get(-this.pos - 1);
/*  723 */           int p = HashCommon.mix(k.hashCode()) & Object2ShortOpenHashMap.this.mask;
/*  724 */           while (!k.equals(key[p]))
/*  725 */             p = p + 1 & Object2ShortOpenHashMap.this.mask; 
/*  726 */           return p;
/*      */         } 
/*  728 */         if (key[this.pos] != null) {
/*  729 */           return this.last = this.pos;
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
/*  743 */       K[] key = Object2ShortOpenHashMap.this.key; while (true) {
/*      */         K curr; int last;
/*  745 */         pos = (last = pos) + 1 & Object2ShortOpenHashMap.this.mask;
/*      */         while (true) {
/*  747 */           if ((curr = key[pos]) == null) {
/*  748 */             key[last] = null;
/*      */             return;
/*      */           } 
/*  751 */           int slot = HashCommon.mix(curr.hashCode()) & Object2ShortOpenHashMap.this.mask;
/*  752 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  754 */           pos = pos + 1 & Object2ShortOpenHashMap.this.mask;
/*      */         } 
/*  756 */         if (pos < last) {
/*  757 */           if (this.wrapped == null)
/*  758 */             this.wrapped = new ObjectArrayList<>(2); 
/*  759 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  761 */         key[last] = curr;
/*  762 */         Object2ShortOpenHashMap.this.value[last] = Object2ShortOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  766 */       if (this.last == -1)
/*  767 */         throw new IllegalStateException(); 
/*  768 */       if (this.last == Object2ShortOpenHashMap.this.n) {
/*  769 */         Object2ShortOpenHashMap.this.containsNullKey = false;
/*  770 */         Object2ShortOpenHashMap.this.key[Object2ShortOpenHashMap.this.n] = null;
/*  771 */       } else if (this.pos >= 0) {
/*  772 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  775 */         Object2ShortOpenHashMap.this.removeShort(this.wrapped.set(-this.pos - 1, null));
/*  776 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  779 */       Object2ShortOpenHashMap.this.size--;
/*  780 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  785 */       int i = n;
/*  786 */       while (i-- != 0 && hasNext())
/*  787 */         nextEntry(); 
/*  788 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Object2ShortMap.Entry<K>> { private Object2ShortOpenHashMap<K>.MapEntry entry;
/*      */     
/*      */     public Object2ShortOpenHashMap<K>.MapEntry next() {
/*  795 */       return this.entry = new Object2ShortOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  799 */       super.remove();
/*  800 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Object2ShortMap.Entry<K>> { private FastEntryIterator() {
/*  804 */       this.entry = new Object2ShortOpenHashMap.MapEntry();
/*      */     } private final Object2ShortOpenHashMap<K>.MapEntry entry;
/*      */     public Object2ShortOpenHashMap<K>.MapEntry next() {
/*  807 */       this.entry.index = nextEntry();
/*  808 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Object2ShortMap.Entry<K>> implements Object2ShortMap.FastEntrySet<K> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Object2ShortMap.Entry<K>> iterator() {
/*  814 */       return new Object2ShortOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Object2ShortMap.Entry<K>> fastIterator() {
/*  818 */       return new Object2ShortOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  823 */       if (!(o instanceof Map.Entry))
/*  824 */         return false; 
/*  825 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  826 */       if (e.getValue() == null || !(e.getValue() instanceof Short))
/*  827 */         return false; 
/*  828 */       K k = (K)e.getKey();
/*  829 */       short v = ((Short)e.getValue()).shortValue();
/*  830 */       if (k == null) {
/*  831 */         return (Object2ShortOpenHashMap.this.containsNullKey && Object2ShortOpenHashMap.this.value[Object2ShortOpenHashMap.this.n] == v);
/*      */       }
/*  833 */       K[] key = Object2ShortOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  836 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2ShortOpenHashMap.this.mask]) == null)
/*  837 */         return false; 
/*  838 */       if (k.equals(curr)) {
/*  839 */         return (Object2ShortOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  842 */         if ((curr = key[pos = pos + 1 & Object2ShortOpenHashMap.this.mask]) == null)
/*  843 */           return false; 
/*  844 */         if (k.equals(curr)) {
/*  845 */           return (Object2ShortOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  851 */       if (!(o instanceof Map.Entry))
/*  852 */         return false; 
/*  853 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  854 */       if (e.getValue() == null || !(e.getValue() instanceof Short))
/*  855 */         return false; 
/*  856 */       K k = (K)e.getKey();
/*  857 */       short v = ((Short)e.getValue()).shortValue();
/*  858 */       if (k == null) {
/*  859 */         if (Object2ShortOpenHashMap.this.containsNullKey && Object2ShortOpenHashMap.this.value[Object2ShortOpenHashMap.this.n] == v) {
/*  860 */           Object2ShortOpenHashMap.this.removeNullEntry();
/*  861 */           return true;
/*      */         } 
/*  863 */         return false;
/*      */       } 
/*      */       
/*  866 */       K[] key = Object2ShortOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  869 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2ShortOpenHashMap.this.mask]) == null)
/*  870 */         return false; 
/*  871 */       if (curr.equals(k)) {
/*  872 */         if (Object2ShortOpenHashMap.this.value[pos] == v) {
/*  873 */           Object2ShortOpenHashMap.this.removeEntry(pos);
/*  874 */           return true;
/*      */         } 
/*  876 */         return false;
/*      */       } 
/*      */       while (true) {
/*  879 */         if ((curr = key[pos = pos + 1 & Object2ShortOpenHashMap.this.mask]) == null)
/*  880 */           return false; 
/*  881 */         if (curr.equals(k) && 
/*  882 */           Object2ShortOpenHashMap.this.value[pos] == v) {
/*  883 */           Object2ShortOpenHashMap.this.removeEntry(pos);
/*  884 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  891 */       return Object2ShortOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  895 */       Object2ShortOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Object2ShortMap.Entry<K>> consumer) {
/*  900 */       if (Object2ShortOpenHashMap.this.containsNullKey)
/*  901 */         consumer.accept(new AbstractObject2ShortMap.BasicEntry<>(Object2ShortOpenHashMap.this.key[Object2ShortOpenHashMap.this.n], Object2ShortOpenHashMap.this.value[Object2ShortOpenHashMap.this.n])); 
/*  902 */       for (int pos = Object2ShortOpenHashMap.this.n; pos-- != 0;) {
/*  903 */         if (Object2ShortOpenHashMap.this.key[pos] != null)
/*  904 */           consumer.accept(new AbstractObject2ShortMap.BasicEntry<>(Object2ShortOpenHashMap.this.key[pos], Object2ShortOpenHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Object2ShortMap.Entry<K>> consumer) {
/*  909 */       AbstractObject2ShortMap.BasicEntry<K> entry = new AbstractObject2ShortMap.BasicEntry<>();
/*  910 */       if (Object2ShortOpenHashMap.this.containsNullKey) {
/*  911 */         entry.key = Object2ShortOpenHashMap.this.key[Object2ShortOpenHashMap.this.n];
/*  912 */         entry.value = Object2ShortOpenHashMap.this.value[Object2ShortOpenHashMap.this.n];
/*  913 */         consumer.accept(entry);
/*      */       } 
/*  915 */       for (int pos = Object2ShortOpenHashMap.this.n; pos-- != 0;) {
/*  916 */         if (Object2ShortOpenHashMap.this.key[pos] != null) {
/*  917 */           entry.key = Object2ShortOpenHashMap.this.key[pos];
/*  918 */           entry.value = Object2ShortOpenHashMap.this.value[pos];
/*  919 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Object2ShortMap.FastEntrySet<K> object2ShortEntrySet() {
/*  925 */     if (this.entries == null)
/*  926 */       this.entries = new MapEntrySet(); 
/*  927 */     return this.entries;
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
/*  944 */       return Object2ShortOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractObjectSet<K> { private KeySet() {}
/*      */     
/*      */     public ObjectIterator<K> iterator() {
/*  950 */       return new Object2ShortOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/*  955 */       if (Object2ShortOpenHashMap.this.containsNullKey)
/*  956 */         consumer.accept(Object2ShortOpenHashMap.this.key[Object2ShortOpenHashMap.this.n]); 
/*  957 */       for (int pos = Object2ShortOpenHashMap.this.n; pos-- != 0; ) {
/*  958 */         K k = Object2ShortOpenHashMap.this.key[pos];
/*  959 */         if (k != null)
/*  960 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  965 */       return Object2ShortOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(Object k) {
/*  969 */       return Object2ShortOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(Object k) {
/*  973 */       int oldSize = Object2ShortOpenHashMap.this.size;
/*  974 */       Object2ShortOpenHashMap.this.removeShort(k);
/*  975 */       return (Object2ShortOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  979 */       Object2ShortOpenHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ObjectSet<K> keySet() {
/*  984 */     if (this.keys == null)
/*  985 */       this.keys = new KeySet(); 
/*  986 */     return this.keys;
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
/*      */     implements ShortIterator
/*      */   {
/*      */     public short nextShort() {
/* 1003 */       return Object2ShortOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public ShortCollection values() {
/* 1008 */     if (this.values == null)
/* 1009 */       this.values = (ShortCollection)new AbstractShortCollection()
/*      */         {
/*      */           public ShortIterator iterator() {
/* 1012 */             return new Object2ShortOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1016 */             return Object2ShortOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(short v) {
/* 1020 */             return Object2ShortOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1024 */             Object2ShortOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(IntConsumer consumer)
/*      */           {
/* 1029 */             if (Object2ShortOpenHashMap.this.containsNullKey)
/* 1030 */               consumer.accept(Object2ShortOpenHashMap.this.value[Object2ShortOpenHashMap.this.n]); 
/* 1031 */             for (int pos = Object2ShortOpenHashMap.this.n; pos-- != 0;) {
/* 1032 */               if (Object2ShortOpenHashMap.this.key[pos] != null)
/* 1033 */                 consumer.accept(Object2ShortOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1036 */     return this.values;
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
/* 1053 */     return trim(this.size);
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
/* 1077 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1078 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1079 */       return true; 
/*      */     try {
/* 1081 */       rehash(l);
/* 1082 */     } catch (OutOfMemoryError cantDoIt) {
/* 1083 */       return false;
/*      */     } 
/* 1085 */     return true;
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
/* 1101 */     K[] key = this.key;
/* 1102 */     short[] value = this.value;
/* 1103 */     int mask = newN - 1;
/* 1104 */     K[] newKey = (K[])new Object[newN + 1];
/* 1105 */     short[] newValue = new short[newN + 1];
/* 1106 */     int i = this.n;
/* 1107 */     for (int j = realSize(); j-- != 0; ) {
/* 1108 */       while (key[--i] == null); int pos;
/* 1109 */       if (newKey[pos = HashCommon.mix(key[i].hashCode()) & mask] != null)
/* 1110 */         while (newKey[pos = pos + 1 & mask] != null); 
/* 1111 */       newKey[pos] = key[i];
/* 1112 */       newValue[pos] = value[i];
/*      */     } 
/* 1114 */     newValue[newN] = value[this.n];
/* 1115 */     this.n = newN;
/* 1116 */     this.mask = mask;
/* 1117 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1118 */     this.key = newKey;
/* 1119 */     this.value = newValue;
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
/*      */   public Object2ShortOpenHashMap<K> clone() {
/*      */     Object2ShortOpenHashMap<K> c;
/*      */     try {
/* 1136 */       c = (Object2ShortOpenHashMap<K>)super.clone();
/* 1137 */     } catch (CloneNotSupportedException cantHappen) {
/* 1138 */       throw new InternalError();
/*      */     } 
/* 1140 */     c.keys = null;
/* 1141 */     c.values = null;
/* 1142 */     c.entries = null;
/* 1143 */     c.containsNullKey = this.containsNullKey;
/* 1144 */     c.key = (K[])this.key.clone();
/* 1145 */     c.value = (short[])this.value.clone();
/* 1146 */     return c;
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
/* 1159 */     int h = 0;
/* 1160 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1161 */       while (this.key[i] == null)
/* 1162 */         i++; 
/* 1163 */       if (this != this.key[i])
/* 1164 */         t = this.key[i].hashCode(); 
/* 1165 */       t ^= this.value[i];
/* 1166 */       h += t;
/* 1167 */       i++;
/*      */     } 
/*      */     
/* 1170 */     if (this.containsNullKey)
/* 1171 */       h += this.value[this.n]; 
/* 1172 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1175 */     K[] key = this.key;
/* 1176 */     short[] value = this.value;
/* 1177 */     MapIterator i = new MapIterator();
/* 1178 */     s.defaultWriteObject();
/* 1179 */     for (int j = this.size; j-- != 0; ) {
/* 1180 */       int e = i.nextEntry();
/* 1181 */       s.writeObject(key[e]);
/* 1182 */       s.writeShort(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1187 */     s.defaultReadObject();
/* 1188 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1189 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1190 */     this.mask = this.n - 1;
/* 1191 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1192 */     short[] value = this.value = new short[this.n + 1];
/*      */ 
/*      */     
/* 1195 */     for (int i = this.size; i-- != 0; ) {
/* 1196 */       int pos; K k = (K)s.readObject();
/* 1197 */       short v = s.readShort();
/* 1198 */       if (k == null) {
/* 1199 */         pos = this.n;
/* 1200 */         this.containsNullKey = true;
/*      */       } else {
/* 1202 */         pos = HashCommon.mix(k.hashCode()) & this.mask;
/* 1203 */         while (key[pos] != null)
/* 1204 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1206 */       key[pos] = k;
/* 1207 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2ShortOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */