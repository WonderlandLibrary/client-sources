/*      */ package it.unimi.dsi.fastutil.longs;
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
/*      */ public class Long2ObjectOpenHashMap<V>
/*      */   extends AbstractLong2ObjectMap<V>
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
/*      */   protected transient Long2ObjectMap.FastEntrySet<V> entries;
/*      */   protected transient LongSet keys;
/*      */   protected transient ObjectCollection<V> values;
/*      */   
/*      */   public Long2ObjectOpenHashMap(int expected, float f) {
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
/*      */   public Long2ObjectOpenHashMap(int expected) {
/*  117 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2ObjectOpenHashMap() {
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
/*      */   public Long2ObjectOpenHashMap(Map<? extends Long, ? extends V> m, float f) {
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
/*      */   public Long2ObjectOpenHashMap(Map<? extends Long, ? extends V> m) {
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
/*      */   public Long2ObjectOpenHashMap(Long2ObjectMap<V> m, float f) {
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
/*      */   public Long2ObjectOpenHashMap(Long2ObjectMap<V> m) {
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
/*      */   public Long2ObjectOpenHashMap(long[] k, V[] v, float f) {
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
/*      */   public Long2ObjectOpenHashMap(long[] k, V[] v) {
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
/*  385 */     if (this.containsNullKey && Objects.equals(value[this.n], v))
/*  386 */       return true; 
/*  387 */     for (int i = this.n; i-- != 0;) {
/*  388 */       if (key[i] != 0L && Objects.equals(value[i], v))
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
/*  428 */       if (this.containsNullKey && Objects.equals(v, this.value[this.n])) {
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
/*  440 */     if (k == curr && Objects.equals(v, this.value[pos])) {
/*  441 */       removeEntry(pos);
/*  442 */       return true;
/*      */     } 
/*      */     while (true) {
/*  445 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  446 */         return false; 
/*  447 */       if (k == curr && Objects.equals(v, this.value[pos])) {
/*  448 */         removeEntry(pos);
/*  449 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(long k, V oldValue, V v) {
/*  456 */     int pos = find(k);
/*  457 */     if (pos < 0 || !Objects.equals(oldValue, this.value[pos]))
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
/*      */     implements Long2ObjectMap.Entry<V>, Map.Entry<Long, V>
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
/*  586 */       return Long2ObjectOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public V getValue() {
/*  590 */       return Long2ObjectOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public V setValue(V v) {
/*  594 */       V oldValue = Long2ObjectOpenHashMap.this.value[this.index];
/*  595 */       Long2ObjectOpenHashMap.this.value[this.index] = v;
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
/*  606 */       return Long.valueOf(Long2ObjectOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  611 */       if (!(o instanceof Map.Entry))
/*  612 */         return false; 
/*  613 */       Map.Entry<Long, V> e = (Map.Entry<Long, V>)o;
/*  614 */       return (Long2ObjectOpenHashMap.this.key[this.index] == ((Long)e.getKey()).longValue() && 
/*  615 */         Objects.equals(Long2ObjectOpenHashMap.this.value[this.index], e.getValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  619 */       return HashCommon.long2int(Long2ObjectOpenHashMap.this.key[this.index]) ^ (
/*  620 */         (Long2ObjectOpenHashMap.this.value[this.index] == null) ? 0 : Long2ObjectOpenHashMap.this.value[this.index].hashCode());
/*      */     }
/*      */     
/*      */     public String toString() {
/*  624 */       return Long2ObjectOpenHashMap.this.key[this.index] + "=>" + Long2ObjectOpenHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  634 */     int pos = Long2ObjectOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  641 */     int last = -1;
/*      */     
/*  643 */     int c = Long2ObjectOpenHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  647 */     boolean mustReturnNullKey = Long2ObjectOpenHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     LongArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  654 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  657 */       if (!hasNext())
/*  658 */         throw new NoSuchElementException(); 
/*  659 */       this.c--;
/*  660 */       if (this.mustReturnNullKey) {
/*  661 */         this.mustReturnNullKey = false;
/*  662 */         return this.last = Long2ObjectOpenHashMap.this.n;
/*      */       } 
/*  664 */       long[] key = Long2ObjectOpenHashMap.this.key;
/*      */       while (true) {
/*  666 */         if (--this.pos < 0) {
/*      */           
/*  668 */           this.last = Integer.MIN_VALUE;
/*  669 */           long k = this.wrapped.getLong(-this.pos - 1);
/*  670 */           int p = (int)HashCommon.mix(k) & Long2ObjectOpenHashMap.this.mask;
/*  671 */           while (k != key[p])
/*  672 */             p = p + 1 & Long2ObjectOpenHashMap.this.mask; 
/*  673 */           return p;
/*      */         } 
/*  675 */         if (key[this.pos] != 0L) {
/*  676 */           return this.last = this.pos;
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
/*  690 */       long[] key = Long2ObjectOpenHashMap.this.key; while (true) {
/*      */         long curr; int last;
/*  692 */         pos = (last = pos) + 1 & Long2ObjectOpenHashMap.this.mask;
/*      */         while (true) {
/*  694 */           if ((curr = key[pos]) == 0L) {
/*  695 */             key[last] = 0L;
/*  696 */             Long2ObjectOpenHashMap.this.value[last] = null;
/*      */             return;
/*      */           } 
/*  699 */           int slot = (int)HashCommon.mix(curr) & Long2ObjectOpenHashMap.this.mask;
/*  700 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  702 */           pos = pos + 1 & Long2ObjectOpenHashMap.this.mask;
/*      */         } 
/*  704 */         if (pos < last) {
/*  705 */           if (this.wrapped == null)
/*  706 */             this.wrapped = new LongArrayList(2); 
/*  707 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  709 */         key[last] = curr;
/*  710 */         Long2ObjectOpenHashMap.this.value[last] = Long2ObjectOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  714 */       if (this.last == -1)
/*  715 */         throw new IllegalStateException(); 
/*  716 */       if (this.last == Long2ObjectOpenHashMap.this.n) {
/*  717 */         Long2ObjectOpenHashMap.this.containsNullKey = false;
/*  718 */         Long2ObjectOpenHashMap.this.value[Long2ObjectOpenHashMap.this.n] = null;
/*  719 */       } else if (this.pos >= 0) {
/*  720 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  723 */         Long2ObjectOpenHashMap.this.remove(this.wrapped.getLong(-this.pos - 1));
/*  724 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  727 */       Long2ObjectOpenHashMap.this.size--;
/*  728 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  733 */       int i = n;
/*  734 */       while (i-- != 0 && hasNext())
/*  735 */         nextEntry(); 
/*  736 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Long2ObjectMap.Entry<V>> { private Long2ObjectOpenHashMap<V>.MapEntry entry;
/*      */     
/*      */     public Long2ObjectOpenHashMap<V>.MapEntry next() {
/*  743 */       return this.entry = new Long2ObjectOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  747 */       super.remove();
/*  748 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Long2ObjectMap.Entry<V>> { private FastEntryIterator() {
/*  752 */       this.entry = new Long2ObjectOpenHashMap.MapEntry();
/*      */     } private final Long2ObjectOpenHashMap<V>.MapEntry entry;
/*      */     public Long2ObjectOpenHashMap<V>.MapEntry next() {
/*  755 */       this.entry.index = nextEntry();
/*  756 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Long2ObjectMap.Entry<V>> implements Long2ObjectMap.FastEntrySet<V> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Long2ObjectMap.Entry<V>> iterator() {
/*  762 */       return new Long2ObjectOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Long2ObjectMap.Entry<V>> fastIterator() {
/*  766 */       return new Long2ObjectOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  771 */       if (!(o instanceof Map.Entry))
/*  772 */         return false; 
/*  773 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  774 */       if (e.getKey() == null || !(e.getKey() instanceof Long))
/*  775 */         return false; 
/*  776 */       long k = ((Long)e.getKey()).longValue();
/*  777 */       V v = (V)e.getValue();
/*  778 */       if (k == 0L) {
/*  779 */         return (Long2ObjectOpenHashMap.this.containsNullKey && Objects.equals(Long2ObjectOpenHashMap.this.value[Long2ObjectOpenHashMap.this.n], v));
/*      */       }
/*  781 */       long[] key = Long2ObjectOpenHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/*  784 */       if ((curr = key[pos = (int)HashCommon.mix(k) & Long2ObjectOpenHashMap.this.mask]) == 0L)
/*  785 */         return false; 
/*  786 */       if (k == curr) {
/*  787 */         return Objects.equals(Long2ObjectOpenHashMap.this.value[pos], v);
/*      */       }
/*      */       while (true) {
/*  790 */         if ((curr = key[pos = pos + 1 & Long2ObjectOpenHashMap.this.mask]) == 0L)
/*  791 */           return false; 
/*  792 */         if (k == curr) {
/*  793 */           return Objects.equals(Long2ObjectOpenHashMap.this.value[pos], v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  799 */       if (!(o instanceof Map.Entry))
/*  800 */         return false; 
/*  801 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  802 */       if (e.getKey() == null || !(e.getKey() instanceof Long))
/*  803 */         return false; 
/*  804 */       long k = ((Long)e.getKey()).longValue();
/*  805 */       V v = (V)e.getValue();
/*  806 */       if (k == 0L) {
/*  807 */         if (Long2ObjectOpenHashMap.this.containsNullKey && Objects.equals(Long2ObjectOpenHashMap.this.value[Long2ObjectOpenHashMap.this.n], v)) {
/*  808 */           Long2ObjectOpenHashMap.this.removeNullEntry();
/*  809 */           return true;
/*      */         } 
/*  811 */         return false;
/*      */       } 
/*      */       
/*  814 */       long[] key = Long2ObjectOpenHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/*  817 */       if ((curr = key[pos = (int)HashCommon.mix(k) & Long2ObjectOpenHashMap.this.mask]) == 0L)
/*  818 */         return false; 
/*  819 */       if (curr == k) {
/*  820 */         if (Objects.equals(Long2ObjectOpenHashMap.this.value[pos], v)) {
/*  821 */           Long2ObjectOpenHashMap.this.removeEntry(pos);
/*  822 */           return true;
/*      */         } 
/*  824 */         return false;
/*      */       } 
/*      */       while (true) {
/*  827 */         if ((curr = key[pos = pos + 1 & Long2ObjectOpenHashMap.this.mask]) == 0L)
/*  828 */           return false; 
/*  829 */         if (curr == k && 
/*  830 */           Objects.equals(Long2ObjectOpenHashMap.this.value[pos], v)) {
/*  831 */           Long2ObjectOpenHashMap.this.removeEntry(pos);
/*  832 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  839 */       return Long2ObjectOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  843 */       Long2ObjectOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Long2ObjectMap.Entry<V>> consumer) {
/*  848 */       if (Long2ObjectOpenHashMap.this.containsNullKey)
/*  849 */         consumer.accept(new AbstractLong2ObjectMap.BasicEntry<>(Long2ObjectOpenHashMap.this.key[Long2ObjectOpenHashMap.this.n], Long2ObjectOpenHashMap.this.value[Long2ObjectOpenHashMap.this.n])); 
/*  850 */       for (int pos = Long2ObjectOpenHashMap.this.n; pos-- != 0;) {
/*  851 */         if (Long2ObjectOpenHashMap.this.key[pos] != 0L)
/*  852 */           consumer.accept(new AbstractLong2ObjectMap.BasicEntry<>(Long2ObjectOpenHashMap.this.key[pos], Long2ObjectOpenHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Long2ObjectMap.Entry<V>> consumer) {
/*  857 */       AbstractLong2ObjectMap.BasicEntry<V> entry = new AbstractLong2ObjectMap.BasicEntry<>();
/*  858 */       if (Long2ObjectOpenHashMap.this.containsNullKey) {
/*  859 */         entry.key = Long2ObjectOpenHashMap.this.key[Long2ObjectOpenHashMap.this.n];
/*  860 */         entry.value = Long2ObjectOpenHashMap.this.value[Long2ObjectOpenHashMap.this.n];
/*  861 */         consumer.accept(entry);
/*      */       } 
/*  863 */       for (int pos = Long2ObjectOpenHashMap.this.n; pos-- != 0;) {
/*  864 */         if (Long2ObjectOpenHashMap.this.key[pos] != 0L) {
/*  865 */           entry.key = Long2ObjectOpenHashMap.this.key[pos];
/*  866 */           entry.value = Long2ObjectOpenHashMap.this.value[pos];
/*  867 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Long2ObjectMap.FastEntrySet<V> long2ObjectEntrySet() {
/*  873 */     if (this.entries == null)
/*  874 */       this.entries = new MapEntrySet(); 
/*  875 */     return this.entries;
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
/*  892 */       return Long2ObjectOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractLongSet { private KeySet() {}
/*      */     
/*      */     public LongIterator iterator() {
/*  898 */       return new Long2ObjectOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(LongConsumer consumer) {
/*  903 */       if (Long2ObjectOpenHashMap.this.containsNullKey)
/*  904 */         consumer.accept(Long2ObjectOpenHashMap.this.key[Long2ObjectOpenHashMap.this.n]); 
/*  905 */       for (int pos = Long2ObjectOpenHashMap.this.n; pos-- != 0; ) {
/*  906 */         long k = Long2ObjectOpenHashMap.this.key[pos];
/*  907 */         if (k != 0L)
/*  908 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  913 */       return Long2ObjectOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(long k) {
/*  917 */       return Long2ObjectOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(long k) {
/*  921 */       int oldSize = Long2ObjectOpenHashMap.this.size;
/*  922 */       Long2ObjectOpenHashMap.this.remove(k);
/*  923 */       return (Long2ObjectOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  927 */       Long2ObjectOpenHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public LongSet keySet() {
/*  932 */     if (this.keys == null)
/*  933 */       this.keys = new KeySet(); 
/*  934 */     return this.keys;
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
/*  951 */       return Long2ObjectOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectCollection<V> values() {
/*  956 */     if (this.values == null)
/*  957 */       this.values = (ObjectCollection<V>)new AbstractObjectCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/*  960 */             return new Long2ObjectOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/*  964 */             return Long2ObjectOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(Object v) {
/*  968 */             return Long2ObjectOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/*  972 */             Long2ObjectOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(Consumer<? super V> consumer)
/*      */           {
/*  977 */             if (Long2ObjectOpenHashMap.this.containsNullKey)
/*  978 */               consumer.accept(Long2ObjectOpenHashMap.this.value[Long2ObjectOpenHashMap.this.n]); 
/*  979 */             for (int pos = Long2ObjectOpenHashMap.this.n; pos-- != 0;) {
/*  980 */               if (Long2ObjectOpenHashMap.this.key[pos] != 0L)
/*  981 */                 consumer.accept(Long2ObjectOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/*  984 */     return this.values;
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
/* 1001 */     return trim(this.size);
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
/* 1025 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1026 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1027 */       return true; 
/*      */     try {
/* 1029 */       rehash(l);
/* 1030 */     } catch (OutOfMemoryError cantDoIt) {
/* 1031 */       return false;
/*      */     } 
/* 1033 */     return true;
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
/* 1049 */     long[] key = this.key;
/* 1050 */     V[] value = this.value;
/* 1051 */     int mask = newN - 1;
/* 1052 */     long[] newKey = new long[newN + 1];
/* 1053 */     V[] newValue = (V[])new Object[newN + 1];
/* 1054 */     int i = this.n;
/* 1055 */     for (int j = realSize(); j-- != 0; ) {
/* 1056 */       while (key[--i] == 0L); int pos;
/* 1057 */       if (newKey[pos = (int)HashCommon.mix(key[i]) & mask] != 0L)
/* 1058 */         while (newKey[pos = pos + 1 & mask] != 0L); 
/* 1059 */       newKey[pos] = key[i];
/* 1060 */       newValue[pos] = value[i];
/*      */     } 
/* 1062 */     newValue[newN] = value[this.n];
/* 1063 */     this.n = newN;
/* 1064 */     this.mask = mask;
/* 1065 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1066 */     this.key = newKey;
/* 1067 */     this.value = newValue;
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
/*      */   public Long2ObjectOpenHashMap<V> clone() {
/*      */     Long2ObjectOpenHashMap<V> c;
/*      */     try {
/* 1084 */       c = (Long2ObjectOpenHashMap<V>)super.clone();
/* 1085 */     } catch (CloneNotSupportedException cantHappen) {
/* 1086 */       throw new InternalError();
/*      */     } 
/* 1088 */     c.keys = null;
/* 1089 */     c.values = null;
/* 1090 */     c.entries = null;
/* 1091 */     c.containsNullKey = this.containsNullKey;
/* 1092 */     c.key = (long[])this.key.clone();
/* 1093 */     c.value = (V[])this.value.clone();
/* 1094 */     return c;
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
/* 1107 */     int h = 0;
/* 1108 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1109 */       while (this.key[i] == 0L)
/* 1110 */         i++; 
/* 1111 */       t = HashCommon.long2int(this.key[i]);
/* 1112 */       if (this != this.value[i])
/* 1113 */         t ^= (this.value[i] == null) ? 0 : this.value[i].hashCode(); 
/* 1114 */       h += t;
/* 1115 */       i++;
/*      */     } 
/*      */     
/* 1118 */     if (this.containsNullKey)
/* 1119 */       h += (this.value[this.n] == null) ? 0 : this.value[this.n].hashCode(); 
/* 1120 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1123 */     long[] key = this.key;
/* 1124 */     V[] value = this.value;
/* 1125 */     MapIterator i = new MapIterator();
/* 1126 */     s.defaultWriteObject();
/* 1127 */     for (int j = this.size; j-- != 0; ) {
/* 1128 */       int e = i.nextEntry();
/* 1129 */       s.writeLong(key[e]);
/* 1130 */       s.writeObject(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1135 */     s.defaultReadObject();
/* 1136 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1137 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1138 */     this.mask = this.n - 1;
/* 1139 */     long[] key = this.key = new long[this.n + 1];
/* 1140 */     V[] value = this.value = (V[])new Object[this.n + 1];
/*      */ 
/*      */     
/* 1143 */     for (int i = this.size; i-- != 0; ) {
/* 1144 */       int pos; long k = s.readLong();
/* 1145 */       V v = (V)s.readObject();
/* 1146 */       if (k == 0L) {
/* 1147 */         pos = this.n;
/* 1148 */         this.containsNullKey = true;
/*      */       } else {
/* 1150 */         pos = (int)HashCommon.mix(k) & this.mask;
/* 1151 */         while (key[pos] != 0L)
/* 1152 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1154 */       key[pos] = k;
/* 1155 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\Long2ObjectOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */