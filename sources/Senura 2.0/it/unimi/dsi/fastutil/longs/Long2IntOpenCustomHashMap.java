/*      */ package it.unimi.dsi.fastutil.longs;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
/*      */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*      */ import it.unimi.dsi.fastutil.ints.IntIterator;
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
/*      */ 
/*      */ public class Long2IntOpenCustomHashMap
/*      */   extends AbstractLong2IntMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient long[] key;
/*      */   protected transient int[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected LongHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Long2IntMap.FastEntrySet entries;
/*      */   protected transient LongSet keys;
/*      */   protected transient IntCollection values;
/*      */   
/*      */   public Long2IntOpenCustomHashMap(int expected, float f, LongHash.Strategy strategy) {
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
/*  113 */     this.value = new int[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2IntOpenCustomHashMap(int expected, LongHash.Strategy strategy) {
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
/*      */   public Long2IntOpenCustomHashMap(LongHash.Strategy strategy) {
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
/*      */   public Long2IntOpenCustomHashMap(Map<? extends Long, ? extends Integer> m, float f, LongHash.Strategy strategy) {
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
/*      */   public Long2IntOpenCustomHashMap(Map<? extends Long, ? extends Integer> m, LongHash.Strategy strategy) {
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
/*      */   public Long2IntOpenCustomHashMap(Long2IntMap m, float f, LongHash.Strategy strategy) {
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
/*      */   public Long2IntOpenCustomHashMap(Long2IntMap m, LongHash.Strategy strategy) {
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
/*      */   public Long2IntOpenCustomHashMap(long[] k, int[] v, float f, LongHash.Strategy strategy) {
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
/*      */   public Long2IntOpenCustomHashMap(long[] k, int[] v, LongHash.Strategy strategy) {
/*  231 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongHash.Strategy strategy() {
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
/*      */   private int removeEntry(int pos) {
/*  256 */     int oldValue = this.value[pos];
/*  257 */     this.size--;
/*  258 */     shiftKeys(pos);
/*  259 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  260 */       rehash(this.n / 2); 
/*  261 */     return oldValue;
/*      */   }
/*      */   private int removeNullEntry() {
/*  264 */     this.containsNullKey = false;
/*  265 */     int oldValue = this.value[this.n];
/*  266 */     this.size--;
/*  267 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  268 */       rehash(this.n / 2); 
/*  269 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Long, ? extends Integer> m) {
/*  273 */     if (this.f <= 0.5D) {
/*  274 */       ensureCapacity(m.size());
/*      */     } else {
/*  276 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  278 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(long k) {
/*  282 */     if (this.strategy.equals(k, 0L)) {
/*  283 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  285 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  288 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  289 */       return -(pos + 1); 
/*  290 */     if (this.strategy.equals(k, curr)) {
/*  291 */       return pos;
/*      */     }
/*      */     while (true) {
/*  294 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  295 */         return -(pos + 1); 
/*  296 */       if (this.strategy.equals(k, curr))
/*  297 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, long k, int v) {
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
/*      */   public int put(long k, int v) {
/*  312 */     int pos = find(k);
/*  313 */     if (pos < 0) {
/*  314 */       insert(-pos - 1, k, v);
/*  315 */       return this.defRetValue;
/*      */     } 
/*  317 */     int oldValue = this.value[pos];
/*  318 */     this.value[pos] = v;
/*  319 */     return oldValue;
/*      */   }
/*      */   private int addToValue(int pos, int incr) {
/*  322 */     int oldValue = this.value[pos];
/*  323 */     this.value[pos] = oldValue + incr;
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
/*      */   public int addTo(long k, int incr) {
/*      */     int pos;
/*  344 */     if (this.strategy.equals(k, 0L)) {
/*  345 */       if (this.containsNullKey)
/*  346 */         return addToValue(this.n, incr); 
/*  347 */       pos = this.n;
/*  348 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  351 */       long[] key = this.key;
/*      */       long curr;
/*  353 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != 0L) {
/*  354 */         if (this.strategy.equals(curr, k))
/*  355 */           return addToValue(pos, incr); 
/*  356 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0L) {
/*  357 */           if (this.strategy.equals(curr, k))
/*  358 */             return addToValue(pos, incr); 
/*      */         } 
/*      */       } 
/*  361 */     }  this.key[pos] = k;
/*  362 */     this.value[pos] = this.defRetValue + incr;
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
/*  380 */     long[] key = this.key; while (true) {
/*      */       long curr; int last;
/*  382 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  384 */         if ((curr = key[pos]) == 0L) {
/*  385 */           key[last] = 0L;
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
/*      */   public int remove(long k) {
/*  400 */     if (this.strategy.equals(k, 0L)) {
/*  401 */       if (this.containsNullKey)
/*  402 */         return removeNullEntry(); 
/*  403 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  406 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  409 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  410 */       return this.defRetValue; 
/*  411 */     if (this.strategy.equals(k, curr))
/*  412 */       return removeEntry(pos); 
/*      */     while (true) {
/*  414 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  415 */         return this.defRetValue; 
/*  416 */       if (this.strategy.equals(k, curr)) {
/*  417 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public int get(long k) {
/*  423 */     if (this.strategy.equals(k, 0L)) {
/*  424 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  426 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  429 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  430 */       return this.defRetValue; 
/*  431 */     if (this.strategy.equals(k, curr)) {
/*  432 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  435 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  436 */         return this.defRetValue; 
/*  437 */       if (this.strategy.equals(k, curr)) {
/*  438 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(long k) {
/*  444 */     if (this.strategy.equals(k, 0L)) {
/*  445 */       return this.containsNullKey;
/*      */     }
/*  447 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  450 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  451 */       return false; 
/*  452 */     if (this.strategy.equals(k, curr)) {
/*  453 */       return true;
/*      */     }
/*      */     while (true) {
/*  456 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  457 */         return false; 
/*  458 */       if (this.strategy.equals(k, curr))
/*  459 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(int v) {
/*  464 */     int[] value = this.value;
/*  465 */     long[] key = this.key;
/*  466 */     if (this.containsNullKey && value[this.n] == v)
/*  467 */       return true; 
/*  468 */     for (int i = this.n; i-- != 0;) {
/*  469 */       if (key[i] != 0L && value[i] == v)
/*  470 */         return true; 
/*  471 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getOrDefault(long k, int defaultValue) {
/*  477 */     if (this.strategy.equals(k, 0L)) {
/*  478 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  480 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  483 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  484 */       return defaultValue; 
/*  485 */     if (this.strategy.equals(k, curr)) {
/*  486 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  489 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  490 */         return defaultValue; 
/*  491 */       if (this.strategy.equals(k, curr)) {
/*  492 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public int putIfAbsent(long k, int v) {
/*  498 */     int pos = find(k);
/*  499 */     if (pos >= 0)
/*  500 */       return this.value[pos]; 
/*  501 */     insert(-pos - 1, k, v);
/*  502 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(long k, int v) {
/*  508 */     if (this.strategy.equals(k, 0L)) {
/*  509 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  510 */         removeNullEntry();
/*  511 */         return true;
/*      */       } 
/*  513 */       return false;
/*      */     } 
/*      */     
/*  516 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  519 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  520 */       return false; 
/*  521 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  522 */       removeEntry(pos);
/*  523 */       return true;
/*      */     } 
/*      */     while (true) {
/*  526 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  527 */         return false; 
/*  528 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  529 */         removeEntry(pos);
/*  530 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(long k, int oldValue, int v) {
/*  537 */     int pos = find(k);
/*  538 */     if (pos < 0 || oldValue != this.value[pos])
/*  539 */       return false; 
/*  540 */     this.value[pos] = v;
/*  541 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public int replace(long k, int v) {
/*  546 */     int pos = find(k);
/*  547 */     if (pos < 0)
/*  548 */       return this.defRetValue; 
/*  549 */     int oldValue = this.value[pos];
/*  550 */     this.value[pos] = v;
/*  551 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public int computeIfAbsent(long k, LongToIntFunction mappingFunction) {
/*  556 */     Objects.requireNonNull(mappingFunction);
/*  557 */     int pos = find(k);
/*  558 */     if (pos >= 0)
/*  559 */       return this.value[pos]; 
/*  560 */     int newValue = mappingFunction.applyAsInt(k);
/*  561 */     insert(-pos - 1, k, newValue);
/*  562 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int computeIfAbsentNullable(long k, LongFunction<? extends Integer> mappingFunction) {
/*  568 */     Objects.requireNonNull(mappingFunction);
/*  569 */     int pos = find(k);
/*  570 */     if (pos >= 0)
/*  571 */       return this.value[pos]; 
/*  572 */     Integer newValue = mappingFunction.apply(k);
/*  573 */     if (newValue == null)
/*  574 */       return this.defRetValue; 
/*  575 */     int v = newValue.intValue();
/*  576 */     insert(-pos - 1, k, v);
/*  577 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int computeIfPresent(long k, BiFunction<? super Long, ? super Integer, ? extends Integer> remappingFunction) {
/*  583 */     Objects.requireNonNull(remappingFunction);
/*  584 */     int pos = find(k);
/*  585 */     if (pos < 0)
/*  586 */       return this.defRetValue; 
/*  587 */     Integer newValue = remappingFunction.apply(Long.valueOf(k), Integer.valueOf(this.value[pos]));
/*  588 */     if (newValue == null) {
/*  589 */       if (this.strategy.equals(k, 0L)) {
/*  590 */         removeNullEntry();
/*      */       } else {
/*  592 */         removeEntry(pos);
/*  593 */       }  return this.defRetValue;
/*      */     } 
/*  595 */     this.value[pos] = newValue.intValue(); return newValue.intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int compute(long k, BiFunction<? super Long, ? super Integer, ? extends Integer> remappingFunction) {
/*  601 */     Objects.requireNonNull(remappingFunction);
/*  602 */     int pos = find(k);
/*  603 */     Integer newValue = remappingFunction.apply(Long.valueOf(k), 
/*  604 */         (pos >= 0) ? Integer.valueOf(this.value[pos]) : null);
/*  605 */     if (newValue == null) {
/*  606 */       if (pos >= 0)
/*  607 */         if (this.strategy.equals(k, 0L)) {
/*  608 */           removeNullEntry();
/*      */         } else {
/*  610 */           removeEntry(pos);
/*      */         }  
/*  612 */       return this.defRetValue;
/*      */     } 
/*  614 */     int newVal = newValue.intValue();
/*  615 */     if (pos < 0) {
/*  616 */       insert(-pos - 1, k, newVal);
/*  617 */       return newVal;
/*      */     } 
/*  619 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int merge(long k, int v, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
/*  625 */     Objects.requireNonNull(remappingFunction);
/*  626 */     int pos = find(k);
/*  627 */     if (pos < 0) {
/*  628 */       insert(-pos - 1, k, v);
/*  629 */       return v;
/*      */     } 
/*  631 */     Integer newValue = remappingFunction.apply(Integer.valueOf(this.value[pos]), Integer.valueOf(v));
/*  632 */     if (newValue == null) {
/*  633 */       if (this.strategy.equals(k, 0L)) {
/*  634 */         removeNullEntry();
/*      */       } else {
/*  636 */         removeEntry(pos);
/*  637 */       }  return this.defRetValue;
/*      */     } 
/*  639 */     this.value[pos] = newValue.intValue(); return newValue.intValue();
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
/*      */     implements Long2IntMap.Entry, Map.Entry<Long, Integer>
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
/*  680 */       return Long2IntOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public int getIntValue() {
/*  684 */       return Long2IntOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public int setValue(int v) {
/*  688 */       int oldValue = Long2IntOpenCustomHashMap.this.value[this.index];
/*  689 */       Long2IntOpenCustomHashMap.this.value[this.index] = v;
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
/*  700 */       return Long.valueOf(Long2IntOpenCustomHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer getValue() {
/*  710 */       return Integer.valueOf(Long2IntOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer setValue(Integer v) {
/*  720 */       return Integer.valueOf(setValue(v.intValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  725 */       if (!(o instanceof Map.Entry))
/*  726 */         return false; 
/*  727 */       Map.Entry<Long, Integer> e = (Map.Entry<Long, Integer>)o;
/*  728 */       return (Long2IntOpenCustomHashMap.this.strategy.equals(Long2IntOpenCustomHashMap.this.key[this.index], ((Long)e.getKey()).longValue()) && Long2IntOpenCustomHashMap.this.value[this.index] == ((Integer)e
/*  729 */         .getValue()).intValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  733 */       return Long2IntOpenCustomHashMap.this.strategy.hashCode(Long2IntOpenCustomHashMap.this.key[this.index]) ^ Long2IntOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/*  737 */       return Long2IntOpenCustomHashMap.this.key[this.index] + "=>" + Long2IntOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  747 */     int pos = Long2IntOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  754 */     int last = -1;
/*      */     
/*  756 */     int c = Long2IntOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  760 */     boolean mustReturnNullKey = Long2IntOpenCustomHashMap.this.containsNullKey;
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
/*  775 */         return this.last = Long2IntOpenCustomHashMap.this.n;
/*      */       } 
/*  777 */       long[] key = Long2IntOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  779 */         if (--this.pos < 0) {
/*      */           
/*  781 */           this.last = Integer.MIN_VALUE;
/*  782 */           long k = this.wrapped.getLong(-this.pos - 1);
/*  783 */           int p = HashCommon.mix(Long2IntOpenCustomHashMap.this.strategy.hashCode(k)) & Long2IntOpenCustomHashMap.this.mask;
/*  784 */           while (!Long2IntOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  785 */             p = p + 1 & Long2IntOpenCustomHashMap.this.mask; 
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
/*  803 */       long[] key = Long2IntOpenCustomHashMap.this.key; while (true) {
/*      */         long curr; int last;
/*  805 */         pos = (last = pos) + 1 & Long2IntOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  807 */           if ((curr = key[pos]) == 0L) {
/*  808 */             key[last] = 0L;
/*      */             return;
/*      */           } 
/*  811 */           int slot = HashCommon.mix(Long2IntOpenCustomHashMap.this.strategy.hashCode(curr)) & Long2IntOpenCustomHashMap.this.mask;
/*  812 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  814 */           pos = pos + 1 & Long2IntOpenCustomHashMap.this.mask;
/*      */         } 
/*  816 */         if (pos < last) {
/*  817 */           if (this.wrapped == null)
/*  818 */             this.wrapped = new LongArrayList(2); 
/*  819 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  821 */         key[last] = curr;
/*  822 */         Long2IntOpenCustomHashMap.this.value[last] = Long2IntOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  826 */       if (this.last == -1)
/*  827 */         throw new IllegalStateException(); 
/*  828 */       if (this.last == Long2IntOpenCustomHashMap.this.n) {
/*  829 */         Long2IntOpenCustomHashMap.this.containsNullKey = false;
/*  830 */       } else if (this.pos >= 0) {
/*  831 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  834 */         Long2IntOpenCustomHashMap.this.remove(this.wrapped.getLong(-this.pos - 1));
/*  835 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  838 */       Long2IntOpenCustomHashMap.this.size--;
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
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Long2IntMap.Entry> { private Long2IntOpenCustomHashMap.MapEntry entry;
/*      */     
/*      */     public Long2IntOpenCustomHashMap.MapEntry next() {
/*  854 */       return this.entry = new Long2IntOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  858 */       super.remove();
/*  859 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Long2IntMap.Entry> { private FastEntryIterator() {
/*  863 */       this.entry = new Long2IntOpenCustomHashMap.MapEntry();
/*      */     } private final Long2IntOpenCustomHashMap.MapEntry entry;
/*      */     public Long2IntOpenCustomHashMap.MapEntry next() {
/*  866 */       this.entry.index = nextEntry();
/*  867 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Long2IntMap.Entry> implements Long2IntMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Long2IntMap.Entry> iterator() {
/*  873 */       return new Long2IntOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Long2IntMap.Entry> fastIterator() {
/*  877 */       return new Long2IntOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  882 */       if (!(o instanceof Map.Entry))
/*  883 */         return false; 
/*  884 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  885 */       if (e.getKey() == null || !(e.getKey() instanceof Long))
/*  886 */         return false; 
/*  887 */       if (e.getValue() == null || !(e.getValue() instanceof Integer))
/*  888 */         return false; 
/*  889 */       long k = ((Long)e.getKey()).longValue();
/*  890 */       int v = ((Integer)e.getValue()).intValue();
/*  891 */       if (Long2IntOpenCustomHashMap.this.strategy.equals(k, 0L)) {
/*  892 */         return (Long2IntOpenCustomHashMap.this.containsNullKey && Long2IntOpenCustomHashMap.this.value[Long2IntOpenCustomHashMap.this.n] == v);
/*      */       }
/*  894 */       long[] key = Long2IntOpenCustomHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/*  897 */       if ((curr = key[pos = HashCommon.mix(Long2IntOpenCustomHashMap.this.strategy.hashCode(k)) & Long2IntOpenCustomHashMap.this.mask]) == 0L)
/*  898 */         return false; 
/*  899 */       if (Long2IntOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  900 */         return (Long2IntOpenCustomHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  903 */         if ((curr = key[pos = pos + 1 & Long2IntOpenCustomHashMap.this.mask]) == 0L)
/*  904 */           return false; 
/*  905 */         if (Long2IntOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  906 */           return (Long2IntOpenCustomHashMap.this.value[pos] == v);
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
/*  917 */       if (e.getValue() == null || !(e.getValue() instanceof Integer))
/*  918 */         return false; 
/*  919 */       long k = ((Long)e.getKey()).longValue();
/*  920 */       int v = ((Integer)e.getValue()).intValue();
/*  921 */       if (Long2IntOpenCustomHashMap.this.strategy.equals(k, 0L)) {
/*  922 */         if (Long2IntOpenCustomHashMap.this.containsNullKey && Long2IntOpenCustomHashMap.this.value[Long2IntOpenCustomHashMap.this.n] == v) {
/*  923 */           Long2IntOpenCustomHashMap.this.removeNullEntry();
/*  924 */           return true;
/*      */         } 
/*  926 */         return false;
/*      */       } 
/*      */       
/*  929 */       long[] key = Long2IntOpenCustomHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/*  932 */       if ((curr = key[pos = HashCommon.mix(Long2IntOpenCustomHashMap.this.strategy.hashCode(k)) & Long2IntOpenCustomHashMap.this.mask]) == 0L)
/*  933 */         return false; 
/*  934 */       if (Long2IntOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  935 */         if (Long2IntOpenCustomHashMap.this.value[pos] == v) {
/*  936 */           Long2IntOpenCustomHashMap.this.removeEntry(pos);
/*  937 */           return true;
/*      */         } 
/*  939 */         return false;
/*      */       } 
/*      */       while (true) {
/*  942 */         if ((curr = key[pos = pos + 1 & Long2IntOpenCustomHashMap.this.mask]) == 0L)
/*  943 */           return false; 
/*  944 */         if (Long2IntOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  945 */           Long2IntOpenCustomHashMap.this.value[pos] == v) {
/*  946 */           Long2IntOpenCustomHashMap.this.removeEntry(pos);
/*  947 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  954 */       return Long2IntOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  958 */       Long2IntOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Long2IntMap.Entry> consumer) {
/*  963 */       if (Long2IntOpenCustomHashMap.this.containsNullKey)
/*  964 */         consumer.accept(new AbstractLong2IntMap.BasicEntry(Long2IntOpenCustomHashMap.this.key[Long2IntOpenCustomHashMap.this.n], Long2IntOpenCustomHashMap.this.value[Long2IntOpenCustomHashMap.this.n])); 
/*  965 */       for (int pos = Long2IntOpenCustomHashMap.this.n; pos-- != 0;) {
/*  966 */         if (Long2IntOpenCustomHashMap.this.key[pos] != 0L)
/*  967 */           consumer.accept(new AbstractLong2IntMap.BasicEntry(Long2IntOpenCustomHashMap.this.key[pos], Long2IntOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Long2IntMap.Entry> consumer) {
/*  972 */       AbstractLong2IntMap.BasicEntry entry = new AbstractLong2IntMap.BasicEntry();
/*  973 */       if (Long2IntOpenCustomHashMap.this.containsNullKey) {
/*  974 */         entry.key = Long2IntOpenCustomHashMap.this.key[Long2IntOpenCustomHashMap.this.n];
/*  975 */         entry.value = Long2IntOpenCustomHashMap.this.value[Long2IntOpenCustomHashMap.this.n];
/*  976 */         consumer.accept(entry);
/*      */       } 
/*  978 */       for (int pos = Long2IntOpenCustomHashMap.this.n; pos-- != 0;) {
/*  979 */         if (Long2IntOpenCustomHashMap.this.key[pos] != 0L) {
/*  980 */           entry.key = Long2IntOpenCustomHashMap.this.key[pos];
/*  981 */           entry.value = Long2IntOpenCustomHashMap.this.value[pos];
/*  982 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Long2IntMap.FastEntrySet long2IntEntrySet() {
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
/* 1007 */       return Long2IntOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractLongSet { private KeySet() {}
/*      */     
/*      */     public LongIterator iterator() {
/* 1013 */       return new Long2IntOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(LongConsumer consumer) {
/* 1018 */       if (Long2IntOpenCustomHashMap.this.containsNullKey)
/* 1019 */         consumer.accept(Long2IntOpenCustomHashMap.this.key[Long2IntOpenCustomHashMap.this.n]); 
/* 1020 */       for (int pos = Long2IntOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1021 */         long k = Long2IntOpenCustomHashMap.this.key[pos];
/* 1022 */         if (k != 0L)
/* 1023 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1028 */       return Long2IntOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(long k) {
/* 1032 */       return Long2IntOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(long k) {
/* 1036 */       int oldSize = Long2IntOpenCustomHashMap.this.size;
/* 1037 */       Long2IntOpenCustomHashMap.this.remove(k);
/* 1038 */       return (Long2IntOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1042 */       Long2IntOpenCustomHashMap.this.clear();
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
/*      */     implements IntIterator
/*      */   {
/*      */     public int nextInt() {
/* 1066 */       return Long2IntOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public IntCollection values() {
/* 1071 */     if (this.values == null)
/* 1072 */       this.values = (IntCollection)new AbstractIntCollection()
/*      */         {
/*      */           public IntIterator iterator() {
/* 1075 */             return new Long2IntOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1079 */             return Long2IntOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(int v) {
/* 1083 */             return Long2IntOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1087 */             Long2IntOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(IntConsumer consumer)
/*      */           {
/* 1092 */             if (Long2IntOpenCustomHashMap.this.containsNullKey)
/* 1093 */               consumer.accept(Long2IntOpenCustomHashMap.this.value[Long2IntOpenCustomHashMap.this.n]); 
/* 1094 */             for (int pos = Long2IntOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1095 */               if (Long2IntOpenCustomHashMap.this.key[pos] != 0L)
/* 1096 */                 consumer.accept(Long2IntOpenCustomHashMap.this.value[pos]); 
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
/* 1165 */     int[] value = this.value;
/* 1166 */     int mask = newN - 1;
/* 1167 */     long[] newKey = new long[newN + 1];
/* 1168 */     int[] newValue = new int[newN + 1];
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
/*      */   public Long2IntOpenCustomHashMap clone() {
/*      */     Long2IntOpenCustomHashMap c;
/*      */     try {
/* 1199 */       c = (Long2IntOpenCustomHashMap)super.clone();
/* 1200 */     } catch (CloneNotSupportedException cantHappen) {
/* 1201 */       throw new InternalError();
/*      */     } 
/* 1203 */     c.keys = null;
/* 1204 */     c.values = null;
/* 1205 */     c.entries = null;
/* 1206 */     c.containsNullKey = this.containsNullKey;
/* 1207 */     c.key = (long[])this.key.clone();
/* 1208 */     c.value = (int[])this.value.clone();
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
/* 1228 */       t ^= this.value[i];
/* 1229 */       h += t;
/* 1230 */       i++;
/*      */     } 
/*      */     
/* 1233 */     if (this.containsNullKey)
/* 1234 */       h += this.value[this.n]; 
/* 1235 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1238 */     long[] key = this.key;
/* 1239 */     int[] value = this.value;
/* 1240 */     MapIterator i = new MapIterator();
/* 1241 */     s.defaultWriteObject();
/* 1242 */     for (int j = this.size; j-- != 0; ) {
/* 1243 */       int e = i.nextEntry();
/* 1244 */       s.writeLong(key[e]);
/* 1245 */       s.writeInt(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1250 */     s.defaultReadObject();
/* 1251 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1252 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1253 */     this.mask = this.n - 1;
/* 1254 */     long[] key = this.key = new long[this.n + 1];
/* 1255 */     int[] value = this.value = new int[this.n + 1];
/*      */ 
/*      */     
/* 1258 */     for (int i = this.size; i-- != 0; ) {
/* 1259 */       int pos; long k = s.readLong();
/* 1260 */       int v = s.readInt();
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


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\Long2IntOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */