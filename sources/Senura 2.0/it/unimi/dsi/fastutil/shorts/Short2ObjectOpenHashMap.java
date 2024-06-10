/*      */ package it.unimi.dsi.fastutil.shorts;
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
/*      */ public class Short2ObjectOpenHashMap<V>
/*      */   extends AbstractShort2ObjectMap<V>
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
/*      */   protected transient Short2ObjectMap.FastEntrySet<V> entries;
/*      */   protected transient ShortSet keys;
/*      */   protected transient ObjectCollection<V> values;
/*      */   
/*      */   public Short2ObjectOpenHashMap(int expected, float f) {
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
/*      */   public Short2ObjectOpenHashMap(int expected) {
/*  117 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2ObjectOpenHashMap() {
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
/*      */   public Short2ObjectOpenHashMap(Map<? extends Short, ? extends V> m, float f) {
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
/*      */   public Short2ObjectOpenHashMap(Map<? extends Short, ? extends V> m) {
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
/*      */   public Short2ObjectOpenHashMap(Short2ObjectMap<V> m, float f) {
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
/*      */   public Short2ObjectOpenHashMap(Short2ObjectMap<V> m) {
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
/*      */   public Short2ObjectOpenHashMap(short[] k, V[] v, float f) {
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
/*      */   public Short2ObjectOpenHashMap(short[] k, V[] v) {
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
/*  385 */     if (this.containsNullKey && Objects.equals(value[this.n], v))
/*  386 */       return true; 
/*  387 */     for (int i = this.n; i-- != 0;) {
/*  388 */       if (key[i] != 0 && Objects.equals(value[i], v))
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
/*  428 */       if (this.containsNullKey && Objects.equals(v, this.value[this.n])) {
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
/*  440 */     if (k == curr && Objects.equals(v, this.value[pos])) {
/*  441 */       removeEntry(pos);
/*  442 */       return true;
/*      */     } 
/*      */     while (true) {
/*  445 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  446 */         return false; 
/*  447 */       if (k == curr && Objects.equals(v, this.value[pos])) {
/*  448 */         removeEntry(pos);
/*  449 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(short k, V oldValue, V v) {
/*  456 */     int pos = find(k);
/*  457 */     if (pos < 0 || !Objects.equals(oldValue, this.value[pos]))
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
/*      */     implements Short2ObjectMap.Entry<V>, Map.Entry<Short, V>
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
/*  586 */       return Short2ObjectOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public V getValue() {
/*  590 */       return Short2ObjectOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public V setValue(V v) {
/*  594 */       V oldValue = Short2ObjectOpenHashMap.this.value[this.index];
/*  595 */       Short2ObjectOpenHashMap.this.value[this.index] = v;
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
/*  606 */       return Short.valueOf(Short2ObjectOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  611 */       if (!(o instanceof Map.Entry))
/*  612 */         return false; 
/*  613 */       Map.Entry<Short, V> e = (Map.Entry<Short, V>)o;
/*  614 */       return (Short2ObjectOpenHashMap.this.key[this.index] == ((Short)e.getKey()).shortValue() && 
/*  615 */         Objects.equals(Short2ObjectOpenHashMap.this.value[this.index], e.getValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  619 */       return Short2ObjectOpenHashMap.this.key[this.index] ^ ((Short2ObjectOpenHashMap.this.value[this.index] == null) ? 0 : Short2ObjectOpenHashMap.this.value[this.index].hashCode());
/*      */     }
/*      */     
/*      */     public String toString() {
/*  623 */       return Short2ObjectOpenHashMap.this.key[this.index] + "=>" + Short2ObjectOpenHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  633 */     int pos = Short2ObjectOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  640 */     int last = -1;
/*      */     
/*  642 */     int c = Short2ObjectOpenHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  646 */     boolean mustReturnNullKey = Short2ObjectOpenHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     ShortArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  653 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  656 */       if (!hasNext())
/*  657 */         throw new NoSuchElementException(); 
/*  658 */       this.c--;
/*  659 */       if (this.mustReturnNullKey) {
/*  660 */         this.mustReturnNullKey = false;
/*  661 */         return this.last = Short2ObjectOpenHashMap.this.n;
/*      */       } 
/*  663 */       short[] key = Short2ObjectOpenHashMap.this.key;
/*      */       while (true) {
/*  665 */         if (--this.pos < 0) {
/*      */           
/*  667 */           this.last = Integer.MIN_VALUE;
/*  668 */           short k = this.wrapped.getShort(-this.pos - 1);
/*  669 */           int p = HashCommon.mix(k) & Short2ObjectOpenHashMap.this.mask;
/*  670 */           while (k != key[p])
/*  671 */             p = p + 1 & Short2ObjectOpenHashMap.this.mask; 
/*  672 */           return p;
/*      */         } 
/*  674 */         if (key[this.pos] != 0) {
/*  675 */           return this.last = this.pos;
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
/*  689 */       short[] key = Short2ObjectOpenHashMap.this.key; while (true) {
/*      */         short curr; int last;
/*  691 */         pos = (last = pos) + 1 & Short2ObjectOpenHashMap.this.mask;
/*      */         while (true) {
/*  693 */           if ((curr = key[pos]) == 0) {
/*  694 */             key[last] = 0;
/*  695 */             Short2ObjectOpenHashMap.this.value[last] = null;
/*      */             return;
/*      */           } 
/*  698 */           int slot = HashCommon.mix(curr) & Short2ObjectOpenHashMap.this.mask;
/*  699 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  701 */           pos = pos + 1 & Short2ObjectOpenHashMap.this.mask;
/*      */         } 
/*  703 */         if (pos < last) {
/*  704 */           if (this.wrapped == null)
/*  705 */             this.wrapped = new ShortArrayList(2); 
/*  706 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  708 */         key[last] = curr;
/*  709 */         Short2ObjectOpenHashMap.this.value[last] = Short2ObjectOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  713 */       if (this.last == -1)
/*  714 */         throw new IllegalStateException(); 
/*  715 */       if (this.last == Short2ObjectOpenHashMap.this.n) {
/*  716 */         Short2ObjectOpenHashMap.this.containsNullKey = false;
/*  717 */         Short2ObjectOpenHashMap.this.value[Short2ObjectOpenHashMap.this.n] = null;
/*  718 */       } else if (this.pos >= 0) {
/*  719 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  722 */         Short2ObjectOpenHashMap.this.remove(this.wrapped.getShort(-this.pos - 1));
/*  723 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  726 */       Short2ObjectOpenHashMap.this.size--;
/*  727 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  732 */       int i = n;
/*  733 */       while (i-- != 0 && hasNext())
/*  734 */         nextEntry(); 
/*  735 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Short2ObjectMap.Entry<V>> { private Short2ObjectOpenHashMap<V>.MapEntry entry;
/*      */     
/*      */     public Short2ObjectOpenHashMap<V>.MapEntry next() {
/*  742 */       return this.entry = new Short2ObjectOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  746 */       super.remove();
/*  747 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Short2ObjectMap.Entry<V>> { private FastEntryIterator() {
/*  751 */       this.entry = new Short2ObjectOpenHashMap.MapEntry();
/*      */     } private final Short2ObjectOpenHashMap<V>.MapEntry entry;
/*      */     public Short2ObjectOpenHashMap<V>.MapEntry next() {
/*  754 */       this.entry.index = nextEntry();
/*  755 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Short2ObjectMap.Entry<V>> implements Short2ObjectMap.FastEntrySet<V> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Short2ObjectMap.Entry<V>> iterator() {
/*  761 */       return new Short2ObjectOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Short2ObjectMap.Entry<V>> fastIterator() {
/*  765 */       return new Short2ObjectOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  770 */       if (!(o instanceof Map.Entry))
/*  771 */         return false; 
/*  772 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  773 */       if (e.getKey() == null || !(e.getKey() instanceof Short))
/*  774 */         return false; 
/*  775 */       short k = ((Short)e.getKey()).shortValue();
/*  776 */       V v = (V)e.getValue();
/*  777 */       if (k == 0) {
/*  778 */         return (Short2ObjectOpenHashMap.this.containsNullKey && Objects.equals(Short2ObjectOpenHashMap.this.value[Short2ObjectOpenHashMap.this.n], v));
/*      */       }
/*  780 */       short[] key = Short2ObjectOpenHashMap.this.key;
/*      */       short curr;
/*      */       int pos;
/*  783 */       if ((curr = key[pos = HashCommon.mix(k) & Short2ObjectOpenHashMap.this.mask]) == 0)
/*  784 */         return false; 
/*  785 */       if (k == curr) {
/*  786 */         return Objects.equals(Short2ObjectOpenHashMap.this.value[pos], v);
/*      */       }
/*      */       while (true) {
/*  789 */         if ((curr = key[pos = pos + 1 & Short2ObjectOpenHashMap.this.mask]) == 0)
/*  790 */           return false; 
/*  791 */         if (k == curr) {
/*  792 */           return Objects.equals(Short2ObjectOpenHashMap.this.value[pos], v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  798 */       if (!(o instanceof Map.Entry))
/*  799 */         return false; 
/*  800 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  801 */       if (e.getKey() == null || !(e.getKey() instanceof Short))
/*  802 */         return false; 
/*  803 */       short k = ((Short)e.getKey()).shortValue();
/*  804 */       V v = (V)e.getValue();
/*  805 */       if (k == 0) {
/*  806 */         if (Short2ObjectOpenHashMap.this.containsNullKey && Objects.equals(Short2ObjectOpenHashMap.this.value[Short2ObjectOpenHashMap.this.n], v)) {
/*  807 */           Short2ObjectOpenHashMap.this.removeNullEntry();
/*  808 */           return true;
/*      */         } 
/*  810 */         return false;
/*      */       } 
/*      */       
/*  813 */       short[] key = Short2ObjectOpenHashMap.this.key;
/*      */       short curr;
/*      */       int pos;
/*  816 */       if ((curr = key[pos = HashCommon.mix(k) & Short2ObjectOpenHashMap.this.mask]) == 0)
/*  817 */         return false; 
/*  818 */       if (curr == k) {
/*  819 */         if (Objects.equals(Short2ObjectOpenHashMap.this.value[pos], v)) {
/*  820 */           Short2ObjectOpenHashMap.this.removeEntry(pos);
/*  821 */           return true;
/*      */         } 
/*  823 */         return false;
/*      */       } 
/*      */       while (true) {
/*  826 */         if ((curr = key[pos = pos + 1 & Short2ObjectOpenHashMap.this.mask]) == 0)
/*  827 */           return false; 
/*  828 */         if (curr == k && 
/*  829 */           Objects.equals(Short2ObjectOpenHashMap.this.value[pos], v)) {
/*  830 */           Short2ObjectOpenHashMap.this.removeEntry(pos);
/*  831 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  838 */       return Short2ObjectOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  842 */       Short2ObjectOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Short2ObjectMap.Entry<V>> consumer) {
/*  847 */       if (Short2ObjectOpenHashMap.this.containsNullKey)
/*  848 */         consumer.accept(new AbstractShort2ObjectMap.BasicEntry<>(Short2ObjectOpenHashMap.this.key[Short2ObjectOpenHashMap.this.n], Short2ObjectOpenHashMap.this.value[Short2ObjectOpenHashMap.this.n])); 
/*  849 */       for (int pos = Short2ObjectOpenHashMap.this.n; pos-- != 0;) {
/*  850 */         if (Short2ObjectOpenHashMap.this.key[pos] != 0)
/*  851 */           consumer.accept(new AbstractShort2ObjectMap.BasicEntry<>(Short2ObjectOpenHashMap.this.key[pos], Short2ObjectOpenHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Short2ObjectMap.Entry<V>> consumer) {
/*  856 */       AbstractShort2ObjectMap.BasicEntry<V> entry = new AbstractShort2ObjectMap.BasicEntry<>();
/*  857 */       if (Short2ObjectOpenHashMap.this.containsNullKey) {
/*  858 */         entry.key = Short2ObjectOpenHashMap.this.key[Short2ObjectOpenHashMap.this.n];
/*  859 */         entry.value = Short2ObjectOpenHashMap.this.value[Short2ObjectOpenHashMap.this.n];
/*  860 */         consumer.accept(entry);
/*      */       } 
/*  862 */       for (int pos = Short2ObjectOpenHashMap.this.n; pos-- != 0;) {
/*  863 */         if (Short2ObjectOpenHashMap.this.key[pos] != 0) {
/*  864 */           entry.key = Short2ObjectOpenHashMap.this.key[pos];
/*  865 */           entry.value = Short2ObjectOpenHashMap.this.value[pos];
/*  866 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Short2ObjectMap.FastEntrySet<V> short2ObjectEntrySet() {
/*  872 */     if (this.entries == null)
/*  873 */       this.entries = new MapEntrySet(); 
/*  874 */     return this.entries;
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
/*  891 */       return Short2ObjectOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractShortSet { private KeySet() {}
/*      */     
/*      */     public ShortIterator iterator() {
/*  897 */       return new Short2ObjectOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer consumer) {
/*  902 */       if (Short2ObjectOpenHashMap.this.containsNullKey)
/*  903 */         consumer.accept(Short2ObjectOpenHashMap.this.key[Short2ObjectOpenHashMap.this.n]); 
/*  904 */       for (int pos = Short2ObjectOpenHashMap.this.n; pos-- != 0; ) {
/*  905 */         short k = Short2ObjectOpenHashMap.this.key[pos];
/*  906 */         if (k != 0)
/*  907 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  912 */       return Short2ObjectOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(short k) {
/*  916 */       return Short2ObjectOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(short k) {
/*  920 */       int oldSize = Short2ObjectOpenHashMap.this.size;
/*  921 */       Short2ObjectOpenHashMap.this.remove(k);
/*  922 */       return (Short2ObjectOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  926 */       Short2ObjectOpenHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ShortSet keySet() {
/*  931 */     if (this.keys == null)
/*  932 */       this.keys = new KeySet(); 
/*  933 */     return this.keys;
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
/*  950 */       return Short2ObjectOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectCollection<V> values() {
/*  955 */     if (this.values == null)
/*  956 */       this.values = (ObjectCollection<V>)new AbstractObjectCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/*  959 */             return new Short2ObjectOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/*  963 */             return Short2ObjectOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(Object v) {
/*  967 */             return Short2ObjectOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/*  971 */             Short2ObjectOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(Consumer<? super V> consumer)
/*      */           {
/*  976 */             if (Short2ObjectOpenHashMap.this.containsNullKey)
/*  977 */               consumer.accept(Short2ObjectOpenHashMap.this.value[Short2ObjectOpenHashMap.this.n]); 
/*  978 */             for (int pos = Short2ObjectOpenHashMap.this.n; pos-- != 0;) {
/*  979 */               if (Short2ObjectOpenHashMap.this.key[pos] != 0)
/*  980 */                 consumer.accept(Short2ObjectOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/*  983 */     return this.values;
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
/* 1000 */     return trim(this.size);
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
/* 1024 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1025 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1026 */       return true; 
/*      */     try {
/* 1028 */       rehash(l);
/* 1029 */     } catch (OutOfMemoryError cantDoIt) {
/* 1030 */       return false;
/*      */     } 
/* 1032 */     return true;
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
/* 1048 */     short[] key = this.key;
/* 1049 */     V[] value = this.value;
/* 1050 */     int mask = newN - 1;
/* 1051 */     short[] newKey = new short[newN + 1];
/* 1052 */     V[] newValue = (V[])new Object[newN + 1];
/* 1053 */     int i = this.n;
/* 1054 */     for (int j = realSize(); j-- != 0; ) {
/* 1055 */       while (key[--i] == 0); int pos;
/* 1056 */       if (newKey[pos = HashCommon.mix(key[i]) & mask] != 0)
/* 1057 */         while (newKey[pos = pos + 1 & mask] != 0); 
/* 1058 */       newKey[pos] = key[i];
/* 1059 */       newValue[pos] = value[i];
/*      */     } 
/* 1061 */     newValue[newN] = value[this.n];
/* 1062 */     this.n = newN;
/* 1063 */     this.mask = mask;
/* 1064 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1065 */     this.key = newKey;
/* 1066 */     this.value = newValue;
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
/*      */   public Short2ObjectOpenHashMap<V> clone() {
/*      */     Short2ObjectOpenHashMap<V> c;
/*      */     try {
/* 1083 */       c = (Short2ObjectOpenHashMap<V>)super.clone();
/* 1084 */     } catch (CloneNotSupportedException cantHappen) {
/* 1085 */       throw new InternalError();
/*      */     } 
/* 1087 */     c.keys = null;
/* 1088 */     c.values = null;
/* 1089 */     c.entries = null;
/* 1090 */     c.containsNullKey = this.containsNullKey;
/* 1091 */     c.key = (short[])this.key.clone();
/* 1092 */     c.value = (V[])this.value.clone();
/* 1093 */     return c;
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
/* 1106 */     int h = 0;
/* 1107 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1108 */       while (this.key[i] == 0)
/* 1109 */         i++; 
/* 1110 */       t = this.key[i];
/* 1111 */       if (this != this.value[i])
/* 1112 */         t ^= (this.value[i] == null) ? 0 : this.value[i].hashCode(); 
/* 1113 */       h += t;
/* 1114 */       i++;
/*      */     } 
/*      */     
/* 1117 */     if (this.containsNullKey)
/* 1118 */       h += (this.value[this.n] == null) ? 0 : this.value[this.n].hashCode(); 
/* 1119 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1122 */     short[] key = this.key;
/* 1123 */     V[] value = this.value;
/* 1124 */     MapIterator i = new MapIterator();
/* 1125 */     s.defaultWriteObject();
/* 1126 */     for (int j = this.size; j-- != 0; ) {
/* 1127 */       int e = i.nextEntry();
/* 1128 */       s.writeShort(key[e]);
/* 1129 */       s.writeObject(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1134 */     s.defaultReadObject();
/* 1135 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1136 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1137 */     this.mask = this.n - 1;
/* 1138 */     short[] key = this.key = new short[this.n + 1];
/* 1139 */     V[] value = this.value = (V[])new Object[this.n + 1];
/*      */ 
/*      */     
/* 1142 */     for (int i = this.size; i-- != 0; ) {
/* 1143 */       int pos; short k = s.readShort();
/* 1144 */       V v = (V)s.readObject();
/* 1145 */       if (k == 0) {
/* 1146 */         pos = this.n;
/* 1147 */         this.containsNullKey = true;
/*      */       } else {
/* 1149 */         pos = HashCommon.mix(k) & this.mask;
/* 1150 */         while (key[pos] != 0)
/* 1151 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1153 */       key[pos] = k;
/* 1154 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\Short2ObjectOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */