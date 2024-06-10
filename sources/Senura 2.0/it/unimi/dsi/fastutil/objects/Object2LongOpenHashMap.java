/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
/*      */ import it.unimi.dsi.fastutil.longs.LongCollection;
/*      */ import it.unimi.dsi.fastutil.longs.LongIterator;
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
/*      */ import java.util.function.LongConsumer;
/*      */ import java.util.function.ToLongFunction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Object2LongOpenHashMap<K>
/*      */   extends AbstractObject2LongMap<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient long[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Object2LongMap.FastEntrySet<K> entries;
/*      */   protected transient ObjectSet<K> keys;
/*      */   protected transient LongCollection values;
/*      */   
/*      */   public Object2LongOpenHashMap(int expected, float f) {
/*  100 */     if (f <= 0.0F || f > 1.0F)
/*  101 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  102 */     if (expected < 0)
/*  103 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  104 */     this.f = f;
/*  105 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  106 */     this.mask = this.n - 1;
/*  107 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  108 */     this.key = (K[])new Object[this.n + 1];
/*  109 */     this.value = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2LongOpenHashMap(int expected) {
/*  118 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2LongOpenHashMap() {
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
/*      */   public Object2LongOpenHashMap(Map<? extends K, ? extends Long> m, float f) {
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
/*      */   public Object2LongOpenHashMap(Map<? extends K, ? extends Long> m) {
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
/*      */   public Object2LongOpenHashMap(Object2LongMap<K> m, float f) {
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
/*      */   public Object2LongOpenHashMap(Object2LongMap<K> m) {
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
/*      */   public Object2LongOpenHashMap(K[] k, long[] v, float f) {
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
/*      */   public Object2LongOpenHashMap(K[] k, long[] v) {
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
/*      */   private long removeEntry(int pos) {
/*  221 */     long oldValue = this.value[pos];
/*  222 */     this.size--;
/*  223 */     shiftKeys(pos);
/*  224 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  225 */       rehash(this.n / 2); 
/*  226 */     return oldValue;
/*      */   }
/*      */   private long removeNullEntry() {
/*  229 */     this.containsNullKey = false;
/*  230 */     this.key[this.n] = null;
/*  231 */     long oldValue = this.value[this.n];
/*  232 */     this.size--;
/*  233 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  234 */       rehash(this.n / 2); 
/*  235 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends Long> m) {
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
/*      */   private void insert(int pos, K k, long v) {
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
/*      */   public long put(K k, long v) {
/*  278 */     int pos = find(k);
/*  279 */     if (pos < 0) {
/*  280 */       insert(-pos - 1, k, v);
/*  281 */       return this.defRetValue;
/*      */     } 
/*  283 */     long oldValue = this.value[pos];
/*  284 */     this.value[pos] = v;
/*  285 */     return oldValue;
/*      */   }
/*      */   private long addToValue(int pos, long incr) {
/*  288 */     long oldValue = this.value[pos];
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
/*      */   public long addTo(K k, long incr) {
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
/*      */   public long removeLong(Object k) {
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
/*      */   public long getLong(Object k) {
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
/*      */   public boolean containsValue(long v) {
/*  430 */     long[] value = this.value;
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
/*      */   public long getOrDefault(Object k, long defaultValue) {
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
/*      */   public long putIfAbsent(K k, long v) {
/*  464 */     int pos = find(k);
/*  465 */     if (pos >= 0)
/*  466 */       return this.value[pos]; 
/*  467 */     insert(-pos - 1, k, v);
/*  468 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, long v) {
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
/*      */   public boolean replace(K k, long oldValue, long v) {
/*  503 */     int pos = find(k);
/*  504 */     if (pos < 0 || oldValue != this.value[pos])
/*  505 */       return false; 
/*  506 */     this.value[pos] = v;
/*  507 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public long replace(K k, long v) {
/*  512 */     int pos = find(k);
/*  513 */     if (pos < 0)
/*  514 */       return this.defRetValue; 
/*  515 */     long oldValue = this.value[pos];
/*  516 */     this.value[pos] = v;
/*  517 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public long computeLongIfAbsent(K k, ToLongFunction<? super K> mappingFunction) {
/*  522 */     Objects.requireNonNull(mappingFunction);
/*  523 */     int pos = find(k);
/*  524 */     if (pos >= 0)
/*  525 */       return this.value[pos]; 
/*  526 */     long newValue = mappingFunction.applyAsLong(k);
/*  527 */     insert(-pos - 1, k, newValue);
/*  528 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeLongIfPresent(K k, BiFunction<? super K, ? super Long, ? extends Long> remappingFunction) {
/*  534 */     Objects.requireNonNull(remappingFunction);
/*  535 */     int pos = find(k);
/*  536 */     if (pos < 0)
/*  537 */       return this.defRetValue; 
/*  538 */     Long newValue = remappingFunction.apply(k, Long.valueOf(this.value[pos]));
/*  539 */     if (newValue == null) {
/*  540 */       if (k == null) {
/*  541 */         removeNullEntry();
/*      */       } else {
/*  543 */         removeEntry(pos);
/*  544 */       }  return this.defRetValue;
/*      */     } 
/*  546 */     this.value[pos] = newValue.longValue(); return newValue.longValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeLong(K k, BiFunction<? super K, ? super Long, ? extends Long> remappingFunction) {
/*  552 */     Objects.requireNonNull(remappingFunction);
/*  553 */     int pos = find(k);
/*  554 */     Long newValue = remappingFunction.apply(k, (pos >= 0) ? Long.valueOf(this.value[pos]) : null);
/*  555 */     if (newValue == null) {
/*  556 */       if (pos >= 0)
/*  557 */         if (k == null) {
/*  558 */           removeNullEntry();
/*      */         } else {
/*  560 */           removeEntry(pos);
/*      */         }  
/*  562 */       return this.defRetValue;
/*      */     } 
/*  564 */     long newVal = newValue.longValue();
/*  565 */     if (pos < 0) {
/*  566 */       insert(-pos - 1, k, newVal);
/*  567 */       return newVal;
/*      */     } 
/*  569 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long mergeLong(K k, long v, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/*  575 */     Objects.requireNonNull(remappingFunction);
/*  576 */     int pos = find(k);
/*  577 */     if (pos < 0) {
/*  578 */       insert(-pos - 1, k, v);
/*  579 */       return v;
/*      */     } 
/*  581 */     Long newValue = remappingFunction.apply(Long.valueOf(this.value[pos]), Long.valueOf(v));
/*  582 */     if (newValue == null) {
/*  583 */       if (k == null) {
/*  584 */         removeNullEntry();
/*      */       } else {
/*  586 */         removeEntry(pos);
/*  587 */       }  return this.defRetValue;
/*      */     } 
/*  589 */     this.value[pos] = newValue.longValue(); return newValue.longValue();
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
/*      */     implements Object2LongMap.Entry<K>, Map.Entry<K, Long>
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
/*  630 */       return Object2LongOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public long getLongValue() {
/*  634 */       return Object2LongOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public long setValue(long v) {
/*  638 */       long oldValue = Object2LongOpenHashMap.this.value[this.index];
/*  639 */       Object2LongOpenHashMap.this.value[this.index] = v;
/*  640 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long getValue() {
/*  650 */       return Long.valueOf(Object2LongOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long setValue(Long v) {
/*  660 */       return Long.valueOf(setValue(v.longValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  665 */       if (!(o instanceof Map.Entry))
/*  666 */         return false; 
/*  667 */       Map.Entry<K, Long> e = (Map.Entry<K, Long>)o;
/*  668 */       return (Objects.equals(Object2LongOpenHashMap.this.key[this.index], e.getKey()) && Object2LongOpenHashMap.this.value[this.index] == ((Long)e
/*  669 */         .getValue()).longValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  673 */       return ((Object2LongOpenHashMap.this.key[this.index] == null) ? 0 : Object2LongOpenHashMap.this.key[this.index].hashCode()) ^ 
/*  674 */         HashCommon.long2int(Object2LongOpenHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  678 */       return (new StringBuilder()).append(Object2LongOpenHashMap.this.key[this.index]).append("=>").append(Object2LongOpenHashMap.this.value[this.index]).toString();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  688 */     int pos = Object2LongOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  695 */     int last = -1;
/*      */     
/*  697 */     int c = Object2LongOpenHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  701 */     boolean mustReturnNullKey = Object2LongOpenHashMap.this.containsNullKey;
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
/*  716 */         return this.last = Object2LongOpenHashMap.this.n;
/*      */       } 
/*  718 */       K[] key = Object2LongOpenHashMap.this.key;
/*      */       while (true) {
/*  720 */         if (--this.pos < 0) {
/*      */           
/*  722 */           this.last = Integer.MIN_VALUE;
/*  723 */           K k = this.wrapped.get(-this.pos - 1);
/*  724 */           int p = HashCommon.mix(k.hashCode()) & Object2LongOpenHashMap.this.mask;
/*  725 */           while (!k.equals(key[p]))
/*  726 */             p = p + 1 & Object2LongOpenHashMap.this.mask; 
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
/*  744 */       K[] key = Object2LongOpenHashMap.this.key; while (true) {
/*      */         K curr; int last;
/*  746 */         pos = (last = pos) + 1 & Object2LongOpenHashMap.this.mask;
/*      */         while (true) {
/*  748 */           if ((curr = key[pos]) == null) {
/*  749 */             key[last] = null;
/*      */             return;
/*      */           } 
/*  752 */           int slot = HashCommon.mix(curr.hashCode()) & Object2LongOpenHashMap.this.mask;
/*  753 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  755 */           pos = pos + 1 & Object2LongOpenHashMap.this.mask;
/*      */         } 
/*  757 */         if (pos < last) {
/*  758 */           if (this.wrapped == null)
/*  759 */             this.wrapped = new ObjectArrayList<>(2); 
/*  760 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  762 */         key[last] = curr;
/*  763 */         Object2LongOpenHashMap.this.value[last] = Object2LongOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  767 */       if (this.last == -1)
/*  768 */         throw new IllegalStateException(); 
/*  769 */       if (this.last == Object2LongOpenHashMap.this.n) {
/*  770 */         Object2LongOpenHashMap.this.containsNullKey = false;
/*  771 */         Object2LongOpenHashMap.this.key[Object2LongOpenHashMap.this.n] = null;
/*  772 */       } else if (this.pos >= 0) {
/*  773 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  776 */         Object2LongOpenHashMap.this.removeLong(this.wrapped.set(-this.pos - 1, null));
/*  777 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  780 */       Object2LongOpenHashMap.this.size--;
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
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Object2LongMap.Entry<K>> { private Object2LongOpenHashMap<K>.MapEntry entry;
/*      */     
/*      */     public Object2LongOpenHashMap<K>.MapEntry next() {
/*  796 */       return this.entry = new Object2LongOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  800 */       super.remove();
/*  801 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Object2LongMap.Entry<K>> { private FastEntryIterator() {
/*  805 */       this.entry = new Object2LongOpenHashMap.MapEntry();
/*      */     } private final Object2LongOpenHashMap<K>.MapEntry entry;
/*      */     public Object2LongOpenHashMap<K>.MapEntry next() {
/*  808 */       this.entry.index = nextEntry();
/*  809 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Object2LongMap.Entry<K>> implements Object2LongMap.FastEntrySet<K> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Object2LongMap.Entry<K>> iterator() {
/*  815 */       return new Object2LongOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Object2LongMap.Entry<K>> fastIterator() {
/*  819 */       return new Object2LongOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  824 */       if (!(o instanceof Map.Entry))
/*  825 */         return false; 
/*  826 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  827 */       if (e.getValue() == null || !(e.getValue() instanceof Long))
/*  828 */         return false; 
/*  829 */       K k = (K)e.getKey();
/*  830 */       long v = ((Long)e.getValue()).longValue();
/*  831 */       if (k == null) {
/*  832 */         return (Object2LongOpenHashMap.this.containsNullKey && Object2LongOpenHashMap.this.value[Object2LongOpenHashMap.this.n] == v);
/*      */       }
/*  834 */       K[] key = Object2LongOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  837 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2LongOpenHashMap.this.mask]) == null)
/*  838 */         return false; 
/*  839 */       if (k.equals(curr)) {
/*  840 */         return (Object2LongOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  843 */         if ((curr = key[pos = pos + 1 & Object2LongOpenHashMap.this.mask]) == null)
/*  844 */           return false; 
/*  845 */         if (k.equals(curr)) {
/*  846 */           return (Object2LongOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  852 */       if (!(o instanceof Map.Entry))
/*  853 */         return false; 
/*  854 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  855 */       if (e.getValue() == null || !(e.getValue() instanceof Long))
/*  856 */         return false; 
/*  857 */       K k = (K)e.getKey();
/*  858 */       long v = ((Long)e.getValue()).longValue();
/*  859 */       if (k == null) {
/*  860 */         if (Object2LongOpenHashMap.this.containsNullKey && Object2LongOpenHashMap.this.value[Object2LongOpenHashMap.this.n] == v) {
/*  861 */           Object2LongOpenHashMap.this.removeNullEntry();
/*  862 */           return true;
/*      */         } 
/*  864 */         return false;
/*      */       } 
/*      */       
/*  867 */       K[] key = Object2LongOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  870 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2LongOpenHashMap.this.mask]) == null)
/*  871 */         return false; 
/*  872 */       if (curr.equals(k)) {
/*  873 */         if (Object2LongOpenHashMap.this.value[pos] == v) {
/*  874 */           Object2LongOpenHashMap.this.removeEntry(pos);
/*  875 */           return true;
/*      */         } 
/*  877 */         return false;
/*      */       } 
/*      */       while (true) {
/*  880 */         if ((curr = key[pos = pos + 1 & Object2LongOpenHashMap.this.mask]) == null)
/*  881 */           return false; 
/*  882 */         if (curr.equals(k) && 
/*  883 */           Object2LongOpenHashMap.this.value[pos] == v) {
/*  884 */           Object2LongOpenHashMap.this.removeEntry(pos);
/*  885 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  892 */       return Object2LongOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  896 */       Object2LongOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Object2LongMap.Entry<K>> consumer) {
/*  901 */       if (Object2LongOpenHashMap.this.containsNullKey)
/*  902 */         consumer.accept(new AbstractObject2LongMap.BasicEntry<>(Object2LongOpenHashMap.this.key[Object2LongOpenHashMap.this.n], Object2LongOpenHashMap.this.value[Object2LongOpenHashMap.this.n])); 
/*  903 */       for (int pos = Object2LongOpenHashMap.this.n; pos-- != 0;) {
/*  904 */         if (Object2LongOpenHashMap.this.key[pos] != null)
/*  905 */           consumer.accept(new AbstractObject2LongMap.BasicEntry<>(Object2LongOpenHashMap.this.key[pos], Object2LongOpenHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Object2LongMap.Entry<K>> consumer) {
/*  910 */       AbstractObject2LongMap.BasicEntry<K> entry = new AbstractObject2LongMap.BasicEntry<>();
/*  911 */       if (Object2LongOpenHashMap.this.containsNullKey) {
/*  912 */         entry.key = Object2LongOpenHashMap.this.key[Object2LongOpenHashMap.this.n];
/*  913 */         entry.value = Object2LongOpenHashMap.this.value[Object2LongOpenHashMap.this.n];
/*  914 */         consumer.accept(entry);
/*      */       } 
/*  916 */       for (int pos = Object2LongOpenHashMap.this.n; pos-- != 0;) {
/*  917 */         if (Object2LongOpenHashMap.this.key[pos] != null) {
/*  918 */           entry.key = Object2LongOpenHashMap.this.key[pos];
/*  919 */           entry.value = Object2LongOpenHashMap.this.value[pos];
/*  920 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Object2LongMap.FastEntrySet<K> object2LongEntrySet() {
/*  926 */     if (this.entries == null)
/*  927 */       this.entries = new MapEntrySet(); 
/*  928 */     return this.entries;
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
/*  945 */       return Object2LongOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractObjectSet<K> { private KeySet() {}
/*      */     
/*      */     public ObjectIterator<K> iterator() {
/*  951 */       return new Object2LongOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/*  956 */       if (Object2LongOpenHashMap.this.containsNullKey)
/*  957 */         consumer.accept(Object2LongOpenHashMap.this.key[Object2LongOpenHashMap.this.n]); 
/*  958 */       for (int pos = Object2LongOpenHashMap.this.n; pos-- != 0; ) {
/*  959 */         K k = Object2LongOpenHashMap.this.key[pos];
/*  960 */         if (k != null)
/*  961 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  966 */       return Object2LongOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(Object k) {
/*  970 */       return Object2LongOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(Object k) {
/*  974 */       int oldSize = Object2LongOpenHashMap.this.size;
/*  975 */       Object2LongOpenHashMap.this.removeLong(k);
/*  976 */       return (Object2LongOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  980 */       Object2LongOpenHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ObjectSet<K> keySet() {
/*  985 */     if (this.keys == null)
/*  986 */       this.keys = new KeySet(); 
/*  987 */     return this.keys;
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
/*      */     implements LongIterator
/*      */   {
/*      */     public long nextLong() {
/* 1004 */       return Object2LongOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public LongCollection values() {
/* 1009 */     if (this.values == null)
/* 1010 */       this.values = (LongCollection)new AbstractLongCollection()
/*      */         {
/*      */           public LongIterator iterator() {
/* 1013 */             return new Object2LongOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1017 */             return Object2LongOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(long v) {
/* 1021 */             return Object2LongOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1025 */             Object2LongOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(LongConsumer consumer)
/*      */           {
/* 1030 */             if (Object2LongOpenHashMap.this.containsNullKey)
/* 1031 */               consumer.accept(Object2LongOpenHashMap.this.value[Object2LongOpenHashMap.this.n]); 
/* 1032 */             for (int pos = Object2LongOpenHashMap.this.n; pos-- != 0;) {
/* 1033 */               if (Object2LongOpenHashMap.this.key[pos] != null)
/* 1034 */                 consumer.accept(Object2LongOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1037 */     return this.values;
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
/* 1054 */     return trim(this.size);
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
/* 1078 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1079 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1080 */       return true; 
/*      */     try {
/* 1082 */       rehash(l);
/* 1083 */     } catch (OutOfMemoryError cantDoIt) {
/* 1084 */       return false;
/*      */     } 
/* 1086 */     return true;
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
/* 1102 */     K[] key = this.key;
/* 1103 */     long[] value = this.value;
/* 1104 */     int mask = newN - 1;
/* 1105 */     K[] newKey = (K[])new Object[newN + 1];
/* 1106 */     long[] newValue = new long[newN + 1];
/* 1107 */     int i = this.n;
/* 1108 */     for (int j = realSize(); j-- != 0; ) {
/* 1109 */       while (key[--i] == null); int pos;
/* 1110 */       if (newKey[pos = HashCommon.mix(key[i].hashCode()) & mask] != null)
/* 1111 */         while (newKey[pos = pos + 1 & mask] != null); 
/* 1112 */       newKey[pos] = key[i];
/* 1113 */       newValue[pos] = value[i];
/*      */     } 
/* 1115 */     newValue[newN] = value[this.n];
/* 1116 */     this.n = newN;
/* 1117 */     this.mask = mask;
/* 1118 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1119 */     this.key = newKey;
/* 1120 */     this.value = newValue;
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
/*      */   public Object2LongOpenHashMap<K> clone() {
/*      */     Object2LongOpenHashMap<K> c;
/*      */     try {
/* 1137 */       c = (Object2LongOpenHashMap<K>)super.clone();
/* 1138 */     } catch (CloneNotSupportedException cantHappen) {
/* 1139 */       throw new InternalError();
/*      */     } 
/* 1141 */     c.keys = null;
/* 1142 */     c.values = null;
/* 1143 */     c.entries = null;
/* 1144 */     c.containsNullKey = this.containsNullKey;
/* 1145 */     c.key = (K[])this.key.clone();
/* 1146 */     c.value = (long[])this.value.clone();
/* 1147 */     return c;
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
/* 1160 */     int h = 0;
/* 1161 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1162 */       while (this.key[i] == null)
/* 1163 */         i++; 
/* 1164 */       if (this != this.key[i])
/* 1165 */         t = this.key[i].hashCode(); 
/* 1166 */       t ^= HashCommon.long2int(this.value[i]);
/* 1167 */       h += t;
/* 1168 */       i++;
/*      */     } 
/*      */     
/* 1171 */     if (this.containsNullKey)
/* 1172 */       h += HashCommon.long2int(this.value[this.n]); 
/* 1173 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1176 */     K[] key = this.key;
/* 1177 */     long[] value = this.value;
/* 1178 */     MapIterator i = new MapIterator();
/* 1179 */     s.defaultWriteObject();
/* 1180 */     for (int j = this.size; j-- != 0; ) {
/* 1181 */       int e = i.nextEntry();
/* 1182 */       s.writeObject(key[e]);
/* 1183 */       s.writeLong(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1188 */     s.defaultReadObject();
/* 1189 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1190 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1191 */     this.mask = this.n - 1;
/* 1192 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1193 */     long[] value = this.value = new long[this.n + 1];
/*      */ 
/*      */     
/* 1196 */     for (int i = this.size; i-- != 0; ) {
/* 1197 */       int pos; K k = (K)s.readObject();
/* 1198 */       long v = s.readLong();
/* 1199 */       if (k == null) {
/* 1200 */         pos = this.n;
/* 1201 */         this.containsNullKey = true;
/*      */       } else {
/* 1203 */         pos = HashCommon.mix(k.hashCode()) & this.mask;
/* 1204 */         while (key[pos] != null)
/* 1205 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1207 */       key[pos] = k;
/* 1208 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2LongOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */