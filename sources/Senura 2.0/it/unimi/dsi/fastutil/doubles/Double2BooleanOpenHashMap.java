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
/*      */ public class Double2BooleanOpenHashMap
/*      */   extends AbstractDouble2BooleanMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient double[] key;
/*      */   protected transient boolean[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Double2BooleanMap.FastEntrySet entries;
/*      */   protected transient DoubleSet keys;
/*      */   protected transient BooleanCollection values;
/*      */   
/*      */   public Double2BooleanOpenHashMap(int expected, float f) {
/*  101 */     if (f <= 0.0F || f > 1.0F)
/*  102 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  103 */     if (expected < 0)
/*  104 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  105 */     this.f = f;
/*  106 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  107 */     this.mask = this.n - 1;
/*  108 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  109 */     this.key = new double[this.n + 1];
/*  110 */     this.value = new boolean[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2BooleanOpenHashMap(int expected) {
/*  119 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2BooleanOpenHashMap() {
/*  127 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2BooleanOpenHashMap(Map<? extends Double, ? extends Boolean> m, float f) {
/*  138 */     this(m.size(), f);
/*  139 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2BooleanOpenHashMap(Map<? extends Double, ? extends Boolean> m) {
/*  149 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2BooleanOpenHashMap(Double2BooleanMap m, float f) {
/*  160 */     this(m.size(), f);
/*  161 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2BooleanOpenHashMap(Double2BooleanMap m) {
/*  171 */     this(m, 0.75F);
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
/*      */   public Double2BooleanOpenHashMap(double[] k, boolean[] v, float f) {
/*  186 */     this(k.length, f);
/*  187 */     if (k.length != v.length) {
/*  188 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  190 */     for (int i = 0; i < k.length; i++) {
/*  191 */       put(k[i], v[i]);
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
/*      */   public Double2BooleanOpenHashMap(double[] k, boolean[] v) {
/*  205 */     this(k, v, 0.75F);
/*      */   }
/*      */   private int realSize() {
/*  208 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  211 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  212 */     if (needed > this.n)
/*  213 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  216 */     int needed = (int)Math.min(1073741824L, 
/*  217 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  218 */     if (needed > this.n)
/*  219 */       rehash(needed); 
/*      */   }
/*      */   private boolean removeEntry(int pos) {
/*  222 */     boolean oldValue = this.value[pos];
/*  223 */     this.size--;
/*  224 */     shiftKeys(pos);
/*  225 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  226 */       rehash(this.n / 2); 
/*  227 */     return oldValue;
/*      */   }
/*      */   private boolean removeNullEntry() {
/*  230 */     this.containsNullKey = false;
/*  231 */     boolean oldValue = this.value[this.n];
/*  232 */     this.size--;
/*  233 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  234 */       rehash(this.n / 2); 
/*  235 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Double, ? extends Boolean> m) {
/*  239 */     if (this.f <= 0.5D) {
/*  240 */       ensureCapacity(m.size());
/*      */     } else {
/*  242 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  244 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(double k) {
/*  248 */     if (Double.doubleToLongBits(k) == 0L) {
/*  249 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  251 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  254 */     if (Double.doubleToLongBits(
/*  255 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  257 */       return -(pos + 1); } 
/*  258 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  259 */       return pos;
/*      */     }
/*      */     while (true) {
/*  262 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  263 */         return -(pos + 1); 
/*  264 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr))
/*  265 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, double k, boolean v) {
/*  269 */     if (pos == this.n)
/*  270 */       this.containsNullKey = true; 
/*  271 */     this.key[pos] = k;
/*  272 */     this.value[pos] = v;
/*  273 */     if (this.size++ >= this.maxFill) {
/*  274 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean put(double k, boolean v) {
/*  280 */     int pos = find(k);
/*  281 */     if (pos < 0) {
/*  282 */       insert(-pos - 1, k, v);
/*  283 */       return this.defRetValue;
/*      */     } 
/*  285 */     boolean oldValue = this.value[pos];
/*  286 */     this.value[pos] = v;
/*  287 */     return oldValue;
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
/*  300 */     double[] key = this.key; while (true) {
/*      */       double curr; int last;
/*  302 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  304 */         if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/*  305 */           key[last] = 0.0D;
/*      */           return;
/*      */         } 
/*  308 */         int slot = (int)HashCommon.mix(Double.doubleToRawLongBits(curr)) & this.mask;
/*  309 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  311 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  313 */       key[last] = curr;
/*  314 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean remove(double k) {
/*  320 */     if (Double.doubleToLongBits(k) == 0L) {
/*  321 */       if (this.containsNullKey)
/*  322 */         return removeNullEntry(); 
/*  323 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  326 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  329 */     if (Double.doubleToLongBits(
/*  330 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  332 */       return this.defRetValue; } 
/*  333 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr))
/*  334 */       return removeEntry(pos); 
/*      */     while (true) {
/*  336 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  337 */         return this.defRetValue; 
/*  338 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  339 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean get(double k) {
/*  345 */     if (Double.doubleToLongBits(k) == 0L) {
/*  346 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  348 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  351 */     if (Double.doubleToLongBits(
/*  352 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  354 */       return this.defRetValue; } 
/*  355 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  356 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  359 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  360 */         return this.defRetValue; 
/*  361 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  362 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(double k) {
/*  368 */     if (Double.doubleToLongBits(k) == 0L) {
/*  369 */       return this.containsNullKey;
/*      */     }
/*  371 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  374 */     if (Double.doubleToLongBits(
/*  375 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  377 */       return false; } 
/*  378 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  379 */       return true;
/*      */     }
/*      */     while (true) {
/*  382 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  383 */         return false; 
/*  384 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr))
/*  385 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(boolean v) {
/*  390 */     boolean[] value = this.value;
/*  391 */     double[] key = this.key;
/*  392 */     if (this.containsNullKey && value[this.n] == v)
/*  393 */       return true; 
/*  394 */     for (int i = this.n; i-- != 0;) {
/*  395 */       if (Double.doubleToLongBits(key[i]) != 0L && value[i] == v)
/*  396 */         return true; 
/*  397 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getOrDefault(double k, boolean defaultValue) {
/*  403 */     if (Double.doubleToLongBits(k) == 0L) {
/*  404 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  406 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  409 */     if (Double.doubleToLongBits(
/*  410 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  412 */       return defaultValue; } 
/*  413 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  414 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  417 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  418 */         return defaultValue; 
/*  419 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  420 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean putIfAbsent(double k, boolean v) {
/*  426 */     int pos = find(k);
/*  427 */     if (pos >= 0)
/*  428 */       return this.value[pos]; 
/*  429 */     insert(-pos - 1, k, v);
/*  430 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(double k, boolean v) {
/*  436 */     if (Double.doubleToLongBits(k) == 0L) {
/*  437 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  438 */         removeNullEntry();
/*  439 */         return true;
/*      */       } 
/*  441 */       return false;
/*      */     } 
/*      */     
/*  444 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  447 */     if (Double.doubleToLongBits(
/*  448 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  450 */       return false; } 
/*  451 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr) && v == this.value[pos]) {
/*  452 */       removeEntry(pos);
/*  453 */       return true;
/*      */     } 
/*      */     while (true) {
/*  456 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  457 */         return false; 
/*  458 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr) && v == this.value[pos]) {
/*  459 */         removeEntry(pos);
/*  460 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(double k, boolean oldValue, boolean v) {
/*  467 */     int pos = find(k);
/*  468 */     if (pos < 0 || oldValue != this.value[pos])
/*  469 */       return false; 
/*  470 */     this.value[pos] = v;
/*  471 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(double k, boolean v) {
/*  476 */     int pos = find(k);
/*  477 */     if (pos < 0)
/*  478 */       return this.defRetValue; 
/*  479 */     boolean oldValue = this.value[pos];
/*  480 */     this.value[pos] = v;
/*  481 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean computeIfAbsent(double k, DoublePredicate mappingFunction) {
/*  486 */     Objects.requireNonNull(mappingFunction);
/*  487 */     int pos = find(k);
/*  488 */     if (pos >= 0)
/*  489 */       return this.value[pos]; 
/*  490 */     boolean newValue = mappingFunction.test(k);
/*  491 */     insert(-pos - 1, k, newValue);
/*  492 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeIfAbsentNullable(double k, DoubleFunction<? extends Boolean> mappingFunction) {
/*  498 */     Objects.requireNonNull(mappingFunction);
/*  499 */     int pos = find(k);
/*  500 */     if (pos >= 0)
/*  501 */       return this.value[pos]; 
/*  502 */     Boolean newValue = mappingFunction.apply(k);
/*  503 */     if (newValue == null)
/*  504 */       return this.defRetValue; 
/*  505 */     boolean v = newValue.booleanValue();
/*  506 */     insert(-pos - 1, k, v);
/*  507 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeIfPresent(double k, BiFunction<? super Double, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  513 */     Objects.requireNonNull(remappingFunction);
/*  514 */     int pos = find(k);
/*  515 */     if (pos < 0)
/*  516 */       return this.defRetValue; 
/*  517 */     Boolean newValue = remappingFunction.apply(Double.valueOf(k), Boolean.valueOf(this.value[pos]));
/*  518 */     if (newValue == null) {
/*  519 */       if (Double.doubleToLongBits(k) == 0L) {
/*  520 */         removeNullEntry();
/*      */       } else {
/*  522 */         removeEntry(pos);
/*  523 */       }  return this.defRetValue;
/*      */     } 
/*  525 */     this.value[pos] = newValue.booleanValue(); return newValue.booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean compute(double k, BiFunction<? super Double, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  531 */     Objects.requireNonNull(remappingFunction);
/*  532 */     int pos = find(k);
/*  533 */     Boolean newValue = remappingFunction.apply(Double.valueOf(k), 
/*  534 */         (pos >= 0) ? Boolean.valueOf(this.value[pos]) : null);
/*  535 */     if (newValue == null) {
/*  536 */       if (pos >= 0)
/*  537 */         if (Double.doubleToLongBits(k) == 0L) {
/*  538 */           removeNullEntry();
/*      */         } else {
/*  540 */           removeEntry(pos);
/*      */         }  
/*  542 */       return this.defRetValue;
/*      */     } 
/*  544 */     boolean newVal = newValue.booleanValue();
/*  545 */     if (pos < 0) {
/*  546 */       insert(-pos - 1, k, newVal);
/*  547 */       return newVal;
/*      */     } 
/*  549 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean merge(double k, boolean v, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  555 */     Objects.requireNonNull(remappingFunction);
/*  556 */     int pos = find(k);
/*  557 */     if (pos < 0) {
/*  558 */       insert(-pos - 1, k, v);
/*  559 */       return v;
/*      */     } 
/*  561 */     Boolean newValue = remappingFunction.apply(Boolean.valueOf(this.value[pos]), Boolean.valueOf(v));
/*  562 */     if (newValue == null) {
/*  563 */       if (Double.doubleToLongBits(k) == 0L) {
/*  564 */         removeNullEntry();
/*      */       } else {
/*  566 */         removeEntry(pos);
/*  567 */       }  return this.defRetValue;
/*      */     } 
/*  569 */     this.value[pos] = newValue.booleanValue(); return newValue.booleanValue();
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
/*  580 */     if (this.size == 0)
/*      */       return; 
/*  582 */     this.size = 0;
/*  583 */     this.containsNullKey = false;
/*  584 */     Arrays.fill(this.key, 0.0D);
/*      */   }
/*      */   
/*      */   public int size() {
/*  588 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  592 */     return (this.size == 0);
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
/*  604 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public double getDoubleKey() {
/*  610 */       return Double2BooleanOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public boolean getBooleanValue() {
/*  614 */       return Double2BooleanOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public boolean setValue(boolean v) {
/*  618 */       boolean oldValue = Double2BooleanOpenHashMap.this.value[this.index];
/*  619 */       Double2BooleanOpenHashMap.this.value[this.index] = v;
/*  620 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double getKey() {
/*  630 */       return Double.valueOf(Double2BooleanOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean getValue() {
/*  640 */       return Boolean.valueOf(Double2BooleanOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean setValue(Boolean v) {
/*  650 */       return Boolean.valueOf(setValue(v.booleanValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  655 */       if (!(o instanceof Map.Entry))
/*  656 */         return false; 
/*  657 */       Map.Entry<Double, Boolean> e = (Map.Entry<Double, Boolean>)o;
/*  658 */       return (Double.doubleToLongBits(Double2BooleanOpenHashMap.this.key[this.index]) == Double.doubleToLongBits(((Double)e.getKey()).doubleValue()) && Double2BooleanOpenHashMap.this.value[this.index] == ((Boolean)e
/*  659 */         .getValue()).booleanValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  663 */       return HashCommon.double2int(Double2BooleanOpenHashMap.this.key[this.index]) ^ (Double2BooleanOpenHashMap.this.value[this.index] ? 1231 : 1237);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  667 */       return Double2BooleanOpenHashMap.this.key[this.index] + "=>" + Double2BooleanOpenHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  677 */     int pos = Double2BooleanOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  684 */     int last = -1;
/*      */     
/*  686 */     int c = Double2BooleanOpenHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  690 */     boolean mustReturnNullKey = Double2BooleanOpenHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     DoubleArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  697 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  700 */       if (!hasNext())
/*  701 */         throw new NoSuchElementException(); 
/*  702 */       this.c--;
/*  703 */       if (this.mustReturnNullKey) {
/*  704 */         this.mustReturnNullKey = false;
/*  705 */         return this.last = Double2BooleanOpenHashMap.this.n;
/*      */       } 
/*  707 */       double[] key = Double2BooleanOpenHashMap.this.key;
/*      */       while (true) {
/*  709 */         if (--this.pos < 0) {
/*      */           
/*  711 */           this.last = Integer.MIN_VALUE;
/*  712 */           double k = this.wrapped.getDouble(-this.pos - 1);
/*  713 */           int p = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2BooleanOpenHashMap.this.mask;
/*  714 */           while (Double.doubleToLongBits(k) != Double.doubleToLongBits(key[p]))
/*  715 */             p = p + 1 & Double2BooleanOpenHashMap.this.mask; 
/*  716 */           return p;
/*      */         } 
/*  718 */         if (Double.doubleToLongBits(key[this.pos]) != 0L) {
/*  719 */           return this.last = this.pos;
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
/*  733 */       double[] key = Double2BooleanOpenHashMap.this.key; while (true) {
/*      */         double curr; int last;
/*  735 */         pos = (last = pos) + 1 & Double2BooleanOpenHashMap.this.mask;
/*      */         while (true) {
/*  737 */           if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/*  738 */             key[last] = 0.0D;
/*      */             return;
/*      */           } 
/*  741 */           int slot = (int)HashCommon.mix(Double.doubleToRawLongBits(curr)) & Double2BooleanOpenHashMap.this.mask;
/*  742 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  744 */           pos = pos + 1 & Double2BooleanOpenHashMap.this.mask;
/*      */         } 
/*  746 */         if (pos < last) {
/*  747 */           if (this.wrapped == null)
/*  748 */             this.wrapped = new DoubleArrayList(2); 
/*  749 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  751 */         key[last] = curr;
/*  752 */         Double2BooleanOpenHashMap.this.value[last] = Double2BooleanOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  756 */       if (this.last == -1)
/*  757 */         throw new IllegalStateException(); 
/*  758 */       if (this.last == Double2BooleanOpenHashMap.this.n) {
/*  759 */         Double2BooleanOpenHashMap.this.containsNullKey = false;
/*  760 */       } else if (this.pos >= 0) {
/*  761 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  764 */         Double2BooleanOpenHashMap.this.remove(this.wrapped.getDouble(-this.pos - 1));
/*  765 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  768 */       Double2BooleanOpenHashMap.this.size--;
/*  769 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  774 */       int i = n;
/*  775 */       while (i-- != 0 && hasNext())
/*  776 */         nextEntry(); 
/*  777 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Double2BooleanMap.Entry> { private Double2BooleanOpenHashMap.MapEntry entry;
/*      */     
/*      */     public Double2BooleanOpenHashMap.MapEntry next() {
/*  784 */       return this.entry = new Double2BooleanOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  788 */       super.remove();
/*  789 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Double2BooleanMap.Entry> { private FastEntryIterator() {
/*  793 */       this.entry = new Double2BooleanOpenHashMap.MapEntry();
/*      */     } private final Double2BooleanOpenHashMap.MapEntry entry;
/*      */     public Double2BooleanOpenHashMap.MapEntry next() {
/*  796 */       this.entry.index = nextEntry();
/*  797 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Double2BooleanMap.Entry> implements Double2BooleanMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Double2BooleanMap.Entry> iterator() {
/*  803 */       return new Double2BooleanOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Double2BooleanMap.Entry> fastIterator() {
/*  807 */       return new Double2BooleanOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  812 */       if (!(o instanceof Map.Entry))
/*  813 */         return false; 
/*  814 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  815 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/*  816 */         return false; 
/*  817 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/*  818 */         return false; 
/*  819 */       double k = ((Double)e.getKey()).doubleValue();
/*  820 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/*  821 */       if (Double.doubleToLongBits(k) == 0L) {
/*  822 */         return (Double2BooleanOpenHashMap.this.containsNullKey && Double2BooleanOpenHashMap.this.value[Double2BooleanOpenHashMap.this.n] == v);
/*      */       }
/*  824 */       double[] key = Double2BooleanOpenHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/*  827 */       if (Double.doubleToLongBits(
/*  828 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2BooleanOpenHashMap.this.mask]) == 0L)
/*      */       {
/*  830 */         return false; } 
/*  831 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  832 */         return (Double2BooleanOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  835 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2BooleanOpenHashMap.this.mask]) == 0L)
/*  836 */           return false; 
/*  837 */         if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  838 */           return (Double2BooleanOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  844 */       if (!(o instanceof Map.Entry))
/*  845 */         return false; 
/*  846 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  847 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/*  848 */         return false; 
/*  849 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/*  850 */         return false; 
/*  851 */       double k = ((Double)e.getKey()).doubleValue();
/*  852 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/*  853 */       if (Double.doubleToLongBits(k) == 0L) {
/*  854 */         if (Double2BooleanOpenHashMap.this.containsNullKey && Double2BooleanOpenHashMap.this.value[Double2BooleanOpenHashMap.this.n] == v) {
/*  855 */           Double2BooleanOpenHashMap.this.removeNullEntry();
/*  856 */           return true;
/*      */         } 
/*  858 */         return false;
/*      */       } 
/*      */       
/*  861 */       double[] key = Double2BooleanOpenHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/*  864 */       if (Double.doubleToLongBits(
/*  865 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2BooleanOpenHashMap.this.mask]) == 0L)
/*      */       {
/*  867 */         return false; } 
/*  868 */       if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
/*  869 */         if (Double2BooleanOpenHashMap.this.value[pos] == v) {
/*  870 */           Double2BooleanOpenHashMap.this.removeEntry(pos);
/*  871 */           return true;
/*      */         } 
/*  873 */         return false;
/*      */       } 
/*      */       while (true) {
/*  876 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2BooleanOpenHashMap.this.mask]) == 0L)
/*  877 */           return false; 
/*  878 */         if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k) && 
/*  879 */           Double2BooleanOpenHashMap.this.value[pos] == v) {
/*  880 */           Double2BooleanOpenHashMap.this.removeEntry(pos);
/*  881 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  888 */       return Double2BooleanOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  892 */       Double2BooleanOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Double2BooleanMap.Entry> consumer) {
/*  897 */       if (Double2BooleanOpenHashMap.this.containsNullKey)
/*  898 */         consumer.accept(new AbstractDouble2BooleanMap.BasicEntry(Double2BooleanOpenHashMap.this.key[Double2BooleanOpenHashMap.this.n], Double2BooleanOpenHashMap.this.value[Double2BooleanOpenHashMap.this.n])); 
/*  899 */       for (int pos = Double2BooleanOpenHashMap.this.n; pos-- != 0;) {
/*  900 */         if (Double.doubleToLongBits(Double2BooleanOpenHashMap.this.key[pos]) != 0L)
/*  901 */           consumer.accept(new AbstractDouble2BooleanMap.BasicEntry(Double2BooleanOpenHashMap.this.key[pos], Double2BooleanOpenHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Double2BooleanMap.Entry> consumer) {
/*  906 */       AbstractDouble2BooleanMap.BasicEntry entry = new AbstractDouble2BooleanMap.BasicEntry();
/*  907 */       if (Double2BooleanOpenHashMap.this.containsNullKey) {
/*  908 */         entry.key = Double2BooleanOpenHashMap.this.key[Double2BooleanOpenHashMap.this.n];
/*  909 */         entry.value = Double2BooleanOpenHashMap.this.value[Double2BooleanOpenHashMap.this.n];
/*  910 */         consumer.accept(entry);
/*      */       } 
/*  912 */       for (int pos = Double2BooleanOpenHashMap.this.n; pos-- != 0;) {
/*  913 */         if (Double.doubleToLongBits(Double2BooleanOpenHashMap.this.key[pos]) != 0L) {
/*  914 */           entry.key = Double2BooleanOpenHashMap.this.key[pos];
/*  915 */           entry.value = Double2BooleanOpenHashMap.this.value[pos];
/*  916 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Double2BooleanMap.FastEntrySet double2BooleanEntrySet() {
/*  922 */     if (this.entries == null)
/*  923 */       this.entries = new MapEntrySet(); 
/*  924 */     return this.entries;
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
/*  941 */       return Double2BooleanOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractDoubleSet { private KeySet() {}
/*      */     
/*      */     public DoubleIterator iterator() {
/*  947 */       return new Double2BooleanOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/*  952 */       if (Double2BooleanOpenHashMap.this.containsNullKey)
/*  953 */         consumer.accept(Double2BooleanOpenHashMap.this.key[Double2BooleanOpenHashMap.this.n]); 
/*  954 */       for (int pos = Double2BooleanOpenHashMap.this.n; pos-- != 0; ) {
/*  955 */         double k = Double2BooleanOpenHashMap.this.key[pos];
/*  956 */         if (Double.doubleToLongBits(k) != 0L)
/*  957 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  962 */       return Double2BooleanOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(double k) {
/*  966 */       return Double2BooleanOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(double k) {
/*  970 */       int oldSize = Double2BooleanOpenHashMap.this.size;
/*  971 */       Double2BooleanOpenHashMap.this.remove(k);
/*  972 */       return (Double2BooleanOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  976 */       Double2BooleanOpenHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public DoubleSet keySet() {
/*  981 */     if (this.keys == null)
/*  982 */       this.keys = new KeySet(); 
/*  983 */     return this.keys;
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
/* 1000 */       return Double2BooleanOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public BooleanCollection values() {
/* 1005 */     if (this.values == null)
/* 1006 */       this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */         {
/*      */           public BooleanIterator iterator() {
/* 1009 */             return new Double2BooleanOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1013 */             return Double2BooleanOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(boolean v) {
/* 1017 */             return Double2BooleanOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1021 */             Double2BooleanOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(BooleanConsumer consumer)
/*      */           {
/* 1026 */             if (Double2BooleanOpenHashMap.this.containsNullKey)
/* 1027 */               consumer.accept(Double2BooleanOpenHashMap.this.value[Double2BooleanOpenHashMap.this.n]); 
/* 1028 */             for (int pos = Double2BooleanOpenHashMap.this.n; pos-- != 0;) {
/* 1029 */               if (Double.doubleToLongBits(Double2BooleanOpenHashMap.this.key[pos]) != 0L)
/* 1030 */                 consumer.accept(Double2BooleanOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1033 */     return this.values;
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
/* 1050 */     return trim(this.size);
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
/* 1074 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1075 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1076 */       return true; 
/*      */     try {
/* 1078 */       rehash(l);
/* 1079 */     } catch (OutOfMemoryError cantDoIt) {
/* 1080 */       return false;
/*      */     } 
/* 1082 */     return true;
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
/* 1098 */     double[] key = this.key;
/* 1099 */     boolean[] value = this.value;
/* 1100 */     int mask = newN - 1;
/* 1101 */     double[] newKey = new double[newN + 1];
/* 1102 */     boolean[] newValue = new boolean[newN + 1];
/* 1103 */     int i = this.n;
/* 1104 */     for (int j = realSize(); j-- != 0; ) {
/* 1105 */       while (Double.doubleToLongBits(key[--i]) == 0L); int pos;
/* 1106 */       if (Double.doubleToLongBits(newKey[
/* 1107 */             pos = (int)HashCommon.mix(Double.doubleToRawLongBits(key[i])) & mask]) != 0L)
/*      */       {
/* 1109 */         while (Double.doubleToLongBits(newKey[pos = pos + 1 & mask]) != 0L); } 
/* 1110 */       newKey[pos] = key[i];
/* 1111 */       newValue[pos] = value[i];
/*      */     } 
/* 1113 */     newValue[newN] = value[this.n];
/* 1114 */     this.n = newN;
/* 1115 */     this.mask = mask;
/* 1116 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1117 */     this.key = newKey;
/* 1118 */     this.value = newValue;
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
/*      */   public Double2BooleanOpenHashMap clone() {
/*      */     Double2BooleanOpenHashMap c;
/*      */     try {
/* 1135 */       c = (Double2BooleanOpenHashMap)super.clone();
/* 1136 */     } catch (CloneNotSupportedException cantHappen) {
/* 1137 */       throw new InternalError();
/*      */     } 
/* 1139 */     c.keys = null;
/* 1140 */     c.values = null;
/* 1141 */     c.entries = null;
/* 1142 */     c.containsNullKey = this.containsNullKey;
/* 1143 */     c.key = (double[])this.key.clone();
/* 1144 */     c.value = (boolean[])this.value.clone();
/* 1145 */     return c;
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
/* 1158 */     int h = 0;
/* 1159 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1160 */       while (Double.doubleToLongBits(this.key[i]) == 0L)
/* 1161 */         i++; 
/* 1162 */       t = HashCommon.double2int(this.key[i]);
/* 1163 */       t ^= this.value[i] ? 1231 : 1237;
/* 1164 */       h += t;
/* 1165 */       i++;
/*      */     } 
/*      */     
/* 1168 */     if (this.containsNullKey)
/* 1169 */       h += this.value[this.n] ? 1231 : 1237; 
/* 1170 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1173 */     double[] key = this.key;
/* 1174 */     boolean[] value = this.value;
/* 1175 */     MapIterator i = new MapIterator();
/* 1176 */     s.defaultWriteObject();
/* 1177 */     for (int j = this.size; j-- != 0; ) {
/* 1178 */       int e = i.nextEntry();
/* 1179 */       s.writeDouble(key[e]);
/* 1180 */       s.writeBoolean(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1185 */     s.defaultReadObject();
/* 1186 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1187 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1188 */     this.mask = this.n - 1;
/* 1189 */     double[] key = this.key = new double[this.n + 1];
/* 1190 */     boolean[] value = this.value = new boolean[this.n + 1];
/*      */ 
/*      */     
/* 1193 */     for (int i = this.size; i-- != 0; ) {
/* 1194 */       int pos; double k = s.readDouble();
/* 1195 */       boolean v = s.readBoolean();
/* 1196 */       if (Double.doubleToLongBits(k) == 0L) {
/* 1197 */         pos = this.n;
/* 1198 */         this.containsNullKey = true;
/*      */       } else {
/* 1200 */         pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask;
/* 1201 */         while (Double.doubleToLongBits(key[pos]) != 0L)
/* 1202 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1204 */       key[pos] = k;
/* 1205 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2BooleanOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */