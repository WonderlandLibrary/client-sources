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
/*     */ 
/*     */ public class Reference2ReferenceOpenHashMap<K, V>
/*     */   extends AbstractReference2ReferenceMap<K, V>
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
/*     */   protected transient Reference2ReferenceMap.FastEntrySet<K, V> entries;
/*     */   protected transient ReferenceSet<K> keys;
/*     */   protected transient ReferenceCollection<V> values;
/*     */   
/*     */   public Reference2ReferenceOpenHashMap(int expected, float f) {
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
/*     */   public Reference2ReferenceOpenHashMap(int expected) {
/* 117 */     this(expected, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Reference2ReferenceOpenHashMap() {
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
/*     */   public Reference2ReferenceOpenHashMap(Map<? extends K, ? extends V> m, float f) {
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
/*     */   public Reference2ReferenceOpenHashMap(Map<? extends K, ? extends V> m) {
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
/*     */   public Reference2ReferenceOpenHashMap(Reference2ReferenceMap<K, V> m, float f) {
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
/*     */   public Reference2ReferenceOpenHashMap(Reference2ReferenceMap<K, V> m) {
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
/*     */   public Reference2ReferenceOpenHashMap(K[] k, V[] v, float f) {
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
/*     */   public Reference2ReferenceOpenHashMap(K[] k, V[] v) {
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
/*     */     implements Reference2ReferenceMap.Entry<K, V>, Map.Entry<K, V>
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
/* 433 */       return Reference2ReferenceOpenHashMap.this.key[this.index];
/*     */     }
/*     */     
/*     */     public V getValue() {
/* 437 */       return Reference2ReferenceOpenHashMap.this.value[this.index];
/*     */     }
/*     */     
/*     */     public V setValue(V v) {
/* 441 */       V oldValue = Reference2ReferenceOpenHashMap.this.value[this.index];
/* 442 */       Reference2ReferenceOpenHashMap.this.value[this.index] = v;
/* 443 */       return oldValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 448 */       if (!(o instanceof Map.Entry))
/* 449 */         return false; 
/* 450 */       Map.Entry<K, V> e = (Map.Entry<K, V>)o;
/* 451 */       return (Reference2ReferenceOpenHashMap.this.key[this.index] == e.getKey() && Reference2ReferenceOpenHashMap.this.value[this.index] == e.getValue());
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 455 */       return System.identityHashCode(Reference2ReferenceOpenHashMap.this.key[this.index]) ^ (
/* 456 */         (Reference2ReferenceOpenHashMap.this.value[this.index] == null) ? 0 : System.identityHashCode(Reference2ReferenceOpenHashMap.this.value[this.index]));
/*     */     }
/*     */     
/*     */     public String toString() {
/* 460 */       return (new StringBuilder()).append(Reference2ReferenceOpenHashMap.this.key[this.index]).append("=>").append(Reference2ReferenceOpenHashMap.this.value[this.index]).toString();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class MapIterator
/*     */   {
/* 470 */     int pos = Reference2ReferenceOpenHashMap.this.n;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 477 */     int last = -1;
/*     */     
/* 479 */     int c = Reference2ReferenceOpenHashMap.this.size;
/*     */ 
/*     */ 
/*     */     
/* 483 */     boolean mustReturnNullKey = Reference2ReferenceOpenHashMap.this.containsNullKey;
/*     */ 
/*     */     
/*     */     ReferenceArrayList<K> wrapped;
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
/* 498 */         return this.last = Reference2ReferenceOpenHashMap.this.n;
/*     */       } 
/* 500 */       K[] key = Reference2ReferenceOpenHashMap.this.key;
/*     */       while (true) {
/* 502 */         if (--this.pos < 0) {
/*     */           
/* 504 */           this.last = Integer.MIN_VALUE;
/* 505 */           K k = this.wrapped.get(-this.pos - 1);
/* 506 */           int p = HashCommon.mix(System.identityHashCode(k)) & Reference2ReferenceOpenHashMap.this.mask;
/* 507 */           while (k != key[p])
/* 508 */             p = p + 1 & Reference2ReferenceOpenHashMap.this.mask; 
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
/* 526 */       K[] key = Reference2ReferenceOpenHashMap.this.key; while (true) {
/*     */         K curr; int last;
/* 528 */         pos = (last = pos) + 1 & Reference2ReferenceOpenHashMap.this.mask;
/*     */         while (true) {
/* 530 */           if ((curr = key[pos]) == null) {
/* 531 */             key[last] = null;
/* 532 */             Reference2ReferenceOpenHashMap.this.value[last] = null;
/*     */             return;
/*     */           } 
/* 535 */           int slot = HashCommon.mix(System.identityHashCode(curr)) & Reference2ReferenceOpenHashMap.this.mask;
/* 536 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*     */             break; 
/* 538 */           pos = pos + 1 & Reference2ReferenceOpenHashMap.this.mask;
/*     */         } 
/* 540 */         if (pos < last) {
/* 541 */           if (this.wrapped == null)
/* 542 */             this.wrapped = new ReferenceArrayList<>(2); 
/* 543 */           this.wrapped.add(key[pos]);
/*     */         } 
/* 545 */         key[last] = curr;
/* 546 */         Reference2ReferenceOpenHashMap.this.value[last] = Reference2ReferenceOpenHashMap.this.value[pos];
/*     */       } 
/*     */     }
/*     */     public void remove() {
/* 550 */       if (this.last == -1)
/* 551 */         throw new IllegalStateException(); 
/* 552 */       if (this.last == Reference2ReferenceOpenHashMap.this.n) {
/* 553 */         Reference2ReferenceOpenHashMap.this.containsNullKey = false;
/* 554 */         Reference2ReferenceOpenHashMap.this.key[Reference2ReferenceOpenHashMap.this.n] = null;
/* 555 */         Reference2ReferenceOpenHashMap.this.value[Reference2ReferenceOpenHashMap.this.n] = null;
/* 556 */       } else if (this.pos >= 0) {
/* 557 */         shiftKeys(this.last);
/*     */       } else {
/*     */         
/* 560 */         Reference2ReferenceOpenHashMap.this.remove(this.wrapped.set(-this.pos - 1, null));
/* 561 */         this.last = -1;
/*     */         return;
/*     */       } 
/* 564 */       Reference2ReferenceOpenHashMap.this.size--;
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
/*     */   private class EntryIterator extends MapIterator implements ObjectIterator<Reference2ReferenceMap.Entry<K, V>> { private Reference2ReferenceOpenHashMap<K, V>.MapEntry entry;
/*     */     
/*     */     public Reference2ReferenceOpenHashMap<K, V>.MapEntry next() {
/* 580 */       return this.entry = new Reference2ReferenceOpenHashMap.MapEntry(nextEntry());
/*     */     }
/*     */     private EntryIterator() {}
/*     */     public void remove() {
/* 584 */       super.remove();
/* 585 */       this.entry.index = -1;
/*     */     } }
/*     */   
/*     */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Reference2ReferenceMap.Entry<K, V>> { private FastEntryIterator() {
/* 589 */       this.entry = new Reference2ReferenceOpenHashMap.MapEntry();
/*     */     } private final Reference2ReferenceOpenHashMap<K, V>.MapEntry entry;
/*     */     public Reference2ReferenceOpenHashMap<K, V>.MapEntry next() {
/* 592 */       this.entry.index = nextEntry();
/* 593 */       return this.entry;
/*     */     } }
/*     */ 
/*     */   
/*     */   private final class MapEntrySet extends AbstractObjectSet<Reference2ReferenceMap.Entry<K, V>> implements Reference2ReferenceMap.FastEntrySet<K, V> {
/*     */     private MapEntrySet() {}
/*     */     
/*     */     public ObjectIterator<Reference2ReferenceMap.Entry<K, V>> iterator() {
/* 601 */       return new Reference2ReferenceOpenHashMap.EntryIterator();
/*     */     }
/*     */     
/*     */     public ObjectIterator<Reference2ReferenceMap.Entry<K, V>> fastIterator() {
/* 605 */       return new Reference2ReferenceOpenHashMap.FastEntryIterator();
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
/* 616 */         return (Reference2ReferenceOpenHashMap.this.containsNullKey && Reference2ReferenceOpenHashMap.this.value[Reference2ReferenceOpenHashMap.this.n] == v);
/*     */       }
/* 618 */       K[] key = Reference2ReferenceOpenHashMap.this.key;
/*     */       K curr;
/*     */       int pos;
/* 621 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2ReferenceOpenHashMap.this.mask]) == null)
/*     */       {
/* 623 */         return false; } 
/* 624 */       if (k == curr) {
/* 625 */         return (Reference2ReferenceOpenHashMap.this.value[pos] == v);
/*     */       }
/*     */       while (true) {
/* 628 */         if ((curr = key[pos = pos + 1 & Reference2ReferenceOpenHashMap.this.mask]) == null)
/* 629 */           return false; 
/* 630 */         if (k == curr) {
/* 631 */           return (Reference2ReferenceOpenHashMap.this.value[pos] == v);
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
/* 643 */         if (Reference2ReferenceOpenHashMap.this.containsNullKey && Reference2ReferenceOpenHashMap.this.value[Reference2ReferenceOpenHashMap.this.n] == v) {
/* 644 */           Reference2ReferenceOpenHashMap.this.removeNullEntry();
/* 645 */           return true;
/*     */         } 
/* 647 */         return false;
/*     */       } 
/*     */       
/* 650 */       K[] key = Reference2ReferenceOpenHashMap.this.key;
/*     */       K curr;
/*     */       int pos;
/* 653 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2ReferenceOpenHashMap.this.mask]) == null)
/*     */       {
/* 655 */         return false; } 
/* 656 */       if (curr == k) {
/* 657 */         if (Reference2ReferenceOpenHashMap.this.value[pos] == v) {
/* 658 */           Reference2ReferenceOpenHashMap.this.removeEntry(pos);
/* 659 */           return true;
/*     */         } 
/* 661 */         return false;
/*     */       } 
/*     */       while (true) {
/* 664 */         if ((curr = key[pos = pos + 1 & Reference2ReferenceOpenHashMap.this.mask]) == null)
/* 665 */           return false; 
/* 666 */         if (curr == k && 
/* 667 */           Reference2ReferenceOpenHashMap.this.value[pos] == v) {
/* 668 */           Reference2ReferenceOpenHashMap.this.removeEntry(pos);
/* 669 */           return true;
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 676 */       return Reference2ReferenceOpenHashMap.this.size;
/*     */     }
/*     */     
/*     */     public void clear() {
/* 680 */       Reference2ReferenceOpenHashMap.this.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super Reference2ReferenceMap.Entry<K, V>> consumer) {
/* 685 */       if (Reference2ReferenceOpenHashMap.this.containsNullKey)
/* 686 */         consumer.accept(new AbstractReference2ReferenceMap.BasicEntry<>(Reference2ReferenceOpenHashMap.this.key[Reference2ReferenceOpenHashMap.this.n], Reference2ReferenceOpenHashMap.this.value[Reference2ReferenceOpenHashMap.this.n])); 
/* 687 */       for (int pos = Reference2ReferenceOpenHashMap.this.n; pos-- != 0;) {
/* 688 */         if (Reference2ReferenceOpenHashMap.this.key[pos] != null)
/* 689 */           consumer.accept(new AbstractReference2ReferenceMap.BasicEntry<>(Reference2ReferenceOpenHashMap.this.key[pos], Reference2ReferenceOpenHashMap.this.value[pos])); 
/*     */       } 
/*     */     }
/*     */     
/*     */     public void fastForEach(Consumer<? super Reference2ReferenceMap.Entry<K, V>> consumer) {
/* 694 */       AbstractReference2ReferenceMap.BasicEntry<K, V> entry = new AbstractReference2ReferenceMap.BasicEntry<>();
/* 695 */       if (Reference2ReferenceOpenHashMap.this.containsNullKey) {
/* 696 */         entry.key = Reference2ReferenceOpenHashMap.this.key[Reference2ReferenceOpenHashMap.this.n];
/* 697 */         entry.value = Reference2ReferenceOpenHashMap.this.value[Reference2ReferenceOpenHashMap.this.n];
/* 698 */         consumer.accept(entry);
/*     */       } 
/* 700 */       for (int pos = Reference2ReferenceOpenHashMap.this.n; pos-- != 0;) {
/* 701 */         if (Reference2ReferenceOpenHashMap.this.key[pos] != null) {
/* 702 */           entry.key = Reference2ReferenceOpenHashMap.this.key[pos];
/* 703 */           entry.value = Reference2ReferenceOpenHashMap.this.value[pos];
/* 704 */           consumer.accept(entry);
/*     */         } 
/*     */       } 
/*     */     } }
/*     */   
/*     */   public Reference2ReferenceMap.FastEntrySet<K, V> reference2ReferenceEntrySet() {
/* 710 */     if (this.entries == null)
/* 711 */       this.entries = new MapEntrySet(); 
/* 712 */     return this.entries;
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
/* 729 */       return Reference2ReferenceOpenHashMap.this.key[nextEntry()];
/*     */     } }
/*     */   
/*     */   private final class KeySet extends AbstractReferenceSet<K> { private KeySet() {}
/*     */     
/*     */     public ObjectIterator<K> iterator() {
/* 735 */       return new Reference2ReferenceOpenHashMap.KeyIterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super K> consumer) {
/* 740 */       if (Reference2ReferenceOpenHashMap.this.containsNullKey)
/* 741 */         consumer.accept(Reference2ReferenceOpenHashMap.this.key[Reference2ReferenceOpenHashMap.this.n]); 
/* 742 */       for (int pos = Reference2ReferenceOpenHashMap.this.n; pos-- != 0; ) {
/* 743 */         K k = Reference2ReferenceOpenHashMap.this.key[pos];
/* 744 */         if (k != null)
/* 745 */           consumer.accept(k); 
/*     */       } 
/*     */     }
/*     */     
/*     */     public int size() {
/* 750 */       return Reference2ReferenceOpenHashMap.this.size;
/*     */     }
/*     */     
/*     */     public boolean contains(Object k) {
/* 754 */       return Reference2ReferenceOpenHashMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public boolean remove(Object k) {
/* 758 */       int oldSize = Reference2ReferenceOpenHashMap.this.size;
/* 759 */       Reference2ReferenceOpenHashMap.this.remove(k);
/* 760 */       return (Reference2ReferenceOpenHashMap.this.size != oldSize);
/*     */     }
/*     */     
/*     */     public void clear() {
/* 764 */       Reference2ReferenceOpenHashMap.this.clear();
/*     */     } }
/*     */ 
/*     */   
/*     */   public ReferenceSet<K> keySet() {
/* 769 */     if (this.keys == null)
/* 770 */       this.keys = new KeySet(); 
/* 771 */     return this.keys;
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
/* 788 */       return Reference2ReferenceOpenHashMap.this.value[nextEntry()];
/*     */     }
/*     */   }
/*     */   
/*     */   public ReferenceCollection<V> values() {
/* 793 */     if (this.values == null)
/* 794 */       this.values = new AbstractReferenceCollection<V>()
/*     */         {
/*     */           public ObjectIterator<V> iterator() {
/* 797 */             return new Reference2ReferenceOpenHashMap.ValueIterator();
/*     */           }
/*     */           
/*     */           public int size() {
/* 801 */             return Reference2ReferenceOpenHashMap.this.size;
/*     */           }
/*     */           
/*     */           public boolean contains(Object v) {
/* 805 */             return Reference2ReferenceOpenHashMap.this.containsValue(v);
/*     */           }
/*     */           
/*     */           public void clear() {
/* 809 */             Reference2ReferenceOpenHashMap.this.clear();
/*     */           }
/*     */           
/*     */           public void forEach(Consumer<? super V> consumer)
/*     */           {
/* 814 */             if (Reference2ReferenceOpenHashMap.this.containsNullKey)
/* 815 */               consumer.accept(Reference2ReferenceOpenHashMap.this.value[Reference2ReferenceOpenHashMap.this.n]); 
/* 816 */             for (int pos = Reference2ReferenceOpenHashMap.this.n; pos-- != 0;) {
/* 817 */               if (Reference2ReferenceOpenHashMap.this.key[pos] != null)
/* 818 */                 consumer.accept(Reference2ReferenceOpenHashMap.this.value[pos]); 
/*     */             }  }
/*     */         }; 
/* 821 */     return this.values;
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
/* 838 */     return trim(this.size);
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
/* 862 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 863 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 864 */       return true; 
/*     */     try {
/* 866 */       rehash(l);
/* 867 */     } catch (OutOfMemoryError cantDoIt) {
/* 868 */       return false;
/*     */     } 
/* 870 */     return true;
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
/* 886 */     K[] key = this.key;
/* 887 */     V[] value = this.value;
/* 888 */     int mask = newN - 1;
/* 889 */     K[] newKey = (K[])new Object[newN + 1];
/* 890 */     V[] newValue = (V[])new Object[newN + 1];
/* 891 */     int i = this.n;
/* 892 */     for (int j = realSize(); j-- != 0; ) {
/* 893 */       while (key[--i] == null); int pos;
/* 894 */       if (newKey[pos = HashCommon.mix(System.identityHashCode(key[i])) & mask] != null)
/*     */       {
/* 896 */         while (newKey[pos = pos + 1 & mask] != null); } 
/* 897 */       newKey[pos] = key[i];
/* 898 */       newValue[pos] = value[i];
/*     */     } 
/* 900 */     newValue[newN] = value[this.n];
/* 901 */     this.n = newN;
/* 902 */     this.mask = mask;
/* 903 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 904 */     this.key = newKey;
/* 905 */     this.value = newValue;
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
/*     */   public Reference2ReferenceOpenHashMap<K, V> clone() {
/*     */     Reference2ReferenceOpenHashMap<K, V> c;
/*     */     try {
/* 922 */       c = (Reference2ReferenceOpenHashMap<K, V>)super.clone();
/* 923 */     } catch (CloneNotSupportedException cantHappen) {
/* 924 */       throw new InternalError();
/*     */     } 
/* 926 */     c.keys = null;
/* 927 */     c.values = null;
/* 928 */     c.entries = null;
/* 929 */     c.containsNullKey = this.containsNullKey;
/* 930 */     c.key = (K[])this.key.clone();
/* 931 */     c.value = (V[])this.value.clone();
/* 932 */     return c;
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
/* 945 */     int h = 0;
/* 946 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 947 */       while (this.key[i] == null)
/* 948 */         i++; 
/* 949 */       if (this != this.key[i])
/* 950 */         t = System.identityHashCode(this.key[i]); 
/* 951 */       if (this != this.value[i])
/* 952 */         t ^= (this.value[i] == null) ? 0 : System.identityHashCode(this.value[i]); 
/* 953 */       h += t;
/* 954 */       i++;
/*     */     } 
/*     */     
/* 957 */     if (this.containsNullKey)
/* 958 */       h += (this.value[this.n] == null) ? 0 : System.identityHashCode(this.value[this.n]); 
/* 959 */     return h;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 962 */     K[] key = this.key;
/* 963 */     V[] value = this.value;
/* 964 */     MapIterator i = new MapIterator();
/* 965 */     s.defaultWriteObject();
/* 966 */     for (int j = this.size; j-- != 0; ) {
/* 967 */       int e = i.nextEntry();
/* 968 */       s.writeObject(key[e]);
/* 969 */       s.writeObject(value[e]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 974 */     s.defaultReadObject();
/* 975 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 976 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 977 */     this.mask = this.n - 1;
/* 978 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 979 */     V[] value = this.value = (V[])new Object[this.n + 1];
/*     */ 
/*     */     
/* 982 */     for (int i = this.size; i-- != 0; ) {
/* 983 */       int pos; K k = (K)s.readObject();
/* 984 */       V v = (V)s.readObject();
/* 985 */       if (k == null) {
/* 986 */         pos = this.n;
/* 987 */         this.containsNullKey = true;
/*     */       } else {
/* 989 */         pos = HashCommon.mix(System.identityHashCode(k)) & this.mask;
/* 990 */         while (key[pos] != null)
/* 991 */           pos = pos + 1 & this.mask; 
/*     */       } 
/* 993 */       key[pos] = k;
/* 994 */       value[pos] = v;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkTable() {}
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Reference2ReferenceOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */