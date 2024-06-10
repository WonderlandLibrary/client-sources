/*      */ package it.unimi.dsi.fastutil.ints;
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
/*      */ public class Int2FloatOpenCustomHashMap
/*      */   extends AbstractInt2FloatMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient int[] key;
/*      */   protected transient float[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected IntHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Int2FloatMap.FastEntrySet entries;
/*      */   protected transient IntSet keys;
/*      */   protected transient FloatCollection values;
/*      */   
/*      */   public Int2FloatOpenCustomHashMap(int expected, float f, IntHash.Strategy strategy) {
/*  103 */     this.strategy = strategy;
/*  104 */     if (f <= 0.0F || f > 1.0F)
/*  105 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  106 */     if (expected < 0)
/*  107 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  108 */     this.f = f;
/*  109 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  110 */     this.mask = this.n - 1;
/*  111 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  112 */     this.key = new int[this.n + 1];
/*  113 */     this.value = new float[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2FloatOpenCustomHashMap(int expected, IntHash.Strategy strategy) {
/*  124 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2FloatOpenCustomHashMap(IntHash.Strategy strategy) {
/*  135 */     this(16, 0.75F, strategy);
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
/*      */   public Int2FloatOpenCustomHashMap(Map<? extends Integer, ? extends Float> m, float f, IntHash.Strategy strategy) {
/*  149 */     this(m.size(), f, strategy);
/*  150 */     putAll(m);
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
/*      */   public Int2FloatOpenCustomHashMap(Map<? extends Integer, ? extends Float> m, IntHash.Strategy strategy) {
/*  163 */     this(m, 0.75F, strategy);
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
/*      */   public Int2FloatOpenCustomHashMap(Int2FloatMap m, float f, IntHash.Strategy strategy) {
/*  177 */     this(m.size(), f, strategy);
/*  178 */     putAll(m);
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
/*      */   public Int2FloatOpenCustomHashMap(Int2FloatMap m, IntHash.Strategy strategy) {
/*  191 */     this(m, 0.75F, strategy);
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
/*      */   public Int2FloatOpenCustomHashMap(int[] k, float[] v, float f, IntHash.Strategy strategy) {
/*  209 */     this(k.length, f, strategy);
/*  210 */     if (k.length != v.length) {
/*  211 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  213 */     for (int i = 0; i < k.length; i++) {
/*  214 */       put(k[i], v[i]);
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
/*      */   public Int2FloatOpenCustomHashMap(int[] k, float[] v, IntHash.Strategy strategy) {
/*  231 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntHash.Strategy strategy() {
/*  239 */     return this.strategy;
/*      */   }
/*      */   private int realSize() {
/*  242 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  245 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  246 */     if (needed > this.n)
/*  247 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  250 */     int needed = (int)Math.min(1073741824L, 
/*  251 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  252 */     if (needed > this.n)
/*  253 */       rehash(needed); 
/*      */   }
/*      */   private float removeEntry(int pos) {
/*  256 */     float oldValue = this.value[pos];
/*  257 */     this.size--;
/*  258 */     shiftKeys(pos);
/*  259 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  260 */       rehash(this.n / 2); 
/*  261 */     return oldValue;
/*      */   }
/*      */   private float removeNullEntry() {
/*  264 */     this.containsNullKey = false;
/*  265 */     float oldValue = this.value[this.n];
/*  266 */     this.size--;
/*  267 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  268 */       rehash(this.n / 2); 
/*  269 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Integer, ? extends Float> m) {
/*  273 */     if (this.f <= 0.5D) {
/*  274 */       ensureCapacity(m.size());
/*      */     } else {
/*  276 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  278 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(int k) {
/*  282 */     if (this.strategy.equals(k, 0)) {
/*  283 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  285 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  288 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  289 */       return -(pos + 1); 
/*  290 */     if (this.strategy.equals(k, curr)) {
/*  291 */       return pos;
/*      */     }
/*      */     while (true) {
/*  294 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  295 */         return -(pos + 1); 
/*  296 */       if (this.strategy.equals(k, curr))
/*  297 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, int k, float v) {
/*  301 */     if (pos == this.n)
/*  302 */       this.containsNullKey = true; 
/*  303 */     this.key[pos] = k;
/*  304 */     this.value[pos] = v;
/*  305 */     if (this.size++ >= this.maxFill) {
/*  306 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public float put(int k, float v) {
/*  312 */     int pos = find(k);
/*  313 */     if (pos < 0) {
/*  314 */       insert(-pos - 1, k, v);
/*  315 */       return this.defRetValue;
/*      */     } 
/*  317 */     float oldValue = this.value[pos];
/*  318 */     this.value[pos] = v;
/*  319 */     return oldValue;
/*      */   }
/*      */   private float addToValue(int pos, float incr) {
/*  322 */     float oldValue = this.value[pos];
/*  323 */     this.value[pos] = oldValue + incr;
/*  324 */     return oldValue;
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
/*      */   public float addTo(int k, float incr) {
/*      */     int pos;
/*  344 */     if (this.strategy.equals(k, 0)) {
/*  345 */       if (this.containsNullKey)
/*  346 */         return addToValue(this.n, incr); 
/*  347 */       pos = this.n;
/*  348 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  351 */       int[] key = this.key;
/*      */       int curr;
/*  353 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != 0) {
/*  354 */         if (this.strategy.equals(curr, k))
/*  355 */           return addToValue(pos, incr); 
/*  356 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
/*  357 */           if (this.strategy.equals(curr, k))
/*  358 */             return addToValue(pos, incr); 
/*      */         } 
/*      */       } 
/*  361 */     }  this.key[pos] = k;
/*  362 */     this.value[pos] = this.defRetValue + incr;
/*  363 */     if (this.size++ >= this.maxFill) {
/*  364 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  367 */     return this.defRetValue;
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
/*  380 */     int[] key = this.key; while (true) {
/*      */       int curr, last;
/*  382 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  384 */         if ((curr = key[pos]) == 0) {
/*  385 */           key[last] = 0;
/*      */           return;
/*      */         } 
/*  388 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  389 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  391 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  393 */       key[last] = curr;
/*  394 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public float remove(int k) {
/*  400 */     if (this.strategy.equals(k, 0)) {
/*  401 */       if (this.containsNullKey)
/*  402 */         return removeNullEntry(); 
/*  403 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  406 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  409 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  410 */       return this.defRetValue; 
/*  411 */     if (this.strategy.equals(k, curr))
/*  412 */       return removeEntry(pos); 
/*      */     while (true) {
/*  414 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  415 */         return this.defRetValue; 
/*  416 */       if (this.strategy.equals(k, curr)) {
/*  417 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public float get(int k) {
/*  423 */     if (this.strategy.equals(k, 0)) {
/*  424 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  426 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  429 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  430 */       return this.defRetValue; 
/*  431 */     if (this.strategy.equals(k, curr)) {
/*  432 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  435 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  436 */         return this.defRetValue; 
/*  437 */       if (this.strategy.equals(k, curr)) {
/*  438 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(int k) {
/*  444 */     if (this.strategy.equals(k, 0)) {
/*  445 */       return this.containsNullKey;
/*      */     }
/*  447 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  450 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  451 */       return false; 
/*  452 */     if (this.strategy.equals(k, curr)) {
/*  453 */       return true;
/*      */     }
/*      */     while (true) {
/*  456 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  457 */         return false; 
/*  458 */       if (this.strategy.equals(k, curr))
/*  459 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(float v) {
/*  464 */     float[] value = this.value;
/*  465 */     int[] key = this.key;
/*  466 */     if (this.containsNullKey && Float.floatToIntBits(value[this.n]) == Float.floatToIntBits(v))
/*  467 */       return true; 
/*  468 */     for (int i = this.n; i-- != 0;) {
/*  469 */       if (key[i] != 0 && Float.floatToIntBits(value[i]) == Float.floatToIntBits(v))
/*  470 */         return true; 
/*  471 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float getOrDefault(int k, float defaultValue) {
/*  477 */     if (this.strategy.equals(k, 0)) {
/*  478 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  480 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  483 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  484 */       return defaultValue; 
/*  485 */     if (this.strategy.equals(k, curr)) {
/*  486 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  489 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  490 */         return defaultValue; 
/*  491 */       if (this.strategy.equals(k, curr)) {
/*  492 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public float putIfAbsent(int k, float v) {
/*  498 */     int pos = find(k);
/*  499 */     if (pos >= 0)
/*  500 */       return this.value[pos]; 
/*  501 */     insert(-pos - 1, k, v);
/*  502 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(int k, float v) {
/*  508 */     if (this.strategy.equals(k, 0)) {
/*  509 */       if (this.containsNullKey && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[this.n])) {
/*  510 */         removeNullEntry();
/*  511 */         return true;
/*      */       } 
/*  513 */       return false;
/*      */     } 
/*      */     
/*  516 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  519 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  520 */       return false; 
/*  521 */     if (this.strategy.equals(k, curr) && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[pos])) {
/*  522 */       removeEntry(pos);
/*  523 */       return true;
/*      */     } 
/*      */     while (true) {
/*  526 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  527 */         return false; 
/*  528 */       if (this.strategy.equals(k, curr) && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[pos])) {
/*  529 */         removeEntry(pos);
/*  530 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(int k, float oldValue, float v) {
/*  537 */     int pos = find(k);
/*  538 */     if (pos < 0 || Float.floatToIntBits(oldValue) != Float.floatToIntBits(this.value[pos]))
/*  539 */       return false; 
/*  540 */     this.value[pos] = v;
/*  541 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public float replace(int k, float v) {
/*  546 */     int pos = find(k);
/*  547 */     if (pos < 0)
/*  548 */       return this.defRetValue; 
/*  549 */     float oldValue = this.value[pos];
/*  550 */     this.value[pos] = v;
/*  551 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public float computeIfAbsent(int k, IntToDoubleFunction mappingFunction) {
/*  556 */     Objects.requireNonNull(mappingFunction);
/*  557 */     int pos = find(k);
/*  558 */     if (pos >= 0)
/*  559 */       return this.value[pos]; 
/*  560 */     float newValue = SafeMath.safeDoubleToFloat(mappingFunction.applyAsDouble(k));
/*  561 */     insert(-pos - 1, k, newValue);
/*  562 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float computeIfAbsentNullable(int k, IntFunction<? extends Float> mappingFunction) {
/*  568 */     Objects.requireNonNull(mappingFunction);
/*  569 */     int pos = find(k);
/*  570 */     if (pos >= 0)
/*  571 */       return this.value[pos]; 
/*  572 */     Float newValue = mappingFunction.apply(k);
/*  573 */     if (newValue == null)
/*  574 */       return this.defRetValue; 
/*  575 */     float v = newValue.floatValue();
/*  576 */     insert(-pos - 1, k, v);
/*  577 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float computeIfPresent(int k, BiFunction<? super Integer, ? super Float, ? extends Float> remappingFunction) {
/*  583 */     Objects.requireNonNull(remappingFunction);
/*  584 */     int pos = find(k);
/*  585 */     if (pos < 0)
/*  586 */       return this.defRetValue; 
/*  587 */     Float newValue = remappingFunction.apply(Integer.valueOf(k), Float.valueOf(this.value[pos]));
/*  588 */     if (newValue == null) {
/*  589 */       if (this.strategy.equals(k, 0)) {
/*  590 */         removeNullEntry();
/*      */       } else {
/*  592 */         removeEntry(pos);
/*  593 */       }  return this.defRetValue;
/*      */     } 
/*  595 */     this.value[pos] = newValue.floatValue(); return newValue.floatValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float compute(int k, BiFunction<? super Integer, ? super Float, ? extends Float> remappingFunction) {
/*  601 */     Objects.requireNonNull(remappingFunction);
/*  602 */     int pos = find(k);
/*  603 */     Float newValue = remappingFunction.apply(Integer.valueOf(k), (pos >= 0) ? Float.valueOf(this.value[pos]) : null);
/*  604 */     if (newValue == null) {
/*  605 */       if (pos >= 0)
/*  606 */         if (this.strategy.equals(k, 0)) {
/*  607 */           removeNullEntry();
/*      */         } else {
/*  609 */           removeEntry(pos);
/*      */         }  
/*  611 */       return this.defRetValue;
/*      */     } 
/*  613 */     float newVal = newValue.floatValue();
/*  614 */     if (pos < 0) {
/*  615 */       insert(-pos - 1, k, newVal);
/*  616 */       return newVal;
/*      */     } 
/*  618 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float merge(int k, float v, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/*  624 */     Objects.requireNonNull(remappingFunction);
/*  625 */     int pos = find(k);
/*  626 */     if (pos < 0) {
/*  627 */       insert(-pos - 1, k, v);
/*  628 */       return v;
/*      */     } 
/*  630 */     Float newValue = remappingFunction.apply(Float.valueOf(this.value[pos]), Float.valueOf(v));
/*  631 */     if (newValue == null) {
/*  632 */       if (this.strategy.equals(k, 0)) {
/*  633 */         removeNullEntry();
/*      */       } else {
/*  635 */         removeEntry(pos);
/*  636 */       }  return this.defRetValue;
/*      */     } 
/*  638 */     this.value[pos] = newValue.floatValue(); return newValue.floatValue();
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
/*  649 */     if (this.size == 0)
/*      */       return; 
/*  651 */     this.size = 0;
/*  652 */     this.containsNullKey = false;
/*  653 */     Arrays.fill(this.key, 0);
/*      */   }
/*      */   
/*      */   public int size() {
/*  657 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  661 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Int2FloatMap.Entry, Map.Entry<Integer, Float>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  673 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public int getIntKey() {
/*  679 */       return Int2FloatOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public float getFloatValue() {
/*  683 */       return Int2FloatOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public float setValue(float v) {
/*  687 */       float oldValue = Int2FloatOpenCustomHashMap.this.value[this.index];
/*  688 */       Int2FloatOpenCustomHashMap.this.value[this.index] = v;
/*  689 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer getKey() {
/*  699 */       return Integer.valueOf(Int2FloatOpenCustomHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float getValue() {
/*  709 */       return Float.valueOf(Int2FloatOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float setValue(Float v) {
/*  719 */       return Float.valueOf(setValue(v.floatValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  724 */       if (!(o instanceof Map.Entry))
/*  725 */         return false; 
/*  726 */       Map.Entry<Integer, Float> e = (Map.Entry<Integer, Float>)o;
/*  727 */       return (Int2FloatOpenCustomHashMap.this.strategy.equals(Int2FloatOpenCustomHashMap.this.key[this.index], ((Integer)e.getKey()).intValue()) && 
/*  728 */         Float.floatToIntBits(Int2FloatOpenCustomHashMap.this.value[this.index]) == Float.floatToIntBits(((Float)e.getValue()).floatValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  732 */       return Int2FloatOpenCustomHashMap.this.strategy.hashCode(Int2FloatOpenCustomHashMap.this.key[this.index]) ^ HashCommon.float2int(Int2FloatOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  736 */       return Int2FloatOpenCustomHashMap.this.key[this.index] + "=>" + Int2FloatOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  746 */     int pos = Int2FloatOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  753 */     int last = -1;
/*      */     
/*  755 */     int c = Int2FloatOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  759 */     boolean mustReturnNullKey = Int2FloatOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     IntArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  766 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  769 */       if (!hasNext())
/*  770 */         throw new NoSuchElementException(); 
/*  771 */       this.c--;
/*  772 */       if (this.mustReturnNullKey) {
/*  773 */         this.mustReturnNullKey = false;
/*  774 */         return this.last = Int2FloatOpenCustomHashMap.this.n;
/*      */       } 
/*  776 */       int[] key = Int2FloatOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  778 */         if (--this.pos < 0) {
/*      */           
/*  780 */           this.last = Integer.MIN_VALUE;
/*  781 */           int k = this.wrapped.getInt(-this.pos - 1);
/*  782 */           int p = HashCommon.mix(Int2FloatOpenCustomHashMap.this.strategy.hashCode(k)) & Int2FloatOpenCustomHashMap.this.mask;
/*  783 */           while (!Int2FloatOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  784 */             p = p + 1 & Int2FloatOpenCustomHashMap.this.mask; 
/*  785 */           return p;
/*      */         } 
/*  787 */         if (key[this.pos] != 0) {
/*  788 */           return this.last = this.pos;
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
/*  802 */       int[] key = Int2FloatOpenCustomHashMap.this.key; while (true) {
/*      */         int curr, last;
/*  804 */         pos = (last = pos) + 1 & Int2FloatOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  806 */           if ((curr = key[pos]) == 0) {
/*  807 */             key[last] = 0;
/*      */             return;
/*      */           } 
/*  810 */           int slot = HashCommon.mix(Int2FloatOpenCustomHashMap.this.strategy.hashCode(curr)) & Int2FloatOpenCustomHashMap.this.mask;
/*  811 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  813 */           pos = pos + 1 & Int2FloatOpenCustomHashMap.this.mask;
/*      */         } 
/*  815 */         if (pos < last) {
/*  816 */           if (this.wrapped == null)
/*  817 */             this.wrapped = new IntArrayList(2); 
/*  818 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  820 */         key[last] = curr;
/*  821 */         Int2FloatOpenCustomHashMap.this.value[last] = Int2FloatOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  825 */       if (this.last == -1)
/*  826 */         throw new IllegalStateException(); 
/*  827 */       if (this.last == Int2FloatOpenCustomHashMap.this.n) {
/*  828 */         Int2FloatOpenCustomHashMap.this.containsNullKey = false;
/*  829 */       } else if (this.pos >= 0) {
/*  830 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  833 */         Int2FloatOpenCustomHashMap.this.remove(this.wrapped.getInt(-this.pos - 1));
/*  834 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  837 */       Int2FloatOpenCustomHashMap.this.size--;
/*  838 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  843 */       int i = n;
/*  844 */       while (i-- != 0 && hasNext())
/*  845 */         nextEntry(); 
/*  846 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Int2FloatMap.Entry> { private Int2FloatOpenCustomHashMap.MapEntry entry;
/*      */     
/*      */     public Int2FloatOpenCustomHashMap.MapEntry next() {
/*  853 */       return this.entry = new Int2FloatOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  857 */       super.remove();
/*  858 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Int2FloatMap.Entry> { private FastEntryIterator() {
/*  862 */       this.entry = new Int2FloatOpenCustomHashMap.MapEntry();
/*      */     } private final Int2FloatOpenCustomHashMap.MapEntry entry;
/*      */     public Int2FloatOpenCustomHashMap.MapEntry next() {
/*  865 */       this.entry.index = nextEntry();
/*  866 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Int2FloatMap.Entry> implements Int2FloatMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Int2FloatMap.Entry> iterator() {
/*  872 */       return new Int2FloatOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Int2FloatMap.Entry> fastIterator() {
/*  876 */       return new Int2FloatOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  881 */       if (!(o instanceof Map.Entry))
/*  882 */         return false; 
/*  883 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  884 */       if (e.getKey() == null || !(e.getKey() instanceof Integer))
/*  885 */         return false; 
/*  886 */       if (e.getValue() == null || !(e.getValue() instanceof Float))
/*  887 */         return false; 
/*  888 */       int k = ((Integer)e.getKey()).intValue();
/*  889 */       float v = ((Float)e.getValue()).floatValue();
/*  890 */       if (Int2FloatOpenCustomHashMap.this.strategy.equals(k, 0)) {
/*  891 */         return (Int2FloatOpenCustomHashMap.this.containsNullKey && 
/*  892 */           Float.floatToIntBits(Int2FloatOpenCustomHashMap.this.value[Int2FloatOpenCustomHashMap.this.n]) == Float.floatToIntBits(v));
/*      */       }
/*  894 */       int[] key = Int2FloatOpenCustomHashMap.this.key;
/*      */       
/*      */       int curr, pos;
/*  897 */       if ((curr = key[pos = HashCommon.mix(Int2FloatOpenCustomHashMap.this.strategy.hashCode(k)) & Int2FloatOpenCustomHashMap.this.mask]) == 0)
/*  898 */         return false; 
/*  899 */       if (Int2FloatOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  900 */         return (Float.floatToIntBits(Int2FloatOpenCustomHashMap.this.value[pos]) == Float.floatToIntBits(v));
/*      */       }
/*      */       while (true) {
/*  903 */         if ((curr = key[pos = pos + 1 & Int2FloatOpenCustomHashMap.this.mask]) == 0)
/*  904 */           return false; 
/*  905 */         if (Int2FloatOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  906 */           return (Float.floatToIntBits(Int2FloatOpenCustomHashMap.this.value[pos]) == Float.floatToIntBits(v));
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  912 */       if (!(o instanceof Map.Entry))
/*  913 */         return false; 
/*  914 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  915 */       if (e.getKey() == null || !(e.getKey() instanceof Integer))
/*  916 */         return false; 
/*  917 */       if (e.getValue() == null || !(e.getValue() instanceof Float))
/*  918 */         return false; 
/*  919 */       int k = ((Integer)e.getKey()).intValue();
/*  920 */       float v = ((Float)e.getValue()).floatValue();
/*  921 */       if (Int2FloatOpenCustomHashMap.this.strategy.equals(k, 0)) {
/*  922 */         if (Int2FloatOpenCustomHashMap.this.containsNullKey && Float.floatToIntBits(Int2FloatOpenCustomHashMap.this.value[Int2FloatOpenCustomHashMap.this.n]) == Float.floatToIntBits(v)) {
/*  923 */           Int2FloatOpenCustomHashMap.this.removeNullEntry();
/*  924 */           return true;
/*      */         } 
/*  926 */         return false;
/*      */       } 
/*      */       
/*  929 */       int[] key = Int2FloatOpenCustomHashMap.this.key;
/*      */       
/*      */       int curr, pos;
/*  932 */       if ((curr = key[pos = HashCommon.mix(Int2FloatOpenCustomHashMap.this.strategy.hashCode(k)) & Int2FloatOpenCustomHashMap.this.mask]) == 0)
/*  933 */         return false; 
/*  934 */       if (Int2FloatOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  935 */         if (Float.floatToIntBits(Int2FloatOpenCustomHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
/*  936 */           Int2FloatOpenCustomHashMap.this.removeEntry(pos);
/*  937 */           return true;
/*      */         } 
/*  939 */         return false;
/*      */       } 
/*      */       while (true) {
/*  942 */         if ((curr = key[pos = pos + 1 & Int2FloatOpenCustomHashMap.this.mask]) == 0)
/*  943 */           return false; 
/*  944 */         if (Int2FloatOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  945 */           Float.floatToIntBits(Int2FloatOpenCustomHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
/*  946 */           Int2FloatOpenCustomHashMap.this.removeEntry(pos);
/*  947 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  954 */       return Int2FloatOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  958 */       Int2FloatOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Int2FloatMap.Entry> consumer) {
/*  963 */       if (Int2FloatOpenCustomHashMap.this.containsNullKey)
/*  964 */         consumer.accept(new AbstractInt2FloatMap.BasicEntry(Int2FloatOpenCustomHashMap.this.key[Int2FloatOpenCustomHashMap.this.n], Int2FloatOpenCustomHashMap.this.value[Int2FloatOpenCustomHashMap.this.n])); 
/*  965 */       for (int pos = Int2FloatOpenCustomHashMap.this.n; pos-- != 0;) {
/*  966 */         if (Int2FloatOpenCustomHashMap.this.key[pos] != 0)
/*  967 */           consumer.accept(new AbstractInt2FloatMap.BasicEntry(Int2FloatOpenCustomHashMap.this.key[pos], Int2FloatOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Int2FloatMap.Entry> consumer) {
/*  972 */       AbstractInt2FloatMap.BasicEntry entry = new AbstractInt2FloatMap.BasicEntry();
/*  973 */       if (Int2FloatOpenCustomHashMap.this.containsNullKey) {
/*  974 */         entry.key = Int2FloatOpenCustomHashMap.this.key[Int2FloatOpenCustomHashMap.this.n];
/*  975 */         entry.value = Int2FloatOpenCustomHashMap.this.value[Int2FloatOpenCustomHashMap.this.n];
/*  976 */         consumer.accept(entry);
/*      */       } 
/*  978 */       for (int pos = Int2FloatOpenCustomHashMap.this.n; pos-- != 0;) {
/*  979 */         if (Int2FloatOpenCustomHashMap.this.key[pos] != 0) {
/*  980 */           entry.key = Int2FloatOpenCustomHashMap.this.key[pos];
/*  981 */           entry.value = Int2FloatOpenCustomHashMap.this.value[pos];
/*  982 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Int2FloatMap.FastEntrySet int2FloatEntrySet() {
/*  988 */     if (this.entries == null)
/*  989 */       this.entries = new MapEntrySet(); 
/*  990 */     return this.entries;
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
/*      */     implements IntIterator
/*      */   {
/*      */     public int nextInt() {
/* 1007 */       return Int2FloatOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractIntSet { private KeySet() {}
/*      */     
/*      */     public IntIterator iterator() {
/* 1013 */       return new Int2FloatOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer consumer) {
/* 1018 */       if (Int2FloatOpenCustomHashMap.this.containsNullKey)
/* 1019 */         consumer.accept(Int2FloatOpenCustomHashMap.this.key[Int2FloatOpenCustomHashMap.this.n]); 
/* 1020 */       for (int pos = Int2FloatOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1021 */         int k = Int2FloatOpenCustomHashMap.this.key[pos];
/* 1022 */         if (k != 0)
/* 1023 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1028 */       return Int2FloatOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(int k) {
/* 1032 */       return Int2FloatOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(int k) {
/* 1036 */       int oldSize = Int2FloatOpenCustomHashMap.this.size;
/* 1037 */       Int2FloatOpenCustomHashMap.this.remove(k);
/* 1038 */       return (Int2FloatOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1042 */       Int2FloatOpenCustomHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public IntSet keySet() {
/* 1047 */     if (this.keys == null)
/* 1048 */       this.keys = new KeySet(); 
/* 1049 */     return this.keys;
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
/* 1066 */       return Int2FloatOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public FloatCollection values() {
/* 1071 */     if (this.values == null)
/* 1072 */       this.values = (FloatCollection)new AbstractFloatCollection()
/*      */         {
/*      */           public FloatIterator iterator() {
/* 1075 */             return new Int2FloatOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1079 */             return Int2FloatOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(float v) {
/* 1083 */             return Int2FloatOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1087 */             Int2FloatOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(DoubleConsumer consumer)
/*      */           {
/* 1092 */             if (Int2FloatOpenCustomHashMap.this.containsNullKey)
/* 1093 */               consumer.accept(Int2FloatOpenCustomHashMap.this.value[Int2FloatOpenCustomHashMap.this.n]); 
/* 1094 */             for (int pos = Int2FloatOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1095 */               if (Int2FloatOpenCustomHashMap.this.key[pos] != 0)
/* 1096 */                 consumer.accept(Int2FloatOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1099 */     return this.values;
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
/* 1116 */     return trim(this.size);
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
/* 1140 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1141 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1142 */       return true; 
/*      */     try {
/* 1144 */       rehash(l);
/* 1145 */     } catch (OutOfMemoryError cantDoIt) {
/* 1146 */       return false;
/*      */     } 
/* 1148 */     return true;
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
/* 1164 */     int[] key = this.key;
/* 1165 */     float[] value = this.value;
/* 1166 */     int mask = newN - 1;
/* 1167 */     int[] newKey = new int[newN + 1];
/* 1168 */     float[] newValue = new float[newN + 1];
/* 1169 */     int i = this.n;
/* 1170 */     for (int j = realSize(); j-- != 0; ) {
/* 1171 */       while (key[--i] == 0); int pos;
/* 1172 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != 0)
/* 1173 */         while (newKey[pos = pos + 1 & mask] != 0); 
/* 1174 */       newKey[pos] = key[i];
/* 1175 */       newValue[pos] = value[i];
/*      */     } 
/* 1177 */     newValue[newN] = value[this.n];
/* 1178 */     this.n = newN;
/* 1179 */     this.mask = mask;
/* 1180 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1181 */     this.key = newKey;
/* 1182 */     this.value = newValue;
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
/*      */   public Int2FloatOpenCustomHashMap clone() {
/*      */     Int2FloatOpenCustomHashMap c;
/*      */     try {
/* 1199 */       c = (Int2FloatOpenCustomHashMap)super.clone();
/* 1200 */     } catch (CloneNotSupportedException cantHappen) {
/* 1201 */       throw new InternalError();
/*      */     } 
/* 1203 */     c.keys = null;
/* 1204 */     c.values = null;
/* 1205 */     c.entries = null;
/* 1206 */     c.containsNullKey = this.containsNullKey;
/* 1207 */     c.key = (int[])this.key.clone();
/* 1208 */     c.value = (float[])this.value.clone();
/* 1209 */     c.strategy = this.strategy;
/* 1210 */     return c;
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
/* 1223 */     int h = 0;
/* 1224 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1225 */       while (this.key[i] == 0)
/* 1226 */         i++; 
/* 1227 */       t = this.strategy.hashCode(this.key[i]);
/* 1228 */       t ^= HashCommon.float2int(this.value[i]);
/* 1229 */       h += t;
/* 1230 */       i++;
/*      */     } 
/*      */     
/* 1233 */     if (this.containsNullKey)
/* 1234 */       h += HashCommon.float2int(this.value[this.n]); 
/* 1235 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1238 */     int[] key = this.key;
/* 1239 */     float[] value = this.value;
/* 1240 */     MapIterator i = new MapIterator();
/* 1241 */     s.defaultWriteObject();
/* 1242 */     for (int j = this.size; j-- != 0; ) {
/* 1243 */       int e = i.nextEntry();
/* 1244 */       s.writeInt(key[e]);
/* 1245 */       s.writeFloat(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1250 */     s.defaultReadObject();
/* 1251 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1252 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1253 */     this.mask = this.n - 1;
/* 1254 */     int[] key = this.key = new int[this.n + 1];
/* 1255 */     float[] value = this.value = new float[this.n + 1];
/*      */ 
/*      */     
/* 1258 */     for (int i = this.size; i-- != 0; ) {
/* 1259 */       int pos, k = s.readInt();
/* 1260 */       float v = s.readFloat();
/* 1261 */       if (this.strategy.equals(k, 0)) {
/* 1262 */         pos = this.n;
/* 1263 */         this.containsNullKey = true;
/*      */       } else {
/* 1265 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1266 */         while (key[pos] != 0)
/* 1267 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1269 */       key[pos] = k;
/* 1270 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\Int2FloatOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */