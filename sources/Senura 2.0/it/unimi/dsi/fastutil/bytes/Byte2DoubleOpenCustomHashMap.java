/*      */ package it.unimi.dsi.fastutil.bytes;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleCollection;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleIterator;
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
/*      */ 
/*      */ public class Byte2DoubleOpenCustomHashMap
/*      */   extends AbstractByte2DoubleMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient byte[] key;
/*      */   protected transient double[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected ByteHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Byte2DoubleMap.FastEntrySet entries;
/*      */   protected transient ByteSet keys;
/*      */   protected transient DoubleCollection values;
/*      */   
/*      */   public Byte2DoubleOpenCustomHashMap(int expected, float f, ByteHash.Strategy strategy) {
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
/*  117 */     this.value = new double[this.n + 1];
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
/*      */   public Byte2DoubleOpenCustomHashMap(int expected, ByteHash.Strategy strategy) {
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
/*      */   public Byte2DoubleOpenCustomHashMap(ByteHash.Strategy strategy) {
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
/*      */   public Byte2DoubleOpenCustomHashMap(Map<? extends Byte, ? extends Double> m, float f, ByteHash.Strategy strategy) {
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
/*      */   public Byte2DoubleOpenCustomHashMap(Map<? extends Byte, ? extends Double> m, ByteHash.Strategy strategy) {
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
/*      */   public Byte2DoubleOpenCustomHashMap(Byte2DoubleMap m, float f, ByteHash.Strategy strategy) {
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
/*      */   public Byte2DoubleOpenCustomHashMap(Byte2DoubleMap m, ByteHash.Strategy strategy) {
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
/*      */   public Byte2DoubleOpenCustomHashMap(byte[] k, double[] v, float f, ByteHash.Strategy strategy) {
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
/*      */   public Byte2DoubleOpenCustomHashMap(byte[] k, double[] v, ByteHash.Strategy strategy) {
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
/*      */   private double removeEntry(int pos) {
/*  261 */     double oldValue = this.value[pos];
/*  262 */     this.size--;
/*  263 */     shiftKeys(pos);
/*  264 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  265 */       rehash(this.n / 2); 
/*  266 */     return oldValue;
/*      */   }
/*      */   private double removeNullEntry() {
/*  269 */     this.containsNullKey = false;
/*  270 */     double oldValue = this.value[this.n];
/*  271 */     this.size--;
/*  272 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  273 */       rehash(this.n / 2); 
/*  274 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Byte, ? extends Double> m) {
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
/*      */   private void insert(int pos, byte k, double v) {
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
/*      */   public double put(byte k, double v) {
/*  317 */     int pos = find(k);
/*  318 */     if (pos < 0) {
/*  319 */       insert(-pos - 1, k, v);
/*  320 */       return this.defRetValue;
/*      */     } 
/*  322 */     double oldValue = this.value[pos];
/*  323 */     this.value[pos] = v;
/*  324 */     return oldValue;
/*      */   }
/*      */   private double addToValue(int pos, double incr) {
/*  327 */     double oldValue = this.value[pos];
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
/*      */   public double addTo(byte k, double incr) {
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
/*      */   public double remove(byte k) {
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
/*      */   public double get(byte k) {
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
/*      */   public boolean containsValue(double v) {
/*  470 */     double[] value = this.value;
/*  471 */     byte[] key = this.key;
/*  472 */     if (this.containsNullKey && Double.doubleToLongBits(value[this.n]) == Double.doubleToLongBits(v))
/*  473 */       return true; 
/*  474 */     for (int i = this.n; i-- != 0;) {
/*  475 */       if (key[i] != 0 && Double.doubleToLongBits(value[i]) == Double.doubleToLongBits(v))
/*  476 */         return true; 
/*  477 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double getOrDefault(byte k, double defaultValue) {
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
/*      */   public double putIfAbsent(byte k, double v) {
/*  504 */     int pos = find(k);
/*  505 */     if (pos >= 0)
/*  506 */       return this.value[pos]; 
/*  507 */     insert(-pos - 1, k, v);
/*  508 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(byte k, double v) {
/*  514 */     if (this.strategy.equals(k, (byte)0)) {
/*  515 */       if (this.containsNullKey && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[this.n])) {
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
/*  527 */     if (this.strategy.equals(k, curr) && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
/*  528 */       removeEntry(pos);
/*  529 */       return true;
/*      */     } 
/*      */     while (true) {
/*  532 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  533 */         return false; 
/*  534 */       if (this.strategy.equals(k, curr) && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
/*  535 */         removeEntry(pos);
/*  536 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(byte k, double oldValue, double v) {
/*  543 */     int pos = find(k);
/*  544 */     if (pos < 0 || Double.doubleToLongBits(oldValue) != Double.doubleToLongBits(this.value[pos]))
/*  545 */       return false; 
/*  546 */     this.value[pos] = v;
/*  547 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public double replace(byte k, double v) {
/*  552 */     int pos = find(k);
/*  553 */     if (pos < 0)
/*  554 */       return this.defRetValue; 
/*  555 */     double oldValue = this.value[pos];
/*  556 */     this.value[pos] = v;
/*  557 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public double computeIfAbsent(byte k, IntToDoubleFunction mappingFunction) {
/*  562 */     Objects.requireNonNull(mappingFunction);
/*  563 */     int pos = find(k);
/*  564 */     if (pos >= 0)
/*  565 */       return this.value[pos]; 
/*  566 */     double newValue = mappingFunction.applyAsDouble(k);
/*  567 */     insert(-pos - 1, k, newValue);
/*  568 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeIfAbsentNullable(byte k, IntFunction<? extends Double> mappingFunction) {
/*  574 */     Objects.requireNonNull(mappingFunction);
/*  575 */     int pos = find(k);
/*  576 */     if (pos >= 0)
/*  577 */       return this.value[pos]; 
/*  578 */     Double newValue = mappingFunction.apply(k);
/*  579 */     if (newValue == null)
/*  580 */       return this.defRetValue; 
/*  581 */     double v = newValue.doubleValue();
/*  582 */     insert(-pos - 1, k, v);
/*  583 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeIfPresent(byte k, BiFunction<? super Byte, ? super Double, ? extends Double> remappingFunction) {
/*  589 */     Objects.requireNonNull(remappingFunction);
/*  590 */     int pos = find(k);
/*  591 */     if (pos < 0)
/*  592 */       return this.defRetValue; 
/*  593 */     Double newValue = remappingFunction.apply(Byte.valueOf(k), Double.valueOf(this.value[pos]));
/*  594 */     if (newValue == null) {
/*  595 */       if (this.strategy.equals(k, (byte)0)) {
/*  596 */         removeNullEntry();
/*      */       } else {
/*  598 */         removeEntry(pos);
/*  599 */       }  return this.defRetValue;
/*      */     } 
/*  601 */     this.value[pos] = newValue.doubleValue(); return newValue.doubleValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double compute(byte k, BiFunction<? super Byte, ? super Double, ? extends Double> remappingFunction) {
/*  607 */     Objects.requireNonNull(remappingFunction);
/*  608 */     int pos = find(k);
/*  609 */     Double newValue = remappingFunction.apply(Byte.valueOf(k), (pos >= 0) ? Double.valueOf(this.value[pos]) : null);
/*  610 */     if (newValue == null) {
/*  611 */       if (pos >= 0)
/*  612 */         if (this.strategy.equals(k, (byte)0)) {
/*  613 */           removeNullEntry();
/*      */         } else {
/*  615 */           removeEntry(pos);
/*      */         }  
/*  617 */       return this.defRetValue;
/*      */     } 
/*  619 */     double newVal = newValue.doubleValue();
/*  620 */     if (pos < 0) {
/*  621 */       insert(-pos - 1, k, newVal);
/*  622 */       return newVal;
/*      */     } 
/*  624 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double merge(byte k, double v, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/*  630 */     Objects.requireNonNull(remappingFunction);
/*  631 */     int pos = find(k);
/*  632 */     if (pos < 0) {
/*  633 */       insert(-pos - 1, k, v);
/*  634 */       return v;
/*      */     } 
/*  636 */     Double newValue = remappingFunction.apply(Double.valueOf(this.value[pos]), Double.valueOf(v));
/*  637 */     if (newValue == null) {
/*  638 */       if (this.strategy.equals(k, (byte)0)) {
/*  639 */         removeNullEntry();
/*      */       } else {
/*  641 */         removeEntry(pos);
/*  642 */       }  return this.defRetValue;
/*      */     } 
/*  644 */     this.value[pos] = newValue.doubleValue(); return newValue.doubleValue();
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
/*      */     implements Byte2DoubleMap.Entry, Map.Entry<Byte, Double>
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
/*  685 */       return Byte2DoubleOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public double getDoubleValue() {
/*  689 */       return Byte2DoubleOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public double setValue(double v) {
/*  693 */       double oldValue = Byte2DoubleOpenCustomHashMap.this.value[this.index];
/*  694 */       Byte2DoubleOpenCustomHashMap.this.value[this.index] = v;
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
/*  705 */       return Byte.valueOf(Byte2DoubleOpenCustomHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double getValue() {
/*  715 */       return Double.valueOf(Byte2DoubleOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double setValue(Double v) {
/*  725 */       return Double.valueOf(setValue(v.doubleValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  730 */       if (!(o instanceof Map.Entry))
/*  731 */         return false; 
/*  732 */       Map.Entry<Byte, Double> e = (Map.Entry<Byte, Double>)o;
/*  733 */       return (Byte2DoubleOpenCustomHashMap.this.strategy.equals(Byte2DoubleOpenCustomHashMap.this.key[this.index], ((Byte)e.getKey()).byteValue()) && 
/*  734 */         Double.doubleToLongBits(Byte2DoubleOpenCustomHashMap.this.value[this.index]) == Double.doubleToLongBits(((Double)e.getValue()).doubleValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  738 */       return Byte2DoubleOpenCustomHashMap.this.strategy.hashCode(Byte2DoubleOpenCustomHashMap.this.key[this.index]) ^ HashCommon.double2int(Byte2DoubleOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  742 */       return Byte2DoubleOpenCustomHashMap.this.key[this.index] + "=>" + Byte2DoubleOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  752 */     int pos = Byte2DoubleOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  759 */     int last = -1;
/*      */     
/*  761 */     int c = Byte2DoubleOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  765 */     boolean mustReturnNullKey = Byte2DoubleOpenCustomHashMap.this.containsNullKey;
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
/*  780 */         return this.last = Byte2DoubleOpenCustomHashMap.this.n;
/*      */       } 
/*  782 */       byte[] key = Byte2DoubleOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  784 */         if (--this.pos < 0) {
/*      */           
/*  786 */           this.last = Integer.MIN_VALUE;
/*  787 */           byte k = this.wrapped.getByte(-this.pos - 1);
/*  788 */           int p = HashCommon.mix(Byte2DoubleOpenCustomHashMap.this.strategy.hashCode(k)) & Byte2DoubleOpenCustomHashMap.this.mask;
/*  789 */           while (!Byte2DoubleOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  790 */             p = p + 1 & Byte2DoubleOpenCustomHashMap.this.mask; 
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
/*  808 */       byte[] key = Byte2DoubleOpenCustomHashMap.this.key; while (true) {
/*      */         byte curr; int last;
/*  810 */         pos = (last = pos) + 1 & Byte2DoubleOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  812 */           if ((curr = key[pos]) == 0) {
/*  813 */             key[last] = 0;
/*      */             return;
/*      */           } 
/*  816 */           int slot = HashCommon.mix(Byte2DoubleOpenCustomHashMap.this.strategy.hashCode(curr)) & Byte2DoubleOpenCustomHashMap.this.mask;
/*  817 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  819 */           pos = pos + 1 & Byte2DoubleOpenCustomHashMap.this.mask;
/*      */         } 
/*  821 */         if (pos < last) {
/*  822 */           if (this.wrapped == null)
/*  823 */             this.wrapped = new ByteArrayList(2); 
/*  824 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  826 */         key[last] = curr;
/*  827 */         Byte2DoubleOpenCustomHashMap.this.value[last] = Byte2DoubleOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  831 */       if (this.last == -1)
/*  832 */         throw new IllegalStateException(); 
/*  833 */       if (this.last == Byte2DoubleOpenCustomHashMap.this.n) {
/*  834 */         Byte2DoubleOpenCustomHashMap.this.containsNullKey = false;
/*  835 */       } else if (this.pos >= 0) {
/*  836 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  839 */         Byte2DoubleOpenCustomHashMap.this.remove(this.wrapped.getByte(-this.pos - 1));
/*  840 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  843 */       Byte2DoubleOpenCustomHashMap.this.size--;
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
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Byte2DoubleMap.Entry> { private Byte2DoubleOpenCustomHashMap.MapEntry entry;
/*      */     
/*      */     public Byte2DoubleOpenCustomHashMap.MapEntry next() {
/*  859 */       return this.entry = new Byte2DoubleOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  863 */       super.remove();
/*  864 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Byte2DoubleMap.Entry> { private FastEntryIterator() {
/*  868 */       this.entry = new Byte2DoubleOpenCustomHashMap.MapEntry();
/*      */     } private final Byte2DoubleOpenCustomHashMap.MapEntry entry;
/*      */     public Byte2DoubleOpenCustomHashMap.MapEntry next() {
/*  871 */       this.entry.index = nextEntry();
/*  872 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Byte2DoubleMap.Entry> implements Byte2DoubleMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Byte2DoubleMap.Entry> iterator() {
/*  878 */       return new Byte2DoubleOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Byte2DoubleMap.Entry> fastIterator() {
/*  882 */       return new Byte2DoubleOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  887 */       if (!(o instanceof Map.Entry))
/*  888 */         return false; 
/*  889 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  890 */       if (e.getKey() == null || !(e.getKey() instanceof Byte))
/*  891 */         return false; 
/*  892 */       if (e.getValue() == null || !(e.getValue() instanceof Double))
/*  893 */         return false; 
/*  894 */       byte k = ((Byte)e.getKey()).byteValue();
/*  895 */       double v = ((Double)e.getValue()).doubleValue();
/*  896 */       if (Byte2DoubleOpenCustomHashMap.this.strategy.equals(k, (byte)0)) {
/*  897 */         return (Byte2DoubleOpenCustomHashMap.this.containsNullKey && 
/*  898 */           Double.doubleToLongBits(Byte2DoubleOpenCustomHashMap.this.value[Byte2DoubleOpenCustomHashMap.this.n]) == Double.doubleToLongBits(v));
/*      */       }
/*  900 */       byte[] key = Byte2DoubleOpenCustomHashMap.this.key;
/*      */       byte curr;
/*      */       int pos;
/*  903 */       if ((curr = key[pos = HashCommon.mix(Byte2DoubleOpenCustomHashMap.this.strategy.hashCode(k)) & Byte2DoubleOpenCustomHashMap.this.mask]) == 0)
/*  904 */         return false; 
/*  905 */       if (Byte2DoubleOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  906 */         return (Double.doubleToLongBits(Byte2DoubleOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v));
/*      */       }
/*      */       while (true) {
/*  909 */         if ((curr = key[pos = pos + 1 & Byte2DoubleOpenCustomHashMap.this.mask]) == 0)
/*  910 */           return false; 
/*  911 */         if (Byte2DoubleOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  912 */           return (Double.doubleToLongBits(Byte2DoubleOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v));
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  918 */       if (!(o instanceof Map.Entry))
/*  919 */         return false; 
/*  920 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  921 */       if (e.getKey() == null || !(e.getKey() instanceof Byte))
/*  922 */         return false; 
/*  923 */       if (e.getValue() == null || !(e.getValue() instanceof Double))
/*  924 */         return false; 
/*  925 */       byte k = ((Byte)e.getKey()).byteValue();
/*  926 */       double v = ((Double)e.getValue()).doubleValue();
/*  927 */       if (Byte2DoubleOpenCustomHashMap.this.strategy.equals(k, (byte)0)) {
/*  928 */         if (Byte2DoubleOpenCustomHashMap.this.containsNullKey && Double.doubleToLongBits(Byte2DoubleOpenCustomHashMap.this.value[Byte2DoubleOpenCustomHashMap.this.n]) == Double.doubleToLongBits(v)) {
/*  929 */           Byte2DoubleOpenCustomHashMap.this.removeNullEntry();
/*  930 */           return true;
/*      */         } 
/*  932 */         return false;
/*      */       } 
/*      */       
/*  935 */       byte[] key = Byte2DoubleOpenCustomHashMap.this.key;
/*      */       byte curr;
/*      */       int pos;
/*  938 */       if ((curr = key[pos = HashCommon.mix(Byte2DoubleOpenCustomHashMap.this.strategy.hashCode(k)) & Byte2DoubleOpenCustomHashMap.this.mask]) == 0)
/*  939 */         return false; 
/*  940 */       if (Byte2DoubleOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  941 */         if (Double.doubleToLongBits(Byte2DoubleOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
/*  942 */           Byte2DoubleOpenCustomHashMap.this.removeEntry(pos);
/*  943 */           return true;
/*      */         } 
/*  945 */         return false;
/*      */       } 
/*      */       while (true) {
/*  948 */         if ((curr = key[pos = pos + 1 & Byte2DoubleOpenCustomHashMap.this.mask]) == 0)
/*  949 */           return false; 
/*  950 */         if (Byte2DoubleOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  951 */           Double.doubleToLongBits(Byte2DoubleOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
/*  952 */           Byte2DoubleOpenCustomHashMap.this.removeEntry(pos);
/*  953 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  960 */       return Byte2DoubleOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  964 */       Byte2DoubleOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Byte2DoubleMap.Entry> consumer) {
/*  969 */       if (Byte2DoubleOpenCustomHashMap.this.containsNullKey)
/*  970 */         consumer.accept(new AbstractByte2DoubleMap.BasicEntry(Byte2DoubleOpenCustomHashMap.this.key[Byte2DoubleOpenCustomHashMap.this.n], Byte2DoubleOpenCustomHashMap.this.value[Byte2DoubleOpenCustomHashMap.this.n])); 
/*  971 */       for (int pos = Byte2DoubleOpenCustomHashMap.this.n; pos-- != 0;) {
/*  972 */         if (Byte2DoubleOpenCustomHashMap.this.key[pos] != 0)
/*  973 */           consumer.accept(new AbstractByte2DoubleMap.BasicEntry(Byte2DoubleOpenCustomHashMap.this.key[pos], Byte2DoubleOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Byte2DoubleMap.Entry> consumer) {
/*  978 */       AbstractByte2DoubleMap.BasicEntry entry = new AbstractByte2DoubleMap.BasicEntry();
/*  979 */       if (Byte2DoubleOpenCustomHashMap.this.containsNullKey) {
/*  980 */         entry.key = Byte2DoubleOpenCustomHashMap.this.key[Byte2DoubleOpenCustomHashMap.this.n];
/*  981 */         entry.value = Byte2DoubleOpenCustomHashMap.this.value[Byte2DoubleOpenCustomHashMap.this.n];
/*  982 */         consumer.accept(entry);
/*      */       } 
/*  984 */       for (int pos = Byte2DoubleOpenCustomHashMap.this.n; pos-- != 0;) {
/*  985 */         if (Byte2DoubleOpenCustomHashMap.this.key[pos] != 0) {
/*  986 */           entry.key = Byte2DoubleOpenCustomHashMap.this.key[pos];
/*  987 */           entry.value = Byte2DoubleOpenCustomHashMap.this.value[pos];
/*  988 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Byte2DoubleMap.FastEntrySet byte2DoubleEntrySet() {
/*  994 */     if (this.entries == null)
/*  995 */       this.entries = new MapEntrySet(); 
/*  996 */     return this.entries;
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
/* 1013 */       return Byte2DoubleOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractByteSet { private KeySet() {}
/*      */     
/*      */     public ByteIterator iterator() {
/* 1019 */       return new Byte2DoubleOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer consumer) {
/* 1024 */       if (Byte2DoubleOpenCustomHashMap.this.containsNullKey)
/* 1025 */         consumer.accept(Byte2DoubleOpenCustomHashMap.this.key[Byte2DoubleOpenCustomHashMap.this.n]); 
/* 1026 */       for (int pos = Byte2DoubleOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1027 */         byte k = Byte2DoubleOpenCustomHashMap.this.key[pos];
/* 1028 */         if (k != 0)
/* 1029 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1034 */       return Byte2DoubleOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(byte k) {
/* 1038 */       return Byte2DoubleOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(byte k) {
/* 1042 */       int oldSize = Byte2DoubleOpenCustomHashMap.this.size;
/* 1043 */       Byte2DoubleOpenCustomHashMap.this.remove(k);
/* 1044 */       return (Byte2DoubleOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1048 */       Byte2DoubleOpenCustomHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ByteSet keySet() {
/* 1053 */     if (this.keys == null)
/* 1054 */       this.keys = new KeySet(); 
/* 1055 */     return this.keys;
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
/*      */     implements DoubleIterator
/*      */   {
/*      */     public double nextDouble() {
/* 1072 */       return Byte2DoubleOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public DoubleCollection values() {
/* 1077 */     if (this.values == null)
/* 1078 */       this.values = (DoubleCollection)new AbstractDoubleCollection()
/*      */         {
/*      */           public DoubleIterator iterator() {
/* 1081 */             return new Byte2DoubleOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1085 */             return Byte2DoubleOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(double v) {
/* 1089 */             return Byte2DoubleOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1093 */             Byte2DoubleOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(DoubleConsumer consumer)
/*      */           {
/* 1098 */             if (Byte2DoubleOpenCustomHashMap.this.containsNullKey)
/* 1099 */               consumer.accept(Byte2DoubleOpenCustomHashMap.this.value[Byte2DoubleOpenCustomHashMap.this.n]); 
/* 1100 */             for (int pos = Byte2DoubleOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1101 */               if (Byte2DoubleOpenCustomHashMap.this.key[pos] != 0)
/* 1102 */                 consumer.accept(Byte2DoubleOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1105 */     return this.values;
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
/* 1122 */     return trim(this.size);
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
/* 1146 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1147 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1148 */       return true; 
/*      */     try {
/* 1150 */       rehash(l);
/* 1151 */     } catch (OutOfMemoryError cantDoIt) {
/* 1152 */       return false;
/*      */     } 
/* 1154 */     return true;
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
/* 1170 */     byte[] key = this.key;
/* 1171 */     double[] value = this.value;
/* 1172 */     int mask = newN - 1;
/* 1173 */     byte[] newKey = new byte[newN + 1];
/* 1174 */     double[] newValue = new double[newN + 1];
/* 1175 */     int i = this.n;
/* 1176 */     for (int j = realSize(); j-- != 0; ) {
/* 1177 */       while (key[--i] == 0); int pos;
/* 1178 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != 0)
/*      */       {
/* 1180 */         while (newKey[pos = pos + 1 & mask] != 0); } 
/* 1181 */       newKey[pos] = key[i];
/* 1182 */       newValue[pos] = value[i];
/*      */     } 
/* 1184 */     newValue[newN] = value[this.n];
/* 1185 */     this.n = newN;
/* 1186 */     this.mask = mask;
/* 1187 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1188 */     this.key = newKey;
/* 1189 */     this.value = newValue;
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
/*      */   public Byte2DoubleOpenCustomHashMap clone() {
/*      */     Byte2DoubleOpenCustomHashMap c;
/*      */     try {
/* 1206 */       c = (Byte2DoubleOpenCustomHashMap)super.clone();
/* 1207 */     } catch (CloneNotSupportedException cantHappen) {
/* 1208 */       throw new InternalError();
/*      */     } 
/* 1210 */     c.keys = null;
/* 1211 */     c.values = null;
/* 1212 */     c.entries = null;
/* 1213 */     c.containsNullKey = this.containsNullKey;
/* 1214 */     c.key = (byte[])this.key.clone();
/* 1215 */     c.value = (double[])this.value.clone();
/* 1216 */     c.strategy = this.strategy;
/* 1217 */     return c;
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
/* 1230 */     int h = 0;
/* 1231 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1232 */       while (this.key[i] == 0)
/* 1233 */         i++; 
/* 1234 */       t = this.strategy.hashCode(this.key[i]);
/* 1235 */       t ^= HashCommon.double2int(this.value[i]);
/* 1236 */       h += t;
/* 1237 */       i++;
/*      */     } 
/*      */     
/* 1240 */     if (this.containsNullKey)
/* 1241 */       h += HashCommon.double2int(this.value[this.n]); 
/* 1242 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1245 */     byte[] key = this.key;
/* 1246 */     double[] value = this.value;
/* 1247 */     MapIterator i = new MapIterator();
/* 1248 */     s.defaultWriteObject();
/* 1249 */     for (int j = this.size; j-- != 0; ) {
/* 1250 */       int e = i.nextEntry();
/* 1251 */       s.writeByte(key[e]);
/* 1252 */       s.writeDouble(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1257 */     s.defaultReadObject();
/* 1258 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1259 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1260 */     this.mask = this.n - 1;
/* 1261 */     byte[] key = this.key = new byte[this.n + 1];
/* 1262 */     double[] value = this.value = new double[this.n + 1];
/*      */ 
/*      */     
/* 1265 */     for (int i = this.size; i-- != 0; ) {
/* 1266 */       int pos; byte k = s.readByte();
/* 1267 */       double v = s.readDouble();
/* 1268 */       if (this.strategy.equals(k, (byte)0)) {
/* 1269 */         pos = this.n;
/* 1270 */         this.containsNullKey = true;
/*      */       } else {
/* 1272 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1273 */         while (key[pos] != 0)
/* 1274 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1276 */       key[pos] = k;
/* 1277 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\Byte2DoubleOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */