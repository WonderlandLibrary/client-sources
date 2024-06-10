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
/*     */ public class Reference2ObjectOpenHashMap<K, V>
/*     */   extends AbstractReference2ObjectMap<K, V>
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
/*     */   protected transient Reference2ObjectMap.FastEntrySet<K, V> entries;
/*     */   protected transient ReferenceSet<K> keys;
/*     */   protected transient ObjectCollection<V> values;
/*     */   
/*     */   public Reference2ObjectOpenHashMap(int expected, float f) {
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
/*     */   public Reference2ObjectOpenHashMap(int expected) {
/* 117 */     this(expected, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Reference2ObjectOpenHashMap() {
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
/*     */   public Reference2ObjectOpenHashMap(Map<? extends K, ? extends V> m, float f) {
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
/*     */   public Reference2ObjectOpenHashMap(Map<? extends K, ? extends V> m) {
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
/*     */   public Reference2ObjectOpenHashMap(Reference2ObjectMap<K, V> m, float f) {
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
/*     */   public Reference2ObjectOpenHashMap(Reference2ObjectMap<K, V> m) {
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
/*     */   public Reference2ObjectOpenHashMap(K[] k, V[] v, float f) {
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
/*     */   public Reference2ObjectOpenHashMap(K[] k, V[] v) {
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
/* 255 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/* 256 */       return -(pos + 1); 
/* 257 */     if (k == curr) {
/* 258 */       return pos;
/*     */     }
/*     */     while (true) {
/* 261 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/* 262 */         return -(pos + 1); 
/* 263 */       if (k == curr)
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
/* 308 */         int slot = HashCommon.mix(System.identityHashCode(curr)) & this.mask;
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
/* 329 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/* 330 */       return this.defRetValue; 
/* 331 */     if (k == curr)
/* 332 */       return removeEntry(pos); 
/*     */     while (true) {
/* 334 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/* 335 */         return this.defRetValue; 
/* 336 */       if (k == curr) {
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
/* 349 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/* 350 */       return this.defRetValue; 
/* 351 */     if (k == curr) {
/* 352 */       return this.value[pos];
/*     */     }
/*     */     while (true) {
/* 355 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/* 356 */         return this.defRetValue; 
/* 357 */       if (k == curr) {
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
/* 370 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/* 371 */       return false; 
/* 372 */     if (k == curr) {
/* 373 */       return true;
/*     */     }
/*     */     while (true) {
/* 376 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/* 377 */         return false; 
/* 378 */       if (k == curr)
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
/*     */     implements Reference2ObjectMap.Entry<K, V>, Map.Entry<K, V>
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
/* 433 */       return Reference2ObjectOpenHashMap.this.key[this.index];
/*     */     }
/*     */     
/*     */     public V getValue() {
/* 437 */       return Reference2ObjectOpenHashMap.this.value[this.index];
/*     */     }
/*     */     
/*     */     public V setValue(V v) {
/* 441 */       V oldValue = Reference2ObjectOpenHashMap.this.value[this.index];
/* 442 */       Reference2ObjectOpenHashMap.this.value[this.index] = v;
/* 443 */       return oldValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 448 */       if (!(o instanceof Map.Entry))
/* 449 */         return false; 
/* 450 */       Map.Entry<K, V> e = (Map.Entry<K, V>)o;
/* 451 */       return (Reference2ObjectOpenHashMap.this.key[this.index] == e.getKey() && Objects.equals(Reference2ObjectOpenHashMap.this.value[this.index], e.getValue()));
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 455 */       return System.identityHashCode(Reference2ObjectOpenHashMap.this.key[this.index]) ^ ((Reference2ObjectOpenHashMap.this.value[this.index] == null) ? 0 : Reference2ObjectOpenHashMap.this.value[this.index].hashCode());
/*     */     }
/*     */     
/*     */     public String toString() {
/* 459 */       return (new StringBuilder()).append(Reference2ObjectOpenHashMap.this.key[this.index]).append("=>").append(Reference2ObjectOpenHashMap.this.value[this.index]).toString();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class MapIterator
/*     */   {
/* 469 */     int pos = Reference2ObjectOpenHashMap.this.n;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 476 */     int last = -1;
/*     */     
/* 478 */     int c = Reference2ObjectOpenHashMap.this.size;
/*     */ 
/*     */ 
/*     */     
/* 482 */     boolean mustReturnNullKey = Reference2ObjectOpenHashMap.this.containsNullKey;
/*     */ 
/*     */     
/*     */     ReferenceArrayList<K> wrapped;
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 489 */       return (this.c != 0);
/*     */     }
/*     */     public int nextEntry() {
/* 492 */       if (!hasNext())
/* 493 */         throw new NoSuchElementException(); 
/* 494 */       this.c--;
/* 495 */       if (this.mustReturnNullKey) {
/* 496 */         this.mustReturnNullKey = false;
/* 497 */         return this.last = Reference2ObjectOpenHashMap.this.n;
/*     */       } 
/* 499 */       K[] key = Reference2ObjectOpenHashMap.this.key;
/*     */       while (true) {
/* 501 */         if (--this.pos < 0) {
/*     */           
/* 503 */           this.last = Integer.MIN_VALUE;
/* 504 */           K k = this.wrapped.get(-this.pos - 1);
/* 505 */           int p = HashCommon.mix(System.identityHashCode(k)) & Reference2ObjectOpenHashMap.this.mask;
/* 506 */           while (k != key[p])
/* 507 */             p = p + 1 & Reference2ObjectOpenHashMap.this.mask; 
/* 508 */           return p;
/*     */         } 
/* 510 */         if (key[this.pos] != null) {
/* 511 */           return this.last = this.pos;
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
/* 525 */       K[] key = Reference2ObjectOpenHashMap.this.key; while (true) {
/*     */         K curr; int last;
/* 527 */         pos = (last = pos) + 1 & Reference2ObjectOpenHashMap.this.mask;
/*     */         while (true) {
/* 529 */           if ((curr = key[pos]) == null) {
/* 530 */             key[last] = null;
/* 531 */             Reference2ObjectOpenHashMap.this.value[last] = null;
/*     */             return;
/*     */           } 
/* 534 */           int slot = HashCommon.mix(System.identityHashCode(curr)) & Reference2ObjectOpenHashMap.this.mask;
/* 535 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*     */             break; 
/* 537 */           pos = pos + 1 & Reference2ObjectOpenHashMap.this.mask;
/*     */         } 
/* 539 */         if (pos < last) {
/* 540 */           if (this.wrapped == null)
/* 541 */             this.wrapped = new ReferenceArrayList<>(2); 
/* 542 */           this.wrapped.add(key[pos]);
/*     */         } 
/* 544 */         key[last] = curr;
/* 545 */         Reference2ObjectOpenHashMap.this.value[last] = Reference2ObjectOpenHashMap.this.value[pos];
/*     */       } 
/*     */     }
/*     */     public void remove() {
/* 549 */       if (this.last == -1)
/* 550 */         throw new IllegalStateException(); 
/* 551 */       if (this.last == Reference2ObjectOpenHashMap.this.n) {
/* 552 */         Reference2ObjectOpenHashMap.this.containsNullKey = false;
/* 553 */         Reference2ObjectOpenHashMap.this.key[Reference2ObjectOpenHashMap.this.n] = null;
/* 554 */         Reference2ObjectOpenHashMap.this.value[Reference2ObjectOpenHashMap.this.n] = null;
/* 555 */       } else if (this.pos >= 0) {
/* 556 */         shiftKeys(this.last);
/*     */       } else {
/*     */         
/* 559 */         Reference2ObjectOpenHashMap.this.remove(this.wrapped.set(-this.pos - 1, null));
/* 560 */         this.last = -1;
/*     */         return;
/*     */       } 
/* 563 */       Reference2ObjectOpenHashMap.this.size--;
/* 564 */       this.last = -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int skip(int n) {
/* 569 */       int i = n;
/* 570 */       while (i-- != 0 && hasNext())
/* 571 */         nextEntry(); 
/* 572 */       return n - i - 1;
/*     */     }
/*     */     private MapIterator() {} }
/*     */   
/*     */   private class EntryIterator extends MapIterator implements ObjectIterator<Reference2ObjectMap.Entry<K, V>> { private Reference2ObjectOpenHashMap<K, V>.MapEntry entry;
/*     */     
/*     */     public Reference2ObjectOpenHashMap<K, V>.MapEntry next() {
/* 579 */       return this.entry = new Reference2ObjectOpenHashMap.MapEntry(nextEntry());
/*     */     }
/*     */     private EntryIterator() {}
/*     */     public void remove() {
/* 583 */       super.remove();
/* 584 */       this.entry.index = -1;
/*     */     } }
/*     */   
/*     */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Reference2ObjectMap.Entry<K, V>> { private FastEntryIterator() {
/* 588 */       this.entry = new Reference2ObjectOpenHashMap.MapEntry();
/*     */     } private final Reference2ObjectOpenHashMap<K, V>.MapEntry entry;
/*     */     public Reference2ObjectOpenHashMap<K, V>.MapEntry next() {
/* 591 */       this.entry.index = nextEntry();
/* 592 */       return this.entry;
/*     */     } }
/*     */ 
/*     */   
/*     */   private final class MapEntrySet extends AbstractObjectSet<Reference2ObjectMap.Entry<K, V>> implements Reference2ObjectMap.FastEntrySet<K, V> {
/*     */     private MapEntrySet() {}
/*     */     
/*     */     public ObjectIterator<Reference2ObjectMap.Entry<K, V>> iterator() {
/* 600 */       return new Reference2ObjectOpenHashMap.EntryIterator();
/*     */     }
/*     */     
/*     */     public ObjectIterator<Reference2ObjectMap.Entry<K, V>> fastIterator() {
/* 604 */       return new Reference2ObjectOpenHashMap.FastEntryIterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 609 */       if (!(o instanceof Map.Entry))
/* 610 */         return false; 
/* 611 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 612 */       K k = (K)e.getKey();
/* 613 */       V v = (V)e.getValue();
/* 614 */       if (k == null) {
/* 615 */         return (Reference2ObjectOpenHashMap.this.containsNullKey && Objects.equals(Reference2ObjectOpenHashMap.this.value[Reference2ObjectOpenHashMap.this.n], v));
/*     */       }
/* 617 */       K[] key = Reference2ObjectOpenHashMap.this.key;
/*     */       K curr;
/*     */       int pos;
/* 620 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2ObjectOpenHashMap.this.mask]) == null)
/*     */       {
/* 622 */         return false; } 
/* 623 */       if (k == curr) {
/* 624 */         return Objects.equals(Reference2ObjectOpenHashMap.this.value[pos], v);
/*     */       }
/*     */       while (true) {
/* 627 */         if ((curr = key[pos = pos + 1 & Reference2ObjectOpenHashMap.this.mask]) == null)
/* 628 */           return false; 
/* 629 */         if (k == curr) {
/* 630 */           return Objects.equals(Reference2ObjectOpenHashMap.this.value[pos], v);
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
/* 642 */         if (Reference2ObjectOpenHashMap.this.containsNullKey && Objects.equals(Reference2ObjectOpenHashMap.this.value[Reference2ObjectOpenHashMap.this.n], v)) {
/* 643 */           Reference2ObjectOpenHashMap.this.removeNullEntry();
/* 644 */           return true;
/*     */         } 
/* 646 */         return false;
/*     */       } 
/*     */       
/* 649 */       K[] key = Reference2ObjectOpenHashMap.this.key;
/*     */       K curr;
/*     */       int pos;
/* 652 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2ObjectOpenHashMap.this.mask]) == null)
/*     */       {
/* 654 */         return false; } 
/* 655 */       if (curr == k) {
/* 656 */         if (Objects.equals(Reference2ObjectOpenHashMap.this.value[pos], v)) {
/* 657 */           Reference2ObjectOpenHashMap.this.removeEntry(pos);
/* 658 */           return true;
/*     */         } 
/* 660 */         return false;
/*     */       } 
/*     */       while (true) {
/* 663 */         if ((curr = key[pos = pos + 1 & Reference2ObjectOpenHashMap.this.mask]) == null)
/* 664 */           return false; 
/* 665 */         if (curr == k && 
/* 666 */           Objects.equals(Reference2ObjectOpenHashMap.this.value[pos], v)) {
/* 667 */           Reference2ObjectOpenHashMap.this.removeEntry(pos);
/* 668 */           return true;
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 675 */       return Reference2ObjectOpenHashMap.this.size;
/*     */     }
/*     */     
/*     */     public void clear() {
/* 679 */       Reference2ObjectOpenHashMap.this.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super Reference2ObjectMap.Entry<K, V>> consumer) {
/* 684 */       if (Reference2ObjectOpenHashMap.this.containsNullKey)
/* 685 */         consumer.accept(new AbstractReference2ObjectMap.BasicEntry<>(Reference2ObjectOpenHashMap.this.key[Reference2ObjectOpenHashMap.this.n], Reference2ObjectOpenHashMap.this.value[Reference2ObjectOpenHashMap.this.n])); 
/* 686 */       for (int pos = Reference2ObjectOpenHashMap.this.n; pos-- != 0;) {
/* 687 */         if (Reference2ObjectOpenHashMap.this.key[pos] != null)
/* 688 */           consumer.accept(new AbstractReference2ObjectMap.BasicEntry<>(Reference2ObjectOpenHashMap.this.key[pos], Reference2ObjectOpenHashMap.this.value[pos])); 
/*     */       } 
/*     */     }
/*     */     
/*     */     public void fastForEach(Consumer<? super Reference2ObjectMap.Entry<K, V>> consumer) {
/* 693 */       AbstractReference2ObjectMap.BasicEntry<K, V> entry = new AbstractReference2ObjectMap.BasicEntry<>();
/* 694 */       if (Reference2ObjectOpenHashMap.this.containsNullKey) {
/* 695 */         entry.key = Reference2ObjectOpenHashMap.this.key[Reference2ObjectOpenHashMap.this.n];
/* 696 */         entry.value = Reference2ObjectOpenHashMap.this.value[Reference2ObjectOpenHashMap.this.n];
/* 697 */         consumer.accept(entry);
/*     */       } 
/* 699 */       for (int pos = Reference2ObjectOpenHashMap.this.n; pos-- != 0;) {
/* 700 */         if (Reference2ObjectOpenHashMap.this.key[pos] != null) {
/* 701 */           entry.key = Reference2ObjectOpenHashMap.this.key[pos];
/* 702 */           entry.value = Reference2ObjectOpenHashMap.this.value[pos];
/* 703 */           consumer.accept(entry);
/*     */         } 
/*     */       } 
/*     */     } }
/*     */   
/*     */   public Reference2ObjectMap.FastEntrySet<K, V> reference2ObjectEntrySet() {
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
/* 728 */       return Reference2ObjectOpenHashMap.this.key[nextEntry()];
/*     */     } }
/*     */   
/*     */   private final class KeySet extends AbstractReferenceSet<K> { private KeySet() {}
/*     */     
/*     */     public ObjectIterator<K> iterator() {
/* 734 */       return new Reference2ObjectOpenHashMap.KeyIterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super K> consumer) {
/* 739 */       if (Reference2ObjectOpenHashMap.this.containsNullKey)
/* 740 */         consumer.accept(Reference2ObjectOpenHashMap.this.key[Reference2ObjectOpenHashMap.this.n]); 
/* 741 */       for (int pos = Reference2ObjectOpenHashMap.this.n; pos-- != 0; ) {
/* 742 */         K k = Reference2ObjectOpenHashMap.this.key[pos];
/* 743 */         if (k != null)
/* 744 */           consumer.accept(k); 
/*     */       } 
/*     */     }
/*     */     
/*     */     public int size() {
/* 749 */       return Reference2ObjectOpenHashMap.this.size;
/*     */     }
/*     */     
/*     */     public boolean contains(Object k) {
/* 753 */       return Reference2ObjectOpenHashMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public boolean remove(Object k) {
/* 757 */       int oldSize = Reference2ObjectOpenHashMap.this.size;
/* 758 */       Reference2ObjectOpenHashMap.this.remove(k);
/* 759 */       return (Reference2ObjectOpenHashMap.this.size != oldSize);
/*     */     }
/*     */     
/*     */     public void clear() {
/* 763 */       Reference2ObjectOpenHashMap.this.clear();
/*     */     } }
/*     */ 
/*     */   
/*     */   public ReferenceSet<K> keySet() {
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
/* 787 */       return Reference2ObjectOpenHashMap.this.value[nextEntry()];
/*     */     }
/*     */   }
/*     */   
/*     */   public ObjectCollection<V> values() {
/* 792 */     if (this.values == null)
/* 793 */       this.values = new AbstractObjectCollection<V>()
/*     */         {
/*     */           public ObjectIterator<V> iterator() {
/* 796 */             return new Reference2ObjectOpenHashMap.ValueIterator();
/*     */           }
/*     */           
/*     */           public int size() {
/* 800 */             return Reference2ObjectOpenHashMap.this.size;
/*     */           }
/*     */           
/*     */           public boolean contains(Object v) {
/* 804 */             return Reference2ObjectOpenHashMap.this.containsValue(v);
/*     */           }
/*     */           
/*     */           public void clear() {
/* 808 */             Reference2ObjectOpenHashMap.this.clear();
/*     */           }
/*     */           
/*     */           public void forEach(Consumer<? super V> consumer)
/*     */           {
/* 813 */             if (Reference2ObjectOpenHashMap.this.containsNullKey)
/* 814 */               consumer.accept(Reference2ObjectOpenHashMap.this.value[Reference2ObjectOpenHashMap.this.n]); 
/* 815 */             for (int pos = Reference2ObjectOpenHashMap.this.n; pos-- != 0;) {
/* 816 */               if (Reference2ObjectOpenHashMap.this.key[pos] != null)
/* 817 */                 consumer.accept(Reference2ObjectOpenHashMap.this.value[pos]); 
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
/* 893 */       if (newKey[pos = HashCommon.mix(System.identityHashCode(key[i])) & mask] != null)
/*     */       {
/* 895 */         while (newKey[pos = pos + 1 & mask] != null); } 
/* 896 */       newKey[pos] = key[i];
/* 897 */       newValue[pos] = value[i];
/*     */     } 
/* 899 */     newValue[newN] = value[this.n];
/* 900 */     this.n = newN;
/* 901 */     this.mask = mask;
/* 902 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 903 */     this.key = newKey;
/* 904 */     this.value = newValue;
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
/*     */   public Reference2ObjectOpenHashMap<K, V> clone() {
/*     */     Reference2ObjectOpenHashMap<K, V> c;
/*     */     try {
/* 921 */       c = (Reference2ObjectOpenHashMap<K, V>)super.clone();
/* 922 */     } catch (CloneNotSupportedException cantHappen) {
/* 923 */       throw new InternalError();
/*     */     } 
/* 925 */     c.keys = null;
/* 926 */     c.values = null;
/* 927 */     c.entries = null;
/* 928 */     c.containsNullKey = this.containsNullKey;
/* 929 */     c.key = (K[])this.key.clone();
/* 930 */     c.value = (V[])this.value.clone();
/* 931 */     return c;
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
/* 944 */     int h = 0;
/* 945 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 946 */       while (this.key[i] == null)
/* 947 */         i++; 
/* 948 */       if (this != this.key[i])
/* 949 */         t = System.identityHashCode(this.key[i]); 
/* 950 */       if (this != this.value[i])
/* 951 */         t ^= (this.value[i] == null) ? 0 : this.value[i].hashCode(); 
/* 952 */       h += t;
/* 953 */       i++;
/*     */     } 
/*     */     
/* 956 */     if (this.containsNullKey)
/* 957 */       h += (this.value[this.n] == null) ? 0 : this.value[this.n].hashCode(); 
/* 958 */     return h;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 961 */     K[] key = this.key;
/* 962 */     V[] value = this.value;
/* 963 */     MapIterator i = new MapIterator();
/* 964 */     s.defaultWriteObject();
/* 965 */     for (int j = this.size; j-- != 0; ) {
/* 966 */       int e = i.nextEntry();
/* 967 */       s.writeObject(key[e]);
/* 968 */       s.writeObject(value[e]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 973 */     s.defaultReadObject();
/* 974 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 975 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 976 */     this.mask = this.n - 1;
/* 977 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 978 */     V[] value = this.value = (V[])new Object[this.n + 1];
/*     */ 
/*     */     
/* 981 */     for (int i = this.size; i-- != 0; ) {
/* 982 */       int pos; K k = (K)s.readObject();
/* 983 */       V v = (V)s.readObject();
/* 984 */       if (k == null) {
/* 985 */         pos = this.n;
/* 986 */         this.containsNullKey = true;
/*     */       } else {
/* 988 */         pos = HashCommon.mix(System.identityHashCode(k)) & this.mask;
/* 989 */         while (key[pos] != null)
/* 990 */           pos = pos + 1 & this.mask; 
/*     */       } 
/* 992 */       key[pos] = k;
/* 993 */       value[pos] = v;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkTable() {}
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Reference2ObjectOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */