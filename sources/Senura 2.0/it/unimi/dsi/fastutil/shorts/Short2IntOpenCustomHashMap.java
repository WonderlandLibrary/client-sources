/*      */ package it.unimi.dsi.fastutil.shorts;
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
/*      */ public class Short2IntOpenCustomHashMap
/*      */   extends AbstractShort2IntMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient short[] key;
/*      */   protected transient int[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected ShortHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Short2IntMap.FastEntrySet entries;
/*      */   protected transient ShortSet keys;
/*      */   protected transient IntCollection values;
/*      */   
/*      */   public Short2IntOpenCustomHashMap(int expected, float f, ShortHash.Strategy strategy) {
/*  103 */     this.strategy = strategy;
/*  104 */     if (f <= 0.0F || f > 1.0F)
/*  105 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  106 */     if (expected < 0)
/*  107 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  108 */     this.f = f;
/*  109 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  110 */     this.mask = this.n - 1;
/*  111 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  112 */     this.key = new short[this.n + 1];
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
/*      */   public Short2IntOpenCustomHashMap(int expected, ShortHash.Strategy strategy) {
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
/*      */   public Short2IntOpenCustomHashMap(ShortHash.Strategy strategy) {
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
/*      */   public Short2IntOpenCustomHashMap(Map<? extends Short, ? extends Integer> m, float f, ShortHash.Strategy strategy) {
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
/*      */   public Short2IntOpenCustomHashMap(Map<? extends Short, ? extends Integer> m, ShortHash.Strategy strategy) {
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
/*      */   public Short2IntOpenCustomHashMap(Short2IntMap m, float f, ShortHash.Strategy strategy) {
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
/*      */   public Short2IntOpenCustomHashMap(Short2IntMap m, ShortHash.Strategy strategy) {
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
/*      */   public Short2IntOpenCustomHashMap(short[] k, int[] v, float f, ShortHash.Strategy strategy) {
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
/*      */   public Short2IntOpenCustomHashMap(short[] k, int[] v, ShortHash.Strategy strategy) {
/*  232 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ShortHash.Strategy strategy() {
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
/*      */   public void putAll(Map<? extends Short, ? extends Integer> m) {
/*  274 */     if (this.f <= 0.5D) {
/*  275 */       ensureCapacity(m.size());
/*      */     } else {
/*  277 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  279 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(short k) {
/*  283 */     if (this.strategy.equals(k, (short)0)) {
/*  284 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  286 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  289 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  290 */       return -(pos + 1); 
/*  291 */     if (this.strategy.equals(k, curr)) {
/*  292 */       return pos;
/*      */     }
/*      */     while (true) {
/*  295 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  296 */         return -(pos + 1); 
/*  297 */       if (this.strategy.equals(k, curr))
/*  298 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, short k, int v) {
/*  302 */     if (pos == this.n)
/*  303 */       this.containsNullKey = true; 
/*  304 */     this.key[pos] = k;
/*  305 */     this.value[pos] = v;
/*  306 */     if (this.size++ >= this.maxFill) {
/*  307 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public int put(short k, int v) {
/*  313 */     int pos = find(k);
/*  314 */     if (pos < 0) {
/*  315 */       insert(-pos - 1, k, v);
/*  316 */       return this.defRetValue;
/*      */     } 
/*  318 */     int oldValue = this.value[pos];
/*  319 */     this.value[pos] = v;
/*  320 */     return oldValue;
/*      */   }
/*      */   private int addToValue(int pos, int incr) {
/*  323 */     int oldValue = this.value[pos];
/*  324 */     this.value[pos] = oldValue + incr;
/*  325 */     return oldValue;
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
/*      */   public int addTo(short k, int incr) {
/*      */     int pos;
/*  345 */     if (this.strategy.equals(k, (short)0)) {
/*  346 */       if (this.containsNullKey)
/*  347 */         return addToValue(this.n, incr); 
/*  348 */       pos = this.n;
/*  349 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  352 */       short[] key = this.key;
/*      */       short curr;
/*  354 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != 0) {
/*      */         
/*  356 */         if (this.strategy.equals(curr, k))
/*  357 */           return addToValue(pos, incr); 
/*  358 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
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
/*  382 */     short[] key = this.key; while (true) {
/*      */       short curr; int last;
/*  384 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  386 */         if ((curr = key[pos]) == 0) {
/*  387 */           key[last] = 0;
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
/*      */   public int remove(short k) {
/*  402 */     if (this.strategy.equals(k, (short)0)) {
/*  403 */       if (this.containsNullKey)
/*  404 */         return removeNullEntry(); 
/*  405 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  408 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  411 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  412 */       return this.defRetValue; 
/*  413 */     if (this.strategy.equals(k, curr))
/*  414 */       return removeEntry(pos); 
/*      */     while (true) {
/*  416 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  417 */         return this.defRetValue; 
/*  418 */       if (this.strategy.equals(k, curr)) {
/*  419 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public int get(short k) {
/*  425 */     if (this.strategy.equals(k, (short)0)) {
/*  426 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  428 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  431 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  432 */       return this.defRetValue; 
/*  433 */     if (this.strategy.equals(k, curr)) {
/*  434 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  437 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  438 */         return this.defRetValue; 
/*  439 */       if (this.strategy.equals(k, curr)) {
/*  440 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(short k) {
/*  446 */     if (this.strategy.equals(k, (short)0)) {
/*  447 */       return this.containsNullKey;
/*      */     }
/*  449 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  452 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  453 */       return false; 
/*  454 */     if (this.strategy.equals(k, curr)) {
/*  455 */       return true;
/*      */     }
/*      */     while (true) {
/*  458 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  459 */         return false; 
/*  460 */       if (this.strategy.equals(k, curr))
/*  461 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(int v) {
/*  466 */     int[] value = this.value;
/*  467 */     short[] key = this.key;
/*  468 */     if (this.containsNullKey && value[this.n] == v)
/*  469 */       return true; 
/*  470 */     for (int i = this.n; i-- != 0;) {
/*  471 */       if (key[i] != 0 && value[i] == v)
/*  472 */         return true; 
/*  473 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getOrDefault(short k, int defaultValue) {
/*  479 */     if (this.strategy.equals(k, (short)0)) {
/*  480 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  482 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  485 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  486 */       return defaultValue; 
/*  487 */     if (this.strategy.equals(k, curr)) {
/*  488 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  491 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  492 */         return defaultValue; 
/*  493 */       if (this.strategy.equals(k, curr)) {
/*  494 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public int putIfAbsent(short k, int v) {
/*  500 */     int pos = find(k);
/*  501 */     if (pos >= 0)
/*  502 */       return this.value[pos]; 
/*  503 */     insert(-pos - 1, k, v);
/*  504 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(short k, int v) {
/*  510 */     if (this.strategy.equals(k, (short)0)) {
/*  511 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  512 */         removeNullEntry();
/*  513 */         return true;
/*      */       } 
/*  515 */       return false;
/*      */     } 
/*      */     
/*  518 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  521 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  522 */       return false; 
/*  523 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  524 */       removeEntry(pos);
/*  525 */       return true;
/*      */     } 
/*      */     while (true) {
/*  528 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  529 */         return false; 
/*  530 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  531 */         removeEntry(pos);
/*  532 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(short k, int oldValue, int v) {
/*  539 */     int pos = find(k);
/*  540 */     if (pos < 0 || oldValue != this.value[pos])
/*  541 */       return false; 
/*  542 */     this.value[pos] = v;
/*  543 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public int replace(short k, int v) {
/*  548 */     int pos = find(k);
/*  549 */     if (pos < 0)
/*  550 */       return this.defRetValue; 
/*  551 */     int oldValue = this.value[pos];
/*  552 */     this.value[pos] = v;
/*  553 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public int computeIfAbsent(short k, IntUnaryOperator mappingFunction) {
/*  558 */     Objects.requireNonNull(mappingFunction);
/*  559 */     int pos = find(k);
/*  560 */     if (pos >= 0)
/*  561 */       return this.value[pos]; 
/*  562 */     int newValue = mappingFunction.applyAsInt(k);
/*  563 */     insert(-pos - 1, k, newValue);
/*  564 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int computeIfAbsentNullable(short k, IntFunction<? extends Integer> mappingFunction) {
/*  570 */     Objects.requireNonNull(mappingFunction);
/*  571 */     int pos = find(k);
/*  572 */     if (pos >= 0)
/*  573 */       return this.value[pos]; 
/*  574 */     Integer newValue = mappingFunction.apply(k);
/*  575 */     if (newValue == null)
/*  576 */       return this.defRetValue; 
/*  577 */     int v = newValue.intValue();
/*  578 */     insert(-pos - 1, k, v);
/*  579 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int computeIfPresent(short k, BiFunction<? super Short, ? super Integer, ? extends Integer> remappingFunction) {
/*  585 */     Objects.requireNonNull(remappingFunction);
/*  586 */     int pos = find(k);
/*  587 */     if (pos < 0)
/*  588 */       return this.defRetValue; 
/*  589 */     Integer newValue = remappingFunction.apply(Short.valueOf(k), Integer.valueOf(this.value[pos]));
/*  590 */     if (newValue == null) {
/*  591 */       if (this.strategy.equals(k, (short)0)) {
/*  592 */         removeNullEntry();
/*      */       } else {
/*  594 */         removeEntry(pos);
/*  595 */       }  return this.defRetValue;
/*      */     } 
/*  597 */     this.value[pos] = newValue.intValue(); return newValue.intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int compute(short k, BiFunction<? super Short, ? super Integer, ? extends Integer> remappingFunction) {
/*  603 */     Objects.requireNonNull(remappingFunction);
/*  604 */     int pos = find(k);
/*  605 */     Integer newValue = remappingFunction.apply(Short.valueOf(k), 
/*  606 */         (pos >= 0) ? Integer.valueOf(this.value[pos]) : null);
/*  607 */     if (newValue == null) {
/*  608 */       if (pos >= 0)
/*  609 */         if (this.strategy.equals(k, (short)0)) {
/*  610 */           removeNullEntry();
/*      */         } else {
/*  612 */           removeEntry(pos);
/*      */         }  
/*  614 */       return this.defRetValue;
/*      */     } 
/*  616 */     int newVal = newValue.intValue();
/*  617 */     if (pos < 0) {
/*  618 */       insert(-pos - 1, k, newVal);
/*  619 */       return newVal;
/*      */     } 
/*  621 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int merge(short k, int v, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
/*  627 */     Objects.requireNonNull(remappingFunction);
/*  628 */     int pos = find(k);
/*  629 */     if (pos < 0) {
/*  630 */       insert(-pos - 1, k, v);
/*  631 */       return v;
/*      */     } 
/*  633 */     Integer newValue = remappingFunction.apply(Integer.valueOf(this.value[pos]), Integer.valueOf(v));
/*  634 */     if (newValue == null) {
/*  635 */       if (this.strategy.equals(k, (short)0)) {
/*  636 */         removeNullEntry();
/*      */       } else {
/*  638 */         removeEntry(pos);
/*  639 */       }  return this.defRetValue;
/*      */     } 
/*  641 */     this.value[pos] = newValue.intValue(); return newValue.intValue();
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
/*  652 */     if (this.size == 0)
/*      */       return; 
/*  654 */     this.size = 0;
/*  655 */     this.containsNullKey = false;
/*  656 */     Arrays.fill(this.key, (short)0);
/*      */   }
/*      */   
/*      */   public int size() {
/*  660 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  664 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Short2IntMap.Entry, Map.Entry<Short, Integer>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  676 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public short getShortKey() {
/*  682 */       return Short2IntOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public int getIntValue() {
/*  686 */       return Short2IntOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public int setValue(int v) {
/*  690 */       int oldValue = Short2IntOpenCustomHashMap.this.value[this.index];
/*  691 */       Short2IntOpenCustomHashMap.this.value[this.index] = v;
/*  692 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Short getKey() {
/*  702 */       return Short.valueOf(Short2IntOpenCustomHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer getValue() {
/*  712 */       return Integer.valueOf(Short2IntOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer setValue(Integer v) {
/*  722 */       return Integer.valueOf(setValue(v.intValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  727 */       if (!(o instanceof Map.Entry))
/*  728 */         return false; 
/*  729 */       Map.Entry<Short, Integer> e = (Map.Entry<Short, Integer>)o;
/*  730 */       return (Short2IntOpenCustomHashMap.this.strategy.equals(Short2IntOpenCustomHashMap.this.key[this.index], ((Short)e.getKey()).shortValue()) && Short2IntOpenCustomHashMap.this.value[this.index] == ((Integer)e
/*  731 */         .getValue()).intValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  735 */       return Short2IntOpenCustomHashMap.this.strategy.hashCode(Short2IntOpenCustomHashMap.this.key[this.index]) ^ Short2IntOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/*  739 */       return Short2IntOpenCustomHashMap.this.key[this.index] + "=>" + Short2IntOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  749 */     int pos = Short2IntOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  756 */     int last = -1;
/*      */     
/*  758 */     int c = Short2IntOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  762 */     boolean mustReturnNullKey = Short2IntOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     ShortArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  769 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  772 */       if (!hasNext())
/*  773 */         throw new NoSuchElementException(); 
/*  774 */       this.c--;
/*  775 */       if (this.mustReturnNullKey) {
/*  776 */         this.mustReturnNullKey = false;
/*  777 */         return this.last = Short2IntOpenCustomHashMap.this.n;
/*      */       } 
/*  779 */       short[] key = Short2IntOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  781 */         if (--this.pos < 0) {
/*      */           
/*  783 */           this.last = Integer.MIN_VALUE;
/*  784 */           short k = this.wrapped.getShort(-this.pos - 1);
/*  785 */           int p = HashCommon.mix(Short2IntOpenCustomHashMap.this.strategy.hashCode(k)) & Short2IntOpenCustomHashMap.this.mask;
/*  786 */           while (!Short2IntOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  787 */             p = p + 1 & Short2IntOpenCustomHashMap.this.mask; 
/*  788 */           return p;
/*      */         } 
/*  790 */         if (key[this.pos] != 0) {
/*  791 */           return this.last = this.pos;
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
/*  805 */       short[] key = Short2IntOpenCustomHashMap.this.key; while (true) {
/*      */         short curr; int last;
/*  807 */         pos = (last = pos) + 1 & Short2IntOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  809 */           if ((curr = key[pos]) == 0) {
/*  810 */             key[last] = 0;
/*      */             return;
/*      */           } 
/*  813 */           int slot = HashCommon.mix(Short2IntOpenCustomHashMap.this.strategy.hashCode(curr)) & Short2IntOpenCustomHashMap.this.mask;
/*  814 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  816 */           pos = pos + 1 & Short2IntOpenCustomHashMap.this.mask;
/*      */         } 
/*  818 */         if (pos < last) {
/*  819 */           if (this.wrapped == null)
/*  820 */             this.wrapped = new ShortArrayList(2); 
/*  821 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  823 */         key[last] = curr;
/*  824 */         Short2IntOpenCustomHashMap.this.value[last] = Short2IntOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  828 */       if (this.last == -1)
/*  829 */         throw new IllegalStateException(); 
/*  830 */       if (this.last == Short2IntOpenCustomHashMap.this.n) {
/*  831 */         Short2IntOpenCustomHashMap.this.containsNullKey = false;
/*  832 */       } else if (this.pos >= 0) {
/*  833 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  836 */         Short2IntOpenCustomHashMap.this.remove(this.wrapped.getShort(-this.pos - 1));
/*  837 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  840 */       Short2IntOpenCustomHashMap.this.size--;
/*  841 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  846 */       int i = n;
/*  847 */       while (i-- != 0 && hasNext())
/*  848 */         nextEntry(); 
/*  849 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Short2IntMap.Entry> { private Short2IntOpenCustomHashMap.MapEntry entry;
/*      */     
/*      */     public Short2IntOpenCustomHashMap.MapEntry next() {
/*  856 */       return this.entry = new Short2IntOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  860 */       super.remove();
/*  861 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Short2IntMap.Entry> { private FastEntryIterator() {
/*  865 */       this.entry = new Short2IntOpenCustomHashMap.MapEntry();
/*      */     } private final Short2IntOpenCustomHashMap.MapEntry entry;
/*      */     public Short2IntOpenCustomHashMap.MapEntry next() {
/*  868 */       this.entry.index = nextEntry();
/*  869 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Short2IntMap.Entry> implements Short2IntMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Short2IntMap.Entry> iterator() {
/*  875 */       return new Short2IntOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Short2IntMap.Entry> fastIterator() {
/*  879 */       return new Short2IntOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  884 */       if (!(o instanceof Map.Entry))
/*  885 */         return false; 
/*  886 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  887 */       if (e.getKey() == null || !(e.getKey() instanceof Short))
/*  888 */         return false; 
/*  889 */       if (e.getValue() == null || !(e.getValue() instanceof Integer))
/*  890 */         return false; 
/*  891 */       short k = ((Short)e.getKey()).shortValue();
/*  892 */       int v = ((Integer)e.getValue()).intValue();
/*  893 */       if (Short2IntOpenCustomHashMap.this.strategy.equals(k, (short)0)) {
/*  894 */         return (Short2IntOpenCustomHashMap.this.containsNullKey && Short2IntOpenCustomHashMap.this.value[Short2IntOpenCustomHashMap.this.n] == v);
/*      */       }
/*  896 */       short[] key = Short2IntOpenCustomHashMap.this.key;
/*      */       short curr;
/*      */       int pos;
/*  899 */       if ((curr = key[pos = HashCommon.mix(Short2IntOpenCustomHashMap.this.strategy.hashCode(k)) & Short2IntOpenCustomHashMap.this.mask]) == 0)
/*      */       {
/*  901 */         return false; } 
/*  902 */       if (Short2IntOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  903 */         return (Short2IntOpenCustomHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  906 */         if ((curr = key[pos = pos + 1 & Short2IntOpenCustomHashMap.this.mask]) == 0)
/*  907 */           return false; 
/*  908 */         if (Short2IntOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  909 */           return (Short2IntOpenCustomHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  915 */       if (!(o instanceof Map.Entry))
/*  916 */         return false; 
/*  917 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  918 */       if (e.getKey() == null || !(e.getKey() instanceof Short))
/*  919 */         return false; 
/*  920 */       if (e.getValue() == null || !(e.getValue() instanceof Integer))
/*  921 */         return false; 
/*  922 */       short k = ((Short)e.getKey()).shortValue();
/*  923 */       int v = ((Integer)e.getValue()).intValue();
/*  924 */       if (Short2IntOpenCustomHashMap.this.strategy.equals(k, (short)0)) {
/*  925 */         if (Short2IntOpenCustomHashMap.this.containsNullKey && Short2IntOpenCustomHashMap.this.value[Short2IntOpenCustomHashMap.this.n] == v) {
/*  926 */           Short2IntOpenCustomHashMap.this.removeNullEntry();
/*  927 */           return true;
/*      */         } 
/*  929 */         return false;
/*      */       } 
/*      */       
/*  932 */       short[] key = Short2IntOpenCustomHashMap.this.key;
/*      */       short curr;
/*      */       int pos;
/*  935 */       if ((curr = key[pos = HashCommon.mix(Short2IntOpenCustomHashMap.this.strategy.hashCode(k)) & Short2IntOpenCustomHashMap.this.mask]) == 0)
/*      */       {
/*  937 */         return false; } 
/*  938 */       if (Short2IntOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  939 */         if (Short2IntOpenCustomHashMap.this.value[pos] == v) {
/*  940 */           Short2IntOpenCustomHashMap.this.removeEntry(pos);
/*  941 */           return true;
/*      */         } 
/*  943 */         return false;
/*      */       } 
/*      */       while (true) {
/*  946 */         if ((curr = key[pos = pos + 1 & Short2IntOpenCustomHashMap.this.mask]) == 0)
/*  947 */           return false; 
/*  948 */         if (Short2IntOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  949 */           Short2IntOpenCustomHashMap.this.value[pos] == v) {
/*  950 */           Short2IntOpenCustomHashMap.this.removeEntry(pos);
/*  951 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  958 */       return Short2IntOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  962 */       Short2IntOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Short2IntMap.Entry> consumer) {
/*  967 */       if (Short2IntOpenCustomHashMap.this.containsNullKey)
/*  968 */         consumer.accept(new AbstractShort2IntMap.BasicEntry(Short2IntOpenCustomHashMap.this.key[Short2IntOpenCustomHashMap.this.n], Short2IntOpenCustomHashMap.this.value[Short2IntOpenCustomHashMap.this.n])); 
/*  969 */       for (int pos = Short2IntOpenCustomHashMap.this.n; pos-- != 0;) {
/*  970 */         if (Short2IntOpenCustomHashMap.this.key[pos] != 0)
/*  971 */           consumer.accept(new AbstractShort2IntMap.BasicEntry(Short2IntOpenCustomHashMap.this.key[pos], Short2IntOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Short2IntMap.Entry> consumer) {
/*  976 */       AbstractShort2IntMap.BasicEntry entry = new AbstractShort2IntMap.BasicEntry();
/*  977 */       if (Short2IntOpenCustomHashMap.this.containsNullKey) {
/*  978 */         entry.key = Short2IntOpenCustomHashMap.this.key[Short2IntOpenCustomHashMap.this.n];
/*  979 */         entry.value = Short2IntOpenCustomHashMap.this.value[Short2IntOpenCustomHashMap.this.n];
/*  980 */         consumer.accept(entry);
/*      */       } 
/*  982 */       for (int pos = Short2IntOpenCustomHashMap.this.n; pos-- != 0;) {
/*  983 */         if (Short2IntOpenCustomHashMap.this.key[pos] != 0) {
/*  984 */           entry.key = Short2IntOpenCustomHashMap.this.key[pos];
/*  985 */           entry.value = Short2IntOpenCustomHashMap.this.value[pos];
/*  986 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Short2IntMap.FastEntrySet short2IntEntrySet() {
/*  992 */     if (this.entries == null)
/*  993 */       this.entries = new MapEntrySet(); 
/*  994 */     return this.entries;
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
/* 1011 */       return Short2IntOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractShortSet { private KeySet() {}
/*      */     
/*      */     public ShortIterator iterator() {
/* 1017 */       return new Short2IntOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer consumer) {
/* 1022 */       if (Short2IntOpenCustomHashMap.this.containsNullKey)
/* 1023 */         consumer.accept(Short2IntOpenCustomHashMap.this.key[Short2IntOpenCustomHashMap.this.n]); 
/* 1024 */       for (int pos = Short2IntOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1025 */         short k = Short2IntOpenCustomHashMap.this.key[pos];
/* 1026 */         if (k != 0)
/* 1027 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1032 */       return Short2IntOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(short k) {
/* 1036 */       return Short2IntOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(short k) {
/* 1040 */       int oldSize = Short2IntOpenCustomHashMap.this.size;
/* 1041 */       Short2IntOpenCustomHashMap.this.remove(k);
/* 1042 */       return (Short2IntOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1046 */       Short2IntOpenCustomHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ShortSet keySet() {
/* 1051 */     if (this.keys == null)
/* 1052 */       this.keys = new KeySet(); 
/* 1053 */     return this.keys;
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
/* 1070 */       return Short2IntOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public IntCollection values() {
/* 1075 */     if (this.values == null)
/* 1076 */       this.values = (IntCollection)new AbstractIntCollection()
/*      */         {
/*      */           public IntIterator iterator() {
/* 1079 */             return new Short2IntOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1083 */             return Short2IntOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(int v) {
/* 1087 */             return Short2IntOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1091 */             Short2IntOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(IntConsumer consumer)
/*      */           {
/* 1096 */             if (Short2IntOpenCustomHashMap.this.containsNullKey)
/* 1097 */               consumer.accept(Short2IntOpenCustomHashMap.this.value[Short2IntOpenCustomHashMap.this.n]); 
/* 1098 */             for (int pos = Short2IntOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1099 */               if (Short2IntOpenCustomHashMap.this.key[pos] != 0)
/* 1100 */                 consumer.accept(Short2IntOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1103 */     return this.values;
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
/* 1120 */     return trim(this.size);
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
/* 1144 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1145 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1146 */       return true; 
/*      */     try {
/* 1148 */       rehash(l);
/* 1149 */     } catch (OutOfMemoryError cantDoIt) {
/* 1150 */       return false;
/*      */     } 
/* 1152 */     return true;
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
/* 1168 */     short[] key = this.key;
/* 1169 */     int[] value = this.value;
/* 1170 */     int mask = newN - 1;
/* 1171 */     short[] newKey = new short[newN + 1];
/* 1172 */     int[] newValue = new int[newN + 1];
/* 1173 */     int i = this.n;
/* 1174 */     for (int j = realSize(); j-- != 0; ) {
/* 1175 */       while (key[--i] == 0); int pos;
/* 1176 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != 0)
/*      */       {
/* 1178 */         while (newKey[pos = pos + 1 & mask] != 0); } 
/* 1179 */       newKey[pos] = key[i];
/* 1180 */       newValue[pos] = value[i];
/*      */     } 
/* 1182 */     newValue[newN] = value[this.n];
/* 1183 */     this.n = newN;
/* 1184 */     this.mask = mask;
/* 1185 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1186 */     this.key = newKey;
/* 1187 */     this.value = newValue;
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
/*      */   public Short2IntOpenCustomHashMap clone() {
/*      */     Short2IntOpenCustomHashMap c;
/*      */     try {
/* 1204 */       c = (Short2IntOpenCustomHashMap)super.clone();
/* 1205 */     } catch (CloneNotSupportedException cantHappen) {
/* 1206 */       throw new InternalError();
/*      */     } 
/* 1208 */     c.keys = null;
/* 1209 */     c.values = null;
/* 1210 */     c.entries = null;
/* 1211 */     c.containsNullKey = this.containsNullKey;
/* 1212 */     c.key = (short[])this.key.clone();
/* 1213 */     c.value = (int[])this.value.clone();
/* 1214 */     c.strategy = this.strategy;
/* 1215 */     return c;
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
/* 1228 */     int h = 0;
/* 1229 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1230 */       while (this.key[i] == 0)
/* 1231 */         i++; 
/* 1232 */       t = this.strategy.hashCode(this.key[i]);
/* 1233 */       t ^= this.value[i];
/* 1234 */       h += t;
/* 1235 */       i++;
/*      */     } 
/*      */     
/* 1238 */     if (this.containsNullKey)
/* 1239 */       h += this.value[this.n]; 
/* 1240 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1243 */     short[] key = this.key;
/* 1244 */     int[] value = this.value;
/* 1245 */     MapIterator i = new MapIterator();
/* 1246 */     s.defaultWriteObject();
/* 1247 */     for (int j = this.size; j-- != 0; ) {
/* 1248 */       int e = i.nextEntry();
/* 1249 */       s.writeShort(key[e]);
/* 1250 */       s.writeInt(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1255 */     s.defaultReadObject();
/* 1256 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1257 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1258 */     this.mask = this.n - 1;
/* 1259 */     short[] key = this.key = new short[this.n + 1];
/* 1260 */     int[] value = this.value = new int[this.n + 1];
/*      */ 
/*      */     
/* 1263 */     for (int i = this.size; i-- != 0; ) {
/* 1264 */       int pos; short k = s.readShort();
/* 1265 */       int v = s.readInt();
/* 1266 */       if (this.strategy.equals(k, (short)0)) {
/* 1267 */         pos = this.n;
/* 1268 */         this.containsNullKey = true;
/*      */       } else {
/* 1270 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1271 */         while (key[pos] != 0)
/* 1272 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1274 */       key[pos] = k;
/* 1275 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\Short2IntOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */