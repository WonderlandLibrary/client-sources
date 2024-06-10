/*      */ package it.unimi.dsi.fastutil.ints;
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
/*      */ public class Int2ShortOpenCustomHashMap
/*      */   extends AbstractInt2ShortMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient int[] key;
/*      */   protected transient short[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected IntHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Int2ShortMap.FastEntrySet entries;
/*      */   protected transient IntSet keys;
/*      */   protected transient ShortCollection values;
/*      */   
/*      */   public Int2ShortOpenCustomHashMap(int expected, float f, IntHash.Strategy strategy) {
/*  103 */     this.strategy = strategy;
/*  104 */     if (f <= 0.0F || f > 1.0F)
/*  105 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  106 */     if (expected < 0)
/*  107 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  108 */     this.f = f;
/*  109 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  110 */     this.mask = this.n - 1;
/*  111 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  112 */     this.key = new int[this.n + 1];
/*  113 */     this.value = new short[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2ShortOpenCustomHashMap(int expected, IntHash.Strategy strategy) {
/*  124 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2ShortOpenCustomHashMap(IntHash.Strategy strategy) {
/*  135 */     this(16, 0.75F, strategy);
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
/*      */   public Int2ShortOpenCustomHashMap(Map<? extends Integer, ? extends Short> m, float f, IntHash.Strategy strategy) {
/*  149 */     this(m.size(), f, strategy);
/*  150 */     putAll(m);
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
/*      */   public Int2ShortOpenCustomHashMap(Map<? extends Integer, ? extends Short> m, IntHash.Strategy strategy) {
/*  163 */     this(m, 0.75F, strategy);
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
/*      */   public Int2ShortOpenCustomHashMap(Int2ShortMap m, float f, IntHash.Strategy strategy) {
/*  177 */     this(m.size(), f, strategy);
/*  178 */     putAll(m);
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
/*      */   public Int2ShortOpenCustomHashMap(Int2ShortMap m, IntHash.Strategy strategy) {
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
/*      */   public Int2ShortOpenCustomHashMap(int[] k, short[] v, float f, IntHash.Strategy strategy) {
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
/*      */   
/*      */   public Int2ShortOpenCustomHashMap(int[] k, short[] v, IntHash.Strategy strategy) {
/*  231 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntHash.Strategy strategy() {
/*  239 */     return this.strategy;
/*      */   }
/*      */   private int realSize() {
/*  242 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  245 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  246 */     if (needed > this.n)
/*  247 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  250 */     int needed = (int)Math.min(1073741824L, 
/*  251 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  252 */     if (needed > this.n)
/*  253 */       rehash(needed); 
/*      */   }
/*      */   private short removeEntry(int pos) {
/*  256 */     short oldValue = this.value[pos];
/*  257 */     this.size--;
/*  258 */     shiftKeys(pos);
/*  259 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  260 */       rehash(this.n / 2); 
/*  261 */     return oldValue;
/*      */   }
/*      */   private short removeNullEntry() {
/*  264 */     this.containsNullKey = false;
/*  265 */     short oldValue = this.value[this.n];
/*  266 */     this.size--;
/*  267 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  268 */       rehash(this.n / 2); 
/*  269 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Integer, ? extends Short> m) {
/*  273 */     if (this.f <= 0.5D) {
/*  274 */       ensureCapacity(m.size());
/*      */     } else {
/*  276 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  278 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(int k) {
/*  282 */     if (this.strategy.equals(k, 0)) {
/*  283 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  285 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  288 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  289 */       return -(pos + 1); 
/*  290 */     if (this.strategy.equals(k, curr)) {
/*  291 */       return pos;
/*      */     }
/*      */     while (true) {
/*  294 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  295 */         return -(pos + 1); 
/*  296 */       if (this.strategy.equals(k, curr))
/*  297 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, int k, short v) {
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
/*      */   public short put(int k, short v) {
/*  312 */     int pos = find(k);
/*  313 */     if (pos < 0) {
/*  314 */       insert(-pos - 1, k, v);
/*  315 */       return this.defRetValue;
/*      */     } 
/*  317 */     short oldValue = this.value[pos];
/*  318 */     this.value[pos] = v;
/*  319 */     return oldValue;
/*      */   }
/*      */   private short addToValue(int pos, short incr) {
/*  322 */     short oldValue = this.value[pos];
/*  323 */     this.value[pos] = (short)(oldValue + incr);
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
/*      */   public short addTo(int k, short incr) {
/*      */     int pos;
/*  344 */     if (this.strategy.equals(k, 0)) {
/*  345 */       if (this.containsNullKey)
/*  346 */         return addToValue(this.n, incr); 
/*  347 */       pos = this.n;
/*  348 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  351 */       int[] key = this.key;
/*      */       int curr;
/*  353 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != 0) {
/*  354 */         if (this.strategy.equals(curr, k))
/*  355 */           return addToValue(pos, incr); 
/*  356 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
/*  357 */           if (this.strategy.equals(curr, k))
/*  358 */             return addToValue(pos, incr); 
/*      */         } 
/*      */       } 
/*  361 */     }  this.key[pos] = k;
/*  362 */     this.value[pos] = (short)(this.defRetValue + incr);
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
/*  380 */     int[] key = this.key; while (true) {
/*      */       int curr, last;
/*  382 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  384 */         if ((curr = key[pos]) == 0) {
/*  385 */           key[last] = 0;
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
/*      */   public short remove(int k) {
/*  400 */     if (this.strategy.equals(k, 0)) {
/*  401 */       if (this.containsNullKey)
/*  402 */         return removeNullEntry(); 
/*  403 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  406 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  409 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  410 */       return this.defRetValue; 
/*  411 */     if (this.strategy.equals(k, curr))
/*  412 */       return removeEntry(pos); 
/*      */     while (true) {
/*  414 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  415 */         return this.defRetValue; 
/*  416 */       if (this.strategy.equals(k, curr)) {
/*  417 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public short get(int k) {
/*  423 */     if (this.strategy.equals(k, 0)) {
/*  424 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  426 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  429 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  430 */       return this.defRetValue; 
/*  431 */     if (this.strategy.equals(k, curr)) {
/*  432 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  435 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  436 */         return this.defRetValue; 
/*  437 */       if (this.strategy.equals(k, curr)) {
/*  438 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(int k) {
/*  444 */     if (this.strategy.equals(k, 0)) {
/*  445 */       return this.containsNullKey;
/*      */     }
/*  447 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  450 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  451 */       return false; 
/*  452 */     if (this.strategy.equals(k, curr)) {
/*  453 */       return true;
/*      */     }
/*      */     while (true) {
/*  456 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  457 */         return false; 
/*  458 */       if (this.strategy.equals(k, curr))
/*  459 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(short v) {
/*  464 */     short[] value = this.value;
/*  465 */     int[] key = this.key;
/*  466 */     if (this.containsNullKey && value[this.n] == v)
/*  467 */       return true; 
/*  468 */     for (int i = this.n; i-- != 0;) {
/*  469 */       if (key[i] != 0 && value[i] == v)
/*  470 */         return true; 
/*  471 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short getOrDefault(int k, short defaultValue) {
/*  477 */     if (this.strategy.equals(k, 0)) {
/*  478 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  480 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  483 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  484 */       return defaultValue; 
/*  485 */     if (this.strategy.equals(k, curr)) {
/*  486 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  489 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  490 */         return defaultValue; 
/*  491 */       if (this.strategy.equals(k, curr)) {
/*  492 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public short putIfAbsent(int k, short v) {
/*  498 */     int pos = find(k);
/*  499 */     if (pos >= 0)
/*  500 */       return this.value[pos]; 
/*  501 */     insert(-pos - 1, k, v);
/*  502 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(int k, short v) {
/*  508 */     if (this.strategy.equals(k, 0)) {
/*  509 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  510 */         removeNullEntry();
/*  511 */         return true;
/*      */       } 
/*  513 */       return false;
/*      */     } 
/*      */     
/*  516 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  519 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  520 */       return false; 
/*  521 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  522 */       removeEntry(pos);
/*  523 */       return true;
/*      */     } 
/*      */     while (true) {
/*  526 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  527 */         return false; 
/*  528 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  529 */         removeEntry(pos);
/*  530 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(int k, short oldValue, short v) {
/*  537 */     int pos = find(k);
/*  538 */     if (pos < 0 || oldValue != this.value[pos])
/*  539 */       return false; 
/*  540 */     this.value[pos] = v;
/*  541 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public short replace(int k, short v) {
/*  546 */     int pos = find(k);
/*  547 */     if (pos < 0)
/*  548 */       return this.defRetValue; 
/*  549 */     short oldValue = this.value[pos];
/*  550 */     this.value[pos] = v;
/*  551 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public short computeIfAbsent(int k, IntUnaryOperator mappingFunction) {
/*  556 */     Objects.requireNonNull(mappingFunction);
/*  557 */     int pos = find(k);
/*  558 */     if (pos >= 0)
/*  559 */       return this.value[pos]; 
/*  560 */     short newValue = SafeMath.safeIntToShort(mappingFunction.applyAsInt(k));
/*  561 */     insert(-pos - 1, k, newValue);
/*  562 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short computeIfAbsentNullable(int k, IntFunction<? extends Short> mappingFunction) {
/*  568 */     Objects.requireNonNull(mappingFunction);
/*  569 */     int pos = find(k);
/*  570 */     if (pos >= 0)
/*  571 */       return this.value[pos]; 
/*  572 */     Short newValue = mappingFunction.apply(k);
/*  573 */     if (newValue == null)
/*  574 */       return this.defRetValue; 
/*  575 */     short v = newValue.shortValue();
/*  576 */     insert(-pos - 1, k, v);
/*  577 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short computeIfPresent(int k, BiFunction<? super Integer, ? super Short, ? extends Short> remappingFunction) {
/*  583 */     Objects.requireNonNull(remappingFunction);
/*  584 */     int pos = find(k);
/*  585 */     if (pos < 0)
/*  586 */       return this.defRetValue; 
/*  587 */     Short newValue = remappingFunction.apply(Integer.valueOf(k), Short.valueOf(this.value[pos]));
/*  588 */     if (newValue == null) {
/*  589 */       if (this.strategy.equals(k, 0)) {
/*  590 */         removeNullEntry();
/*      */       } else {
/*  592 */         removeEntry(pos);
/*  593 */       }  return this.defRetValue;
/*      */     } 
/*  595 */     this.value[pos] = newValue.shortValue(); return newValue.shortValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short compute(int k, BiFunction<? super Integer, ? super Short, ? extends Short> remappingFunction) {
/*  601 */     Objects.requireNonNull(remappingFunction);
/*  602 */     int pos = find(k);
/*  603 */     Short newValue = remappingFunction.apply(Integer.valueOf(k), (pos >= 0) ? Short.valueOf(this.value[pos]) : null);
/*  604 */     if (newValue == null) {
/*  605 */       if (pos >= 0)
/*  606 */         if (this.strategy.equals(k, 0)) {
/*  607 */           removeNullEntry();
/*      */         } else {
/*  609 */           removeEntry(pos);
/*      */         }  
/*  611 */       return this.defRetValue;
/*      */     } 
/*  613 */     short newVal = newValue.shortValue();
/*  614 */     if (pos < 0) {
/*  615 */       insert(-pos - 1, k, newVal);
/*  616 */       return newVal;
/*      */     } 
/*  618 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short merge(int k, short v, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
/*  624 */     Objects.requireNonNull(remappingFunction);
/*  625 */     int pos = find(k);
/*  626 */     if (pos < 0) {
/*  627 */       insert(-pos - 1, k, v);
/*  628 */       return v;
/*      */     } 
/*  630 */     Short newValue = remappingFunction.apply(Short.valueOf(this.value[pos]), Short.valueOf(v));
/*  631 */     if (newValue == null) {
/*  632 */       if (this.strategy.equals(k, 0)) {
/*  633 */         removeNullEntry();
/*      */       } else {
/*  635 */         removeEntry(pos);
/*  636 */       }  return this.defRetValue;
/*      */     } 
/*  638 */     this.value[pos] = newValue.shortValue(); return newValue.shortValue();
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
/*  649 */     if (this.size == 0)
/*      */       return; 
/*  651 */     this.size = 0;
/*  652 */     this.containsNullKey = false;
/*  653 */     Arrays.fill(this.key, 0);
/*      */   }
/*      */   
/*      */   public int size() {
/*  657 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  661 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Int2ShortMap.Entry, Map.Entry<Integer, Short>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  673 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public int getIntKey() {
/*  679 */       return Int2ShortOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public short getShortValue() {
/*  683 */       return Int2ShortOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public short setValue(short v) {
/*  687 */       short oldValue = Int2ShortOpenCustomHashMap.this.value[this.index];
/*  688 */       Int2ShortOpenCustomHashMap.this.value[this.index] = v;
/*  689 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer getKey() {
/*  699 */       return Integer.valueOf(Int2ShortOpenCustomHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Short getValue() {
/*  709 */       return Short.valueOf(Int2ShortOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Short setValue(Short v) {
/*  719 */       return Short.valueOf(setValue(v.shortValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  724 */       if (!(o instanceof Map.Entry))
/*  725 */         return false; 
/*  726 */       Map.Entry<Integer, Short> e = (Map.Entry<Integer, Short>)o;
/*  727 */       return (Int2ShortOpenCustomHashMap.this.strategy.equals(Int2ShortOpenCustomHashMap.this.key[this.index], ((Integer)e.getKey()).intValue()) && Int2ShortOpenCustomHashMap.this.value[this.index] == ((Short)e
/*  728 */         .getValue()).shortValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  732 */       return Int2ShortOpenCustomHashMap.this.strategy.hashCode(Int2ShortOpenCustomHashMap.this.key[this.index]) ^ Int2ShortOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/*  736 */       return Int2ShortOpenCustomHashMap.this.key[this.index] + "=>" + Int2ShortOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  746 */     int pos = Int2ShortOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  753 */     int last = -1;
/*      */     
/*  755 */     int c = Int2ShortOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  759 */     boolean mustReturnNullKey = Int2ShortOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     IntArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  766 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  769 */       if (!hasNext())
/*  770 */         throw new NoSuchElementException(); 
/*  771 */       this.c--;
/*  772 */       if (this.mustReturnNullKey) {
/*  773 */         this.mustReturnNullKey = false;
/*  774 */         return this.last = Int2ShortOpenCustomHashMap.this.n;
/*      */       } 
/*  776 */       int[] key = Int2ShortOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  778 */         if (--this.pos < 0) {
/*      */           
/*  780 */           this.last = Integer.MIN_VALUE;
/*  781 */           int k = this.wrapped.getInt(-this.pos - 1);
/*  782 */           int p = HashCommon.mix(Int2ShortOpenCustomHashMap.this.strategy.hashCode(k)) & Int2ShortOpenCustomHashMap.this.mask;
/*  783 */           while (!Int2ShortOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  784 */             p = p + 1 & Int2ShortOpenCustomHashMap.this.mask; 
/*  785 */           return p;
/*      */         } 
/*  787 */         if (key[this.pos] != 0) {
/*  788 */           return this.last = this.pos;
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
/*  802 */       int[] key = Int2ShortOpenCustomHashMap.this.key; while (true) {
/*      */         int curr, last;
/*  804 */         pos = (last = pos) + 1 & Int2ShortOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  806 */           if ((curr = key[pos]) == 0) {
/*  807 */             key[last] = 0;
/*      */             return;
/*      */           } 
/*  810 */           int slot = HashCommon.mix(Int2ShortOpenCustomHashMap.this.strategy.hashCode(curr)) & Int2ShortOpenCustomHashMap.this.mask;
/*  811 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  813 */           pos = pos + 1 & Int2ShortOpenCustomHashMap.this.mask;
/*      */         } 
/*  815 */         if (pos < last) {
/*  816 */           if (this.wrapped == null)
/*  817 */             this.wrapped = new IntArrayList(2); 
/*  818 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  820 */         key[last] = curr;
/*  821 */         Int2ShortOpenCustomHashMap.this.value[last] = Int2ShortOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  825 */       if (this.last == -1)
/*  826 */         throw new IllegalStateException(); 
/*  827 */       if (this.last == Int2ShortOpenCustomHashMap.this.n) {
/*  828 */         Int2ShortOpenCustomHashMap.this.containsNullKey = false;
/*  829 */       } else if (this.pos >= 0) {
/*  830 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  833 */         Int2ShortOpenCustomHashMap.this.remove(this.wrapped.getInt(-this.pos - 1));
/*  834 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  837 */       Int2ShortOpenCustomHashMap.this.size--;
/*  838 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  843 */       int i = n;
/*  844 */       while (i-- != 0 && hasNext())
/*  845 */         nextEntry(); 
/*  846 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Int2ShortMap.Entry> { private Int2ShortOpenCustomHashMap.MapEntry entry;
/*      */     
/*      */     public Int2ShortOpenCustomHashMap.MapEntry next() {
/*  853 */       return this.entry = new Int2ShortOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  857 */       super.remove();
/*  858 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Int2ShortMap.Entry> { private FastEntryIterator() {
/*  862 */       this.entry = new Int2ShortOpenCustomHashMap.MapEntry();
/*      */     } private final Int2ShortOpenCustomHashMap.MapEntry entry;
/*      */     public Int2ShortOpenCustomHashMap.MapEntry next() {
/*  865 */       this.entry.index = nextEntry();
/*  866 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Int2ShortMap.Entry> implements Int2ShortMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Int2ShortMap.Entry> iterator() {
/*  872 */       return new Int2ShortOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Int2ShortMap.Entry> fastIterator() {
/*  876 */       return new Int2ShortOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  881 */       if (!(o instanceof Map.Entry))
/*  882 */         return false; 
/*  883 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  884 */       if (e.getKey() == null || !(e.getKey() instanceof Integer))
/*  885 */         return false; 
/*  886 */       if (e.getValue() == null || !(e.getValue() instanceof Short))
/*  887 */         return false; 
/*  888 */       int k = ((Integer)e.getKey()).intValue();
/*  889 */       short v = ((Short)e.getValue()).shortValue();
/*  890 */       if (Int2ShortOpenCustomHashMap.this.strategy.equals(k, 0)) {
/*  891 */         return (Int2ShortOpenCustomHashMap.this.containsNullKey && Int2ShortOpenCustomHashMap.this.value[Int2ShortOpenCustomHashMap.this.n] == v);
/*      */       }
/*  893 */       int[] key = Int2ShortOpenCustomHashMap.this.key;
/*      */       
/*      */       int curr, pos;
/*  896 */       if ((curr = key[pos = HashCommon.mix(Int2ShortOpenCustomHashMap.this.strategy.hashCode(k)) & Int2ShortOpenCustomHashMap.this.mask]) == 0)
/*  897 */         return false; 
/*  898 */       if (Int2ShortOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  899 */         return (Int2ShortOpenCustomHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  902 */         if ((curr = key[pos = pos + 1 & Int2ShortOpenCustomHashMap.this.mask]) == 0)
/*  903 */           return false; 
/*  904 */         if (Int2ShortOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  905 */           return (Int2ShortOpenCustomHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  911 */       if (!(o instanceof Map.Entry))
/*  912 */         return false; 
/*  913 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  914 */       if (e.getKey() == null || !(e.getKey() instanceof Integer))
/*  915 */         return false; 
/*  916 */       if (e.getValue() == null || !(e.getValue() instanceof Short))
/*  917 */         return false; 
/*  918 */       int k = ((Integer)e.getKey()).intValue();
/*  919 */       short v = ((Short)e.getValue()).shortValue();
/*  920 */       if (Int2ShortOpenCustomHashMap.this.strategy.equals(k, 0)) {
/*  921 */         if (Int2ShortOpenCustomHashMap.this.containsNullKey && Int2ShortOpenCustomHashMap.this.value[Int2ShortOpenCustomHashMap.this.n] == v) {
/*  922 */           Int2ShortOpenCustomHashMap.this.removeNullEntry();
/*  923 */           return true;
/*      */         } 
/*  925 */         return false;
/*      */       } 
/*      */       
/*  928 */       int[] key = Int2ShortOpenCustomHashMap.this.key;
/*      */       
/*      */       int curr, pos;
/*  931 */       if ((curr = key[pos = HashCommon.mix(Int2ShortOpenCustomHashMap.this.strategy.hashCode(k)) & Int2ShortOpenCustomHashMap.this.mask]) == 0)
/*  932 */         return false; 
/*  933 */       if (Int2ShortOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  934 */         if (Int2ShortOpenCustomHashMap.this.value[pos] == v) {
/*  935 */           Int2ShortOpenCustomHashMap.this.removeEntry(pos);
/*  936 */           return true;
/*      */         } 
/*  938 */         return false;
/*      */       } 
/*      */       while (true) {
/*  941 */         if ((curr = key[pos = pos + 1 & Int2ShortOpenCustomHashMap.this.mask]) == 0)
/*  942 */           return false; 
/*  943 */         if (Int2ShortOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  944 */           Int2ShortOpenCustomHashMap.this.value[pos] == v) {
/*  945 */           Int2ShortOpenCustomHashMap.this.removeEntry(pos);
/*  946 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  953 */       return Int2ShortOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  957 */       Int2ShortOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Int2ShortMap.Entry> consumer) {
/*  962 */       if (Int2ShortOpenCustomHashMap.this.containsNullKey)
/*  963 */         consumer.accept(new AbstractInt2ShortMap.BasicEntry(Int2ShortOpenCustomHashMap.this.key[Int2ShortOpenCustomHashMap.this.n], Int2ShortOpenCustomHashMap.this.value[Int2ShortOpenCustomHashMap.this.n])); 
/*  964 */       for (int pos = Int2ShortOpenCustomHashMap.this.n; pos-- != 0;) {
/*  965 */         if (Int2ShortOpenCustomHashMap.this.key[pos] != 0)
/*  966 */           consumer.accept(new AbstractInt2ShortMap.BasicEntry(Int2ShortOpenCustomHashMap.this.key[pos], Int2ShortOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Int2ShortMap.Entry> consumer) {
/*  971 */       AbstractInt2ShortMap.BasicEntry entry = new AbstractInt2ShortMap.BasicEntry();
/*  972 */       if (Int2ShortOpenCustomHashMap.this.containsNullKey) {
/*  973 */         entry.key = Int2ShortOpenCustomHashMap.this.key[Int2ShortOpenCustomHashMap.this.n];
/*  974 */         entry.value = Int2ShortOpenCustomHashMap.this.value[Int2ShortOpenCustomHashMap.this.n];
/*  975 */         consumer.accept(entry);
/*      */       } 
/*  977 */       for (int pos = Int2ShortOpenCustomHashMap.this.n; pos-- != 0;) {
/*  978 */         if (Int2ShortOpenCustomHashMap.this.key[pos] != 0) {
/*  979 */           entry.key = Int2ShortOpenCustomHashMap.this.key[pos];
/*  980 */           entry.value = Int2ShortOpenCustomHashMap.this.value[pos];
/*  981 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Int2ShortMap.FastEntrySet int2ShortEntrySet() {
/*  987 */     if (this.entries == null)
/*  988 */       this.entries = new MapEntrySet(); 
/*  989 */     return this.entries;
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
/* 1006 */       return Int2ShortOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractIntSet { private KeySet() {}
/*      */     
/*      */     public IntIterator iterator() {
/* 1012 */       return new Int2ShortOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer consumer) {
/* 1017 */       if (Int2ShortOpenCustomHashMap.this.containsNullKey)
/* 1018 */         consumer.accept(Int2ShortOpenCustomHashMap.this.key[Int2ShortOpenCustomHashMap.this.n]); 
/* 1019 */       for (int pos = Int2ShortOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1020 */         int k = Int2ShortOpenCustomHashMap.this.key[pos];
/* 1021 */         if (k != 0)
/* 1022 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1027 */       return Int2ShortOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(int k) {
/* 1031 */       return Int2ShortOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(int k) {
/* 1035 */       int oldSize = Int2ShortOpenCustomHashMap.this.size;
/* 1036 */       Int2ShortOpenCustomHashMap.this.remove(k);
/* 1037 */       return (Int2ShortOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1041 */       Int2ShortOpenCustomHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public IntSet keySet() {
/* 1046 */     if (this.keys == null)
/* 1047 */       this.keys = new KeySet(); 
/* 1048 */     return this.keys;
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
/* 1065 */       return Int2ShortOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public ShortCollection values() {
/* 1070 */     if (this.values == null)
/* 1071 */       this.values = (ShortCollection)new AbstractShortCollection()
/*      */         {
/*      */           public ShortIterator iterator() {
/* 1074 */             return new Int2ShortOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1078 */             return Int2ShortOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(short v) {
/* 1082 */             return Int2ShortOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1086 */             Int2ShortOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(IntConsumer consumer)
/*      */           {
/* 1091 */             if (Int2ShortOpenCustomHashMap.this.containsNullKey)
/* 1092 */               consumer.accept(Int2ShortOpenCustomHashMap.this.value[Int2ShortOpenCustomHashMap.this.n]); 
/* 1093 */             for (int pos = Int2ShortOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1094 */               if (Int2ShortOpenCustomHashMap.this.key[pos] != 0)
/* 1095 */                 consumer.accept(Int2ShortOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1098 */     return this.values;
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
/* 1115 */     return trim(this.size);
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
/* 1139 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1140 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1141 */       return true; 
/*      */     try {
/* 1143 */       rehash(l);
/* 1144 */     } catch (OutOfMemoryError cantDoIt) {
/* 1145 */       return false;
/*      */     } 
/* 1147 */     return true;
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
/* 1163 */     int[] key = this.key;
/* 1164 */     short[] value = this.value;
/* 1165 */     int mask = newN - 1;
/* 1166 */     int[] newKey = new int[newN + 1];
/* 1167 */     short[] newValue = new short[newN + 1];
/* 1168 */     int i = this.n;
/* 1169 */     for (int j = realSize(); j-- != 0; ) {
/* 1170 */       while (key[--i] == 0); int pos;
/* 1171 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != 0)
/* 1172 */         while (newKey[pos = pos + 1 & mask] != 0); 
/* 1173 */       newKey[pos] = key[i];
/* 1174 */       newValue[pos] = value[i];
/*      */     } 
/* 1176 */     newValue[newN] = value[this.n];
/* 1177 */     this.n = newN;
/* 1178 */     this.mask = mask;
/* 1179 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1180 */     this.key = newKey;
/* 1181 */     this.value = newValue;
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
/*      */   public Int2ShortOpenCustomHashMap clone() {
/*      */     Int2ShortOpenCustomHashMap c;
/*      */     try {
/* 1198 */       c = (Int2ShortOpenCustomHashMap)super.clone();
/* 1199 */     } catch (CloneNotSupportedException cantHappen) {
/* 1200 */       throw new InternalError();
/*      */     } 
/* 1202 */     c.keys = null;
/* 1203 */     c.values = null;
/* 1204 */     c.entries = null;
/* 1205 */     c.containsNullKey = this.containsNullKey;
/* 1206 */     c.key = (int[])this.key.clone();
/* 1207 */     c.value = (short[])this.value.clone();
/* 1208 */     c.strategy = this.strategy;
/* 1209 */     return c;
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
/* 1222 */     int h = 0;
/* 1223 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1224 */       while (this.key[i] == 0)
/* 1225 */         i++; 
/* 1226 */       t = this.strategy.hashCode(this.key[i]);
/* 1227 */       t ^= this.value[i];
/* 1228 */       h += t;
/* 1229 */       i++;
/*      */     } 
/*      */     
/* 1232 */     if (this.containsNullKey)
/* 1233 */       h += this.value[this.n]; 
/* 1234 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1237 */     int[] key = this.key;
/* 1238 */     short[] value = this.value;
/* 1239 */     MapIterator i = new MapIterator();
/* 1240 */     s.defaultWriteObject();
/* 1241 */     for (int j = this.size; j-- != 0; ) {
/* 1242 */       int e = i.nextEntry();
/* 1243 */       s.writeInt(key[e]);
/* 1244 */       s.writeShort(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1249 */     s.defaultReadObject();
/* 1250 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1251 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1252 */     this.mask = this.n - 1;
/* 1253 */     int[] key = this.key = new int[this.n + 1];
/* 1254 */     short[] value = this.value = new short[this.n + 1];
/*      */ 
/*      */     
/* 1257 */     for (int i = this.size; i-- != 0; ) {
/* 1258 */       int pos, k = s.readInt();
/* 1259 */       short v = s.readShort();
/* 1260 */       if (this.strategy.equals(k, 0)) {
/* 1261 */         pos = this.n;
/* 1262 */         this.containsNullKey = true;
/*      */       } else {
/* 1264 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1265 */         while (key[pos] != 0)
/* 1266 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1268 */       key[pos] = k;
/* 1269 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\Int2ShortOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */