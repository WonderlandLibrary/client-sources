/*      */ package it.unimi.dsi.fastutil.chars;
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
/*      */ public class Char2FloatOpenCustomHashMap
/*      */   extends AbstractChar2FloatMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient char[] key;
/*      */   protected transient float[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected CharHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Char2FloatMap.FastEntrySet entries;
/*      */   protected transient CharSet keys;
/*      */   protected transient FloatCollection values;
/*      */   
/*      */   public Char2FloatOpenCustomHashMap(int expected, float f, CharHash.Strategy strategy) {
/*  107 */     this.strategy = strategy;
/*  108 */     if (f <= 0.0F || f > 1.0F)
/*  109 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  110 */     if (expected < 0)
/*  111 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  112 */     this.f = f;
/*  113 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  114 */     this.mask = this.n - 1;
/*  115 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  116 */     this.key = new char[this.n + 1];
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
/*      */   public Char2FloatOpenCustomHashMap(int expected, CharHash.Strategy strategy) {
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
/*      */   public Char2FloatOpenCustomHashMap(CharHash.Strategy strategy) {
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
/*      */   public Char2FloatOpenCustomHashMap(Map<? extends Character, ? extends Float> m, float f, CharHash.Strategy strategy) {
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
/*      */   public Char2FloatOpenCustomHashMap(Map<? extends Character, ? extends Float> m, CharHash.Strategy strategy) {
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
/*      */   public Char2FloatOpenCustomHashMap(Char2FloatMap m, float f, CharHash.Strategy strategy) {
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
/*      */   public Char2FloatOpenCustomHashMap(Char2FloatMap m, CharHash.Strategy strategy) {
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
/*      */   public Char2FloatOpenCustomHashMap(char[] k, float[] v, float f, CharHash.Strategy strategy) {
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
/*      */   public Char2FloatOpenCustomHashMap(char[] k, float[] v, CharHash.Strategy strategy) {
/*  236 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CharHash.Strategy strategy() {
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
/*      */   public void putAll(Map<? extends Character, ? extends Float> m) {
/*  278 */     if (this.f <= 0.5D) {
/*  279 */       ensureCapacity(m.size());
/*      */     } else {
/*  281 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  283 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(char k) {
/*  287 */     if (this.strategy.equals(k, false)) {
/*  288 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  290 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  293 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == '\000')
/*  294 */       return -(pos + 1); 
/*  295 */     if (this.strategy.equals(k, curr)) {
/*  296 */       return pos;
/*      */     }
/*      */     while (true) {
/*  299 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  300 */         return -(pos + 1); 
/*  301 */       if (this.strategy.equals(k, curr))
/*  302 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, char k, float v) {
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
/*      */   public float put(char k, float v) {
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
/*      */   public float addTo(char k, float incr) {
/*      */     int pos;
/*  349 */     if (this.strategy.equals(k, false)) {
/*  350 */       if (this.containsNullKey)
/*  351 */         return addToValue(this.n, incr); 
/*  352 */       pos = this.n;
/*  353 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  356 */       char[] key = this.key;
/*      */       char curr;
/*  358 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != '\000') {
/*      */         
/*  360 */         if (this.strategy.equals(curr, k))
/*  361 */           return addToValue(pos, incr); 
/*  362 */         while ((curr = key[pos = pos + 1 & this.mask]) != '\000') {
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
/*  386 */     char[] key = this.key; while (true) {
/*      */       char curr; int last;
/*  388 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  390 */         if ((curr = key[pos]) == '\000') {
/*  391 */           key[last] = Character.MIN_VALUE;
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
/*      */   public float remove(char k) {
/*  406 */     if (this.strategy.equals(k, false)) {
/*  407 */       if (this.containsNullKey)
/*  408 */         return removeNullEntry(); 
/*  409 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  412 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  415 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == '\000')
/*  416 */       return this.defRetValue; 
/*  417 */     if (this.strategy.equals(k, curr))
/*  418 */       return removeEntry(pos); 
/*      */     while (true) {
/*  420 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  421 */         return this.defRetValue; 
/*  422 */       if (this.strategy.equals(k, curr)) {
/*  423 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public float get(char k) {
/*  429 */     if (this.strategy.equals(k, false)) {
/*  430 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  432 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  435 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == '\000')
/*  436 */       return this.defRetValue; 
/*  437 */     if (this.strategy.equals(k, curr)) {
/*  438 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  441 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  442 */         return this.defRetValue; 
/*  443 */       if (this.strategy.equals(k, curr)) {
/*  444 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(char k) {
/*  450 */     if (this.strategy.equals(k, false)) {
/*  451 */       return this.containsNullKey;
/*      */     }
/*  453 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  456 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == '\000')
/*  457 */       return false; 
/*  458 */     if (this.strategy.equals(k, curr)) {
/*  459 */       return true;
/*      */     }
/*      */     while (true) {
/*  462 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  463 */         return false; 
/*  464 */       if (this.strategy.equals(k, curr))
/*  465 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(float v) {
/*  470 */     float[] value = this.value;
/*  471 */     char[] key = this.key;
/*  472 */     if (this.containsNullKey && Float.floatToIntBits(value[this.n]) == Float.floatToIntBits(v))
/*  473 */       return true; 
/*  474 */     for (int i = this.n; i-- != 0;) {
/*  475 */       if (key[i] != '\000' && Float.floatToIntBits(value[i]) == Float.floatToIntBits(v))
/*  476 */         return true; 
/*  477 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float getOrDefault(char k, float defaultValue) {
/*  483 */     if (this.strategy.equals(k, false)) {
/*  484 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  486 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  489 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == '\000')
/*  490 */       return defaultValue; 
/*  491 */     if (this.strategy.equals(k, curr)) {
/*  492 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  495 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  496 */         return defaultValue; 
/*  497 */       if (this.strategy.equals(k, curr)) {
/*  498 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public float putIfAbsent(char k, float v) {
/*  504 */     int pos = find(k);
/*  505 */     if (pos >= 0)
/*  506 */       return this.value[pos]; 
/*  507 */     insert(-pos - 1, k, v);
/*  508 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(char k, float v) {
/*  514 */     if (this.strategy.equals(k, false)) {
/*  515 */       if (this.containsNullKey && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[this.n])) {
/*  516 */         removeNullEntry();
/*  517 */         return true;
/*      */       } 
/*  519 */       return false;
/*      */     } 
/*      */     
/*  522 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  525 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == '\000')
/*  526 */       return false; 
/*  527 */     if (this.strategy.equals(k, curr) && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[pos])) {
/*  528 */       removeEntry(pos);
/*  529 */       return true;
/*      */     } 
/*      */     while (true) {
/*  532 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  533 */         return false; 
/*  534 */       if (this.strategy.equals(k, curr) && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[pos])) {
/*  535 */         removeEntry(pos);
/*  536 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(char k, float oldValue, float v) {
/*  543 */     int pos = find(k);
/*  544 */     if (pos < 0 || Float.floatToIntBits(oldValue) != Float.floatToIntBits(this.value[pos]))
/*  545 */       return false; 
/*  546 */     this.value[pos] = v;
/*  547 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public float replace(char k, float v) {
/*  552 */     int pos = find(k);
/*  553 */     if (pos < 0)
/*  554 */       return this.defRetValue; 
/*  555 */     float oldValue = this.value[pos];
/*  556 */     this.value[pos] = v;
/*  557 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public float computeIfAbsent(char k, IntToDoubleFunction mappingFunction) {
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
/*      */   public float computeIfAbsentNullable(char k, IntFunction<? extends Float> mappingFunction) {
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
/*      */   public float computeIfPresent(char k, BiFunction<? super Character, ? super Float, ? extends Float> remappingFunction) {
/*  589 */     Objects.requireNonNull(remappingFunction);
/*  590 */     int pos = find(k);
/*  591 */     if (pos < 0)
/*  592 */       return this.defRetValue; 
/*  593 */     Float newValue = remappingFunction.apply(Character.valueOf(k), Float.valueOf(this.value[pos]));
/*  594 */     if (newValue == null) {
/*  595 */       if (this.strategy.equals(k, false)) {
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
/*      */   public float compute(char k, BiFunction<? super Character, ? super Float, ? extends Float> remappingFunction) {
/*  607 */     Objects.requireNonNull(remappingFunction);
/*  608 */     int pos = find(k);
/*  609 */     Float newValue = remappingFunction.apply(Character.valueOf(k), 
/*  610 */         (pos >= 0) ? Float.valueOf(this.value[pos]) : null);
/*  611 */     if (newValue == null) {
/*  612 */       if (pos >= 0)
/*  613 */         if (this.strategy.equals(k, false)) {
/*  614 */           removeNullEntry();
/*      */         } else {
/*  616 */           removeEntry(pos);
/*      */         }  
/*  618 */       return this.defRetValue;
/*      */     } 
/*  620 */     float newVal = newValue.floatValue();
/*  621 */     if (pos < 0) {
/*  622 */       insert(-pos - 1, k, newVal);
/*  623 */       return newVal;
/*      */     } 
/*  625 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float merge(char k, float v, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/*  631 */     Objects.requireNonNull(remappingFunction);
/*  632 */     int pos = find(k);
/*  633 */     if (pos < 0) {
/*  634 */       insert(-pos - 1, k, v);
/*  635 */       return v;
/*      */     } 
/*  637 */     Float newValue = remappingFunction.apply(Float.valueOf(this.value[pos]), Float.valueOf(v));
/*  638 */     if (newValue == null) {
/*  639 */       if (this.strategy.equals(k, false)) {
/*  640 */         removeNullEntry();
/*      */       } else {
/*  642 */         removeEntry(pos);
/*  643 */       }  return this.defRetValue;
/*      */     } 
/*  645 */     this.value[pos] = newValue.floatValue(); return newValue.floatValue();
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
/*  656 */     if (this.size == 0)
/*      */       return; 
/*  658 */     this.size = 0;
/*  659 */     this.containsNullKey = false;
/*  660 */     Arrays.fill(this.key, false);
/*      */   }
/*      */   
/*      */   public int size() {
/*  664 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  668 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Char2FloatMap.Entry, Map.Entry<Character, Float>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  680 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public char getCharKey() {
/*  686 */       return Char2FloatOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public float getFloatValue() {
/*  690 */       return Char2FloatOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public float setValue(float v) {
/*  694 */       float oldValue = Char2FloatOpenCustomHashMap.this.value[this.index];
/*  695 */       Char2FloatOpenCustomHashMap.this.value[this.index] = v;
/*  696 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character getKey() {
/*  706 */       return Character.valueOf(Char2FloatOpenCustomHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float getValue() {
/*  716 */       return Float.valueOf(Char2FloatOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float setValue(Float v) {
/*  726 */       return Float.valueOf(setValue(v.floatValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  731 */       if (!(o instanceof Map.Entry))
/*  732 */         return false; 
/*  733 */       Map.Entry<Character, Float> e = (Map.Entry<Character, Float>)o;
/*  734 */       return (Char2FloatOpenCustomHashMap.this.strategy.equals(Char2FloatOpenCustomHashMap.this.key[this.index], ((Character)e.getKey()).charValue()) && 
/*  735 */         Float.floatToIntBits(Char2FloatOpenCustomHashMap.this.value[this.index]) == Float.floatToIntBits(((Float)e.getValue()).floatValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  739 */       return Char2FloatOpenCustomHashMap.this.strategy.hashCode(Char2FloatOpenCustomHashMap.this.key[this.index]) ^ HashCommon.float2int(Char2FloatOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  743 */       return Char2FloatOpenCustomHashMap.this.key[this.index] + "=>" + Char2FloatOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  753 */     int pos = Char2FloatOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  760 */     int last = -1;
/*      */     
/*  762 */     int c = Char2FloatOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  766 */     boolean mustReturnNullKey = Char2FloatOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     CharArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  773 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  776 */       if (!hasNext())
/*  777 */         throw new NoSuchElementException(); 
/*  778 */       this.c--;
/*  779 */       if (this.mustReturnNullKey) {
/*  780 */         this.mustReturnNullKey = false;
/*  781 */         return this.last = Char2FloatOpenCustomHashMap.this.n;
/*      */       } 
/*  783 */       char[] key = Char2FloatOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  785 */         if (--this.pos < 0) {
/*      */           
/*  787 */           this.last = Integer.MIN_VALUE;
/*  788 */           char k = this.wrapped.getChar(-this.pos - 1);
/*  789 */           int p = HashCommon.mix(Char2FloatOpenCustomHashMap.this.strategy.hashCode(k)) & Char2FloatOpenCustomHashMap.this.mask;
/*  790 */           while (!Char2FloatOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  791 */             p = p + 1 & Char2FloatOpenCustomHashMap.this.mask; 
/*  792 */           return p;
/*      */         } 
/*  794 */         if (key[this.pos] != '\000') {
/*  795 */           return this.last = this.pos;
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
/*  809 */       char[] key = Char2FloatOpenCustomHashMap.this.key; while (true) {
/*      */         char curr; int last;
/*  811 */         pos = (last = pos) + 1 & Char2FloatOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  813 */           if ((curr = key[pos]) == '\000') {
/*  814 */             key[last] = Character.MIN_VALUE;
/*      */             return;
/*      */           } 
/*  817 */           int slot = HashCommon.mix(Char2FloatOpenCustomHashMap.this.strategy.hashCode(curr)) & Char2FloatOpenCustomHashMap.this.mask;
/*  818 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  820 */           pos = pos + 1 & Char2FloatOpenCustomHashMap.this.mask;
/*      */         } 
/*  822 */         if (pos < last) {
/*  823 */           if (this.wrapped == null)
/*  824 */             this.wrapped = new CharArrayList(2); 
/*  825 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  827 */         key[last] = curr;
/*  828 */         Char2FloatOpenCustomHashMap.this.value[last] = Char2FloatOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  832 */       if (this.last == -1)
/*  833 */         throw new IllegalStateException(); 
/*  834 */       if (this.last == Char2FloatOpenCustomHashMap.this.n) {
/*  835 */         Char2FloatOpenCustomHashMap.this.containsNullKey = false;
/*  836 */       } else if (this.pos >= 0) {
/*  837 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  840 */         Char2FloatOpenCustomHashMap.this.remove(this.wrapped.getChar(-this.pos - 1));
/*  841 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  844 */       Char2FloatOpenCustomHashMap.this.size--;
/*  845 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  850 */       int i = n;
/*  851 */       while (i-- != 0 && hasNext())
/*  852 */         nextEntry(); 
/*  853 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Char2FloatMap.Entry> { private Char2FloatOpenCustomHashMap.MapEntry entry;
/*      */     
/*      */     public Char2FloatOpenCustomHashMap.MapEntry next() {
/*  860 */       return this.entry = new Char2FloatOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  864 */       super.remove();
/*  865 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Char2FloatMap.Entry> { private FastEntryIterator() {
/*  869 */       this.entry = new Char2FloatOpenCustomHashMap.MapEntry();
/*      */     } private final Char2FloatOpenCustomHashMap.MapEntry entry;
/*      */     public Char2FloatOpenCustomHashMap.MapEntry next() {
/*  872 */       this.entry.index = nextEntry();
/*  873 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Char2FloatMap.Entry> implements Char2FloatMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Char2FloatMap.Entry> iterator() {
/*  879 */       return new Char2FloatOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Char2FloatMap.Entry> fastIterator() {
/*  883 */       return new Char2FloatOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  888 */       if (!(o instanceof Map.Entry))
/*  889 */         return false; 
/*  890 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  891 */       if (e.getKey() == null || !(e.getKey() instanceof Character))
/*  892 */         return false; 
/*  893 */       if (e.getValue() == null || !(e.getValue() instanceof Float))
/*  894 */         return false; 
/*  895 */       char k = ((Character)e.getKey()).charValue();
/*  896 */       float v = ((Float)e.getValue()).floatValue();
/*  897 */       if (Char2FloatOpenCustomHashMap.this.strategy.equals(k, false)) {
/*  898 */         return (Char2FloatOpenCustomHashMap.this.containsNullKey && 
/*  899 */           Float.floatToIntBits(Char2FloatOpenCustomHashMap.this.value[Char2FloatOpenCustomHashMap.this.n]) == Float.floatToIntBits(v));
/*      */       }
/*  901 */       char[] key = Char2FloatOpenCustomHashMap.this.key;
/*      */       char curr;
/*      */       int pos;
/*  904 */       if ((curr = key[pos = HashCommon.mix(Char2FloatOpenCustomHashMap.this.strategy.hashCode(k)) & Char2FloatOpenCustomHashMap.this.mask]) == '\000')
/*  905 */         return false; 
/*  906 */       if (Char2FloatOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  907 */         return (Float.floatToIntBits(Char2FloatOpenCustomHashMap.this.value[pos]) == Float.floatToIntBits(v));
/*      */       }
/*      */       while (true) {
/*  910 */         if ((curr = key[pos = pos + 1 & Char2FloatOpenCustomHashMap.this.mask]) == '\000')
/*  911 */           return false; 
/*  912 */         if (Char2FloatOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  913 */           return (Float.floatToIntBits(Char2FloatOpenCustomHashMap.this.value[pos]) == Float.floatToIntBits(v));
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  919 */       if (!(o instanceof Map.Entry))
/*  920 */         return false; 
/*  921 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  922 */       if (e.getKey() == null || !(e.getKey() instanceof Character))
/*  923 */         return false; 
/*  924 */       if (e.getValue() == null || !(e.getValue() instanceof Float))
/*  925 */         return false; 
/*  926 */       char k = ((Character)e.getKey()).charValue();
/*  927 */       float v = ((Float)e.getValue()).floatValue();
/*  928 */       if (Char2FloatOpenCustomHashMap.this.strategy.equals(k, false)) {
/*  929 */         if (Char2FloatOpenCustomHashMap.this.containsNullKey && Float.floatToIntBits(Char2FloatOpenCustomHashMap.this.value[Char2FloatOpenCustomHashMap.this.n]) == Float.floatToIntBits(v)) {
/*  930 */           Char2FloatOpenCustomHashMap.this.removeNullEntry();
/*  931 */           return true;
/*      */         } 
/*  933 */         return false;
/*      */       } 
/*      */       
/*  936 */       char[] key = Char2FloatOpenCustomHashMap.this.key;
/*      */       char curr;
/*      */       int pos;
/*  939 */       if ((curr = key[pos = HashCommon.mix(Char2FloatOpenCustomHashMap.this.strategy.hashCode(k)) & Char2FloatOpenCustomHashMap.this.mask]) == '\000')
/*  940 */         return false; 
/*  941 */       if (Char2FloatOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  942 */         if (Float.floatToIntBits(Char2FloatOpenCustomHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
/*  943 */           Char2FloatOpenCustomHashMap.this.removeEntry(pos);
/*  944 */           return true;
/*      */         } 
/*  946 */         return false;
/*      */       } 
/*      */       while (true) {
/*  949 */         if ((curr = key[pos = pos + 1 & Char2FloatOpenCustomHashMap.this.mask]) == '\000')
/*  950 */           return false; 
/*  951 */         if (Char2FloatOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  952 */           Float.floatToIntBits(Char2FloatOpenCustomHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
/*  953 */           Char2FloatOpenCustomHashMap.this.removeEntry(pos);
/*  954 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  961 */       return Char2FloatOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  965 */       Char2FloatOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Char2FloatMap.Entry> consumer) {
/*  970 */       if (Char2FloatOpenCustomHashMap.this.containsNullKey)
/*  971 */         consumer.accept(new AbstractChar2FloatMap.BasicEntry(Char2FloatOpenCustomHashMap.this.key[Char2FloatOpenCustomHashMap.this.n], Char2FloatOpenCustomHashMap.this.value[Char2FloatOpenCustomHashMap.this.n])); 
/*  972 */       for (int pos = Char2FloatOpenCustomHashMap.this.n; pos-- != 0;) {
/*  973 */         if (Char2FloatOpenCustomHashMap.this.key[pos] != '\000')
/*  974 */           consumer.accept(new AbstractChar2FloatMap.BasicEntry(Char2FloatOpenCustomHashMap.this.key[pos], Char2FloatOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Char2FloatMap.Entry> consumer) {
/*  979 */       AbstractChar2FloatMap.BasicEntry entry = new AbstractChar2FloatMap.BasicEntry();
/*  980 */       if (Char2FloatOpenCustomHashMap.this.containsNullKey) {
/*  981 */         entry.key = Char2FloatOpenCustomHashMap.this.key[Char2FloatOpenCustomHashMap.this.n];
/*  982 */         entry.value = Char2FloatOpenCustomHashMap.this.value[Char2FloatOpenCustomHashMap.this.n];
/*  983 */         consumer.accept(entry);
/*      */       } 
/*  985 */       for (int pos = Char2FloatOpenCustomHashMap.this.n; pos-- != 0;) {
/*  986 */         if (Char2FloatOpenCustomHashMap.this.key[pos] != '\000') {
/*  987 */           entry.key = Char2FloatOpenCustomHashMap.this.key[pos];
/*  988 */           entry.value = Char2FloatOpenCustomHashMap.this.value[pos];
/*  989 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Char2FloatMap.FastEntrySet char2FloatEntrySet() {
/*  995 */     if (this.entries == null)
/*  996 */       this.entries = new MapEntrySet(); 
/*  997 */     return this.entries;
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
/*      */     implements CharIterator
/*      */   {
/*      */     public char nextChar() {
/* 1014 */       return Char2FloatOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractCharSet { private KeySet() {}
/*      */     
/*      */     public CharIterator iterator() {
/* 1020 */       return new Char2FloatOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer consumer) {
/* 1025 */       if (Char2FloatOpenCustomHashMap.this.containsNullKey)
/* 1026 */         consumer.accept(Char2FloatOpenCustomHashMap.this.key[Char2FloatOpenCustomHashMap.this.n]); 
/* 1027 */       for (int pos = Char2FloatOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1028 */         char k = Char2FloatOpenCustomHashMap.this.key[pos];
/* 1029 */         if (k != '\000')
/* 1030 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1035 */       return Char2FloatOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(char k) {
/* 1039 */       return Char2FloatOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(char k) {
/* 1043 */       int oldSize = Char2FloatOpenCustomHashMap.this.size;
/* 1044 */       Char2FloatOpenCustomHashMap.this.remove(k);
/* 1045 */       return (Char2FloatOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1049 */       Char2FloatOpenCustomHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public CharSet keySet() {
/* 1054 */     if (this.keys == null)
/* 1055 */       this.keys = new KeySet(); 
/* 1056 */     return this.keys;
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
/* 1073 */       return Char2FloatOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public FloatCollection values() {
/* 1078 */     if (this.values == null)
/* 1079 */       this.values = (FloatCollection)new AbstractFloatCollection()
/*      */         {
/*      */           public FloatIterator iterator() {
/* 1082 */             return new Char2FloatOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1086 */             return Char2FloatOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(float v) {
/* 1090 */             return Char2FloatOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1094 */             Char2FloatOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(DoubleConsumer consumer)
/*      */           {
/* 1099 */             if (Char2FloatOpenCustomHashMap.this.containsNullKey)
/* 1100 */               consumer.accept(Char2FloatOpenCustomHashMap.this.value[Char2FloatOpenCustomHashMap.this.n]); 
/* 1101 */             for (int pos = Char2FloatOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1102 */               if (Char2FloatOpenCustomHashMap.this.key[pos] != '\000')
/* 1103 */                 consumer.accept(Char2FloatOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1106 */     return this.values;
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
/* 1123 */     return trim(this.size);
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
/* 1147 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1148 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1149 */       return true; 
/*      */     try {
/* 1151 */       rehash(l);
/* 1152 */     } catch (OutOfMemoryError cantDoIt) {
/* 1153 */       return false;
/*      */     } 
/* 1155 */     return true;
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
/* 1171 */     char[] key = this.key;
/* 1172 */     float[] value = this.value;
/* 1173 */     int mask = newN - 1;
/* 1174 */     char[] newKey = new char[newN + 1];
/* 1175 */     float[] newValue = new float[newN + 1];
/* 1176 */     int i = this.n;
/* 1177 */     for (int j = realSize(); j-- != 0; ) {
/* 1178 */       while (key[--i] == '\000'); int pos;
/* 1179 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != '\000')
/*      */       {
/* 1181 */         while (newKey[pos = pos + 1 & mask] != '\000'); } 
/* 1182 */       newKey[pos] = key[i];
/* 1183 */       newValue[pos] = value[i];
/*      */     } 
/* 1185 */     newValue[newN] = value[this.n];
/* 1186 */     this.n = newN;
/* 1187 */     this.mask = mask;
/* 1188 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1189 */     this.key = newKey;
/* 1190 */     this.value = newValue;
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
/*      */   public Char2FloatOpenCustomHashMap clone() {
/*      */     Char2FloatOpenCustomHashMap c;
/*      */     try {
/* 1207 */       c = (Char2FloatOpenCustomHashMap)super.clone();
/* 1208 */     } catch (CloneNotSupportedException cantHappen) {
/* 1209 */       throw new InternalError();
/*      */     } 
/* 1211 */     c.keys = null;
/* 1212 */     c.values = null;
/* 1213 */     c.entries = null;
/* 1214 */     c.containsNullKey = this.containsNullKey;
/* 1215 */     c.key = (char[])this.key.clone();
/* 1216 */     c.value = (float[])this.value.clone();
/* 1217 */     c.strategy = this.strategy;
/* 1218 */     return c;
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
/* 1231 */     int h = 0;
/* 1232 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1233 */       while (this.key[i] == '\000')
/* 1234 */         i++; 
/* 1235 */       t = this.strategy.hashCode(this.key[i]);
/* 1236 */       t ^= HashCommon.float2int(this.value[i]);
/* 1237 */       h += t;
/* 1238 */       i++;
/*      */     } 
/*      */     
/* 1241 */     if (this.containsNullKey)
/* 1242 */       h += HashCommon.float2int(this.value[this.n]); 
/* 1243 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1246 */     char[] key = this.key;
/* 1247 */     float[] value = this.value;
/* 1248 */     MapIterator i = new MapIterator();
/* 1249 */     s.defaultWriteObject();
/* 1250 */     for (int j = this.size; j-- != 0; ) {
/* 1251 */       int e = i.nextEntry();
/* 1252 */       s.writeChar(key[e]);
/* 1253 */       s.writeFloat(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1258 */     s.defaultReadObject();
/* 1259 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1260 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1261 */     this.mask = this.n - 1;
/* 1262 */     char[] key = this.key = new char[this.n + 1];
/* 1263 */     float[] value = this.value = new float[this.n + 1];
/*      */ 
/*      */     
/* 1266 */     for (int i = this.size; i-- != 0; ) {
/* 1267 */       int pos; char k = s.readChar();
/* 1268 */       float v = s.readFloat();
/* 1269 */       if (this.strategy.equals(k, false)) {
/* 1270 */         pos = this.n;
/* 1271 */         this.containsNullKey = true;
/*      */       } else {
/* 1273 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1274 */         while (key[pos] != '\000')
/* 1275 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1277 */       key[pos] = k;
/* 1278 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\Char2FloatOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */