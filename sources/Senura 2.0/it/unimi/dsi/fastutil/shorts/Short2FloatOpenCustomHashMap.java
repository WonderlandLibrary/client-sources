/*      */ package it.unimi.dsi.fastutil.shorts;
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
/*      */ import java.util.function.IntConsumer;
/*      */ import java.util.function.IntFunction;
/*      */ import java.util.function.IntToDoubleFunction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Short2FloatOpenCustomHashMap
/*      */   extends AbstractShort2FloatMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient short[] key;
/*      */   protected transient float[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected ShortHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Short2FloatMap.FastEntrySet entries;
/*      */   protected transient ShortSet keys;
/*      */   protected transient FloatCollection values;
/*      */   
/*      */   public Short2FloatOpenCustomHashMap(int expected, float f, ShortHash.Strategy strategy) {
/*  107 */     this.strategy = strategy;
/*  108 */     if (f <= 0.0F || f > 1.0F)
/*  109 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  110 */     if (expected < 0)
/*  111 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  112 */     this.f = f;
/*  113 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  114 */     this.mask = this.n - 1;
/*  115 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  116 */     this.key = new short[this.n + 1];
/*  117 */     this.value = new float[this.n + 1];
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
/*      */   public Short2FloatOpenCustomHashMap(int expected, ShortHash.Strategy strategy) {
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
/*      */   public Short2FloatOpenCustomHashMap(ShortHash.Strategy strategy) {
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
/*      */   public Short2FloatOpenCustomHashMap(Map<? extends Short, ? extends Float> m, float f, ShortHash.Strategy strategy) {
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
/*      */   public Short2FloatOpenCustomHashMap(Map<? extends Short, ? extends Float> m, ShortHash.Strategy strategy) {
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
/*      */   public Short2FloatOpenCustomHashMap(Short2FloatMap m, float f, ShortHash.Strategy strategy) {
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
/*      */   public Short2FloatOpenCustomHashMap(Short2FloatMap m, ShortHash.Strategy strategy) {
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
/*      */   public Short2FloatOpenCustomHashMap(short[] k, float[] v, float f, ShortHash.Strategy strategy) {
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
/*      */   public Short2FloatOpenCustomHashMap(short[] k, float[] v, ShortHash.Strategy strategy) {
/*  236 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ShortHash.Strategy strategy() {
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
/*      */   private float removeEntry(int pos) {
/*  261 */     float oldValue = this.value[pos];
/*  262 */     this.size--;
/*  263 */     shiftKeys(pos);
/*  264 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  265 */       rehash(this.n / 2); 
/*  266 */     return oldValue;
/*      */   }
/*      */   private float removeNullEntry() {
/*  269 */     this.containsNullKey = false;
/*  270 */     float oldValue = this.value[this.n];
/*  271 */     this.size--;
/*  272 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  273 */       rehash(this.n / 2); 
/*  274 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Short, ? extends Float> m) {
/*  278 */     if (this.f <= 0.5D) {
/*  279 */       ensureCapacity(m.size());
/*      */     } else {
/*  281 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  283 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(short k) {
/*  287 */     if (this.strategy.equals(k, (short)0)) {
/*  288 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  290 */     short[] key = this.key;
/*      */     short curr;
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
/*      */   private void insert(int pos, short k, float v) {
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
/*      */   public float put(short k, float v) {
/*  317 */     int pos = find(k);
/*  318 */     if (pos < 0) {
/*  319 */       insert(-pos - 1, k, v);
/*  320 */       return this.defRetValue;
/*      */     } 
/*  322 */     float oldValue = this.value[pos];
/*  323 */     this.value[pos] = v;
/*  324 */     return oldValue;
/*      */   }
/*      */   private float addToValue(int pos, float incr) {
/*  327 */     float oldValue = this.value[pos];
/*  328 */     this.value[pos] = oldValue + incr;
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
/*      */   public float addTo(short k, float incr) {
/*      */     int pos;
/*  349 */     if (this.strategy.equals(k, (short)0)) {
/*  350 */       if (this.containsNullKey)
/*  351 */         return addToValue(this.n, incr); 
/*  352 */       pos = this.n;
/*  353 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  356 */       short[] key = this.key;
/*      */       short curr;
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
/*  368 */     this.value[pos] = this.defRetValue + incr;
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
/*  386 */     short[] key = this.key; while (true) {
/*      */       short curr; int last;
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
/*      */   public float remove(short k) {
/*  406 */     if (this.strategy.equals(k, (short)0)) {
/*  407 */       if (this.containsNullKey)
/*  408 */         return removeNullEntry(); 
/*  409 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  412 */     short[] key = this.key;
/*      */     short curr;
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
/*      */   public float get(short k) {
/*  429 */     if (this.strategy.equals(k, (short)0)) {
/*  430 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  432 */     short[] key = this.key;
/*      */     short curr;
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
/*      */   public boolean containsKey(short k) {
/*  450 */     if (this.strategy.equals(k, (short)0)) {
/*  451 */       return this.containsNullKey;
/*      */     }
/*  453 */     short[] key = this.key;
/*      */     short curr;
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
/*      */   public boolean containsValue(float v) {
/*  470 */     float[] value = this.value;
/*  471 */     short[] key = this.key;
/*  472 */     if (this.containsNullKey && Float.floatToIntBits(value[this.n]) == Float.floatToIntBits(v))
/*  473 */       return true; 
/*  474 */     for (int i = this.n; i-- != 0;) {
/*  475 */       if (key[i] != 0 && Float.floatToIntBits(value[i]) == Float.floatToIntBits(v))
/*  476 */         return true; 
/*  477 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float getOrDefault(short k, float defaultValue) {
/*  483 */     if (this.strategy.equals(k, (short)0)) {
/*  484 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  486 */     short[] key = this.key;
/*      */     short curr;
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
/*      */   public float putIfAbsent(short k, float v) {
/*  504 */     int pos = find(k);
/*  505 */     if (pos >= 0)
/*  506 */       return this.value[pos]; 
/*  507 */     insert(-pos - 1, k, v);
/*  508 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(short k, float v) {
/*  514 */     if (this.strategy.equals(k, (short)0)) {
/*  515 */       if (this.containsNullKey && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[this.n])) {
/*  516 */         removeNullEntry();
/*  517 */         return true;
/*      */       } 
/*  519 */       return false;
/*      */     } 
/*      */     
/*  522 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  525 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  526 */       return false; 
/*  527 */     if (this.strategy.equals(k, curr) && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[pos])) {
/*  528 */       removeEntry(pos);
/*  529 */       return true;
/*      */     } 
/*      */     while (true) {
/*  532 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  533 */         return false; 
/*  534 */       if (this.strategy.equals(k, curr) && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[pos])) {
/*  535 */         removeEntry(pos);
/*  536 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(short k, float oldValue, float v) {
/*  543 */     int pos = find(k);
/*  544 */     if (pos < 0 || Float.floatToIntBits(oldValue) != Float.floatToIntBits(this.value[pos]))
/*  545 */       return false; 
/*  546 */     this.value[pos] = v;
/*  547 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public float replace(short k, float v) {
/*  552 */     int pos = find(k);
/*  553 */     if (pos < 0)
/*  554 */       return this.defRetValue; 
/*  555 */     float oldValue = this.value[pos];
/*  556 */     this.value[pos] = v;
/*  557 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public float computeIfAbsent(short k, IntToDoubleFunction mappingFunction) {
/*  562 */     Objects.requireNonNull(mappingFunction);
/*  563 */     int pos = find(k);
/*  564 */     if (pos >= 0)
/*  565 */       return this.value[pos]; 
/*  566 */     float newValue = SafeMath.safeDoubleToFloat(mappingFunction.applyAsDouble(k));
/*  567 */     insert(-pos - 1, k, newValue);
/*  568 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float computeIfAbsentNullable(short k, IntFunction<? extends Float> mappingFunction) {
/*  574 */     Objects.requireNonNull(mappingFunction);
/*  575 */     int pos = find(k);
/*  576 */     if (pos >= 0)
/*  577 */       return this.value[pos]; 
/*  578 */     Float newValue = mappingFunction.apply(k);
/*  579 */     if (newValue == null)
/*  580 */       return this.defRetValue; 
/*  581 */     float v = newValue.floatValue();
/*  582 */     insert(-pos - 1, k, v);
/*  583 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float computeIfPresent(short k, BiFunction<? super Short, ? super Float, ? extends Float> remappingFunction) {
/*  589 */     Objects.requireNonNull(remappingFunction);
/*  590 */     int pos = find(k);
/*  591 */     if (pos < 0)
/*  592 */       return this.defRetValue; 
/*  593 */     Float newValue = remappingFunction.apply(Short.valueOf(k), Float.valueOf(this.value[pos]));
/*  594 */     if (newValue == null) {
/*  595 */       if (this.strategy.equals(k, (short)0)) {
/*  596 */         removeNullEntry();
/*      */       } else {
/*  598 */         removeEntry(pos);
/*  599 */       }  return this.defRetValue;
/*      */     } 
/*  601 */     this.value[pos] = newValue.floatValue(); return newValue.floatValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float compute(short k, BiFunction<? super Short, ? super Float, ? extends Float> remappingFunction) {
/*  607 */     Objects.requireNonNull(remappingFunction);
/*  608 */     int pos = find(k);
/*  609 */     Float newValue = remappingFunction.apply(Short.valueOf(k), (pos >= 0) ? Float.valueOf(this.value[pos]) : null);
/*  610 */     if (newValue == null) {
/*  611 */       if (pos >= 0)
/*  612 */         if (this.strategy.equals(k, (short)0)) {
/*  613 */           removeNullEntry();
/*      */         } else {
/*  615 */           removeEntry(pos);
/*      */         }  
/*  617 */       return this.defRetValue;
/*      */     } 
/*  619 */     float newVal = newValue.floatValue();
/*  620 */     if (pos < 0) {
/*  621 */       insert(-pos - 1, k, newVal);
/*  622 */       return newVal;
/*      */     } 
/*  624 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float merge(short k, float v, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/*  630 */     Objects.requireNonNull(remappingFunction);
/*  631 */     int pos = find(k);
/*  632 */     if (pos < 0) {
/*  633 */       insert(-pos - 1, k, v);
/*  634 */       return v;
/*      */     } 
/*  636 */     Float newValue = remappingFunction.apply(Float.valueOf(this.value[pos]), Float.valueOf(v));
/*  637 */     if (newValue == null) {
/*  638 */       if (this.strategy.equals(k, (short)0)) {
/*  639 */         removeNullEntry();
/*      */       } else {
/*  641 */         removeEntry(pos);
/*  642 */       }  return this.defRetValue;
/*      */     } 
/*  644 */     this.value[pos] = newValue.floatValue(); return newValue.floatValue();
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
/*  659 */     Arrays.fill(this.key, (short)0);
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
/*      */     implements Short2FloatMap.Entry, Map.Entry<Short, Float>
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
/*      */     public short getShortKey() {
/*  685 */       return Short2FloatOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public float getFloatValue() {
/*  689 */       return Short2FloatOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public float setValue(float v) {
/*  693 */       float oldValue = Short2FloatOpenCustomHashMap.this.value[this.index];
/*  694 */       Short2FloatOpenCustomHashMap.this.value[this.index] = v;
/*  695 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Short getKey() {
/*  705 */       return Short.valueOf(Short2FloatOpenCustomHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float getValue() {
/*  715 */       return Float.valueOf(Short2FloatOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float setValue(Float v) {
/*  725 */       return Float.valueOf(setValue(v.floatValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  730 */       if (!(o instanceof Map.Entry))
/*  731 */         return false; 
/*  732 */       Map.Entry<Short, Float> e = (Map.Entry<Short, Float>)o;
/*  733 */       return (Short2FloatOpenCustomHashMap.this.strategy.equals(Short2FloatOpenCustomHashMap.this.key[this.index], ((Short)e.getKey()).shortValue()) && 
/*  734 */         Float.floatToIntBits(Short2FloatOpenCustomHashMap.this.value[this.index]) == Float.floatToIntBits(((Float)e.getValue()).floatValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  738 */       return Short2FloatOpenCustomHashMap.this.strategy.hashCode(Short2FloatOpenCustomHashMap.this.key[this.index]) ^ HashCommon.float2int(Short2FloatOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  742 */       return Short2FloatOpenCustomHashMap.this.key[this.index] + "=>" + Short2FloatOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  752 */     int pos = Short2FloatOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  759 */     int last = -1;
/*      */     
/*  761 */     int c = Short2FloatOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  765 */     boolean mustReturnNullKey = Short2FloatOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     ShortArrayList wrapped;
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
/*  780 */         return this.last = Short2FloatOpenCustomHashMap.this.n;
/*      */       } 
/*  782 */       short[] key = Short2FloatOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  784 */         if (--this.pos < 0) {
/*      */           
/*  786 */           this.last = Integer.MIN_VALUE;
/*  787 */           short k = this.wrapped.getShort(-this.pos - 1);
/*  788 */           int p = HashCommon.mix(Short2FloatOpenCustomHashMap.this.strategy.hashCode(k)) & Short2FloatOpenCustomHashMap.this.mask;
/*  789 */           while (!Short2FloatOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  790 */             p = p + 1 & Short2FloatOpenCustomHashMap.this.mask; 
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
/*  808 */       short[] key = Short2FloatOpenCustomHashMap.this.key; while (true) {
/*      */         short curr; int last;
/*  810 */         pos = (last = pos) + 1 & Short2FloatOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  812 */           if ((curr = key[pos]) == 0) {
/*  813 */             key[last] = 0;
/*      */             return;
/*      */           } 
/*  816 */           int slot = HashCommon.mix(Short2FloatOpenCustomHashMap.this.strategy.hashCode(curr)) & Short2FloatOpenCustomHashMap.this.mask;
/*  817 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  819 */           pos = pos + 1 & Short2FloatOpenCustomHashMap.this.mask;
/*      */         } 
/*  821 */         if (pos < last) {
/*  822 */           if (this.wrapped == null)
/*  823 */             this.wrapped = new ShortArrayList(2); 
/*  824 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  826 */         key[last] = curr;
/*  827 */         Short2FloatOpenCustomHashMap.this.value[last] = Short2FloatOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  831 */       if (this.last == -1)
/*  832 */         throw new IllegalStateException(); 
/*  833 */       if (this.last == Short2FloatOpenCustomHashMap.this.n) {
/*  834 */         Short2FloatOpenCustomHashMap.this.containsNullKey = false;
/*  835 */       } else if (this.pos >= 0) {
/*  836 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  839 */         Short2FloatOpenCustomHashMap.this.remove(this.wrapped.getShort(-this.pos - 1));
/*  840 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  843 */       Short2FloatOpenCustomHashMap.this.size--;
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
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Short2FloatMap.Entry> { private Short2FloatOpenCustomHashMap.MapEntry entry;
/*      */     
/*      */     public Short2FloatOpenCustomHashMap.MapEntry next() {
/*  859 */       return this.entry = new Short2FloatOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  863 */       super.remove();
/*  864 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Short2FloatMap.Entry> { private FastEntryIterator() {
/*  868 */       this.entry = new Short2FloatOpenCustomHashMap.MapEntry();
/*      */     } private final Short2FloatOpenCustomHashMap.MapEntry entry;
/*      */     public Short2FloatOpenCustomHashMap.MapEntry next() {
/*  871 */       this.entry.index = nextEntry();
/*  872 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Short2FloatMap.Entry> implements Short2FloatMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Short2FloatMap.Entry> iterator() {
/*  878 */       return new Short2FloatOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Short2FloatMap.Entry> fastIterator() {
/*  882 */       return new Short2FloatOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  887 */       if (!(o instanceof Map.Entry))
/*  888 */         return false; 
/*  889 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  890 */       if (e.getKey() == null || !(e.getKey() instanceof Short))
/*  891 */         return false; 
/*  892 */       if (e.getValue() == null || !(e.getValue() instanceof Float))
/*  893 */         return false; 
/*  894 */       short k = ((Short)e.getKey()).shortValue();
/*  895 */       float v = ((Float)e.getValue()).floatValue();
/*  896 */       if (Short2FloatOpenCustomHashMap.this.strategy.equals(k, (short)0)) {
/*  897 */         return (Short2FloatOpenCustomHashMap.this.containsNullKey && 
/*  898 */           Float.floatToIntBits(Short2FloatOpenCustomHashMap.this.value[Short2FloatOpenCustomHashMap.this.n]) == Float.floatToIntBits(v));
/*      */       }
/*  900 */       short[] key = Short2FloatOpenCustomHashMap.this.key;
/*      */       short curr;
/*      */       int pos;
/*  903 */       if ((curr = key[pos = HashCommon.mix(Short2FloatOpenCustomHashMap.this.strategy.hashCode(k)) & Short2FloatOpenCustomHashMap.this.mask]) == 0)
/*      */       {
/*  905 */         return false; } 
/*  906 */       if (Short2FloatOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  907 */         return (Float.floatToIntBits(Short2FloatOpenCustomHashMap.this.value[pos]) == Float.floatToIntBits(v));
/*      */       }
/*      */       while (true) {
/*  910 */         if ((curr = key[pos = pos + 1 & Short2FloatOpenCustomHashMap.this.mask]) == 0)
/*  911 */           return false; 
/*  912 */         if (Short2FloatOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  913 */           return (Float.floatToIntBits(Short2FloatOpenCustomHashMap.this.value[pos]) == Float.floatToIntBits(v));
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  919 */       if (!(o instanceof Map.Entry))
/*  920 */         return false; 
/*  921 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  922 */       if (e.getKey() == null || !(e.getKey() instanceof Short))
/*  923 */         return false; 
/*  924 */       if (e.getValue() == null || !(e.getValue() instanceof Float))
/*  925 */         return false; 
/*  926 */       short k = ((Short)e.getKey()).shortValue();
/*  927 */       float v = ((Float)e.getValue()).floatValue();
/*  928 */       if (Short2FloatOpenCustomHashMap.this.strategy.equals(k, (short)0)) {
/*  929 */         if (Short2FloatOpenCustomHashMap.this.containsNullKey && Float.floatToIntBits(Short2FloatOpenCustomHashMap.this.value[Short2FloatOpenCustomHashMap.this.n]) == Float.floatToIntBits(v)) {
/*  930 */           Short2FloatOpenCustomHashMap.this.removeNullEntry();
/*  931 */           return true;
/*      */         } 
/*  933 */         return false;
/*      */       } 
/*      */       
/*  936 */       short[] key = Short2FloatOpenCustomHashMap.this.key;
/*      */       short curr;
/*      */       int pos;
/*  939 */       if ((curr = key[pos = HashCommon.mix(Short2FloatOpenCustomHashMap.this.strategy.hashCode(k)) & Short2FloatOpenCustomHashMap.this.mask]) == 0)
/*      */       {
/*  941 */         return false; } 
/*  942 */       if (Short2FloatOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  943 */         if (Float.floatToIntBits(Short2FloatOpenCustomHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
/*  944 */           Short2FloatOpenCustomHashMap.this.removeEntry(pos);
/*  945 */           return true;
/*      */         } 
/*  947 */         return false;
/*      */       } 
/*      */       while (true) {
/*  950 */         if ((curr = key[pos = pos + 1 & Short2FloatOpenCustomHashMap.this.mask]) == 0)
/*  951 */           return false; 
/*  952 */         if (Short2FloatOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  953 */           Float.floatToIntBits(Short2FloatOpenCustomHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
/*  954 */           Short2FloatOpenCustomHashMap.this.removeEntry(pos);
/*  955 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  962 */       return Short2FloatOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  966 */       Short2FloatOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Short2FloatMap.Entry> consumer) {
/*  971 */       if (Short2FloatOpenCustomHashMap.this.containsNullKey)
/*  972 */         consumer.accept(new AbstractShort2FloatMap.BasicEntry(Short2FloatOpenCustomHashMap.this.key[Short2FloatOpenCustomHashMap.this.n], Short2FloatOpenCustomHashMap.this.value[Short2FloatOpenCustomHashMap.this.n])); 
/*  973 */       for (int pos = Short2FloatOpenCustomHashMap.this.n; pos-- != 0;) {
/*  974 */         if (Short2FloatOpenCustomHashMap.this.key[pos] != 0)
/*  975 */           consumer.accept(new AbstractShort2FloatMap.BasicEntry(Short2FloatOpenCustomHashMap.this.key[pos], Short2FloatOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Short2FloatMap.Entry> consumer) {
/*  980 */       AbstractShort2FloatMap.BasicEntry entry = new AbstractShort2FloatMap.BasicEntry();
/*  981 */       if (Short2FloatOpenCustomHashMap.this.containsNullKey) {
/*  982 */         entry.key = Short2FloatOpenCustomHashMap.this.key[Short2FloatOpenCustomHashMap.this.n];
/*  983 */         entry.value = Short2FloatOpenCustomHashMap.this.value[Short2FloatOpenCustomHashMap.this.n];
/*  984 */         consumer.accept(entry);
/*      */       } 
/*  986 */       for (int pos = Short2FloatOpenCustomHashMap.this.n; pos-- != 0;) {
/*  987 */         if (Short2FloatOpenCustomHashMap.this.key[pos] != 0) {
/*  988 */           entry.key = Short2FloatOpenCustomHashMap.this.key[pos];
/*  989 */           entry.value = Short2FloatOpenCustomHashMap.this.value[pos];
/*  990 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Short2FloatMap.FastEntrySet short2FloatEntrySet() {
/*  996 */     if (this.entries == null)
/*  997 */       this.entries = new MapEntrySet(); 
/*  998 */     return this.entries;
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
/*      */     implements ShortIterator
/*      */   {
/*      */     public short nextShort() {
/* 1015 */       return Short2FloatOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractShortSet { private KeySet() {}
/*      */     
/*      */     public ShortIterator iterator() {
/* 1021 */       return new Short2FloatOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer consumer) {
/* 1026 */       if (Short2FloatOpenCustomHashMap.this.containsNullKey)
/* 1027 */         consumer.accept(Short2FloatOpenCustomHashMap.this.key[Short2FloatOpenCustomHashMap.this.n]); 
/* 1028 */       for (int pos = Short2FloatOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1029 */         short k = Short2FloatOpenCustomHashMap.this.key[pos];
/* 1030 */         if (k != 0)
/* 1031 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1036 */       return Short2FloatOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(short k) {
/* 1040 */       return Short2FloatOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(short k) {
/* 1044 */       int oldSize = Short2FloatOpenCustomHashMap.this.size;
/* 1045 */       Short2FloatOpenCustomHashMap.this.remove(k);
/* 1046 */       return (Short2FloatOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1050 */       Short2FloatOpenCustomHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ShortSet keySet() {
/* 1055 */     if (this.keys == null)
/* 1056 */       this.keys = new KeySet(); 
/* 1057 */     return this.keys;
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
/* 1074 */       return Short2FloatOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public FloatCollection values() {
/* 1079 */     if (this.values == null)
/* 1080 */       this.values = (FloatCollection)new AbstractFloatCollection()
/*      */         {
/*      */           public FloatIterator iterator() {
/* 1083 */             return new Short2FloatOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1087 */             return Short2FloatOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(float v) {
/* 1091 */             return Short2FloatOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1095 */             Short2FloatOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(DoubleConsumer consumer)
/*      */           {
/* 1100 */             if (Short2FloatOpenCustomHashMap.this.containsNullKey)
/* 1101 */               consumer.accept(Short2FloatOpenCustomHashMap.this.value[Short2FloatOpenCustomHashMap.this.n]); 
/* 1102 */             for (int pos = Short2FloatOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1103 */               if (Short2FloatOpenCustomHashMap.this.key[pos] != 0)
/* 1104 */                 consumer.accept(Short2FloatOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1107 */     return this.values;
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
/* 1124 */     return trim(this.size);
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
/* 1148 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1149 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1150 */       return true; 
/*      */     try {
/* 1152 */       rehash(l);
/* 1153 */     } catch (OutOfMemoryError cantDoIt) {
/* 1154 */       return false;
/*      */     } 
/* 1156 */     return true;
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
/* 1172 */     short[] key = this.key;
/* 1173 */     float[] value = this.value;
/* 1174 */     int mask = newN - 1;
/* 1175 */     short[] newKey = new short[newN + 1];
/* 1176 */     float[] newValue = new float[newN + 1];
/* 1177 */     int i = this.n;
/* 1178 */     for (int j = realSize(); j-- != 0; ) {
/* 1179 */       while (key[--i] == 0); int pos;
/* 1180 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != 0)
/*      */       {
/* 1182 */         while (newKey[pos = pos + 1 & mask] != 0); } 
/* 1183 */       newKey[pos] = key[i];
/* 1184 */       newValue[pos] = value[i];
/*      */     } 
/* 1186 */     newValue[newN] = value[this.n];
/* 1187 */     this.n = newN;
/* 1188 */     this.mask = mask;
/* 1189 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1190 */     this.key = newKey;
/* 1191 */     this.value = newValue;
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
/*      */   public Short2FloatOpenCustomHashMap clone() {
/*      */     Short2FloatOpenCustomHashMap c;
/*      */     try {
/* 1208 */       c = (Short2FloatOpenCustomHashMap)super.clone();
/* 1209 */     } catch (CloneNotSupportedException cantHappen) {
/* 1210 */       throw new InternalError();
/*      */     } 
/* 1212 */     c.keys = null;
/* 1213 */     c.values = null;
/* 1214 */     c.entries = null;
/* 1215 */     c.containsNullKey = this.containsNullKey;
/* 1216 */     c.key = (short[])this.key.clone();
/* 1217 */     c.value = (float[])this.value.clone();
/* 1218 */     c.strategy = this.strategy;
/* 1219 */     return c;
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
/* 1232 */     int h = 0;
/* 1233 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1234 */       while (this.key[i] == 0)
/* 1235 */         i++; 
/* 1236 */       t = this.strategy.hashCode(this.key[i]);
/* 1237 */       t ^= HashCommon.float2int(this.value[i]);
/* 1238 */       h += t;
/* 1239 */       i++;
/*      */     } 
/*      */     
/* 1242 */     if (this.containsNullKey)
/* 1243 */       h += HashCommon.float2int(this.value[this.n]); 
/* 1244 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1247 */     short[] key = this.key;
/* 1248 */     float[] value = this.value;
/* 1249 */     MapIterator i = new MapIterator();
/* 1250 */     s.defaultWriteObject();
/* 1251 */     for (int j = this.size; j-- != 0; ) {
/* 1252 */       int e = i.nextEntry();
/* 1253 */       s.writeShort(key[e]);
/* 1254 */       s.writeFloat(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1259 */     s.defaultReadObject();
/* 1260 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1261 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1262 */     this.mask = this.n - 1;
/* 1263 */     short[] key = this.key = new short[this.n + 1];
/* 1264 */     float[] value = this.value = new float[this.n + 1];
/*      */ 
/*      */     
/* 1267 */     for (int i = this.size; i-- != 0; ) {
/* 1268 */       int pos; short k = s.readShort();
/* 1269 */       float v = s.readFloat();
/* 1270 */       if (this.strategy.equals(k, (short)0)) {
/* 1271 */         pos = this.n;
/* 1272 */         this.containsNullKey = true;
/*      */       } else {
/* 1274 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1275 */         while (key[pos] != 0)
/* 1276 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1278 */       key[pos] = k;
/* 1279 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\Short2FloatOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */