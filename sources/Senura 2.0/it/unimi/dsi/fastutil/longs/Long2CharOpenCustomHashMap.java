/*      */ package it.unimi.dsi.fastutil.longs;
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
/*      */ public class Long2CharOpenCustomHashMap
/*      */   extends AbstractLong2CharMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient long[] key;
/*      */   protected transient char[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected LongHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Long2CharMap.FastEntrySet entries;
/*      */   protected transient LongSet keys;
/*      */   protected transient CharCollection values;
/*      */   
/*      */   public Long2CharOpenCustomHashMap(int expected, float f, LongHash.Strategy strategy) {
/*  103 */     this.strategy = strategy;
/*  104 */     if (f <= 0.0F || f > 1.0F)
/*  105 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  106 */     if (expected < 0)
/*  107 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  108 */     this.f = f;
/*  109 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  110 */     this.mask = this.n - 1;
/*  111 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  112 */     this.key = new long[this.n + 1];
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
/*      */   
/*      */   public Long2CharOpenCustomHashMap(int expected, LongHash.Strategy strategy) {
/*  125 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2CharOpenCustomHashMap(LongHash.Strategy strategy) {
/*  136 */     this(16, 0.75F, strategy);
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
/*      */   public Long2CharOpenCustomHashMap(Map<? extends Long, ? extends Character> m, float f, LongHash.Strategy strategy) {
/*  150 */     this(m.size(), f, strategy);
/*  151 */     putAll(m);
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
/*      */   public Long2CharOpenCustomHashMap(Map<? extends Long, ? extends Character> m, LongHash.Strategy strategy) {
/*  164 */     this(m, 0.75F, strategy);
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
/*      */   public Long2CharOpenCustomHashMap(Long2CharMap m, float f, LongHash.Strategy strategy) {
/*  178 */     this(m.size(), f, strategy);
/*  179 */     putAll(m);
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
/*      */   public Long2CharOpenCustomHashMap(Long2CharMap m, LongHash.Strategy strategy) {
/*  192 */     this(m, 0.75F, strategy);
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
/*      */   public Long2CharOpenCustomHashMap(long[] k, char[] v, float f, LongHash.Strategy strategy) {
/*  210 */     this(k.length, f, strategy);
/*  211 */     if (k.length != v.length) {
/*  212 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  214 */     for (int i = 0; i < k.length; i++) {
/*  215 */       put(k[i], v[i]);
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
/*      */   public Long2CharOpenCustomHashMap(long[] k, char[] v, LongHash.Strategy strategy) {
/*  232 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongHash.Strategy strategy() {
/*  240 */     return this.strategy;
/*      */   }
/*      */   private int realSize() {
/*  243 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  246 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  247 */     if (needed > this.n)
/*  248 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  251 */     int needed = (int)Math.min(1073741824L, 
/*  252 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  253 */     if (needed > this.n)
/*  254 */       rehash(needed); 
/*      */   }
/*      */   private char removeEntry(int pos) {
/*  257 */     char oldValue = this.value[pos];
/*  258 */     this.size--;
/*  259 */     shiftKeys(pos);
/*  260 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  261 */       rehash(this.n / 2); 
/*  262 */     return oldValue;
/*      */   }
/*      */   private char removeNullEntry() {
/*  265 */     this.containsNullKey = false;
/*  266 */     char oldValue = this.value[this.n];
/*  267 */     this.size--;
/*  268 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  269 */       rehash(this.n / 2); 
/*  270 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Long, ? extends Character> m) {
/*  274 */     if (this.f <= 0.5D) {
/*  275 */       ensureCapacity(m.size());
/*      */     } else {
/*  277 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  279 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(long k) {
/*  283 */     if (this.strategy.equals(k, 0L)) {
/*  284 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  286 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  289 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  290 */       return -(pos + 1); 
/*  291 */     if (this.strategy.equals(k, curr)) {
/*  292 */       return pos;
/*      */     }
/*      */     while (true) {
/*  295 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  296 */         return -(pos + 1); 
/*  297 */       if (this.strategy.equals(k, curr))
/*  298 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, long k, char v) {
/*  302 */     if (pos == this.n)
/*  303 */       this.containsNullKey = true; 
/*  304 */     this.key[pos] = k;
/*  305 */     this.value[pos] = v;
/*  306 */     if (this.size++ >= this.maxFill) {
/*  307 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public char put(long k, char v) {
/*  313 */     int pos = find(k);
/*  314 */     if (pos < 0) {
/*  315 */       insert(-pos - 1, k, v);
/*  316 */       return this.defRetValue;
/*      */     } 
/*  318 */     char oldValue = this.value[pos];
/*  319 */     this.value[pos] = v;
/*  320 */     return oldValue;
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
/*  333 */     long[] key = this.key; while (true) {
/*      */       long curr; int last;
/*  335 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  337 */         if ((curr = key[pos]) == 0L) {
/*  338 */           key[last] = 0L;
/*      */           return;
/*      */         } 
/*  341 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  342 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  344 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  346 */       key[last] = curr;
/*  347 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public char remove(long k) {
/*  353 */     if (this.strategy.equals(k, 0L)) {
/*  354 */       if (this.containsNullKey)
/*  355 */         return removeNullEntry(); 
/*  356 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  359 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  362 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  363 */       return this.defRetValue; 
/*  364 */     if (this.strategy.equals(k, curr))
/*  365 */       return removeEntry(pos); 
/*      */     while (true) {
/*  367 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  368 */         return this.defRetValue; 
/*  369 */       if (this.strategy.equals(k, curr)) {
/*  370 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public char get(long k) {
/*  376 */     if (this.strategy.equals(k, 0L)) {
/*  377 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  379 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  382 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  383 */       return this.defRetValue; 
/*  384 */     if (this.strategy.equals(k, curr)) {
/*  385 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  388 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  389 */         return this.defRetValue; 
/*  390 */       if (this.strategy.equals(k, curr)) {
/*  391 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(long k) {
/*  397 */     if (this.strategy.equals(k, 0L)) {
/*  398 */       return this.containsNullKey;
/*      */     }
/*  400 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  403 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  404 */       return false; 
/*  405 */     if (this.strategy.equals(k, curr)) {
/*  406 */       return true;
/*      */     }
/*      */     while (true) {
/*  409 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  410 */         return false; 
/*  411 */       if (this.strategy.equals(k, curr))
/*  412 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(char v) {
/*  417 */     char[] value = this.value;
/*  418 */     long[] key = this.key;
/*  419 */     if (this.containsNullKey && value[this.n] == v)
/*  420 */       return true; 
/*  421 */     for (int i = this.n; i-- != 0;) {
/*  422 */       if (key[i] != 0L && value[i] == v)
/*  423 */         return true; 
/*  424 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char getOrDefault(long k, char defaultValue) {
/*  430 */     if (this.strategy.equals(k, 0L)) {
/*  431 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  433 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  436 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  437 */       return defaultValue; 
/*  438 */     if (this.strategy.equals(k, curr)) {
/*  439 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  442 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  443 */         return defaultValue; 
/*  444 */       if (this.strategy.equals(k, curr)) {
/*  445 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public char putIfAbsent(long k, char v) {
/*  451 */     int pos = find(k);
/*  452 */     if (pos >= 0)
/*  453 */       return this.value[pos]; 
/*  454 */     insert(-pos - 1, k, v);
/*  455 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(long k, char v) {
/*  461 */     if (this.strategy.equals(k, 0L)) {
/*  462 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  463 */         removeNullEntry();
/*  464 */         return true;
/*      */       } 
/*  466 */       return false;
/*      */     } 
/*      */     
/*  469 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  472 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  473 */       return false; 
/*  474 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  475 */       removeEntry(pos);
/*  476 */       return true;
/*      */     } 
/*      */     while (true) {
/*  479 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  480 */         return false; 
/*  481 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  482 */         removeEntry(pos);
/*  483 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(long k, char oldValue, char v) {
/*  490 */     int pos = find(k);
/*  491 */     if (pos < 0 || oldValue != this.value[pos])
/*  492 */       return false; 
/*  493 */     this.value[pos] = v;
/*  494 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public char replace(long k, char v) {
/*  499 */     int pos = find(k);
/*  500 */     if (pos < 0)
/*  501 */       return this.defRetValue; 
/*  502 */     char oldValue = this.value[pos];
/*  503 */     this.value[pos] = v;
/*  504 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public char computeIfAbsent(long k, LongToIntFunction mappingFunction) {
/*  509 */     Objects.requireNonNull(mappingFunction);
/*  510 */     int pos = find(k);
/*  511 */     if (pos >= 0)
/*  512 */       return this.value[pos]; 
/*  513 */     char newValue = SafeMath.safeIntToChar(mappingFunction.applyAsInt(k));
/*  514 */     insert(-pos - 1, k, newValue);
/*  515 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char computeIfAbsentNullable(long k, LongFunction<? extends Character> mappingFunction) {
/*  521 */     Objects.requireNonNull(mappingFunction);
/*  522 */     int pos = find(k);
/*  523 */     if (pos >= 0)
/*  524 */       return this.value[pos]; 
/*  525 */     Character newValue = mappingFunction.apply(k);
/*  526 */     if (newValue == null)
/*  527 */       return this.defRetValue; 
/*  528 */     char v = newValue.charValue();
/*  529 */     insert(-pos - 1, k, v);
/*  530 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char computeIfPresent(long k, BiFunction<? super Long, ? super Character, ? extends Character> remappingFunction) {
/*  536 */     Objects.requireNonNull(remappingFunction);
/*  537 */     int pos = find(k);
/*  538 */     if (pos < 0)
/*  539 */       return this.defRetValue; 
/*  540 */     Character newValue = remappingFunction.apply(Long.valueOf(k), Character.valueOf(this.value[pos]));
/*  541 */     if (newValue == null) {
/*  542 */       if (this.strategy.equals(k, 0L)) {
/*  543 */         removeNullEntry();
/*      */       } else {
/*  545 */         removeEntry(pos);
/*  546 */       }  return this.defRetValue;
/*      */     } 
/*  548 */     this.value[pos] = newValue.charValue(); return newValue.charValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char compute(long k, BiFunction<? super Long, ? super Character, ? extends Character> remappingFunction) {
/*  554 */     Objects.requireNonNull(remappingFunction);
/*  555 */     int pos = find(k);
/*  556 */     Character newValue = remappingFunction.apply(Long.valueOf(k), 
/*  557 */         (pos >= 0) ? Character.valueOf(this.value[pos]) : null);
/*  558 */     if (newValue == null) {
/*  559 */       if (pos >= 0)
/*  560 */         if (this.strategy.equals(k, 0L)) {
/*  561 */           removeNullEntry();
/*      */         } else {
/*  563 */           removeEntry(pos);
/*      */         }  
/*  565 */       return this.defRetValue;
/*      */     } 
/*  567 */     char newVal = newValue.charValue();
/*  568 */     if (pos < 0) {
/*  569 */       insert(-pos - 1, k, newVal);
/*  570 */       return newVal;
/*      */     } 
/*  572 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char merge(long k, char v, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
/*  578 */     Objects.requireNonNull(remappingFunction);
/*  579 */     int pos = find(k);
/*  580 */     if (pos < 0) {
/*  581 */       insert(-pos - 1, k, v);
/*  582 */       return v;
/*      */     } 
/*  584 */     Character newValue = remappingFunction.apply(Character.valueOf(this.value[pos]), Character.valueOf(v));
/*  585 */     if (newValue == null) {
/*  586 */       if (this.strategy.equals(k, 0L)) {
/*  587 */         removeNullEntry();
/*      */       } else {
/*  589 */         removeEntry(pos);
/*  590 */       }  return this.defRetValue;
/*      */     } 
/*  592 */     this.value[pos] = newValue.charValue(); return newValue.charValue();
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
/*  603 */     if (this.size == 0)
/*      */       return; 
/*  605 */     this.size = 0;
/*  606 */     this.containsNullKey = false;
/*  607 */     Arrays.fill(this.key, 0L);
/*      */   }
/*      */   
/*      */   public int size() {
/*  611 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  615 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Long2CharMap.Entry, Map.Entry<Long, Character>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  627 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public long getLongKey() {
/*  633 */       return Long2CharOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public char getCharValue() {
/*  637 */       return Long2CharOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public char setValue(char v) {
/*  641 */       char oldValue = Long2CharOpenCustomHashMap.this.value[this.index];
/*  642 */       Long2CharOpenCustomHashMap.this.value[this.index] = v;
/*  643 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long getKey() {
/*  653 */       return Long.valueOf(Long2CharOpenCustomHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character getValue() {
/*  663 */       return Character.valueOf(Long2CharOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character setValue(Character v) {
/*  673 */       return Character.valueOf(setValue(v.charValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  678 */       if (!(o instanceof Map.Entry))
/*  679 */         return false; 
/*  680 */       Map.Entry<Long, Character> e = (Map.Entry<Long, Character>)o;
/*  681 */       return (Long2CharOpenCustomHashMap.this.strategy.equals(Long2CharOpenCustomHashMap.this.key[this.index], ((Long)e.getKey()).longValue()) && Long2CharOpenCustomHashMap.this.value[this.index] == ((Character)e
/*  682 */         .getValue()).charValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  686 */       return Long2CharOpenCustomHashMap.this.strategy.hashCode(Long2CharOpenCustomHashMap.this.key[this.index]) ^ Long2CharOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/*  690 */       return Long2CharOpenCustomHashMap.this.key[this.index] + "=>" + Long2CharOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  700 */     int pos = Long2CharOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  707 */     int last = -1;
/*      */     
/*  709 */     int c = Long2CharOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  713 */     boolean mustReturnNullKey = Long2CharOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     LongArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  720 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  723 */       if (!hasNext())
/*  724 */         throw new NoSuchElementException(); 
/*  725 */       this.c--;
/*  726 */       if (this.mustReturnNullKey) {
/*  727 */         this.mustReturnNullKey = false;
/*  728 */         return this.last = Long2CharOpenCustomHashMap.this.n;
/*      */       } 
/*  730 */       long[] key = Long2CharOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  732 */         if (--this.pos < 0) {
/*      */           
/*  734 */           this.last = Integer.MIN_VALUE;
/*  735 */           long k = this.wrapped.getLong(-this.pos - 1);
/*  736 */           int p = HashCommon.mix(Long2CharOpenCustomHashMap.this.strategy.hashCode(k)) & Long2CharOpenCustomHashMap.this.mask;
/*  737 */           while (!Long2CharOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  738 */             p = p + 1 & Long2CharOpenCustomHashMap.this.mask; 
/*  739 */           return p;
/*      */         } 
/*  741 */         if (key[this.pos] != 0L) {
/*  742 */           return this.last = this.pos;
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
/*  756 */       long[] key = Long2CharOpenCustomHashMap.this.key; while (true) {
/*      */         long curr; int last;
/*  758 */         pos = (last = pos) + 1 & Long2CharOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  760 */           if ((curr = key[pos]) == 0L) {
/*  761 */             key[last] = 0L;
/*      */             return;
/*      */           } 
/*  764 */           int slot = HashCommon.mix(Long2CharOpenCustomHashMap.this.strategy.hashCode(curr)) & Long2CharOpenCustomHashMap.this.mask;
/*  765 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  767 */           pos = pos + 1 & Long2CharOpenCustomHashMap.this.mask;
/*      */         } 
/*  769 */         if (pos < last) {
/*  770 */           if (this.wrapped == null)
/*  771 */             this.wrapped = new LongArrayList(2); 
/*  772 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  774 */         key[last] = curr;
/*  775 */         Long2CharOpenCustomHashMap.this.value[last] = Long2CharOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  779 */       if (this.last == -1)
/*  780 */         throw new IllegalStateException(); 
/*  781 */       if (this.last == Long2CharOpenCustomHashMap.this.n) {
/*  782 */         Long2CharOpenCustomHashMap.this.containsNullKey = false;
/*  783 */       } else if (this.pos >= 0) {
/*  784 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  787 */         Long2CharOpenCustomHashMap.this.remove(this.wrapped.getLong(-this.pos - 1));
/*  788 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  791 */       Long2CharOpenCustomHashMap.this.size--;
/*  792 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  797 */       int i = n;
/*  798 */       while (i-- != 0 && hasNext())
/*  799 */         nextEntry(); 
/*  800 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Long2CharMap.Entry> { private Long2CharOpenCustomHashMap.MapEntry entry;
/*      */     
/*      */     public Long2CharOpenCustomHashMap.MapEntry next() {
/*  807 */       return this.entry = new Long2CharOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  811 */       super.remove();
/*  812 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Long2CharMap.Entry> { private FastEntryIterator() {
/*  816 */       this.entry = new Long2CharOpenCustomHashMap.MapEntry();
/*      */     } private final Long2CharOpenCustomHashMap.MapEntry entry;
/*      */     public Long2CharOpenCustomHashMap.MapEntry next() {
/*  819 */       this.entry.index = nextEntry();
/*  820 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Long2CharMap.Entry> implements Long2CharMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Long2CharMap.Entry> iterator() {
/*  826 */       return new Long2CharOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Long2CharMap.Entry> fastIterator() {
/*  830 */       return new Long2CharOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  835 */       if (!(o instanceof Map.Entry))
/*  836 */         return false; 
/*  837 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  838 */       if (e.getKey() == null || !(e.getKey() instanceof Long))
/*  839 */         return false; 
/*  840 */       if (e.getValue() == null || !(e.getValue() instanceof Character))
/*  841 */         return false; 
/*  842 */       long k = ((Long)e.getKey()).longValue();
/*  843 */       char v = ((Character)e.getValue()).charValue();
/*  844 */       if (Long2CharOpenCustomHashMap.this.strategy.equals(k, 0L)) {
/*  845 */         return (Long2CharOpenCustomHashMap.this.containsNullKey && Long2CharOpenCustomHashMap.this.value[Long2CharOpenCustomHashMap.this.n] == v);
/*      */       }
/*  847 */       long[] key = Long2CharOpenCustomHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/*  850 */       if ((curr = key[pos = HashCommon.mix(Long2CharOpenCustomHashMap.this.strategy.hashCode(k)) & Long2CharOpenCustomHashMap.this.mask]) == 0L)
/*  851 */         return false; 
/*  852 */       if (Long2CharOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  853 */         return (Long2CharOpenCustomHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  856 */         if ((curr = key[pos = pos + 1 & Long2CharOpenCustomHashMap.this.mask]) == 0L)
/*  857 */           return false; 
/*  858 */         if (Long2CharOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  859 */           return (Long2CharOpenCustomHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  865 */       if (!(o instanceof Map.Entry))
/*  866 */         return false; 
/*  867 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  868 */       if (e.getKey() == null || !(e.getKey() instanceof Long))
/*  869 */         return false; 
/*  870 */       if (e.getValue() == null || !(e.getValue() instanceof Character))
/*  871 */         return false; 
/*  872 */       long k = ((Long)e.getKey()).longValue();
/*  873 */       char v = ((Character)e.getValue()).charValue();
/*  874 */       if (Long2CharOpenCustomHashMap.this.strategy.equals(k, 0L)) {
/*  875 */         if (Long2CharOpenCustomHashMap.this.containsNullKey && Long2CharOpenCustomHashMap.this.value[Long2CharOpenCustomHashMap.this.n] == v) {
/*  876 */           Long2CharOpenCustomHashMap.this.removeNullEntry();
/*  877 */           return true;
/*      */         } 
/*  879 */         return false;
/*      */       } 
/*      */       
/*  882 */       long[] key = Long2CharOpenCustomHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/*  885 */       if ((curr = key[pos = HashCommon.mix(Long2CharOpenCustomHashMap.this.strategy.hashCode(k)) & Long2CharOpenCustomHashMap.this.mask]) == 0L)
/*  886 */         return false; 
/*  887 */       if (Long2CharOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  888 */         if (Long2CharOpenCustomHashMap.this.value[pos] == v) {
/*  889 */           Long2CharOpenCustomHashMap.this.removeEntry(pos);
/*  890 */           return true;
/*      */         } 
/*  892 */         return false;
/*      */       } 
/*      */       while (true) {
/*  895 */         if ((curr = key[pos = pos + 1 & Long2CharOpenCustomHashMap.this.mask]) == 0L)
/*  896 */           return false; 
/*  897 */         if (Long2CharOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  898 */           Long2CharOpenCustomHashMap.this.value[pos] == v) {
/*  899 */           Long2CharOpenCustomHashMap.this.removeEntry(pos);
/*  900 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  907 */       return Long2CharOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  911 */       Long2CharOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Long2CharMap.Entry> consumer) {
/*  916 */       if (Long2CharOpenCustomHashMap.this.containsNullKey)
/*  917 */         consumer.accept(new AbstractLong2CharMap.BasicEntry(Long2CharOpenCustomHashMap.this.key[Long2CharOpenCustomHashMap.this.n], Long2CharOpenCustomHashMap.this.value[Long2CharOpenCustomHashMap.this.n])); 
/*  918 */       for (int pos = Long2CharOpenCustomHashMap.this.n; pos-- != 0;) {
/*  919 */         if (Long2CharOpenCustomHashMap.this.key[pos] != 0L)
/*  920 */           consumer.accept(new AbstractLong2CharMap.BasicEntry(Long2CharOpenCustomHashMap.this.key[pos], Long2CharOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Long2CharMap.Entry> consumer) {
/*  925 */       AbstractLong2CharMap.BasicEntry entry = new AbstractLong2CharMap.BasicEntry();
/*  926 */       if (Long2CharOpenCustomHashMap.this.containsNullKey) {
/*  927 */         entry.key = Long2CharOpenCustomHashMap.this.key[Long2CharOpenCustomHashMap.this.n];
/*  928 */         entry.value = Long2CharOpenCustomHashMap.this.value[Long2CharOpenCustomHashMap.this.n];
/*  929 */         consumer.accept(entry);
/*      */       } 
/*  931 */       for (int pos = Long2CharOpenCustomHashMap.this.n; pos-- != 0;) {
/*  932 */         if (Long2CharOpenCustomHashMap.this.key[pos] != 0L) {
/*  933 */           entry.key = Long2CharOpenCustomHashMap.this.key[pos];
/*  934 */           entry.value = Long2CharOpenCustomHashMap.this.value[pos];
/*  935 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Long2CharMap.FastEntrySet long2CharEntrySet() {
/*  941 */     if (this.entries == null)
/*  942 */       this.entries = new MapEntrySet(); 
/*  943 */     return this.entries;
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
/*  960 */       return Long2CharOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractLongSet { private KeySet() {}
/*      */     
/*      */     public LongIterator iterator() {
/*  966 */       return new Long2CharOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(LongConsumer consumer) {
/*  971 */       if (Long2CharOpenCustomHashMap.this.containsNullKey)
/*  972 */         consumer.accept(Long2CharOpenCustomHashMap.this.key[Long2CharOpenCustomHashMap.this.n]); 
/*  973 */       for (int pos = Long2CharOpenCustomHashMap.this.n; pos-- != 0; ) {
/*  974 */         long k = Long2CharOpenCustomHashMap.this.key[pos];
/*  975 */         if (k != 0L)
/*  976 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  981 */       return Long2CharOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(long k) {
/*  985 */       return Long2CharOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(long k) {
/*  989 */       int oldSize = Long2CharOpenCustomHashMap.this.size;
/*  990 */       Long2CharOpenCustomHashMap.this.remove(k);
/*  991 */       return (Long2CharOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  995 */       Long2CharOpenCustomHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public LongSet keySet() {
/* 1000 */     if (this.keys == null)
/* 1001 */       this.keys = new KeySet(); 
/* 1002 */     return this.keys;
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
/* 1019 */       return Long2CharOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public CharCollection values() {
/* 1024 */     if (this.values == null)
/* 1025 */       this.values = (CharCollection)new AbstractCharCollection()
/*      */         {
/*      */           public CharIterator iterator() {
/* 1028 */             return new Long2CharOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1032 */             return Long2CharOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(char v) {
/* 1036 */             return Long2CharOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1040 */             Long2CharOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(IntConsumer consumer)
/*      */           {
/* 1045 */             if (Long2CharOpenCustomHashMap.this.containsNullKey)
/* 1046 */               consumer.accept(Long2CharOpenCustomHashMap.this.value[Long2CharOpenCustomHashMap.this.n]); 
/* 1047 */             for (int pos = Long2CharOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1048 */               if (Long2CharOpenCustomHashMap.this.key[pos] != 0L)
/* 1049 */                 consumer.accept(Long2CharOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1052 */     return this.values;
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
/* 1069 */     return trim(this.size);
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
/* 1093 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1094 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1095 */       return true; 
/*      */     try {
/* 1097 */       rehash(l);
/* 1098 */     } catch (OutOfMemoryError cantDoIt) {
/* 1099 */       return false;
/*      */     } 
/* 1101 */     return true;
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
/* 1117 */     long[] key = this.key;
/* 1118 */     char[] value = this.value;
/* 1119 */     int mask = newN - 1;
/* 1120 */     long[] newKey = new long[newN + 1];
/* 1121 */     char[] newValue = new char[newN + 1];
/* 1122 */     int i = this.n;
/* 1123 */     for (int j = realSize(); j-- != 0; ) {
/* 1124 */       while (key[--i] == 0L); int pos;
/* 1125 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != 0L)
/* 1126 */         while (newKey[pos = pos + 1 & mask] != 0L); 
/* 1127 */       newKey[pos] = key[i];
/* 1128 */       newValue[pos] = value[i];
/*      */     } 
/* 1130 */     newValue[newN] = value[this.n];
/* 1131 */     this.n = newN;
/* 1132 */     this.mask = mask;
/* 1133 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1134 */     this.key = newKey;
/* 1135 */     this.value = newValue;
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
/*      */   public Long2CharOpenCustomHashMap clone() {
/*      */     Long2CharOpenCustomHashMap c;
/*      */     try {
/* 1152 */       c = (Long2CharOpenCustomHashMap)super.clone();
/* 1153 */     } catch (CloneNotSupportedException cantHappen) {
/* 1154 */       throw new InternalError();
/*      */     } 
/* 1156 */     c.keys = null;
/* 1157 */     c.values = null;
/* 1158 */     c.entries = null;
/* 1159 */     c.containsNullKey = this.containsNullKey;
/* 1160 */     c.key = (long[])this.key.clone();
/* 1161 */     c.value = (char[])this.value.clone();
/* 1162 */     c.strategy = this.strategy;
/* 1163 */     return c;
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
/* 1176 */     int h = 0;
/* 1177 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1178 */       while (this.key[i] == 0L)
/* 1179 */         i++; 
/* 1180 */       t = this.strategy.hashCode(this.key[i]);
/* 1181 */       t ^= this.value[i];
/* 1182 */       h += t;
/* 1183 */       i++;
/*      */     } 
/*      */     
/* 1186 */     if (this.containsNullKey)
/* 1187 */       h += this.value[this.n]; 
/* 1188 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1191 */     long[] key = this.key;
/* 1192 */     char[] value = this.value;
/* 1193 */     MapIterator i = new MapIterator();
/* 1194 */     s.defaultWriteObject();
/* 1195 */     for (int j = this.size; j-- != 0; ) {
/* 1196 */       int e = i.nextEntry();
/* 1197 */       s.writeLong(key[e]);
/* 1198 */       s.writeChar(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1203 */     s.defaultReadObject();
/* 1204 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1205 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1206 */     this.mask = this.n - 1;
/* 1207 */     long[] key = this.key = new long[this.n + 1];
/* 1208 */     char[] value = this.value = new char[this.n + 1];
/*      */ 
/*      */     
/* 1211 */     for (int i = this.size; i-- != 0; ) {
/* 1212 */       int pos; long k = s.readLong();
/* 1213 */       char v = s.readChar();
/* 1214 */       if (this.strategy.equals(k, 0L)) {
/* 1215 */         pos = this.n;
/* 1216 */         this.containsNullKey = true;
/*      */       } else {
/* 1218 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1219 */         while (key[pos] != 0L)
/* 1220 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1222 */       key[pos] = k;
/* 1223 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\Long2CharOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */