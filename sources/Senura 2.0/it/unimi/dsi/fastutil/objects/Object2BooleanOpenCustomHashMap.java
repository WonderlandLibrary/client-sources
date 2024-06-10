/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanIterator;
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
/*      */ import java.util.function.Predicate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Object2BooleanOpenCustomHashMap<K>
/*      */   extends AbstractObject2BooleanMap<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient boolean[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected Hash.Strategy<? super K> strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Object2BooleanMap.FastEntrySet<K> entries;
/*      */   protected transient ObjectSet<K> keys;
/*      */   protected transient BooleanCollection values;
/*      */   
/*      */   public Object2BooleanOpenCustomHashMap(int expected, float f, Hash.Strategy<? super K> strategy) {
/*  107 */     this.strategy = strategy;
/*  108 */     if (f <= 0.0F || f > 1.0F)
/*  109 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  110 */     if (expected < 0)
/*  111 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  112 */     this.f = f;
/*  113 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  114 */     this.mask = this.n - 1;
/*  115 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  116 */     this.key = (K[])new Object[this.n + 1];
/*  117 */     this.value = new boolean[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanOpenCustomHashMap(int expected, Hash.Strategy<? super K> strategy) {
/*  128 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanOpenCustomHashMap(Hash.Strategy<? super K> strategy) {
/*  139 */     this(16, 0.75F, strategy);
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
/*      */   public Object2BooleanOpenCustomHashMap(Map<? extends K, ? extends Boolean> m, float f, Hash.Strategy<? super K> strategy) {
/*  153 */     this(m.size(), f, strategy);
/*  154 */     putAll(m);
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
/*      */   public Object2BooleanOpenCustomHashMap(Map<? extends K, ? extends Boolean> m, Hash.Strategy<? super K> strategy) {
/*  167 */     this(m, 0.75F, strategy);
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
/*      */   public Object2BooleanOpenCustomHashMap(Object2BooleanMap<K> m, float f, Hash.Strategy<? super K> strategy) {
/*  181 */     this(m.size(), f, strategy);
/*  182 */     putAll(m);
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
/*      */   public Object2BooleanOpenCustomHashMap(Object2BooleanMap<K> m, Hash.Strategy<? super K> strategy) {
/*  194 */     this(m, 0.75F, strategy);
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
/*      */   public Object2BooleanOpenCustomHashMap(K[] k, boolean[] v, float f, Hash.Strategy<? super K> strategy) {
/*  212 */     this(k.length, f, strategy);
/*  213 */     if (k.length != v.length) {
/*  214 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  216 */     for (int i = 0; i < k.length; i++) {
/*  217 */       put(k[i], v[i]);
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
/*      */   public Object2BooleanOpenCustomHashMap(K[] k, boolean[] v, Hash.Strategy<? super K> strategy) {
/*  233 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Hash.Strategy<? super K> strategy() {
/*  241 */     return this.strategy;
/*      */   }
/*      */   private int realSize() {
/*  244 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  247 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  248 */     if (needed > this.n)
/*  249 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  252 */     int needed = (int)Math.min(1073741824L, 
/*  253 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  254 */     if (needed > this.n)
/*  255 */       rehash(needed); 
/*      */   }
/*      */   private boolean removeEntry(int pos) {
/*  258 */     boolean oldValue = this.value[pos];
/*  259 */     this.size--;
/*  260 */     shiftKeys(pos);
/*  261 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  262 */       rehash(this.n / 2); 
/*  263 */     return oldValue;
/*      */   }
/*      */   private boolean removeNullEntry() {
/*  266 */     this.containsNullKey = false;
/*  267 */     this.key[this.n] = null;
/*  268 */     boolean oldValue = this.value[this.n];
/*  269 */     this.size--;
/*  270 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  271 */       rehash(this.n / 2); 
/*  272 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends Boolean> m) {
/*  276 */     if (this.f <= 0.5D) {
/*  277 */       ensureCapacity(m.size());
/*      */     } else {
/*  279 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  281 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(K k) {
/*  285 */     if (this.strategy.equals(k, null)) {
/*  286 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  288 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  291 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  292 */       return -(pos + 1); 
/*  293 */     if (this.strategy.equals(k, curr)) {
/*  294 */       return pos;
/*      */     }
/*      */     while (true) {
/*  297 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  298 */         return -(pos + 1); 
/*  299 */       if (this.strategy.equals(k, curr))
/*  300 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, K k, boolean v) {
/*  304 */     if (pos == this.n)
/*  305 */       this.containsNullKey = true; 
/*  306 */     this.key[pos] = k;
/*  307 */     this.value[pos] = v;
/*  308 */     if (this.size++ >= this.maxFill) {
/*  309 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean put(K k, boolean v) {
/*  315 */     int pos = find(k);
/*  316 */     if (pos < 0) {
/*  317 */       insert(-pos - 1, k, v);
/*  318 */       return this.defRetValue;
/*      */     } 
/*  320 */     boolean oldValue = this.value[pos];
/*  321 */     this.value[pos] = v;
/*  322 */     return oldValue;
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
/*  335 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  337 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  339 */         if ((curr = key[pos]) == null) {
/*  340 */           key[last] = null;
/*      */           return;
/*      */         } 
/*  343 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  344 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  346 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  348 */       key[last] = curr;
/*  349 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean removeBoolean(Object k) {
/*  355 */     if (this.strategy.equals(k, null)) {
/*  356 */       if (this.containsNullKey)
/*  357 */         return removeNullEntry(); 
/*  358 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  361 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  364 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  365 */       return this.defRetValue; 
/*  366 */     if (this.strategy.equals(k, curr))
/*  367 */       return removeEntry(pos); 
/*      */     while (true) {
/*  369 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  370 */         return this.defRetValue; 
/*  371 */       if (this.strategy.equals(k, curr)) {
/*  372 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean getBoolean(Object k) {
/*  378 */     if (this.strategy.equals(k, null)) {
/*  379 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  381 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  384 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  385 */       return this.defRetValue; 
/*  386 */     if (this.strategy.equals(k, curr)) {
/*  387 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  390 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  391 */         return this.defRetValue; 
/*  392 */       if (this.strategy.equals(k, curr)) {
/*  393 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  399 */     if (this.strategy.equals(k, null)) {
/*  400 */       return this.containsNullKey;
/*      */     }
/*  402 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  405 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  406 */       return false; 
/*  407 */     if (this.strategy.equals(k, curr)) {
/*  408 */       return true;
/*      */     }
/*      */     while (true) {
/*  411 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  412 */         return false; 
/*  413 */       if (this.strategy.equals(k, curr))
/*  414 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(boolean v) {
/*  419 */     boolean[] value = this.value;
/*  420 */     K[] key = this.key;
/*  421 */     if (this.containsNullKey && value[this.n] == v)
/*  422 */       return true; 
/*  423 */     for (int i = this.n; i-- != 0;) {
/*  424 */       if (key[i] != null && value[i] == v)
/*  425 */         return true; 
/*  426 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getOrDefault(Object k, boolean defaultValue) {
/*  432 */     if (this.strategy.equals(k, null)) {
/*  433 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  435 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  438 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  439 */       return defaultValue; 
/*  440 */     if (this.strategy.equals(k, curr)) {
/*  441 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  444 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  445 */         return defaultValue; 
/*  446 */       if (this.strategy.equals(k, curr)) {
/*  447 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean putIfAbsent(K k, boolean v) {
/*  453 */     int pos = find(k);
/*  454 */     if (pos >= 0)
/*  455 */       return this.value[pos]; 
/*  456 */     insert(-pos - 1, k, v);
/*  457 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, boolean v) {
/*  463 */     if (this.strategy.equals(k, null)) {
/*  464 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  465 */         removeNullEntry();
/*  466 */         return true;
/*      */       } 
/*  468 */       return false;
/*      */     } 
/*      */     
/*  471 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  474 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  475 */       return false; 
/*  476 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  477 */       removeEntry(pos);
/*  478 */       return true;
/*      */     } 
/*      */     while (true) {
/*  481 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  482 */         return false; 
/*  483 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  484 */         removeEntry(pos);
/*  485 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(K k, boolean oldValue, boolean v) {
/*  492 */     int pos = find(k);
/*  493 */     if (pos < 0 || oldValue != this.value[pos])
/*  494 */       return false; 
/*  495 */     this.value[pos] = v;
/*  496 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(K k, boolean v) {
/*  501 */     int pos = find(k);
/*  502 */     if (pos < 0)
/*  503 */       return this.defRetValue; 
/*  504 */     boolean oldValue = this.value[pos];
/*  505 */     this.value[pos] = v;
/*  506 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean computeBooleanIfAbsent(K k, Predicate<? super K> mappingFunction) {
/*  511 */     Objects.requireNonNull(mappingFunction);
/*  512 */     int pos = find(k);
/*  513 */     if (pos >= 0)
/*  514 */       return this.value[pos]; 
/*  515 */     boolean newValue = mappingFunction.test(k);
/*  516 */     insert(-pos - 1, k, newValue);
/*  517 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeBooleanIfPresent(K k, BiFunction<? super K, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  523 */     Objects.requireNonNull(remappingFunction);
/*  524 */     int pos = find(k);
/*  525 */     if (pos < 0)
/*  526 */       return this.defRetValue; 
/*  527 */     Boolean newValue = remappingFunction.apply(k, Boolean.valueOf(this.value[pos]));
/*  528 */     if (newValue == null) {
/*  529 */       if (this.strategy.equals(k, null)) {
/*  530 */         removeNullEntry();
/*      */       } else {
/*  532 */         removeEntry(pos);
/*  533 */       }  return this.defRetValue;
/*      */     } 
/*  535 */     this.value[pos] = newValue.booleanValue(); return newValue.booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeBoolean(K k, BiFunction<? super K, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  541 */     Objects.requireNonNull(remappingFunction);
/*  542 */     int pos = find(k);
/*  543 */     Boolean newValue = remappingFunction.apply(k, (pos >= 0) ? Boolean.valueOf(this.value[pos]) : null);
/*  544 */     if (newValue == null) {
/*  545 */       if (pos >= 0)
/*  546 */         if (this.strategy.equals(k, null)) {
/*  547 */           removeNullEntry();
/*      */         } else {
/*  549 */           removeEntry(pos);
/*      */         }  
/*  551 */       return this.defRetValue;
/*      */     } 
/*  553 */     boolean newVal = newValue.booleanValue();
/*  554 */     if (pos < 0) {
/*  555 */       insert(-pos - 1, k, newVal);
/*  556 */       return newVal;
/*      */     } 
/*  558 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean mergeBoolean(K k, boolean v, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  564 */     Objects.requireNonNull(remappingFunction);
/*  565 */     int pos = find(k);
/*  566 */     if (pos < 0) {
/*  567 */       insert(-pos - 1, k, v);
/*  568 */       return v;
/*      */     } 
/*  570 */     Boolean newValue = remappingFunction.apply(Boolean.valueOf(this.value[pos]), Boolean.valueOf(v));
/*  571 */     if (newValue == null) {
/*  572 */       if (this.strategy.equals(k, null)) {
/*  573 */         removeNullEntry();
/*      */       } else {
/*  575 */         removeEntry(pos);
/*  576 */       }  return this.defRetValue;
/*      */     } 
/*  578 */     this.value[pos] = newValue.booleanValue(); return newValue.booleanValue();
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
/*  589 */     if (this.size == 0)
/*      */       return; 
/*  591 */     this.size = 0;
/*  592 */     this.containsNullKey = false;
/*  593 */     Arrays.fill((Object[])this.key, (Object)null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  597 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  601 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Object2BooleanMap.Entry<K>, Map.Entry<K, Boolean>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  613 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public K getKey() {
/*  619 */       return Object2BooleanOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public boolean getBooleanValue() {
/*  623 */       return Object2BooleanOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public boolean setValue(boolean v) {
/*  627 */       boolean oldValue = Object2BooleanOpenCustomHashMap.this.value[this.index];
/*  628 */       Object2BooleanOpenCustomHashMap.this.value[this.index] = v;
/*  629 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean getValue() {
/*  639 */       return Boolean.valueOf(Object2BooleanOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean setValue(Boolean v) {
/*  649 */       return Boolean.valueOf(setValue(v.booleanValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  654 */       if (!(o instanceof Map.Entry))
/*  655 */         return false; 
/*  656 */       Map.Entry<K, Boolean> e = (Map.Entry<K, Boolean>)o;
/*  657 */       return (Object2BooleanOpenCustomHashMap.this.strategy.equals(Object2BooleanOpenCustomHashMap.this.key[this.index], e.getKey()) && Object2BooleanOpenCustomHashMap.this.value[this.index] == ((Boolean)e
/*  658 */         .getValue()).booleanValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  662 */       return Object2BooleanOpenCustomHashMap.this.strategy.hashCode(Object2BooleanOpenCustomHashMap.this.key[this.index]) ^ (Object2BooleanOpenCustomHashMap.this.value[this.index] ? 1231 : 1237);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  666 */       return (new StringBuilder()).append(Object2BooleanOpenCustomHashMap.this.key[this.index]).append("=>").append(Object2BooleanOpenCustomHashMap.this.value[this.index]).toString();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  676 */     int pos = Object2BooleanOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  683 */     int last = -1;
/*      */     
/*  685 */     int c = Object2BooleanOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  689 */     boolean mustReturnNullKey = Object2BooleanOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     ObjectArrayList<K> wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  696 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  699 */       if (!hasNext())
/*  700 */         throw new NoSuchElementException(); 
/*  701 */       this.c--;
/*  702 */       if (this.mustReturnNullKey) {
/*  703 */         this.mustReturnNullKey = false;
/*  704 */         return this.last = Object2BooleanOpenCustomHashMap.this.n;
/*      */       } 
/*  706 */       K[] key = Object2BooleanOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  708 */         if (--this.pos < 0) {
/*      */           
/*  710 */           this.last = Integer.MIN_VALUE;
/*  711 */           K k = this.wrapped.get(-this.pos - 1);
/*  712 */           int p = HashCommon.mix(Object2BooleanOpenCustomHashMap.this.strategy.hashCode(k)) & Object2BooleanOpenCustomHashMap.this.mask;
/*  713 */           while (!Object2BooleanOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  714 */             p = p + 1 & Object2BooleanOpenCustomHashMap.this.mask; 
/*  715 */           return p;
/*      */         } 
/*  717 */         if (key[this.pos] != null) {
/*  718 */           return this.last = this.pos;
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
/*  732 */       K[] key = Object2BooleanOpenCustomHashMap.this.key; while (true) {
/*      */         K curr; int last;
/*  734 */         pos = (last = pos) + 1 & Object2BooleanOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  736 */           if ((curr = key[pos]) == null) {
/*  737 */             key[last] = null;
/*      */             return;
/*      */           } 
/*  740 */           int slot = HashCommon.mix(Object2BooleanOpenCustomHashMap.this.strategy.hashCode(curr)) & Object2BooleanOpenCustomHashMap.this.mask;
/*  741 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  743 */           pos = pos + 1 & Object2BooleanOpenCustomHashMap.this.mask;
/*      */         } 
/*  745 */         if (pos < last) {
/*  746 */           if (this.wrapped == null)
/*  747 */             this.wrapped = new ObjectArrayList<>(2); 
/*  748 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  750 */         key[last] = curr;
/*  751 */         Object2BooleanOpenCustomHashMap.this.value[last] = Object2BooleanOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  755 */       if (this.last == -1)
/*  756 */         throw new IllegalStateException(); 
/*  757 */       if (this.last == Object2BooleanOpenCustomHashMap.this.n) {
/*  758 */         Object2BooleanOpenCustomHashMap.this.containsNullKey = false;
/*  759 */         Object2BooleanOpenCustomHashMap.this.key[Object2BooleanOpenCustomHashMap.this.n] = null;
/*  760 */       } else if (this.pos >= 0) {
/*  761 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  764 */         Object2BooleanOpenCustomHashMap.this.removeBoolean(this.wrapped.set(-this.pos - 1, null));
/*  765 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  768 */       Object2BooleanOpenCustomHashMap.this.size--;
/*  769 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  774 */       int i = n;
/*  775 */       while (i-- != 0 && hasNext())
/*  776 */         nextEntry(); 
/*  777 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Object2BooleanMap.Entry<K>> { private Object2BooleanOpenCustomHashMap<K>.MapEntry entry;
/*      */     
/*      */     public Object2BooleanOpenCustomHashMap<K>.MapEntry next() {
/*  784 */       return this.entry = new Object2BooleanOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  788 */       super.remove();
/*  789 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Object2BooleanMap.Entry<K>> { private FastEntryIterator() {
/*  793 */       this.entry = new Object2BooleanOpenCustomHashMap.MapEntry();
/*      */     } private final Object2BooleanOpenCustomHashMap<K>.MapEntry entry;
/*      */     public Object2BooleanOpenCustomHashMap<K>.MapEntry next() {
/*  796 */       this.entry.index = nextEntry();
/*  797 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Object2BooleanMap.Entry<K>> implements Object2BooleanMap.FastEntrySet<K> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Object2BooleanMap.Entry<K>> iterator() {
/*  803 */       return new Object2BooleanOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Object2BooleanMap.Entry<K>> fastIterator() {
/*  807 */       return new Object2BooleanOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  812 */       if (!(o instanceof Map.Entry))
/*  813 */         return false; 
/*  814 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  815 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/*  816 */         return false; 
/*  817 */       K k = (K)e.getKey();
/*  818 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/*  819 */       if (Object2BooleanOpenCustomHashMap.this.strategy.equals(k, null)) {
/*  820 */         return (Object2BooleanOpenCustomHashMap.this.containsNullKey && Object2BooleanOpenCustomHashMap.this.value[Object2BooleanOpenCustomHashMap.this.n] == v);
/*      */       }
/*  822 */       K[] key = Object2BooleanOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  825 */       if ((curr = key[pos = HashCommon.mix(Object2BooleanOpenCustomHashMap.this.strategy.hashCode(k)) & Object2BooleanOpenCustomHashMap.this.mask]) == null)
/*  826 */         return false; 
/*  827 */       if (Object2BooleanOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  828 */         return (Object2BooleanOpenCustomHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  831 */         if ((curr = key[pos = pos + 1 & Object2BooleanOpenCustomHashMap.this.mask]) == null)
/*  832 */           return false; 
/*  833 */         if (Object2BooleanOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  834 */           return (Object2BooleanOpenCustomHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  840 */       if (!(o instanceof Map.Entry))
/*  841 */         return false; 
/*  842 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  843 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/*  844 */         return false; 
/*  845 */       K k = (K)e.getKey();
/*  846 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/*  847 */       if (Object2BooleanOpenCustomHashMap.this.strategy.equals(k, null)) {
/*  848 */         if (Object2BooleanOpenCustomHashMap.this.containsNullKey && Object2BooleanOpenCustomHashMap.this.value[Object2BooleanOpenCustomHashMap.this.n] == v) {
/*  849 */           Object2BooleanOpenCustomHashMap.this.removeNullEntry();
/*  850 */           return true;
/*      */         } 
/*  852 */         return false;
/*      */       } 
/*      */       
/*  855 */       K[] key = Object2BooleanOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  858 */       if ((curr = key[pos = HashCommon.mix(Object2BooleanOpenCustomHashMap.this.strategy.hashCode(k)) & Object2BooleanOpenCustomHashMap.this.mask]) == null)
/*  859 */         return false; 
/*  860 */       if (Object2BooleanOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  861 */         if (Object2BooleanOpenCustomHashMap.this.value[pos] == v) {
/*  862 */           Object2BooleanOpenCustomHashMap.this.removeEntry(pos);
/*  863 */           return true;
/*      */         } 
/*  865 */         return false;
/*      */       } 
/*      */       while (true) {
/*  868 */         if ((curr = key[pos = pos + 1 & Object2BooleanOpenCustomHashMap.this.mask]) == null)
/*  869 */           return false; 
/*  870 */         if (Object2BooleanOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  871 */           Object2BooleanOpenCustomHashMap.this.value[pos] == v) {
/*  872 */           Object2BooleanOpenCustomHashMap.this.removeEntry(pos);
/*  873 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  880 */       return Object2BooleanOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  884 */       Object2BooleanOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Object2BooleanMap.Entry<K>> consumer) {
/*  889 */       if (Object2BooleanOpenCustomHashMap.this.containsNullKey)
/*  890 */         consumer.accept(new AbstractObject2BooleanMap.BasicEntry<>(Object2BooleanOpenCustomHashMap.this.key[Object2BooleanOpenCustomHashMap.this.n], Object2BooleanOpenCustomHashMap.this.value[Object2BooleanOpenCustomHashMap.this.n])); 
/*  891 */       for (int pos = Object2BooleanOpenCustomHashMap.this.n; pos-- != 0;) {
/*  892 */         if (Object2BooleanOpenCustomHashMap.this.key[pos] != null)
/*  893 */           consumer.accept(new AbstractObject2BooleanMap.BasicEntry<>(Object2BooleanOpenCustomHashMap.this.key[pos], Object2BooleanOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Object2BooleanMap.Entry<K>> consumer) {
/*  898 */       AbstractObject2BooleanMap.BasicEntry<K> entry = new AbstractObject2BooleanMap.BasicEntry<>();
/*  899 */       if (Object2BooleanOpenCustomHashMap.this.containsNullKey) {
/*  900 */         entry.key = Object2BooleanOpenCustomHashMap.this.key[Object2BooleanOpenCustomHashMap.this.n];
/*  901 */         entry.value = Object2BooleanOpenCustomHashMap.this.value[Object2BooleanOpenCustomHashMap.this.n];
/*  902 */         consumer.accept(entry);
/*      */       } 
/*  904 */       for (int pos = Object2BooleanOpenCustomHashMap.this.n; pos-- != 0;) {
/*  905 */         if (Object2BooleanOpenCustomHashMap.this.key[pos] != null) {
/*  906 */           entry.key = Object2BooleanOpenCustomHashMap.this.key[pos];
/*  907 */           entry.value = Object2BooleanOpenCustomHashMap.this.value[pos];
/*  908 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Object2BooleanMap.FastEntrySet<K> object2BooleanEntrySet() {
/*  914 */     if (this.entries == null)
/*  915 */       this.entries = new MapEntrySet(); 
/*  916 */     return this.entries;
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
/*      */     implements ObjectIterator<K>
/*      */   {
/*      */     public K next() {
/*  933 */       return Object2BooleanOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractObjectSet<K> { private KeySet() {}
/*      */     
/*      */     public ObjectIterator<K> iterator() {
/*  939 */       return new Object2BooleanOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/*  944 */       if (Object2BooleanOpenCustomHashMap.this.containsNullKey)
/*  945 */         consumer.accept(Object2BooleanOpenCustomHashMap.this.key[Object2BooleanOpenCustomHashMap.this.n]); 
/*  946 */       for (int pos = Object2BooleanOpenCustomHashMap.this.n; pos-- != 0; ) {
/*  947 */         K k = Object2BooleanOpenCustomHashMap.this.key[pos];
/*  948 */         if (k != null)
/*  949 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  954 */       return Object2BooleanOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(Object k) {
/*  958 */       return Object2BooleanOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(Object k) {
/*  962 */       int oldSize = Object2BooleanOpenCustomHashMap.this.size;
/*  963 */       Object2BooleanOpenCustomHashMap.this.removeBoolean(k);
/*  964 */       return (Object2BooleanOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  968 */       Object2BooleanOpenCustomHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ObjectSet<K> keySet() {
/*  973 */     if (this.keys == null)
/*  974 */       this.keys = new KeySet(); 
/*  975 */     return this.keys;
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
/*  992 */       return Object2BooleanOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public BooleanCollection values() {
/*  997 */     if (this.values == null)
/*  998 */       this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */         {
/*      */           public BooleanIterator iterator() {
/* 1001 */             return new Object2BooleanOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1005 */             return Object2BooleanOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(boolean v) {
/* 1009 */             return Object2BooleanOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1013 */             Object2BooleanOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(BooleanConsumer consumer)
/*      */           {
/* 1018 */             if (Object2BooleanOpenCustomHashMap.this.containsNullKey)
/* 1019 */               consumer.accept(Object2BooleanOpenCustomHashMap.this.value[Object2BooleanOpenCustomHashMap.this.n]); 
/* 1020 */             for (int pos = Object2BooleanOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1021 */               if (Object2BooleanOpenCustomHashMap.this.key[pos] != null)
/* 1022 */                 consumer.accept(Object2BooleanOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1025 */     return this.values;
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
/* 1042 */     return trim(this.size);
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
/* 1066 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1067 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1068 */       return true; 
/*      */     try {
/* 1070 */       rehash(l);
/* 1071 */     } catch (OutOfMemoryError cantDoIt) {
/* 1072 */       return false;
/*      */     } 
/* 1074 */     return true;
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
/* 1090 */     K[] key = this.key;
/* 1091 */     boolean[] value = this.value;
/* 1092 */     int mask = newN - 1;
/* 1093 */     K[] newKey = (K[])new Object[newN + 1];
/* 1094 */     boolean[] newValue = new boolean[newN + 1];
/* 1095 */     int i = this.n;
/* 1096 */     for (int j = realSize(); j-- != 0; ) {
/* 1097 */       while (key[--i] == null); int pos;
/* 1098 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != null)
/* 1099 */         while (newKey[pos = pos + 1 & mask] != null); 
/* 1100 */       newKey[pos] = key[i];
/* 1101 */       newValue[pos] = value[i];
/*      */     } 
/* 1103 */     newValue[newN] = value[this.n];
/* 1104 */     this.n = newN;
/* 1105 */     this.mask = mask;
/* 1106 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1107 */     this.key = newKey;
/* 1108 */     this.value = newValue;
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
/*      */   public Object2BooleanOpenCustomHashMap<K> clone() {
/*      */     Object2BooleanOpenCustomHashMap<K> c;
/*      */     try {
/* 1125 */       c = (Object2BooleanOpenCustomHashMap<K>)super.clone();
/* 1126 */     } catch (CloneNotSupportedException cantHappen) {
/* 1127 */       throw new InternalError();
/*      */     } 
/* 1129 */     c.keys = null;
/* 1130 */     c.values = null;
/* 1131 */     c.entries = null;
/* 1132 */     c.containsNullKey = this.containsNullKey;
/* 1133 */     c.key = (K[])this.key.clone();
/* 1134 */     c.value = (boolean[])this.value.clone();
/* 1135 */     c.strategy = this.strategy;
/* 1136 */     return c;
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
/* 1149 */     int h = 0;
/* 1150 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1151 */       while (this.key[i] == null)
/* 1152 */         i++; 
/* 1153 */       if (this != this.key[i])
/* 1154 */         t = this.strategy.hashCode(this.key[i]); 
/* 1155 */       t ^= this.value[i] ? 1231 : 1237;
/* 1156 */       h += t;
/* 1157 */       i++;
/*      */     } 
/*      */     
/* 1160 */     if (this.containsNullKey)
/* 1161 */       h += this.value[this.n] ? 1231 : 1237; 
/* 1162 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1165 */     K[] key = this.key;
/* 1166 */     boolean[] value = this.value;
/* 1167 */     MapIterator i = new MapIterator();
/* 1168 */     s.defaultWriteObject();
/* 1169 */     for (int j = this.size; j-- != 0; ) {
/* 1170 */       int e = i.nextEntry();
/* 1171 */       s.writeObject(key[e]);
/* 1172 */       s.writeBoolean(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1177 */     s.defaultReadObject();
/* 1178 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1179 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1180 */     this.mask = this.n - 1;
/* 1181 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1182 */     boolean[] value = this.value = new boolean[this.n + 1];
/*      */ 
/*      */     
/* 1185 */     for (int i = this.size; i-- != 0; ) {
/* 1186 */       int pos; K k = (K)s.readObject();
/* 1187 */       boolean v = s.readBoolean();
/* 1188 */       if (this.strategy.equals(k, null)) {
/* 1189 */         pos = this.n;
/* 1190 */         this.containsNullKey = true;
/*      */       } else {
/* 1192 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1193 */         while (key[pos] != null)
/* 1194 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1196 */       key[pos] = k;
/* 1197 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2BooleanOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */