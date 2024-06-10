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
/*      */ 
/*      */ public class Reference2ReferenceOpenCustomHashMap<K, V>
/*      */   extends AbstractReference2ReferenceMap<K, V>
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
/*      */   protected transient Reference2ReferenceMap.FastEntrySet<K, V> entries;
/*      */   protected transient ReferenceSet<K> keys;
/*      */   protected transient ReferenceCollection<V> values;
/*      */   
/*      */   public Reference2ReferenceOpenCustomHashMap(int expected, float f, Hash.Strategy<? super K> strategy) {
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
/*      */   public Reference2ReferenceOpenCustomHashMap(int expected, Hash.Strategy<? super K> strategy) {
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
/*      */   public Reference2ReferenceOpenCustomHashMap(Hash.Strategy<? super K> strategy) {
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
/*      */   public Reference2ReferenceOpenCustomHashMap(Map<? extends K, ? extends V> m, float f, Hash.Strategy<? super K> strategy) {
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
/*      */   
/*      */   public Reference2ReferenceOpenCustomHashMap(Map<? extends K, ? extends V> m, Hash.Strategy<? super K> strategy) {
/*  165 */     this(m, 0.75F, strategy);
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
/*      */   public Reference2ReferenceOpenCustomHashMap(Reference2ReferenceMap<K, V> m, float f, Hash.Strategy<? super K> strategy) {
/*  179 */     this(m.size(), f, strategy);
/*  180 */     putAll(m);
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
/*      */   public Reference2ReferenceOpenCustomHashMap(Reference2ReferenceMap<K, V> m, Hash.Strategy<? super K> strategy) {
/*  193 */     this(m, 0.75F, strategy);
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
/*      */   public Reference2ReferenceOpenCustomHashMap(K[] k, V[] v, float f, Hash.Strategy<? super K> strategy) {
/*  211 */     this(k.length, f, strategy);
/*  212 */     if (k.length != v.length) {
/*  213 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  215 */     for (int i = 0; i < k.length; i++) {
/*  216 */       put(k[i], v[i]);
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
/*      */   public Reference2ReferenceOpenCustomHashMap(K[] k, V[] v, Hash.Strategy<? super K> strategy) {
/*  232 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Hash.Strategy<? super K> strategy() {
/*  240 */     return this.strategy;
/*      */   }
/*      */   private int realSize() {
/*  243 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  246 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  247 */     if (needed > this.n)
/*  248 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  251 */     int needed = (int)Math.min(1073741824L, 
/*  252 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  253 */     if (needed > this.n)
/*  254 */       rehash(needed); 
/*      */   }
/*      */   private V removeEntry(int pos) {
/*  257 */     V oldValue = this.value[pos];
/*  258 */     this.value[pos] = null;
/*  259 */     this.size--;
/*  260 */     shiftKeys(pos);
/*  261 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  262 */       rehash(this.n / 2); 
/*  263 */     return oldValue;
/*      */   }
/*      */   private V removeNullEntry() {
/*  266 */     this.containsNullKey = false;
/*  267 */     this.key[this.n] = null;
/*  268 */     V oldValue = this.value[this.n];
/*  269 */     this.value[this.n] = null;
/*  270 */     this.size--;
/*  271 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  272 */       rehash(this.n / 2); 
/*  273 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends V> m) {
/*  277 */     if (this.f <= 0.5D) {
/*  278 */       ensureCapacity(m.size());
/*      */     } else {
/*  280 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  282 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(K k) {
/*  286 */     if (this.strategy.equals(k, null)) {
/*  287 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  289 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  292 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  293 */       return -(pos + 1); 
/*  294 */     if (this.strategy.equals(k, curr)) {
/*  295 */       return pos;
/*      */     }
/*      */     while (true) {
/*  298 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  299 */         return -(pos + 1); 
/*  300 */       if (this.strategy.equals(k, curr))
/*  301 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, K k, V v) {
/*  305 */     if (pos == this.n)
/*  306 */       this.containsNullKey = true; 
/*  307 */     this.key[pos] = k;
/*  308 */     this.value[pos] = v;
/*  309 */     if (this.size++ >= this.maxFill) {
/*  310 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public V put(K k, V v) {
/*  316 */     int pos = find(k);
/*  317 */     if (pos < 0) {
/*  318 */       insert(-pos - 1, k, v);
/*  319 */       return this.defRetValue;
/*      */     } 
/*  321 */     V oldValue = this.value[pos];
/*  322 */     this.value[pos] = v;
/*  323 */     return oldValue;
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
/*  336 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  338 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  340 */         if ((curr = key[pos]) == null) {
/*  341 */           key[last] = null;
/*  342 */           this.value[last] = null;
/*      */           return;
/*      */         } 
/*  345 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  346 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  348 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  350 */       key[last] = curr;
/*  351 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public V remove(Object k) {
/*  357 */     if (this.strategy.equals(k, null)) {
/*  358 */       if (this.containsNullKey)
/*  359 */         return removeNullEntry(); 
/*  360 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  363 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  366 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  367 */       return this.defRetValue; 
/*  368 */     if (this.strategy.equals(k, curr))
/*  369 */       return removeEntry(pos); 
/*      */     while (true) {
/*  371 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  372 */         return this.defRetValue; 
/*  373 */       if (this.strategy.equals(k, curr)) {
/*  374 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public V get(Object k) {
/*  380 */     if (this.strategy.equals(k, null)) {
/*  381 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  383 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  386 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  387 */       return this.defRetValue; 
/*  388 */     if (this.strategy.equals(k, curr)) {
/*  389 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  392 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  393 */         return this.defRetValue; 
/*  394 */       if (this.strategy.equals(k, curr)) {
/*  395 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  401 */     if (this.strategy.equals(k, null)) {
/*  402 */       return this.containsNullKey;
/*      */     }
/*  404 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  407 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  408 */       return false; 
/*  409 */     if (this.strategy.equals(k, curr)) {
/*  410 */       return true;
/*      */     }
/*      */     while (true) {
/*  413 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  414 */         return false; 
/*  415 */       if (this.strategy.equals(k, curr))
/*  416 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(Object v) {
/*  421 */     V[] value = this.value;
/*  422 */     K[] key = this.key;
/*  423 */     if (this.containsNullKey && value[this.n] == v)
/*  424 */       return true; 
/*  425 */     for (int i = this.n; i-- != 0;) {
/*  426 */       if (key[i] != null && value[i] == v)
/*  427 */         return true; 
/*  428 */     }  return false;
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
/*  439 */     if (this.size == 0)
/*      */       return; 
/*  441 */     this.size = 0;
/*  442 */     this.containsNullKey = false;
/*  443 */     Arrays.fill((Object[])this.key, (Object)null);
/*  444 */     Arrays.fill((Object[])this.value, (Object)null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  448 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  452 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Reference2ReferenceMap.Entry<K, V>, Map.Entry<K, V>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  464 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public K getKey() {
/*  470 */       return Reference2ReferenceOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public V getValue() {
/*  474 */       return Reference2ReferenceOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public V setValue(V v) {
/*  478 */       V oldValue = Reference2ReferenceOpenCustomHashMap.this.value[this.index];
/*  479 */       Reference2ReferenceOpenCustomHashMap.this.value[this.index] = v;
/*  480 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  485 */       if (!(o instanceof Map.Entry))
/*  486 */         return false; 
/*  487 */       Map.Entry<K, V> e = (Map.Entry<K, V>)o;
/*  488 */       return (Reference2ReferenceOpenCustomHashMap.this.strategy.equals(Reference2ReferenceOpenCustomHashMap.this.key[this.index], e.getKey()) && Reference2ReferenceOpenCustomHashMap.this.value[this.index] == e.getValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  492 */       return Reference2ReferenceOpenCustomHashMap.this.strategy.hashCode(Reference2ReferenceOpenCustomHashMap.this.key[this.index]) ^ (
/*  493 */         (Reference2ReferenceOpenCustomHashMap.this.value[this.index] == null) ? 0 : System.identityHashCode(Reference2ReferenceOpenCustomHashMap.this.value[this.index]));
/*      */     }
/*      */     
/*      */     public String toString() {
/*  497 */       return (new StringBuilder()).append(Reference2ReferenceOpenCustomHashMap.this.key[this.index]).append("=>").append(Reference2ReferenceOpenCustomHashMap.this.value[this.index]).toString();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  507 */     int pos = Reference2ReferenceOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  514 */     int last = -1;
/*      */     
/*  516 */     int c = Reference2ReferenceOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  520 */     boolean mustReturnNullKey = Reference2ReferenceOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     ReferenceArrayList<K> wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  527 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  530 */       if (!hasNext())
/*  531 */         throw new NoSuchElementException(); 
/*  532 */       this.c--;
/*  533 */       if (this.mustReturnNullKey) {
/*  534 */         this.mustReturnNullKey = false;
/*  535 */         return this.last = Reference2ReferenceOpenCustomHashMap.this.n;
/*      */       } 
/*  537 */       K[] key = Reference2ReferenceOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  539 */         if (--this.pos < 0) {
/*      */           
/*  541 */           this.last = Integer.MIN_VALUE;
/*  542 */           K k = this.wrapped.get(-this.pos - 1);
/*  543 */           int p = HashCommon.mix(Reference2ReferenceOpenCustomHashMap.this.strategy.hashCode(k)) & Reference2ReferenceOpenCustomHashMap.this.mask;
/*  544 */           while (!Reference2ReferenceOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  545 */             p = p + 1 & Reference2ReferenceOpenCustomHashMap.this.mask; 
/*  546 */           return p;
/*      */         } 
/*  548 */         if (key[this.pos] != null) {
/*  549 */           return this.last = this.pos;
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
/*  563 */       K[] key = Reference2ReferenceOpenCustomHashMap.this.key; while (true) {
/*      */         K curr; int last;
/*  565 */         pos = (last = pos) + 1 & Reference2ReferenceOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  567 */           if ((curr = key[pos]) == null) {
/*  568 */             key[last] = null;
/*  569 */             Reference2ReferenceOpenCustomHashMap.this.value[last] = null;
/*      */             return;
/*      */           } 
/*  572 */           int slot = HashCommon.mix(Reference2ReferenceOpenCustomHashMap.this.strategy.hashCode(curr)) & Reference2ReferenceOpenCustomHashMap.this.mask;
/*  573 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  575 */           pos = pos + 1 & Reference2ReferenceOpenCustomHashMap.this.mask;
/*      */         } 
/*  577 */         if (pos < last) {
/*  578 */           if (this.wrapped == null)
/*  579 */             this.wrapped = new ReferenceArrayList<>(2); 
/*  580 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  582 */         key[last] = curr;
/*  583 */         Reference2ReferenceOpenCustomHashMap.this.value[last] = Reference2ReferenceOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  587 */       if (this.last == -1)
/*  588 */         throw new IllegalStateException(); 
/*  589 */       if (this.last == Reference2ReferenceOpenCustomHashMap.this.n) {
/*  590 */         Reference2ReferenceOpenCustomHashMap.this.containsNullKey = false;
/*  591 */         Reference2ReferenceOpenCustomHashMap.this.key[Reference2ReferenceOpenCustomHashMap.this.n] = null;
/*  592 */         Reference2ReferenceOpenCustomHashMap.this.value[Reference2ReferenceOpenCustomHashMap.this.n] = null;
/*  593 */       } else if (this.pos >= 0) {
/*  594 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  597 */         Reference2ReferenceOpenCustomHashMap.this.remove(this.wrapped.set(-this.pos - 1, null));
/*  598 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  601 */       Reference2ReferenceOpenCustomHashMap.this.size--;
/*  602 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  607 */       int i = n;
/*  608 */       while (i-- != 0 && hasNext())
/*  609 */         nextEntry(); 
/*  610 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Reference2ReferenceMap.Entry<K, V>> { private Reference2ReferenceOpenCustomHashMap<K, V>.MapEntry entry;
/*      */     
/*      */     public Reference2ReferenceOpenCustomHashMap<K, V>.MapEntry next() {
/*  617 */       return this.entry = new Reference2ReferenceOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  621 */       super.remove();
/*  622 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Reference2ReferenceMap.Entry<K, V>> { private FastEntryIterator() {
/*  626 */       this.entry = new Reference2ReferenceOpenCustomHashMap.MapEntry();
/*      */     } private final Reference2ReferenceOpenCustomHashMap<K, V>.MapEntry entry;
/*      */     public Reference2ReferenceOpenCustomHashMap<K, V>.MapEntry next() {
/*  629 */       this.entry.index = nextEntry();
/*  630 */       return this.entry;
/*      */     } }
/*      */ 
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Reference2ReferenceMap.Entry<K, V>> implements Reference2ReferenceMap.FastEntrySet<K, V> {
/*      */     private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Reference2ReferenceMap.Entry<K, V>> iterator() {
/*  638 */       return new Reference2ReferenceOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Reference2ReferenceMap.Entry<K, V>> fastIterator() {
/*  642 */       return new Reference2ReferenceOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  647 */       if (!(o instanceof Map.Entry))
/*  648 */         return false; 
/*  649 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  650 */       K k = (K)e.getKey();
/*  651 */       V v = (V)e.getValue();
/*  652 */       if (Reference2ReferenceOpenCustomHashMap.this.strategy.equals(k, null)) {
/*  653 */         return (Reference2ReferenceOpenCustomHashMap.this.containsNullKey && Reference2ReferenceOpenCustomHashMap.this.value[Reference2ReferenceOpenCustomHashMap.this.n] == v);
/*      */       }
/*  655 */       K[] key = Reference2ReferenceOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  658 */       if ((curr = key[pos = HashCommon.mix(Reference2ReferenceOpenCustomHashMap.this.strategy.hashCode(k)) & Reference2ReferenceOpenCustomHashMap.this.mask]) == null)
/*  659 */         return false; 
/*  660 */       if (Reference2ReferenceOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  661 */         return (Reference2ReferenceOpenCustomHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  664 */         if ((curr = key[pos = pos + 1 & Reference2ReferenceOpenCustomHashMap.this.mask]) == null)
/*  665 */           return false; 
/*  666 */         if (Reference2ReferenceOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  667 */           return (Reference2ReferenceOpenCustomHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  673 */       if (!(o instanceof Map.Entry))
/*  674 */         return false; 
/*  675 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  676 */       K k = (K)e.getKey();
/*  677 */       V v = (V)e.getValue();
/*  678 */       if (Reference2ReferenceOpenCustomHashMap.this.strategy.equals(k, null)) {
/*  679 */         if (Reference2ReferenceOpenCustomHashMap.this.containsNullKey && Reference2ReferenceOpenCustomHashMap.this.value[Reference2ReferenceOpenCustomHashMap.this.n] == v) {
/*  680 */           Reference2ReferenceOpenCustomHashMap.this.removeNullEntry();
/*  681 */           return true;
/*      */         } 
/*  683 */         return false;
/*      */       } 
/*      */       
/*  686 */       K[] key = Reference2ReferenceOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  689 */       if ((curr = key[pos = HashCommon.mix(Reference2ReferenceOpenCustomHashMap.this.strategy.hashCode(k)) & Reference2ReferenceOpenCustomHashMap.this.mask]) == null)
/*  690 */         return false; 
/*  691 */       if (Reference2ReferenceOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  692 */         if (Reference2ReferenceOpenCustomHashMap.this.value[pos] == v) {
/*  693 */           Reference2ReferenceOpenCustomHashMap.this.removeEntry(pos);
/*  694 */           return true;
/*      */         } 
/*  696 */         return false;
/*      */       } 
/*      */       while (true) {
/*  699 */         if ((curr = key[pos = pos + 1 & Reference2ReferenceOpenCustomHashMap.this.mask]) == null)
/*  700 */           return false; 
/*  701 */         if (Reference2ReferenceOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  702 */           Reference2ReferenceOpenCustomHashMap.this.value[pos] == v) {
/*  703 */           Reference2ReferenceOpenCustomHashMap.this.removeEntry(pos);
/*  704 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  711 */       return Reference2ReferenceOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  715 */       Reference2ReferenceOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Reference2ReferenceMap.Entry<K, V>> consumer) {
/*  720 */       if (Reference2ReferenceOpenCustomHashMap.this.containsNullKey)
/*  721 */         consumer.accept(new AbstractReference2ReferenceMap.BasicEntry<>(Reference2ReferenceOpenCustomHashMap.this.key[Reference2ReferenceOpenCustomHashMap.this.n], Reference2ReferenceOpenCustomHashMap.this.value[Reference2ReferenceOpenCustomHashMap.this.n])); 
/*  722 */       for (int pos = Reference2ReferenceOpenCustomHashMap.this.n; pos-- != 0;) {
/*  723 */         if (Reference2ReferenceOpenCustomHashMap.this.key[pos] != null)
/*  724 */           consumer.accept(new AbstractReference2ReferenceMap.BasicEntry<>(Reference2ReferenceOpenCustomHashMap.this.key[pos], Reference2ReferenceOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Reference2ReferenceMap.Entry<K, V>> consumer) {
/*  729 */       AbstractReference2ReferenceMap.BasicEntry<K, V> entry = new AbstractReference2ReferenceMap.BasicEntry<>();
/*  730 */       if (Reference2ReferenceOpenCustomHashMap.this.containsNullKey) {
/*  731 */         entry.key = Reference2ReferenceOpenCustomHashMap.this.key[Reference2ReferenceOpenCustomHashMap.this.n];
/*  732 */         entry.value = Reference2ReferenceOpenCustomHashMap.this.value[Reference2ReferenceOpenCustomHashMap.this.n];
/*  733 */         consumer.accept(entry);
/*      */       } 
/*  735 */       for (int pos = Reference2ReferenceOpenCustomHashMap.this.n; pos-- != 0;) {
/*  736 */         if (Reference2ReferenceOpenCustomHashMap.this.key[pos] != null) {
/*  737 */           entry.key = Reference2ReferenceOpenCustomHashMap.this.key[pos];
/*  738 */           entry.value = Reference2ReferenceOpenCustomHashMap.this.value[pos];
/*  739 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Reference2ReferenceMap.FastEntrySet<K, V> reference2ReferenceEntrySet() {
/*  745 */     if (this.entries == null)
/*  746 */       this.entries = new MapEntrySet(); 
/*  747 */     return this.entries;
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
/*  764 */       return Reference2ReferenceOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractReferenceSet<K> { private KeySet() {}
/*      */     
/*      */     public ObjectIterator<K> iterator() {
/*  770 */       return new Reference2ReferenceOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/*  775 */       if (Reference2ReferenceOpenCustomHashMap.this.containsNullKey)
/*  776 */         consumer.accept(Reference2ReferenceOpenCustomHashMap.this.key[Reference2ReferenceOpenCustomHashMap.this.n]); 
/*  777 */       for (int pos = Reference2ReferenceOpenCustomHashMap.this.n; pos-- != 0; ) {
/*  778 */         K k = Reference2ReferenceOpenCustomHashMap.this.key[pos];
/*  779 */         if (k != null)
/*  780 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  785 */       return Reference2ReferenceOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(Object k) {
/*  789 */       return Reference2ReferenceOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(Object k) {
/*  793 */       int oldSize = Reference2ReferenceOpenCustomHashMap.this.size;
/*  794 */       Reference2ReferenceOpenCustomHashMap.this.remove(k);
/*  795 */       return (Reference2ReferenceOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  799 */       Reference2ReferenceOpenCustomHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ReferenceSet<K> keySet() {
/*  804 */     if (this.keys == null)
/*  805 */       this.keys = new KeySet(); 
/*  806 */     return this.keys;
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
/*  823 */       return Reference2ReferenceOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public ReferenceCollection<V> values() {
/*  828 */     if (this.values == null)
/*  829 */       this.values = new AbstractReferenceCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/*  832 */             return new Reference2ReferenceOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/*  836 */             return Reference2ReferenceOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(Object v) {
/*  840 */             return Reference2ReferenceOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/*  844 */             Reference2ReferenceOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(Consumer<? super V> consumer)
/*      */           {
/*  849 */             if (Reference2ReferenceOpenCustomHashMap.this.containsNullKey)
/*  850 */               consumer.accept(Reference2ReferenceOpenCustomHashMap.this.value[Reference2ReferenceOpenCustomHashMap.this.n]); 
/*  851 */             for (int pos = Reference2ReferenceOpenCustomHashMap.this.n; pos-- != 0;) {
/*  852 */               if (Reference2ReferenceOpenCustomHashMap.this.key[pos] != null)
/*  853 */                 consumer.accept(Reference2ReferenceOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/*  856 */     return this.values;
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
/*  873 */     return trim(this.size);
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
/*  897 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/*  898 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/*  899 */       return true; 
/*      */     try {
/*  901 */       rehash(l);
/*  902 */     } catch (OutOfMemoryError cantDoIt) {
/*  903 */       return false;
/*      */     } 
/*  905 */     return true;
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
/*  921 */     K[] key = this.key;
/*  922 */     V[] value = this.value;
/*  923 */     int mask = newN - 1;
/*  924 */     K[] newKey = (K[])new Object[newN + 1];
/*  925 */     V[] newValue = (V[])new Object[newN + 1];
/*  926 */     int i = this.n;
/*  927 */     for (int j = realSize(); j-- != 0; ) {
/*  928 */       while (key[--i] == null); int pos;
/*  929 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != null)
/*  930 */         while (newKey[pos = pos + 1 & mask] != null); 
/*  931 */       newKey[pos] = key[i];
/*  932 */       newValue[pos] = value[i];
/*      */     } 
/*  934 */     newValue[newN] = value[this.n];
/*  935 */     this.n = newN;
/*  936 */     this.mask = mask;
/*  937 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/*  938 */     this.key = newKey;
/*  939 */     this.value = newValue;
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
/*      */   public Reference2ReferenceOpenCustomHashMap<K, V> clone() {
/*      */     Reference2ReferenceOpenCustomHashMap<K, V> c;
/*      */     try {
/*  956 */       c = (Reference2ReferenceOpenCustomHashMap<K, V>)super.clone();
/*  957 */     } catch (CloneNotSupportedException cantHappen) {
/*  958 */       throw new InternalError();
/*      */     } 
/*  960 */     c.keys = null;
/*  961 */     c.values = null;
/*  962 */     c.entries = null;
/*  963 */     c.containsNullKey = this.containsNullKey;
/*  964 */     c.key = (K[])this.key.clone();
/*  965 */     c.value = (V[])this.value.clone();
/*  966 */     c.strategy = this.strategy;
/*  967 */     return c;
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
/*  980 */     int h = 0;
/*  981 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/*  982 */       while (this.key[i] == null)
/*  983 */         i++; 
/*  984 */       if (this != this.key[i])
/*  985 */         t = this.strategy.hashCode(this.key[i]); 
/*  986 */       if (this != this.value[i])
/*  987 */         t ^= (this.value[i] == null) ? 0 : System.identityHashCode(this.value[i]); 
/*  988 */       h += t;
/*  989 */       i++;
/*      */     } 
/*      */     
/*  992 */     if (this.containsNullKey)
/*  993 */       h += (this.value[this.n] == null) ? 0 : System.identityHashCode(this.value[this.n]); 
/*  994 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/*  997 */     K[] key = this.key;
/*  998 */     V[] value = this.value;
/*  999 */     MapIterator i = new MapIterator();
/* 1000 */     s.defaultWriteObject();
/* 1001 */     for (int j = this.size; j-- != 0; ) {
/* 1002 */       int e = i.nextEntry();
/* 1003 */       s.writeObject(key[e]);
/* 1004 */       s.writeObject(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1009 */     s.defaultReadObject();
/* 1010 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1011 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1012 */     this.mask = this.n - 1;
/* 1013 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1014 */     V[] value = this.value = (V[])new Object[this.n + 1];
/*      */ 
/*      */     
/* 1017 */     for (int i = this.size; i-- != 0; ) {
/* 1018 */       int pos; K k = (K)s.readObject();
/* 1019 */       V v = (V)s.readObject();
/* 1020 */       if (this.strategy.equals(k, null)) {
/* 1021 */         pos = this.n;
/* 1022 */         this.containsNullKey = true;
/*      */       } else {
/* 1024 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1025 */         while (key[pos] != null)
/* 1026 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1028 */       key[pos] = k;
/* 1029 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Reference2ReferenceOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */