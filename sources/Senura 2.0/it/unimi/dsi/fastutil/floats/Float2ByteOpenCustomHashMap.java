/*      */ package it.unimi.dsi.fastutil.floats;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteCollection;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteIterator;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Float2ByteOpenCustomHashMap
/*      */   extends AbstractFloat2ByteMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient float[] key;
/*      */   protected transient byte[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected FloatHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Float2ByteMap.FastEntrySet entries;
/*      */   protected transient FloatSet keys;
/*      */   protected transient ByteCollection values;
/*      */   
/*      */   public Float2ByteOpenCustomHashMap(int expected, float f, FloatHash.Strategy strategy) {
/*  107 */     this.strategy = strategy;
/*  108 */     if (f <= 0.0F || f > 1.0F)
/*  109 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  110 */     if (expected < 0)
/*  111 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  112 */     this.f = f;
/*  113 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  114 */     this.mask = this.n - 1;
/*  115 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  116 */     this.key = new float[this.n + 1];
/*  117 */     this.value = new byte[this.n + 1];
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
/*      */   public Float2ByteOpenCustomHashMap(int expected, FloatHash.Strategy strategy) {
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
/*      */   public Float2ByteOpenCustomHashMap(FloatHash.Strategy strategy) {
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
/*      */   public Float2ByteOpenCustomHashMap(Map<? extends Float, ? extends Byte> m, float f, FloatHash.Strategy strategy) {
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
/*      */   public Float2ByteOpenCustomHashMap(Map<? extends Float, ? extends Byte> m, FloatHash.Strategy strategy) {
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
/*      */   public Float2ByteOpenCustomHashMap(Float2ByteMap m, float f, FloatHash.Strategy strategy) {
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
/*      */   public Float2ByteOpenCustomHashMap(Float2ByteMap m, FloatHash.Strategy strategy) {
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
/*      */   public Float2ByteOpenCustomHashMap(float[] k, byte[] v, float f, FloatHash.Strategy strategy) {
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
/*      */   public Float2ByteOpenCustomHashMap(float[] k, byte[] v, FloatHash.Strategy strategy) {
/*  236 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FloatHash.Strategy strategy() {
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
/*      */   private byte removeEntry(int pos) {
/*  261 */     byte oldValue = this.value[pos];
/*  262 */     this.size--;
/*  263 */     shiftKeys(pos);
/*  264 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  265 */       rehash(this.n / 2); 
/*  266 */     return oldValue;
/*      */   }
/*      */   private byte removeNullEntry() {
/*  269 */     this.containsNullKey = false;
/*  270 */     byte oldValue = this.value[this.n];
/*  271 */     this.size--;
/*  272 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  273 */       rehash(this.n / 2); 
/*  274 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Float, ? extends Byte> m) {
/*  278 */     if (this.f <= 0.5D) {
/*  279 */       ensureCapacity(m.size());
/*      */     } else {
/*  281 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  283 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(float k) {
/*  287 */     if (this.strategy.equals(k, 0.0F)) {
/*  288 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  290 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  293 */     if (Float.floatToIntBits(
/*  294 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  295 */       return -(pos + 1); 
/*  296 */     if (this.strategy.equals(k, curr)) {
/*  297 */       return pos;
/*      */     }
/*      */     while (true) {
/*  300 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  301 */         return -(pos + 1); 
/*  302 */       if (this.strategy.equals(k, curr))
/*  303 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, float k, byte v) {
/*  307 */     if (pos == this.n)
/*  308 */       this.containsNullKey = true; 
/*  309 */     this.key[pos] = k;
/*  310 */     this.value[pos] = v;
/*  311 */     if (this.size++ >= this.maxFill) {
/*  312 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public byte put(float k, byte v) {
/*  318 */     int pos = find(k);
/*  319 */     if (pos < 0) {
/*  320 */       insert(-pos - 1, k, v);
/*  321 */       return this.defRetValue;
/*      */     } 
/*  323 */     byte oldValue = this.value[pos];
/*  324 */     this.value[pos] = v;
/*  325 */     return oldValue;
/*      */   }
/*      */   private byte addToValue(int pos, byte incr) {
/*  328 */     byte oldValue = this.value[pos];
/*  329 */     this.value[pos] = (byte)(oldValue + incr);
/*  330 */     return oldValue;
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
/*      */   public byte addTo(float k, byte incr) {
/*      */     int pos;
/*  350 */     if (this.strategy.equals(k, 0.0F)) {
/*  351 */       if (this.containsNullKey)
/*  352 */         return addToValue(this.n, incr); 
/*  353 */       pos = this.n;
/*  354 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  357 */       float[] key = this.key;
/*      */       float curr;
/*  359 */       if (Float.floatToIntBits(
/*  360 */           curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != 0) {
/*  361 */         if (this.strategy.equals(curr, k))
/*  362 */           return addToValue(pos, incr); 
/*  363 */         while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) {
/*  364 */           if (this.strategy.equals(curr, k))
/*  365 */             return addToValue(pos, incr); 
/*      */         } 
/*      */       } 
/*  368 */     }  this.key[pos] = k;
/*  369 */     this.value[pos] = (byte)(this.defRetValue + incr);
/*  370 */     if (this.size++ >= this.maxFill) {
/*  371 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  374 */     return this.defRetValue;
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
/*  387 */     float[] key = this.key; while (true) {
/*      */       float curr; int last;
/*  389 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  391 */         if (Float.floatToIntBits(curr = key[pos]) == 0) {
/*  392 */           key[last] = 0.0F;
/*      */           return;
/*      */         } 
/*  395 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  396 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  398 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  400 */       key[last] = curr;
/*  401 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public byte remove(float k) {
/*  407 */     if (this.strategy.equals(k, 0.0F)) {
/*  408 */       if (this.containsNullKey)
/*  409 */         return removeNullEntry(); 
/*  410 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  413 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  416 */     if (Float.floatToIntBits(
/*  417 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  418 */       return this.defRetValue; 
/*  419 */     if (this.strategy.equals(k, curr))
/*  420 */       return removeEntry(pos); 
/*      */     while (true) {
/*  422 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  423 */         return this.defRetValue; 
/*  424 */       if (this.strategy.equals(k, curr)) {
/*  425 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public byte get(float k) {
/*  431 */     if (this.strategy.equals(k, 0.0F)) {
/*  432 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  434 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  437 */     if (Float.floatToIntBits(
/*  438 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  439 */       return this.defRetValue; 
/*  440 */     if (this.strategy.equals(k, curr)) {
/*  441 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  444 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  445 */         return this.defRetValue; 
/*  446 */       if (this.strategy.equals(k, curr)) {
/*  447 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(float k) {
/*  453 */     if (this.strategy.equals(k, 0.0F)) {
/*  454 */       return this.containsNullKey;
/*      */     }
/*  456 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  459 */     if (Float.floatToIntBits(
/*  460 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  461 */       return false; 
/*  462 */     if (this.strategy.equals(k, curr)) {
/*  463 */       return true;
/*      */     }
/*      */     while (true) {
/*  466 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  467 */         return false; 
/*  468 */       if (this.strategy.equals(k, curr))
/*  469 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(byte v) {
/*  474 */     byte[] value = this.value;
/*  475 */     float[] key = this.key;
/*  476 */     if (this.containsNullKey && value[this.n] == v)
/*  477 */       return true; 
/*  478 */     for (int i = this.n; i-- != 0;) {
/*  479 */       if (Float.floatToIntBits(key[i]) != 0 && value[i] == v)
/*  480 */         return true; 
/*  481 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte getOrDefault(float k, byte defaultValue) {
/*  487 */     if (this.strategy.equals(k, 0.0F)) {
/*  488 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  490 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  493 */     if (Float.floatToIntBits(
/*  494 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  495 */       return defaultValue; 
/*  496 */     if (this.strategy.equals(k, curr)) {
/*  497 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  500 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  501 */         return defaultValue; 
/*  502 */       if (this.strategy.equals(k, curr)) {
/*  503 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public byte putIfAbsent(float k, byte v) {
/*  509 */     int pos = find(k);
/*  510 */     if (pos >= 0)
/*  511 */       return this.value[pos]; 
/*  512 */     insert(-pos - 1, k, v);
/*  513 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(float k, byte v) {
/*  519 */     if (this.strategy.equals(k, 0.0F)) {
/*  520 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  521 */         removeNullEntry();
/*  522 */         return true;
/*      */       } 
/*  524 */       return false;
/*      */     } 
/*      */     
/*  527 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  530 */     if (Float.floatToIntBits(
/*  531 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  532 */       return false; 
/*  533 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  534 */       removeEntry(pos);
/*  535 */       return true;
/*      */     } 
/*      */     while (true) {
/*  538 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  539 */         return false; 
/*  540 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  541 */         removeEntry(pos);
/*  542 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(float k, byte oldValue, byte v) {
/*  549 */     int pos = find(k);
/*  550 */     if (pos < 0 || oldValue != this.value[pos])
/*  551 */       return false; 
/*  552 */     this.value[pos] = v;
/*  553 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public byte replace(float k, byte v) {
/*  558 */     int pos = find(k);
/*  559 */     if (pos < 0)
/*  560 */       return this.defRetValue; 
/*  561 */     byte oldValue = this.value[pos];
/*  562 */     this.value[pos] = v;
/*  563 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public byte computeIfAbsent(float k, DoubleToIntFunction mappingFunction) {
/*  568 */     Objects.requireNonNull(mappingFunction);
/*  569 */     int pos = find(k);
/*  570 */     if (pos >= 0)
/*  571 */       return this.value[pos]; 
/*  572 */     byte newValue = SafeMath.safeIntToByte(mappingFunction.applyAsInt(k));
/*  573 */     insert(-pos - 1, k, newValue);
/*  574 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte computeIfAbsentNullable(float k, DoubleFunction<? extends Byte> mappingFunction) {
/*  580 */     Objects.requireNonNull(mappingFunction);
/*  581 */     int pos = find(k);
/*  582 */     if (pos >= 0)
/*  583 */       return this.value[pos]; 
/*  584 */     Byte newValue = mappingFunction.apply(k);
/*  585 */     if (newValue == null)
/*  586 */       return this.defRetValue; 
/*  587 */     byte v = newValue.byteValue();
/*  588 */     insert(-pos - 1, k, v);
/*  589 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte computeIfPresent(float k, BiFunction<? super Float, ? super Byte, ? extends Byte> remappingFunction) {
/*  595 */     Objects.requireNonNull(remappingFunction);
/*  596 */     int pos = find(k);
/*  597 */     if (pos < 0)
/*  598 */       return this.defRetValue; 
/*  599 */     Byte newValue = remappingFunction.apply(Float.valueOf(k), Byte.valueOf(this.value[pos]));
/*  600 */     if (newValue == null) {
/*  601 */       if (this.strategy.equals(k, 0.0F)) {
/*  602 */         removeNullEntry();
/*      */       } else {
/*  604 */         removeEntry(pos);
/*  605 */       }  return this.defRetValue;
/*      */     } 
/*  607 */     this.value[pos] = newValue.byteValue(); return newValue.byteValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte compute(float k, BiFunction<? super Float, ? super Byte, ? extends Byte> remappingFunction) {
/*  613 */     Objects.requireNonNull(remappingFunction);
/*  614 */     int pos = find(k);
/*  615 */     Byte newValue = remappingFunction.apply(Float.valueOf(k), (pos >= 0) ? Byte.valueOf(this.value[pos]) : null);
/*  616 */     if (newValue == null) {
/*  617 */       if (pos >= 0)
/*  618 */         if (this.strategy.equals(k, 0.0F)) {
/*  619 */           removeNullEntry();
/*      */         } else {
/*  621 */           removeEntry(pos);
/*      */         }  
/*  623 */       return this.defRetValue;
/*      */     } 
/*  625 */     byte newVal = newValue.byteValue();
/*  626 */     if (pos < 0) {
/*  627 */       insert(-pos - 1, k, newVal);
/*  628 */       return newVal;
/*      */     } 
/*  630 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte merge(float k, byte v, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
/*  636 */     Objects.requireNonNull(remappingFunction);
/*  637 */     int pos = find(k);
/*  638 */     if (pos < 0) {
/*  639 */       insert(-pos - 1, k, v);
/*  640 */       return v;
/*      */     } 
/*  642 */     Byte newValue = remappingFunction.apply(Byte.valueOf(this.value[pos]), Byte.valueOf(v));
/*  643 */     if (newValue == null) {
/*  644 */       if (this.strategy.equals(k, 0.0F)) {
/*  645 */         removeNullEntry();
/*      */       } else {
/*  647 */         removeEntry(pos);
/*  648 */       }  return this.defRetValue;
/*      */     } 
/*  650 */     this.value[pos] = newValue.byteValue(); return newValue.byteValue();
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
/*  661 */     if (this.size == 0)
/*      */       return; 
/*  663 */     this.size = 0;
/*  664 */     this.containsNullKey = false;
/*  665 */     Arrays.fill(this.key, 0.0F);
/*      */   }
/*      */   
/*      */   public int size() {
/*  669 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  673 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Float2ByteMap.Entry, Map.Entry<Float, Byte>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  685 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public float getFloatKey() {
/*  691 */       return Float2ByteOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public byte getByteValue() {
/*  695 */       return Float2ByteOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public byte setValue(byte v) {
/*  699 */       byte oldValue = Float2ByteOpenCustomHashMap.this.value[this.index];
/*  700 */       Float2ByteOpenCustomHashMap.this.value[this.index] = v;
/*  701 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float getKey() {
/*  711 */       return Float.valueOf(Float2ByteOpenCustomHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Byte getValue() {
/*  721 */       return Byte.valueOf(Float2ByteOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Byte setValue(Byte v) {
/*  731 */       return Byte.valueOf(setValue(v.byteValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  736 */       if (!(o instanceof Map.Entry))
/*  737 */         return false; 
/*  738 */       Map.Entry<Float, Byte> e = (Map.Entry<Float, Byte>)o;
/*  739 */       return (Float2ByteOpenCustomHashMap.this.strategy.equals(Float2ByteOpenCustomHashMap.this.key[this.index], ((Float)e.getKey()).floatValue()) && Float2ByteOpenCustomHashMap.this.value[this.index] == ((Byte)e
/*  740 */         .getValue()).byteValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  744 */       return Float2ByteOpenCustomHashMap.this.strategy.hashCode(Float2ByteOpenCustomHashMap.this.key[this.index]) ^ Float2ByteOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/*  748 */       return Float2ByteOpenCustomHashMap.this.key[this.index] + "=>" + Float2ByteOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  758 */     int pos = Float2ByteOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  765 */     int last = -1;
/*      */     
/*  767 */     int c = Float2ByteOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  771 */     boolean mustReturnNullKey = Float2ByteOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     FloatArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  778 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  781 */       if (!hasNext())
/*  782 */         throw new NoSuchElementException(); 
/*  783 */       this.c--;
/*  784 */       if (this.mustReturnNullKey) {
/*  785 */         this.mustReturnNullKey = false;
/*  786 */         return this.last = Float2ByteOpenCustomHashMap.this.n;
/*      */       } 
/*  788 */       float[] key = Float2ByteOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  790 */         if (--this.pos < 0) {
/*      */           
/*  792 */           this.last = Integer.MIN_VALUE;
/*  793 */           float k = this.wrapped.getFloat(-this.pos - 1);
/*  794 */           int p = HashCommon.mix(Float2ByteOpenCustomHashMap.this.strategy.hashCode(k)) & Float2ByteOpenCustomHashMap.this.mask;
/*  795 */           while (!Float2ByteOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  796 */             p = p + 1 & Float2ByteOpenCustomHashMap.this.mask; 
/*  797 */           return p;
/*      */         } 
/*  799 */         if (Float.floatToIntBits(key[this.pos]) != 0) {
/*  800 */           return this.last = this.pos;
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
/*  814 */       float[] key = Float2ByteOpenCustomHashMap.this.key; while (true) {
/*      */         float curr; int last;
/*  816 */         pos = (last = pos) + 1 & Float2ByteOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  818 */           if (Float.floatToIntBits(curr = key[pos]) == 0) {
/*  819 */             key[last] = 0.0F;
/*      */             return;
/*      */           } 
/*  822 */           int slot = HashCommon.mix(Float2ByteOpenCustomHashMap.this.strategy.hashCode(curr)) & Float2ByteOpenCustomHashMap.this.mask;
/*  823 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  825 */           pos = pos + 1 & Float2ByteOpenCustomHashMap.this.mask;
/*      */         } 
/*  827 */         if (pos < last) {
/*  828 */           if (this.wrapped == null)
/*  829 */             this.wrapped = new FloatArrayList(2); 
/*  830 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  832 */         key[last] = curr;
/*  833 */         Float2ByteOpenCustomHashMap.this.value[last] = Float2ByteOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  837 */       if (this.last == -1)
/*  838 */         throw new IllegalStateException(); 
/*  839 */       if (this.last == Float2ByteOpenCustomHashMap.this.n) {
/*  840 */         Float2ByteOpenCustomHashMap.this.containsNullKey = false;
/*  841 */       } else if (this.pos >= 0) {
/*  842 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  845 */         Float2ByteOpenCustomHashMap.this.remove(this.wrapped.getFloat(-this.pos - 1));
/*  846 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  849 */       Float2ByteOpenCustomHashMap.this.size--;
/*  850 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  855 */       int i = n;
/*  856 */       while (i-- != 0 && hasNext())
/*  857 */         nextEntry(); 
/*  858 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Float2ByteMap.Entry> { private Float2ByteOpenCustomHashMap.MapEntry entry;
/*      */     
/*      */     public Float2ByteOpenCustomHashMap.MapEntry next() {
/*  865 */       return this.entry = new Float2ByteOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  869 */       super.remove();
/*  870 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Float2ByteMap.Entry> { private FastEntryIterator() {
/*  874 */       this.entry = new Float2ByteOpenCustomHashMap.MapEntry();
/*      */     } private final Float2ByteOpenCustomHashMap.MapEntry entry;
/*      */     public Float2ByteOpenCustomHashMap.MapEntry next() {
/*  877 */       this.entry.index = nextEntry();
/*  878 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Float2ByteMap.Entry> implements Float2ByteMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Float2ByteMap.Entry> iterator() {
/*  884 */       return new Float2ByteOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Float2ByteMap.Entry> fastIterator() {
/*  888 */       return new Float2ByteOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  893 */       if (!(o instanceof Map.Entry))
/*  894 */         return false; 
/*  895 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  896 */       if (e.getKey() == null || !(e.getKey() instanceof Float))
/*  897 */         return false; 
/*  898 */       if (e.getValue() == null || !(e.getValue() instanceof Byte))
/*  899 */         return false; 
/*  900 */       float k = ((Float)e.getKey()).floatValue();
/*  901 */       byte v = ((Byte)e.getValue()).byteValue();
/*  902 */       if (Float2ByteOpenCustomHashMap.this.strategy.equals(k, 0.0F)) {
/*  903 */         return (Float2ByteOpenCustomHashMap.this.containsNullKey && Float2ByteOpenCustomHashMap.this.value[Float2ByteOpenCustomHashMap.this.n] == v);
/*      */       }
/*  905 */       float[] key = Float2ByteOpenCustomHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/*  908 */       if (Float.floatToIntBits(
/*  909 */           curr = key[pos = HashCommon.mix(Float2ByteOpenCustomHashMap.this.strategy.hashCode(k)) & Float2ByteOpenCustomHashMap.this.mask]) == 0)
/*  910 */         return false; 
/*  911 */       if (Float2ByteOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  912 */         return (Float2ByteOpenCustomHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  915 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2ByteOpenCustomHashMap.this.mask]) == 0)
/*  916 */           return false; 
/*  917 */         if (Float2ByteOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  918 */           return (Float2ByteOpenCustomHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  924 */       if (!(o instanceof Map.Entry))
/*  925 */         return false; 
/*  926 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  927 */       if (e.getKey() == null || !(e.getKey() instanceof Float))
/*  928 */         return false; 
/*  929 */       if (e.getValue() == null || !(e.getValue() instanceof Byte))
/*  930 */         return false; 
/*  931 */       float k = ((Float)e.getKey()).floatValue();
/*  932 */       byte v = ((Byte)e.getValue()).byteValue();
/*  933 */       if (Float2ByteOpenCustomHashMap.this.strategy.equals(k, 0.0F)) {
/*  934 */         if (Float2ByteOpenCustomHashMap.this.containsNullKey && Float2ByteOpenCustomHashMap.this.value[Float2ByteOpenCustomHashMap.this.n] == v) {
/*  935 */           Float2ByteOpenCustomHashMap.this.removeNullEntry();
/*  936 */           return true;
/*      */         } 
/*  938 */         return false;
/*      */       } 
/*      */       
/*  941 */       float[] key = Float2ByteOpenCustomHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/*  944 */       if (Float.floatToIntBits(
/*  945 */           curr = key[pos = HashCommon.mix(Float2ByteOpenCustomHashMap.this.strategy.hashCode(k)) & Float2ByteOpenCustomHashMap.this.mask]) == 0)
/*  946 */         return false; 
/*  947 */       if (Float2ByteOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  948 */         if (Float2ByteOpenCustomHashMap.this.value[pos] == v) {
/*  949 */           Float2ByteOpenCustomHashMap.this.removeEntry(pos);
/*  950 */           return true;
/*      */         } 
/*  952 */         return false;
/*      */       } 
/*      */       while (true) {
/*  955 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2ByteOpenCustomHashMap.this.mask]) == 0)
/*  956 */           return false; 
/*  957 */         if (Float2ByteOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  958 */           Float2ByteOpenCustomHashMap.this.value[pos] == v) {
/*  959 */           Float2ByteOpenCustomHashMap.this.removeEntry(pos);
/*  960 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  967 */       return Float2ByteOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  971 */       Float2ByteOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Float2ByteMap.Entry> consumer) {
/*  976 */       if (Float2ByteOpenCustomHashMap.this.containsNullKey)
/*  977 */         consumer.accept(new AbstractFloat2ByteMap.BasicEntry(Float2ByteOpenCustomHashMap.this.key[Float2ByteOpenCustomHashMap.this.n], Float2ByteOpenCustomHashMap.this.value[Float2ByteOpenCustomHashMap.this.n])); 
/*  978 */       for (int pos = Float2ByteOpenCustomHashMap.this.n; pos-- != 0;) {
/*  979 */         if (Float.floatToIntBits(Float2ByteOpenCustomHashMap.this.key[pos]) != 0)
/*  980 */           consumer.accept(new AbstractFloat2ByteMap.BasicEntry(Float2ByteOpenCustomHashMap.this.key[pos], Float2ByteOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Float2ByteMap.Entry> consumer) {
/*  985 */       AbstractFloat2ByteMap.BasicEntry entry = new AbstractFloat2ByteMap.BasicEntry();
/*  986 */       if (Float2ByteOpenCustomHashMap.this.containsNullKey) {
/*  987 */         entry.key = Float2ByteOpenCustomHashMap.this.key[Float2ByteOpenCustomHashMap.this.n];
/*  988 */         entry.value = Float2ByteOpenCustomHashMap.this.value[Float2ByteOpenCustomHashMap.this.n];
/*  989 */         consumer.accept(entry);
/*      */       } 
/*  991 */       for (int pos = Float2ByteOpenCustomHashMap.this.n; pos-- != 0;) {
/*  992 */         if (Float.floatToIntBits(Float2ByteOpenCustomHashMap.this.key[pos]) != 0) {
/*  993 */           entry.key = Float2ByteOpenCustomHashMap.this.key[pos];
/*  994 */           entry.value = Float2ByteOpenCustomHashMap.this.value[pos];
/*  995 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Float2ByteMap.FastEntrySet float2ByteEntrySet() {
/* 1001 */     if (this.entries == null)
/* 1002 */       this.entries = new MapEntrySet(); 
/* 1003 */     return this.entries;
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
/* 1020 */       return Float2ByteOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractFloatSet { private KeySet() {}
/*      */     
/*      */     public FloatIterator iterator() {
/* 1026 */       return new Float2ByteOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/* 1031 */       if (Float2ByteOpenCustomHashMap.this.containsNullKey)
/* 1032 */         consumer.accept(Float2ByteOpenCustomHashMap.this.key[Float2ByteOpenCustomHashMap.this.n]); 
/* 1033 */       for (int pos = Float2ByteOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1034 */         float k = Float2ByteOpenCustomHashMap.this.key[pos];
/* 1035 */         if (Float.floatToIntBits(k) != 0)
/* 1036 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1041 */       return Float2ByteOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(float k) {
/* 1045 */       return Float2ByteOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(float k) {
/* 1049 */       int oldSize = Float2ByteOpenCustomHashMap.this.size;
/* 1050 */       Float2ByteOpenCustomHashMap.this.remove(k);
/* 1051 */       return (Float2ByteOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1055 */       Float2ByteOpenCustomHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public FloatSet keySet() {
/* 1060 */     if (this.keys == null)
/* 1061 */       this.keys = new KeySet(); 
/* 1062 */     return this.keys;
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
/*      */     implements ByteIterator
/*      */   {
/*      */     public byte nextByte() {
/* 1079 */       return Float2ByteOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public ByteCollection values() {
/* 1084 */     if (this.values == null)
/* 1085 */       this.values = (ByteCollection)new AbstractByteCollection()
/*      */         {
/*      */           public ByteIterator iterator() {
/* 1088 */             return new Float2ByteOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1092 */             return Float2ByteOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(byte v) {
/* 1096 */             return Float2ByteOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1100 */             Float2ByteOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(IntConsumer consumer)
/*      */           {
/* 1105 */             if (Float2ByteOpenCustomHashMap.this.containsNullKey)
/* 1106 */               consumer.accept(Float2ByteOpenCustomHashMap.this.value[Float2ByteOpenCustomHashMap.this.n]); 
/* 1107 */             for (int pos = Float2ByteOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1108 */               if (Float.floatToIntBits(Float2ByteOpenCustomHashMap.this.key[pos]) != 0)
/* 1109 */                 consumer.accept(Float2ByteOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1112 */     return this.values;
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
/* 1129 */     return trim(this.size);
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
/* 1153 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1154 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1155 */       return true; 
/*      */     try {
/* 1157 */       rehash(l);
/* 1158 */     } catch (OutOfMemoryError cantDoIt) {
/* 1159 */       return false;
/*      */     } 
/* 1161 */     return true;
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
/* 1177 */     float[] key = this.key;
/* 1178 */     byte[] value = this.value;
/* 1179 */     int mask = newN - 1;
/* 1180 */     float[] newKey = new float[newN + 1];
/* 1181 */     byte[] newValue = new byte[newN + 1];
/* 1182 */     int i = this.n;
/* 1183 */     for (int j = realSize(); j-- != 0; ) {
/* 1184 */       while (Float.floatToIntBits(key[--i]) == 0); int pos;
/* 1185 */       if (Float.floatToIntBits(newKey[
/* 1186 */             pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask]) != 0)
/* 1187 */         while (Float.floatToIntBits(newKey[pos = pos + 1 & mask]) != 0); 
/* 1188 */       newKey[pos] = key[i];
/* 1189 */       newValue[pos] = value[i];
/*      */     } 
/* 1191 */     newValue[newN] = value[this.n];
/* 1192 */     this.n = newN;
/* 1193 */     this.mask = mask;
/* 1194 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1195 */     this.key = newKey;
/* 1196 */     this.value = newValue;
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
/*      */   public Float2ByteOpenCustomHashMap clone() {
/*      */     Float2ByteOpenCustomHashMap c;
/*      */     try {
/* 1213 */       c = (Float2ByteOpenCustomHashMap)super.clone();
/* 1214 */     } catch (CloneNotSupportedException cantHappen) {
/* 1215 */       throw new InternalError();
/*      */     } 
/* 1217 */     c.keys = null;
/* 1218 */     c.values = null;
/* 1219 */     c.entries = null;
/* 1220 */     c.containsNullKey = this.containsNullKey;
/* 1221 */     c.key = (float[])this.key.clone();
/* 1222 */     c.value = (byte[])this.value.clone();
/* 1223 */     c.strategy = this.strategy;
/* 1224 */     return c;
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
/* 1237 */     int h = 0;
/* 1238 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1239 */       while (Float.floatToIntBits(this.key[i]) == 0)
/* 1240 */         i++; 
/* 1241 */       t = this.strategy.hashCode(this.key[i]);
/* 1242 */       t ^= this.value[i];
/* 1243 */       h += t;
/* 1244 */       i++;
/*      */     } 
/*      */     
/* 1247 */     if (this.containsNullKey)
/* 1248 */       h += this.value[this.n]; 
/* 1249 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1252 */     float[] key = this.key;
/* 1253 */     byte[] value = this.value;
/* 1254 */     MapIterator i = new MapIterator();
/* 1255 */     s.defaultWriteObject();
/* 1256 */     for (int j = this.size; j-- != 0; ) {
/* 1257 */       int e = i.nextEntry();
/* 1258 */       s.writeFloat(key[e]);
/* 1259 */       s.writeByte(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1264 */     s.defaultReadObject();
/* 1265 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1266 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1267 */     this.mask = this.n - 1;
/* 1268 */     float[] key = this.key = new float[this.n + 1];
/* 1269 */     byte[] value = this.value = new byte[this.n + 1];
/*      */ 
/*      */     
/* 1272 */     for (int i = this.size; i-- != 0; ) {
/* 1273 */       int pos; float k = s.readFloat();
/* 1274 */       byte v = s.readByte();
/* 1275 */       if (this.strategy.equals(k, 0.0F)) {
/* 1276 */         pos = this.n;
/* 1277 */         this.containsNullKey = true;
/*      */       } else {
/* 1279 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1280 */         while (Float.floatToIntBits(key[pos]) != 0)
/* 1281 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1283 */       key[pos] = k;
/* 1284 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\Float2ByteOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */