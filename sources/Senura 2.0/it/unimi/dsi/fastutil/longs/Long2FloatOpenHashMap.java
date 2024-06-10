/*      */ package it.unimi.dsi.fastutil.longs;
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
/*      */ import java.util.function.LongConsumer;
/*      */ import java.util.function.LongFunction;
/*      */ import java.util.function.LongToDoubleFunction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Long2FloatOpenHashMap
/*      */   extends AbstractLong2FloatMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient long[] key;
/*      */   protected transient float[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Long2FloatMap.FastEntrySet entries;
/*      */   protected transient LongSet keys;
/*      */   protected transient FloatCollection values;
/*      */   
/*      */   public Long2FloatOpenHashMap(int expected, float f) {
/*   96 */     if (f <= 0.0F || f > 1.0F)
/*   97 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*   98 */     if (expected < 0)
/*   99 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  100 */     this.f = f;
/*  101 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  102 */     this.mask = this.n - 1;
/*  103 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  104 */     this.key = new long[this.n + 1];
/*  105 */     this.value = new float[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2FloatOpenHashMap(int expected) {
/*  114 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2FloatOpenHashMap() {
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
/*      */   public Long2FloatOpenHashMap(Map<? extends Long, ? extends Float> m, float f) {
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
/*      */   public Long2FloatOpenHashMap(Map<? extends Long, ? extends Float> m) {
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
/*      */   public Long2FloatOpenHashMap(Long2FloatMap m, float f) {
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
/*      */   public Long2FloatOpenHashMap(Long2FloatMap m) {
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
/*      */   public Long2FloatOpenHashMap(long[] k, float[] v, float f) {
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
/*      */   public Long2FloatOpenHashMap(long[] k, float[] v) {
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
/*      */   public void putAll(Map<? extends Long, ? extends Float> m) {
/*  234 */     if (this.f <= 0.5D) {
/*  235 */       ensureCapacity(m.size());
/*      */     } else {
/*  237 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  239 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(long k) {
/*  243 */     if (k == 0L) {
/*  244 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  246 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  249 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  250 */       return -(pos + 1); 
/*  251 */     if (k == curr) {
/*  252 */       return pos;
/*      */     }
/*      */     while (true) {
/*  255 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  256 */         return -(pos + 1); 
/*  257 */       if (k == curr)
/*  258 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, long k, float v) {
/*  262 */     if (pos == this.n)
/*  263 */       this.containsNullKey = true; 
/*  264 */     this.key[pos] = k;
/*  265 */     this.value[pos] = v;
/*  266 */     if (this.size++ >= this.maxFill) {
/*  267 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public float put(long k, float v) {
/*  273 */     int pos = find(k);
/*  274 */     if (pos < 0) {
/*  275 */       insert(-pos - 1, k, v);
/*  276 */       return this.defRetValue;
/*      */     } 
/*  278 */     float oldValue = this.value[pos];
/*  279 */     this.value[pos] = v;
/*  280 */     return oldValue;
/*      */   }
/*      */   private float addToValue(int pos, float incr) {
/*  283 */     float oldValue = this.value[pos];
/*  284 */     this.value[pos] = oldValue + incr;
/*  285 */     return oldValue;
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
/*      */   public float addTo(long k, float incr) {
/*      */     int pos;
/*  305 */     if (k == 0L) {
/*  306 */       if (this.containsNullKey)
/*  307 */         return addToValue(this.n, incr); 
/*  308 */       pos = this.n;
/*  309 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  312 */       long[] key = this.key;
/*      */       long curr;
/*  314 */       if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) != 0L) {
/*  315 */         if (curr == k)
/*  316 */           return addToValue(pos, incr); 
/*  317 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0L) {
/*  318 */           if (curr == k)
/*  319 */             return addToValue(pos, incr); 
/*      */         } 
/*      */       } 
/*  322 */     }  this.key[pos] = k;
/*  323 */     this.value[pos] = this.defRetValue + incr;
/*  324 */     if (this.size++ >= this.maxFill) {
/*  325 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  328 */     return this.defRetValue;
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
/*  341 */     long[] key = this.key; while (true) {
/*      */       long curr; int last;
/*  343 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  345 */         if ((curr = key[pos]) == 0L) {
/*  346 */           key[last] = 0L;
/*      */           return;
/*      */         } 
/*  349 */         int slot = (int)HashCommon.mix(curr) & this.mask;
/*  350 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  352 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  354 */       key[last] = curr;
/*  355 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public float remove(long k) {
/*  361 */     if (k == 0L) {
/*  362 */       if (this.containsNullKey)
/*  363 */         return removeNullEntry(); 
/*  364 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  367 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  370 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  371 */       return this.defRetValue; 
/*  372 */     if (k == curr)
/*  373 */       return removeEntry(pos); 
/*      */     while (true) {
/*  375 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  376 */         return this.defRetValue; 
/*  377 */       if (k == curr) {
/*  378 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public float get(long k) {
/*  384 */     if (k == 0L) {
/*  385 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  387 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  390 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  391 */       return this.defRetValue; 
/*  392 */     if (k == curr) {
/*  393 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  396 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  397 */         return this.defRetValue; 
/*  398 */       if (k == curr) {
/*  399 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(long k) {
/*  405 */     if (k == 0L) {
/*  406 */       return this.containsNullKey;
/*      */     }
/*  408 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  411 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  412 */       return false; 
/*  413 */     if (k == curr) {
/*  414 */       return true;
/*      */     }
/*      */     while (true) {
/*  417 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  418 */         return false; 
/*  419 */       if (k == curr)
/*  420 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(float v) {
/*  425 */     float[] value = this.value;
/*  426 */     long[] key = this.key;
/*  427 */     if (this.containsNullKey && Float.floatToIntBits(value[this.n]) == Float.floatToIntBits(v))
/*  428 */       return true; 
/*  429 */     for (int i = this.n; i-- != 0;) {
/*  430 */       if (key[i] != 0L && Float.floatToIntBits(value[i]) == Float.floatToIntBits(v))
/*  431 */         return true; 
/*  432 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float getOrDefault(long k, float defaultValue) {
/*  438 */     if (k == 0L) {
/*  439 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  441 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  444 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  445 */       return defaultValue; 
/*  446 */     if (k == curr) {
/*  447 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  450 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  451 */         return defaultValue; 
/*  452 */       if (k == curr) {
/*  453 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public float putIfAbsent(long k, float v) {
/*  459 */     int pos = find(k);
/*  460 */     if (pos >= 0)
/*  461 */       return this.value[pos]; 
/*  462 */     insert(-pos - 1, k, v);
/*  463 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(long k, float v) {
/*  469 */     if (k == 0L) {
/*  470 */       if (this.containsNullKey && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[this.n])) {
/*  471 */         removeNullEntry();
/*  472 */         return true;
/*      */       } 
/*  474 */       return false;
/*      */     } 
/*      */     
/*  477 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  480 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  481 */       return false; 
/*  482 */     if (k == curr && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[pos])) {
/*  483 */       removeEntry(pos);
/*  484 */       return true;
/*      */     } 
/*      */     while (true) {
/*  487 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  488 */         return false; 
/*  489 */       if (k == curr && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[pos])) {
/*  490 */         removeEntry(pos);
/*  491 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(long k, float oldValue, float v) {
/*  498 */     int pos = find(k);
/*  499 */     if (pos < 0 || Float.floatToIntBits(oldValue) != Float.floatToIntBits(this.value[pos]))
/*  500 */       return false; 
/*  501 */     this.value[pos] = v;
/*  502 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public float replace(long k, float v) {
/*  507 */     int pos = find(k);
/*  508 */     if (pos < 0)
/*  509 */       return this.defRetValue; 
/*  510 */     float oldValue = this.value[pos];
/*  511 */     this.value[pos] = v;
/*  512 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public float computeIfAbsent(long k, LongToDoubleFunction mappingFunction) {
/*  517 */     Objects.requireNonNull(mappingFunction);
/*  518 */     int pos = find(k);
/*  519 */     if (pos >= 0)
/*  520 */       return this.value[pos]; 
/*  521 */     float newValue = SafeMath.safeDoubleToFloat(mappingFunction.applyAsDouble(k));
/*  522 */     insert(-pos - 1, k, newValue);
/*  523 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float computeIfAbsentNullable(long k, LongFunction<? extends Float> mappingFunction) {
/*  529 */     Objects.requireNonNull(mappingFunction);
/*  530 */     int pos = find(k);
/*  531 */     if (pos >= 0)
/*  532 */       return this.value[pos]; 
/*  533 */     Float newValue = mappingFunction.apply(k);
/*  534 */     if (newValue == null)
/*  535 */       return this.defRetValue; 
/*  536 */     float v = newValue.floatValue();
/*  537 */     insert(-pos - 1, k, v);
/*  538 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float computeIfPresent(long k, BiFunction<? super Long, ? super Float, ? extends Float> remappingFunction) {
/*  544 */     Objects.requireNonNull(remappingFunction);
/*  545 */     int pos = find(k);
/*  546 */     if (pos < 0)
/*  547 */       return this.defRetValue; 
/*  548 */     Float newValue = remappingFunction.apply(Long.valueOf(k), Float.valueOf(this.value[pos]));
/*  549 */     if (newValue == null) {
/*  550 */       if (k == 0L) {
/*  551 */         removeNullEntry();
/*      */       } else {
/*  553 */         removeEntry(pos);
/*  554 */       }  return this.defRetValue;
/*      */     } 
/*  556 */     this.value[pos] = newValue.floatValue(); return newValue.floatValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float compute(long k, BiFunction<? super Long, ? super Float, ? extends Float> remappingFunction) {
/*  562 */     Objects.requireNonNull(remappingFunction);
/*  563 */     int pos = find(k);
/*  564 */     Float newValue = remappingFunction.apply(Long.valueOf(k), (pos >= 0) ? Float.valueOf(this.value[pos]) : null);
/*  565 */     if (newValue == null) {
/*  566 */       if (pos >= 0)
/*  567 */         if (k == 0L) {
/*  568 */           removeNullEntry();
/*      */         } else {
/*  570 */           removeEntry(pos);
/*      */         }  
/*  572 */       return this.defRetValue;
/*      */     } 
/*  574 */     float newVal = newValue.floatValue();
/*  575 */     if (pos < 0) {
/*  576 */       insert(-pos - 1, k, newVal);
/*  577 */       return newVal;
/*      */     } 
/*  579 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float merge(long k, float v, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/*  585 */     Objects.requireNonNull(remappingFunction);
/*  586 */     int pos = find(k);
/*  587 */     if (pos < 0) {
/*  588 */       insert(-pos - 1, k, v);
/*  589 */       return v;
/*      */     } 
/*  591 */     Float newValue = remappingFunction.apply(Float.valueOf(this.value[pos]), Float.valueOf(v));
/*  592 */     if (newValue == null) {
/*  593 */       if (k == 0L) {
/*  594 */         removeNullEntry();
/*      */       } else {
/*  596 */         removeEntry(pos);
/*  597 */       }  return this.defRetValue;
/*      */     } 
/*  599 */     this.value[pos] = newValue.floatValue(); return newValue.floatValue();
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
/*  610 */     if (this.size == 0)
/*      */       return; 
/*  612 */     this.size = 0;
/*  613 */     this.containsNullKey = false;
/*  614 */     Arrays.fill(this.key, 0L);
/*      */   }
/*      */   
/*      */   public int size() {
/*  618 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  622 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Long2FloatMap.Entry, Map.Entry<Long, Float>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  634 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public long getLongKey() {
/*  640 */       return Long2FloatOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public float getFloatValue() {
/*  644 */       return Long2FloatOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public float setValue(float v) {
/*  648 */       float oldValue = Long2FloatOpenHashMap.this.value[this.index];
/*  649 */       Long2FloatOpenHashMap.this.value[this.index] = v;
/*  650 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long getKey() {
/*  660 */       return Long.valueOf(Long2FloatOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float getValue() {
/*  670 */       return Float.valueOf(Long2FloatOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float setValue(Float v) {
/*  680 */       return Float.valueOf(setValue(v.floatValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  685 */       if (!(o instanceof Map.Entry))
/*  686 */         return false; 
/*  687 */       Map.Entry<Long, Float> e = (Map.Entry<Long, Float>)o;
/*  688 */       return (Long2FloatOpenHashMap.this.key[this.index] == ((Long)e.getKey()).longValue() && 
/*  689 */         Float.floatToIntBits(Long2FloatOpenHashMap.this.value[this.index]) == Float.floatToIntBits(((Float)e.getValue()).floatValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  693 */       return HashCommon.long2int(Long2FloatOpenHashMap.this.key[this.index]) ^ 
/*  694 */         HashCommon.float2int(Long2FloatOpenHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  698 */       return Long2FloatOpenHashMap.this.key[this.index] + "=>" + Long2FloatOpenHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  708 */     int pos = Long2FloatOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  715 */     int last = -1;
/*      */     
/*  717 */     int c = Long2FloatOpenHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  721 */     boolean mustReturnNullKey = Long2FloatOpenHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     LongArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  728 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  731 */       if (!hasNext())
/*  732 */         throw new NoSuchElementException(); 
/*  733 */       this.c--;
/*  734 */       if (this.mustReturnNullKey) {
/*  735 */         this.mustReturnNullKey = false;
/*  736 */         return this.last = Long2FloatOpenHashMap.this.n;
/*      */       } 
/*  738 */       long[] key = Long2FloatOpenHashMap.this.key;
/*      */       while (true) {
/*  740 */         if (--this.pos < 0) {
/*      */           
/*  742 */           this.last = Integer.MIN_VALUE;
/*  743 */           long k = this.wrapped.getLong(-this.pos - 1);
/*  744 */           int p = (int)HashCommon.mix(k) & Long2FloatOpenHashMap.this.mask;
/*  745 */           while (k != key[p])
/*  746 */             p = p + 1 & Long2FloatOpenHashMap.this.mask; 
/*  747 */           return p;
/*      */         } 
/*  749 */         if (key[this.pos] != 0L) {
/*  750 */           return this.last = this.pos;
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
/*  764 */       long[] key = Long2FloatOpenHashMap.this.key; while (true) {
/*      */         long curr; int last;
/*  766 */         pos = (last = pos) + 1 & Long2FloatOpenHashMap.this.mask;
/*      */         while (true) {
/*  768 */           if ((curr = key[pos]) == 0L) {
/*  769 */             key[last] = 0L;
/*      */             return;
/*      */           } 
/*  772 */           int slot = (int)HashCommon.mix(curr) & Long2FloatOpenHashMap.this.mask;
/*  773 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  775 */           pos = pos + 1 & Long2FloatOpenHashMap.this.mask;
/*      */         } 
/*  777 */         if (pos < last) {
/*  778 */           if (this.wrapped == null)
/*  779 */             this.wrapped = new LongArrayList(2); 
/*  780 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  782 */         key[last] = curr;
/*  783 */         Long2FloatOpenHashMap.this.value[last] = Long2FloatOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  787 */       if (this.last == -1)
/*  788 */         throw new IllegalStateException(); 
/*  789 */       if (this.last == Long2FloatOpenHashMap.this.n) {
/*  790 */         Long2FloatOpenHashMap.this.containsNullKey = false;
/*  791 */       } else if (this.pos >= 0) {
/*  792 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  795 */         Long2FloatOpenHashMap.this.remove(this.wrapped.getLong(-this.pos - 1));
/*  796 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  799 */       Long2FloatOpenHashMap.this.size--;
/*  800 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  805 */       int i = n;
/*  806 */       while (i-- != 0 && hasNext())
/*  807 */         nextEntry(); 
/*  808 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Long2FloatMap.Entry> { private Long2FloatOpenHashMap.MapEntry entry;
/*      */     
/*      */     public Long2FloatOpenHashMap.MapEntry next() {
/*  815 */       return this.entry = new Long2FloatOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  819 */       super.remove();
/*  820 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Long2FloatMap.Entry> { private FastEntryIterator() {
/*  824 */       this.entry = new Long2FloatOpenHashMap.MapEntry();
/*      */     } private final Long2FloatOpenHashMap.MapEntry entry;
/*      */     public Long2FloatOpenHashMap.MapEntry next() {
/*  827 */       this.entry.index = nextEntry();
/*  828 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Long2FloatMap.Entry> implements Long2FloatMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Long2FloatMap.Entry> iterator() {
/*  834 */       return new Long2FloatOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Long2FloatMap.Entry> fastIterator() {
/*  838 */       return new Long2FloatOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  843 */       if (!(o instanceof Map.Entry))
/*  844 */         return false; 
/*  845 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  846 */       if (e.getKey() == null || !(e.getKey() instanceof Long))
/*  847 */         return false; 
/*  848 */       if (e.getValue() == null || !(e.getValue() instanceof Float))
/*  849 */         return false; 
/*  850 */       long k = ((Long)e.getKey()).longValue();
/*  851 */       float v = ((Float)e.getValue()).floatValue();
/*  852 */       if (k == 0L) {
/*  853 */         return (Long2FloatOpenHashMap.this.containsNullKey && 
/*  854 */           Float.floatToIntBits(Long2FloatOpenHashMap.this.value[Long2FloatOpenHashMap.this.n]) == Float.floatToIntBits(v));
/*      */       }
/*  856 */       long[] key = Long2FloatOpenHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/*  859 */       if ((curr = key[pos = (int)HashCommon.mix(k) & Long2FloatOpenHashMap.this.mask]) == 0L)
/*  860 */         return false; 
/*  861 */       if (k == curr) {
/*  862 */         return (Float.floatToIntBits(Long2FloatOpenHashMap.this.value[pos]) == Float.floatToIntBits(v));
/*      */       }
/*      */       while (true) {
/*  865 */         if ((curr = key[pos = pos + 1 & Long2FloatOpenHashMap.this.mask]) == 0L)
/*  866 */           return false; 
/*  867 */         if (k == curr) {
/*  868 */           return (Float.floatToIntBits(Long2FloatOpenHashMap.this.value[pos]) == Float.floatToIntBits(v));
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  874 */       if (!(o instanceof Map.Entry))
/*  875 */         return false; 
/*  876 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  877 */       if (e.getKey() == null || !(e.getKey() instanceof Long))
/*  878 */         return false; 
/*  879 */       if (e.getValue() == null || !(e.getValue() instanceof Float))
/*  880 */         return false; 
/*  881 */       long k = ((Long)e.getKey()).longValue();
/*  882 */       float v = ((Float)e.getValue()).floatValue();
/*  883 */       if (k == 0L) {
/*  884 */         if (Long2FloatOpenHashMap.this.containsNullKey && Float.floatToIntBits(Long2FloatOpenHashMap.this.value[Long2FloatOpenHashMap.this.n]) == Float.floatToIntBits(v)) {
/*  885 */           Long2FloatOpenHashMap.this.removeNullEntry();
/*  886 */           return true;
/*      */         } 
/*  888 */         return false;
/*      */       } 
/*      */       
/*  891 */       long[] key = Long2FloatOpenHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/*  894 */       if ((curr = key[pos = (int)HashCommon.mix(k) & Long2FloatOpenHashMap.this.mask]) == 0L)
/*  895 */         return false; 
/*  896 */       if (curr == k) {
/*  897 */         if (Float.floatToIntBits(Long2FloatOpenHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
/*  898 */           Long2FloatOpenHashMap.this.removeEntry(pos);
/*  899 */           return true;
/*      */         } 
/*  901 */         return false;
/*      */       } 
/*      */       while (true) {
/*  904 */         if ((curr = key[pos = pos + 1 & Long2FloatOpenHashMap.this.mask]) == 0L)
/*  905 */           return false; 
/*  906 */         if (curr == k && 
/*  907 */           Float.floatToIntBits(Long2FloatOpenHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
/*  908 */           Long2FloatOpenHashMap.this.removeEntry(pos);
/*  909 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  916 */       return Long2FloatOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  920 */       Long2FloatOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Long2FloatMap.Entry> consumer) {
/*  925 */       if (Long2FloatOpenHashMap.this.containsNullKey)
/*  926 */         consumer.accept(new AbstractLong2FloatMap.BasicEntry(Long2FloatOpenHashMap.this.key[Long2FloatOpenHashMap.this.n], Long2FloatOpenHashMap.this.value[Long2FloatOpenHashMap.this.n])); 
/*  927 */       for (int pos = Long2FloatOpenHashMap.this.n; pos-- != 0;) {
/*  928 */         if (Long2FloatOpenHashMap.this.key[pos] != 0L)
/*  929 */           consumer.accept(new AbstractLong2FloatMap.BasicEntry(Long2FloatOpenHashMap.this.key[pos], Long2FloatOpenHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Long2FloatMap.Entry> consumer) {
/*  934 */       AbstractLong2FloatMap.BasicEntry entry = new AbstractLong2FloatMap.BasicEntry();
/*  935 */       if (Long2FloatOpenHashMap.this.containsNullKey) {
/*  936 */         entry.key = Long2FloatOpenHashMap.this.key[Long2FloatOpenHashMap.this.n];
/*  937 */         entry.value = Long2FloatOpenHashMap.this.value[Long2FloatOpenHashMap.this.n];
/*  938 */         consumer.accept(entry);
/*      */       } 
/*  940 */       for (int pos = Long2FloatOpenHashMap.this.n; pos-- != 0;) {
/*  941 */         if (Long2FloatOpenHashMap.this.key[pos] != 0L) {
/*  942 */           entry.key = Long2FloatOpenHashMap.this.key[pos];
/*  943 */           entry.value = Long2FloatOpenHashMap.this.value[pos];
/*  944 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Long2FloatMap.FastEntrySet long2FloatEntrySet() {
/*  950 */     if (this.entries == null)
/*  951 */       this.entries = new MapEntrySet(); 
/*  952 */     return this.entries;
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
/*      */     implements LongIterator
/*      */   {
/*      */     public long nextLong() {
/*  969 */       return Long2FloatOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractLongSet { private KeySet() {}
/*      */     
/*      */     public LongIterator iterator() {
/*  975 */       return new Long2FloatOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(LongConsumer consumer) {
/*  980 */       if (Long2FloatOpenHashMap.this.containsNullKey)
/*  981 */         consumer.accept(Long2FloatOpenHashMap.this.key[Long2FloatOpenHashMap.this.n]); 
/*  982 */       for (int pos = Long2FloatOpenHashMap.this.n; pos-- != 0; ) {
/*  983 */         long k = Long2FloatOpenHashMap.this.key[pos];
/*  984 */         if (k != 0L)
/*  985 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  990 */       return Long2FloatOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(long k) {
/*  994 */       return Long2FloatOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(long k) {
/*  998 */       int oldSize = Long2FloatOpenHashMap.this.size;
/*  999 */       Long2FloatOpenHashMap.this.remove(k);
/* 1000 */       return (Long2FloatOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1004 */       Long2FloatOpenHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public LongSet keySet() {
/* 1009 */     if (this.keys == null)
/* 1010 */       this.keys = new KeySet(); 
/* 1011 */     return this.keys;
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
/* 1028 */       return Long2FloatOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public FloatCollection values() {
/* 1033 */     if (this.values == null)
/* 1034 */       this.values = (FloatCollection)new AbstractFloatCollection()
/*      */         {
/*      */           public FloatIterator iterator() {
/* 1037 */             return new Long2FloatOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1041 */             return Long2FloatOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(float v) {
/* 1045 */             return Long2FloatOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1049 */             Long2FloatOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(DoubleConsumer consumer)
/*      */           {
/* 1054 */             if (Long2FloatOpenHashMap.this.containsNullKey)
/* 1055 */               consumer.accept(Long2FloatOpenHashMap.this.value[Long2FloatOpenHashMap.this.n]); 
/* 1056 */             for (int pos = Long2FloatOpenHashMap.this.n; pos-- != 0;) {
/* 1057 */               if (Long2FloatOpenHashMap.this.key[pos] != 0L)
/* 1058 */                 consumer.accept(Long2FloatOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1061 */     return this.values;
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
/* 1078 */     return trim(this.size);
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
/* 1102 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1103 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1104 */       return true; 
/*      */     try {
/* 1106 */       rehash(l);
/* 1107 */     } catch (OutOfMemoryError cantDoIt) {
/* 1108 */       return false;
/*      */     } 
/* 1110 */     return true;
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
/* 1126 */     long[] key = this.key;
/* 1127 */     float[] value = this.value;
/* 1128 */     int mask = newN - 1;
/* 1129 */     long[] newKey = new long[newN + 1];
/* 1130 */     float[] newValue = new float[newN + 1];
/* 1131 */     int i = this.n;
/* 1132 */     for (int j = realSize(); j-- != 0; ) {
/* 1133 */       while (key[--i] == 0L); int pos;
/* 1134 */       if (newKey[pos = (int)HashCommon.mix(key[i]) & mask] != 0L)
/* 1135 */         while (newKey[pos = pos + 1 & mask] != 0L); 
/* 1136 */       newKey[pos] = key[i];
/* 1137 */       newValue[pos] = value[i];
/*      */     } 
/* 1139 */     newValue[newN] = value[this.n];
/* 1140 */     this.n = newN;
/* 1141 */     this.mask = mask;
/* 1142 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1143 */     this.key = newKey;
/* 1144 */     this.value = newValue;
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
/*      */   public Long2FloatOpenHashMap clone() {
/*      */     Long2FloatOpenHashMap c;
/*      */     try {
/* 1161 */       c = (Long2FloatOpenHashMap)super.clone();
/* 1162 */     } catch (CloneNotSupportedException cantHappen) {
/* 1163 */       throw new InternalError();
/*      */     } 
/* 1165 */     c.keys = null;
/* 1166 */     c.values = null;
/* 1167 */     c.entries = null;
/* 1168 */     c.containsNullKey = this.containsNullKey;
/* 1169 */     c.key = (long[])this.key.clone();
/* 1170 */     c.value = (float[])this.value.clone();
/* 1171 */     return c;
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
/* 1184 */     int h = 0;
/* 1185 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1186 */       while (this.key[i] == 0L)
/* 1187 */         i++; 
/* 1188 */       t = HashCommon.long2int(this.key[i]);
/* 1189 */       t ^= HashCommon.float2int(this.value[i]);
/* 1190 */       h += t;
/* 1191 */       i++;
/*      */     } 
/*      */     
/* 1194 */     if (this.containsNullKey)
/* 1195 */       h += HashCommon.float2int(this.value[this.n]); 
/* 1196 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1199 */     long[] key = this.key;
/* 1200 */     float[] value = this.value;
/* 1201 */     MapIterator i = new MapIterator();
/* 1202 */     s.defaultWriteObject();
/* 1203 */     for (int j = this.size; j-- != 0; ) {
/* 1204 */       int e = i.nextEntry();
/* 1205 */       s.writeLong(key[e]);
/* 1206 */       s.writeFloat(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1211 */     s.defaultReadObject();
/* 1212 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1213 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1214 */     this.mask = this.n - 1;
/* 1215 */     long[] key = this.key = new long[this.n + 1];
/* 1216 */     float[] value = this.value = new float[this.n + 1];
/*      */ 
/*      */     
/* 1219 */     for (int i = this.size; i-- != 0; ) {
/* 1220 */       int pos; long k = s.readLong();
/* 1221 */       float v = s.readFloat();
/* 1222 */       if (k == 0L) {
/* 1223 */         pos = this.n;
/* 1224 */         this.containsNullKey = true;
/*      */       } else {
/* 1226 */         pos = (int)HashCommon.mix(k) & this.mask;
/* 1227 */         while (key[pos] != 0L)
/* 1228 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1230 */       key[pos] = k;
/* 1231 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\Long2FloatOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */