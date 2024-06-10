/*      */ package it.unimi.dsi.fastutil.floats;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*      */ import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortCollection;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortIterator;
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
/*      */ public class Float2ShortOpenHashMap
/*      */   extends AbstractFloat2ShortMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient float[] key;
/*      */   protected transient short[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Float2ShortMap.FastEntrySet entries;
/*      */   protected transient FloatSet keys;
/*      */   protected transient ShortCollection values;
/*      */   
/*      */   public Float2ShortOpenHashMap(int expected, float f) {
/*   96 */     if (f <= 0.0F || f > 1.0F)
/*   97 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*   98 */     if (expected < 0)
/*   99 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  100 */     this.f = f;
/*  101 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  102 */     this.mask = this.n - 1;
/*  103 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  104 */     this.key = new float[this.n + 1];
/*  105 */     this.value = new short[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ShortOpenHashMap(int expected) {
/*  114 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ShortOpenHashMap() {
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
/*      */   public Float2ShortOpenHashMap(Map<? extends Float, ? extends Short> m, float f) {
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
/*      */   public Float2ShortOpenHashMap(Map<? extends Float, ? extends Short> m) {
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
/*      */   public Float2ShortOpenHashMap(Float2ShortMap m, float f) {
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
/*      */   public Float2ShortOpenHashMap(Float2ShortMap m) {
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
/*      */   public Float2ShortOpenHashMap(float[] k, short[] v, float f) {
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
/*      */   public Float2ShortOpenHashMap(float[] k, short[] v) {
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
/*      */   private short removeEntry(int pos) {
/*  217 */     short oldValue = this.value[pos];
/*  218 */     this.size--;
/*  219 */     shiftKeys(pos);
/*  220 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  221 */       rehash(this.n / 2); 
/*  222 */     return oldValue;
/*      */   }
/*      */   private short removeNullEntry() {
/*  225 */     this.containsNullKey = false;
/*  226 */     short oldValue = this.value[this.n];
/*  227 */     this.size--;
/*  228 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  229 */       rehash(this.n / 2); 
/*  230 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Float, ? extends Short> m) {
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
/*      */   private void insert(int pos, float k, short v) {
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
/*      */   public short put(float k, short v) {
/*  275 */     int pos = find(k);
/*  276 */     if (pos < 0) {
/*  277 */       insert(-pos - 1, k, v);
/*  278 */       return this.defRetValue;
/*      */     } 
/*  280 */     short oldValue = this.value[pos];
/*  281 */     this.value[pos] = v;
/*  282 */     return oldValue;
/*      */   }
/*      */   private short addToValue(int pos, short incr) {
/*  285 */     short oldValue = this.value[pos];
/*  286 */     this.value[pos] = (short)(oldValue + incr);
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
/*      */   public short addTo(float k, short incr) {
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
/*  327 */     this.value[pos] = (short)(this.defRetValue + incr);
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
/*      */   public short remove(float k) {
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
/*      */   public short get(float k) {
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
/*      */   public boolean containsValue(short v) {
/*  435 */     short[] value = this.value;
/*  436 */     float[] key = this.key;
/*  437 */     if (this.containsNullKey && value[this.n] == v)
/*  438 */       return true; 
/*  439 */     for (int i = this.n; i-- != 0;) {
/*  440 */       if (Float.floatToIntBits(key[i]) != 0 && value[i] == v)
/*  441 */         return true; 
/*  442 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short getOrDefault(float k, short defaultValue) {
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
/*      */   public short putIfAbsent(float k, short v) {
/*  471 */     int pos = find(k);
/*  472 */     if (pos >= 0)
/*  473 */       return this.value[pos]; 
/*  474 */     insert(-pos - 1, k, v);
/*  475 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(float k, short v) {
/*  481 */     if (Float.floatToIntBits(k) == 0) {
/*  482 */       if (this.containsNullKey && v == this.value[this.n]) {
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
/*  496 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr) && v == this.value[pos]) {
/*  497 */       removeEntry(pos);
/*  498 */       return true;
/*      */     } 
/*      */     while (true) {
/*  501 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  502 */         return false; 
/*  503 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr) && v == this.value[pos]) {
/*  504 */         removeEntry(pos);
/*  505 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(float k, short oldValue, short v) {
/*  512 */     int pos = find(k);
/*  513 */     if (pos < 0 || oldValue != this.value[pos])
/*  514 */       return false; 
/*  515 */     this.value[pos] = v;
/*  516 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public short replace(float k, short v) {
/*  521 */     int pos = find(k);
/*  522 */     if (pos < 0)
/*  523 */       return this.defRetValue; 
/*  524 */     short oldValue = this.value[pos];
/*  525 */     this.value[pos] = v;
/*  526 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public short computeIfAbsent(float k, DoubleToIntFunction mappingFunction) {
/*  531 */     Objects.requireNonNull(mappingFunction);
/*  532 */     int pos = find(k);
/*  533 */     if (pos >= 0)
/*  534 */       return this.value[pos]; 
/*  535 */     short newValue = SafeMath.safeIntToShort(mappingFunction.applyAsInt(k));
/*  536 */     insert(-pos - 1, k, newValue);
/*  537 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short computeIfAbsentNullable(float k, DoubleFunction<? extends Short> mappingFunction) {
/*  543 */     Objects.requireNonNull(mappingFunction);
/*  544 */     int pos = find(k);
/*  545 */     if (pos >= 0)
/*  546 */       return this.value[pos]; 
/*  547 */     Short newValue = mappingFunction.apply(k);
/*  548 */     if (newValue == null)
/*  549 */       return this.defRetValue; 
/*  550 */     short v = newValue.shortValue();
/*  551 */     insert(-pos - 1, k, v);
/*  552 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short computeIfPresent(float k, BiFunction<? super Float, ? super Short, ? extends Short> remappingFunction) {
/*  558 */     Objects.requireNonNull(remappingFunction);
/*  559 */     int pos = find(k);
/*  560 */     if (pos < 0)
/*  561 */       return this.defRetValue; 
/*  562 */     Short newValue = remappingFunction.apply(Float.valueOf(k), Short.valueOf(this.value[pos]));
/*  563 */     if (newValue == null) {
/*  564 */       if (Float.floatToIntBits(k) == 0) {
/*  565 */         removeNullEntry();
/*      */       } else {
/*  567 */         removeEntry(pos);
/*  568 */       }  return this.defRetValue;
/*      */     } 
/*  570 */     this.value[pos] = newValue.shortValue(); return newValue.shortValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short compute(float k, BiFunction<? super Float, ? super Short, ? extends Short> remappingFunction) {
/*  576 */     Objects.requireNonNull(remappingFunction);
/*  577 */     int pos = find(k);
/*  578 */     Short newValue = remappingFunction.apply(Float.valueOf(k), (pos >= 0) ? Short.valueOf(this.value[pos]) : null);
/*  579 */     if (newValue == null) {
/*  580 */       if (pos >= 0)
/*  581 */         if (Float.floatToIntBits(k) == 0) {
/*  582 */           removeNullEntry();
/*      */         } else {
/*  584 */           removeEntry(pos);
/*      */         }  
/*  586 */       return this.defRetValue;
/*      */     } 
/*  588 */     short newVal = newValue.shortValue();
/*  589 */     if (pos < 0) {
/*  590 */       insert(-pos - 1, k, newVal);
/*  591 */       return newVal;
/*      */     } 
/*  593 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short merge(float k, short v, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
/*  599 */     Objects.requireNonNull(remappingFunction);
/*  600 */     int pos = find(k);
/*  601 */     if (pos < 0) {
/*  602 */       insert(-pos - 1, k, v);
/*  603 */       return v;
/*      */     } 
/*  605 */     Short newValue = remappingFunction.apply(Short.valueOf(this.value[pos]), Short.valueOf(v));
/*  606 */     if (newValue == null) {
/*  607 */       if (Float.floatToIntBits(k) == 0) {
/*  608 */         removeNullEntry();
/*      */       } else {
/*  610 */         removeEntry(pos);
/*  611 */       }  return this.defRetValue;
/*      */     } 
/*  613 */     this.value[pos] = newValue.shortValue(); return newValue.shortValue();
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
/*  624 */     if (this.size == 0)
/*      */       return; 
/*  626 */     this.size = 0;
/*  627 */     this.containsNullKey = false;
/*  628 */     Arrays.fill(this.key, 0.0F);
/*      */   }
/*      */   
/*      */   public int size() {
/*  632 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  636 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Float2ShortMap.Entry, Map.Entry<Float, Short>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  648 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public float getFloatKey() {
/*  654 */       return Float2ShortOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public short getShortValue() {
/*  658 */       return Float2ShortOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public short setValue(short v) {
/*  662 */       short oldValue = Float2ShortOpenHashMap.this.value[this.index];
/*  663 */       Float2ShortOpenHashMap.this.value[this.index] = v;
/*  664 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float getKey() {
/*  674 */       return Float.valueOf(Float2ShortOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Short getValue() {
/*  684 */       return Short.valueOf(Float2ShortOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Short setValue(Short v) {
/*  694 */       return Short.valueOf(setValue(v.shortValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  699 */       if (!(o instanceof Map.Entry))
/*  700 */         return false; 
/*  701 */       Map.Entry<Float, Short> e = (Map.Entry<Float, Short>)o;
/*  702 */       return (Float.floatToIntBits(Float2ShortOpenHashMap.this.key[this.index]) == Float.floatToIntBits(((Float)e.getKey()).floatValue()) && Float2ShortOpenHashMap.this.value[this.index] == ((Short)e
/*  703 */         .getValue()).shortValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  707 */       return HashCommon.float2int(Float2ShortOpenHashMap.this.key[this.index]) ^ Float2ShortOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/*  711 */       return Float2ShortOpenHashMap.this.key[this.index] + "=>" + Float2ShortOpenHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  721 */     int pos = Float2ShortOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  728 */     int last = -1;
/*      */     
/*  730 */     int c = Float2ShortOpenHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  734 */     boolean mustReturnNullKey = Float2ShortOpenHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     FloatArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  741 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  744 */       if (!hasNext())
/*  745 */         throw new NoSuchElementException(); 
/*  746 */       this.c--;
/*  747 */       if (this.mustReturnNullKey) {
/*  748 */         this.mustReturnNullKey = false;
/*  749 */         return this.last = Float2ShortOpenHashMap.this.n;
/*      */       } 
/*  751 */       float[] key = Float2ShortOpenHashMap.this.key;
/*      */       while (true) {
/*  753 */         if (--this.pos < 0) {
/*      */           
/*  755 */           this.last = Integer.MIN_VALUE;
/*  756 */           float k = this.wrapped.getFloat(-this.pos - 1);
/*  757 */           int p = HashCommon.mix(HashCommon.float2int(k)) & Float2ShortOpenHashMap.this.mask;
/*  758 */           while (Float.floatToIntBits(k) != Float.floatToIntBits(key[p]))
/*  759 */             p = p + 1 & Float2ShortOpenHashMap.this.mask; 
/*  760 */           return p;
/*      */         } 
/*  762 */         if (Float.floatToIntBits(key[this.pos]) != 0) {
/*  763 */           return this.last = this.pos;
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
/*  777 */       float[] key = Float2ShortOpenHashMap.this.key; while (true) {
/*      */         float curr; int last;
/*  779 */         pos = (last = pos) + 1 & Float2ShortOpenHashMap.this.mask;
/*      */         while (true) {
/*  781 */           if (Float.floatToIntBits(curr = key[pos]) == 0) {
/*  782 */             key[last] = 0.0F;
/*      */             return;
/*      */           } 
/*  785 */           int slot = HashCommon.mix(HashCommon.float2int(curr)) & Float2ShortOpenHashMap.this.mask;
/*      */           
/*  787 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  789 */           pos = pos + 1 & Float2ShortOpenHashMap.this.mask;
/*      */         } 
/*  791 */         if (pos < last) {
/*  792 */           if (this.wrapped == null)
/*  793 */             this.wrapped = new FloatArrayList(2); 
/*  794 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  796 */         key[last] = curr;
/*  797 */         Float2ShortOpenHashMap.this.value[last] = Float2ShortOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  801 */       if (this.last == -1)
/*  802 */         throw new IllegalStateException(); 
/*  803 */       if (this.last == Float2ShortOpenHashMap.this.n) {
/*  804 */         Float2ShortOpenHashMap.this.containsNullKey = false;
/*  805 */       } else if (this.pos >= 0) {
/*  806 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  809 */         Float2ShortOpenHashMap.this.remove(this.wrapped.getFloat(-this.pos - 1));
/*  810 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  813 */       Float2ShortOpenHashMap.this.size--;
/*  814 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  819 */       int i = n;
/*  820 */       while (i-- != 0 && hasNext())
/*  821 */         nextEntry(); 
/*  822 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Float2ShortMap.Entry> { private Float2ShortOpenHashMap.MapEntry entry;
/*      */     
/*      */     public Float2ShortOpenHashMap.MapEntry next() {
/*  829 */       return this.entry = new Float2ShortOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  833 */       super.remove();
/*  834 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Float2ShortMap.Entry> { private FastEntryIterator() {
/*  838 */       this.entry = new Float2ShortOpenHashMap.MapEntry();
/*      */     } private final Float2ShortOpenHashMap.MapEntry entry;
/*      */     public Float2ShortOpenHashMap.MapEntry next() {
/*  841 */       this.entry.index = nextEntry();
/*  842 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Float2ShortMap.Entry> implements Float2ShortMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Float2ShortMap.Entry> iterator() {
/*  848 */       return new Float2ShortOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Float2ShortMap.Entry> fastIterator() {
/*  852 */       return new Float2ShortOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  857 */       if (!(o instanceof Map.Entry))
/*  858 */         return false; 
/*  859 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  860 */       if (e.getKey() == null || !(e.getKey() instanceof Float))
/*  861 */         return false; 
/*  862 */       if (e.getValue() == null || !(e.getValue() instanceof Short))
/*  863 */         return false; 
/*  864 */       float k = ((Float)e.getKey()).floatValue();
/*  865 */       short v = ((Short)e.getValue()).shortValue();
/*  866 */       if (Float.floatToIntBits(k) == 0) {
/*  867 */         return (Float2ShortOpenHashMap.this.containsNullKey && Float2ShortOpenHashMap.this.value[Float2ShortOpenHashMap.this.n] == v);
/*      */       }
/*  869 */       float[] key = Float2ShortOpenHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/*  872 */       if (Float.floatToIntBits(
/*  873 */           curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & Float2ShortOpenHashMap.this.mask]) == 0)
/*      */       {
/*  875 */         return false; } 
/*  876 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  877 */         return (Float2ShortOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  880 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2ShortOpenHashMap.this.mask]) == 0)
/*  881 */           return false; 
/*  882 */         if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  883 */           return (Float2ShortOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  889 */       if (!(o instanceof Map.Entry))
/*  890 */         return false; 
/*  891 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  892 */       if (e.getKey() == null || !(e.getKey() instanceof Float))
/*  893 */         return false; 
/*  894 */       if (e.getValue() == null || !(e.getValue() instanceof Short))
/*  895 */         return false; 
/*  896 */       float k = ((Float)e.getKey()).floatValue();
/*  897 */       short v = ((Short)e.getValue()).shortValue();
/*  898 */       if (Float.floatToIntBits(k) == 0) {
/*  899 */         if (Float2ShortOpenHashMap.this.containsNullKey && Float2ShortOpenHashMap.this.value[Float2ShortOpenHashMap.this.n] == v) {
/*  900 */           Float2ShortOpenHashMap.this.removeNullEntry();
/*  901 */           return true;
/*      */         } 
/*  903 */         return false;
/*      */       } 
/*      */       
/*  906 */       float[] key = Float2ShortOpenHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/*  909 */       if (Float.floatToIntBits(
/*  910 */           curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & Float2ShortOpenHashMap.this.mask]) == 0)
/*      */       {
/*  912 */         return false; } 
/*  913 */       if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
/*  914 */         if (Float2ShortOpenHashMap.this.value[pos] == v) {
/*  915 */           Float2ShortOpenHashMap.this.removeEntry(pos);
/*  916 */           return true;
/*      */         } 
/*  918 */         return false;
/*      */       } 
/*      */       while (true) {
/*  921 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2ShortOpenHashMap.this.mask]) == 0)
/*  922 */           return false; 
/*  923 */         if (Float.floatToIntBits(curr) == Float.floatToIntBits(k) && 
/*  924 */           Float2ShortOpenHashMap.this.value[pos] == v) {
/*  925 */           Float2ShortOpenHashMap.this.removeEntry(pos);
/*  926 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  933 */       return Float2ShortOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  937 */       Float2ShortOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Float2ShortMap.Entry> consumer) {
/*  942 */       if (Float2ShortOpenHashMap.this.containsNullKey)
/*  943 */         consumer.accept(new AbstractFloat2ShortMap.BasicEntry(Float2ShortOpenHashMap.this.key[Float2ShortOpenHashMap.this.n], Float2ShortOpenHashMap.this.value[Float2ShortOpenHashMap.this.n])); 
/*  944 */       for (int pos = Float2ShortOpenHashMap.this.n; pos-- != 0;) {
/*  945 */         if (Float.floatToIntBits(Float2ShortOpenHashMap.this.key[pos]) != 0)
/*  946 */           consumer.accept(new AbstractFloat2ShortMap.BasicEntry(Float2ShortOpenHashMap.this.key[pos], Float2ShortOpenHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Float2ShortMap.Entry> consumer) {
/*  951 */       AbstractFloat2ShortMap.BasicEntry entry = new AbstractFloat2ShortMap.BasicEntry();
/*  952 */       if (Float2ShortOpenHashMap.this.containsNullKey) {
/*  953 */         entry.key = Float2ShortOpenHashMap.this.key[Float2ShortOpenHashMap.this.n];
/*  954 */         entry.value = Float2ShortOpenHashMap.this.value[Float2ShortOpenHashMap.this.n];
/*  955 */         consumer.accept(entry);
/*      */       } 
/*  957 */       for (int pos = Float2ShortOpenHashMap.this.n; pos-- != 0;) {
/*  958 */         if (Float.floatToIntBits(Float2ShortOpenHashMap.this.key[pos]) != 0) {
/*  959 */           entry.key = Float2ShortOpenHashMap.this.key[pos];
/*  960 */           entry.value = Float2ShortOpenHashMap.this.value[pos];
/*  961 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Float2ShortMap.FastEntrySet float2ShortEntrySet() {
/*  967 */     if (this.entries == null)
/*  968 */       this.entries = new MapEntrySet(); 
/*  969 */     return this.entries;
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
/*  986 */       return Float2ShortOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractFloatSet { private KeySet() {}
/*      */     
/*      */     public FloatIterator iterator() {
/*  992 */       return new Float2ShortOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/*  997 */       if (Float2ShortOpenHashMap.this.containsNullKey)
/*  998 */         consumer.accept(Float2ShortOpenHashMap.this.key[Float2ShortOpenHashMap.this.n]); 
/*  999 */       for (int pos = Float2ShortOpenHashMap.this.n; pos-- != 0; ) {
/* 1000 */         float k = Float2ShortOpenHashMap.this.key[pos];
/* 1001 */         if (Float.floatToIntBits(k) != 0)
/* 1002 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1007 */       return Float2ShortOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(float k) {
/* 1011 */       return Float2ShortOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(float k) {
/* 1015 */       int oldSize = Float2ShortOpenHashMap.this.size;
/* 1016 */       Float2ShortOpenHashMap.this.remove(k);
/* 1017 */       return (Float2ShortOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1021 */       Float2ShortOpenHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public FloatSet keySet() {
/* 1026 */     if (this.keys == null)
/* 1027 */       this.keys = new KeySet(); 
/* 1028 */     return this.keys;
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
/*      */     implements ShortIterator
/*      */   {
/*      */     public short nextShort() {
/* 1045 */       return Float2ShortOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public ShortCollection values() {
/* 1050 */     if (this.values == null)
/* 1051 */       this.values = (ShortCollection)new AbstractShortCollection()
/*      */         {
/*      */           public ShortIterator iterator() {
/* 1054 */             return new Float2ShortOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1058 */             return Float2ShortOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(short v) {
/* 1062 */             return Float2ShortOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1066 */             Float2ShortOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(IntConsumer consumer)
/*      */           {
/* 1071 */             if (Float2ShortOpenHashMap.this.containsNullKey)
/* 1072 */               consumer.accept(Float2ShortOpenHashMap.this.value[Float2ShortOpenHashMap.this.n]); 
/* 1073 */             for (int pos = Float2ShortOpenHashMap.this.n; pos-- != 0;) {
/* 1074 */               if (Float.floatToIntBits(Float2ShortOpenHashMap.this.key[pos]) != 0)
/* 1075 */                 consumer.accept(Float2ShortOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1078 */     return this.values;
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
/* 1095 */     return trim(this.size);
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
/* 1119 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1120 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1121 */       return true; 
/*      */     try {
/* 1123 */       rehash(l);
/* 1124 */     } catch (OutOfMemoryError cantDoIt) {
/* 1125 */       return false;
/*      */     } 
/* 1127 */     return true;
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
/* 1143 */     float[] key = this.key;
/* 1144 */     short[] value = this.value;
/* 1145 */     int mask = newN - 1;
/* 1146 */     float[] newKey = new float[newN + 1];
/* 1147 */     short[] newValue = new short[newN + 1];
/* 1148 */     int i = this.n;
/* 1149 */     for (int j = realSize(); j-- != 0; ) {
/* 1150 */       while (Float.floatToIntBits(key[--i]) == 0); int pos;
/* 1151 */       if (Float.floatToIntBits(newKey[
/* 1152 */             pos = HashCommon.mix(HashCommon.float2int(key[i])) & mask]) != 0)
/* 1153 */         while (Float.floatToIntBits(newKey[pos = pos + 1 & mask]) != 0); 
/* 1154 */       newKey[pos] = key[i];
/* 1155 */       newValue[pos] = value[i];
/*      */     } 
/* 1157 */     newValue[newN] = value[this.n];
/* 1158 */     this.n = newN;
/* 1159 */     this.mask = mask;
/* 1160 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1161 */     this.key = newKey;
/* 1162 */     this.value = newValue;
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
/*      */   public Float2ShortOpenHashMap clone() {
/*      */     Float2ShortOpenHashMap c;
/*      */     try {
/* 1179 */       c = (Float2ShortOpenHashMap)super.clone();
/* 1180 */     } catch (CloneNotSupportedException cantHappen) {
/* 1181 */       throw new InternalError();
/*      */     } 
/* 1183 */     c.keys = null;
/* 1184 */     c.values = null;
/* 1185 */     c.entries = null;
/* 1186 */     c.containsNullKey = this.containsNullKey;
/* 1187 */     c.key = (float[])this.key.clone();
/* 1188 */     c.value = (short[])this.value.clone();
/* 1189 */     return c;
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
/* 1202 */     int h = 0;
/* 1203 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1204 */       while (Float.floatToIntBits(this.key[i]) == 0)
/* 1205 */         i++; 
/* 1206 */       t = HashCommon.float2int(this.key[i]);
/* 1207 */       t ^= this.value[i];
/* 1208 */       h += t;
/* 1209 */       i++;
/*      */     } 
/*      */     
/* 1212 */     if (this.containsNullKey)
/* 1213 */       h += this.value[this.n]; 
/* 1214 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1217 */     float[] key = this.key;
/* 1218 */     short[] value = this.value;
/* 1219 */     MapIterator i = new MapIterator();
/* 1220 */     s.defaultWriteObject();
/* 1221 */     for (int j = this.size; j-- != 0; ) {
/* 1222 */       int e = i.nextEntry();
/* 1223 */       s.writeFloat(key[e]);
/* 1224 */       s.writeShort(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1229 */     s.defaultReadObject();
/* 1230 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1231 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1232 */     this.mask = this.n - 1;
/* 1233 */     float[] key = this.key = new float[this.n + 1];
/* 1234 */     short[] value = this.value = new short[this.n + 1];
/*      */ 
/*      */     
/* 1237 */     for (int i = this.size; i-- != 0; ) {
/* 1238 */       int pos; float k = s.readFloat();
/* 1239 */       short v = s.readShort();
/* 1240 */       if (Float.floatToIntBits(k) == 0) {
/* 1241 */         pos = this.n;
/* 1242 */         this.containsNullKey = true;
/*      */       } else {
/* 1244 */         pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask;
/* 1245 */         while (Float.floatToIntBits(key[pos]) != 0)
/* 1246 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1248 */       key[pos] = k;
/* 1249 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\Float2ShortOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */