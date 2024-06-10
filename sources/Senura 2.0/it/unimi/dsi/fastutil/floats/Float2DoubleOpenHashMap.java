/*      */ package it.unimi.dsi.fastutil.floats;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleCollection;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleIterator;
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
/*      */ import java.util.function.DoubleUnaryOperator;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Float2DoubleOpenHashMap
/*      */   extends AbstractFloat2DoubleMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient float[] key;
/*      */   protected transient double[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Float2DoubleMap.FastEntrySet entries;
/*      */   protected transient FloatSet keys;
/*      */   protected transient DoubleCollection values;
/*      */   
/*      */   public Float2DoubleOpenHashMap(int expected, float f) {
/*   96 */     if (f <= 0.0F || f > 1.0F)
/*   97 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*   98 */     if (expected < 0)
/*   99 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  100 */     this.f = f;
/*  101 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  102 */     this.mask = this.n - 1;
/*  103 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  104 */     this.key = new float[this.n + 1];
/*  105 */     this.value = new double[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2DoubleOpenHashMap(int expected) {
/*  114 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2DoubleOpenHashMap() {
/*  122 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2DoubleOpenHashMap(Map<? extends Float, ? extends Double> m, float f) {
/*  133 */     this(m.size(), f);
/*  134 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2DoubleOpenHashMap(Map<? extends Float, ? extends Double> m) {
/*  144 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2DoubleOpenHashMap(Float2DoubleMap m, float f) {
/*  155 */     this(m.size(), f);
/*  156 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2DoubleOpenHashMap(Float2DoubleMap m) {
/*  166 */     this(m, 0.75F);
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
/*      */   public Float2DoubleOpenHashMap(float[] k, double[] v, float f) {
/*  181 */     this(k.length, f);
/*  182 */     if (k.length != v.length) {
/*  183 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  185 */     for (int i = 0; i < k.length; i++) {
/*  186 */       put(k[i], v[i]);
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
/*      */   public Float2DoubleOpenHashMap(float[] k, double[] v) {
/*  200 */     this(k, v, 0.75F);
/*      */   }
/*      */   private int realSize() {
/*  203 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  206 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  207 */     if (needed > this.n)
/*  208 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  211 */     int needed = (int)Math.min(1073741824L, 
/*  212 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  213 */     if (needed > this.n)
/*  214 */       rehash(needed); 
/*      */   }
/*      */   private double removeEntry(int pos) {
/*  217 */     double oldValue = this.value[pos];
/*  218 */     this.size--;
/*  219 */     shiftKeys(pos);
/*  220 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  221 */       rehash(this.n / 2); 
/*  222 */     return oldValue;
/*      */   }
/*      */   private double removeNullEntry() {
/*  225 */     this.containsNullKey = false;
/*  226 */     double oldValue = this.value[this.n];
/*  227 */     this.size--;
/*  228 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  229 */       rehash(this.n / 2); 
/*  230 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Float, ? extends Double> m) {
/*  234 */     if (this.f <= 0.5D) {
/*  235 */       ensureCapacity(m.size());
/*      */     } else {
/*  237 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  239 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(float k) {
/*  243 */     if (Float.floatToIntBits(k) == 0) {
/*  244 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  246 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  249 */     if (Float.floatToIntBits(
/*  250 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  252 */       return -(pos + 1); } 
/*  253 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  254 */       return pos;
/*      */     }
/*      */     while (true) {
/*  257 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  258 */         return -(pos + 1); 
/*  259 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr))
/*  260 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, float k, double v) {
/*  264 */     if (pos == this.n)
/*  265 */       this.containsNullKey = true; 
/*  266 */     this.key[pos] = k;
/*  267 */     this.value[pos] = v;
/*  268 */     if (this.size++ >= this.maxFill) {
/*  269 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public double put(float k, double v) {
/*  275 */     int pos = find(k);
/*  276 */     if (pos < 0) {
/*  277 */       insert(-pos - 1, k, v);
/*  278 */       return this.defRetValue;
/*      */     } 
/*  280 */     double oldValue = this.value[pos];
/*  281 */     this.value[pos] = v;
/*  282 */     return oldValue;
/*      */   }
/*      */   private double addToValue(int pos, double incr) {
/*  285 */     double oldValue = this.value[pos];
/*  286 */     this.value[pos] = oldValue + incr;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double addTo(float k, double incr) {
/*      */     int pos;
/*  307 */     if (Float.floatToIntBits(k) == 0) {
/*  308 */       if (this.containsNullKey)
/*  309 */         return addToValue(this.n, incr); 
/*  310 */       pos = this.n;
/*  311 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  314 */       float[] key = this.key;
/*      */       float curr;
/*  316 */       if (Float.floatToIntBits(
/*  317 */           curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) != 0) {
/*      */         
/*  319 */         if (Float.floatToIntBits(curr) == Float.floatToIntBits(k))
/*  320 */           return addToValue(pos, incr); 
/*  321 */         while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) {
/*  322 */           if (Float.floatToIntBits(curr) == Float.floatToIntBits(k))
/*  323 */             return addToValue(pos, incr); 
/*      */         } 
/*      */       } 
/*  326 */     }  this.key[pos] = k;
/*  327 */     this.value[pos] = this.defRetValue + incr;
/*  328 */     if (this.size++ >= this.maxFill) {
/*  329 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  332 */     return this.defRetValue;
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
/*  345 */     float[] key = this.key; while (true) {
/*      */       float curr; int last;
/*  347 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  349 */         if (Float.floatToIntBits(curr = key[pos]) == 0) {
/*  350 */           key[last] = 0.0F;
/*      */           return;
/*      */         } 
/*  353 */         int slot = HashCommon.mix(HashCommon.float2int(curr)) & this.mask;
/*  354 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  356 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  358 */       key[last] = curr;
/*  359 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public double remove(float k) {
/*  365 */     if (Float.floatToIntBits(k) == 0) {
/*  366 */       if (this.containsNullKey)
/*  367 */         return removeNullEntry(); 
/*  368 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  371 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  374 */     if (Float.floatToIntBits(
/*  375 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  377 */       return this.defRetValue; } 
/*  378 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr))
/*  379 */       return removeEntry(pos); 
/*      */     while (true) {
/*  381 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  382 */         return this.defRetValue; 
/*  383 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  384 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public double get(float k) {
/*  390 */     if (Float.floatToIntBits(k) == 0) {
/*  391 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  393 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  396 */     if (Float.floatToIntBits(
/*  397 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  399 */       return this.defRetValue; } 
/*  400 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  401 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  404 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  405 */         return this.defRetValue; 
/*  406 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  407 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(float k) {
/*  413 */     if (Float.floatToIntBits(k) == 0) {
/*  414 */       return this.containsNullKey;
/*      */     }
/*  416 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  419 */     if (Float.floatToIntBits(
/*  420 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  422 */       return false; } 
/*  423 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  424 */       return true;
/*      */     }
/*      */     while (true) {
/*  427 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  428 */         return false; 
/*  429 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr))
/*  430 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(double v) {
/*  435 */     double[] value = this.value;
/*  436 */     float[] key = this.key;
/*  437 */     if (this.containsNullKey && Double.doubleToLongBits(value[this.n]) == Double.doubleToLongBits(v))
/*  438 */       return true; 
/*  439 */     for (int i = this.n; i-- != 0;) {
/*  440 */       if (Float.floatToIntBits(key[i]) != 0 && 
/*  441 */         Double.doubleToLongBits(value[i]) == Double.doubleToLongBits(v))
/*  442 */         return true; 
/*  443 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double getOrDefault(float k, double defaultValue) {
/*  449 */     if (Float.floatToIntBits(k) == 0) {
/*  450 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  452 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  455 */     if (Float.floatToIntBits(
/*  456 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  458 */       return defaultValue; } 
/*  459 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  460 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  463 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  464 */         return defaultValue; 
/*  465 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  466 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public double putIfAbsent(float k, double v) {
/*  472 */     int pos = find(k);
/*  473 */     if (pos >= 0)
/*  474 */       return this.value[pos]; 
/*  475 */     insert(-pos - 1, k, v);
/*  476 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(float k, double v) {
/*  482 */     if (Float.floatToIntBits(k) == 0) {
/*  483 */       if (this.containsNullKey && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[this.n])) {
/*  484 */         removeNullEntry();
/*  485 */         return true;
/*      */       } 
/*  487 */       return false;
/*      */     } 
/*      */     
/*  490 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  493 */     if (Float.floatToIntBits(
/*  494 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  496 */       return false; } 
/*  497 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr) && 
/*  498 */       Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
/*  499 */       removeEntry(pos);
/*  500 */       return true;
/*      */     } 
/*      */     while (true) {
/*  503 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  504 */         return false; 
/*  505 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr) && 
/*  506 */         Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
/*  507 */         removeEntry(pos);
/*  508 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(float k, double oldValue, double v) {
/*  515 */     int pos = find(k);
/*  516 */     if (pos < 0 || Double.doubleToLongBits(oldValue) != Double.doubleToLongBits(this.value[pos]))
/*  517 */       return false; 
/*  518 */     this.value[pos] = v;
/*  519 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public double replace(float k, double v) {
/*  524 */     int pos = find(k);
/*  525 */     if (pos < 0)
/*  526 */       return this.defRetValue; 
/*  527 */     double oldValue = this.value[pos];
/*  528 */     this.value[pos] = v;
/*  529 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public double computeIfAbsent(float k, DoubleUnaryOperator mappingFunction) {
/*  534 */     Objects.requireNonNull(mappingFunction);
/*  535 */     int pos = find(k);
/*  536 */     if (pos >= 0)
/*  537 */       return this.value[pos]; 
/*  538 */     double newValue = mappingFunction.applyAsDouble(k);
/*  539 */     insert(-pos - 1, k, newValue);
/*  540 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeIfAbsentNullable(float k, DoubleFunction<? extends Double> mappingFunction) {
/*  546 */     Objects.requireNonNull(mappingFunction);
/*  547 */     int pos = find(k);
/*  548 */     if (pos >= 0)
/*  549 */       return this.value[pos]; 
/*  550 */     Double newValue = mappingFunction.apply(k);
/*  551 */     if (newValue == null)
/*  552 */       return this.defRetValue; 
/*  553 */     double v = newValue.doubleValue();
/*  554 */     insert(-pos - 1, k, v);
/*  555 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeIfPresent(float k, BiFunction<? super Float, ? super Double, ? extends Double> remappingFunction) {
/*  561 */     Objects.requireNonNull(remappingFunction);
/*  562 */     int pos = find(k);
/*  563 */     if (pos < 0)
/*  564 */       return this.defRetValue; 
/*  565 */     Double newValue = remappingFunction.apply(Float.valueOf(k), Double.valueOf(this.value[pos]));
/*  566 */     if (newValue == null) {
/*  567 */       if (Float.floatToIntBits(k) == 0) {
/*  568 */         removeNullEntry();
/*      */       } else {
/*  570 */         removeEntry(pos);
/*  571 */       }  return this.defRetValue;
/*      */     } 
/*  573 */     this.value[pos] = newValue.doubleValue(); return newValue.doubleValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double compute(float k, BiFunction<? super Float, ? super Double, ? extends Double> remappingFunction) {
/*  579 */     Objects.requireNonNull(remappingFunction);
/*  580 */     int pos = find(k);
/*  581 */     Double newValue = remappingFunction.apply(Float.valueOf(k), (pos >= 0) ? Double.valueOf(this.value[pos]) : null);
/*  582 */     if (newValue == null) {
/*  583 */       if (pos >= 0)
/*  584 */         if (Float.floatToIntBits(k) == 0) {
/*  585 */           removeNullEntry();
/*      */         } else {
/*  587 */           removeEntry(pos);
/*      */         }  
/*  589 */       return this.defRetValue;
/*      */     } 
/*  591 */     double newVal = newValue.doubleValue();
/*  592 */     if (pos < 0) {
/*  593 */       insert(-pos - 1, k, newVal);
/*  594 */       return newVal;
/*      */     } 
/*  596 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double merge(float k, double v, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/*  602 */     Objects.requireNonNull(remappingFunction);
/*  603 */     int pos = find(k);
/*  604 */     if (pos < 0) {
/*  605 */       insert(-pos - 1, k, v);
/*  606 */       return v;
/*      */     } 
/*  608 */     Double newValue = remappingFunction.apply(Double.valueOf(this.value[pos]), Double.valueOf(v));
/*  609 */     if (newValue == null) {
/*  610 */       if (Float.floatToIntBits(k) == 0) {
/*  611 */         removeNullEntry();
/*      */       } else {
/*  613 */         removeEntry(pos);
/*  614 */       }  return this.defRetValue;
/*      */     } 
/*  616 */     this.value[pos] = newValue.doubleValue(); return newValue.doubleValue();
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
/*  627 */     if (this.size == 0)
/*      */       return; 
/*  629 */     this.size = 0;
/*  630 */     this.containsNullKey = false;
/*  631 */     Arrays.fill(this.key, 0.0F);
/*      */   }
/*      */   
/*      */   public int size() {
/*  635 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  639 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Float2DoubleMap.Entry, Map.Entry<Float, Double>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  651 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public float getFloatKey() {
/*  657 */       return Float2DoubleOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public double getDoubleValue() {
/*  661 */       return Float2DoubleOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public double setValue(double v) {
/*  665 */       double oldValue = Float2DoubleOpenHashMap.this.value[this.index];
/*  666 */       Float2DoubleOpenHashMap.this.value[this.index] = v;
/*  667 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float getKey() {
/*  677 */       return Float.valueOf(Float2DoubleOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double getValue() {
/*  687 */       return Double.valueOf(Float2DoubleOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double setValue(Double v) {
/*  697 */       return Double.valueOf(setValue(v.doubleValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  702 */       if (!(o instanceof Map.Entry))
/*  703 */         return false; 
/*  704 */       Map.Entry<Float, Double> e = (Map.Entry<Float, Double>)o;
/*  705 */       return (Float.floatToIntBits(Float2DoubleOpenHashMap.this.key[this.index]) == Float.floatToIntBits(((Float)e.getKey()).floatValue()) && 
/*  706 */         Double.doubleToLongBits(Float2DoubleOpenHashMap.this.value[this.index]) == Double.doubleToLongBits(((Double)e.getValue()).doubleValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  710 */       return HashCommon.float2int(Float2DoubleOpenHashMap.this.key[this.index]) ^ 
/*  711 */         HashCommon.double2int(Float2DoubleOpenHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  715 */       return Float2DoubleOpenHashMap.this.key[this.index] + "=>" + Float2DoubleOpenHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  725 */     int pos = Float2DoubleOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  732 */     int last = -1;
/*      */     
/*  734 */     int c = Float2DoubleOpenHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  738 */     boolean mustReturnNullKey = Float2DoubleOpenHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     FloatArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  745 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  748 */       if (!hasNext())
/*  749 */         throw new NoSuchElementException(); 
/*  750 */       this.c--;
/*  751 */       if (this.mustReturnNullKey) {
/*  752 */         this.mustReturnNullKey = false;
/*  753 */         return this.last = Float2DoubleOpenHashMap.this.n;
/*      */       } 
/*  755 */       float[] key = Float2DoubleOpenHashMap.this.key;
/*      */       while (true) {
/*  757 */         if (--this.pos < 0) {
/*      */           
/*  759 */           this.last = Integer.MIN_VALUE;
/*  760 */           float k = this.wrapped.getFloat(-this.pos - 1);
/*  761 */           int p = HashCommon.mix(HashCommon.float2int(k)) & Float2DoubleOpenHashMap.this.mask;
/*  762 */           while (Float.floatToIntBits(k) != Float.floatToIntBits(key[p]))
/*  763 */             p = p + 1 & Float2DoubleOpenHashMap.this.mask; 
/*  764 */           return p;
/*      */         } 
/*  766 */         if (Float.floatToIntBits(key[this.pos]) != 0) {
/*  767 */           return this.last = this.pos;
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
/*  781 */       float[] key = Float2DoubleOpenHashMap.this.key; while (true) {
/*      */         float curr; int last;
/*  783 */         pos = (last = pos) + 1 & Float2DoubleOpenHashMap.this.mask;
/*      */         while (true) {
/*  785 */           if (Float.floatToIntBits(curr = key[pos]) == 0) {
/*  786 */             key[last] = 0.0F;
/*      */             return;
/*      */           } 
/*  789 */           int slot = HashCommon.mix(HashCommon.float2int(curr)) & Float2DoubleOpenHashMap.this.mask;
/*      */           
/*  791 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  793 */           pos = pos + 1 & Float2DoubleOpenHashMap.this.mask;
/*      */         } 
/*  795 */         if (pos < last) {
/*  796 */           if (this.wrapped == null)
/*  797 */             this.wrapped = new FloatArrayList(2); 
/*  798 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  800 */         key[last] = curr;
/*  801 */         Float2DoubleOpenHashMap.this.value[last] = Float2DoubleOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  805 */       if (this.last == -1)
/*  806 */         throw new IllegalStateException(); 
/*  807 */       if (this.last == Float2DoubleOpenHashMap.this.n) {
/*  808 */         Float2DoubleOpenHashMap.this.containsNullKey = false;
/*  809 */       } else if (this.pos >= 0) {
/*  810 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  813 */         Float2DoubleOpenHashMap.this.remove(this.wrapped.getFloat(-this.pos - 1));
/*  814 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  817 */       Float2DoubleOpenHashMap.this.size--;
/*  818 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  823 */       int i = n;
/*  824 */       while (i-- != 0 && hasNext())
/*  825 */         nextEntry(); 
/*  826 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Float2DoubleMap.Entry> { private Float2DoubleOpenHashMap.MapEntry entry;
/*      */     
/*      */     public Float2DoubleOpenHashMap.MapEntry next() {
/*  833 */       return this.entry = new Float2DoubleOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  837 */       super.remove();
/*  838 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Float2DoubleMap.Entry> { private FastEntryIterator() {
/*  842 */       this.entry = new Float2DoubleOpenHashMap.MapEntry();
/*      */     } private final Float2DoubleOpenHashMap.MapEntry entry;
/*      */     public Float2DoubleOpenHashMap.MapEntry next() {
/*  845 */       this.entry.index = nextEntry();
/*  846 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Float2DoubleMap.Entry> implements Float2DoubleMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Float2DoubleMap.Entry> iterator() {
/*  852 */       return new Float2DoubleOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Float2DoubleMap.Entry> fastIterator() {
/*  856 */       return new Float2DoubleOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  861 */       if (!(o instanceof Map.Entry))
/*  862 */         return false; 
/*  863 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  864 */       if (e.getKey() == null || !(e.getKey() instanceof Float))
/*  865 */         return false; 
/*  866 */       if (e.getValue() == null || !(e.getValue() instanceof Double))
/*  867 */         return false; 
/*  868 */       float k = ((Float)e.getKey()).floatValue();
/*  869 */       double v = ((Double)e.getValue()).doubleValue();
/*  870 */       if (Float.floatToIntBits(k) == 0) {
/*  871 */         return (Float2DoubleOpenHashMap.this.containsNullKey && 
/*  872 */           Double.doubleToLongBits(Float2DoubleOpenHashMap.this.value[Float2DoubleOpenHashMap.this.n]) == Double.doubleToLongBits(v));
/*      */       }
/*  874 */       float[] key = Float2DoubleOpenHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/*  877 */       if (Float.floatToIntBits(
/*  878 */           curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & Float2DoubleOpenHashMap.this.mask]) == 0)
/*      */       {
/*  880 */         return false; } 
/*  881 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  882 */         return (Double.doubleToLongBits(Float2DoubleOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v));
/*      */       }
/*      */       while (true) {
/*  885 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2DoubleOpenHashMap.this.mask]) == 0)
/*  886 */           return false; 
/*  887 */         if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  888 */           return (Double.doubleToLongBits(Float2DoubleOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v));
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  894 */       if (!(o instanceof Map.Entry))
/*  895 */         return false; 
/*  896 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  897 */       if (e.getKey() == null || !(e.getKey() instanceof Float))
/*  898 */         return false; 
/*  899 */       if (e.getValue() == null || !(e.getValue() instanceof Double))
/*  900 */         return false; 
/*  901 */       float k = ((Float)e.getKey()).floatValue();
/*  902 */       double v = ((Double)e.getValue()).doubleValue();
/*  903 */       if (Float.floatToIntBits(k) == 0) {
/*  904 */         if (Float2DoubleOpenHashMap.this.containsNullKey && Double.doubleToLongBits(Float2DoubleOpenHashMap.this.value[Float2DoubleOpenHashMap.this.n]) == Double.doubleToLongBits(v)) {
/*  905 */           Float2DoubleOpenHashMap.this.removeNullEntry();
/*  906 */           return true;
/*      */         } 
/*  908 */         return false;
/*      */       } 
/*      */       
/*  911 */       float[] key = Float2DoubleOpenHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/*  914 */       if (Float.floatToIntBits(
/*  915 */           curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & Float2DoubleOpenHashMap.this.mask]) == 0)
/*      */       {
/*  917 */         return false; } 
/*  918 */       if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
/*  919 */         if (Double.doubleToLongBits(Float2DoubleOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
/*  920 */           Float2DoubleOpenHashMap.this.removeEntry(pos);
/*  921 */           return true;
/*      */         } 
/*  923 */         return false;
/*      */       } 
/*      */       while (true) {
/*  926 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2DoubleOpenHashMap.this.mask]) == 0)
/*  927 */           return false; 
/*  928 */         if (Float.floatToIntBits(curr) == Float.floatToIntBits(k) && 
/*  929 */           Double.doubleToLongBits(Float2DoubleOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
/*  930 */           Float2DoubleOpenHashMap.this.removeEntry(pos);
/*  931 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  938 */       return Float2DoubleOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  942 */       Float2DoubleOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Float2DoubleMap.Entry> consumer) {
/*  947 */       if (Float2DoubleOpenHashMap.this.containsNullKey)
/*  948 */         consumer.accept(new AbstractFloat2DoubleMap.BasicEntry(Float2DoubleOpenHashMap.this.key[Float2DoubleOpenHashMap.this.n], Float2DoubleOpenHashMap.this.value[Float2DoubleOpenHashMap.this.n])); 
/*  949 */       for (int pos = Float2DoubleOpenHashMap.this.n; pos-- != 0;) {
/*  950 */         if (Float.floatToIntBits(Float2DoubleOpenHashMap.this.key[pos]) != 0)
/*  951 */           consumer.accept(new AbstractFloat2DoubleMap.BasicEntry(Float2DoubleOpenHashMap.this.key[pos], Float2DoubleOpenHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Float2DoubleMap.Entry> consumer) {
/*  956 */       AbstractFloat2DoubleMap.BasicEntry entry = new AbstractFloat2DoubleMap.BasicEntry();
/*  957 */       if (Float2DoubleOpenHashMap.this.containsNullKey) {
/*  958 */         entry.key = Float2DoubleOpenHashMap.this.key[Float2DoubleOpenHashMap.this.n];
/*  959 */         entry.value = Float2DoubleOpenHashMap.this.value[Float2DoubleOpenHashMap.this.n];
/*  960 */         consumer.accept(entry);
/*      */       } 
/*  962 */       for (int pos = Float2DoubleOpenHashMap.this.n; pos-- != 0;) {
/*  963 */         if (Float.floatToIntBits(Float2DoubleOpenHashMap.this.key[pos]) != 0) {
/*  964 */           entry.key = Float2DoubleOpenHashMap.this.key[pos];
/*  965 */           entry.value = Float2DoubleOpenHashMap.this.value[pos];
/*  966 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Float2DoubleMap.FastEntrySet float2DoubleEntrySet() {
/*  972 */     if (this.entries == null)
/*  973 */       this.entries = new MapEntrySet(); 
/*  974 */     return this.entries;
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
/*      */     implements FloatIterator
/*      */   {
/*      */     public float nextFloat() {
/*  991 */       return Float2DoubleOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractFloatSet { private KeySet() {}
/*      */     
/*      */     public FloatIterator iterator() {
/*  997 */       return new Float2DoubleOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/* 1002 */       if (Float2DoubleOpenHashMap.this.containsNullKey)
/* 1003 */         consumer.accept(Float2DoubleOpenHashMap.this.key[Float2DoubleOpenHashMap.this.n]); 
/* 1004 */       for (int pos = Float2DoubleOpenHashMap.this.n; pos-- != 0; ) {
/* 1005 */         float k = Float2DoubleOpenHashMap.this.key[pos];
/* 1006 */         if (Float.floatToIntBits(k) != 0)
/* 1007 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1012 */       return Float2DoubleOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(float k) {
/* 1016 */       return Float2DoubleOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(float k) {
/* 1020 */       int oldSize = Float2DoubleOpenHashMap.this.size;
/* 1021 */       Float2DoubleOpenHashMap.this.remove(k);
/* 1022 */       return (Float2DoubleOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1026 */       Float2DoubleOpenHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public FloatSet keySet() {
/* 1031 */     if (this.keys == null)
/* 1032 */       this.keys = new KeySet(); 
/* 1033 */     return this.keys;
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
/*      */     implements DoubleIterator
/*      */   {
/*      */     public double nextDouble() {
/* 1050 */       return Float2DoubleOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public DoubleCollection values() {
/* 1055 */     if (this.values == null)
/* 1056 */       this.values = (DoubleCollection)new AbstractDoubleCollection()
/*      */         {
/*      */           public DoubleIterator iterator() {
/* 1059 */             return new Float2DoubleOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1063 */             return Float2DoubleOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(double v) {
/* 1067 */             return Float2DoubleOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1071 */             Float2DoubleOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(DoubleConsumer consumer)
/*      */           {
/* 1076 */             if (Float2DoubleOpenHashMap.this.containsNullKey)
/* 1077 */               consumer.accept(Float2DoubleOpenHashMap.this.value[Float2DoubleOpenHashMap.this.n]); 
/* 1078 */             for (int pos = Float2DoubleOpenHashMap.this.n; pos-- != 0;) {
/* 1079 */               if (Float.floatToIntBits(Float2DoubleOpenHashMap.this.key[pos]) != 0)
/* 1080 */                 consumer.accept(Float2DoubleOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1083 */     return this.values;
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
/* 1100 */     return trim(this.size);
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
/* 1124 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1125 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1126 */       return true; 
/*      */     try {
/* 1128 */       rehash(l);
/* 1129 */     } catch (OutOfMemoryError cantDoIt) {
/* 1130 */       return false;
/*      */     } 
/* 1132 */     return true;
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
/* 1148 */     float[] key = this.key;
/* 1149 */     double[] value = this.value;
/* 1150 */     int mask = newN - 1;
/* 1151 */     float[] newKey = new float[newN + 1];
/* 1152 */     double[] newValue = new double[newN + 1];
/* 1153 */     int i = this.n;
/* 1154 */     for (int j = realSize(); j-- != 0; ) {
/* 1155 */       while (Float.floatToIntBits(key[--i]) == 0); int pos;
/* 1156 */       if (Float.floatToIntBits(newKey[
/* 1157 */             pos = HashCommon.mix(HashCommon.float2int(key[i])) & mask]) != 0)
/* 1158 */         while (Float.floatToIntBits(newKey[pos = pos + 1 & mask]) != 0); 
/* 1159 */       newKey[pos] = key[i];
/* 1160 */       newValue[pos] = value[i];
/*      */     } 
/* 1162 */     newValue[newN] = value[this.n];
/* 1163 */     this.n = newN;
/* 1164 */     this.mask = mask;
/* 1165 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1166 */     this.key = newKey;
/* 1167 */     this.value = newValue;
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
/*      */   public Float2DoubleOpenHashMap clone() {
/*      */     Float2DoubleOpenHashMap c;
/*      */     try {
/* 1184 */       c = (Float2DoubleOpenHashMap)super.clone();
/* 1185 */     } catch (CloneNotSupportedException cantHappen) {
/* 1186 */       throw new InternalError();
/*      */     } 
/* 1188 */     c.keys = null;
/* 1189 */     c.values = null;
/* 1190 */     c.entries = null;
/* 1191 */     c.containsNullKey = this.containsNullKey;
/* 1192 */     c.key = (float[])this.key.clone();
/* 1193 */     c.value = (double[])this.value.clone();
/* 1194 */     return c;
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
/* 1207 */     int h = 0;
/* 1208 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1209 */       while (Float.floatToIntBits(this.key[i]) == 0)
/* 1210 */         i++; 
/* 1211 */       t = HashCommon.float2int(this.key[i]);
/* 1212 */       t ^= HashCommon.double2int(this.value[i]);
/* 1213 */       h += t;
/* 1214 */       i++;
/*      */     } 
/*      */     
/* 1217 */     if (this.containsNullKey)
/* 1218 */       h += HashCommon.double2int(this.value[this.n]); 
/* 1219 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1222 */     float[] key = this.key;
/* 1223 */     double[] value = this.value;
/* 1224 */     MapIterator i = new MapIterator();
/* 1225 */     s.defaultWriteObject();
/* 1226 */     for (int j = this.size; j-- != 0; ) {
/* 1227 */       int e = i.nextEntry();
/* 1228 */       s.writeFloat(key[e]);
/* 1229 */       s.writeDouble(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1234 */     s.defaultReadObject();
/* 1235 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1236 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1237 */     this.mask = this.n - 1;
/* 1238 */     float[] key = this.key = new float[this.n + 1];
/* 1239 */     double[] value = this.value = new double[this.n + 1];
/*      */ 
/*      */     
/* 1242 */     for (int i = this.size; i-- != 0; ) {
/* 1243 */       int pos; float k = s.readFloat();
/* 1244 */       double v = s.readDouble();
/* 1245 */       if (Float.floatToIntBits(k) == 0) {
/* 1246 */         pos = this.n;
/* 1247 */         this.containsNullKey = true;
/*      */       } else {
/* 1249 */         pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask;
/* 1250 */         while (Float.floatToIntBits(key[pos]) != 0)
/* 1251 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1253 */       key[pos] = k;
/* 1254 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\Float2DoubleOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */