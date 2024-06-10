/*      */ package it.unimi.dsi.fastutil.longs;
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
/*      */ import java.util.function.IntConsumer;
/*      */ import java.util.function.LongConsumer;
/*      */ import java.util.function.LongFunction;
/*      */ import java.util.function.LongToIntFunction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Long2ShortOpenHashMap
/*      */   extends AbstractLong2ShortMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient long[] key;
/*      */   protected transient short[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Long2ShortMap.FastEntrySet entries;
/*      */   protected transient LongSet keys;
/*      */   protected transient ShortCollection values;
/*      */   
/*      */   public Long2ShortOpenHashMap(int expected, float f) {
/*   96 */     if (f <= 0.0F || f > 1.0F)
/*   97 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*   98 */     if (expected < 0)
/*   99 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  100 */     this.f = f;
/*  101 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  102 */     this.mask = this.n - 1;
/*  103 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  104 */     this.key = new long[this.n + 1];
/*  105 */     this.value = new short[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2ShortOpenHashMap(int expected) {
/*  114 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2ShortOpenHashMap() {
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
/*      */   public Long2ShortOpenHashMap(Map<? extends Long, ? extends Short> m, float f) {
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
/*      */   public Long2ShortOpenHashMap(Map<? extends Long, ? extends Short> m) {
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
/*      */   public Long2ShortOpenHashMap(Long2ShortMap m, float f) {
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
/*      */   public Long2ShortOpenHashMap(Long2ShortMap m) {
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
/*      */   public Long2ShortOpenHashMap(long[] k, short[] v, float f) {
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
/*      */   public Long2ShortOpenHashMap(long[] k, short[] v) {
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
/*      */   public void putAll(Map<? extends Long, ? extends Short> m) {
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
/*      */   private void insert(int pos, long k, short v) {
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
/*      */   public short put(long k, short v) {
/*  273 */     int pos = find(k);
/*  274 */     if (pos < 0) {
/*  275 */       insert(-pos - 1, k, v);
/*  276 */       return this.defRetValue;
/*      */     } 
/*  278 */     short oldValue = this.value[pos];
/*  279 */     this.value[pos] = v;
/*  280 */     return oldValue;
/*      */   }
/*      */   private short addToValue(int pos, short incr) {
/*  283 */     short oldValue = this.value[pos];
/*  284 */     this.value[pos] = (short)(oldValue + incr);
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
/*      */   public short addTo(long k, short incr) {
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
/*  323 */     this.value[pos] = (short)(this.defRetValue + incr);
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
/*      */   public short remove(long k) {
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
/*      */   public short get(long k) {
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
/*      */   public boolean containsValue(short v) {
/*  425 */     short[] value = this.value;
/*  426 */     long[] key = this.key;
/*  427 */     if (this.containsNullKey && value[this.n] == v)
/*  428 */       return true; 
/*  429 */     for (int i = this.n; i-- != 0;) {
/*  430 */       if (key[i] != 0L && value[i] == v)
/*  431 */         return true; 
/*  432 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short getOrDefault(long k, short defaultValue) {
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
/*      */   public short putIfAbsent(long k, short v) {
/*  459 */     int pos = find(k);
/*  460 */     if (pos >= 0)
/*  461 */       return this.value[pos]; 
/*  462 */     insert(-pos - 1, k, v);
/*  463 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(long k, short v) {
/*  469 */     if (k == 0L) {
/*  470 */       if (this.containsNullKey && v == this.value[this.n]) {
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
/*  482 */     if (k == curr && v == this.value[pos]) {
/*  483 */       removeEntry(pos);
/*  484 */       return true;
/*      */     } 
/*      */     while (true) {
/*  487 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  488 */         return false; 
/*  489 */       if (k == curr && v == this.value[pos]) {
/*  490 */         removeEntry(pos);
/*  491 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(long k, short oldValue, short v) {
/*  498 */     int pos = find(k);
/*  499 */     if (pos < 0 || oldValue != this.value[pos])
/*  500 */       return false; 
/*  501 */     this.value[pos] = v;
/*  502 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public short replace(long k, short v) {
/*  507 */     int pos = find(k);
/*  508 */     if (pos < 0)
/*  509 */       return this.defRetValue; 
/*  510 */     short oldValue = this.value[pos];
/*  511 */     this.value[pos] = v;
/*  512 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public short computeIfAbsent(long k, LongToIntFunction mappingFunction) {
/*  517 */     Objects.requireNonNull(mappingFunction);
/*  518 */     int pos = find(k);
/*  519 */     if (pos >= 0)
/*  520 */       return this.value[pos]; 
/*  521 */     short newValue = SafeMath.safeIntToShort(mappingFunction.applyAsInt(k));
/*  522 */     insert(-pos - 1, k, newValue);
/*  523 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short computeIfAbsentNullable(long k, LongFunction<? extends Short> mappingFunction) {
/*  529 */     Objects.requireNonNull(mappingFunction);
/*  530 */     int pos = find(k);
/*  531 */     if (pos >= 0)
/*  532 */       return this.value[pos]; 
/*  533 */     Short newValue = mappingFunction.apply(k);
/*  534 */     if (newValue == null)
/*  535 */       return this.defRetValue; 
/*  536 */     short v = newValue.shortValue();
/*  537 */     insert(-pos - 1, k, v);
/*  538 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short computeIfPresent(long k, BiFunction<? super Long, ? super Short, ? extends Short> remappingFunction) {
/*  544 */     Objects.requireNonNull(remappingFunction);
/*  545 */     int pos = find(k);
/*  546 */     if (pos < 0)
/*  547 */       return this.defRetValue; 
/*  548 */     Short newValue = remappingFunction.apply(Long.valueOf(k), Short.valueOf(this.value[pos]));
/*  549 */     if (newValue == null) {
/*  550 */       if (k == 0L) {
/*  551 */         removeNullEntry();
/*      */       } else {
/*  553 */         removeEntry(pos);
/*  554 */       }  return this.defRetValue;
/*      */     } 
/*  556 */     this.value[pos] = newValue.shortValue(); return newValue.shortValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short compute(long k, BiFunction<? super Long, ? super Short, ? extends Short> remappingFunction) {
/*  562 */     Objects.requireNonNull(remappingFunction);
/*  563 */     int pos = find(k);
/*  564 */     Short newValue = remappingFunction.apply(Long.valueOf(k), (pos >= 0) ? Short.valueOf(this.value[pos]) : null);
/*  565 */     if (newValue == null) {
/*  566 */       if (pos >= 0)
/*  567 */         if (k == 0L) {
/*  568 */           removeNullEntry();
/*      */         } else {
/*  570 */           removeEntry(pos);
/*      */         }  
/*  572 */       return this.defRetValue;
/*      */     } 
/*  574 */     short newVal = newValue.shortValue();
/*  575 */     if (pos < 0) {
/*  576 */       insert(-pos - 1, k, newVal);
/*  577 */       return newVal;
/*      */     } 
/*  579 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short merge(long k, short v, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
/*  585 */     Objects.requireNonNull(remappingFunction);
/*  586 */     int pos = find(k);
/*  587 */     if (pos < 0) {
/*  588 */       insert(-pos - 1, k, v);
/*  589 */       return v;
/*      */     } 
/*  591 */     Short newValue = remappingFunction.apply(Short.valueOf(this.value[pos]), Short.valueOf(v));
/*  592 */     if (newValue == null) {
/*  593 */       if (k == 0L) {
/*  594 */         removeNullEntry();
/*      */       } else {
/*  596 */         removeEntry(pos);
/*  597 */       }  return this.defRetValue;
/*      */     } 
/*  599 */     this.value[pos] = newValue.shortValue(); return newValue.shortValue();
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
/*      */     implements Long2ShortMap.Entry, Map.Entry<Long, Short>
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
/*  640 */       return Long2ShortOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public short getShortValue() {
/*  644 */       return Long2ShortOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public short setValue(short v) {
/*  648 */       short oldValue = Long2ShortOpenHashMap.this.value[this.index];
/*  649 */       Long2ShortOpenHashMap.this.value[this.index] = v;
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
/*  660 */       return Long.valueOf(Long2ShortOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Short getValue() {
/*  670 */       return Short.valueOf(Long2ShortOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Short setValue(Short v) {
/*  680 */       return Short.valueOf(setValue(v.shortValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  685 */       if (!(o instanceof Map.Entry))
/*  686 */         return false; 
/*  687 */       Map.Entry<Long, Short> e = (Map.Entry<Long, Short>)o;
/*  688 */       return (Long2ShortOpenHashMap.this.key[this.index] == ((Long)e.getKey()).longValue() && Long2ShortOpenHashMap.this.value[this.index] == ((Short)e.getValue()).shortValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  692 */       return HashCommon.long2int(Long2ShortOpenHashMap.this.key[this.index]) ^ Long2ShortOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/*  696 */       return Long2ShortOpenHashMap.this.key[this.index] + "=>" + Long2ShortOpenHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  706 */     int pos = Long2ShortOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  713 */     int last = -1;
/*      */     
/*  715 */     int c = Long2ShortOpenHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  719 */     boolean mustReturnNullKey = Long2ShortOpenHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     LongArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  726 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  729 */       if (!hasNext())
/*  730 */         throw new NoSuchElementException(); 
/*  731 */       this.c--;
/*  732 */       if (this.mustReturnNullKey) {
/*  733 */         this.mustReturnNullKey = false;
/*  734 */         return this.last = Long2ShortOpenHashMap.this.n;
/*      */       } 
/*  736 */       long[] key = Long2ShortOpenHashMap.this.key;
/*      */       while (true) {
/*  738 */         if (--this.pos < 0) {
/*      */           
/*  740 */           this.last = Integer.MIN_VALUE;
/*  741 */           long k = this.wrapped.getLong(-this.pos - 1);
/*  742 */           int p = (int)HashCommon.mix(k) & Long2ShortOpenHashMap.this.mask;
/*  743 */           while (k != key[p])
/*  744 */             p = p + 1 & Long2ShortOpenHashMap.this.mask; 
/*  745 */           return p;
/*      */         } 
/*  747 */         if (key[this.pos] != 0L) {
/*  748 */           return this.last = this.pos;
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
/*  762 */       long[] key = Long2ShortOpenHashMap.this.key; while (true) {
/*      */         long curr; int last;
/*  764 */         pos = (last = pos) + 1 & Long2ShortOpenHashMap.this.mask;
/*      */         while (true) {
/*  766 */           if ((curr = key[pos]) == 0L) {
/*  767 */             key[last] = 0L;
/*      */             return;
/*      */           } 
/*  770 */           int slot = (int)HashCommon.mix(curr) & Long2ShortOpenHashMap.this.mask;
/*  771 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  773 */           pos = pos + 1 & Long2ShortOpenHashMap.this.mask;
/*      */         } 
/*  775 */         if (pos < last) {
/*  776 */           if (this.wrapped == null)
/*  777 */             this.wrapped = new LongArrayList(2); 
/*  778 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  780 */         key[last] = curr;
/*  781 */         Long2ShortOpenHashMap.this.value[last] = Long2ShortOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  785 */       if (this.last == -1)
/*  786 */         throw new IllegalStateException(); 
/*  787 */       if (this.last == Long2ShortOpenHashMap.this.n) {
/*  788 */         Long2ShortOpenHashMap.this.containsNullKey = false;
/*  789 */       } else if (this.pos >= 0) {
/*  790 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  793 */         Long2ShortOpenHashMap.this.remove(this.wrapped.getLong(-this.pos - 1));
/*  794 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  797 */       Long2ShortOpenHashMap.this.size--;
/*  798 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  803 */       int i = n;
/*  804 */       while (i-- != 0 && hasNext())
/*  805 */         nextEntry(); 
/*  806 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Long2ShortMap.Entry> { private Long2ShortOpenHashMap.MapEntry entry;
/*      */     
/*      */     public Long2ShortOpenHashMap.MapEntry next() {
/*  813 */       return this.entry = new Long2ShortOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  817 */       super.remove();
/*  818 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Long2ShortMap.Entry> { private FastEntryIterator() {
/*  822 */       this.entry = new Long2ShortOpenHashMap.MapEntry();
/*      */     } private final Long2ShortOpenHashMap.MapEntry entry;
/*      */     public Long2ShortOpenHashMap.MapEntry next() {
/*  825 */       this.entry.index = nextEntry();
/*  826 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Long2ShortMap.Entry> implements Long2ShortMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Long2ShortMap.Entry> iterator() {
/*  832 */       return new Long2ShortOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Long2ShortMap.Entry> fastIterator() {
/*  836 */       return new Long2ShortOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  841 */       if (!(o instanceof Map.Entry))
/*  842 */         return false; 
/*  843 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  844 */       if (e.getKey() == null || !(e.getKey() instanceof Long))
/*  845 */         return false; 
/*  846 */       if (e.getValue() == null || !(e.getValue() instanceof Short))
/*  847 */         return false; 
/*  848 */       long k = ((Long)e.getKey()).longValue();
/*  849 */       short v = ((Short)e.getValue()).shortValue();
/*  850 */       if (k == 0L) {
/*  851 */         return (Long2ShortOpenHashMap.this.containsNullKey && Long2ShortOpenHashMap.this.value[Long2ShortOpenHashMap.this.n] == v);
/*      */       }
/*  853 */       long[] key = Long2ShortOpenHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/*  856 */       if ((curr = key[pos = (int)HashCommon.mix(k) & Long2ShortOpenHashMap.this.mask]) == 0L)
/*  857 */         return false; 
/*  858 */       if (k == curr) {
/*  859 */         return (Long2ShortOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  862 */         if ((curr = key[pos = pos + 1 & Long2ShortOpenHashMap.this.mask]) == 0L)
/*  863 */           return false; 
/*  864 */         if (k == curr) {
/*  865 */           return (Long2ShortOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  871 */       if (!(o instanceof Map.Entry))
/*  872 */         return false; 
/*  873 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  874 */       if (e.getKey() == null || !(e.getKey() instanceof Long))
/*  875 */         return false; 
/*  876 */       if (e.getValue() == null || !(e.getValue() instanceof Short))
/*  877 */         return false; 
/*  878 */       long k = ((Long)e.getKey()).longValue();
/*  879 */       short v = ((Short)e.getValue()).shortValue();
/*  880 */       if (k == 0L) {
/*  881 */         if (Long2ShortOpenHashMap.this.containsNullKey && Long2ShortOpenHashMap.this.value[Long2ShortOpenHashMap.this.n] == v) {
/*  882 */           Long2ShortOpenHashMap.this.removeNullEntry();
/*  883 */           return true;
/*      */         } 
/*  885 */         return false;
/*      */       } 
/*      */       
/*  888 */       long[] key = Long2ShortOpenHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/*  891 */       if ((curr = key[pos = (int)HashCommon.mix(k) & Long2ShortOpenHashMap.this.mask]) == 0L)
/*  892 */         return false; 
/*  893 */       if (curr == k) {
/*  894 */         if (Long2ShortOpenHashMap.this.value[pos] == v) {
/*  895 */           Long2ShortOpenHashMap.this.removeEntry(pos);
/*  896 */           return true;
/*      */         } 
/*  898 */         return false;
/*      */       } 
/*      */       while (true) {
/*  901 */         if ((curr = key[pos = pos + 1 & Long2ShortOpenHashMap.this.mask]) == 0L)
/*  902 */           return false; 
/*  903 */         if (curr == k && 
/*  904 */           Long2ShortOpenHashMap.this.value[pos] == v) {
/*  905 */           Long2ShortOpenHashMap.this.removeEntry(pos);
/*  906 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  913 */       return Long2ShortOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  917 */       Long2ShortOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Long2ShortMap.Entry> consumer) {
/*  922 */       if (Long2ShortOpenHashMap.this.containsNullKey)
/*  923 */         consumer.accept(new AbstractLong2ShortMap.BasicEntry(Long2ShortOpenHashMap.this.key[Long2ShortOpenHashMap.this.n], Long2ShortOpenHashMap.this.value[Long2ShortOpenHashMap.this.n])); 
/*  924 */       for (int pos = Long2ShortOpenHashMap.this.n; pos-- != 0;) {
/*  925 */         if (Long2ShortOpenHashMap.this.key[pos] != 0L)
/*  926 */           consumer.accept(new AbstractLong2ShortMap.BasicEntry(Long2ShortOpenHashMap.this.key[pos], Long2ShortOpenHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Long2ShortMap.Entry> consumer) {
/*  931 */       AbstractLong2ShortMap.BasicEntry entry = new AbstractLong2ShortMap.BasicEntry();
/*  932 */       if (Long2ShortOpenHashMap.this.containsNullKey) {
/*  933 */         entry.key = Long2ShortOpenHashMap.this.key[Long2ShortOpenHashMap.this.n];
/*  934 */         entry.value = Long2ShortOpenHashMap.this.value[Long2ShortOpenHashMap.this.n];
/*  935 */         consumer.accept(entry);
/*      */       } 
/*  937 */       for (int pos = Long2ShortOpenHashMap.this.n; pos-- != 0;) {
/*  938 */         if (Long2ShortOpenHashMap.this.key[pos] != 0L) {
/*  939 */           entry.key = Long2ShortOpenHashMap.this.key[pos];
/*  940 */           entry.value = Long2ShortOpenHashMap.this.value[pos];
/*  941 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Long2ShortMap.FastEntrySet long2ShortEntrySet() {
/*  947 */     if (this.entries == null)
/*  948 */       this.entries = new MapEntrySet(); 
/*  949 */     return this.entries;
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
/*  966 */       return Long2ShortOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractLongSet { private KeySet() {}
/*      */     
/*      */     public LongIterator iterator() {
/*  972 */       return new Long2ShortOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(LongConsumer consumer) {
/*  977 */       if (Long2ShortOpenHashMap.this.containsNullKey)
/*  978 */         consumer.accept(Long2ShortOpenHashMap.this.key[Long2ShortOpenHashMap.this.n]); 
/*  979 */       for (int pos = Long2ShortOpenHashMap.this.n; pos-- != 0; ) {
/*  980 */         long k = Long2ShortOpenHashMap.this.key[pos];
/*  981 */         if (k != 0L)
/*  982 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  987 */       return Long2ShortOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(long k) {
/*  991 */       return Long2ShortOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(long k) {
/*  995 */       int oldSize = Long2ShortOpenHashMap.this.size;
/*  996 */       Long2ShortOpenHashMap.this.remove(k);
/*  997 */       return (Long2ShortOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1001 */       Long2ShortOpenHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public LongSet keySet() {
/* 1006 */     if (this.keys == null)
/* 1007 */       this.keys = new KeySet(); 
/* 1008 */     return this.keys;
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
/* 1025 */       return Long2ShortOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public ShortCollection values() {
/* 1030 */     if (this.values == null)
/* 1031 */       this.values = (ShortCollection)new AbstractShortCollection()
/*      */         {
/*      */           public ShortIterator iterator() {
/* 1034 */             return new Long2ShortOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1038 */             return Long2ShortOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(short v) {
/* 1042 */             return Long2ShortOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1046 */             Long2ShortOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(IntConsumer consumer)
/*      */           {
/* 1051 */             if (Long2ShortOpenHashMap.this.containsNullKey)
/* 1052 */               consumer.accept(Long2ShortOpenHashMap.this.value[Long2ShortOpenHashMap.this.n]); 
/* 1053 */             for (int pos = Long2ShortOpenHashMap.this.n; pos-- != 0;) {
/* 1054 */               if (Long2ShortOpenHashMap.this.key[pos] != 0L)
/* 1055 */                 consumer.accept(Long2ShortOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1058 */     return this.values;
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
/* 1075 */     return trim(this.size);
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
/* 1099 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1100 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1101 */       return true; 
/*      */     try {
/* 1103 */       rehash(l);
/* 1104 */     } catch (OutOfMemoryError cantDoIt) {
/* 1105 */       return false;
/*      */     } 
/* 1107 */     return true;
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
/* 1123 */     long[] key = this.key;
/* 1124 */     short[] value = this.value;
/* 1125 */     int mask = newN - 1;
/* 1126 */     long[] newKey = new long[newN + 1];
/* 1127 */     short[] newValue = new short[newN + 1];
/* 1128 */     int i = this.n;
/* 1129 */     for (int j = realSize(); j-- != 0; ) {
/* 1130 */       while (key[--i] == 0L); int pos;
/* 1131 */       if (newKey[pos = (int)HashCommon.mix(key[i]) & mask] != 0L)
/* 1132 */         while (newKey[pos = pos + 1 & mask] != 0L); 
/* 1133 */       newKey[pos] = key[i];
/* 1134 */       newValue[pos] = value[i];
/*      */     } 
/* 1136 */     newValue[newN] = value[this.n];
/* 1137 */     this.n = newN;
/* 1138 */     this.mask = mask;
/* 1139 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1140 */     this.key = newKey;
/* 1141 */     this.value = newValue;
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
/*      */   public Long2ShortOpenHashMap clone() {
/*      */     Long2ShortOpenHashMap c;
/*      */     try {
/* 1158 */       c = (Long2ShortOpenHashMap)super.clone();
/* 1159 */     } catch (CloneNotSupportedException cantHappen) {
/* 1160 */       throw new InternalError();
/*      */     } 
/* 1162 */     c.keys = null;
/* 1163 */     c.values = null;
/* 1164 */     c.entries = null;
/* 1165 */     c.containsNullKey = this.containsNullKey;
/* 1166 */     c.key = (long[])this.key.clone();
/* 1167 */     c.value = (short[])this.value.clone();
/* 1168 */     return c;
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
/* 1181 */     int h = 0;
/* 1182 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1183 */       while (this.key[i] == 0L)
/* 1184 */         i++; 
/* 1185 */       t = HashCommon.long2int(this.key[i]);
/* 1186 */       t ^= this.value[i];
/* 1187 */       h += t;
/* 1188 */       i++;
/*      */     } 
/*      */     
/* 1191 */     if (this.containsNullKey)
/* 1192 */       h += this.value[this.n]; 
/* 1193 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1196 */     long[] key = this.key;
/* 1197 */     short[] value = this.value;
/* 1198 */     MapIterator i = new MapIterator();
/* 1199 */     s.defaultWriteObject();
/* 1200 */     for (int j = this.size; j-- != 0; ) {
/* 1201 */       int e = i.nextEntry();
/* 1202 */       s.writeLong(key[e]);
/* 1203 */       s.writeShort(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1208 */     s.defaultReadObject();
/* 1209 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1210 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1211 */     this.mask = this.n - 1;
/* 1212 */     long[] key = this.key = new long[this.n + 1];
/* 1213 */     short[] value = this.value = new short[this.n + 1];
/*      */ 
/*      */     
/* 1216 */     for (int i = this.size; i-- != 0; ) {
/* 1217 */       int pos; long k = s.readLong();
/* 1218 */       short v = s.readShort();
/* 1219 */       if (k == 0L) {
/* 1220 */         pos = this.n;
/* 1221 */         this.containsNullKey = true;
/*      */       } else {
/* 1223 */         pos = (int)HashCommon.mix(k) & this.mask;
/* 1224 */         while (key[pos] != 0L)
/* 1225 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1227 */       key[pos] = k;
/* 1228 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\Long2ShortOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */