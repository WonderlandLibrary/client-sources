/*      */ package it.unimi.dsi.fastutil.doubles;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
/*      */ import it.unimi.dsi.fastutil.floats.FloatCollection;
/*      */ import it.unimi.dsi.fastutil.floats.FloatIterator;
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
/*      */ public class Double2FloatOpenHashMap
/*      */   extends AbstractDouble2FloatMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient double[] key;
/*      */   protected transient float[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Double2FloatMap.FastEntrySet entries;
/*      */   protected transient DoubleSet keys;
/*      */   protected transient FloatCollection values;
/*      */   
/*      */   public Double2FloatOpenHashMap(int expected, float f) {
/*   96 */     if (f <= 0.0F || f > 1.0F)
/*   97 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*   98 */     if (expected < 0)
/*   99 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  100 */     this.f = f;
/*  101 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  102 */     this.mask = this.n - 1;
/*  103 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  104 */     this.key = new double[this.n + 1];
/*  105 */     this.value = new float[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2FloatOpenHashMap(int expected) {
/*  114 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2FloatOpenHashMap() {
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
/*      */   public Double2FloatOpenHashMap(Map<? extends Double, ? extends Float> m, float f) {
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
/*      */   public Double2FloatOpenHashMap(Map<? extends Double, ? extends Float> m) {
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
/*      */   public Double2FloatOpenHashMap(Double2FloatMap m, float f) {
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
/*      */   public Double2FloatOpenHashMap(Double2FloatMap m) {
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
/*      */   public Double2FloatOpenHashMap(double[] k, float[] v, float f) {
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
/*      */   public Double2FloatOpenHashMap(double[] k, float[] v) {
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
/*      */   private float removeEntry(int pos) {
/*  217 */     float oldValue = this.value[pos];
/*  218 */     this.size--;
/*  219 */     shiftKeys(pos);
/*  220 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  221 */       rehash(this.n / 2); 
/*  222 */     return oldValue;
/*      */   }
/*      */   private float removeNullEntry() {
/*  225 */     this.containsNullKey = false;
/*  226 */     float oldValue = this.value[this.n];
/*  227 */     this.size--;
/*  228 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  229 */       rehash(this.n / 2); 
/*  230 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Double, ? extends Float> m) {
/*  234 */     if (this.f <= 0.5D) {
/*  235 */       ensureCapacity(m.size());
/*      */     } else {
/*  237 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  239 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(double k) {
/*  243 */     if (Double.doubleToLongBits(k) == 0L) {
/*  244 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  246 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  249 */     if (Double.doubleToLongBits(
/*  250 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  252 */       return -(pos + 1); } 
/*  253 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  254 */       return pos;
/*      */     }
/*      */     while (true) {
/*  257 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  258 */         return -(pos + 1); 
/*  259 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr))
/*  260 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, double k, float v) {
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
/*      */   public float put(double k, float v) {
/*  275 */     int pos = find(k);
/*  276 */     if (pos < 0) {
/*  277 */       insert(-pos - 1, k, v);
/*  278 */       return this.defRetValue;
/*      */     } 
/*  280 */     float oldValue = this.value[pos];
/*  281 */     this.value[pos] = v;
/*  282 */     return oldValue;
/*      */   }
/*      */   private float addToValue(int pos, float incr) {
/*  285 */     float oldValue = this.value[pos];
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
/*      */   public float addTo(double k, float incr) {
/*      */     int pos;
/*  307 */     if (Double.doubleToLongBits(k) == 0L) {
/*  308 */       if (this.containsNullKey)
/*  309 */         return addToValue(this.n, incr); 
/*  310 */       pos = this.n;
/*  311 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  314 */       double[] key = this.key;
/*      */       double curr;
/*  316 */       if (Double.doubleToLongBits(
/*  317 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) != 0L) {
/*      */         
/*  319 */         if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k))
/*  320 */           return addToValue(pos, incr); 
/*  321 */         while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
/*  322 */           if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k))
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
/*  345 */     double[] key = this.key; while (true) {
/*      */       double curr; int last;
/*  347 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  349 */         if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/*  350 */           key[last] = 0.0D;
/*      */           return;
/*      */         } 
/*  353 */         int slot = (int)HashCommon.mix(Double.doubleToRawLongBits(curr)) & this.mask;
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
/*      */   public float remove(double k) {
/*  365 */     if (Double.doubleToLongBits(k) == 0L) {
/*  366 */       if (this.containsNullKey)
/*  367 */         return removeNullEntry(); 
/*  368 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  371 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  374 */     if (Double.doubleToLongBits(
/*  375 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  377 */       return this.defRetValue; } 
/*  378 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr))
/*  379 */       return removeEntry(pos); 
/*      */     while (true) {
/*  381 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  382 */         return this.defRetValue; 
/*  383 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  384 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public float get(double k) {
/*  390 */     if (Double.doubleToLongBits(k) == 0L) {
/*  391 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  393 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  396 */     if (Double.doubleToLongBits(
/*  397 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  399 */       return this.defRetValue; } 
/*  400 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  401 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  404 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  405 */         return this.defRetValue; 
/*  406 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  407 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(double k) {
/*  413 */     if (Double.doubleToLongBits(k) == 0L) {
/*  414 */       return this.containsNullKey;
/*      */     }
/*  416 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  419 */     if (Double.doubleToLongBits(
/*  420 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  422 */       return false; } 
/*  423 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  424 */       return true;
/*      */     }
/*      */     while (true) {
/*  427 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  428 */         return false; 
/*  429 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr))
/*  430 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(float v) {
/*  435 */     float[] value = this.value;
/*  436 */     double[] key = this.key;
/*  437 */     if (this.containsNullKey && Float.floatToIntBits(value[this.n]) == Float.floatToIntBits(v))
/*  438 */       return true; 
/*  439 */     for (int i = this.n; i-- != 0;) {
/*  440 */       if (Double.doubleToLongBits(key[i]) != 0L && Float.floatToIntBits(value[i]) == Float.floatToIntBits(v))
/*  441 */         return true; 
/*  442 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float getOrDefault(double k, float defaultValue) {
/*  448 */     if (Double.doubleToLongBits(k) == 0L) {
/*  449 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  451 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  454 */     if (Double.doubleToLongBits(
/*  455 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  457 */       return defaultValue; } 
/*  458 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  459 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  462 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  463 */         return defaultValue; 
/*  464 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  465 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public float putIfAbsent(double k, float v) {
/*  471 */     int pos = find(k);
/*  472 */     if (pos >= 0)
/*  473 */       return this.value[pos]; 
/*  474 */     insert(-pos - 1, k, v);
/*  475 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(double k, float v) {
/*  481 */     if (Double.doubleToLongBits(k) == 0L) {
/*  482 */       if (this.containsNullKey && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[this.n])) {
/*  483 */         removeNullEntry();
/*  484 */         return true;
/*      */       } 
/*  486 */       return false;
/*      */     } 
/*      */     
/*  489 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  492 */     if (Double.doubleToLongBits(
/*  493 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  495 */       return false; } 
/*  496 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr) && 
/*  497 */       Float.floatToIntBits(v) == Float.floatToIntBits(this.value[pos])) {
/*  498 */       removeEntry(pos);
/*  499 */       return true;
/*      */     } 
/*      */     while (true) {
/*  502 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  503 */         return false; 
/*  504 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr) && 
/*  505 */         Float.floatToIntBits(v) == Float.floatToIntBits(this.value[pos])) {
/*  506 */         removeEntry(pos);
/*  507 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(double k, float oldValue, float v) {
/*  514 */     int pos = find(k);
/*  515 */     if (pos < 0 || Float.floatToIntBits(oldValue) != Float.floatToIntBits(this.value[pos]))
/*  516 */       return false; 
/*  517 */     this.value[pos] = v;
/*  518 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public float replace(double k, float v) {
/*  523 */     int pos = find(k);
/*  524 */     if (pos < 0)
/*  525 */       return this.defRetValue; 
/*  526 */     float oldValue = this.value[pos];
/*  527 */     this.value[pos] = v;
/*  528 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public float computeIfAbsent(double k, DoubleUnaryOperator mappingFunction) {
/*  533 */     Objects.requireNonNull(mappingFunction);
/*  534 */     int pos = find(k);
/*  535 */     if (pos >= 0)
/*  536 */       return this.value[pos]; 
/*  537 */     float newValue = SafeMath.safeDoubleToFloat(mappingFunction.applyAsDouble(k));
/*  538 */     insert(-pos - 1, k, newValue);
/*  539 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float computeIfAbsentNullable(double k, DoubleFunction<? extends Float> mappingFunction) {
/*  545 */     Objects.requireNonNull(mappingFunction);
/*  546 */     int pos = find(k);
/*  547 */     if (pos >= 0)
/*  548 */       return this.value[pos]; 
/*  549 */     Float newValue = mappingFunction.apply(k);
/*  550 */     if (newValue == null)
/*  551 */       return this.defRetValue; 
/*  552 */     float v = newValue.floatValue();
/*  553 */     insert(-pos - 1, k, v);
/*  554 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float computeIfPresent(double k, BiFunction<? super Double, ? super Float, ? extends Float> remappingFunction) {
/*  560 */     Objects.requireNonNull(remappingFunction);
/*  561 */     int pos = find(k);
/*  562 */     if (pos < 0)
/*  563 */       return this.defRetValue; 
/*  564 */     Float newValue = remappingFunction.apply(Double.valueOf(k), Float.valueOf(this.value[pos]));
/*  565 */     if (newValue == null) {
/*  566 */       if (Double.doubleToLongBits(k) == 0L) {
/*  567 */         removeNullEntry();
/*      */       } else {
/*  569 */         removeEntry(pos);
/*  570 */       }  return this.defRetValue;
/*      */     } 
/*  572 */     this.value[pos] = newValue.floatValue(); return newValue.floatValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float compute(double k, BiFunction<? super Double, ? super Float, ? extends Float> remappingFunction) {
/*  578 */     Objects.requireNonNull(remappingFunction);
/*  579 */     int pos = find(k);
/*  580 */     Float newValue = remappingFunction.apply(Double.valueOf(k), (pos >= 0) ? Float.valueOf(this.value[pos]) : null);
/*  581 */     if (newValue == null) {
/*  582 */       if (pos >= 0)
/*  583 */         if (Double.doubleToLongBits(k) == 0L) {
/*  584 */           removeNullEntry();
/*      */         } else {
/*  586 */           removeEntry(pos);
/*      */         }  
/*  588 */       return this.defRetValue;
/*      */     } 
/*  590 */     float newVal = newValue.floatValue();
/*  591 */     if (pos < 0) {
/*  592 */       insert(-pos - 1, k, newVal);
/*  593 */       return newVal;
/*      */     } 
/*  595 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float merge(double k, float v, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/*  601 */     Objects.requireNonNull(remappingFunction);
/*  602 */     int pos = find(k);
/*  603 */     if (pos < 0) {
/*  604 */       insert(-pos - 1, k, v);
/*  605 */       return v;
/*      */     } 
/*  607 */     Float newValue = remappingFunction.apply(Float.valueOf(this.value[pos]), Float.valueOf(v));
/*  608 */     if (newValue == null) {
/*  609 */       if (Double.doubleToLongBits(k) == 0L) {
/*  610 */         removeNullEntry();
/*      */       } else {
/*  612 */         removeEntry(pos);
/*  613 */       }  return this.defRetValue;
/*      */     } 
/*  615 */     this.value[pos] = newValue.floatValue(); return newValue.floatValue();
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
/*  626 */     if (this.size == 0)
/*      */       return; 
/*  628 */     this.size = 0;
/*  629 */     this.containsNullKey = false;
/*  630 */     Arrays.fill(this.key, 0.0D);
/*      */   }
/*      */   
/*      */   public int size() {
/*  634 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  638 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Double2FloatMap.Entry, Map.Entry<Double, Float>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  650 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public double getDoubleKey() {
/*  656 */       return Double2FloatOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public float getFloatValue() {
/*  660 */       return Double2FloatOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public float setValue(float v) {
/*  664 */       float oldValue = Double2FloatOpenHashMap.this.value[this.index];
/*  665 */       Double2FloatOpenHashMap.this.value[this.index] = v;
/*  666 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double getKey() {
/*  676 */       return Double.valueOf(Double2FloatOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float getValue() {
/*  686 */       return Float.valueOf(Double2FloatOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float setValue(Float v) {
/*  696 */       return Float.valueOf(setValue(v.floatValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  701 */       if (!(o instanceof Map.Entry))
/*  702 */         return false; 
/*  703 */       Map.Entry<Double, Float> e = (Map.Entry<Double, Float>)o;
/*  704 */       return (Double.doubleToLongBits(Double2FloatOpenHashMap.this.key[this.index]) == Double.doubleToLongBits(((Double)e.getKey()).doubleValue()) && 
/*  705 */         Float.floatToIntBits(Double2FloatOpenHashMap.this.value[this.index]) == Float.floatToIntBits(((Float)e.getValue()).floatValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  709 */       return HashCommon.double2int(Double2FloatOpenHashMap.this.key[this.index]) ^ 
/*  710 */         HashCommon.float2int(Double2FloatOpenHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  714 */       return Double2FloatOpenHashMap.this.key[this.index] + "=>" + Double2FloatOpenHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  724 */     int pos = Double2FloatOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  731 */     int last = -1;
/*      */     
/*  733 */     int c = Double2FloatOpenHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  737 */     boolean mustReturnNullKey = Double2FloatOpenHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     DoubleArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  744 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  747 */       if (!hasNext())
/*  748 */         throw new NoSuchElementException(); 
/*  749 */       this.c--;
/*  750 */       if (this.mustReturnNullKey) {
/*  751 */         this.mustReturnNullKey = false;
/*  752 */         return this.last = Double2FloatOpenHashMap.this.n;
/*      */       } 
/*  754 */       double[] key = Double2FloatOpenHashMap.this.key;
/*      */       while (true) {
/*  756 */         if (--this.pos < 0) {
/*      */           
/*  758 */           this.last = Integer.MIN_VALUE;
/*  759 */           double k = this.wrapped.getDouble(-this.pos - 1);
/*  760 */           int p = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2FloatOpenHashMap.this.mask;
/*  761 */           while (Double.doubleToLongBits(k) != Double.doubleToLongBits(key[p]))
/*  762 */             p = p + 1 & Double2FloatOpenHashMap.this.mask; 
/*  763 */           return p;
/*      */         } 
/*  765 */         if (Double.doubleToLongBits(key[this.pos]) != 0L) {
/*  766 */           return this.last = this.pos;
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
/*  780 */       double[] key = Double2FloatOpenHashMap.this.key; while (true) {
/*      */         double curr; int last;
/*  782 */         pos = (last = pos) + 1 & Double2FloatOpenHashMap.this.mask;
/*      */         while (true) {
/*  784 */           if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/*  785 */             key[last] = 0.0D;
/*      */             return;
/*      */           } 
/*  788 */           int slot = (int)HashCommon.mix(Double.doubleToRawLongBits(curr)) & Double2FloatOpenHashMap.this.mask;
/*  789 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  791 */           pos = pos + 1 & Double2FloatOpenHashMap.this.mask;
/*      */         } 
/*  793 */         if (pos < last) {
/*  794 */           if (this.wrapped == null)
/*  795 */             this.wrapped = new DoubleArrayList(2); 
/*  796 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  798 */         key[last] = curr;
/*  799 */         Double2FloatOpenHashMap.this.value[last] = Double2FloatOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  803 */       if (this.last == -1)
/*  804 */         throw new IllegalStateException(); 
/*  805 */       if (this.last == Double2FloatOpenHashMap.this.n) {
/*  806 */         Double2FloatOpenHashMap.this.containsNullKey = false;
/*  807 */       } else if (this.pos >= 0) {
/*  808 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  811 */         Double2FloatOpenHashMap.this.remove(this.wrapped.getDouble(-this.pos - 1));
/*  812 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  815 */       Double2FloatOpenHashMap.this.size--;
/*  816 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  821 */       int i = n;
/*  822 */       while (i-- != 0 && hasNext())
/*  823 */         nextEntry(); 
/*  824 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Double2FloatMap.Entry> { private Double2FloatOpenHashMap.MapEntry entry;
/*      */     
/*      */     public Double2FloatOpenHashMap.MapEntry next() {
/*  831 */       return this.entry = new Double2FloatOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  835 */       super.remove();
/*  836 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Double2FloatMap.Entry> { private FastEntryIterator() {
/*  840 */       this.entry = new Double2FloatOpenHashMap.MapEntry();
/*      */     } private final Double2FloatOpenHashMap.MapEntry entry;
/*      */     public Double2FloatOpenHashMap.MapEntry next() {
/*  843 */       this.entry.index = nextEntry();
/*  844 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Double2FloatMap.Entry> implements Double2FloatMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Double2FloatMap.Entry> iterator() {
/*  850 */       return new Double2FloatOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Double2FloatMap.Entry> fastIterator() {
/*  854 */       return new Double2FloatOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  859 */       if (!(o instanceof Map.Entry))
/*  860 */         return false; 
/*  861 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  862 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/*  863 */         return false; 
/*  864 */       if (e.getValue() == null || !(e.getValue() instanceof Float))
/*  865 */         return false; 
/*  866 */       double k = ((Double)e.getKey()).doubleValue();
/*  867 */       float v = ((Float)e.getValue()).floatValue();
/*  868 */       if (Double.doubleToLongBits(k) == 0L) {
/*  869 */         return (Double2FloatOpenHashMap.this.containsNullKey && 
/*  870 */           Float.floatToIntBits(Double2FloatOpenHashMap.this.value[Double2FloatOpenHashMap.this.n]) == Float.floatToIntBits(v));
/*      */       }
/*  872 */       double[] key = Double2FloatOpenHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/*  875 */       if (Double.doubleToLongBits(
/*  876 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2FloatOpenHashMap.this.mask]) == 0L)
/*      */       {
/*  878 */         return false; } 
/*  879 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  880 */         return (Float.floatToIntBits(Double2FloatOpenHashMap.this.value[pos]) == Float.floatToIntBits(v));
/*      */       }
/*      */       while (true) {
/*  883 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2FloatOpenHashMap.this.mask]) == 0L)
/*  884 */           return false; 
/*  885 */         if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  886 */           return (Float.floatToIntBits(Double2FloatOpenHashMap.this.value[pos]) == Float.floatToIntBits(v));
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  892 */       if (!(o instanceof Map.Entry))
/*  893 */         return false; 
/*  894 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  895 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/*  896 */         return false; 
/*  897 */       if (e.getValue() == null || !(e.getValue() instanceof Float))
/*  898 */         return false; 
/*  899 */       double k = ((Double)e.getKey()).doubleValue();
/*  900 */       float v = ((Float)e.getValue()).floatValue();
/*  901 */       if (Double.doubleToLongBits(k) == 0L) {
/*  902 */         if (Double2FloatOpenHashMap.this.containsNullKey && Float.floatToIntBits(Double2FloatOpenHashMap.this.value[Double2FloatOpenHashMap.this.n]) == Float.floatToIntBits(v)) {
/*  903 */           Double2FloatOpenHashMap.this.removeNullEntry();
/*  904 */           return true;
/*      */         } 
/*  906 */         return false;
/*      */       } 
/*      */       
/*  909 */       double[] key = Double2FloatOpenHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/*  912 */       if (Double.doubleToLongBits(
/*  913 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2FloatOpenHashMap.this.mask]) == 0L)
/*      */       {
/*  915 */         return false; } 
/*  916 */       if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
/*  917 */         if (Float.floatToIntBits(Double2FloatOpenHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
/*  918 */           Double2FloatOpenHashMap.this.removeEntry(pos);
/*  919 */           return true;
/*      */         } 
/*  921 */         return false;
/*      */       } 
/*      */       while (true) {
/*  924 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2FloatOpenHashMap.this.mask]) == 0L)
/*  925 */           return false; 
/*  926 */         if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k) && 
/*  927 */           Float.floatToIntBits(Double2FloatOpenHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
/*  928 */           Double2FloatOpenHashMap.this.removeEntry(pos);
/*  929 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  936 */       return Double2FloatOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  940 */       Double2FloatOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Double2FloatMap.Entry> consumer) {
/*  945 */       if (Double2FloatOpenHashMap.this.containsNullKey)
/*  946 */         consumer.accept(new AbstractDouble2FloatMap.BasicEntry(Double2FloatOpenHashMap.this.key[Double2FloatOpenHashMap.this.n], Double2FloatOpenHashMap.this.value[Double2FloatOpenHashMap.this.n])); 
/*  947 */       for (int pos = Double2FloatOpenHashMap.this.n; pos-- != 0;) {
/*  948 */         if (Double.doubleToLongBits(Double2FloatOpenHashMap.this.key[pos]) != 0L)
/*  949 */           consumer.accept(new AbstractDouble2FloatMap.BasicEntry(Double2FloatOpenHashMap.this.key[pos], Double2FloatOpenHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Double2FloatMap.Entry> consumer) {
/*  954 */       AbstractDouble2FloatMap.BasicEntry entry = new AbstractDouble2FloatMap.BasicEntry();
/*  955 */       if (Double2FloatOpenHashMap.this.containsNullKey) {
/*  956 */         entry.key = Double2FloatOpenHashMap.this.key[Double2FloatOpenHashMap.this.n];
/*  957 */         entry.value = Double2FloatOpenHashMap.this.value[Double2FloatOpenHashMap.this.n];
/*  958 */         consumer.accept(entry);
/*      */       } 
/*  960 */       for (int pos = Double2FloatOpenHashMap.this.n; pos-- != 0;) {
/*  961 */         if (Double.doubleToLongBits(Double2FloatOpenHashMap.this.key[pos]) != 0L) {
/*  962 */           entry.key = Double2FloatOpenHashMap.this.key[pos];
/*  963 */           entry.value = Double2FloatOpenHashMap.this.value[pos];
/*  964 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Double2FloatMap.FastEntrySet double2FloatEntrySet() {
/*  970 */     if (this.entries == null)
/*  971 */       this.entries = new MapEntrySet(); 
/*  972 */     return this.entries;
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
/*  989 */       return Double2FloatOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractDoubleSet { private KeySet() {}
/*      */     
/*      */     public DoubleIterator iterator() {
/*  995 */       return new Double2FloatOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/* 1000 */       if (Double2FloatOpenHashMap.this.containsNullKey)
/* 1001 */         consumer.accept(Double2FloatOpenHashMap.this.key[Double2FloatOpenHashMap.this.n]); 
/* 1002 */       for (int pos = Double2FloatOpenHashMap.this.n; pos-- != 0; ) {
/* 1003 */         double k = Double2FloatOpenHashMap.this.key[pos];
/* 1004 */         if (Double.doubleToLongBits(k) != 0L)
/* 1005 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1010 */       return Double2FloatOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(double k) {
/* 1014 */       return Double2FloatOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(double k) {
/* 1018 */       int oldSize = Double2FloatOpenHashMap.this.size;
/* 1019 */       Double2FloatOpenHashMap.this.remove(k);
/* 1020 */       return (Double2FloatOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1024 */       Double2FloatOpenHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public DoubleSet keySet() {
/* 1029 */     if (this.keys == null)
/* 1030 */       this.keys = new KeySet(); 
/* 1031 */     return this.keys;
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
/*      */     implements FloatIterator
/*      */   {
/*      */     public float nextFloat() {
/* 1048 */       return Double2FloatOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public FloatCollection values() {
/* 1053 */     if (this.values == null)
/* 1054 */       this.values = (FloatCollection)new AbstractFloatCollection()
/*      */         {
/*      */           public FloatIterator iterator() {
/* 1057 */             return new Double2FloatOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1061 */             return Double2FloatOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(float v) {
/* 1065 */             return Double2FloatOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1069 */             Double2FloatOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(DoubleConsumer consumer)
/*      */           {
/* 1074 */             if (Double2FloatOpenHashMap.this.containsNullKey)
/* 1075 */               consumer.accept(Double2FloatOpenHashMap.this.value[Double2FloatOpenHashMap.this.n]); 
/* 1076 */             for (int pos = Double2FloatOpenHashMap.this.n; pos-- != 0;) {
/* 1077 */               if (Double.doubleToLongBits(Double2FloatOpenHashMap.this.key[pos]) != 0L)
/* 1078 */                 consumer.accept(Double2FloatOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1081 */     return this.values;
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
/* 1098 */     return trim(this.size);
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
/* 1122 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1123 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1124 */       return true; 
/*      */     try {
/* 1126 */       rehash(l);
/* 1127 */     } catch (OutOfMemoryError cantDoIt) {
/* 1128 */       return false;
/*      */     } 
/* 1130 */     return true;
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
/* 1146 */     double[] key = this.key;
/* 1147 */     float[] value = this.value;
/* 1148 */     int mask = newN - 1;
/* 1149 */     double[] newKey = new double[newN + 1];
/* 1150 */     float[] newValue = new float[newN + 1];
/* 1151 */     int i = this.n;
/* 1152 */     for (int j = realSize(); j-- != 0; ) {
/* 1153 */       while (Double.doubleToLongBits(key[--i]) == 0L); int pos;
/* 1154 */       if (Double.doubleToLongBits(newKey[
/* 1155 */             pos = (int)HashCommon.mix(Double.doubleToRawLongBits(key[i])) & mask]) != 0L)
/*      */       {
/* 1157 */         while (Double.doubleToLongBits(newKey[pos = pos + 1 & mask]) != 0L); } 
/* 1158 */       newKey[pos] = key[i];
/* 1159 */       newValue[pos] = value[i];
/*      */     } 
/* 1161 */     newValue[newN] = value[this.n];
/* 1162 */     this.n = newN;
/* 1163 */     this.mask = mask;
/* 1164 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1165 */     this.key = newKey;
/* 1166 */     this.value = newValue;
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
/*      */   public Double2FloatOpenHashMap clone() {
/*      */     Double2FloatOpenHashMap c;
/*      */     try {
/* 1183 */       c = (Double2FloatOpenHashMap)super.clone();
/* 1184 */     } catch (CloneNotSupportedException cantHappen) {
/* 1185 */       throw new InternalError();
/*      */     } 
/* 1187 */     c.keys = null;
/* 1188 */     c.values = null;
/* 1189 */     c.entries = null;
/* 1190 */     c.containsNullKey = this.containsNullKey;
/* 1191 */     c.key = (double[])this.key.clone();
/* 1192 */     c.value = (float[])this.value.clone();
/* 1193 */     return c;
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
/* 1206 */     int h = 0;
/* 1207 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1208 */       while (Double.doubleToLongBits(this.key[i]) == 0L)
/* 1209 */         i++; 
/* 1210 */       t = HashCommon.double2int(this.key[i]);
/* 1211 */       t ^= HashCommon.float2int(this.value[i]);
/* 1212 */       h += t;
/* 1213 */       i++;
/*      */     } 
/*      */     
/* 1216 */     if (this.containsNullKey)
/* 1217 */       h += HashCommon.float2int(this.value[this.n]); 
/* 1218 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1221 */     double[] key = this.key;
/* 1222 */     float[] value = this.value;
/* 1223 */     MapIterator i = new MapIterator();
/* 1224 */     s.defaultWriteObject();
/* 1225 */     for (int j = this.size; j-- != 0; ) {
/* 1226 */       int e = i.nextEntry();
/* 1227 */       s.writeDouble(key[e]);
/* 1228 */       s.writeFloat(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1233 */     s.defaultReadObject();
/* 1234 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1235 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1236 */     this.mask = this.n - 1;
/* 1237 */     double[] key = this.key = new double[this.n + 1];
/* 1238 */     float[] value = this.value = new float[this.n + 1];
/*      */ 
/*      */     
/* 1241 */     for (int i = this.size; i-- != 0; ) {
/* 1242 */       int pos; double k = s.readDouble();
/* 1243 */       float v = s.readFloat();
/* 1244 */       if (Double.doubleToLongBits(k) == 0L) {
/* 1245 */         pos = this.n;
/* 1246 */         this.containsNullKey = true;
/*      */       } else {
/* 1248 */         pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask;
/* 1249 */         while (Double.doubleToLongBits(key[pos]) != 0L)
/* 1250 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1252 */       key[pos] = k;
/* 1253 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2FloatOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */