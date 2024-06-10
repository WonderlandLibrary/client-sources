/*      */ package it.unimi.dsi.fastutil.shorts;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanIterator;
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
/*      */ import java.util.function.IntPredicate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Short2BooleanOpenHashMap
/*      */   extends AbstractShort2BooleanMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient short[] key;
/*      */   protected transient boolean[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Short2BooleanMap.FastEntrySet entries;
/*      */   protected transient ShortSet keys;
/*      */   protected transient BooleanCollection values;
/*      */   
/*      */   public Short2BooleanOpenHashMap(int expected, float f) {
/*  101 */     if (f <= 0.0F || f > 1.0F)
/*  102 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  103 */     if (expected < 0)
/*  104 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  105 */     this.f = f;
/*  106 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  107 */     this.mask = this.n - 1;
/*  108 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  109 */     this.key = new short[this.n + 1];
/*  110 */     this.value = new boolean[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2BooleanOpenHashMap(int expected) {
/*  119 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2BooleanOpenHashMap() {
/*  127 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2BooleanOpenHashMap(Map<? extends Short, ? extends Boolean> m, float f) {
/*  138 */     this(m.size(), f);
/*  139 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2BooleanOpenHashMap(Map<? extends Short, ? extends Boolean> m) {
/*  149 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2BooleanOpenHashMap(Short2BooleanMap m, float f) {
/*  160 */     this(m.size(), f);
/*  161 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2BooleanOpenHashMap(Short2BooleanMap m) {
/*  171 */     this(m, 0.75F);
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
/*      */   public Short2BooleanOpenHashMap(short[] k, boolean[] v, float f) {
/*  186 */     this(k.length, f);
/*  187 */     if (k.length != v.length) {
/*  188 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  190 */     for (int i = 0; i < k.length; i++) {
/*  191 */       put(k[i], v[i]);
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
/*      */   public Short2BooleanOpenHashMap(short[] k, boolean[] v) {
/*  205 */     this(k, v, 0.75F);
/*      */   }
/*      */   private int realSize() {
/*  208 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  211 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  212 */     if (needed > this.n)
/*  213 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  216 */     int needed = (int)Math.min(1073741824L, 
/*  217 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  218 */     if (needed > this.n)
/*  219 */       rehash(needed); 
/*      */   }
/*      */   private boolean removeEntry(int pos) {
/*  222 */     boolean oldValue = this.value[pos];
/*  223 */     this.size--;
/*  224 */     shiftKeys(pos);
/*  225 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  226 */       rehash(this.n / 2); 
/*  227 */     return oldValue;
/*      */   }
/*      */   private boolean removeNullEntry() {
/*  230 */     this.containsNullKey = false;
/*  231 */     boolean oldValue = this.value[this.n];
/*  232 */     this.size--;
/*  233 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  234 */       rehash(this.n / 2); 
/*  235 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Short, ? extends Boolean> m) {
/*  239 */     if (this.f <= 0.5D) {
/*  240 */       ensureCapacity(m.size());
/*      */     } else {
/*  242 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  244 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(short k) {
/*  248 */     if (k == 0) {
/*  249 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  251 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  254 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  255 */       return -(pos + 1); 
/*  256 */     if (k == curr) {
/*  257 */       return pos;
/*      */     }
/*      */     while (true) {
/*  260 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  261 */         return -(pos + 1); 
/*  262 */       if (k == curr)
/*  263 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, short k, boolean v) {
/*  267 */     if (pos == this.n)
/*  268 */       this.containsNullKey = true; 
/*  269 */     this.key[pos] = k;
/*  270 */     this.value[pos] = v;
/*  271 */     if (this.size++ >= this.maxFill) {
/*  272 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean put(short k, boolean v) {
/*  278 */     int pos = find(k);
/*  279 */     if (pos < 0) {
/*  280 */       insert(-pos - 1, k, v);
/*  281 */       return this.defRetValue;
/*      */     } 
/*  283 */     boolean oldValue = this.value[pos];
/*  284 */     this.value[pos] = v;
/*  285 */     return oldValue;
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
/*  298 */     short[] key = this.key; while (true) {
/*      */       short curr; int last;
/*  300 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  302 */         if ((curr = key[pos]) == 0) {
/*  303 */           key[last] = 0;
/*      */           return;
/*      */         } 
/*  306 */         int slot = HashCommon.mix(curr) & this.mask;
/*  307 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  309 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  311 */       key[last] = curr;
/*  312 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean remove(short k) {
/*  318 */     if (k == 0) {
/*  319 */       if (this.containsNullKey)
/*  320 */         return removeNullEntry(); 
/*  321 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  324 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  327 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  328 */       return this.defRetValue; 
/*  329 */     if (k == curr)
/*  330 */       return removeEntry(pos); 
/*      */     while (true) {
/*  332 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  333 */         return this.defRetValue; 
/*  334 */       if (k == curr) {
/*  335 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean get(short k) {
/*  341 */     if (k == 0) {
/*  342 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  344 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  347 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  348 */       return this.defRetValue; 
/*  349 */     if (k == curr) {
/*  350 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  353 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  354 */         return this.defRetValue; 
/*  355 */       if (k == curr) {
/*  356 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(short k) {
/*  362 */     if (k == 0) {
/*  363 */       return this.containsNullKey;
/*      */     }
/*  365 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  368 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  369 */       return false; 
/*  370 */     if (k == curr) {
/*  371 */       return true;
/*      */     }
/*      */     while (true) {
/*  374 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  375 */         return false; 
/*  376 */       if (k == curr)
/*  377 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(boolean v) {
/*  382 */     boolean[] value = this.value;
/*  383 */     short[] key = this.key;
/*  384 */     if (this.containsNullKey && value[this.n] == v)
/*  385 */       return true; 
/*  386 */     for (int i = this.n; i-- != 0;) {
/*  387 */       if (key[i] != 0 && value[i] == v)
/*  388 */         return true; 
/*  389 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getOrDefault(short k, boolean defaultValue) {
/*  395 */     if (k == 0) {
/*  396 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  398 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  401 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  402 */       return defaultValue; 
/*  403 */     if (k == curr) {
/*  404 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  407 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  408 */         return defaultValue; 
/*  409 */       if (k == curr) {
/*  410 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean putIfAbsent(short k, boolean v) {
/*  416 */     int pos = find(k);
/*  417 */     if (pos >= 0)
/*  418 */       return this.value[pos]; 
/*  419 */     insert(-pos - 1, k, v);
/*  420 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(short k, boolean v) {
/*  426 */     if (k == 0) {
/*  427 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  428 */         removeNullEntry();
/*  429 */         return true;
/*      */       } 
/*  431 */       return false;
/*      */     } 
/*      */     
/*  434 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  437 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  438 */       return false; 
/*  439 */     if (k == curr && v == this.value[pos]) {
/*  440 */       removeEntry(pos);
/*  441 */       return true;
/*      */     } 
/*      */     while (true) {
/*  444 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  445 */         return false; 
/*  446 */       if (k == curr && v == this.value[pos]) {
/*  447 */         removeEntry(pos);
/*  448 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(short k, boolean oldValue, boolean v) {
/*  455 */     int pos = find(k);
/*  456 */     if (pos < 0 || oldValue != this.value[pos])
/*  457 */       return false; 
/*  458 */     this.value[pos] = v;
/*  459 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(short k, boolean v) {
/*  464 */     int pos = find(k);
/*  465 */     if (pos < 0)
/*  466 */       return this.defRetValue; 
/*  467 */     boolean oldValue = this.value[pos];
/*  468 */     this.value[pos] = v;
/*  469 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean computeIfAbsent(short k, IntPredicate mappingFunction) {
/*  474 */     Objects.requireNonNull(mappingFunction);
/*  475 */     int pos = find(k);
/*  476 */     if (pos >= 0)
/*  477 */       return this.value[pos]; 
/*  478 */     boolean newValue = mappingFunction.test(k);
/*  479 */     insert(-pos - 1, k, newValue);
/*  480 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeIfAbsentNullable(short k, IntFunction<? extends Boolean> mappingFunction) {
/*  486 */     Objects.requireNonNull(mappingFunction);
/*  487 */     int pos = find(k);
/*  488 */     if (pos >= 0)
/*  489 */       return this.value[pos]; 
/*  490 */     Boolean newValue = mappingFunction.apply(k);
/*  491 */     if (newValue == null)
/*  492 */       return this.defRetValue; 
/*  493 */     boolean v = newValue.booleanValue();
/*  494 */     insert(-pos - 1, k, v);
/*  495 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeIfPresent(short k, BiFunction<? super Short, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  501 */     Objects.requireNonNull(remappingFunction);
/*  502 */     int pos = find(k);
/*  503 */     if (pos < 0)
/*  504 */       return this.defRetValue; 
/*  505 */     Boolean newValue = remappingFunction.apply(Short.valueOf(k), Boolean.valueOf(this.value[pos]));
/*  506 */     if (newValue == null) {
/*  507 */       if (k == 0) {
/*  508 */         removeNullEntry();
/*      */       } else {
/*  510 */         removeEntry(pos);
/*  511 */       }  return this.defRetValue;
/*      */     } 
/*  513 */     this.value[pos] = newValue.booleanValue(); return newValue.booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean compute(short k, BiFunction<? super Short, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  519 */     Objects.requireNonNull(remappingFunction);
/*  520 */     int pos = find(k);
/*  521 */     Boolean newValue = remappingFunction.apply(Short.valueOf(k), 
/*  522 */         (pos >= 0) ? Boolean.valueOf(this.value[pos]) : null);
/*  523 */     if (newValue == null) {
/*  524 */       if (pos >= 0)
/*  525 */         if (k == 0) {
/*  526 */           removeNullEntry();
/*      */         } else {
/*  528 */           removeEntry(pos);
/*      */         }  
/*  530 */       return this.defRetValue;
/*      */     } 
/*  532 */     boolean newVal = newValue.booleanValue();
/*  533 */     if (pos < 0) {
/*  534 */       insert(-pos - 1, k, newVal);
/*  535 */       return newVal;
/*      */     } 
/*  537 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean merge(short k, boolean v, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  543 */     Objects.requireNonNull(remappingFunction);
/*  544 */     int pos = find(k);
/*  545 */     if (pos < 0) {
/*  546 */       insert(-pos - 1, k, v);
/*  547 */       return v;
/*      */     } 
/*  549 */     Boolean newValue = remappingFunction.apply(Boolean.valueOf(this.value[pos]), Boolean.valueOf(v));
/*  550 */     if (newValue == null) {
/*  551 */       if (k == 0) {
/*  552 */         removeNullEntry();
/*      */       } else {
/*  554 */         removeEntry(pos);
/*  555 */       }  return this.defRetValue;
/*      */     } 
/*  557 */     this.value[pos] = newValue.booleanValue(); return newValue.booleanValue();
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
/*  568 */     if (this.size == 0)
/*      */       return; 
/*  570 */     this.size = 0;
/*  571 */     this.containsNullKey = false;
/*  572 */     Arrays.fill(this.key, (short)0);
/*      */   }
/*      */   
/*      */   public int size() {
/*  576 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  580 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Short2BooleanMap.Entry, Map.Entry<Short, Boolean>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  592 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public short getShortKey() {
/*  598 */       return Short2BooleanOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public boolean getBooleanValue() {
/*  602 */       return Short2BooleanOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public boolean setValue(boolean v) {
/*  606 */       boolean oldValue = Short2BooleanOpenHashMap.this.value[this.index];
/*  607 */       Short2BooleanOpenHashMap.this.value[this.index] = v;
/*  608 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Short getKey() {
/*  618 */       return Short.valueOf(Short2BooleanOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean getValue() {
/*  628 */       return Boolean.valueOf(Short2BooleanOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean setValue(Boolean v) {
/*  638 */       return Boolean.valueOf(setValue(v.booleanValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  643 */       if (!(o instanceof Map.Entry))
/*  644 */         return false; 
/*  645 */       Map.Entry<Short, Boolean> e = (Map.Entry<Short, Boolean>)o;
/*  646 */       return (Short2BooleanOpenHashMap.this.key[this.index] == ((Short)e.getKey()).shortValue() && Short2BooleanOpenHashMap.this.value[this.index] == ((Boolean)e.getValue()).booleanValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  650 */       return Short2BooleanOpenHashMap.this.key[this.index] ^ (Short2BooleanOpenHashMap.this.value[this.index] ? 1231 : 1237);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  654 */       return Short2BooleanOpenHashMap.this.key[this.index] + "=>" + Short2BooleanOpenHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  664 */     int pos = Short2BooleanOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  671 */     int last = -1;
/*      */     
/*  673 */     int c = Short2BooleanOpenHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  677 */     boolean mustReturnNullKey = Short2BooleanOpenHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     ShortArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  684 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  687 */       if (!hasNext())
/*  688 */         throw new NoSuchElementException(); 
/*  689 */       this.c--;
/*  690 */       if (this.mustReturnNullKey) {
/*  691 */         this.mustReturnNullKey = false;
/*  692 */         return this.last = Short2BooleanOpenHashMap.this.n;
/*      */       } 
/*  694 */       short[] key = Short2BooleanOpenHashMap.this.key;
/*      */       while (true) {
/*  696 */         if (--this.pos < 0) {
/*      */           
/*  698 */           this.last = Integer.MIN_VALUE;
/*  699 */           short k = this.wrapped.getShort(-this.pos - 1);
/*  700 */           int p = HashCommon.mix(k) & Short2BooleanOpenHashMap.this.mask;
/*  701 */           while (k != key[p])
/*  702 */             p = p + 1 & Short2BooleanOpenHashMap.this.mask; 
/*  703 */           return p;
/*      */         } 
/*  705 */         if (key[this.pos] != 0) {
/*  706 */           return this.last = this.pos;
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
/*  720 */       short[] key = Short2BooleanOpenHashMap.this.key; while (true) {
/*      */         short curr; int last;
/*  722 */         pos = (last = pos) + 1 & Short2BooleanOpenHashMap.this.mask;
/*      */         while (true) {
/*  724 */           if ((curr = key[pos]) == 0) {
/*  725 */             key[last] = 0;
/*      */             return;
/*      */           } 
/*  728 */           int slot = HashCommon.mix(curr) & Short2BooleanOpenHashMap.this.mask;
/*  729 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  731 */           pos = pos + 1 & Short2BooleanOpenHashMap.this.mask;
/*      */         } 
/*  733 */         if (pos < last) {
/*  734 */           if (this.wrapped == null)
/*  735 */             this.wrapped = new ShortArrayList(2); 
/*  736 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  738 */         key[last] = curr;
/*  739 */         Short2BooleanOpenHashMap.this.value[last] = Short2BooleanOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  743 */       if (this.last == -1)
/*  744 */         throw new IllegalStateException(); 
/*  745 */       if (this.last == Short2BooleanOpenHashMap.this.n) {
/*  746 */         Short2BooleanOpenHashMap.this.containsNullKey = false;
/*  747 */       } else if (this.pos >= 0) {
/*  748 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  751 */         Short2BooleanOpenHashMap.this.remove(this.wrapped.getShort(-this.pos - 1));
/*  752 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  755 */       Short2BooleanOpenHashMap.this.size--;
/*  756 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  761 */       int i = n;
/*  762 */       while (i-- != 0 && hasNext())
/*  763 */         nextEntry(); 
/*  764 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Short2BooleanMap.Entry> { private Short2BooleanOpenHashMap.MapEntry entry;
/*      */     
/*      */     public Short2BooleanOpenHashMap.MapEntry next() {
/*  771 */       return this.entry = new Short2BooleanOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  775 */       super.remove();
/*  776 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Short2BooleanMap.Entry> { private FastEntryIterator() {
/*  780 */       this.entry = new Short2BooleanOpenHashMap.MapEntry();
/*      */     } private final Short2BooleanOpenHashMap.MapEntry entry;
/*      */     public Short2BooleanOpenHashMap.MapEntry next() {
/*  783 */       this.entry.index = nextEntry();
/*  784 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Short2BooleanMap.Entry> implements Short2BooleanMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Short2BooleanMap.Entry> iterator() {
/*  790 */       return new Short2BooleanOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Short2BooleanMap.Entry> fastIterator() {
/*  794 */       return new Short2BooleanOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  799 */       if (!(o instanceof Map.Entry))
/*  800 */         return false; 
/*  801 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  802 */       if (e.getKey() == null || !(e.getKey() instanceof Short))
/*  803 */         return false; 
/*  804 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/*  805 */         return false; 
/*  806 */       short k = ((Short)e.getKey()).shortValue();
/*  807 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/*  808 */       if (k == 0) {
/*  809 */         return (Short2BooleanOpenHashMap.this.containsNullKey && Short2BooleanOpenHashMap.this.value[Short2BooleanOpenHashMap.this.n] == v);
/*      */       }
/*  811 */       short[] key = Short2BooleanOpenHashMap.this.key;
/*      */       short curr;
/*      */       int pos;
/*  814 */       if ((curr = key[pos = HashCommon.mix(k) & Short2BooleanOpenHashMap.this.mask]) == 0)
/*  815 */         return false; 
/*  816 */       if (k == curr) {
/*  817 */         return (Short2BooleanOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  820 */         if ((curr = key[pos = pos + 1 & Short2BooleanOpenHashMap.this.mask]) == 0)
/*  821 */           return false; 
/*  822 */         if (k == curr) {
/*  823 */           return (Short2BooleanOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  829 */       if (!(o instanceof Map.Entry))
/*  830 */         return false; 
/*  831 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  832 */       if (e.getKey() == null || !(e.getKey() instanceof Short))
/*  833 */         return false; 
/*  834 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/*  835 */         return false; 
/*  836 */       short k = ((Short)e.getKey()).shortValue();
/*  837 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/*  838 */       if (k == 0) {
/*  839 */         if (Short2BooleanOpenHashMap.this.containsNullKey && Short2BooleanOpenHashMap.this.value[Short2BooleanOpenHashMap.this.n] == v) {
/*  840 */           Short2BooleanOpenHashMap.this.removeNullEntry();
/*  841 */           return true;
/*      */         } 
/*  843 */         return false;
/*      */       } 
/*      */       
/*  846 */       short[] key = Short2BooleanOpenHashMap.this.key;
/*      */       short curr;
/*      */       int pos;
/*  849 */       if ((curr = key[pos = HashCommon.mix(k) & Short2BooleanOpenHashMap.this.mask]) == 0)
/*  850 */         return false; 
/*  851 */       if (curr == k) {
/*  852 */         if (Short2BooleanOpenHashMap.this.value[pos] == v) {
/*  853 */           Short2BooleanOpenHashMap.this.removeEntry(pos);
/*  854 */           return true;
/*      */         } 
/*  856 */         return false;
/*      */       } 
/*      */       while (true) {
/*  859 */         if ((curr = key[pos = pos + 1 & Short2BooleanOpenHashMap.this.mask]) == 0)
/*  860 */           return false; 
/*  861 */         if (curr == k && 
/*  862 */           Short2BooleanOpenHashMap.this.value[pos] == v) {
/*  863 */           Short2BooleanOpenHashMap.this.removeEntry(pos);
/*  864 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  871 */       return Short2BooleanOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  875 */       Short2BooleanOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Short2BooleanMap.Entry> consumer) {
/*  880 */       if (Short2BooleanOpenHashMap.this.containsNullKey)
/*  881 */         consumer.accept(new AbstractShort2BooleanMap.BasicEntry(Short2BooleanOpenHashMap.this.key[Short2BooleanOpenHashMap.this.n], Short2BooleanOpenHashMap.this.value[Short2BooleanOpenHashMap.this.n])); 
/*  882 */       for (int pos = Short2BooleanOpenHashMap.this.n; pos-- != 0;) {
/*  883 */         if (Short2BooleanOpenHashMap.this.key[pos] != 0)
/*  884 */           consumer.accept(new AbstractShort2BooleanMap.BasicEntry(Short2BooleanOpenHashMap.this.key[pos], Short2BooleanOpenHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Short2BooleanMap.Entry> consumer) {
/*  889 */       AbstractShort2BooleanMap.BasicEntry entry = new AbstractShort2BooleanMap.BasicEntry();
/*  890 */       if (Short2BooleanOpenHashMap.this.containsNullKey) {
/*  891 */         entry.key = Short2BooleanOpenHashMap.this.key[Short2BooleanOpenHashMap.this.n];
/*  892 */         entry.value = Short2BooleanOpenHashMap.this.value[Short2BooleanOpenHashMap.this.n];
/*  893 */         consumer.accept(entry);
/*      */       } 
/*  895 */       for (int pos = Short2BooleanOpenHashMap.this.n; pos-- != 0;) {
/*  896 */         if (Short2BooleanOpenHashMap.this.key[pos] != 0) {
/*  897 */           entry.key = Short2BooleanOpenHashMap.this.key[pos];
/*  898 */           entry.value = Short2BooleanOpenHashMap.this.value[pos];
/*  899 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Short2BooleanMap.FastEntrySet short2BooleanEntrySet() {
/*  905 */     if (this.entries == null)
/*  906 */       this.entries = new MapEntrySet(); 
/*  907 */     return this.entries;
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
/*  924 */       return Short2BooleanOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractShortSet { private KeySet() {}
/*      */     
/*      */     public ShortIterator iterator() {
/*  930 */       return new Short2BooleanOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer consumer) {
/*  935 */       if (Short2BooleanOpenHashMap.this.containsNullKey)
/*  936 */         consumer.accept(Short2BooleanOpenHashMap.this.key[Short2BooleanOpenHashMap.this.n]); 
/*  937 */       for (int pos = Short2BooleanOpenHashMap.this.n; pos-- != 0; ) {
/*  938 */         short k = Short2BooleanOpenHashMap.this.key[pos];
/*  939 */         if (k != 0)
/*  940 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  945 */       return Short2BooleanOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(short k) {
/*  949 */       return Short2BooleanOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(short k) {
/*  953 */       int oldSize = Short2BooleanOpenHashMap.this.size;
/*  954 */       Short2BooleanOpenHashMap.this.remove(k);
/*  955 */       return (Short2BooleanOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  959 */       Short2BooleanOpenHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ShortSet keySet() {
/*  964 */     if (this.keys == null)
/*  965 */       this.keys = new KeySet(); 
/*  966 */     return this.keys;
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
/*      */     implements BooleanIterator
/*      */   {
/*      */     public boolean nextBoolean() {
/*  983 */       return Short2BooleanOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public BooleanCollection values() {
/*  988 */     if (this.values == null)
/*  989 */       this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */         {
/*      */           public BooleanIterator iterator() {
/*  992 */             return new Short2BooleanOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/*  996 */             return Short2BooleanOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(boolean v) {
/* 1000 */             return Short2BooleanOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1004 */             Short2BooleanOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(BooleanConsumer consumer)
/*      */           {
/* 1009 */             if (Short2BooleanOpenHashMap.this.containsNullKey)
/* 1010 */               consumer.accept(Short2BooleanOpenHashMap.this.value[Short2BooleanOpenHashMap.this.n]); 
/* 1011 */             for (int pos = Short2BooleanOpenHashMap.this.n; pos-- != 0;) {
/* 1012 */               if (Short2BooleanOpenHashMap.this.key[pos] != 0)
/* 1013 */                 consumer.accept(Short2BooleanOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1016 */     return this.values;
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
/* 1033 */     return trim(this.size);
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
/* 1057 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1058 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1059 */       return true; 
/*      */     try {
/* 1061 */       rehash(l);
/* 1062 */     } catch (OutOfMemoryError cantDoIt) {
/* 1063 */       return false;
/*      */     } 
/* 1065 */     return true;
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
/* 1081 */     short[] key = this.key;
/* 1082 */     boolean[] value = this.value;
/* 1083 */     int mask = newN - 1;
/* 1084 */     short[] newKey = new short[newN + 1];
/* 1085 */     boolean[] newValue = new boolean[newN + 1];
/* 1086 */     int i = this.n;
/* 1087 */     for (int j = realSize(); j-- != 0; ) {
/* 1088 */       while (key[--i] == 0); int pos;
/* 1089 */       if (newKey[pos = HashCommon.mix(key[i]) & mask] != 0)
/* 1090 */         while (newKey[pos = pos + 1 & mask] != 0); 
/* 1091 */       newKey[pos] = key[i];
/* 1092 */       newValue[pos] = value[i];
/*      */     } 
/* 1094 */     newValue[newN] = value[this.n];
/* 1095 */     this.n = newN;
/* 1096 */     this.mask = mask;
/* 1097 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1098 */     this.key = newKey;
/* 1099 */     this.value = newValue;
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
/*      */   public Short2BooleanOpenHashMap clone() {
/*      */     Short2BooleanOpenHashMap c;
/*      */     try {
/* 1116 */       c = (Short2BooleanOpenHashMap)super.clone();
/* 1117 */     } catch (CloneNotSupportedException cantHappen) {
/* 1118 */       throw new InternalError();
/*      */     } 
/* 1120 */     c.keys = null;
/* 1121 */     c.values = null;
/* 1122 */     c.entries = null;
/* 1123 */     c.containsNullKey = this.containsNullKey;
/* 1124 */     c.key = (short[])this.key.clone();
/* 1125 */     c.value = (boolean[])this.value.clone();
/* 1126 */     return c;
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
/* 1139 */     int h = 0;
/* 1140 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1141 */       while (this.key[i] == 0)
/* 1142 */         i++; 
/* 1143 */       t = this.key[i];
/* 1144 */       t ^= this.value[i] ? 1231 : 1237;
/* 1145 */       h += t;
/* 1146 */       i++;
/*      */     } 
/*      */     
/* 1149 */     if (this.containsNullKey)
/* 1150 */       h += this.value[this.n] ? 1231 : 1237; 
/* 1151 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1154 */     short[] key = this.key;
/* 1155 */     boolean[] value = this.value;
/* 1156 */     MapIterator i = new MapIterator();
/* 1157 */     s.defaultWriteObject();
/* 1158 */     for (int j = this.size; j-- != 0; ) {
/* 1159 */       int e = i.nextEntry();
/* 1160 */       s.writeShort(key[e]);
/* 1161 */       s.writeBoolean(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1166 */     s.defaultReadObject();
/* 1167 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1168 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1169 */     this.mask = this.n - 1;
/* 1170 */     short[] key = this.key = new short[this.n + 1];
/* 1171 */     boolean[] value = this.value = new boolean[this.n + 1];
/*      */ 
/*      */     
/* 1174 */     for (int i = this.size; i-- != 0; ) {
/* 1175 */       int pos; short k = s.readShort();
/* 1176 */       boolean v = s.readBoolean();
/* 1177 */       if (k == 0) {
/* 1178 */         pos = this.n;
/* 1179 */         this.containsNullKey = true;
/*      */       } else {
/* 1181 */         pos = HashCommon.mix(k) & this.mask;
/* 1182 */         while (key[pos] != 0)
/* 1183 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1185 */       key[pos] = k;
/* 1186 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\Short2BooleanOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */