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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Object2LongOpenCustomHashMap<K>
/*      */   extends AbstractObject2LongMap<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient long[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected Hash.Strategy<? super K> strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Object2LongMap.FastEntrySet<K> entries;
/*      */   protected transient ObjectSet<K> keys;
/*      */   protected transient LongCollection values;
/*      */   
/*      */   public Object2LongOpenCustomHashMap(int expected, float f, Hash.Strategy<? super K> strategy) {
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
/*  116 */     this.value = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2LongOpenCustomHashMap(int expected, Hash.Strategy<? super K> strategy) {
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
/*      */   public Object2LongOpenCustomHashMap(Hash.Strategy<? super K> strategy) {
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
/*      */   public Object2LongOpenCustomHashMap(Map<? extends K, ? extends Long> m, float f, Hash.Strategy<? super K> strategy) {
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
/*      */   public Object2LongOpenCustomHashMap(Map<? extends K, ? extends Long> m, Hash.Strategy<? super K> strategy) {
/*  165 */     this(m, 0.75F, strategy);
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
/*      */   public Object2LongOpenCustomHashMap(Object2LongMap<K> m, float f, Hash.Strategy<? super K> strategy) {
/*  178 */     this(m.size(), f, strategy);
/*  179 */     putAll(m);
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
/*      */   public Object2LongOpenCustomHashMap(Object2LongMap<K> m, Hash.Strategy<? super K> strategy) {
/*  191 */     this(m, 0.75F, strategy);
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
/*      */   public Object2LongOpenCustomHashMap(K[] k, long[] v, float f, Hash.Strategy<? super K> strategy) {
/*  209 */     this(k.length, f, strategy);
/*  210 */     if (k.length != v.length) {
/*  211 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  213 */     for (int i = 0; i < k.length; i++) {
/*  214 */       put(k[i], v[i]);
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
/*      */   public Object2LongOpenCustomHashMap(K[] k, long[] v, Hash.Strategy<? super K> strategy) {
/*  230 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Hash.Strategy<? super K> strategy() {
/*  238 */     return this.strategy;
/*      */   }
/*      */   private int realSize() {
/*  241 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  244 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  245 */     if (needed > this.n)
/*  246 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  249 */     int needed = (int)Math.min(1073741824L, 
/*  250 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  251 */     if (needed > this.n)
/*  252 */       rehash(needed); 
/*      */   }
/*      */   private long removeEntry(int pos) {
/*  255 */     long oldValue = this.value[pos];
/*  256 */     this.size--;
/*  257 */     shiftKeys(pos);
/*  258 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  259 */       rehash(this.n / 2); 
/*  260 */     return oldValue;
/*      */   }
/*      */   private long removeNullEntry() {
/*  263 */     this.containsNullKey = false;
/*  264 */     this.key[this.n] = null;
/*  265 */     long oldValue = this.value[this.n];
/*  266 */     this.size--;
/*  267 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  268 */       rehash(this.n / 2); 
/*  269 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends Long> m) {
/*  273 */     if (this.f <= 0.5D) {
/*  274 */       ensureCapacity(m.size());
/*      */     } else {
/*  276 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  278 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(K k) {
/*  282 */     if (this.strategy.equals(k, null)) {
/*  283 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  285 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  288 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  289 */       return -(pos + 1); 
/*  290 */     if (this.strategy.equals(k, curr)) {
/*  291 */       return pos;
/*      */     }
/*      */     while (true) {
/*  294 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  295 */         return -(pos + 1); 
/*  296 */       if (this.strategy.equals(k, curr))
/*  297 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, K k, long v) {
/*  301 */     if (pos == this.n)
/*  302 */       this.containsNullKey = true; 
/*  303 */     this.key[pos] = k;
/*  304 */     this.value[pos] = v;
/*  305 */     if (this.size++ >= this.maxFill) {
/*  306 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public long put(K k, long v) {
/*  312 */     int pos = find(k);
/*  313 */     if (pos < 0) {
/*  314 */       insert(-pos - 1, k, v);
/*  315 */       return this.defRetValue;
/*      */     } 
/*  317 */     long oldValue = this.value[pos];
/*  318 */     this.value[pos] = v;
/*  319 */     return oldValue;
/*      */   }
/*      */   private long addToValue(int pos, long incr) {
/*  322 */     long oldValue = this.value[pos];
/*  323 */     this.value[pos] = oldValue + incr;
/*  324 */     return oldValue;
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
/*  344 */     if (this.strategy.equals(k, null)) {
/*  345 */       if (this.containsNullKey)
/*  346 */         return addToValue(this.n, incr); 
/*  347 */       pos = this.n;
/*  348 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  351 */       K[] key = this.key;
/*      */       K curr;
/*  353 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != null) {
/*  354 */         if (this.strategy.equals(curr, k))
/*  355 */           return addToValue(pos, incr); 
/*  356 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  357 */           if (this.strategy.equals(curr, k))
/*  358 */             return addToValue(pos, incr); 
/*      */         } 
/*      */       } 
/*  361 */     }  this.key[pos] = k;
/*  362 */     this.value[pos] = this.defRetValue + incr;
/*  363 */     if (this.size++ >= this.maxFill) {
/*  364 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  367 */     return this.defRetValue;
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
/*  380 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  382 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  384 */         if ((curr = key[pos]) == null) {
/*  385 */           key[last] = null;
/*      */           return;
/*      */         } 
/*  388 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  389 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  391 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  393 */       key[last] = curr;
/*  394 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public long removeLong(Object k) {
/*  400 */     if (this.strategy.equals(k, null)) {
/*  401 */       if (this.containsNullKey)
/*  402 */         return removeNullEntry(); 
/*  403 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  406 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  409 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  410 */       return this.defRetValue; 
/*  411 */     if (this.strategy.equals(k, curr))
/*  412 */       return removeEntry(pos); 
/*      */     while (true) {
/*  414 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  415 */         return this.defRetValue; 
/*  416 */       if (this.strategy.equals(k, curr)) {
/*  417 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public long getLong(Object k) {
/*  423 */     if (this.strategy.equals(k, null)) {
/*  424 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  426 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  429 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  430 */       return this.defRetValue; 
/*  431 */     if (this.strategy.equals(k, curr)) {
/*  432 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  435 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  436 */         return this.defRetValue; 
/*  437 */       if (this.strategy.equals(k, curr)) {
/*  438 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  444 */     if (this.strategy.equals(k, null)) {
/*  445 */       return this.containsNullKey;
/*      */     }
/*  447 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  450 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  451 */       return false; 
/*  452 */     if (this.strategy.equals(k, curr)) {
/*  453 */       return true;
/*      */     }
/*      */     while (true) {
/*  456 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  457 */         return false; 
/*  458 */       if (this.strategy.equals(k, curr))
/*  459 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(long v) {
/*  464 */     long[] value = this.value;
/*  465 */     K[] key = this.key;
/*  466 */     if (this.containsNullKey && value[this.n] == v)
/*  467 */       return true; 
/*  468 */     for (int i = this.n; i-- != 0;) {
/*  469 */       if (key[i] != null && value[i] == v)
/*  470 */         return true; 
/*  471 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long getOrDefault(Object k, long defaultValue) {
/*  477 */     if (this.strategy.equals(k, null)) {
/*  478 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  480 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  483 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  484 */       return defaultValue; 
/*  485 */     if (this.strategy.equals(k, curr)) {
/*  486 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  489 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  490 */         return defaultValue; 
/*  491 */       if (this.strategy.equals(k, curr)) {
/*  492 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public long putIfAbsent(K k, long v) {
/*  498 */     int pos = find(k);
/*  499 */     if (pos >= 0)
/*  500 */       return this.value[pos]; 
/*  501 */     insert(-pos - 1, k, v);
/*  502 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, long v) {
/*  508 */     if (this.strategy.equals(k, null)) {
/*  509 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  510 */         removeNullEntry();
/*  511 */         return true;
/*      */       } 
/*  513 */       return false;
/*      */     } 
/*      */     
/*  516 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  519 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  520 */       return false; 
/*  521 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  522 */       removeEntry(pos);
/*  523 */       return true;
/*      */     } 
/*      */     while (true) {
/*  526 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  527 */         return false; 
/*  528 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  529 */         removeEntry(pos);
/*  530 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(K k, long oldValue, long v) {
/*  537 */     int pos = find(k);
/*  538 */     if (pos < 0 || oldValue != this.value[pos])
/*  539 */       return false; 
/*  540 */     this.value[pos] = v;
/*  541 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public long replace(K k, long v) {
/*  546 */     int pos = find(k);
/*  547 */     if (pos < 0)
/*  548 */       return this.defRetValue; 
/*  549 */     long oldValue = this.value[pos];
/*  550 */     this.value[pos] = v;
/*  551 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public long computeLongIfAbsent(K k, ToLongFunction<? super K> mappingFunction) {
/*  556 */     Objects.requireNonNull(mappingFunction);
/*  557 */     int pos = find(k);
/*  558 */     if (pos >= 0)
/*  559 */       return this.value[pos]; 
/*  560 */     long newValue = mappingFunction.applyAsLong(k);
/*  561 */     insert(-pos - 1, k, newValue);
/*  562 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeLongIfPresent(K k, BiFunction<? super K, ? super Long, ? extends Long> remappingFunction) {
/*  568 */     Objects.requireNonNull(remappingFunction);
/*  569 */     int pos = find(k);
/*  570 */     if (pos < 0)
/*  571 */       return this.defRetValue; 
/*  572 */     Long newValue = remappingFunction.apply(k, Long.valueOf(this.value[pos]));
/*  573 */     if (newValue == null) {
/*  574 */       if (this.strategy.equals(k, null)) {
/*  575 */         removeNullEntry();
/*      */       } else {
/*  577 */         removeEntry(pos);
/*  578 */       }  return this.defRetValue;
/*      */     } 
/*  580 */     this.value[pos] = newValue.longValue(); return newValue.longValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeLong(K k, BiFunction<? super K, ? super Long, ? extends Long> remappingFunction) {
/*  586 */     Objects.requireNonNull(remappingFunction);
/*  587 */     int pos = find(k);
/*  588 */     Long newValue = remappingFunction.apply(k, (pos >= 0) ? Long.valueOf(this.value[pos]) : null);
/*  589 */     if (newValue == null) {
/*  590 */       if (pos >= 0)
/*  591 */         if (this.strategy.equals(k, null)) {
/*  592 */           removeNullEntry();
/*      */         } else {
/*  594 */           removeEntry(pos);
/*      */         }  
/*  596 */       return this.defRetValue;
/*      */     } 
/*  598 */     long newVal = newValue.longValue();
/*  599 */     if (pos < 0) {
/*  600 */       insert(-pos - 1, k, newVal);
/*  601 */       return newVal;
/*      */     } 
/*  603 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long mergeLong(K k, long v, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/*  609 */     Objects.requireNonNull(remappingFunction);
/*  610 */     int pos = find(k);
/*  611 */     if (pos < 0) {
/*  612 */       insert(-pos - 1, k, v);
/*  613 */       return v;
/*      */     } 
/*  615 */     Long newValue = remappingFunction.apply(Long.valueOf(this.value[pos]), Long.valueOf(v));
/*  616 */     if (newValue == null) {
/*  617 */       if (this.strategy.equals(k, null)) {
/*  618 */         removeNullEntry();
/*      */       } else {
/*  620 */         removeEntry(pos);
/*  621 */       }  return this.defRetValue;
/*      */     } 
/*  623 */     this.value[pos] = newValue.longValue(); return newValue.longValue();
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
/*  634 */     if (this.size == 0)
/*      */       return; 
/*  636 */     this.size = 0;
/*  637 */     this.containsNullKey = false;
/*  638 */     Arrays.fill((Object[])this.key, (Object)null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  642 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  646 */     return (this.size == 0);
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
/*  658 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public K getKey() {
/*  664 */       return Object2LongOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public long getLongValue() {
/*  668 */       return Object2LongOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public long setValue(long v) {
/*  672 */       long oldValue = Object2LongOpenCustomHashMap.this.value[this.index];
/*  673 */       Object2LongOpenCustomHashMap.this.value[this.index] = v;
/*  674 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long getValue() {
/*  684 */       return Long.valueOf(Object2LongOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long setValue(Long v) {
/*  694 */       return Long.valueOf(setValue(v.longValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  699 */       if (!(o instanceof Map.Entry))
/*  700 */         return false; 
/*  701 */       Map.Entry<K, Long> e = (Map.Entry<K, Long>)o;
/*  702 */       return (Object2LongOpenCustomHashMap.this.strategy.equals(Object2LongOpenCustomHashMap.this.key[this.index], e.getKey()) && Object2LongOpenCustomHashMap.this.value[this.index] == ((Long)e.getValue()).longValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  706 */       return Object2LongOpenCustomHashMap.this.strategy.hashCode(Object2LongOpenCustomHashMap.this.key[this.index]) ^ HashCommon.long2int(Object2LongOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  710 */       return (new StringBuilder()).append(Object2LongOpenCustomHashMap.this.key[this.index]).append("=>").append(Object2LongOpenCustomHashMap.this.value[this.index]).toString();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  720 */     int pos = Object2LongOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  727 */     int last = -1;
/*      */     
/*  729 */     int c = Object2LongOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  733 */     boolean mustReturnNullKey = Object2LongOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     ObjectArrayList<K> wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  740 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  743 */       if (!hasNext())
/*  744 */         throw new NoSuchElementException(); 
/*  745 */       this.c--;
/*  746 */       if (this.mustReturnNullKey) {
/*  747 */         this.mustReturnNullKey = false;
/*  748 */         return this.last = Object2LongOpenCustomHashMap.this.n;
/*      */       } 
/*  750 */       K[] key = Object2LongOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  752 */         if (--this.pos < 0) {
/*      */           
/*  754 */           this.last = Integer.MIN_VALUE;
/*  755 */           K k = this.wrapped.get(-this.pos - 1);
/*  756 */           int p = HashCommon.mix(Object2LongOpenCustomHashMap.this.strategy.hashCode(k)) & Object2LongOpenCustomHashMap.this.mask;
/*  757 */           while (!Object2LongOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  758 */             p = p + 1 & Object2LongOpenCustomHashMap.this.mask; 
/*  759 */           return p;
/*      */         } 
/*  761 */         if (key[this.pos] != null) {
/*  762 */           return this.last = this.pos;
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
/*  776 */       K[] key = Object2LongOpenCustomHashMap.this.key; while (true) {
/*      */         K curr; int last;
/*  778 */         pos = (last = pos) + 1 & Object2LongOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  780 */           if ((curr = key[pos]) == null) {
/*  781 */             key[last] = null;
/*      */             return;
/*      */           } 
/*  784 */           int slot = HashCommon.mix(Object2LongOpenCustomHashMap.this.strategy.hashCode(curr)) & Object2LongOpenCustomHashMap.this.mask;
/*  785 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  787 */           pos = pos + 1 & Object2LongOpenCustomHashMap.this.mask;
/*      */         } 
/*  789 */         if (pos < last) {
/*  790 */           if (this.wrapped == null)
/*  791 */             this.wrapped = new ObjectArrayList<>(2); 
/*  792 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  794 */         key[last] = curr;
/*  795 */         Object2LongOpenCustomHashMap.this.value[last] = Object2LongOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  799 */       if (this.last == -1)
/*  800 */         throw new IllegalStateException(); 
/*  801 */       if (this.last == Object2LongOpenCustomHashMap.this.n) {
/*  802 */         Object2LongOpenCustomHashMap.this.containsNullKey = false;
/*  803 */         Object2LongOpenCustomHashMap.this.key[Object2LongOpenCustomHashMap.this.n] = null;
/*  804 */       } else if (this.pos >= 0) {
/*  805 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  808 */         Object2LongOpenCustomHashMap.this.removeLong(this.wrapped.set(-this.pos - 1, null));
/*  809 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  812 */       Object2LongOpenCustomHashMap.this.size--;
/*  813 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  818 */       int i = n;
/*  819 */       while (i-- != 0 && hasNext())
/*  820 */         nextEntry(); 
/*  821 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Object2LongMap.Entry<K>> { private Object2LongOpenCustomHashMap<K>.MapEntry entry;
/*      */     
/*      */     public Object2LongOpenCustomHashMap<K>.MapEntry next() {
/*  828 */       return this.entry = new Object2LongOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  832 */       super.remove();
/*  833 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Object2LongMap.Entry<K>> { private FastEntryIterator() {
/*  837 */       this.entry = new Object2LongOpenCustomHashMap.MapEntry();
/*      */     } private final Object2LongOpenCustomHashMap<K>.MapEntry entry;
/*      */     public Object2LongOpenCustomHashMap<K>.MapEntry next() {
/*  840 */       this.entry.index = nextEntry();
/*  841 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Object2LongMap.Entry<K>> implements Object2LongMap.FastEntrySet<K> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Object2LongMap.Entry<K>> iterator() {
/*  847 */       return new Object2LongOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Object2LongMap.Entry<K>> fastIterator() {
/*  851 */       return new Object2LongOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  856 */       if (!(o instanceof Map.Entry))
/*  857 */         return false; 
/*  858 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  859 */       if (e.getValue() == null || !(e.getValue() instanceof Long))
/*  860 */         return false; 
/*  861 */       K k = (K)e.getKey();
/*  862 */       long v = ((Long)e.getValue()).longValue();
/*  863 */       if (Object2LongOpenCustomHashMap.this.strategy.equals(k, null)) {
/*  864 */         return (Object2LongOpenCustomHashMap.this.containsNullKey && Object2LongOpenCustomHashMap.this.value[Object2LongOpenCustomHashMap.this.n] == v);
/*      */       }
/*  866 */       K[] key = Object2LongOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  869 */       if ((curr = key[pos = HashCommon.mix(Object2LongOpenCustomHashMap.this.strategy.hashCode(k)) & Object2LongOpenCustomHashMap.this.mask]) == null)
/*  870 */         return false; 
/*  871 */       if (Object2LongOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  872 */         return (Object2LongOpenCustomHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  875 */         if ((curr = key[pos = pos + 1 & Object2LongOpenCustomHashMap.this.mask]) == null)
/*  876 */           return false; 
/*  877 */         if (Object2LongOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  878 */           return (Object2LongOpenCustomHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  884 */       if (!(o instanceof Map.Entry))
/*  885 */         return false; 
/*  886 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  887 */       if (e.getValue() == null || !(e.getValue() instanceof Long))
/*  888 */         return false; 
/*  889 */       K k = (K)e.getKey();
/*  890 */       long v = ((Long)e.getValue()).longValue();
/*  891 */       if (Object2LongOpenCustomHashMap.this.strategy.equals(k, null)) {
/*  892 */         if (Object2LongOpenCustomHashMap.this.containsNullKey && Object2LongOpenCustomHashMap.this.value[Object2LongOpenCustomHashMap.this.n] == v) {
/*  893 */           Object2LongOpenCustomHashMap.this.removeNullEntry();
/*  894 */           return true;
/*      */         } 
/*  896 */         return false;
/*      */       } 
/*      */       
/*  899 */       K[] key = Object2LongOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  902 */       if ((curr = key[pos = HashCommon.mix(Object2LongOpenCustomHashMap.this.strategy.hashCode(k)) & Object2LongOpenCustomHashMap.this.mask]) == null)
/*  903 */         return false; 
/*  904 */       if (Object2LongOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  905 */         if (Object2LongOpenCustomHashMap.this.value[pos] == v) {
/*  906 */           Object2LongOpenCustomHashMap.this.removeEntry(pos);
/*  907 */           return true;
/*      */         } 
/*  909 */         return false;
/*      */       } 
/*      */       while (true) {
/*  912 */         if ((curr = key[pos = pos + 1 & Object2LongOpenCustomHashMap.this.mask]) == null)
/*  913 */           return false; 
/*  914 */         if (Object2LongOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  915 */           Object2LongOpenCustomHashMap.this.value[pos] == v) {
/*  916 */           Object2LongOpenCustomHashMap.this.removeEntry(pos);
/*  917 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  924 */       return Object2LongOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  928 */       Object2LongOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Object2LongMap.Entry<K>> consumer) {
/*  933 */       if (Object2LongOpenCustomHashMap.this.containsNullKey)
/*  934 */         consumer.accept(new AbstractObject2LongMap.BasicEntry<>(Object2LongOpenCustomHashMap.this.key[Object2LongOpenCustomHashMap.this.n], Object2LongOpenCustomHashMap.this.value[Object2LongOpenCustomHashMap.this.n])); 
/*  935 */       for (int pos = Object2LongOpenCustomHashMap.this.n; pos-- != 0;) {
/*  936 */         if (Object2LongOpenCustomHashMap.this.key[pos] != null)
/*  937 */           consumer.accept(new AbstractObject2LongMap.BasicEntry<>(Object2LongOpenCustomHashMap.this.key[pos], Object2LongOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Object2LongMap.Entry<K>> consumer) {
/*  942 */       AbstractObject2LongMap.BasicEntry<K> entry = new AbstractObject2LongMap.BasicEntry<>();
/*  943 */       if (Object2LongOpenCustomHashMap.this.containsNullKey) {
/*  944 */         entry.key = Object2LongOpenCustomHashMap.this.key[Object2LongOpenCustomHashMap.this.n];
/*  945 */         entry.value = Object2LongOpenCustomHashMap.this.value[Object2LongOpenCustomHashMap.this.n];
/*  946 */         consumer.accept(entry);
/*      */       } 
/*  948 */       for (int pos = Object2LongOpenCustomHashMap.this.n; pos-- != 0;) {
/*  949 */         if (Object2LongOpenCustomHashMap.this.key[pos] != null) {
/*  950 */           entry.key = Object2LongOpenCustomHashMap.this.key[pos];
/*  951 */           entry.value = Object2LongOpenCustomHashMap.this.value[pos];
/*  952 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Object2LongMap.FastEntrySet<K> object2LongEntrySet() {
/*  958 */     if (this.entries == null)
/*  959 */       this.entries = new MapEntrySet(); 
/*  960 */     return this.entries;
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
/*  977 */       return Object2LongOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractObjectSet<K> { private KeySet() {}
/*      */     
/*      */     public ObjectIterator<K> iterator() {
/*  983 */       return new Object2LongOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/*  988 */       if (Object2LongOpenCustomHashMap.this.containsNullKey)
/*  989 */         consumer.accept(Object2LongOpenCustomHashMap.this.key[Object2LongOpenCustomHashMap.this.n]); 
/*  990 */       for (int pos = Object2LongOpenCustomHashMap.this.n; pos-- != 0; ) {
/*  991 */         K k = Object2LongOpenCustomHashMap.this.key[pos];
/*  992 */         if (k != null)
/*  993 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  998 */       return Object2LongOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(Object k) {
/* 1002 */       return Object2LongOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(Object k) {
/* 1006 */       int oldSize = Object2LongOpenCustomHashMap.this.size;
/* 1007 */       Object2LongOpenCustomHashMap.this.removeLong(k);
/* 1008 */       return (Object2LongOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1012 */       Object2LongOpenCustomHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ObjectSet<K> keySet() {
/* 1017 */     if (this.keys == null)
/* 1018 */       this.keys = new KeySet(); 
/* 1019 */     return this.keys;
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
/* 1036 */       return Object2LongOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public LongCollection values() {
/* 1041 */     if (this.values == null)
/* 1042 */       this.values = (LongCollection)new AbstractLongCollection()
/*      */         {
/*      */           public LongIterator iterator() {
/* 1045 */             return new Object2LongOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1049 */             return Object2LongOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(long v) {
/* 1053 */             return Object2LongOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1057 */             Object2LongOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(LongConsumer consumer)
/*      */           {
/* 1062 */             if (Object2LongOpenCustomHashMap.this.containsNullKey)
/* 1063 */               consumer.accept(Object2LongOpenCustomHashMap.this.value[Object2LongOpenCustomHashMap.this.n]); 
/* 1064 */             for (int pos = Object2LongOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1065 */               if (Object2LongOpenCustomHashMap.this.key[pos] != null)
/* 1066 */                 consumer.accept(Object2LongOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1069 */     return this.values;
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
/* 1086 */     return trim(this.size);
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
/* 1110 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1111 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1112 */       return true; 
/*      */     try {
/* 1114 */       rehash(l);
/* 1115 */     } catch (OutOfMemoryError cantDoIt) {
/* 1116 */       return false;
/*      */     } 
/* 1118 */     return true;
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
/* 1134 */     K[] key = this.key;
/* 1135 */     long[] value = this.value;
/* 1136 */     int mask = newN - 1;
/* 1137 */     K[] newKey = (K[])new Object[newN + 1];
/* 1138 */     long[] newValue = new long[newN + 1];
/* 1139 */     int i = this.n;
/* 1140 */     for (int j = realSize(); j-- != 0; ) {
/* 1141 */       while (key[--i] == null); int pos;
/* 1142 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != null)
/* 1143 */         while (newKey[pos = pos + 1 & mask] != null); 
/* 1144 */       newKey[pos] = key[i];
/* 1145 */       newValue[pos] = value[i];
/*      */     } 
/* 1147 */     newValue[newN] = value[this.n];
/* 1148 */     this.n = newN;
/* 1149 */     this.mask = mask;
/* 1150 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1151 */     this.key = newKey;
/* 1152 */     this.value = newValue;
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
/*      */   public Object2LongOpenCustomHashMap<K> clone() {
/*      */     Object2LongOpenCustomHashMap<K> c;
/*      */     try {
/* 1169 */       c = (Object2LongOpenCustomHashMap<K>)super.clone();
/* 1170 */     } catch (CloneNotSupportedException cantHappen) {
/* 1171 */       throw new InternalError();
/*      */     } 
/* 1173 */     c.keys = null;
/* 1174 */     c.values = null;
/* 1175 */     c.entries = null;
/* 1176 */     c.containsNullKey = this.containsNullKey;
/* 1177 */     c.key = (K[])this.key.clone();
/* 1178 */     c.value = (long[])this.value.clone();
/* 1179 */     c.strategy = this.strategy;
/* 1180 */     return c;
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
/* 1193 */     int h = 0;
/* 1194 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1195 */       while (this.key[i] == null)
/* 1196 */         i++; 
/* 1197 */       if (this != this.key[i])
/* 1198 */         t = this.strategy.hashCode(this.key[i]); 
/* 1199 */       t ^= HashCommon.long2int(this.value[i]);
/* 1200 */       h += t;
/* 1201 */       i++;
/*      */     } 
/*      */     
/* 1204 */     if (this.containsNullKey)
/* 1205 */       h += HashCommon.long2int(this.value[this.n]); 
/* 1206 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1209 */     K[] key = this.key;
/* 1210 */     long[] value = this.value;
/* 1211 */     MapIterator i = new MapIterator();
/* 1212 */     s.defaultWriteObject();
/* 1213 */     for (int j = this.size; j-- != 0; ) {
/* 1214 */       int e = i.nextEntry();
/* 1215 */       s.writeObject(key[e]);
/* 1216 */       s.writeLong(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1221 */     s.defaultReadObject();
/* 1222 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1223 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1224 */     this.mask = this.n - 1;
/* 1225 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1226 */     long[] value = this.value = new long[this.n + 1];
/*      */ 
/*      */     
/* 1229 */     for (int i = this.size; i-- != 0; ) {
/* 1230 */       int pos; K k = (K)s.readObject();
/* 1231 */       long v = s.readLong();
/* 1232 */       if (this.strategy.equals(k, null)) {
/* 1233 */         pos = this.n;
/* 1234 */         this.containsNullKey = true;
/*      */       } else {
/* 1236 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1237 */         while (key[pos] != null)
/* 1238 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1240 */       key[pos] = k;
/* 1241 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2LongOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */