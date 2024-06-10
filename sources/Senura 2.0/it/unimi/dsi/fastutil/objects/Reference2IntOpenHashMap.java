/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
/*      */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*      */ import it.unimi.dsi.fastutil.ints.IntIterator;
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
/*      */ import java.util.function.ToIntFunction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Reference2IntOpenHashMap<K>
/*      */   extends AbstractReference2IntMap<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient int[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Reference2IntMap.FastEntrySet<K> entries;
/*      */   protected transient ReferenceSet<K> keys;
/*      */   protected transient IntCollection values;
/*      */   
/*      */   public Reference2IntOpenHashMap(int expected, float f) {
/*  100 */     if (f <= 0.0F || f > 1.0F)
/*  101 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  102 */     if (expected < 0)
/*  103 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  104 */     this.f = f;
/*  105 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  106 */     this.mask = this.n - 1;
/*  107 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  108 */     this.key = (K[])new Object[this.n + 1];
/*  109 */     this.value = new int[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2IntOpenHashMap(int expected) {
/*  118 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2IntOpenHashMap() {
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
/*      */   public Reference2IntOpenHashMap(Map<? extends K, ? extends Integer> m, float f) {
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
/*      */   public Reference2IntOpenHashMap(Map<? extends K, ? extends Integer> m) {
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
/*      */   public Reference2IntOpenHashMap(Reference2IntMap<K> m, float f) {
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
/*      */   public Reference2IntOpenHashMap(Reference2IntMap<K> m) {
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
/*      */   public Reference2IntOpenHashMap(K[] k, int[] v, float f) {
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
/*      */   public Reference2IntOpenHashMap(K[] k, int[] v) {
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
/*      */   private int removeEntry(int pos) {
/*  221 */     int oldValue = this.value[pos];
/*  222 */     this.size--;
/*  223 */     shiftKeys(pos);
/*  224 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  225 */       rehash(this.n / 2); 
/*  226 */     return oldValue;
/*      */   }
/*      */   private int removeNullEntry() {
/*  229 */     this.containsNullKey = false;
/*  230 */     this.key[this.n] = null;
/*  231 */     int oldValue = this.value[this.n];
/*  232 */     this.size--;
/*  233 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  234 */       rehash(this.n / 2); 
/*  235 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends Integer> m) {
/*  239 */     if (this.f <= 0.5D) {
/*  240 */       ensureCapacity(m.size());
/*      */     } else {
/*  242 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  244 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(K k) {
/*  248 */     if (k == null) {
/*  249 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  251 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  254 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  255 */       return -(pos + 1); 
/*  256 */     if (k == curr) {
/*  257 */       return pos;
/*      */     }
/*      */     while (true) {
/*  260 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  261 */         return -(pos + 1); 
/*  262 */       if (k == curr)
/*  263 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, K k, int v) {
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
/*      */   public int put(K k, int v) {
/*  278 */     int pos = find(k);
/*  279 */     if (pos < 0) {
/*  280 */       insert(-pos - 1, k, v);
/*  281 */       return this.defRetValue;
/*      */     } 
/*  283 */     int oldValue = this.value[pos];
/*  284 */     this.value[pos] = v;
/*  285 */     return oldValue;
/*      */   }
/*      */   private int addToValue(int pos, int incr) {
/*  288 */     int oldValue = this.value[pos];
/*  289 */     this.value[pos] = oldValue + incr;
/*  290 */     return oldValue;
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
/*      */   public int addTo(K k, int incr) {
/*      */     int pos;
/*  310 */     if (k == null) {
/*  311 */       if (this.containsNullKey)
/*  312 */         return addToValue(this.n, incr); 
/*  313 */       pos = this.n;
/*  314 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  317 */       K[] key = this.key;
/*      */       K curr;
/*  319 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) != null) {
/*      */         
/*  321 */         if (curr == k)
/*  322 */           return addToValue(pos, incr); 
/*  323 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  324 */           if (curr == k)
/*  325 */             return addToValue(pos, incr); 
/*      */         } 
/*      */       } 
/*  328 */     }  this.key[pos] = k;
/*  329 */     this.value[pos] = this.defRetValue + incr;
/*  330 */     if (this.size++ >= this.maxFill) {
/*  331 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  334 */     return this.defRetValue;
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
/*  347 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  349 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  351 */         if ((curr = key[pos]) == null) {
/*  352 */           key[last] = null;
/*      */           return;
/*      */         } 
/*  355 */         int slot = HashCommon.mix(System.identityHashCode(curr)) & this.mask;
/*  356 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  358 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  360 */       key[last] = curr;
/*  361 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int removeInt(Object k) {
/*  367 */     if (k == null) {
/*  368 */       if (this.containsNullKey)
/*  369 */         return removeNullEntry(); 
/*  370 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  373 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  376 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  377 */       return this.defRetValue; 
/*  378 */     if (k == curr)
/*  379 */       return removeEntry(pos); 
/*      */     while (true) {
/*  381 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  382 */         return this.defRetValue; 
/*  383 */       if (k == curr) {
/*  384 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getInt(Object k) {
/*  390 */     if (k == null) {
/*  391 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  393 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  396 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  397 */       return this.defRetValue; 
/*  398 */     if (k == curr) {
/*  399 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  402 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  403 */         return this.defRetValue; 
/*  404 */       if (k == curr) {
/*  405 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  411 */     if (k == null) {
/*  412 */       return this.containsNullKey;
/*      */     }
/*  414 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  417 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  418 */       return false; 
/*  419 */     if (k == curr) {
/*  420 */       return true;
/*      */     }
/*      */     while (true) {
/*  423 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  424 */         return false; 
/*  425 */       if (k == curr)
/*  426 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(int v) {
/*  431 */     int[] value = this.value;
/*  432 */     K[] key = this.key;
/*  433 */     if (this.containsNullKey && value[this.n] == v)
/*  434 */       return true; 
/*  435 */     for (int i = this.n; i-- != 0;) {
/*  436 */       if (key[i] != null && value[i] == v)
/*  437 */         return true; 
/*  438 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getOrDefault(Object k, int defaultValue) {
/*  444 */     if (k == null) {
/*  445 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  447 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  450 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  451 */       return defaultValue; 
/*  452 */     if (k == curr) {
/*  453 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  456 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  457 */         return defaultValue; 
/*  458 */       if (k == curr) {
/*  459 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public int putIfAbsent(K k, int v) {
/*  465 */     int pos = find(k);
/*  466 */     if (pos >= 0)
/*  467 */       return this.value[pos]; 
/*  468 */     insert(-pos - 1, k, v);
/*  469 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, int v) {
/*  475 */     if (k == null) {
/*  476 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  477 */         removeNullEntry();
/*  478 */         return true;
/*      */       } 
/*  480 */       return false;
/*      */     } 
/*      */     
/*  483 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  486 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  487 */       return false; 
/*  488 */     if (k == curr && v == this.value[pos]) {
/*  489 */       removeEntry(pos);
/*  490 */       return true;
/*      */     } 
/*      */     while (true) {
/*  493 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  494 */         return false; 
/*  495 */       if (k == curr && v == this.value[pos]) {
/*  496 */         removeEntry(pos);
/*  497 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(K k, int oldValue, int v) {
/*  504 */     int pos = find(k);
/*  505 */     if (pos < 0 || oldValue != this.value[pos])
/*  506 */       return false; 
/*  507 */     this.value[pos] = v;
/*  508 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public int replace(K k, int v) {
/*  513 */     int pos = find(k);
/*  514 */     if (pos < 0)
/*  515 */       return this.defRetValue; 
/*  516 */     int oldValue = this.value[pos];
/*  517 */     this.value[pos] = v;
/*  518 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public int computeIntIfAbsent(K k, ToIntFunction<? super K> mappingFunction) {
/*  523 */     Objects.requireNonNull(mappingFunction);
/*  524 */     int pos = find(k);
/*  525 */     if (pos >= 0)
/*  526 */       return this.value[pos]; 
/*  527 */     int newValue = mappingFunction.applyAsInt(k);
/*  528 */     insert(-pos - 1, k, newValue);
/*  529 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int computeIntIfPresent(K k, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
/*  535 */     Objects.requireNonNull(remappingFunction);
/*  536 */     int pos = find(k);
/*  537 */     if (pos < 0)
/*  538 */       return this.defRetValue; 
/*  539 */     Integer newValue = remappingFunction.apply(k, Integer.valueOf(this.value[pos]));
/*  540 */     if (newValue == null) {
/*  541 */       if (k == null) {
/*  542 */         removeNullEntry();
/*      */       } else {
/*  544 */         removeEntry(pos);
/*  545 */       }  return this.defRetValue;
/*      */     } 
/*  547 */     this.value[pos] = newValue.intValue(); return newValue.intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int computeInt(K k, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
/*  553 */     Objects.requireNonNull(remappingFunction);
/*  554 */     int pos = find(k);
/*  555 */     Integer newValue = remappingFunction.apply(k, (pos >= 0) ? Integer.valueOf(this.value[pos]) : null);
/*  556 */     if (newValue == null) {
/*  557 */       if (pos >= 0)
/*  558 */         if (k == null) {
/*  559 */           removeNullEntry();
/*      */         } else {
/*  561 */           removeEntry(pos);
/*      */         }  
/*  563 */       return this.defRetValue;
/*      */     } 
/*  565 */     int newVal = newValue.intValue();
/*  566 */     if (pos < 0) {
/*  567 */       insert(-pos - 1, k, newVal);
/*  568 */       return newVal;
/*      */     } 
/*  570 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int mergeInt(K k, int v, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
/*  576 */     Objects.requireNonNull(remappingFunction);
/*  577 */     int pos = find(k);
/*  578 */     if (pos < 0) {
/*  579 */       insert(-pos - 1, k, v);
/*  580 */       return v;
/*      */     } 
/*  582 */     Integer newValue = remappingFunction.apply(Integer.valueOf(this.value[pos]), Integer.valueOf(v));
/*  583 */     if (newValue == null) {
/*  584 */       if (k == null) {
/*  585 */         removeNullEntry();
/*      */       } else {
/*  587 */         removeEntry(pos);
/*  588 */       }  return this.defRetValue;
/*      */     } 
/*  590 */     this.value[pos] = newValue.intValue(); return newValue.intValue();
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
/*  601 */     if (this.size == 0)
/*      */       return; 
/*  603 */     this.size = 0;
/*  604 */     this.containsNullKey = false;
/*  605 */     Arrays.fill((Object[])this.key, (Object)null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  609 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  613 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Reference2IntMap.Entry<K>, Map.Entry<K, Integer>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  625 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public K getKey() {
/*  631 */       return Reference2IntOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public int getIntValue() {
/*  635 */       return Reference2IntOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public int setValue(int v) {
/*  639 */       int oldValue = Reference2IntOpenHashMap.this.value[this.index];
/*  640 */       Reference2IntOpenHashMap.this.value[this.index] = v;
/*  641 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer getValue() {
/*  651 */       return Integer.valueOf(Reference2IntOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer setValue(Integer v) {
/*  661 */       return Integer.valueOf(setValue(v.intValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  666 */       if (!(o instanceof Map.Entry))
/*  667 */         return false; 
/*  668 */       Map.Entry<K, Integer> e = (Map.Entry<K, Integer>)o;
/*  669 */       return (Reference2IntOpenHashMap.this.key[this.index] == e.getKey() && Reference2IntOpenHashMap.this.value[this.index] == ((Integer)e.getValue()).intValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  673 */       return System.identityHashCode(Reference2IntOpenHashMap.this.key[this.index]) ^ Reference2IntOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/*  677 */       return (new StringBuilder()).append(Reference2IntOpenHashMap.this.key[this.index]).append("=>").append(Reference2IntOpenHashMap.this.value[this.index]).toString();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  687 */     int pos = Reference2IntOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  694 */     int last = -1;
/*      */     
/*  696 */     int c = Reference2IntOpenHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  700 */     boolean mustReturnNullKey = Reference2IntOpenHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     ReferenceArrayList<K> wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  707 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  710 */       if (!hasNext())
/*  711 */         throw new NoSuchElementException(); 
/*  712 */       this.c--;
/*  713 */       if (this.mustReturnNullKey) {
/*  714 */         this.mustReturnNullKey = false;
/*  715 */         return this.last = Reference2IntOpenHashMap.this.n;
/*      */       } 
/*  717 */       K[] key = Reference2IntOpenHashMap.this.key;
/*      */       while (true) {
/*  719 */         if (--this.pos < 0) {
/*      */           
/*  721 */           this.last = Integer.MIN_VALUE;
/*  722 */           K k = this.wrapped.get(-this.pos - 1);
/*  723 */           int p = HashCommon.mix(System.identityHashCode(k)) & Reference2IntOpenHashMap.this.mask;
/*  724 */           while (k != key[p])
/*  725 */             p = p + 1 & Reference2IntOpenHashMap.this.mask; 
/*  726 */           return p;
/*      */         } 
/*  728 */         if (key[this.pos] != null) {
/*  729 */           return this.last = this.pos;
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
/*  743 */       K[] key = Reference2IntOpenHashMap.this.key; while (true) {
/*      */         K curr; int last;
/*  745 */         pos = (last = pos) + 1 & Reference2IntOpenHashMap.this.mask;
/*      */         while (true) {
/*  747 */           if ((curr = key[pos]) == null) {
/*  748 */             key[last] = null;
/*      */             return;
/*      */           } 
/*  751 */           int slot = HashCommon.mix(System.identityHashCode(curr)) & Reference2IntOpenHashMap.this.mask;
/*  752 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  754 */           pos = pos + 1 & Reference2IntOpenHashMap.this.mask;
/*      */         } 
/*  756 */         if (pos < last) {
/*  757 */           if (this.wrapped == null)
/*  758 */             this.wrapped = new ReferenceArrayList<>(2); 
/*  759 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  761 */         key[last] = curr;
/*  762 */         Reference2IntOpenHashMap.this.value[last] = Reference2IntOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  766 */       if (this.last == -1)
/*  767 */         throw new IllegalStateException(); 
/*  768 */       if (this.last == Reference2IntOpenHashMap.this.n) {
/*  769 */         Reference2IntOpenHashMap.this.containsNullKey = false;
/*  770 */         Reference2IntOpenHashMap.this.key[Reference2IntOpenHashMap.this.n] = null;
/*  771 */       } else if (this.pos >= 0) {
/*  772 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  775 */         Reference2IntOpenHashMap.this.removeInt(this.wrapped.set(-this.pos - 1, null));
/*  776 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  779 */       Reference2IntOpenHashMap.this.size--;
/*  780 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  785 */       int i = n;
/*  786 */       while (i-- != 0 && hasNext())
/*  787 */         nextEntry(); 
/*  788 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Reference2IntMap.Entry<K>> { private Reference2IntOpenHashMap<K>.MapEntry entry;
/*      */     
/*      */     public Reference2IntOpenHashMap<K>.MapEntry next() {
/*  795 */       return this.entry = new Reference2IntOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  799 */       super.remove();
/*  800 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Reference2IntMap.Entry<K>> { private FastEntryIterator() {
/*  804 */       this.entry = new Reference2IntOpenHashMap.MapEntry();
/*      */     } private final Reference2IntOpenHashMap<K>.MapEntry entry;
/*      */     public Reference2IntOpenHashMap<K>.MapEntry next() {
/*  807 */       this.entry.index = nextEntry();
/*  808 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Reference2IntMap.Entry<K>> implements Reference2IntMap.FastEntrySet<K> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Reference2IntMap.Entry<K>> iterator() {
/*  814 */       return new Reference2IntOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Reference2IntMap.Entry<K>> fastIterator() {
/*  818 */       return new Reference2IntOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  823 */       if (!(o instanceof Map.Entry))
/*  824 */         return false; 
/*  825 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  826 */       if (e.getValue() == null || !(e.getValue() instanceof Integer))
/*  827 */         return false; 
/*  828 */       K k = (K)e.getKey();
/*  829 */       int v = ((Integer)e.getValue()).intValue();
/*  830 */       if (k == null) {
/*  831 */         return (Reference2IntOpenHashMap.this.containsNullKey && Reference2IntOpenHashMap.this.value[Reference2IntOpenHashMap.this.n] == v);
/*      */       }
/*  833 */       K[] key = Reference2IntOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  836 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2IntOpenHashMap.this.mask]) == null)
/*      */       {
/*  838 */         return false; } 
/*  839 */       if (k == curr) {
/*  840 */         return (Reference2IntOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  843 */         if ((curr = key[pos = pos + 1 & Reference2IntOpenHashMap.this.mask]) == null)
/*  844 */           return false; 
/*  845 */         if (k == curr) {
/*  846 */           return (Reference2IntOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  852 */       if (!(o instanceof Map.Entry))
/*  853 */         return false; 
/*  854 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  855 */       if (e.getValue() == null || !(e.getValue() instanceof Integer))
/*  856 */         return false; 
/*  857 */       K k = (K)e.getKey();
/*  858 */       int v = ((Integer)e.getValue()).intValue();
/*  859 */       if (k == null) {
/*  860 */         if (Reference2IntOpenHashMap.this.containsNullKey && Reference2IntOpenHashMap.this.value[Reference2IntOpenHashMap.this.n] == v) {
/*  861 */           Reference2IntOpenHashMap.this.removeNullEntry();
/*  862 */           return true;
/*      */         } 
/*  864 */         return false;
/*      */       } 
/*      */       
/*  867 */       K[] key = Reference2IntOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  870 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2IntOpenHashMap.this.mask]) == null)
/*      */       {
/*  872 */         return false; } 
/*  873 */       if (curr == k) {
/*  874 */         if (Reference2IntOpenHashMap.this.value[pos] == v) {
/*  875 */           Reference2IntOpenHashMap.this.removeEntry(pos);
/*  876 */           return true;
/*      */         } 
/*  878 */         return false;
/*      */       } 
/*      */       while (true) {
/*  881 */         if ((curr = key[pos = pos + 1 & Reference2IntOpenHashMap.this.mask]) == null)
/*  882 */           return false; 
/*  883 */         if (curr == k && 
/*  884 */           Reference2IntOpenHashMap.this.value[pos] == v) {
/*  885 */           Reference2IntOpenHashMap.this.removeEntry(pos);
/*  886 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  893 */       return Reference2IntOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  897 */       Reference2IntOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Reference2IntMap.Entry<K>> consumer) {
/*  902 */       if (Reference2IntOpenHashMap.this.containsNullKey)
/*  903 */         consumer.accept(new AbstractReference2IntMap.BasicEntry<>(Reference2IntOpenHashMap.this.key[Reference2IntOpenHashMap.this.n], Reference2IntOpenHashMap.this.value[Reference2IntOpenHashMap.this.n])); 
/*  904 */       for (int pos = Reference2IntOpenHashMap.this.n; pos-- != 0;) {
/*  905 */         if (Reference2IntOpenHashMap.this.key[pos] != null)
/*  906 */           consumer.accept(new AbstractReference2IntMap.BasicEntry<>(Reference2IntOpenHashMap.this.key[pos], Reference2IntOpenHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Reference2IntMap.Entry<K>> consumer) {
/*  911 */       AbstractReference2IntMap.BasicEntry<K> entry = new AbstractReference2IntMap.BasicEntry<>();
/*  912 */       if (Reference2IntOpenHashMap.this.containsNullKey) {
/*  913 */         entry.key = Reference2IntOpenHashMap.this.key[Reference2IntOpenHashMap.this.n];
/*  914 */         entry.value = Reference2IntOpenHashMap.this.value[Reference2IntOpenHashMap.this.n];
/*  915 */         consumer.accept(entry);
/*      */       } 
/*  917 */       for (int pos = Reference2IntOpenHashMap.this.n; pos-- != 0;) {
/*  918 */         if (Reference2IntOpenHashMap.this.key[pos] != null) {
/*  919 */           entry.key = Reference2IntOpenHashMap.this.key[pos];
/*  920 */           entry.value = Reference2IntOpenHashMap.this.value[pos];
/*  921 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Reference2IntMap.FastEntrySet<K> reference2IntEntrySet() {
/*  927 */     if (this.entries == null)
/*  928 */       this.entries = new MapEntrySet(); 
/*  929 */     return this.entries;
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
/*  946 */       return Reference2IntOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractReferenceSet<K> { private KeySet() {}
/*      */     
/*      */     public ObjectIterator<K> iterator() {
/*  952 */       return new Reference2IntOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/*  957 */       if (Reference2IntOpenHashMap.this.containsNullKey)
/*  958 */         consumer.accept(Reference2IntOpenHashMap.this.key[Reference2IntOpenHashMap.this.n]); 
/*  959 */       for (int pos = Reference2IntOpenHashMap.this.n; pos-- != 0; ) {
/*  960 */         K k = Reference2IntOpenHashMap.this.key[pos];
/*  961 */         if (k != null)
/*  962 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  967 */       return Reference2IntOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(Object k) {
/*  971 */       return Reference2IntOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(Object k) {
/*  975 */       int oldSize = Reference2IntOpenHashMap.this.size;
/*  976 */       Reference2IntOpenHashMap.this.removeInt(k);
/*  977 */       return (Reference2IntOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  981 */       Reference2IntOpenHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ReferenceSet<K> keySet() {
/*  986 */     if (this.keys == null)
/*  987 */       this.keys = new KeySet(); 
/*  988 */     return this.keys;
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
/* 1005 */       return Reference2IntOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public IntCollection values() {
/* 1010 */     if (this.values == null)
/* 1011 */       this.values = (IntCollection)new AbstractIntCollection()
/*      */         {
/*      */           public IntIterator iterator() {
/* 1014 */             return new Reference2IntOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1018 */             return Reference2IntOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(int v) {
/* 1022 */             return Reference2IntOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1026 */             Reference2IntOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(IntConsumer consumer)
/*      */           {
/* 1031 */             if (Reference2IntOpenHashMap.this.containsNullKey)
/* 1032 */               consumer.accept(Reference2IntOpenHashMap.this.value[Reference2IntOpenHashMap.this.n]); 
/* 1033 */             for (int pos = Reference2IntOpenHashMap.this.n; pos-- != 0;) {
/* 1034 */               if (Reference2IntOpenHashMap.this.key[pos] != null)
/* 1035 */                 consumer.accept(Reference2IntOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1038 */     return this.values;
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
/* 1055 */     return trim(this.size);
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
/* 1079 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1080 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1081 */       return true; 
/*      */     try {
/* 1083 */       rehash(l);
/* 1084 */     } catch (OutOfMemoryError cantDoIt) {
/* 1085 */       return false;
/*      */     } 
/* 1087 */     return true;
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
/* 1103 */     K[] key = this.key;
/* 1104 */     int[] value = this.value;
/* 1105 */     int mask = newN - 1;
/* 1106 */     K[] newKey = (K[])new Object[newN + 1];
/* 1107 */     int[] newValue = new int[newN + 1];
/* 1108 */     int i = this.n;
/* 1109 */     for (int j = realSize(); j-- != 0; ) {
/* 1110 */       while (key[--i] == null); int pos;
/* 1111 */       if (newKey[pos = HashCommon.mix(System.identityHashCode(key[i])) & mask] != null)
/*      */       {
/* 1113 */         while (newKey[pos = pos + 1 & mask] != null); } 
/* 1114 */       newKey[pos] = key[i];
/* 1115 */       newValue[pos] = value[i];
/*      */     } 
/* 1117 */     newValue[newN] = value[this.n];
/* 1118 */     this.n = newN;
/* 1119 */     this.mask = mask;
/* 1120 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1121 */     this.key = newKey;
/* 1122 */     this.value = newValue;
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
/*      */   public Reference2IntOpenHashMap<K> clone() {
/*      */     Reference2IntOpenHashMap<K> c;
/*      */     try {
/* 1139 */       c = (Reference2IntOpenHashMap<K>)super.clone();
/* 1140 */     } catch (CloneNotSupportedException cantHappen) {
/* 1141 */       throw new InternalError();
/*      */     } 
/* 1143 */     c.keys = null;
/* 1144 */     c.values = null;
/* 1145 */     c.entries = null;
/* 1146 */     c.containsNullKey = this.containsNullKey;
/* 1147 */     c.key = (K[])this.key.clone();
/* 1148 */     c.value = (int[])this.value.clone();
/* 1149 */     return c;
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
/* 1162 */     int h = 0;
/* 1163 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1164 */       while (this.key[i] == null)
/* 1165 */         i++; 
/* 1166 */       if (this != this.key[i])
/* 1167 */         t = System.identityHashCode(this.key[i]); 
/* 1168 */       t ^= this.value[i];
/* 1169 */       h += t;
/* 1170 */       i++;
/*      */     } 
/*      */     
/* 1173 */     if (this.containsNullKey)
/* 1174 */       h += this.value[this.n]; 
/* 1175 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1178 */     K[] key = this.key;
/* 1179 */     int[] value = this.value;
/* 1180 */     MapIterator i = new MapIterator();
/* 1181 */     s.defaultWriteObject();
/* 1182 */     for (int j = this.size; j-- != 0; ) {
/* 1183 */       int e = i.nextEntry();
/* 1184 */       s.writeObject(key[e]);
/* 1185 */       s.writeInt(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1190 */     s.defaultReadObject();
/* 1191 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1192 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1193 */     this.mask = this.n - 1;
/* 1194 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1195 */     int[] value = this.value = new int[this.n + 1];
/*      */ 
/*      */     
/* 1198 */     for (int i = this.size; i-- != 0; ) {
/* 1199 */       int pos; K k = (K)s.readObject();
/* 1200 */       int v = s.readInt();
/* 1201 */       if (k == null) {
/* 1202 */         pos = this.n;
/* 1203 */         this.containsNullKey = true;
/*      */       } else {
/* 1205 */         pos = HashCommon.mix(System.identityHashCode(k)) & this.mask;
/* 1206 */         while (key[pos] != null)
/* 1207 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1209 */       key[pos] = k;
/* 1210 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Reference2IntOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */