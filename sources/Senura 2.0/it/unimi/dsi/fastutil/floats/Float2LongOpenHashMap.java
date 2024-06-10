/*      */ package it.unimi.dsi.fastutil.floats;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
/*      */ import it.unimi.dsi.fastutil.longs.LongCollection;
/*      */ import it.unimi.dsi.fastutil.longs.LongIterator;
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
/*      */ import java.util.function.DoubleToLongFunction;
/*      */ import java.util.function.LongConsumer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Float2LongOpenHashMap
/*      */   extends AbstractFloat2LongMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient float[] key;
/*      */   protected transient long[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Float2LongMap.FastEntrySet entries;
/*      */   protected transient FloatSet keys;
/*      */   protected transient LongCollection values;
/*      */   
/*      */   public Float2LongOpenHashMap(int expected, float f) {
/*   96 */     if (f <= 0.0F || f > 1.0F)
/*   97 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*   98 */     if (expected < 0)
/*   99 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  100 */     this.f = f;
/*  101 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  102 */     this.mask = this.n - 1;
/*  103 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  104 */     this.key = new float[this.n + 1];
/*  105 */     this.value = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2LongOpenHashMap(int expected) {
/*  114 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2LongOpenHashMap() {
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
/*      */   public Float2LongOpenHashMap(Map<? extends Float, ? extends Long> m, float f) {
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
/*      */   public Float2LongOpenHashMap(Map<? extends Float, ? extends Long> m) {
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
/*      */   public Float2LongOpenHashMap(Float2LongMap m, float f) {
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
/*      */   public Float2LongOpenHashMap(Float2LongMap m) {
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
/*      */   public Float2LongOpenHashMap(float[] k, long[] v, float f) {
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
/*      */   public Float2LongOpenHashMap(float[] k, long[] v) {
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
/*      */   private long removeEntry(int pos) {
/*  217 */     long oldValue = this.value[pos];
/*  218 */     this.size--;
/*  219 */     shiftKeys(pos);
/*  220 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  221 */       rehash(this.n / 2); 
/*  222 */     return oldValue;
/*      */   }
/*      */   private long removeNullEntry() {
/*  225 */     this.containsNullKey = false;
/*  226 */     long oldValue = this.value[this.n];
/*  227 */     this.size--;
/*  228 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  229 */       rehash(this.n / 2); 
/*  230 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Float, ? extends Long> m) {
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
/*      */   private void insert(int pos, float k, long v) {
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
/*      */   public long put(float k, long v) {
/*  275 */     int pos = find(k);
/*  276 */     if (pos < 0) {
/*  277 */       insert(-pos - 1, k, v);
/*  278 */       return this.defRetValue;
/*      */     } 
/*  280 */     long oldValue = this.value[pos];
/*  281 */     this.value[pos] = v;
/*  282 */     return oldValue;
/*      */   }
/*      */   private long addToValue(int pos, long incr) {
/*  285 */     long oldValue = this.value[pos];
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
/*      */   public long addTo(float k, long incr) {
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
/*      */   public long remove(float k) {
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
/*      */   public long get(float k) {
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
/*      */   public boolean containsValue(long v) {
/*  435 */     long[] value = this.value;
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
/*      */   public long getOrDefault(float k, long defaultValue) {
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
/*      */   public long putIfAbsent(float k, long v) {
/*  471 */     int pos = find(k);
/*  472 */     if (pos >= 0)
/*  473 */       return this.value[pos]; 
/*  474 */     insert(-pos - 1, k, v);
/*  475 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(float k, long v) {
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
/*      */   public boolean replace(float k, long oldValue, long v) {
/*  512 */     int pos = find(k);
/*  513 */     if (pos < 0 || oldValue != this.value[pos])
/*  514 */       return false; 
/*  515 */     this.value[pos] = v;
/*  516 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public long replace(float k, long v) {
/*  521 */     int pos = find(k);
/*  522 */     if (pos < 0)
/*  523 */       return this.defRetValue; 
/*  524 */     long oldValue = this.value[pos];
/*  525 */     this.value[pos] = v;
/*  526 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public long computeIfAbsent(float k, DoubleToLongFunction mappingFunction) {
/*  531 */     Objects.requireNonNull(mappingFunction);
/*  532 */     int pos = find(k);
/*  533 */     if (pos >= 0)
/*  534 */       return this.value[pos]; 
/*  535 */     long newValue = mappingFunction.applyAsLong(k);
/*  536 */     insert(-pos - 1, k, newValue);
/*  537 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeIfAbsentNullable(float k, DoubleFunction<? extends Long> mappingFunction) {
/*  543 */     Objects.requireNonNull(mappingFunction);
/*  544 */     int pos = find(k);
/*  545 */     if (pos >= 0)
/*  546 */       return this.value[pos]; 
/*  547 */     Long newValue = mappingFunction.apply(k);
/*  548 */     if (newValue == null)
/*  549 */       return this.defRetValue; 
/*  550 */     long v = newValue.longValue();
/*  551 */     insert(-pos - 1, k, v);
/*  552 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeIfPresent(float k, BiFunction<? super Float, ? super Long, ? extends Long> remappingFunction) {
/*  558 */     Objects.requireNonNull(remappingFunction);
/*  559 */     int pos = find(k);
/*  560 */     if (pos < 0)
/*  561 */       return this.defRetValue; 
/*  562 */     Long newValue = remappingFunction.apply(Float.valueOf(k), Long.valueOf(this.value[pos]));
/*  563 */     if (newValue == null) {
/*  564 */       if (Float.floatToIntBits(k) == 0) {
/*  565 */         removeNullEntry();
/*      */       } else {
/*  567 */         removeEntry(pos);
/*  568 */       }  return this.defRetValue;
/*      */     } 
/*  570 */     this.value[pos] = newValue.longValue(); return newValue.longValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long compute(float k, BiFunction<? super Float, ? super Long, ? extends Long> remappingFunction) {
/*  576 */     Objects.requireNonNull(remappingFunction);
/*  577 */     int pos = find(k);
/*  578 */     Long newValue = remappingFunction.apply(Float.valueOf(k), (pos >= 0) ? Long.valueOf(this.value[pos]) : null);
/*  579 */     if (newValue == null) {
/*  580 */       if (pos >= 0)
/*  581 */         if (Float.floatToIntBits(k) == 0) {
/*  582 */           removeNullEntry();
/*      */         } else {
/*  584 */           removeEntry(pos);
/*      */         }  
/*  586 */       return this.defRetValue;
/*      */     } 
/*  588 */     long newVal = newValue.longValue();
/*  589 */     if (pos < 0) {
/*  590 */       insert(-pos - 1, k, newVal);
/*  591 */       return newVal;
/*      */     } 
/*  593 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long merge(float k, long v, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/*  599 */     Objects.requireNonNull(remappingFunction);
/*  600 */     int pos = find(k);
/*  601 */     if (pos < 0) {
/*  602 */       insert(-pos - 1, k, v);
/*  603 */       return v;
/*      */     } 
/*  605 */     Long newValue = remappingFunction.apply(Long.valueOf(this.value[pos]), Long.valueOf(v));
/*  606 */     if (newValue == null) {
/*  607 */       if (Float.floatToIntBits(k) == 0) {
/*  608 */         removeNullEntry();
/*      */       } else {
/*  610 */         removeEntry(pos);
/*  611 */       }  return this.defRetValue;
/*      */     } 
/*  613 */     this.value[pos] = newValue.longValue(); return newValue.longValue();
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
/*      */     implements Float2LongMap.Entry, Map.Entry<Float, Long>
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
/*  654 */       return Float2LongOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public long getLongValue() {
/*  658 */       return Float2LongOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public long setValue(long v) {
/*  662 */       long oldValue = Float2LongOpenHashMap.this.value[this.index];
/*  663 */       Float2LongOpenHashMap.this.value[this.index] = v;
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
/*  674 */       return Float.valueOf(Float2LongOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long getValue() {
/*  684 */       return Long.valueOf(Float2LongOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long setValue(Long v) {
/*  694 */       return Long.valueOf(setValue(v.longValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  699 */       if (!(o instanceof Map.Entry))
/*  700 */         return false; 
/*  701 */       Map.Entry<Float, Long> e = (Map.Entry<Float, Long>)o;
/*  702 */       return (Float.floatToIntBits(Float2LongOpenHashMap.this.key[this.index]) == Float.floatToIntBits(((Float)e.getKey()).floatValue()) && Float2LongOpenHashMap.this.value[this.index] == ((Long)e
/*  703 */         .getValue()).longValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  707 */       return HashCommon.float2int(Float2LongOpenHashMap.this.key[this.index]) ^ 
/*  708 */         HashCommon.long2int(Float2LongOpenHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  712 */       return Float2LongOpenHashMap.this.key[this.index] + "=>" + Float2LongOpenHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  722 */     int pos = Float2LongOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  729 */     int last = -1;
/*      */     
/*  731 */     int c = Float2LongOpenHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  735 */     boolean mustReturnNullKey = Float2LongOpenHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     FloatArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  742 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  745 */       if (!hasNext())
/*  746 */         throw new NoSuchElementException(); 
/*  747 */       this.c--;
/*  748 */       if (this.mustReturnNullKey) {
/*  749 */         this.mustReturnNullKey = false;
/*  750 */         return this.last = Float2LongOpenHashMap.this.n;
/*      */       } 
/*  752 */       float[] key = Float2LongOpenHashMap.this.key;
/*      */       while (true) {
/*  754 */         if (--this.pos < 0) {
/*      */           
/*  756 */           this.last = Integer.MIN_VALUE;
/*  757 */           float k = this.wrapped.getFloat(-this.pos - 1);
/*  758 */           int p = HashCommon.mix(HashCommon.float2int(k)) & Float2LongOpenHashMap.this.mask;
/*  759 */           while (Float.floatToIntBits(k) != Float.floatToIntBits(key[p]))
/*  760 */             p = p + 1 & Float2LongOpenHashMap.this.mask; 
/*  761 */           return p;
/*      */         } 
/*  763 */         if (Float.floatToIntBits(key[this.pos]) != 0) {
/*  764 */           return this.last = this.pos;
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
/*  778 */       float[] key = Float2LongOpenHashMap.this.key; while (true) {
/*      */         float curr; int last;
/*  780 */         pos = (last = pos) + 1 & Float2LongOpenHashMap.this.mask;
/*      */         while (true) {
/*  782 */           if (Float.floatToIntBits(curr = key[pos]) == 0) {
/*  783 */             key[last] = 0.0F;
/*      */             return;
/*      */           } 
/*  786 */           int slot = HashCommon.mix(HashCommon.float2int(curr)) & Float2LongOpenHashMap.this.mask;
/*      */           
/*  788 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  790 */           pos = pos + 1 & Float2LongOpenHashMap.this.mask;
/*      */         } 
/*  792 */         if (pos < last) {
/*  793 */           if (this.wrapped == null)
/*  794 */             this.wrapped = new FloatArrayList(2); 
/*  795 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  797 */         key[last] = curr;
/*  798 */         Float2LongOpenHashMap.this.value[last] = Float2LongOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  802 */       if (this.last == -1)
/*  803 */         throw new IllegalStateException(); 
/*  804 */       if (this.last == Float2LongOpenHashMap.this.n) {
/*  805 */         Float2LongOpenHashMap.this.containsNullKey = false;
/*  806 */       } else if (this.pos >= 0) {
/*  807 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  810 */         Float2LongOpenHashMap.this.remove(this.wrapped.getFloat(-this.pos - 1));
/*  811 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  814 */       Float2LongOpenHashMap.this.size--;
/*  815 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  820 */       int i = n;
/*  821 */       while (i-- != 0 && hasNext())
/*  822 */         nextEntry(); 
/*  823 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Float2LongMap.Entry> { private Float2LongOpenHashMap.MapEntry entry;
/*      */     
/*      */     public Float2LongOpenHashMap.MapEntry next() {
/*  830 */       return this.entry = new Float2LongOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  834 */       super.remove();
/*  835 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Float2LongMap.Entry> { private FastEntryIterator() {
/*  839 */       this.entry = new Float2LongOpenHashMap.MapEntry();
/*      */     } private final Float2LongOpenHashMap.MapEntry entry;
/*      */     public Float2LongOpenHashMap.MapEntry next() {
/*  842 */       this.entry.index = nextEntry();
/*  843 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Float2LongMap.Entry> implements Float2LongMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Float2LongMap.Entry> iterator() {
/*  849 */       return new Float2LongOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Float2LongMap.Entry> fastIterator() {
/*  853 */       return new Float2LongOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  858 */       if (!(o instanceof Map.Entry))
/*  859 */         return false; 
/*  860 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  861 */       if (e.getKey() == null || !(e.getKey() instanceof Float))
/*  862 */         return false; 
/*  863 */       if (e.getValue() == null || !(e.getValue() instanceof Long))
/*  864 */         return false; 
/*  865 */       float k = ((Float)e.getKey()).floatValue();
/*  866 */       long v = ((Long)e.getValue()).longValue();
/*  867 */       if (Float.floatToIntBits(k) == 0) {
/*  868 */         return (Float2LongOpenHashMap.this.containsNullKey && Float2LongOpenHashMap.this.value[Float2LongOpenHashMap.this.n] == v);
/*      */       }
/*  870 */       float[] key = Float2LongOpenHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/*  873 */       if (Float.floatToIntBits(
/*  874 */           curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & Float2LongOpenHashMap.this.mask]) == 0)
/*      */       {
/*  876 */         return false; } 
/*  877 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  878 */         return (Float2LongOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  881 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2LongOpenHashMap.this.mask]) == 0)
/*  882 */           return false; 
/*  883 */         if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  884 */           return (Float2LongOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  890 */       if (!(o instanceof Map.Entry))
/*  891 */         return false; 
/*  892 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  893 */       if (e.getKey() == null || !(e.getKey() instanceof Float))
/*  894 */         return false; 
/*  895 */       if (e.getValue() == null || !(e.getValue() instanceof Long))
/*  896 */         return false; 
/*  897 */       float k = ((Float)e.getKey()).floatValue();
/*  898 */       long v = ((Long)e.getValue()).longValue();
/*  899 */       if (Float.floatToIntBits(k) == 0) {
/*  900 */         if (Float2LongOpenHashMap.this.containsNullKey && Float2LongOpenHashMap.this.value[Float2LongOpenHashMap.this.n] == v) {
/*  901 */           Float2LongOpenHashMap.this.removeNullEntry();
/*  902 */           return true;
/*      */         } 
/*  904 */         return false;
/*      */       } 
/*      */       
/*  907 */       float[] key = Float2LongOpenHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/*  910 */       if (Float.floatToIntBits(
/*  911 */           curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & Float2LongOpenHashMap.this.mask]) == 0)
/*      */       {
/*  913 */         return false; } 
/*  914 */       if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
/*  915 */         if (Float2LongOpenHashMap.this.value[pos] == v) {
/*  916 */           Float2LongOpenHashMap.this.removeEntry(pos);
/*  917 */           return true;
/*      */         } 
/*  919 */         return false;
/*      */       } 
/*      */       while (true) {
/*  922 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2LongOpenHashMap.this.mask]) == 0)
/*  923 */           return false; 
/*  924 */         if (Float.floatToIntBits(curr) == Float.floatToIntBits(k) && 
/*  925 */           Float2LongOpenHashMap.this.value[pos] == v) {
/*  926 */           Float2LongOpenHashMap.this.removeEntry(pos);
/*  927 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  934 */       return Float2LongOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  938 */       Float2LongOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Float2LongMap.Entry> consumer) {
/*  943 */       if (Float2LongOpenHashMap.this.containsNullKey)
/*  944 */         consumer.accept(new AbstractFloat2LongMap.BasicEntry(Float2LongOpenHashMap.this.key[Float2LongOpenHashMap.this.n], Float2LongOpenHashMap.this.value[Float2LongOpenHashMap.this.n])); 
/*  945 */       for (int pos = Float2LongOpenHashMap.this.n; pos-- != 0;) {
/*  946 */         if (Float.floatToIntBits(Float2LongOpenHashMap.this.key[pos]) != 0)
/*  947 */           consumer.accept(new AbstractFloat2LongMap.BasicEntry(Float2LongOpenHashMap.this.key[pos], Float2LongOpenHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Float2LongMap.Entry> consumer) {
/*  952 */       AbstractFloat2LongMap.BasicEntry entry = new AbstractFloat2LongMap.BasicEntry();
/*  953 */       if (Float2LongOpenHashMap.this.containsNullKey) {
/*  954 */         entry.key = Float2LongOpenHashMap.this.key[Float2LongOpenHashMap.this.n];
/*  955 */         entry.value = Float2LongOpenHashMap.this.value[Float2LongOpenHashMap.this.n];
/*  956 */         consumer.accept(entry);
/*      */       } 
/*  958 */       for (int pos = Float2LongOpenHashMap.this.n; pos-- != 0;) {
/*  959 */         if (Float.floatToIntBits(Float2LongOpenHashMap.this.key[pos]) != 0) {
/*  960 */           entry.key = Float2LongOpenHashMap.this.key[pos];
/*  961 */           entry.value = Float2LongOpenHashMap.this.value[pos];
/*  962 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Float2LongMap.FastEntrySet float2LongEntrySet() {
/*  968 */     if (this.entries == null)
/*  969 */       this.entries = new MapEntrySet(); 
/*  970 */     return this.entries;
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
/*  987 */       return Float2LongOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractFloatSet { private KeySet() {}
/*      */     
/*      */     public FloatIterator iterator() {
/*  993 */       return new Float2LongOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/*  998 */       if (Float2LongOpenHashMap.this.containsNullKey)
/*  999 */         consumer.accept(Float2LongOpenHashMap.this.key[Float2LongOpenHashMap.this.n]); 
/* 1000 */       for (int pos = Float2LongOpenHashMap.this.n; pos-- != 0; ) {
/* 1001 */         float k = Float2LongOpenHashMap.this.key[pos];
/* 1002 */         if (Float.floatToIntBits(k) != 0)
/* 1003 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1008 */       return Float2LongOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(float k) {
/* 1012 */       return Float2LongOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(float k) {
/* 1016 */       int oldSize = Float2LongOpenHashMap.this.size;
/* 1017 */       Float2LongOpenHashMap.this.remove(k);
/* 1018 */       return (Float2LongOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1022 */       Float2LongOpenHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public FloatSet keySet() {
/* 1027 */     if (this.keys == null)
/* 1028 */       this.keys = new KeySet(); 
/* 1029 */     return this.keys;
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
/*      */     implements LongIterator
/*      */   {
/*      */     public long nextLong() {
/* 1046 */       return Float2LongOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public LongCollection values() {
/* 1051 */     if (this.values == null)
/* 1052 */       this.values = (LongCollection)new AbstractLongCollection()
/*      */         {
/*      */           public LongIterator iterator() {
/* 1055 */             return new Float2LongOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1059 */             return Float2LongOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(long v) {
/* 1063 */             return Float2LongOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1067 */             Float2LongOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(LongConsumer consumer)
/*      */           {
/* 1072 */             if (Float2LongOpenHashMap.this.containsNullKey)
/* 1073 */               consumer.accept(Float2LongOpenHashMap.this.value[Float2LongOpenHashMap.this.n]); 
/* 1074 */             for (int pos = Float2LongOpenHashMap.this.n; pos-- != 0;) {
/* 1075 */               if (Float.floatToIntBits(Float2LongOpenHashMap.this.key[pos]) != 0)
/* 1076 */                 consumer.accept(Float2LongOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1079 */     return this.values;
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
/* 1096 */     return trim(this.size);
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
/* 1120 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1121 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1122 */       return true; 
/*      */     try {
/* 1124 */       rehash(l);
/* 1125 */     } catch (OutOfMemoryError cantDoIt) {
/* 1126 */       return false;
/*      */     } 
/* 1128 */     return true;
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
/* 1144 */     float[] key = this.key;
/* 1145 */     long[] value = this.value;
/* 1146 */     int mask = newN - 1;
/* 1147 */     float[] newKey = new float[newN + 1];
/* 1148 */     long[] newValue = new long[newN + 1];
/* 1149 */     int i = this.n;
/* 1150 */     for (int j = realSize(); j-- != 0; ) {
/* 1151 */       while (Float.floatToIntBits(key[--i]) == 0); int pos;
/* 1152 */       if (Float.floatToIntBits(newKey[
/* 1153 */             pos = HashCommon.mix(HashCommon.float2int(key[i])) & mask]) != 0)
/* 1154 */         while (Float.floatToIntBits(newKey[pos = pos + 1 & mask]) != 0); 
/* 1155 */       newKey[pos] = key[i];
/* 1156 */       newValue[pos] = value[i];
/*      */     } 
/* 1158 */     newValue[newN] = value[this.n];
/* 1159 */     this.n = newN;
/* 1160 */     this.mask = mask;
/* 1161 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1162 */     this.key = newKey;
/* 1163 */     this.value = newValue;
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
/*      */   public Float2LongOpenHashMap clone() {
/*      */     Float2LongOpenHashMap c;
/*      */     try {
/* 1180 */       c = (Float2LongOpenHashMap)super.clone();
/* 1181 */     } catch (CloneNotSupportedException cantHappen) {
/* 1182 */       throw new InternalError();
/*      */     } 
/* 1184 */     c.keys = null;
/* 1185 */     c.values = null;
/* 1186 */     c.entries = null;
/* 1187 */     c.containsNullKey = this.containsNullKey;
/* 1188 */     c.key = (float[])this.key.clone();
/* 1189 */     c.value = (long[])this.value.clone();
/* 1190 */     return c;
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
/* 1203 */     int h = 0;
/* 1204 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1205 */       while (Float.floatToIntBits(this.key[i]) == 0)
/* 1206 */         i++; 
/* 1207 */       t = HashCommon.float2int(this.key[i]);
/* 1208 */       t ^= HashCommon.long2int(this.value[i]);
/* 1209 */       h += t;
/* 1210 */       i++;
/*      */     } 
/*      */     
/* 1213 */     if (this.containsNullKey)
/* 1214 */       h += HashCommon.long2int(this.value[this.n]); 
/* 1215 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1218 */     float[] key = this.key;
/* 1219 */     long[] value = this.value;
/* 1220 */     MapIterator i = new MapIterator();
/* 1221 */     s.defaultWriteObject();
/* 1222 */     for (int j = this.size; j-- != 0; ) {
/* 1223 */       int e = i.nextEntry();
/* 1224 */       s.writeFloat(key[e]);
/* 1225 */       s.writeLong(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1230 */     s.defaultReadObject();
/* 1231 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1232 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1233 */     this.mask = this.n - 1;
/* 1234 */     float[] key = this.key = new float[this.n + 1];
/* 1235 */     long[] value = this.value = new long[this.n + 1];
/*      */ 
/*      */     
/* 1238 */     for (int i = this.size; i-- != 0; ) {
/* 1239 */       int pos; float k = s.readFloat();
/* 1240 */       long v = s.readLong();
/* 1241 */       if (Float.floatToIntBits(k) == 0) {
/* 1242 */         pos = this.n;
/* 1243 */         this.containsNullKey = true;
/*      */       } else {
/* 1245 */         pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask;
/* 1246 */         while (Float.floatToIntBits(key[pos]) != 0)
/* 1247 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1249 */       key[pos] = k;
/* 1250 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\Float2LongOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */