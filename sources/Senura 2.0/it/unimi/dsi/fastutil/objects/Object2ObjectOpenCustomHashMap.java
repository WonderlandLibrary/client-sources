/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
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
/*      */ import java.util.function.Consumer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Object2ObjectOpenCustomHashMap<K, V>
/*      */   extends AbstractObject2ObjectMap<K, V>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient V[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected Hash.Strategy<? super K> strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Object2ObjectMap.FastEntrySet<K, V> entries;
/*      */   protected transient ObjectSet<K> keys;
/*      */   protected transient ObjectCollection<V> values;
/*      */   
/*      */   public Object2ObjectOpenCustomHashMap(int expected, float f, Hash.Strategy<? super K> strategy) {
/*  105 */     this.strategy = strategy;
/*  106 */     if (f <= 0.0F || f > 1.0F)
/*  107 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  108 */     if (expected < 0)
/*  109 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  110 */     this.f = f;
/*  111 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  112 */     this.mask = this.n - 1;
/*  113 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  114 */     this.key = (K[])new Object[this.n + 1];
/*  115 */     this.value = (V[])new Object[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ObjectOpenCustomHashMap(int expected, Hash.Strategy<? super K> strategy) {
/*  126 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ObjectOpenCustomHashMap(Hash.Strategy<? super K> strategy) {
/*  137 */     this(16, 0.75F, strategy);
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
/*      */   public Object2ObjectOpenCustomHashMap(Map<? extends K, ? extends V> m, float f, Hash.Strategy<? super K> strategy) {
/*  151 */     this(m.size(), f, strategy);
/*  152 */     putAll(m);
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
/*      */   public Object2ObjectOpenCustomHashMap(Map<? extends K, ? extends V> m, Hash.Strategy<? super K> strategy) {
/*  164 */     this(m, 0.75F, strategy);
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
/*      */   public Object2ObjectOpenCustomHashMap(Object2ObjectMap<K, V> m, float f, Hash.Strategy<? super K> strategy) {
/*  178 */     this(m.size(), f, strategy);
/*  179 */     putAll(m);
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
/*      */   public Object2ObjectOpenCustomHashMap(Object2ObjectMap<K, V> m, Hash.Strategy<? super K> strategy) {
/*  191 */     this(m, 0.75F, strategy);
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
/*      */   public Object2ObjectOpenCustomHashMap(K[] k, V[] v, float f, Hash.Strategy<? super K> strategy) {
/*  208 */     this(k.length, f, strategy);
/*  209 */     if (k.length != v.length) {
/*  210 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  212 */     for (int i = 0; i < k.length; i++) {
/*  213 */       put(k[i], v[i]);
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
/*      */   public Object2ObjectOpenCustomHashMap(K[] k, V[] v, Hash.Strategy<? super K> strategy) {
/*  229 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Hash.Strategy<? super K> strategy() {
/*  237 */     return this.strategy;
/*      */   }
/*      */   private int realSize() {
/*  240 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  243 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  244 */     if (needed > this.n)
/*  245 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  248 */     int needed = (int)Math.min(1073741824L, 
/*  249 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  250 */     if (needed > this.n)
/*  251 */       rehash(needed); 
/*      */   }
/*      */   private V removeEntry(int pos) {
/*  254 */     V oldValue = this.value[pos];
/*  255 */     this.value[pos] = null;
/*  256 */     this.size--;
/*  257 */     shiftKeys(pos);
/*  258 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  259 */       rehash(this.n / 2); 
/*  260 */     return oldValue;
/*      */   }
/*      */   private V removeNullEntry() {
/*  263 */     this.containsNullKey = false;
/*  264 */     this.key[this.n] = null;
/*  265 */     V oldValue = this.value[this.n];
/*  266 */     this.value[this.n] = null;
/*  267 */     this.size--;
/*  268 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  269 */       rehash(this.n / 2); 
/*  270 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends V> m) {
/*  274 */     if (this.f <= 0.5D) {
/*  275 */       ensureCapacity(m.size());
/*      */     } else {
/*  277 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  279 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(K k) {
/*  283 */     if (this.strategy.equals(k, null)) {
/*  284 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  286 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  289 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  290 */       return -(pos + 1); 
/*  291 */     if (this.strategy.equals(k, curr)) {
/*  292 */       return pos;
/*      */     }
/*      */     while (true) {
/*  295 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  296 */         return -(pos + 1); 
/*  297 */       if (this.strategy.equals(k, curr))
/*  298 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, K k, V v) {
/*  302 */     if (pos == this.n)
/*  303 */       this.containsNullKey = true; 
/*  304 */     this.key[pos] = k;
/*  305 */     this.value[pos] = v;
/*  306 */     if (this.size++ >= this.maxFill) {
/*  307 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public V put(K k, V v) {
/*  313 */     int pos = find(k);
/*  314 */     if (pos < 0) {
/*  315 */       insert(-pos - 1, k, v);
/*  316 */       return this.defRetValue;
/*      */     } 
/*  318 */     V oldValue = this.value[pos];
/*  319 */     this.value[pos] = v;
/*  320 */     return oldValue;
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
/*  333 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  335 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  337 */         if ((curr = key[pos]) == null) {
/*  338 */           key[last] = null;
/*  339 */           this.value[last] = null;
/*      */           return;
/*      */         } 
/*  342 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  343 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  345 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  347 */       key[last] = curr;
/*  348 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public V remove(Object k) {
/*  354 */     if (this.strategy.equals(k, null)) {
/*  355 */       if (this.containsNullKey)
/*  356 */         return removeNullEntry(); 
/*  357 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  360 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  363 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  364 */       return this.defRetValue; 
/*  365 */     if (this.strategy.equals(k, curr))
/*  366 */       return removeEntry(pos); 
/*      */     while (true) {
/*  368 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  369 */         return this.defRetValue; 
/*  370 */       if (this.strategy.equals(k, curr)) {
/*  371 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public V get(Object k) {
/*  377 */     if (this.strategy.equals(k, null)) {
/*  378 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  380 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  383 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  384 */       return this.defRetValue; 
/*  385 */     if (this.strategy.equals(k, curr)) {
/*  386 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  389 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  390 */         return this.defRetValue; 
/*  391 */       if (this.strategy.equals(k, curr)) {
/*  392 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  398 */     if (this.strategy.equals(k, null)) {
/*  399 */       return this.containsNullKey;
/*      */     }
/*  401 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  404 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  405 */       return false; 
/*  406 */     if (this.strategy.equals(k, curr)) {
/*  407 */       return true;
/*      */     }
/*      */     while (true) {
/*  410 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  411 */         return false; 
/*  412 */       if (this.strategy.equals(k, curr))
/*  413 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(Object v) {
/*  418 */     V[] value = this.value;
/*  419 */     K[] key = this.key;
/*  420 */     if (this.containsNullKey && Objects.equals(value[this.n], v))
/*  421 */       return true; 
/*  422 */     for (int i = this.n; i-- != 0;) {
/*  423 */       if (key[i] != null && Objects.equals(value[i], v))
/*  424 */         return true; 
/*  425 */     }  return false;
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
/*  436 */     if (this.size == 0)
/*      */       return; 
/*  438 */     this.size = 0;
/*  439 */     this.containsNullKey = false;
/*  440 */     Arrays.fill((Object[])this.key, (Object)null);
/*  441 */     Arrays.fill((Object[])this.value, (Object)null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  445 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  449 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Object2ObjectMap.Entry<K, V>, Map.Entry<K, V>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  461 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public K getKey() {
/*  467 */       return Object2ObjectOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public V getValue() {
/*  471 */       return Object2ObjectOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public V setValue(V v) {
/*  475 */       V oldValue = Object2ObjectOpenCustomHashMap.this.value[this.index];
/*  476 */       Object2ObjectOpenCustomHashMap.this.value[this.index] = v;
/*  477 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  482 */       if (!(o instanceof Map.Entry))
/*  483 */         return false; 
/*  484 */       Map.Entry<K, V> e = (Map.Entry<K, V>)o;
/*  485 */       return (Object2ObjectOpenCustomHashMap.this.strategy.equals(Object2ObjectOpenCustomHashMap.this.key[this.index], e.getKey()) && 
/*  486 */         Objects.equals(Object2ObjectOpenCustomHashMap.this.value[this.index], e.getValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  490 */       return Object2ObjectOpenCustomHashMap.this.strategy.hashCode(Object2ObjectOpenCustomHashMap.this.key[this.index]) ^ ((Object2ObjectOpenCustomHashMap.this.value[this.index] == null) ? 0 : Object2ObjectOpenCustomHashMap.this.value[this.index].hashCode());
/*      */     }
/*      */     
/*      */     public String toString() {
/*  494 */       return (new StringBuilder()).append(Object2ObjectOpenCustomHashMap.this.key[this.index]).append("=>").append(Object2ObjectOpenCustomHashMap.this.value[this.index]).toString();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  504 */     int pos = Object2ObjectOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  511 */     int last = -1;
/*      */     
/*  513 */     int c = Object2ObjectOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  517 */     boolean mustReturnNullKey = Object2ObjectOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     ObjectArrayList<K> wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  524 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  527 */       if (!hasNext())
/*  528 */         throw new NoSuchElementException(); 
/*  529 */       this.c--;
/*  530 */       if (this.mustReturnNullKey) {
/*  531 */         this.mustReturnNullKey = false;
/*  532 */         return this.last = Object2ObjectOpenCustomHashMap.this.n;
/*      */       } 
/*  534 */       K[] key = Object2ObjectOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  536 */         if (--this.pos < 0) {
/*      */           
/*  538 */           this.last = Integer.MIN_VALUE;
/*  539 */           K k = this.wrapped.get(-this.pos - 1);
/*  540 */           int p = HashCommon.mix(Object2ObjectOpenCustomHashMap.this.strategy.hashCode(k)) & Object2ObjectOpenCustomHashMap.this.mask;
/*  541 */           while (!Object2ObjectOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  542 */             p = p + 1 & Object2ObjectOpenCustomHashMap.this.mask; 
/*  543 */           return p;
/*      */         } 
/*  545 */         if (key[this.pos] != null) {
/*  546 */           return this.last = this.pos;
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
/*  560 */       K[] key = Object2ObjectOpenCustomHashMap.this.key; while (true) {
/*      */         K curr; int last;
/*  562 */         pos = (last = pos) + 1 & Object2ObjectOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  564 */           if ((curr = key[pos]) == null) {
/*  565 */             key[last] = null;
/*  566 */             Object2ObjectOpenCustomHashMap.this.value[last] = null;
/*      */             return;
/*      */           } 
/*  569 */           int slot = HashCommon.mix(Object2ObjectOpenCustomHashMap.this.strategy.hashCode(curr)) & Object2ObjectOpenCustomHashMap.this.mask;
/*  570 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  572 */           pos = pos + 1 & Object2ObjectOpenCustomHashMap.this.mask;
/*      */         } 
/*  574 */         if (pos < last) {
/*  575 */           if (this.wrapped == null)
/*  576 */             this.wrapped = new ObjectArrayList<>(2); 
/*  577 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  579 */         key[last] = curr;
/*  580 */         Object2ObjectOpenCustomHashMap.this.value[last] = Object2ObjectOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  584 */       if (this.last == -1)
/*  585 */         throw new IllegalStateException(); 
/*  586 */       if (this.last == Object2ObjectOpenCustomHashMap.this.n) {
/*  587 */         Object2ObjectOpenCustomHashMap.this.containsNullKey = false;
/*  588 */         Object2ObjectOpenCustomHashMap.this.key[Object2ObjectOpenCustomHashMap.this.n] = null;
/*  589 */         Object2ObjectOpenCustomHashMap.this.value[Object2ObjectOpenCustomHashMap.this.n] = null;
/*  590 */       } else if (this.pos >= 0) {
/*  591 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  594 */         Object2ObjectOpenCustomHashMap.this.remove(this.wrapped.set(-this.pos - 1, null));
/*  595 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  598 */       Object2ObjectOpenCustomHashMap.this.size--;
/*  599 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  604 */       int i = n;
/*  605 */       while (i-- != 0 && hasNext())
/*  606 */         nextEntry(); 
/*  607 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Object2ObjectMap.Entry<K, V>> { private Object2ObjectOpenCustomHashMap<K, V>.MapEntry entry;
/*      */     
/*      */     public Object2ObjectOpenCustomHashMap<K, V>.MapEntry next() {
/*  614 */       return this.entry = new Object2ObjectOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  618 */       super.remove();
/*  619 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Object2ObjectMap.Entry<K, V>> { private FastEntryIterator() {
/*  623 */       this.entry = new Object2ObjectOpenCustomHashMap.MapEntry();
/*      */     } private final Object2ObjectOpenCustomHashMap<K, V>.MapEntry entry;
/*      */     public Object2ObjectOpenCustomHashMap<K, V>.MapEntry next() {
/*  626 */       this.entry.index = nextEntry();
/*  627 */       return this.entry;
/*      */     } }
/*      */ 
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Object2ObjectMap.Entry<K, V>> implements Object2ObjectMap.FastEntrySet<K, V> {
/*      */     private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Object2ObjectMap.Entry<K, V>> iterator() {
/*  635 */       return new Object2ObjectOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Object2ObjectMap.Entry<K, V>> fastIterator() {
/*  639 */       return new Object2ObjectOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  644 */       if (!(o instanceof Map.Entry))
/*  645 */         return false; 
/*  646 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  647 */       K k = (K)e.getKey();
/*  648 */       V v = (V)e.getValue();
/*  649 */       if (Object2ObjectOpenCustomHashMap.this.strategy.equals(k, null)) {
/*  650 */         return (Object2ObjectOpenCustomHashMap.this.containsNullKey && Objects.equals(Object2ObjectOpenCustomHashMap.this.value[Object2ObjectOpenCustomHashMap.this.n], v));
/*      */       }
/*  652 */       K[] key = Object2ObjectOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  655 */       if ((curr = key[pos = HashCommon.mix(Object2ObjectOpenCustomHashMap.this.strategy.hashCode(k)) & Object2ObjectOpenCustomHashMap.this.mask]) == null)
/*  656 */         return false; 
/*  657 */       if (Object2ObjectOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  658 */         return Objects.equals(Object2ObjectOpenCustomHashMap.this.value[pos], v);
/*      */       }
/*      */       while (true) {
/*  661 */         if ((curr = key[pos = pos + 1 & Object2ObjectOpenCustomHashMap.this.mask]) == null)
/*  662 */           return false; 
/*  663 */         if (Object2ObjectOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  664 */           return Objects.equals(Object2ObjectOpenCustomHashMap.this.value[pos], v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  670 */       if (!(o instanceof Map.Entry))
/*  671 */         return false; 
/*  672 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  673 */       K k = (K)e.getKey();
/*  674 */       V v = (V)e.getValue();
/*  675 */       if (Object2ObjectOpenCustomHashMap.this.strategy.equals(k, null)) {
/*  676 */         if (Object2ObjectOpenCustomHashMap.this.containsNullKey && Objects.equals(Object2ObjectOpenCustomHashMap.this.value[Object2ObjectOpenCustomHashMap.this.n], v)) {
/*  677 */           Object2ObjectOpenCustomHashMap.this.removeNullEntry();
/*  678 */           return true;
/*      */         } 
/*  680 */         return false;
/*      */       } 
/*      */       
/*  683 */       K[] key = Object2ObjectOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  686 */       if ((curr = key[pos = HashCommon.mix(Object2ObjectOpenCustomHashMap.this.strategy.hashCode(k)) & Object2ObjectOpenCustomHashMap.this.mask]) == null)
/*  687 */         return false; 
/*  688 */       if (Object2ObjectOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  689 */         if (Objects.equals(Object2ObjectOpenCustomHashMap.this.value[pos], v)) {
/*  690 */           Object2ObjectOpenCustomHashMap.this.removeEntry(pos);
/*  691 */           return true;
/*      */         } 
/*  693 */         return false;
/*      */       } 
/*      */       while (true) {
/*  696 */         if ((curr = key[pos = pos + 1 & Object2ObjectOpenCustomHashMap.this.mask]) == null)
/*  697 */           return false; 
/*  698 */         if (Object2ObjectOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  699 */           Objects.equals(Object2ObjectOpenCustomHashMap.this.value[pos], v)) {
/*  700 */           Object2ObjectOpenCustomHashMap.this.removeEntry(pos);
/*  701 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  708 */       return Object2ObjectOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  712 */       Object2ObjectOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Object2ObjectMap.Entry<K, V>> consumer) {
/*  717 */       if (Object2ObjectOpenCustomHashMap.this.containsNullKey)
/*  718 */         consumer.accept(new AbstractObject2ObjectMap.BasicEntry<>(Object2ObjectOpenCustomHashMap.this.key[Object2ObjectOpenCustomHashMap.this.n], Object2ObjectOpenCustomHashMap.this.value[Object2ObjectOpenCustomHashMap.this.n])); 
/*  719 */       for (int pos = Object2ObjectOpenCustomHashMap.this.n; pos-- != 0;) {
/*  720 */         if (Object2ObjectOpenCustomHashMap.this.key[pos] != null)
/*  721 */           consumer.accept(new AbstractObject2ObjectMap.BasicEntry<>(Object2ObjectOpenCustomHashMap.this.key[pos], Object2ObjectOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Object2ObjectMap.Entry<K, V>> consumer) {
/*  726 */       AbstractObject2ObjectMap.BasicEntry<K, V> entry = new AbstractObject2ObjectMap.BasicEntry<>();
/*  727 */       if (Object2ObjectOpenCustomHashMap.this.containsNullKey) {
/*  728 */         entry.key = Object2ObjectOpenCustomHashMap.this.key[Object2ObjectOpenCustomHashMap.this.n];
/*  729 */         entry.value = Object2ObjectOpenCustomHashMap.this.value[Object2ObjectOpenCustomHashMap.this.n];
/*  730 */         consumer.accept(entry);
/*      */       } 
/*  732 */       for (int pos = Object2ObjectOpenCustomHashMap.this.n; pos-- != 0;) {
/*  733 */         if (Object2ObjectOpenCustomHashMap.this.key[pos] != null) {
/*  734 */           entry.key = Object2ObjectOpenCustomHashMap.this.key[pos];
/*  735 */           entry.value = Object2ObjectOpenCustomHashMap.this.value[pos];
/*  736 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Object2ObjectMap.FastEntrySet<K, V> object2ObjectEntrySet() {
/*  742 */     if (this.entries == null)
/*  743 */       this.entries = new MapEntrySet(); 
/*  744 */     return this.entries;
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
/*  761 */       return Object2ObjectOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractObjectSet<K> { private KeySet() {}
/*      */     
/*      */     public ObjectIterator<K> iterator() {
/*  767 */       return new Object2ObjectOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/*  772 */       if (Object2ObjectOpenCustomHashMap.this.containsNullKey)
/*  773 */         consumer.accept(Object2ObjectOpenCustomHashMap.this.key[Object2ObjectOpenCustomHashMap.this.n]); 
/*  774 */       for (int pos = Object2ObjectOpenCustomHashMap.this.n; pos-- != 0; ) {
/*  775 */         K k = Object2ObjectOpenCustomHashMap.this.key[pos];
/*  776 */         if (k != null)
/*  777 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  782 */       return Object2ObjectOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(Object k) {
/*  786 */       return Object2ObjectOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(Object k) {
/*  790 */       int oldSize = Object2ObjectOpenCustomHashMap.this.size;
/*  791 */       Object2ObjectOpenCustomHashMap.this.remove(k);
/*  792 */       return (Object2ObjectOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  796 */       Object2ObjectOpenCustomHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ObjectSet<K> keySet() {
/*  801 */     if (this.keys == null)
/*  802 */       this.keys = new KeySet(); 
/*  803 */     return this.keys;
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
/*  820 */       return Object2ObjectOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectCollection<V> values() {
/*  825 */     if (this.values == null)
/*  826 */       this.values = new AbstractObjectCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/*  829 */             return new Object2ObjectOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/*  833 */             return Object2ObjectOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(Object v) {
/*  837 */             return Object2ObjectOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/*  841 */             Object2ObjectOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(Consumer<? super V> consumer)
/*      */           {
/*  846 */             if (Object2ObjectOpenCustomHashMap.this.containsNullKey)
/*  847 */               consumer.accept(Object2ObjectOpenCustomHashMap.this.value[Object2ObjectOpenCustomHashMap.this.n]); 
/*  848 */             for (int pos = Object2ObjectOpenCustomHashMap.this.n; pos-- != 0;) {
/*  849 */               if (Object2ObjectOpenCustomHashMap.this.key[pos] != null)
/*  850 */                 consumer.accept(Object2ObjectOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/*  853 */     return this.values;
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
/*  870 */     return trim(this.size);
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
/*  894 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/*  895 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/*  896 */       return true; 
/*      */     try {
/*  898 */       rehash(l);
/*  899 */     } catch (OutOfMemoryError cantDoIt) {
/*  900 */       return false;
/*      */     } 
/*  902 */     return true;
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
/*  918 */     K[] key = this.key;
/*  919 */     V[] value = this.value;
/*  920 */     int mask = newN - 1;
/*  921 */     K[] newKey = (K[])new Object[newN + 1];
/*  922 */     V[] newValue = (V[])new Object[newN + 1];
/*  923 */     int i = this.n;
/*  924 */     for (int j = realSize(); j-- != 0; ) {
/*  925 */       while (key[--i] == null); int pos;
/*  926 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != null)
/*  927 */         while (newKey[pos = pos + 1 & mask] != null); 
/*  928 */       newKey[pos] = key[i];
/*  929 */       newValue[pos] = value[i];
/*      */     } 
/*  931 */     newValue[newN] = value[this.n];
/*  932 */     this.n = newN;
/*  933 */     this.mask = mask;
/*  934 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/*  935 */     this.key = newKey;
/*  936 */     this.value = newValue;
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
/*      */   public Object2ObjectOpenCustomHashMap<K, V> clone() {
/*      */     Object2ObjectOpenCustomHashMap<K, V> c;
/*      */     try {
/*  953 */       c = (Object2ObjectOpenCustomHashMap<K, V>)super.clone();
/*  954 */     } catch (CloneNotSupportedException cantHappen) {
/*  955 */       throw new InternalError();
/*      */     } 
/*  957 */     c.keys = null;
/*  958 */     c.values = null;
/*  959 */     c.entries = null;
/*  960 */     c.containsNullKey = this.containsNullKey;
/*  961 */     c.key = (K[])this.key.clone();
/*  962 */     c.value = (V[])this.value.clone();
/*  963 */     c.strategy = this.strategy;
/*  964 */     return c;
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
/*  977 */     int h = 0;
/*  978 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/*  979 */       while (this.key[i] == null)
/*  980 */         i++; 
/*  981 */       if (this != this.key[i])
/*  982 */         t = this.strategy.hashCode(this.key[i]); 
/*  983 */       if (this != this.value[i])
/*  984 */         t ^= (this.value[i] == null) ? 0 : this.value[i].hashCode(); 
/*  985 */       h += t;
/*  986 */       i++;
/*      */     } 
/*      */     
/*  989 */     if (this.containsNullKey)
/*  990 */       h += (this.value[this.n] == null) ? 0 : this.value[this.n].hashCode(); 
/*  991 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/*  994 */     K[] key = this.key;
/*  995 */     V[] value = this.value;
/*  996 */     MapIterator i = new MapIterator();
/*  997 */     s.defaultWriteObject();
/*  998 */     for (int j = this.size; j-- != 0; ) {
/*  999 */       int e = i.nextEntry();
/* 1000 */       s.writeObject(key[e]);
/* 1001 */       s.writeObject(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1006 */     s.defaultReadObject();
/* 1007 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1008 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1009 */     this.mask = this.n - 1;
/* 1010 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1011 */     V[] value = this.value = (V[])new Object[this.n + 1];
/*      */ 
/*      */     
/* 1014 */     for (int i = this.size; i-- != 0; ) {
/* 1015 */       int pos; K k = (K)s.readObject();
/* 1016 */       V v = (V)s.readObject();
/* 1017 */       if (this.strategy.equals(k, null)) {
/* 1018 */         pos = this.n;
/* 1019 */         this.containsNullKey = true;
/*      */       } else {
/* 1021 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1022 */         while (key[pos] != null)
/* 1023 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1025 */       key[pos] = k;
/* 1026 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2ObjectOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */