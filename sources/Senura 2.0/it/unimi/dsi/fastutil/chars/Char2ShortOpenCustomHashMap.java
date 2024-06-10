/*      */ package it.unimi.dsi.fastutil.chars;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Char2ShortOpenCustomHashMap
/*      */   extends AbstractChar2ShortMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient char[] key;
/*      */   protected transient short[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected CharHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Char2ShortMap.FastEntrySet entries;
/*      */   protected transient CharSet keys;
/*      */   protected transient ShortCollection values;
/*      */   
/*      */   public Char2ShortOpenCustomHashMap(int expected, float f, CharHash.Strategy strategy) {
/*  107 */     this.strategy = strategy;
/*  108 */     if (f <= 0.0F || f > 1.0F)
/*  109 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  110 */     if (expected < 0)
/*  111 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  112 */     this.f = f;
/*  113 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  114 */     this.mask = this.n - 1;
/*  115 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  116 */     this.key = new char[this.n + 1];
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
/*      */   public Char2ShortOpenCustomHashMap(int expected, CharHash.Strategy strategy) {
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
/*      */   public Char2ShortOpenCustomHashMap(CharHash.Strategy strategy) {
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
/*      */   public Char2ShortOpenCustomHashMap(Map<? extends Character, ? extends Short> m, float f, CharHash.Strategy strategy) {
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
/*      */   public Char2ShortOpenCustomHashMap(Map<? extends Character, ? extends Short> m, CharHash.Strategy strategy) {
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
/*      */   public Char2ShortOpenCustomHashMap(Char2ShortMap m, float f, CharHash.Strategy strategy) {
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
/*      */   public Char2ShortOpenCustomHashMap(Char2ShortMap m, CharHash.Strategy strategy) {
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
/*      */   public Char2ShortOpenCustomHashMap(char[] k, short[] v, float f, CharHash.Strategy strategy) {
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
/*      */   public Char2ShortOpenCustomHashMap(char[] k, short[] v, CharHash.Strategy strategy) {
/*  236 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CharHash.Strategy strategy() {
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
/*      */   public void putAll(Map<? extends Character, ? extends Short> m) {
/*  278 */     if (this.f <= 0.5D) {
/*  279 */       ensureCapacity(m.size());
/*      */     } else {
/*  281 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  283 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(char k) {
/*  287 */     if (this.strategy.equals(k, false)) {
/*  288 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  290 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  293 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == '\000')
/*  294 */       return -(pos + 1); 
/*  295 */     if (this.strategy.equals(k, curr)) {
/*  296 */       return pos;
/*      */     }
/*      */     while (true) {
/*  299 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  300 */         return -(pos + 1); 
/*  301 */       if (this.strategy.equals(k, curr))
/*  302 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, char k, short v) {
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
/*      */   public short put(char k, short v) {
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
/*      */   public short addTo(char k, short incr) {
/*      */     int pos;
/*  349 */     if (this.strategy.equals(k, false)) {
/*  350 */       if (this.containsNullKey)
/*  351 */         return addToValue(this.n, incr); 
/*  352 */       pos = this.n;
/*  353 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  356 */       char[] key = this.key;
/*      */       char curr;
/*  358 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != '\000') {
/*      */         
/*  360 */         if (this.strategy.equals(curr, k))
/*  361 */           return addToValue(pos, incr); 
/*  362 */         while ((curr = key[pos = pos + 1 & this.mask]) != '\000') {
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
/*  386 */     char[] key = this.key; while (true) {
/*      */       char curr; int last;
/*  388 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  390 */         if ((curr = key[pos]) == '\000') {
/*  391 */           key[last] = Character.MIN_VALUE;
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
/*      */   public short remove(char k) {
/*  406 */     if (this.strategy.equals(k, false)) {
/*  407 */       if (this.containsNullKey)
/*  408 */         return removeNullEntry(); 
/*  409 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  412 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  415 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == '\000')
/*  416 */       return this.defRetValue; 
/*  417 */     if (this.strategy.equals(k, curr))
/*  418 */       return removeEntry(pos); 
/*      */     while (true) {
/*  420 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  421 */         return this.defRetValue; 
/*  422 */       if (this.strategy.equals(k, curr)) {
/*  423 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public short get(char k) {
/*  429 */     if (this.strategy.equals(k, false)) {
/*  430 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  432 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  435 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == '\000')
/*  436 */       return this.defRetValue; 
/*  437 */     if (this.strategy.equals(k, curr)) {
/*  438 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  441 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  442 */         return this.defRetValue; 
/*  443 */       if (this.strategy.equals(k, curr)) {
/*  444 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(char k) {
/*  450 */     if (this.strategy.equals(k, false)) {
/*  451 */       return this.containsNullKey;
/*      */     }
/*  453 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  456 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == '\000')
/*  457 */       return false; 
/*  458 */     if (this.strategy.equals(k, curr)) {
/*  459 */       return true;
/*      */     }
/*      */     while (true) {
/*  462 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  463 */         return false; 
/*  464 */       if (this.strategy.equals(k, curr))
/*  465 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(short v) {
/*  470 */     short[] value = this.value;
/*  471 */     char[] key = this.key;
/*  472 */     if (this.containsNullKey && value[this.n] == v)
/*  473 */       return true; 
/*  474 */     for (int i = this.n; i-- != 0;) {
/*  475 */       if (key[i] != '\000' && value[i] == v)
/*  476 */         return true; 
/*  477 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short getOrDefault(char k, short defaultValue) {
/*  483 */     if (this.strategy.equals(k, false)) {
/*  484 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  486 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  489 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == '\000')
/*  490 */       return defaultValue; 
/*  491 */     if (this.strategy.equals(k, curr)) {
/*  492 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  495 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  496 */         return defaultValue; 
/*  497 */       if (this.strategy.equals(k, curr)) {
/*  498 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public short putIfAbsent(char k, short v) {
/*  504 */     int pos = find(k);
/*  505 */     if (pos >= 0)
/*  506 */       return this.value[pos]; 
/*  507 */     insert(-pos - 1, k, v);
/*  508 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(char k, short v) {
/*  514 */     if (this.strategy.equals(k, false)) {
/*  515 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  516 */         removeNullEntry();
/*  517 */         return true;
/*      */       } 
/*  519 */       return false;
/*      */     } 
/*      */     
/*  522 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  525 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == '\000')
/*  526 */       return false; 
/*  527 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  528 */       removeEntry(pos);
/*  529 */       return true;
/*      */     } 
/*      */     while (true) {
/*  532 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  533 */         return false; 
/*  534 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  535 */         removeEntry(pos);
/*  536 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(char k, short oldValue, short v) {
/*  543 */     int pos = find(k);
/*  544 */     if (pos < 0 || oldValue != this.value[pos])
/*  545 */       return false; 
/*  546 */     this.value[pos] = v;
/*  547 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public short replace(char k, short v) {
/*  552 */     int pos = find(k);
/*  553 */     if (pos < 0)
/*  554 */       return this.defRetValue; 
/*  555 */     short oldValue = this.value[pos];
/*  556 */     this.value[pos] = v;
/*  557 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public short computeIfAbsent(char k, IntUnaryOperator mappingFunction) {
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
/*      */   public short computeIfAbsentNullable(char k, IntFunction<? extends Short> mappingFunction) {
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
/*      */   public short computeIfPresent(char k, BiFunction<? super Character, ? super Short, ? extends Short> remappingFunction) {
/*  589 */     Objects.requireNonNull(remappingFunction);
/*  590 */     int pos = find(k);
/*  591 */     if (pos < 0)
/*  592 */       return this.defRetValue; 
/*  593 */     Short newValue = remappingFunction.apply(Character.valueOf(k), Short.valueOf(this.value[pos]));
/*  594 */     if (newValue == null) {
/*  595 */       if (this.strategy.equals(k, false)) {
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
/*      */   public short compute(char k, BiFunction<? super Character, ? super Short, ? extends Short> remappingFunction) {
/*  607 */     Objects.requireNonNull(remappingFunction);
/*  608 */     int pos = find(k);
/*  609 */     Short newValue = remappingFunction.apply(Character.valueOf(k), 
/*  610 */         (pos >= 0) ? Short.valueOf(this.value[pos]) : null);
/*  611 */     if (newValue == null) {
/*  612 */       if (pos >= 0)
/*  613 */         if (this.strategy.equals(k, false)) {
/*  614 */           removeNullEntry();
/*      */         } else {
/*  616 */           removeEntry(pos);
/*      */         }  
/*  618 */       return this.defRetValue;
/*      */     } 
/*  620 */     short newVal = newValue.shortValue();
/*  621 */     if (pos < 0) {
/*  622 */       insert(-pos - 1, k, newVal);
/*  623 */       return newVal;
/*      */     } 
/*  625 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short merge(char k, short v, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
/*  631 */     Objects.requireNonNull(remappingFunction);
/*  632 */     int pos = find(k);
/*  633 */     if (pos < 0) {
/*  634 */       insert(-pos - 1, k, v);
/*  635 */       return v;
/*      */     } 
/*  637 */     Short newValue = remappingFunction.apply(Short.valueOf(this.value[pos]), Short.valueOf(v));
/*  638 */     if (newValue == null) {
/*  639 */       if (this.strategy.equals(k, false)) {
/*  640 */         removeNullEntry();
/*      */       } else {
/*  642 */         removeEntry(pos);
/*  643 */       }  return this.defRetValue;
/*      */     } 
/*  645 */     this.value[pos] = newValue.shortValue(); return newValue.shortValue();
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
/*  656 */     if (this.size == 0)
/*      */       return; 
/*  658 */     this.size = 0;
/*  659 */     this.containsNullKey = false;
/*  660 */     Arrays.fill(this.key, false);
/*      */   }
/*      */   
/*      */   public int size() {
/*  664 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  668 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Char2ShortMap.Entry, Map.Entry<Character, Short>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  680 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public char getCharKey() {
/*  686 */       return Char2ShortOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public short getShortValue() {
/*  690 */       return Char2ShortOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public short setValue(short v) {
/*  694 */       short oldValue = Char2ShortOpenCustomHashMap.this.value[this.index];
/*  695 */       Char2ShortOpenCustomHashMap.this.value[this.index] = v;
/*  696 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character getKey() {
/*  706 */       return Character.valueOf(Char2ShortOpenCustomHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Short getValue() {
/*  716 */       return Short.valueOf(Char2ShortOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Short setValue(Short v) {
/*  726 */       return Short.valueOf(setValue(v.shortValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  731 */       if (!(o instanceof Map.Entry))
/*  732 */         return false; 
/*  733 */       Map.Entry<Character, Short> e = (Map.Entry<Character, Short>)o;
/*  734 */       return (Char2ShortOpenCustomHashMap.this.strategy.equals(Char2ShortOpenCustomHashMap.this.key[this.index], ((Character)e.getKey()).charValue()) && Char2ShortOpenCustomHashMap.this.value[this.index] == ((Short)e
/*  735 */         .getValue()).shortValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  739 */       return Char2ShortOpenCustomHashMap.this.strategy.hashCode(Char2ShortOpenCustomHashMap.this.key[this.index]) ^ Char2ShortOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/*  743 */       return Char2ShortOpenCustomHashMap.this.key[this.index] + "=>" + Char2ShortOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  753 */     int pos = Char2ShortOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  760 */     int last = -1;
/*      */     
/*  762 */     int c = Char2ShortOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  766 */     boolean mustReturnNullKey = Char2ShortOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     CharArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  773 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  776 */       if (!hasNext())
/*  777 */         throw new NoSuchElementException(); 
/*  778 */       this.c--;
/*  779 */       if (this.mustReturnNullKey) {
/*  780 */         this.mustReturnNullKey = false;
/*  781 */         return this.last = Char2ShortOpenCustomHashMap.this.n;
/*      */       } 
/*  783 */       char[] key = Char2ShortOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  785 */         if (--this.pos < 0) {
/*      */           
/*  787 */           this.last = Integer.MIN_VALUE;
/*  788 */           char k = this.wrapped.getChar(-this.pos - 1);
/*  789 */           int p = HashCommon.mix(Char2ShortOpenCustomHashMap.this.strategy.hashCode(k)) & Char2ShortOpenCustomHashMap.this.mask;
/*  790 */           while (!Char2ShortOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  791 */             p = p + 1 & Char2ShortOpenCustomHashMap.this.mask; 
/*  792 */           return p;
/*      */         } 
/*  794 */         if (key[this.pos] != '\000') {
/*  795 */           return this.last = this.pos;
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
/*  809 */       char[] key = Char2ShortOpenCustomHashMap.this.key; while (true) {
/*      */         char curr; int last;
/*  811 */         pos = (last = pos) + 1 & Char2ShortOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  813 */           if ((curr = key[pos]) == '\000') {
/*  814 */             key[last] = Character.MIN_VALUE;
/*      */             return;
/*      */           } 
/*  817 */           int slot = HashCommon.mix(Char2ShortOpenCustomHashMap.this.strategy.hashCode(curr)) & Char2ShortOpenCustomHashMap.this.mask;
/*  818 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  820 */           pos = pos + 1 & Char2ShortOpenCustomHashMap.this.mask;
/*      */         } 
/*  822 */         if (pos < last) {
/*  823 */           if (this.wrapped == null)
/*  824 */             this.wrapped = new CharArrayList(2); 
/*  825 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  827 */         key[last] = curr;
/*  828 */         Char2ShortOpenCustomHashMap.this.value[last] = Char2ShortOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  832 */       if (this.last == -1)
/*  833 */         throw new IllegalStateException(); 
/*  834 */       if (this.last == Char2ShortOpenCustomHashMap.this.n) {
/*  835 */         Char2ShortOpenCustomHashMap.this.containsNullKey = false;
/*  836 */       } else if (this.pos >= 0) {
/*  837 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  840 */         Char2ShortOpenCustomHashMap.this.remove(this.wrapped.getChar(-this.pos - 1));
/*  841 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  844 */       Char2ShortOpenCustomHashMap.this.size--;
/*  845 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  850 */       int i = n;
/*  851 */       while (i-- != 0 && hasNext())
/*  852 */         nextEntry(); 
/*  853 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Char2ShortMap.Entry> { private Char2ShortOpenCustomHashMap.MapEntry entry;
/*      */     
/*      */     public Char2ShortOpenCustomHashMap.MapEntry next() {
/*  860 */       return this.entry = new Char2ShortOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  864 */       super.remove();
/*  865 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Char2ShortMap.Entry> { private FastEntryIterator() {
/*  869 */       this.entry = new Char2ShortOpenCustomHashMap.MapEntry();
/*      */     } private final Char2ShortOpenCustomHashMap.MapEntry entry;
/*      */     public Char2ShortOpenCustomHashMap.MapEntry next() {
/*  872 */       this.entry.index = nextEntry();
/*  873 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Char2ShortMap.Entry> implements Char2ShortMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Char2ShortMap.Entry> iterator() {
/*  879 */       return new Char2ShortOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Char2ShortMap.Entry> fastIterator() {
/*  883 */       return new Char2ShortOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  888 */       if (!(o instanceof Map.Entry))
/*  889 */         return false; 
/*  890 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  891 */       if (e.getKey() == null || !(e.getKey() instanceof Character))
/*  892 */         return false; 
/*  893 */       if (e.getValue() == null || !(e.getValue() instanceof Short))
/*  894 */         return false; 
/*  895 */       char k = ((Character)e.getKey()).charValue();
/*  896 */       short v = ((Short)e.getValue()).shortValue();
/*  897 */       if (Char2ShortOpenCustomHashMap.this.strategy.equals(k, false)) {
/*  898 */         return (Char2ShortOpenCustomHashMap.this.containsNullKey && Char2ShortOpenCustomHashMap.this.value[Char2ShortOpenCustomHashMap.this.n] == v);
/*      */       }
/*  900 */       char[] key = Char2ShortOpenCustomHashMap.this.key;
/*      */       char curr;
/*      */       int pos;
/*  903 */       if ((curr = key[pos = HashCommon.mix(Char2ShortOpenCustomHashMap.this.strategy.hashCode(k)) & Char2ShortOpenCustomHashMap.this.mask]) == '\000')
/*  904 */         return false; 
/*  905 */       if (Char2ShortOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  906 */         return (Char2ShortOpenCustomHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  909 */         if ((curr = key[pos = pos + 1 & Char2ShortOpenCustomHashMap.this.mask]) == '\000')
/*  910 */           return false; 
/*  911 */         if (Char2ShortOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  912 */           return (Char2ShortOpenCustomHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  918 */       if (!(o instanceof Map.Entry))
/*  919 */         return false; 
/*  920 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  921 */       if (e.getKey() == null || !(e.getKey() instanceof Character))
/*  922 */         return false; 
/*  923 */       if (e.getValue() == null || !(e.getValue() instanceof Short))
/*  924 */         return false; 
/*  925 */       char k = ((Character)e.getKey()).charValue();
/*  926 */       short v = ((Short)e.getValue()).shortValue();
/*  927 */       if (Char2ShortOpenCustomHashMap.this.strategy.equals(k, false)) {
/*  928 */         if (Char2ShortOpenCustomHashMap.this.containsNullKey && Char2ShortOpenCustomHashMap.this.value[Char2ShortOpenCustomHashMap.this.n] == v) {
/*  929 */           Char2ShortOpenCustomHashMap.this.removeNullEntry();
/*  930 */           return true;
/*      */         } 
/*  932 */         return false;
/*      */       } 
/*      */       
/*  935 */       char[] key = Char2ShortOpenCustomHashMap.this.key;
/*      */       char curr;
/*      */       int pos;
/*  938 */       if ((curr = key[pos = HashCommon.mix(Char2ShortOpenCustomHashMap.this.strategy.hashCode(k)) & Char2ShortOpenCustomHashMap.this.mask]) == '\000')
/*  939 */         return false; 
/*  940 */       if (Char2ShortOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  941 */         if (Char2ShortOpenCustomHashMap.this.value[pos] == v) {
/*  942 */           Char2ShortOpenCustomHashMap.this.removeEntry(pos);
/*  943 */           return true;
/*      */         } 
/*  945 */         return false;
/*      */       } 
/*      */       while (true) {
/*  948 */         if ((curr = key[pos = pos + 1 & Char2ShortOpenCustomHashMap.this.mask]) == '\000')
/*  949 */           return false; 
/*  950 */         if (Char2ShortOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  951 */           Char2ShortOpenCustomHashMap.this.value[pos] == v) {
/*  952 */           Char2ShortOpenCustomHashMap.this.removeEntry(pos);
/*  953 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  960 */       return Char2ShortOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  964 */       Char2ShortOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Char2ShortMap.Entry> consumer) {
/*  969 */       if (Char2ShortOpenCustomHashMap.this.containsNullKey)
/*  970 */         consumer.accept(new AbstractChar2ShortMap.BasicEntry(Char2ShortOpenCustomHashMap.this.key[Char2ShortOpenCustomHashMap.this.n], Char2ShortOpenCustomHashMap.this.value[Char2ShortOpenCustomHashMap.this.n])); 
/*  971 */       for (int pos = Char2ShortOpenCustomHashMap.this.n; pos-- != 0;) {
/*  972 */         if (Char2ShortOpenCustomHashMap.this.key[pos] != '\000')
/*  973 */           consumer.accept(new AbstractChar2ShortMap.BasicEntry(Char2ShortOpenCustomHashMap.this.key[pos], Char2ShortOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Char2ShortMap.Entry> consumer) {
/*  978 */       AbstractChar2ShortMap.BasicEntry entry = new AbstractChar2ShortMap.BasicEntry();
/*  979 */       if (Char2ShortOpenCustomHashMap.this.containsNullKey) {
/*  980 */         entry.key = Char2ShortOpenCustomHashMap.this.key[Char2ShortOpenCustomHashMap.this.n];
/*  981 */         entry.value = Char2ShortOpenCustomHashMap.this.value[Char2ShortOpenCustomHashMap.this.n];
/*  982 */         consumer.accept(entry);
/*      */       } 
/*  984 */       for (int pos = Char2ShortOpenCustomHashMap.this.n; pos-- != 0;) {
/*  985 */         if (Char2ShortOpenCustomHashMap.this.key[pos] != '\000') {
/*  986 */           entry.key = Char2ShortOpenCustomHashMap.this.key[pos];
/*  987 */           entry.value = Char2ShortOpenCustomHashMap.this.value[pos];
/*  988 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Char2ShortMap.FastEntrySet char2ShortEntrySet() {
/*  994 */     if (this.entries == null)
/*  995 */       this.entries = new MapEntrySet(); 
/*  996 */     return this.entries;
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
/*      */     implements CharIterator
/*      */   {
/*      */     public char nextChar() {
/* 1013 */       return Char2ShortOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractCharSet { private KeySet() {}
/*      */     
/*      */     public CharIterator iterator() {
/* 1019 */       return new Char2ShortOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer consumer) {
/* 1024 */       if (Char2ShortOpenCustomHashMap.this.containsNullKey)
/* 1025 */         consumer.accept(Char2ShortOpenCustomHashMap.this.key[Char2ShortOpenCustomHashMap.this.n]); 
/* 1026 */       for (int pos = Char2ShortOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1027 */         char k = Char2ShortOpenCustomHashMap.this.key[pos];
/* 1028 */         if (k != '\000')
/* 1029 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1034 */       return Char2ShortOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(char k) {
/* 1038 */       return Char2ShortOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(char k) {
/* 1042 */       int oldSize = Char2ShortOpenCustomHashMap.this.size;
/* 1043 */       Char2ShortOpenCustomHashMap.this.remove(k);
/* 1044 */       return (Char2ShortOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1048 */       Char2ShortOpenCustomHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public CharSet keySet() {
/* 1053 */     if (this.keys == null)
/* 1054 */       this.keys = new KeySet(); 
/* 1055 */     return this.keys;
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
/* 1072 */       return Char2ShortOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public ShortCollection values() {
/* 1077 */     if (this.values == null)
/* 1078 */       this.values = (ShortCollection)new AbstractShortCollection()
/*      */         {
/*      */           public ShortIterator iterator() {
/* 1081 */             return new Char2ShortOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1085 */             return Char2ShortOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(short v) {
/* 1089 */             return Char2ShortOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1093 */             Char2ShortOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(IntConsumer consumer)
/*      */           {
/* 1098 */             if (Char2ShortOpenCustomHashMap.this.containsNullKey)
/* 1099 */               consumer.accept(Char2ShortOpenCustomHashMap.this.value[Char2ShortOpenCustomHashMap.this.n]); 
/* 1100 */             for (int pos = Char2ShortOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1101 */               if (Char2ShortOpenCustomHashMap.this.key[pos] != '\000')
/* 1102 */                 consumer.accept(Char2ShortOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1105 */     return this.values;
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
/* 1122 */     return trim(this.size);
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
/* 1146 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1147 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1148 */       return true; 
/*      */     try {
/* 1150 */       rehash(l);
/* 1151 */     } catch (OutOfMemoryError cantDoIt) {
/* 1152 */       return false;
/*      */     } 
/* 1154 */     return true;
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
/* 1170 */     char[] key = this.key;
/* 1171 */     short[] value = this.value;
/* 1172 */     int mask = newN - 1;
/* 1173 */     char[] newKey = new char[newN + 1];
/* 1174 */     short[] newValue = new short[newN + 1];
/* 1175 */     int i = this.n;
/* 1176 */     for (int j = realSize(); j-- != 0; ) {
/* 1177 */       while (key[--i] == '\000'); int pos;
/* 1178 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != '\000')
/*      */       {
/* 1180 */         while (newKey[pos = pos + 1 & mask] != '\000'); } 
/* 1181 */       newKey[pos] = key[i];
/* 1182 */       newValue[pos] = value[i];
/*      */     } 
/* 1184 */     newValue[newN] = value[this.n];
/* 1185 */     this.n = newN;
/* 1186 */     this.mask = mask;
/* 1187 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1188 */     this.key = newKey;
/* 1189 */     this.value = newValue;
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
/*      */   public Char2ShortOpenCustomHashMap clone() {
/*      */     Char2ShortOpenCustomHashMap c;
/*      */     try {
/* 1206 */       c = (Char2ShortOpenCustomHashMap)super.clone();
/* 1207 */     } catch (CloneNotSupportedException cantHappen) {
/* 1208 */       throw new InternalError();
/*      */     } 
/* 1210 */     c.keys = null;
/* 1211 */     c.values = null;
/* 1212 */     c.entries = null;
/* 1213 */     c.containsNullKey = this.containsNullKey;
/* 1214 */     c.key = (char[])this.key.clone();
/* 1215 */     c.value = (short[])this.value.clone();
/* 1216 */     c.strategy = this.strategy;
/* 1217 */     return c;
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
/* 1230 */     int h = 0;
/* 1231 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1232 */       while (this.key[i] == '\000')
/* 1233 */         i++; 
/* 1234 */       t = this.strategy.hashCode(this.key[i]);
/* 1235 */       t ^= this.value[i];
/* 1236 */       h += t;
/* 1237 */       i++;
/*      */     } 
/*      */     
/* 1240 */     if (this.containsNullKey)
/* 1241 */       h += this.value[this.n]; 
/* 1242 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1245 */     char[] key = this.key;
/* 1246 */     short[] value = this.value;
/* 1247 */     MapIterator i = new MapIterator();
/* 1248 */     s.defaultWriteObject();
/* 1249 */     for (int j = this.size; j-- != 0; ) {
/* 1250 */       int e = i.nextEntry();
/* 1251 */       s.writeChar(key[e]);
/* 1252 */       s.writeShort(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1257 */     s.defaultReadObject();
/* 1258 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1259 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1260 */     this.mask = this.n - 1;
/* 1261 */     char[] key = this.key = new char[this.n + 1];
/* 1262 */     short[] value = this.value = new short[this.n + 1];
/*      */ 
/*      */     
/* 1265 */     for (int i = this.size; i-- != 0; ) {
/* 1266 */       int pos; char k = s.readChar();
/* 1267 */       short v = s.readShort();
/* 1268 */       if (this.strategy.equals(k, false)) {
/* 1269 */         pos = this.n;
/* 1270 */         this.containsNullKey = true;
/*      */       } else {
/* 1272 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1273 */         while (key[pos] != '\000')
/* 1274 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1276 */       key[pos] = k;
/* 1277 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\Char2ShortOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */