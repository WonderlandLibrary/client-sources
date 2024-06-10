/*      */ package it.unimi.dsi.fastutil.shorts;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*      */ import it.unimi.dsi.fastutil.objects.ReferenceCollection;
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
/*      */ public class Short2ReferenceOpenHashMap<V>
/*      */   extends AbstractShort2ReferenceMap<V>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient short[] key;
/*      */   protected transient V[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Short2ReferenceMap.FastEntrySet<V> entries;
/*      */   protected transient ShortSet keys;
/*      */   protected transient ReferenceCollection<V> values;
/*      */   
/*      */   public Short2ReferenceOpenHashMap(int expected, float f) {
/*   99 */     if (f <= 0.0F || f > 1.0F)
/*  100 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  101 */     if (expected < 0)
/*  102 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  103 */     this.f = f;
/*  104 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  105 */     this.mask = this.n - 1;
/*  106 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  107 */     this.key = new short[this.n + 1];
/*  108 */     this.value = (V[])new Object[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2ReferenceOpenHashMap(int expected) {
/*  117 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2ReferenceOpenHashMap() {
/*  125 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2ReferenceOpenHashMap(Map<? extends Short, ? extends V> m, float f) {
/*  136 */     this(m.size(), f);
/*  137 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2ReferenceOpenHashMap(Map<? extends Short, ? extends V> m) {
/*  147 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2ReferenceOpenHashMap(Short2ReferenceMap<V> m, float f) {
/*  158 */     this(m.size(), f);
/*  159 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2ReferenceOpenHashMap(Short2ReferenceMap<V> m) {
/*  169 */     this(m, 0.75F);
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
/*      */   public Short2ReferenceOpenHashMap(short[] k, V[] v, float f) {
/*  184 */     this(k.length, f);
/*  185 */     if (k.length != v.length) {
/*  186 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  188 */     for (int i = 0; i < k.length; i++) {
/*  189 */       put(k[i], v[i]);
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
/*      */   public Short2ReferenceOpenHashMap(short[] k, V[] v) {
/*  203 */     this(k, v, 0.75F);
/*      */   }
/*      */   private int realSize() {
/*  206 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  209 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  210 */     if (needed > this.n)
/*  211 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  214 */     int needed = (int)Math.min(1073741824L, 
/*  215 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  216 */     if (needed > this.n)
/*  217 */       rehash(needed); 
/*      */   }
/*      */   private V removeEntry(int pos) {
/*  220 */     V oldValue = this.value[pos];
/*  221 */     this.value[pos] = null;
/*  222 */     this.size--;
/*  223 */     shiftKeys(pos);
/*  224 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  225 */       rehash(this.n / 2); 
/*  226 */     return oldValue;
/*      */   }
/*      */   private V removeNullEntry() {
/*  229 */     this.containsNullKey = false;
/*  230 */     V oldValue = this.value[this.n];
/*  231 */     this.value[this.n] = null;
/*  232 */     this.size--;
/*  233 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  234 */       rehash(this.n / 2); 
/*  235 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Short, ? extends V> m) {
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
/*      */   private void insert(int pos, short k, V v) {
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
/*      */   public V put(short k, V v) {
/*  278 */     int pos = find(k);
/*  279 */     if (pos < 0) {
/*  280 */       insert(-pos - 1, k, v);
/*  281 */       return this.defRetValue;
/*      */     } 
/*  283 */     V oldValue = this.value[pos];
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
/*  304 */           this.value[last] = null;
/*      */           return;
/*      */         } 
/*  307 */         int slot = HashCommon.mix(curr) & this.mask;
/*  308 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  310 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  312 */       key[last] = curr;
/*  313 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public V remove(short k) {
/*  319 */     if (k == 0) {
/*  320 */       if (this.containsNullKey)
/*  321 */         return removeNullEntry(); 
/*  322 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  325 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  328 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  329 */       return this.defRetValue; 
/*  330 */     if (k == curr)
/*  331 */       return removeEntry(pos); 
/*      */     while (true) {
/*  333 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  334 */         return this.defRetValue; 
/*  335 */       if (k == curr) {
/*  336 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public V get(short k) {
/*  342 */     if (k == 0) {
/*  343 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  345 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  348 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  349 */       return this.defRetValue; 
/*  350 */     if (k == curr) {
/*  351 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  354 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  355 */         return this.defRetValue; 
/*  356 */       if (k == curr) {
/*  357 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(short k) {
/*  363 */     if (k == 0) {
/*  364 */       return this.containsNullKey;
/*      */     }
/*  366 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  369 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  370 */       return false; 
/*  371 */     if (k == curr) {
/*  372 */       return true;
/*      */     }
/*      */     while (true) {
/*  375 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  376 */         return false; 
/*  377 */       if (k == curr)
/*  378 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(Object v) {
/*  383 */     V[] value = this.value;
/*  384 */     short[] key = this.key;
/*  385 */     if (this.containsNullKey && value[this.n] == v)
/*  386 */       return true; 
/*  387 */     for (int i = this.n; i-- != 0;) {
/*  388 */       if (key[i] != 0 && value[i] == v)
/*  389 */         return true; 
/*  390 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V getOrDefault(short k, V defaultValue) {
/*  396 */     if (k == 0) {
/*  397 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  399 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  402 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  403 */       return defaultValue; 
/*  404 */     if (k == curr) {
/*  405 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  408 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  409 */         return defaultValue; 
/*  410 */       if (k == curr) {
/*  411 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public V putIfAbsent(short k, V v) {
/*  417 */     int pos = find(k);
/*  418 */     if (pos >= 0)
/*  419 */       return this.value[pos]; 
/*  420 */     insert(-pos - 1, k, v);
/*  421 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(short k, Object v) {
/*  427 */     if (k == 0) {
/*  428 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  429 */         removeNullEntry();
/*  430 */         return true;
/*      */       } 
/*  432 */       return false;
/*      */     } 
/*      */     
/*  435 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  438 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  439 */       return false; 
/*  440 */     if (k == curr && v == this.value[pos]) {
/*  441 */       removeEntry(pos);
/*  442 */       return true;
/*      */     } 
/*      */     while (true) {
/*  445 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  446 */         return false; 
/*  447 */       if (k == curr && v == this.value[pos]) {
/*  448 */         removeEntry(pos);
/*  449 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(short k, V oldValue, V v) {
/*  456 */     int pos = find(k);
/*  457 */     if (pos < 0 || oldValue != this.value[pos])
/*  458 */       return false; 
/*  459 */     this.value[pos] = v;
/*  460 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public V replace(short k, V v) {
/*  465 */     int pos = find(k);
/*  466 */     if (pos < 0)
/*  467 */       return this.defRetValue; 
/*  468 */     V oldValue = this.value[pos];
/*  469 */     this.value[pos] = v;
/*  470 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public V computeIfAbsent(short k, IntFunction<? extends V> mappingFunction) {
/*  475 */     Objects.requireNonNull(mappingFunction);
/*  476 */     int pos = find(k);
/*  477 */     if (pos >= 0)
/*  478 */       return this.value[pos]; 
/*  479 */     V newValue = mappingFunction.apply(k);
/*  480 */     insert(-pos - 1, k, newValue);
/*  481 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V computeIfPresent(short k, BiFunction<? super Short, ? super V, ? extends V> remappingFunction) {
/*  487 */     Objects.requireNonNull(remappingFunction);
/*  488 */     int pos = find(k);
/*  489 */     if (pos < 0)
/*  490 */       return this.defRetValue; 
/*  491 */     V newValue = remappingFunction.apply(Short.valueOf(k), this.value[pos]);
/*  492 */     if (newValue == null) {
/*  493 */       if (k == 0) {
/*  494 */         removeNullEntry();
/*      */       } else {
/*  496 */         removeEntry(pos);
/*  497 */       }  return this.defRetValue;
/*      */     } 
/*  499 */     this.value[pos] = newValue; return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V compute(short k, BiFunction<? super Short, ? super V, ? extends V> remappingFunction) {
/*  505 */     Objects.requireNonNull(remappingFunction);
/*  506 */     int pos = find(k);
/*  507 */     V newValue = remappingFunction.apply(Short.valueOf(k), (pos >= 0) ? this.value[pos] : null);
/*  508 */     if (newValue == null) {
/*  509 */       if (pos >= 0)
/*  510 */         if (k == 0) {
/*  511 */           removeNullEntry();
/*      */         } else {
/*  513 */           removeEntry(pos);
/*      */         }  
/*  515 */       return this.defRetValue;
/*      */     } 
/*  517 */     V newVal = newValue;
/*  518 */     if (pos < 0) {
/*  519 */       insert(-pos - 1, k, newVal);
/*  520 */       return newVal;
/*      */     } 
/*  522 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V merge(short k, V v, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/*  528 */     Objects.requireNonNull(remappingFunction);
/*  529 */     int pos = find(k);
/*  530 */     if (pos < 0 || this.value[pos] == null) {
/*  531 */       if (v == null)
/*  532 */         return this.defRetValue; 
/*  533 */       insert(-pos - 1, k, v);
/*  534 */       return v;
/*      */     } 
/*  536 */     V newValue = remappingFunction.apply(this.value[pos], v);
/*  537 */     if (newValue == null) {
/*  538 */       if (k == 0) {
/*  539 */         removeNullEntry();
/*      */       } else {
/*  541 */         removeEntry(pos);
/*  542 */       }  return this.defRetValue;
/*      */     } 
/*  544 */     this.value[pos] = newValue; return newValue;
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
/*  555 */     if (this.size == 0)
/*      */       return; 
/*  557 */     this.size = 0;
/*  558 */     this.containsNullKey = false;
/*  559 */     Arrays.fill(this.key, (short)0);
/*  560 */     Arrays.fill((Object[])this.value, (Object)null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  564 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  568 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Short2ReferenceMap.Entry<V>, Map.Entry<Short, V>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  580 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public short getShortKey() {
/*  586 */       return Short2ReferenceOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public V getValue() {
/*  590 */       return Short2ReferenceOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public V setValue(V v) {
/*  594 */       V oldValue = Short2ReferenceOpenHashMap.this.value[this.index];
/*  595 */       Short2ReferenceOpenHashMap.this.value[this.index] = v;
/*  596 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Short getKey() {
/*  606 */       return Short.valueOf(Short2ReferenceOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  611 */       if (!(o instanceof Map.Entry))
/*  612 */         return false; 
/*  613 */       Map.Entry<Short, V> e = (Map.Entry<Short, V>)o;
/*  614 */       return (Short2ReferenceOpenHashMap.this.key[this.index] == ((Short)e.getKey()).shortValue() && Short2ReferenceOpenHashMap.this.value[this.index] == e.getValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  618 */       return Short2ReferenceOpenHashMap.this.key[this.index] ^ ((Short2ReferenceOpenHashMap.this.value[this.index] == null) ? 0 : System.identityHashCode(Short2ReferenceOpenHashMap.this.value[this.index]));
/*      */     }
/*      */     
/*      */     public String toString() {
/*  622 */       return Short2ReferenceOpenHashMap.this.key[this.index] + "=>" + Short2ReferenceOpenHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  632 */     int pos = Short2ReferenceOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  639 */     int last = -1;
/*      */     
/*  641 */     int c = Short2ReferenceOpenHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  645 */     boolean mustReturnNullKey = Short2ReferenceOpenHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     ShortArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  652 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  655 */       if (!hasNext())
/*  656 */         throw new NoSuchElementException(); 
/*  657 */       this.c--;
/*  658 */       if (this.mustReturnNullKey) {
/*  659 */         this.mustReturnNullKey = false;
/*  660 */         return this.last = Short2ReferenceOpenHashMap.this.n;
/*      */       } 
/*  662 */       short[] key = Short2ReferenceOpenHashMap.this.key;
/*      */       while (true) {
/*  664 */         if (--this.pos < 0) {
/*      */           
/*  666 */           this.last = Integer.MIN_VALUE;
/*  667 */           short k = this.wrapped.getShort(-this.pos - 1);
/*  668 */           int p = HashCommon.mix(k) & Short2ReferenceOpenHashMap.this.mask;
/*  669 */           while (k != key[p])
/*  670 */             p = p + 1 & Short2ReferenceOpenHashMap.this.mask; 
/*  671 */           return p;
/*      */         } 
/*  673 */         if (key[this.pos] != 0) {
/*  674 */           return this.last = this.pos;
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
/*  688 */       short[] key = Short2ReferenceOpenHashMap.this.key; while (true) {
/*      */         short curr; int last;
/*  690 */         pos = (last = pos) + 1 & Short2ReferenceOpenHashMap.this.mask;
/*      */         while (true) {
/*  692 */           if ((curr = key[pos]) == 0) {
/*  693 */             key[last] = 0;
/*  694 */             Short2ReferenceOpenHashMap.this.value[last] = null;
/*      */             return;
/*      */           } 
/*  697 */           int slot = HashCommon.mix(curr) & Short2ReferenceOpenHashMap.this.mask;
/*  698 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  700 */           pos = pos + 1 & Short2ReferenceOpenHashMap.this.mask;
/*      */         } 
/*  702 */         if (pos < last) {
/*  703 */           if (this.wrapped == null)
/*  704 */             this.wrapped = new ShortArrayList(2); 
/*  705 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  707 */         key[last] = curr;
/*  708 */         Short2ReferenceOpenHashMap.this.value[last] = Short2ReferenceOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  712 */       if (this.last == -1)
/*  713 */         throw new IllegalStateException(); 
/*  714 */       if (this.last == Short2ReferenceOpenHashMap.this.n) {
/*  715 */         Short2ReferenceOpenHashMap.this.containsNullKey = false;
/*  716 */         Short2ReferenceOpenHashMap.this.value[Short2ReferenceOpenHashMap.this.n] = null;
/*  717 */       } else if (this.pos >= 0) {
/*  718 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  721 */         Short2ReferenceOpenHashMap.this.remove(this.wrapped.getShort(-this.pos - 1));
/*  722 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  725 */       Short2ReferenceOpenHashMap.this.size--;
/*  726 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  731 */       int i = n;
/*  732 */       while (i-- != 0 && hasNext())
/*  733 */         nextEntry(); 
/*  734 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Short2ReferenceMap.Entry<V>> { private Short2ReferenceOpenHashMap<V>.MapEntry entry;
/*      */     
/*      */     public Short2ReferenceOpenHashMap<V>.MapEntry next() {
/*  741 */       return this.entry = new Short2ReferenceOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  745 */       super.remove();
/*  746 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Short2ReferenceMap.Entry<V>> { private FastEntryIterator() {
/*  750 */       this.entry = new Short2ReferenceOpenHashMap.MapEntry();
/*      */     } private final Short2ReferenceOpenHashMap<V>.MapEntry entry;
/*      */     public Short2ReferenceOpenHashMap<V>.MapEntry next() {
/*  753 */       this.entry.index = nextEntry();
/*  754 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Short2ReferenceMap.Entry<V>> implements Short2ReferenceMap.FastEntrySet<V> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Short2ReferenceMap.Entry<V>> iterator() {
/*  760 */       return new Short2ReferenceOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Short2ReferenceMap.Entry<V>> fastIterator() {
/*  764 */       return new Short2ReferenceOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  769 */       if (!(o instanceof Map.Entry))
/*  770 */         return false; 
/*  771 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  772 */       if (e.getKey() == null || !(e.getKey() instanceof Short))
/*  773 */         return false; 
/*  774 */       short k = ((Short)e.getKey()).shortValue();
/*  775 */       V v = (V)e.getValue();
/*  776 */       if (k == 0) {
/*  777 */         return (Short2ReferenceOpenHashMap.this.containsNullKey && Short2ReferenceOpenHashMap.this.value[Short2ReferenceOpenHashMap.this.n] == v);
/*      */       }
/*  779 */       short[] key = Short2ReferenceOpenHashMap.this.key;
/*      */       short curr;
/*      */       int pos;
/*  782 */       if ((curr = key[pos = HashCommon.mix(k) & Short2ReferenceOpenHashMap.this.mask]) == 0)
/*  783 */         return false; 
/*  784 */       if (k == curr) {
/*  785 */         return (Short2ReferenceOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  788 */         if ((curr = key[pos = pos + 1 & Short2ReferenceOpenHashMap.this.mask]) == 0)
/*  789 */           return false; 
/*  790 */         if (k == curr) {
/*  791 */           return (Short2ReferenceOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  797 */       if (!(o instanceof Map.Entry))
/*  798 */         return false; 
/*  799 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  800 */       if (e.getKey() == null || !(e.getKey() instanceof Short))
/*  801 */         return false; 
/*  802 */       short k = ((Short)e.getKey()).shortValue();
/*  803 */       V v = (V)e.getValue();
/*  804 */       if (k == 0) {
/*  805 */         if (Short2ReferenceOpenHashMap.this.containsNullKey && Short2ReferenceOpenHashMap.this.value[Short2ReferenceOpenHashMap.this.n] == v) {
/*  806 */           Short2ReferenceOpenHashMap.this.removeNullEntry();
/*  807 */           return true;
/*      */         } 
/*  809 */         return false;
/*      */       } 
/*      */       
/*  812 */       short[] key = Short2ReferenceOpenHashMap.this.key;
/*      */       short curr;
/*      */       int pos;
/*  815 */       if ((curr = key[pos = HashCommon.mix(k) & Short2ReferenceOpenHashMap.this.mask]) == 0)
/*  816 */         return false; 
/*  817 */       if (curr == k) {
/*  818 */         if (Short2ReferenceOpenHashMap.this.value[pos] == v) {
/*  819 */           Short2ReferenceOpenHashMap.this.removeEntry(pos);
/*  820 */           return true;
/*      */         } 
/*  822 */         return false;
/*      */       } 
/*      */       while (true) {
/*  825 */         if ((curr = key[pos = pos + 1 & Short2ReferenceOpenHashMap.this.mask]) == 0)
/*  826 */           return false; 
/*  827 */         if (curr == k && 
/*  828 */           Short2ReferenceOpenHashMap.this.value[pos] == v) {
/*  829 */           Short2ReferenceOpenHashMap.this.removeEntry(pos);
/*  830 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  837 */       return Short2ReferenceOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  841 */       Short2ReferenceOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Short2ReferenceMap.Entry<V>> consumer) {
/*  846 */       if (Short2ReferenceOpenHashMap.this.containsNullKey)
/*  847 */         consumer.accept(new AbstractShort2ReferenceMap.BasicEntry<>(Short2ReferenceOpenHashMap.this.key[Short2ReferenceOpenHashMap.this.n], Short2ReferenceOpenHashMap.this.value[Short2ReferenceOpenHashMap.this.n])); 
/*  848 */       for (int pos = Short2ReferenceOpenHashMap.this.n; pos-- != 0;) {
/*  849 */         if (Short2ReferenceOpenHashMap.this.key[pos] != 0)
/*  850 */           consumer.accept(new AbstractShort2ReferenceMap.BasicEntry<>(Short2ReferenceOpenHashMap.this.key[pos], Short2ReferenceOpenHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Short2ReferenceMap.Entry<V>> consumer) {
/*  855 */       AbstractShort2ReferenceMap.BasicEntry<V> entry = new AbstractShort2ReferenceMap.BasicEntry<>();
/*  856 */       if (Short2ReferenceOpenHashMap.this.containsNullKey) {
/*  857 */         entry.key = Short2ReferenceOpenHashMap.this.key[Short2ReferenceOpenHashMap.this.n];
/*  858 */         entry.value = Short2ReferenceOpenHashMap.this.value[Short2ReferenceOpenHashMap.this.n];
/*  859 */         consumer.accept(entry);
/*      */       } 
/*  861 */       for (int pos = Short2ReferenceOpenHashMap.this.n; pos-- != 0;) {
/*  862 */         if (Short2ReferenceOpenHashMap.this.key[pos] != 0) {
/*  863 */           entry.key = Short2ReferenceOpenHashMap.this.key[pos];
/*  864 */           entry.value = Short2ReferenceOpenHashMap.this.value[pos];
/*  865 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Short2ReferenceMap.FastEntrySet<V> short2ReferenceEntrySet() {
/*  871 */     if (this.entries == null)
/*  872 */       this.entries = new MapEntrySet(); 
/*  873 */     return this.entries;
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
/*  890 */       return Short2ReferenceOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractShortSet { private KeySet() {}
/*      */     
/*      */     public ShortIterator iterator() {
/*  896 */       return new Short2ReferenceOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer consumer) {
/*  901 */       if (Short2ReferenceOpenHashMap.this.containsNullKey)
/*  902 */         consumer.accept(Short2ReferenceOpenHashMap.this.key[Short2ReferenceOpenHashMap.this.n]); 
/*  903 */       for (int pos = Short2ReferenceOpenHashMap.this.n; pos-- != 0; ) {
/*  904 */         short k = Short2ReferenceOpenHashMap.this.key[pos];
/*  905 */         if (k != 0)
/*  906 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  911 */       return Short2ReferenceOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(short k) {
/*  915 */       return Short2ReferenceOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(short k) {
/*  919 */       int oldSize = Short2ReferenceOpenHashMap.this.size;
/*  920 */       Short2ReferenceOpenHashMap.this.remove(k);
/*  921 */       return (Short2ReferenceOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  925 */       Short2ReferenceOpenHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ShortSet keySet() {
/*  930 */     if (this.keys == null)
/*  931 */       this.keys = new KeySet(); 
/*  932 */     return this.keys;
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
/*  949 */       return Short2ReferenceOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public ReferenceCollection<V> values() {
/*  954 */     if (this.values == null)
/*  955 */       this.values = (ReferenceCollection<V>)new AbstractReferenceCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/*  958 */             return new Short2ReferenceOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/*  962 */             return Short2ReferenceOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(Object v) {
/*  966 */             return Short2ReferenceOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/*  970 */             Short2ReferenceOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(Consumer<? super V> consumer)
/*      */           {
/*  975 */             if (Short2ReferenceOpenHashMap.this.containsNullKey)
/*  976 */               consumer.accept(Short2ReferenceOpenHashMap.this.value[Short2ReferenceOpenHashMap.this.n]); 
/*  977 */             for (int pos = Short2ReferenceOpenHashMap.this.n; pos-- != 0;) {
/*  978 */               if (Short2ReferenceOpenHashMap.this.key[pos] != 0)
/*  979 */                 consumer.accept(Short2ReferenceOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/*  982 */     return this.values;
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
/*  999 */     return trim(this.size);
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
/* 1023 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1024 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1025 */       return true; 
/*      */     try {
/* 1027 */       rehash(l);
/* 1028 */     } catch (OutOfMemoryError cantDoIt) {
/* 1029 */       return false;
/*      */     } 
/* 1031 */     return true;
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
/* 1047 */     short[] key = this.key;
/* 1048 */     V[] value = this.value;
/* 1049 */     int mask = newN - 1;
/* 1050 */     short[] newKey = new short[newN + 1];
/* 1051 */     V[] newValue = (V[])new Object[newN + 1];
/* 1052 */     int i = this.n;
/* 1053 */     for (int j = realSize(); j-- != 0; ) {
/* 1054 */       while (key[--i] == 0); int pos;
/* 1055 */       if (newKey[pos = HashCommon.mix(key[i]) & mask] != 0)
/* 1056 */         while (newKey[pos = pos + 1 & mask] != 0); 
/* 1057 */       newKey[pos] = key[i];
/* 1058 */       newValue[pos] = value[i];
/*      */     } 
/* 1060 */     newValue[newN] = value[this.n];
/* 1061 */     this.n = newN;
/* 1062 */     this.mask = mask;
/* 1063 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1064 */     this.key = newKey;
/* 1065 */     this.value = newValue;
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
/*      */   public Short2ReferenceOpenHashMap<V> clone() {
/*      */     Short2ReferenceOpenHashMap<V> c;
/*      */     try {
/* 1082 */       c = (Short2ReferenceOpenHashMap<V>)super.clone();
/* 1083 */     } catch (CloneNotSupportedException cantHappen) {
/* 1084 */       throw new InternalError();
/*      */     } 
/* 1086 */     c.keys = null;
/* 1087 */     c.values = null;
/* 1088 */     c.entries = null;
/* 1089 */     c.containsNullKey = this.containsNullKey;
/* 1090 */     c.key = (short[])this.key.clone();
/* 1091 */     c.value = (V[])this.value.clone();
/* 1092 */     return c;
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
/* 1105 */     int h = 0;
/* 1106 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1107 */       while (this.key[i] == 0)
/* 1108 */         i++; 
/* 1109 */       t = this.key[i];
/* 1110 */       if (this != this.value[i])
/* 1111 */         t ^= (this.value[i] == null) ? 0 : System.identityHashCode(this.value[i]); 
/* 1112 */       h += t;
/* 1113 */       i++;
/*      */     } 
/*      */     
/* 1116 */     if (this.containsNullKey)
/* 1117 */       h += (this.value[this.n] == null) ? 0 : System.identityHashCode(this.value[this.n]); 
/* 1118 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1121 */     short[] key = this.key;
/* 1122 */     V[] value = this.value;
/* 1123 */     MapIterator i = new MapIterator();
/* 1124 */     s.defaultWriteObject();
/* 1125 */     for (int j = this.size; j-- != 0; ) {
/* 1126 */       int e = i.nextEntry();
/* 1127 */       s.writeShort(key[e]);
/* 1128 */       s.writeObject(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1133 */     s.defaultReadObject();
/* 1134 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1135 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1136 */     this.mask = this.n - 1;
/* 1137 */     short[] key = this.key = new short[this.n + 1];
/* 1138 */     V[] value = this.value = (V[])new Object[this.n + 1];
/*      */ 
/*      */     
/* 1141 */     for (int i = this.size; i-- != 0; ) {
/* 1142 */       int pos; short k = s.readShort();
/* 1143 */       V v = (V)s.readObject();
/* 1144 */       if (k == 0) {
/* 1145 */         pos = this.n;
/* 1146 */         this.containsNullKey = true;
/*      */       } else {
/* 1148 */         pos = HashCommon.mix(k) & this.mask;
/* 1149 */         while (key[pos] != 0)
/* 1150 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1152 */       key[pos] = k;
/* 1153 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\Short2ReferenceOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */