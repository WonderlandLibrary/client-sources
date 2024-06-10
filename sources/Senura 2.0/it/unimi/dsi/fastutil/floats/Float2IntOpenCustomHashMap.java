/*      */ package it.unimi.dsi.fastutil.floats;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
/*      */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*      */ import it.unimi.dsi.fastutil.ints.IntIterator;
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
/*      */ public class Float2IntOpenCustomHashMap
/*      */   extends AbstractFloat2IntMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient float[] key;
/*      */   protected transient int[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected FloatHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Float2IntMap.FastEntrySet entries;
/*      */   protected transient FloatSet keys;
/*      */   protected transient IntCollection values;
/*      */   
/*      */   public Float2IntOpenCustomHashMap(int expected, float f, FloatHash.Strategy strategy) {
/*  103 */     this.strategy = strategy;
/*  104 */     if (f <= 0.0F || f > 1.0F)
/*  105 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  106 */     if (expected < 0)
/*  107 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  108 */     this.f = f;
/*  109 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  110 */     this.mask = this.n - 1;
/*  111 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  112 */     this.key = new float[this.n + 1];
/*  113 */     this.value = new int[this.n + 1];
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
/*      */   public Float2IntOpenCustomHashMap(int expected, FloatHash.Strategy strategy) {
/*  125 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2IntOpenCustomHashMap(FloatHash.Strategy strategy) {
/*  136 */     this(16, 0.75F, strategy);
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
/*      */   public Float2IntOpenCustomHashMap(Map<? extends Float, ? extends Integer> m, float f, FloatHash.Strategy strategy) {
/*  150 */     this(m.size(), f, strategy);
/*  151 */     putAll(m);
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
/*      */   public Float2IntOpenCustomHashMap(Map<? extends Float, ? extends Integer> m, FloatHash.Strategy strategy) {
/*  164 */     this(m, 0.75F, strategy);
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
/*      */   public Float2IntOpenCustomHashMap(Float2IntMap m, float f, FloatHash.Strategy strategy) {
/*  178 */     this(m.size(), f, strategy);
/*  179 */     putAll(m);
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
/*      */   public Float2IntOpenCustomHashMap(Float2IntMap m, FloatHash.Strategy strategy) {
/*  192 */     this(m, 0.75F, strategy);
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
/*      */   public Float2IntOpenCustomHashMap(float[] k, int[] v, float f, FloatHash.Strategy strategy) {
/*  210 */     this(k.length, f, strategy);
/*  211 */     if (k.length != v.length) {
/*  212 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  214 */     for (int i = 0; i < k.length; i++) {
/*  215 */       put(k[i], v[i]);
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
/*      */   public Float2IntOpenCustomHashMap(float[] k, int[] v, FloatHash.Strategy strategy) {
/*  232 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FloatHash.Strategy strategy() {
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
/*      */   private int removeEntry(int pos) {
/*  257 */     int oldValue = this.value[pos];
/*  258 */     this.size--;
/*  259 */     shiftKeys(pos);
/*  260 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  261 */       rehash(this.n / 2); 
/*  262 */     return oldValue;
/*      */   }
/*      */   private int removeNullEntry() {
/*  265 */     this.containsNullKey = false;
/*  266 */     int oldValue = this.value[this.n];
/*  267 */     this.size--;
/*  268 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  269 */       rehash(this.n / 2); 
/*  270 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Float, ? extends Integer> m) {
/*  274 */     if (this.f <= 0.5D) {
/*  275 */       ensureCapacity(m.size());
/*      */     } else {
/*  277 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  279 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(float k) {
/*  283 */     if (this.strategy.equals(k, 0.0F)) {
/*  284 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  286 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  289 */     if (Float.floatToIntBits(
/*  290 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  291 */       return -(pos + 1); 
/*  292 */     if (this.strategy.equals(k, curr)) {
/*  293 */       return pos;
/*      */     }
/*      */     while (true) {
/*  296 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  297 */         return -(pos + 1); 
/*  298 */       if (this.strategy.equals(k, curr))
/*  299 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, float k, int v) {
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
/*      */   public int put(float k, int v) {
/*  314 */     int pos = find(k);
/*  315 */     if (pos < 0) {
/*  316 */       insert(-pos - 1, k, v);
/*  317 */       return this.defRetValue;
/*      */     } 
/*  319 */     int oldValue = this.value[pos];
/*  320 */     this.value[pos] = v;
/*  321 */     return oldValue;
/*      */   }
/*      */   private int addToValue(int pos, int incr) {
/*  324 */     int oldValue = this.value[pos];
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
/*      */   public int addTo(float k, int incr) {
/*      */     int pos;
/*  346 */     if (this.strategy.equals(k, 0.0F)) {
/*  347 */       if (this.containsNullKey)
/*  348 */         return addToValue(this.n, incr); 
/*  349 */       pos = this.n;
/*  350 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  353 */       float[] key = this.key;
/*      */       float curr;
/*  355 */       if (Float.floatToIntBits(
/*  356 */           curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != 0) {
/*  357 */         if (this.strategy.equals(curr, k))
/*  358 */           return addToValue(pos, incr); 
/*  359 */         while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) {
/*  360 */           if (this.strategy.equals(curr, k))
/*  361 */             return addToValue(pos, incr); 
/*      */         } 
/*      */       } 
/*  364 */     }  this.key[pos] = k;
/*  365 */     this.value[pos] = this.defRetValue + incr;
/*  366 */     if (this.size++ >= this.maxFill) {
/*  367 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  370 */     return this.defRetValue;
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
/*  383 */     float[] key = this.key; while (true) {
/*      */       float curr; int last;
/*  385 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  387 */         if (Float.floatToIntBits(curr = key[pos]) == 0) {
/*  388 */           key[last] = 0.0F;
/*      */           return;
/*      */         } 
/*  391 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  392 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  394 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  396 */       key[last] = curr;
/*  397 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int remove(float k) {
/*  403 */     if (this.strategy.equals(k, 0.0F)) {
/*  404 */       if (this.containsNullKey)
/*  405 */         return removeNullEntry(); 
/*  406 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  409 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  412 */     if (Float.floatToIntBits(
/*  413 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  414 */       return this.defRetValue; 
/*  415 */     if (this.strategy.equals(k, curr))
/*  416 */       return removeEntry(pos); 
/*      */     while (true) {
/*  418 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  419 */         return this.defRetValue; 
/*  420 */       if (this.strategy.equals(k, curr)) {
/*  421 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public int get(float k) {
/*  427 */     if (this.strategy.equals(k, 0.0F)) {
/*  428 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  430 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  433 */     if (Float.floatToIntBits(
/*  434 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  435 */       return this.defRetValue; 
/*  436 */     if (this.strategy.equals(k, curr)) {
/*  437 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  440 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  441 */         return this.defRetValue; 
/*  442 */       if (this.strategy.equals(k, curr)) {
/*  443 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(float k) {
/*  449 */     if (this.strategy.equals(k, 0.0F)) {
/*  450 */       return this.containsNullKey;
/*      */     }
/*  452 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  455 */     if (Float.floatToIntBits(
/*  456 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  457 */       return false; 
/*  458 */     if (this.strategy.equals(k, curr)) {
/*  459 */       return true;
/*      */     }
/*      */     while (true) {
/*  462 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  463 */         return false; 
/*  464 */       if (this.strategy.equals(k, curr))
/*  465 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(int v) {
/*  470 */     int[] value = this.value;
/*  471 */     float[] key = this.key;
/*  472 */     if (this.containsNullKey && value[this.n] == v)
/*  473 */       return true; 
/*  474 */     for (int i = this.n; i-- != 0;) {
/*  475 */       if (Float.floatToIntBits(key[i]) != 0 && value[i] == v)
/*  476 */         return true; 
/*  477 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getOrDefault(float k, int defaultValue) {
/*  483 */     if (this.strategy.equals(k, 0.0F)) {
/*  484 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  486 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  489 */     if (Float.floatToIntBits(
/*  490 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  491 */       return defaultValue; 
/*  492 */     if (this.strategy.equals(k, curr)) {
/*  493 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  496 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  497 */         return defaultValue; 
/*  498 */       if (this.strategy.equals(k, curr)) {
/*  499 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public int putIfAbsent(float k, int v) {
/*  505 */     int pos = find(k);
/*  506 */     if (pos >= 0)
/*  507 */       return this.value[pos]; 
/*  508 */     insert(-pos - 1, k, v);
/*  509 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(float k, int v) {
/*  515 */     if (this.strategy.equals(k, 0.0F)) {
/*  516 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  517 */         removeNullEntry();
/*  518 */         return true;
/*      */       } 
/*  520 */       return false;
/*      */     } 
/*      */     
/*  523 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  526 */     if (Float.floatToIntBits(
/*  527 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  528 */       return false; 
/*  529 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  530 */       removeEntry(pos);
/*  531 */       return true;
/*      */     } 
/*      */     while (true) {
/*  534 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  535 */         return false; 
/*  536 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  537 */         removeEntry(pos);
/*  538 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(float k, int oldValue, int v) {
/*  545 */     int pos = find(k);
/*  546 */     if (pos < 0 || oldValue != this.value[pos])
/*  547 */       return false; 
/*  548 */     this.value[pos] = v;
/*  549 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public int replace(float k, int v) {
/*  554 */     int pos = find(k);
/*  555 */     if (pos < 0)
/*  556 */       return this.defRetValue; 
/*  557 */     int oldValue = this.value[pos];
/*  558 */     this.value[pos] = v;
/*  559 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public int computeIfAbsent(float k, DoubleToIntFunction mappingFunction) {
/*  564 */     Objects.requireNonNull(mappingFunction);
/*  565 */     int pos = find(k);
/*  566 */     if (pos >= 0)
/*  567 */       return this.value[pos]; 
/*  568 */     int newValue = mappingFunction.applyAsInt(k);
/*  569 */     insert(-pos - 1, k, newValue);
/*  570 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int computeIfAbsentNullable(float k, DoubleFunction<? extends Integer> mappingFunction) {
/*  576 */     Objects.requireNonNull(mappingFunction);
/*  577 */     int pos = find(k);
/*  578 */     if (pos >= 0)
/*  579 */       return this.value[pos]; 
/*  580 */     Integer newValue = mappingFunction.apply(k);
/*  581 */     if (newValue == null)
/*  582 */       return this.defRetValue; 
/*  583 */     int v = newValue.intValue();
/*  584 */     insert(-pos - 1, k, v);
/*  585 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int computeIfPresent(float k, BiFunction<? super Float, ? super Integer, ? extends Integer> remappingFunction) {
/*  591 */     Objects.requireNonNull(remappingFunction);
/*  592 */     int pos = find(k);
/*  593 */     if (pos < 0)
/*  594 */       return this.defRetValue; 
/*  595 */     Integer newValue = remappingFunction.apply(Float.valueOf(k), Integer.valueOf(this.value[pos]));
/*  596 */     if (newValue == null) {
/*  597 */       if (this.strategy.equals(k, 0.0F)) {
/*  598 */         removeNullEntry();
/*      */       } else {
/*  600 */         removeEntry(pos);
/*  601 */       }  return this.defRetValue;
/*      */     } 
/*  603 */     this.value[pos] = newValue.intValue(); return newValue.intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int compute(float k, BiFunction<? super Float, ? super Integer, ? extends Integer> remappingFunction) {
/*  609 */     Objects.requireNonNull(remappingFunction);
/*  610 */     int pos = find(k);
/*  611 */     Integer newValue = remappingFunction.apply(Float.valueOf(k), 
/*  612 */         (pos >= 0) ? Integer.valueOf(this.value[pos]) : null);
/*  613 */     if (newValue == null) {
/*  614 */       if (pos >= 0)
/*  615 */         if (this.strategy.equals(k, 0.0F)) {
/*  616 */           removeNullEntry();
/*      */         } else {
/*  618 */           removeEntry(pos);
/*      */         }  
/*  620 */       return this.defRetValue;
/*      */     } 
/*  622 */     int newVal = newValue.intValue();
/*  623 */     if (pos < 0) {
/*  624 */       insert(-pos - 1, k, newVal);
/*  625 */       return newVal;
/*      */     } 
/*  627 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int merge(float k, int v, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
/*  633 */     Objects.requireNonNull(remappingFunction);
/*  634 */     int pos = find(k);
/*  635 */     if (pos < 0) {
/*  636 */       insert(-pos - 1, k, v);
/*  637 */       return v;
/*      */     } 
/*  639 */     Integer newValue = remappingFunction.apply(Integer.valueOf(this.value[pos]), Integer.valueOf(v));
/*  640 */     if (newValue == null) {
/*  641 */       if (this.strategy.equals(k, 0.0F)) {
/*  642 */         removeNullEntry();
/*      */       } else {
/*  644 */         removeEntry(pos);
/*  645 */       }  return this.defRetValue;
/*      */     } 
/*  647 */     this.value[pos] = newValue.intValue(); return newValue.intValue();
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
/*  658 */     if (this.size == 0)
/*      */       return; 
/*  660 */     this.size = 0;
/*  661 */     this.containsNullKey = false;
/*  662 */     Arrays.fill(this.key, 0.0F);
/*      */   }
/*      */   
/*      */   public int size() {
/*  666 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  670 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Float2IntMap.Entry, Map.Entry<Float, Integer>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  682 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public float getFloatKey() {
/*  688 */       return Float2IntOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public int getIntValue() {
/*  692 */       return Float2IntOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public int setValue(int v) {
/*  696 */       int oldValue = Float2IntOpenCustomHashMap.this.value[this.index];
/*  697 */       Float2IntOpenCustomHashMap.this.value[this.index] = v;
/*  698 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float getKey() {
/*  708 */       return Float.valueOf(Float2IntOpenCustomHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer getValue() {
/*  718 */       return Integer.valueOf(Float2IntOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer setValue(Integer v) {
/*  728 */       return Integer.valueOf(setValue(v.intValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  733 */       if (!(o instanceof Map.Entry))
/*  734 */         return false; 
/*  735 */       Map.Entry<Float, Integer> e = (Map.Entry<Float, Integer>)o;
/*  736 */       return (Float2IntOpenCustomHashMap.this.strategy.equals(Float2IntOpenCustomHashMap.this.key[this.index], ((Float)e.getKey()).floatValue()) && Float2IntOpenCustomHashMap.this.value[this.index] == ((Integer)e
/*  737 */         .getValue()).intValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  741 */       return Float2IntOpenCustomHashMap.this.strategy.hashCode(Float2IntOpenCustomHashMap.this.key[this.index]) ^ Float2IntOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/*  745 */       return Float2IntOpenCustomHashMap.this.key[this.index] + "=>" + Float2IntOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  755 */     int pos = Float2IntOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  762 */     int last = -1;
/*      */     
/*  764 */     int c = Float2IntOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  768 */     boolean mustReturnNullKey = Float2IntOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     FloatArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  775 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  778 */       if (!hasNext())
/*  779 */         throw new NoSuchElementException(); 
/*  780 */       this.c--;
/*  781 */       if (this.mustReturnNullKey) {
/*  782 */         this.mustReturnNullKey = false;
/*  783 */         return this.last = Float2IntOpenCustomHashMap.this.n;
/*      */       } 
/*  785 */       float[] key = Float2IntOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  787 */         if (--this.pos < 0) {
/*      */           
/*  789 */           this.last = Integer.MIN_VALUE;
/*  790 */           float k = this.wrapped.getFloat(-this.pos - 1);
/*  791 */           int p = HashCommon.mix(Float2IntOpenCustomHashMap.this.strategy.hashCode(k)) & Float2IntOpenCustomHashMap.this.mask;
/*  792 */           while (!Float2IntOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  793 */             p = p + 1 & Float2IntOpenCustomHashMap.this.mask; 
/*  794 */           return p;
/*      */         } 
/*  796 */         if (Float.floatToIntBits(key[this.pos]) != 0) {
/*  797 */           return this.last = this.pos;
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
/*  811 */       float[] key = Float2IntOpenCustomHashMap.this.key; while (true) {
/*      */         float curr; int last;
/*  813 */         pos = (last = pos) + 1 & Float2IntOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  815 */           if (Float.floatToIntBits(curr = key[pos]) == 0) {
/*  816 */             key[last] = 0.0F;
/*      */             return;
/*      */           } 
/*  819 */           int slot = HashCommon.mix(Float2IntOpenCustomHashMap.this.strategy.hashCode(curr)) & Float2IntOpenCustomHashMap.this.mask;
/*  820 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  822 */           pos = pos + 1 & Float2IntOpenCustomHashMap.this.mask;
/*      */         } 
/*  824 */         if (pos < last) {
/*  825 */           if (this.wrapped == null)
/*  826 */             this.wrapped = new FloatArrayList(2); 
/*  827 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  829 */         key[last] = curr;
/*  830 */         Float2IntOpenCustomHashMap.this.value[last] = Float2IntOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  834 */       if (this.last == -1)
/*  835 */         throw new IllegalStateException(); 
/*  836 */       if (this.last == Float2IntOpenCustomHashMap.this.n) {
/*  837 */         Float2IntOpenCustomHashMap.this.containsNullKey = false;
/*  838 */       } else if (this.pos >= 0) {
/*  839 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  842 */         Float2IntOpenCustomHashMap.this.remove(this.wrapped.getFloat(-this.pos - 1));
/*  843 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  846 */       Float2IntOpenCustomHashMap.this.size--;
/*  847 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  852 */       int i = n;
/*  853 */       while (i-- != 0 && hasNext())
/*  854 */         nextEntry(); 
/*  855 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Float2IntMap.Entry> { private Float2IntOpenCustomHashMap.MapEntry entry;
/*      */     
/*      */     public Float2IntOpenCustomHashMap.MapEntry next() {
/*  862 */       return this.entry = new Float2IntOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  866 */       super.remove();
/*  867 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Float2IntMap.Entry> { private FastEntryIterator() {
/*  871 */       this.entry = new Float2IntOpenCustomHashMap.MapEntry();
/*      */     } private final Float2IntOpenCustomHashMap.MapEntry entry;
/*      */     public Float2IntOpenCustomHashMap.MapEntry next() {
/*  874 */       this.entry.index = nextEntry();
/*  875 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Float2IntMap.Entry> implements Float2IntMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Float2IntMap.Entry> iterator() {
/*  881 */       return new Float2IntOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Float2IntMap.Entry> fastIterator() {
/*  885 */       return new Float2IntOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  890 */       if (!(o instanceof Map.Entry))
/*  891 */         return false; 
/*  892 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  893 */       if (e.getKey() == null || !(e.getKey() instanceof Float))
/*  894 */         return false; 
/*  895 */       if (e.getValue() == null || !(e.getValue() instanceof Integer))
/*  896 */         return false; 
/*  897 */       float k = ((Float)e.getKey()).floatValue();
/*  898 */       int v = ((Integer)e.getValue()).intValue();
/*  899 */       if (Float2IntOpenCustomHashMap.this.strategy.equals(k, 0.0F)) {
/*  900 */         return (Float2IntOpenCustomHashMap.this.containsNullKey && Float2IntOpenCustomHashMap.this.value[Float2IntOpenCustomHashMap.this.n] == v);
/*      */       }
/*  902 */       float[] key = Float2IntOpenCustomHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/*  905 */       if (Float.floatToIntBits(
/*  906 */           curr = key[pos = HashCommon.mix(Float2IntOpenCustomHashMap.this.strategy.hashCode(k)) & Float2IntOpenCustomHashMap.this.mask]) == 0)
/*  907 */         return false; 
/*  908 */       if (Float2IntOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  909 */         return (Float2IntOpenCustomHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  912 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2IntOpenCustomHashMap.this.mask]) == 0)
/*  913 */           return false; 
/*  914 */         if (Float2IntOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  915 */           return (Float2IntOpenCustomHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  921 */       if (!(o instanceof Map.Entry))
/*  922 */         return false; 
/*  923 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  924 */       if (e.getKey() == null || !(e.getKey() instanceof Float))
/*  925 */         return false; 
/*  926 */       if (e.getValue() == null || !(e.getValue() instanceof Integer))
/*  927 */         return false; 
/*  928 */       float k = ((Float)e.getKey()).floatValue();
/*  929 */       int v = ((Integer)e.getValue()).intValue();
/*  930 */       if (Float2IntOpenCustomHashMap.this.strategy.equals(k, 0.0F)) {
/*  931 */         if (Float2IntOpenCustomHashMap.this.containsNullKey && Float2IntOpenCustomHashMap.this.value[Float2IntOpenCustomHashMap.this.n] == v) {
/*  932 */           Float2IntOpenCustomHashMap.this.removeNullEntry();
/*  933 */           return true;
/*      */         } 
/*  935 */         return false;
/*      */       } 
/*      */       
/*  938 */       float[] key = Float2IntOpenCustomHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/*  941 */       if (Float.floatToIntBits(
/*  942 */           curr = key[pos = HashCommon.mix(Float2IntOpenCustomHashMap.this.strategy.hashCode(k)) & Float2IntOpenCustomHashMap.this.mask]) == 0)
/*  943 */         return false; 
/*  944 */       if (Float2IntOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  945 */         if (Float2IntOpenCustomHashMap.this.value[pos] == v) {
/*  946 */           Float2IntOpenCustomHashMap.this.removeEntry(pos);
/*  947 */           return true;
/*      */         } 
/*  949 */         return false;
/*      */       } 
/*      */       while (true) {
/*  952 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2IntOpenCustomHashMap.this.mask]) == 0)
/*  953 */           return false; 
/*  954 */         if (Float2IntOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  955 */           Float2IntOpenCustomHashMap.this.value[pos] == v) {
/*  956 */           Float2IntOpenCustomHashMap.this.removeEntry(pos);
/*  957 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  964 */       return Float2IntOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  968 */       Float2IntOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Float2IntMap.Entry> consumer) {
/*  973 */       if (Float2IntOpenCustomHashMap.this.containsNullKey)
/*  974 */         consumer.accept(new AbstractFloat2IntMap.BasicEntry(Float2IntOpenCustomHashMap.this.key[Float2IntOpenCustomHashMap.this.n], Float2IntOpenCustomHashMap.this.value[Float2IntOpenCustomHashMap.this.n])); 
/*  975 */       for (int pos = Float2IntOpenCustomHashMap.this.n; pos-- != 0;) {
/*  976 */         if (Float.floatToIntBits(Float2IntOpenCustomHashMap.this.key[pos]) != 0)
/*  977 */           consumer.accept(new AbstractFloat2IntMap.BasicEntry(Float2IntOpenCustomHashMap.this.key[pos], Float2IntOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Float2IntMap.Entry> consumer) {
/*  982 */       AbstractFloat2IntMap.BasicEntry entry = new AbstractFloat2IntMap.BasicEntry();
/*  983 */       if (Float2IntOpenCustomHashMap.this.containsNullKey) {
/*  984 */         entry.key = Float2IntOpenCustomHashMap.this.key[Float2IntOpenCustomHashMap.this.n];
/*  985 */         entry.value = Float2IntOpenCustomHashMap.this.value[Float2IntOpenCustomHashMap.this.n];
/*  986 */         consumer.accept(entry);
/*      */       } 
/*  988 */       for (int pos = Float2IntOpenCustomHashMap.this.n; pos-- != 0;) {
/*  989 */         if (Float.floatToIntBits(Float2IntOpenCustomHashMap.this.key[pos]) != 0) {
/*  990 */           entry.key = Float2IntOpenCustomHashMap.this.key[pos];
/*  991 */           entry.value = Float2IntOpenCustomHashMap.this.value[pos];
/*  992 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Float2IntMap.FastEntrySet float2IntEntrySet() {
/*  998 */     if (this.entries == null)
/*  999 */       this.entries = new MapEntrySet(); 
/* 1000 */     return this.entries;
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
/* 1017 */       return Float2IntOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractFloatSet { private KeySet() {}
/*      */     
/*      */     public FloatIterator iterator() {
/* 1023 */       return new Float2IntOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/* 1028 */       if (Float2IntOpenCustomHashMap.this.containsNullKey)
/* 1029 */         consumer.accept(Float2IntOpenCustomHashMap.this.key[Float2IntOpenCustomHashMap.this.n]); 
/* 1030 */       for (int pos = Float2IntOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1031 */         float k = Float2IntOpenCustomHashMap.this.key[pos];
/* 1032 */         if (Float.floatToIntBits(k) != 0)
/* 1033 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1038 */       return Float2IntOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(float k) {
/* 1042 */       return Float2IntOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(float k) {
/* 1046 */       int oldSize = Float2IntOpenCustomHashMap.this.size;
/* 1047 */       Float2IntOpenCustomHashMap.this.remove(k);
/* 1048 */       return (Float2IntOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1052 */       Float2IntOpenCustomHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public FloatSet keySet() {
/* 1057 */     if (this.keys == null)
/* 1058 */       this.keys = new KeySet(); 
/* 1059 */     return this.keys;
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
/*      */     implements IntIterator
/*      */   {
/*      */     public int nextInt() {
/* 1076 */       return Float2IntOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public IntCollection values() {
/* 1081 */     if (this.values == null)
/* 1082 */       this.values = (IntCollection)new AbstractIntCollection()
/*      */         {
/*      */           public IntIterator iterator() {
/* 1085 */             return new Float2IntOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1089 */             return Float2IntOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(int v) {
/* 1093 */             return Float2IntOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1097 */             Float2IntOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(IntConsumer consumer)
/*      */           {
/* 1102 */             if (Float2IntOpenCustomHashMap.this.containsNullKey)
/* 1103 */               consumer.accept(Float2IntOpenCustomHashMap.this.value[Float2IntOpenCustomHashMap.this.n]); 
/* 1104 */             for (int pos = Float2IntOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1105 */               if (Float.floatToIntBits(Float2IntOpenCustomHashMap.this.key[pos]) != 0)
/* 1106 */                 consumer.accept(Float2IntOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1109 */     return this.values;
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
/* 1126 */     return trim(this.size);
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
/* 1150 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1151 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1152 */       return true; 
/*      */     try {
/* 1154 */       rehash(l);
/* 1155 */     } catch (OutOfMemoryError cantDoIt) {
/* 1156 */       return false;
/*      */     } 
/* 1158 */     return true;
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
/* 1174 */     float[] key = this.key;
/* 1175 */     int[] value = this.value;
/* 1176 */     int mask = newN - 1;
/* 1177 */     float[] newKey = new float[newN + 1];
/* 1178 */     int[] newValue = new int[newN + 1];
/* 1179 */     int i = this.n;
/* 1180 */     for (int j = realSize(); j-- != 0; ) {
/* 1181 */       while (Float.floatToIntBits(key[--i]) == 0); int pos;
/* 1182 */       if (Float.floatToIntBits(newKey[
/* 1183 */             pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask]) != 0)
/* 1184 */         while (Float.floatToIntBits(newKey[pos = pos + 1 & mask]) != 0); 
/* 1185 */       newKey[pos] = key[i];
/* 1186 */       newValue[pos] = value[i];
/*      */     } 
/* 1188 */     newValue[newN] = value[this.n];
/* 1189 */     this.n = newN;
/* 1190 */     this.mask = mask;
/* 1191 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1192 */     this.key = newKey;
/* 1193 */     this.value = newValue;
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
/*      */   public Float2IntOpenCustomHashMap clone() {
/*      */     Float2IntOpenCustomHashMap c;
/*      */     try {
/* 1210 */       c = (Float2IntOpenCustomHashMap)super.clone();
/* 1211 */     } catch (CloneNotSupportedException cantHappen) {
/* 1212 */       throw new InternalError();
/*      */     } 
/* 1214 */     c.keys = null;
/* 1215 */     c.values = null;
/* 1216 */     c.entries = null;
/* 1217 */     c.containsNullKey = this.containsNullKey;
/* 1218 */     c.key = (float[])this.key.clone();
/* 1219 */     c.value = (int[])this.value.clone();
/* 1220 */     c.strategy = this.strategy;
/* 1221 */     return c;
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
/* 1234 */     int h = 0;
/* 1235 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1236 */       while (Float.floatToIntBits(this.key[i]) == 0)
/* 1237 */         i++; 
/* 1238 */       t = this.strategy.hashCode(this.key[i]);
/* 1239 */       t ^= this.value[i];
/* 1240 */       h += t;
/* 1241 */       i++;
/*      */     } 
/*      */     
/* 1244 */     if (this.containsNullKey)
/* 1245 */       h += this.value[this.n]; 
/* 1246 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1249 */     float[] key = this.key;
/* 1250 */     int[] value = this.value;
/* 1251 */     MapIterator i = new MapIterator();
/* 1252 */     s.defaultWriteObject();
/* 1253 */     for (int j = this.size; j-- != 0; ) {
/* 1254 */       int e = i.nextEntry();
/* 1255 */       s.writeFloat(key[e]);
/* 1256 */       s.writeInt(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1261 */     s.defaultReadObject();
/* 1262 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1263 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1264 */     this.mask = this.n - 1;
/* 1265 */     float[] key = this.key = new float[this.n + 1];
/* 1266 */     int[] value = this.value = new int[this.n + 1];
/*      */ 
/*      */     
/* 1269 */     for (int i = this.size; i-- != 0; ) {
/* 1270 */       int pos; float k = s.readFloat();
/* 1271 */       int v = s.readInt();
/* 1272 */       if (this.strategy.equals(k, 0.0F)) {
/* 1273 */         pos = this.n;
/* 1274 */         this.containsNullKey = true;
/*      */       } else {
/* 1276 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1277 */         while (Float.floatToIntBits(key[pos]) != 0)
/* 1278 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1280 */       key[pos] = k;
/* 1281 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\Float2IntOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */