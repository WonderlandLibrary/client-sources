/*      */ package it.unimi.dsi.fastutil.doubles;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanIterator;
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
/*      */ import java.util.function.DoublePredicate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Double2BooleanOpenCustomHashMap
/*      */   extends AbstractDouble2BooleanMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient double[] key;
/*      */   protected transient boolean[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected DoubleHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Double2BooleanMap.FastEntrySet entries;
/*      */   protected transient DoubleSet keys;
/*      */   protected transient BooleanCollection values;
/*      */   
/*      */   public Double2BooleanOpenCustomHashMap(int expected, float f, DoubleHash.Strategy strategy) {
/*  108 */     this.strategy = strategy;
/*  109 */     if (f <= 0.0F || f > 1.0F)
/*  110 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  111 */     if (expected < 0)
/*  112 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  113 */     this.f = f;
/*  114 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  115 */     this.mask = this.n - 1;
/*  116 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  117 */     this.key = new double[this.n + 1];
/*  118 */     this.value = new boolean[this.n + 1];
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
/*      */   public Double2BooleanOpenCustomHashMap(int expected, DoubleHash.Strategy strategy) {
/*  130 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2BooleanOpenCustomHashMap(DoubleHash.Strategy strategy) {
/*  141 */     this(16, 0.75F, strategy);
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
/*      */   public Double2BooleanOpenCustomHashMap(Map<? extends Double, ? extends Boolean> m, float f, DoubleHash.Strategy strategy) {
/*  155 */     this(m.size(), f, strategy);
/*  156 */     putAll(m);
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
/*      */   public Double2BooleanOpenCustomHashMap(Map<? extends Double, ? extends Boolean> m, DoubleHash.Strategy strategy) {
/*  169 */     this(m, 0.75F, strategy);
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
/*      */   public Double2BooleanOpenCustomHashMap(Double2BooleanMap m, float f, DoubleHash.Strategy strategy) {
/*  183 */     this(m.size(), f, strategy);
/*  184 */     putAll(m);
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
/*      */   public Double2BooleanOpenCustomHashMap(Double2BooleanMap m, DoubleHash.Strategy strategy) {
/*  197 */     this(m, 0.75F, strategy);
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
/*      */   public Double2BooleanOpenCustomHashMap(double[] k, boolean[] v, float f, DoubleHash.Strategy strategy) {
/*  215 */     this(k.length, f, strategy);
/*  216 */     if (k.length != v.length) {
/*  217 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  219 */     for (int i = 0; i < k.length; i++) {
/*  220 */       put(k[i], v[i]);
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
/*      */   public Double2BooleanOpenCustomHashMap(double[] k, boolean[] v, DoubleHash.Strategy strategy) {
/*  237 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleHash.Strategy strategy() {
/*  245 */     return this.strategy;
/*      */   }
/*      */   private int realSize() {
/*  248 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  251 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  252 */     if (needed > this.n)
/*  253 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  256 */     int needed = (int)Math.min(1073741824L, 
/*  257 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  258 */     if (needed > this.n)
/*  259 */       rehash(needed); 
/*      */   }
/*      */   private boolean removeEntry(int pos) {
/*  262 */     boolean oldValue = this.value[pos];
/*  263 */     this.size--;
/*  264 */     shiftKeys(pos);
/*  265 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  266 */       rehash(this.n / 2); 
/*  267 */     return oldValue;
/*      */   }
/*      */   private boolean removeNullEntry() {
/*  270 */     this.containsNullKey = false;
/*  271 */     boolean oldValue = this.value[this.n];
/*  272 */     this.size--;
/*  273 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  274 */       rehash(this.n / 2); 
/*  275 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Double, ? extends Boolean> m) {
/*  279 */     if (this.f <= 0.5D) {
/*  280 */       ensureCapacity(m.size());
/*      */     } else {
/*  282 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  284 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(double k) {
/*  288 */     if (this.strategy.equals(k, 0.0D)) {
/*  289 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  291 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  294 */     if (Double.doubleToLongBits(
/*  295 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  296 */       return -(pos + 1); 
/*  297 */     if (this.strategy.equals(k, curr)) {
/*  298 */       return pos;
/*      */     }
/*      */     while (true) {
/*  301 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  302 */         return -(pos + 1); 
/*  303 */       if (this.strategy.equals(k, curr))
/*  304 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, double k, boolean v) {
/*  308 */     if (pos == this.n)
/*  309 */       this.containsNullKey = true; 
/*  310 */     this.key[pos] = k;
/*  311 */     this.value[pos] = v;
/*  312 */     if (this.size++ >= this.maxFill) {
/*  313 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean put(double k, boolean v) {
/*  319 */     int pos = find(k);
/*  320 */     if (pos < 0) {
/*  321 */       insert(-pos - 1, k, v);
/*  322 */       return this.defRetValue;
/*      */     } 
/*  324 */     boolean oldValue = this.value[pos];
/*  325 */     this.value[pos] = v;
/*  326 */     return oldValue;
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
/*  339 */     double[] key = this.key; while (true) {
/*      */       double curr; int last;
/*  341 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  343 */         if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/*  344 */           key[last] = 0.0D;
/*      */           return;
/*      */         } 
/*  347 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  348 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  350 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  352 */       key[last] = curr;
/*  353 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean remove(double k) {
/*  359 */     if (this.strategy.equals(k, 0.0D)) {
/*  360 */       if (this.containsNullKey)
/*  361 */         return removeNullEntry(); 
/*  362 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  365 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  368 */     if (Double.doubleToLongBits(
/*  369 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  370 */       return this.defRetValue; 
/*  371 */     if (this.strategy.equals(k, curr))
/*  372 */       return removeEntry(pos); 
/*      */     while (true) {
/*  374 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  375 */         return this.defRetValue; 
/*  376 */       if (this.strategy.equals(k, curr)) {
/*  377 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean get(double k) {
/*  383 */     if (this.strategy.equals(k, 0.0D)) {
/*  384 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  386 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  389 */     if (Double.doubleToLongBits(
/*  390 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  391 */       return this.defRetValue; 
/*  392 */     if (this.strategy.equals(k, curr)) {
/*  393 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  396 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  397 */         return this.defRetValue; 
/*  398 */       if (this.strategy.equals(k, curr)) {
/*  399 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(double k) {
/*  405 */     if (this.strategy.equals(k, 0.0D)) {
/*  406 */       return this.containsNullKey;
/*      */     }
/*  408 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  411 */     if (Double.doubleToLongBits(
/*  412 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  413 */       return false; 
/*  414 */     if (this.strategy.equals(k, curr)) {
/*  415 */       return true;
/*      */     }
/*      */     while (true) {
/*  418 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  419 */         return false; 
/*  420 */       if (this.strategy.equals(k, curr))
/*  421 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(boolean v) {
/*  426 */     boolean[] value = this.value;
/*  427 */     double[] key = this.key;
/*  428 */     if (this.containsNullKey && value[this.n] == v)
/*  429 */       return true; 
/*  430 */     for (int i = this.n; i-- != 0;) {
/*  431 */       if (Double.doubleToLongBits(key[i]) != 0L && value[i] == v)
/*  432 */         return true; 
/*  433 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getOrDefault(double k, boolean defaultValue) {
/*  439 */     if (this.strategy.equals(k, 0.0D)) {
/*  440 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  442 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  445 */     if (Double.doubleToLongBits(
/*  446 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  447 */       return defaultValue; 
/*  448 */     if (this.strategy.equals(k, curr)) {
/*  449 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  452 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  453 */         return defaultValue; 
/*  454 */       if (this.strategy.equals(k, curr)) {
/*  455 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean putIfAbsent(double k, boolean v) {
/*  461 */     int pos = find(k);
/*  462 */     if (pos >= 0)
/*  463 */       return this.value[pos]; 
/*  464 */     insert(-pos - 1, k, v);
/*  465 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(double k, boolean v) {
/*  471 */     if (this.strategy.equals(k, 0.0D)) {
/*  472 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  473 */         removeNullEntry();
/*  474 */         return true;
/*      */       } 
/*  476 */       return false;
/*      */     } 
/*      */     
/*  479 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  482 */     if (Double.doubleToLongBits(
/*  483 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  484 */       return false; 
/*  485 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  486 */       removeEntry(pos);
/*  487 */       return true;
/*      */     } 
/*      */     while (true) {
/*  490 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  491 */         return false; 
/*  492 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  493 */         removeEntry(pos);
/*  494 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(double k, boolean oldValue, boolean v) {
/*  501 */     int pos = find(k);
/*  502 */     if (pos < 0 || oldValue != this.value[pos])
/*  503 */       return false; 
/*  504 */     this.value[pos] = v;
/*  505 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(double k, boolean v) {
/*  510 */     int pos = find(k);
/*  511 */     if (pos < 0)
/*  512 */       return this.defRetValue; 
/*  513 */     boolean oldValue = this.value[pos];
/*  514 */     this.value[pos] = v;
/*  515 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean computeIfAbsent(double k, DoublePredicate mappingFunction) {
/*  520 */     Objects.requireNonNull(mappingFunction);
/*  521 */     int pos = find(k);
/*  522 */     if (pos >= 0)
/*  523 */       return this.value[pos]; 
/*  524 */     boolean newValue = mappingFunction.test(k);
/*  525 */     insert(-pos - 1, k, newValue);
/*  526 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeIfAbsentNullable(double k, DoubleFunction<? extends Boolean> mappingFunction) {
/*  532 */     Objects.requireNonNull(mappingFunction);
/*  533 */     int pos = find(k);
/*  534 */     if (pos >= 0)
/*  535 */       return this.value[pos]; 
/*  536 */     Boolean newValue = mappingFunction.apply(k);
/*  537 */     if (newValue == null)
/*  538 */       return this.defRetValue; 
/*  539 */     boolean v = newValue.booleanValue();
/*  540 */     insert(-pos - 1, k, v);
/*  541 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeIfPresent(double k, BiFunction<? super Double, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  547 */     Objects.requireNonNull(remappingFunction);
/*  548 */     int pos = find(k);
/*  549 */     if (pos < 0)
/*  550 */       return this.defRetValue; 
/*  551 */     Boolean newValue = remappingFunction.apply(Double.valueOf(k), Boolean.valueOf(this.value[pos]));
/*  552 */     if (newValue == null) {
/*  553 */       if (this.strategy.equals(k, 0.0D)) {
/*  554 */         removeNullEntry();
/*      */       } else {
/*  556 */         removeEntry(pos);
/*  557 */       }  return this.defRetValue;
/*      */     } 
/*  559 */     this.value[pos] = newValue.booleanValue(); return newValue.booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean compute(double k, BiFunction<? super Double, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  565 */     Objects.requireNonNull(remappingFunction);
/*  566 */     int pos = find(k);
/*  567 */     Boolean newValue = remappingFunction.apply(Double.valueOf(k), 
/*  568 */         (pos >= 0) ? Boolean.valueOf(this.value[pos]) : null);
/*  569 */     if (newValue == null) {
/*  570 */       if (pos >= 0)
/*  571 */         if (this.strategy.equals(k, 0.0D)) {
/*  572 */           removeNullEntry();
/*      */         } else {
/*  574 */           removeEntry(pos);
/*      */         }  
/*  576 */       return this.defRetValue;
/*      */     } 
/*  578 */     boolean newVal = newValue.booleanValue();
/*  579 */     if (pos < 0) {
/*  580 */       insert(-pos - 1, k, newVal);
/*  581 */       return newVal;
/*      */     } 
/*  583 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean merge(double k, boolean v, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  589 */     Objects.requireNonNull(remappingFunction);
/*  590 */     int pos = find(k);
/*  591 */     if (pos < 0) {
/*  592 */       insert(-pos - 1, k, v);
/*  593 */       return v;
/*      */     } 
/*  595 */     Boolean newValue = remappingFunction.apply(Boolean.valueOf(this.value[pos]), Boolean.valueOf(v));
/*  596 */     if (newValue == null) {
/*  597 */       if (this.strategy.equals(k, 0.0D)) {
/*  598 */         removeNullEntry();
/*      */       } else {
/*  600 */         removeEntry(pos);
/*  601 */       }  return this.defRetValue;
/*      */     } 
/*  603 */     this.value[pos] = newValue.booleanValue(); return newValue.booleanValue();
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
/*  614 */     if (this.size == 0)
/*      */       return; 
/*  616 */     this.size = 0;
/*  617 */     this.containsNullKey = false;
/*  618 */     Arrays.fill(this.key, 0.0D);
/*      */   }
/*      */   
/*      */   public int size() {
/*  622 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  626 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Double2BooleanMap.Entry, Map.Entry<Double, Boolean>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  638 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public double getDoubleKey() {
/*  644 */       return Double2BooleanOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public boolean getBooleanValue() {
/*  648 */       return Double2BooleanOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public boolean setValue(boolean v) {
/*  652 */       boolean oldValue = Double2BooleanOpenCustomHashMap.this.value[this.index];
/*  653 */       Double2BooleanOpenCustomHashMap.this.value[this.index] = v;
/*  654 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double getKey() {
/*  664 */       return Double.valueOf(Double2BooleanOpenCustomHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean getValue() {
/*  674 */       return Boolean.valueOf(Double2BooleanOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean setValue(Boolean v) {
/*  684 */       return Boolean.valueOf(setValue(v.booleanValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  689 */       if (!(o instanceof Map.Entry))
/*  690 */         return false; 
/*  691 */       Map.Entry<Double, Boolean> e = (Map.Entry<Double, Boolean>)o;
/*  692 */       return (Double2BooleanOpenCustomHashMap.this.strategy.equals(Double2BooleanOpenCustomHashMap.this.key[this.index], ((Double)e.getKey()).doubleValue()) && Double2BooleanOpenCustomHashMap.this.value[this.index] == ((Boolean)e
/*  693 */         .getValue()).booleanValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  697 */       return Double2BooleanOpenCustomHashMap.this.strategy.hashCode(Double2BooleanOpenCustomHashMap.this.key[this.index]) ^ (Double2BooleanOpenCustomHashMap.this.value[this.index] ? 1231 : 1237);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  701 */       return Double2BooleanOpenCustomHashMap.this.key[this.index] + "=>" + Double2BooleanOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  711 */     int pos = Double2BooleanOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  718 */     int last = -1;
/*      */     
/*  720 */     int c = Double2BooleanOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  724 */     boolean mustReturnNullKey = Double2BooleanOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     DoubleArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  731 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  734 */       if (!hasNext())
/*  735 */         throw new NoSuchElementException(); 
/*  736 */       this.c--;
/*  737 */       if (this.mustReturnNullKey) {
/*  738 */         this.mustReturnNullKey = false;
/*  739 */         return this.last = Double2BooleanOpenCustomHashMap.this.n;
/*      */       } 
/*  741 */       double[] key = Double2BooleanOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  743 */         if (--this.pos < 0) {
/*      */           
/*  745 */           this.last = Integer.MIN_VALUE;
/*  746 */           double k = this.wrapped.getDouble(-this.pos - 1);
/*  747 */           int p = HashCommon.mix(Double2BooleanOpenCustomHashMap.this.strategy.hashCode(k)) & Double2BooleanOpenCustomHashMap.this.mask;
/*  748 */           while (!Double2BooleanOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  749 */             p = p + 1 & Double2BooleanOpenCustomHashMap.this.mask; 
/*  750 */           return p;
/*      */         } 
/*  752 */         if (Double.doubleToLongBits(key[this.pos]) != 0L) {
/*  753 */           return this.last = this.pos;
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
/*  767 */       double[] key = Double2BooleanOpenCustomHashMap.this.key; while (true) {
/*      */         double curr; int last;
/*  769 */         pos = (last = pos) + 1 & Double2BooleanOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  771 */           if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/*  772 */             key[last] = 0.0D;
/*      */             return;
/*      */           } 
/*  775 */           int slot = HashCommon.mix(Double2BooleanOpenCustomHashMap.this.strategy.hashCode(curr)) & Double2BooleanOpenCustomHashMap.this.mask;
/*  776 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  778 */           pos = pos + 1 & Double2BooleanOpenCustomHashMap.this.mask;
/*      */         } 
/*  780 */         if (pos < last) {
/*  781 */           if (this.wrapped == null)
/*  782 */             this.wrapped = new DoubleArrayList(2); 
/*  783 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  785 */         key[last] = curr;
/*  786 */         Double2BooleanOpenCustomHashMap.this.value[last] = Double2BooleanOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  790 */       if (this.last == -1)
/*  791 */         throw new IllegalStateException(); 
/*  792 */       if (this.last == Double2BooleanOpenCustomHashMap.this.n) {
/*  793 */         Double2BooleanOpenCustomHashMap.this.containsNullKey = false;
/*  794 */       } else if (this.pos >= 0) {
/*  795 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  798 */         Double2BooleanOpenCustomHashMap.this.remove(this.wrapped.getDouble(-this.pos - 1));
/*  799 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  802 */       Double2BooleanOpenCustomHashMap.this.size--;
/*  803 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  808 */       int i = n;
/*  809 */       while (i-- != 0 && hasNext())
/*  810 */         nextEntry(); 
/*  811 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Double2BooleanMap.Entry> { private Double2BooleanOpenCustomHashMap.MapEntry entry;
/*      */     
/*      */     public Double2BooleanOpenCustomHashMap.MapEntry next() {
/*  818 */       return this.entry = new Double2BooleanOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  822 */       super.remove();
/*  823 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Double2BooleanMap.Entry> { private FastEntryIterator() {
/*  827 */       this.entry = new Double2BooleanOpenCustomHashMap.MapEntry();
/*      */     } private final Double2BooleanOpenCustomHashMap.MapEntry entry;
/*      */     public Double2BooleanOpenCustomHashMap.MapEntry next() {
/*  830 */       this.entry.index = nextEntry();
/*  831 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Double2BooleanMap.Entry> implements Double2BooleanMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Double2BooleanMap.Entry> iterator() {
/*  837 */       return new Double2BooleanOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Double2BooleanMap.Entry> fastIterator() {
/*  841 */       return new Double2BooleanOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  846 */       if (!(o instanceof Map.Entry))
/*  847 */         return false; 
/*  848 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  849 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/*  850 */         return false; 
/*  851 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/*  852 */         return false; 
/*  853 */       double k = ((Double)e.getKey()).doubleValue();
/*  854 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/*  855 */       if (Double2BooleanOpenCustomHashMap.this.strategy.equals(k, 0.0D)) {
/*  856 */         return (Double2BooleanOpenCustomHashMap.this.containsNullKey && Double2BooleanOpenCustomHashMap.this.value[Double2BooleanOpenCustomHashMap.this.n] == v);
/*      */       }
/*  858 */       double[] key = Double2BooleanOpenCustomHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/*  861 */       if (Double.doubleToLongBits(
/*  862 */           curr = key[pos = HashCommon.mix(Double2BooleanOpenCustomHashMap.this.strategy.hashCode(k)) & Double2BooleanOpenCustomHashMap.this.mask]) == 0L)
/*  863 */         return false; 
/*  864 */       if (Double2BooleanOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  865 */         return (Double2BooleanOpenCustomHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  868 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2BooleanOpenCustomHashMap.this.mask]) == 0L)
/*  869 */           return false; 
/*  870 */         if (Double2BooleanOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  871 */           return (Double2BooleanOpenCustomHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  877 */       if (!(o instanceof Map.Entry))
/*  878 */         return false; 
/*  879 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  880 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/*  881 */         return false; 
/*  882 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/*  883 */         return false; 
/*  884 */       double k = ((Double)e.getKey()).doubleValue();
/*  885 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/*  886 */       if (Double2BooleanOpenCustomHashMap.this.strategy.equals(k, 0.0D)) {
/*  887 */         if (Double2BooleanOpenCustomHashMap.this.containsNullKey && Double2BooleanOpenCustomHashMap.this.value[Double2BooleanOpenCustomHashMap.this.n] == v) {
/*  888 */           Double2BooleanOpenCustomHashMap.this.removeNullEntry();
/*  889 */           return true;
/*      */         } 
/*  891 */         return false;
/*      */       } 
/*      */       
/*  894 */       double[] key = Double2BooleanOpenCustomHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/*  897 */       if (Double.doubleToLongBits(
/*  898 */           curr = key[pos = HashCommon.mix(Double2BooleanOpenCustomHashMap.this.strategy.hashCode(k)) & Double2BooleanOpenCustomHashMap.this.mask]) == 0L)
/*  899 */         return false; 
/*  900 */       if (Double2BooleanOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  901 */         if (Double2BooleanOpenCustomHashMap.this.value[pos] == v) {
/*  902 */           Double2BooleanOpenCustomHashMap.this.removeEntry(pos);
/*  903 */           return true;
/*      */         } 
/*  905 */         return false;
/*      */       } 
/*      */       while (true) {
/*  908 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2BooleanOpenCustomHashMap.this.mask]) == 0L)
/*  909 */           return false; 
/*  910 */         if (Double2BooleanOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  911 */           Double2BooleanOpenCustomHashMap.this.value[pos] == v) {
/*  912 */           Double2BooleanOpenCustomHashMap.this.removeEntry(pos);
/*  913 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  920 */       return Double2BooleanOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  924 */       Double2BooleanOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Double2BooleanMap.Entry> consumer) {
/*  929 */       if (Double2BooleanOpenCustomHashMap.this.containsNullKey)
/*  930 */         consumer.accept(new AbstractDouble2BooleanMap.BasicEntry(Double2BooleanOpenCustomHashMap.this.key[Double2BooleanOpenCustomHashMap.this.n], Double2BooleanOpenCustomHashMap.this.value[Double2BooleanOpenCustomHashMap.this.n])); 
/*  931 */       for (int pos = Double2BooleanOpenCustomHashMap.this.n; pos-- != 0;) {
/*  932 */         if (Double.doubleToLongBits(Double2BooleanOpenCustomHashMap.this.key[pos]) != 0L)
/*  933 */           consumer.accept(new AbstractDouble2BooleanMap.BasicEntry(Double2BooleanOpenCustomHashMap.this.key[pos], Double2BooleanOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Double2BooleanMap.Entry> consumer) {
/*  938 */       AbstractDouble2BooleanMap.BasicEntry entry = new AbstractDouble2BooleanMap.BasicEntry();
/*  939 */       if (Double2BooleanOpenCustomHashMap.this.containsNullKey) {
/*  940 */         entry.key = Double2BooleanOpenCustomHashMap.this.key[Double2BooleanOpenCustomHashMap.this.n];
/*  941 */         entry.value = Double2BooleanOpenCustomHashMap.this.value[Double2BooleanOpenCustomHashMap.this.n];
/*  942 */         consumer.accept(entry);
/*      */       } 
/*  944 */       for (int pos = Double2BooleanOpenCustomHashMap.this.n; pos-- != 0;) {
/*  945 */         if (Double.doubleToLongBits(Double2BooleanOpenCustomHashMap.this.key[pos]) != 0L) {
/*  946 */           entry.key = Double2BooleanOpenCustomHashMap.this.key[pos];
/*  947 */           entry.value = Double2BooleanOpenCustomHashMap.this.value[pos];
/*  948 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Double2BooleanMap.FastEntrySet double2BooleanEntrySet() {
/*  954 */     if (this.entries == null)
/*  955 */       this.entries = new MapEntrySet(); 
/*  956 */     return this.entries;
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
/*  973 */       return Double2BooleanOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractDoubleSet { private KeySet() {}
/*      */     
/*      */     public DoubleIterator iterator() {
/*  979 */       return new Double2BooleanOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/*  984 */       if (Double2BooleanOpenCustomHashMap.this.containsNullKey)
/*  985 */         consumer.accept(Double2BooleanOpenCustomHashMap.this.key[Double2BooleanOpenCustomHashMap.this.n]); 
/*  986 */       for (int pos = Double2BooleanOpenCustomHashMap.this.n; pos-- != 0; ) {
/*  987 */         double k = Double2BooleanOpenCustomHashMap.this.key[pos];
/*  988 */         if (Double.doubleToLongBits(k) != 0L)
/*  989 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  994 */       return Double2BooleanOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(double k) {
/*  998 */       return Double2BooleanOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(double k) {
/* 1002 */       int oldSize = Double2BooleanOpenCustomHashMap.this.size;
/* 1003 */       Double2BooleanOpenCustomHashMap.this.remove(k);
/* 1004 */       return (Double2BooleanOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1008 */       Double2BooleanOpenCustomHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public DoubleSet keySet() {
/* 1013 */     if (this.keys == null)
/* 1014 */       this.keys = new KeySet(); 
/* 1015 */     return this.keys;
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
/*      */     implements BooleanIterator
/*      */   {
/*      */     public boolean nextBoolean() {
/* 1032 */       return Double2BooleanOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public BooleanCollection values() {
/* 1037 */     if (this.values == null)
/* 1038 */       this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */         {
/*      */           public BooleanIterator iterator() {
/* 1041 */             return new Double2BooleanOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1045 */             return Double2BooleanOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(boolean v) {
/* 1049 */             return Double2BooleanOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1053 */             Double2BooleanOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(BooleanConsumer consumer)
/*      */           {
/* 1058 */             if (Double2BooleanOpenCustomHashMap.this.containsNullKey)
/* 1059 */               consumer.accept(Double2BooleanOpenCustomHashMap.this.value[Double2BooleanOpenCustomHashMap.this.n]); 
/* 1060 */             for (int pos = Double2BooleanOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1061 */               if (Double.doubleToLongBits(Double2BooleanOpenCustomHashMap.this.key[pos]) != 0L)
/* 1062 */                 consumer.accept(Double2BooleanOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1065 */     return this.values;
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
/* 1082 */     return trim(this.size);
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
/* 1106 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1107 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1108 */       return true; 
/*      */     try {
/* 1110 */       rehash(l);
/* 1111 */     } catch (OutOfMemoryError cantDoIt) {
/* 1112 */       return false;
/*      */     } 
/* 1114 */     return true;
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
/* 1130 */     double[] key = this.key;
/* 1131 */     boolean[] value = this.value;
/* 1132 */     int mask = newN - 1;
/* 1133 */     double[] newKey = new double[newN + 1];
/* 1134 */     boolean[] newValue = new boolean[newN + 1];
/* 1135 */     int i = this.n;
/* 1136 */     for (int j = realSize(); j-- != 0; ) {
/* 1137 */       while (Double.doubleToLongBits(key[--i]) == 0L); int pos;
/* 1138 */       if (Double.doubleToLongBits(newKey[
/* 1139 */             pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask]) != 0L)
/* 1140 */         while (Double.doubleToLongBits(newKey[pos = pos + 1 & mask]) != 0L); 
/* 1141 */       newKey[pos] = key[i];
/* 1142 */       newValue[pos] = value[i];
/*      */     } 
/* 1144 */     newValue[newN] = value[this.n];
/* 1145 */     this.n = newN;
/* 1146 */     this.mask = mask;
/* 1147 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1148 */     this.key = newKey;
/* 1149 */     this.value = newValue;
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
/*      */   public Double2BooleanOpenCustomHashMap clone() {
/*      */     Double2BooleanOpenCustomHashMap c;
/*      */     try {
/* 1166 */       c = (Double2BooleanOpenCustomHashMap)super.clone();
/* 1167 */     } catch (CloneNotSupportedException cantHappen) {
/* 1168 */       throw new InternalError();
/*      */     } 
/* 1170 */     c.keys = null;
/* 1171 */     c.values = null;
/* 1172 */     c.entries = null;
/* 1173 */     c.containsNullKey = this.containsNullKey;
/* 1174 */     c.key = (double[])this.key.clone();
/* 1175 */     c.value = (boolean[])this.value.clone();
/* 1176 */     c.strategy = this.strategy;
/* 1177 */     return c;
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
/* 1190 */     int h = 0;
/* 1191 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1192 */       while (Double.doubleToLongBits(this.key[i]) == 0L)
/* 1193 */         i++; 
/* 1194 */       t = this.strategy.hashCode(this.key[i]);
/* 1195 */       t ^= this.value[i] ? 1231 : 1237;
/* 1196 */       h += t;
/* 1197 */       i++;
/*      */     } 
/*      */     
/* 1200 */     if (this.containsNullKey)
/* 1201 */       h += this.value[this.n] ? 1231 : 1237; 
/* 1202 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1205 */     double[] key = this.key;
/* 1206 */     boolean[] value = this.value;
/* 1207 */     MapIterator i = new MapIterator();
/* 1208 */     s.defaultWriteObject();
/* 1209 */     for (int j = this.size; j-- != 0; ) {
/* 1210 */       int e = i.nextEntry();
/* 1211 */       s.writeDouble(key[e]);
/* 1212 */       s.writeBoolean(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1217 */     s.defaultReadObject();
/* 1218 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1219 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1220 */     this.mask = this.n - 1;
/* 1221 */     double[] key = this.key = new double[this.n + 1];
/* 1222 */     boolean[] value = this.value = new boolean[this.n + 1];
/*      */ 
/*      */     
/* 1225 */     for (int i = this.size; i-- != 0; ) {
/* 1226 */       int pos; double k = s.readDouble();
/* 1227 */       boolean v = s.readBoolean();
/* 1228 */       if (this.strategy.equals(k, 0.0D)) {
/* 1229 */         pos = this.n;
/* 1230 */         this.containsNullKey = true;
/*      */       } else {
/* 1232 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1233 */         while (Double.doubleToLongBits(key[pos]) != 0L)
/* 1234 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1236 */       key[pos] = k;
/* 1237 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2BooleanOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */