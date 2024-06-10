/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Hash;
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.function.Consumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Object2ReferenceOpenHashMap<K, V>
/*     */   extends AbstractObject2ReferenceMap<K, V>
/*     */   implements Serializable, Cloneable, Hash
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private static final boolean ASSERTS = false;
/*     */   protected transient K[] key;
/*     */   protected transient V[] value;
/*     */   protected transient int mask;
/*     */   protected transient boolean containsNullKey;
/*     */   protected transient int n;
/*     */   protected transient int maxFill;
/*     */   protected final transient int minN;
/*     */   protected int size;
/*     */   protected final float f;
/*     */   protected transient Object2ReferenceMap.FastEntrySet<K, V> entries;
/*     */   protected transient ObjectSet<K> keys;
/*     */   protected transient ReferenceCollection<V> values;
/*     */   
/*     */   public Object2ReferenceOpenHashMap(int expected, float f) {
/*  99 */     if (f <= 0.0F || f > 1.0F)
/* 100 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/* 101 */     if (expected < 0)
/* 102 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/* 103 */     this.f = f;
/* 104 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/* 105 */     this.mask = this.n - 1;
/* 106 */     this.maxFill = HashCommon.maxFill(this.n, f);
/* 107 */     this.key = (K[])new Object[this.n + 1];
/* 108 */     this.value = (V[])new Object[this.n + 1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object2ReferenceOpenHashMap(int expected) {
/* 117 */     this(expected, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object2ReferenceOpenHashMap() {
/* 125 */     this(16, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object2ReferenceOpenHashMap(Map<? extends K, ? extends V> m, float f) {
/* 136 */     this(m.size(), f);
/* 137 */     putAll(m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object2ReferenceOpenHashMap(Map<? extends K, ? extends V> m) {
/* 147 */     this(m, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object2ReferenceOpenHashMap(Object2ReferenceMap<K, V> m, float f) {
/* 158 */     this(m.size(), f);
/* 159 */     putAll(m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object2ReferenceOpenHashMap(Object2ReferenceMap<K, V> m) {
/* 169 */     this(m, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object2ReferenceOpenHashMap(K[] k, V[] v, float f) {
/* 184 */     this(k.length, f);
/* 185 */     if (k.length != v.length) {
/* 186 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*     */     }
/* 188 */     for (int i = 0; i < k.length; i++) {
/* 189 */       put(k[i], v[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object2ReferenceOpenHashMap(K[] k, V[] v) {
/* 203 */     this(k, v, 0.75F);
/*     */   }
/*     */   private int realSize() {
/* 206 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*     */   }
/*     */   private void ensureCapacity(int capacity) {
/* 209 */     int needed = HashCommon.arraySize(capacity, this.f);
/* 210 */     if (needed > this.n)
/* 211 */       rehash(needed); 
/*     */   }
/*     */   private void tryCapacity(long capacity) {
/* 214 */     int needed = (int)Math.min(1073741824L, 
/* 215 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/* 216 */     if (needed > this.n)
/* 217 */       rehash(needed); 
/*     */   }
/*     */   private V removeEntry(int pos) {
/* 220 */     V oldValue = this.value[pos];
/* 221 */     this.value[pos] = null;
/* 222 */     this.size--;
/* 223 */     shiftKeys(pos);
/* 224 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/* 225 */       rehash(this.n / 2); 
/* 226 */     return oldValue;
/*     */   }
/*     */   private V removeNullEntry() {
/* 229 */     this.containsNullKey = false;
/* 230 */     this.key[this.n] = null;
/* 231 */     V oldValue = this.value[this.n];
/* 232 */     this.value[this.n] = null;
/* 233 */     this.size--;
/* 234 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/* 235 */       rehash(this.n / 2); 
/* 236 */     return oldValue;
/*     */   }
/*     */   
/*     */   public void putAll(Map<? extends K, ? extends V> m) {
/* 240 */     if (this.f <= 0.5D) {
/* 241 */       ensureCapacity(m.size());
/*     */     } else {
/* 243 */       tryCapacity((size() + m.size()));
/*     */     } 
/* 245 */     super.putAll(m);
/*     */   }
/*     */   
/*     */   private int find(K k) {
/* 249 */     if (k == null) {
/* 250 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*     */     }
/* 252 */     K[] key = this.key;
/*     */     K curr;
/*     */     int pos;
/* 255 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/* 256 */       return -(pos + 1); 
/* 257 */     if (k.equals(curr)) {
/* 258 */       return pos;
/*     */     }
/*     */     while (true) {
/* 261 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/* 262 */         return -(pos + 1); 
/* 263 */       if (k.equals(curr))
/* 264 */         return pos; 
/*     */     } 
/*     */   }
/*     */   private void insert(int pos, K k, V v) {
/* 268 */     if (pos == this.n)
/* 269 */       this.containsNullKey = true; 
/* 270 */     this.key[pos] = k;
/* 271 */     this.value[pos] = v;
/* 272 */     if (this.size++ >= this.maxFill) {
/* 273 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public V put(K k, V v) {
/* 279 */     int pos = find(k);
/* 280 */     if (pos < 0) {
/* 281 */       insert(-pos - 1, k, v);
/* 282 */       return this.defRetValue;
/*     */     } 
/* 284 */     V oldValue = this.value[pos];
/* 285 */     this.value[pos] = v;
/* 286 */     return oldValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void shiftKeys(int pos) {
/* 299 */     K[] key = this.key; while (true) {
/*     */       K curr; int last;
/* 301 */       pos = (last = pos) + 1 & this.mask;
/*     */       while (true) {
/* 303 */         if ((curr = key[pos]) == null) {
/* 304 */           key[last] = null;
/* 305 */           this.value[last] = null;
/*     */           return;
/*     */         } 
/* 308 */         int slot = HashCommon.mix(curr.hashCode()) & this.mask;
/* 309 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*     */           break; 
/* 311 */         pos = pos + 1 & this.mask;
/*     */       } 
/* 313 */       key[last] = curr;
/* 314 */       this.value[last] = this.value[pos];
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public V remove(Object k) {
/* 320 */     if (k == null) {
/* 321 */       if (this.containsNullKey)
/* 322 */         return removeNullEntry(); 
/* 323 */       return this.defRetValue;
/*     */     } 
/*     */     
/* 326 */     K[] key = this.key;
/*     */     K curr;
/*     */     int pos;
/* 329 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/* 330 */       return this.defRetValue; 
/* 331 */     if (k.equals(curr))
/* 332 */       return removeEntry(pos); 
/*     */     while (true) {
/* 334 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/* 335 */         return this.defRetValue; 
/* 336 */       if (k.equals(curr)) {
/* 337 */         return removeEntry(pos);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public V get(Object k) {
/* 343 */     if (k == null) {
/* 344 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*     */     }
/* 346 */     K[] key = this.key;
/*     */     K curr;
/*     */     int pos;
/* 349 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/* 350 */       return this.defRetValue; 
/* 351 */     if (k.equals(curr)) {
/* 352 */       return this.value[pos];
/*     */     }
/*     */     while (true) {
/* 355 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/* 356 */         return this.defRetValue; 
/* 357 */       if (k.equals(curr)) {
/* 358 */         return this.value[pos];
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean containsKey(Object k) {
/* 364 */     if (k == null) {
/* 365 */       return this.containsNullKey;
/*     */     }
/* 367 */     K[] key = this.key;
/*     */     K curr;
/*     */     int pos;
/* 370 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/* 371 */       return false; 
/* 372 */     if (k.equals(curr)) {
/* 373 */       return true;
/*     */     }
/*     */     while (true) {
/* 376 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/* 377 */         return false; 
/* 378 */       if (k.equals(curr))
/* 379 */         return true; 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean containsValue(Object v) {
/* 384 */     V[] value = this.value;
/* 385 */     K[] key = this.key;
/* 386 */     if (this.containsNullKey && value[this.n] == v)
/* 387 */       return true; 
/* 388 */     for (int i = this.n; i-- != 0;) {
/* 389 */       if (key[i] != null && value[i] == v)
/* 390 */         return true; 
/* 391 */     }  return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 402 */     if (this.size == 0)
/*     */       return; 
/* 404 */     this.size = 0;
/* 405 */     this.containsNullKey = false;
/* 406 */     Arrays.fill((Object[])this.key, (Object)null);
/* 407 */     Arrays.fill((Object[])this.value, (Object)null);
/*     */   }
/*     */   
/*     */   public int size() {
/* 411 */     return this.size;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 415 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   final class MapEntry
/*     */     implements Object2ReferenceMap.Entry<K, V>, Map.Entry<K, V>
/*     */   {
/*     */     int index;
/*     */ 
/*     */     
/*     */     MapEntry(int index) {
/* 427 */       this.index = index;
/*     */     }
/*     */     
/*     */     MapEntry() {}
/*     */     
/*     */     public K getKey() {
/* 433 */       return Object2ReferenceOpenHashMap.this.key[this.index];
/*     */     }
/*     */     
/*     */     public V getValue() {
/* 437 */       return Object2ReferenceOpenHashMap.this.value[this.index];
/*     */     }
/*     */     
/*     */     public V setValue(V v) {
/* 441 */       V oldValue = Object2ReferenceOpenHashMap.this.value[this.index];
/* 442 */       Object2ReferenceOpenHashMap.this.value[this.index] = v;
/* 443 */       return oldValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 448 */       if (!(o instanceof Map.Entry))
/* 449 */         return false; 
/* 450 */       Map.Entry<K, V> e = (Map.Entry<K, V>)o;
/* 451 */       return (Objects.equals(Object2ReferenceOpenHashMap.this.key[this.index], e.getKey()) && Object2ReferenceOpenHashMap.this.value[this.index] == e.getValue());
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 455 */       return ((Object2ReferenceOpenHashMap.this.key[this.index] == null) ? 0 : Object2ReferenceOpenHashMap.this.key[this.index].hashCode()) ^ (
/* 456 */         (Object2ReferenceOpenHashMap.this.value[this.index] == null) ? 0 : System.identityHashCode(Object2ReferenceOpenHashMap.this.value[this.index]));
/*     */     }
/*     */     
/*     */     public String toString() {
/* 460 */       return (new StringBuilder()).append(Object2ReferenceOpenHashMap.this.key[this.index]).append("=>").append(Object2ReferenceOpenHashMap.this.value[this.index]).toString();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class MapIterator
/*     */   {
/* 470 */     int pos = Object2ReferenceOpenHashMap.this.n;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 477 */     int last = -1;
/*     */     
/* 479 */     int c = Object2ReferenceOpenHashMap.this.size;
/*     */ 
/*     */ 
/*     */     
/* 483 */     boolean mustReturnNullKey = Object2ReferenceOpenHashMap.this.containsNullKey;
/*     */ 
/*     */     
/*     */     ObjectArrayList<K> wrapped;
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 490 */       return (this.c != 0);
/*     */     }
/*     */     public int nextEntry() {
/* 493 */       if (!hasNext())
/* 494 */         throw new NoSuchElementException(); 
/* 495 */       this.c--;
/* 496 */       if (this.mustReturnNullKey) {
/* 497 */         this.mustReturnNullKey = false;
/* 498 */         return this.last = Object2ReferenceOpenHashMap.this.n;
/*     */       } 
/* 500 */       K[] key = Object2ReferenceOpenHashMap.this.key;
/*     */       while (true) {
/* 502 */         if (--this.pos < 0) {
/*     */           
/* 504 */           this.last = Integer.MIN_VALUE;
/* 505 */           K k = this.wrapped.get(-this.pos - 1);
/* 506 */           int p = HashCommon.mix(k.hashCode()) & Object2ReferenceOpenHashMap.this.mask;
/* 507 */           while (!k.equals(key[p]))
/* 508 */             p = p + 1 & Object2ReferenceOpenHashMap.this.mask; 
/* 509 */           return p;
/*     */         } 
/* 511 */         if (key[this.pos] != null) {
/* 512 */           return this.last = this.pos;
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void shiftKeys(int pos) {
/* 526 */       K[] key = Object2ReferenceOpenHashMap.this.key; while (true) {
/*     */         K curr; int last;
/* 528 */         pos = (last = pos) + 1 & Object2ReferenceOpenHashMap.this.mask;
/*     */         while (true) {
/* 530 */           if ((curr = key[pos]) == null) {
/* 531 */             key[last] = null;
/* 532 */             Object2ReferenceOpenHashMap.this.value[last] = null;
/*     */             return;
/*     */           } 
/* 535 */           int slot = HashCommon.mix(curr.hashCode()) & Object2ReferenceOpenHashMap.this.mask;
/* 536 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*     */             break; 
/* 538 */           pos = pos + 1 & Object2ReferenceOpenHashMap.this.mask;
/*     */         } 
/* 540 */         if (pos < last) {
/* 541 */           if (this.wrapped == null)
/* 542 */             this.wrapped = new ObjectArrayList<>(2); 
/* 543 */           this.wrapped.add(key[pos]);
/*     */         } 
/* 545 */         key[last] = curr;
/* 546 */         Object2ReferenceOpenHashMap.this.value[last] = Object2ReferenceOpenHashMap.this.value[pos];
/*     */       } 
/*     */     }
/*     */     public void remove() {
/* 550 */       if (this.last == -1)
/* 551 */         throw new IllegalStateException(); 
/* 552 */       if (this.last == Object2ReferenceOpenHashMap.this.n) {
/* 553 */         Object2ReferenceOpenHashMap.this.containsNullKey = false;
/* 554 */         Object2ReferenceOpenHashMap.this.key[Object2ReferenceOpenHashMap.this.n] = null;
/* 555 */         Object2ReferenceOpenHashMap.this.value[Object2ReferenceOpenHashMap.this.n] = null;
/* 556 */       } else if (this.pos >= 0) {
/* 557 */         shiftKeys(this.last);
/*     */       } else {
/*     */         
/* 560 */         Object2ReferenceOpenHashMap.this.remove(this.wrapped.set(-this.pos - 1, null));
/* 561 */         this.last = -1;
/*     */         return;
/*     */       } 
/* 564 */       Object2ReferenceOpenHashMap.this.size--;
/* 565 */       this.last = -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int skip(int n) {
/* 570 */       int i = n;
/* 571 */       while (i-- != 0 && hasNext())
/* 572 */         nextEntry(); 
/* 573 */       return n - i - 1;
/*     */     }
/*     */     private MapIterator() {} }
/*     */   
/*     */   private class EntryIterator extends MapIterator implements ObjectIterator<Object2ReferenceMap.Entry<K, V>> { private Object2ReferenceOpenHashMap<K, V>.MapEntry entry;
/*     */     
/*     */     public Object2ReferenceOpenHashMap<K, V>.MapEntry next() {
/* 580 */       return this.entry = new Object2ReferenceOpenHashMap.MapEntry(nextEntry());
/*     */     }
/*     */     private EntryIterator() {}
/*     */     public void remove() {
/* 584 */       super.remove();
/* 585 */       this.entry.index = -1;
/*     */     } }
/*     */   
/*     */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Object2ReferenceMap.Entry<K, V>> { private FastEntryIterator() {
/* 589 */       this.entry = new Object2ReferenceOpenHashMap.MapEntry();
/*     */     } private final Object2ReferenceOpenHashMap<K, V>.MapEntry entry;
/*     */     public Object2ReferenceOpenHashMap<K, V>.MapEntry next() {
/* 592 */       this.entry.index = nextEntry();
/* 593 */       return this.entry;
/*     */     } }
/*     */ 
/*     */   
/*     */   private final class MapEntrySet extends AbstractObjectSet<Object2ReferenceMap.Entry<K, V>> implements Object2ReferenceMap.FastEntrySet<K, V> {
/*     */     private MapEntrySet() {}
/*     */     
/*     */     public ObjectIterator<Object2ReferenceMap.Entry<K, V>> iterator() {
/* 601 */       return new Object2ReferenceOpenHashMap.EntryIterator();
/*     */     }
/*     */     
/*     */     public ObjectIterator<Object2ReferenceMap.Entry<K, V>> fastIterator() {
/* 605 */       return new Object2ReferenceOpenHashMap.FastEntryIterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 610 */       if (!(o instanceof Map.Entry))
/* 611 */         return false; 
/* 612 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 613 */       K k = (K)e.getKey();
/* 614 */       V v = (V)e.getValue();
/* 615 */       if (k == null) {
/* 616 */         return (Object2ReferenceOpenHashMap.this.containsNullKey && Object2ReferenceOpenHashMap.this.value[Object2ReferenceOpenHashMap.this.n] == v);
/*     */       }
/* 618 */       K[] key = Object2ReferenceOpenHashMap.this.key;
/*     */       K curr;
/*     */       int pos;
/* 621 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2ReferenceOpenHashMap.this.mask]) == null)
/* 622 */         return false; 
/* 623 */       if (k.equals(curr)) {
/* 624 */         return (Object2ReferenceOpenHashMap.this.value[pos] == v);
/*     */       }
/*     */       while (true) {
/* 627 */         if ((curr = key[pos = pos + 1 & Object2ReferenceOpenHashMap.this.mask]) == null)
/* 628 */           return false; 
/* 629 */         if (k.equals(curr)) {
/* 630 */           return (Object2ReferenceOpenHashMap.this.value[pos] == v);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean remove(Object o) {
/* 636 */       if (!(o instanceof Map.Entry))
/* 637 */         return false; 
/* 638 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 639 */       K k = (K)e.getKey();
/* 640 */       V v = (V)e.getValue();
/* 641 */       if (k == null) {
/* 642 */         if (Object2ReferenceOpenHashMap.this.containsNullKey && Object2ReferenceOpenHashMap.this.value[Object2ReferenceOpenHashMap.this.n] == v) {
/* 643 */           Object2ReferenceOpenHashMap.this.removeNullEntry();
/* 644 */           return true;
/*     */         } 
/* 646 */         return false;
/*     */       } 
/*     */       
/* 649 */       K[] key = Object2ReferenceOpenHashMap.this.key;
/*     */       K curr;
/*     */       int pos;
/* 652 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2ReferenceOpenHashMap.this.mask]) == null)
/* 653 */         return false; 
/* 654 */       if (curr.equals(k)) {
/* 655 */         if (Object2ReferenceOpenHashMap.this.value[pos] == v) {
/* 656 */           Object2ReferenceOpenHashMap.this.removeEntry(pos);
/* 657 */           return true;
/*     */         } 
/* 659 */         return false;
/*     */       } 
/*     */       while (true) {
/* 662 */         if ((curr = key[pos = pos + 1 & Object2ReferenceOpenHashMap.this.mask]) == null)
/* 663 */           return false; 
/* 664 */         if (curr.equals(k) && 
/* 665 */           Object2ReferenceOpenHashMap.this.value[pos] == v) {
/* 666 */           Object2ReferenceOpenHashMap.this.removeEntry(pos);
/* 667 */           return true;
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 674 */       return Object2ReferenceOpenHashMap.this.size;
/*     */     }
/*     */     
/*     */     public void clear() {
/* 678 */       Object2ReferenceOpenHashMap.this.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super Object2ReferenceMap.Entry<K, V>> consumer) {
/* 683 */       if (Object2ReferenceOpenHashMap.this.containsNullKey)
/* 684 */         consumer.accept(new AbstractObject2ReferenceMap.BasicEntry<>(Object2ReferenceOpenHashMap.this.key[Object2ReferenceOpenHashMap.this.n], Object2ReferenceOpenHashMap.this.value[Object2ReferenceOpenHashMap.this.n])); 
/* 685 */       for (int pos = Object2ReferenceOpenHashMap.this.n; pos-- != 0;) {
/* 686 */         if (Object2ReferenceOpenHashMap.this.key[pos] != null)
/* 687 */           consumer.accept(new AbstractObject2ReferenceMap.BasicEntry<>(Object2ReferenceOpenHashMap.this.key[pos], Object2ReferenceOpenHashMap.this.value[pos])); 
/*     */       } 
/*     */     }
/*     */     
/*     */     public void fastForEach(Consumer<? super Object2ReferenceMap.Entry<K, V>> consumer) {
/* 692 */       AbstractObject2ReferenceMap.BasicEntry<K, V> entry = new AbstractObject2ReferenceMap.BasicEntry<>();
/* 693 */       if (Object2ReferenceOpenHashMap.this.containsNullKey) {
/* 694 */         entry.key = Object2ReferenceOpenHashMap.this.key[Object2ReferenceOpenHashMap.this.n];
/* 695 */         entry.value = Object2ReferenceOpenHashMap.this.value[Object2ReferenceOpenHashMap.this.n];
/* 696 */         consumer.accept(entry);
/*     */       } 
/* 698 */       for (int pos = Object2ReferenceOpenHashMap.this.n; pos-- != 0;) {
/* 699 */         if (Object2ReferenceOpenHashMap.this.key[pos] != null) {
/* 700 */           entry.key = Object2ReferenceOpenHashMap.this.key[pos];
/* 701 */           entry.value = Object2ReferenceOpenHashMap.this.value[pos];
/* 702 */           consumer.accept(entry);
/*     */         } 
/*     */       } 
/*     */     } }
/*     */   
/*     */   public Object2ReferenceMap.FastEntrySet<K, V> object2ReferenceEntrySet() {
/* 708 */     if (this.entries == null)
/* 709 */       this.entries = new MapEntrySet(); 
/* 710 */     return this.entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final class KeyIterator
/*     */     extends MapIterator
/*     */     implements ObjectIterator<K>
/*     */   {
/*     */     public K next() {
/* 727 */       return Object2ReferenceOpenHashMap.this.key[nextEntry()];
/*     */     } }
/*     */   
/*     */   private final class KeySet extends AbstractObjectSet<K> { private KeySet() {}
/*     */     
/*     */     public ObjectIterator<K> iterator() {
/* 733 */       return new Object2ReferenceOpenHashMap.KeyIterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super K> consumer) {
/* 738 */       if (Object2ReferenceOpenHashMap.this.containsNullKey)
/* 739 */         consumer.accept(Object2ReferenceOpenHashMap.this.key[Object2ReferenceOpenHashMap.this.n]); 
/* 740 */       for (int pos = Object2ReferenceOpenHashMap.this.n; pos-- != 0; ) {
/* 741 */         K k = Object2ReferenceOpenHashMap.this.key[pos];
/* 742 */         if (k != null)
/* 743 */           consumer.accept(k); 
/*     */       } 
/*     */     }
/*     */     
/*     */     public int size() {
/* 748 */       return Object2ReferenceOpenHashMap.this.size;
/*     */     }
/*     */     
/*     */     public boolean contains(Object k) {
/* 752 */       return Object2ReferenceOpenHashMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public boolean remove(Object k) {
/* 756 */       int oldSize = Object2ReferenceOpenHashMap.this.size;
/* 757 */       Object2ReferenceOpenHashMap.this.remove(k);
/* 758 */       return (Object2ReferenceOpenHashMap.this.size != oldSize);
/*     */     }
/*     */     
/*     */     public void clear() {
/* 762 */       Object2ReferenceOpenHashMap.this.clear();
/*     */     } }
/*     */ 
/*     */   
/*     */   public ObjectSet<K> keySet() {
/* 767 */     if (this.keys == null)
/* 768 */       this.keys = new KeySet(); 
/* 769 */     return this.keys;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final class ValueIterator
/*     */     extends MapIterator
/*     */     implements ObjectIterator<V>
/*     */   {
/*     */     public V next() {
/* 786 */       return Object2ReferenceOpenHashMap.this.value[nextEntry()];
/*     */     }
/*     */   }
/*     */   
/*     */   public ReferenceCollection<V> values() {
/* 791 */     if (this.values == null)
/* 792 */       this.values = new AbstractReferenceCollection<V>()
/*     */         {
/*     */           public ObjectIterator<V> iterator() {
/* 795 */             return new Object2ReferenceOpenHashMap.ValueIterator();
/*     */           }
/*     */           
/*     */           public int size() {
/* 799 */             return Object2ReferenceOpenHashMap.this.size;
/*     */           }
/*     */           
/*     */           public boolean contains(Object v) {
/* 803 */             return Object2ReferenceOpenHashMap.this.containsValue(v);
/*     */           }
/*     */           
/*     */           public void clear() {
/* 807 */             Object2ReferenceOpenHashMap.this.clear();
/*     */           }
/*     */           
/*     */           public void forEach(Consumer<? super V> consumer)
/*     */           {
/* 812 */             if (Object2ReferenceOpenHashMap.this.containsNullKey)
/* 813 */               consumer.accept(Object2ReferenceOpenHashMap.this.value[Object2ReferenceOpenHashMap.this.n]); 
/* 814 */             for (int pos = Object2ReferenceOpenHashMap.this.n; pos-- != 0;) {
/* 815 */               if (Object2ReferenceOpenHashMap.this.key[pos] != null)
/* 816 */                 consumer.accept(Object2ReferenceOpenHashMap.this.value[pos]); 
/*     */             }  }
/*     */         }; 
/* 819 */     return this.values;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean trim() {
/* 836 */     return trim(this.size);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean trim(int n) {
/* 860 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 861 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 862 */       return true; 
/*     */     try {
/* 864 */       rehash(l);
/* 865 */     } catch (OutOfMemoryError cantDoIt) {
/* 866 */       return false;
/*     */     } 
/* 868 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void rehash(int newN) {
/* 884 */     K[] key = this.key;
/* 885 */     V[] value = this.value;
/* 886 */     int mask = newN - 1;
/* 887 */     K[] newKey = (K[])new Object[newN + 1];
/* 888 */     V[] newValue = (V[])new Object[newN + 1];
/* 889 */     int i = this.n;
/* 890 */     for (int j = realSize(); j-- != 0; ) {
/* 891 */       while (key[--i] == null); int pos;
/* 892 */       if (newKey[pos = HashCommon.mix(key[i].hashCode()) & mask] != null)
/* 893 */         while (newKey[pos = pos + 1 & mask] != null); 
/* 894 */       newKey[pos] = key[i];
/* 895 */       newValue[pos] = value[i];
/*     */     } 
/* 897 */     newValue[newN] = value[this.n];
/* 898 */     this.n = newN;
/* 899 */     this.mask = mask;
/* 900 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 901 */     this.key = newKey;
/* 902 */     this.value = newValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object2ReferenceOpenHashMap<K, V> clone() {
/*     */     Object2ReferenceOpenHashMap<K, V> c;
/*     */     try {
/* 919 */       c = (Object2ReferenceOpenHashMap<K, V>)super.clone();
/* 920 */     } catch (CloneNotSupportedException cantHappen) {
/* 921 */       throw new InternalError();
/*     */     } 
/* 923 */     c.keys = null;
/* 924 */     c.values = null;
/* 925 */     c.entries = null;
/* 926 */     c.containsNullKey = this.containsNullKey;
/* 927 */     c.key = (K[])this.key.clone();
/* 928 */     c.value = (V[])this.value.clone();
/* 929 */     return c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 942 */     int h = 0;
/* 943 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 944 */       while (this.key[i] == null)
/* 945 */         i++; 
/* 946 */       if (this != this.key[i])
/* 947 */         t = this.key[i].hashCode(); 
/* 948 */       if (this != this.value[i])
/* 949 */         t ^= (this.value[i] == null) ? 0 : System.identityHashCode(this.value[i]); 
/* 950 */       h += t;
/* 951 */       i++;
/*     */     } 
/*     */     
/* 954 */     if (this.containsNullKey)
/* 955 */       h += (this.value[this.n] == null) ? 0 : System.identityHashCode(this.value[this.n]); 
/* 956 */     return h;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 959 */     K[] key = this.key;
/* 960 */     V[] value = this.value;
/* 961 */     MapIterator i = new MapIterator();
/* 962 */     s.defaultWriteObject();
/* 963 */     for (int j = this.size; j-- != 0; ) {
/* 964 */       int e = i.nextEntry();
/* 965 */       s.writeObject(key[e]);
/* 966 */       s.writeObject(value[e]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 971 */     s.defaultReadObject();
/* 972 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 973 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 974 */     this.mask = this.n - 1;
/* 975 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 976 */     V[] value = this.value = (V[])new Object[this.n + 1];
/*     */ 
/*     */     
/* 979 */     for (int i = this.size; i-- != 0; ) {
/* 980 */       int pos; K k = (K)s.readObject();
/* 981 */       V v = (V)s.readObject();
/* 982 */       if (k == null) {
/* 983 */         pos = this.n;
/* 984 */         this.containsNullKey = true;
/*     */       } else {
/* 986 */         pos = HashCommon.mix(k.hashCode()) & this.mask;
/* 987 */         while (key[pos] != null)
/* 988 */           pos = pos + 1 & this.mask; 
/*     */       } 
/* 990 */       key[pos] = k;
/* 991 */       value[pos] = v;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkTable() {}
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2ReferenceOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */