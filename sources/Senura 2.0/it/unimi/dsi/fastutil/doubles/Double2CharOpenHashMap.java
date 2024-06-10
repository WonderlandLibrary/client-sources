/*      */ package it.unimi.dsi.fastutil.doubles;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
/*      */ import it.unimi.dsi.fastutil.chars.CharCollection;
/*      */ import it.unimi.dsi.fastutil.chars.CharIterator;
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
/*      */ public class Double2CharOpenHashMap
/*      */   extends AbstractDouble2CharMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient double[] key;
/*      */   protected transient char[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Double2CharMap.FastEntrySet entries;
/*      */   protected transient DoubleSet keys;
/*      */   protected transient CharCollection values;
/*      */   
/*      */   public Double2CharOpenHashMap(int expected, float f) {
/*   96 */     if (f <= 0.0F || f > 1.0F)
/*   97 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*   98 */     if (expected < 0)
/*   99 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  100 */     this.f = f;
/*  101 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  102 */     this.mask = this.n - 1;
/*  103 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  104 */     this.key = new double[this.n + 1];
/*  105 */     this.value = new char[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2CharOpenHashMap(int expected) {
/*  114 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2CharOpenHashMap() {
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
/*      */   public Double2CharOpenHashMap(Map<? extends Double, ? extends Character> m, float f) {
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
/*      */   public Double2CharOpenHashMap(Map<? extends Double, ? extends Character> m) {
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
/*      */   public Double2CharOpenHashMap(Double2CharMap m, float f) {
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
/*      */   public Double2CharOpenHashMap(Double2CharMap m) {
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
/*      */   public Double2CharOpenHashMap(double[] k, char[] v, float f) {
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
/*      */   public Double2CharOpenHashMap(double[] k, char[] v) {
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
/*      */   private char removeEntry(int pos) {
/*  217 */     char oldValue = this.value[pos];
/*  218 */     this.size--;
/*  219 */     shiftKeys(pos);
/*  220 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  221 */       rehash(this.n / 2); 
/*  222 */     return oldValue;
/*      */   }
/*      */   private char removeNullEntry() {
/*  225 */     this.containsNullKey = false;
/*  226 */     char oldValue = this.value[this.n];
/*  227 */     this.size--;
/*  228 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  229 */       rehash(this.n / 2); 
/*  230 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Double, ? extends Character> m) {
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
/*      */   private void insert(int pos, double k, char v) {
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
/*      */   public char put(double k, char v) {
/*  275 */     int pos = find(k);
/*  276 */     if (pos < 0) {
/*  277 */       insert(-pos - 1, k, v);
/*  278 */       return this.defRetValue;
/*      */     } 
/*  280 */     char oldValue = this.value[pos];
/*  281 */     this.value[pos] = v;
/*  282 */     return oldValue;
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
/*  295 */     double[] key = this.key; while (true) {
/*      */       double curr; int last;
/*  297 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  299 */         if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/*  300 */           key[last] = 0.0D;
/*      */           return;
/*      */         } 
/*  303 */         int slot = (int)HashCommon.mix(Double.doubleToRawLongBits(curr)) & this.mask;
/*  304 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  306 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  308 */       key[last] = curr;
/*  309 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public char remove(double k) {
/*  315 */     if (Double.doubleToLongBits(k) == 0L) {
/*  316 */       if (this.containsNullKey)
/*  317 */         return removeNullEntry(); 
/*  318 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  321 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  324 */     if (Double.doubleToLongBits(
/*  325 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  327 */       return this.defRetValue; } 
/*  328 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr))
/*  329 */       return removeEntry(pos); 
/*      */     while (true) {
/*  331 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  332 */         return this.defRetValue; 
/*  333 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  334 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public char get(double k) {
/*  340 */     if (Double.doubleToLongBits(k) == 0L) {
/*  341 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  343 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  346 */     if (Double.doubleToLongBits(
/*  347 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  349 */       return this.defRetValue; } 
/*  350 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  351 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  354 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  355 */         return this.defRetValue; 
/*  356 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  357 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(double k) {
/*  363 */     if (Double.doubleToLongBits(k) == 0L) {
/*  364 */       return this.containsNullKey;
/*      */     }
/*  366 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  369 */     if (Double.doubleToLongBits(
/*  370 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  372 */       return false; } 
/*  373 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  374 */       return true;
/*      */     }
/*      */     while (true) {
/*  377 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  378 */         return false; 
/*  379 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr))
/*  380 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(char v) {
/*  385 */     char[] value = this.value;
/*  386 */     double[] key = this.key;
/*  387 */     if (this.containsNullKey && value[this.n] == v)
/*  388 */       return true; 
/*  389 */     for (int i = this.n; i-- != 0;) {
/*  390 */       if (Double.doubleToLongBits(key[i]) != 0L && value[i] == v)
/*  391 */         return true; 
/*  392 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char getOrDefault(double k, char defaultValue) {
/*  398 */     if (Double.doubleToLongBits(k) == 0L) {
/*  399 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  401 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  404 */     if (Double.doubleToLongBits(
/*  405 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  407 */       return defaultValue; } 
/*  408 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  409 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  412 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  413 */         return defaultValue; 
/*  414 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  415 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public char putIfAbsent(double k, char v) {
/*  421 */     int pos = find(k);
/*  422 */     if (pos >= 0)
/*  423 */       return this.value[pos]; 
/*  424 */     insert(-pos - 1, k, v);
/*  425 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(double k, char v) {
/*  431 */     if (Double.doubleToLongBits(k) == 0L) {
/*  432 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  433 */         removeNullEntry();
/*  434 */         return true;
/*      */       } 
/*  436 */       return false;
/*      */     } 
/*      */     
/*  439 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  442 */     if (Double.doubleToLongBits(
/*  443 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  445 */       return false; } 
/*  446 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr) && v == this.value[pos]) {
/*  447 */       removeEntry(pos);
/*  448 */       return true;
/*      */     } 
/*      */     while (true) {
/*  451 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  452 */         return false; 
/*  453 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr) && v == this.value[pos]) {
/*  454 */         removeEntry(pos);
/*  455 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(double k, char oldValue, char v) {
/*  462 */     int pos = find(k);
/*  463 */     if (pos < 0 || oldValue != this.value[pos])
/*  464 */       return false; 
/*  465 */     this.value[pos] = v;
/*  466 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public char replace(double k, char v) {
/*  471 */     int pos = find(k);
/*  472 */     if (pos < 0)
/*  473 */       return this.defRetValue; 
/*  474 */     char oldValue = this.value[pos];
/*  475 */     this.value[pos] = v;
/*  476 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public char computeIfAbsent(double k, DoubleToIntFunction mappingFunction) {
/*  481 */     Objects.requireNonNull(mappingFunction);
/*  482 */     int pos = find(k);
/*  483 */     if (pos >= 0)
/*  484 */       return this.value[pos]; 
/*  485 */     char newValue = SafeMath.safeIntToChar(mappingFunction.applyAsInt(k));
/*  486 */     insert(-pos - 1, k, newValue);
/*  487 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char computeIfAbsentNullable(double k, DoubleFunction<? extends Character> mappingFunction) {
/*  493 */     Objects.requireNonNull(mappingFunction);
/*  494 */     int pos = find(k);
/*  495 */     if (pos >= 0)
/*  496 */       return this.value[pos]; 
/*  497 */     Character newValue = mappingFunction.apply(k);
/*  498 */     if (newValue == null)
/*  499 */       return this.defRetValue; 
/*  500 */     char v = newValue.charValue();
/*  501 */     insert(-pos - 1, k, v);
/*  502 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char computeIfPresent(double k, BiFunction<? super Double, ? super Character, ? extends Character> remappingFunction) {
/*  508 */     Objects.requireNonNull(remappingFunction);
/*  509 */     int pos = find(k);
/*  510 */     if (pos < 0)
/*  511 */       return this.defRetValue; 
/*  512 */     Character newValue = remappingFunction.apply(Double.valueOf(k), Character.valueOf(this.value[pos]));
/*  513 */     if (newValue == null) {
/*  514 */       if (Double.doubleToLongBits(k) == 0L) {
/*  515 */         removeNullEntry();
/*      */       } else {
/*  517 */         removeEntry(pos);
/*  518 */       }  return this.defRetValue;
/*      */     } 
/*  520 */     this.value[pos] = newValue.charValue(); return newValue.charValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char compute(double k, BiFunction<? super Double, ? super Character, ? extends Character> remappingFunction) {
/*  526 */     Objects.requireNonNull(remappingFunction);
/*  527 */     int pos = find(k);
/*  528 */     Character newValue = remappingFunction.apply(Double.valueOf(k), 
/*  529 */         (pos >= 0) ? Character.valueOf(this.value[pos]) : null);
/*  530 */     if (newValue == null) {
/*  531 */       if (pos >= 0)
/*  532 */         if (Double.doubleToLongBits(k) == 0L) {
/*  533 */           removeNullEntry();
/*      */         } else {
/*  535 */           removeEntry(pos);
/*      */         }  
/*  537 */       return this.defRetValue;
/*      */     } 
/*  539 */     char newVal = newValue.charValue();
/*  540 */     if (pos < 0) {
/*  541 */       insert(-pos - 1, k, newVal);
/*  542 */       return newVal;
/*      */     } 
/*  544 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char merge(double k, char v, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
/*  550 */     Objects.requireNonNull(remappingFunction);
/*  551 */     int pos = find(k);
/*  552 */     if (pos < 0) {
/*  553 */       insert(-pos - 1, k, v);
/*  554 */       return v;
/*      */     } 
/*  556 */     Character newValue = remappingFunction.apply(Character.valueOf(this.value[pos]), Character.valueOf(v));
/*  557 */     if (newValue == null) {
/*  558 */       if (Double.doubleToLongBits(k) == 0L) {
/*  559 */         removeNullEntry();
/*      */       } else {
/*  561 */         removeEntry(pos);
/*  562 */       }  return this.defRetValue;
/*      */     } 
/*  564 */     this.value[pos] = newValue.charValue(); return newValue.charValue();
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
/*  575 */     if (this.size == 0)
/*      */       return; 
/*  577 */     this.size = 0;
/*  578 */     this.containsNullKey = false;
/*  579 */     Arrays.fill(this.key, 0.0D);
/*      */   }
/*      */   
/*      */   public int size() {
/*  583 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  587 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Double2CharMap.Entry, Map.Entry<Double, Character>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  599 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public double getDoubleKey() {
/*  605 */       return Double2CharOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public char getCharValue() {
/*  609 */       return Double2CharOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public char setValue(char v) {
/*  613 */       char oldValue = Double2CharOpenHashMap.this.value[this.index];
/*  614 */       Double2CharOpenHashMap.this.value[this.index] = v;
/*  615 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double getKey() {
/*  625 */       return Double.valueOf(Double2CharOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character getValue() {
/*  635 */       return Character.valueOf(Double2CharOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character setValue(Character v) {
/*  645 */       return Character.valueOf(setValue(v.charValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  650 */       if (!(o instanceof Map.Entry))
/*  651 */         return false; 
/*  652 */       Map.Entry<Double, Character> e = (Map.Entry<Double, Character>)o;
/*  653 */       return (Double.doubleToLongBits(Double2CharOpenHashMap.this.key[this.index]) == Double.doubleToLongBits(((Double)e.getKey()).doubleValue()) && Double2CharOpenHashMap.this.value[this.index] == ((Character)e
/*  654 */         .getValue()).charValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  658 */       return HashCommon.double2int(Double2CharOpenHashMap.this.key[this.index]) ^ Double2CharOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/*  662 */       return Double2CharOpenHashMap.this.key[this.index] + "=>" + Double2CharOpenHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  672 */     int pos = Double2CharOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  679 */     int last = -1;
/*      */     
/*  681 */     int c = Double2CharOpenHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  685 */     boolean mustReturnNullKey = Double2CharOpenHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     DoubleArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  692 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  695 */       if (!hasNext())
/*  696 */         throw new NoSuchElementException(); 
/*  697 */       this.c--;
/*  698 */       if (this.mustReturnNullKey) {
/*  699 */         this.mustReturnNullKey = false;
/*  700 */         return this.last = Double2CharOpenHashMap.this.n;
/*      */       } 
/*  702 */       double[] key = Double2CharOpenHashMap.this.key;
/*      */       while (true) {
/*  704 */         if (--this.pos < 0) {
/*      */           
/*  706 */           this.last = Integer.MIN_VALUE;
/*  707 */           double k = this.wrapped.getDouble(-this.pos - 1);
/*  708 */           int p = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2CharOpenHashMap.this.mask;
/*  709 */           while (Double.doubleToLongBits(k) != Double.doubleToLongBits(key[p]))
/*  710 */             p = p + 1 & Double2CharOpenHashMap.this.mask; 
/*  711 */           return p;
/*      */         } 
/*  713 */         if (Double.doubleToLongBits(key[this.pos]) != 0L) {
/*  714 */           return this.last = this.pos;
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
/*  728 */       double[] key = Double2CharOpenHashMap.this.key; while (true) {
/*      */         double curr; int last;
/*  730 */         pos = (last = pos) + 1 & Double2CharOpenHashMap.this.mask;
/*      */         while (true) {
/*  732 */           if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/*  733 */             key[last] = 0.0D;
/*      */             return;
/*      */           } 
/*  736 */           int slot = (int)HashCommon.mix(Double.doubleToRawLongBits(curr)) & Double2CharOpenHashMap.this.mask;
/*  737 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  739 */           pos = pos + 1 & Double2CharOpenHashMap.this.mask;
/*      */         } 
/*  741 */         if (pos < last) {
/*  742 */           if (this.wrapped == null)
/*  743 */             this.wrapped = new DoubleArrayList(2); 
/*  744 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  746 */         key[last] = curr;
/*  747 */         Double2CharOpenHashMap.this.value[last] = Double2CharOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  751 */       if (this.last == -1)
/*  752 */         throw new IllegalStateException(); 
/*  753 */       if (this.last == Double2CharOpenHashMap.this.n) {
/*  754 */         Double2CharOpenHashMap.this.containsNullKey = false;
/*  755 */       } else if (this.pos >= 0) {
/*  756 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  759 */         Double2CharOpenHashMap.this.remove(this.wrapped.getDouble(-this.pos - 1));
/*  760 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  763 */       Double2CharOpenHashMap.this.size--;
/*  764 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  769 */       int i = n;
/*  770 */       while (i-- != 0 && hasNext())
/*  771 */         nextEntry(); 
/*  772 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Double2CharMap.Entry> { private Double2CharOpenHashMap.MapEntry entry;
/*      */     
/*      */     public Double2CharOpenHashMap.MapEntry next() {
/*  779 */       return this.entry = new Double2CharOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  783 */       super.remove();
/*  784 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Double2CharMap.Entry> { private FastEntryIterator() {
/*  788 */       this.entry = new Double2CharOpenHashMap.MapEntry();
/*      */     } private final Double2CharOpenHashMap.MapEntry entry;
/*      */     public Double2CharOpenHashMap.MapEntry next() {
/*  791 */       this.entry.index = nextEntry();
/*  792 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Double2CharMap.Entry> implements Double2CharMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Double2CharMap.Entry> iterator() {
/*  798 */       return new Double2CharOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Double2CharMap.Entry> fastIterator() {
/*  802 */       return new Double2CharOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  807 */       if (!(o instanceof Map.Entry))
/*  808 */         return false; 
/*  809 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  810 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/*  811 */         return false; 
/*  812 */       if (e.getValue() == null || !(e.getValue() instanceof Character))
/*  813 */         return false; 
/*  814 */       double k = ((Double)e.getKey()).doubleValue();
/*  815 */       char v = ((Character)e.getValue()).charValue();
/*  816 */       if (Double.doubleToLongBits(k) == 0L) {
/*  817 */         return (Double2CharOpenHashMap.this.containsNullKey && Double2CharOpenHashMap.this.value[Double2CharOpenHashMap.this.n] == v);
/*      */       }
/*  819 */       double[] key = Double2CharOpenHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/*  822 */       if (Double.doubleToLongBits(
/*  823 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2CharOpenHashMap.this.mask]) == 0L)
/*      */       {
/*  825 */         return false; } 
/*  826 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  827 */         return (Double2CharOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  830 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2CharOpenHashMap.this.mask]) == 0L)
/*  831 */           return false; 
/*  832 */         if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  833 */           return (Double2CharOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  839 */       if (!(o instanceof Map.Entry))
/*  840 */         return false; 
/*  841 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  842 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/*  843 */         return false; 
/*  844 */       if (e.getValue() == null || !(e.getValue() instanceof Character))
/*  845 */         return false; 
/*  846 */       double k = ((Double)e.getKey()).doubleValue();
/*  847 */       char v = ((Character)e.getValue()).charValue();
/*  848 */       if (Double.doubleToLongBits(k) == 0L) {
/*  849 */         if (Double2CharOpenHashMap.this.containsNullKey && Double2CharOpenHashMap.this.value[Double2CharOpenHashMap.this.n] == v) {
/*  850 */           Double2CharOpenHashMap.this.removeNullEntry();
/*  851 */           return true;
/*      */         } 
/*  853 */         return false;
/*      */       } 
/*      */       
/*  856 */       double[] key = Double2CharOpenHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/*  859 */       if (Double.doubleToLongBits(
/*  860 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2CharOpenHashMap.this.mask]) == 0L)
/*      */       {
/*  862 */         return false; } 
/*  863 */       if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
/*  864 */         if (Double2CharOpenHashMap.this.value[pos] == v) {
/*  865 */           Double2CharOpenHashMap.this.removeEntry(pos);
/*  866 */           return true;
/*      */         } 
/*  868 */         return false;
/*      */       } 
/*      */       while (true) {
/*  871 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2CharOpenHashMap.this.mask]) == 0L)
/*  872 */           return false; 
/*  873 */         if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k) && 
/*  874 */           Double2CharOpenHashMap.this.value[pos] == v) {
/*  875 */           Double2CharOpenHashMap.this.removeEntry(pos);
/*  876 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  883 */       return Double2CharOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  887 */       Double2CharOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Double2CharMap.Entry> consumer) {
/*  892 */       if (Double2CharOpenHashMap.this.containsNullKey)
/*  893 */         consumer.accept(new AbstractDouble2CharMap.BasicEntry(Double2CharOpenHashMap.this.key[Double2CharOpenHashMap.this.n], Double2CharOpenHashMap.this.value[Double2CharOpenHashMap.this.n])); 
/*  894 */       for (int pos = Double2CharOpenHashMap.this.n; pos-- != 0;) {
/*  895 */         if (Double.doubleToLongBits(Double2CharOpenHashMap.this.key[pos]) != 0L)
/*  896 */           consumer.accept(new AbstractDouble2CharMap.BasicEntry(Double2CharOpenHashMap.this.key[pos], Double2CharOpenHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Double2CharMap.Entry> consumer) {
/*  901 */       AbstractDouble2CharMap.BasicEntry entry = new AbstractDouble2CharMap.BasicEntry();
/*  902 */       if (Double2CharOpenHashMap.this.containsNullKey) {
/*  903 */         entry.key = Double2CharOpenHashMap.this.key[Double2CharOpenHashMap.this.n];
/*  904 */         entry.value = Double2CharOpenHashMap.this.value[Double2CharOpenHashMap.this.n];
/*  905 */         consumer.accept(entry);
/*      */       } 
/*  907 */       for (int pos = Double2CharOpenHashMap.this.n; pos-- != 0;) {
/*  908 */         if (Double.doubleToLongBits(Double2CharOpenHashMap.this.key[pos]) != 0L) {
/*  909 */           entry.key = Double2CharOpenHashMap.this.key[pos];
/*  910 */           entry.value = Double2CharOpenHashMap.this.value[pos];
/*  911 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Double2CharMap.FastEntrySet double2CharEntrySet() {
/*  917 */     if (this.entries == null)
/*  918 */       this.entries = new MapEntrySet(); 
/*  919 */     return this.entries;
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
/*  936 */       return Double2CharOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractDoubleSet { private KeySet() {}
/*      */     
/*      */     public DoubleIterator iterator() {
/*  942 */       return new Double2CharOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/*  947 */       if (Double2CharOpenHashMap.this.containsNullKey)
/*  948 */         consumer.accept(Double2CharOpenHashMap.this.key[Double2CharOpenHashMap.this.n]); 
/*  949 */       for (int pos = Double2CharOpenHashMap.this.n; pos-- != 0; ) {
/*  950 */         double k = Double2CharOpenHashMap.this.key[pos];
/*  951 */         if (Double.doubleToLongBits(k) != 0L)
/*  952 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  957 */       return Double2CharOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(double k) {
/*  961 */       return Double2CharOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(double k) {
/*  965 */       int oldSize = Double2CharOpenHashMap.this.size;
/*  966 */       Double2CharOpenHashMap.this.remove(k);
/*  967 */       return (Double2CharOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  971 */       Double2CharOpenHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public DoubleSet keySet() {
/*  976 */     if (this.keys == null)
/*  977 */       this.keys = new KeySet(); 
/*  978 */     return this.keys;
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
/*      */     implements CharIterator
/*      */   {
/*      */     public char nextChar() {
/*  995 */       return Double2CharOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public CharCollection values() {
/* 1000 */     if (this.values == null)
/* 1001 */       this.values = (CharCollection)new AbstractCharCollection()
/*      */         {
/*      */           public CharIterator iterator() {
/* 1004 */             return new Double2CharOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1008 */             return Double2CharOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(char v) {
/* 1012 */             return Double2CharOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1016 */             Double2CharOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(IntConsumer consumer)
/*      */           {
/* 1021 */             if (Double2CharOpenHashMap.this.containsNullKey)
/* 1022 */               consumer.accept(Double2CharOpenHashMap.this.value[Double2CharOpenHashMap.this.n]); 
/* 1023 */             for (int pos = Double2CharOpenHashMap.this.n; pos-- != 0;) {
/* 1024 */               if (Double.doubleToLongBits(Double2CharOpenHashMap.this.key[pos]) != 0L)
/* 1025 */                 consumer.accept(Double2CharOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1028 */     return this.values;
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
/* 1045 */     return trim(this.size);
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
/* 1069 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1070 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1071 */       return true; 
/*      */     try {
/* 1073 */       rehash(l);
/* 1074 */     } catch (OutOfMemoryError cantDoIt) {
/* 1075 */       return false;
/*      */     } 
/* 1077 */     return true;
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
/* 1093 */     double[] key = this.key;
/* 1094 */     char[] value = this.value;
/* 1095 */     int mask = newN - 1;
/* 1096 */     double[] newKey = new double[newN + 1];
/* 1097 */     char[] newValue = new char[newN + 1];
/* 1098 */     int i = this.n;
/* 1099 */     for (int j = realSize(); j-- != 0; ) {
/* 1100 */       while (Double.doubleToLongBits(key[--i]) == 0L); int pos;
/* 1101 */       if (Double.doubleToLongBits(newKey[
/* 1102 */             pos = (int)HashCommon.mix(Double.doubleToRawLongBits(key[i])) & mask]) != 0L)
/*      */       {
/* 1104 */         while (Double.doubleToLongBits(newKey[pos = pos + 1 & mask]) != 0L); } 
/* 1105 */       newKey[pos] = key[i];
/* 1106 */       newValue[pos] = value[i];
/*      */     } 
/* 1108 */     newValue[newN] = value[this.n];
/* 1109 */     this.n = newN;
/* 1110 */     this.mask = mask;
/* 1111 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1112 */     this.key = newKey;
/* 1113 */     this.value = newValue;
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
/*      */   public Double2CharOpenHashMap clone() {
/*      */     Double2CharOpenHashMap c;
/*      */     try {
/* 1130 */       c = (Double2CharOpenHashMap)super.clone();
/* 1131 */     } catch (CloneNotSupportedException cantHappen) {
/* 1132 */       throw new InternalError();
/*      */     } 
/* 1134 */     c.keys = null;
/* 1135 */     c.values = null;
/* 1136 */     c.entries = null;
/* 1137 */     c.containsNullKey = this.containsNullKey;
/* 1138 */     c.key = (double[])this.key.clone();
/* 1139 */     c.value = (char[])this.value.clone();
/* 1140 */     return c;
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
/* 1153 */     int h = 0;
/* 1154 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1155 */       while (Double.doubleToLongBits(this.key[i]) == 0L)
/* 1156 */         i++; 
/* 1157 */       t = HashCommon.double2int(this.key[i]);
/* 1158 */       t ^= this.value[i];
/* 1159 */       h += t;
/* 1160 */       i++;
/*      */     } 
/*      */     
/* 1163 */     if (this.containsNullKey)
/* 1164 */       h += this.value[this.n]; 
/* 1165 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1168 */     double[] key = this.key;
/* 1169 */     char[] value = this.value;
/* 1170 */     MapIterator i = new MapIterator();
/* 1171 */     s.defaultWriteObject();
/* 1172 */     for (int j = this.size; j-- != 0; ) {
/* 1173 */       int e = i.nextEntry();
/* 1174 */       s.writeDouble(key[e]);
/* 1175 */       s.writeChar(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1180 */     s.defaultReadObject();
/* 1181 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1182 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1183 */     this.mask = this.n - 1;
/* 1184 */     double[] key = this.key = new double[this.n + 1];
/* 1185 */     char[] value = this.value = new char[this.n + 1];
/*      */ 
/*      */     
/* 1188 */     for (int i = this.size; i-- != 0; ) {
/* 1189 */       int pos; double k = s.readDouble();
/* 1190 */       char v = s.readChar();
/* 1191 */       if (Double.doubleToLongBits(k) == 0L) {
/* 1192 */         pos = this.n;
/* 1193 */         this.containsNullKey = true;
/*      */       } else {
/* 1195 */         pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask;
/* 1196 */         while (Double.doubleToLongBits(key[pos]) != 0L)
/* 1197 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1199 */       key[pos] = k;
/* 1200 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2CharOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */