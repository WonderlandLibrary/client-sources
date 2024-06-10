/*      */ package it.unimi.dsi.fastutil.doubles;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
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
/*      */ import java.util.function.DoubleUnaryOperator;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Double2DoubleOpenHashMap
/*      */   extends AbstractDouble2DoubleMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient double[] key;
/*      */   protected transient double[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Double2DoubleMap.FastEntrySet entries;
/*      */   protected transient DoubleSet keys;
/*      */   protected transient DoubleCollection values;
/*      */   
/*      */   public Double2DoubleOpenHashMap(int expected, float f) {
/*  100 */     if (f <= 0.0F || f > 1.0F)
/*  101 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  102 */     if (expected < 0)
/*  103 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  104 */     this.f = f;
/*  105 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  106 */     this.mask = this.n - 1;
/*  107 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  108 */     this.key = new double[this.n + 1];
/*  109 */     this.value = new double[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2DoubleOpenHashMap(int expected) {
/*  118 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2DoubleOpenHashMap() {
/*  126 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2DoubleOpenHashMap(Map<? extends Double, ? extends Double> m, float f) {
/*  137 */     this(m.size(), f);
/*  138 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2DoubleOpenHashMap(Map<? extends Double, ? extends Double> m) {
/*  148 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2DoubleOpenHashMap(Double2DoubleMap m, float f) {
/*  159 */     this(m.size(), f);
/*  160 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2DoubleOpenHashMap(Double2DoubleMap m) {
/*  170 */     this(m, 0.75F);
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
/*      */   public Double2DoubleOpenHashMap(double[] k, double[] v, float f) {
/*  185 */     this(k.length, f);
/*  186 */     if (k.length != v.length) {
/*  187 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  189 */     for (int i = 0; i < k.length; i++) {
/*  190 */       put(k[i], v[i]);
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
/*      */   public Double2DoubleOpenHashMap(double[] k, double[] v) {
/*  204 */     this(k, v, 0.75F);
/*      */   }
/*      */   private int realSize() {
/*  207 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  210 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  211 */     if (needed > this.n)
/*  212 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  215 */     int needed = (int)Math.min(1073741824L, 
/*  216 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  217 */     if (needed > this.n)
/*  218 */       rehash(needed); 
/*      */   }
/*      */   private double removeEntry(int pos) {
/*  221 */     double oldValue = this.value[pos];
/*  222 */     this.size--;
/*  223 */     shiftKeys(pos);
/*  224 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  225 */       rehash(this.n / 2); 
/*  226 */     return oldValue;
/*      */   }
/*      */   private double removeNullEntry() {
/*  229 */     this.containsNullKey = false;
/*  230 */     double oldValue = this.value[this.n];
/*  231 */     this.size--;
/*  232 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  233 */       rehash(this.n / 2); 
/*  234 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Double, ? extends Double> m) {
/*  238 */     if (this.f <= 0.5D) {
/*  239 */       ensureCapacity(m.size());
/*      */     } else {
/*  241 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  243 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(double k) {
/*  247 */     if (Double.doubleToLongBits(k) == 0L) {
/*  248 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  250 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  253 */     if (Double.doubleToLongBits(
/*  254 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  256 */       return -(pos + 1); } 
/*  257 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  258 */       return pos;
/*      */     }
/*      */     while (true) {
/*  261 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  262 */         return -(pos + 1); 
/*  263 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr))
/*  264 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, double k, double v) {
/*  268 */     if (pos == this.n)
/*  269 */       this.containsNullKey = true; 
/*  270 */     this.key[pos] = k;
/*  271 */     this.value[pos] = v;
/*  272 */     if (this.size++ >= this.maxFill) {
/*  273 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public double put(double k, double v) {
/*  279 */     int pos = find(k);
/*  280 */     if (pos < 0) {
/*  281 */       insert(-pos - 1, k, v);
/*  282 */       return this.defRetValue;
/*      */     } 
/*  284 */     double oldValue = this.value[pos];
/*  285 */     this.value[pos] = v;
/*  286 */     return oldValue;
/*      */   }
/*      */   private double addToValue(int pos, double incr) {
/*  289 */     double oldValue = this.value[pos];
/*  290 */     this.value[pos] = oldValue + incr;
/*  291 */     return oldValue;
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
/*      */   public double addTo(double k, double incr) {
/*      */     int pos;
/*  311 */     if (Double.doubleToLongBits(k) == 0L) {
/*  312 */       if (this.containsNullKey)
/*  313 */         return addToValue(this.n, incr); 
/*  314 */       pos = this.n;
/*  315 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  318 */       double[] key = this.key;
/*      */       double curr;
/*  320 */       if (Double.doubleToLongBits(
/*  321 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) != 0L) {
/*      */         
/*  323 */         if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k))
/*  324 */           return addToValue(pos, incr); 
/*  325 */         while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
/*  326 */           if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k))
/*  327 */             return addToValue(pos, incr); 
/*      */         } 
/*      */       } 
/*  330 */     }  this.key[pos] = k;
/*  331 */     this.value[pos] = this.defRetValue + incr;
/*  332 */     if (this.size++ >= this.maxFill) {
/*  333 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  336 */     return this.defRetValue;
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
/*  349 */     double[] key = this.key; while (true) {
/*      */       double curr; int last;
/*  351 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  353 */         if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/*  354 */           key[last] = 0.0D;
/*      */           return;
/*      */         } 
/*  357 */         int slot = (int)HashCommon.mix(Double.doubleToRawLongBits(curr)) & this.mask;
/*  358 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  360 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  362 */       key[last] = curr;
/*  363 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public double remove(double k) {
/*  369 */     if (Double.doubleToLongBits(k) == 0L) {
/*  370 */       if (this.containsNullKey)
/*  371 */         return removeNullEntry(); 
/*  372 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  375 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  378 */     if (Double.doubleToLongBits(
/*  379 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  381 */       return this.defRetValue; } 
/*  382 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr))
/*  383 */       return removeEntry(pos); 
/*      */     while (true) {
/*  385 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  386 */         return this.defRetValue; 
/*  387 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  388 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public double get(double k) {
/*  394 */     if (Double.doubleToLongBits(k) == 0L) {
/*  395 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  397 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  400 */     if (Double.doubleToLongBits(
/*  401 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  403 */       return this.defRetValue; } 
/*  404 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  405 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  408 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  409 */         return this.defRetValue; 
/*  410 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  411 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(double k) {
/*  417 */     if (Double.doubleToLongBits(k) == 0L) {
/*  418 */       return this.containsNullKey;
/*      */     }
/*  420 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  423 */     if (Double.doubleToLongBits(
/*  424 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  426 */       return false; } 
/*  427 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  428 */       return true;
/*      */     }
/*      */     while (true) {
/*  431 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  432 */         return false; 
/*  433 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr))
/*  434 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(double v) {
/*  439 */     double[] value = this.value;
/*  440 */     double[] key = this.key;
/*  441 */     if (this.containsNullKey && Double.doubleToLongBits(value[this.n]) == Double.doubleToLongBits(v))
/*  442 */       return true; 
/*  443 */     for (int i = this.n; i-- != 0;) {
/*  444 */       if (Double.doubleToLongBits(key[i]) != 0L && 
/*  445 */         Double.doubleToLongBits(value[i]) == Double.doubleToLongBits(v))
/*  446 */         return true; 
/*  447 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double getOrDefault(double k, double defaultValue) {
/*  453 */     if (Double.doubleToLongBits(k) == 0L) {
/*  454 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  456 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  459 */     if (Double.doubleToLongBits(
/*  460 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  462 */       return defaultValue; } 
/*  463 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  464 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  467 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  468 */         return defaultValue; 
/*  469 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  470 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public double putIfAbsent(double k, double v) {
/*  476 */     int pos = find(k);
/*  477 */     if (pos >= 0)
/*  478 */       return this.value[pos]; 
/*  479 */     insert(-pos - 1, k, v);
/*  480 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(double k, double v) {
/*  486 */     if (Double.doubleToLongBits(k) == 0L) {
/*  487 */       if (this.containsNullKey && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[this.n])) {
/*  488 */         removeNullEntry();
/*  489 */         return true;
/*      */       } 
/*  491 */       return false;
/*      */     } 
/*      */     
/*  494 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  497 */     if (Double.doubleToLongBits(
/*  498 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  500 */       return false; } 
/*  501 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr) && 
/*  502 */       Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
/*  503 */       removeEntry(pos);
/*  504 */       return true;
/*      */     } 
/*      */     while (true) {
/*  507 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  508 */         return false; 
/*  509 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr) && 
/*  510 */         Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
/*  511 */         removeEntry(pos);
/*  512 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(double k, double oldValue, double v) {
/*  519 */     int pos = find(k);
/*  520 */     if (pos < 0 || Double.doubleToLongBits(oldValue) != Double.doubleToLongBits(this.value[pos]))
/*  521 */       return false; 
/*  522 */     this.value[pos] = v;
/*  523 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public double replace(double k, double v) {
/*  528 */     int pos = find(k);
/*  529 */     if (pos < 0)
/*  530 */       return this.defRetValue; 
/*  531 */     double oldValue = this.value[pos];
/*  532 */     this.value[pos] = v;
/*  533 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public double computeIfAbsent(double k, DoubleUnaryOperator mappingFunction) {
/*  538 */     Objects.requireNonNull(mappingFunction);
/*  539 */     int pos = find(k);
/*  540 */     if (pos >= 0)
/*  541 */       return this.value[pos]; 
/*  542 */     double newValue = mappingFunction.applyAsDouble(k);
/*  543 */     insert(-pos - 1, k, newValue);
/*  544 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeIfAbsentNullable(double k, DoubleFunction<? extends Double> mappingFunction) {
/*  550 */     Objects.requireNonNull(mappingFunction);
/*  551 */     int pos = find(k);
/*  552 */     if (pos >= 0)
/*  553 */       return this.value[pos]; 
/*  554 */     Double newValue = mappingFunction.apply(k);
/*  555 */     if (newValue == null)
/*  556 */       return this.defRetValue; 
/*  557 */     double v = newValue.doubleValue();
/*  558 */     insert(-pos - 1, k, v);
/*  559 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeIfPresent(double k, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/*  565 */     Objects.requireNonNull(remappingFunction);
/*  566 */     int pos = find(k);
/*  567 */     if (pos < 0)
/*  568 */       return this.defRetValue; 
/*  569 */     Double newValue = remappingFunction.apply(Double.valueOf(k), Double.valueOf(this.value[pos]));
/*  570 */     if (newValue == null) {
/*  571 */       if (Double.doubleToLongBits(k) == 0L) {
/*  572 */         removeNullEntry();
/*      */       } else {
/*  574 */         removeEntry(pos);
/*  575 */       }  return this.defRetValue;
/*      */     } 
/*  577 */     this.value[pos] = newValue.doubleValue(); return newValue.doubleValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double compute(double k, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/*  583 */     Objects.requireNonNull(remappingFunction);
/*  584 */     int pos = find(k);
/*  585 */     Double newValue = remappingFunction.apply(Double.valueOf(k), 
/*  586 */         (pos >= 0) ? Double.valueOf(this.value[pos]) : null);
/*  587 */     if (newValue == null) {
/*  588 */       if (pos >= 0)
/*  589 */         if (Double.doubleToLongBits(k) == 0L) {
/*  590 */           removeNullEntry();
/*      */         } else {
/*  592 */           removeEntry(pos);
/*      */         }  
/*  594 */       return this.defRetValue;
/*      */     } 
/*  596 */     double newVal = newValue.doubleValue();
/*  597 */     if (pos < 0) {
/*  598 */       insert(-pos - 1, k, newVal);
/*  599 */       return newVal;
/*      */     } 
/*  601 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double merge(double k, double v, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/*  607 */     Objects.requireNonNull(remappingFunction);
/*  608 */     int pos = find(k);
/*  609 */     if (pos < 0) {
/*  610 */       insert(-pos - 1, k, v);
/*  611 */       return v;
/*      */     } 
/*  613 */     Double newValue = remappingFunction.apply(Double.valueOf(this.value[pos]), Double.valueOf(v));
/*  614 */     if (newValue == null) {
/*  615 */       if (Double.doubleToLongBits(k) == 0L) {
/*  616 */         removeNullEntry();
/*      */       } else {
/*  618 */         removeEntry(pos);
/*  619 */       }  return this.defRetValue;
/*      */     } 
/*  621 */     this.value[pos] = newValue.doubleValue(); return newValue.doubleValue();
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
/*  632 */     if (this.size == 0)
/*      */       return; 
/*  634 */     this.size = 0;
/*  635 */     this.containsNullKey = false;
/*  636 */     Arrays.fill(this.key, 0.0D);
/*      */   }
/*      */   
/*      */   public int size() {
/*  640 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  644 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Double2DoubleMap.Entry, Map.Entry<Double, Double>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  656 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public double getDoubleKey() {
/*  662 */       return Double2DoubleOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public double getDoubleValue() {
/*  666 */       return Double2DoubleOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public double setValue(double v) {
/*  670 */       double oldValue = Double2DoubleOpenHashMap.this.value[this.index];
/*  671 */       Double2DoubleOpenHashMap.this.value[this.index] = v;
/*  672 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double getKey() {
/*  682 */       return Double.valueOf(Double2DoubleOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double getValue() {
/*  692 */       return Double.valueOf(Double2DoubleOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double setValue(Double v) {
/*  702 */       return Double.valueOf(setValue(v.doubleValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  707 */       if (!(o instanceof Map.Entry))
/*  708 */         return false; 
/*  709 */       Map.Entry<Double, Double> e = (Map.Entry<Double, Double>)o;
/*  710 */       return (Double.doubleToLongBits(Double2DoubleOpenHashMap.this.key[this.index]) == Double.doubleToLongBits(((Double)e.getKey()).doubleValue()) && 
/*  711 */         Double.doubleToLongBits(Double2DoubleOpenHashMap.this.value[this.index]) == Double.doubleToLongBits(((Double)e.getValue()).doubleValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  715 */       return HashCommon.double2int(Double2DoubleOpenHashMap.this.key[this.index]) ^ 
/*  716 */         HashCommon.double2int(Double2DoubleOpenHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  720 */       return Double2DoubleOpenHashMap.this.key[this.index] + "=>" + Double2DoubleOpenHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  730 */     int pos = Double2DoubleOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  737 */     int last = -1;
/*      */     
/*  739 */     int c = Double2DoubleOpenHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  743 */     boolean mustReturnNullKey = Double2DoubleOpenHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     DoubleArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  750 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  753 */       if (!hasNext())
/*  754 */         throw new NoSuchElementException(); 
/*  755 */       this.c--;
/*  756 */       if (this.mustReturnNullKey) {
/*  757 */         this.mustReturnNullKey = false;
/*  758 */         return this.last = Double2DoubleOpenHashMap.this.n;
/*      */       } 
/*  760 */       double[] key = Double2DoubleOpenHashMap.this.key;
/*      */       while (true) {
/*  762 */         if (--this.pos < 0) {
/*      */           
/*  764 */           this.last = Integer.MIN_VALUE;
/*  765 */           double k = this.wrapped.getDouble(-this.pos - 1);
/*  766 */           int p = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2DoubleOpenHashMap.this.mask;
/*  767 */           while (Double.doubleToLongBits(k) != Double.doubleToLongBits(key[p]))
/*  768 */             p = p + 1 & Double2DoubleOpenHashMap.this.mask; 
/*  769 */           return p;
/*      */         } 
/*  771 */         if (Double.doubleToLongBits(key[this.pos]) != 0L) {
/*  772 */           return this.last = this.pos;
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
/*  786 */       double[] key = Double2DoubleOpenHashMap.this.key; while (true) {
/*      */         double curr; int last;
/*  788 */         pos = (last = pos) + 1 & Double2DoubleOpenHashMap.this.mask;
/*      */         while (true) {
/*  790 */           if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/*  791 */             key[last] = 0.0D;
/*      */             return;
/*      */           } 
/*  794 */           int slot = (int)HashCommon.mix(Double.doubleToRawLongBits(curr)) & Double2DoubleOpenHashMap.this.mask;
/*  795 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  797 */           pos = pos + 1 & Double2DoubleOpenHashMap.this.mask;
/*      */         } 
/*  799 */         if (pos < last) {
/*  800 */           if (this.wrapped == null)
/*  801 */             this.wrapped = new DoubleArrayList(2); 
/*  802 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  804 */         key[last] = curr;
/*  805 */         Double2DoubleOpenHashMap.this.value[last] = Double2DoubleOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  809 */       if (this.last == -1)
/*  810 */         throw new IllegalStateException(); 
/*  811 */       if (this.last == Double2DoubleOpenHashMap.this.n) {
/*  812 */         Double2DoubleOpenHashMap.this.containsNullKey = false;
/*  813 */       } else if (this.pos >= 0) {
/*  814 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  817 */         Double2DoubleOpenHashMap.this.remove(this.wrapped.getDouble(-this.pos - 1));
/*  818 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  821 */       Double2DoubleOpenHashMap.this.size--;
/*  822 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  827 */       int i = n;
/*  828 */       while (i-- != 0 && hasNext())
/*  829 */         nextEntry(); 
/*  830 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Double2DoubleMap.Entry> { private Double2DoubleOpenHashMap.MapEntry entry;
/*      */     
/*      */     public Double2DoubleOpenHashMap.MapEntry next() {
/*  837 */       return this.entry = new Double2DoubleOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  841 */       super.remove();
/*  842 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Double2DoubleMap.Entry> { private FastEntryIterator() {
/*  846 */       this.entry = new Double2DoubleOpenHashMap.MapEntry();
/*      */     } private final Double2DoubleOpenHashMap.MapEntry entry;
/*      */     public Double2DoubleOpenHashMap.MapEntry next() {
/*  849 */       this.entry.index = nextEntry();
/*  850 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Double2DoubleMap.Entry> implements Double2DoubleMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Double2DoubleMap.Entry> iterator() {
/*  856 */       return new Double2DoubleOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Double2DoubleMap.Entry> fastIterator() {
/*  860 */       return new Double2DoubleOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  865 */       if (!(o instanceof Map.Entry))
/*  866 */         return false; 
/*  867 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  868 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/*  869 */         return false; 
/*  870 */       if (e.getValue() == null || !(e.getValue() instanceof Double))
/*  871 */         return false; 
/*  872 */       double k = ((Double)e.getKey()).doubleValue();
/*  873 */       double v = ((Double)e.getValue()).doubleValue();
/*  874 */       if (Double.doubleToLongBits(k) == 0L) {
/*  875 */         return (Double2DoubleOpenHashMap.this.containsNullKey && 
/*  876 */           Double.doubleToLongBits(Double2DoubleOpenHashMap.this.value[Double2DoubleOpenHashMap.this.n]) == Double.doubleToLongBits(v));
/*      */       }
/*  878 */       double[] key = Double2DoubleOpenHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/*  881 */       if (Double.doubleToLongBits(
/*  882 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2DoubleOpenHashMap.this.mask]) == 0L)
/*      */       {
/*  884 */         return false; } 
/*  885 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  886 */         return (Double.doubleToLongBits(Double2DoubleOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v));
/*      */       }
/*      */       while (true) {
/*  889 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2DoubleOpenHashMap.this.mask]) == 0L)
/*  890 */           return false; 
/*  891 */         if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  892 */           return (Double.doubleToLongBits(Double2DoubleOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v));
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  898 */       if (!(o instanceof Map.Entry))
/*  899 */         return false; 
/*  900 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  901 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/*  902 */         return false; 
/*  903 */       if (e.getValue() == null || !(e.getValue() instanceof Double))
/*  904 */         return false; 
/*  905 */       double k = ((Double)e.getKey()).doubleValue();
/*  906 */       double v = ((Double)e.getValue()).doubleValue();
/*  907 */       if (Double.doubleToLongBits(k) == 0L) {
/*  908 */         if (Double2DoubleOpenHashMap.this.containsNullKey && Double.doubleToLongBits(Double2DoubleOpenHashMap.this.value[Double2DoubleOpenHashMap.this.n]) == Double.doubleToLongBits(v)) {
/*  909 */           Double2DoubleOpenHashMap.this.removeNullEntry();
/*  910 */           return true;
/*      */         } 
/*  912 */         return false;
/*      */       } 
/*      */       
/*  915 */       double[] key = Double2DoubleOpenHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/*  918 */       if (Double.doubleToLongBits(
/*  919 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2DoubleOpenHashMap.this.mask]) == 0L)
/*      */       {
/*  921 */         return false; } 
/*  922 */       if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
/*  923 */         if (Double.doubleToLongBits(Double2DoubleOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
/*  924 */           Double2DoubleOpenHashMap.this.removeEntry(pos);
/*  925 */           return true;
/*      */         } 
/*  927 */         return false;
/*      */       } 
/*      */       while (true) {
/*  930 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2DoubleOpenHashMap.this.mask]) == 0L)
/*  931 */           return false; 
/*  932 */         if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k) && 
/*  933 */           Double.doubleToLongBits(Double2DoubleOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
/*  934 */           Double2DoubleOpenHashMap.this.removeEntry(pos);
/*  935 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  942 */       return Double2DoubleOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  946 */       Double2DoubleOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Double2DoubleMap.Entry> consumer) {
/*  951 */       if (Double2DoubleOpenHashMap.this.containsNullKey)
/*  952 */         consumer.accept(new AbstractDouble2DoubleMap.BasicEntry(Double2DoubleOpenHashMap.this.key[Double2DoubleOpenHashMap.this.n], Double2DoubleOpenHashMap.this.value[Double2DoubleOpenHashMap.this.n])); 
/*  953 */       for (int pos = Double2DoubleOpenHashMap.this.n; pos-- != 0;) {
/*  954 */         if (Double.doubleToLongBits(Double2DoubleOpenHashMap.this.key[pos]) != 0L)
/*  955 */           consumer.accept(new AbstractDouble2DoubleMap.BasicEntry(Double2DoubleOpenHashMap.this.key[pos], Double2DoubleOpenHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Double2DoubleMap.Entry> consumer) {
/*  960 */       AbstractDouble2DoubleMap.BasicEntry entry = new AbstractDouble2DoubleMap.BasicEntry();
/*  961 */       if (Double2DoubleOpenHashMap.this.containsNullKey) {
/*  962 */         entry.key = Double2DoubleOpenHashMap.this.key[Double2DoubleOpenHashMap.this.n];
/*  963 */         entry.value = Double2DoubleOpenHashMap.this.value[Double2DoubleOpenHashMap.this.n];
/*  964 */         consumer.accept(entry);
/*      */       } 
/*  966 */       for (int pos = Double2DoubleOpenHashMap.this.n; pos-- != 0;) {
/*  967 */         if (Double.doubleToLongBits(Double2DoubleOpenHashMap.this.key[pos]) != 0L) {
/*  968 */           entry.key = Double2DoubleOpenHashMap.this.key[pos];
/*  969 */           entry.value = Double2DoubleOpenHashMap.this.value[pos];
/*  970 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Double2DoubleMap.FastEntrySet double2DoubleEntrySet() {
/*  976 */     if (this.entries == null)
/*  977 */       this.entries = new MapEntrySet(); 
/*  978 */     return this.entries;
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
/*      */     implements DoubleIterator
/*      */   {
/*      */     public double nextDouble() {
/*  995 */       return Double2DoubleOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractDoubleSet { private KeySet() {}
/*      */     
/*      */     public DoubleIterator iterator() {
/* 1001 */       return new Double2DoubleOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/* 1006 */       if (Double2DoubleOpenHashMap.this.containsNullKey)
/* 1007 */         consumer.accept(Double2DoubleOpenHashMap.this.key[Double2DoubleOpenHashMap.this.n]); 
/* 1008 */       for (int pos = Double2DoubleOpenHashMap.this.n; pos-- != 0; ) {
/* 1009 */         double k = Double2DoubleOpenHashMap.this.key[pos];
/* 1010 */         if (Double.doubleToLongBits(k) != 0L)
/* 1011 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1016 */       return Double2DoubleOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(double k) {
/* 1020 */       return Double2DoubleOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(double k) {
/* 1024 */       int oldSize = Double2DoubleOpenHashMap.this.size;
/* 1025 */       Double2DoubleOpenHashMap.this.remove(k);
/* 1026 */       return (Double2DoubleOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1030 */       Double2DoubleOpenHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public DoubleSet keySet() {
/* 1035 */     if (this.keys == null)
/* 1036 */       this.keys = new KeySet(); 
/* 1037 */     return this.keys;
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
/* 1054 */       return Double2DoubleOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public DoubleCollection values() {
/* 1059 */     if (this.values == null)
/* 1060 */       this.values = new AbstractDoubleCollection()
/*      */         {
/*      */           public DoubleIterator iterator() {
/* 1063 */             return new Double2DoubleOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1067 */             return Double2DoubleOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(double v) {
/* 1071 */             return Double2DoubleOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1075 */             Double2DoubleOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(DoubleConsumer consumer)
/*      */           {
/* 1080 */             if (Double2DoubleOpenHashMap.this.containsNullKey)
/* 1081 */               consumer.accept(Double2DoubleOpenHashMap.this.value[Double2DoubleOpenHashMap.this.n]); 
/* 1082 */             for (int pos = Double2DoubleOpenHashMap.this.n; pos-- != 0;) {
/* 1083 */               if (Double.doubleToLongBits(Double2DoubleOpenHashMap.this.key[pos]) != 0L)
/* 1084 */                 consumer.accept(Double2DoubleOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1087 */     return this.values;
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
/* 1104 */     return trim(this.size);
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
/* 1128 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1129 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1130 */       return true; 
/*      */     try {
/* 1132 */       rehash(l);
/* 1133 */     } catch (OutOfMemoryError cantDoIt) {
/* 1134 */       return false;
/*      */     } 
/* 1136 */     return true;
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
/* 1152 */     double[] key = this.key;
/* 1153 */     double[] value = this.value;
/* 1154 */     int mask = newN - 1;
/* 1155 */     double[] newKey = new double[newN + 1];
/* 1156 */     double[] newValue = new double[newN + 1];
/* 1157 */     int i = this.n;
/* 1158 */     for (int j = realSize(); j-- != 0; ) {
/* 1159 */       while (Double.doubleToLongBits(key[--i]) == 0L); int pos;
/* 1160 */       if (Double.doubleToLongBits(newKey[
/* 1161 */             pos = (int)HashCommon.mix(Double.doubleToRawLongBits(key[i])) & mask]) != 0L)
/*      */       {
/* 1163 */         while (Double.doubleToLongBits(newKey[pos = pos + 1 & mask]) != 0L); } 
/* 1164 */       newKey[pos] = key[i];
/* 1165 */       newValue[pos] = value[i];
/*      */     } 
/* 1167 */     newValue[newN] = value[this.n];
/* 1168 */     this.n = newN;
/* 1169 */     this.mask = mask;
/* 1170 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1171 */     this.key = newKey;
/* 1172 */     this.value = newValue;
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
/*      */   public Double2DoubleOpenHashMap clone() {
/*      */     Double2DoubleOpenHashMap c;
/*      */     try {
/* 1189 */       c = (Double2DoubleOpenHashMap)super.clone();
/* 1190 */     } catch (CloneNotSupportedException cantHappen) {
/* 1191 */       throw new InternalError();
/*      */     } 
/* 1193 */     c.keys = null;
/* 1194 */     c.values = null;
/* 1195 */     c.entries = null;
/* 1196 */     c.containsNullKey = this.containsNullKey;
/* 1197 */     c.key = (double[])this.key.clone();
/* 1198 */     c.value = (double[])this.value.clone();
/* 1199 */     return c;
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
/* 1212 */     int h = 0;
/* 1213 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1214 */       while (Double.doubleToLongBits(this.key[i]) == 0L)
/* 1215 */         i++; 
/* 1216 */       t = HashCommon.double2int(this.key[i]);
/* 1217 */       t ^= HashCommon.double2int(this.value[i]);
/* 1218 */       h += t;
/* 1219 */       i++;
/*      */     } 
/*      */     
/* 1222 */     if (this.containsNullKey)
/* 1223 */       h += HashCommon.double2int(this.value[this.n]); 
/* 1224 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1227 */     double[] key = this.key;
/* 1228 */     double[] value = this.value;
/* 1229 */     MapIterator i = new MapIterator();
/* 1230 */     s.defaultWriteObject();
/* 1231 */     for (int j = this.size; j-- != 0; ) {
/* 1232 */       int e = i.nextEntry();
/* 1233 */       s.writeDouble(key[e]);
/* 1234 */       s.writeDouble(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1239 */     s.defaultReadObject();
/* 1240 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1241 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1242 */     this.mask = this.n - 1;
/* 1243 */     double[] key = this.key = new double[this.n + 1];
/* 1244 */     double[] value = this.value = new double[this.n + 1];
/*      */ 
/*      */     
/* 1247 */     for (int i = this.size; i-- != 0; ) {
/* 1248 */       int pos; double k = s.readDouble();
/* 1249 */       double v = s.readDouble();
/* 1250 */       if (Double.doubleToLongBits(k) == 0L) {
/* 1251 */         pos = this.n;
/* 1252 */         this.containsNullKey = true;
/*      */       } else {
/* 1254 */         pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask;
/* 1255 */         while (Double.doubleToLongBits(key[pos]) != 0L)
/* 1256 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1258 */       key[pos] = k;
/* 1259 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2DoubleOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */