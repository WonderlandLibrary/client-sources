/*      */ package it.unimi.dsi.fastutil.shorts;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
/*      */ import it.unimi.dsi.fastutil.chars.CharCollection;
/*      */ import it.unimi.dsi.fastutil.chars.CharIterator;
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
/*      */ public class Short2CharOpenCustomHashMap
/*      */   extends AbstractShort2CharMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient short[] key;
/*      */   protected transient char[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected ShortHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Short2CharMap.FastEntrySet entries;
/*      */   protected transient ShortSet keys;
/*      */   protected transient CharCollection values;
/*      */   
/*      */   public Short2CharOpenCustomHashMap(int expected, float f, ShortHash.Strategy strategy) {
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
/*  117 */     this.value = new char[this.n + 1];
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
/*      */   public Short2CharOpenCustomHashMap(int expected, ShortHash.Strategy strategy) {
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
/*      */   public Short2CharOpenCustomHashMap(ShortHash.Strategy strategy) {
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
/*      */   public Short2CharOpenCustomHashMap(Map<? extends Short, ? extends Character> m, float f, ShortHash.Strategy strategy) {
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
/*      */   public Short2CharOpenCustomHashMap(Map<? extends Short, ? extends Character> m, ShortHash.Strategy strategy) {
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
/*      */   public Short2CharOpenCustomHashMap(Short2CharMap m, float f, ShortHash.Strategy strategy) {
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
/*      */   public Short2CharOpenCustomHashMap(Short2CharMap m, ShortHash.Strategy strategy) {
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
/*      */   public Short2CharOpenCustomHashMap(short[] k, char[] v, float f, ShortHash.Strategy strategy) {
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
/*      */   public Short2CharOpenCustomHashMap(short[] k, char[] v, ShortHash.Strategy strategy) {
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
/*      */   private char removeEntry(int pos) {
/*  261 */     char oldValue = this.value[pos];
/*  262 */     this.size--;
/*  263 */     shiftKeys(pos);
/*  264 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  265 */       rehash(this.n / 2); 
/*  266 */     return oldValue;
/*      */   }
/*      */   private char removeNullEntry() {
/*  269 */     this.containsNullKey = false;
/*  270 */     char oldValue = this.value[this.n];
/*  271 */     this.size--;
/*  272 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  273 */       rehash(this.n / 2); 
/*  274 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Short, ? extends Character> m) {
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
/*      */   private void insert(int pos, short k, char v) {
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
/*      */   public char put(short k, char v) {
/*  317 */     int pos = find(k);
/*  318 */     if (pos < 0) {
/*  319 */       insert(-pos - 1, k, v);
/*  320 */       return this.defRetValue;
/*      */     } 
/*  322 */     char oldValue = this.value[pos];
/*  323 */     this.value[pos] = v;
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
/*      */   protected final void shiftKeys(int pos) {
/*  337 */     short[] key = this.key; while (true) {
/*      */       short curr; int last;
/*  339 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  341 */         if ((curr = key[pos]) == 0) {
/*  342 */           key[last] = 0;
/*      */           return;
/*      */         } 
/*  345 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  346 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  348 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  350 */       key[last] = curr;
/*  351 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public char remove(short k) {
/*  357 */     if (this.strategy.equals(k, (short)0)) {
/*  358 */       if (this.containsNullKey)
/*  359 */         return removeNullEntry(); 
/*  360 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  363 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  366 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  367 */       return this.defRetValue; 
/*  368 */     if (this.strategy.equals(k, curr))
/*  369 */       return removeEntry(pos); 
/*      */     while (true) {
/*  371 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  372 */         return this.defRetValue; 
/*  373 */       if (this.strategy.equals(k, curr)) {
/*  374 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public char get(short k) {
/*  380 */     if (this.strategy.equals(k, (short)0)) {
/*  381 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  383 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  386 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  387 */       return this.defRetValue; 
/*  388 */     if (this.strategy.equals(k, curr)) {
/*  389 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  392 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  393 */         return this.defRetValue; 
/*  394 */       if (this.strategy.equals(k, curr)) {
/*  395 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(short k) {
/*  401 */     if (this.strategy.equals(k, (short)0)) {
/*  402 */       return this.containsNullKey;
/*      */     }
/*  404 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  407 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  408 */       return false; 
/*  409 */     if (this.strategy.equals(k, curr)) {
/*  410 */       return true;
/*      */     }
/*      */     while (true) {
/*  413 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  414 */         return false; 
/*  415 */       if (this.strategy.equals(k, curr))
/*  416 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(char v) {
/*  421 */     char[] value = this.value;
/*  422 */     short[] key = this.key;
/*  423 */     if (this.containsNullKey && value[this.n] == v)
/*  424 */       return true; 
/*  425 */     for (int i = this.n; i-- != 0;) {
/*  426 */       if (key[i] != 0 && value[i] == v)
/*  427 */         return true; 
/*  428 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char getOrDefault(short k, char defaultValue) {
/*  434 */     if (this.strategy.equals(k, (short)0)) {
/*  435 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  437 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  440 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  441 */       return defaultValue; 
/*  442 */     if (this.strategy.equals(k, curr)) {
/*  443 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  446 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  447 */         return defaultValue; 
/*  448 */       if (this.strategy.equals(k, curr)) {
/*  449 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public char putIfAbsent(short k, char v) {
/*  455 */     int pos = find(k);
/*  456 */     if (pos >= 0)
/*  457 */       return this.value[pos]; 
/*  458 */     insert(-pos - 1, k, v);
/*  459 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(short k, char v) {
/*  465 */     if (this.strategy.equals(k, (short)0)) {
/*  466 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  467 */         removeNullEntry();
/*  468 */         return true;
/*      */       } 
/*  470 */       return false;
/*      */     } 
/*      */     
/*  473 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  476 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  477 */       return false; 
/*  478 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  479 */       removeEntry(pos);
/*  480 */       return true;
/*      */     } 
/*      */     while (true) {
/*  483 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  484 */         return false; 
/*  485 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  486 */         removeEntry(pos);
/*  487 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(short k, char oldValue, char v) {
/*  494 */     int pos = find(k);
/*  495 */     if (pos < 0 || oldValue != this.value[pos])
/*  496 */       return false; 
/*  497 */     this.value[pos] = v;
/*  498 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public char replace(short k, char v) {
/*  503 */     int pos = find(k);
/*  504 */     if (pos < 0)
/*  505 */       return this.defRetValue; 
/*  506 */     char oldValue = this.value[pos];
/*  507 */     this.value[pos] = v;
/*  508 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public char computeIfAbsent(short k, IntUnaryOperator mappingFunction) {
/*  513 */     Objects.requireNonNull(mappingFunction);
/*  514 */     int pos = find(k);
/*  515 */     if (pos >= 0)
/*  516 */       return this.value[pos]; 
/*  517 */     char newValue = SafeMath.safeIntToChar(mappingFunction.applyAsInt(k));
/*  518 */     insert(-pos - 1, k, newValue);
/*  519 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char computeIfAbsentNullable(short k, IntFunction<? extends Character> mappingFunction) {
/*  525 */     Objects.requireNonNull(mappingFunction);
/*  526 */     int pos = find(k);
/*  527 */     if (pos >= 0)
/*  528 */       return this.value[pos]; 
/*  529 */     Character newValue = mappingFunction.apply(k);
/*  530 */     if (newValue == null)
/*  531 */       return this.defRetValue; 
/*  532 */     char v = newValue.charValue();
/*  533 */     insert(-pos - 1, k, v);
/*  534 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char computeIfPresent(short k, BiFunction<? super Short, ? super Character, ? extends Character> remappingFunction) {
/*  540 */     Objects.requireNonNull(remappingFunction);
/*  541 */     int pos = find(k);
/*  542 */     if (pos < 0)
/*  543 */       return this.defRetValue; 
/*  544 */     Character newValue = remappingFunction.apply(Short.valueOf(k), Character.valueOf(this.value[pos]));
/*  545 */     if (newValue == null) {
/*  546 */       if (this.strategy.equals(k, (short)0)) {
/*  547 */         removeNullEntry();
/*      */       } else {
/*  549 */         removeEntry(pos);
/*  550 */       }  return this.defRetValue;
/*      */     } 
/*  552 */     this.value[pos] = newValue.charValue(); return newValue.charValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char compute(short k, BiFunction<? super Short, ? super Character, ? extends Character> remappingFunction) {
/*  558 */     Objects.requireNonNull(remappingFunction);
/*  559 */     int pos = find(k);
/*  560 */     Character newValue = remappingFunction.apply(Short.valueOf(k), 
/*  561 */         (pos >= 0) ? Character.valueOf(this.value[pos]) : null);
/*  562 */     if (newValue == null) {
/*  563 */       if (pos >= 0)
/*  564 */         if (this.strategy.equals(k, (short)0)) {
/*  565 */           removeNullEntry();
/*      */         } else {
/*  567 */           removeEntry(pos);
/*      */         }  
/*  569 */       return this.defRetValue;
/*      */     } 
/*  571 */     char newVal = newValue.charValue();
/*  572 */     if (pos < 0) {
/*  573 */       insert(-pos - 1, k, newVal);
/*  574 */       return newVal;
/*      */     } 
/*  576 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char merge(short k, char v, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
/*  582 */     Objects.requireNonNull(remappingFunction);
/*  583 */     int pos = find(k);
/*  584 */     if (pos < 0) {
/*  585 */       insert(-pos - 1, k, v);
/*  586 */       return v;
/*      */     } 
/*  588 */     Character newValue = remappingFunction.apply(Character.valueOf(this.value[pos]), Character.valueOf(v));
/*  589 */     if (newValue == null) {
/*  590 */       if (this.strategy.equals(k, (short)0)) {
/*  591 */         removeNullEntry();
/*      */       } else {
/*  593 */         removeEntry(pos);
/*  594 */       }  return this.defRetValue;
/*      */     } 
/*  596 */     this.value[pos] = newValue.charValue(); return newValue.charValue();
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
/*  607 */     if (this.size == 0)
/*      */       return; 
/*  609 */     this.size = 0;
/*  610 */     this.containsNullKey = false;
/*  611 */     Arrays.fill(this.key, (short)0);
/*      */   }
/*      */   
/*      */   public int size() {
/*  615 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  619 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Short2CharMap.Entry, Map.Entry<Short, Character>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  631 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public short getShortKey() {
/*  637 */       return Short2CharOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public char getCharValue() {
/*  641 */       return Short2CharOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public char setValue(char v) {
/*  645 */       char oldValue = Short2CharOpenCustomHashMap.this.value[this.index];
/*  646 */       Short2CharOpenCustomHashMap.this.value[this.index] = v;
/*  647 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Short getKey() {
/*  657 */       return Short.valueOf(Short2CharOpenCustomHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character getValue() {
/*  667 */       return Character.valueOf(Short2CharOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character setValue(Character v) {
/*  677 */       return Character.valueOf(setValue(v.charValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  682 */       if (!(o instanceof Map.Entry))
/*  683 */         return false; 
/*  684 */       Map.Entry<Short, Character> e = (Map.Entry<Short, Character>)o;
/*  685 */       return (Short2CharOpenCustomHashMap.this.strategy.equals(Short2CharOpenCustomHashMap.this.key[this.index], ((Short)e.getKey()).shortValue()) && Short2CharOpenCustomHashMap.this.value[this.index] == ((Character)e
/*  686 */         .getValue()).charValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  690 */       return Short2CharOpenCustomHashMap.this.strategy.hashCode(Short2CharOpenCustomHashMap.this.key[this.index]) ^ Short2CharOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/*  694 */       return Short2CharOpenCustomHashMap.this.key[this.index] + "=>" + Short2CharOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  704 */     int pos = Short2CharOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  711 */     int last = -1;
/*      */     
/*  713 */     int c = Short2CharOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  717 */     boolean mustReturnNullKey = Short2CharOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     ShortArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  724 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  727 */       if (!hasNext())
/*  728 */         throw new NoSuchElementException(); 
/*  729 */       this.c--;
/*  730 */       if (this.mustReturnNullKey) {
/*  731 */         this.mustReturnNullKey = false;
/*  732 */         return this.last = Short2CharOpenCustomHashMap.this.n;
/*      */       } 
/*  734 */       short[] key = Short2CharOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  736 */         if (--this.pos < 0) {
/*      */           
/*  738 */           this.last = Integer.MIN_VALUE;
/*  739 */           short k = this.wrapped.getShort(-this.pos - 1);
/*  740 */           int p = HashCommon.mix(Short2CharOpenCustomHashMap.this.strategy.hashCode(k)) & Short2CharOpenCustomHashMap.this.mask;
/*  741 */           while (!Short2CharOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  742 */             p = p + 1 & Short2CharOpenCustomHashMap.this.mask; 
/*  743 */           return p;
/*      */         } 
/*  745 */         if (key[this.pos] != 0) {
/*  746 */           return this.last = this.pos;
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
/*  760 */       short[] key = Short2CharOpenCustomHashMap.this.key; while (true) {
/*      */         short curr; int last;
/*  762 */         pos = (last = pos) + 1 & Short2CharOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  764 */           if ((curr = key[pos]) == 0) {
/*  765 */             key[last] = 0;
/*      */             return;
/*      */           } 
/*  768 */           int slot = HashCommon.mix(Short2CharOpenCustomHashMap.this.strategy.hashCode(curr)) & Short2CharOpenCustomHashMap.this.mask;
/*  769 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  771 */           pos = pos + 1 & Short2CharOpenCustomHashMap.this.mask;
/*      */         } 
/*  773 */         if (pos < last) {
/*  774 */           if (this.wrapped == null)
/*  775 */             this.wrapped = new ShortArrayList(2); 
/*  776 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  778 */         key[last] = curr;
/*  779 */         Short2CharOpenCustomHashMap.this.value[last] = Short2CharOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  783 */       if (this.last == -1)
/*  784 */         throw new IllegalStateException(); 
/*  785 */       if (this.last == Short2CharOpenCustomHashMap.this.n) {
/*  786 */         Short2CharOpenCustomHashMap.this.containsNullKey = false;
/*  787 */       } else if (this.pos >= 0) {
/*  788 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  791 */         Short2CharOpenCustomHashMap.this.remove(this.wrapped.getShort(-this.pos - 1));
/*  792 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  795 */       Short2CharOpenCustomHashMap.this.size--;
/*  796 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  801 */       int i = n;
/*  802 */       while (i-- != 0 && hasNext())
/*  803 */         nextEntry(); 
/*  804 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Short2CharMap.Entry> { private Short2CharOpenCustomHashMap.MapEntry entry;
/*      */     
/*      */     public Short2CharOpenCustomHashMap.MapEntry next() {
/*  811 */       return this.entry = new Short2CharOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  815 */       super.remove();
/*  816 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Short2CharMap.Entry> { private FastEntryIterator() {
/*  820 */       this.entry = new Short2CharOpenCustomHashMap.MapEntry();
/*      */     } private final Short2CharOpenCustomHashMap.MapEntry entry;
/*      */     public Short2CharOpenCustomHashMap.MapEntry next() {
/*  823 */       this.entry.index = nextEntry();
/*  824 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Short2CharMap.Entry> implements Short2CharMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Short2CharMap.Entry> iterator() {
/*  830 */       return new Short2CharOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Short2CharMap.Entry> fastIterator() {
/*  834 */       return new Short2CharOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  839 */       if (!(o instanceof Map.Entry))
/*  840 */         return false; 
/*  841 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  842 */       if (e.getKey() == null || !(e.getKey() instanceof Short))
/*  843 */         return false; 
/*  844 */       if (e.getValue() == null || !(e.getValue() instanceof Character))
/*  845 */         return false; 
/*  846 */       short k = ((Short)e.getKey()).shortValue();
/*  847 */       char v = ((Character)e.getValue()).charValue();
/*  848 */       if (Short2CharOpenCustomHashMap.this.strategy.equals(k, (short)0)) {
/*  849 */         return (Short2CharOpenCustomHashMap.this.containsNullKey && Short2CharOpenCustomHashMap.this.value[Short2CharOpenCustomHashMap.this.n] == v);
/*      */       }
/*  851 */       short[] key = Short2CharOpenCustomHashMap.this.key;
/*      */       short curr;
/*      */       int pos;
/*  854 */       if ((curr = key[pos = HashCommon.mix(Short2CharOpenCustomHashMap.this.strategy.hashCode(k)) & Short2CharOpenCustomHashMap.this.mask]) == 0)
/*      */       {
/*  856 */         return false; } 
/*  857 */       if (Short2CharOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  858 */         return (Short2CharOpenCustomHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  861 */         if ((curr = key[pos = pos + 1 & Short2CharOpenCustomHashMap.this.mask]) == 0)
/*  862 */           return false; 
/*  863 */         if (Short2CharOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  864 */           return (Short2CharOpenCustomHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  870 */       if (!(o instanceof Map.Entry))
/*  871 */         return false; 
/*  872 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  873 */       if (e.getKey() == null || !(e.getKey() instanceof Short))
/*  874 */         return false; 
/*  875 */       if (e.getValue() == null || !(e.getValue() instanceof Character))
/*  876 */         return false; 
/*  877 */       short k = ((Short)e.getKey()).shortValue();
/*  878 */       char v = ((Character)e.getValue()).charValue();
/*  879 */       if (Short2CharOpenCustomHashMap.this.strategy.equals(k, (short)0)) {
/*  880 */         if (Short2CharOpenCustomHashMap.this.containsNullKey && Short2CharOpenCustomHashMap.this.value[Short2CharOpenCustomHashMap.this.n] == v) {
/*  881 */           Short2CharOpenCustomHashMap.this.removeNullEntry();
/*  882 */           return true;
/*      */         } 
/*  884 */         return false;
/*      */       } 
/*      */       
/*  887 */       short[] key = Short2CharOpenCustomHashMap.this.key;
/*      */       short curr;
/*      */       int pos;
/*  890 */       if ((curr = key[pos = HashCommon.mix(Short2CharOpenCustomHashMap.this.strategy.hashCode(k)) & Short2CharOpenCustomHashMap.this.mask]) == 0)
/*      */       {
/*  892 */         return false; } 
/*  893 */       if (Short2CharOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  894 */         if (Short2CharOpenCustomHashMap.this.value[pos] == v) {
/*  895 */           Short2CharOpenCustomHashMap.this.removeEntry(pos);
/*  896 */           return true;
/*      */         } 
/*  898 */         return false;
/*      */       } 
/*      */       while (true) {
/*  901 */         if ((curr = key[pos = pos + 1 & Short2CharOpenCustomHashMap.this.mask]) == 0)
/*  902 */           return false; 
/*  903 */         if (Short2CharOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  904 */           Short2CharOpenCustomHashMap.this.value[pos] == v) {
/*  905 */           Short2CharOpenCustomHashMap.this.removeEntry(pos);
/*  906 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  913 */       return Short2CharOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  917 */       Short2CharOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Short2CharMap.Entry> consumer) {
/*  922 */       if (Short2CharOpenCustomHashMap.this.containsNullKey)
/*  923 */         consumer.accept(new AbstractShort2CharMap.BasicEntry(Short2CharOpenCustomHashMap.this.key[Short2CharOpenCustomHashMap.this.n], Short2CharOpenCustomHashMap.this.value[Short2CharOpenCustomHashMap.this.n])); 
/*  924 */       for (int pos = Short2CharOpenCustomHashMap.this.n; pos-- != 0;) {
/*  925 */         if (Short2CharOpenCustomHashMap.this.key[pos] != 0)
/*  926 */           consumer.accept(new AbstractShort2CharMap.BasicEntry(Short2CharOpenCustomHashMap.this.key[pos], Short2CharOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Short2CharMap.Entry> consumer) {
/*  931 */       AbstractShort2CharMap.BasicEntry entry = new AbstractShort2CharMap.BasicEntry();
/*  932 */       if (Short2CharOpenCustomHashMap.this.containsNullKey) {
/*  933 */         entry.key = Short2CharOpenCustomHashMap.this.key[Short2CharOpenCustomHashMap.this.n];
/*  934 */         entry.value = Short2CharOpenCustomHashMap.this.value[Short2CharOpenCustomHashMap.this.n];
/*  935 */         consumer.accept(entry);
/*      */       } 
/*  937 */       for (int pos = Short2CharOpenCustomHashMap.this.n; pos-- != 0;) {
/*  938 */         if (Short2CharOpenCustomHashMap.this.key[pos] != 0) {
/*  939 */           entry.key = Short2CharOpenCustomHashMap.this.key[pos];
/*  940 */           entry.value = Short2CharOpenCustomHashMap.this.value[pos];
/*  941 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Short2CharMap.FastEntrySet short2CharEntrySet() {
/*  947 */     if (this.entries == null)
/*  948 */       this.entries = new MapEntrySet(); 
/*  949 */     return this.entries;
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
/*  966 */       return Short2CharOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractShortSet { private KeySet() {}
/*      */     
/*      */     public ShortIterator iterator() {
/*  972 */       return new Short2CharOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer consumer) {
/*  977 */       if (Short2CharOpenCustomHashMap.this.containsNullKey)
/*  978 */         consumer.accept(Short2CharOpenCustomHashMap.this.key[Short2CharOpenCustomHashMap.this.n]); 
/*  979 */       for (int pos = Short2CharOpenCustomHashMap.this.n; pos-- != 0; ) {
/*  980 */         short k = Short2CharOpenCustomHashMap.this.key[pos];
/*  981 */         if (k != 0)
/*  982 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  987 */       return Short2CharOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(short k) {
/*  991 */       return Short2CharOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(short k) {
/*  995 */       int oldSize = Short2CharOpenCustomHashMap.this.size;
/*  996 */       Short2CharOpenCustomHashMap.this.remove(k);
/*  997 */       return (Short2CharOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1001 */       Short2CharOpenCustomHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ShortSet keySet() {
/* 1006 */     if (this.keys == null)
/* 1007 */       this.keys = new KeySet(); 
/* 1008 */     return this.keys;
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
/*      */     implements CharIterator
/*      */   {
/*      */     public char nextChar() {
/* 1025 */       return Short2CharOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public CharCollection values() {
/* 1030 */     if (this.values == null)
/* 1031 */       this.values = (CharCollection)new AbstractCharCollection()
/*      */         {
/*      */           public CharIterator iterator() {
/* 1034 */             return new Short2CharOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1038 */             return Short2CharOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(char v) {
/* 1042 */             return Short2CharOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1046 */             Short2CharOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(IntConsumer consumer)
/*      */           {
/* 1051 */             if (Short2CharOpenCustomHashMap.this.containsNullKey)
/* 1052 */               consumer.accept(Short2CharOpenCustomHashMap.this.value[Short2CharOpenCustomHashMap.this.n]); 
/* 1053 */             for (int pos = Short2CharOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1054 */               if (Short2CharOpenCustomHashMap.this.key[pos] != 0)
/* 1055 */                 consumer.accept(Short2CharOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1058 */     return this.values;
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
/* 1075 */     return trim(this.size);
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
/* 1099 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1100 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1101 */       return true; 
/*      */     try {
/* 1103 */       rehash(l);
/* 1104 */     } catch (OutOfMemoryError cantDoIt) {
/* 1105 */       return false;
/*      */     } 
/* 1107 */     return true;
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
/* 1123 */     short[] key = this.key;
/* 1124 */     char[] value = this.value;
/* 1125 */     int mask = newN - 1;
/* 1126 */     short[] newKey = new short[newN + 1];
/* 1127 */     char[] newValue = new char[newN + 1];
/* 1128 */     int i = this.n;
/* 1129 */     for (int j = realSize(); j-- != 0; ) {
/* 1130 */       while (key[--i] == 0); int pos;
/* 1131 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != 0)
/*      */       {
/* 1133 */         while (newKey[pos = pos + 1 & mask] != 0); } 
/* 1134 */       newKey[pos] = key[i];
/* 1135 */       newValue[pos] = value[i];
/*      */     } 
/* 1137 */     newValue[newN] = value[this.n];
/* 1138 */     this.n = newN;
/* 1139 */     this.mask = mask;
/* 1140 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1141 */     this.key = newKey;
/* 1142 */     this.value = newValue;
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
/*      */   public Short2CharOpenCustomHashMap clone() {
/*      */     Short2CharOpenCustomHashMap c;
/*      */     try {
/* 1159 */       c = (Short2CharOpenCustomHashMap)super.clone();
/* 1160 */     } catch (CloneNotSupportedException cantHappen) {
/* 1161 */       throw new InternalError();
/*      */     } 
/* 1163 */     c.keys = null;
/* 1164 */     c.values = null;
/* 1165 */     c.entries = null;
/* 1166 */     c.containsNullKey = this.containsNullKey;
/* 1167 */     c.key = (short[])this.key.clone();
/* 1168 */     c.value = (char[])this.value.clone();
/* 1169 */     c.strategy = this.strategy;
/* 1170 */     return c;
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
/* 1183 */     int h = 0;
/* 1184 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1185 */       while (this.key[i] == 0)
/* 1186 */         i++; 
/* 1187 */       t = this.strategy.hashCode(this.key[i]);
/* 1188 */       t ^= this.value[i];
/* 1189 */       h += t;
/* 1190 */       i++;
/*      */     } 
/*      */     
/* 1193 */     if (this.containsNullKey)
/* 1194 */       h += this.value[this.n]; 
/* 1195 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1198 */     short[] key = this.key;
/* 1199 */     char[] value = this.value;
/* 1200 */     MapIterator i = new MapIterator();
/* 1201 */     s.defaultWriteObject();
/* 1202 */     for (int j = this.size; j-- != 0; ) {
/* 1203 */       int e = i.nextEntry();
/* 1204 */       s.writeShort(key[e]);
/* 1205 */       s.writeChar(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1210 */     s.defaultReadObject();
/* 1211 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1212 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1213 */     this.mask = this.n - 1;
/* 1214 */     short[] key = this.key = new short[this.n + 1];
/* 1215 */     char[] value = this.value = new char[this.n + 1];
/*      */ 
/*      */     
/* 1218 */     for (int i = this.size; i-- != 0; ) {
/* 1219 */       int pos; short k = s.readShort();
/* 1220 */       char v = s.readChar();
/* 1221 */       if (this.strategy.equals(k, (short)0)) {
/* 1222 */         pos = this.n;
/* 1223 */         this.containsNullKey = true;
/*      */       } else {
/* 1225 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1226 */         while (key[pos] != 0)
/* 1227 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1229 */       key[pos] = k;
/* 1230 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\Short2CharOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */