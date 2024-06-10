/*      */ package it.unimi.dsi.fastutil.longs;
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
/*      */ import java.util.function.LongConsumer;
/*      */ import java.util.function.LongFunction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Long2ReferenceOpenHashMap<V>
/*      */   extends AbstractLong2ReferenceMap<V>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient long[] key;
/*      */   protected transient V[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Long2ReferenceMap.FastEntrySet<V> entries;
/*      */   protected transient LongSet keys;
/*      */   protected transient ReferenceCollection<V> values;
/*      */   
/*      */   public Long2ReferenceOpenHashMap(int expected, float f) {
/*   99 */     if (f <= 0.0F || f > 1.0F)
/*  100 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  101 */     if (expected < 0)
/*  102 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  103 */     this.f = f;
/*  104 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  105 */     this.mask = this.n - 1;
/*  106 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  107 */     this.key = new long[this.n + 1];
/*  108 */     this.value = (V[])new Object[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2ReferenceOpenHashMap(int expected) {
/*  117 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2ReferenceOpenHashMap() {
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
/*      */   public Long2ReferenceOpenHashMap(Map<? extends Long, ? extends V> m, float f) {
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
/*      */   public Long2ReferenceOpenHashMap(Map<? extends Long, ? extends V> m) {
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
/*      */   public Long2ReferenceOpenHashMap(Long2ReferenceMap<V> m, float f) {
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
/*      */   public Long2ReferenceOpenHashMap(Long2ReferenceMap<V> m) {
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
/*      */   public Long2ReferenceOpenHashMap(long[] k, V[] v, float f) {
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
/*      */   public Long2ReferenceOpenHashMap(long[] k, V[] v) {
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
/*      */   public void putAll(Map<? extends Long, ? extends V> m) {
/*  239 */     if (this.f <= 0.5D) {
/*  240 */       ensureCapacity(m.size());
/*      */     } else {
/*  242 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  244 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(long k) {
/*  248 */     if (k == 0L) {
/*  249 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  251 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  254 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  255 */       return -(pos + 1); 
/*  256 */     if (k == curr) {
/*  257 */       return pos;
/*      */     }
/*      */     while (true) {
/*  260 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  261 */         return -(pos + 1); 
/*  262 */       if (k == curr)
/*  263 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, long k, V v) {
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
/*      */   public V put(long k, V v) {
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
/*  298 */     long[] key = this.key; while (true) {
/*      */       long curr; int last;
/*  300 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  302 */         if ((curr = key[pos]) == 0L) {
/*  303 */           key[last] = 0L;
/*  304 */           this.value[last] = null;
/*      */           return;
/*      */         } 
/*  307 */         int slot = (int)HashCommon.mix(curr) & this.mask;
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
/*      */   public V remove(long k) {
/*  319 */     if (k == 0L) {
/*  320 */       if (this.containsNullKey)
/*  321 */         return removeNullEntry(); 
/*  322 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  325 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  328 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  329 */       return this.defRetValue; 
/*  330 */     if (k == curr)
/*  331 */       return removeEntry(pos); 
/*      */     while (true) {
/*  333 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  334 */         return this.defRetValue; 
/*  335 */       if (k == curr) {
/*  336 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public V get(long k) {
/*  342 */     if (k == 0L) {
/*  343 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  345 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  348 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  349 */       return this.defRetValue; 
/*  350 */     if (k == curr) {
/*  351 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  354 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  355 */         return this.defRetValue; 
/*  356 */       if (k == curr) {
/*  357 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(long k) {
/*  363 */     if (k == 0L) {
/*  364 */       return this.containsNullKey;
/*      */     }
/*  366 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  369 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  370 */       return false; 
/*  371 */     if (k == curr) {
/*  372 */       return true;
/*      */     }
/*      */     while (true) {
/*  375 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  376 */         return false; 
/*  377 */       if (k == curr)
/*  378 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(Object v) {
/*  383 */     V[] value = this.value;
/*  384 */     long[] key = this.key;
/*  385 */     if (this.containsNullKey && value[this.n] == v)
/*  386 */       return true; 
/*  387 */     for (int i = this.n; i-- != 0;) {
/*  388 */       if (key[i] != 0L && value[i] == v)
/*  389 */         return true; 
/*  390 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V getOrDefault(long k, V defaultValue) {
/*  396 */     if (k == 0L) {
/*  397 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  399 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  402 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  403 */       return defaultValue; 
/*  404 */     if (k == curr) {
/*  405 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  408 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  409 */         return defaultValue; 
/*  410 */       if (k == curr) {
/*  411 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public V putIfAbsent(long k, V v) {
/*  417 */     int pos = find(k);
/*  418 */     if (pos >= 0)
/*  419 */       return this.value[pos]; 
/*  420 */     insert(-pos - 1, k, v);
/*  421 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(long k, Object v) {
/*  427 */     if (k == 0L) {
/*  428 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  429 */         removeNullEntry();
/*  430 */         return true;
/*      */       } 
/*  432 */       return false;
/*      */     } 
/*      */     
/*  435 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  438 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  439 */       return false; 
/*  440 */     if (k == curr && v == this.value[pos]) {
/*  441 */       removeEntry(pos);
/*  442 */       return true;
/*      */     } 
/*      */     while (true) {
/*  445 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  446 */         return false; 
/*  447 */       if (k == curr && v == this.value[pos]) {
/*  448 */         removeEntry(pos);
/*  449 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(long k, V oldValue, V v) {
/*  456 */     int pos = find(k);
/*  457 */     if (pos < 0 || oldValue != this.value[pos])
/*  458 */       return false; 
/*  459 */     this.value[pos] = v;
/*  460 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public V replace(long k, V v) {
/*  465 */     int pos = find(k);
/*  466 */     if (pos < 0)
/*  467 */       return this.defRetValue; 
/*  468 */     V oldValue = this.value[pos];
/*  469 */     this.value[pos] = v;
/*  470 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public V computeIfAbsent(long k, LongFunction<? extends V> mappingFunction) {
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
/*      */   public V computeIfPresent(long k, BiFunction<? super Long, ? super V, ? extends V> remappingFunction) {
/*  487 */     Objects.requireNonNull(remappingFunction);
/*  488 */     int pos = find(k);
/*  489 */     if (pos < 0)
/*  490 */       return this.defRetValue; 
/*  491 */     V newValue = remappingFunction.apply(Long.valueOf(k), this.value[pos]);
/*  492 */     if (newValue == null) {
/*  493 */       if (k == 0L) {
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
/*      */   public V compute(long k, BiFunction<? super Long, ? super V, ? extends V> remappingFunction) {
/*  505 */     Objects.requireNonNull(remappingFunction);
/*  506 */     int pos = find(k);
/*  507 */     V newValue = remappingFunction.apply(Long.valueOf(k), (pos >= 0) ? this.value[pos] : null);
/*  508 */     if (newValue == null) {
/*  509 */       if (pos >= 0)
/*  510 */         if (k == 0L) {
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
/*      */   public V merge(long k, V v, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
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
/*  538 */       if (k == 0L) {
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
/*  559 */     Arrays.fill(this.key, 0L);
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
/*      */     implements Long2ReferenceMap.Entry<V>, Map.Entry<Long, V>
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
/*      */     public long getLongKey() {
/*  586 */       return Long2ReferenceOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public V getValue() {
/*  590 */       return Long2ReferenceOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public V setValue(V v) {
/*  594 */       V oldValue = Long2ReferenceOpenHashMap.this.value[this.index];
/*  595 */       Long2ReferenceOpenHashMap.this.value[this.index] = v;
/*  596 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long getKey() {
/*  606 */       return Long.valueOf(Long2ReferenceOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  611 */       if (!(o instanceof Map.Entry))
/*  612 */         return false; 
/*  613 */       Map.Entry<Long, V> e = (Map.Entry<Long, V>)o;
/*  614 */       return (Long2ReferenceOpenHashMap.this.key[this.index] == ((Long)e.getKey()).longValue() && Long2ReferenceOpenHashMap.this.value[this.index] == e.getValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  618 */       return HashCommon.long2int(Long2ReferenceOpenHashMap.this.key[this.index]) ^ (
/*  619 */         (Long2ReferenceOpenHashMap.this.value[this.index] == null) ? 0 : System.identityHashCode(Long2ReferenceOpenHashMap.this.value[this.index]));
/*      */     }
/*      */     
/*      */     public String toString() {
/*  623 */       return Long2ReferenceOpenHashMap.this.key[this.index] + "=>" + Long2ReferenceOpenHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  633 */     int pos = Long2ReferenceOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  640 */     int last = -1;
/*      */     
/*  642 */     int c = Long2ReferenceOpenHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  646 */     boolean mustReturnNullKey = Long2ReferenceOpenHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     LongArrayList wrapped;
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
/*  661 */         return this.last = Long2ReferenceOpenHashMap.this.n;
/*      */       } 
/*  663 */       long[] key = Long2ReferenceOpenHashMap.this.key;
/*      */       while (true) {
/*  665 */         if (--this.pos < 0) {
/*      */           
/*  667 */           this.last = Integer.MIN_VALUE;
/*  668 */           long k = this.wrapped.getLong(-this.pos - 1);
/*  669 */           int p = (int)HashCommon.mix(k) & Long2ReferenceOpenHashMap.this.mask;
/*  670 */           while (k != key[p])
/*  671 */             p = p + 1 & Long2ReferenceOpenHashMap.this.mask; 
/*  672 */           return p;
/*      */         } 
/*  674 */         if (key[this.pos] != 0L) {
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
/*  689 */       long[] key = Long2ReferenceOpenHashMap.this.key; while (true) {
/*      */         long curr; int last;
/*  691 */         pos = (last = pos) + 1 & Long2ReferenceOpenHashMap.this.mask;
/*      */         while (true) {
/*  693 */           if ((curr = key[pos]) == 0L) {
/*  694 */             key[last] = 0L;
/*  695 */             Long2ReferenceOpenHashMap.this.value[last] = null;
/*      */             return;
/*      */           } 
/*  698 */           int slot = (int)HashCommon.mix(curr) & Long2ReferenceOpenHashMap.this.mask;
/*  699 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  701 */           pos = pos + 1 & Long2ReferenceOpenHashMap.this.mask;
/*      */         } 
/*  703 */         if (pos < last) {
/*  704 */           if (this.wrapped == null)
/*  705 */             this.wrapped = new LongArrayList(2); 
/*  706 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  708 */         key[last] = curr;
/*  709 */         Long2ReferenceOpenHashMap.this.value[last] = Long2ReferenceOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  713 */       if (this.last == -1)
/*  714 */         throw new IllegalStateException(); 
/*  715 */       if (this.last == Long2ReferenceOpenHashMap.this.n) {
/*  716 */         Long2ReferenceOpenHashMap.this.containsNullKey = false;
/*  717 */         Long2ReferenceOpenHashMap.this.value[Long2ReferenceOpenHashMap.this.n] = null;
/*  718 */       } else if (this.pos >= 0) {
/*  719 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  722 */         Long2ReferenceOpenHashMap.this.remove(this.wrapped.getLong(-this.pos - 1));
/*  723 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  726 */       Long2ReferenceOpenHashMap.this.size--;
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
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Long2ReferenceMap.Entry<V>> { private Long2ReferenceOpenHashMap<V>.MapEntry entry;
/*      */     
/*      */     public Long2ReferenceOpenHashMap<V>.MapEntry next() {
/*  742 */       return this.entry = new Long2ReferenceOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  746 */       super.remove();
/*  747 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Long2ReferenceMap.Entry<V>> { private FastEntryIterator() {
/*  751 */       this.entry = new Long2ReferenceOpenHashMap.MapEntry();
/*      */     } private final Long2ReferenceOpenHashMap<V>.MapEntry entry;
/*      */     public Long2ReferenceOpenHashMap<V>.MapEntry next() {
/*  754 */       this.entry.index = nextEntry();
/*  755 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Long2ReferenceMap.Entry<V>> implements Long2ReferenceMap.FastEntrySet<V> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Long2ReferenceMap.Entry<V>> iterator() {
/*  761 */       return new Long2ReferenceOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Long2ReferenceMap.Entry<V>> fastIterator() {
/*  765 */       return new Long2ReferenceOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  770 */       if (!(o instanceof Map.Entry))
/*  771 */         return false; 
/*  772 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  773 */       if (e.getKey() == null || !(e.getKey() instanceof Long))
/*  774 */         return false; 
/*  775 */       long k = ((Long)e.getKey()).longValue();
/*  776 */       V v = (V)e.getValue();
/*  777 */       if (k == 0L) {
/*  778 */         return (Long2ReferenceOpenHashMap.this.containsNullKey && Long2ReferenceOpenHashMap.this.value[Long2ReferenceOpenHashMap.this.n] == v);
/*      */       }
/*  780 */       long[] key = Long2ReferenceOpenHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/*  783 */       if ((curr = key[pos = (int)HashCommon.mix(k) & Long2ReferenceOpenHashMap.this.mask]) == 0L)
/*  784 */         return false; 
/*  785 */       if (k == curr) {
/*  786 */         return (Long2ReferenceOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  789 */         if ((curr = key[pos = pos + 1 & Long2ReferenceOpenHashMap.this.mask]) == 0L)
/*  790 */           return false; 
/*  791 */         if (k == curr) {
/*  792 */           return (Long2ReferenceOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  798 */       if (!(o instanceof Map.Entry))
/*  799 */         return false; 
/*  800 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  801 */       if (e.getKey() == null || !(e.getKey() instanceof Long))
/*  802 */         return false; 
/*  803 */       long k = ((Long)e.getKey()).longValue();
/*  804 */       V v = (V)e.getValue();
/*  805 */       if (k == 0L) {
/*  806 */         if (Long2ReferenceOpenHashMap.this.containsNullKey && Long2ReferenceOpenHashMap.this.value[Long2ReferenceOpenHashMap.this.n] == v) {
/*  807 */           Long2ReferenceOpenHashMap.this.removeNullEntry();
/*  808 */           return true;
/*      */         } 
/*  810 */         return false;
/*      */       } 
/*      */       
/*  813 */       long[] key = Long2ReferenceOpenHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/*  816 */       if ((curr = key[pos = (int)HashCommon.mix(k) & Long2ReferenceOpenHashMap.this.mask]) == 0L)
/*  817 */         return false; 
/*  818 */       if (curr == k) {
/*  819 */         if (Long2ReferenceOpenHashMap.this.value[pos] == v) {
/*  820 */           Long2ReferenceOpenHashMap.this.removeEntry(pos);
/*  821 */           return true;
/*      */         } 
/*  823 */         return false;
/*      */       } 
/*      */       while (true) {
/*  826 */         if ((curr = key[pos = pos + 1 & Long2ReferenceOpenHashMap.this.mask]) == 0L)
/*  827 */           return false; 
/*  828 */         if (curr == k && 
/*  829 */           Long2ReferenceOpenHashMap.this.value[pos] == v) {
/*  830 */           Long2ReferenceOpenHashMap.this.removeEntry(pos);
/*  831 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  838 */       return Long2ReferenceOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  842 */       Long2ReferenceOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Long2ReferenceMap.Entry<V>> consumer) {
/*  847 */       if (Long2ReferenceOpenHashMap.this.containsNullKey)
/*  848 */         consumer.accept(new AbstractLong2ReferenceMap.BasicEntry<>(Long2ReferenceOpenHashMap.this.key[Long2ReferenceOpenHashMap.this.n], Long2ReferenceOpenHashMap.this.value[Long2ReferenceOpenHashMap.this.n])); 
/*  849 */       for (int pos = Long2ReferenceOpenHashMap.this.n; pos-- != 0;) {
/*  850 */         if (Long2ReferenceOpenHashMap.this.key[pos] != 0L)
/*  851 */           consumer.accept(new AbstractLong2ReferenceMap.BasicEntry<>(Long2ReferenceOpenHashMap.this.key[pos], Long2ReferenceOpenHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Long2ReferenceMap.Entry<V>> consumer) {
/*  856 */       AbstractLong2ReferenceMap.BasicEntry<V> entry = new AbstractLong2ReferenceMap.BasicEntry<>();
/*  857 */       if (Long2ReferenceOpenHashMap.this.containsNullKey) {
/*  858 */         entry.key = Long2ReferenceOpenHashMap.this.key[Long2ReferenceOpenHashMap.this.n];
/*  859 */         entry.value = Long2ReferenceOpenHashMap.this.value[Long2ReferenceOpenHashMap.this.n];
/*  860 */         consumer.accept(entry);
/*      */       } 
/*  862 */       for (int pos = Long2ReferenceOpenHashMap.this.n; pos-- != 0;) {
/*  863 */         if (Long2ReferenceOpenHashMap.this.key[pos] != 0L) {
/*  864 */           entry.key = Long2ReferenceOpenHashMap.this.key[pos];
/*  865 */           entry.value = Long2ReferenceOpenHashMap.this.value[pos];
/*  866 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Long2ReferenceMap.FastEntrySet<V> long2ReferenceEntrySet() {
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
/*      */     implements LongIterator
/*      */   {
/*      */     public long nextLong() {
/*  891 */       return Long2ReferenceOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractLongSet { private KeySet() {}
/*      */     
/*      */     public LongIterator iterator() {
/*  897 */       return new Long2ReferenceOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(LongConsumer consumer) {
/*  902 */       if (Long2ReferenceOpenHashMap.this.containsNullKey)
/*  903 */         consumer.accept(Long2ReferenceOpenHashMap.this.key[Long2ReferenceOpenHashMap.this.n]); 
/*  904 */       for (int pos = Long2ReferenceOpenHashMap.this.n; pos-- != 0; ) {
/*  905 */         long k = Long2ReferenceOpenHashMap.this.key[pos];
/*  906 */         if (k != 0L)
/*  907 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  912 */       return Long2ReferenceOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(long k) {
/*  916 */       return Long2ReferenceOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(long k) {
/*  920 */       int oldSize = Long2ReferenceOpenHashMap.this.size;
/*  921 */       Long2ReferenceOpenHashMap.this.remove(k);
/*  922 */       return (Long2ReferenceOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  926 */       Long2ReferenceOpenHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public LongSet keySet() {
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
/*  950 */       return Long2ReferenceOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public ReferenceCollection<V> values() {
/*  955 */     if (this.values == null)
/*  956 */       this.values = (ReferenceCollection<V>)new AbstractReferenceCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/*  959 */             return new Long2ReferenceOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/*  963 */             return Long2ReferenceOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(Object v) {
/*  967 */             return Long2ReferenceOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/*  971 */             Long2ReferenceOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(Consumer<? super V> consumer)
/*      */           {
/*  976 */             if (Long2ReferenceOpenHashMap.this.containsNullKey)
/*  977 */               consumer.accept(Long2ReferenceOpenHashMap.this.value[Long2ReferenceOpenHashMap.this.n]); 
/*  978 */             for (int pos = Long2ReferenceOpenHashMap.this.n; pos-- != 0;) {
/*  979 */               if (Long2ReferenceOpenHashMap.this.key[pos] != 0L)
/*  980 */                 consumer.accept(Long2ReferenceOpenHashMap.this.value[pos]); 
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
/* 1048 */     long[] key = this.key;
/* 1049 */     V[] value = this.value;
/* 1050 */     int mask = newN - 1;
/* 1051 */     long[] newKey = new long[newN + 1];
/* 1052 */     V[] newValue = (V[])new Object[newN + 1];
/* 1053 */     int i = this.n;
/* 1054 */     for (int j = realSize(); j-- != 0; ) {
/* 1055 */       while (key[--i] == 0L); int pos;
/* 1056 */       if (newKey[pos = (int)HashCommon.mix(key[i]) & mask] != 0L)
/* 1057 */         while (newKey[pos = pos + 1 & mask] != 0L); 
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
/*      */   public Long2ReferenceOpenHashMap<V> clone() {
/*      */     Long2ReferenceOpenHashMap<V> c;
/*      */     try {
/* 1083 */       c = (Long2ReferenceOpenHashMap<V>)super.clone();
/* 1084 */     } catch (CloneNotSupportedException cantHappen) {
/* 1085 */       throw new InternalError();
/*      */     } 
/* 1087 */     c.keys = null;
/* 1088 */     c.values = null;
/* 1089 */     c.entries = null;
/* 1090 */     c.containsNullKey = this.containsNullKey;
/* 1091 */     c.key = (long[])this.key.clone();
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
/* 1108 */       while (this.key[i] == 0L)
/* 1109 */         i++; 
/* 1110 */       t = HashCommon.long2int(this.key[i]);
/* 1111 */       if (this != this.value[i])
/* 1112 */         t ^= (this.value[i] == null) ? 0 : System.identityHashCode(this.value[i]); 
/* 1113 */       h += t;
/* 1114 */       i++;
/*      */     } 
/*      */     
/* 1117 */     if (this.containsNullKey)
/* 1118 */       h += (this.value[this.n] == null) ? 0 : System.identityHashCode(this.value[this.n]); 
/* 1119 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1122 */     long[] key = this.key;
/* 1123 */     V[] value = this.value;
/* 1124 */     MapIterator i = new MapIterator();
/* 1125 */     s.defaultWriteObject();
/* 1126 */     for (int j = this.size; j-- != 0; ) {
/* 1127 */       int e = i.nextEntry();
/* 1128 */       s.writeLong(key[e]);
/* 1129 */       s.writeObject(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1134 */     s.defaultReadObject();
/* 1135 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1136 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1137 */     this.mask = this.n - 1;
/* 1138 */     long[] key = this.key = new long[this.n + 1];
/* 1139 */     V[] value = this.value = (V[])new Object[this.n + 1];
/*      */ 
/*      */     
/* 1142 */     for (int i = this.size; i-- != 0; ) {
/* 1143 */       int pos; long k = s.readLong();
/* 1144 */       V v = (V)s.readObject();
/* 1145 */       if (k == 0L) {
/* 1146 */         pos = this.n;
/* 1147 */         this.containsNullKey = true;
/*      */       } else {
/* 1149 */         pos = (int)HashCommon.mix(k) & this.mask;
/* 1150 */         while (key[pos] != 0L)
/* 1151 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1153 */       key[pos] = k;
/* 1154 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\Long2ReferenceOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */