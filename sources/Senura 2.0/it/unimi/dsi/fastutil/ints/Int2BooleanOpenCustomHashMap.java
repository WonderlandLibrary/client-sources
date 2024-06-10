/*      */ package it.unimi.dsi.fastutil.ints;
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
/*      */ public class Int2BooleanOpenCustomHashMap
/*      */   extends AbstractInt2BooleanMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient int[] key;
/*      */   protected transient boolean[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected IntHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Int2BooleanMap.FastEntrySet entries;
/*      */   protected transient IntSet keys;
/*      */   protected transient BooleanCollection values;
/*      */   
/*      */   public Int2BooleanOpenCustomHashMap(int expected, float f, IntHash.Strategy strategy) {
/*  108 */     this.strategy = strategy;
/*  109 */     if (f <= 0.0F || f > 1.0F)
/*  110 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  111 */     if (expected < 0)
/*  112 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  113 */     this.f = f;
/*  114 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  115 */     this.mask = this.n - 1;
/*  116 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  117 */     this.key = new int[this.n + 1];
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
/*      */   public Int2BooleanOpenCustomHashMap(int expected, IntHash.Strategy strategy) {
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
/*      */   public Int2BooleanOpenCustomHashMap(IntHash.Strategy strategy) {
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
/*      */   public Int2BooleanOpenCustomHashMap(Map<? extends Integer, ? extends Boolean> m, float f, IntHash.Strategy strategy) {
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
/*      */   public Int2BooleanOpenCustomHashMap(Map<? extends Integer, ? extends Boolean> m, IntHash.Strategy strategy) {
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
/*      */   public Int2BooleanOpenCustomHashMap(Int2BooleanMap m, float f, IntHash.Strategy strategy) {
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
/*      */   public Int2BooleanOpenCustomHashMap(Int2BooleanMap m, IntHash.Strategy strategy) {
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
/*      */   public Int2BooleanOpenCustomHashMap(int[] k, boolean[] v, float f, IntHash.Strategy strategy) {
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
/*      */   public Int2BooleanOpenCustomHashMap(int[] k, boolean[] v, IntHash.Strategy strategy) {
/*  237 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntHash.Strategy strategy() {
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
/*      */   public void putAll(Map<? extends Integer, ? extends Boolean> m) {
/*  279 */     if (this.f <= 0.5D) {
/*  280 */       ensureCapacity(m.size());
/*      */     } else {
/*  282 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  284 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(int k) {
/*  288 */     if (this.strategy.equals(k, 0)) {
/*  289 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  291 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
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
/*      */   private void insert(int pos, int k, boolean v) {
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
/*      */   public boolean put(int k, boolean v) {
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
/*  338 */     int[] key = this.key; while (true) {
/*      */       int curr, last;
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
/*      */   public boolean remove(int k) {
/*  358 */     if (this.strategy.equals(k, 0)) {
/*  359 */       if (this.containsNullKey)
/*  360 */         return removeNullEntry(); 
/*  361 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  364 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
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
/*      */   public boolean get(int k) {
/*  381 */     if (this.strategy.equals(k, 0)) {
/*  382 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  384 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
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
/*      */   public boolean containsKey(int k) {
/*  402 */     if (this.strategy.equals(k, 0)) {
/*  403 */       return this.containsNullKey;
/*      */     }
/*  405 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
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
/*  423 */     int[] key = this.key;
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
/*      */   public boolean getOrDefault(int k, boolean defaultValue) {
/*  435 */     if (this.strategy.equals(k, 0)) {
/*  436 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  438 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
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
/*      */   public boolean putIfAbsent(int k, boolean v) {
/*  456 */     int pos = find(k);
/*  457 */     if (pos >= 0)
/*  458 */       return this.value[pos]; 
/*  459 */     insert(-pos - 1, k, v);
/*  460 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(int k, boolean v) {
/*  466 */     if (this.strategy.equals(k, 0)) {
/*  467 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  468 */         removeNullEntry();
/*  469 */         return true;
/*      */       } 
/*  471 */       return false;
/*      */     } 
/*      */     
/*  474 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
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
/*      */   public boolean replace(int k, boolean oldValue, boolean v) {
/*  495 */     int pos = find(k);
/*  496 */     if (pos < 0 || oldValue != this.value[pos])
/*  497 */       return false; 
/*  498 */     this.value[pos] = v;
/*  499 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(int k, boolean v) {
/*  504 */     int pos = find(k);
/*  505 */     if (pos < 0)
/*  506 */       return this.defRetValue; 
/*  507 */     boolean oldValue = this.value[pos];
/*  508 */     this.value[pos] = v;
/*  509 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean computeIfAbsent(int k, IntPredicate mappingFunction) {
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
/*      */   public boolean computeIfAbsentNullable(int k, IntFunction<? extends Boolean> mappingFunction) {
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
/*      */   public boolean computeIfPresent(int k, BiFunction<? super Integer, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  541 */     Objects.requireNonNull(remappingFunction);
/*  542 */     int pos = find(k);
/*  543 */     if (pos < 0)
/*  544 */       return this.defRetValue; 
/*  545 */     Boolean newValue = remappingFunction.apply(Integer.valueOf(k), Boolean.valueOf(this.value[pos]));
/*  546 */     if (newValue == null) {
/*  547 */       if (this.strategy.equals(k, 0)) {
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
/*      */   public boolean compute(int k, BiFunction<? super Integer, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  559 */     Objects.requireNonNull(remappingFunction);
/*  560 */     int pos = find(k);
/*  561 */     Boolean newValue = remappingFunction.apply(Integer.valueOf(k), 
/*  562 */         (pos >= 0) ? Boolean.valueOf(this.value[pos]) : null);
/*  563 */     if (newValue == null) {
/*  564 */       if (pos >= 0)
/*  565 */         if (this.strategy.equals(k, 0)) {
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
/*      */   public boolean merge(int k, boolean v, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  583 */     Objects.requireNonNull(remappingFunction);
/*  584 */     int pos = find(k);
/*  585 */     if (pos < 0) {
/*  586 */       insert(-pos - 1, k, v);
/*  587 */       return v;
/*      */     } 
/*  589 */     Boolean newValue = remappingFunction.apply(Boolean.valueOf(this.value[pos]), Boolean.valueOf(v));
/*  590 */     if (newValue == null) {
/*  591 */       if (this.strategy.equals(k, 0)) {
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
/*  612 */     Arrays.fill(this.key, 0);
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
/*      */     implements Int2BooleanMap.Entry, Map.Entry<Integer, Boolean>
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
/*      */     public int getIntKey() {
/*  638 */       return Int2BooleanOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public boolean getBooleanValue() {
/*  642 */       return Int2BooleanOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public boolean setValue(boolean v) {
/*  646 */       boolean oldValue = Int2BooleanOpenCustomHashMap.this.value[this.index];
/*  647 */       Int2BooleanOpenCustomHashMap.this.value[this.index] = v;
/*  648 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer getKey() {
/*  658 */       return Integer.valueOf(Int2BooleanOpenCustomHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean getValue() {
/*  668 */       return Boolean.valueOf(Int2BooleanOpenCustomHashMap.this.value[this.index]);
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
/*  685 */       Map.Entry<Integer, Boolean> e = (Map.Entry<Integer, Boolean>)o;
/*  686 */       return (Int2BooleanOpenCustomHashMap.this.strategy.equals(Int2BooleanOpenCustomHashMap.this.key[this.index], ((Integer)e.getKey()).intValue()) && Int2BooleanOpenCustomHashMap.this.value[this.index] == ((Boolean)e
/*  687 */         .getValue()).booleanValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  691 */       return Int2BooleanOpenCustomHashMap.this.strategy.hashCode(Int2BooleanOpenCustomHashMap.this.key[this.index]) ^ (Int2BooleanOpenCustomHashMap.this.value[this.index] ? 1231 : 1237);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  695 */       return Int2BooleanOpenCustomHashMap.this.key[this.index] + "=>" + Int2BooleanOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  705 */     int pos = Int2BooleanOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  712 */     int last = -1;
/*      */     
/*  714 */     int c = Int2BooleanOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  718 */     boolean mustReturnNullKey = Int2BooleanOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     IntArrayList wrapped;
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
/*  733 */         return this.last = Int2BooleanOpenCustomHashMap.this.n;
/*      */       } 
/*  735 */       int[] key = Int2BooleanOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  737 */         if (--this.pos < 0) {
/*      */           
/*  739 */           this.last = Integer.MIN_VALUE;
/*  740 */           int k = this.wrapped.getInt(-this.pos - 1);
/*  741 */           int p = HashCommon.mix(Int2BooleanOpenCustomHashMap.this.strategy.hashCode(k)) & Int2BooleanOpenCustomHashMap.this.mask;
/*  742 */           while (!Int2BooleanOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  743 */             p = p + 1 & Int2BooleanOpenCustomHashMap.this.mask; 
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
/*  761 */       int[] key = Int2BooleanOpenCustomHashMap.this.key; while (true) {
/*      */         int curr, last;
/*  763 */         pos = (last = pos) + 1 & Int2BooleanOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  765 */           if ((curr = key[pos]) == 0) {
/*  766 */             key[last] = 0;
/*      */             return;
/*      */           } 
/*  769 */           int slot = HashCommon.mix(Int2BooleanOpenCustomHashMap.this.strategy.hashCode(curr)) & Int2BooleanOpenCustomHashMap.this.mask;
/*  770 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  772 */           pos = pos + 1 & Int2BooleanOpenCustomHashMap.this.mask;
/*      */         } 
/*  774 */         if (pos < last) {
/*  775 */           if (this.wrapped == null)
/*  776 */             this.wrapped = new IntArrayList(2); 
/*  777 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  779 */         key[last] = curr;
/*  780 */         Int2BooleanOpenCustomHashMap.this.value[last] = Int2BooleanOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  784 */       if (this.last == -1)
/*  785 */         throw new IllegalStateException(); 
/*  786 */       if (this.last == Int2BooleanOpenCustomHashMap.this.n) {
/*  787 */         Int2BooleanOpenCustomHashMap.this.containsNullKey = false;
/*  788 */       } else if (this.pos >= 0) {
/*  789 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  792 */         Int2BooleanOpenCustomHashMap.this.remove(this.wrapped.getInt(-this.pos - 1));
/*  793 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  796 */       Int2BooleanOpenCustomHashMap.this.size--;
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
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Int2BooleanMap.Entry> { private Int2BooleanOpenCustomHashMap.MapEntry entry;
/*      */     
/*      */     public Int2BooleanOpenCustomHashMap.MapEntry next() {
/*  812 */       return this.entry = new Int2BooleanOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  816 */       super.remove();
/*  817 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Int2BooleanMap.Entry> { private FastEntryIterator() {
/*  821 */       this.entry = new Int2BooleanOpenCustomHashMap.MapEntry();
/*      */     } private final Int2BooleanOpenCustomHashMap.MapEntry entry;
/*      */     public Int2BooleanOpenCustomHashMap.MapEntry next() {
/*  824 */       this.entry.index = nextEntry();
/*  825 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Int2BooleanMap.Entry> implements Int2BooleanMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Int2BooleanMap.Entry> iterator() {
/*  831 */       return new Int2BooleanOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Int2BooleanMap.Entry> fastIterator() {
/*  835 */       return new Int2BooleanOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  840 */       if (!(o instanceof Map.Entry))
/*  841 */         return false; 
/*  842 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  843 */       if (e.getKey() == null || !(e.getKey() instanceof Integer))
/*  844 */         return false; 
/*  845 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/*  846 */         return false; 
/*  847 */       int k = ((Integer)e.getKey()).intValue();
/*  848 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/*  849 */       if (Int2BooleanOpenCustomHashMap.this.strategy.equals(k, 0)) {
/*  850 */         return (Int2BooleanOpenCustomHashMap.this.containsNullKey && Int2BooleanOpenCustomHashMap.this.value[Int2BooleanOpenCustomHashMap.this.n] == v);
/*      */       }
/*  852 */       int[] key = Int2BooleanOpenCustomHashMap.this.key;
/*      */       
/*      */       int curr, pos;
/*  855 */       if ((curr = key[pos = HashCommon.mix(Int2BooleanOpenCustomHashMap.this.strategy.hashCode(k)) & Int2BooleanOpenCustomHashMap.this.mask]) == 0)
/*  856 */         return false; 
/*  857 */       if (Int2BooleanOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  858 */         return (Int2BooleanOpenCustomHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  861 */         if ((curr = key[pos = pos + 1 & Int2BooleanOpenCustomHashMap.this.mask]) == 0)
/*  862 */           return false; 
/*  863 */         if (Int2BooleanOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  864 */           return (Int2BooleanOpenCustomHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  870 */       if (!(o instanceof Map.Entry))
/*  871 */         return false; 
/*  872 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  873 */       if (e.getKey() == null || !(e.getKey() instanceof Integer))
/*  874 */         return false; 
/*  875 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/*  876 */         return false; 
/*  877 */       int k = ((Integer)e.getKey()).intValue();
/*  878 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/*  879 */       if (Int2BooleanOpenCustomHashMap.this.strategy.equals(k, 0)) {
/*  880 */         if (Int2BooleanOpenCustomHashMap.this.containsNullKey && Int2BooleanOpenCustomHashMap.this.value[Int2BooleanOpenCustomHashMap.this.n] == v) {
/*  881 */           Int2BooleanOpenCustomHashMap.this.removeNullEntry();
/*  882 */           return true;
/*      */         } 
/*  884 */         return false;
/*      */       } 
/*      */       
/*  887 */       int[] key = Int2BooleanOpenCustomHashMap.this.key;
/*      */       
/*      */       int curr, pos;
/*  890 */       if ((curr = key[pos = HashCommon.mix(Int2BooleanOpenCustomHashMap.this.strategy.hashCode(k)) & Int2BooleanOpenCustomHashMap.this.mask]) == 0)
/*  891 */         return false; 
/*  892 */       if (Int2BooleanOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  893 */         if (Int2BooleanOpenCustomHashMap.this.value[pos] == v) {
/*  894 */           Int2BooleanOpenCustomHashMap.this.removeEntry(pos);
/*  895 */           return true;
/*      */         } 
/*  897 */         return false;
/*      */       } 
/*      */       while (true) {
/*  900 */         if ((curr = key[pos = pos + 1 & Int2BooleanOpenCustomHashMap.this.mask]) == 0)
/*  901 */           return false; 
/*  902 */         if (Int2BooleanOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  903 */           Int2BooleanOpenCustomHashMap.this.value[pos] == v) {
/*  904 */           Int2BooleanOpenCustomHashMap.this.removeEntry(pos);
/*  905 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  912 */       return Int2BooleanOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  916 */       Int2BooleanOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Int2BooleanMap.Entry> consumer) {
/*  921 */       if (Int2BooleanOpenCustomHashMap.this.containsNullKey)
/*  922 */         consumer.accept(new AbstractInt2BooleanMap.BasicEntry(Int2BooleanOpenCustomHashMap.this.key[Int2BooleanOpenCustomHashMap.this.n], Int2BooleanOpenCustomHashMap.this.value[Int2BooleanOpenCustomHashMap.this.n])); 
/*  923 */       for (int pos = Int2BooleanOpenCustomHashMap.this.n; pos-- != 0;) {
/*  924 */         if (Int2BooleanOpenCustomHashMap.this.key[pos] != 0)
/*  925 */           consumer.accept(new AbstractInt2BooleanMap.BasicEntry(Int2BooleanOpenCustomHashMap.this.key[pos], Int2BooleanOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Int2BooleanMap.Entry> consumer) {
/*  930 */       AbstractInt2BooleanMap.BasicEntry entry = new AbstractInt2BooleanMap.BasicEntry();
/*  931 */       if (Int2BooleanOpenCustomHashMap.this.containsNullKey) {
/*  932 */         entry.key = Int2BooleanOpenCustomHashMap.this.key[Int2BooleanOpenCustomHashMap.this.n];
/*  933 */         entry.value = Int2BooleanOpenCustomHashMap.this.value[Int2BooleanOpenCustomHashMap.this.n];
/*  934 */         consumer.accept(entry);
/*      */       } 
/*  936 */       for (int pos = Int2BooleanOpenCustomHashMap.this.n; pos-- != 0;) {
/*  937 */         if (Int2BooleanOpenCustomHashMap.this.key[pos] != 0) {
/*  938 */           entry.key = Int2BooleanOpenCustomHashMap.this.key[pos];
/*  939 */           entry.value = Int2BooleanOpenCustomHashMap.this.value[pos];
/*  940 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Int2BooleanMap.FastEntrySet int2BooleanEntrySet() {
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
/*      */     implements IntIterator
/*      */   {
/*      */     public int nextInt() {
/*  965 */       return Int2BooleanOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractIntSet { private KeySet() {}
/*      */     
/*      */     public IntIterator iterator() {
/*  971 */       return new Int2BooleanOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer consumer) {
/*  976 */       if (Int2BooleanOpenCustomHashMap.this.containsNullKey)
/*  977 */         consumer.accept(Int2BooleanOpenCustomHashMap.this.key[Int2BooleanOpenCustomHashMap.this.n]); 
/*  978 */       for (int pos = Int2BooleanOpenCustomHashMap.this.n; pos-- != 0; ) {
/*  979 */         int k = Int2BooleanOpenCustomHashMap.this.key[pos];
/*  980 */         if (k != 0)
/*  981 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  986 */       return Int2BooleanOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(int k) {
/*  990 */       return Int2BooleanOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(int k) {
/*  994 */       int oldSize = Int2BooleanOpenCustomHashMap.this.size;
/*  995 */       Int2BooleanOpenCustomHashMap.this.remove(k);
/*  996 */       return (Int2BooleanOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1000 */       Int2BooleanOpenCustomHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public IntSet keySet() {
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
/* 1024 */       return Int2BooleanOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public BooleanCollection values() {
/* 1029 */     if (this.values == null)
/* 1030 */       this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */         {
/*      */           public BooleanIterator iterator() {
/* 1033 */             return new Int2BooleanOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1037 */             return Int2BooleanOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(boolean v) {
/* 1041 */             return Int2BooleanOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1045 */             Int2BooleanOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(BooleanConsumer consumer)
/*      */           {
/* 1050 */             if (Int2BooleanOpenCustomHashMap.this.containsNullKey)
/* 1051 */               consumer.accept(Int2BooleanOpenCustomHashMap.this.value[Int2BooleanOpenCustomHashMap.this.n]); 
/* 1052 */             for (int pos = Int2BooleanOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1053 */               if (Int2BooleanOpenCustomHashMap.this.key[pos] != 0)
/* 1054 */                 consumer.accept(Int2BooleanOpenCustomHashMap.this.value[pos]); 
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
/* 1122 */     int[] key = this.key;
/* 1123 */     boolean[] value = this.value;
/* 1124 */     int mask = newN - 1;
/* 1125 */     int[] newKey = new int[newN + 1];
/* 1126 */     boolean[] newValue = new boolean[newN + 1];
/* 1127 */     int i = this.n;
/* 1128 */     for (int j = realSize(); j-- != 0; ) {
/* 1129 */       while (key[--i] == 0); int pos;
/* 1130 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != 0)
/* 1131 */         while (newKey[pos = pos + 1 & mask] != 0); 
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
/*      */   public Int2BooleanOpenCustomHashMap clone() {
/*      */     Int2BooleanOpenCustomHashMap c;
/*      */     try {
/* 1157 */       c = (Int2BooleanOpenCustomHashMap)super.clone();
/* 1158 */     } catch (CloneNotSupportedException cantHappen) {
/* 1159 */       throw new InternalError();
/*      */     } 
/* 1161 */     c.keys = null;
/* 1162 */     c.values = null;
/* 1163 */     c.entries = null;
/* 1164 */     c.containsNullKey = this.containsNullKey;
/* 1165 */     c.key = (int[])this.key.clone();
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
/* 1183 */       while (this.key[i] == 0)
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
/* 1196 */     int[] key = this.key;
/* 1197 */     boolean[] value = this.value;
/* 1198 */     MapIterator i = new MapIterator();
/* 1199 */     s.defaultWriteObject();
/* 1200 */     for (int j = this.size; j-- != 0; ) {
/* 1201 */       int e = i.nextEntry();
/* 1202 */       s.writeInt(key[e]);
/* 1203 */       s.writeBoolean(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1208 */     s.defaultReadObject();
/* 1209 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1210 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1211 */     this.mask = this.n - 1;
/* 1212 */     int[] key = this.key = new int[this.n + 1];
/* 1213 */     boolean[] value = this.value = new boolean[this.n + 1];
/*      */ 
/*      */     
/* 1216 */     for (int i = this.size; i-- != 0; ) {
/* 1217 */       int pos, k = s.readInt();
/* 1218 */       boolean v = s.readBoolean();
/* 1219 */       if (this.strategy.equals(k, 0)) {
/* 1220 */         pos = this.n;
/* 1221 */         this.containsNullKey = true;
/*      */       } else {
/* 1223 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1224 */         while (key[pos] != 0)
/* 1225 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1227 */       key[pos] = k;
/* 1228 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\Int2BooleanOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */