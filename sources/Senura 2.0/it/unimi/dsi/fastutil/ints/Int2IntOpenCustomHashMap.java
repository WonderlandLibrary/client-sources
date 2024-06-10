/*      */ package it.unimi.dsi.fastutil.ints;
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
/*      */ public class Int2IntOpenCustomHashMap
/*      */   extends AbstractInt2IntMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient int[] key;
/*      */   protected transient int[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected IntHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Int2IntMap.FastEntrySet entries;
/*      */   protected transient IntSet keys;
/*      */   protected transient IntCollection values;
/*      */   
/*      */   public Int2IntOpenCustomHashMap(int expected, float f, IntHash.Strategy strategy) {
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
/*      */   public Int2IntOpenCustomHashMap(int expected, IntHash.Strategy strategy) {
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
/*      */   public Int2IntOpenCustomHashMap(IntHash.Strategy strategy) {
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
/*      */   public Int2IntOpenCustomHashMap(Map<? extends Integer, ? extends Integer> m, float f, IntHash.Strategy strategy) {
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
/*      */   public Int2IntOpenCustomHashMap(Map<? extends Integer, ? extends Integer> m, IntHash.Strategy strategy) {
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
/*      */   public Int2IntOpenCustomHashMap(Int2IntMap m, float f, IntHash.Strategy strategy) {
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
/*      */   public Int2IntOpenCustomHashMap(Int2IntMap m, IntHash.Strategy strategy) {
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
/*      */   public Int2IntOpenCustomHashMap(int[] k, int[] v, float f, IntHash.Strategy strategy) {
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
/*      */   public Int2IntOpenCustomHashMap(int[] k, int[] v, IntHash.Strategy strategy) {
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
/*      */   private int removeEntry(int pos) {
/*  255 */     int oldValue = this.value[pos];
/*  256 */     this.size--;
/*  257 */     shiftKeys(pos);
/*  258 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  259 */       rehash(this.n / 2); 
/*  260 */     return oldValue;
/*      */   }
/*      */   private int removeNullEntry() {
/*  263 */     this.containsNullKey = false;
/*  264 */     int oldValue = this.value[this.n];
/*  265 */     this.size--;
/*  266 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  267 */       rehash(this.n / 2); 
/*  268 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Integer, ? extends Integer> m) {
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
/*      */   private void insert(int pos, int k, int v) {
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
/*      */   public int put(int k, int v) {
/*  311 */     int pos = find(k);
/*  312 */     if (pos < 0) {
/*  313 */       insert(-pos - 1, k, v);
/*  314 */       return this.defRetValue;
/*      */     } 
/*  316 */     int oldValue = this.value[pos];
/*  317 */     this.value[pos] = v;
/*  318 */     return oldValue;
/*      */   }
/*      */   private int addToValue(int pos, int incr) {
/*  321 */     int oldValue = this.value[pos];
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
/*      */   public int addTo(int k, int incr) {
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
/*      */   public int remove(int k) {
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
/*      */   public int get(int k) {
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
/*      */   public boolean containsValue(int v) {
/*  463 */     int[] value = this.value;
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
/*      */   public int getOrDefault(int k, int defaultValue) {
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
/*      */   public int putIfAbsent(int k, int v) {
/*  497 */     int pos = find(k);
/*  498 */     if (pos >= 0)
/*  499 */       return this.value[pos]; 
/*  500 */     insert(-pos - 1, k, v);
/*  501 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(int k, int v) {
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
/*      */   public boolean replace(int k, int oldValue, int v) {
/*  536 */     int pos = find(k);
/*  537 */     if (pos < 0 || oldValue != this.value[pos])
/*  538 */       return false; 
/*  539 */     this.value[pos] = v;
/*  540 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public int replace(int k, int v) {
/*  545 */     int pos = find(k);
/*  546 */     if (pos < 0)
/*  547 */       return this.defRetValue; 
/*  548 */     int oldValue = this.value[pos];
/*  549 */     this.value[pos] = v;
/*  550 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public int computeIfAbsent(int k, IntUnaryOperator mappingFunction) {
/*  555 */     Objects.requireNonNull(mappingFunction);
/*  556 */     int pos = find(k);
/*  557 */     if (pos >= 0)
/*  558 */       return this.value[pos]; 
/*  559 */     int newValue = mappingFunction.applyAsInt(k);
/*  560 */     insert(-pos - 1, k, newValue);
/*  561 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int computeIfAbsentNullable(int k, IntFunction<? extends Integer> mappingFunction) {
/*  567 */     Objects.requireNonNull(mappingFunction);
/*  568 */     int pos = find(k);
/*  569 */     if (pos >= 0)
/*  570 */       return this.value[pos]; 
/*  571 */     Integer newValue = mappingFunction.apply(k);
/*  572 */     if (newValue == null)
/*  573 */       return this.defRetValue; 
/*  574 */     int v = newValue.intValue();
/*  575 */     insert(-pos - 1, k, v);
/*  576 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int computeIfPresent(int k, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
/*  582 */     Objects.requireNonNull(remappingFunction);
/*  583 */     int pos = find(k);
/*  584 */     if (pos < 0)
/*  585 */       return this.defRetValue; 
/*  586 */     Integer newValue = remappingFunction.apply(Integer.valueOf(k), Integer.valueOf(this.value[pos]));
/*  587 */     if (newValue == null) {
/*  588 */       if (this.strategy.equals(k, 0)) {
/*  589 */         removeNullEntry();
/*      */       } else {
/*  591 */         removeEntry(pos);
/*  592 */       }  return this.defRetValue;
/*      */     } 
/*  594 */     this.value[pos] = newValue.intValue(); return newValue.intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int compute(int k, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
/*  600 */     Objects.requireNonNull(remappingFunction);
/*  601 */     int pos = find(k);
/*  602 */     Integer newValue = remappingFunction.apply(Integer.valueOf(k), 
/*  603 */         (pos >= 0) ? Integer.valueOf(this.value[pos]) : null);
/*  604 */     if (newValue == null) {
/*  605 */       if (pos >= 0)
/*  606 */         if (this.strategy.equals(k, 0)) {
/*  607 */           removeNullEntry();
/*      */         } else {
/*  609 */           removeEntry(pos);
/*      */         }  
/*  611 */       return this.defRetValue;
/*      */     } 
/*  613 */     int newVal = newValue.intValue();
/*  614 */     if (pos < 0) {
/*  615 */       insert(-pos - 1, k, newVal);
/*  616 */       return newVal;
/*      */     } 
/*  618 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int merge(int k, int v, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
/*  624 */     Objects.requireNonNull(remappingFunction);
/*  625 */     int pos = find(k);
/*  626 */     if (pos < 0) {
/*  627 */       insert(-pos - 1, k, v);
/*  628 */       return v;
/*      */     } 
/*  630 */     Integer newValue = remappingFunction.apply(Integer.valueOf(this.value[pos]), Integer.valueOf(v));
/*  631 */     if (newValue == null) {
/*  632 */       if (this.strategy.equals(k, 0)) {
/*  633 */         removeNullEntry();
/*      */       } else {
/*  635 */         removeEntry(pos);
/*  636 */       }  return this.defRetValue;
/*      */     } 
/*  638 */     this.value[pos] = newValue.intValue(); return newValue.intValue();
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
/*      */     implements Int2IntMap.Entry, Map.Entry<Integer, Integer>
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
/*  679 */       return Int2IntOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public int getIntValue() {
/*  683 */       return Int2IntOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public int setValue(int v) {
/*  687 */       int oldValue = Int2IntOpenCustomHashMap.this.value[this.index];
/*  688 */       Int2IntOpenCustomHashMap.this.value[this.index] = v;
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
/*  699 */       return Integer.valueOf(Int2IntOpenCustomHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer getValue() {
/*  709 */       return Integer.valueOf(Int2IntOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer setValue(Integer v) {
/*  719 */       return Integer.valueOf(setValue(v.intValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  724 */       if (!(o instanceof Map.Entry))
/*  725 */         return false; 
/*  726 */       Map.Entry<Integer, Integer> e = (Map.Entry<Integer, Integer>)o;
/*  727 */       return (Int2IntOpenCustomHashMap.this.strategy.equals(Int2IntOpenCustomHashMap.this.key[this.index], ((Integer)e.getKey()).intValue()) && Int2IntOpenCustomHashMap.this.value[this.index] == ((Integer)e
/*  728 */         .getValue()).intValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  732 */       return Int2IntOpenCustomHashMap.this.strategy.hashCode(Int2IntOpenCustomHashMap.this.key[this.index]) ^ Int2IntOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/*  736 */       return Int2IntOpenCustomHashMap.this.key[this.index] + "=>" + Int2IntOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  746 */     int pos = Int2IntOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  753 */     int last = -1;
/*      */     
/*  755 */     int c = Int2IntOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  759 */     boolean mustReturnNullKey = Int2IntOpenCustomHashMap.this.containsNullKey;
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
/*  774 */         return this.last = Int2IntOpenCustomHashMap.this.n;
/*      */       } 
/*  776 */       int[] key = Int2IntOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  778 */         if (--this.pos < 0) {
/*      */           
/*  780 */           this.last = Integer.MIN_VALUE;
/*  781 */           int k = this.wrapped.getInt(-this.pos - 1);
/*  782 */           int p = HashCommon.mix(Int2IntOpenCustomHashMap.this.strategy.hashCode(k)) & Int2IntOpenCustomHashMap.this.mask;
/*  783 */           while (!Int2IntOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  784 */             p = p + 1 & Int2IntOpenCustomHashMap.this.mask; 
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
/*  802 */       int[] key = Int2IntOpenCustomHashMap.this.key; while (true) {
/*      */         int curr, last;
/*  804 */         pos = (last = pos) + 1 & Int2IntOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  806 */           if ((curr = key[pos]) == 0) {
/*  807 */             key[last] = 0;
/*      */             return;
/*      */           } 
/*  810 */           int slot = HashCommon.mix(Int2IntOpenCustomHashMap.this.strategy.hashCode(curr)) & Int2IntOpenCustomHashMap.this.mask;
/*  811 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  813 */           pos = pos + 1 & Int2IntOpenCustomHashMap.this.mask;
/*      */         } 
/*  815 */         if (pos < last) {
/*  816 */           if (this.wrapped == null)
/*  817 */             this.wrapped = new IntArrayList(2); 
/*  818 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  820 */         key[last] = curr;
/*  821 */         Int2IntOpenCustomHashMap.this.value[last] = Int2IntOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  825 */       if (this.last == -1)
/*  826 */         throw new IllegalStateException(); 
/*  827 */       if (this.last == Int2IntOpenCustomHashMap.this.n) {
/*  828 */         Int2IntOpenCustomHashMap.this.containsNullKey = false;
/*  829 */       } else if (this.pos >= 0) {
/*  830 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  833 */         Int2IntOpenCustomHashMap.this.remove(this.wrapped.getInt(-this.pos - 1));
/*  834 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  837 */       Int2IntOpenCustomHashMap.this.size--;
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
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Int2IntMap.Entry> { private Int2IntOpenCustomHashMap.MapEntry entry;
/*      */     
/*      */     public Int2IntOpenCustomHashMap.MapEntry next() {
/*  853 */       return this.entry = new Int2IntOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  857 */       super.remove();
/*  858 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Int2IntMap.Entry> { private FastEntryIterator() {
/*  862 */       this.entry = new Int2IntOpenCustomHashMap.MapEntry();
/*      */     } private final Int2IntOpenCustomHashMap.MapEntry entry;
/*      */     public Int2IntOpenCustomHashMap.MapEntry next() {
/*  865 */       this.entry.index = nextEntry();
/*  866 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Int2IntMap.Entry> implements Int2IntMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Int2IntMap.Entry> iterator() {
/*  872 */       return new Int2IntOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Int2IntMap.Entry> fastIterator() {
/*  876 */       return new Int2IntOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  881 */       if (!(o instanceof Map.Entry))
/*  882 */         return false; 
/*  883 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  884 */       if (e.getKey() == null || !(e.getKey() instanceof Integer))
/*  885 */         return false; 
/*  886 */       if (e.getValue() == null || !(e.getValue() instanceof Integer))
/*  887 */         return false; 
/*  888 */       int k = ((Integer)e.getKey()).intValue();
/*  889 */       int v = ((Integer)e.getValue()).intValue();
/*  890 */       if (Int2IntOpenCustomHashMap.this.strategy.equals(k, 0)) {
/*  891 */         return (Int2IntOpenCustomHashMap.this.containsNullKey && Int2IntOpenCustomHashMap.this.value[Int2IntOpenCustomHashMap.this.n] == v);
/*      */       }
/*  893 */       int[] key = Int2IntOpenCustomHashMap.this.key;
/*      */       
/*      */       int curr, pos;
/*  896 */       if ((curr = key[pos = HashCommon.mix(Int2IntOpenCustomHashMap.this.strategy.hashCode(k)) & Int2IntOpenCustomHashMap.this.mask]) == 0)
/*  897 */         return false; 
/*  898 */       if (Int2IntOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  899 */         return (Int2IntOpenCustomHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  902 */         if ((curr = key[pos = pos + 1 & Int2IntOpenCustomHashMap.this.mask]) == 0)
/*  903 */           return false; 
/*  904 */         if (Int2IntOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  905 */           return (Int2IntOpenCustomHashMap.this.value[pos] == v);
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
/*  916 */       if (e.getValue() == null || !(e.getValue() instanceof Integer))
/*  917 */         return false; 
/*  918 */       int k = ((Integer)e.getKey()).intValue();
/*  919 */       int v = ((Integer)e.getValue()).intValue();
/*  920 */       if (Int2IntOpenCustomHashMap.this.strategy.equals(k, 0)) {
/*  921 */         if (Int2IntOpenCustomHashMap.this.containsNullKey && Int2IntOpenCustomHashMap.this.value[Int2IntOpenCustomHashMap.this.n] == v) {
/*  922 */           Int2IntOpenCustomHashMap.this.removeNullEntry();
/*  923 */           return true;
/*      */         } 
/*  925 */         return false;
/*      */       } 
/*      */       
/*  928 */       int[] key = Int2IntOpenCustomHashMap.this.key;
/*      */       
/*      */       int curr, pos;
/*  931 */       if ((curr = key[pos = HashCommon.mix(Int2IntOpenCustomHashMap.this.strategy.hashCode(k)) & Int2IntOpenCustomHashMap.this.mask]) == 0)
/*  932 */         return false; 
/*  933 */       if (Int2IntOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  934 */         if (Int2IntOpenCustomHashMap.this.value[pos] == v) {
/*  935 */           Int2IntOpenCustomHashMap.this.removeEntry(pos);
/*  936 */           return true;
/*      */         } 
/*  938 */         return false;
/*      */       } 
/*      */       while (true) {
/*  941 */         if ((curr = key[pos = pos + 1 & Int2IntOpenCustomHashMap.this.mask]) == 0)
/*  942 */           return false; 
/*  943 */         if (Int2IntOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  944 */           Int2IntOpenCustomHashMap.this.value[pos] == v) {
/*  945 */           Int2IntOpenCustomHashMap.this.removeEntry(pos);
/*  946 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  953 */       return Int2IntOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  957 */       Int2IntOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Int2IntMap.Entry> consumer) {
/*  962 */       if (Int2IntOpenCustomHashMap.this.containsNullKey)
/*  963 */         consumer.accept(new AbstractInt2IntMap.BasicEntry(Int2IntOpenCustomHashMap.this.key[Int2IntOpenCustomHashMap.this.n], Int2IntOpenCustomHashMap.this.value[Int2IntOpenCustomHashMap.this.n])); 
/*  964 */       for (int pos = Int2IntOpenCustomHashMap.this.n; pos-- != 0;) {
/*  965 */         if (Int2IntOpenCustomHashMap.this.key[pos] != 0)
/*  966 */           consumer.accept(new AbstractInt2IntMap.BasicEntry(Int2IntOpenCustomHashMap.this.key[pos], Int2IntOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Int2IntMap.Entry> consumer) {
/*  971 */       AbstractInt2IntMap.BasicEntry entry = new AbstractInt2IntMap.BasicEntry();
/*  972 */       if (Int2IntOpenCustomHashMap.this.containsNullKey) {
/*  973 */         entry.key = Int2IntOpenCustomHashMap.this.key[Int2IntOpenCustomHashMap.this.n];
/*  974 */         entry.value = Int2IntOpenCustomHashMap.this.value[Int2IntOpenCustomHashMap.this.n];
/*  975 */         consumer.accept(entry);
/*      */       } 
/*  977 */       for (int pos = Int2IntOpenCustomHashMap.this.n; pos-- != 0;) {
/*  978 */         if (Int2IntOpenCustomHashMap.this.key[pos] != 0) {
/*  979 */           entry.key = Int2IntOpenCustomHashMap.this.key[pos];
/*  980 */           entry.value = Int2IntOpenCustomHashMap.this.value[pos];
/*  981 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Int2IntMap.FastEntrySet int2IntEntrySet() {
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
/* 1006 */       return Int2IntOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractIntSet { private KeySet() {}
/*      */     
/*      */     public IntIterator iterator() {
/* 1012 */       return new Int2IntOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer consumer) {
/* 1017 */       if (Int2IntOpenCustomHashMap.this.containsNullKey)
/* 1018 */         consumer.accept(Int2IntOpenCustomHashMap.this.key[Int2IntOpenCustomHashMap.this.n]); 
/* 1019 */       for (int pos = Int2IntOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1020 */         int k = Int2IntOpenCustomHashMap.this.key[pos];
/* 1021 */         if (k != 0)
/* 1022 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1027 */       return Int2IntOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(int k) {
/* 1031 */       return Int2IntOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(int k) {
/* 1035 */       int oldSize = Int2IntOpenCustomHashMap.this.size;
/* 1036 */       Int2IntOpenCustomHashMap.this.remove(k);
/* 1037 */       return (Int2IntOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1041 */       Int2IntOpenCustomHashMap.this.clear();
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
/*      */     implements IntIterator
/*      */   {
/*      */     public int nextInt() {
/* 1065 */       return Int2IntOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public IntCollection values() {
/* 1070 */     if (this.values == null)
/* 1071 */       this.values = new AbstractIntCollection()
/*      */         {
/*      */           public IntIterator iterator() {
/* 1074 */             return new Int2IntOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1078 */             return Int2IntOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(int v) {
/* 1082 */             return Int2IntOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1086 */             Int2IntOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(IntConsumer consumer)
/*      */           {
/* 1091 */             if (Int2IntOpenCustomHashMap.this.containsNullKey)
/* 1092 */               consumer.accept(Int2IntOpenCustomHashMap.this.value[Int2IntOpenCustomHashMap.this.n]); 
/* 1093 */             for (int pos = Int2IntOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1094 */               if (Int2IntOpenCustomHashMap.this.key[pos] != 0)
/* 1095 */                 consumer.accept(Int2IntOpenCustomHashMap.this.value[pos]); 
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
/* 1164 */     int[] value = this.value;
/* 1165 */     int mask = newN - 1;
/* 1166 */     int[] newKey = new int[newN + 1];
/* 1167 */     int[] newValue = new int[newN + 1];
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
/*      */   public Int2IntOpenCustomHashMap clone() {
/*      */     Int2IntOpenCustomHashMap c;
/*      */     try {
/* 1198 */       c = (Int2IntOpenCustomHashMap)super.clone();
/* 1199 */     } catch (CloneNotSupportedException cantHappen) {
/* 1200 */       throw new InternalError();
/*      */     } 
/* 1202 */     c.keys = null;
/* 1203 */     c.values = null;
/* 1204 */     c.entries = null;
/* 1205 */     c.containsNullKey = this.containsNullKey;
/* 1206 */     c.key = (int[])this.key.clone();
/* 1207 */     c.value = (int[])this.value.clone();
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
/* 1238 */     int[] value = this.value;
/* 1239 */     MapIterator i = new MapIterator();
/* 1240 */     s.defaultWriteObject();
/* 1241 */     for (int j = this.size; j-- != 0; ) {
/* 1242 */       int e = i.nextEntry();
/* 1243 */       s.writeInt(key[e]);
/* 1244 */       s.writeInt(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1249 */     s.defaultReadObject();
/* 1250 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1251 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1252 */     this.mask = this.n - 1;
/* 1253 */     int[] key = this.key = new int[this.n + 1];
/* 1254 */     int[] value = this.value = new int[this.n + 1];
/*      */ 
/*      */     
/* 1257 */     for (int i = this.size; i-- != 0; ) {
/* 1258 */       int pos, k = s.readInt();
/* 1259 */       int v = s.readInt();
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


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\Int2IntOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */