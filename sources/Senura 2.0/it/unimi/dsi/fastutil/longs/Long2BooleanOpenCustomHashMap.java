/*      */ package it.unimi.dsi.fastutil.longs;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanIterator;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
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
/*      */ import java.util.function.LongConsumer;
/*      */ import java.util.function.LongFunction;
/*      */ import java.util.function.LongPredicate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Long2BooleanOpenCustomHashMap
/*      */   extends AbstractLong2BooleanMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient long[] key;
/*      */   protected transient boolean[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected LongHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Long2BooleanMap.FastEntrySet entries;
/*      */   protected transient LongSet keys;
/*      */   protected transient BooleanCollection values;
/*      */   
/*      */   public Long2BooleanOpenCustomHashMap(int expected, float f, LongHash.Strategy strategy) {
/*  108 */     this.strategy = strategy;
/*  109 */     if (f <= 0.0F || f > 1.0F)
/*  110 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  111 */     if (expected < 0)
/*  112 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  113 */     this.f = f;
/*  114 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  115 */     this.mask = this.n - 1;
/*  116 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  117 */     this.key = new long[this.n + 1];
/*  118 */     this.value = new boolean[this.n + 1];
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
/*      */   public Long2BooleanOpenCustomHashMap(int expected, LongHash.Strategy strategy) {
/*  130 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2BooleanOpenCustomHashMap(LongHash.Strategy strategy) {
/*  141 */     this(16, 0.75F, strategy);
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
/*      */   public Long2BooleanOpenCustomHashMap(Map<? extends Long, ? extends Boolean> m, float f, LongHash.Strategy strategy) {
/*  155 */     this(m.size(), f, strategy);
/*  156 */     putAll(m);
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
/*      */   public Long2BooleanOpenCustomHashMap(Map<? extends Long, ? extends Boolean> m, LongHash.Strategy strategy) {
/*  169 */     this(m, 0.75F, strategy);
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
/*      */   public Long2BooleanOpenCustomHashMap(Long2BooleanMap m, float f, LongHash.Strategy strategy) {
/*  183 */     this(m.size(), f, strategy);
/*  184 */     putAll(m);
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
/*      */   public Long2BooleanOpenCustomHashMap(Long2BooleanMap m, LongHash.Strategy strategy) {
/*  197 */     this(m, 0.75F, strategy);
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
/*      */   public Long2BooleanOpenCustomHashMap(long[] k, boolean[] v, float f, LongHash.Strategy strategy) {
/*  215 */     this(k.length, f, strategy);
/*  216 */     if (k.length != v.length) {
/*  217 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  219 */     for (int i = 0; i < k.length; i++) {
/*  220 */       put(k[i], v[i]);
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
/*      */   public Long2BooleanOpenCustomHashMap(long[] k, boolean[] v, LongHash.Strategy strategy) {
/*  237 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongHash.Strategy strategy() {
/*  245 */     return this.strategy;
/*      */   }
/*      */   private int realSize() {
/*  248 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  251 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  252 */     if (needed > this.n)
/*  253 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  256 */     int needed = (int)Math.min(1073741824L, 
/*  257 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  258 */     if (needed > this.n)
/*  259 */       rehash(needed); 
/*      */   }
/*      */   private boolean removeEntry(int pos) {
/*  262 */     boolean oldValue = this.value[pos];
/*  263 */     this.size--;
/*  264 */     shiftKeys(pos);
/*  265 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  266 */       rehash(this.n / 2); 
/*  267 */     return oldValue;
/*      */   }
/*      */   private boolean removeNullEntry() {
/*  270 */     this.containsNullKey = false;
/*  271 */     boolean oldValue = this.value[this.n];
/*  272 */     this.size--;
/*  273 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  274 */       rehash(this.n / 2); 
/*  275 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Long, ? extends Boolean> m) {
/*  279 */     if (this.f <= 0.5D) {
/*  280 */       ensureCapacity(m.size());
/*      */     } else {
/*  282 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  284 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(long k) {
/*  288 */     if (this.strategy.equals(k, 0L)) {
/*  289 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  291 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  294 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  295 */       return -(pos + 1); 
/*  296 */     if (this.strategy.equals(k, curr)) {
/*  297 */       return pos;
/*      */     }
/*      */     while (true) {
/*  300 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  301 */         return -(pos + 1); 
/*  302 */       if (this.strategy.equals(k, curr))
/*  303 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, long k, boolean v) {
/*  307 */     if (pos == this.n)
/*  308 */       this.containsNullKey = true; 
/*  309 */     this.key[pos] = k;
/*  310 */     this.value[pos] = v;
/*  311 */     if (this.size++ >= this.maxFill) {
/*  312 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean put(long k, boolean v) {
/*  318 */     int pos = find(k);
/*  319 */     if (pos < 0) {
/*  320 */       insert(-pos - 1, k, v);
/*  321 */       return this.defRetValue;
/*      */     } 
/*  323 */     boolean oldValue = this.value[pos];
/*  324 */     this.value[pos] = v;
/*  325 */     return oldValue;
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
/*  338 */     long[] key = this.key; while (true) {
/*      */       long curr; int last;
/*  340 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  342 */         if ((curr = key[pos]) == 0L) {
/*  343 */           key[last] = 0L;
/*      */           return;
/*      */         } 
/*  346 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  347 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  349 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  351 */       key[last] = curr;
/*  352 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean remove(long k) {
/*  358 */     if (this.strategy.equals(k, 0L)) {
/*  359 */       if (this.containsNullKey)
/*  360 */         return removeNullEntry(); 
/*  361 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  364 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  367 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  368 */       return this.defRetValue; 
/*  369 */     if (this.strategy.equals(k, curr))
/*  370 */       return removeEntry(pos); 
/*      */     while (true) {
/*  372 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  373 */         return this.defRetValue; 
/*  374 */       if (this.strategy.equals(k, curr)) {
/*  375 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean get(long k) {
/*  381 */     if (this.strategy.equals(k, 0L)) {
/*  382 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  384 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  387 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  388 */       return this.defRetValue; 
/*  389 */     if (this.strategy.equals(k, curr)) {
/*  390 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  393 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  394 */         return this.defRetValue; 
/*  395 */       if (this.strategy.equals(k, curr)) {
/*  396 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(long k) {
/*  402 */     if (this.strategy.equals(k, 0L)) {
/*  403 */       return this.containsNullKey;
/*      */     }
/*  405 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  408 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  409 */       return false; 
/*  410 */     if (this.strategy.equals(k, curr)) {
/*  411 */       return true;
/*      */     }
/*      */     while (true) {
/*  414 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  415 */         return false; 
/*  416 */       if (this.strategy.equals(k, curr))
/*  417 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(boolean v) {
/*  422 */     boolean[] value = this.value;
/*  423 */     long[] key = this.key;
/*  424 */     if (this.containsNullKey && value[this.n] == v)
/*  425 */       return true; 
/*  426 */     for (int i = this.n; i-- != 0;) {
/*  427 */       if (key[i] != 0L && value[i] == v)
/*  428 */         return true; 
/*  429 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getOrDefault(long k, boolean defaultValue) {
/*  435 */     if (this.strategy.equals(k, 0L)) {
/*  436 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  438 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  441 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  442 */       return defaultValue; 
/*  443 */     if (this.strategy.equals(k, curr)) {
/*  444 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  447 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  448 */         return defaultValue; 
/*  449 */       if (this.strategy.equals(k, curr)) {
/*  450 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean putIfAbsent(long k, boolean v) {
/*  456 */     int pos = find(k);
/*  457 */     if (pos >= 0)
/*  458 */       return this.value[pos]; 
/*  459 */     insert(-pos - 1, k, v);
/*  460 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(long k, boolean v) {
/*  466 */     if (this.strategy.equals(k, 0L)) {
/*  467 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  468 */         removeNullEntry();
/*  469 */         return true;
/*      */       } 
/*  471 */       return false;
/*      */     } 
/*      */     
/*  474 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  477 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  478 */       return false; 
/*  479 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  480 */       removeEntry(pos);
/*  481 */       return true;
/*      */     } 
/*      */     while (true) {
/*  484 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  485 */         return false; 
/*  486 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  487 */         removeEntry(pos);
/*  488 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(long k, boolean oldValue, boolean v) {
/*  495 */     int pos = find(k);
/*  496 */     if (pos < 0 || oldValue != this.value[pos])
/*  497 */       return false; 
/*  498 */     this.value[pos] = v;
/*  499 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(long k, boolean v) {
/*  504 */     int pos = find(k);
/*  505 */     if (pos < 0)
/*  506 */       return this.defRetValue; 
/*  507 */     boolean oldValue = this.value[pos];
/*  508 */     this.value[pos] = v;
/*  509 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean computeIfAbsent(long k, LongPredicate mappingFunction) {
/*  514 */     Objects.requireNonNull(mappingFunction);
/*  515 */     int pos = find(k);
/*  516 */     if (pos >= 0)
/*  517 */       return this.value[pos]; 
/*  518 */     boolean newValue = mappingFunction.test(k);
/*  519 */     insert(-pos - 1, k, newValue);
/*  520 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeIfAbsentNullable(long k, LongFunction<? extends Boolean> mappingFunction) {
/*  526 */     Objects.requireNonNull(mappingFunction);
/*  527 */     int pos = find(k);
/*  528 */     if (pos >= 0)
/*  529 */       return this.value[pos]; 
/*  530 */     Boolean newValue = mappingFunction.apply(k);
/*  531 */     if (newValue == null)
/*  532 */       return this.defRetValue; 
/*  533 */     boolean v = newValue.booleanValue();
/*  534 */     insert(-pos - 1, k, v);
/*  535 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeIfPresent(long k, BiFunction<? super Long, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  541 */     Objects.requireNonNull(remappingFunction);
/*  542 */     int pos = find(k);
/*  543 */     if (pos < 0)
/*  544 */       return this.defRetValue; 
/*  545 */     Boolean newValue = remappingFunction.apply(Long.valueOf(k), Boolean.valueOf(this.value[pos]));
/*  546 */     if (newValue == null) {
/*  547 */       if (this.strategy.equals(k, 0L)) {
/*  548 */         removeNullEntry();
/*      */       } else {
/*  550 */         removeEntry(pos);
/*  551 */       }  return this.defRetValue;
/*      */     } 
/*  553 */     this.value[pos] = newValue.booleanValue(); return newValue.booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean compute(long k, BiFunction<? super Long, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  559 */     Objects.requireNonNull(remappingFunction);
/*  560 */     int pos = find(k);
/*  561 */     Boolean newValue = remappingFunction.apply(Long.valueOf(k), 
/*  562 */         (pos >= 0) ? Boolean.valueOf(this.value[pos]) : null);
/*  563 */     if (newValue == null) {
/*  564 */       if (pos >= 0)
/*  565 */         if (this.strategy.equals(k, 0L)) {
/*  566 */           removeNullEntry();
/*      */         } else {
/*  568 */           removeEntry(pos);
/*      */         }  
/*  570 */       return this.defRetValue;
/*      */     } 
/*  572 */     boolean newVal = newValue.booleanValue();
/*  573 */     if (pos < 0) {
/*  574 */       insert(-pos - 1, k, newVal);
/*  575 */       return newVal;
/*      */     } 
/*  577 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean merge(long k, boolean v, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  583 */     Objects.requireNonNull(remappingFunction);
/*  584 */     int pos = find(k);
/*  585 */     if (pos < 0) {
/*  586 */       insert(-pos - 1, k, v);
/*  587 */       return v;
/*      */     } 
/*  589 */     Boolean newValue = remappingFunction.apply(Boolean.valueOf(this.value[pos]), Boolean.valueOf(v));
/*  590 */     if (newValue == null) {
/*  591 */       if (this.strategy.equals(k, 0L)) {
/*  592 */         removeNullEntry();
/*      */       } else {
/*  594 */         removeEntry(pos);
/*  595 */       }  return this.defRetValue;
/*      */     } 
/*  597 */     this.value[pos] = newValue.booleanValue(); return newValue.booleanValue();
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
/*  608 */     if (this.size == 0)
/*      */       return; 
/*  610 */     this.size = 0;
/*  611 */     this.containsNullKey = false;
/*  612 */     Arrays.fill(this.key, 0L);
/*      */   }
/*      */   
/*      */   public int size() {
/*  616 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  620 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Long2BooleanMap.Entry, Map.Entry<Long, Boolean>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  632 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public long getLongKey() {
/*  638 */       return Long2BooleanOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public boolean getBooleanValue() {
/*  642 */       return Long2BooleanOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public boolean setValue(boolean v) {
/*  646 */       boolean oldValue = Long2BooleanOpenCustomHashMap.this.value[this.index];
/*  647 */       Long2BooleanOpenCustomHashMap.this.value[this.index] = v;
/*  648 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long getKey() {
/*  658 */       return Long.valueOf(Long2BooleanOpenCustomHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean getValue() {
/*  668 */       return Boolean.valueOf(Long2BooleanOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean setValue(Boolean v) {
/*  678 */       return Boolean.valueOf(setValue(v.booleanValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  683 */       if (!(o instanceof Map.Entry))
/*  684 */         return false; 
/*  685 */       Map.Entry<Long, Boolean> e = (Map.Entry<Long, Boolean>)o;
/*  686 */       return (Long2BooleanOpenCustomHashMap.this.strategy.equals(Long2BooleanOpenCustomHashMap.this.key[this.index], ((Long)e.getKey()).longValue()) && Long2BooleanOpenCustomHashMap.this.value[this.index] == ((Boolean)e
/*  687 */         .getValue()).booleanValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  691 */       return Long2BooleanOpenCustomHashMap.this.strategy.hashCode(Long2BooleanOpenCustomHashMap.this.key[this.index]) ^ (Long2BooleanOpenCustomHashMap.this.value[this.index] ? 1231 : 1237);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  695 */       return Long2BooleanOpenCustomHashMap.this.key[this.index] + "=>" + Long2BooleanOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  705 */     int pos = Long2BooleanOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  712 */     int last = -1;
/*      */     
/*  714 */     int c = Long2BooleanOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  718 */     boolean mustReturnNullKey = Long2BooleanOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     LongArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  725 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  728 */       if (!hasNext())
/*  729 */         throw new NoSuchElementException(); 
/*  730 */       this.c--;
/*  731 */       if (this.mustReturnNullKey) {
/*  732 */         this.mustReturnNullKey = false;
/*  733 */         return this.last = Long2BooleanOpenCustomHashMap.this.n;
/*      */       } 
/*  735 */       long[] key = Long2BooleanOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  737 */         if (--this.pos < 0) {
/*      */           
/*  739 */           this.last = Integer.MIN_VALUE;
/*  740 */           long k = this.wrapped.getLong(-this.pos - 1);
/*  741 */           int p = HashCommon.mix(Long2BooleanOpenCustomHashMap.this.strategy.hashCode(k)) & Long2BooleanOpenCustomHashMap.this.mask;
/*  742 */           while (!Long2BooleanOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  743 */             p = p + 1 & Long2BooleanOpenCustomHashMap.this.mask; 
/*  744 */           return p;
/*      */         } 
/*  746 */         if (key[this.pos] != 0L) {
/*  747 */           return this.last = this.pos;
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
/*  761 */       long[] key = Long2BooleanOpenCustomHashMap.this.key; while (true) {
/*      */         long curr; int last;
/*  763 */         pos = (last = pos) + 1 & Long2BooleanOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  765 */           if ((curr = key[pos]) == 0L) {
/*  766 */             key[last] = 0L;
/*      */             return;
/*      */           } 
/*  769 */           int slot = HashCommon.mix(Long2BooleanOpenCustomHashMap.this.strategy.hashCode(curr)) & Long2BooleanOpenCustomHashMap.this.mask;
/*  770 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  772 */           pos = pos + 1 & Long2BooleanOpenCustomHashMap.this.mask;
/*      */         } 
/*  774 */         if (pos < last) {
/*  775 */           if (this.wrapped == null)
/*  776 */             this.wrapped = new LongArrayList(2); 
/*  777 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  779 */         key[last] = curr;
/*  780 */         Long2BooleanOpenCustomHashMap.this.value[last] = Long2BooleanOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  784 */       if (this.last == -1)
/*  785 */         throw new IllegalStateException(); 
/*  786 */       if (this.last == Long2BooleanOpenCustomHashMap.this.n) {
/*  787 */         Long2BooleanOpenCustomHashMap.this.containsNullKey = false;
/*  788 */       } else if (this.pos >= 0) {
/*  789 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  792 */         Long2BooleanOpenCustomHashMap.this.remove(this.wrapped.getLong(-this.pos - 1));
/*  793 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  796 */       Long2BooleanOpenCustomHashMap.this.size--;
/*  797 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  802 */       int i = n;
/*  803 */       while (i-- != 0 && hasNext())
/*  804 */         nextEntry(); 
/*  805 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Long2BooleanMap.Entry> { private Long2BooleanOpenCustomHashMap.MapEntry entry;
/*      */     
/*      */     public Long2BooleanOpenCustomHashMap.MapEntry next() {
/*  812 */       return this.entry = new Long2BooleanOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  816 */       super.remove();
/*  817 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Long2BooleanMap.Entry> { private FastEntryIterator() {
/*  821 */       this.entry = new Long2BooleanOpenCustomHashMap.MapEntry();
/*      */     } private final Long2BooleanOpenCustomHashMap.MapEntry entry;
/*      */     public Long2BooleanOpenCustomHashMap.MapEntry next() {
/*  824 */       this.entry.index = nextEntry();
/*  825 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Long2BooleanMap.Entry> implements Long2BooleanMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Long2BooleanMap.Entry> iterator() {
/*  831 */       return new Long2BooleanOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Long2BooleanMap.Entry> fastIterator() {
/*  835 */       return new Long2BooleanOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  840 */       if (!(o instanceof Map.Entry))
/*  841 */         return false; 
/*  842 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  843 */       if (e.getKey() == null || !(e.getKey() instanceof Long))
/*  844 */         return false; 
/*  845 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/*  846 */         return false; 
/*  847 */       long k = ((Long)e.getKey()).longValue();
/*  848 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/*  849 */       if (Long2BooleanOpenCustomHashMap.this.strategy.equals(k, 0L)) {
/*  850 */         return (Long2BooleanOpenCustomHashMap.this.containsNullKey && Long2BooleanOpenCustomHashMap.this.value[Long2BooleanOpenCustomHashMap.this.n] == v);
/*      */       }
/*  852 */       long[] key = Long2BooleanOpenCustomHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/*  855 */       if ((curr = key[pos = HashCommon.mix(Long2BooleanOpenCustomHashMap.this.strategy.hashCode(k)) & Long2BooleanOpenCustomHashMap.this.mask]) == 0L)
/*  856 */         return false; 
/*  857 */       if (Long2BooleanOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  858 */         return (Long2BooleanOpenCustomHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  861 */         if ((curr = key[pos = pos + 1 & Long2BooleanOpenCustomHashMap.this.mask]) == 0L)
/*  862 */           return false; 
/*  863 */         if (Long2BooleanOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  864 */           return (Long2BooleanOpenCustomHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  870 */       if (!(o instanceof Map.Entry))
/*  871 */         return false; 
/*  872 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  873 */       if (e.getKey() == null || !(e.getKey() instanceof Long))
/*  874 */         return false; 
/*  875 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/*  876 */         return false; 
/*  877 */       long k = ((Long)e.getKey()).longValue();
/*  878 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/*  879 */       if (Long2BooleanOpenCustomHashMap.this.strategy.equals(k, 0L)) {
/*  880 */         if (Long2BooleanOpenCustomHashMap.this.containsNullKey && Long2BooleanOpenCustomHashMap.this.value[Long2BooleanOpenCustomHashMap.this.n] == v) {
/*  881 */           Long2BooleanOpenCustomHashMap.this.removeNullEntry();
/*  882 */           return true;
/*      */         } 
/*  884 */         return false;
/*      */       } 
/*      */       
/*  887 */       long[] key = Long2BooleanOpenCustomHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/*  890 */       if ((curr = key[pos = HashCommon.mix(Long2BooleanOpenCustomHashMap.this.strategy.hashCode(k)) & Long2BooleanOpenCustomHashMap.this.mask]) == 0L)
/*  891 */         return false; 
/*  892 */       if (Long2BooleanOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  893 */         if (Long2BooleanOpenCustomHashMap.this.value[pos] == v) {
/*  894 */           Long2BooleanOpenCustomHashMap.this.removeEntry(pos);
/*  895 */           return true;
/*      */         } 
/*  897 */         return false;
/*      */       } 
/*      */       while (true) {
/*  900 */         if ((curr = key[pos = pos + 1 & Long2BooleanOpenCustomHashMap.this.mask]) == 0L)
/*  901 */           return false; 
/*  902 */         if (Long2BooleanOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  903 */           Long2BooleanOpenCustomHashMap.this.value[pos] == v) {
/*  904 */           Long2BooleanOpenCustomHashMap.this.removeEntry(pos);
/*  905 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  912 */       return Long2BooleanOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  916 */       Long2BooleanOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Long2BooleanMap.Entry> consumer) {
/*  921 */       if (Long2BooleanOpenCustomHashMap.this.containsNullKey)
/*  922 */         consumer.accept(new AbstractLong2BooleanMap.BasicEntry(Long2BooleanOpenCustomHashMap.this.key[Long2BooleanOpenCustomHashMap.this.n], Long2BooleanOpenCustomHashMap.this.value[Long2BooleanOpenCustomHashMap.this.n])); 
/*  923 */       for (int pos = Long2BooleanOpenCustomHashMap.this.n; pos-- != 0;) {
/*  924 */         if (Long2BooleanOpenCustomHashMap.this.key[pos] != 0L)
/*  925 */           consumer.accept(new AbstractLong2BooleanMap.BasicEntry(Long2BooleanOpenCustomHashMap.this.key[pos], Long2BooleanOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Long2BooleanMap.Entry> consumer) {
/*  930 */       AbstractLong2BooleanMap.BasicEntry entry = new AbstractLong2BooleanMap.BasicEntry();
/*  931 */       if (Long2BooleanOpenCustomHashMap.this.containsNullKey) {
/*  932 */         entry.key = Long2BooleanOpenCustomHashMap.this.key[Long2BooleanOpenCustomHashMap.this.n];
/*  933 */         entry.value = Long2BooleanOpenCustomHashMap.this.value[Long2BooleanOpenCustomHashMap.this.n];
/*  934 */         consumer.accept(entry);
/*      */       } 
/*  936 */       for (int pos = Long2BooleanOpenCustomHashMap.this.n; pos-- != 0;) {
/*  937 */         if (Long2BooleanOpenCustomHashMap.this.key[pos] != 0L) {
/*  938 */           entry.key = Long2BooleanOpenCustomHashMap.this.key[pos];
/*  939 */           entry.value = Long2BooleanOpenCustomHashMap.this.value[pos];
/*  940 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Long2BooleanMap.FastEntrySet long2BooleanEntrySet() {
/*  946 */     if (this.entries == null)
/*  947 */       this.entries = new MapEntrySet(); 
/*  948 */     return this.entries;
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
/*      */     implements LongIterator
/*      */   {
/*      */     public long nextLong() {
/*  965 */       return Long2BooleanOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractLongSet { private KeySet() {}
/*      */     
/*      */     public LongIterator iterator() {
/*  971 */       return new Long2BooleanOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(LongConsumer consumer) {
/*  976 */       if (Long2BooleanOpenCustomHashMap.this.containsNullKey)
/*  977 */         consumer.accept(Long2BooleanOpenCustomHashMap.this.key[Long2BooleanOpenCustomHashMap.this.n]); 
/*  978 */       for (int pos = Long2BooleanOpenCustomHashMap.this.n; pos-- != 0; ) {
/*  979 */         long k = Long2BooleanOpenCustomHashMap.this.key[pos];
/*  980 */         if (k != 0L)
/*  981 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  986 */       return Long2BooleanOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(long k) {
/*  990 */       return Long2BooleanOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(long k) {
/*  994 */       int oldSize = Long2BooleanOpenCustomHashMap.this.size;
/*  995 */       Long2BooleanOpenCustomHashMap.this.remove(k);
/*  996 */       return (Long2BooleanOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1000 */       Long2BooleanOpenCustomHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public LongSet keySet() {
/* 1005 */     if (this.keys == null)
/* 1006 */       this.keys = new KeySet(); 
/* 1007 */     return this.keys;
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
/*      */     implements BooleanIterator
/*      */   {
/*      */     public boolean nextBoolean() {
/* 1024 */       return Long2BooleanOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public BooleanCollection values() {
/* 1029 */     if (this.values == null)
/* 1030 */       this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */         {
/*      */           public BooleanIterator iterator() {
/* 1033 */             return new Long2BooleanOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1037 */             return Long2BooleanOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(boolean v) {
/* 1041 */             return Long2BooleanOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1045 */             Long2BooleanOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(BooleanConsumer consumer)
/*      */           {
/* 1050 */             if (Long2BooleanOpenCustomHashMap.this.containsNullKey)
/* 1051 */               consumer.accept(Long2BooleanOpenCustomHashMap.this.value[Long2BooleanOpenCustomHashMap.this.n]); 
/* 1052 */             for (int pos = Long2BooleanOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1053 */               if (Long2BooleanOpenCustomHashMap.this.key[pos] != 0L)
/* 1054 */                 consumer.accept(Long2BooleanOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1057 */     return this.values;
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
/* 1074 */     return trim(this.size);
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
/* 1098 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1099 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1100 */       return true; 
/*      */     try {
/* 1102 */       rehash(l);
/* 1103 */     } catch (OutOfMemoryError cantDoIt) {
/* 1104 */       return false;
/*      */     } 
/* 1106 */     return true;
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
/* 1122 */     long[] key = this.key;
/* 1123 */     boolean[] value = this.value;
/* 1124 */     int mask = newN - 1;
/* 1125 */     long[] newKey = new long[newN + 1];
/* 1126 */     boolean[] newValue = new boolean[newN + 1];
/* 1127 */     int i = this.n;
/* 1128 */     for (int j = realSize(); j-- != 0; ) {
/* 1129 */       while (key[--i] == 0L); int pos;
/* 1130 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != 0L)
/* 1131 */         while (newKey[pos = pos + 1 & mask] != 0L); 
/* 1132 */       newKey[pos] = key[i];
/* 1133 */       newValue[pos] = value[i];
/*      */     } 
/* 1135 */     newValue[newN] = value[this.n];
/* 1136 */     this.n = newN;
/* 1137 */     this.mask = mask;
/* 1138 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1139 */     this.key = newKey;
/* 1140 */     this.value = newValue;
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
/*      */   public Long2BooleanOpenCustomHashMap clone() {
/*      */     Long2BooleanOpenCustomHashMap c;
/*      */     try {
/* 1157 */       c = (Long2BooleanOpenCustomHashMap)super.clone();
/* 1158 */     } catch (CloneNotSupportedException cantHappen) {
/* 1159 */       throw new InternalError();
/*      */     } 
/* 1161 */     c.keys = null;
/* 1162 */     c.values = null;
/* 1163 */     c.entries = null;
/* 1164 */     c.containsNullKey = this.containsNullKey;
/* 1165 */     c.key = (long[])this.key.clone();
/* 1166 */     c.value = (boolean[])this.value.clone();
/* 1167 */     c.strategy = this.strategy;
/* 1168 */     return c;
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
/* 1181 */     int h = 0;
/* 1182 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1183 */       while (this.key[i] == 0L)
/* 1184 */         i++; 
/* 1185 */       t = this.strategy.hashCode(this.key[i]);
/* 1186 */       t ^= this.value[i] ? 1231 : 1237;
/* 1187 */       h += t;
/* 1188 */       i++;
/*      */     } 
/*      */     
/* 1191 */     if (this.containsNullKey)
/* 1192 */       h += this.value[this.n] ? 1231 : 1237; 
/* 1193 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1196 */     long[] key = this.key;
/* 1197 */     boolean[] value = this.value;
/* 1198 */     MapIterator i = new MapIterator();
/* 1199 */     s.defaultWriteObject();
/* 1200 */     for (int j = this.size; j-- != 0; ) {
/* 1201 */       int e = i.nextEntry();
/* 1202 */       s.writeLong(key[e]);
/* 1203 */       s.writeBoolean(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1208 */     s.defaultReadObject();
/* 1209 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1210 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1211 */     this.mask = this.n - 1;
/* 1212 */     long[] key = this.key = new long[this.n + 1];
/* 1213 */     boolean[] value = this.value = new boolean[this.n + 1];
/*      */ 
/*      */     
/* 1216 */     for (int i = this.size; i-- != 0; ) {
/* 1217 */       int pos; long k = s.readLong();
/* 1218 */       boolean v = s.readBoolean();
/* 1219 */       if (this.strategy.equals(k, 0L)) {
/* 1220 */         pos = this.n;
/* 1221 */         this.containsNullKey = true;
/*      */       } else {
/* 1223 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1224 */         while (key[pos] != 0L)
/* 1225 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1227 */       key[pos] = k;
/* 1228 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\Long2BooleanOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */