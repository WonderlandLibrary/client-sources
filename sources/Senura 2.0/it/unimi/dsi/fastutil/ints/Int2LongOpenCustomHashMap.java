/*      */ package it.unimi.dsi.fastutil.ints;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
/*      */ import it.unimi.dsi.fastutil.longs.LongCollection;
/*      */ import it.unimi.dsi.fastutil.longs.LongIterator;
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
/*      */ import java.util.function.IntToLongFunction;
/*      */ import java.util.function.LongConsumer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Int2LongOpenCustomHashMap
/*      */   extends AbstractInt2LongMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient int[] key;
/*      */   protected transient long[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected IntHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Int2LongMap.FastEntrySet entries;
/*      */   protected transient IntSet keys;
/*      */   protected transient LongCollection values;
/*      */   
/*      */   public Int2LongOpenCustomHashMap(int expected, float f, IntHash.Strategy strategy) {
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
/*      */   public Int2LongOpenCustomHashMap(int expected, IntHash.Strategy strategy) {
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
/*      */   public Int2LongOpenCustomHashMap(IntHash.Strategy strategy) {
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
/*      */   public Int2LongOpenCustomHashMap(Map<? extends Integer, ? extends Long> m, float f, IntHash.Strategy strategy) {
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
/*      */   public Int2LongOpenCustomHashMap(Map<? extends Integer, ? extends Long> m, IntHash.Strategy strategy) {
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
/*      */   public Int2LongOpenCustomHashMap(Int2LongMap m, float f, IntHash.Strategy strategy) {
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
/*      */   public Int2LongOpenCustomHashMap(Int2LongMap m, IntHash.Strategy strategy) {
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
/*      */   public Int2LongOpenCustomHashMap(int[] k, long[] v, float f, IntHash.Strategy strategy) {
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
/*      */   public Int2LongOpenCustomHashMap(int[] k, long[] v, IntHash.Strategy strategy) {
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
/*      */   private long removeEntry(int pos) {
/*  255 */     long oldValue = this.value[pos];
/*  256 */     this.size--;
/*  257 */     shiftKeys(pos);
/*  258 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  259 */       rehash(this.n / 2); 
/*  260 */     return oldValue;
/*      */   }
/*      */   private long removeNullEntry() {
/*  263 */     this.containsNullKey = false;
/*  264 */     long oldValue = this.value[this.n];
/*  265 */     this.size--;
/*  266 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  267 */       rehash(this.n / 2); 
/*  268 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Integer, ? extends Long> m) {
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
/*      */   private void insert(int pos, int k, long v) {
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
/*      */   public long put(int k, long v) {
/*  311 */     int pos = find(k);
/*  312 */     if (pos < 0) {
/*  313 */       insert(-pos - 1, k, v);
/*  314 */       return this.defRetValue;
/*      */     } 
/*  316 */     long oldValue = this.value[pos];
/*  317 */     this.value[pos] = v;
/*  318 */     return oldValue;
/*      */   }
/*      */   private long addToValue(int pos, long incr) {
/*  321 */     long oldValue = this.value[pos];
/*  322 */     this.value[pos] = oldValue + incr;
/*  323 */     return oldValue;
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
/*      */   public long addTo(int k, long incr) {
/*      */     int pos;
/*  343 */     if (this.strategy.equals(k, 0)) {
/*  344 */       if (this.containsNullKey)
/*  345 */         return addToValue(this.n, incr); 
/*  346 */       pos = this.n;
/*  347 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  350 */       int[] key = this.key;
/*      */       int curr;
/*  352 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != 0) {
/*  353 */         if (this.strategy.equals(curr, k))
/*  354 */           return addToValue(pos, incr); 
/*  355 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
/*  356 */           if (this.strategy.equals(curr, k))
/*  357 */             return addToValue(pos, incr); 
/*      */         } 
/*      */       } 
/*  360 */     }  this.key[pos] = k;
/*  361 */     this.value[pos] = this.defRetValue + incr;
/*  362 */     if (this.size++ >= this.maxFill) {
/*  363 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  366 */     return this.defRetValue;
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
/*  379 */     int[] key = this.key; while (true) {
/*      */       int curr, last;
/*  381 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  383 */         if ((curr = key[pos]) == 0) {
/*  384 */           key[last] = 0;
/*      */           return;
/*      */         } 
/*  387 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  388 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  390 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  392 */       key[last] = curr;
/*  393 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public long remove(int k) {
/*  399 */     if (this.strategy.equals(k, 0)) {
/*  400 */       if (this.containsNullKey)
/*  401 */         return removeNullEntry(); 
/*  402 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  405 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  408 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  409 */       return this.defRetValue; 
/*  410 */     if (this.strategy.equals(k, curr))
/*  411 */       return removeEntry(pos); 
/*      */     while (true) {
/*  413 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  414 */         return this.defRetValue; 
/*  415 */       if (this.strategy.equals(k, curr)) {
/*  416 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public long get(int k) {
/*  422 */     if (this.strategy.equals(k, 0)) {
/*  423 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  425 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  428 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  429 */       return this.defRetValue; 
/*  430 */     if (this.strategy.equals(k, curr)) {
/*  431 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  434 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  435 */         return this.defRetValue; 
/*  436 */       if (this.strategy.equals(k, curr)) {
/*  437 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(int k) {
/*  443 */     if (this.strategy.equals(k, 0)) {
/*  444 */       return this.containsNullKey;
/*      */     }
/*  446 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  449 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  450 */       return false; 
/*  451 */     if (this.strategy.equals(k, curr)) {
/*  452 */       return true;
/*      */     }
/*      */     while (true) {
/*  455 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  456 */         return false; 
/*  457 */       if (this.strategy.equals(k, curr))
/*  458 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(long v) {
/*  463 */     long[] value = this.value;
/*  464 */     int[] key = this.key;
/*  465 */     if (this.containsNullKey && value[this.n] == v)
/*  466 */       return true; 
/*  467 */     for (int i = this.n; i-- != 0;) {
/*  468 */       if (key[i] != 0 && value[i] == v)
/*  469 */         return true; 
/*  470 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long getOrDefault(int k, long defaultValue) {
/*  476 */     if (this.strategy.equals(k, 0)) {
/*  477 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  479 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  482 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  483 */       return defaultValue; 
/*  484 */     if (this.strategy.equals(k, curr)) {
/*  485 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  488 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  489 */         return defaultValue; 
/*  490 */       if (this.strategy.equals(k, curr)) {
/*  491 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public long putIfAbsent(int k, long v) {
/*  497 */     int pos = find(k);
/*  498 */     if (pos >= 0)
/*  499 */       return this.value[pos]; 
/*  500 */     insert(-pos - 1, k, v);
/*  501 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(int k, long v) {
/*  507 */     if (this.strategy.equals(k, 0)) {
/*  508 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  509 */         removeNullEntry();
/*  510 */         return true;
/*      */       } 
/*  512 */       return false;
/*      */     } 
/*      */     
/*  515 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  518 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  519 */       return false; 
/*  520 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  521 */       removeEntry(pos);
/*  522 */       return true;
/*      */     } 
/*      */     while (true) {
/*  525 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  526 */         return false; 
/*  527 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  528 */         removeEntry(pos);
/*  529 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(int k, long oldValue, long v) {
/*  536 */     int pos = find(k);
/*  537 */     if (pos < 0 || oldValue != this.value[pos])
/*  538 */       return false; 
/*  539 */     this.value[pos] = v;
/*  540 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public long replace(int k, long v) {
/*  545 */     int pos = find(k);
/*  546 */     if (pos < 0)
/*  547 */       return this.defRetValue; 
/*  548 */     long oldValue = this.value[pos];
/*  549 */     this.value[pos] = v;
/*  550 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public long computeIfAbsent(int k, IntToLongFunction mappingFunction) {
/*  555 */     Objects.requireNonNull(mappingFunction);
/*  556 */     int pos = find(k);
/*  557 */     if (pos >= 0)
/*  558 */       return this.value[pos]; 
/*  559 */     long newValue = mappingFunction.applyAsLong(k);
/*  560 */     insert(-pos - 1, k, newValue);
/*  561 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeIfAbsentNullable(int k, IntFunction<? extends Long> mappingFunction) {
/*  567 */     Objects.requireNonNull(mappingFunction);
/*  568 */     int pos = find(k);
/*  569 */     if (pos >= 0)
/*  570 */       return this.value[pos]; 
/*  571 */     Long newValue = mappingFunction.apply(k);
/*  572 */     if (newValue == null)
/*  573 */       return this.defRetValue; 
/*  574 */     long v = newValue.longValue();
/*  575 */     insert(-pos - 1, k, v);
/*  576 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeIfPresent(int k, BiFunction<? super Integer, ? super Long, ? extends Long> remappingFunction) {
/*  582 */     Objects.requireNonNull(remappingFunction);
/*  583 */     int pos = find(k);
/*  584 */     if (pos < 0)
/*  585 */       return this.defRetValue; 
/*  586 */     Long newValue = remappingFunction.apply(Integer.valueOf(k), Long.valueOf(this.value[pos]));
/*  587 */     if (newValue == null) {
/*  588 */       if (this.strategy.equals(k, 0)) {
/*  589 */         removeNullEntry();
/*      */       } else {
/*  591 */         removeEntry(pos);
/*  592 */       }  return this.defRetValue;
/*      */     } 
/*  594 */     this.value[pos] = newValue.longValue(); return newValue.longValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long compute(int k, BiFunction<? super Integer, ? super Long, ? extends Long> remappingFunction) {
/*  600 */     Objects.requireNonNull(remappingFunction);
/*  601 */     int pos = find(k);
/*  602 */     Long newValue = remappingFunction.apply(Integer.valueOf(k), (pos >= 0) ? Long.valueOf(this.value[pos]) : null);
/*  603 */     if (newValue == null) {
/*  604 */       if (pos >= 0)
/*  605 */         if (this.strategy.equals(k, 0)) {
/*  606 */           removeNullEntry();
/*      */         } else {
/*  608 */           removeEntry(pos);
/*      */         }  
/*  610 */       return this.defRetValue;
/*      */     } 
/*  612 */     long newVal = newValue.longValue();
/*  613 */     if (pos < 0) {
/*  614 */       insert(-pos - 1, k, newVal);
/*  615 */       return newVal;
/*      */     } 
/*  617 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long merge(int k, long v, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/*  623 */     Objects.requireNonNull(remappingFunction);
/*  624 */     int pos = find(k);
/*  625 */     if (pos < 0) {
/*  626 */       insert(-pos - 1, k, v);
/*  627 */       return v;
/*      */     } 
/*  629 */     Long newValue = remappingFunction.apply(Long.valueOf(this.value[pos]), Long.valueOf(v));
/*  630 */     if (newValue == null) {
/*  631 */       if (this.strategy.equals(k, 0)) {
/*  632 */         removeNullEntry();
/*      */       } else {
/*  634 */         removeEntry(pos);
/*  635 */       }  return this.defRetValue;
/*      */     } 
/*  637 */     this.value[pos] = newValue.longValue(); return newValue.longValue();
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
/*  648 */     if (this.size == 0)
/*      */       return; 
/*  650 */     this.size = 0;
/*  651 */     this.containsNullKey = false;
/*  652 */     Arrays.fill(this.key, 0);
/*      */   }
/*      */   
/*      */   public int size() {
/*  656 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  660 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Int2LongMap.Entry, Map.Entry<Integer, Long>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  672 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public int getIntKey() {
/*  678 */       return Int2LongOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public long getLongValue() {
/*  682 */       return Int2LongOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public long setValue(long v) {
/*  686 */       long oldValue = Int2LongOpenCustomHashMap.this.value[this.index];
/*  687 */       Int2LongOpenCustomHashMap.this.value[this.index] = v;
/*  688 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer getKey() {
/*  698 */       return Integer.valueOf(Int2LongOpenCustomHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long getValue() {
/*  708 */       return Long.valueOf(Int2LongOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long setValue(Long v) {
/*  718 */       return Long.valueOf(setValue(v.longValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  723 */       if (!(o instanceof Map.Entry))
/*  724 */         return false; 
/*  725 */       Map.Entry<Integer, Long> e = (Map.Entry<Integer, Long>)o;
/*  726 */       return (Int2LongOpenCustomHashMap.this.strategy.equals(Int2LongOpenCustomHashMap.this.key[this.index], ((Integer)e.getKey()).intValue()) && Int2LongOpenCustomHashMap.this.value[this.index] == ((Long)e
/*  727 */         .getValue()).longValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  731 */       return Int2LongOpenCustomHashMap.this.strategy.hashCode(Int2LongOpenCustomHashMap.this.key[this.index]) ^ HashCommon.long2int(Int2LongOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  735 */       return Int2LongOpenCustomHashMap.this.key[this.index] + "=>" + Int2LongOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  745 */     int pos = Int2LongOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  752 */     int last = -1;
/*      */     
/*  754 */     int c = Int2LongOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  758 */     boolean mustReturnNullKey = Int2LongOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     IntArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  765 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  768 */       if (!hasNext())
/*  769 */         throw new NoSuchElementException(); 
/*  770 */       this.c--;
/*  771 */       if (this.mustReturnNullKey) {
/*  772 */         this.mustReturnNullKey = false;
/*  773 */         return this.last = Int2LongOpenCustomHashMap.this.n;
/*      */       } 
/*  775 */       int[] key = Int2LongOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  777 */         if (--this.pos < 0) {
/*      */           
/*  779 */           this.last = Integer.MIN_VALUE;
/*  780 */           int k = this.wrapped.getInt(-this.pos - 1);
/*  781 */           int p = HashCommon.mix(Int2LongOpenCustomHashMap.this.strategy.hashCode(k)) & Int2LongOpenCustomHashMap.this.mask;
/*  782 */           while (!Int2LongOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  783 */             p = p + 1 & Int2LongOpenCustomHashMap.this.mask; 
/*  784 */           return p;
/*      */         } 
/*  786 */         if (key[this.pos] != 0) {
/*  787 */           return this.last = this.pos;
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
/*  801 */       int[] key = Int2LongOpenCustomHashMap.this.key; while (true) {
/*      */         int curr, last;
/*  803 */         pos = (last = pos) + 1 & Int2LongOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  805 */           if ((curr = key[pos]) == 0) {
/*  806 */             key[last] = 0;
/*      */             return;
/*      */           } 
/*  809 */           int slot = HashCommon.mix(Int2LongOpenCustomHashMap.this.strategy.hashCode(curr)) & Int2LongOpenCustomHashMap.this.mask;
/*  810 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  812 */           pos = pos + 1 & Int2LongOpenCustomHashMap.this.mask;
/*      */         } 
/*  814 */         if (pos < last) {
/*  815 */           if (this.wrapped == null)
/*  816 */             this.wrapped = new IntArrayList(2); 
/*  817 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  819 */         key[last] = curr;
/*  820 */         Int2LongOpenCustomHashMap.this.value[last] = Int2LongOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  824 */       if (this.last == -1)
/*  825 */         throw new IllegalStateException(); 
/*  826 */       if (this.last == Int2LongOpenCustomHashMap.this.n) {
/*  827 */         Int2LongOpenCustomHashMap.this.containsNullKey = false;
/*  828 */       } else if (this.pos >= 0) {
/*  829 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  832 */         Int2LongOpenCustomHashMap.this.remove(this.wrapped.getInt(-this.pos - 1));
/*  833 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  836 */       Int2LongOpenCustomHashMap.this.size--;
/*  837 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  842 */       int i = n;
/*  843 */       while (i-- != 0 && hasNext())
/*  844 */         nextEntry(); 
/*  845 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Int2LongMap.Entry> { private Int2LongOpenCustomHashMap.MapEntry entry;
/*      */     
/*      */     public Int2LongOpenCustomHashMap.MapEntry next() {
/*  852 */       return this.entry = new Int2LongOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  856 */       super.remove();
/*  857 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Int2LongMap.Entry> { private FastEntryIterator() {
/*  861 */       this.entry = new Int2LongOpenCustomHashMap.MapEntry();
/*      */     } private final Int2LongOpenCustomHashMap.MapEntry entry;
/*      */     public Int2LongOpenCustomHashMap.MapEntry next() {
/*  864 */       this.entry.index = nextEntry();
/*  865 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Int2LongMap.Entry> implements Int2LongMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Int2LongMap.Entry> iterator() {
/*  871 */       return new Int2LongOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Int2LongMap.Entry> fastIterator() {
/*  875 */       return new Int2LongOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  880 */       if (!(o instanceof Map.Entry))
/*  881 */         return false; 
/*  882 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  883 */       if (e.getKey() == null || !(e.getKey() instanceof Integer))
/*  884 */         return false; 
/*  885 */       if (e.getValue() == null || !(e.getValue() instanceof Long))
/*  886 */         return false; 
/*  887 */       int k = ((Integer)e.getKey()).intValue();
/*  888 */       long v = ((Long)e.getValue()).longValue();
/*  889 */       if (Int2LongOpenCustomHashMap.this.strategy.equals(k, 0)) {
/*  890 */         return (Int2LongOpenCustomHashMap.this.containsNullKey && Int2LongOpenCustomHashMap.this.value[Int2LongOpenCustomHashMap.this.n] == v);
/*      */       }
/*  892 */       int[] key = Int2LongOpenCustomHashMap.this.key;
/*      */       
/*      */       int curr, pos;
/*  895 */       if ((curr = key[pos = HashCommon.mix(Int2LongOpenCustomHashMap.this.strategy.hashCode(k)) & Int2LongOpenCustomHashMap.this.mask]) == 0)
/*  896 */         return false; 
/*  897 */       if (Int2LongOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  898 */         return (Int2LongOpenCustomHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  901 */         if ((curr = key[pos = pos + 1 & Int2LongOpenCustomHashMap.this.mask]) == 0)
/*  902 */           return false; 
/*  903 */         if (Int2LongOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  904 */           return (Int2LongOpenCustomHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  910 */       if (!(o instanceof Map.Entry))
/*  911 */         return false; 
/*  912 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  913 */       if (e.getKey() == null || !(e.getKey() instanceof Integer))
/*  914 */         return false; 
/*  915 */       if (e.getValue() == null || !(e.getValue() instanceof Long))
/*  916 */         return false; 
/*  917 */       int k = ((Integer)e.getKey()).intValue();
/*  918 */       long v = ((Long)e.getValue()).longValue();
/*  919 */       if (Int2LongOpenCustomHashMap.this.strategy.equals(k, 0)) {
/*  920 */         if (Int2LongOpenCustomHashMap.this.containsNullKey && Int2LongOpenCustomHashMap.this.value[Int2LongOpenCustomHashMap.this.n] == v) {
/*  921 */           Int2LongOpenCustomHashMap.this.removeNullEntry();
/*  922 */           return true;
/*      */         } 
/*  924 */         return false;
/*      */       } 
/*      */       
/*  927 */       int[] key = Int2LongOpenCustomHashMap.this.key;
/*      */       
/*      */       int curr, pos;
/*  930 */       if ((curr = key[pos = HashCommon.mix(Int2LongOpenCustomHashMap.this.strategy.hashCode(k)) & Int2LongOpenCustomHashMap.this.mask]) == 0)
/*  931 */         return false; 
/*  932 */       if (Int2LongOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  933 */         if (Int2LongOpenCustomHashMap.this.value[pos] == v) {
/*  934 */           Int2LongOpenCustomHashMap.this.removeEntry(pos);
/*  935 */           return true;
/*      */         } 
/*  937 */         return false;
/*      */       } 
/*      */       while (true) {
/*  940 */         if ((curr = key[pos = pos + 1 & Int2LongOpenCustomHashMap.this.mask]) == 0)
/*  941 */           return false; 
/*  942 */         if (Int2LongOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  943 */           Int2LongOpenCustomHashMap.this.value[pos] == v) {
/*  944 */           Int2LongOpenCustomHashMap.this.removeEntry(pos);
/*  945 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  952 */       return Int2LongOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  956 */       Int2LongOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Int2LongMap.Entry> consumer) {
/*  961 */       if (Int2LongOpenCustomHashMap.this.containsNullKey)
/*  962 */         consumer.accept(new AbstractInt2LongMap.BasicEntry(Int2LongOpenCustomHashMap.this.key[Int2LongOpenCustomHashMap.this.n], Int2LongOpenCustomHashMap.this.value[Int2LongOpenCustomHashMap.this.n])); 
/*  963 */       for (int pos = Int2LongOpenCustomHashMap.this.n; pos-- != 0;) {
/*  964 */         if (Int2LongOpenCustomHashMap.this.key[pos] != 0)
/*  965 */           consumer.accept(new AbstractInt2LongMap.BasicEntry(Int2LongOpenCustomHashMap.this.key[pos], Int2LongOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Int2LongMap.Entry> consumer) {
/*  970 */       AbstractInt2LongMap.BasicEntry entry = new AbstractInt2LongMap.BasicEntry();
/*  971 */       if (Int2LongOpenCustomHashMap.this.containsNullKey) {
/*  972 */         entry.key = Int2LongOpenCustomHashMap.this.key[Int2LongOpenCustomHashMap.this.n];
/*  973 */         entry.value = Int2LongOpenCustomHashMap.this.value[Int2LongOpenCustomHashMap.this.n];
/*  974 */         consumer.accept(entry);
/*      */       } 
/*  976 */       for (int pos = Int2LongOpenCustomHashMap.this.n; pos-- != 0;) {
/*  977 */         if (Int2LongOpenCustomHashMap.this.key[pos] != 0) {
/*  978 */           entry.key = Int2LongOpenCustomHashMap.this.key[pos];
/*  979 */           entry.value = Int2LongOpenCustomHashMap.this.value[pos];
/*  980 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Int2LongMap.FastEntrySet int2LongEntrySet() {
/*  986 */     if (this.entries == null)
/*  987 */       this.entries = new MapEntrySet(); 
/*  988 */     return this.entries;
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
/* 1005 */       return Int2LongOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractIntSet { private KeySet() {}
/*      */     
/*      */     public IntIterator iterator() {
/* 1011 */       return new Int2LongOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer consumer) {
/* 1016 */       if (Int2LongOpenCustomHashMap.this.containsNullKey)
/* 1017 */         consumer.accept(Int2LongOpenCustomHashMap.this.key[Int2LongOpenCustomHashMap.this.n]); 
/* 1018 */       for (int pos = Int2LongOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1019 */         int k = Int2LongOpenCustomHashMap.this.key[pos];
/* 1020 */         if (k != 0)
/* 1021 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1026 */       return Int2LongOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(int k) {
/* 1030 */       return Int2LongOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(int k) {
/* 1034 */       int oldSize = Int2LongOpenCustomHashMap.this.size;
/* 1035 */       Int2LongOpenCustomHashMap.this.remove(k);
/* 1036 */       return (Int2LongOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1040 */       Int2LongOpenCustomHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public IntSet keySet() {
/* 1045 */     if (this.keys == null)
/* 1046 */       this.keys = new KeySet(); 
/* 1047 */     return this.keys;
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
/* 1064 */       return Int2LongOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public LongCollection values() {
/* 1069 */     if (this.values == null)
/* 1070 */       this.values = (LongCollection)new AbstractLongCollection()
/*      */         {
/*      */           public LongIterator iterator() {
/* 1073 */             return new Int2LongOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1077 */             return Int2LongOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(long v) {
/* 1081 */             return Int2LongOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1085 */             Int2LongOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(LongConsumer consumer)
/*      */           {
/* 1090 */             if (Int2LongOpenCustomHashMap.this.containsNullKey)
/* 1091 */               consumer.accept(Int2LongOpenCustomHashMap.this.value[Int2LongOpenCustomHashMap.this.n]); 
/* 1092 */             for (int pos = Int2LongOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1093 */               if (Int2LongOpenCustomHashMap.this.key[pos] != 0)
/* 1094 */                 consumer.accept(Int2LongOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1097 */     return this.values;
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
/* 1114 */     return trim(this.size);
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
/* 1138 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1139 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1140 */       return true; 
/*      */     try {
/* 1142 */       rehash(l);
/* 1143 */     } catch (OutOfMemoryError cantDoIt) {
/* 1144 */       return false;
/*      */     } 
/* 1146 */     return true;
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
/* 1162 */     int[] key = this.key;
/* 1163 */     long[] value = this.value;
/* 1164 */     int mask = newN - 1;
/* 1165 */     int[] newKey = new int[newN + 1];
/* 1166 */     long[] newValue = new long[newN + 1];
/* 1167 */     int i = this.n;
/* 1168 */     for (int j = realSize(); j-- != 0; ) {
/* 1169 */       while (key[--i] == 0); int pos;
/* 1170 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != 0)
/* 1171 */         while (newKey[pos = pos + 1 & mask] != 0); 
/* 1172 */       newKey[pos] = key[i];
/* 1173 */       newValue[pos] = value[i];
/*      */     } 
/* 1175 */     newValue[newN] = value[this.n];
/* 1176 */     this.n = newN;
/* 1177 */     this.mask = mask;
/* 1178 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1179 */     this.key = newKey;
/* 1180 */     this.value = newValue;
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
/*      */   public Int2LongOpenCustomHashMap clone() {
/*      */     Int2LongOpenCustomHashMap c;
/*      */     try {
/* 1197 */       c = (Int2LongOpenCustomHashMap)super.clone();
/* 1198 */     } catch (CloneNotSupportedException cantHappen) {
/* 1199 */       throw new InternalError();
/*      */     } 
/* 1201 */     c.keys = null;
/* 1202 */     c.values = null;
/* 1203 */     c.entries = null;
/* 1204 */     c.containsNullKey = this.containsNullKey;
/* 1205 */     c.key = (int[])this.key.clone();
/* 1206 */     c.value = (long[])this.value.clone();
/* 1207 */     c.strategy = this.strategy;
/* 1208 */     return c;
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
/* 1221 */     int h = 0;
/* 1222 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1223 */       while (this.key[i] == 0)
/* 1224 */         i++; 
/* 1225 */       t = this.strategy.hashCode(this.key[i]);
/* 1226 */       t ^= HashCommon.long2int(this.value[i]);
/* 1227 */       h += t;
/* 1228 */       i++;
/*      */     } 
/*      */     
/* 1231 */     if (this.containsNullKey)
/* 1232 */       h += HashCommon.long2int(this.value[this.n]); 
/* 1233 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1236 */     int[] key = this.key;
/* 1237 */     long[] value = this.value;
/* 1238 */     MapIterator i = new MapIterator();
/* 1239 */     s.defaultWriteObject();
/* 1240 */     for (int j = this.size; j-- != 0; ) {
/* 1241 */       int e = i.nextEntry();
/* 1242 */       s.writeInt(key[e]);
/* 1243 */       s.writeLong(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1248 */     s.defaultReadObject();
/* 1249 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1250 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1251 */     this.mask = this.n - 1;
/* 1252 */     int[] key = this.key = new int[this.n + 1];
/* 1253 */     long[] value = this.value = new long[this.n + 1];
/*      */ 
/*      */     
/* 1256 */     for (int i = this.size; i-- != 0; ) {
/* 1257 */       int pos, k = s.readInt();
/* 1258 */       long v = s.readLong();
/* 1259 */       if (this.strategy.equals(k, 0)) {
/* 1260 */         pos = this.n;
/* 1261 */         this.containsNullKey = true;
/*      */       } else {
/* 1263 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1264 */         while (key[pos] != 0)
/* 1265 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1267 */       key[pos] = k;
/* 1268 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\Int2LongOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */