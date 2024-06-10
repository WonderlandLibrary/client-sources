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
/*      */ public class Reference2ObjectOpenCustomHashMap<K, V>
/*      */   extends AbstractReference2ObjectMap<K, V>
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
/*      */   protected transient Reference2ObjectMap.FastEntrySet<K, V> entries;
/*      */   protected transient ReferenceSet<K> keys;
/*      */   protected transient ObjectCollection<V> values;
/*      */   
/*      */   public Reference2ObjectOpenCustomHashMap(int expected, float f, Hash.Strategy<? super K> strategy) {
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
/*      */   public Reference2ObjectOpenCustomHashMap(int expected, Hash.Strategy<? super K> strategy) {
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
/*      */   public Reference2ObjectOpenCustomHashMap(Hash.Strategy<? super K> strategy) {
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
/*      */   public Reference2ObjectOpenCustomHashMap(Map<? extends K, ? extends V> m, float f, Hash.Strategy<? super K> strategy) {
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
/*      */   public Reference2ObjectOpenCustomHashMap(Map<? extends K, ? extends V> m, Hash.Strategy<? super K> strategy) {
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
/*      */   public Reference2ObjectOpenCustomHashMap(Reference2ObjectMap<K, V> m, float f, Hash.Strategy<? super K> strategy) {
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
/*      */   public Reference2ObjectOpenCustomHashMap(Reference2ObjectMap<K, V> m, Hash.Strategy<? super K> strategy) {
/*  192 */     this(m, 0.75F, strategy);
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
/*      */   public Reference2ObjectOpenCustomHashMap(K[] k, V[] v, float f, Hash.Strategy<? super K> strategy) {
/*  210 */     this(k.length, f, strategy);
/*  211 */     if (k.length != v.length) {
/*  212 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  214 */     for (int i = 0; i < k.length; i++) {
/*  215 */       put(k[i], v[i]);
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
/*      */   public Reference2ObjectOpenCustomHashMap(K[] k, V[] v, Hash.Strategy<? super K> strategy) {
/*  231 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Hash.Strategy<? super K> strategy() {
/*  239 */     return this.strategy;
/*      */   }
/*      */   private int realSize() {
/*  242 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  245 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  246 */     if (needed > this.n)
/*  247 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  250 */     int needed = (int)Math.min(1073741824L, 
/*  251 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  252 */     if (needed > this.n)
/*  253 */       rehash(needed); 
/*      */   }
/*      */   private V removeEntry(int pos) {
/*  256 */     V oldValue = this.value[pos];
/*  257 */     this.value[pos] = null;
/*  258 */     this.size--;
/*  259 */     shiftKeys(pos);
/*  260 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  261 */       rehash(this.n / 2); 
/*  262 */     return oldValue;
/*      */   }
/*      */   private V removeNullEntry() {
/*  265 */     this.containsNullKey = false;
/*  266 */     this.key[this.n] = null;
/*  267 */     V oldValue = this.value[this.n];
/*  268 */     this.value[this.n] = null;
/*  269 */     this.size--;
/*  270 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  271 */       rehash(this.n / 2); 
/*  272 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends V> m) {
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
/*      */   private void insert(int pos, K k, V v) {
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
/*      */   public V put(K k, V v) {
/*  315 */     int pos = find(k);
/*  316 */     if (pos < 0) {
/*  317 */       insert(-pos - 1, k, v);
/*  318 */       return this.defRetValue;
/*      */     } 
/*  320 */     V oldValue = this.value[pos];
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
/*  341 */           this.value[last] = null;
/*      */           return;
/*      */         } 
/*  344 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  345 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  347 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  349 */       key[last] = curr;
/*  350 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public V remove(Object k) {
/*  356 */     if (this.strategy.equals(k, null)) {
/*  357 */       if (this.containsNullKey)
/*  358 */         return removeNullEntry(); 
/*  359 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  362 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  365 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  366 */       return this.defRetValue; 
/*  367 */     if (this.strategy.equals(k, curr))
/*  368 */       return removeEntry(pos); 
/*      */     while (true) {
/*  370 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  371 */         return this.defRetValue; 
/*  372 */       if (this.strategy.equals(k, curr)) {
/*  373 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public V get(Object k) {
/*  379 */     if (this.strategy.equals(k, null)) {
/*  380 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  382 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  385 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  386 */       return this.defRetValue; 
/*  387 */     if (this.strategy.equals(k, curr)) {
/*  388 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  391 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  392 */         return this.defRetValue; 
/*  393 */       if (this.strategy.equals(k, curr)) {
/*  394 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  400 */     if (this.strategy.equals(k, null)) {
/*  401 */       return this.containsNullKey;
/*      */     }
/*  403 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  406 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  407 */       return false; 
/*  408 */     if (this.strategy.equals(k, curr)) {
/*  409 */       return true;
/*      */     }
/*      */     while (true) {
/*  412 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  413 */         return false; 
/*  414 */       if (this.strategy.equals(k, curr))
/*  415 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(Object v) {
/*  420 */     V[] value = this.value;
/*  421 */     K[] key = this.key;
/*  422 */     if (this.containsNullKey && Objects.equals(value[this.n], v))
/*  423 */       return true; 
/*  424 */     for (int i = this.n; i-- != 0;) {
/*  425 */       if (key[i] != null && Objects.equals(value[i], v))
/*  426 */         return true; 
/*  427 */     }  return false;
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
/*  438 */     if (this.size == 0)
/*      */       return; 
/*  440 */     this.size = 0;
/*  441 */     this.containsNullKey = false;
/*  442 */     Arrays.fill((Object[])this.key, (Object)null);
/*  443 */     Arrays.fill((Object[])this.value, (Object)null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  447 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  451 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Reference2ObjectMap.Entry<K, V>, Map.Entry<K, V>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  463 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public K getKey() {
/*  469 */       return Reference2ObjectOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public V getValue() {
/*  473 */       return Reference2ObjectOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public V setValue(V v) {
/*  477 */       V oldValue = Reference2ObjectOpenCustomHashMap.this.value[this.index];
/*  478 */       Reference2ObjectOpenCustomHashMap.this.value[this.index] = v;
/*  479 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  484 */       if (!(o instanceof Map.Entry))
/*  485 */         return false; 
/*  486 */       Map.Entry<K, V> e = (Map.Entry<K, V>)o;
/*  487 */       return (Reference2ObjectOpenCustomHashMap.this.strategy.equals(Reference2ObjectOpenCustomHashMap.this.key[this.index], e.getKey()) && 
/*  488 */         Objects.equals(Reference2ObjectOpenCustomHashMap.this.value[this.index], e.getValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  492 */       return Reference2ObjectOpenCustomHashMap.this.strategy.hashCode(Reference2ObjectOpenCustomHashMap.this.key[this.index]) ^ ((Reference2ObjectOpenCustomHashMap.this.value[this.index] == null) ? 0 : Reference2ObjectOpenCustomHashMap.this.value[this.index].hashCode());
/*      */     }
/*      */     
/*      */     public String toString() {
/*  496 */       return (new StringBuilder()).append(Reference2ObjectOpenCustomHashMap.this.key[this.index]).append("=>").append(Reference2ObjectOpenCustomHashMap.this.value[this.index]).toString();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  506 */     int pos = Reference2ObjectOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  513 */     int last = -1;
/*      */     
/*  515 */     int c = Reference2ObjectOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  519 */     boolean mustReturnNullKey = Reference2ObjectOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     ReferenceArrayList<K> wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  526 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  529 */       if (!hasNext())
/*  530 */         throw new NoSuchElementException(); 
/*  531 */       this.c--;
/*  532 */       if (this.mustReturnNullKey) {
/*  533 */         this.mustReturnNullKey = false;
/*  534 */         return this.last = Reference2ObjectOpenCustomHashMap.this.n;
/*      */       } 
/*  536 */       K[] key = Reference2ObjectOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  538 */         if (--this.pos < 0) {
/*      */           
/*  540 */           this.last = Integer.MIN_VALUE;
/*  541 */           K k = this.wrapped.get(-this.pos - 1);
/*  542 */           int p = HashCommon.mix(Reference2ObjectOpenCustomHashMap.this.strategy.hashCode(k)) & Reference2ObjectOpenCustomHashMap.this.mask;
/*  543 */           while (!Reference2ObjectOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  544 */             p = p + 1 & Reference2ObjectOpenCustomHashMap.this.mask; 
/*  545 */           return p;
/*      */         } 
/*  547 */         if (key[this.pos] != null) {
/*  548 */           return this.last = this.pos;
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
/*  562 */       K[] key = Reference2ObjectOpenCustomHashMap.this.key; while (true) {
/*      */         K curr; int last;
/*  564 */         pos = (last = pos) + 1 & Reference2ObjectOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  566 */           if ((curr = key[pos]) == null) {
/*  567 */             key[last] = null;
/*  568 */             Reference2ObjectOpenCustomHashMap.this.value[last] = null;
/*      */             return;
/*      */           } 
/*  571 */           int slot = HashCommon.mix(Reference2ObjectOpenCustomHashMap.this.strategy.hashCode(curr)) & Reference2ObjectOpenCustomHashMap.this.mask;
/*  572 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  574 */           pos = pos + 1 & Reference2ObjectOpenCustomHashMap.this.mask;
/*      */         } 
/*  576 */         if (pos < last) {
/*  577 */           if (this.wrapped == null)
/*  578 */             this.wrapped = new ReferenceArrayList<>(2); 
/*  579 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  581 */         key[last] = curr;
/*  582 */         Reference2ObjectOpenCustomHashMap.this.value[last] = Reference2ObjectOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  586 */       if (this.last == -1)
/*  587 */         throw new IllegalStateException(); 
/*  588 */       if (this.last == Reference2ObjectOpenCustomHashMap.this.n) {
/*  589 */         Reference2ObjectOpenCustomHashMap.this.containsNullKey = false;
/*  590 */         Reference2ObjectOpenCustomHashMap.this.key[Reference2ObjectOpenCustomHashMap.this.n] = null;
/*  591 */         Reference2ObjectOpenCustomHashMap.this.value[Reference2ObjectOpenCustomHashMap.this.n] = null;
/*  592 */       } else if (this.pos >= 0) {
/*  593 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  596 */         Reference2ObjectOpenCustomHashMap.this.remove(this.wrapped.set(-this.pos - 1, null));
/*  597 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  600 */       Reference2ObjectOpenCustomHashMap.this.size--;
/*  601 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  606 */       int i = n;
/*  607 */       while (i-- != 0 && hasNext())
/*  608 */         nextEntry(); 
/*  609 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Reference2ObjectMap.Entry<K, V>> { private Reference2ObjectOpenCustomHashMap<K, V>.MapEntry entry;
/*      */     
/*      */     public Reference2ObjectOpenCustomHashMap<K, V>.MapEntry next() {
/*  616 */       return this.entry = new Reference2ObjectOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  620 */       super.remove();
/*  621 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Reference2ObjectMap.Entry<K, V>> { private FastEntryIterator() {
/*  625 */       this.entry = new Reference2ObjectOpenCustomHashMap.MapEntry();
/*      */     } private final Reference2ObjectOpenCustomHashMap<K, V>.MapEntry entry;
/*      */     public Reference2ObjectOpenCustomHashMap<K, V>.MapEntry next() {
/*  628 */       this.entry.index = nextEntry();
/*  629 */       return this.entry;
/*      */     } }
/*      */ 
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Reference2ObjectMap.Entry<K, V>> implements Reference2ObjectMap.FastEntrySet<K, V> {
/*      */     private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Reference2ObjectMap.Entry<K, V>> iterator() {
/*  637 */       return new Reference2ObjectOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Reference2ObjectMap.Entry<K, V>> fastIterator() {
/*  641 */       return new Reference2ObjectOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  646 */       if (!(o instanceof Map.Entry))
/*  647 */         return false; 
/*  648 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  649 */       K k = (K)e.getKey();
/*  650 */       V v = (V)e.getValue();
/*  651 */       if (Reference2ObjectOpenCustomHashMap.this.strategy.equals(k, null)) {
/*  652 */         return (Reference2ObjectOpenCustomHashMap.this.containsNullKey && Objects.equals(Reference2ObjectOpenCustomHashMap.this.value[Reference2ObjectOpenCustomHashMap.this.n], v));
/*      */       }
/*  654 */       K[] key = Reference2ObjectOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  657 */       if ((curr = key[pos = HashCommon.mix(Reference2ObjectOpenCustomHashMap.this.strategy.hashCode(k)) & Reference2ObjectOpenCustomHashMap.this.mask]) == null)
/*  658 */         return false; 
/*  659 */       if (Reference2ObjectOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  660 */         return Objects.equals(Reference2ObjectOpenCustomHashMap.this.value[pos], v);
/*      */       }
/*      */       while (true) {
/*  663 */         if ((curr = key[pos = pos + 1 & Reference2ObjectOpenCustomHashMap.this.mask]) == null)
/*  664 */           return false; 
/*  665 */         if (Reference2ObjectOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  666 */           return Objects.equals(Reference2ObjectOpenCustomHashMap.this.value[pos], v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  672 */       if (!(o instanceof Map.Entry))
/*  673 */         return false; 
/*  674 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  675 */       K k = (K)e.getKey();
/*  676 */       V v = (V)e.getValue();
/*  677 */       if (Reference2ObjectOpenCustomHashMap.this.strategy.equals(k, null)) {
/*  678 */         if (Reference2ObjectOpenCustomHashMap.this.containsNullKey && Objects.equals(Reference2ObjectOpenCustomHashMap.this.value[Reference2ObjectOpenCustomHashMap.this.n], v)) {
/*  679 */           Reference2ObjectOpenCustomHashMap.this.removeNullEntry();
/*  680 */           return true;
/*      */         } 
/*  682 */         return false;
/*      */       } 
/*      */       
/*  685 */       K[] key = Reference2ObjectOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  688 */       if ((curr = key[pos = HashCommon.mix(Reference2ObjectOpenCustomHashMap.this.strategy.hashCode(k)) & Reference2ObjectOpenCustomHashMap.this.mask]) == null)
/*  689 */         return false; 
/*  690 */       if (Reference2ObjectOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  691 */         if (Objects.equals(Reference2ObjectOpenCustomHashMap.this.value[pos], v)) {
/*  692 */           Reference2ObjectOpenCustomHashMap.this.removeEntry(pos);
/*  693 */           return true;
/*      */         } 
/*  695 */         return false;
/*      */       } 
/*      */       while (true) {
/*  698 */         if ((curr = key[pos = pos + 1 & Reference2ObjectOpenCustomHashMap.this.mask]) == null)
/*  699 */           return false; 
/*  700 */         if (Reference2ObjectOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  701 */           Objects.equals(Reference2ObjectOpenCustomHashMap.this.value[pos], v)) {
/*  702 */           Reference2ObjectOpenCustomHashMap.this.removeEntry(pos);
/*  703 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  710 */       return Reference2ObjectOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  714 */       Reference2ObjectOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Reference2ObjectMap.Entry<K, V>> consumer) {
/*  719 */       if (Reference2ObjectOpenCustomHashMap.this.containsNullKey)
/*  720 */         consumer.accept(new AbstractReference2ObjectMap.BasicEntry<>(Reference2ObjectOpenCustomHashMap.this.key[Reference2ObjectOpenCustomHashMap.this.n], Reference2ObjectOpenCustomHashMap.this.value[Reference2ObjectOpenCustomHashMap.this.n])); 
/*  721 */       for (int pos = Reference2ObjectOpenCustomHashMap.this.n; pos-- != 0;) {
/*  722 */         if (Reference2ObjectOpenCustomHashMap.this.key[pos] != null)
/*  723 */           consumer.accept(new AbstractReference2ObjectMap.BasicEntry<>(Reference2ObjectOpenCustomHashMap.this.key[pos], Reference2ObjectOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Reference2ObjectMap.Entry<K, V>> consumer) {
/*  728 */       AbstractReference2ObjectMap.BasicEntry<K, V> entry = new AbstractReference2ObjectMap.BasicEntry<>();
/*  729 */       if (Reference2ObjectOpenCustomHashMap.this.containsNullKey) {
/*  730 */         entry.key = Reference2ObjectOpenCustomHashMap.this.key[Reference2ObjectOpenCustomHashMap.this.n];
/*  731 */         entry.value = Reference2ObjectOpenCustomHashMap.this.value[Reference2ObjectOpenCustomHashMap.this.n];
/*  732 */         consumer.accept(entry);
/*      */       } 
/*  734 */       for (int pos = Reference2ObjectOpenCustomHashMap.this.n; pos-- != 0;) {
/*  735 */         if (Reference2ObjectOpenCustomHashMap.this.key[pos] != null) {
/*  736 */           entry.key = Reference2ObjectOpenCustomHashMap.this.key[pos];
/*  737 */           entry.value = Reference2ObjectOpenCustomHashMap.this.value[pos];
/*  738 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Reference2ObjectMap.FastEntrySet<K, V> reference2ObjectEntrySet() {
/*  744 */     if (this.entries == null)
/*  745 */       this.entries = new MapEntrySet(); 
/*  746 */     return this.entries;
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
/*  763 */       return Reference2ObjectOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractReferenceSet<K> { private KeySet() {}
/*      */     
/*      */     public ObjectIterator<K> iterator() {
/*  769 */       return new Reference2ObjectOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/*  774 */       if (Reference2ObjectOpenCustomHashMap.this.containsNullKey)
/*  775 */         consumer.accept(Reference2ObjectOpenCustomHashMap.this.key[Reference2ObjectOpenCustomHashMap.this.n]); 
/*  776 */       for (int pos = Reference2ObjectOpenCustomHashMap.this.n; pos-- != 0; ) {
/*  777 */         K k = Reference2ObjectOpenCustomHashMap.this.key[pos];
/*  778 */         if (k != null)
/*  779 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  784 */       return Reference2ObjectOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(Object k) {
/*  788 */       return Reference2ObjectOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(Object k) {
/*  792 */       int oldSize = Reference2ObjectOpenCustomHashMap.this.size;
/*  793 */       Reference2ObjectOpenCustomHashMap.this.remove(k);
/*  794 */       return (Reference2ObjectOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  798 */       Reference2ObjectOpenCustomHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ReferenceSet<K> keySet() {
/*  803 */     if (this.keys == null)
/*  804 */       this.keys = new KeySet(); 
/*  805 */     return this.keys;
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
/*  822 */       return Reference2ObjectOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectCollection<V> values() {
/*  827 */     if (this.values == null)
/*  828 */       this.values = new AbstractObjectCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/*  831 */             return new Reference2ObjectOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/*  835 */             return Reference2ObjectOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(Object v) {
/*  839 */             return Reference2ObjectOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/*  843 */             Reference2ObjectOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(Consumer<? super V> consumer)
/*      */           {
/*  848 */             if (Reference2ObjectOpenCustomHashMap.this.containsNullKey)
/*  849 */               consumer.accept(Reference2ObjectOpenCustomHashMap.this.value[Reference2ObjectOpenCustomHashMap.this.n]); 
/*  850 */             for (int pos = Reference2ObjectOpenCustomHashMap.this.n; pos-- != 0;) {
/*  851 */               if (Reference2ObjectOpenCustomHashMap.this.key[pos] != null)
/*  852 */                 consumer.accept(Reference2ObjectOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/*  855 */     return this.values;
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
/*  872 */     return trim(this.size);
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
/*  896 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/*  897 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/*  898 */       return true; 
/*      */     try {
/*  900 */       rehash(l);
/*  901 */     } catch (OutOfMemoryError cantDoIt) {
/*  902 */       return false;
/*      */     } 
/*  904 */     return true;
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
/*  920 */     K[] key = this.key;
/*  921 */     V[] value = this.value;
/*  922 */     int mask = newN - 1;
/*  923 */     K[] newKey = (K[])new Object[newN + 1];
/*  924 */     V[] newValue = (V[])new Object[newN + 1];
/*  925 */     int i = this.n;
/*  926 */     for (int j = realSize(); j-- != 0; ) {
/*  927 */       while (key[--i] == null); int pos;
/*  928 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != null)
/*  929 */         while (newKey[pos = pos + 1 & mask] != null); 
/*  930 */       newKey[pos] = key[i];
/*  931 */       newValue[pos] = value[i];
/*      */     } 
/*  933 */     newValue[newN] = value[this.n];
/*  934 */     this.n = newN;
/*  935 */     this.mask = mask;
/*  936 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/*  937 */     this.key = newKey;
/*  938 */     this.value = newValue;
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
/*      */   public Reference2ObjectOpenCustomHashMap<K, V> clone() {
/*      */     Reference2ObjectOpenCustomHashMap<K, V> c;
/*      */     try {
/*  955 */       c = (Reference2ObjectOpenCustomHashMap<K, V>)super.clone();
/*  956 */     } catch (CloneNotSupportedException cantHappen) {
/*  957 */       throw new InternalError();
/*      */     } 
/*  959 */     c.keys = null;
/*  960 */     c.values = null;
/*  961 */     c.entries = null;
/*  962 */     c.containsNullKey = this.containsNullKey;
/*  963 */     c.key = (K[])this.key.clone();
/*  964 */     c.value = (V[])this.value.clone();
/*  965 */     c.strategy = this.strategy;
/*  966 */     return c;
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
/*  979 */     int h = 0;
/*  980 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/*  981 */       while (this.key[i] == null)
/*  982 */         i++; 
/*  983 */       if (this != this.key[i])
/*  984 */         t = this.strategy.hashCode(this.key[i]); 
/*  985 */       if (this != this.value[i])
/*  986 */         t ^= (this.value[i] == null) ? 0 : this.value[i].hashCode(); 
/*  987 */       h += t;
/*  988 */       i++;
/*      */     } 
/*      */     
/*  991 */     if (this.containsNullKey)
/*  992 */       h += (this.value[this.n] == null) ? 0 : this.value[this.n].hashCode(); 
/*  993 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/*  996 */     K[] key = this.key;
/*  997 */     V[] value = this.value;
/*  998 */     MapIterator i = new MapIterator();
/*  999 */     s.defaultWriteObject();
/* 1000 */     for (int j = this.size; j-- != 0; ) {
/* 1001 */       int e = i.nextEntry();
/* 1002 */       s.writeObject(key[e]);
/* 1003 */       s.writeObject(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1008 */     s.defaultReadObject();
/* 1009 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1010 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1011 */     this.mask = this.n - 1;
/* 1012 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1013 */     V[] value = this.value = (V[])new Object[this.n + 1];
/*      */ 
/*      */     
/* 1016 */     for (int i = this.size; i-- != 0; ) {
/* 1017 */       int pos; K k = (K)s.readObject();
/* 1018 */       V v = (V)s.readObject();
/* 1019 */       if (this.strategy.equals(k, null)) {
/* 1020 */         pos = this.n;
/* 1021 */         this.containsNullKey = true;
/*      */       } else {
/* 1023 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1024 */         while (key[pos] != null)
/* 1025 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1027 */       key[pos] = k;
/* 1028 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Reference2ObjectOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */