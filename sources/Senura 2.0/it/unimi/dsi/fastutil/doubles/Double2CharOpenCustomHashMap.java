/*      */ package it.unimi.dsi.fastutil.doubles;
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
/*      */ import java.util.function.DoubleConsumer;
/*      */ import java.util.function.DoubleFunction;
/*      */ import java.util.function.DoubleToIntFunction;
/*      */ import java.util.function.IntConsumer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Double2CharOpenCustomHashMap
/*      */   extends AbstractDouble2CharMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient double[] key;
/*      */   protected transient char[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected DoubleHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Double2CharMap.FastEntrySet entries;
/*      */   protected transient DoubleSet keys;
/*      */   protected transient CharCollection values;
/*      */   
/*      */   public Double2CharOpenCustomHashMap(int expected, float f, DoubleHash.Strategy strategy) {
/*  107 */     this.strategy = strategy;
/*  108 */     if (f <= 0.0F || f > 1.0F)
/*  109 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  110 */     if (expected < 0)
/*  111 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  112 */     this.f = f;
/*  113 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  114 */     this.mask = this.n - 1;
/*  115 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  116 */     this.key = new double[this.n + 1];
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
/*      */   public Double2CharOpenCustomHashMap(int expected, DoubleHash.Strategy strategy) {
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
/*      */   public Double2CharOpenCustomHashMap(DoubleHash.Strategy strategy) {
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
/*      */   public Double2CharOpenCustomHashMap(Map<? extends Double, ? extends Character> m, float f, DoubleHash.Strategy strategy) {
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
/*      */   public Double2CharOpenCustomHashMap(Map<? extends Double, ? extends Character> m, DoubleHash.Strategy strategy) {
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
/*      */   public Double2CharOpenCustomHashMap(Double2CharMap m, float f, DoubleHash.Strategy strategy) {
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
/*      */   public Double2CharOpenCustomHashMap(Double2CharMap m, DoubleHash.Strategy strategy) {
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
/*      */   public Double2CharOpenCustomHashMap(double[] k, char[] v, float f, DoubleHash.Strategy strategy) {
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
/*      */   public Double2CharOpenCustomHashMap(double[] k, char[] v, DoubleHash.Strategy strategy) {
/*  236 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleHash.Strategy strategy() {
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
/*      */   public void putAll(Map<? extends Double, ? extends Character> m) {
/*  278 */     if (this.f <= 0.5D) {
/*  279 */       ensureCapacity(m.size());
/*      */     } else {
/*  281 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  283 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(double k) {
/*  287 */     if (this.strategy.equals(k, 0.0D)) {
/*  288 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  290 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  293 */     if (Double.doubleToLongBits(
/*  294 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  295 */       return -(pos + 1); 
/*  296 */     if (this.strategy.equals(k, curr)) {
/*  297 */       return pos;
/*      */     }
/*      */     while (true) {
/*  300 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  301 */         return -(pos + 1); 
/*  302 */       if (this.strategy.equals(k, curr))
/*  303 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, double k, char v) {
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
/*      */   public char put(double k, char v) {
/*  318 */     int pos = find(k);
/*  319 */     if (pos < 0) {
/*  320 */       insert(-pos - 1, k, v);
/*  321 */       return this.defRetValue;
/*      */     } 
/*  323 */     char oldValue = this.value[pos];
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
/*  338 */     double[] key = this.key; while (true) {
/*      */       double curr; int last;
/*  340 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  342 */         if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/*  343 */           key[last] = 0.0D;
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
/*      */   public char remove(double k) {
/*  358 */     if (this.strategy.equals(k, 0.0D)) {
/*  359 */       if (this.containsNullKey)
/*  360 */         return removeNullEntry(); 
/*  361 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  364 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  367 */     if (Double.doubleToLongBits(
/*  368 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  369 */       return this.defRetValue; 
/*  370 */     if (this.strategy.equals(k, curr))
/*  371 */       return removeEntry(pos); 
/*      */     while (true) {
/*  373 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  374 */         return this.defRetValue; 
/*  375 */       if (this.strategy.equals(k, curr)) {
/*  376 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public char get(double k) {
/*  382 */     if (this.strategy.equals(k, 0.0D)) {
/*  383 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  385 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  388 */     if (Double.doubleToLongBits(
/*  389 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  390 */       return this.defRetValue; 
/*  391 */     if (this.strategy.equals(k, curr)) {
/*  392 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  395 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  396 */         return this.defRetValue; 
/*  397 */       if (this.strategy.equals(k, curr)) {
/*  398 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(double k) {
/*  404 */     if (this.strategy.equals(k, 0.0D)) {
/*  405 */       return this.containsNullKey;
/*      */     }
/*  407 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  410 */     if (Double.doubleToLongBits(
/*  411 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  412 */       return false; 
/*  413 */     if (this.strategy.equals(k, curr)) {
/*  414 */       return true;
/*      */     }
/*      */     while (true) {
/*  417 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  418 */         return false; 
/*  419 */       if (this.strategy.equals(k, curr))
/*  420 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(char v) {
/*  425 */     char[] value = this.value;
/*  426 */     double[] key = this.key;
/*  427 */     if (this.containsNullKey && value[this.n] == v)
/*  428 */       return true; 
/*  429 */     for (int i = this.n; i-- != 0;) {
/*  430 */       if (Double.doubleToLongBits(key[i]) != 0L && value[i] == v)
/*  431 */         return true; 
/*  432 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char getOrDefault(double k, char defaultValue) {
/*  438 */     if (this.strategy.equals(k, 0.0D)) {
/*  439 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  441 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  444 */     if (Double.doubleToLongBits(
/*  445 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  446 */       return defaultValue; 
/*  447 */     if (this.strategy.equals(k, curr)) {
/*  448 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  451 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  452 */         return defaultValue; 
/*  453 */       if (this.strategy.equals(k, curr)) {
/*  454 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public char putIfAbsent(double k, char v) {
/*  460 */     int pos = find(k);
/*  461 */     if (pos >= 0)
/*  462 */       return this.value[pos]; 
/*  463 */     insert(-pos - 1, k, v);
/*  464 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(double k, char v) {
/*  470 */     if (this.strategy.equals(k, 0.0D)) {
/*  471 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  472 */         removeNullEntry();
/*  473 */         return true;
/*      */       } 
/*  475 */       return false;
/*      */     } 
/*      */     
/*  478 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  481 */     if (Double.doubleToLongBits(
/*  482 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  483 */       return false; 
/*  484 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  485 */       removeEntry(pos);
/*  486 */       return true;
/*      */     } 
/*      */     while (true) {
/*  489 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  490 */         return false; 
/*  491 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  492 */         removeEntry(pos);
/*  493 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(double k, char oldValue, char v) {
/*  500 */     int pos = find(k);
/*  501 */     if (pos < 0 || oldValue != this.value[pos])
/*  502 */       return false; 
/*  503 */     this.value[pos] = v;
/*  504 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public char replace(double k, char v) {
/*  509 */     int pos = find(k);
/*  510 */     if (pos < 0)
/*  511 */       return this.defRetValue; 
/*  512 */     char oldValue = this.value[pos];
/*  513 */     this.value[pos] = v;
/*  514 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public char computeIfAbsent(double k, DoubleToIntFunction mappingFunction) {
/*  519 */     Objects.requireNonNull(mappingFunction);
/*  520 */     int pos = find(k);
/*  521 */     if (pos >= 0)
/*  522 */       return this.value[pos]; 
/*  523 */     char newValue = SafeMath.safeIntToChar(mappingFunction.applyAsInt(k));
/*  524 */     insert(-pos - 1, k, newValue);
/*  525 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char computeIfAbsentNullable(double k, DoubleFunction<? extends Character> mappingFunction) {
/*  531 */     Objects.requireNonNull(mappingFunction);
/*  532 */     int pos = find(k);
/*  533 */     if (pos >= 0)
/*  534 */       return this.value[pos]; 
/*  535 */     Character newValue = mappingFunction.apply(k);
/*  536 */     if (newValue == null)
/*  537 */       return this.defRetValue; 
/*  538 */     char v = newValue.charValue();
/*  539 */     insert(-pos - 1, k, v);
/*  540 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char computeIfPresent(double k, BiFunction<? super Double, ? super Character, ? extends Character> remappingFunction) {
/*  546 */     Objects.requireNonNull(remappingFunction);
/*  547 */     int pos = find(k);
/*  548 */     if (pos < 0)
/*  549 */       return this.defRetValue; 
/*  550 */     Character newValue = remappingFunction.apply(Double.valueOf(k), Character.valueOf(this.value[pos]));
/*  551 */     if (newValue == null) {
/*  552 */       if (this.strategy.equals(k, 0.0D)) {
/*  553 */         removeNullEntry();
/*      */       } else {
/*  555 */         removeEntry(pos);
/*  556 */       }  return this.defRetValue;
/*      */     } 
/*  558 */     this.value[pos] = newValue.charValue(); return newValue.charValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char compute(double k, BiFunction<? super Double, ? super Character, ? extends Character> remappingFunction) {
/*  564 */     Objects.requireNonNull(remappingFunction);
/*  565 */     int pos = find(k);
/*  566 */     Character newValue = remappingFunction.apply(Double.valueOf(k), 
/*  567 */         (pos >= 0) ? Character.valueOf(this.value[pos]) : null);
/*  568 */     if (newValue == null) {
/*  569 */       if (pos >= 0)
/*  570 */         if (this.strategy.equals(k, 0.0D)) {
/*  571 */           removeNullEntry();
/*      */         } else {
/*  573 */           removeEntry(pos);
/*      */         }  
/*  575 */       return this.defRetValue;
/*      */     } 
/*  577 */     char newVal = newValue.charValue();
/*  578 */     if (pos < 0) {
/*  579 */       insert(-pos - 1, k, newVal);
/*  580 */       return newVal;
/*      */     } 
/*  582 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char merge(double k, char v, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
/*  588 */     Objects.requireNonNull(remappingFunction);
/*  589 */     int pos = find(k);
/*  590 */     if (pos < 0) {
/*  591 */       insert(-pos - 1, k, v);
/*  592 */       return v;
/*      */     } 
/*  594 */     Character newValue = remappingFunction.apply(Character.valueOf(this.value[pos]), Character.valueOf(v));
/*  595 */     if (newValue == null) {
/*  596 */       if (this.strategy.equals(k, 0.0D)) {
/*  597 */         removeNullEntry();
/*      */       } else {
/*  599 */         removeEntry(pos);
/*  600 */       }  return this.defRetValue;
/*      */     } 
/*  602 */     this.value[pos] = newValue.charValue(); return newValue.charValue();
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
/*  613 */     if (this.size == 0)
/*      */       return; 
/*  615 */     this.size = 0;
/*  616 */     this.containsNullKey = false;
/*  617 */     Arrays.fill(this.key, 0.0D);
/*      */   }
/*      */   
/*      */   public int size() {
/*  621 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  625 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Double2CharMap.Entry, Map.Entry<Double, Character>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  637 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public double getDoubleKey() {
/*  643 */       return Double2CharOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public char getCharValue() {
/*  647 */       return Double2CharOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public char setValue(char v) {
/*  651 */       char oldValue = Double2CharOpenCustomHashMap.this.value[this.index];
/*  652 */       Double2CharOpenCustomHashMap.this.value[this.index] = v;
/*  653 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double getKey() {
/*  663 */       return Double.valueOf(Double2CharOpenCustomHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character getValue() {
/*  673 */       return Character.valueOf(Double2CharOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character setValue(Character v) {
/*  683 */       return Character.valueOf(setValue(v.charValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  688 */       if (!(o instanceof Map.Entry))
/*  689 */         return false; 
/*  690 */       Map.Entry<Double, Character> e = (Map.Entry<Double, Character>)o;
/*  691 */       return (Double2CharOpenCustomHashMap.this.strategy.equals(Double2CharOpenCustomHashMap.this.key[this.index], ((Double)e.getKey()).doubleValue()) && Double2CharOpenCustomHashMap.this.value[this.index] == ((Character)e
/*  692 */         .getValue()).charValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  696 */       return Double2CharOpenCustomHashMap.this.strategy.hashCode(Double2CharOpenCustomHashMap.this.key[this.index]) ^ Double2CharOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/*  700 */       return Double2CharOpenCustomHashMap.this.key[this.index] + "=>" + Double2CharOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  710 */     int pos = Double2CharOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  717 */     int last = -1;
/*      */     
/*  719 */     int c = Double2CharOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  723 */     boolean mustReturnNullKey = Double2CharOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     DoubleArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  730 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  733 */       if (!hasNext())
/*  734 */         throw new NoSuchElementException(); 
/*  735 */       this.c--;
/*  736 */       if (this.mustReturnNullKey) {
/*  737 */         this.mustReturnNullKey = false;
/*  738 */         return this.last = Double2CharOpenCustomHashMap.this.n;
/*      */       } 
/*  740 */       double[] key = Double2CharOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  742 */         if (--this.pos < 0) {
/*      */           
/*  744 */           this.last = Integer.MIN_VALUE;
/*  745 */           double k = this.wrapped.getDouble(-this.pos - 1);
/*  746 */           int p = HashCommon.mix(Double2CharOpenCustomHashMap.this.strategy.hashCode(k)) & Double2CharOpenCustomHashMap.this.mask;
/*  747 */           while (!Double2CharOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  748 */             p = p + 1 & Double2CharOpenCustomHashMap.this.mask; 
/*  749 */           return p;
/*      */         } 
/*  751 */         if (Double.doubleToLongBits(key[this.pos]) != 0L) {
/*  752 */           return this.last = this.pos;
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
/*  766 */       double[] key = Double2CharOpenCustomHashMap.this.key; while (true) {
/*      */         double curr; int last;
/*  768 */         pos = (last = pos) + 1 & Double2CharOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  770 */           if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/*  771 */             key[last] = 0.0D;
/*      */             return;
/*      */           } 
/*  774 */           int slot = HashCommon.mix(Double2CharOpenCustomHashMap.this.strategy.hashCode(curr)) & Double2CharOpenCustomHashMap.this.mask;
/*  775 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  777 */           pos = pos + 1 & Double2CharOpenCustomHashMap.this.mask;
/*      */         } 
/*  779 */         if (pos < last) {
/*  780 */           if (this.wrapped == null)
/*  781 */             this.wrapped = new DoubleArrayList(2); 
/*  782 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  784 */         key[last] = curr;
/*  785 */         Double2CharOpenCustomHashMap.this.value[last] = Double2CharOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  789 */       if (this.last == -1)
/*  790 */         throw new IllegalStateException(); 
/*  791 */       if (this.last == Double2CharOpenCustomHashMap.this.n) {
/*  792 */         Double2CharOpenCustomHashMap.this.containsNullKey = false;
/*  793 */       } else if (this.pos >= 0) {
/*  794 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  797 */         Double2CharOpenCustomHashMap.this.remove(this.wrapped.getDouble(-this.pos - 1));
/*  798 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  801 */       Double2CharOpenCustomHashMap.this.size--;
/*  802 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  807 */       int i = n;
/*  808 */       while (i-- != 0 && hasNext())
/*  809 */         nextEntry(); 
/*  810 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Double2CharMap.Entry> { private Double2CharOpenCustomHashMap.MapEntry entry;
/*      */     
/*      */     public Double2CharOpenCustomHashMap.MapEntry next() {
/*  817 */       return this.entry = new Double2CharOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  821 */       super.remove();
/*  822 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Double2CharMap.Entry> { private FastEntryIterator() {
/*  826 */       this.entry = new Double2CharOpenCustomHashMap.MapEntry();
/*      */     } private final Double2CharOpenCustomHashMap.MapEntry entry;
/*      */     public Double2CharOpenCustomHashMap.MapEntry next() {
/*  829 */       this.entry.index = nextEntry();
/*  830 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Double2CharMap.Entry> implements Double2CharMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Double2CharMap.Entry> iterator() {
/*  836 */       return new Double2CharOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Double2CharMap.Entry> fastIterator() {
/*  840 */       return new Double2CharOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  845 */       if (!(o instanceof Map.Entry))
/*  846 */         return false; 
/*  847 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  848 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/*  849 */         return false; 
/*  850 */       if (e.getValue() == null || !(e.getValue() instanceof Character))
/*  851 */         return false; 
/*  852 */       double k = ((Double)e.getKey()).doubleValue();
/*  853 */       char v = ((Character)e.getValue()).charValue();
/*  854 */       if (Double2CharOpenCustomHashMap.this.strategy.equals(k, 0.0D)) {
/*  855 */         return (Double2CharOpenCustomHashMap.this.containsNullKey && Double2CharOpenCustomHashMap.this.value[Double2CharOpenCustomHashMap.this.n] == v);
/*      */       }
/*  857 */       double[] key = Double2CharOpenCustomHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/*  860 */       if (Double.doubleToLongBits(
/*  861 */           curr = key[pos = HashCommon.mix(Double2CharOpenCustomHashMap.this.strategy.hashCode(k)) & Double2CharOpenCustomHashMap.this.mask]) == 0L)
/*  862 */         return false; 
/*  863 */       if (Double2CharOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  864 */         return (Double2CharOpenCustomHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  867 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2CharOpenCustomHashMap.this.mask]) == 0L)
/*  868 */           return false; 
/*  869 */         if (Double2CharOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  870 */           return (Double2CharOpenCustomHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  876 */       if (!(o instanceof Map.Entry))
/*  877 */         return false; 
/*  878 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  879 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/*  880 */         return false; 
/*  881 */       if (e.getValue() == null || !(e.getValue() instanceof Character))
/*  882 */         return false; 
/*  883 */       double k = ((Double)e.getKey()).doubleValue();
/*  884 */       char v = ((Character)e.getValue()).charValue();
/*  885 */       if (Double2CharOpenCustomHashMap.this.strategy.equals(k, 0.0D)) {
/*  886 */         if (Double2CharOpenCustomHashMap.this.containsNullKey && Double2CharOpenCustomHashMap.this.value[Double2CharOpenCustomHashMap.this.n] == v) {
/*  887 */           Double2CharOpenCustomHashMap.this.removeNullEntry();
/*  888 */           return true;
/*      */         } 
/*  890 */         return false;
/*      */       } 
/*      */       
/*  893 */       double[] key = Double2CharOpenCustomHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/*  896 */       if (Double.doubleToLongBits(
/*  897 */           curr = key[pos = HashCommon.mix(Double2CharOpenCustomHashMap.this.strategy.hashCode(k)) & Double2CharOpenCustomHashMap.this.mask]) == 0L)
/*  898 */         return false; 
/*  899 */       if (Double2CharOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  900 */         if (Double2CharOpenCustomHashMap.this.value[pos] == v) {
/*  901 */           Double2CharOpenCustomHashMap.this.removeEntry(pos);
/*  902 */           return true;
/*      */         } 
/*  904 */         return false;
/*      */       } 
/*      */       while (true) {
/*  907 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2CharOpenCustomHashMap.this.mask]) == 0L)
/*  908 */           return false; 
/*  909 */         if (Double2CharOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  910 */           Double2CharOpenCustomHashMap.this.value[pos] == v) {
/*  911 */           Double2CharOpenCustomHashMap.this.removeEntry(pos);
/*  912 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  919 */       return Double2CharOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  923 */       Double2CharOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Double2CharMap.Entry> consumer) {
/*  928 */       if (Double2CharOpenCustomHashMap.this.containsNullKey)
/*  929 */         consumer.accept(new AbstractDouble2CharMap.BasicEntry(Double2CharOpenCustomHashMap.this.key[Double2CharOpenCustomHashMap.this.n], Double2CharOpenCustomHashMap.this.value[Double2CharOpenCustomHashMap.this.n])); 
/*  930 */       for (int pos = Double2CharOpenCustomHashMap.this.n; pos-- != 0;) {
/*  931 */         if (Double.doubleToLongBits(Double2CharOpenCustomHashMap.this.key[pos]) != 0L)
/*  932 */           consumer.accept(new AbstractDouble2CharMap.BasicEntry(Double2CharOpenCustomHashMap.this.key[pos], Double2CharOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Double2CharMap.Entry> consumer) {
/*  937 */       AbstractDouble2CharMap.BasicEntry entry = new AbstractDouble2CharMap.BasicEntry();
/*  938 */       if (Double2CharOpenCustomHashMap.this.containsNullKey) {
/*  939 */         entry.key = Double2CharOpenCustomHashMap.this.key[Double2CharOpenCustomHashMap.this.n];
/*  940 */         entry.value = Double2CharOpenCustomHashMap.this.value[Double2CharOpenCustomHashMap.this.n];
/*  941 */         consumer.accept(entry);
/*      */       } 
/*  943 */       for (int pos = Double2CharOpenCustomHashMap.this.n; pos-- != 0;) {
/*  944 */         if (Double.doubleToLongBits(Double2CharOpenCustomHashMap.this.key[pos]) != 0L) {
/*  945 */           entry.key = Double2CharOpenCustomHashMap.this.key[pos];
/*  946 */           entry.value = Double2CharOpenCustomHashMap.this.value[pos];
/*  947 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Double2CharMap.FastEntrySet double2CharEntrySet() {
/*  953 */     if (this.entries == null)
/*  954 */       this.entries = new MapEntrySet(); 
/*  955 */     return this.entries;
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
/*      */     implements DoubleIterator
/*      */   {
/*      */     public double nextDouble() {
/*  972 */       return Double2CharOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractDoubleSet { private KeySet() {}
/*      */     
/*      */     public DoubleIterator iterator() {
/*  978 */       return new Double2CharOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/*  983 */       if (Double2CharOpenCustomHashMap.this.containsNullKey)
/*  984 */         consumer.accept(Double2CharOpenCustomHashMap.this.key[Double2CharOpenCustomHashMap.this.n]); 
/*  985 */       for (int pos = Double2CharOpenCustomHashMap.this.n; pos-- != 0; ) {
/*  986 */         double k = Double2CharOpenCustomHashMap.this.key[pos];
/*  987 */         if (Double.doubleToLongBits(k) != 0L)
/*  988 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  993 */       return Double2CharOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(double k) {
/*  997 */       return Double2CharOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(double k) {
/* 1001 */       int oldSize = Double2CharOpenCustomHashMap.this.size;
/* 1002 */       Double2CharOpenCustomHashMap.this.remove(k);
/* 1003 */       return (Double2CharOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1007 */       Double2CharOpenCustomHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public DoubleSet keySet() {
/* 1012 */     if (this.keys == null)
/* 1013 */       this.keys = new KeySet(); 
/* 1014 */     return this.keys;
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
/* 1031 */       return Double2CharOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public CharCollection values() {
/* 1036 */     if (this.values == null)
/* 1037 */       this.values = (CharCollection)new AbstractCharCollection()
/*      */         {
/*      */           public CharIterator iterator() {
/* 1040 */             return new Double2CharOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1044 */             return Double2CharOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(char v) {
/* 1048 */             return Double2CharOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1052 */             Double2CharOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(IntConsumer consumer)
/*      */           {
/* 1057 */             if (Double2CharOpenCustomHashMap.this.containsNullKey)
/* 1058 */               consumer.accept(Double2CharOpenCustomHashMap.this.value[Double2CharOpenCustomHashMap.this.n]); 
/* 1059 */             for (int pos = Double2CharOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1060 */               if (Double.doubleToLongBits(Double2CharOpenCustomHashMap.this.key[pos]) != 0L)
/* 1061 */                 consumer.accept(Double2CharOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1064 */     return this.values;
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
/* 1081 */     return trim(this.size);
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
/* 1105 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1106 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1107 */       return true; 
/*      */     try {
/* 1109 */       rehash(l);
/* 1110 */     } catch (OutOfMemoryError cantDoIt) {
/* 1111 */       return false;
/*      */     } 
/* 1113 */     return true;
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
/* 1129 */     double[] key = this.key;
/* 1130 */     char[] value = this.value;
/* 1131 */     int mask = newN - 1;
/* 1132 */     double[] newKey = new double[newN + 1];
/* 1133 */     char[] newValue = new char[newN + 1];
/* 1134 */     int i = this.n;
/* 1135 */     for (int j = realSize(); j-- != 0; ) {
/* 1136 */       while (Double.doubleToLongBits(key[--i]) == 0L); int pos;
/* 1137 */       if (Double.doubleToLongBits(newKey[
/* 1138 */             pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask]) != 0L)
/* 1139 */         while (Double.doubleToLongBits(newKey[pos = pos + 1 & mask]) != 0L); 
/* 1140 */       newKey[pos] = key[i];
/* 1141 */       newValue[pos] = value[i];
/*      */     } 
/* 1143 */     newValue[newN] = value[this.n];
/* 1144 */     this.n = newN;
/* 1145 */     this.mask = mask;
/* 1146 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1147 */     this.key = newKey;
/* 1148 */     this.value = newValue;
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
/*      */   public Double2CharOpenCustomHashMap clone() {
/*      */     Double2CharOpenCustomHashMap c;
/*      */     try {
/* 1165 */       c = (Double2CharOpenCustomHashMap)super.clone();
/* 1166 */     } catch (CloneNotSupportedException cantHappen) {
/* 1167 */       throw new InternalError();
/*      */     } 
/* 1169 */     c.keys = null;
/* 1170 */     c.values = null;
/* 1171 */     c.entries = null;
/* 1172 */     c.containsNullKey = this.containsNullKey;
/* 1173 */     c.key = (double[])this.key.clone();
/* 1174 */     c.value = (char[])this.value.clone();
/* 1175 */     c.strategy = this.strategy;
/* 1176 */     return c;
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
/* 1189 */     int h = 0;
/* 1190 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1191 */       while (Double.doubleToLongBits(this.key[i]) == 0L)
/* 1192 */         i++; 
/* 1193 */       t = this.strategy.hashCode(this.key[i]);
/* 1194 */       t ^= this.value[i];
/* 1195 */       h += t;
/* 1196 */       i++;
/*      */     } 
/*      */     
/* 1199 */     if (this.containsNullKey)
/* 1200 */       h += this.value[this.n]; 
/* 1201 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1204 */     double[] key = this.key;
/* 1205 */     char[] value = this.value;
/* 1206 */     MapIterator i = new MapIterator();
/* 1207 */     s.defaultWriteObject();
/* 1208 */     for (int j = this.size; j-- != 0; ) {
/* 1209 */       int e = i.nextEntry();
/* 1210 */       s.writeDouble(key[e]);
/* 1211 */       s.writeChar(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1216 */     s.defaultReadObject();
/* 1217 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1218 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1219 */     this.mask = this.n - 1;
/* 1220 */     double[] key = this.key = new double[this.n + 1];
/* 1221 */     char[] value = this.value = new char[this.n + 1];
/*      */ 
/*      */     
/* 1224 */     for (int i = this.size; i-- != 0; ) {
/* 1225 */       int pos; double k = s.readDouble();
/* 1226 */       char v = s.readChar();
/* 1227 */       if (this.strategy.equals(k, 0.0D)) {
/* 1228 */         pos = this.n;
/* 1229 */         this.containsNullKey = true;
/*      */       } else {
/* 1231 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1232 */         while (Double.doubleToLongBits(key[pos]) != 0L)
/* 1233 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1235 */       key[pos] = k;
/* 1236 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2CharOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */