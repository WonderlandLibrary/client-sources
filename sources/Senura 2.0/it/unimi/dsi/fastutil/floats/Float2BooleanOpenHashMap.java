/*      */ package it.unimi.dsi.fastutil.floats;
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
/*      */ public class Float2BooleanOpenHashMap
/*      */   extends AbstractFloat2BooleanMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient float[] key;
/*      */   protected transient boolean[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Float2BooleanMap.FastEntrySet entries;
/*      */   protected transient FloatSet keys;
/*      */   protected transient BooleanCollection values;
/*      */   
/*      */   public Float2BooleanOpenHashMap(int expected, float f) {
/*  101 */     if (f <= 0.0F || f > 1.0F)
/*  102 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  103 */     if (expected < 0)
/*  104 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  105 */     this.f = f;
/*  106 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  107 */     this.mask = this.n - 1;
/*  108 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  109 */     this.key = new float[this.n + 1];
/*  110 */     this.value = new boolean[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2BooleanOpenHashMap(int expected) {
/*  119 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2BooleanOpenHashMap() {
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
/*      */   public Float2BooleanOpenHashMap(Map<? extends Float, ? extends Boolean> m, float f) {
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
/*      */   public Float2BooleanOpenHashMap(Map<? extends Float, ? extends Boolean> m) {
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
/*      */   public Float2BooleanOpenHashMap(Float2BooleanMap m, float f) {
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
/*      */   public Float2BooleanOpenHashMap(Float2BooleanMap m) {
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
/*      */   public Float2BooleanOpenHashMap(float[] k, boolean[] v, float f) {
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
/*      */   public Float2BooleanOpenHashMap(float[] k, boolean[] v) {
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
/*      */   public void putAll(Map<? extends Float, ? extends Boolean> m) {
/*  239 */     if (this.f <= 0.5D) {
/*  240 */       ensureCapacity(m.size());
/*      */     } else {
/*  242 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  244 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(float k) {
/*  248 */     if (Float.floatToIntBits(k) == 0) {
/*  249 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  251 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  254 */     if (Float.floatToIntBits(
/*  255 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  257 */       return -(pos + 1); } 
/*  258 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  259 */       return pos;
/*      */     }
/*      */     while (true) {
/*  262 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  263 */         return -(pos + 1); 
/*  264 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr))
/*  265 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, float k, boolean v) {
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
/*      */   public boolean put(float k, boolean v) {
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
/*  300 */     float[] key = this.key; while (true) {
/*      */       float curr; int last;
/*  302 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  304 */         if (Float.floatToIntBits(curr = key[pos]) == 0) {
/*  305 */           key[last] = 0.0F;
/*      */           return;
/*      */         } 
/*  308 */         int slot = HashCommon.mix(HashCommon.float2int(curr)) & this.mask;
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
/*      */   public boolean remove(float k) {
/*  320 */     if (Float.floatToIntBits(k) == 0) {
/*  321 */       if (this.containsNullKey)
/*  322 */         return removeNullEntry(); 
/*  323 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  326 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  329 */     if (Float.floatToIntBits(
/*  330 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  332 */       return this.defRetValue; } 
/*  333 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr))
/*  334 */       return removeEntry(pos); 
/*      */     while (true) {
/*  336 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  337 */         return this.defRetValue; 
/*  338 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  339 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean get(float k) {
/*  345 */     if (Float.floatToIntBits(k) == 0) {
/*  346 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  348 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  351 */     if (Float.floatToIntBits(
/*  352 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  354 */       return this.defRetValue; } 
/*  355 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  356 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  359 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  360 */         return this.defRetValue; 
/*  361 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  362 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(float k) {
/*  368 */     if (Float.floatToIntBits(k) == 0) {
/*  369 */       return this.containsNullKey;
/*      */     }
/*  371 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  374 */     if (Float.floatToIntBits(
/*  375 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  377 */       return false; } 
/*  378 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  379 */       return true;
/*      */     }
/*      */     while (true) {
/*  382 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  383 */         return false; 
/*  384 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr))
/*  385 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(boolean v) {
/*  390 */     boolean[] value = this.value;
/*  391 */     float[] key = this.key;
/*  392 */     if (this.containsNullKey && value[this.n] == v)
/*  393 */       return true; 
/*  394 */     for (int i = this.n; i-- != 0;) {
/*  395 */       if (Float.floatToIntBits(key[i]) != 0 && value[i] == v)
/*  396 */         return true; 
/*  397 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getOrDefault(float k, boolean defaultValue) {
/*  403 */     if (Float.floatToIntBits(k) == 0) {
/*  404 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  406 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  409 */     if (Float.floatToIntBits(
/*  410 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  412 */       return defaultValue; } 
/*  413 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  414 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  417 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  418 */         return defaultValue; 
/*  419 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  420 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean putIfAbsent(float k, boolean v) {
/*  426 */     int pos = find(k);
/*  427 */     if (pos >= 0)
/*  428 */       return this.value[pos]; 
/*  429 */     insert(-pos - 1, k, v);
/*  430 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(float k, boolean v) {
/*  436 */     if (Float.floatToIntBits(k) == 0) {
/*  437 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  438 */         removeNullEntry();
/*  439 */         return true;
/*      */       } 
/*  441 */       return false;
/*      */     } 
/*      */     
/*  444 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  447 */     if (Float.floatToIntBits(
/*  448 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  450 */       return false; } 
/*  451 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr) && v == this.value[pos]) {
/*  452 */       removeEntry(pos);
/*  453 */       return true;
/*      */     } 
/*      */     while (true) {
/*  456 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  457 */         return false; 
/*  458 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr) && v == this.value[pos]) {
/*  459 */         removeEntry(pos);
/*  460 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(float k, boolean oldValue, boolean v) {
/*  467 */     int pos = find(k);
/*  468 */     if (pos < 0 || oldValue != this.value[pos])
/*  469 */       return false; 
/*  470 */     this.value[pos] = v;
/*  471 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(float k, boolean v) {
/*  476 */     int pos = find(k);
/*  477 */     if (pos < 0)
/*  478 */       return this.defRetValue; 
/*  479 */     boolean oldValue = this.value[pos];
/*  480 */     this.value[pos] = v;
/*  481 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean computeIfAbsent(float k, DoublePredicate mappingFunction) {
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
/*      */   public boolean computeIfAbsentNullable(float k, DoubleFunction<? extends Boolean> mappingFunction) {
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
/*      */   public boolean computeIfPresent(float k, BiFunction<? super Float, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  513 */     Objects.requireNonNull(remappingFunction);
/*  514 */     int pos = find(k);
/*  515 */     if (pos < 0)
/*  516 */       return this.defRetValue; 
/*  517 */     Boolean newValue = remappingFunction.apply(Float.valueOf(k), Boolean.valueOf(this.value[pos]));
/*  518 */     if (newValue == null) {
/*  519 */       if (Float.floatToIntBits(k) == 0) {
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
/*      */   public boolean compute(float k, BiFunction<? super Float, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  531 */     Objects.requireNonNull(remappingFunction);
/*  532 */     int pos = find(k);
/*  533 */     Boolean newValue = remappingFunction.apply(Float.valueOf(k), 
/*  534 */         (pos >= 0) ? Boolean.valueOf(this.value[pos]) : null);
/*  535 */     if (newValue == null) {
/*  536 */       if (pos >= 0)
/*  537 */         if (Float.floatToIntBits(k) == 0) {
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
/*      */   public boolean merge(float k, boolean v, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  555 */     Objects.requireNonNull(remappingFunction);
/*  556 */     int pos = find(k);
/*  557 */     if (pos < 0) {
/*  558 */       insert(-pos - 1, k, v);
/*  559 */       return v;
/*      */     } 
/*  561 */     Boolean newValue = remappingFunction.apply(Boolean.valueOf(this.value[pos]), Boolean.valueOf(v));
/*  562 */     if (newValue == null) {
/*  563 */       if (Float.floatToIntBits(k) == 0) {
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
/*  584 */     Arrays.fill(this.key, 0.0F);
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
/*      */     implements Float2BooleanMap.Entry, Map.Entry<Float, Boolean>
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
/*      */     public float getFloatKey() {
/*  610 */       return Float2BooleanOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public boolean getBooleanValue() {
/*  614 */       return Float2BooleanOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public boolean setValue(boolean v) {
/*  618 */       boolean oldValue = Float2BooleanOpenHashMap.this.value[this.index];
/*  619 */       Float2BooleanOpenHashMap.this.value[this.index] = v;
/*  620 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float getKey() {
/*  630 */       return Float.valueOf(Float2BooleanOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean getValue() {
/*  640 */       return Boolean.valueOf(Float2BooleanOpenHashMap.this.value[this.index]);
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
/*  657 */       Map.Entry<Float, Boolean> e = (Map.Entry<Float, Boolean>)o;
/*  658 */       return (Float.floatToIntBits(Float2BooleanOpenHashMap.this.key[this.index]) == Float.floatToIntBits(((Float)e.getKey()).floatValue()) && Float2BooleanOpenHashMap.this.value[this.index] == ((Boolean)e
/*  659 */         .getValue()).booleanValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  663 */       return HashCommon.float2int(Float2BooleanOpenHashMap.this.key[this.index]) ^ (Float2BooleanOpenHashMap.this.value[this.index] ? 1231 : 1237);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  667 */       return Float2BooleanOpenHashMap.this.key[this.index] + "=>" + Float2BooleanOpenHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  677 */     int pos = Float2BooleanOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  684 */     int last = -1;
/*      */     
/*  686 */     int c = Float2BooleanOpenHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  690 */     boolean mustReturnNullKey = Float2BooleanOpenHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     FloatArrayList wrapped;
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
/*  705 */         return this.last = Float2BooleanOpenHashMap.this.n;
/*      */       } 
/*  707 */       float[] key = Float2BooleanOpenHashMap.this.key;
/*      */       while (true) {
/*  709 */         if (--this.pos < 0) {
/*      */           
/*  711 */           this.last = Integer.MIN_VALUE;
/*  712 */           float k = this.wrapped.getFloat(-this.pos - 1);
/*  713 */           int p = HashCommon.mix(HashCommon.float2int(k)) & Float2BooleanOpenHashMap.this.mask;
/*  714 */           while (Float.floatToIntBits(k) != Float.floatToIntBits(key[p]))
/*  715 */             p = p + 1 & Float2BooleanOpenHashMap.this.mask; 
/*  716 */           return p;
/*      */         } 
/*  718 */         if (Float.floatToIntBits(key[this.pos]) != 0) {
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
/*  733 */       float[] key = Float2BooleanOpenHashMap.this.key; while (true) {
/*      */         float curr; int last;
/*  735 */         pos = (last = pos) + 1 & Float2BooleanOpenHashMap.this.mask;
/*      */         while (true) {
/*  737 */           if (Float.floatToIntBits(curr = key[pos]) == 0) {
/*  738 */             key[last] = 0.0F;
/*      */             return;
/*      */           } 
/*  741 */           int slot = HashCommon.mix(HashCommon.float2int(curr)) & Float2BooleanOpenHashMap.this.mask;
/*      */           
/*  743 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  745 */           pos = pos + 1 & Float2BooleanOpenHashMap.this.mask;
/*      */         } 
/*  747 */         if (pos < last) {
/*  748 */           if (this.wrapped == null)
/*  749 */             this.wrapped = new FloatArrayList(2); 
/*  750 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  752 */         key[last] = curr;
/*  753 */         Float2BooleanOpenHashMap.this.value[last] = Float2BooleanOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  757 */       if (this.last == -1)
/*  758 */         throw new IllegalStateException(); 
/*  759 */       if (this.last == Float2BooleanOpenHashMap.this.n) {
/*  760 */         Float2BooleanOpenHashMap.this.containsNullKey = false;
/*  761 */       } else if (this.pos >= 0) {
/*  762 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  765 */         Float2BooleanOpenHashMap.this.remove(this.wrapped.getFloat(-this.pos - 1));
/*  766 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  769 */       Float2BooleanOpenHashMap.this.size--;
/*  770 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  775 */       int i = n;
/*  776 */       while (i-- != 0 && hasNext())
/*  777 */         nextEntry(); 
/*  778 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Float2BooleanMap.Entry> { private Float2BooleanOpenHashMap.MapEntry entry;
/*      */     
/*      */     public Float2BooleanOpenHashMap.MapEntry next() {
/*  785 */       return this.entry = new Float2BooleanOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  789 */       super.remove();
/*  790 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Float2BooleanMap.Entry> { private FastEntryIterator() {
/*  794 */       this.entry = new Float2BooleanOpenHashMap.MapEntry();
/*      */     } private final Float2BooleanOpenHashMap.MapEntry entry;
/*      */     public Float2BooleanOpenHashMap.MapEntry next() {
/*  797 */       this.entry.index = nextEntry();
/*  798 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Float2BooleanMap.Entry> implements Float2BooleanMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Float2BooleanMap.Entry> iterator() {
/*  804 */       return new Float2BooleanOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Float2BooleanMap.Entry> fastIterator() {
/*  808 */       return new Float2BooleanOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  813 */       if (!(o instanceof Map.Entry))
/*  814 */         return false; 
/*  815 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  816 */       if (e.getKey() == null || !(e.getKey() instanceof Float))
/*  817 */         return false; 
/*  818 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/*  819 */         return false; 
/*  820 */       float k = ((Float)e.getKey()).floatValue();
/*  821 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/*  822 */       if (Float.floatToIntBits(k) == 0) {
/*  823 */         return (Float2BooleanOpenHashMap.this.containsNullKey && Float2BooleanOpenHashMap.this.value[Float2BooleanOpenHashMap.this.n] == v);
/*      */       }
/*  825 */       float[] key = Float2BooleanOpenHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/*  828 */       if (Float.floatToIntBits(
/*  829 */           curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & Float2BooleanOpenHashMap.this.mask]) == 0)
/*      */       {
/*  831 */         return false; } 
/*  832 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  833 */         return (Float2BooleanOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  836 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2BooleanOpenHashMap.this.mask]) == 0)
/*  837 */           return false; 
/*  838 */         if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  839 */           return (Float2BooleanOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  845 */       if (!(o instanceof Map.Entry))
/*  846 */         return false; 
/*  847 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  848 */       if (e.getKey() == null || !(e.getKey() instanceof Float))
/*  849 */         return false; 
/*  850 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/*  851 */         return false; 
/*  852 */       float k = ((Float)e.getKey()).floatValue();
/*  853 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/*  854 */       if (Float.floatToIntBits(k) == 0) {
/*  855 */         if (Float2BooleanOpenHashMap.this.containsNullKey && Float2BooleanOpenHashMap.this.value[Float2BooleanOpenHashMap.this.n] == v) {
/*  856 */           Float2BooleanOpenHashMap.this.removeNullEntry();
/*  857 */           return true;
/*      */         } 
/*  859 */         return false;
/*      */       } 
/*      */       
/*  862 */       float[] key = Float2BooleanOpenHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/*  865 */       if (Float.floatToIntBits(
/*  866 */           curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & Float2BooleanOpenHashMap.this.mask]) == 0)
/*      */       {
/*  868 */         return false; } 
/*  869 */       if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
/*  870 */         if (Float2BooleanOpenHashMap.this.value[pos] == v) {
/*  871 */           Float2BooleanOpenHashMap.this.removeEntry(pos);
/*  872 */           return true;
/*      */         } 
/*  874 */         return false;
/*      */       } 
/*      */       while (true) {
/*  877 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2BooleanOpenHashMap.this.mask]) == 0)
/*  878 */           return false; 
/*  879 */         if (Float.floatToIntBits(curr) == Float.floatToIntBits(k) && 
/*  880 */           Float2BooleanOpenHashMap.this.value[pos] == v) {
/*  881 */           Float2BooleanOpenHashMap.this.removeEntry(pos);
/*  882 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  889 */       return Float2BooleanOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  893 */       Float2BooleanOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Float2BooleanMap.Entry> consumer) {
/*  898 */       if (Float2BooleanOpenHashMap.this.containsNullKey)
/*  899 */         consumer.accept(new AbstractFloat2BooleanMap.BasicEntry(Float2BooleanOpenHashMap.this.key[Float2BooleanOpenHashMap.this.n], Float2BooleanOpenHashMap.this.value[Float2BooleanOpenHashMap.this.n])); 
/*  900 */       for (int pos = Float2BooleanOpenHashMap.this.n; pos-- != 0;) {
/*  901 */         if (Float.floatToIntBits(Float2BooleanOpenHashMap.this.key[pos]) != 0)
/*  902 */           consumer.accept(new AbstractFloat2BooleanMap.BasicEntry(Float2BooleanOpenHashMap.this.key[pos], Float2BooleanOpenHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Float2BooleanMap.Entry> consumer) {
/*  907 */       AbstractFloat2BooleanMap.BasicEntry entry = new AbstractFloat2BooleanMap.BasicEntry();
/*  908 */       if (Float2BooleanOpenHashMap.this.containsNullKey) {
/*  909 */         entry.key = Float2BooleanOpenHashMap.this.key[Float2BooleanOpenHashMap.this.n];
/*  910 */         entry.value = Float2BooleanOpenHashMap.this.value[Float2BooleanOpenHashMap.this.n];
/*  911 */         consumer.accept(entry);
/*      */       } 
/*  913 */       for (int pos = Float2BooleanOpenHashMap.this.n; pos-- != 0;) {
/*  914 */         if (Float.floatToIntBits(Float2BooleanOpenHashMap.this.key[pos]) != 0) {
/*  915 */           entry.key = Float2BooleanOpenHashMap.this.key[pos];
/*  916 */           entry.value = Float2BooleanOpenHashMap.this.value[pos];
/*  917 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Float2BooleanMap.FastEntrySet float2BooleanEntrySet() {
/*  923 */     if (this.entries == null)
/*  924 */       this.entries = new MapEntrySet(); 
/*  925 */     return this.entries;
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
/*  942 */       return Float2BooleanOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractFloatSet { private KeySet() {}
/*      */     
/*      */     public FloatIterator iterator() {
/*  948 */       return new Float2BooleanOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/*  953 */       if (Float2BooleanOpenHashMap.this.containsNullKey)
/*  954 */         consumer.accept(Float2BooleanOpenHashMap.this.key[Float2BooleanOpenHashMap.this.n]); 
/*  955 */       for (int pos = Float2BooleanOpenHashMap.this.n; pos-- != 0; ) {
/*  956 */         float k = Float2BooleanOpenHashMap.this.key[pos];
/*  957 */         if (Float.floatToIntBits(k) != 0)
/*  958 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  963 */       return Float2BooleanOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(float k) {
/*  967 */       return Float2BooleanOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(float k) {
/*  971 */       int oldSize = Float2BooleanOpenHashMap.this.size;
/*  972 */       Float2BooleanOpenHashMap.this.remove(k);
/*  973 */       return (Float2BooleanOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  977 */       Float2BooleanOpenHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public FloatSet keySet() {
/*  982 */     if (this.keys == null)
/*  983 */       this.keys = new KeySet(); 
/*  984 */     return this.keys;
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
/* 1001 */       return Float2BooleanOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public BooleanCollection values() {
/* 1006 */     if (this.values == null)
/* 1007 */       this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */         {
/*      */           public BooleanIterator iterator() {
/* 1010 */             return new Float2BooleanOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1014 */             return Float2BooleanOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(boolean v) {
/* 1018 */             return Float2BooleanOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1022 */             Float2BooleanOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(BooleanConsumer consumer)
/*      */           {
/* 1027 */             if (Float2BooleanOpenHashMap.this.containsNullKey)
/* 1028 */               consumer.accept(Float2BooleanOpenHashMap.this.value[Float2BooleanOpenHashMap.this.n]); 
/* 1029 */             for (int pos = Float2BooleanOpenHashMap.this.n; pos-- != 0;) {
/* 1030 */               if (Float.floatToIntBits(Float2BooleanOpenHashMap.this.key[pos]) != 0)
/* 1031 */                 consumer.accept(Float2BooleanOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1034 */     return this.values;
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
/* 1051 */     return trim(this.size);
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
/* 1075 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1076 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1077 */       return true; 
/*      */     try {
/* 1079 */       rehash(l);
/* 1080 */     } catch (OutOfMemoryError cantDoIt) {
/* 1081 */       return false;
/*      */     } 
/* 1083 */     return true;
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
/* 1099 */     float[] key = this.key;
/* 1100 */     boolean[] value = this.value;
/* 1101 */     int mask = newN - 1;
/* 1102 */     float[] newKey = new float[newN + 1];
/* 1103 */     boolean[] newValue = new boolean[newN + 1];
/* 1104 */     int i = this.n;
/* 1105 */     for (int j = realSize(); j-- != 0; ) {
/* 1106 */       while (Float.floatToIntBits(key[--i]) == 0); int pos;
/* 1107 */       if (Float.floatToIntBits(newKey[
/* 1108 */             pos = HashCommon.mix(HashCommon.float2int(key[i])) & mask]) != 0)
/* 1109 */         while (Float.floatToIntBits(newKey[pos = pos + 1 & mask]) != 0); 
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
/*      */   public Float2BooleanOpenHashMap clone() {
/*      */     Float2BooleanOpenHashMap c;
/*      */     try {
/* 1135 */       c = (Float2BooleanOpenHashMap)super.clone();
/* 1136 */     } catch (CloneNotSupportedException cantHappen) {
/* 1137 */       throw new InternalError();
/*      */     } 
/* 1139 */     c.keys = null;
/* 1140 */     c.values = null;
/* 1141 */     c.entries = null;
/* 1142 */     c.containsNullKey = this.containsNullKey;
/* 1143 */     c.key = (float[])this.key.clone();
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
/* 1160 */       while (Float.floatToIntBits(this.key[i]) == 0)
/* 1161 */         i++; 
/* 1162 */       t = HashCommon.float2int(this.key[i]);
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
/* 1173 */     float[] key = this.key;
/* 1174 */     boolean[] value = this.value;
/* 1175 */     MapIterator i = new MapIterator();
/* 1176 */     s.defaultWriteObject();
/* 1177 */     for (int j = this.size; j-- != 0; ) {
/* 1178 */       int e = i.nextEntry();
/* 1179 */       s.writeFloat(key[e]);
/* 1180 */       s.writeBoolean(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1185 */     s.defaultReadObject();
/* 1186 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1187 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1188 */     this.mask = this.n - 1;
/* 1189 */     float[] key = this.key = new float[this.n + 1];
/* 1190 */     boolean[] value = this.value = new boolean[this.n + 1];
/*      */ 
/*      */     
/* 1193 */     for (int i = this.size; i-- != 0; ) {
/* 1194 */       int pos; float k = s.readFloat();
/* 1195 */       boolean v = s.readBoolean();
/* 1196 */       if (Float.floatToIntBits(k) == 0) {
/* 1197 */         pos = this.n;
/* 1198 */         this.containsNullKey = true;
/*      */       } else {
/* 1200 */         pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask;
/* 1201 */         while (Float.floatToIntBits(key[pos]) != 0)
/* 1202 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1204 */       key[pos] = k;
/* 1205 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\Float2BooleanOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */