/*      */ package it.unimi.dsi.fastutil.floats;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
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
/*      */ 
/*      */ 
/*      */ public class Float2FloatOpenHashMap
/*      */   extends AbstractFloat2FloatMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient float[] key;
/*      */   protected transient float[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Float2FloatMap.FastEntrySet entries;
/*      */   protected transient FloatSet keys;
/*      */   protected transient FloatCollection values;
/*      */   
/*      */   public Float2FloatOpenHashMap(int expected, float f) {
/*   96 */     if (f <= 0.0F || f > 1.0F)
/*   97 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*   98 */     if (expected < 0)
/*   99 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  100 */     this.f = f;
/*  101 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  102 */     this.mask = this.n - 1;
/*  103 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  104 */     this.key = new float[this.n + 1];
/*  105 */     this.value = new float[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2FloatOpenHashMap(int expected) {
/*  114 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2FloatOpenHashMap() {
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
/*      */   public Float2FloatOpenHashMap(Map<? extends Float, ? extends Float> m, float f) {
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
/*      */   public Float2FloatOpenHashMap(Map<? extends Float, ? extends Float> m) {
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
/*      */   public Float2FloatOpenHashMap(Float2FloatMap m, float f) {
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
/*      */   public Float2FloatOpenHashMap(Float2FloatMap m) {
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
/*      */   public Float2FloatOpenHashMap(float[] k, float[] v, float f) {
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
/*      */   public Float2FloatOpenHashMap(float[] k, float[] v) {
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
/*      */   public void putAll(Map<? extends Float, ? extends Float> m) {
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
/*      */   private void insert(int pos, float k, float v) {
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
/*      */   public float put(float k, float v) {
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
/*      */   public float addTo(float k, float incr) {
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
/*      */   public float remove(float k) {
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
/*      */   public float get(float k) {
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
/*      */   public boolean containsValue(float v) {
/*  435 */     float[] value = this.value;
/*  436 */     float[] key = this.key;
/*  437 */     if (this.containsNullKey && Float.floatToIntBits(value[this.n]) == Float.floatToIntBits(v))
/*  438 */       return true; 
/*  439 */     for (int i = this.n; i-- != 0;) {
/*  440 */       if (Float.floatToIntBits(key[i]) != 0 && Float.floatToIntBits(value[i]) == Float.floatToIntBits(v))
/*  441 */         return true; 
/*  442 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float getOrDefault(float k, float defaultValue) {
/*  448 */     if (Float.floatToIntBits(k) == 0) {
/*  449 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  451 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  454 */     if (Float.floatToIntBits(
/*  455 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  457 */       return defaultValue; } 
/*  458 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  459 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  462 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  463 */         return defaultValue; 
/*  464 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  465 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public float putIfAbsent(float k, float v) {
/*  471 */     int pos = find(k);
/*  472 */     if (pos >= 0)
/*  473 */       return this.value[pos]; 
/*  474 */     insert(-pos - 1, k, v);
/*  475 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(float k, float v) {
/*  481 */     if (Float.floatToIntBits(k) == 0) {
/*  482 */       if (this.containsNullKey && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[this.n])) {
/*  483 */         removeNullEntry();
/*  484 */         return true;
/*      */       } 
/*  486 */       return false;
/*      */     } 
/*      */     
/*  489 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  492 */     if (Float.floatToIntBits(
/*  493 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  495 */       return false; } 
/*  496 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr) && 
/*  497 */       Float.floatToIntBits(v) == Float.floatToIntBits(this.value[pos])) {
/*  498 */       removeEntry(pos);
/*  499 */       return true;
/*      */     } 
/*      */     while (true) {
/*  502 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  503 */         return false; 
/*  504 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr) && 
/*  505 */         Float.floatToIntBits(v) == Float.floatToIntBits(this.value[pos])) {
/*  506 */         removeEntry(pos);
/*  507 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(float k, float oldValue, float v) {
/*  514 */     int pos = find(k);
/*  515 */     if (pos < 0 || Float.floatToIntBits(oldValue) != Float.floatToIntBits(this.value[pos]))
/*  516 */       return false; 
/*  517 */     this.value[pos] = v;
/*  518 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public float replace(float k, float v) {
/*  523 */     int pos = find(k);
/*  524 */     if (pos < 0)
/*  525 */       return this.defRetValue; 
/*  526 */     float oldValue = this.value[pos];
/*  527 */     this.value[pos] = v;
/*  528 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public float computeIfAbsent(float k, DoubleUnaryOperator mappingFunction) {
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
/*      */   public float computeIfAbsentNullable(float k, DoubleFunction<? extends Float> mappingFunction) {
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
/*      */   public float computeIfPresent(float k, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/*  560 */     Objects.requireNonNull(remappingFunction);
/*  561 */     int pos = find(k);
/*  562 */     if (pos < 0)
/*  563 */       return this.defRetValue; 
/*  564 */     Float newValue = remappingFunction.apply(Float.valueOf(k), Float.valueOf(this.value[pos]));
/*  565 */     if (newValue == null) {
/*  566 */       if (Float.floatToIntBits(k) == 0) {
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
/*      */   public float compute(float k, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/*  578 */     Objects.requireNonNull(remappingFunction);
/*  579 */     int pos = find(k);
/*  580 */     Float newValue = remappingFunction.apply(Float.valueOf(k), (pos >= 0) ? Float.valueOf(this.value[pos]) : null);
/*  581 */     if (newValue == null) {
/*  582 */       if (pos >= 0)
/*  583 */         if (Float.floatToIntBits(k) == 0) {
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
/*      */   public float merge(float k, float v, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/*  601 */     Objects.requireNonNull(remappingFunction);
/*  602 */     int pos = find(k);
/*  603 */     if (pos < 0) {
/*  604 */       insert(-pos - 1, k, v);
/*  605 */       return v;
/*      */     } 
/*  607 */     Float newValue = remappingFunction.apply(Float.valueOf(this.value[pos]), Float.valueOf(v));
/*  608 */     if (newValue == null) {
/*  609 */       if (Float.floatToIntBits(k) == 0) {
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
/*  630 */     Arrays.fill(this.key, 0.0F);
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
/*      */     implements Float2FloatMap.Entry, Map.Entry<Float, Float>
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
/*      */     public float getFloatKey() {
/*  656 */       return Float2FloatOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public float getFloatValue() {
/*  660 */       return Float2FloatOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public float setValue(float v) {
/*  664 */       float oldValue = Float2FloatOpenHashMap.this.value[this.index];
/*  665 */       Float2FloatOpenHashMap.this.value[this.index] = v;
/*  666 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float getKey() {
/*  676 */       return Float.valueOf(Float2FloatOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float getValue() {
/*  686 */       return Float.valueOf(Float2FloatOpenHashMap.this.value[this.index]);
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
/*  703 */       Map.Entry<Float, Float> e = (Map.Entry<Float, Float>)o;
/*  704 */       return (Float.floatToIntBits(Float2FloatOpenHashMap.this.key[this.index]) == Float.floatToIntBits(((Float)e.getKey()).floatValue()) && 
/*  705 */         Float.floatToIntBits(Float2FloatOpenHashMap.this.value[this.index]) == Float.floatToIntBits(((Float)e.getValue()).floatValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  709 */       return HashCommon.float2int(Float2FloatOpenHashMap.this.key[this.index]) ^ 
/*  710 */         HashCommon.float2int(Float2FloatOpenHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  714 */       return Float2FloatOpenHashMap.this.key[this.index] + "=>" + Float2FloatOpenHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  724 */     int pos = Float2FloatOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  731 */     int last = -1;
/*      */     
/*  733 */     int c = Float2FloatOpenHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  737 */     boolean mustReturnNullKey = Float2FloatOpenHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     FloatArrayList wrapped;
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
/*  752 */         return this.last = Float2FloatOpenHashMap.this.n;
/*      */       } 
/*  754 */       float[] key = Float2FloatOpenHashMap.this.key;
/*      */       while (true) {
/*  756 */         if (--this.pos < 0) {
/*      */           
/*  758 */           this.last = Integer.MIN_VALUE;
/*  759 */           float k = this.wrapped.getFloat(-this.pos - 1);
/*  760 */           int p = HashCommon.mix(HashCommon.float2int(k)) & Float2FloatOpenHashMap.this.mask;
/*  761 */           while (Float.floatToIntBits(k) != Float.floatToIntBits(key[p]))
/*  762 */             p = p + 1 & Float2FloatOpenHashMap.this.mask; 
/*  763 */           return p;
/*      */         } 
/*  765 */         if (Float.floatToIntBits(key[this.pos]) != 0) {
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
/*  780 */       float[] key = Float2FloatOpenHashMap.this.key; while (true) {
/*      */         float curr; int last;
/*  782 */         pos = (last = pos) + 1 & Float2FloatOpenHashMap.this.mask;
/*      */         while (true) {
/*  784 */           if (Float.floatToIntBits(curr = key[pos]) == 0) {
/*  785 */             key[last] = 0.0F;
/*      */             return;
/*      */           } 
/*  788 */           int slot = HashCommon.mix(HashCommon.float2int(curr)) & Float2FloatOpenHashMap.this.mask;
/*      */           
/*  790 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  792 */           pos = pos + 1 & Float2FloatOpenHashMap.this.mask;
/*      */         } 
/*  794 */         if (pos < last) {
/*  795 */           if (this.wrapped == null)
/*  796 */             this.wrapped = new FloatArrayList(2); 
/*  797 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  799 */         key[last] = curr;
/*  800 */         Float2FloatOpenHashMap.this.value[last] = Float2FloatOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  804 */       if (this.last == -1)
/*  805 */         throw new IllegalStateException(); 
/*  806 */       if (this.last == Float2FloatOpenHashMap.this.n) {
/*  807 */         Float2FloatOpenHashMap.this.containsNullKey = false;
/*  808 */       } else if (this.pos >= 0) {
/*  809 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  812 */         Float2FloatOpenHashMap.this.remove(this.wrapped.getFloat(-this.pos - 1));
/*  813 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  816 */       Float2FloatOpenHashMap.this.size--;
/*  817 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  822 */       int i = n;
/*  823 */       while (i-- != 0 && hasNext())
/*  824 */         nextEntry(); 
/*  825 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Float2FloatMap.Entry> { private Float2FloatOpenHashMap.MapEntry entry;
/*      */     
/*      */     public Float2FloatOpenHashMap.MapEntry next() {
/*  832 */       return this.entry = new Float2FloatOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  836 */       super.remove();
/*  837 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Float2FloatMap.Entry> { private FastEntryIterator() {
/*  841 */       this.entry = new Float2FloatOpenHashMap.MapEntry();
/*      */     } private final Float2FloatOpenHashMap.MapEntry entry;
/*      */     public Float2FloatOpenHashMap.MapEntry next() {
/*  844 */       this.entry.index = nextEntry();
/*  845 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Float2FloatMap.Entry> implements Float2FloatMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Float2FloatMap.Entry> iterator() {
/*  851 */       return new Float2FloatOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Float2FloatMap.Entry> fastIterator() {
/*  855 */       return new Float2FloatOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  860 */       if (!(o instanceof Map.Entry))
/*  861 */         return false; 
/*  862 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  863 */       if (e.getKey() == null || !(e.getKey() instanceof Float))
/*  864 */         return false; 
/*  865 */       if (e.getValue() == null || !(e.getValue() instanceof Float))
/*  866 */         return false; 
/*  867 */       float k = ((Float)e.getKey()).floatValue();
/*  868 */       float v = ((Float)e.getValue()).floatValue();
/*  869 */       if (Float.floatToIntBits(k) == 0) {
/*  870 */         return (Float2FloatOpenHashMap.this.containsNullKey && 
/*  871 */           Float.floatToIntBits(Float2FloatOpenHashMap.this.value[Float2FloatOpenHashMap.this.n]) == Float.floatToIntBits(v));
/*      */       }
/*  873 */       float[] key = Float2FloatOpenHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/*  876 */       if (Float.floatToIntBits(
/*  877 */           curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & Float2FloatOpenHashMap.this.mask]) == 0)
/*      */       {
/*  879 */         return false; } 
/*  880 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  881 */         return (Float.floatToIntBits(Float2FloatOpenHashMap.this.value[pos]) == Float.floatToIntBits(v));
/*      */       }
/*      */       while (true) {
/*  884 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2FloatOpenHashMap.this.mask]) == 0)
/*  885 */           return false; 
/*  886 */         if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  887 */           return (Float.floatToIntBits(Float2FloatOpenHashMap.this.value[pos]) == Float.floatToIntBits(v));
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  893 */       if (!(o instanceof Map.Entry))
/*  894 */         return false; 
/*  895 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  896 */       if (e.getKey() == null || !(e.getKey() instanceof Float))
/*  897 */         return false; 
/*  898 */       if (e.getValue() == null || !(e.getValue() instanceof Float))
/*  899 */         return false; 
/*  900 */       float k = ((Float)e.getKey()).floatValue();
/*  901 */       float v = ((Float)e.getValue()).floatValue();
/*  902 */       if (Float.floatToIntBits(k) == 0) {
/*  903 */         if (Float2FloatOpenHashMap.this.containsNullKey && Float.floatToIntBits(Float2FloatOpenHashMap.this.value[Float2FloatOpenHashMap.this.n]) == Float.floatToIntBits(v)) {
/*  904 */           Float2FloatOpenHashMap.this.removeNullEntry();
/*  905 */           return true;
/*      */         } 
/*  907 */         return false;
/*      */       } 
/*      */       
/*  910 */       float[] key = Float2FloatOpenHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/*  913 */       if (Float.floatToIntBits(
/*  914 */           curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & Float2FloatOpenHashMap.this.mask]) == 0)
/*      */       {
/*  916 */         return false; } 
/*  917 */       if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
/*  918 */         if (Float.floatToIntBits(Float2FloatOpenHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
/*  919 */           Float2FloatOpenHashMap.this.removeEntry(pos);
/*  920 */           return true;
/*      */         } 
/*  922 */         return false;
/*      */       } 
/*      */       while (true) {
/*  925 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2FloatOpenHashMap.this.mask]) == 0)
/*  926 */           return false; 
/*  927 */         if (Float.floatToIntBits(curr) == Float.floatToIntBits(k) && 
/*  928 */           Float.floatToIntBits(Float2FloatOpenHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
/*  929 */           Float2FloatOpenHashMap.this.removeEntry(pos);
/*  930 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  937 */       return Float2FloatOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  941 */       Float2FloatOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Float2FloatMap.Entry> consumer) {
/*  946 */       if (Float2FloatOpenHashMap.this.containsNullKey)
/*  947 */         consumer.accept(new AbstractFloat2FloatMap.BasicEntry(Float2FloatOpenHashMap.this.key[Float2FloatOpenHashMap.this.n], Float2FloatOpenHashMap.this.value[Float2FloatOpenHashMap.this.n])); 
/*  948 */       for (int pos = Float2FloatOpenHashMap.this.n; pos-- != 0;) {
/*  949 */         if (Float.floatToIntBits(Float2FloatOpenHashMap.this.key[pos]) != 0)
/*  950 */           consumer.accept(new AbstractFloat2FloatMap.BasicEntry(Float2FloatOpenHashMap.this.key[pos], Float2FloatOpenHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Float2FloatMap.Entry> consumer) {
/*  955 */       AbstractFloat2FloatMap.BasicEntry entry = new AbstractFloat2FloatMap.BasicEntry();
/*  956 */       if (Float2FloatOpenHashMap.this.containsNullKey) {
/*  957 */         entry.key = Float2FloatOpenHashMap.this.key[Float2FloatOpenHashMap.this.n];
/*  958 */         entry.value = Float2FloatOpenHashMap.this.value[Float2FloatOpenHashMap.this.n];
/*  959 */         consumer.accept(entry);
/*      */       } 
/*  961 */       for (int pos = Float2FloatOpenHashMap.this.n; pos-- != 0;) {
/*  962 */         if (Float.floatToIntBits(Float2FloatOpenHashMap.this.key[pos]) != 0) {
/*  963 */           entry.key = Float2FloatOpenHashMap.this.key[pos];
/*  964 */           entry.value = Float2FloatOpenHashMap.this.value[pos];
/*  965 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Float2FloatMap.FastEntrySet float2FloatEntrySet() {
/*  971 */     if (this.entries == null)
/*  972 */       this.entries = new MapEntrySet(); 
/*  973 */     return this.entries;
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
/*  990 */       return Float2FloatOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractFloatSet { private KeySet() {}
/*      */     
/*      */     public FloatIterator iterator() {
/*  996 */       return new Float2FloatOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/* 1001 */       if (Float2FloatOpenHashMap.this.containsNullKey)
/* 1002 */         consumer.accept(Float2FloatOpenHashMap.this.key[Float2FloatOpenHashMap.this.n]); 
/* 1003 */       for (int pos = Float2FloatOpenHashMap.this.n; pos-- != 0; ) {
/* 1004 */         float k = Float2FloatOpenHashMap.this.key[pos];
/* 1005 */         if (Float.floatToIntBits(k) != 0)
/* 1006 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1011 */       return Float2FloatOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(float k) {
/* 1015 */       return Float2FloatOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(float k) {
/* 1019 */       int oldSize = Float2FloatOpenHashMap.this.size;
/* 1020 */       Float2FloatOpenHashMap.this.remove(k);
/* 1021 */       return (Float2FloatOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1025 */       Float2FloatOpenHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public FloatSet keySet() {
/* 1030 */     if (this.keys == null)
/* 1031 */       this.keys = new KeySet(); 
/* 1032 */     return this.keys;
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
/* 1049 */       return Float2FloatOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public FloatCollection values() {
/* 1054 */     if (this.values == null)
/* 1055 */       this.values = new AbstractFloatCollection()
/*      */         {
/*      */           public FloatIterator iterator() {
/* 1058 */             return new Float2FloatOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1062 */             return Float2FloatOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(float v) {
/* 1066 */             return Float2FloatOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1070 */             Float2FloatOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(DoubleConsumer consumer)
/*      */           {
/* 1075 */             if (Float2FloatOpenHashMap.this.containsNullKey)
/* 1076 */               consumer.accept(Float2FloatOpenHashMap.this.value[Float2FloatOpenHashMap.this.n]); 
/* 1077 */             for (int pos = Float2FloatOpenHashMap.this.n; pos-- != 0;) {
/* 1078 */               if (Float.floatToIntBits(Float2FloatOpenHashMap.this.key[pos]) != 0)
/* 1079 */                 consumer.accept(Float2FloatOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1082 */     return this.values;
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
/* 1099 */     return trim(this.size);
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
/* 1123 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1124 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1125 */       return true; 
/*      */     try {
/* 1127 */       rehash(l);
/* 1128 */     } catch (OutOfMemoryError cantDoIt) {
/* 1129 */       return false;
/*      */     } 
/* 1131 */     return true;
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
/* 1147 */     float[] key = this.key;
/* 1148 */     float[] value = this.value;
/* 1149 */     int mask = newN - 1;
/* 1150 */     float[] newKey = new float[newN + 1];
/* 1151 */     float[] newValue = new float[newN + 1];
/* 1152 */     int i = this.n;
/* 1153 */     for (int j = realSize(); j-- != 0; ) {
/* 1154 */       while (Float.floatToIntBits(key[--i]) == 0); int pos;
/* 1155 */       if (Float.floatToIntBits(newKey[
/* 1156 */             pos = HashCommon.mix(HashCommon.float2int(key[i])) & mask]) != 0)
/* 1157 */         while (Float.floatToIntBits(newKey[pos = pos + 1 & mask]) != 0); 
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
/*      */   public Float2FloatOpenHashMap clone() {
/*      */     Float2FloatOpenHashMap c;
/*      */     try {
/* 1183 */       c = (Float2FloatOpenHashMap)super.clone();
/* 1184 */     } catch (CloneNotSupportedException cantHappen) {
/* 1185 */       throw new InternalError();
/*      */     } 
/* 1187 */     c.keys = null;
/* 1188 */     c.values = null;
/* 1189 */     c.entries = null;
/* 1190 */     c.containsNullKey = this.containsNullKey;
/* 1191 */     c.key = (float[])this.key.clone();
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
/* 1208 */       while (Float.floatToIntBits(this.key[i]) == 0)
/* 1209 */         i++; 
/* 1210 */       t = HashCommon.float2int(this.key[i]);
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
/* 1221 */     float[] key = this.key;
/* 1222 */     float[] value = this.value;
/* 1223 */     MapIterator i = new MapIterator();
/* 1224 */     s.defaultWriteObject();
/* 1225 */     for (int j = this.size; j-- != 0; ) {
/* 1226 */       int e = i.nextEntry();
/* 1227 */       s.writeFloat(key[e]);
/* 1228 */       s.writeFloat(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1233 */     s.defaultReadObject();
/* 1234 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1235 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1236 */     this.mask = this.n - 1;
/* 1237 */     float[] key = this.key = new float[this.n + 1];
/* 1238 */     float[] value = this.value = new float[this.n + 1];
/*      */ 
/*      */     
/* 1241 */     for (int i = this.size; i-- != 0; ) {
/* 1242 */       int pos; float k = s.readFloat();
/* 1243 */       float v = s.readFloat();
/* 1244 */       if (Float.floatToIntBits(k) == 0) {
/* 1245 */         pos = this.n;
/* 1246 */         this.containsNullKey = true;
/*      */       } else {
/* 1248 */         pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask;
/* 1249 */         while (Float.floatToIntBits(key[pos]) != 0)
/* 1250 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1252 */       key[pos] = k;
/* 1253 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\Float2FloatOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */