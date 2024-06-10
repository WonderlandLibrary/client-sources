/*      */ package it.unimi.dsi.fastutil.ints;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectCollection;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Int2ObjectOpenCustomHashMap<V>
/*      */   extends AbstractInt2ObjectMap<V>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient int[] key;
/*      */   protected transient V[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected IntHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Int2ObjectMap.FastEntrySet<V> entries;
/*      */   protected transient IntSet keys;
/*      */   protected transient ObjectCollection<V> values;
/*      */   
/*      */   public Int2ObjectOpenCustomHashMap(int expected, float f, IntHash.Strategy strategy) {
/*  106 */     this.strategy = strategy;
/*  107 */     if (f <= 0.0F || f > 1.0F)
/*  108 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  109 */     if (expected < 0)
/*  110 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  111 */     this.f = f;
/*  112 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  113 */     this.mask = this.n - 1;
/*  114 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  115 */     this.key = new int[this.n + 1];
/*  116 */     this.value = (V[])new Object[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2ObjectOpenCustomHashMap(int expected, IntHash.Strategy strategy) {
/*  127 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2ObjectOpenCustomHashMap(IntHash.Strategy strategy) {
/*  138 */     this(16, 0.75F, strategy);
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
/*      */   public Int2ObjectOpenCustomHashMap(Map<? extends Integer, ? extends V> m, float f, IntHash.Strategy strategy) {
/*  152 */     this(m.size(), f, strategy);
/*  153 */     putAll(m);
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
/*      */   public Int2ObjectOpenCustomHashMap(Map<? extends Integer, ? extends V> m, IntHash.Strategy strategy) {
/*  166 */     this(m, 0.75F, strategy);
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
/*      */   public Int2ObjectOpenCustomHashMap(Int2ObjectMap<V> m, float f, IntHash.Strategy strategy) {
/*  180 */     this(m.size(), f, strategy);
/*  181 */     putAll(m);
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
/*      */   public Int2ObjectOpenCustomHashMap(Int2ObjectMap<V> m, IntHash.Strategy strategy) {
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
/*      */   public Int2ObjectOpenCustomHashMap(int[] k, V[] v, float f, IntHash.Strategy strategy) {
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
/*      */   
/*      */   public Int2ObjectOpenCustomHashMap(int[] k, V[] v, IntHash.Strategy strategy) {
/*  234 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntHash.Strategy strategy() {
/*  242 */     return this.strategy;
/*      */   }
/*      */   private int realSize() {
/*  245 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  248 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  249 */     if (needed > this.n)
/*  250 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  253 */     int needed = (int)Math.min(1073741824L, 
/*  254 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  255 */     if (needed > this.n)
/*  256 */       rehash(needed); 
/*      */   }
/*      */   private V removeEntry(int pos) {
/*  259 */     V oldValue = this.value[pos];
/*  260 */     this.value[pos] = null;
/*  261 */     this.size--;
/*  262 */     shiftKeys(pos);
/*  263 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  264 */       rehash(this.n / 2); 
/*  265 */     return oldValue;
/*      */   }
/*      */   private V removeNullEntry() {
/*  268 */     this.containsNullKey = false;
/*  269 */     V oldValue = this.value[this.n];
/*  270 */     this.value[this.n] = null;
/*  271 */     this.size--;
/*  272 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  273 */       rehash(this.n / 2); 
/*  274 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Integer, ? extends V> m) {
/*  278 */     if (this.f <= 0.5D) {
/*  279 */       ensureCapacity(m.size());
/*      */     } else {
/*  281 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  283 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(int k) {
/*  287 */     if (this.strategy.equals(k, 0)) {
/*  288 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  290 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
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
/*      */   private void insert(int pos, int k, V v) {
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
/*      */   public V put(int k, V v) {
/*  317 */     int pos = find(k);
/*  318 */     if (pos < 0) {
/*  319 */       insert(-pos - 1, k, v);
/*  320 */       return this.defRetValue;
/*      */     } 
/*  322 */     V oldValue = this.value[pos];
/*  323 */     this.value[pos] = v;
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
/*      */   protected final void shiftKeys(int pos) {
/*  337 */     int[] key = this.key; while (true) {
/*      */       int curr, last;
/*  339 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  341 */         if ((curr = key[pos]) == 0) {
/*  342 */           key[last] = 0;
/*  343 */           this.value[last] = null;
/*      */           return;
/*      */         } 
/*  346 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  347 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  349 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  351 */       key[last] = curr;
/*  352 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public V remove(int k) {
/*  358 */     if (this.strategy.equals(k, 0)) {
/*  359 */       if (this.containsNullKey)
/*  360 */         return removeNullEntry(); 
/*  361 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  364 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  367 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  368 */       return this.defRetValue; 
/*  369 */     if (this.strategy.equals(k, curr))
/*  370 */       return removeEntry(pos); 
/*      */     while (true) {
/*  372 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  373 */         return this.defRetValue; 
/*  374 */       if (this.strategy.equals(k, curr)) {
/*  375 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public V get(int k) {
/*  381 */     if (this.strategy.equals(k, 0)) {
/*  382 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  384 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  387 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  388 */       return this.defRetValue; 
/*  389 */     if (this.strategy.equals(k, curr)) {
/*  390 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  393 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  394 */         return this.defRetValue; 
/*  395 */       if (this.strategy.equals(k, curr)) {
/*  396 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(int k) {
/*  402 */     if (this.strategy.equals(k, 0)) {
/*  403 */       return this.containsNullKey;
/*      */     }
/*  405 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  408 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  409 */       return false; 
/*  410 */     if (this.strategy.equals(k, curr)) {
/*  411 */       return true;
/*      */     }
/*      */     while (true) {
/*  414 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  415 */         return false; 
/*  416 */       if (this.strategy.equals(k, curr))
/*  417 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(Object v) {
/*  422 */     V[] value = this.value;
/*  423 */     int[] key = this.key;
/*  424 */     if (this.containsNullKey && Objects.equals(value[this.n], v))
/*  425 */       return true; 
/*  426 */     for (int i = this.n; i-- != 0;) {
/*  427 */       if (key[i] != 0 && Objects.equals(value[i], v))
/*  428 */         return true; 
/*  429 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V getOrDefault(int k, V defaultValue) {
/*  435 */     if (this.strategy.equals(k, 0)) {
/*  436 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  438 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  441 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  442 */       return defaultValue; 
/*  443 */     if (this.strategy.equals(k, curr)) {
/*  444 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  447 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  448 */         return defaultValue; 
/*  449 */       if (this.strategy.equals(k, curr)) {
/*  450 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public V putIfAbsent(int k, V v) {
/*  456 */     int pos = find(k);
/*  457 */     if (pos >= 0)
/*  458 */       return this.value[pos]; 
/*  459 */     insert(-pos - 1, k, v);
/*  460 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(int k, Object v) {
/*  466 */     if (this.strategy.equals(k, 0)) {
/*  467 */       if (this.containsNullKey && Objects.equals(v, this.value[this.n])) {
/*  468 */         removeNullEntry();
/*  469 */         return true;
/*      */       } 
/*  471 */       return false;
/*      */     } 
/*      */     
/*  474 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  477 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  478 */       return false; 
/*  479 */     if (this.strategy.equals(k, curr) && Objects.equals(v, this.value[pos])) {
/*  480 */       removeEntry(pos);
/*  481 */       return true;
/*      */     } 
/*      */     while (true) {
/*  484 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  485 */         return false; 
/*  486 */       if (this.strategy.equals(k, curr) && Objects.equals(v, this.value[pos])) {
/*  487 */         removeEntry(pos);
/*  488 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(int k, V oldValue, V v) {
/*  495 */     int pos = find(k);
/*  496 */     if (pos < 0 || !Objects.equals(oldValue, this.value[pos]))
/*  497 */       return false; 
/*  498 */     this.value[pos] = v;
/*  499 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public V replace(int k, V v) {
/*  504 */     int pos = find(k);
/*  505 */     if (pos < 0)
/*  506 */       return this.defRetValue; 
/*  507 */     V oldValue = this.value[pos];
/*  508 */     this.value[pos] = v;
/*  509 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public V computeIfAbsent(int k, IntFunction<? extends V> mappingFunction) {
/*  514 */     Objects.requireNonNull(mappingFunction);
/*  515 */     int pos = find(k);
/*  516 */     if (pos >= 0)
/*  517 */       return this.value[pos]; 
/*  518 */     V newValue = mappingFunction.apply(k);
/*  519 */     insert(-pos - 1, k, newValue);
/*  520 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V computeIfPresent(int k, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
/*  526 */     Objects.requireNonNull(remappingFunction);
/*  527 */     int pos = find(k);
/*  528 */     if (pos < 0)
/*  529 */       return this.defRetValue; 
/*  530 */     V newValue = remappingFunction.apply(Integer.valueOf(k), this.value[pos]);
/*  531 */     if (newValue == null) {
/*  532 */       if (this.strategy.equals(k, 0)) {
/*  533 */         removeNullEntry();
/*      */       } else {
/*  535 */         removeEntry(pos);
/*  536 */       }  return this.defRetValue;
/*      */     } 
/*  538 */     this.value[pos] = newValue; return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V compute(int k, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
/*  544 */     Objects.requireNonNull(remappingFunction);
/*  545 */     int pos = find(k);
/*  546 */     V newValue = remappingFunction.apply(Integer.valueOf(k), (pos >= 0) ? this.value[pos] : null);
/*  547 */     if (newValue == null) {
/*  548 */       if (pos >= 0)
/*  549 */         if (this.strategy.equals(k, 0)) {
/*  550 */           removeNullEntry();
/*      */         } else {
/*  552 */           removeEntry(pos);
/*      */         }  
/*  554 */       return this.defRetValue;
/*      */     } 
/*  556 */     V newVal = newValue;
/*  557 */     if (pos < 0) {
/*  558 */       insert(-pos - 1, k, newVal);
/*  559 */       return newVal;
/*      */     } 
/*  561 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V merge(int k, V v, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/*  567 */     Objects.requireNonNull(remappingFunction);
/*  568 */     int pos = find(k);
/*  569 */     if (pos < 0 || this.value[pos] == null) {
/*  570 */       if (v == null)
/*  571 */         return this.defRetValue; 
/*  572 */       insert(-pos - 1, k, v);
/*  573 */       return v;
/*      */     } 
/*  575 */     V newValue = remappingFunction.apply(this.value[pos], v);
/*  576 */     if (newValue == null) {
/*  577 */       if (this.strategy.equals(k, 0)) {
/*  578 */         removeNullEntry();
/*      */       } else {
/*  580 */         removeEntry(pos);
/*  581 */       }  return this.defRetValue;
/*      */     } 
/*  583 */     this.value[pos] = newValue; return newValue;
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
/*  594 */     if (this.size == 0)
/*      */       return; 
/*  596 */     this.size = 0;
/*  597 */     this.containsNullKey = false;
/*  598 */     Arrays.fill(this.key, 0);
/*  599 */     Arrays.fill((Object[])this.value, (Object)null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  603 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  607 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Int2ObjectMap.Entry<V>, Map.Entry<Integer, V>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  619 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public int getIntKey() {
/*  625 */       return Int2ObjectOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public V getValue() {
/*  629 */       return Int2ObjectOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public V setValue(V v) {
/*  633 */       V oldValue = Int2ObjectOpenCustomHashMap.this.value[this.index];
/*  634 */       Int2ObjectOpenCustomHashMap.this.value[this.index] = v;
/*  635 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer getKey() {
/*  645 */       return Integer.valueOf(Int2ObjectOpenCustomHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  650 */       if (!(o instanceof Map.Entry))
/*  651 */         return false; 
/*  652 */       Map.Entry<Integer, V> e = (Map.Entry<Integer, V>)o;
/*  653 */       return (Int2ObjectOpenCustomHashMap.this.strategy.equals(Int2ObjectOpenCustomHashMap.this.key[this.index], ((Integer)e.getKey()).intValue()) && 
/*  654 */         Objects.equals(Int2ObjectOpenCustomHashMap.this.value[this.index], e.getValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  658 */       return Int2ObjectOpenCustomHashMap.this.strategy.hashCode(Int2ObjectOpenCustomHashMap.this.key[this.index]) ^ ((Int2ObjectOpenCustomHashMap.this.value[this.index] == null) ? 0 : Int2ObjectOpenCustomHashMap.this.value[this.index].hashCode());
/*      */     }
/*      */     
/*      */     public String toString() {
/*  662 */       return Int2ObjectOpenCustomHashMap.this.key[this.index] + "=>" + Int2ObjectOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  672 */     int pos = Int2ObjectOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  679 */     int last = -1;
/*      */     
/*  681 */     int c = Int2ObjectOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  685 */     boolean mustReturnNullKey = Int2ObjectOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     IntArrayList wrapped;
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
/*  700 */         return this.last = Int2ObjectOpenCustomHashMap.this.n;
/*      */       } 
/*  702 */       int[] key = Int2ObjectOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  704 */         if (--this.pos < 0) {
/*      */           
/*  706 */           this.last = Integer.MIN_VALUE;
/*  707 */           int k = this.wrapped.getInt(-this.pos - 1);
/*  708 */           int p = HashCommon.mix(Int2ObjectOpenCustomHashMap.this.strategy.hashCode(k)) & Int2ObjectOpenCustomHashMap.this.mask;
/*  709 */           while (!Int2ObjectOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  710 */             p = p + 1 & Int2ObjectOpenCustomHashMap.this.mask; 
/*  711 */           return p;
/*      */         } 
/*  713 */         if (key[this.pos] != 0) {
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
/*  728 */       int[] key = Int2ObjectOpenCustomHashMap.this.key; while (true) {
/*      */         int curr, last;
/*  730 */         pos = (last = pos) + 1 & Int2ObjectOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  732 */           if ((curr = key[pos]) == 0) {
/*  733 */             key[last] = 0;
/*  734 */             Int2ObjectOpenCustomHashMap.this.value[last] = null;
/*      */             return;
/*      */           } 
/*  737 */           int slot = HashCommon.mix(Int2ObjectOpenCustomHashMap.this.strategy.hashCode(curr)) & Int2ObjectOpenCustomHashMap.this.mask;
/*  738 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  740 */           pos = pos + 1 & Int2ObjectOpenCustomHashMap.this.mask;
/*      */         } 
/*  742 */         if (pos < last) {
/*  743 */           if (this.wrapped == null)
/*  744 */             this.wrapped = new IntArrayList(2); 
/*  745 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  747 */         key[last] = curr;
/*  748 */         Int2ObjectOpenCustomHashMap.this.value[last] = Int2ObjectOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  752 */       if (this.last == -1)
/*  753 */         throw new IllegalStateException(); 
/*  754 */       if (this.last == Int2ObjectOpenCustomHashMap.this.n) {
/*  755 */         Int2ObjectOpenCustomHashMap.this.containsNullKey = false;
/*  756 */         Int2ObjectOpenCustomHashMap.this.value[Int2ObjectOpenCustomHashMap.this.n] = null;
/*  757 */       } else if (this.pos >= 0) {
/*  758 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  761 */         Int2ObjectOpenCustomHashMap.this.remove(this.wrapped.getInt(-this.pos - 1));
/*  762 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  765 */       Int2ObjectOpenCustomHashMap.this.size--;
/*  766 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  771 */       int i = n;
/*  772 */       while (i-- != 0 && hasNext())
/*  773 */         nextEntry(); 
/*  774 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Int2ObjectMap.Entry<V>> { private Int2ObjectOpenCustomHashMap<V>.MapEntry entry;
/*      */     
/*      */     public Int2ObjectOpenCustomHashMap<V>.MapEntry next() {
/*  781 */       return this.entry = new Int2ObjectOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  785 */       super.remove();
/*  786 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Int2ObjectMap.Entry<V>> { private FastEntryIterator() {
/*  790 */       this.entry = new Int2ObjectOpenCustomHashMap.MapEntry();
/*      */     } private final Int2ObjectOpenCustomHashMap<V>.MapEntry entry;
/*      */     public Int2ObjectOpenCustomHashMap<V>.MapEntry next() {
/*  793 */       this.entry.index = nextEntry();
/*  794 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Int2ObjectMap.Entry<V>> implements Int2ObjectMap.FastEntrySet<V> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Int2ObjectMap.Entry<V>> iterator() {
/*  800 */       return new Int2ObjectOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Int2ObjectMap.Entry<V>> fastIterator() {
/*  804 */       return new Int2ObjectOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  809 */       if (!(o instanceof Map.Entry))
/*  810 */         return false; 
/*  811 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  812 */       if (e.getKey() == null || !(e.getKey() instanceof Integer))
/*  813 */         return false; 
/*  814 */       int k = ((Integer)e.getKey()).intValue();
/*  815 */       V v = (V)e.getValue();
/*  816 */       if (Int2ObjectOpenCustomHashMap.this.strategy.equals(k, 0)) {
/*  817 */         return (Int2ObjectOpenCustomHashMap.this.containsNullKey && Objects.equals(Int2ObjectOpenCustomHashMap.this.value[Int2ObjectOpenCustomHashMap.this.n], v));
/*      */       }
/*  819 */       int[] key = Int2ObjectOpenCustomHashMap.this.key;
/*      */       
/*      */       int curr, pos;
/*  822 */       if ((curr = key[pos = HashCommon.mix(Int2ObjectOpenCustomHashMap.this.strategy.hashCode(k)) & Int2ObjectOpenCustomHashMap.this.mask]) == 0)
/*  823 */         return false; 
/*  824 */       if (Int2ObjectOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  825 */         return Objects.equals(Int2ObjectOpenCustomHashMap.this.value[pos], v);
/*      */       }
/*      */       while (true) {
/*  828 */         if ((curr = key[pos = pos + 1 & Int2ObjectOpenCustomHashMap.this.mask]) == 0)
/*  829 */           return false; 
/*  830 */         if (Int2ObjectOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  831 */           return Objects.equals(Int2ObjectOpenCustomHashMap.this.value[pos], v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  837 */       if (!(o instanceof Map.Entry))
/*  838 */         return false; 
/*  839 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  840 */       if (e.getKey() == null || !(e.getKey() instanceof Integer))
/*  841 */         return false; 
/*  842 */       int k = ((Integer)e.getKey()).intValue();
/*  843 */       V v = (V)e.getValue();
/*  844 */       if (Int2ObjectOpenCustomHashMap.this.strategy.equals(k, 0)) {
/*  845 */         if (Int2ObjectOpenCustomHashMap.this.containsNullKey && Objects.equals(Int2ObjectOpenCustomHashMap.this.value[Int2ObjectOpenCustomHashMap.this.n], v)) {
/*  846 */           Int2ObjectOpenCustomHashMap.this.removeNullEntry();
/*  847 */           return true;
/*      */         } 
/*  849 */         return false;
/*      */       } 
/*      */       
/*  852 */       int[] key = Int2ObjectOpenCustomHashMap.this.key;
/*      */       
/*      */       int curr, pos;
/*  855 */       if ((curr = key[pos = HashCommon.mix(Int2ObjectOpenCustomHashMap.this.strategy.hashCode(k)) & Int2ObjectOpenCustomHashMap.this.mask]) == 0)
/*  856 */         return false; 
/*  857 */       if (Int2ObjectOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  858 */         if (Objects.equals(Int2ObjectOpenCustomHashMap.this.value[pos], v)) {
/*  859 */           Int2ObjectOpenCustomHashMap.this.removeEntry(pos);
/*  860 */           return true;
/*      */         } 
/*  862 */         return false;
/*      */       } 
/*      */       while (true) {
/*  865 */         if ((curr = key[pos = pos + 1 & Int2ObjectOpenCustomHashMap.this.mask]) == 0)
/*  866 */           return false; 
/*  867 */         if (Int2ObjectOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  868 */           Objects.equals(Int2ObjectOpenCustomHashMap.this.value[pos], v)) {
/*  869 */           Int2ObjectOpenCustomHashMap.this.removeEntry(pos);
/*  870 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  877 */       return Int2ObjectOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  881 */       Int2ObjectOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Int2ObjectMap.Entry<V>> consumer) {
/*  886 */       if (Int2ObjectOpenCustomHashMap.this.containsNullKey)
/*  887 */         consumer.accept(new AbstractInt2ObjectMap.BasicEntry<>(Int2ObjectOpenCustomHashMap.this.key[Int2ObjectOpenCustomHashMap.this.n], Int2ObjectOpenCustomHashMap.this.value[Int2ObjectOpenCustomHashMap.this.n])); 
/*  888 */       for (int pos = Int2ObjectOpenCustomHashMap.this.n; pos-- != 0;) {
/*  889 */         if (Int2ObjectOpenCustomHashMap.this.key[pos] != 0)
/*  890 */           consumer.accept(new AbstractInt2ObjectMap.BasicEntry<>(Int2ObjectOpenCustomHashMap.this.key[pos], Int2ObjectOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Int2ObjectMap.Entry<V>> consumer) {
/*  895 */       AbstractInt2ObjectMap.BasicEntry<V> entry = new AbstractInt2ObjectMap.BasicEntry<>();
/*  896 */       if (Int2ObjectOpenCustomHashMap.this.containsNullKey) {
/*  897 */         entry.key = Int2ObjectOpenCustomHashMap.this.key[Int2ObjectOpenCustomHashMap.this.n];
/*  898 */         entry.value = Int2ObjectOpenCustomHashMap.this.value[Int2ObjectOpenCustomHashMap.this.n];
/*  899 */         consumer.accept(entry);
/*      */       } 
/*  901 */       for (int pos = Int2ObjectOpenCustomHashMap.this.n; pos-- != 0;) {
/*  902 */         if (Int2ObjectOpenCustomHashMap.this.key[pos] != 0) {
/*  903 */           entry.key = Int2ObjectOpenCustomHashMap.this.key[pos];
/*  904 */           entry.value = Int2ObjectOpenCustomHashMap.this.value[pos];
/*  905 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Int2ObjectMap.FastEntrySet<V> int2ObjectEntrySet() {
/*  911 */     if (this.entries == null)
/*  912 */       this.entries = new MapEntrySet(); 
/*  913 */     return this.entries;
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
/*  930 */       return Int2ObjectOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractIntSet { private KeySet() {}
/*      */     
/*      */     public IntIterator iterator() {
/*  936 */       return new Int2ObjectOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer consumer) {
/*  941 */       if (Int2ObjectOpenCustomHashMap.this.containsNullKey)
/*  942 */         consumer.accept(Int2ObjectOpenCustomHashMap.this.key[Int2ObjectOpenCustomHashMap.this.n]); 
/*  943 */       for (int pos = Int2ObjectOpenCustomHashMap.this.n; pos-- != 0; ) {
/*  944 */         int k = Int2ObjectOpenCustomHashMap.this.key[pos];
/*  945 */         if (k != 0)
/*  946 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  951 */       return Int2ObjectOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(int k) {
/*  955 */       return Int2ObjectOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(int k) {
/*  959 */       int oldSize = Int2ObjectOpenCustomHashMap.this.size;
/*  960 */       Int2ObjectOpenCustomHashMap.this.remove(k);
/*  961 */       return (Int2ObjectOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  965 */       Int2ObjectOpenCustomHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public IntSet keySet() {
/*  970 */     if (this.keys == null)
/*  971 */       this.keys = new KeySet(); 
/*  972 */     return this.keys;
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
/*      */     implements ObjectIterator<V>
/*      */   {
/*      */     public V next() {
/*  989 */       return Int2ObjectOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectCollection<V> values() {
/*  994 */     if (this.values == null)
/*  995 */       this.values = (ObjectCollection<V>)new AbstractObjectCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/*  998 */             return new Int2ObjectOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1002 */             return Int2ObjectOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(Object v) {
/* 1006 */             return Int2ObjectOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1010 */             Int2ObjectOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(Consumer<? super V> consumer)
/*      */           {
/* 1015 */             if (Int2ObjectOpenCustomHashMap.this.containsNullKey)
/* 1016 */               consumer.accept(Int2ObjectOpenCustomHashMap.this.value[Int2ObjectOpenCustomHashMap.this.n]); 
/* 1017 */             for (int pos = Int2ObjectOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1018 */               if (Int2ObjectOpenCustomHashMap.this.key[pos] != 0)
/* 1019 */                 consumer.accept(Int2ObjectOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1022 */     return this.values;
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
/* 1039 */     return trim(this.size);
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
/* 1063 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1064 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1065 */       return true; 
/*      */     try {
/* 1067 */       rehash(l);
/* 1068 */     } catch (OutOfMemoryError cantDoIt) {
/* 1069 */       return false;
/*      */     } 
/* 1071 */     return true;
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
/* 1087 */     int[] key = this.key;
/* 1088 */     V[] value = this.value;
/* 1089 */     int mask = newN - 1;
/* 1090 */     int[] newKey = new int[newN + 1];
/* 1091 */     V[] newValue = (V[])new Object[newN + 1];
/* 1092 */     int i = this.n;
/* 1093 */     for (int j = realSize(); j-- != 0; ) {
/* 1094 */       while (key[--i] == 0); int pos;
/* 1095 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != 0)
/* 1096 */         while (newKey[pos = pos + 1 & mask] != 0); 
/* 1097 */       newKey[pos] = key[i];
/* 1098 */       newValue[pos] = value[i];
/*      */     } 
/* 1100 */     newValue[newN] = value[this.n];
/* 1101 */     this.n = newN;
/* 1102 */     this.mask = mask;
/* 1103 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1104 */     this.key = newKey;
/* 1105 */     this.value = newValue;
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
/*      */   public Int2ObjectOpenCustomHashMap<V> clone() {
/*      */     Int2ObjectOpenCustomHashMap<V> c;
/*      */     try {
/* 1122 */       c = (Int2ObjectOpenCustomHashMap<V>)super.clone();
/* 1123 */     } catch (CloneNotSupportedException cantHappen) {
/* 1124 */       throw new InternalError();
/*      */     } 
/* 1126 */     c.keys = null;
/* 1127 */     c.values = null;
/* 1128 */     c.entries = null;
/* 1129 */     c.containsNullKey = this.containsNullKey;
/* 1130 */     c.key = (int[])this.key.clone();
/* 1131 */     c.value = (V[])this.value.clone();
/* 1132 */     c.strategy = this.strategy;
/* 1133 */     return c;
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
/* 1146 */     int h = 0;
/* 1147 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1148 */       while (this.key[i] == 0)
/* 1149 */         i++; 
/* 1150 */       t = this.strategy.hashCode(this.key[i]);
/* 1151 */       if (this != this.value[i])
/* 1152 */         t ^= (this.value[i] == null) ? 0 : this.value[i].hashCode(); 
/* 1153 */       h += t;
/* 1154 */       i++;
/*      */     } 
/*      */     
/* 1157 */     if (this.containsNullKey)
/* 1158 */       h += (this.value[this.n] == null) ? 0 : this.value[this.n].hashCode(); 
/* 1159 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1162 */     int[] key = this.key;
/* 1163 */     V[] value = this.value;
/* 1164 */     MapIterator i = new MapIterator();
/* 1165 */     s.defaultWriteObject();
/* 1166 */     for (int j = this.size; j-- != 0; ) {
/* 1167 */       int e = i.nextEntry();
/* 1168 */       s.writeInt(key[e]);
/* 1169 */       s.writeObject(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1174 */     s.defaultReadObject();
/* 1175 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1176 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1177 */     this.mask = this.n - 1;
/* 1178 */     int[] key = this.key = new int[this.n + 1];
/* 1179 */     V[] value = this.value = (V[])new Object[this.n + 1];
/*      */ 
/*      */     
/* 1182 */     for (int i = this.size; i-- != 0; ) {
/* 1183 */       int pos, k = s.readInt();
/* 1184 */       V v = (V)s.readObject();
/* 1185 */       if (this.strategy.equals(k, 0)) {
/* 1186 */         pos = this.n;
/* 1187 */         this.containsNullKey = true;
/*      */       } else {
/* 1189 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1190 */         while (key[pos] != 0)
/* 1191 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1193 */       key[pos] = k;
/* 1194 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\Int2ObjectOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */