/*      */ package it.unimi.dsi.fastutil.ints;
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
/*      */ public class Int2CharOpenCustomHashMap
/*      */   extends AbstractInt2CharMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient int[] key;
/*      */   protected transient char[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected IntHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Int2CharMap.FastEntrySet entries;
/*      */   protected transient IntSet keys;
/*      */   protected transient CharCollection values;
/*      */   
/*      */   public Int2CharOpenCustomHashMap(int expected, float f, IntHash.Strategy strategy) {
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
/*  113 */     this.value = new char[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2CharOpenCustomHashMap(int expected, IntHash.Strategy strategy) {
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
/*      */   public Int2CharOpenCustomHashMap(IntHash.Strategy strategy) {
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
/*      */   public Int2CharOpenCustomHashMap(Map<? extends Integer, ? extends Character> m, float f, IntHash.Strategy strategy) {
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
/*      */   public Int2CharOpenCustomHashMap(Map<? extends Integer, ? extends Character> m, IntHash.Strategy strategy) {
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
/*      */   public Int2CharOpenCustomHashMap(Int2CharMap m, float f, IntHash.Strategy strategy) {
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
/*      */   public Int2CharOpenCustomHashMap(Int2CharMap m, IntHash.Strategy strategy) {
/*  190 */     this(m, 0.75F, strategy);
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
/*      */   public Int2CharOpenCustomHashMap(int[] k, char[] v, float f, IntHash.Strategy strategy) {
/*  208 */     this(k.length, f, strategy);
/*  209 */     if (k.length != v.length) {
/*  210 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  212 */     for (int i = 0; i < k.length; i++) {
/*  213 */       put(k[i], v[i]);
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
/*      */   public Int2CharOpenCustomHashMap(int[] k, char[] v, IntHash.Strategy strategy) {
/*  230 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntHash.Strategy strategy() {
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
/*      */   private char removeEntry(int pos) {
/*  255 */     char oldValue = this.value[pos];
/*  256 */     this.size--;
/*  257 */     shiftKeys(pos);
/*  258 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  259 */       rehash(this.n / 2); 
/*  260 */     return oldValue;
/*      */   }
/*      */   private char removeNullEntry() {
/*  263 */     this.containsNullKey = false;
/*  264 */     char oldValue = this.value[this.n];
/*  265 */     this.size--;
/*  266 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  267 */       rehash(this.n / 2); 
/*  268 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Integer, ? extends Character> m) {
/*  272 */     if (this.f <= 0.5D) {
/*  273 */       ensureCapacity(m.size());
/*      */     } else {
/*  275 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  277 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(int k) {
/*  281 */     if (this.strategy.equals(k, 0)) {
/*  282 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  284 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  287 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  288 */       return -(pos + 1); 
/*  289 */     if (this.strategy.equals(k, curr)) {
/*  290 */       return pos;
/*      */     }
/*      */     while (true) {
/*  293 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  294 */         return -(pos + 1); 
/*  295 */       if (this.strategy.equals(k, curr))
/*  296 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, int k, char v) {
/*  300 */     if (pos == this.n)
/*  301 */       this.containsNullKey = true; 
/*  302 */     this.key[pos] = k;
/*  303 */     this.value[pos] = v;
/*  304 */     if (this.size++ >= this.maxFill) {
/*  305 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public char put(int k, char v) {
/*  311 */     int pos = find(k);
/*  312 */     if (pos < 0) {
/*  313 */       insert(-pos - 1, k, v);
/*  314 */       return this.defRetValue;
/*      */     } 
/*  316 */     char oldValue = this.value[pos];
/*  317 */     this.value[pos] = v;
/*  318 */     return oldValue;
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
/*  331 */     int[] key = this.key; while (true) {
/*      */       int curr, last;
/*  333 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  335 */         if ((curr = key[pos]) == 0) {
/*  336 */           key[last] = 0;
/*      */           return;
/*      */         } 
/*  339 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  340 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  342 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  344 */       key[last] = curr;
/*  345 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public char remove(int k) {
/*  351 */     if (this.strategy.equals(k, 0)) {
/*  352 */       if (this.containsNullKey)
/*  353 */         return removeNullEntry(); 
/*  354 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  357 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  360 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  361 */       return this.defRetValue; 
/*  362 */     if (this.strategy.equals(k, curr))
/*  363 */       return removeEntry(pos); 
/*      */     while (true) {
/*  365 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  366 */         return this.defRetValue; 
/*  367 */       if (this.strategy.equals(k, curr)) {
/*  368 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public char get(int k) {
/*  374 */     if (this.strategy.equals(k, 0)) {
/*  375 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  377 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  380 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  381 */       return this.defRetValue; 
/*  382 */     if (this.strategy.equals(k, curr)) {
/*  383 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  386 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  387 */         return this.defRetValue; 
/*  388 */       if (this.strategy.equals(k, curr)) {
/*  389 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(int k) {
/*  395 */     if (this.strategy.equals(k, 0)) {
/*  396 */       return this.containsNullKey;
/*      */     }
/*  398 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  401 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  402 */       return false; 
/*  403 */     if (this.strategy.equals(k, curr)) {
/*  404 */       return true;
/*      */     }
/*      */     while (true) {
/*  407 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  408 */         return false; 
/*  409 */       if (this.strategy.equals(k, curr))
/*  410 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(char v) {
/*  415 */     char[] value = this.value;
/*  416 */     int[] key = this.key;
/*  417 */     if (this.containsNullKey && value[this.n] == v)
/*  418 */       return true; 
/*  419 */     for (int i = this.n; i-- != 0;) {
/*  420 */       if (key[i] != 0 && value[i] == v)
/*  421 */         return true; 
/*  422 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char getOrDefault(int k, char defaultValue) {
/*  428 */     if (this.strategy.equals(k, 0)) {
/*  429 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  431 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  434 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  435 */       return defaultValue; 
/*  436 */     if (this.strategy.equals(k, curr)) {
/*  437 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  440 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  441 */         return defaultValue; 
/*  442 */       if (this.strategy.equals(k, curr)) {
/*  443 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public char putIfAbsent(int k, char v) {
/*  449 */     int pos = find(k);
/*  450 */     if (pos >= 0)
/*  451 */       return this.value[pos]; 
/*  452 */     insert(-pos - 1, k, v);
/*  453 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(int k, char v) {
/*  459 */     if (this.strategy.equals(k, 0)) {
/*  460 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  461 */         removeNullEntry();
/*  462 */         return true;
/*      */       } 
/*  464 */       return false;
/*      */     } 
/*      */     
/*  467 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  470 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  471 */       return false; 
/*  472 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  473 */       removeEntry(pos);
/*  474 */       return true;
/*      */     } 
/*      */     while (true) {
/*  477 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  478 */         return false; 
/*  479 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  480 */         removeEntry(pos);
/*  481 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(int k, char oldValue, char v) {
/*  488 */     int pos = find(k);
/*  489 */     if (pos < 0 || oldValue != this.value[pos])
/*  490 */       return false; 
/*  491 */     this.value[pos] = v;
/*  492 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public char replace(int k, char v) {
/*  497 */     int pos = find(k);
/*  498 */     if (pos < 0)
/*  499 */       return this.defRetValue; 
/*  500 */     char oldValue = this.value[pos];
/*  501 */     this.value[pos] = v;
/*  502 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public char computeIfAbsent(int k, IntUnaryOperator mappingFunction) {
/*  507 */     Objects.requireNonNull(mappingFunction);
/*  508 */     int pos = find(k);
/*  509 */     if (pos >= 0)
/*  510 */       return this.value[pos]; 
/*  511 */     char newValue = SafeMath.safeIntToChar(mappingFunction.applyAsInt(k));
/*  512 */     insert(-pos - 1, k, newValue);
/*  513 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char computeIfAbsentNullable(int k, IntFunction<? extends Character> mappingFunction) {
/*  519 */     Objects.requireNonNull(mappingFunction);
/*  520 */     int pos = find(k);
/*  521 */     if (pos >= 0)
/*  522 */       return this.value[pos]; 
/*  523 */     Character newValue = mappingFunction.apply(k);
/*  524 */     if (newValue == null)
/*  525 */       return this.defRetValue; 
/*  526 */     char v = newValue.charValue();
/*  527 */     insert(-pos - 1, k, v);
/*  528 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char computeIfPresent(int k, BiFunction<? super Integer, ? super Character, ? extends Character> remappingFunction) {
/*  534 */     Objects.requireNonNull(remappingFunction);
/*  535 */     int pos = find(k);
/*  536 */     if (pos < 0)
/*  537 */       return this.defRetValue; 
/*  538 */     Character newValue = remappingFunction.apply(Integer.valueOf(k), Character.valueOf(this.value[pos]));
/*  539 */     if (newValue == null) {
/*  540 */       if (this.strategy.equals(k, 0)) {
/*  541 */         removeNullEntry();
/*      */       } else {
/*  543 */         removeEntry(pos);
/*  544 */       }  return this.defRetValue;
/*      */     } 
/*  546 */     this.value[pos] = newValue.charValue(); return newValue.charValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char compute(int k, BiFunction<? super Integer, ? super Character, ? extends Character> remappingFunction) {
/*  552 */     Objects.requireNonNull(remappingFunction);
/*  553 */     int pos = find(k);
/*  554 */     Character newValue = remappingFunction.apply(Integer.valueOf(k), 
/*  555 */         (pos >= 0) ? Character.valueOf(this.value[pos]) : null);
/*  556 */     if (newValue == null) {
/*  557 */       if (pos >= 0)
/*  558 */         if (this.strategy.equals(k, 0)) {
/*  559 */           removeNullEntry();
/*      */         } else {
/*  561 */           removeEntry(pos);
/*      */         }  
/*  563 */       return this.defRetValue;
/*      */     } 
/*  565 */     char newVal = newValue.charValue();
/*  566 */     if (pos < 0) {
/*  567 */       insert(-pos - 1, k, newVal);
/*  568 */       return newVal;
/*      */     } 
/*  570 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char merge(int k, char v, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
/*  576 */     Objects.requireNonNull(remappingFunction);
/*  577 */     int pos = find(k);
/*  578 */     if (pos < 0) {
/*  579 */       insert(-pos - 1, k, v);
/*  580 */       return v;
/*      */     } 
/*  582 */     Character newValue = remappingFunction.apply(Character.valueOf(this.value[pos]), Character.valueOf(v));
/*  583 */     if (newValue == null) {
/*  584 */       if (this.strategy.equals(k, 0)) {
/*  585 */         removeNullEntry();
/*      */       } else {
/*  587 */         removeEntry(pos);
/*  588 */       }  return this.defRetValue;
/*      */     } 
/*  590 */     this.value[pos] = newValue.charValue(); return newValue.charValue();
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
/*  601 */     if (this.size == 0)
/*      */       return; 
/*  603 */     this.size = 0;
/*  604 */     this.containsNullKey = false;
/*  605 */     Arrays.fill(this.key, 0);
/*      */   }
/*      */   
/*      */   public int size() {
/*  609 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  613 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Int2CharMap.Entry, Map.Entry<Integer, Character>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  625 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public int getIntKey() {
/*  631 */       return Int2CharOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public char getCharValue() {
/*  635 */       return Int2CharOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public char setValue(char v) {
/*  639 */       char oldValue = Int2CharOpenCustomHashMap.this.value[this.index];
/*  640 */       Int2CharOpenCustomHashMap.this.value[this.index] = v;
/*  641 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer getKey() {
/*  651 */       return Integer.valueOf(Int2CharOpenCustomHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character getValue() {
/*  661 */       return Character.valueOf(Int2CharOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character setValue(Character v) {
/*  671 */       return Character.valueOf(setValue(v.charValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  676 */       if (!(o instanceof Map.Entry))
/*  677 */         return false; 
/*  678 */       Map.Entry<Integer, Character> e = (Map.Entry<Integer, Character>)o;
/*  679 */       return (Int2CharOpenCustomHashMap.this.strategy.equals(Int2CharOpenCustomHashMap.this.key[this.index], ((Integer)e.getKey()).intValue()) && Int2CharOpenCustomHashMap.this.value[this.index] == ((Character)e
/*  680 */         .getValue()).charValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  684 */       return Int2CharOpenCustomHashMap.this.strategy.hashCode(Int2CharOpenCustomHashMap.this.key[this.index]) ^ Int2CharOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/*  688 */       return Int2CharOpenCustomHashMap.this.key[this.index] + "=>" + Int2CharOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  698 */     int pos = Int2CharOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  705 */     int last = -1;
/*      */     
/*  707 */     int c = Int2CharOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  711 */     boolean mustReturnNullKey = Int2CharOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     IntArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  718 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  721 */       if (!hasNext())
/*  722 */         throw new NoSuchElementException(); 
/*  723 */       this.c--;
/*  724 */       if (this.mustReturnNullKey) {
/*  725 */         this.mustReturnNullKey = false;
/*  726 */         return this.last = Int2CharOpenCustomHashMap.this.n;
/*      */       } 
/*  728 */       int[] key = Int2CharOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  730 */         if (--this.pos < 0) {
/*      */           
/*  732 */           this.last = Integer.MIN_VALUE;
/*  733 */           int k = this.wrapped.getInt(-this.pos - 1);
/*  734 */           int p = HashCommon.mix(Int2CharOpenCustomHashMap.this.strategy.hashCode(k)) & Int2CharOpenCustomHashMap.this.mask;
/*  735 */           while (!Int2CharOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  736 */             p = p + 1 & Int2CharOpenCustomHashMap.this.mask; 
/*  737 */           return p;
/*      */         } 
/*  739 */         if (key[this.pos] != 0) {
/*  740 */           return this.last = this.pos;
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
/*  754 */       int[] key = Int2CharOpenCustomHashMap.this.key; while (true) {
/*      */         int curr, last;
/*  756 */         pos = (last = pos) + 1 & Int2CharOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  758 */           if ((curr = key[pos]) == 0) {
/*  759 */             key[last] = 0;
/*      */             return;
/*      */           } 
/*  762 */           int slot = HashCommon.mix(Int2CharOpenCustomHashMap.this.strategy.hashCode(curr)) & Int2CharOpenCustomHashMap.this.mask;
/*  763 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  765 */           pos = pos + 1 & Int2CharOpenCustomHashMap.this.mask;
/*      */         } 
/*  767 */         if (pos < last) {
/*  768 */           if (this.wrapped == null)
/*  769 */             this.wrapped = new IntArrayList(2); 
/*  770 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  772 */         key[last] = curr;
/*  773 */         Int2CharOpenCustomHashMap.this.value[last] = Int2CharOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  777 */       if (this.last == -1)
/*  778 */         throw new IllegalStateException(); 
/*  779 */       if (this.last == Int2CharOpenCustomHashMap.this.n) {
/*  780 */         Int2CharOpenCustomHashMap.this.containsNullKey = false;
/*  781 */       } else if (this.pos >= 0) {
/*  782 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  785 */         Int2CharOpenCustomHashMap.this.remove(this.wrapped.getInt(-this.pos - 1));
/*  786 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  789 */       Int2CharOpenCustomHashMap.this.size--;
/*  790 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  795 */       int i = n;
/*  796 */       while (i-- != 0 && hasNext())
/*  797 */         nextEntry(); 
/*  798 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Int2CharMap.Entry> { private Int2CharOpenCustomHashMap.MapEntry entry;
/*      */     
/*      */     public Int2CharOpenCustomHashMap.MapEntry next() {
/*  805 */       return this.entry = new Int2CharOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  809 */       super.remove();
/*  810 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Int2CharMap.Entry> { private FastEntryIterator() {
/*  814 */       this.entry = new Int2CharOpenCustomHashMap.MapEntry();
/*      */     } private final Int2CharOpenCustomHashMap.MapEntry entry;
/*      */     public Int2CharOpenCustomHashMap.MapEntry next() {
/*  817 */       this.entry.index = nextEntry();
/*  818 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Int2CharMap.Entry> implements Int2CharMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Int2CharMap.Entry> iterator() {
/*  824 */       return new Int2CharOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Int2CharMap.Entry> fastIterator() {
/*  828 */       return new Int2CharOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  833 */       if (!(o instanceof Map.Entry))
/*  834 */         return false; 
/*  835 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  836 */       if (e.getKey() == null || !(e.getKey() instanceof Integer))
/*  837 */         return false; 
/*  838 */       if (e.getValue() == null || !(e.getValue() instanceof Character))
/*  839 */         return false; 
/*  840 */       int k = ((Integer)e.getKey()).intValue();
/*  841 */       char v = ((Character)e.getValue()).charValue();
/*  842 */       if (Int2CharOpenCustomHashMap.this.strategy.equals(k, 0)) {
/*  843 */         return (Int2CharOpenCustomHashMap.this.containsNullKey && Int2CharOpenCustomHashMap.this.value[Int2CharOpenCustomHashMap.this.n] == v);
/*      */       }
/*  845 */       int[] key = Int2CharOpenCustomHashMap.this.key;
/*      */       
/*      */       int curr, pos;
/*  848 */       if ((curr = key[pos = HashCommon.mix(Int2CharOpenCustomHashMap.this.strategy.hashCode(k)) & Int2CharOpenCustomHashMap.this.mask]) == 0)
/*  849 */         return false; 
/*  850 */       if (Int2CharOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  851 */         return (Int2CharOpenCustomHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  854 */         if ((curr = key[pos = pos + 1 & Int2CharOpenCustomHashMap.this.mask]) == 0)
/*  855 */           return false; 
/*  856 */         if (Int2CharOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  857 */           return (Int2CharOpenCustomHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  863 */       if (!(o instanceof Map.Entry))
/*  864 */         return false; 
/*  865 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  866 */       if (e.getKey() == null || !(e.getKey() instanceof Integer))
/*  867 */         return false; 
/*  868 */       if (e.getValue() == null || !(e.getValue() instanceof Character))
/*  869 */         return false; 
/*  870 */       int k = ((Integer)e.getKey()).intValue();
/*  871 */       char v = ((Character)e.getValue()).charValue();
/*  872 */       if (Int2CharOpenCustomHashMap.this.strategy.equals(k, 0)) {
/*  873 */         if (Int2CharOpenCustomHashMap.this.containsNullKey && Int2CharOpenCustomHashMap.this.value[Int2CharOpenCustomHashMap.this.n] == v) {
/*  874 */           Int2CharOpenCustomHashMap.this.removeNullEntry();
/*  875 */           return true;
/*      */         } 
/*  877 */         return false;
/*      */       } 
/*      */       
/*  880 */       int[] key = Int2CharOpenCustomHashMap.this.key;
/*      */       
/*      */       int curr, pos;
/*  883 */       if ((curr = key[pos = HashCommon.mix(Int2CharOpenCustomHashMap.this.strategy.hashCode(k)) & Int2CharOpenCustomHashMap.this.mask]) == 0)
/*  884 */         return false; 
/*  885 */       if (Int2CharOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  886 */         if (Int2CharOpenCustomHashMap.this.value[pos] == v) {
/*  887 */           Int2CharOpenCustomHashMap.this.removeEntry(pos);
/*  888 */           return true;
/*      */         } 
/*  890 */         return false;
/*      */       } 
/*      */       while (true) {
/*  893 */         if ((curr = key[pos = pos + 1 & Int2CharOpenCustomHashMap.this.mask]) == 0)
/*  894 */           return false; 
/*  895 */         if (Int2CharOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  896 */           Int2CharOpenCustomHashMap.this.value[pos] == v) {
/*  897 */           Int2CharOpenCustomHashMap.this.removeEntry(pos);
/*  898 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  905 */       return Int2CharOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  909 */       Int2CharOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Int2CharMap.Entry> consumer) {
/*  914 */       if (Int2CharOpenCustomHashMap.this.containsNullKey)
/*  915 */         consumer.accept(new AbstractInt2CharMap.BasicEntry(Int2CharOpenCustomHashMap.this.key[Int2CharOpenCustomHashMap.this.n], Int2CharOpenCustomHashMap.this.value[Int2CharOpenCustomHashMap.this.n])); 
/*  916 */       for (int pos = Int2CharOpenCustomHashMap.this.n; pos-- != 0;) {
/*  917 */         if (Int2CharOpenCustomHashMap.this.key[pos] != 0)
/*  918 */           consumer.accept(new AbstractInt2CharMap.BasicEntry(Int2CharOpenCustomHashMap.this.key[pos], Int2CharOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Int2CharMap.Entry> consumer) {
/*  923 */       AbstractInt2CharMap.BasicEntry entry = new AbstractInt2CharMap.BasicEntry();
/*  924 */       if (Int2CharOpenCustomHashMap.this.containsNullKey) {
/*  925 */         entry.key = Int2CharOpenCustomHashMap.this.key[Int2CharOpenCustomHashMap.this.n];
/*  926 */         entry.value = Int2CharOpenCustomHashMap.this.value[Int2CharOpenCustomHashMap.this.n];
/*  927 */         consumer.accept(entry);
/*      */       } 
/*  929 */       for (int pos = Int2CharOpenCustomHashMap.this.n; pos-- != 0;) {
/*  930 */         if (Int2CharOpenCustomHashMap.this.key[pos] != 0) {
/*  931 */           entry.key = Int2CharOpenCustomHashMap.this.key[pos];
/*  932 */           entry.value = Int2CharOpenCustomHashMap.this.value[pos];
/*  933 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Int2CharMap.FastEntrySet int2CharEntrySet() {
/*  939 */     if (this.entries == null)
/*  940 */       this.entries = new MapEntrySet(); 
/*  941 */     return this.entries;
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
/*  958 */       return Int2CharOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractIntSet { private KeySet() {}
/*      */     
/*      */     public IntIterator iterator() {
/*  964 */       return new Int2CharOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer consumer) {
/*  969 */       if (Int2CharOpenCustomHashMap.this.containsNullKey)
/*  970 */         consumer.accept(Int2CharOpenCustomHashMap.this.key[Int2CharOpenCustomHashMap.this.n]); 
/*  971 */       for (int pos = Int2CharOpenCustomHashMap.this.n; pos-- != 0; ) {
/*  972 */         int k = Int2CharOpenCustomHashMap.this.key[pos];
/*  973 */         if (k != 0)
/*  974 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  979 */       return Int2CharOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(int k) {
/*  983 */       return Int2CharOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(int k) {
/*  987 */       int oldSize = Int2CharOpenCustomHashMap.this.size;
/*  988 */       Int2CharOpenCustomHashMap.this.remove(k);
/*  989 */       return (Int2CharOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  993 */       Int2CharOpenCustomHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public IntSet keySet() {
/*  998 */     if (this.keys == null)
/*  999 */       this.keys = new KeySet(); 
/* 1000 */     return this.keys;
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
/* 1017 */       return Int2CharOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public CharCollection values() {
/* 1022 */     if (this.values == null)
/* 1023 */       this.values = (CharCollection)new AbstractCharCollection()
/*      */         {
/*      */           public CharIterator iterator() {
/* 1026 */             return new Int2CharOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1030 */             return Int2CharOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(char v) {
/* 1034 */             return Int2CharOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1038 */             Int2CharOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(IntConsumer consumer)
/*      */           {
/* 1043 */             if (Int2CharOpenCustomHashMap.this.containsNullKey)
/* 1044 */               consumer.accept(Int2CharOpenCustomHashMap.this.value[Int2CharOpenCustomHashMap.this.n]); 
/* 1045 */             for (int pos = Int2CharOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1046 */               if (Int2CharOpenCustomHashMap.this.key[pos] != 0)
/* 1047 */                 consumer.accept(Int2CharOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1050 */     return this.values;
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
/* 1067 */     return trim(this.size);
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
/* 1091 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1092 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1093 */       return true; 
/*      */     try {
/* 1095 */       rehash(l);
/* 1096 */     } catch (OutOfMemoryError cantDoIt) {
/* 1097 */       return false;
/*      */     } 
/* 1099 */     return true;
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
/* 1115 */     int[] key = this.key;
/* 1116 */     char[] value = this.value;
/* 1117 */     int mask = newN - 1;
/* 1118 */     int[] newKey = new int[newN + 1];
/* 1119 */     char[] newValue = new char[newN + 1];
/* 1120 */     int i = this.n;
/* 1121 */     for (int j = realSize(); j-- != 0; ) {
/* 1122 */       while (key[--i] == 0); int pos;
/* 1123 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != 0)
/* 1124 */         while (newKey[pos = pos + 1 & mask] != 0); 
/* 1125 */       newKey[pos] = key[i];
/* 1126 */       newValue[pos] = value[i];
/*      */     } 
/* 1128 */     newValue[newN] = value[this.n];
/* 1129 */     this.n = newN;
/* 1130 */     this.mask = mask;
/* 1131 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1132 */     this.key = newKey;
/* 1133 */     this.value = newValue;
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
/*      */   public Int2CharOpenCustomHashMap clone() {
/*      */     Int2CharOpenCustomHashMap c;
/*      */     try {
/* 1150 */       c = (Int2CharOpenCustomHashMap)super.clone();
/* 1151 */     } catch (CloneNotSupportedException cantHappen) {
/* 1152 */       throw new InternalError();
/*      */     } 
/* 1154 */     c.keys = null;
/* 1155 */     c.values = null;
/* 1156 */     c.entries = null;
/* 1157 */     c.containsNullKey = this.containsNullKey;
/* 1158 */     c.key = (int[])this.key.clone();
/* 1159 */     c.value = (char[])this.value.clone();
/* 1160 */     c.strategy = this.strategy;
/* 1161 */     return c;
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
/* 1174 */     int h = 0;
/* 1175 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1176 */       while (this.key[i] == 0)
/* 1177 */         i++; 
/* 1178 */       t = this.strategy.hashCode(this.key[i]);
/* 1179 */       t ^= this.value[i];
/* 1180 */       h += t;
/* 1181 */       i++;
/*      */     } 
/*      */     
/* 1184 */     if (this.containsNullKey)
/* 1185 */       h += this.value[this.n]; 
/* 1186 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1189 */     int[] key = this.key;
/* 1190 */     char[] value = this.value;
/* 1191 */     MapIterator i = new MapIterator();
/* 1192 */     s.defaultWriteObject();
/* 1193 */     for (int j = this.size; j-- != 0; ) {
/* 1194 */       int e = i.nextEntry();
/* 1195 */       s.writeInt(key[e]);
/* 1196 */       s.writeChar(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1201 */     s.defaultReadObject();
/* 1202 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1203 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1204 */     this.mask = this.n - 1;
/* 1205 */     int[] key = this.key = new int[this.n + 1];
/* 1206 */     char[] value = this.value = new char[this.n + 1];
/*      */ 
/*      */     
/* 1209 */     for (int i = this.size; i-- != 0; ) {
/* 1210 */       int pos, k = s.readInt();
/* 1211 */       char v = s.readChar();
/* 1212 */       if (this.strategy.equals(k, 0)) {
/* 1213 */         pos = this.n;
/* 1214 */         this.containsNullKey = true;
/*      */       } else {
/* 1216 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1217 */         while (key[pos] != 0)
/* 1218 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1220 */       key[pos] = k;
/* 1221 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\Int2CharOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */