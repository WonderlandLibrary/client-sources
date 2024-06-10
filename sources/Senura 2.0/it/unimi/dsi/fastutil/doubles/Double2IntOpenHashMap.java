/*      */ package it.unimi.dsi.fastutil.doubles;
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
/*      */ public class Double2IntOpenHashMap
/*      */   extends AbstractDouble2IntMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient double[] key;
/*      */   protected transient int[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Double2IntMap.FastEntrySet entries;
/*      */   protected transient DoubleSet keys;
/*      */   protected transient IntCollection values;
/*      */   
/*      */   public Double2IntOpenHashMap(int expected, float f) {
/*   96 */     if (f <= 0.0F || f > 1.0F)
/*   97 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*   98 */     if (expected < 0)
/*   99 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  100 */     this.f = f;
/*  101 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  102 */     this.mask = this.n - 1;
/*  103 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  104 */     this.key = new double[this.n + 1];
/*  105 */     this.value = new int[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2IntOpenHashMap(int expected) {
/*  114 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2IntOpenHashMap() {
/*  122 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2IntOpenHashMap(Map<? extends Double, ? extends Integer> m, float f) {
/*  133 */     this(m.size(), f);
/*  134 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2IntOpenHashMap(Map<? extends Double, ? extends Integer> m) {
/*  144 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2IntOpenHashMap(Double2IntMap m, float f) {
/*  155 */     this(m.size(), f);
/*  156 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2IntOpenHashMap(Double2IntMap m) {
/*  166 */     this(m, 0.75F);
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
/*      */   public Double2IntOpenHashMap(double[] k, int[] v, float f) {
/*  181 */     this(k.length, f);
/*  182 */     if (k.length != v.length) {
/*  183 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  185 */     for (int i = 0; i < k.length; i++) {
/*  186 */       put(k[i], v[i]);
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
/*      */   public Double2IntOpenHashMap(double[] k, int[] v) {
/*  200 */     this(k, v, 0.75F);
/*      */   }
/*      */   private int realSize() {
/*  203 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  206 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  207 */     if (needed > this.n)
/*  208 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  211 */     int needed = (int)Math.min(1073741824L, 
/*  212 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  213 */     if (needed > this.n)
/*  214 */       rehash(needed); 
/*      */   }
/*      */   private int removeEntry(int pos) {
/*  217 */     int oldValue = this.value[pos];
/*  218 */     this.size--;
/*  219 */     shiftKeys(pos);
/*  220 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  221 */       rehash(this.n / 2); 
/*  222 */     return oldValue;
/*      */   }
/*      */   private int removeNullEntry() {
/*  225 */     this.containsNullKey = false;
/*  226 */     int oldValue = this.value[this.n];
/*  227 */     this.size--;
/*  228 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  229 */       rehash(this.n / 2); 
/*  230 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Double, ? extends Integer> m) {
/*  234 */     if (this.f <= 0.5D) {
/*  235 */       ensureCapacity(m.size());
/*      */     } else {
/*  237 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  239 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(double k) {
/*  243 */     if (Double.doubleToLongBits(k) == 0L) {
/*  244 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  246 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  249 */     if (Double.doubleToLongBits(
/*  250 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  252 */       return -(pos + 1); } 
/*  253 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  254 */       return pos;
/*      */     }
/*      */     while (true) {
/*  257 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  258 */         return -(pos + 1); 
/*  259 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr))
/*  260 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, double k, int v) {
/*  264 */     if (pos == this.n)
/*  265 */       this.containsNullKey = true; 
/*  266 */     this.key[pos] = k;
/*  267 */     this.value[pos] = v;
/*  268 */     if (this.size++ >= this.maxFill) {
/*  269 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public int put(double k, int v) {
/*  275 */     int pos = find(k);
/*  276 */     if (pos < 0) {
/*  277 */       insert(-pos - 1, k, v);
/*  278 */       return this.defRetValue;
/*      */     } 
/*  280 */     int oldValue = this.value[pos];
/*  281 */     this.value[pos] = v;
/*  282 */     return oldValue;
/*      */   }
/*      */   private int addToValue(int pos, int incr) {
/*  285 */     int oldValue = this.value[pos];
/*  286 */     this.value[pos] = oldValue + incr;
/*  287 */     return oldValue;
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
/*      */   public int addTo(double k, int incr) {
/*      */     int pos;
/*  307 */     if (Double.doubleToLongBits(k) == 0L) {
/*  308 */       if (this.containsNullKey)
/*  309 */         return addToValue(this.n, incr); 
/*  310 */       pos = this.n;
/*  311 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  314 */       double[] key = this.key;
/*      */       double curr;
/*  316 */       if (Double.doubleToLongBits(
/*  317 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) != 0L) {
/*      */         
/*  319 */         if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k))
/*  320 */           return addToValue(pos, incr); 
/*  321 */         while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
/*  322 */           if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k))
/*  323 */             return addToValue(pos, incr); 
/*      */         } 
/*      */       } 
/*  326 */     }  this.key[pos] = k;
/*  327 */     this.value[pos] = this.defRetValue + incr;
/*  328 */     if (this.size++ >= this.maxFill) {
/*  329 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  332 */     return this.defRetValue;
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
/*  345 */     double[] key = this.key; while (true) {
/*      */       double curr; int last;
/*  347 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  349 */         if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/*  350 */           key[last] = 0.0D;
/*      */           return;
/*      */         } 
/*  353 */         int slot = (int)HashCommon.mix(Double.doubleToRawLongBits(curr)) & this.mask;
/*  354 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  356 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  358 */       key[last] = curr;
/*  359 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int remove(double k) {
/*  365 */     if (Double.doubleToLongBits(k) == 0L) {
/*  366 */       if (this.containsNullKey)
/*  367 */         return removeNullEntry(); 
/*  368 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  371 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  374 */     if (Double.doubleToLongBits(
/*  375 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  377 */       return this.defRetValue; } 
/*  378 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr))
/*  379 */       return removeEntry(pos); 
/*      */     while (true) {
/*  381 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  382 */         return this.defRetValue; 
/*  383 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  384 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public int get(double k) {
/*  390 */     if (Double.doubleToLongBits(k) == 0L) {
/*  391 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  393 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  396 */     if (Double.doubleToLongBits(
/*  397 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  399 */       return this.defRetValue; } 
/*  400 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  401 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  404 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  405 */         return this.defRetValue; 
/*  406 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  407 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(double k) {
/*  413 */     if (Double.doubleToLongBits(k) == 0L) {
/*  414 */       return this.containsNullKey;
/*      */     }
/*  416 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  419 */     if (Double.doubleToLongBits(
/*  420 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  422 */       return false; } 
/*  423 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  424 */       return true;
/*      */     }
/*      */     while (true) {
/*  427 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  428 */         return false; 
/*  429 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr))
/*  430 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(int v) {
/*  435 */     int[] value = this.value;
/*  436 */     double[] key = this.key;
/*  437 */     if (this.containsNullKey && value[this.n] == v)
/*  438 */       return true; 
/*  439 */     for (int i = this.n; i-- != 0;) {
/*  440 */       if (Double.doubleToLongBits(key[i]) != 0L && value[i] == v)
/*  441 */         return true; 
/*  442 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getOrDefault(double k, int defaultValue) {
/*  448 */     if (Double.doubleToLongBits(k) == 0L) {
/*  449 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  451 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  454 */     if (Double.doubleToLongBits(
/*  455 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  457 */       return defaultValue; } 
/*  458 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  459 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  462 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  463 */         return defaultValue; 
/*  464 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  465 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public int putIfAbsent(double k, int v) {
/*  471 */     int pos = find(k);
/*  472 */     if (pos >= 0)
/*  473 */       return this.value[pos]; 
/*  474 */     insert(-pos - 1, k, v);
/*  475 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(double k, int v) {
/*  481 */     if (Double.doubleToLongBits(k) == 0L) {
/*  482 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  483 */         removeNullEntry();
/*  484 */         return true;
/*      */       } 
/*  486 */       return false;
/*      */     } 
/*      */     
/*  489 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  492 */     if (Double.doubleToLongBits(
/*  493 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  495 */       return false; } 
/*  496 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr) && v == this.value[pos]) {
/*  497 */       removeEntry(pos);
/*  498 */       return true;
/*      */     } 
/*      */     while (true) {
/*  501 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  502 */         return false; 
/*  503 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr) && v == this.value[pos]) {
/*  504 */         removeEntry(pos);
/*  505 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(double k, int oldValue, int v) {
/*  512 */     int pos = find(k);
/*  513 */     if (pos < 0 || oldValue != this.value[pos])
/*  514 */       return false; 
/*  515 */     this.value[pos] = v;
/*  516 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public int replace(double k, int v) {
/*  521 */     int pos = find(k);
/*  522 */     if (pos < 0)
/*  523 */       return this.defRetValue; 
/*  524 */     int oldValue = this.value[pos];
/*  525 */     this.value[pos] = v;
/*  526 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public int computeIfAbsent(double k, DoubleToIntFunction mappingFunction) {
/*  531 */     Objects.requireNonNull(mappingFunction);
/*  532 */     int pos = find(k);
/*  533 */     if (pos >= 0)
/*  534 */       return this.value[pos]; 
/*  535 */     int newValue = mappingFunction.applyAsInt(k);
/*  536 */     insert(-pos - 1, k, newValue);
/*  537 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int computeIfAbsentNullable(double k, DoubleFunction<? extends Integer> mappingFunction) {
/*  543 */     Objects.requireNonNull(mappingFunction);
/*  544 */     int pos = find(k);
/*  545 */     if (pos >= 0)
/*  546 */       return this.value[pos]; 
/*  547 */     Integer newValue = mappingFunction.apply(k);
/*  548 */     if (newValue == null)
/*  549 */       return this.defRetValue; 
/*  550 */     int v = newValue.intValue();
/*  551 */     insert(-pos - 1, k, v);
/*  552 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int computeIfPresent(double k, BiFunction<? super Double, ? super Integer, ? extends Integer> remappingFunction) {
/*  558 */     Objects.requireNonNull(remappingFunction);
/*  559 */     int pos = find(k);
/*  560 */     if (pos < 0)
/*  561 */       return this.defRetValue; 
/*  562 */     Integer newValue = remappingFunction.apply(Double.valueOf(k), Integer.valueOf(this.value[pos]));
/*  563 */     if (newValue == null) {
/*  564 */       if (Double.doubleToLongBits(k) == 0L) {
/*  565 */         removeNullEntry();
/*      */       } else {
/*  567 */         removeEntry(pos);
/*  568 */       }  return this.defRetValue;
/*      */     } 
/*  570 */     this.value[pos] = newValue.intValue(); return newValue.intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int compute(double k, BiFunction<? super Double, ? super Integer, ? extends Integer> remappingFunction) {
/*  576 */     Objects.requireNonNull(remappingFunction);
/*  577 */     int pos = find(k);
/*  578 */     Integer newValue = remappingFunction.apply(Double.valueOf(k), 
/*  579 */         (pos >= 0) ? Integer.valueOf(this.value[pos]) : null);
/*  580 */     if (newValue == null) {
/*  581 */       if (pos >= 0)
/*  582 */         if (Double.doubleToLongBits(k) == 0L) {
/*  583 */           removeNullEntry();
/*      */         } else {
/*  585 */           removeEntry(pos);
/*      */         }  
/*  587 */       return this.defRetValue;
/*      */     } 
/*  589 */     int newVal = newValue.intValue();
/*  590 */     if (pos < 0) {
/*  591 */       insert(-pos - 1, k, newVal);
/*  592 */       return newVal;
/*      */     } 
/*  594 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int merge(double k, int v, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
/*  600 */     Objects.requireNonNull(remappingFunction);
/*  601 */     int pos = find(k);
/*  602 */     if (pos < 0) {
/*  603 */       insert(-pos - 1, k, v);
/*  604 */       return v;
/*      */     } 
/*  606 */     Integer newValue = remappingFunction.apply(Integer.valueOf(this.value[pos]), Integer.valueOf(v));
/*  607 */     if (newValue == null) {
/*  608 */       if (Double.doubleToLongBits(k) == 0L) {
/*  609 */         removeNullEntry();
/*      */       } else {
/*  611 */         removeEntry(pos);
/*  612 */       }  return this.defRetValue;
/*      */     } 
/*  614 */     this.value[pos] = newValue.intValue(); return newValue.intValue();
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
/*  625 */     if (this.size == 0)
/*      */       return; 
/*  627 */     this.size = 0;
/*  628 */     this.containsNullKey = false;
/*  629 */     Arrays.fill(this.key, 0.0D);
/*      */   }
/*      */   
/*      */   public int size() {
/*  633 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  637 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Double2IntMap.Entry, Map.Entry<Double, Integer>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  649 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public double getDoubleKey() {
/*  655 */       return Double2IntOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public int getIntValue() {
/*  659 */       return Double2IntOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public int setValue(int v) {
/*  663 */       int oldValue = Double2IntOpenHashMap.this.value[this.index];
/*  664 */       Double2IntOpenHashMap.this.value[this.index] = v;
/*  665 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double getKey() {
/*  675 */       return Double.valueOf(Double2IntOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer getValue() {
/*  685 */       return Integer.valueOf(Double2IntOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer setValue(Integer v) {
/*  695 */       return Integer.valueOf(setValue(v.intValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  700 */       if (!(o instanceof Map.Entry))
/*  701 */         return false; 
/*  702 */       Map.Entry<Double, Integer> e = (Map.Entry<Double, Integer>)o;
/*  703 */       return (Double.doubleToLongBits(Double2IntOpenHashMap.this.key[this.index]) == Double.doubleToLongBits(((Double)e.getKey()).doubleValue()) && Double2IntOpenHashMap.this.value[this.index] == ((Integer)e
/*  704 */         .getValue()).intValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  708 */       return HashCommon.double2int(Double2IntOpenHashMap.this.key[this.index]) ^ Double2IntOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/*  712 */       return Double2IntOpenHashMap.this.key[this.index] + "=>" + Double2IntOpenHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  722 */     int pos = Double2IntOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  729 */     int last = -1;
/*      */     
/*  731 */     int c = Double2IntOpenHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  735 */     boolean mustReturnNullKey = Double2IntOpenHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     DoubleArrayList wrapped;
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
/*  750 */         return this.last = Double2IntOpenHashMap.this.n;
/*      */       } 
/*  752 */       double[] key = Double2IntOpenHashMap.this.key;
/*      */       while (true) {
/*  754 */         if (--this.pos < 0) {
/*      */           
/*  756 */           this.last = Integer.MIN_VALUE;
/*  757 */           double k = this.wrapped.getDouble(-this.pos - 1);
/*  758 */           int p = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2IntOpenHashMap.this.mask;
/*  759 */           while (Double.doubleToLongBits(k) != Double.doubleToLongBits(key[p]))
/*  760 */             p = p + 1 & Double2IntOpenHashMap.this.mask; 
/*  761 */           return p;
/*      */         } 
/*  763 */         if (Double.doubleToLongBits(key[this.pos]) != 0L) {
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
/*  778 */       double[] key = Double2IntOpenHashMap.this.key; while (true) {
/*      */         double curr; int last;
/*  780 */         pos = (last = pos) + 1 & Double2IntOpenHashMap.this.mask;
/*      */         while (true) {
/*  782 */           if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/*  783 */             key[last] = 0.0D;
/*      */             return;
/*      */           } 
/*  786 */           int slot = (int)HashCommon.mix(Double.doubleToRawLongBits(curr)) & Double2IntOpenHashMap.this.mask;
/*  787 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  789 */           pos = pos + 1 & Double2IntOpenHashMap.this.mask;
/*      */         } 
/*  791 */         if (pos < last) {
/*  792 */           if (this.wrapped == null)
/*  793 */             this.wrapped = new DoubleArrayList(2); 
/*  794 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  796 */         key[last] = curr;
/*  797 */         Double2IntOpenHashMap.this.value[last] = Double2IntOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  801 */       if (this.last == -1)
/*  802 */         throw new IllegalStateException(); 
/*  803 */       if (this.last == Double2IntOpenHashMap.this.n) {
/*  804 */         Double2IntOpenHashMap.this.containsNullKey = false;
/*  805 */       } else if (this.pos >= 0) {
/*  806 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  809 */         Double2IntOpenHashMap.this.remove(this.wrapped.getDouble(-this.pos - 1));
/*  810 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  813 */       Double2IntOpenHashMap.this.size--;
/*  814 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  819 */       int i = n;
/*  820 */       while (i-- != 0 && hasNext())
/*  821 */         nextEntry(); 
/*  822 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Double2IntMap.Entry> { private Double2IntOpenHashMap.MapEntry entry;
/*      */     
/*      */     public Double2IntOpenHashMap.MapEntry next() {
/*  829 */       return this.entry = new Double2IntOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  833 */       super.remove();
/*  834 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Double2IntMap.Entry> { private FastEntryIterator() {
/*  838 */       this.entry = new Double2IntOpenHashMap.MapEntry();
/*      */     } private final Double2IntOpenHashMap.MapEntry entry;
/*      */     public Double2IntOpenHashMap.MapEntry next() {
/*  841 */       this.entry.index = nextEntry();
/*  842 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Double2IntMap.Entry> implements Double2IntMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Double2IntMap.Entry> iterator() {
/*  848 */       return new Double2IntOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Double2IntMap.Entry> fastIterator() {
/*  852 */       return new Double2IntOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  857 */       if (!(o instanceof Map.Entry))
/*  858 */         return false; 
/*  859 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  860 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/*  861 */         return false; 
/*  862 */       if (e.getValue() == null || !(e.getValue() instanceof Integer))
/*  863 */         return false; 
/*  864 */       double k = ((Double)e.getKey()).doubleValue();
/*  865 */       int v = ((Integer)e.getValue()).intValue();
/*  866 */       if (Double.doubleToLongBits(k) == 0L) {
/*  867 */         return (Double2IntOpenHashMap.this.containsNullKey && Double2IntOpenHashMap.this.value[Double2IntOpenHashMap.this.n] == v);
/*      */       }
/*  869 */       double[] key = Double2IntOpenHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/*  872 */       if (Double.doubleToLongBits(
/*  873 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2IntOpenHashMap.this.mask]) == 0L)
/*      */       {
/*  875 */         return false; } 
/*  876 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  877 */         return (Double2IntOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  880 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2IntOpenHashMap.this.mask]) == 0L)
/*  881 */           return false; 
/*  882 */         if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  883 */           return (Double2IntOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  889 */       if (!(o instanceof Map.Entry))
/*  890 */         return false; 
/*  891 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  892 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/*  893 */         return false; 
/*  894 */       if (e.getValue() == null || !(e.getValue() instanceof Integer))
/*  895 */         return false; 
/*  896 */       double k = ((Double)e.getKey()).doubleValue();
/*  897 */       int v = ((Integer)e.getValue()).intValue();
/*  898 */       if (Double.doubleToLongBits(k) == 0L) {
/*  899 */         if (Double2IntOpenHashMap.this.containsNullKey && Double2IntOpenHashMap.this.value[Double2IntOpenHashMap.this.n] == v) {
/*  900 */           Double2IntOpenHashMap.this.removeNullEntry();
/*  901 */           return true;
/*      */         } 
/*  903 */         return false;
/*      */       } 
/*      */       
/*  906 */       double[] key = Double2IntOpenHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/*  909 */       if (Double.doubleToLongBits(
/*  910 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2IntOpenHashMap.this.mask]) == 0L)
/*      */       {
/*  912 */         return false; } 
/*  913 */       if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
/*  914 */         if (Double2IntOpenHashMap.this.value[pos] == v) {
/*  915 */           Double2IntOpenHashMap.this.removeEntry(pos);
/*  916 */           return true;
/*      */         } 
/*  918 */         return false;
/*      */       } 
/*      */       while (true) {
/*  921 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2IntOpenHashMap.this.mask]) == 0L)
/*  922 */           return false; 
/*  923 */         if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k) && 
/*  924 */           Double2IntOpenHashMap.this.value[pos] == v) {
/*  925 */           Double2IntOpenHashMap.this.removeEntry(pos);
/*  926 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  933 */       return Double2IntOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  937 */       Double2IntOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Double2IntMap.Entry> consumer) {
/*  942 */       if (Double2IntOpenHashMap.this.containsNullKey)
/*  943 */         consumer.accept(new AbstractDouble2IntMap.BasicEntry(Double2IntOpenHashMap.this.key[Double2IntOpenHashMap.this.n], Double2IntOpenHashMap.this.value[Double2IntOpenHashMap.this.n])); 
/*  944 */       for (int pos = Double2IntOpenHashMap.this.n; pos-- != 0;) {
/*  945 */         if (Double.doubleToLongBits(Double2IntOpenHashMap.this.key[pos]) != 0L)
/*  946 */           consumer.accept(new AbstractDouble2IntMap.BasicEntry(Double2IntOpenHashMap.this.key[pos], Double2IntOpenHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Double2IntMap.Entry> consumer) {
/*  951 */       AbstractDouble2IntMap.BasicEntry entry = new AbstractDouble2IntMap.BasicEntry();
/*  952 */       if (Double2IntOpenHashMap.this.containsNullKey) {
/*  953 */         entry.key = Double2IntOpenHashMap.this.key[Double2IntOpenHashMap.this.n];
/*  954 */         entry.value = Double2IntOpenHashMap.this.value[Double2IntOpenHashMap.this.n];
/*  955 */         consumer.accept(entry);
/*      */       } 
/*  957 */       for (int pos = Double2IntOpenHashMap.this.n; pos-- != 0;) {
/*  958 */         if (Double.doubleToLongBits(Double2IntOpenHashMap.this.key[pos]) != 0L) {
/*  959 */           entry.key = Double2IntOpenHashMap.this.key[pos];
/*  960 */           entry.value = Double2IntOpenHashMap.this.value[pos];
/*  961 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Double2IntMap.FastEntrySet double2IntEntrySet() {
/*  967 */     if (this.entries == null)
/*  968 */       this.entries = new MapEntrySet(); 
/*  969 */     return this.entries;
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
/*  986 */       return Double2IntOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractDoubleSet { private KeySet() {}
/*      */     
/*      */     public DoubleIterator iterator() {
/*  992 */       return new Double2IntOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/*  997 */       if (Double2IntOpenHashMap.this.containsNullKey)
/*  998 */         consumer.accept(Double2IntOpenHashMap.this.key[Double2IntOpenHashMap.this.n]); 
/*  999 */       for (int pos = Double2IntOpenHashMap.this.n; pos-- != 0; ) {
/* 1000 */         double k = Double2IntOpenHashMap.this.key[pos];
/* 1001 */         if (Double.doubleToLongBits(k) != 0L)
/* 1002 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1007 */       return Double2IntOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(double k) {
/* 1011 */       return Double2IntOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(double k) {
/* 1015 */       int oldSize = Double2IntOpenHashMap.this.size;
/* 1016 */       Double2IntOpenHashMap.this.remove(k);
/* 1017 */       return (Double2IntOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1021 */       Double2IntOpenHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public DoubleSet keySet() {
/* 1026 */     if (this.keys == null)
/* 1027 */       this.keys = new KeySet(); 
/* 1028 */     return this.keys;
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
/* 1045 */       return Double2IntOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public IntCollection values() {
/* 1050 */     if (this.values == null)
/* 1051 */       this.values = (IntCollection)new AbstractIntCollection()
/*      */         {
/*      */           public IntIterator iterator() {
/* 1054 */             return new Double2IntOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1058 */             return Double2IntOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(int v) {
/* 1062 */             return Double2IntOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1066 */             Double2IntOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(IntConsumer consumer)
/*      */           {
/* 1071 */             if (Double2IntOpenHashMap.this.containsNullKey)
/* 1072 */               consumer.accept(Double2IntOpenHashMap.this.value[Double2IntOpenHashMap.this.n]); 
/* 1073 */             for (int pos = Double2IntOpenHashMap.this.n; pos-- != 0;) {
/* 1074 */               if (Double.doubleToLongBits(Double2IntOpenHashMap.this.key[pos]) != 0L)
/* 1075 */                 consumer.accept(Double2IntOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1078 */     return this.values;
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
/* 1095 */     return trim(this.size);
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
/* 1119 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1120 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1121 */       return true; 
/*      */     try {
/* 1123 */       rehash(l);
/* 1124 */     } catch (OutOfMemoryError cantDoIt) {
/* 1125 */       return false;
/*      */     } 
/* 1127 */     return true;
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
/* 1143 */     double[] key = this.key;
/* 1144 */     int[] value = this.value;
/* 1145 */     int mask = newN - 1;
/* 1146 */     double[] newKey = new double[newN + 1];
/* 1147 */     int[] newValue = new int[newN + 1];
/* 1148 */     int i = this.n;
/* 1149 */     for (int j = realSize(); j-- != 0; ) {
/* 1150 */       while (Double.doubleToLongBits(key[--i]) == 0L); int pos;
/* 1151 */       if (Double.doubleToLongBits(newKey[
/* 1152 */             pos = (int)HashCommon.mix(Double.doubleToRawLongBits(key[i])) & mask]) != 0L)
/*      */       {
/* 1154 */         while (Double.doubleToLongBits(newKey[pos = pos + 1 & mask]) != 0L); } 
/* 1155 */       newKey[pos] = key[i];
/* 1156 */       newValue[pos] = value[i];
/*      */     } 
/* 1158 */     newValue[newN] = value[this.n];
/* 1159 */     this.n = newN;
/* 1160 */     this.mask = mask;
/* 1161 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1162 */     this.key = newKey;
/* 1163 */     this.value = newValue;
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
/*      */   public Double2IntOpenHashMap clone() {
/*      */     Double2IntOpenHashMap c;
/*      */     try {
/* 1180 */       c = (Double2IntOpenHashMap)super.clone();
/* 1181 */     } catch (CloneNotSupportedException cantHappen) {
/* 1182 */       throw new InternalError();
/*      */     } 
/* 1184 */     c.keys = null;
/* 1185 */     c.values = null;
/* 1186 */     c.entries = null;
/* 1187 */     c.containsNullKey = this.containsNullKey;
/* 1188 */     c.key = (double[])this.key.clone();
/* 1189 */     c.value = (int[])this.value.clone();
/* 1190 */     return c;
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
/* 1203 */     int h = 0;
/* 1204 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1205 */       while (Double.doubleToLongBits(this.key[i]) == 0L)
/* 1206 */         i++; 
/* 1207 */       t = HashCommon.double2int(this.key[i]);
/* 1208 */       t ^= this.value[i];
/* 1209 */       h += t;
/* 1210 */       i++;
/*      */     } 
/*      */     
/* 1213 */     if (this.containsNullKey)
/* 1214 */       h += this.value[this.n]; 
/* 1215 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1218 */     double[] key = this.key;
/* 1219 */     int[] value = this.value;
/* 1220 */     MapIterator i = new MapIterator();
/* 1221 */     s.defaultWriteObject();
/* 1222 */     for (int j = this.size; j-- != 0; ) {
/* 1223 */       int e = i.nextEntry();
/* 1224 */       s.writeDouble(key[e]);
/* 1225 */       s.writeInt(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1230 */     s.defaultReadObject();
/* 1231 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1232 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1233 */     this.mask = this.n - 1;
/* 1234 */     double[] key = this.key = new double[this.n + 1];
/* 1235 */     int[] value = this.value = new int[this.n + 1];
/*      */ 
/*      */     
/* 1238 */     for (int i = this.size; i-- != 0; ) {
/* 1239 */       int pos; double k = s.readDouble();
/* 1240 */       int v = s.readInt();
/* 1241 */       if (Double.doubleToLongBits(k) == 0L) {
/* 1242 */         pos = this.n;
/* 1243 */         this.containsNullKey = true;
/*      */       } else {
/* 1245 */         pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask;
/* 1246 */         while (Double.doubleToLongBits(key[pos]) != 0L)
/* 1247 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1249 */       key[pos] = k;
/* 1250 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2IntOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */