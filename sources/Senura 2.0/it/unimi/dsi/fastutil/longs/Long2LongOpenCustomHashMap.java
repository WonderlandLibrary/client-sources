/*      */ package it.unimi.dsi.fastutil.longs;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
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
/*      */ import java.util.function.LongConsumer;
/*      */ import java.util.function.LongFunction;
/*      */ import java.util.function.LongUnaryOperator;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Long2LongOpenCustomHashMap
/*      */   extends AbstractLong2LongMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient long[] key;
/*      */   protected transient long[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected LongHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Long2LongMap.FastEntrySet entries;
/*      */   protected transient LongSet keys;
/*      */   protected transient LongCollection values;
/*      */   
/*      */   public Long2LongOpenCustomHashMap(int expected, float f, LongHash.Strategy strategy) {
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
/*  113 */     this.value = new long[this.n + 1];
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
/*      */   public Long2LongOpenCustomHashMap(int expected, LongHash.Strategy strategy) {
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
/*      */   public Long2LongOpenCustomHashMap(LongHash.Strategy strategy) {
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
/*      */   public Long2LongOpenCustomHashMap(Map<? extends Long, ? extends Long> m, float f, LongHash.Strategy strategy) {
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
/*      */   public Long2LongOpenCustomHashMap(Map<? extends Long, ? extends Long> m, LongHash.Strategy strategy) {
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
/*      */   public Long2LongOpenCustomHashMap(Long2LongMap m, float f, LongHash.Strategy strategy) {
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
/*      */   public Long2LongOpenCustomHashMap(Long2LongMap m, LongHash.Strategy strategy) {
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
/*      */   public Long2LongOpenCustomHashMap(long[] k, long[] v, float f, LongHash.Strategy strategy) {
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
/*      */   public Long2LongOpenCustomHashMap(long[] k, long[] v, LongHash.Strategy strategy) {
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
/*      */   private long removeEntry(int pos) {
/*  257 */     long oldValue = this.value[pos];
/*  258 */     this.size--;
/*  259 */     shiftKeys(pos);
/*  260 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  261 */       rehash(this.n / 2); 
/*  262 */     return oldValue;
/*      */   }
/*      */   private long removeNullEntry() {
/*  265 */     this.containsNullKey = false;
/*  266 */     long oldValue = this.value[this.n];
/*  267 */     this.size--;
/*  268 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  269 */       rehash(this.n / 2); 
/*  270 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Long, ? extends Long> m) {
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
/*      */   private void insert(int pos, long k, long v) {
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
/*      */   public long put(long k, long v) {
/*  313 */     int pos = find(k);
/*  314 */     if (pos < 0) {
/*  315 */       insert(-pos - 1, k, v);
/*  316 */       return this.defRetValue;
/*      */     } 
/*  318 */     long oldValue = this.value[pos];
/*  319 */     this.value[pos] = v;
/*  320 */     return oldValue;
/*      */   }
/*      */   private long addToValue(int pos, long incr) {
/*  323 */     long oldValue = this.value[pos];
/*  324 */     this.value[pos] = oldValue + incr;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long addTo(long k, long incr) {
/*      */     int pos;
/*  345 */     if (this.strategy.equals(k, 0L)) {
/*  346 */       if (this.containsNullKey)
/*  347 */         return addToValue(this.n, incr); 
/*  348 */       pos = this.n;
/*  349 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  352 */       long[] key = this.key;
/*      */       long curr;
/*  354 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != 0L) {
/*  355 */         if (this.strategy.equals(curr, k))
/*  356 */           return addToValue(pos, incr); 
/*  357 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0L) {
/*  358 */           if (this.strategy.equals(curr, k))
/*  359 */             return addToValue(pos, incr); 
/*      */         } 
/*      */       } 
/*  362 */     }  this.key[pos] = k;
/*  363 */     this.value[pos] = this.defRetValue + incr;
/*  364 */     if (this.size++ >= this.maxFill) {
/*  365 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  368 */     return this.defRetValue;
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
/*  381 */     long[] key = this.key; while (true) {
/*      */       long curr; int last;
/*  383 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  385 */         if ((curr = key[pos]) == 0L) {
/*  386 */           key[last] = 0L;
/*      */           return;
/*      */         } 
/*  389 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  390 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  392 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  394 */       key[last] = curr;
/*  395 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public long remove(long k) {
/*  401 */     if (this.strategy.equals(k, 0L)) {
/*  402 */       if (this.containsNullKey)
/*  403 */         return removeNullEntry(); 
/*  404 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  407 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  410 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  411 */       return this.defRetValue; 
/*  412 */     if (this.strategy.equals(k, curr))
/*  413 */       return removeEntry(pos); 
/*      */     while (true) {
/*  415 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  416 */         return this.defRetValue; 
/*  417 */       if (this.strategy.equals(k, curr)) {
/*  418 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public long get(long k) {
/*  424 */     if (this.strategy.equals(k, 0L)) {
/*  425 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  427 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  430 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  431 */       return this.defRetValue; 
/*  432 */     if (this.strategy.equals(k, curr)) {
/*  433 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  436 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  437 */         return this.defRetValue; 
/*  438 */       if (this.strategy.equals(k, curr)) {
/*  439 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(long k) {
/*  445 */     if (this.strategy.equals(k, 0L)) {
/*  446 */       return this.containsNullKey;
/*      */     }
/*  448 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  451 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  452 */       return false; 
/*  453 */     if (this.strategy.equals(k, curr)) {
/*  454 */       return true;
/*      */     }
/*      */     while (true) {
/*  457 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  458 */         return false; 
/*  459 */       if (this.strategy.equals(k, curr))
/*  460 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(long v) {
/*  465 */     long[] value = this.value;
/*  466 */     long[] key = this.key;
/*  467 */     if (this.containsNullKey && value[this.n] == v)
/*  468 */       return true; 
/*  469 */     for (int i = this.n; i-- != 0;) {
/*  470 */       if (key[i] != 0L && value[i] == v)
/*  471 */         return true; 
/*  472 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long getOrDefault(long k, long defaultValue) {
/*  478 */     if (this.strategy.equals(k, 0L)) {
/*  479 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  481 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  484 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  485 */       return defaultValue; 
/*  486 */     if (this.strategy.equals(k, curr)) {
/*  487 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  490 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  491 */         return defaultValue; 
/*  492 */       if (this.strategy.equals(k, curr)) {
/*  493 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public long putIfAbsent(long k, long v) {
/*  499 */     int pos = find(k);
/*  500 */     if (pos >= 0)
/*  501 */       return this.value[pos]; 
/*  502 */     insert(-pos - 1, k, v);
/*  503 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(long k, long v) {
/*  509 */     if (this.strategy.equals(k, 0L)) {
/*  510 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  511 */         removeNullEntry();
/*  512 */         return true;
/*      */       } 
/*  514 */       return false;
/*      */     } 
/*      */     
/*  517 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  520 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  521 */       return false; 
/*  522 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  523 */       removeEntry(pos);
/*  524 */       return true;
/*      */     } 
/*      */     while (true) {
/*  527 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  528 */         return false; 
/*  529 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  530 */         removeEntry(pos);
/*  531 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(long k, long oldValue, long v) {
/*  538 */     int pos = find(k);
/*  539 */     if (pos < 0 || oldValue != this.value[pos])
/*  540 */       return false; 
/*  541 */     this.value[pos] = v;
/*  542 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public long replace(long k, long v) {
/*  547 */     int pos = find(k);
/*  548 */     if (pos < 0)
/*  549 */       return this.defRetValue; 
/*  550 */     long oldValue = this.value[pos];
/*  551 */     this.value[pos] = v;
/*  552 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public long computeIfAbsent(long k, LongUnaryOperator mappingFunction) {
/*  557 */     Objects.requireNonNull(mappingFunction);
/*  558 */     int pos = find(k);
/*  559 */     if (pos >= 0)
/*  560 */       return this.value[pos]; 
/*  561 */     long newValue = mappingFunction.applyAsLong(k);
/*  562 */     insert(-pos - 1, k, newValue);
/*  563 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeIfAbsentNullable(long k, LongFunction<? extends Long> mappingFunction) {
/*  569 */     Objects.requireNonNull(mappingFunction);
/*  570 */     int pos = find(k);
/*  571 */     if (pos >= 0)
/*  572 */       return this.value[pos]; 
/*  573 */     Long newValue = mappingFunction.apply(k);
/*  574 */     if (newValue == null)
/*  575 */       return this.defRetValue; 
/*  576 */     long v = newValue.longValue();
/*  577 */     insert(-pos - 1, k, v);
/*  578 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeIfPresent(long k, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/*  584 */     Objects.requireNonNull(remappingFunction);
/*  585 */     int pos = find(k);
/*  586 */     if (pos < 0)
/*  587 */       return this.defRetValue; 
/*  588 */     Long newValue = remappingFunction.apply(Long.valueOf(k), Long.valueOf(this.value[pos]));
/*  589 */     if (newValue == null) {
/*  590 */       if (this.strategy.equals(k, 0L)) {
/*  591 */         removeNullEntry();
/*      */       } else {
/*  593 */         removeEntry(pos);
/*  594 */       }  return this.defRetValue;
/*      */     } 
/*  596 */     this.value[pos] = newValue.longValue(); return newValue.longValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long compute(long k, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/*  602 */     Objects.requireNonNull(remappingFunction);
/*  603 */     int pos = find(k);
/*  604 */     Long newValue = remappingFunction.apply(Long.valueOf(k), (pos >= 0) ? Long.valueOf(this.value[pos]) : null);
/*  605 */     if (newValue == null) {
/*  606 */       if (pos >= 0)
/*  607 */         if (this.strategy.equals(k, 0L)) {
/*  608 */           removeNullEntry();
/*      */         } else {
/*  610 */           removeEntry(pos);
/*      */         }  
/*  612 */       return this.defRetValue;
/*      */     } 
/*  614 */     long newVal = newValue.longValue();
/*  615 */     if (pos < 0) {
/*  616 */       insert(-pos - 1, k, newVal);
/*  617 */       return newVal;
/*      */     } 
/*  619 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long merge(long k, long v, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/*  625 */     Objects.requireNonNull(remappingFunction);
/*  626 */     int pos = find(k);
/*  627 */     if (pos < 0) {
/*  628 */       insert(-pos - 1, k, v);
/*  629 */       return v;
/*      */     } 
/*  631 */     Long newValue = remappingFunction.apply(Long.valueOf(this.value[pos]), Long.valueOf(v));
/*  632 */     if (newValue == null) {
/*  633 */       if (this.strategy.equals(k, 0L)) {
/*  634 */         removeNullEntry();
/*      */       } else {
/*  636 */         removeEntry(pos);
/*  637 */       }  return this.defRetValue;
/*      */     } 
/*  639 */     this.value[pos] = newValue.longValue(); return newValue.longValue();
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
/*  650 */     if (this.size == 0)
/*      */       return; 
/*  652 */     this.size = 0;
/*  653 */     this.containsNullKey = false;
/*  654 */     Arrays.fill(this.key, 0L);
/*      */   }
/*      */   
/*      */   public int size() {
/*  658 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  662 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Long2LongMap.Entry, Map.Entry<Long, Long>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  674 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public long getLongKey() {
/*  680 */       return Long2LongOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public long getLongValue() {
/*  684 */       return Long2LongOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public long setValue(long v) {
/*  688 */       long oldValue = Long2LongOpenCustomHashMap.this.value[this.index];
/*  689 */       Long2LongOpenCustomHashMap.this.value[this.index] = v;
/*  690 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long getKey() {
/*  700 */       return Long.valueOf(Long2LongOpenCustomHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long getValue() {
/*  710 */       return Long.valueOf(Long2LongOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long setValue(Long v) {
/*  720 */       return Long.valueOf(setValue(v.longValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  725 */       if (!(o instanceof Map.Entry))
/*  726 */         return false; 
/*  727 */       Map.Entry<Long, Long> e = (Map.Entry<Long, Long>)o;
/*  728 */       return (Long2LongOpenCustomHashMap.this.strategy.equals(Long2LongOpenCustomHashMap.this.key[this.index], ((Long)e.getKey()).longValue()) && Long2LongOpenCustomHashMap.this.value[this.index] == ((Long)e
/*  729 */         .getValue()).longValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  733 */       return Long2LongOpenCustomHashMap.this.strategy.hashCode(Long2LongOpenCustomHashMap.this.key[this.index]) ^ HashCommon.long2int(Long2LongOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  737 */       return Long2LongOpenCustomHashMap.this.key[this.index] + "=>" + Long2LongOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  747 */     int pos = Long2LongOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  754 */     int last = -1;
/*      */     
/*  756 */     int c = Long2LongOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  760 */     boolean mustReturnNullKey = Long2LongOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     LongArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  767 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  770 */       if (!hasNext())
/*  771 */         throw new NoSuchElementException(); 
/*  772 */       this.c--;
/*  773 */       if (this.mustReturnNullKey) {
/*  774 */         this.mustReturnNullKey = false;
/*  775 */         return this.last = Long2LongOpenCustomHashMap.this.n;
/*      */       } 
/*  777 */       long[] key = Long2LongOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  779 */         if (--this.pos < 0) {
/*      */           
/*  781 */           this.last = Integer.MIN_VALUE;
/*  782 */           long k = this.wrapped.getLong(-this.pos - 1);
/*  783 */           int p = HashCommon.mix(Long2LongOpenCustomHashMap.this.strategy.hashCode(k)) & Long2LongOpenCustomHashMap.this.mask;
/*  784 */           while (!Long2LongOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  785 */             p = p + 1 & Long2LongOpenCustomHashMap.this.mask; 
/*  786 */           return p;
/*      */         } 
/*  788 */         if (key[this.pos] != 0L) {
/*  789 */           return this.last = this.pos;
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
/*  803 */       long[] key = Long2LongOpenCustomHashMap.this.key; while (true) {
/*      */         long curr; int last;
/*  805 */         pos = (last = pos) + 1 & Long2LongOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  807 */           if ((curr = key[pos]) == 0L) {
/*  808 */             key[last] = 0L;
/*      */             return;
/*      */           } 
/*  811 */           int slot = HashCommon.mix(Long2LongOpenCustomHashMap.this.strategy.hashCode(curr)) & Long2LongOpenCustomHashMap.this.mask;
/*  812 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  814 */           pos = pos + 1 & Long2LongOpenCustomHashMap.this.mask;
/*      */         } 
/*  816 */         if (pos < last) {
/*  817 */           if (this.wrapped == null)
/*  818 */             this.wrapped = new LongArrayList(2); 
/*  819 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  821 */         key[last] = curr;
/*  822 */         Long2LongOpenCustomHashMap.this.value[last] = Long2LongOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  826 */       if (this.last == -1)
/*  827 */         throw new IllegalStateException(); 
/*  828 */       if (this.last == Long2LongOpenCustomHashMap.this.n) {
/*  829 */         Long2LongOpenCustomHashMap.this.containsNullKey = false;
/*  830 */       } else if (this.pos >= 0) {
/*  831 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  834 */         Long2LongOpenCustomHashMap.this.remove(this.wrapped.getLong(-this.pos - 1));
/*  835 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  838 */       Long2LongOpenCustomHashMap.this.size--;
/*  839 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  844 */       int i = n;
/*  845 */       while (i-- != 0 && hasNext())
/*  846 */         nextEntry(); 
/*  847 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Long2LongMap.Entry> { private Long2LongOpenCustomHashMap.MapEntry entry;
/*      */     
/*      */     public Long2LongOpenCustomHashMap.MapEntry next() {
/*  854 */       return this.entry = new Long2LongOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  858 */       super.remove();
/*  859 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Long2LongMap.Entry> { private FastEntryIterator() {
/*  863 */       this.entry = new Long2LongOpenCustomHashMap.MapEntry();
/*      */     } private final Long2LongOpenCustomHashMap.MapEntry entry;
/*      */     public Long2LongOpenCustomHashMap.MapEntry next() {
/*  866 */       this.entry.index = nextEntry();
/*  867 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Long2LongMap.Entry> implements Long2LongMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Long2LongMap.Entry> iterator() {
/*  873 */       return new Long2LongOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Long2LongMap.Entry> fastIterator() {
/*  877 */       return new Long2LongOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  882 */       if (!(o instanceof Map.Entry))
/*  883 */         return false; 
/*  884 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  885 */       if (e.getKey() == null || !(e.getKey() instanceof Long))
/*  886 */         return false; 
/*  887 */       if (e.getValue() == null || !(e.getValue() instanceof Long))
/*  888 */         return false; 
/*  889 */       long k = ((Long)e.getKey()).longValue();
/*  890 */       long v = ((Long)e.getValue()).longValue();
/*  891 */       if (Long2LongOpenCustomHashMap.this.strategy.equals(k, 0L)) {
/*  892 */         return (Long2LongOpenCustomHashMap.this.containsNullKey && Long2LongOpenCustomHashMap.this.value[Long2LongOpenCustomHashMap.this.n] == v);
/*      */       }
/*  894 */       long[] key = Long2LongOpenCustomHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/*  897 */       if ((curr = key[pos = HashCommon.mix(Long2LongOpenCustomHashMap.this.strategy.hashCode(k)) & Long2LongOpenCustomHashMap.this.mask]) == 0L)
/*  898 */         return false; 
/*  899 */       if (Long2LongOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  900 */         return (Long2LongOpenCustomHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  903 */         if ((curr = key[pos = pos + 1 & Long2LongOpenCustomHashMap.this.mask]) == 0L)
/*  904 */           return false; 
/*  905 */         if (Long2LongOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  906 */           return (Long2LongOpenCustomHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  912 */       if (!(o instanceof Map.Entry))
/*  913 */         return false; 
/*  914 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  915 */       if (e.getKey() == null || !(e.getKey() instanceof Long))
/*  916 */         return false; 
/*  917 */       if (e.getValue() == null || !(e.getValue() instanceof Long))
/*  918 */         return false; 
/*  919 */       long k = ((Long)e.getKey()).longValue();
/*  920 */       long v = ((Long)e.getValue()).longValue();
/*  921 */       if (Long2LongOpenCustomHashMap.this.strategy.equals(k, 0L)) {
/*  922 */         if (Long2LongOpenCustomHashMap.this.containsNullKey && Long2LongOpenCustomHashMap.this.value[Long2LongOpenCustomHashMap.this.n] == v) {
/*  923 */           Long2LongOpenCustomHashMap.this.removeNullEntry();
/*  924 */           return true;
/*      */         } 
/*  926 */         return false;
/*      */       } 
/*      */       
/*  929 */       long[] key = Long2LongOpenCustomHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/*  932 */       if ((curr = key[pos = HashCommon.mix(Long2LongOpenCustomHashMap.this.strategy.hashCode(k)) & Long2LongOpenCustomHashMap.this.mask]) == 0L)
/*  933 */         return false; 
/*  934 */       if (Long2LongOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  935 */         if (Long2LongOpenCustomHashMap.this.value[pos] == v) {
/*  936 */           Long2LongOpenCustomHashMap.this.removeEntry(pos);
/*  937 */           return true;
/*      */         } 
/*  939 */         return false;
/*      */       } 
/*      */       while (true) {
/*  942 */         if ((curr = key[pos = pos + 1 & Long2LongOpenCustomHashMap.this.mask]) == 0L)
/*  943 */           return false; 
/*  944 */         if (Long2LongOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  945 */           Long2LongOpenCustomHashMap.this.value[pos] == v) {
/*  946 */           Long2LongOpenCustomHashMap.this.removeEntry(pos);
/*  947 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  954 */       return Long2LongOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  958 */       Long2LongOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Long2LongMap.Entry> consumer) {
/*  963 */       if (Long2LongOpenCustomHashMap.this.containsNullKey)
/*  964 */         consumer.accept(new AbstractLong2LongMap.BasicEntry(Long2LongOpenCustomHashMap.this.key[Long2LongOpenCustomHashMap.this.n], Long2LongOpenCustomHashMap.this.value[Long2LongOpenCustomHashMap.this.n])); 
/*  965 */       for (int pos = Long2LongOpenCustomHashMap.this.n; pos-- != 0;) {
/*  966 */         if (Long2LongOpenCustomHashMap.this.key[pos] != 0L)
/*  967 */           consumer.accept(new AbstractLong2LongMap.BasicEntry(Long2LongOpenCustomHashMap.this.key[pos], Long2LongOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Long2LongMap.Entry> consumer) {
/*  972 */       AbstractLong2LongMap.BasicEntry entry = new AbstractLong2LongMap.BasicEntry();
/*  973 */       if (Long2LongOpenCustomHashMap.this.containsNullKey) {
/*  974 */         entry.key = Long2LongOpenCustomHashMap.this.key[Long2LongOpenCustomHashMap.this.n];
/*  975 */         entry.value = Long2LongOpenCustomHashMap.this.value[Long2LongOpenCustomHashMap.this.n];
/*  976 */         consumer.accept(entry);
/*      */       } 
/*  978 */       for (int pos = Long2LongOpenCustomHashMap.this.n; pos-- != 0;) {
/*  979 */         if (Long2LongOpenCustomHashMap.this.key[pos] != 0L) {
/*  980 */           entry.key = Long2LongOpenCustomHashMap.this.key[pos];
/*  981 */           entry.value = Long2LongOpenCustomHashMap.this.value[pos];
/*  982 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Long2LongMap.FastEntrySet long2LongEntrySet() {
/*  988 */     if (this.entries == null)
/*  989 */       this.entries = new MapEntrySet(); 
/*  990 */     return this.entries;
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
/* 1007 */       return Long2LongOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractLongSet { private KeySet() {}
/*      */     
/*      */     public LongIterator iterator() {
/* 1013 */       return new Long2LongOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(LongConsumer consumer) {
/* 1018 */       if (Long2LongOpenCustomHashMap.this.containsNullKey)
/* 1019 */         consumer.accept(Long2LongOpenCustomHashMap.this.key[Long2LongOpenCustomHashMap.this.n]); 
/* 1020 */       for (int pos = Long2LongOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1021 */         long k = Long2LongOpenCustomHashMap.this.key[pos];
/* 1022 */         if (k != 0L)
/* 1023 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1028 */       return Long2LongOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(long k) {
/* 1032 */       return Long2LongOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(long k) {
/* 1036 */       int oldSize = Long2LongOpenCustomHashMap.this.size;
/* 1037 */       Long2LongOpenCustomHashMap.this.remove(k);
/* 1038 */       return (Long2LongOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1042 */       Long2LongOpenCustomHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public LongSet keySet() {
/* 1047 */     if (this.keys == null)
/* 1048 */       this.keys = new KeySet(); 
/* 1049 */     return this.keys;
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
/*      */     implements LongIterator
/*      */   {
/*      */     public long nextLong() {
/* 1066 */       return Long2LongOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public LongCollection values() {
/* 1071 */     if (this.values == null)
/* 1072 */       this.values = new AbstractLongCollection()
/*      */         {
/*      */           public LongIterator iterator() {
/* 1075 */             return new Long2LongOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1079 */             return Long2LongOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(long v) {
/* 1083 */             return Long2LongOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1087 */             Long2LongOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(LongConsumer consumer)
/*      */           {
/* 1092 */             if (Long2LongOpenCustomHashMap.this.containsNullKey)
/* 1093 */               consumer.accept(Long2LongOpenCustomHashMap.this.value[Long2LongOpenCustomHashMap.this.n]); 
/* 1094 */             for (int pos = Long2LongOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1095 */               if (Long2LongOpenCustomHashMap.this.key[pos] != 0L)
/* 1096 */                 consumer.accept(Long2LongOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1099 */     return this.values;
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
/* 1116 */     return trim(this.size);
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
/* 1140 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1141 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1142 */       return true; 
/*      */     try {
/* 1144 */       rehash(l);
/* 1145 */     } catch (OutOfMemoryError cantDoIt) {
/* 1146 */       return false;
/*      */     } 
/* 1148 */     return true;
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
/* 1164 */     long[] key = this.key;
/* 1165 */     long[] value = this.value;
/* 1166 */     int mask = newN - 1;
/* 1167 */     long[] newKey = new long[newN + 1];
/* 1168 */     long[] newValue = new long[newN + 1];
/* 1169 */     int i = this.n;
/* 1170 */     for (int j = realSize(); j-- != 0; ) {
/* 1171 */       while (key[--i] == 0L); int pos;
/* 1172 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != 0L)
/* 1173 */         while (newKey[pos = pos + 1 & mask] != 0L); 
/* 1174 */       newKey[pos] = key[i];
/* 1175 */       newValue[pos] = value[i];
/*      */     } 
/* 1177 */     newValue[newN] = value[this.n];
/* 1178 */     this.n = newN;
/* 1179 */     this.mask = mask;
/* 1180 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1181 */     this.key = newKey;
/* 1182 */     this.value = newValue;
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
/*      */   public Long2LongOpenCustomHashMap clone() {
/*      */     Long2LongOpenCustomHashMap c;
/*      */     try {
/* 1199 */       c = (Long2LongOpenCustomHashMap)super.clone();
/* 1200 */     } catch (CloneNotSupportedException cantHappen) {
/* 1201 */       throw new InternalError();
/*      */     } 
/* 1203 */     c.keys = null;
/* 1204 */     c.values = null;
/* 1205 */     c.entries = null;
/* 1206 */     c.containsNullKey = this.containsNullKey;
/* 1207 */     c.key = (long[])this.key.clone();
/* 1208 */     c.value = (long[])this.value.clone();
/* 1209 */     c.strategy = this.strategy;
/* 1210 */     return c;
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
/* 1223 */     int h = 0;
/* 1224 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1225 */       while (this.key[i] == 0L)
/* 1226 */         i++; 
/* 1227 */       t = this.strategy.hashCode(this.key[i]);
/* 1228 */       t ^= HashCommon.long2int(this.value[i]);
/* 1229 */       h += t;
/* 1230 */       i++;
/*      */     } 
/*      */     
/* 1233 */     if (this.containsNullKey)
/* 1234 */       h += HashCommon.long2int(this.value[this.n]); 
/* 1235 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1238 */     long[] key = this.key;
/* 1239 */     long[] value = this.value;
/* 1240 */     MapIterator i = new MapIterator();
/* 1241 */     s.defaultWriteObject();
/* 1242 */     for (int j = this.size; j-- != 0; ) {
/* 1243 */       int e = i.nextEntry();
/* 1244 */       s.writeLong(key[e]);
/* 1245 */       s.writeLong(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1250 */     s.defaultReadObject();
/* 1251 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1252 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1253 */     this.mask = this.n - 1;
/* 1254 */     long[] key = this.key = new long[this.n + 1];
/* 1255 */     long[] value = this.value = new long[this.n + 1];
/*      */ 
/*      */     
/* 1258 */     for (int i = this.size; i-- != 0; ) {
/* 1259 */       int pos; long k = s.readLong();
/* 1260 */       long v = s.readLong();
/* 1261 */       if (this.strategy.equals(k, 0L)) {
/* 1262 */         pos = this.n;
/* 1263 */         this.containsNullKey = true;
/*      */       } else {
/* 1265 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1266 */         while (key[pos] != 0L)
/* 1267 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1269 */       key[pos] = k;
/* 1270 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\Long2LongOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */