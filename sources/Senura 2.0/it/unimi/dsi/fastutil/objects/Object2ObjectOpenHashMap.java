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
/*     */ public class Object2ObjectOpenHashMap<K, V>
/*     */   extends AbstractObject2ObjectMap<K, V>
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
/*     */   protected transient Object2ObjectMap.FastEntrySet<K, V> entries;
/*     */   protected transient ObjectSet<K> keys;
/*     */   protected transient ObjectCollection<V> values;
/*     */   
/*     */   public Object2ObjectOpenHashMap(int expected, float f) {
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
/*     */   public Object2ObjectOpenHashMap(int expected) {
/* 117 */     this(expected, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object2ObjectOpenHashMap() {
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
/*     */   public Object2ObjectOpenHashMap(Map<? extends K, ? extends V> m, float f) {
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
/*     */   public Object2ObjectOpenHashMap(Map<? extends K, ? extends V> m) {
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
/*     */   public Object2ObjectOpenHashMap(Object2ObjectMap<K, V> m, float f) {
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
/*     */   public Object2ObjectOpenHashMap(Object2ObjectMap<K, V> m) {
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
/*     */   public Object2ObjectOpenHashMap(K[] k, V[] v, float f) {
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
/*     */   public Object2ObjectOpenHashMap(K[] k, V[] v) {
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
/* 386 */     if (this.containsNullKey && Objects.equals(value[this.n], v))
/* 387 */       return true; 
/* 388 */     for (int i = this.n; i-- != 0;) {
/* 389 */       if (key[i] != null && Objects.equals(value[i], v))
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
/*     */     implements Object2ObjectMap.Entry<K, V>, Map.Entry<K, V>
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
/* 433 */       return Object2ObjectOpenHashMap.this.key[this.index];
/*     */     }
/*     */     
/*     */     public V getValue() {
/* 437 */       return Object2ObjectOpenHashMap.this.value[this.index];
/*     */     }
/*     */     
/*     */     public V setValue(V v) {
/* 441 */       V oldValue = Object2ObjectOpenHashMap.this.value[this.index];
/* 442 */       Object2ObjectOpenHashMap.this.value[this.index] = v;
/* 443 */       return oldValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 448 */       if (!(o instanceof Map.Entry))
/* 449 */         return false; 
/* 450 */       Map.Entry<K, V> e = (Map.Entry<K, V>)o;
/* 451 */       return (Objects.equals(Object2ObjectOpenHashMap.this.key[this.index], e.getKey()) && 
/* 452 */         Objects.equals(Object2ObjectOpenHashMap.this.value[this.index], e.getValue()));
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 456 */       return ((Object2ObjectOpenHashMap.this.key[this.index] == null) ? 0 : Object2ObjectOpenHashMap.this.key[this.index].hashCode()) ^ (
/* 457 */         (Object2ObjectOpenHashMap.this.value[this.index] == null) ? 0 : Object2ObjectOpenHashMap.this.value[this.index].hashCode());
/*     */     }
/*     */     
/*     */     public String toString() {
/* 461 */       return (new StringBuilder()).append(Object2ObjectOpenHashMap.this.key[this.index]).append("=>").append(Object2ObjectOpenHashMap.this.value[this.index]).toString();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class MapIterator
/*     */   {
/* 471 */     int pos = Object2ObjectOpenHashMap.this.n;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 478 */     int last = -1;
/*     */     
/* 480 */     int c = Object2ObjectOpenHashMap.this.size;
/*     */ 
/*     */ 
/*     */     
/* 484 */     boolean mustReturnNullKey = Object2ObjectOpenHashMap.this.containsNullKey;
/*     */ 
/*     */     
/*     */     ObjectArrayList<K> wrapped;
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 491 */       return (this.c != 0);
/*     */     }
/*     */     public int nextEntry() {
/* 494 */       if (!hasNext())
/* 495 */         throw new NoSuchElementException(); 
/* 496 */       this.c--;
/* 497 */       if (this.mustReturnNullKey) {
/* 498 */         this.mustReturnNullKey = false;
/* 499 */         return this.last = Object2ObjectOpenHashMap.this.n;
/*     */       } 
/* 501 */       K[] key = Object2ObjectOpenHashMap.this.key;
/*     */       while (true) {
/* 503 */         if (--this.pos < 0) {
/*     */           
/* 505 */           this.last = Integer.MIN_VALUE;
/* 506 */           K k = this.wrapped.get(-this.pos - 1);
/* 507 */           int p = HashCommon.mix(k.hashCode()) & Object2ObjectOpenHashMap.this.mask;
/* 508 */           while (!k.equals(key[p]))
/* 509 */             p = p + 1 & Object2ObjectOpenHashMap.this.mask; 
/* 510 */           return p;
/*     */         } 
/* 512 */         if (key[this.pos] != null) {
/* 513 */           return this.last = this.pos;
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
/* 527 */       K[] key = Object2ObjectOpenHashMap.this.key; while (true) {
/*     */         K curr; int last;
/* 529 */         pos = (last = pos) + 1 & Object2ObjectOpenHashMap.this.mask;
/*     */         while (true) {
/* 531 */           if ((curr = key[pos]) == null) {
/* 532 */             key[last] = null;
/* 533 */             Object2ObjectOpenHashMap.this.value[last] = null;
/*     */             return;
/*     */           } 
/* 536 */           int slot = HashCommon.mix(curr.hashCode()) & Object2ObjectOpenHashMap.this.mask;
/* 537 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*     */             break; 
/* 539 */           pos = pos + 1 & Object2ObjectOpenHashMap.this.mask;
/*     */         } 
/* 541 */         if (pos < last) {
/* 542 */           if (this.wrapped == null)
/* 543 */             this.wrapped = new ObjectArrayList<>(2); 
/* 544 */           this.wrapped.add(key[pos]);
/*     */         } 
/* 546 */         key[last] = curr;
/* 547 */         Object2ObjectOpenHashMap.this.value[last] = Object2ObjectOpenHashMap.this.value[pos];
/*     */       } 
/*     */     }
/*     */     public void remove() {
/* 551 */       if (this.last == -1)
/* 552 */         throw new IllegalStateException(); 
/* 553 */       if (this.last == Object2ObjectOpenHashMap.this.n) {
/* 554 */         Object2ObjectOpenHashMap.this.containsNullKey = false;
/* 555 */         Object2ObjectOpenHashMap.this.key[Object2ObjectOpenHashMap.this.n] = null;
/* 556 */         Object2ObjectOpenHashMap.this.value[Object2ObjectOpenHashMap.this.n] = null;
/* 557 */       } else if (this.pos >= 0) {
/* 558 */         shiftKeys(this.last);
/*     */       } else {
/*     */         
/* 561 */         Object2ObjectOpenHashMap.this.remove(this.wrapped.set(-this.pos - 1, null));
/* 562 */         this.last = -1;
/*     */         return;
/*     */       } 
/* 565 */       Object2ObjectOpenHashMap.this.size--;
/* 566 */       this.last = -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int skip(int n) {
/* 571 */       int i = n;
/* 572 */       while (i-- != 0 && hasNext())
/* 573 */         nextEntry(); 
/* 574 */       return n - i - 1;
/*     */     }
/*     */     private MapIterator() {} }
/*     */   
/*     */   private class EntryIterator extends MapIterator implements ObjectIterator<Object2ObjectMap.Entry<K, V>> { private Object2ObjectOpenHashMap<K, V>.MapEntry entry;
/*     */     
/*     */     public Object2ObjectOpenHashMap<K, V>.MapEntry next() {
/* 581 */       return this.entry = new Object2ObjectOpenHashMap.MapEntry(nextEntry());
/*     */     }
/*     */     private EntryIterator() {}
/*     */     public void remove() {
/* 585 */       super.remove();
/* 586 */       this.entry.index = -1;
/*     */     } }
/*     */   
/*     */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Object2ObjectMap.Entry<K, V>> { private FastEntryIterator() {
/* 590 */       this.entry = new Object2ObjectOpenHashMap.MapEntry();
/*     */     } private final Object2ObjectOpenHashMap<K, V>.MapEntry entry;
/*     */     public Object2ObjectOpenHashMap<K, V>.MapEntry next() {
/* 593 */       this.entry.index = nextEntry();
/* 594 */       return this.entry;
/*     */     } }
/*     */ 
/*     */   
/*     */   private final class MapEntrySet extends AbstractObjectSet<Object2ObjectMap.Entry<K, V>> implements Object2ObjectMap.FastEntrySet<K, V> {
/*     */     private MapEntrySet() {}
/*     */     
/*     */     public ObjectIterator<Object2ObjectMap.Entry<K, V>> iterator() {
/* 602 */       return new Object2ObjectOpenHashMap.EntryIterator();
/*     */     }
/*     */     
/*     */     public ObjectIterator<Object2ObjectMap.Entry<K, V>> fastIterator() {
/* 606 */       return new Object2ObjectOpenHashMap.FastEntryIterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 611 */       if (!(o instanceof Map.Entry))
/* 612 */         return false; 
/* 613 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 614 */       K k = (K)e.getKey();
/* 615 */       V v = (V)e.getValue();
/* 616 */       if (k == null) {
/* 617 */         return (Object2ObjectOpenHashMap.this.containsNullKey && Objects.equals(Object2ObjectOpenHashMap.this.value[Object2ObjectOpenHashMap.this.n], v));
/*     */       }
/* 619 */       K[] key = Object2ObjectOpenHashMap.this.key;
/*     */       K curr;
/*     */       int pos;
/* 622 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2ObjectOpenHashMap.this.mask]) == null)
/* 623 */         return false; 
/* 624 */       if (k.equals(curr)) {
/* 625 */         return Objects.equals(Object2ObjectOpenHashMap.this.value[pos], v);
/*     */       }
/*     */       while (true) {
/* 628 */         if ((curr = key[pos = pos + 1 & Object2ObjectOpenHashMap.this.mask]) == null)
/* 629 */           return false; 
/* 630 */         if (k.equals(curr)) {
/* 631 */           return Objects.equals(Object2ObjectOpenHashMap.this.value[pos], v);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean remove(Object o) {
/* 637 */       if (!(o instanceof Map.Entry))
/* 638 */         return false; 
/* 639 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 640 */       K k = (K)e.getKey();
/* 641 */       V v = (V)e.getValue();
/* 642 */       if (k == null) {
/* 643 */         if (Object2ObjectOpenHashMap.this.containsNullKey && Objects.equals(Object2ObjectOpenHashMap.this.value[Object2ObjectOpenHashMap.this.n], v)) {
/* 644 */           Object2ObjectOpenHashMap.this.removeNullEntry();
/* 645 */           return true;
/*     */         } 
/* 647 */         return false;
/*     */       } 
/*     */       
/* 650 */       K[] key = Object2ObjectOpenHashMap.this.key;
/*     */       K curr;
/*     */       int pos;
/* 653 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2ObjectOpenHashMap.this.mask]) == null)
/* 654 */         return false; 
/* 655 */       if (curr.equals(k)) {
/* 656 */         if (Objects.equals(Object2ObjectOpenHashMap.this.value[pos], v)) {
/* 657 */           Object2ObjectOpenHashMap.this.removeEntry(pos);
/* 658 */           return true;
/*     */         } 
/* 660 */         return false;
/*     */       } 
/*     */       while (true) {
/* 663 */         if ((curr = key[pos = pos + 1 & Object2ObjectOpenHashMap.this.mask]) == null)
/* 664 */           return false; 
/* 665 */         if (curr.equals(k) && 
/* 666 */           Objects.equals(Object2ObjectOpenHashMap.this.value[pos], v)) {
/* 667 */           Object2ObjectOpenHashMap.this.removeEntry(pos);
/* 668 */           return true;
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 675 */       return Object2ObjectOpenHashMap.this.size;
/*     */     }
/*     */     
/*     */     public void clear() {
/* 679 */       Object2ObjectOpenHashMap.this.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super Object2ObjectMap.Entry<K, V>> consumer) {
/* 684 */       if (Object2ObjectOpenHashMap.this.containsNullKey)
/* 685 */         consumer.accept(new AbstractObject2ObjectMap.BasicEntry<>(Object2ObjectOpenHashMap.this.key[Object2ObjectOpenHashMap.this.n], Object2ObjectOpenHashMap.this.value[Object2ObjectOpenHashMap.this.n])); 
/* 686 */       for (int pos = Object2ObjectOpenHashMap.this.n; pos-- != 0;) {
/* 687 */         if (Object2ObjectOpenHashMap.this.key[pos] != null)
/* 688 */           consumer.accept(new AbstractObject2ObjectMap.BasicEntry<>(Object2ObjectOpenHashMap.this.key[pos], Object2ObjectOpenHashMap.this.value[pos])); 
/*     */       } 
/*     */     }
/*     */     
/*     */     public void fastForEach(Consumer<? super Object2ObjectMap.Entry<K, V>> consumer) {
/* 693 */       AbstractObject2ObjectMap.BasicEntry<K, V> entry = new AbstractObject2ObjectMap.BasicEntry<>();
/* 694 */       if (Object2ObjectOpenHashMap.this.containsNullKey) {
/* 695 */         entry.key = Object2ObjectOpenHashMap.this.key[Object2ObjectOpenHashMap.this.n];
/* 696 */         entry.value = Object2ObjectOpenHashMap.this.value[Object2ObjectOpenHashMap.this.n];
/* 697 */         consumer.accept(entry);
/*     */       } 
/* 699 */       for (int pos = Object2ObjectOpenHashMap.this.n; pos-- != 0;) {
/* 700 */         if (Object2ObjectOpenHashMap.this.key[pos] != null) {
/* 701 */           entry.key = Object2ObjectOpenHashMap.this.key[pos];
/* 702 */           entry.value = Object2ObjectOpenHashMap.this.value[pos];
/* 703 */           consumer.accept(entry);
/*     */         } 
/*     */       } 
/*     */     } }
/*     */   
/*     */   public Object2ObjectMap.FastEntrySet<K, V> object2ObjectEntrySet() {
/* 709 */     if (this.entries == null)
/* 710 */       this.entries = new MapEntrySet(); 
/* 711 */     return this.entries;
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
/* 728 */       return Object2ObjectOpenHashMap.this.key[nextEntry()];
/*     */     } }
/*     */   
/*     */   private final class KeySet extends AbstractObjectSet<K> { private KeySet() {}
/*     */     
/*     */     public ObjectIterator<K> iterator() {
/* 734 */       return new Object2ObjectOpenHashMap.KeyIterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super K> consumer) {
/* 739 */       if (Object2ObjectOpenHashMap.this.containsNullKey)
/* 740 */         consumer.accept(Object2ObjectOpenHashMap.this.key[Object2ObjectOpenHashMap.this.n]); 
/* 741 */       for (int pos = Object2ObjectOpenHashMap.this.n; pos-- != 0; ) {
/* 742 */         K k = Object2ObjectOpenHashMap.this.key[pos];
/* 743 */         if (k != null)
/* 744 */           consumer.accept(k); 
/*     */       } 
/*     */     }
/*     */     
/*     */     public int size() {
/* 749 */       return Object2ObjectOpenHashMap.this.size;
/*     */     }
/*     */     
/*     */     public boolean contains(Object k) {
/* 753 */       return Object2ObjectOpenHashMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public boolean remove(Object k) {
/* 757 */       int oldSize = Object2ObjectOpenHashMap.this.size;
/* 758 */       Object2ObjectOpenHashMap.this.remove(k);
/* 759 */       return (Object2ObjectOpenHashMap.this.size != oldSize);
/*     */     }
/*     */     
/*     */     public void clear() {
/* 763 */       Object2ObjectOpenHashMap.this.clear();
/*     */     } }
/*     */ 
/*     */   
/*     */   public ObjectSet<K> keySet() {
/* 768 */     if (this.keys == null)
/* 769 */       this.keys = new KeySet(); 
/* 770 */     return this.keys;
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
/* 787 */       return Object2ObjectOpenHashMap.this.value[nextEntry()];
/*     */     }
/*     */   }
/*     */   
/*     */   public ObjectCollection<V> values() {
/* 792 */     if (this.values == null)
/* 793 */       this.values = new AbstractObjectCollection<V>()
/*     */         {
/*     */           public ObjectIterator<V> iterator() {
/* 796 */             return new Object2ObjectOpenHashMap.ValueIterator();
/*     */           }
/*     */           
/*     */           public int size() {
/* 800 */             return Object2ObjectOpenHashMap.this.size;
/*     */           }
/*     */           
/*     */           public boolean contains(Object v) {
/* 804 */             return Object2ObjectOpenHashMap.this.containsValue(v);
/*     */           }
/*     */           
/*     */           public void clear() {
/* 808 */             Object2ObjectOpenHashMap.this.clear();
/*     */           }
/*     */           
/*     */           public void forEach(Consumer<? super V> consumer)
/*     */           {
/* 813 */             if (Object2ObjectOpenHashMap.this.containsNullKey)
/* 814 */               consumer.accept(Object2ObjectOpenHashMap.this.value[Object2ObjectOpenHashMap.this.n]); 
/* 815 */             for (int pos = Object2ObjectOpenHashMap.this.n; pos-- != 0;) {
/* 816 */               if (Object2ObjectOpenHashMap.this.key[pos] != null)
/* 817 */                 consumer.accept(Object2ObjectOpenHashMap.this.value[pos]); 
/*     */             }  }
/*     */         }; 
/* 820 */     return this.values;
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
/* 837 */     return trim(this.size);
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
/* 861 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 862 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 863 */       return true; 
/*     */     try {
/* 865 */       rehash(l);
/* 866 */     } catch (OutOfMemoryError cantDoIt) {
/* 867 */       return false;
/*     */     } 
/* 869 */     return true;
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
/* 885 */     K[] key = this.key;
/* 886 */     V[] value = this.value;
/* 887 */     int mask = newN - 1;
/* 888 */     K[] newKey = (K[])new Object[newN + 1];
/* 889 */     V[] newValue = (V[])new Object[newN + 1];
/* 890 */     int i = this.n;
/* 891 */     for (int j = realSize(); j-- != 0; ) {
/* 892 */       while (key[--i] == null); int pos;
/* 893 */       if (newKey[pos = HashCommon.mix(key[i].hashCode()) & mask] != null)
/* 894 */         while (newKey[pos = pos + 1 & mask] != null); 
/* 895 */       newKey[pos] = key[i];
/* 896 */       newValue[pos] = value[i];
/*     */     } 
/* 898 */     newValue[newN] = value[this.n];
/* 899 */     this.n = newN;
/* 900 */     this.mask = mask;
/* 901 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 902 */     this.key = newKey;
/* 903 */     this.value = newValue;
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
/*     */   public Object2ObjectOpenHashMap<K, V> clone() {
/*     */     Object2ObjectOpenHashMap<K, V> c;
/*     */     try {
/* 920 */       c = (Object2ObjectOpenHashMap<K, V>)super.clone();
/* 921 */     } catch (CloneNotSupportedException cantHappen) {
/* 922 */       throw new InternalError();
/*     */     } 
/* 924 */     c.keys = null;
/* 925 */     c.values = null;
/* 926 */     c.entries = null;
/* 927 */     c.containsNullKey = this.containsNullKey;
/* 928 */     c.key = (K[])this.key.clone();
/* 929 */     c.value = (V[])this.value.clone();
/* 930 */     return c;
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
/* 943 */     int h = 0;
/* 944 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 945 */       while (this.key[i] == null)
/* 946 */         i++; 
/* 947 */       if (this != this.key[i])
/* 948 */         t = this.key[i].hashCode(); 
/* 949 */       if (this != this.value[i])
/* 950 */         t ^= (this.value[i] == null) ? 0 : this.value[i].hashCode(); 
/* 951 */       h += t;
/* 952 */       i++;
/*     */     } 
/*     */     
/* 955 */     if (this.containsNullKey)
/* 956 */       h += (this.value[this.n] == null) ? 0 : this.value[this.n].hashCode(); 
/* 957 */     return h;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 960 */     K[] key = this.key;
/* 961 */     V[] value = this.value;
/* 962 */     MapIterator i = new MapIterator();
/* 963 */     s.defaultWriteObject();
/* 964 */     for (int j = this.size; j-- != 0; ) {
/* 965 */       int e = i.nextEntry();
/* 966 */       s.writeObject(key[e]);
/* 967 */       s.writeObject(value[e]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 972 */     s.defaultReadObject();
/* 973 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 974 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 975 */     this.mask = this.n - 1;
/* 976 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 977 */     V[] value = this.value = (V[])new Object[this.n + 1];
/*     */ 
/*     */     
/* 980 */     for (int i = this.size; i-- != 0; ) {
/* 981 */       int pos; K k = (K)s.readObject();
/* 982 */       V v = (V)s.readObject();
/* 983 */       if (k == null) {
/* 984 */         pos = this.n;
/* 985 */         this.containsNullKey = true;
/*     */       } else {
/* 987 */         pos = HashCommon.mix(k.hashCode()) & this.mask;
/* 988 */         while (key[pos] != null)
/* 989 */           pos = pos + 1 & this.mask; 
/*     */       } 
/* 991 */       key[pos] = k;
/* 992 */       value[pos] = v;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkTable() {}
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2ObjectOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */