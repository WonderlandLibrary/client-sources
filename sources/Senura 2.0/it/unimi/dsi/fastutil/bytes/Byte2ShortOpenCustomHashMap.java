/*      */ package it.unimi.dsi.fastutil.bytes;
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
/*      */ import java.util.function.IntFunction;
/*      */ import java.util.function.IntUnaryOperator;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Byte2ShortOpenCustomHashMap
/*      */   extends AbstractByte2ShortMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient byte[] key;
/*      */   protected transient short[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected ByteHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Byte2ShortMap.FastEntrySet entries;
/*      */   protected transient ByteSet keys;
/*      */   protected transient ShortCollection values;
/*      */   
/*      */   public Byte2ShortOpenCustomHashMap(int expected, float f, ByteHash.Strategy strategy) {
/*  107 */     this.strategy = strategy;
/*  108 */     if (f <= 0.0F || f > 1.0F)
/*  109 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  110 */     if (expected < 0)
/*  111 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  112 */     this.f = f;
/*  113 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  114 */     this.mask = this.n - 1;
/*  115 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  116 */     this.key = new byte[this.n + 1];
/*  117 */     this.value = new short[this.n + 1];
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
/*      */   public Byte2ShortOpenCustomHashMap(int expected, ByteHash.Strategy strategy) {
/*  129 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2ShortOpenCustomHashMap(ByteHash.Strategy strategy) {
/*  140 */     this(16, 0.75F, strategy);
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
/*      */   public Byte2ShortOpenCustomHashMap(Map<? extends Byte, ? extends Short> m, float f, ByteHash.Strategy strategy) {
/*  154 */     this(m.size(), f, strategy);
/*  155 */     putAll(m);
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
/*      */   public Byte2ShortOpenCustomHashMap(Map<? extends Byte, ? extends Short> m, ByteHash.Strategy strategy) {
/*  168 */     this(m, 0.75F, strategy);
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
/*      */   public Byte2ShortOpenCustomHashMap(Byte2ShortMap m, float f, ByteHash.Strategy strategy) {
/*  182 */     this(m.size(), f, strategy);
/*  183 */     putAll(m);
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
/*      */   public Byte2ShortOpenCustomHashMap(Byte2ShortMap m, ByteHash.Strategy strategy) {
/*  196 */     this(m, 0.75F, strategy);
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
/*      */   public Byte2ShortOpenCustomHashMap(byte[] k, short[] v, float f, ByteHash.Strategy strategy) {
/*  214 */     this(k.length, f, strategy);
/*  215 */     if (k.length != v.length) {
/*  216 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  218 */     for (int i = 0; i < k.length; i++) {
/*  219 */       put(k[i], v[i]);
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
/*      */   
/*      */   public Byte2ShortOpenCustomHashMap(byte[] k, short[] v, ByteHash.Strategy strategy) {
/*  236 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteHash.Strategy strategy() {
/*  244 */     return this.strategy;
/*      */   }
/*      */   private int realSize() {
/*  247 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  250 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  251 */     if (needed > this.n)
/*  252 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  255 */     int needed = (int)Math.min(1073741824L, 
/*  256 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  257 */     if (needed > this.n)
/*  258 */       rehash(needed); 
/*      */   }
/*      */   private short removeEntry(int pos) {
/*  261 */     short oldValue = this.value[pos];
/*  262 */     this.size--;
/*  263 */     shiftKeys(pos);
/*  264 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  265 */       rehash(this.n / 2); 
/*  266 */     return oldValue;
/*      */   }
/*      */   private short removeNullEntry() {
/*  269 */     this.containsNullKey = false;
/*  270 */     short oldValue = this.value[this.n];
/*  271 */     this.size--;
/*  272 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  273 */       rehash(this.n / 2); 
/*  274 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Byte, ? extends Short> m) {
/*  278 */     if (this.f <= 0.5D) {
/*  279 */       ensureCapacity(m.size());
/*      */     } else {
/*  281 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  283 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(byte k) {
/*  287 */     if (this.strategy.equals(k, (byte)0)) {
/*  288 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  290 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  293 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  294 */       return -(pos + 1); 
/*  295 */     if (this.strategy.equals(k, curr)) {
/*  296 */       return pos;
/*      */     }
/*      */     while (true) {
/*  299 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  300 */         return -(pos + 1); 
/*  301 */       if (this.strategy.equals(k, curr))
/*  302 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, byte k, short v) {
/*  306 */     if (pos == this.n)
/*  307 */       this.containsNullKey = true; 
/*  308 */     this.key[pos] = k;
/*  309 */     this.value[pos] = v;
/*  310 */     if (this.size++ >= this.maxFill) {
/*  311 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public short put(byte k, short v) {
/*  317 */     int pos = find(k);
/*  318 */     if (pos < 0) {
/*  319 */       insert(-pos - 1, k, v);
/*  320 */       return this.defRetValue;
/*      */     } 
/*  322 */     short oldValue = this.value[pos];
/*  323 */     this.value[pos] = v;
/*  324 */     return oldValue;
/*      */   }
/*      */   private short addToValue(int pos, short incr) {
/*  327 */     short oldValue = this.value[pos];
/*  328 */     this.value[pos] = (short)(oldValue + incr);
/*  329 */     return oldValue;
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
/*      */   public short addTo(byte k, short incr) {
/*      */     int pos;
/*  349 */     if (this.strategy.equals(k, (byte)0)) {
/*  350 */       if (this.containsNullKey)
/*  351 */         return addToValue(this.n, incr); 
/*  352 */       pos = this.n;
/*  353 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  356 */       byte[] key = this.key;
/*      */       byte curr;
/*  358 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != 0) {
/*      */         
/*  360 */         if (this.strategy.equals(curr, k))
/*  361 */           return addToValue(pos, incr); 
/*  362 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
/*  363 */           if (this.strategy.equals(curr, k))
/*  364 */             return addToValue(pos, incr); 
/*      */         } 
/*      */       } 
/*  367 */     }  this.key[pos] = k;
/*  368 */     this.value[pos] = (short)(this.defRetValue + incr);
/*  369 */     if (this.size++ >= this.maxFill) {
/*  370 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  373 */     return this.defRetValue;
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
/*  386 */     byte[] key = this.key; while (true) {
/*      */       byte curr; int last;
/*  388 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  390 */         if ((curr = key[pos]) == 0) {
/*  391 */           key[last] = 0;
/*      */           return;
/*      */         } 
/*  394 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  395 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  397 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  399 */       key[last] = curr;
/*  400 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public short remove(byte k) {
/*  406 */     if (this.strategy.equals(k, (byte)0)) {
/*  407 */       if (this.containsNullKey)
/*  408 */         return removeNullEntry(); 
/*  409 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  412 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  415 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  416 */       return this.defRetValue; 
/*  417 */     if (this.strategy.equals(k, curr))
/*  418 */       return removeEntry(pos); 
/*      */     while (true) {
/*  420 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  421 */         return this.defRetValue; 
/*  422 */       if (this.strategy.equals(k, curr)) {
/*  423 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public short get(byte k) {
/*  429 */     if (this.strategy.equals(k, (byte)0)) {
/*  430 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  432 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  435 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  436 */       return this.defRetValue; 
/*  437 */     if (this.strategy.equals(k, curr)) {
/*  438 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  441 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  442 */         return this.defRetValue; 
/*  443 */       if (this.strategy.equals(k, curr)) {
/*  444 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(byte k) {
/*  450 */     if (this.strategy.equals(k, (byte)0)) {
/*  451 */       return this.containsNullKey;
/*      */     }
/*  453 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  456 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  457 */       return false; 
/*  458 */     if (this.strategy.equals(k, curr)) {
/*  459 */       return true;
/*      */     }
/*      */     while (true) {
/*  462 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  463 */         return false; 
/*  464 */       if (this.strategy.equals(k, curr))
/*  465 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(short v) {
/*  470 */     short[] value = this.value;
/*  471 */     byte[] key = this.key;
/*  472 */     if (this.containsNullKey && value[this.n] == v)
/*  473 */       return true; 
/*  474 */     for (int i = this.n; i-- != 0;) {
/*  475 */       if (key[i] != 0 && value[i] == v)
/*  476 */         return true; 
/*  477 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short getOrDefault(byte k, short defaultValue) {
/*  483 */     if (this.strategy.equals(k, (byte)0)) {
/*  484 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  486 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  489 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  490 */       return defaultValue; 
/*  491 */     if (this.strategy.equals(k, curr)) {
/*  492 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  495 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  496 */         return defaultValue; 
/*  497 */       if (this.strategy.equals(k, curr)) {
/*  498 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public short putIfAbsent(byte k, short v) {
/*  504 */     int pos = find(k);
/*  505 */     if (pos >= 0)
/*  506 */       return this.value[pos]; 
/*  507 */     insert(-pos - 1, k, v);
/*  508 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(byte k, short v) {
/*  514 */     if (this.strategy.equals(k, (byte)0)) {
/*  515 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  516 */         removeNullEntry();
/*  517 */         return true;
/*      */       } 
/*  519 */       return false;
/*      */     } 
/*      */     
/*  522 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  525 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  526 */       return false; 
/*  527 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  528 */       removeEntry(pos);
/*  529 */       return true;
/*      */     } 
/*      */     while (true) {
/*  532 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  533 */         return false; 
/*  534 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  535 */         removeEntry(pos);
/*  536 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(byte k, short oldValue, short v) {
/*  543 */     int pos = find(k);
/*  544 */     if (pos < 0 || oldValue != this.value[pos])
/*  545 */       return false; 
/*  546 */     this.value[pos] = v;
/*  547 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public short replace(byte k, short v) {
/*  552 */     int pos = find(k);
/*  553 */     if (pos < 0)
/*  554 */       return this.defRetValue; 
/*  555 */     short oldValue = this.value[pos];
/*  556 */     this.value[pos] = v;
/*  557 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public short computeIfAbsent(byte k, IntUnaryOperator mappingFunction) {
/*  562 */     Objects.requireNonNull(mappingFunction);
/*  563 */     int pos = find(k);
/*  564 */     if (pos >= 0)
/*  565 */       return this.value[pos]; 
/*  566 */     short newValue = SafeMath.safeIntToShort(mappingFunction.applyAsInt(k));
/*  567 */     insert(-pos - 1, k, newValue);
/*  568 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short computeIfAbsentNullable(byte k, IntFunction<? extends Short> mappingFunction) {
/*  574 */     Objects.requireNonNull(mappingFunction);
/*  575 */     int pos = find(k);
/*  576 */     if (pos >= 0)
/*  577 */       return this.value[pos]; 
/*  578 */     Short newValue = mappingFunction.apply(k);
/*  579 */     if (newValue == null)
/*  580 */       return this.defRetValue; 
/*  581 */     short v = newValue.shortValue();
/*  582 */     insert(-pos - 1, k, v);
/*  583 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short computeIfPresent(byte k, BiFunction<? super Byte, ? super Short, ? extends Short> remappingFunction) {
/*  589 */     Objects.requireNonNull(remappingFunction);
/*  590 */     int pos = find(k);
/*  591 */     if (pos < 0)
/*  592 */       return this.defRetValue; 
/*  593 */     Short newValue = remappingFunction.apply(Byte.valueOf(k), Short.valueOf(this.value[pos]));
/*  594 */     if (newValue == null) {
/*  595 */       if (this.strategy.equals(k, (byte)0)) {
/*  596 */         removeNullEntry();
/*      */       } else {
/*  598 */         removeEntry(pos);
/*  599 */       }  return this.defRetValue;
/*      */     } 
/*  601 */     this.value[pos] = newValue.shortValue(); return newValue.shortValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short compute(byte k, BiFunction<? super Byte, ? super Short, ? extends Short> remappingFunction) {
/*  607 */     Objects.requireNonNull(remappingFunction);
/*  608 */     int pos = find(k);
/*  609 */     Short newValue = remappingFunction.apply(Byte.valueOf(k), (pos >= 0) ? Short.valueOf(this.value[pos]) : null);
/*  610 */     if (newValue == null) {
/*  611 */       if (pos >= 0)
/*  612 */         if (this.strategy.equals(k, (byte)0)) {
/*  613 */           removeNullEntry();
/*      */         } else {
/*  615 */           removeEntry(pos);
/*      */         }  
/*  617 */       return this.defRetValue;
/*      */     } 
/*  619 */     short newVal = newValue.shortValue();
/*  620 */     if (pos < 0) {
/*  621 */       insert(-pos - 1, k, newVal);
/*  622 */       return newVal;
/*      */     } 
/*  624 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short merge(byte k, short v, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
/*  630 */     Objects.requireNonNull(remappingFunction);
/*  631 */     int pos = find(k);
/*  632 */     if (pos < 0) {
/*  633 */       insert(-pos - 1, k, v);
/*  634 */       return v;
/*      */     } 
/*  636 */     Short newValue = remappingFunction.apply(Short.valueOf(this.value[pos]), Short.valueOf(v));
/*  637 */     if (newValue == null) {
/*  638 */       if (this.strategy.equals(k, (byte)0)) {
/*  639 */         removeNullEntry();
/*      */       } else {
/*  641 */         removeEntry(pos);
/*  642 */       }  return this.defRetValue;
/*      */     } 
/*  644 */     this.value[pos] = newValue.shortValue(); return newValue.shortValue();
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
/*  655 */     if (this.size == 0)
/*      */       return; 
/*  657 */     this.size = 0;
/*  658 */     this.containsNullKey = false;
/*  659 */     Arrays.fill(this.key, (byte)0);
/*      */   }
/*      */   
/*      */   public int size() {
/*  663 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  667 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Byte2ShortMap.Entry, Map.Entry<Byte, Short>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  679 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public byte getByteKey() {
/*  685 */       return Byte2ShortOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public short getShortValue() {
/*  689 */       return Byte2ShortOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public short setValue(short v) {
/*  693 */       short oldValue = Byte2ShortOpenCustomHashMap.this.value[this.index];
/*  694 */       Byte2ShortOpenCustomHashMap.this.value[this.index] = v;
/*  695 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Byte getKey() {
/*  705 */       return Byte.valueOf(Byte2ShortOpenCustomHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Short getValue() {
/*  715 */       return Short.valueOf(Byte2ShortOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Short setValue(Short v) {
/*  725 */       return Short.valueOf(setValue(v.shortValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  730 */       if (!(o instanceof Map.Entry))
/*  731 */         return false; 
/*  732 */       Map.Entry<Byte, Short> e = (Map.Entry<Byte, Short>)o;
/*  733 */       return (Byte2ShortOpenCustomHashMap.this.strategy.equals(Byte2ShortOpenCustomHashMap.this.key[this.index], ((Byte)e.getKey()).byteValue()) && Byte2ShortOpenCustomHashMap.this.value[this.index] == ((Short)e
/*  734 */         .getValue()).shortValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  738 */       return Byte2ShortOpenCustomHashMap.this.strategy.hashCode(Byte2ShortOpenCustomHashMap.this.key[this.index]) ^ Byte2ShortOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/*  742 */       return Byte2ShortOpenCustomHashMap.this.key[this.index] + "=>" + Byte2ShortOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  752 */     int pos = Byte2ShortOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  759 */     int last = -1;
/*      */     
/*  761 */     int c = Byte2ShortOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  765 */     boolean mustReturnNullKey = Byte2ShortOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     ByteArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  772 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  775 */       if (!hasNext())
/*  776 */         throw new NoSuchElementException(); 
/*  777 */       this.c--;
/*  778 */       if (this.mustReturnNullKey) {
/*  779 */         this.mustReturnNullKey = false;
/*  780 */         return this.last = Byte2ShortOpenCustomHashMap.this.n;
/*      */       } 
/*  782 */       byte[] key = Byte2ShortOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  784 */         if (--this.pos < 0) {
/*      */           
/*  786 */           this.last = Integer.MIN_VALUE;
/*  787 */           byte k = this.wrapped.getByte(-this.pos - 1);
/*  788 */           int p = HashCommon.mix(Byte2ShortOpenCustomHashMap.this.strategy.hashCode(k)) & Byte2ShortOpenCustomHashMap.this.mask;
/*  789 */           while (!Byte2ShortOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  790 */             p = p + 1 & Byte2ShortOpenCustomHashMap.this.mask; 
/*  791 */           return p;
/*      */         } 
/*  793 */         if (key[this.pos] != 0) {
/*  794 */           return this.last = this.pos;
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
/*  808 */       byte[] key = Byte2ShortOpenCustomHashMap.this.key; while (true) {
/*      */         byte curr; int last;
/*  810 */         pos = (last = pos) + 1 & Byte2ShortOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  812 */           if ((curr = key[pos]) == 0) {
/*  813 */             key[last] = 0;
/*      */             return;
/*      */           } 
/*  816 */           int slot = HashCommon.mix(Byte2ShortOpenCustomHashMap.this.strategy.hashCode(curr)) & Byte2ShortOpenCustomHashMap.this.mask;
/*  817 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  819 */           pos = pos + 1 & Byte2ShortOpenCustomHashMap.this.mask;
/*      */         } 
/*  821 */         if (pos < last) {
/*  822 */           if (this.wrapped == null)
/*  823 */             this.wrapped = new ByteArrayList(2); 
/*  824 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  826 */         key[last] = curr;
/*  827 */         Byte2ShortOpenCustomHashMap.this.value[last] = Byte2ShortOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  831 */       if (this.last == -1)
/*  832 */         throw new IllegalStateException(); 
/*  833 */       if (this.last == Byte2ShortOpenCustomHashMap.this.n) {
/*  834 */         Byte2ShortOpenCustomHashMap.this.containsNullKey = false;
/*  835 */       } else if (this.pos >= 0) {
/*  836 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  839 */         Byte2ShortOpenCustomHashMap.this.remove(this.wrapped.getByte(-this.pos - 1));
/*  840 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  843 */       Byte2ShortOpenCustomHashMap.this.size--;
/*  844 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  849 */       int i = n;
/*  850 */       while (i-- != 0 && hasNext())
/*  851 */         nextEntry(); 
/*  852 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Byte2ShortMap.Entry> { private Byte2ShortOpenCustomHashMap.MapEntry entry;
/*      */     
/*      */     public Byte2ShortOpenCustomHashMap.MapEntry next() {
/*  859 */       return this.entry = new Byte2ShortOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  863 */       super.remove();
/*  864 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Byte2ShortMap.Entry> { private FastEntryIterator() {
/*  868 */       this.entry = new Byte2ShortOpenCustomHashMap.MapEntry();
/*      */     } private final Byte2ShortOpenCustomHashMap.MapEntry entry;
/*      */     public Byte2ShortOpenCustomHashMap.MapEntry next() {
/*  871 */       this.entry.index = nextEntry();
/*  872 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Byte2ShortMap.Entry> implements Byte2ShortMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Byte2ShortMap.Entry> iterator() {
/*  878 */       return new Byte2ShortOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Byte2ShortMap.Entry> fastIterator() {
/*  882 */       return new Byte2ShortOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  887 */       if (!(o instanceof Map.Entry))
/*  888 */         return false; 
/*  889 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  890 */       if (e.getKey() == null || !(e.getKey() instanceof Byte))
/*  891 */         return false; 
/*  892 */       if (e.getValue() == null || !(e.getValue() instanceof Short))
/*  893 */         return false; 
/*  894 */       byte k = ((Byte)e.getKey()).byteValue();
/*  895 */       short v = ((Short)e.getValue()).shortValue();
/*  896 */       if (Byte2ShortOpenCustomHashMap.this.strategy.equals(k, (byte)0)) {
/*  897 */         return (Byte2ShortOpenCustomHashMap.this.containsNullKey && Byte2ShortOpenCustomHashMap.this.value[Byte2ShortOpenCustomHashMap.this.n] == v);
/*      */       }
/*  899 */       byte[] key = Byte2ShortOpenCustomHashMap.this.key;
/*      */       byte curr;
/*      */       int pos;
/*  902 */       if ((curr = key[pos = HashCommon.mix(Byte2ShortOpenCustomHashMap.this.strategy.hashCode(k)) & Byte2ShortOpenCustomHashMap.this.mask]) == 0)
/*  903 */         return false; 
/*  904 */       if (Byte2ShortOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  905 */         return (Byte2ShortOpenCustomHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  908 */         if ((curr = key[pos = pos + 1 & Byte2ShortOpenCustomHashMap.this.mask]) == 0)
/*  909 */           return false; 
/*  910 */         if (Byte2ShortOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  911 */           return (Byte2ShortOpenCustomHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  917 */       if (!(o instanceof Map.Entry))
/*  918 */         return false; 
/*  919 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  920 */       if (e.getKey() == null || !(e.getKey() instanceof Byte))
/*  921 */         return false; 
/*  922 */       if (e.getValue() == null || !(e.getValue() instanceof Short))
/*  923 */         return false; 
/*  924 */       byte k = ((Byte)e.getKey()).byteValue();
/*  925 */       short v = ((Short)e.getValue()).shortValue();
/*  926 */       if (Byte2ShortOpenCustomHashMap.this.strategy.equals(k, (byte)0)) {
/*  927 */         if (Byte2ShortOpenCustomHashMap.this.containsNullKey && Byte2ShortOpenCustomHashMap.this.value[Byte2ShortOpenCustomHashMap.this.n] == v) {
/*  928 */           Byte2ShortOpenCustomHashMap.this.removeNullEntry();
/*  929 */           return true;
/*      */         } 
/*  931 */         return false;
/*      */       } 
/*      */       
/*  934 */       byte[] key = Byte2ShortOpenCustomHashMap.this.key;
/*      */       byte curr;
/*      */       int pos;
/*  937 */       if ((curr = key[pos = HashCommon.mix(Byte2ShortOpenCustomHashMap.this.strategy.hashCode(k)) & Byte2ShortOpenCustomHashMap.this.mask]) == 0)
/*  938 */         return false; 
/*  939 */       if (Byte2ShortOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  940 */         if (Byte2ShortOpenCustomHashMap.this.value[pos] == v) {
/*  941 */           Byte2ShortOpenCustomHashMap.this.removeEntry(pos);
/*  942 */           return true;
/*      */         } 
/*  944 */         return false;
/*      */       } 
/*      */       while (true) {
/*  947 */         if ((curr = key[pos = pos + 1 & Byte2ShortOpenCustomHashMap.this.mask]) == 0)
/*  948 */           return false; 
/*  949 */         if (Byte2ShortOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  950 */           Byte2ShortOpenCustomHashMap.this.value[pos] == v) {
/*  951 */           Byte2ShortOpenCustomHashMap.this.removeEntry(pos);
/*  952 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  959 */       return Byte2ShortOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  963 */       Byte2ShortOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Byte2ShortMap.Entry> consumer) {
/*  968 */       if (Byte2ShortOpenCustomHashMap.this.containsNullKey)
/*  969 */         consumer.accept(new AbstractByte2ShortMap.BasicEntry(Byte2ShortOpenCustomHashMap.this.key[Byte2ShortOpenCustomHashMap.this.n], Byte2ShortOpenCustomHashMap.this.value[Byte2ShortOpenCustomHashMap.this.n])); 
/*  970 */       for (int pos = Byte2ShortOpenCustomHashMap.this.n; pos-- != 0;) {
/*  971 */         if (Byte2ShortOpenCustomHashMap.this.key[pos] != 0)
/*  972 */           consumer.accept(new AbstractByte2ShortMap.BasicEntry(Byte2ShortOpenCustomHashMap.this.key[pos], Byte2ShortOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Byte2ShortMap.Entry> consumer) {
/*  977 */       AbstractByte2ShortMap.BasicEntry entry = new AbstractByte2ShortMap.BasicEntry();
/*  978 */       if (Byte2ShortOpenCustomHashMap.this.containsNullKey) {
/*  979 */         entry.key = Byte2ShortOpenCustomHashMap.this.key[Byte2ShortOpenCustomHashMap.this.n];
/*  980 */         entry.value = Byte2ShortOpenCustomHashMap.this.value[Byte2ShortOpenCustomHashMap.this.n];
/*  981 */         consumer.accept(entry);
/*      */       } 
/*  983 */       for (int pos = Byte2ShortOpenCustomHashMap.this.n; pos-- != 0;) {
/*  984 */         if (Byte2ShortOpenCustomHashMap.this.key[pos] != 0) {
/*  985 */           entry.key = Byte2ShortOpenCustomHashMap.this.key[pos];
/*  986 */           entry.value = Byte2ShortOpenCustomHashMap.this.value[pos];
/*  987 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Byte2ShortMap.FastEntrySet byte2ShortEntrySet() {
/*  993 */     if (this.entries == null)
/*  994 */       this.entries = new MapEntrySet(); 
/*  995 */     return this.entries;
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
/*      */     implements ByteIterator
/*      */   {
/*      */     public byte nextByte() {
/* 1012 */       return Byte2ShortOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractByteSet { private KeySet() {}
/*      */     
/*      */     public ByteIterator iterator() {
/* 1018 */       return new Byte2ShortOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer consumer) {
/* 1023 */       if (Byte2ShortOpenCustomHashMap.this.containsNullKey)
/* 1024 */         consumer.accept(Byte2ShortOpenCustomHashMap.this.key[Byte2ShortOpenCustomHashMap.this.n]); 
/* 1025 */       for (int pos = Byte2ShortOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1026 */         byte k = Byte2ShortOpenCustomHashMap.this.key[pos];
/* 1027 */         if (k != 0)
/* 1028 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1033 */       return Byte2ShortOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(byte k) {
/* 1037 */       return Byte2ShortOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(byte k) {
/* 1041 */       int oldSize = Byte2ShortOpenCustomHashMap.this.size;
/* 1042 */       Byte2ShortOpenCustomHashMap.this.remove(k);
/* 1043 */       return (Byte2ShortOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1047 */       Byte2ShortOpenCustomHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ByteSet keySet() {
/* 1052 */     if (this.keys == null)
/* 1053 */       this.keys = new KeySet(); 
/* 1054 */     return this.keys;
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
/* 1071 */       return Byte2ShortOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public ShortCollection values() {
/* 1076 */     if (this.values == null)
/* 1077 */       this.values = (ShortCollection)new AbstractShortCollection()
/*      */         {
/*      */           public ShortIterator iterator() {
/* 1080 */             return new Byte2ShortOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1084 */             return Byte2ShortOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(short v) {
/* 1088 */             return Byte2ShortOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1092 */             Byte2ShortOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(IntConsumer consumer)
/*      */           {
/* 1097 */             if (Byte2ShortOpenCustomHashMap.this.containsNullKey)
/* 1098 */               consumer.accept(Byte2ShortOpenCustomHashMap.this.value[Byte2ShortOpenCustomHashMap.this.n]); 
/* 1099 */             for (int pos = Byte2ShortOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1100 */               if (Byte2ShortOpenCustomHashMap.this.key[pos] != 0)
/* 1101 */                 consumer.accept(Byte2ShortOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1104 */     return this.values;
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
/* 1121 */     return trim(this.size);
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
/* 1145 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1146 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1147 */       return true; 
/*      */     try {
/* 1149 */       rehash(l);
/* 1150 */     } catch (OutOfMemoryError cantDoIt) {
/* 1151 */       return false;
/*      */     } 
/* 1153 */     return true;
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
/* 1169 */     byte[] key = this.key;
/* 1170 */     short[] value = this.value;
/* 1171 */     int mask = newN - 1;
/* 1172 */     byte[] newKey = new byte[newN + 1];
/* 1173 */     short[] newValue = new short[newN + 1];
/* 1174 */     int i = this.n;
/* 1175 */     for (int j = realSize(); j-- != 0; ) {
/* 1176 */       while (key[--i] == 0); int pos;
/* 1177 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != 0)
/*      */       {
/* 1179 */         while (newKey[pos = pos + 1 & mask] != 0); } 
/* 1180 */       newKey[pos] = key[i];
/* 1181 */       newValue[pos] = value[i];
/*      */     } 
/* 1183 */     newValue[newN] = value[this.n];
/* 1184 */     this.n = newN;
/* 1185 */     this.mask = mask;
/* 1186 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1187 */     this.key = newKey;
/* 1188 */     this.value = newValue;
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
/*      */   public Byte2ShortOpenCustomHashMap clone() {
/*      */     Byte2ShortOpenCustomHashMap c;
/*      */     try {
/* 1205 */       c = (Byte2ShortOpenCustomHashMap)super.clone();
/* 1206 */     } catch (CloneNotSupportedException cantHappen) {
/* 1207 */       throw new InternalError();
/*      */     } 
/* 1209 */     c.keys = null;
/* 1210 */     c.values = null;
/* 1211 */     c.entries = null;
/* 1212 */     c.containsNullKey = this.containsNullKey;
/* 1213 */     c.key = (byte[])this.key.clone();
/* 1214 */     c.value = (short[])this.value.clone();
/* 1215 */     c.strategy = this.strategy;
/* 1216 */     return c;
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
/* 1229 */     int h = 0;
/* 1230 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1231 */       while (this.key[i] == 0)
/* 1232 */         i++; 
/* 1233 */       t = this.strategy.hashCode(this.key[i]);
/* 1234 */       t ^= this.value[i];
/* 1235 */       h += t;
/* 1236 */       i++;
/*      */     } 
/*      */     
/* 1239 */     if (this.containsNullKey)
/* 1240 */       h += this.value[this.n]; 
/* 1241 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1244 */     byte[] key = this.key;
/* 1245 */     short[] value = this.value;
/* 1246 */     MapIterator i = new MapIterator();
/* 1247 */     s.defaultWriteObject();
/* 1248 */     for (int j = this.size; j-- != 0; ) {
/* 1249 */       int e = i.nextEntry();
/* 1250 */       s.writeByte(key[e]);
/* 1251 */       s.writeShort(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1256 */     s.defaultReadObject();
/* 1257 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1258 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1259 */     this.mask = this.n - 1;
/* 1260 */     byte[] key = this.key = new byte[this.n + 1];
/* 1261 */     short[] value = this.value = new short[this.n + 1];
/*      */ 
/*      */     
/* 1264 */     for (int i = this.size; i-- != 0; ) {
/* 1265 */       int pos; byte k = s.readByte();
/* 1266 */       short v = s.readShort();
/* 1267 */       if (this.strategy.equals(k, (byte)0)) {
/* 1268 */         pos = this.n;
/* 1269 */         this.containsNullKey = true;
/*      */       } else {
/* 1271 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1272 */         while (key[pos] != 0)
/* 1273 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1275 */       key[pos] = k;
/* 1276 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\Byte2ShortOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */