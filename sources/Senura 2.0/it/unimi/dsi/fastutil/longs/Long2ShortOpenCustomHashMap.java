/*      */ package it.unimi.dsi.fastutil.longs;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSet;
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
/*      */ import java.util.function.LongConsumer;
/*      */ import java.util.function.LongFunction;
/*      */ import java.util.function.LongToIntFunction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Long2ShortOpenCustomHashMap
/*      */   extends AbstractLong2ShortMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient long[] key;
/*      */   protected transient short[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected LongHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Long2ShortMap.FastEntrySet entries;
/*      */   protected transient LongSet keys;
/*      */   protected transient ShortCollection values;
/*      */   
/*      */   public Long2ShortOpenCustomHashMap(int expected, float f, LongHash.Strategy strategy) {
/*  107 */     this.strategy = strategy;
/*  108 */     if (f <= 0.0F || f > 1.0F)
/*  109 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  110 */     if (expected < 0)
/*  111 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  112 */     this.f = f;
/*  113 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  114 */     this.mask = this.n - 1;
/*  115 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  116 */     this.key = new long[this.n + 1];
/*  117 */     this.value = new short[this.n + 1];
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
/*      */   public Long2ShortOpenCustomHashMap(int expected, LongHash.Strategy strategy) {
/*  129 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2ShortOpenCustomHashMap(LongHash.Strategy strategy) {
/*  140 */     this(16, 0.75F, strategy);
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
/*      */   public Long2ShortOpenCustomHashMap(Map<? extends Long, ? extends Short> m, float f, LongHash.Strategy strategy) {
/*  154 */     this(m.size(), f, strategy);
/*  155 */     putAll(m);
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
/*      */   public Long2ShortOpenCustomHashMap(Map<? extends Long, ? extends Short> m, LongHash.Strategy strategy) {
/*  168 */     this(m, 0.75F, strategy);
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
/*      */   public Long2ShortOpenCustomHashMap(Long2ShortMap m, float f, LongHash.Strategy strategy) {
/*  182 */     this(m.size(), f, strategy);
/*  183 */     putAll(m);
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
/*      */   public Long2ShortOpenCustomHashMap(Long2ShortMap m, LongHash.Strategy strategy) {
/*  196 */     this(m, 0.75F, strategy);
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
/*      */   public Long2ShortOpenCustomHashMap(long[] k, short[] v, float f, LongHash.Strategy strategy) {
/*  214 */     this(k.length, f, strategy);
/*  215 */     if (k.length != v.length) {
/*  216 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  218 */     for (int i = 0; i < k.length; i++) {
/*  219 */       put(k[i], v[i]);
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
/*      */   public Long2ShortOpenCustomHashMap(long[] k, short[] v, LongHash.Strategy strategy) {
/*  236 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongHash.Strategy strategy() {
/*  244 */     return this.strategy;
/*      */   }
/*      */   private int realSize() {
/*  247 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  250 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  251 */     if (needed > this.n)
/*  252 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  255 */     int needed = (int)Math.min(1073741824L, 
/*  256 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  257 */     if (needed > this.n)
/*  258 */       rehash(needed); 
/*      */   }
/*      */   private short removeEntry(int pos) {
/*  261 */     short oldValue = this.value[pos];
/*  262 */     this.size--;
/*  263 */     shiftKeys(pos);
/*  264 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  265 */       rehash(this.n / 2); 
/*  266 */     return oldValue;
/*      */   }
/*      */   private short removeNullEntry() {
/*  269 */     this.containsNullKey = false;
/*  270 */     short oldValue = this.value[this.n];
/*  271 */     this.size--;
/*  272 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  273 */       rehash(this.n / 2); 
/*  274 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Long, ? extends Short> m) {
/*  278 */     if (this.f <= 0.5D) {
/*  279 */       ensureCapacity(m.size());
/*      */     } else {
/*  281 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  283 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(long k) {
/*  287 */     if (this.strategy.equals(k, 0L)) {
/*  288 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  290 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  293 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  294 */       return -(pos + 1); 
/*  295 */     if (this.strategy.equals(k, curr)) {
/*  296 */       return pos;
/*      */     }
/*      */     while (true) {
/*  299 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  300 */         return -(pos + 1); 
/*  301 */       if (this.strategy.equals(k, curr))
/*  302 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, long k, short v) {
/*  306 */     if (pos == this.n)
/*  307 */       this.containsNullKey = true; 
/*  308 */     this.key[pos] = k;
/*  309 */     this.value[pos] = v;
/*  310 */     if (this.size++ >= this.maxFill) {
/*  311 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public short put(long k, short v) {
/*  317 */     int pos = find(k);
/*  318 */     if (pos < 0) {
/*  319 */       insert(-pos - 1, k, v);
/*  320 */       return this.defRetValue;
/*      */     } 
/*  322 */     short oldValue = this.value[pos];
/*  323 */     this.value[pos] = v;
/*  324 */     return oldValue;
/*      */   }
/*      */   private short addToValue(int pos, short incr) {
/*  327 */     short oldValue = this.value[pos];
/*  328 */     this.value[pos] = (short)(oldValue + incr);
/*  329 */     return oldValue;
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
/*      */   public short addTo(long k, short incr) {
/*      */     int pos;
/*  349 */     if (this.strategy.equals(k, 0L)) {
/*  350 */       if (this.containsNullKey)
/*  351 */         return addToValue(this.n, incr); 
/*  352 */       pos = this.n;
/*  353 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  356 */       long[] key = this.key;
/*      */       long curr;
/*  358 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != 0L) {
/*  359 */         if (this.strategy.equals(curr, k))
/*  360 */           return addToValue(pos, incr); 
/*  361 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0L) {
/*  362 */           if (this.strategy.equals(curr, k))
/*  363 */             return addToValue(pos, incr); 
/*      */         } 
/*      */       } 
/*  366 */     }  this.key[pos] = k;
/*  367 */     this.value[pos] = (short)(this.defRetValue + incr);
/*  368 */     if (this.size++ >= this.maxFill) {
/*  369 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  372 */     return this.defRetValue;
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
/*  385 */     long[] key = this.key; while (true) {
/*      */       long curr; int last;
/*  387 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  389 */         if ((curr = key[pos]) == 0L) {
/*  390 */           key[last] = 0L;
/*      */           return;
/*      */         } 
/*  393 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  394 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  396 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  398 */       key[last] = curr;
/*  399 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public short remove(long k) {
/*  405 */     if (this.strategy.equals(k, 0L)) {
/*  406 */       if (this.containsNullKey)
/*  407 */         return removeNullEntry(); 
/*  408 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  411 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  414 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  415 */       return this.defRetValue; 
/*  416 */     if (this.strategy.equals(k, curr))
/*  417 */       return removeEntry(pos); 
/*      */     while (true) {
/*  419 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  420 */         return this.defRetValue; 
/*  421 */       if (this.strategy.equals(k, curr)) {
/*  422 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public short get(long k) {
/*  428 */     if (this.strategy.equals(k, 0L)) {
/*  429 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  431 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  434 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  435 */       return this.defRetValue; 
/*  436 */     if (this.strategy.equals(k, curr)) {
/*  437 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  440 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  441 */         return this.defRetValue; 
/*  442 */       if (this.strategy.equals(k, curr)) {
/*  443 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(long k) {
/*  449 */     if (this.strategy.equals(k, 0L)) {
/*  450 */       return this.containsNullKey;
/*      */     }
/*  452 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  455 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  456 */       return false; 
/*  457 */     if (this.strategy.equals(k, curr)) {
/*  458 */       return true;
/*      */     }
/*      */     while (true) {
/*  461 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  462 */         return false; 
/*  463 */       if (this.strategy.equals(k, curr))
/*  464 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(short v) {
/*  469 */     short[] value = this.value;
/*  470 */     long[] key = this.key;
/*  471 */     if (this.containsNullKey && value[this.n] == v)
/*  472 */       return true; 
/*  473 */     for (int i = this.n; i-- != 0;) {
/*  474 */       if (key[i] != 0L && value[i] == v)
/*  475 */         return true; 
/*  476 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short getOrDefault(long k, short defaultValue) {
/*  482 */     if (this.strategy.equals(k, 0L)) {
/*  483 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  485 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  488 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  489 */       return defaultValue; 
/*  490 */     if (this.strategy.equals(k, curr)) {
/*  491 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  494 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  495 */         return defaultValue; 
/*  496 */       if (this.strategy.equals(k, curr)) {
/*  497 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public short putIfAbsent(long k, short v) {
/*  503 */     int pos = find(k);
/*  504 */     if (pos >= 0)
/*  505 */       return this.value[pos]; 
/*  506 */     insert(-pos - 1, k, v);
/*  507 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(long k, short v) {
/*  513 */     if (this.strategy.equals(k, 0L)) {
/*  514 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  515 */         removeNullEntry();
/*  516 */         return true;
/*      */       } 
/*  518 */       return false;
/*      */     } 
/*      */     
/*  521 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  524 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  525 */       return false; 
/*  526 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  527 */       removeEntry(pos);
/*  528 */       return true;
/*      */     } 
/*      */     while (true) {
/*  531 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  532 */         return false; 
/*  533 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  534 */         removeEntry(pos);
/*  535 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(long k, short oldValue, short v) {
/*  542 */     int pos = find(k);
/*  543 */     if (pos < 0 || oldValue != this.value[pos])
/*  544 */       return false; 
/*  545 */     this.value[pos] = v;
/*  546 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public short replace(long k, short v) {
/*  551 */     int pos = find(k);
/*  552 */     if (pos < 0)
/*  553 */       return this.defRetValue; 
/*  554 */     short oldValue = this.value[pos];
/*  555 */     this.value[pos] = v;
/*  556 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public short computeIfAbsent(long k, LongToIntFunction mappingFunction) {
/*  561 */     Objects.requireNonNull(mappingFunction);
/*  562 */     int pos = find(k);
/*  563 */     if (pos >= 0)
/*  564 */       return this.value[pos]; 
/*  565 */     short newValue = SafeMath.safeIntToShort(mappingFunction.applyAsInt(k));
/*  566 */     insert(-pos - 1, k, newValue);
/*  567 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short computeIfAbsentNullable(long k, LongFunction<? extends Short> mappingFunction) {
/*  573 */     Objects.requireNonNull(mappingFunction);
/*  574 */     int pos = find(k);
/*  575 */     if (pos >= 0)
/*  576 */       return this.value[pos]; 
/*  577 */     Short newValue = mappingFunction.apply(k);
/*  578 */     if (newValue == null)
/*  579 */       return this.defRetValue; 
/*  580 */     short v = newValue.shortValue();
/*  581 */     insert(-pos - 1, k, v);
/*  582 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short computeIfPresent(long k, BiFunction<? super Long, ? super Short, ? extends Short> remappingFunction) {
/*  588 */     Objects.requireNonNull(remappingFunction);
/*  589 */     int pos = find(k);
/*  590 */     if (pos < 0)
/*  591 */       return this.defRetValue; 
/*  592 */     Short newValue = remappingFunction.apply(Long.valueOf(k), Short.valueOf(this.value[pos]));
/*  593 */     if (newValue == null) {
/*  594 */       if (this.strategy.equals(k, 0L)) {
/*  595 */         removeNullEntry();
/*      */       } else {
/*  597 */         removeEntry(pos);
/*  598 */       }  return this.defRetValue;
/*      */     } 
/*  600 */     this.value[pos] = newValue.shortValue(); return newValue.shortValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short compute(long k, BiFunction<? super Long, ? super Short, ? extends Short> remappingFunction) {
/*  606 */     Objects.requireNonNull(remappingFunction);
/*  607 */     int pos = find(k);
/*  608 */     Short newValue = remappingFunction.apply(Long.valueOf(k), (pos >= 0) ? Short.valueOf(this.value[pos]) : null);
/*  609 */     if (newValue == null) {
/*  610 */       if (pos >= 0)
/*  611 */         if (this.strategy.equals(k, 0L)) {
/*  612 */           removeNullEntry();
/*      */         } else {
/*  614 */           removeEntry(pos);
/*      */         }  
/*  616 */       return this.defRetValue;
/*      */     } 
/*  618 */     short newVal = newValue.shortValue();
/*  619 */     if (pos < 0) {
/*  620 */       insert(-pos - 1, k, newVal);
/*  621 */       return newVal;
/*      */     } 
/*  623 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short merge(long k, short v, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
/*  629 */     Objects.requireNonNull(remappingFunction);
/*  630 */     int pos = find(k);
/*  631 */     if (pos < 0) {
/*  632 */       insert(-pos - 1, k, v);
/*  633 */       return v;
/*      */     } 
/*  635 */     Short newValue = remappingFunction.apply(Short.valueOf(this.value[pos]), Short.valueOf(v));
/*  636 */     if (newValue == null) {
/*  637 */       if (this.strategy.equals(k, 0L)) {
/*  638 */         removeNullEntry();
/*      */       } else {
/*  640 */         removeEntry(pos);
/*  641 */       }  return this.defRetValue;
/*      */     } 
/*  643 */     this.value[pos] = newValue.shortValue(); return newValue.shortValue();
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
/*  654 */     if (this.size == 0)
/*      */       return; 
/*  656 */     this.size = 0;
/*  657 */     this.containsNullKey = false;
/*  658 */     Arrays.fill(this.key, 0L);
/*      */   }
/*      */   
/*      */   public int size() {
/*  662 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  666 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Long2ShortMap.Entry, Map.Entry<Long, Short>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  678 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public long getLongKey() {
/*  684 */       return Long2ShortOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public short getShortValue() {
/*  688 */       return Long2ShortOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public short setValue(short v) {
/*  692 */       short oldValue = Long2ShortOpenCustomHashMap.this.value[this.index];
/*  693 */       Long2ShortOpenCustomHashMap.this.value[this.index] = v;
/*  694 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long getKey() {
/*  704 */       return Long.valueOf(Long2ShortOpenCustomHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Short getValue() {
/*  714 */       return Short.valueOf(Long2ShortOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Short setValue(Short v) {
/*  724 */       return Short.valueOf(setValue(v.shortValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  729 */       if (!(o instanceof Map.Entry))
/*  730 */         return false; 
/*  731 */       Map.Entry<Long, Short> e = (Map.Entry<Long, Short>)o;
/*  732 */       return (Long2ShortOpenCustomHashMap.this.strategy.equals(Long2ShortOpenCustomHashMap.this.key[this.index], ((Long)e.getKey()).longValue()) && Long2ShortOpenCustomHashMap.this.value[this.index] == ((Short)e
/*  733 */         .getValue()).shortValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  737 */       return Long2ShortOpenCustomHashMap.this.strategy.hashCode(Long2ShortOpenCustomHashMap.this.key[this.index]) ^ Long2ShortOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/*  741 */       return Long2ShortOpenCustomHashMap.this.key[this.index] + "=>" + Long2ShortOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  751 */     int pos = Long2ShortOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  758 */     int last = -1;
/*      */     
/*  760 */     int c = Long2ShortOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  764 */     boolean mustReturnNullKey = Long2ShortOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     LongArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  771 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  774 */       if (!hasNext())
/*  775 */         throw new NoSuchElementException(); 
/*  776 */       this.c--;
/*  777 */       if (this.mustReturnNullKey) {
/*  778 */         this.mustReturnNullKey = false;
/*  779 */         return this.last = Long2ShortOpenCustomHashMap.this.n;
/*      */       } 
/*  781 */       long[] key = Long2ShortOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  783 */         if (--this.pos < 0) {
/*      */           
/*  785 */           this.last = Integer.MIN_VALUE;
/*  786 */           long k = this.wrapped.getLong(-this.pos - 1);
/*  787 */           int p = HashCommon.mix(Long2ShortOpenCustomHashMap.this.strategy.hashCode(k)) & Long2ShortOpenCustomHashMap.this.mask;
/*  788 */           while (!Long2ShortOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  789 */             p = p + 1 & Long2ShortOpenCustomHashMap.this.mask; 
/*  790 */           return p;
/*      */         } 
/*  792 */         if (key[this.pos] != 0L) {
/*  793 */           return this.last = this.pos;
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
/*  807 */       long[] key = Long2ShortOpenCustomHashMap.this.key; while (true) {
/*      */         long curr; int last;
/*  809 */         pos = (last = pos) + 1 & Long2ShortOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  811 */           if ((curr = key[pos]) == 0L) {
/*  812 */             key[last] = 0L;
/*      */             return;
/*      */           } 
/*  815 */           int slot = HashCommon.mix(Long2ShortOpenCustomHashMap.this.strategy.hashCode(curr)) & Long2ShortOpenCustomHashMap.this.mask;
/*  816 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  818 */           pos = pos + 1 & Long2ShortOpenCustomHashMap.this.mask;
/*      */         } 
/*  820 */         if (pos < last) {
/*  821 */           if (this.wrapped == null)
/*  822 */             this.wrapped = new LongArrayList(2); 
/*  823 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  825 */         key[last] = curr;
/*  826 */         Long2ShortOpenCustomHashMap.this.value[last] = Long2ShortOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  830 */       if (this.last == -1)
/*  831 */         throw new IllegalStateException(); 
/*  832 */       if (this.last == Long2ShortOpenCustomHashMap.this.n) {
/*  833 */         Long2ShortOpenCustomHashMap.this.containsNullKey = false;
/*  834 */       } else if (this.pos >= 0) {
/*  835 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  838 */         Long2ShortOpenCustomHashMap.this.remove(this.wrapped.getLong(-this.pos - 1));
/*  839 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  842 */       Long2ShortOpenCustomHashMap.this.size--;
/*  843 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  848 */       int i = n;
/*  849 */       while (i-- != 0 && hasNext())
/*  850 */         nextEntry(); 
/*  851 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Long2ShortMap.Entry> { private Long2ShortOpenCustomHashMap.MapEntry entry;
/*      */     
/*      */     public Long2ShortOpenCustomHashMap.MapEntry next() {
/*  858 */       return this.entry = new Long2ShortOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  862 */       super.remove();
/*  863 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Long2ShortMap.Entry> { private FastEntryIterator() {
/*  867 */       this.entry = new Long2ShortOpenCustomHashMap.MapEntry();
/*      */     } private final Long2ShortOpenCustomHashMap.MapEntry entry;
/*      */     public Long2ShortOpenCustomHashMap.MapEntry next() {
/*  870 */       this.entry.index = nextEntry();
/*  871 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Long2ShortMap.Entry> implements Long2ShortMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Long2ShortMap.Entry> iterator() {
/*  877 */       return new Long2ShortOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Long2ShortMap.Entry> fastIterator() {
/*  881 */       return new Long2ShortOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  886 */       if (!(o instanceof Map.Entry))
/*  887 */         return false; 
/*  888 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  889 */       if (e.getKey() == null || !(e.getKey() instanceof Long))
/*  890 */         return false; 
/*  891 */       if (e.getValue() == null || !(e.getValue() instanceof Short))
/*  892 */         return false; 
/*  893 */       long k = ((Long)e.getKey()).longValue();
/*  894 */       short v = ((Short)e.getValue()).shortValue();
/*  895 */       if (Long2ShortOpenCustomHashMap.this.strategy.equals(k, 0L)) {
/*  896 */         return (Long2ShortOpenCustomHashMap.this.containsNullKey && Long2ShortOpenCustomHashMap.this.value[Long2ShortOpenCustomHashMap.this.n] == v);
/*      */       }
/*  898 */       long[] key = Long2ShortOpenCustomHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/*  901 */       if ((curr = key[pos = HashCommon.mix(Long2ShortOpenCustomHashMap.this.strategy.hashCode(k)) & Long2ShortOpenCustomHashMap.this.mask]) == 0L)
/*  902 */         return false; 
/*  903 */       if (Long2ShortOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  904 */         return (Long2ShortOpenCustomHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  907 */         if ((curr = key[pos = pos + 1 & Long2ShortOpenCustomHashMap.this.mask]) == 0L)
/*  908 */           return false; 
/*  909 */         if (Long2ShortOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  910 */           return (Long2ShortOpenCustomHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  916 */       if (!(o instanceof Map.Entry))
/*  917 */         return false; 
/*  918 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  919 */       if (e.getKey() == null || !(e.getKey() instanceof Long))
/*  920 */         return false; 
/*  921 */       if (e.getValue() == null || !(e.getValue() instanceof Short))
/*  922 */         return false; 
/*  923 */       long k = ((Long)e.getKey()).longValue();
/*  924 */       short v = ((Short)e.getValue()).shortValue();
/*  925 */       if (Long2ShortOpenCustomHashMap.this.strategy.equals(k, 0L)) {
/*  926 */         if (Long2ShortOpenCustomHashMap.this.containsNullKey && Long2ShortOpenCustomHashMap.this.value[Long2ShortOpenCustomHashMap.this.n] == v) {
/*  927 */           Long2ShortOpenCustomHashMap.this.removeNullEntry();
/*  928 */           return true;
/*      */         } 
/*  930 */         return false;
/*      */       } 
/*      */       
/*  933 */       long[] key = Long2ShortOpenCustomHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/*  936 */       if ((curr = key[pos = HashCommon.mix(Long2ShortOpenCustomHashMap.this.strategy.hashCode(k)) & Long2ShortOpenCustomHashMap.this.mask]) == 0L)
/*  937 */         return false; 
/*  938 */       if (Long2ShortOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  939 */         if (Long2ShortOpenCustomHashMap.this.value[pos] == v) {
/*  940 */           Long2ShortOpenCustomHashMap.this.removeEntry(pos);
/*  941 */           return true;
/*      */         } 
/*  943 */         return false;
/*      */       } 
/*      */       while (true) {
/*  946 */         if ((curr = key[pos = pos + 1 & Long2ShortOpenCustomHashMap.this.mask]) == 0L)
/*  947 */           return false; 
/*  948 */         if (Long2ShortOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  949 */           Long2ShortOpenCustomHashMap.this.value[pos] == v) {
/*  950 */           Long2ShortOpenCustomHashMap.this.removeEntry(pos);
/*  951 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  958 */       return Long2ShortOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  962 */       Long2ShortOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Long2ShortMap.Entry> consumer) {
/*  967 */       if (Long2ShortOpenCustomHashMap.this.containsNullKey)
/*  968 */         consumer.accept(new AbstractLong2ShortMap.BasicEntry(Long2ShortOpenCustomHashMap.this.key[Long2ShortOpenCustomHashMap.this.n], Long2ShortOpenCustomHashMap.this.value[Long2ShortOpenCustomHashMap.this.n])); 
/*  969 */       for (int pos = Long2ShortOpenCustomHashMap.this.n; pos-- != 0;) {
/*  970 */         if (Long2ShortOpenCustomHashMap.this.key[pos] != 0L)
/*  971 */           consumer.accept(new AbstractLong2ShortMap.BasicEntry(Long2ShortOpenCustomHashMap.this.key[pos], Long2ShortOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Long2ShortMap.Entry> consumer) {
/*  976 */       AbstractLong2ShortMap.BasicEntry entry = new AbstractLong2ShortMap.BasicEntry();
/*  977 */       if (Long2ShortOpenCustomHashMap.this.containsNullKey) {
/*  978 */         entry.key = Long2ShortOpenCustomHashMap.this.key[Long2ShortOpenCustomHashMap.this.n];
/*  979 */         entry.value = Long2ShortOpenCustomHashMap.this.value[Long2ShortOpenCustomHashMap.this.n];
/*  980 */         consumer.accept(entry);
/*      */       } 
/*  982 */       for (int pos = Long2ShortOpenCustomHashMap.this.n; pos-- != 0;) {
/*  983 */         if (Long2ShortOpenCustomHashMap.this.key[pos] != 0L) {
/*  984 */           entry.key = Long2ShortOpenCustomHashMap.this.key[pos];
/*  985 */           entry.value = Long2ShortOpenCustomHashMap.this.value[pos];
/*  986 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Long2ShortMap.FastEntrySet long2ShortEntrySet() {
/*  992 */     if (this.entries == null)
/*  993 */       this.entries = new MapEntrySet(); 
/*  994 */     return this.entries;
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
/* 1011 */       return Long2ShortOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractLongSet { private KeySet() {}
/*      */     
/*      */     public LongIterator iterator() {
/* 1017 */       return new Long2ShortOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(LongConsumer consumer) {
/* 1022 */       if (Long2ShortOpenCustomHashMap.this.containsNullKey)
/* 1023 */         consumer.accept(Long2ShortOpenCustomHashMap.this.key[Long2ShortOpenCustomHashMap.this.n]); 
/* 1024 */       for (int pos = Long2ShortOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1025 */         long k = Long2ShortOpenCustomHashMap.this.key[pos];
/* 1026 */         if (k != 0L)
/* 1027 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1032 */       return Long2ShortOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(long k) {
/* 1036 */       return Long2ShortOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(long k) {
/* 1040 */       int oldSize = Long2ShortOpenCustomHashMap.this.size;
/* 1041 */       Long2ShortOpenCustomHashMap.this.remove(k);
/* 1042 */       return (Long2ShortOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1046 */       Long2ShortOpenCustomHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public LongSet keySet() {
/* 1051 */     if (this.keys == null)
/* 1052 */       this.keys = new KeySet(); 
/* 1053 */     return this.keys;
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
/* 1070 */       return Long2ShortOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public ShortCollection values() {
/* 1075 */     if (this.values == null)
/* 1076 */       this.values = (ShortCollection)new AbstractShortCollection()
/*      */         {
/*      */           public ShortIterator iterator() {
/* 1079 */             return new Long2ShortOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1083 */             return Long2ShortOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(short v) {
/* 1087 */             return Long2ShortOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1091 */             Long2ShortOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(IntConsumer consumer)
/*      */           {
/* 1096 */             if (Long2ShortOpenCustomHashMap.this.containsNullKey)
/* 1097 */               consumer.accept(Long2ShortOpenCustomHashMap.this.value[Long2ShortOpenCustomHashMap.this.n]); 
/* 1098 */             for (int pos = Long2ShortOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1099 */               if (Long2ShortOpenCustomHashMap.this.key[pos] != 0L)
/* 1100 */                 consumer.accept(Long2ShortOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1103 */     return this.values;
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
/* 1120 */     return trim(this.size);
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
/* 1144 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1145 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1146 */       return true; 
/*      */     try {
/* 1148 */       rehash(l);
/* 1149 */     } catch (OutOfMemoryError cantDoIt) {
/* 1150 */       return false;
/*      */     } 
/* 1152 */     return true;
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
/* 1168 */     long[] key = this.key;
/* 1169 */     short[] value = this.value;
/* 1170 */     int mask = newN - 1;
/* 1171 */     long[] newKey = new long[newN + 1];
/* 1172 */     short[] newValue = new short[newN + 1];
/* 1173 */     int i = this.n;
/* 1174 */     for (int j = realSize(); j-- != 0; ) {
/* 1175 */       while (key[--i] == 0L); int pos;
/* 1176 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != 0L)
/* 1177 */         while (newKey[pos = pos + 1 & mask] != 0L); 
/* 1178 */       newKey[pos] = key[i];
/* 1179 */       newValue[pos] = value[i];
/*      */     } 
/* 1181 */     newValue[newN] = value[this.n];
/* 1182 */     this.n = newN;
/* 1183 */     this.mask = mask;
/* 1184 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1185 */     this.key = newKey;
/* 1186 */     this.value = newValue;
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
/*      */   public Long2ShortOpenCustomHashMap clone() {
/*      */     Long2ShortOpenCustomHashMap c;
/*      */     try {
/* 1203 */       c = (Long2ShortOpenCustomHashMap)super.clone();
/* 1204 */     } catch (CloneNotSupportedException cantHappen) {
/* 1205 */       throw new InternalError();
/*      */     } 
/* 1207 */     c.keys = null;
/* 1208 */     c.values = null;
/* 1209 */     c.entries = null;
/* 1210 */     c.containsNullKey = this.containsNullKey;
/* 1211 */     c.key = (long[])this.key.clone();
/* 1212 */     c.value = (short[])this.value.clone();
/* 1213 */     c.strategy = this.strategy;
/* 1214 */     return c;
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
/* 1227 */     int h = 0;
/* 1228 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1229 */       while (this.key[i] == 0L)
/* 1230 */         i++; 
/* 1231 */       t = this.strategy.hashCode(this.key[i]);
/* 1232 */       t ^= this.value[i];
/* 1233 */       h += t;
/* 1234 */       i++;
/*      */     } 
/*      */     
/* 1237 */     if (this.containsNullKey)
/* 1238 */       h += this.value[this.n]; 
/* 1239 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1242 */     long[] key = this.key;
/* 1243 */     short[] value = this.value;
/* 1244 */     MapIterator i = new MapIterator();
/* 1245 */     s.defaultWriteObject();
/* 1246 */     for (int j = this.size; j-- != 0; ) {
/* 1247 */       int e = i.nextEntry();
/* 1248 */       s.writeLong(key[e]);
/* 1249 */       s.writeShort(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1254 */     s.defaultReadObject();
/* 1255 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1256 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1257 */     this.mask = this.n - 1;
/* 1258 */     long[] key = this.key = new long[this.n + 1];
/* 1259 */     short[] value = this.value = new short[this.n + 1];
/*      */ 
/*      */     
/* 1262 */     for (int i = this.size; i-- != 0; ) {
/* 1263 */       int pos; long k = s.readLong();
/* 1264 */       short v = s.readShort();
/* 1265 */       if (this.strategy.equals(k, 0L)) {
/* 1266 */         pos = this.n;
/* 1267 */         this.containsNullKey = true;
/*      */       } else {
/* 1269 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1270 */         while (key[pos] != 0L)
/* 1271 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1273 */       key[pos] = k;
/* 1274 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\Long2ShortOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */