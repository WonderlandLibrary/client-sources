/*      */ package it.unimi.dsi.fastutil.shorts;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
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
/*      */ import java.util.function.IntConsumer;
/*      */ import java.util.function.IntFunction;
/*      */ import java.util.function.IntUnaryOperator;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Short2ShortOpenCustomHashMap
/*      */   extends AbstractShort2ShortMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient short[] key;
/*      */   protected transient short[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected ShortHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Short2ShortMap.FastEntrySet entries;
/*      */   protected transient ShortSet keys;
/*      */   protected transient ShortCollection values;
/*      */   
/*      */   public Short2ShortOpenCustomHashMap(int expected, float f, ShortHash.Strategy strategy) {
/*  107 */     this.strategy = strategy;
/*  108 */     if (f <= 0.0F || f > 1.0F)
/*  109 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  110 */     if (expected < 0)
/*  111 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  112 */     this.f = f;
/*  113 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  114 */     this.mask = this.n - 1;
/*  115 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  116 */     this.key = new short[this.n + 1];
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
/*      */   public Short2ShortOpenCustomHashMap(int expected, ShortHash.Strategy strategy) {
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
/*      */   public Short2ShortOpenCustomHashMap(ShortHash.Strategy strategy) {
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
/*      */   public Short2ShortOpenCustomHashMap(Map<? extends Short, ? extends Short> m, float f, ShortHash.Strategy strategy) {
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
/*      */   public Short2ShortOpenCustomHashMap(Map<? extends Short, ? extends Short> m, ShortHash.Strategy strategy) {
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
/*      */   public Short2ShortOpenCustomHashMap(Short2ShortMap m, float f, ShortHash.Strategy strategy) {
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
/*      */   public Short2ShortOpenCustomHashMap(Short2ShortMap m, ShortHash.Strategy strategy) {
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
/*      */   public Short2ShortOpenCustomHashMap(short[] k, short[] v, float f, ShortHash.Strategy strategy) {
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
/*      */   public Short2ShortOpenCustomHashMap(short[] k, short[] v, ShortHash.Strategy strategy) {
/*  236 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ShortHash.Strategy strategy() {
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
/*      */   public void putAll(Map<? extends Short, ? extends Short> m) {
/*  278 */     if (this.f <= 0.5D) {
/*  279 */       ensureCapacity(m.size());
/*      */     } else {
/*  281 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  283 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(short k) {
/*  287 */     if (this.strategy.equals(k, (short)0)) {
/*  288 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  290 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  293 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  294 */       return -(pos + 1); 
/*  295 */     if (this.strategy.equals(k, curr)) {
/*  296 */       return pos;
/*      */     }
/*      */     while (true) {
/*  299 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  300 */         return -(pos + 1); 
/*  301 */       if (this.strategy.equals(k, curr))
/*  302 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, short k, short v) {
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
/*      */   public short put(short k, short v) {
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
/*      */   public short addTo(short k, short incr) {
/*      */     int pos;
/*  349 */     if (this.strategy.equals(k, (short)0)) {
/*  350 */       if (this.containsNullKey)
/*  351 */         return addToValue(this.n, incr); 
/*  352 */       pos = this.n;
/*  353 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  356 */       short[] key = this.key;
/*      */       short curr;
/*  358 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != 0) {
/*      */         
/*  360 */         if (this.strategy.equals(curr, k))
/*  361 */           return addToValue(pos, incr); 
/*  362 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
/*  363 */           if (this.strategy.equals(curr, k))
/*  364 */             return addToValue(pos, incr); 
/*      */         } 
/*      */       } 
/*  367 */     }  this.key[pos] = k;
/*  368 */     this.value[pos] = (short)(this.defRetValue + incr);
/*  369 */     if (this.size++ >= this.maxFill) {
/*  370 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  373 */     return this.defRetValue;
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
/*  386 */     short[] key = this.key; while (true) {
/*      */       short curr; int last;
/*  388 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  390 */         if ((curr = key[pos]) == 0) {
/*  391 */           key[last] = 0;
/*      */           return;
/*      */         } 
/*  394 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  395 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  397 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  399 */       key[last] = curr;
/*  400 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public short remove(short k) {
/*  406 */     if (this.strategy.equals(k, (short)0)) {
/*  407 */       if (this.containsNullKey)
/*  408 */         return removeNullEntry(); 
/*  409 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  412 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  415 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  416 */       return this.defRetValue; 
/*  417 */     if (this.strategy.equals(k, curr))
/*  418 */       return removeEntry(pos); 
/*      */     while (true) {
/*  420 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  421 */         return this.defRetValue; 
/*  422 */       if (this.strategy.equals(k, curr)) {
/*  423 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public short get(short k) {
/*  429 */     if (this.strategy.equals(k, (short)0)) {
/*  430 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  432 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  435 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  436 */       return this.defRetValue; 
/*  437 */     if (this.strategy.equals(k, curr)) {
/*  438 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  441 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  442 */         return this.defRetValue; 
/*  443 */       if (this.strategy.equals(k, curr)) {
/*  444 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(short k) {
/*  450 */     if (this.strategy.equals(k, (short)0)) {
/*  451 */       return this.containsNullKey;
/*      */     }
/*  453 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  456 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  457 */       return false; 
/*  458 */     if (this.strategy.equals(k, curr)) {
/*  459 */       return true;
/*      */     }
/*      */     while (true) {
/*  462 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  463 */         return false; 
/*  464 */       if (this.strategy.equals(k, curr))
/*  465 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(short v) {
/*  470 */     short[] value = this.value;
/*  471 */     short[] key = this.key;
/*  472 */     if (this.containsNullKey && value[this.n] == v)
/*  473 */       return true; 
/*  474 */     for (int i = this.n; i-- != 0;) {
/*  475 */       if (key[i] != 0 && value[i] == v)
/*  476 */         return true; 
/*  477 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short getOrDefault(short k, short defaultValue) {
/*  483 */     if (this.strategy.equals(k, (short)0)) {
/*  484 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  486 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  489 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  490 */       return defaultValue; 
/*  491 */     if (this.strategy.equals(k, curr)) {
/*  492 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  495 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  496 */         return defaultValue; 
/*  497 */       if (this.strategy.equals(k, curr)) {
/*  498 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public short putIfAbsent(short k, short v) {
/*  504 */     int pos = find(k);
/*  505 */     if (pos >= 0)
/*  506 */       return this.value[pos]; 
/*  507 */     insert(-pos - 1, k, v);
/*  508 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(short k, short v) {
/*  514 */     if (this.strategy.equals(k, (short)0)) {
/*  515 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  516 */         removeNullEntry();
/*  517 */         return true;
/*      */       } 
/*  519 */       return false;
/*      */     } 
/*      */     
/*  522 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  525 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  526 */       return false; 
/*  527 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  528 */       removeEntry(pos);
/*  529 */       return true;
/*      */     } 
/*      */     while (true) {
/*  532 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  533 */         return false; 
/*  534 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  535 */         removeEntry(pos);
/*  536 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(short k, short oldValue, short v) {
/*  543 */     int pos = find(k);
/*  544 */     if (pos < 0 || oldValue != this.value[pos])
/*  545 */       return false; 
/*  546 */     this.value[pos] = v;
/*  547 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public short replace(short k, short v) {
/*  552 */     int pos = find(k);
/*  553 */     if (pos < 0)
/*  554 */       return this.defRetValue; 
/*  555 */     short oldValue = this.value[pos];
/*  556 */     this.value[pos] = v;
/*  557 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public short computeIfAbsent(short k, IntUnaryOperator mappingFunction) {
/*  562 */     Objects.requireNonNull(mappingFunction);
/*  563 */     int pos = find(k);
/*  564 */     if (pos >= 0)
/*  565 */       return this.value[pos]; 
/*  566 */     short newValue = SafeMath.safeIntToShort(mappingFunction.applyAsInt(k));
/*  567 */     insert(-pos - 1, k, newValue);
/*  568 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short computeIfAbsentNullable(short k, IntFunction<? extends Short> mappingFunction) {
/*  574 */     Objects.requireNonNull(mappingFunction);
/*  575 */     int pos = find(k);
/*  576 */     if (pos >= 0)
/*  577 */       return this.value[pos]; 
/*  578 */     Short newValue = mappingFunction.apply(k);
/*  579 */     if (newValue == null)
/*  580 */       return this.defRetValue; 
/*  581 */     short v = newValue.shortValue();
/*  582 */     insert(-pos - 1, k, v);
/*  583 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short computeIfPresent(short k, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
/*  589 */     Objects.requireNonNull(remappingFunction);
/*  590 */     int pos = find(k);
/*  591 */     if (pos < 0)
/*  592 */       return this.defRetValue; 
/*  593 */     Short newValue = remappingFunction.apply(Short.valueOf(k), Short.valueOf(this.value[pos]));
/*  594 */     if (newValue == null) {
/*  595 */       if (this.strategy.equals(k, (short)0)) {
/*  596 */         removeNullEntry();
/*      */       } else {
/*  598 */         removeEntry(pos);
/*  599 */       }  return this.defRetValue;
/*      */     } 
/*  601 */     this.value[pos] = newValue.shortValue(); return newValue.shortValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short compute(short k, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
/*  607 */     Objects.requireNonNull(remappingFunction);
/*  608 */     int pos = find(k);
/*  609 */     Short newValue = remappingFunction.apply(Short.valueOf(k), (pos >= 0) ? Short.valueOf(this.value[pos]) : null);
/*  610 */     if (newValue == null) {
/*  611 */       if (pos >= 0)
/*  612 */         if (this.strategy.equals(k, (short)0)) {
/*  613 */           removeNullEntry();
/*      */         } else {
/*  615 */           removeEntry(pos);
/*      */         }  
/*  617 */       return this.defRetValue;
/*      */     } 
/*  619 */     short newVal = newValue.shortValue();
/*  620 */     if (pos < 0) {
/*  621 */       insert(-pos - 1, k, newVal);
/*  622 */       return newVal;
/*      */     } 
/*  624 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short merge(short k, short v, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
/*  630 */     Objects.requireNonNull(remappingFunction);
/*  631 */     int pos = find(k);
/*  632 */     if (pos < 0) {
/*  633 */       insert(-pos - 1, k, v);
/*  634 */       return v;
/*      */     } 
/*  636 */     Short newValue = remappingFunction.apply(Short.valueOf(this.value[pos]), Short.valueOf(v));
/*  637 */     if (newValue == null) {
/*  638 */       if (this.strategy.equals(k, (short)0)) {
/*  639 */         removeNullEntry();
/*      */       } else {
/*  641 */         removeEntry(pos);
/*  642 */       }  return this.defRetValue;
/*      */     } 
/*  644 */     this.value[pos] = newValue.shortValue(); return newValue.shortValue();
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
/*  655 */     if (this.size == 0)
/*      */       return; 
/*  657 */     this.size = 0;
/*  658 */     this.containsNullKey = false;
/*  659 */     Arrays.fill(this.key, (short)0);
/*      */   }
/*      */   
/*      */   public int size() {
/*  663 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  667 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Short2ShortMap.Entry, Map.Entry<Short, Short>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  679 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public short getShortKey() {
/*  685 */       return Short2ShortOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public short getShortValue() {
/*  689 */       return Short2ShortOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public short setValue(short v) {
/*  693 */       short oldValue = Short2ShortOpenCustomHashMap.this.value[this.index];
/*  694 */       Short2ShortOpenCustomHashMap.this.value[this.index] = v;
/*  695 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Short getKey() {
/*  705 */       return Short.valueOf(Short2ShortOpenCustomHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Short getValue() {
/*  715 */       return Short.valueOf(Short2ShortOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Short setValue(Short v) {
/*  725 */       return Short.valueOf(setValue(v.shortValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  730 */       if (!(o instanceof Map.Entry))
/*  731 */         return false; 
/*  732 */       Map.Entry<Short, Short> e = (Map.Entry<Short, Short>)o;
/*  733 */       return (Short2ShortOpenCustomHashMap.this.strategy.equals(Short2ShortOpenCustomHashMap.this.key[this.index], ((Short)e.getKey()).shortValue()) && Short2ShortOpenCustomHashMap.this.value[this.index] == ((Short)e
/*  734 */         .getValue()).shortValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  738 */       return Short2ShortOpenCustomHashMap.this.strategy.hashCode(Short2ShortOpenCustomHashMap.this.key[this.index]) ^ Short2ShortOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/*  742 */       return Short2ShortOpenCustomHashMap.this.key[this.index] + "=>" + Short2ShortOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  752 */     int pos = Short2ShortOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  759 */     int last = -1;
/*      */     
/*  761 */     int c = Short2ShortOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  765 */     boolean mustReturnNullKey = Short2ShortOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     ShortArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  772 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  775 */       if (!hasNext())
/*  776 */         throw new NoSuchElementException(); 
/*  777 */       this.c--;
/*  778 */       if (this.mustReturnNullKey) {
/*  779 */         this.mustReturnNullKey = false;
/*  780 */         return this.last = Short2ShortOpenCustomHashMap.this.n;
/*      */       } 
/*  782 */       short[] key = Short2ShortOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  784 */         if (--this.pos < 0) {
/*      */           
/*  786 */           this.last = Integer.MIN_VALUE;
/*  787 */           short k = this.wrapped.getShort(-this.pos - 1);
/*  788 */           int p = HashCommon.mix(Short2ShortOpenCustomHashMap.this.strategy.hashCode(k)) & Short2ShortOpenCustomHashMap.this.mask;
/*  789 */           while (!Short2ShortOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  790 */             p = p + 1 & Short2ShortOpenCustomHashMap.this.mask; 
/*  791 */           return p;
/*      */         } 
/*  793 */         if (key[this.pos] != 0) {
/*  794 */           return this.last = this.pos;
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
/*  808 */       short[] key = Short2ShortOpenCustomHashMap.this.key; while (true) {
/*      */         short curr; int last;
/*  810 */         pos = (last = pos) + 1 & Short2ShortOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  812 */           if ((curr = key[pos]) == 0) {
/*  813 */             key[last] = 0;
/*      */             return;
/*      */           } 
/*  816 */           int slot = HashCommon.mix(Short2ShortOpenCustomHashMap.this.strategy.hashCode(curr)) & Short2ShortOpenCustomHashMap.this.mask;
/*  817 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  819 */           pos = pos + 1 & Short2ShortOpenCustomHashMap.this.mask;
/*      */         } 
/*  821 */         if (pos < last) {
/*  822 */           if (this.wrapped == null)
/*  823 */             this.wrapped = new ShortArrayList(2); 
/*  824 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  826 */         key[last] = curr;
/*  827 */         Short2ShortOpenCustomHashMap.this.value[last] = Short2ShortOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  831 */       if (this.last == -1)
/*  832 */         throw new IllegalStateException(); 
/*  833 */       if (this.last == Short2ShortOpenCustomHashMap.this.n) {
/*  834 */         Short2ShortOpenCustomHashMap.this.containsNullKey = false;
/*  835 */       } else if (this.pos >= 0) {
/*  836 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  839 */         Short2ShortOpenCustomHashMap.this.remove(this.wrapped.getShort(-this.pos - 1));
/*  840 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  843 */       Short2ShortOpenCustomHashMap.this.size--;
/*  844 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  849 */       int i = n;
/*  850 */       while (i-- != 0 && hasNext())
/*  851 */         nextEntry(); 
/*  852 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Short2ShortMap.Entry> { private Short2ShortOpenCustomHashMap.MapEntry entry;
/*      */     
/*      */     public Short2ShortOpenCustomHashMap.MapEntry next() {
/*  859 */       return this.entry = new Short2ShortOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  863 */       super.remove();
/*  864 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Short2ShortMap.Entry> { private FastEntryIterator() {
/*  868 */       this.entry = new Short2ShortOpenCustomHashMap.MapEntry();
/*      */     } private final Short2ShortOpenCustomHashMap.MapEntry entry;
/*      */     public Short2ShortOpenCustomHashMap.MapEntry next() {
/*  871 */       this.entry.index = nextEntry();
/*  872 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Short2ShortMap.Entry> implements Short2ShortMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Short2ShortMap.Entry> iterator() {
/*  878 */       return new Short2ShortOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Short2ShortMap.Entry> fastIterator() {
/*  882 */       return new Short2ShortOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  887 */       if (!(o instanceof Map.Entry))
/*  888 */         return false; 
/*  889 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  890 */       if (e.getKey() == null || !(e.getKey() instanceof Short))
/*  891 */         return false; 
/*  892 */       if (e.getValue() == null || !(e.getValue() instanceof Short))
/*  893 */         return false; 
/*  894 */       short k = ((Short)e.getKey()).shortValue();
/*  895 */       short v = ((Short)e.getValue()).shortValue();
/*  896 */       if (Short2ShortOpenCustomHashMap.this.strategy.equals(k, (short)0)) {
/*  897 */         return (Short2ShortOpenCustomHashMap.this.containsNullKey && Short2ShortOpenCustomHashMap.this.value[Short2ShortOpenCustomHashMap.this.n] == v);
/*      */       }
/*  899 */       short[] key = Short2ShortOpenCustomHashMap.this.key;
/*      */       short curr;
/*      */       int pos;
/*  902 */       if ((curr = key[pos = HashCommon.mix(Short2ShortOpenCustomHashMap.this.strategy.hashCode(k)) & Short2ShortOpenCustomHashMap.this.mask]) == 0)
/*      */       {
/*  904 */         return false; } 
/*  905 */       if (Short2ShortOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  906 */         return (Short2ShortOpenCustomHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  909 */         if ((curr = key[pos = pos + 1 & Short2ShortOpenCustomHashMap.this.mask]) == 0)
/*  910 */           return false; 
/*  911 */         if (Short2ShortOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  912 */           return (Short2ShortOpenCustomHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  918 */       if (!(o instanceof Map.Entry))
/*  919 */         return false; 
/*  920 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  921 */       if (e.getKey() == null || !(e.getKey() instanceof Short))
/*  922 */         return false; 
/*  923 */       if (e.getValue() == null || !(e.getValue() instanceof Short))
/*  924 */         return false; 
/*  925 */       short k = ((Short)e.getKey()).shortValue();
/*  926 */       short v = ((Short)e.getValue()).shortValue();
/*  927 */       if (Short2ShortOpenCustomHashMap.this.strategy.equals(k, (short)0)) {
/*  928 */         if (Short2ShortOpenCustomHashMap.this.containsNullKey && Short2ShortOpenCustomHashMap.this.value[Short2ShortOpenCustomHashMap.this.n] == v) {
/*  929 */           Short2ShortOpenCustomHashMap.this.removeNullEntry();
/*  930 */           return true;
/*      */         } 
/*  932 */         return false;
/*      */       } 
/*      */       
/*  935 */       short[] key = Short2ShortOpenCustomHashMap.this.key;
/*      */       short curr;
/*      */       int pos;
/*  938 */       if ((curr = key[pos = HashCommon.mix(Short2ShortOpenCustomHashMap.this.strategy.hashCode(k)) & Short2ShortOpenCustomHashMap.this.mask]) == 0)
/*      */       {
/*  940 */         return false; } 
/*  941 */       if (Short2ShortOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  942 */         if (Short2ShortOpenCustomHashMap.this.value[pos] == v) {
/*  943 */           Short2ShortOpenCustomHashMap.this.removeEntry(pos);
/*  944 */           return true;
/*      */         } 
/*  946 */         return false;
/*      */       } 
/*      */       while (true) {
/*  949 */         if ((curr = key[pos = pos + 1 & Short2ShortOpenCustomHashMap.this.mask]) == 0)
/*  950 */           return false; 
/*  951 */         if (Short2ShortOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  952 */           Short2ShortOpenCustomHashMap.this.value[pos] == v) {
/*  953 */           Short2ShortOpenCustomHashMap.this.removeEntry(pos);
/*  954 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  961 */       return Short2ShortOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  965 */       Short2ShortOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Short2ShortMap.Entry> consumer) {
/*  970 */       if (Short2ShortOpenCustomHashMap.this.containsNullKey)
/*  971 */         consumer.accept(new AbstractShort2ShortMap.BasicEntry(Short2ShortOpenCustomHashMap.this.key[Short2ShortOpenCustomHashMap.this.n], Short2ShortOpenCustomHashMap.this.value[Short2ShortOpenCustomHashMap.this.n])); 
/*  972 */       for (int pos = Short2ShortOpenCustomHashMap.this.n; pos-- != 0;) {
/*  973 */         if (Short2ShortOpenCustomHashMap.this.key[pos] != 0)
/*  974 */           consumer.accept(new AbstractShort2ShortMap.BasicEntry(Short2ShortOpenCustomHashMap.this.key[pos], Short2ShortOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Short2ShortMap.Entry> consumer) {
/*  979 */       AbstractShort2ShortMap.BasicEntry entry = new AbstractShort2ShortMap.BasicEntry();
/*  980 */       if (Short2ShortOpenCustomHashMap.this.containsNullKey) {
/*  981 */         entry.key = Short2ShortOpenCustomHashMap.this.key[Short2ShortOpenCustomHashMap.this.n];
/*  982 */         entry.value = Short2ShortOpenCustomHashMap.this.value[Short2ShortOpenCustomHashMap.this.n];
/*  983 */         consumer.accept(entry);
/*      */       } 
/*  985 */       for (int pos = Short2ShortOpenCustomHashMap.this.n; pos-- != 0;) {
/*  986 */         if (Short2ShortOpenCustomHashMap.this.key[pos] != 0) {
/*  987 */           entry.key = Short2ShortOpenCustomHashMap.this.key[pos];
/*  988 */           entry.value = Short2ShortOpenCustomHashMap.this.value[pos];
/*  989 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Short2ShortMap.FastEntrySet short2ShortEntrySet() {
/*  995 */     if (this.entries == null)
/*  996 */       this.entries = new MapEntrySet(); 
/*  997 */     return this.entries;
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
/*      */     implements ShortIterator
/*      */   {
/*      */     public short nextShort() {
/* 1014 */       return Short2ShortOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractShortSet { private KeySet() {}
/*      */     
/*      */     public ShortIterator iterator() {
/* 1020 */       return new Short2ShortOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer consumer) {
/* 1025 */       if (Short2ShortOpenCustomHashMap.this.containsNullKey)
/* 1026 */         consumer.accept(Short2ShortOpenCustomHashMap.this.key[Short2ShortOpenCustomHashMap.this.n]); 
/* 1027 */       for (int pos = Short2ShortOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1028 */         short k = Short2ShortOpenCustomHashMap.this.key[pos];
/* 1029 */         if (k != 0)
/* 1030 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1035 */       return Short2ShortOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(short k) {
/* 1039 */       return Short2ShortOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(short k) {
/* 1043 */       int oldSize = Short2ShortOpenCustomHashMap.this.size;
/* 1044 */       Short2ShortOpenCustomHashMap.this.remove(k);
/* 1045 */       return (Short2ShortOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1049 */       Short2ShortOpenCustomHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ShortSet keySet() {
/* 1054 */     if (this.keys == null)
/* 1055 */       this.keys = new KeySet(); 
/* 1056 */     return this.keys;
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
/* 1073 */       return Short2ShortOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public ShortCollection values() {
/* 1078 */     if (this.values == null)
/* 1079 */       this.values = new AbstractShortCollection()
/*      */         {
/*      */           public ShortIterator iterator() {
/* 1082 */             return new Short2ShortOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1086 */             return Short2ShortOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(short v) {
/* 1090 */             return Short2ShortOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1094 */             Short2ShortOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(IntConsumer consumer)
/*      */           {
/* 1099 */             if (Short2ShortOpenCustomHashMap.this.containsNullKey)
/* 1100 */               consumer.accept(Short2ShortOpenCustomHashMap.this.value[Short2ShortOpenCustomHashMap.this.n]); 
/* 1101 */             for (int pos = Short2ShortOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1102 */               if (Short2ShortOpenCustomHashMap.this.key[pos] != 0)
/* 1103 */                 consumer.accept(Short2ShortOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1106 */     return this.values;
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
/* 1123 */     return trim(this.size);
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
/* 1147 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1148 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1149 */       return true; 
/*      */     try {
/* 1151 */       rehash(l);
/* 1152 */     } catch (OutOfMemoryError cantDoIt) {
/* 1153 */       return false;
/*      */     } 
/* 1155 */     return true;
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
/* 1171 */     short[] key = this.key;
/* 1172 */     short[] value = this.value;
/* 1173 */     int mask = newN - 1;
/* 1174 */     short[] newKey = new short[newN + 1];
/* 1175 */     short[] newValue = new short[newN + 1];
/* 1176 */     int i = this.n;
/* 1177 */     for (int j = realSize(); j-- != 0; ) {
/* 1178 */       while (key[--i] == 0); int pos;
/* 1179 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != 0)
/*      */       {
/* 1181 */         while (newKey[pos = pos + 1 & mask] != 0); } 
/* 1182 */       newKey[pos] = key[i];
/* 1183 */       newValue[pos] = value[i];
/*      */     } 
/* 1185 */     newValue[newN] = value[this.n];
/* 1186 */     this.n = newN;
/* 1187 */     this.mask = mask;
/* 1188 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1189 */     this.key = newKey;
/* 1190 */     this.value = newValue;
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
/*      */   public Short2ShortOpenCustomHashMap clone() {
/*      */     Short2ShortOpenCustomHashMap c;
/*      */     try {
/* 1207 */       c = (Short2ShortOpenCustomHashMap)super.clone();
/* 1208 */     } catch (CloneNotSupportedException cantHappen) {
/* 1209 */       throw new InternalError();
/*      */     } 
/* 1211 */     c.keys = null;
/* 1212 */     c.values = null;
/* 1213 */     c.entries = null;
/* 1214 */     c.containsNullKey = this.containsNullKey;
/* 1215 */     c.key = (short[])this.key.clone();
/* 1216 */     c.value = (short[])this.value.clone();
/* 1217 */     c.strategy = this.strategy;
/* 1218 */     return c;
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
/* 1231 */     int h = 0;
/* 1232 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1233 */       while (this.key[i] == 0)
/* 1234 */         i++; 
/* 1235 */       t = this.strategy.hashCode(this.key[i]);
/* 1236 */       t ^= this.value[i];
/* 1237 */       h += t;
/* 1238 */       i++;
/*      */     } 
/*      */     
/* 1241 */     if (this.containsNullKey)
/* 1242 */       h += this.value[this.n]; 
/* 1243 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1246 */     short[] key = this.key;
/* 1247 */     short[] value = this.value;
/* 1248 */     MapIterator i = new MapIterator();
/* 1249 */     s.defaultWriteObject();
/* 1250 */     for (int j = this.size; j-- != 0; ) {
/* 1251 */       int e = i.nextEntry();
/* 1252 */       s.writeShort(key[e]);
/* 1253 */       s.writeShort(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1258 */     s.defaultReadObject();
/* 1259 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1260 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1261 */     this.mask = this.n - 1;
/* 1262 */     short[] key = this.key = new short[this.n + 1];
/* 1263 */     short[] value = this.value = new short[this.n + 1];
/*      */ 
/*      */     
/* 1266 */     for (int i = this.size; i-- != 0; ) {
/* 1267 */       int pos; short k = s.readShort();
/* 1268 */       short v = s.readShort();
/* 1269 */       if (this.strategy.equals(k, (short)0)) {
/* 1270 */         pos = this.n;
/* 1271 */         this.containsNullKey = true;
/*      */       } else {
/* 1273 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1274 */         while (key[pos] != 0)
/* 1275 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1277 */       key[pos] = k;
/* 1278 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\Short2ShortOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */