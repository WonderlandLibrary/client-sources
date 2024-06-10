/*      */ package it.unimi.dsi.fastutil.doubles;
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
/*      */ 
/*      */ public class Double2IntOpenCustomHashMap
/*      */   extends AbstractDouble2IntMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient double[] key;
/*      */   protected transient int[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected DoubleHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Double2IntMap.FastEntrySet entries;
/*      */   protected transient DoubleSet keys;
/*      */   protected transient IntCollection values;
/*      */   
/*      */   public Double2IntOpenCustomHashMap(int expected, float f, DoubleHash.Strategy strategy) {
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
/*  117 */     this.value = new int[this.n + 1];
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
/*      */   public Double2IntOpenCustomHashMap(int expected, DoubleHash.Strategy strategy) {
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
/*      */   public Double2IntOpenCustomHashMap(DoubleHash.Strategy strategy) {
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
/*      */   public Double2IntOpenCustomHashMap(Map<? extends Double, ? extends Integer> m, float f, DoubleHash.Strategy strategy) {
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
/*      */   public Double2IntOpenCustomHashMap(Map<? extends Double, ? extends Integer> m, DoubleHash.Strategy strategy) {
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
/*      */   public Double2IntOpenCustomHashMap(Double2IntMap m, float f, DoubleHash.Strategy strategy) {
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
/*      */   public Double2IntOpenCustomHashMap(Double2IntMap m, DoubleHash.Strategy strategy) {
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
/*      */   public Double2IntOpenCustomHashMap(double[] k, int[] v, float f, DoubleHash.Strategy strategy) {
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
/*      */   public Double2IntOpenCustomHashMap(double[] k, int[] v, DoubleHash.Strategy strategy) {
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
/*      */   private int removeEntry(int pos) {
/*  261 */     int oldValue = this.value[pos];
/*  262 */     this.size--;
/*  263 */     shiftKeys(pos);
/*  264 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  265 */       rehash(this.n / 2); 
/*  266 */     return oldValue;
/*      */   }
/*      */   private int removeNullEntry() {
/*  269 */     this.containsNullKey = false;
/*  270 */     int oldValue = this.value[this.n];
/*  271 */     this.size--;
/*  272 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  273 */       rehash(this.n / 2); 
/*  274 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Double, ? extends Integer> m) {
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
/*      */   private void insert(int pos, double k, int v) {
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
/*      */   public int put(double k, int v) {
/*  318 */     int pos = find(k);
/*  319 */     if (pos < 0) {
/*  320 */       insert(-pos - 1, k, v);
/*  321 */       return this.defRetValue;
/*      */     } 
/*  323 */     int oldValue = this.value[pos];
/*  324 */     this.value[pos] = v;
/*  325 */     return oldValue;
/*      */   }
/*      */   private int addToValue(int pos, int incr) {
/*  328 */     int oldValue = this.value[pos];
/*  329 */     this.value[pos] = oldValue + incr;
/*  330 */     return oldValue;
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
/*      */   public int addTo(double k, int incr) {
/*      */     int pos;
/*  350 */     if (this.strategy.equals(k, 0.0D)) {
/*  351 */       if (this.containsNullKey)
/*  352 */         return addToValue(this.n, incr); 
/*  353 */       pos = this.n;
/*  354 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  357 */       double[] key = this.key;
/*      */       double curr;
/*  359 */       if (Double.doubleToLongBits(
/*  360 */           curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != 0L) {
/*  361 */         if (this.strategy.equals(curr, k))
/*  362 */           return addToValue(pos, incr); 
/*  363 */         while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
/*  364 */           if (this.strategy.equals(curr, k))
/*  365 */             return addToValue(pos, incr); 
/*      */         } 
/*      */       } 
/*  368 */     }  this.key[pos] = k;
/*  369 */     this.value[pos] = this.defRetValue + incr;
/*  370 */     if (this.size++ >= this.maxFill) {
/*  371 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  374 */     return this.defRetValue;
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
/*  387 */     double[] key = this.key; while (true) {
/*      */       double curr; int last;
/*  389 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  391 */         if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/*  392 */           key[last] = 0.0D;
/*      */           return;
/*      */         } 
/*  395 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  396 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  398 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  400 */       key[last] = curr;
/*  401 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int remove(double k) {
/*  407 */     if (this.strategy.equals(k, 0.0D)) {
/*  408 */       if (this.containsNullKey)
/*  409 */         return removeNullEntry(); 
/*  410 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  413 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  416 */     if (Double.doubleToLongBits(
/*  417 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  418 */       return this.defRetValue; 
/*  419 */     if (this.strategy.equals(k, curr))
/*  420 */       return removeEntry(pos); 
/*      */     while (true) {
/*  422 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  423 */         return this.defRetValue; 
/*  424 */       if (this.strategy.equals(k, curr)) {
/*  425 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public int get(double k) {
/*  431 */     if (this.strategy.equals(k, 0.0D)) {
/*  432 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  434 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  437 */     if (Double.doubleToLongBits(
/*  438 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  439 */       return this.defRetValue; 
/*  440 */     if (this.strategy.equals(k, curr)) {
/*  441 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  444 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  445 */         return this.defRetValue; 
/*  446 */       if (this.strategy.equals(k, curr)) {
/*  447 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(double k) {
/*  453 */     if (this.strategy.equals(k, 0.0D)) {
/*  454 */       return this.containsNullKey;
/*      */     }
/*  456 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  459 */     if (Double.doubleToLongBits(
/*  460 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  461 */       return false; 
/*  462 */     if (this.strategy.equals(k, curr)) {
/*  463 */       return true;
/*      */     }
/*      */     while (true) {
/*  466 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  467 */         return false; 
/*  468 */       if (this.strategy.equals(k, curr))
/*  469 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(int v) {
/*  474 */     int[] value = this.value;
/*  475 */     double[] key = this.key;
/*  476 */     if (this.containsNullKey && value[this.n] == v)
/*  477 */       return true; 
/*  478 */     for (int i = this.n; i-- != 0;) {
/*  479 */       if (Double.doubleToLongBits(key[i]) != 0L && value[i] == v)
/*  480 */         return true; 
/*  481 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getOrDefault(double k, int defaultValue) {
/*  487 */     if (this.strategy.equals(k, 0.0D)) {
/*  488 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  490 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  493 */     if (Double.doubleToLongBits(
/*  494 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  495 */       return defaultValue; 
/*  496 */     if (this.strategy.equals(k, curr)) {
/*  497 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  500 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  501 */         return defaultValue; 
/*  502 */       if (this.strategy.equals(k, curr)) {
/*  503 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public int putIfAbsent(double k, int v) {
/*  509 */     int pos = find(k);
/*  510 */     if (pos >= 0)
/*  511 */       return this.value[pos]; 
/*  512 */     insert(-pos - 1, k, v);
/*  513 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(double k, int v) {
/*  519 */     if (this.strategy.equals(k, 0.0D)) {
/*  520 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  521 */         removeNullEntry();
/*  522 */         return true;
/*      */       } 
/*  524 */       return false;
/*      */     } 
/*      */     
/*  527 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  530 */     if (Double.doubleToLongBits(
/*  531 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  532 */       return false; 
/*  533 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  534 */       removeEntry(pos);
/*  535 */       return true;
/*      */     } 
/*      */     while (true) {
/*  538 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  539 */         return false; 
/*  540 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  541 */         removeEntry(pos);
/*  542 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(double k, int oldValue, int v) {
/*  549 */     int pos = find(k);
/*  550 */     if (pos < 0 || oldValue != this.value[pos])
/*  551 */       return false; 
/*  552 */     this.value[pos] = v;
/*  553 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public int replace(double k, int v) {
/*  558 */     int pos = find(k);
/*  559 */     if (pos < 0)
/*  560 */       return this.defRetValue; 
/*  561 */     int oldValue = this.value[pos];
/*  562 */     this.value[pos] = v;
/*  563 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public int computeIfAbsent(double k, DoubleToIntFunction mappingFunction) {
/*  568 */     Objects.requireNonNull(mappingFunction);
/*  569 */     int pos = find(k);
/*  570 */     if (pos >= 0)
/*  571 */       return this.value[pos]; 
/*  572 */     int newValue = mappingFunction.applyAsInt(k);
/*  573 */     insert(-pos - 1, k, newValue);
/*  574 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int computeIfAbsentNullable(double k, DoubleFunction<? extends Integer> mappingFunction) {
/*  580 */     Objects.requireNonNull(mappingFunction);
/*  581 */     int pos = find(k);
/*  582 */     if (pos >= 0)
/*  583 */       return this.value[pos]; 
/*  584 */     Integer newValue = mappingFunction.apply(k);
/*  585 */     if (newValue == null)
/*  586 */       return this.defRetValue; 
/*  587 */     int v = newValue.intValue();
/*  588 */     insert(-pos - 1, k, v);
/*  589 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int computeIfPresent(double k, BiFunction<? super Double, ? super Integer, ? extends Integer> remappingFunction) {
/*  595 */     Objects.requireNonNull(remappingFunction);
/*  596 */     int pos = find(k);
/*  597 */     if (pos < 0)
/*  598 */       return this.defRetValue; 
/*  599 */     Integer newValue = remappingFunction.apply(Double.valueOf(k), Integer.valueOf(this.value[pos]));
/*  600 */     if (newValue == null) {
/*  601 */       if (this.strategy.equals(k, 0.0D)) {
/*  602 */         removeNullEntry();
/*      */       } else {
/*  604 */         removeEntry(pos);
/*  605 */       }  return this.defRetValue;
/*      */     } 
/*  607 */     this.value[pos] = newValue.intValue(); return newValue.intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int compute(double k, BiFunction<? super Double, ? super Integer, ? extends Integer> remappingFunction) {
/*  613 */     Objects.requireNonNull(remappingFunction);
/*  614 */     int pos = find(k);
/*  615 */     Integer newValue = remappingFunction.apply(Double.valueOf(k), 
/*  616 */         (pos >= 0) ? Integer.valueOf(this.value[pos]) : null);
/*  617 */     if (newValue == null) {
/*  618 */       if (pos >= 0)
/*  619 */         if (this.strategy.equals(k, 0.0D)) {
/*  620 */           removeNullEntry();
/*      */         } else {
/*  622 */           removeEntry(pos);
/*      */         }  
/*  624 */       return this.defRetValue;
/*      */     } 
/*  626 */     int newVal = newValue.intValue();
/*  627 */     if (pos < 0) {
/*  628 */       insert(-pos - 1, k, newVal);
/*  629 */       return newVal;
/*      */     } 
/*  631 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int merge(double k, int v, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
/*  637 */     Objects.requireNonNull(remappingFunction);
/*  638 */     int pos = find(k);
/*  639 */     if (pos < 0) {
/*  640 */       insert(-pos - 1, k, v);
/*  641 */       return v;
/*      */     } 
/*  643 */     Integer newValue = remappingFunction.apply(Integer.valueOf(this.value[pos]), Integer.valueOf(v));
/*  644 */     if (newValue == null) {
/*  645 */       if (this.strategy.equals(k, 0.0D)) {
/*  646 */         removeNullEntry();
/*      */       } else {
/*  648 */         removeEntry(pos);
/*  649 */       }  return this.defRetValue;
/*      */     } 
/*  651 */     this.value[pos] = newValue.intValue(); return newValue.intValue();
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
/*  662 */     if (this.size == 0)
/*      */       return; 
/*  664 */     this.size = 0;
/*  665 */     this.containsNullKey = false;
/*  666 */     Arrays.fill(this.key, 0.0D);
/*      */   }
/*      */   
/*      */   public int size() {
/*  670 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  674 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Double2IntMap.Entry, Map.Entry<Double, Integer>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  686 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public double getDoubleKey() {
/*  692 */       return Double2IntOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public int getIntValue() {
/*  696 */       return Double2IntOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public int setValue(int v) {
/*  700 */       int oldValue = Double2IntOpenCustomHashMap.this.value[this.index];
/*  701 */       Double2IntOpenCustomHashMap.this.value[this.index] = v;
/*  702 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double getKey() {
/*  712 */       return Double.valueOf(Double2IntOpenCustomHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer getValue() {
/*  722 */       return Integer.valueOf(Double2IntOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer setValue(Integer v) {
/*  732 */       return Integer.valueOf(setValue(v.intValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  737 */       if (!(o instanceof Map.Entry))
/*  738 */         return false; 
/*  739 */       Map.Entry<Double, Integer> e = (Map.Entry<Double, Integer>)o;
/*  740 */       return (Double2IntOpenCustomHashMap.this.strategy.equals(Double2IntOpenCustomHashMap.this.key[this.index], ((Double)e.getKey()).doubleValue()) && Double2IntOpenCustomHashMap.this.value[this.index] == ((Integer)e
/*  741 */         .getValue()).intValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  745 */       return Double2IntOpenCustomHashMap.this.strategy.hashCode(Double2IntOpenCustomHashMap.this.key[this.index]) ^ Double2IntOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/*  749 */       return Double2IntOpenCustomHashMap.this.key[this.index] + "=>" + Double2IntOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  759 */     int pos = Double2IntOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  766 */     int last = -1;
/*      */     
/*  768 */     int c = Double2IntOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  772 */     boolean mustReturnNullKey = Double2IntOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     DoubleArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  779 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  782 */       if (!hasNext())
/*  783 */         throw new NoSuchElementException(); 
/*  784 */       this.c--;
/*  785 */       if (this.mustReturnNullKey) {
/*  786 */         this.mustReturnNullKey = false;
/*  787 */         return this.last = Double2IntOpenCustomHashMap.this.n;
/*      */       } 
/*  789 */       double[] key = Double2IntOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  791 */         if (--this.pos < 0) {
/*      */           
/*  793 */           this.last = Integer.MIN_VALUE;
/*  794 */           double k = this.wrapped.getDouble(-this.pos - 1);
/*  795 */           int p = HashCommon.mix(Double2IntOpenCustomHashMap.this.strategy.hashCode(k)) & Double2IntOpenCustomHashMap.this.mask;
/*  796 */           while (!Double2IntOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  797 */             p = p + 1 & Double2IntOpenCustomHashMap.this.mask; 
/*  798 */           return p;
/*      */         } 
/*  800 */         if (Double.doubleToLongBits(key[this.pos]) != 0L) {
/*  801 */           return this.last = this.pos;
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
/*  815 */       double[] key = Double2IntOpenCustomHashMap.this.key; while (true) {
/*      */         double curr; int last;
/*  817 */         pos = (last = pos) + 1 & Double2IntOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  819 */           if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/*  820 */             key[last] = 0.0D;
/*      */             return;
/*      */           } 
/*  823 */           int slot = HashCommon.mix(Double2IntOpenCustomHashMap.this.strategy.hashCode(curr)) & Double2IntOpenCustomHashMap.this.mask;
/*  824 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  826 */           pos = pos + 1 & Double2IntOpenCustomHashMap.this.mask;
/*      */         } 
/*  828 */         if (pos < last) {
/*  829 */           if (this.wrapped == null)
/*  830 */             this.wrapped = new DoubleArrayList(2); 
/*  831 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  833 */         key[last] = curr;
/*  834 */         Double2IntOpenCustomHashMap.this.value[last] = Double2IntOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  838 */       if (this.last == -1)
/*  839 */         throw new IllegalStateException(); 
/*  840 */       if (this.last == Double2IntOpenCustomHashMap.this.n) {
/*  841 */         Double2IntOpenCustomHashMap.this.containsNullKey = false;
/*  842 */       } else if (this.pos >= 0) {
/*  843 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  846 */         Double2IntOpenCustomHashMap.this.remove(this.wrapped.getDouble(-this.pos - 1));
/*  847 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  850 */       Double2IntOpenCustomHashMap.this.size--;
/*  851 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  856 */       int i = n;
/*  857 */       while (i-- != 0 && hasNext())
/*  858 */         nextEntry(); 
/*  859 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Double2IntMap.Entry> { private Double2IntOpenCustomHashMap.MapEntry entry;
/*      */     
/*      */     public Double2IntOpenCustomHashMap.MapEntry next() {
/*  866 */       return this.entry = new Double2IntOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  870 */       super.remove();
/*  871 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Double2IntMap.Entry> { private FastEntryIterator() {
/*  875 */       this.entry = new Double2IntOpenCustomHashMap.MapEntry();
/*      */     } private final Double2IntOpenCustomHashMap.MapEntry entry;
/*      */     public Double2IntOpenCustomHashMap.MapEntry next() {
/*  878 */       this.entry.index = nextEntry();
/*  879 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Double2IntMap.Entry> implements Double2IntMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Double2IntMap.Entry> iterator() {
/*  885 */       return new Double2IntOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Double2IntMap.Entry> fastIterator() {
/*  889 */       return new Double2IntOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  894 */       if (!(o instanceof Map.Entry))
/*  895 */         return false; 
/*  896 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  897 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/*  898 */         return false; 
/*  899 */       if (e.getValue() == null || !(e.getValue() instanceof Integer))
/*  900 */         return false; 
/*  901 */       double k = ((Double)e.getKey()).doubleValue();
/*  902 */       int v = ((Integer)e.getValue()).intValue();
/*  903 */       if (Double2IntOpenCustomHashMap.this.strategy.equals(k, 0.0D)) {
/*  904 */         return (Double2IntOpenCustomHashMap.this.containsNullKey && Double2IntOpenCustomHashMap.this.value[Double2IntOpenCustomHashMap.this.n] == v);
/*      */       }
/*  906 */       double[] key = Double2IntOpenCustomHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/*  909 */       if (Double.doubleToLongBits(
/*  910 */           curr = key[pos = HashCommon.mix(Double2IntOpenCustomHashMap.this.strategy.hashCode(k)) & Double2IntOpenCustomHashMap.this.mask]) == 0L)
/*  911 */         return false; 
/*  912 */       if (Double2IntOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  913 */         return (Double2IntOpenCustomHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  916 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2IntOpenCustomHashMap.this.mask]) == 0L)
/*  917 */           return false; 
/*  918 */         if (Double2IntOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  919 */           return (Double2IntOpenCustomHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  925 */       if (!(o instanceof Map.Entry))
/*  926 */         return false; 
/*  927 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  928 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/*  929 */         return false; 
/*  930 */       if (e.getValue() == null || !(e.getValue() instanceof Integer))
/*  931 */         return false; 
/*  932 */       double k = ((Double)e.getKey()).doubleValue();
/*  933 */       int v = ((Integer)e.getValue()).intValue();
/*  934 */       if (Double2IntOpenCustomHashMap.this.strategy.equals(k, 0.0D)) {
/*  935 */         if (Double2IntOpenCustomHashMap.this.containsNullKey && Double2IntOpenCustomHashMap.this.value[Double2IntOpenCustomHashMap.this.n] == v) {
/*  936 */           Double2IntOpenCustomHashMap.this.removeNullEntry();
/*  937 */           return true;
/*      */         } 
/*  939 */         return false;
/*      */       } 
/*      */       
/*  942 */       double[] key = Double2IntOpenCustomHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/*  945 */       if (Double.doubleToLongBits(
/*  946 */           curr = key[pos = HashCommon.mix(Double2IntOpenCustomHashMap.this.strategy.hashCode(k)) & Double2IntOpenCustomHashMap.this.mask]) == 0L)
/*  947 */         return false; 
/*  948 */       if (Double2IntOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  949 */         if (Double2IntOpenCustomHashMap.this.value[pos] == v) {
/*  950 */           Double2IntOpenCustomHashMap.this.removeEntry(pos);
/*  951 */           return true;
/*      */         } 
/*  953 */         return false;
/*      */       } 
/*      */       while (true) {
/*  956 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2IntOpenCustomHashMap.this.mask]) == 0L)
/*  957 */           return false; 
/*  958 */         if (Double2IntOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  959 */           Double2IntOpenCustomHashMap.this.value[pos] == v) {
/*  960 */           Double2IntOpenCustomHashMap.this.removeEntry(pos);
/*  961 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  968 */       return Double2IntOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  972 */       Double2IntOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Double2IntMap.Entry> consumer) {
/*  977 */       if (Double2IntOpenCustomHashMap.this.containsNullKey)
/*  978 */         consumer.accept(new AbstractDouble2IntMap.BasicEntry(Double2IntOpenCustomHashMap.this.key[Double2IntOpenCustomHashMap.this.n], Double2IntOpenCustomHashMap.this.value[Double2IntOpenCustomHashMap.this.n])); 
/*  979 */       for (int pos = Double2IntOpenCustomHashMap.this.n; pos-- != 0;) {
/*  980 */         if (Double.doubleToLongBits(Double2IntOpenCustomHashMap.this.key[pos]) != 0L)
/*  981 */           consumer.accept(new AbstractDouble2IntMap.BasicEntry(Double2IntOpenCustomHashMap.this.key[pos], Double2IntOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Double2IntMap.Entry> consumer) {
/*  986 */       AbstractDouble2IntMap.BasicEntry entry = new AbstractDouble2IntMap.BasicEntry();
/*  987 */       if (Double2IntOpenCustomHashMap.this.containsNullKey) {
/*  988 */         entry.key = Double2IntOpenCustomHashMap.this.key[Double2IntOpenCustomHashMap.this.n];
/*  989 */         entry.value = Double2IntOpenCustomHashMap.this.value[Double2IntOpenCustomHashMap.this.n];
/*  990 */         consumer.accept(entry);
/*      */       } 
/*  992 */       for (int pos = Double2IntOpenCustomHashMap.this.n; pos-- != 0;) {
/*  993 */         if (Double.doubleToLongBits(Double2IntOpenCustomHashMap.this.key[pos]) != 0L) {
/*  994 */           entry.key = Double2IntOpenCustomHashMap.this.key[pos];
/*  995 */           entry.value = Double2IntOpenCustomHashMap.this.value[pos];
/*  996 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Double2IntMap.FastEntrySet double2IntEntrySet() {
/* 1002 */     if (this.entries == null)
/* 1003 */       this.entries = new MapEntrySet(); 
/* 1004 */     return this.entries;
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
/* 1021 */       return Double2IntOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractDoubleSet { private KeySet() {}
/*      */     
/*      */     public DoubleIterator iterator() {
/* 1027 */       return new Double2IntOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/* 1032 */       if (Double2IntOpenCustomHashMap.this.containsNullKey)
/* 1033 */         consumer.accept(Double2IntOpenCustomHashMap.this.key[Double2IntOpenCustomHashMap.this.n]); 
/* 1034 */       for (int pos = Double2IntOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1035 */         double k = Double2IntOpenCustomHashMap.this.key[pos];
/* 1036 */         if (Double.doubleToLongBits(k) != 0L)
/* 1037 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1042 */       return Double2IntOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(double k) {
/* 1046 */       return Double2IntOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(double k) {
/* 1050 */       int oldSize = Double2IntOpenCustomHashMap.this.size;
/* 1051 */       Double2IntOpenCustomHashMap.this.remove(k);
/* 1052 */       return (Double2IntOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1056 */       Double2IntOpenCustomHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public DoubleSet keySet() {
/* 1061 */     if (this.keys == null)
/* 1062 */       this.keys = new KeySet(); 
/* 1063 */     return this.keys;
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
/* 1080 */       return Double2IntOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public IntCollection values() {
/* 1085 */     if (this.values == null)
/* 1086 */       this.values = (IntCollection)new AbstractIntCollection()
/*      */         {
/*      */           public IntIterator iterator() {
/* 1089 */             return new Double2IntOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1093 */             return Double2IntOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(int v) {
/* 1097 */             return Double2IntOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1101 */             Double2IntOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(IntConsumer consumer)
/*      */           {
/* 1106 */             if (Double2IntOpenCustomHashMap.this.containsNullKey)
/* 1107 */               consumer.accept(Double2IntOpenCustomHashMap.this.value[Double2IntOpenCustomHashMap.this.n]); 
/* 1108 */             for (int pos = Double2IntOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1109 */               if (Double.doubleToLongBits(Double2IntOpenCustomHashMap.this.key[pos]) != 0L)
/* 1110 */                 consumer.accept(Double2IntOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1113 */     return this.values;
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
/* 1130 */     return trim(this.size);
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
/* 1154 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1155 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1156 */       return true; 
/*      */     try {
/* 1158 */       rehash(l);
/* 1159 */     } catch (OutOfMemoryError cantDoIt) {
/* 1160 */       return false;
/*      */     } 
/* 1162 */     return true;
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
/* 1178 */     double[] key = this.key;
/* 1179 */     int[] value = this.value;
/* 1180 */     int mask = newN - 1;
/* 1181 */     double[] newKey = new double[newN + 1];
/* 1182 */     int[] newValue = new int[newN + 1];
/* 1183 */     int i = this.n;
/* 1184 */     for (int j = realSize(); j-- != 0; ) {
/* 1185 */       while (Double.doubleToLongBits(key[--i]) == 0L); int pos;
/* 1186 */       if (Double.doubleToLongBits(newKey[
/* 1187 */             pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask]) != 0L)
/* 1188 */         while (Double.doubleToLongBits(newKey[pos = pos + 1 & mask]) != 0L); 
/* 1189 */       newKey[pos] = key[i];
/* 1190 */       newValue[pos] = value[i];
/*      */     } 
/* 1192 */     newValue[newN] = value[this.n];
/* 1193 */     this.n = newN;
/* 1194 */     this.mask = mask;
/* 1195 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1196 */     this.key = newKey;
/* 1197 */     this.value = newValue;
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
/*      */   public Double2IntOpenCustomHashMap clone() {
/*      */     Double2IntOpenCustomHashMap c;
/*      */     try {
/* 1214 */       c = (Double2IntOpenCustomHashMap)super.clone();
/* 1215 */     } catch (CloneNotSupportedException cantHappen) {
/* 1216 */       throw new InternalError();
/*      */     } 
/* 1218 */     c.keys = null;
/* 1219 */     c.values = null;
/* 1220 */     c.entries = null;
/* 1221 */     c.containsNullKey = this.containsNullKey;
/* 1222 */     c.key = (double[])this.key.clone();
/* 1223 */     c.value = (int[])this.value.clone();
/* 1224 */     c.strategy = this.strategy;
/* 1225 */     return c;
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
/* 1238 */     int h = 0;
/* 1239 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1240 */       while (Double.doubleToLongBits(this.key[i]) == 0L)
/* 1241 */         i++; 
/* 1242 */       t = this.strategy.hashCode(this.key[i]);
/* 1243 */       t ^= this.value[i];
/* 1244 */       h += t;
/* 1245 */       i++;
/*      */     } 
/*      */     
/* 1248 */     if (this.containsNullKey)
/* 1249 */       h += this.value[this.n]; 
/* 1250 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1253 */     double[] key = this.key;
/* 1254 */     int[] value = this.value;
/* 1255 */     MapIterator i = new MapIterator();
/* 1256 */     s.defaultWriteObject();
/* 1257 */     for (int j = this.size; j-- != 0; ) {
/* 1258 */       int e = i.nextEntry();
/* 1259 */       s.writeDouble(key[e]);
/* 1260 */       s.writeInt(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1265 */     s.defaultReadObject();
/* 1266 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1267 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1268 */     this.mask = this.n - 1;
/* 1269 */     double[] key = this.key = new double[this.n + 1];
/* 1270 */     int[] value = this.value = new int[this.n + 1];
/*      */ 
/*      */     
/* 1273 */     for (int i = this.size; i-- != 0; ) {
/* 1274 */       int pos; double k = s.readDouble();
/* 1275 */       int v = s.readInt();
/* 1276 */       if (this.strategy.equals(k, 0.0D)) {
/* 1277 */         pos = this.n;
/* 1278 */         this.containsNullKey = true;
/*      */       } else {
/* 1280 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1281 */         while (Double.doubleToLongBits(key[pos]) != 0L)
/* 1282 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1284 */       key[pos] = k;
/* 1285 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2IntOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */