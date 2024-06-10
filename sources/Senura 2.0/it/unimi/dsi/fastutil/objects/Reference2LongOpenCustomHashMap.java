/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
/*      */ import it.unimi.dsi.fastutil.longs.LongCollection;
/*      */ import it.unimi.dsi.fastutil.longs.LongIterator;
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
/*      */ import java.util.function.LongConsumer;
/*      */ import java.util.function.ToLongFunction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Reference2LongOpenCustomHashMap<K>
/*      */   extends AbstractReference2LongMap<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient long[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected Hash.Strategy<? super K> strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Reference2LongMap.FastEntrySet<K> entries;
/*      */   protected transient ReferenceSet<K> keys;
/*      */   protected transient LongCollection values;
/*      */   
/*      */   public Reference2LongOpenCustomHashMap(int expected, float f, Hash.Strategy<? super K> strategy) {
/*  106 */     this.strategy = strategy;
/*  107 */     if (f <= 0.0F || f > 1.0F)
/*  108 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  109 */     if (expected < 0)
/*  110 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  111 */     this.f = f;
/*  112 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  113 */     this.mask = this.n - 1;
/*  114 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  115 */     this.key = (K[])new Object[this.n + 1];
/*  116 */     this.value = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2LongOpenCustomHashMap(int expected, Hash.Strategy<? super K> strategy) {
/*  127 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2LongOpenCustomHashMap(Hash.Strategy<? super K> strategy) {
/*  138 */     this(16, 0.75F, strategy);
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
/*      */   public Reference2LongOpenCustomHashMap(Map<? extends K, ? extends Long> m, float f, Hash.Strategy<? super K> strategy) {
/*  152 */     this(m.size(), f, strategy);
/*  153 */     putAll(m);
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
/*      */   public Reference2LongOpenCustomHashMap(Map<? extends K, ? extends Long> m, Hash.Strategy<? super K> strategy) {
/*  166 */     this(m, 0.75F, strategy);
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
/*      */   public Reference2LongOpenCustomHashMap(Reference2LongMap<K> m, float f, Hash.Strategy<? super K> strategy) {
/*  180 */     this(m.size(), f, strategy);
/*  181 */     putAll(m);
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
/*      */   public Reference2LongOpenCustomHashMap(Reference2LongMap<K> m, Hash.Strategy<? super K> strategy) {
/*  193 */     this(m, 0.75F, strategy);
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
/*      */   public Reference2LongOpenCustomHashMap(K[] k, long[] v, float f, Hash.Strategy<? super K> strategy) {
/*  211 */     this(k.length, f, strategy);
/*  212 */     if (k.length != v.length) {
/*  213 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  215 */     for (int i = 0; i < k.length; i++) {
/*  216 */       put(k[i], v[i]);
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
/*      */   public Reference2LongOpenCustomHashMap(K[] k, long[] v, Hash.Strategy<? super K> strategy) {
/*  232 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Hash.Strategy<? super K> strategy() {
/*  240 */     return this.strategy;
/*      */   }
/*      */   private int realSize() {
/*  243 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  246 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  247 */     if (needed > this.n)
/*  248 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  251 */     int needed = (int)Math.min(1073741824L, 
/*  252 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  253 */     if (needed > this.n)
/*  254 */       rehash(needed); 
/*      */   }
/*      */   private long removeEntry(int pos) {
/*  257 */     long oldValue = this.value[pos];
/*  258 */     this.size--;
/*  259 */     shiftKeys(pos);
/*  260 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  261 */       rehash(this.n / 2); 
/*  262 */     return oldValue;
/*      */   }
/*      */   private long removeNullEntry() {
/*  265 */     this.containsNullKey = false;
/*  266 */     this.key[this.n] = null;
/*  267 */     long oldValue = this.value[this.n];
/*  268 */     this.size--;
/*  269 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  270 */       rehash(this.n / 2); 
/*  271 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends Long> m) {
/*  275 */     if (this.f <= 0.5D) {
/*  276 */       ensureCapacity(m.size());
/*      */     } else {
/*  278 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  280 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(K k) {
/*  284 */     if (this.strategy.equals(k, null)) {
/*  285 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  287 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  290 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  291 */       return -(pos + 1); 
/*  292 */     if (this.strategy.equals(k, curr)) {
/*  293 */       return pos;
/*      */     }
/*      */     while (true) {
/*  296 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  297 */         return -(pos + 1); 
/*  298 */       if (this.strategy.equals(k, curr))
/*  299 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, K k, long v) {
/*  303 */     if (pos == this.n)
/*  304 */       this.containsNullKey = true; 
/*  305 */     this.key[pos] = k;
/*  306 */     this.value[pos] = v;
/*  307 */     if (this.size++ >= this.maxFill) {
/*  308 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public long put(K k, long v) {
/*  314 */     int pos = find(k);
/*  315 */     if (pos < 0) {
/*  316 */       insert(-pos - 1, k, v);
/*  317 */       return this.defRetValue;
/*      */     } 
/*  319 */     long oldValue = this.value[pos];
/*  320 */     this.value[pos] = v;
/*  321 */     return oldValue;
/*      */   }
/*      */   private long addToValue(int pos, long incr) {
/*  324 */     long oldValue = this.value[pos];
/*  325 */     this.value[pos] = oldValue + incr;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long addTo(K k, long incr) {
/*      */     int pos;
/*  346 */     if (this.strategy.equals(k, null)) {
/*  347 */       if (this.containsNullKey)
/*  348 */         return addToValue(this.n, incr); 
/*  349 */       pos = this.n;
/*  350 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  353 */       K[] key = this.key;
/*      */       K curr;
/*  355 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != null) {
/*  356 */         if (this.strategy.equals(curr, k))
/*  357 */           return addToValue(pos, incr); 
/*  358 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  359 */           if (this.strategy.equals(curr, k))
/*  360 */             return addToValue(pos, incr); 
/*      */         } 
/*      */       } 
/*  363 */     }  this.key[pos] = k;
/*  364 */     this.value[pos] = this.defRetValue + incr;
/*  365 */     if (this.size++ >= this.maxFill) {
/*  366 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  369 */     return this.defRetValue;
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
/*  382 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  384 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  386 */         if ((curr = key[pos]) == null) {
/*  387 */           key[last] = null;
/*      */           return;
/*      */         } 
/*  390 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  391 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  393 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  395 */       key[last] = curr;
/*  396 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public long removeLong(Object k) {
/*  402 */     if (this.strategy.equals(k, null)) {
/*  403 */       if (this.containsNullKey)
/*  404 */         return removeNullEntry(); 
/*  405 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  408 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  411 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  412 */       return this.defRetValue; 
/*  413 */     if (this.strategy.equals(k, curr))
/*  414 */       return removeEntry(pos); 
/*      */     while (true) {
/*  416 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  417 */         return this.defRetValue; 
/*  418 */       if (this.strategy.equals(k, curr)) {
/*  419 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public long getLong(Object k) {
/*  425 */     if (this.strategy.equals(k, null)) {
/*  426 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  428 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  431 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  432 */       return this.defRetValue; 
/*  433 */     if (this.strategy.equals(k, curr)) {
/*  434 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  437 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  438 */         return this.defRetValue; 
/*  439 */       if (this.strategy.equals(k, curr)) {
/*  440 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  446 */     if (this.strategy.equals(k, null)) {
/*  447 */       return this.containsNullKey;
/*      */     }
/*  449 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  452 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  453 */       return false; 
/*  454 */     if (this.strategy.equals(k, curr)) {
/*  455 */       return true;
/*      */     }
/*      */     while (true) {
/*  458 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  459 */         return false; 
/*  460 */       if (this.strategy.equals(k, curr))
/*  461 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(long v) {
/*  466 */     long[] value = this.value;
/*  467 */     K[] key = this.key;
/*  468 */     if (this.containsNullKey && value[this.n] == v)
/*  469 */       return true; 
/*  470 */     for (int i = this.n; i-- != 0;) {
/*  471 */       if (key[i] != null && value[i] == v)
/*  472 */         return true; 
/*  473 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long getOrDefault(Object k, long defaultValue) {
/*  479 */     if (this.strategy.equals(k, null)) {
/*  480 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  482 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  485 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  486 */       return defaultValue; 
/*  487 */     if (this.strategy.equals(k, curr)) {
/*  488 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  491 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  492 */         return defaultValue; 
/*  493 */       if (this.strategy.equals(k, curr)) {
/*  494 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public long putIfAbsent(K k, long v) {
/*  500 */     int pos = find(k);
/*  501 */     if (pos >= 0)
/*  502 */       return this.value[pos]; 
/*  503 */     insert(-pos - 1, k, v);
/*  504 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, long v) {
/*  510 */     if (this.strategy.equals(k, null)) {
/*  511 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  512 */         removeNullEntry();
/*  513 */         return true;
/*      */       } 
/*  515 */       return false;
/*      */     } 
/*      */     
/*  518 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  521 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  522 */       return false; 
/*  523 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  524 */       removeEntry(pos);
/*  525 */       return true;
/*      */     } 
/*      */     while (true) {
/*  528 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  529 */         return false; 
/*  530 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  531 */         removeEntry(pos);
/*  532 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(K k, long oldValue, long v) {
/*  539 */     int pos = find(k);
/*  540 */     if (pos < 0 || oldValue != this.value[pos])
/*  541 */       return false; 
/*  542 */     this.value[pos] = v;
/*  543 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public long replace(K k, long v) {
/*  548 */     int pos = find(k);
/*  549 */     if (pos < 0)
/*  550 */       return this.defRetValue; 
/*  551 */     long oldValue = this.value[pos];
/*  552 */     this.value[pos] = v;
/*  553 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public long computeLongIfAbsent(K k, ToLongFunction<? super K> mappingFunction) {
/*  558 */     Objects.requireNonNull(mappingFunction);
/*  559 */     int pos = find(k);
/*  560 */     if (pos >= 0)
/*  561 */       return this.value[pos]; 
/*  562 */     long newValue = mappingFunction.applyAsLong(k);
/*  563 */     insert(-pos - 1, k, newValue);
/*  564 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeLongIfPresent(K k, BiFunction<? super K, ? super Long, ? extends Long> remappingFunction) {
/*  570 */     Objects.requireNonNull(remappingFunction);
/*  571 */     int pos = find(k);
/*  572 */     if (pos < 0)
/*  573 */       return this.defRetValue; 
/*  574 */     Long newValue = remappingFunction.apply(k, Long.valueOf(this.value[pos]));
/*  575 */     if (newValue == null) {
/*  576 */       if (this.strategy.equals(k, null)) {
/*  577 */         removeNullEntry();
/*      */       } else {
/*  579 */         removeEntry(pos);
/*  580 */       }  return this.defRetValue;
/*      */     } 
/*  582 */     this.value[pos] = newValue.longValue(); return newValue.longValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeLong(K k, BiFunction<? super K, ? super Long, ? extends Long> remappingFunction) {
/*  588 */     Objects.requireNonNull(remappingFunction);
/*  589 */     int pos = find(k);
/*  590 */     Long newValue = remappingFunction.apply(k, (pos >= 0) ? Long.valueOf(this.value[pos]) : null);
/*  591 */     if (newValue == null) {
/*  592 */       if (pos >= 0)
/*  593 */         if (this.strategy.equals(k, null)) {
/*  594 */           removeNullEntry();
/*      */         } else {
/*  596 */           removeEntry(pos);
/*      */         }  
/*  598 */       return this.defRetValue;
/*      */     } 
/*  600 */     long newVal = newValue.longValue();
/*  601 */     if (pos < 0) {
/*  602 */       insert(-pos - 1, k, newVal);
/*  603 */       return newVal;
/*      */     } 
/*  605 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long mergeLong(K k, long v, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/*  611 */     Objects.requireNonNull(remappingFunction);
/*  612 */     int pos = find(k);
/*  613 */     if (pos < 0) {
/*  614 */       insert(-pos - 1, k, v);
/*  615 */       return v;
/*      */     } 
/*  617 */     Long newValue = remappingFunction.apply(Long.valueOf(this.value[pos]), Long.valueOf(v));
/*  618 */     if (newValue == null) {
/*  619 */       if (this.strategy.equals(k, null)) {
/*  620 */         removeNullEntry();
/*      */       } else {
/*  622 */         removeEntry(pos);
/*  623 */       }  return this.defRetValue;
/*      */     } 
/*  625 */     this.value[pos] = newValue.longValue(); return newValue.longValue();
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
/*  636 */     if (this.size == 0)
/*      */       return; 
/*  638 */     this.size = 0;
/*  639 */     this.containsNullKey = false;
/*  640 */     Arrays.fill((Object[])this.key, (Object)null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  644 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  648 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Reference2LongMap.Entry<K>, Map.Entry<K, Long>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  660 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public K getKey() {
/*  666 */       return Reference2LongOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public long getLongValue() {
/*  670 */       return Reference2LongOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public long setValue(long v) {
/*  674 */       long oldValue = Reference2LongOpenCustomHashMap.this.value[this.index];
/*  675 */       Reference2LongOpenCustomHashMap.this.value[this.index] = v;
/*  676 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long getValue() {
/*  686 */       return Long.valueOf(Reference2LongOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long setValue(Long v) {
/*  696 */       return Long.valueOf(setValue(v.longValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  701 */       if (!(o instanceof Map.Entry))
/*  702 */         return false; 
/*  703 */       Map.Entry<K, Long> e = (Map.Entry<K, Long>)o;
/*  704 */       return (Reference2LongOpenCustomHashMap.this.strategy.equals(Reference2LongOpenCustomHashMap.this.key[this.index], e.getKey()) && Reference2LongOpenCustomHashMap.this.value[this.index] == ((Long)e.getValue()).longValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  708 */       return Reference2LongOpenCustomHashMap.this.strategy.hashCode(Reference2LongOpenCustomHashMap.this.key[this.index]) ^ HashCommon.long2int(Reference2LongOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  712 */       return (new StringBuilder()).append(Reference2LongOpenCustomHashMap.this.key[this.index]).append("=>").append(Reference2LongOpenCustomHashMap.this.value[this.index]).toString();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  722 */     int pos = Reference2LongOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  729 */     int last = -1;
/*      */     
/*  731 */     int c = Reference2LongOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  735 */     boolean mustReturnNullKey = Reference2LongOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     ReferenceArrayList<K> wrapped;
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
/*  750 */         return this.last = Reference2LongOpenCustomHashMap.this.n;
/*      */       } 
/*  752 */       K[] key = Reference2LongOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  754 */         if (--this.pos < 0) {
/*      */           
/*  756 */           this.last = Integer.MIN_VALUE;
/*  757 */           K k = this.wrapped.get(-this.pos - 1);
/*  758 */           int p = HashCommon.mix(Reference2LongOpenCustomHashMap.this.strategy.hashCode(k)) & Reference2LongOpenCustomHashMap.this.mask;
/*  759 */           while (!Reference2LongOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  760 */             p = p + 1 & Reference2LongOpenCustomHashMap.this.mask; 
/*  761 */           return p;
/*      */         } 
/*  763 */         if (key[this.pos] != null) {
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
/*  778 */       K[] key = Reference2LongOpenCustomHashMap.this.key; while (true) {
/*      */         K curr; int last;
/*  780 */         pos = (last = pos) + 1 & Reference2LongOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  782 */           if ((curr = key[pos]) == null) {
/*  783 */             key[last] = null;
/*      */             return;
/*      */           } 
/*  786 */           int slot = HashCommon.mix(Reference2LongOpenCustomHashMap.this.strategy.hashCode(curr)) & Reference2LongOpenCustomHashMap.this.mask;
/*  787 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  789 */           pos = pos + 1 & Reference2LongOpenCustomHashMap.this.mask;
/*      */         } 
/*  791 */         if (pos < last) {
/*  792 */           if (this.wrapped == null)
/*  793 */             this.wrapped = new ReferenceArrayList<>(2); 
/*  794 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  796 */         key[last] = curr;
/*  797 */         Reference2LongOpenCustomHashMap.this.value[last] = Reference2LongOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  801 */       if (this.last == -1)
/*  802 */         throw new IllegalStateException(); 
/*  803 */       if (this.last == Reference2LongOpenCustomHashMap.this.n) {
/*  804 */         Reference2LongOpenCustomHashMap.this.containsNullKey = false;
/*  805 */         Reference2LongOpenCustomHashMap.this.key[Reference2LongOpenCustomHashMap.this.n] = null;
/*  806 */       } else if (this.pos >= 0) {
/*  807 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  810 */         Reference2LongOpenCustomHashMap.this.removeLong(this.wrapped.set(-this.pos - 1, null));
/*  811 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  814 */       Reference2LongOpenCustomHashMap.this.size--;
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
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Reference2LongMap.Entry<K>> { private Reference2LongOpenCustomHashMap<K>.MapEntry entry;
/*      */     
/*      */     public Reference2LongOpenCustomHashMap<K>.MapEntry next() {
/*  830 */       return this.entry = new Reference2LongOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  834 */       super.remove();
/*  835 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Reference2LongMap.Entry<K>> { private FastEntryIterator() {
/*  839 */       this.entry = new Reference2LongOpenCustomHashMap.MapEntry();
/*      */     } private final Reference2LongOpenCustomHashMap<K>.MapEntry entry;
/*      */     public Reference2LongOpenCustomHashMap<K>.MapEntry next() {
/*  842 */       this.entry.index = nextEntry();
/*  843 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Reference2LongMap.Entry<K>> implements Reference2LongMap.FastEntrySet<K> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Reference2LongMap.Entry<K>> iterator() {
/*  849 */       return new Reference2LongOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Reference2LongMap.Entry<K>> fastIterator() {
/*  853 */       return new Reference2LongOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  858 */       if (!(o instanceof Map.Entry))
/*  859 */         return false; 
/*  860 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  861 */       if (e.getValue() == null || !(e.getValue() instanceof Long))
/*  862 */         return false; 
/*  863 */       K k = (K)e.getKey();
/*  864 */       long v = ((Long)e.getValue()).longValue();
/*  865 */       if (Reference2LongOpenCustomHashMap.this.strategy.equals(k, null)) {
/*  866 */         return (Reference2LongOpenCustomHashMap.this.containsNullKey && Reference2LongOpenCustomHashMap.this.value[Reference2LongOpenCustomHashMap.this.n] == v);
/*      */       }
/*  868 */       K[] key = Reference2LongOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  871 */       if ((curr = key[pos = HashCommon.mix(Reference2LongOpenCustomHashMap.this.strategy.hashCode(k)) & Reference2LongOpenCustomHashMap.this.mask]) == null)
/*  872 */         return false; 
/*  873 */       if (Reference2LongOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  874 */         return (Reference2LongOpenCustomHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  877 */         if ((curr = key[pos = pos + 1 & Reference2LongOpenCustomHashMap.this.mask]) == null)
/*  878 */           return false; 
/*  879 */         if (Reference2LongOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  880 */           return (Reference2LongOpenCustomHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  886 */       if (!(o instanceof Map.Entry))
/*  887 */         return false; 
/*  888 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  889 */       if (e.getValue() == null || !(e.getValue() instanceof Long))
/*  890 */         return false; 
/*  891 */       K k = (K)e.getKey();
/*  892 */       long v = ((Long)e.getValue()).longValue();
/*  893 */       if (Reference2LongOpenCustomHashMap.this.strategy.equals(k, null)) {
/*  894 */         if (Reference2LongOpenCustomHashMap.this.containsNullKey && Reference2LongOpenCustomHashMap.this.value[Reference2LongOpenCustomHashMap.this.n] == v) {
/*  895 */           Reference2LongOpenCustomHashMap.this.removeNullEntry();
/*  896 */           return true;
/*      */         } 
/*  898 */         return false;
/*      */       } 
/*      */       
/*  901 */       K[] key = Reference2LongOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  904 */       if ((curr = key[pos = HashCommon.mix(Reference2LongOpenCustomHashMap.this.strategy.hashCode(k)) & Reference2LongOpenCustomHashMap.this.mask]) == null)
/*  905 */         return false; 
/*  906 */       if (Reference2LongOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  907 */         if (Reference2LongOpenCustomHashMap.this.value[pos] == v) {
/*  908 */           Reference2LongOpenCustomHashMap.this.removeEntry(pos);
/*  909 */           return true;
/*      */         } 
/*  911 */         return false;
/*      */       } 
/*      */       while (true) {
/*  914 */         if ((curr = key[pos = pos + 1 & Reference2LongOpenCustomHashMap.this.mask]) == null)
/*  915 */           return false; 
/*  916 */         if (Reference2LongOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  917 */           Reference2LongOpenCustomHashMap.this.value[pos] == v) {
/*  918 */           Reference2LongOpenCustomHashMap.this.removeEntry(pos);
/*  919 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  926 */       return Reference2LongOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  930 */       Reference2LongOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Reference2LongMap.Entry<K>> consumer) {
/*  935 */       if (Reference2LongOpenCustomHashMap.this.containsNullKey)
/*  936 */         consumer.accept(new AbstractReference2LongMap.BasicEntry<>(Reference2LongOpenCustomHashMap.this.key[Reference2LongOpenCustomHashMap.this.n], Reference2LongOpenCustomHashMap.this.value[Reference2LongOpenCustomHashMap.this.n])); 
/*  937 */       for (int pos = Reference2LongOpenCustomHashMap.this.n; pos-- != 0;) {
/*  938 */         if (Reference2LongOpenCustomHashMap.this.key[pos] != null)
/*  939 */           consumer.accept(new AbstractReference2LongMap.BasicEntry<>(Reference2LongOpenCustomHashMap.this.key[pos], Reference2LongOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Reference2LongMap.Entry<K>> consumer) {
/*  944 */       AbstractReference2LongMap.BasicEntry<K> entry = new AbstractReference2LongMap.BasicEntry<>();
/*  945 */       if (Reference2LongOpenCustomHashMap.this.containsNullKey) {
/*  946 */         entry.key = Reference2LongOpenCustomHashMap.this.key[Reference2LongOpenCustomHashMap.this.n];
/*  947 */         entry.value = Reference2LongOpenCustomHashMap.this.value[Reference2LongOpenCustomHashMap.this.n];
/*  948 */         consumer.accept(entry);
/*      */       } 
/*  950 */       for (int pos = Reference2LongOpenCustomHashMap.this.n; pos-- != 0;) {
/*  951 */         if (Reference2LongOpenCustomHashMap.this.key[pos] != null) {
/*  952 */           entry.key = Reference2LongOpenCustomHashMap.this.key[pos];
/*  953 */           entry.value = Reference2LongOpenCustomHashMap.this.value[pos];
/*  954 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Reference2LongMap.FastEntrySet<K> reference2LongEntrySet() {
/*  960 */     if (this.entries == null)
/*  961 */       this.entries = new MapEntrySet(); 
/*  962 */     return this.entries;
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
/*      */     implements ObjectIterator<K>
/*      */   {
/*      */     public K next() {
/*  979 */       return Reference2LongOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractReferenceSet<K> { private KeySet() {}
/*      */     
/*      */     public ObjectIterator<K> iterator() {
/*  985 */       return new Reference2LongOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/*  990 */       if (Reference2LongOpenCustomHashMap.this.containsNullKey)
/*  991 */         consumer.accept(Reference2LongOpenCustomHashMap.this.key[Reference2LongOpenCustomHashMap.this.n]); 
/*  992 */       for (int pos = Reference2LongOpenCustomHashMap.this.n; pos-- != 0; ) {
/*  993 */         K k = Reference2LongOpenCustomHashMap.this.key[pos];
/*  994 */         if (k != null)
/*  995 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1000 */       return Reference2LongOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(Object k) {
/* 1004 */       return Reference2LongOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(Object k) {
/* 1008 */       int oldSize = Reference2LongOpenCustomHashMap.this.size;
/* 1009 */       Reference2LongOpenCustomHashMap.this.removeLong(k);
/* 1010 */       return (Reference2LongOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1014 */       Reference2LongOpenCustomHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ReferenceSet<K> keySet() {
/* 1019 */     if (this.keys == null)
/* 1020 */       this.keys = new KeySet(); 
/* 1021 */     return this.keys;
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
/* 1038 */       return Reference2LongOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public LongCollection values() {
/* 1043 */     if (this.values == null)
/* 1044 */       this.values = (LongCollection)new AbstractLongCollection()
/*      */         {
/*      */           public LongIterator iterator() {
/* 1047 */             return new Reference2LongOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1051 */             return Reference2LongOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(long v) {
/* 1055 */             return Reference2LongOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1059 */             Reference2LongOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(LongConsumer consumer)
/*      */           {
/* 1064 */             if (Reference2LongOpenCustomHashMap.this.containsNullKey)
/* 1065 */               consumer.accept(Reference2LongOpenCustomHashMap.this.value[Reference2LongOpenCustomHashMap.this.n]); 
/* 1066 */             for (int pos = Reference2LongOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1067 */               if (Reference2LongOpenCustomHashMap.this.key[pos] != null)
/* 1068 */                 consumer.accept(Reference2LongOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1071 */     return this.values;
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
/* 1088 */     return trim(this.size);
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
/* 1112 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1113 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1114 */       return true; 
/*      */     try {
/* 1116 */       rehash(l);
/* 1117 */     } catch (OutOfMemoryError cantDoIt) {
/* 1118 */       return false;
/*      */     } 
/* 1120 */     return true;
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
/* 1136 */     K[] key = this.key;
/* 1137 */     long[] value = this.value;
/* 1138 */     int mask = newN - 1;
/* 1139 */     K[] newKey = (K[])new Object[newN + 1];
/* 1140 */     long[] newValue = new long[newN + 1];
/* 1141 */     int i = this.n;
/* 1142 */     for (int j = realSize(); j-- != 0; ) {
/* 1143 */       while (key[--i] == null); int pos;
/* 1144 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != null)
/* 1145 */         while (newKey[pos = pos + 1 & mask] != null); 
/* 1146 */       newKey[pos] = key[i];
/* 1147 */       newValue[pos] = value[i];
/*      */     } 
/* 1149 */     newValue[newN] = value[this.n];
/* 1150 */     this.n = newN;
/* 1151 */     this.mask = mask;
/* 1152 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1153 */     this.key = newKey;
/* 1154 */     this.value = newValue;
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
/*      */   public Reference2LongOpenCustomHashMap<K> clone() {
/*      */     Reference2LongOpenCustomHashMap<K> c;
/*      */     try {
/* 1171 */       c = (Reference2LongOpenCustomHashMap<K>)super.clone();
/* 1172 */     } catch (CloneNotSupportedException cantHappen) {
/* 1173 */       throw new InternalError();
/*      */     } 
/* 1175 */     c.keys = null;
/* 1176 */     c.values = null;
/* 1177 */     c.entries = null;
/* 1178 */     c.containsNullKey = this.containsNullKey;
/* 1179 */     c.key = (K[])this.key.clone();
/* 1180 */     c.value = (long[])this.value.clone();
/* 1181 */     c.strategy = this.strategy;
/* 1182 */     return c;
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
/* 1195 */     int h = 0;
/* 1196 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1197 */       while (this.key[i] == null)
/* 1198 */         i++; 
/* 1199 */       if (this != this.key[i])
/* 1200 */         t = this.strategy.hashCode(this.key[i]); 
/* 1201 */       t ^= HashCommon.long2int(this.value[i]);
/* 1202 */       h += t;
/* 1203 */       i++;
/*      */     } 
/*      */     
/* 1206 */     if (this.containsNullKey)
/* 1207 */       h += HashCommon.long2int(this.value[this.n]); 
/* 1208 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1211 */     K[] key = this.key;
/* 1212 */     long[] value = this.value;
/* 1213 */     MapIterator i = new MapIterator();
/* 1214 */     s.defaultWriteObject();
/* 1215 */     for (int j = this.size; j-- != 0; ) {
/* 1216 */       int e = i.nextEntry();
/* 1217 */       s.writeObject(key[e]);
/* 1218 */       s.writeLong(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1223 */     s.defaultReadObject();
/* 1224 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1225 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1226 */     this.mask = this.n - 1;
/* 1227 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1228 */     long[] value = this.value = new long[this.n + 1];
/*      */ 
/*      */     
/* 1231 */     for (int i = this.size; i-- != 0; ) {
/* 1232 */       int pos; K k = (K)s.readObject();
/* 1233 */       long v = s.readLong();
/* 1234 */       if (this.strategy.equals(k, null)) {
/* 1235 */         pos = this.n;
/* 1236 */         this.containsNullKey = true;
/*      */       } else {
/* 1238 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1239 */         while (key[pos] != null)
/* 1240 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1242 */       key[pos] = k;
/* 1243 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Reference2LongOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */