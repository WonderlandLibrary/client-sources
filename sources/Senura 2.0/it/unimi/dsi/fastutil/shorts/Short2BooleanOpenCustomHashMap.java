/*      */ package it.unimi.dsi.fastutil.shorts;
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
/*      */ import java.util.function.IntConsumer;
/*      */ import java.util.function.IntFunction;
/*      */ import java.util.function.IntPredicate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Short2BooleanOpenCustomHashMap
/*      */   extends AbstractShort2BooleanMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient short[] key;
/*      */   protected transient boolean[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected ShortHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Short2BooleanMap.FastEntrySet entries;
/*      */   protected transient ShortSet keys;
/*      */   protected transient BooleanCollection values;
/*      */   
/*      */   public Short2BooleanOpenCustomHashMap(int expected, float f, ShortHash.Strategy strategy) {
/*  108 */     this.strategy = strategy;
/*  109 */     if (f <= 0.0F || f > 1.0F)
/*  110 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  111 */     if (expected < 0)
/*  112 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  113 */     this.f = f;
/*  114 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  115 */     this.mask = this.n - 1;
/*  116 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  117 */     this.key = new short[this.n + 1];
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
/*      */   public Short2BooleanOpenCustomHashMap(int expected, ShortHash.Strategy strategy) {
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
/*      */   public Short2BooleanOpenCustomHashMap(ShortHash.Strategy strategy) {
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
/*      */   public Short2BooleanOpenCustomHashMap(Map<? extends Short, ? extends Boolean> m, float f, ShortHash.Strategy strategy) {
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
/*      */   public Short2BooleanOpenCustomHashMap(Map<? extends Short, ? extends Boolean> m, ShortHash.Strategy strategy) {
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
/*      */   public Short2BooleanOpenCustomHashMap(Short2BooleanMap m, float f, ShortHash.Strategy strategy) {
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
/*      */   public Short2BooleanOpenCustomHashMap(Short2BooleanMap m, ShortHash.Strategy strategy) {
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
/*      */   public Short2BooleanOpenCustomHashMap(short[] k, boolean[] v, float f, ShortHash.Strategy strategy) {
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
/*      */   public Short2BooleanOpenCustomHashMap(short[] k, boolean[] v, ShortHash.Strategy strategy) {
/*  237 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ShortHash.Strategy strategy() {
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
/*      */   public void putAll(Map<? extends Short, ? extends Boolean> m) {
/*  279 */     if (this.f <= 0.5D) {
/*  280 */       ensureCapacity(m.size());
/*      */     } else {
/*  282 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  284 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(short k) {
/*  288 */     if (this.strategy.equals(k, (short)0)) {
/*  289 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  291 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  294 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  295 */       return -(pos + 1); 
/*  296 */     if (this.strategy.equals(k, curr)) {
/*  297 */       return pos;
/*      */     }
/*      */     while (true) {
/*  300 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  301 */         return -(pos + 1); 
/*  302 */       if (this.strategy.equals(k, curr))
/*  303 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, short k, boolean v) {
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
/*      */   public boolean put(short k, boolean v) {
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
/*  338 */     short[] key = this.key; while (true) {
/*      */       short curr; int last;
/*  340 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  342 */         if ((curr = key[pos]) == 0) {
/*  343 */           key[last] = 0;
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
/*      */   public boolean remove(short k) {
/*  358 */     if (this.strategy.equals(k, (short)0)) {
/*  359 */       if (this.containsNullKey)
/*  360 */         return removeNullEntry(); 
/*  361 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  364 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  367 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  368 */       return this.defRetValue; 
/*  369 */     if (this.strategy.equals(k, curr))
/*  370 */       return removeEntry(pos); 
/*      */     while (true) {
/*  372 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  373 */         return this.defRetValue; 
/*  374 */       if (this.strategy.equals(k, curr)) {
/*  375 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean get(short k) {
/*  381 */     if (this.strategy.equals(k, (short)0)) {
/*  382 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  384 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  387 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  388 */       return this.defRetValue; 
/*  389 */     if (this.strategy.equals(k, curr)) {
/*  390 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  393 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  394 */         return this.defRetValue; 
/*  395 */       if (this.strategy.equals(k, curr)) {
/*  396 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(short k) {
/*  402 */     if (this.strategy.equals(k, (short)0)) {
/*  403 */       return this.containsNullKey;
/*      */     }
/*  405 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  408 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  409 */       return false; 
/*  410 */     if (this.strategy.equals(k, curr)) {
/*  411 */       return true;
/*      */     }
/*      */     while (true) {
/*  414 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  415 */         return false; 
/*  416 */       if (this.strategy.equals(k, curr))
/*  417 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(boolean v) {
/*  422 */     boolean[] value = this.value;
/*  423 */     short[] key = this.key;
/*  424 */     if (this.containsNullKey && value[this.n] == v)
/*  425 */       return true; 
/*  426 */     for (int i = this.n; i-- != 0;) {
/*  427 */       if (key[i] != 0 && value[i] == v)
/*  428 */         return true; 
/*  429 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getOrDefault(short k, boolean defaultValue) {
/*  435 */     if (this.strategy.equals(k, (short)0)) {
/*  436 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  438 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  441 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  442 */       return defaultValue; 
/*  443 */     if (this.strategy.equals(k, curr)) {
/*  444 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  447 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  448 */         return defaultValue; 
/*  449 */       if (this.strategy.equals(k, curr)) {
/*  450 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean putIfAbsent(short k, boolean v) {
/*  456 */     int pos = find(k);
/*  457 */     if (pos >= 0)
/*  458 */       return this.value[pos]; 
/*  459 */     insert(-pos - 1, k, v);
/*  460 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(short k, boolean v) {
/*  466 */     if (this.strategy.equals(k, (short)0)) {
/*  467 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  468 */         removeNullEntry();
/*  469 */         return true;
/*      */       } 
/*  471 */       return false;
/*      */     } 
/*      */     
/*  474 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  477 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  478 */       return false; 
/*  479 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  480 */       removeEntry(pos);
/*  481 */       return true;
/*      */     } 
/*      */     while (true) {
/*  484 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  485 */         return false; 
/*  486 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  487 */         removeEntry(pos);
/*  488 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(short k, boolean oldValue, boolean v) {
/*  495 */     int pos = find(k);
/*  496 */     if (pos < 0 || oldValue != this.value[pos])
/*  497 */       return false; 
/*  498 */     this.value[pos] = v;
/*  499 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(short k, boolean v) {
/*  504 */     int pos = find(k);
/*  505 */     if (pos < 0)
/*  506 */       return this.defRetValue; 
/*  507 */     boolean oldValue = this.value[pos];
/*  508 */     this.value[pos] = v;
/*  509 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean computeIfAbsent(short k, IntPredicate mappingFunction) {
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
/*      */   public boolean computeIfAbsentNullable(short k, IntFunction<? extends Boolean> mappingFunction) {
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
/*      */   public boolean computeIfPresent(short k, BiFunction<? super Short, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  541 */     Objects.requireNonNull(remappingFunction);
/*  542 */     int pos = find(k);
/*  543 */     if (pos < 0)
/*  544 */       return this.defRetValue; 
/*  545 */     Boolean newValue = remappingFunction.apply(Short.valueOf(k), Boolean.valueOf(this.value[pos]));
/*  546 */     if (newValue == null) {
/*  547 */       if (this.strategy.equals(k, (short)0)) {
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
/*      */   public boolean compute(short k, BiFunction<? super Short, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  559 */     Objects.requireNonNull(remappingFunction);
/*  560 */     int pos = find(k);
/*  561 */     Boolean newValue = remappingFunction.apply(Short.valueOf(k), 
/*  562 */         (pos >= 0) ? Boolean.valueOf(this.value[pos]) : null);
/*  563 */     if (newValue == null) {
/*  564 */       if (pos >= 0)
/*  565 */         if (this.strategy.equals(k, (short)0)) {
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
/*      */   public boolean merge(short k, boolean v, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  583 */     Objects.requireNonNull(remappingFunction);
/*  584 */     int pos = find(k);
/*  585 */     if (pos < 0) {
/*  586 */       insert(-pos - 1, k, v);
/*  587 */       return v;
/*      */     } 
/*  589 */     Boolean newValue = remappingFunction.apply(Boolean.valueOf(this.value[pos]), Boolean.valueOf(v));
/*  590 */     if (newValue == null) {
/*  591 */       if (this.strategy.equals(k, (short)0)) {
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
/*  612 */     Arrays.fill(this.key, (short)0);
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
/*      */     implements Short2BooleanMap.Entry, Map.Entry<Short, Boolean>
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
/*      */     public short getShortKey() {
/*  638 */       return Short2BooleanOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public boolean getBooleanValue() {
/*  642 */       return Short2BooleanOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public boolean setValue(boolean v) {
/*  646 */       boolean oldValue = Short2BooleanOpenCustomHashMap.this.value[this.index];
/*  647 */       Short2BooleanOpenCustomHashMap.this.value[this.index] = v;
/*  648 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Short getKey() {
/*  658 */       return Short.valueOf(Short2BooleanOpenCustomHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean getValue() {
/*  668 */       return Boolean.valueOf(Short2BooleanOpenCustomHashMap.this.value[this.index]);
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
/*  685 */       Map.Entry<Short, Boolean> e = (Map.Entry<Short, Boolean>)o;
/*  686 */       return (Short2BooleanOpenCustomHashMap.this.strategy.equals(Short2BooleanOpenCustomHashMap.this.key[this.index], ((Short)e.getKey()).shortValue()) && Short2BooleanOpenCustomHashMap.this.value[this.index] == ((Boolean)e
/*  687 */         .getValue()).booleanValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  691 */       return Short2BooleanOpenCustomHashMap.this.strategy.hashCode(Short2BooleanOpenCustomHashMap.this.key[this.index]) ^ (Short2BooleanOpenCustomHashMap.this.value[this.index] ? 1231 : 1237);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  695 */       return Short2BooleanOpenCustomHashMap.this.key[this.index] + "=>" + Short2BooleanOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  705 */     int pos = Short2BooleanOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  712 */     int last = -1;
/*      */     
/*  714 */     int c = Short2BooleanOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  718 */     boolean mustReturnNullKey = Short2BooleanOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     ShortArrayList wrapped;
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
/*  733 */         return this.last = Short2BooleanOpenCustomHashMap.this.n;
/*      */       } 
/*  735 */       short[] key = Short2BooleanOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  737 */         if (--this.pos < 0) {
/*      */           
/*  739 */           this.last = Integer.MIN_VALUE;
/*  740 */           short k = this.wrapped.getShort(-this.pos - 1);
/*  741 */           int p = HashCommon.mix(Short2BooleanOpenCustomHashMap.this.strategy.hashCode(k)) & Short2BooleanOpenCustomHashMap.this.mask;
/*  742 */           while (!Short2BooleanOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  743 */             p = p + 1 & Short2BooleanOpenCustomHashMap.this.mask; 
/*  744 */           return p;
/*      */         } 
/*  746 */         if (key[this.pos] != 0) {
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
/*  761 */       short[] key = Short2BooleanOpenCustomHashMap.this.key; while (true) {
/*      */         short curr; int last;
/*  763 */         pos = (last = pos) + 1 & Short2BooleanOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  765 */           if ((curr = key[pos]) == 0) {
/*  766 */             key[last] = 0;
/*      */             return;
/*      */           } 
/*  769 */           int slot = HashCommon.mix(Short2BooleanOpenCustomHashMap.this.strategy.hashCode(curr)) & Short2BooleanOpenCustomHashMap.this.mask;
/*  770 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  772 */           pos = pos + 1 & Short2BooleanOpenCustomHashMap.this.mask;
/*      */         } 
/*  774 */         if (pos < last) {
/*  775 */           if (this.wrapped == null)
/*  776 */             this.wrapped = new ShortArrayList(2); 
/*  777 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  779 */         key[last] = curr;
/*  780 */         Short2BooleanOpenCustomHashMap.this.value[last] = Short2BooleanOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  784 */       if (this.last == -1)
/*  785 */         throw new IllegalStateException(); 
/*  786 */       if (this.last == Short2BooleanOpenCustomHashMap.this.n) {
/*  787 */         Short2BooleanOpenCustomHashMap.this.containsNullKey = false;
/*  788 */       } else if (this.pos >= 0) {
/*  789 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  792 */         Short2BooleanOpenCustomHashMap.this.remove(this.wrapped.getShort(-this.pos - 1));
/*  793 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  796 */       Short2BooleanOpenCustomHashMap.this.size--;
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
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Short2BooleanMap.Entry> { private Short2BooleanOpenCustomHashMap.MapEntry entry;
/*      */     
/*      */     public Short2BooleanOpenCustomHashMap.MapEntry next() {
/*  812 */       return this.entry = new Short2BooleanOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  816 */       super.remove();
/*  817 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Short2BooleanMap.Entry> { private FastEntryIterator() {
/*  821 */       this.entry = new Short2BooleanOpenCustomHashMap.MapEntry();
/*      */     } private final Short2BooleanOpenCustomHashMap.MapEntry entry;
/*      */     public Short2BooleanOpenCustomHashMap.MapEntry next() {
/*  824 */       this.entry.index = nextEntry();
/*  825 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Short2BooleanMap.Entry> implements Short2BooleanMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Short2BooleanMap.Entry> iterator() {
/*  831 */       return new Short2BooleanOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Short2BooleanMap.Entry> fastIterator() {
/*  835 */       return new Short2BooleanOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  840 */       if (!(o instanceof Map.Entry))
/*  841 */         return false; 
/*  842 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  843 */       if (e.getKey() == null || !(e.getKey() instanceof Short))
/*  844 */         return false; 
/*  845 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/*  846 */         return false; 
/*  847 */       short k = ((Short)e.getKey()).shortValue();
/*  848 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/*  849 */       if (Short2BooleanOpenCustomHashMap.this.strategy.equals(k, (short)0)) {
/*  850 */         return (Short2BooleanOpenCustomHashMap.this.containsNullKey && Short2BooleanOpenCustomHashMap.this.value[Short2BooleanOpenCustomHashMap.this.n] == v);
/*      */       }
/*  852 */       short[] key = Short2BooleanOpenCustomHashMap.this.key;
/*      */       short curr;
/*      */       int pos;
/*  855 */       if ((curr = key[pos = HashCommon.mix(Short2BooleanOpenCustomHashMap.this.strategy.hashCode(k)) & Short2BooleanOpenCustomHashMap.this.mask]) == 0)
/*      */       {
/*  857 */         return false; } 
/*  858 */       if (Short2BooleanOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  859 */         return (Short2BooleanOpenCustomHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  862 */         if ((curr = key[pos = pos + 1 & Short2BooleanOpenCustomHashMap.this.mask]) == 0)
/*  863 */           return false; 
/*  864 */         if (Short2BooleanOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  865 */           return (Short2BooleanOpenCustomHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  871 */       if (!(o instanceof Map.Entry))
/*  872 */         return false; 
/*  873 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  874 */       if (e.getKey() == null || !(e.getKey() instanceof Short))
/*  875 */         return false; 
/*  876 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/*  877 */         return false; 
/*  878 */       short k = ((Short)e.getKey()).shortValue();
/*  879 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/*  880 */       if (Short2BooleanOpenCustomHashMap.this.strategy.equals(k, (short)0)) {
/*  881 */         if (Short2BooleanOpenCustomHashMap.this.containsNullKey && Short2BooleanOpenCustomHashMap.this.value[Short2BooleanOpenCustomHashMap.this.n] == v) {
/*  882 */           Short2BooleanOpenCustomHashMap.this.removeNullEntry();
/*  883 */           return true;
/*      */         } 
/*  885 */         return false;
/*      */       } 
/*      */       
/*  888 */       short[] key = Short2BooleanOpenCustomHashMap.this.key;
/*      */       short curr;
/*      */       int pos;
/*  891 */       if ((curr = key[pos = HashCommon.mix(Short2BooleanOpenCustomHashMap.this.strategy.hashCode(k)) & Short2BooleanOpenCustomHashMap.this.mask]) == 0)
/*      */       {
/*  893 */         return false; } 
/*  894 */       if (Short2BooleanOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  895 */         if (Short2BooleanOpenCustomHashMap.this.value[pos] == v) {
/*  896 */           Short2BooleanOpenCustomHashMap.this.removeEntry(pos);
/*  897 */           return true;
/*      */         } 
/*  899 */         return false;
/*      */       } 
/*      */       while (true) {
/*  902 */         if ((curr = key[pos = pos + 1 & Short2BooleanOpenCustomHashMap.this.mask]) == 0)
/*  903 */           return false; 
/*  904 */         if (Short2BooleanOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  905 */           Short2BooleanOpenCustomHashMap.this.value[pos] == v) {
/*  906 */           Short2BooleanOpenCustomHashMap.this.removeEntry(pos);
/*  907 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  914 */       return Short2BooleanOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  918 */       Short2BooleanOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Short2BooleanMap.Entry> consumer) {
/*  923 */       if (Short2BooleanOpenCustomHashMap.this.containsNullKey)
/*  924 */         consumer.accept(new AbstractShort2BooleanMap.BasicEntry(Short2BooleanOpenCustomHashMap.this.key[Short2BooleanOpenCustomHashMap.this.n], Short2BooleanOpenCustomHashMap.this.value[Short2BooleanOpenCustomHashMap.this.n])); 
/*  925 */       for (int pos = Short2BooleanOpenCustomHashMap.this.n; pos-- != 0;) {
/*  926 */         if (Short2BooleanOpenCustomHashMap.this.key[pos] != 0)
/*  927 */           consumer.accept(new AbstractShort2BooleanMap.BasicEntry(Short2BooleanOpenCustomHashMap.this.key[pos], Short2BooleanOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Short2BooleanMap.Entry> consumer) {
/*  932 */       AbstractShort2BooleanMap.BasicEntry entry = new AbstractShort2BooleanMap.BasicEntry();
/*  933 */       if (Short2BooleanOpenCustomHashMap.this.containsNullKey) {
/*  934 */         entry.key = Short2BooleanOpenCustomHashMap.this.key[Short2BooleanOpenCustomHashMap.this.n];
/*  935 */         entry.value = Short2BooleanOpenCustomHashMap.this.value[Short2BooleanOpenCustomHashMap.this.n];
/*  936 */         consumer.accept(entry);
/*      */       } 
/*  938 */       for (int pos = Short2BooleanOpenCustomHashMap.this.n; pos-- != 0;) {
/*  939 */         if (Short2BooleanOpenCustomHashMap.this.key[pos] != 0) {
/*  940 */           entry.key = Short2BooleanOpenCustomHashMap.this.key[pos];
/*  941 */           entry.value = Short2BooleanOpenCustomHashMap.this.value[pos];
/*  942 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Short2BooleanMap.FastEntrySet short2BooleanEntrySet() {
/*  948 */     if (this.entries == null)
/*  949 */       this.entries = new MapEntrySet(); 
/*  950 */     return this.entries;
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
/*  967 */       return Short2BooleanOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractShortSet { private KeySet() {}
/*      */     
/*      */     public ShortIterator iterator() {
/*  973 */       return new Short2BooleanOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer consumer) {
/*  978 */       if (Short2BooleanOpenCustomHashMap.this.containsNullKey)
/*  979 */         consumer.accept(Short2BooleanOpenCustomHashMap.this.key[Short2BooleanOpenCustomHashMap.this.n]); 
/*  980 */       for (int pos = Short2BooleanOpenCustomHashMap.this.n; pos-- != 0; ) {
/*  981 */         short k = Short2BooleanOpenCustomHashMap.this.key[pos];
/*  982 */         if (k != 0)
/*  983 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  988 */       return Short2BooleanOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(short k) {
/*  992 */       return Short2BooleanOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(short k) {
/*  996 */       int oldSize = Short2BooleanOpenCustomHashMap.this.size;
/*  997 */       Short2BooleanOpenCustomHashMap.this.remove(k);
/*  998 */       return (Short2BooleanOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1002 */       Short2BooleanOpenCustomHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ShortSet keySet() {
/* 1007 */     if (this.keys == null)
/* 1008 */       this.keys = new KeySet(); 
/* 1009 */     return this.keys;
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
/* 1026 */       return Short2BooleanOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public BooleanCollection values() {
/* 1031 */     if (this.values == null)
/* 1032 */       this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */         {
/*      */           public BooleanIterator iterator() {
/* 1035 */             return new Short2BooleanOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1039 */             return Short2BooleanOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(boolean v) {
/* 1043 */             return Short2BooleanOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1047 */             Short2BooleanOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(BooleanConsumer consumer)
/*      */           {
/* 1052 */             if (Short2BooleanOpenCustomHashMap.this.containsNullKey)
/* 1053 */               consumer.accept(Short2BooleanOpenCustomHashMap.this.value[Short2BooleanOpenCustomHashMap.this.n]); 
/* 1054 */             for (int pos = Short2BooleanOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1055 */               if (Short2BooleanOpenCustomHashMap.this.key[pos] != 0)
/* 1056 */                 consumer.accept(Short2BooleanOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1059 */     return this.values;
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
/* 1076 */     return trim(this.size);
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
/* 1100 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1101 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1102 */       return true; 
/*      */     try {
/* 1104 */       rehash(l);
/* 1105 */     } catch (OutOfMemoryError cantDoIt) {
/* 1106 */       return false;
/*      */     } 
/* 1108 */     return true;
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
/* 1124 */     short[] key = this.key;
/* 1125 */     boolean[] value = this.value;
/* 1126 */     int mask = newN - 1;
/* 1127 */     short[] newKey = new short[newN + 1];
/* 1128 */     boolean[] newValue = new boolean[newN + 1];
/* 1129 */     int i = this.n;
/* 1130 */     for (int j = realSize(); j-- != 0; ) {
/* 1131 */       while (key[--i] == 0); int pos;
/* 1132 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != 0)
/*      */       {
/* 1134 */         while (newKey[pos = pos + 1 & mask] != 0); } 
/* 1135 */       newKey[pos] = key[i];
/* 1136 */       newValue[pos] = value[i];
/*      */     } 
/* 1138 */     newValue[newN] = value[this.n];
/* 1139 */     this.n = newN;
/* 1140 */     this.mask = mask;
/* 1141 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1142 */     this.key = newKey;
/* 1143 */     this.value = newValue;
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
/*      */   public Short2BooleanOpenCustomHashMap clone() {
/*      */     Short2BooleanOpenCustomHashMap c;
/*      */     try {
/* 1160 */       c = (Short2BooleanOpenCustomHashMap)super.clone();
/* 1161 */     } catch (CloneNotSupportedException cantHappen) {
/* 1162 */       throw new InternalError();
/*      */     } 
/* 1164 */     c.keys = null;
/* 1165 */     c.values = null;
/* 1166 */     c.entries = null;
/* 1167 */     c.containsNullKey = this.containsNullKey;
/* 1168 */     c.key = (short[])this.key.clone();
/* 1169 */     c.value = (boolean[])this.value.clone();
/* 1170 */     c.strategy = this.strategy;
/* 1171 */     return c;
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
/* 1184 */     int h = 0;
/* 1185 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1186 */       while (this.key[i] == 0)
/* 1187 */         i++; 
/* 1188 */       t = this.strategy.hashCode(this.key[i]);
/* 1189 */       t ^= this.value[i] ? 1231 : 1237;
/* 1190 */       h += t;
/* 1191 */       i++;
/*      */     } 
/*      */     
/* 1194 */     if (this.containsNullKey)
/* 1195 */       h += this.value[this.n] ? 1231 : 1237; 
/* 1196 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1199 */     short[] key = this.key;
/* 1200 */     boolean[] value = this.value;
/* 1201 */     MapIterator i = new MapIterator();
/* 1202 */     s.defaultWriteObject();
/* 1203 */     for (int j = this.size; j-- != 0; ) {
/* 1204 */       int e = i.nextEntry();
/* 1205 */       s.writeShort(key[e]);
/* 1206 */       s.writeBoolean(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1211 */     s.defaultReadObject();
/* 1212 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1213 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1214 */     this.mask = this.n - 1;
/* 1215 */     short[] key = this.key = new short[this.n + 1];
/* 1216 */     boolean[] value = this.value = new boolean[this.n + 1];
/*      */ 
/*      */     
/* 1219 */     for (int i = this.size; i-- != 0; ) {
/* 1220 */       int pos; short k = s.readShort();
/* 1221 */       boolean v = s.readBoolean();
/* 1222 */       if (this.strategy.equals(k, (short)0)) {
/* 1223 */         pos = this.n;
/* 1224 */         this.containsNullKey = true;
/*      */       } else {
/* 1226 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1227 */         while (key[pos] != 0)
/* 1228 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1230 */       key[pos] = k;
/* 1231 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\Short2BooleanOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */