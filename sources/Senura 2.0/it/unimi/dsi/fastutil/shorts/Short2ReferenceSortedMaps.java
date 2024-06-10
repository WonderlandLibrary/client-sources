/*     */ package it.unimi.dsi.fastutil.shorts;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterable;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSortedSets;
/*     */ import java.io.Serializable;
/*     */ import java.util.Comparator;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.SortedMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Short2ReferenceSortedMaps
/*     */ {
/*     */   public static Comparator<? super Map.Entry<Short, ?>> entryComparator(ShortComparator comparator) {
/*  43 */     return (x, y) -> comparator.compare(((Short)x.getKey()).shortValue(), ((Short)y.getKey()).shortValue());
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
/*     */   public static <V> ObjectBidirectionalIterator<Short2ReferenceMap.Entry<V>> fastIterator(Short2ReferenceSortedMap<V> map) {
/*  61 */     ObjectSortedSet<Short2ReferenceMap.Entry<V>> entries = map.short2ReferenceEntrySet();
/*  62 */     return (entries instanceof Short2ReferenceSortedMap.FastSortedEntrySet) ? (
/*  63 */       (Short2ReferenceSortedMap.FastSortedEntrySet)entries).fastIterator() : 
/*  64 */       entries.iterator();
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
/*     */   public static <V> ObjectBidirectionalIterable<Short2ReferenceMap.Entry<V>> fastIterable(Short2ReferenceSortedMap<V> map) {
/*  81 */     ObjectSortedSet<Short2ReferenceMap.Entry<V>> entries = map.short2ReferenceEntrySet();
/*     */     
/*  83 */     Objects.requireNonNull((Short2ReferenceSortedMap.FastSortedEntrySet)entries); return (entries instanceof Short2ReferenceSortedMap.FastSortedEntrySet) ? (Short2ReferenceSortedMap.FastSortedEntrySet)entries::fastIterator : 
/*  84 */       (ObjectBidirectionalIterable<Short2ReferenceMap.Entry<V>>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap<V>
/*     */     extends Short2ReferenceMaps.EmptyMap<V>
/*     */     implements Short2ReferenceSortedMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ShortComparator comparator() {
/* 103 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Short2ReferenceMap.Entry<V>> short2ReferenceEntrySet() {
/* 108 */       return (ObjectSortedSet<Short2ReferenceMap.Entry<V>>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Short, V>> entrySet() {
/* 119 */       return (ObjectSortedSet<Map.Entry<Short, V>>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSortedSet keySet() {
/* 124 */       return ShortSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public Short2ReferenceSortedMap<V> subMap(short from, short to) {
/* 129 */       return Short2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Short2ReferenceSortedMap<V> headMap(short to) {
/* 134 */       return Short2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Short2ReferenceSortedMap<V> tailMap(short from) {
/* 139 */       return Short2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public short firstShortKey() {
/* 143 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public short lastShortKey() {
/* 147 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short2ReferenceSortedMap<V> headMap(Short oto) {
/* 157 */       return headMap(oto.shortValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short2ReferenceSortedMap<V> tailMap(Short ofrom) {
/* 167 */       return tailMap(ofrom.shortValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short2ReferenceSortedMap<V> subMap(Short ofrom, Short oto) {
/* 177 */       return subMap(ofrom.shortValue(), oto.shortValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short firstKey() {
/* 187 */       return Short.valueOf(firstShortKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short lastKey() {
/* 197 */       return Short.valueOf(lastShortKey());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 204 */   public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <V> Short2ReferenceSortedMap<V> emptyMap() {
/* 215 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<V>
/*     */     extends Short2ReferenceMaps.Singleton<V>
/*     */     implements Short2ReferenceSortedMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final ShortComparator comparator;
/*     */ 
/*     */ 
/*     */     
/*     */     protected Singleton(short key, V value, ShortComparator comparator) {
/* 232 */       super(key, value);
/* 233 */       this.comparator = comparator;
/*     */     }
/*     */     protected Singleton(short key, V value) {
/* 236 */       this(key, value, (ShortComparator)null);
/*     */     }
/*     */     
/*     */     final int compare(short k1, short k2) {
/* 240 */       return (this.comparator == null) ? Short.compare(k1, k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */     
/*     */     public ShortComparator comparator() {
/* 244 */       return this.comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Short2ReferenceMap.Entry<V>> short2ReferenceEntrySet() {
/* 249 */       if (this.entries == null)
/* 250 */         this.entries = (ObjectSet<Short2ReferenceMap.Entry<V>>)ObjectSortedSets.singleton(new AbstractShort2ReferenceMap.BasicEntry<>(this.key, this.value), 
/* 251 */             Short2ReferenceSortedMaps.entryComparator(this.comparator)); 
/* 252 */       return (ObjectSortedSet<Short2ReferenceMap.Entry<V>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Short, V>> entrySet() {
/* 263 */       return (ObjectSortedSet)short2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public ShortSortedSet keySet() {
/* 267 */       if (this.keys == null)
/* 268 */         this.keys = ShortSortedSets.singleton(this.key, this.comparator); 
/* 269 */       return (ShortSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Short2ReferenceSortedMap<V> subMap(short from, short to) {
/* 274 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0)
/* 275 */         return this; 
/* 276 */       return Short2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Short2ReferenceSortedMap<V> headMap(short to) {
/* 281 */       if (compare(this.key, to) < 0)
/* 282 */         return this; 
/* 283 */       return Short2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Short2ReferenceSortedMap<V> tailMap(short from) {
/* 288 */       if (compare(from, this.key) <= 0)
/* 289 */         return this; 
/* 290 */       return Short2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public short firstShortKey() {
/* 294 */       return this.key;
/*     */     }
/*     */     
/*     */     public short lastShortKey() {
/* 298 */       return this.key;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short2ReferenceSortedMap<V> headMap(Short oto) {
/* 308 */       return headMap(oto.shortValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short2ReferenceSortedMap<V> tailMap(Short ofrom) {
/* 318 */       return tailMap(ofrom.shortValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short2ReferenceSortedMap<V> subMap(Short ofrom, Short oto) {
/* 328 */       return subMap(ofrom.shortValue(), oto.shortValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short firstKey() {
/* 338 */       return Short.valueOf(firstShortKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short lastKey() {
/* 348 */       return Short.valueOf(lastShortKey());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <V> Short2ReferenceSortedMap<V> singleton(Short key, V value) {
/* 367 */     return new Singleton<>(key.shortValue(), value);
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
/*     */   public static <V> Short2ReferenceSortedMap<V> singleton(Short key, V value, ShortComparator comparator) {
/* 387 */     return new Singleton<>(key.shortValue(), value, comparator);
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
/*     */   public static <V> Short2ReferenceSortedMap<V> singleton(short key, V value) {
/* 405 */     return new Singleton<>(key, value);
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
/*     */   public static <V> Short2ReferenceSortedMap<V> singleton(short key, V value, ShortComparator comparator) {
/* 426 */     return new Singleton<>(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap<V>
/*     */     extends Short2ReferenceMaps.SynchronizedMap<V>
/*     */     implements Short2ReferenceSortedMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Short2ReferenceSortedMap<V> sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Short2ReferenceSortedMap<V> m, Object sync) {
/* 436 */       super(m, sync);
/* 437 */       this.sortedMap = m;
/*     */     }
/*     */     protected SynchronizedSortedMap(Short2ReferenceSortedMap<V> m) {
/* 440 */       super(m);
/* 441 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public ShortComparator comparator() {
/* 445 */       synchronized (this.sync) {
/* 446 */         return this.sortedMap.comparator();
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Short2ReferenceMap.Entry<V>> short2ReferenceEntrySet() {
/* 451 */       if (this.entries == null)
/* 452 */         this.entries = (ObjectSet<Short2ReferenceMap.Entry<V>>)ObjectSortedSets.synchronize(this.sortedMap.short2ReferenceEntrySet(), this.sync); 
/* 453 */       return (ObjectSortedSet<Short2ReferenceMap.Entry<V>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Short, V>> entrySet() {
/* 464 */       return (ObjectSortedSet)short2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public ShortSortedSet keySet() {
/* 468 */       if (this.keys == null)
/* 469 */         this.keys = ShortSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 470 */       return (ShortSortedSet)this.keys;
/*     */     }
/*     */     
/*     */     public Short2ReferenceSortedMap<V> subMap(short from, short to) {
/* 474 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */     
/*     */     public Short2ReferenceSortedMap<V> headMap(short to) {
/* 478 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */     
/*     */     public Short2ReferenceSortedMap<V> tailMap(short from) {
/* 482 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
/*     */     }
/*     */     
/*     */     public short firstShortKey() {
/* 486 */       synchronized (this.sync) {
/* 487 */         return this.sortedMap.firstShortKey();
/*     */       } 
/*     */     }
/*     */     
/*     */     public short lastShortKey() {
/* 492 */       synchronized (this.sync) {
/* 493 */         return this.sortedMap.lastShortKey();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short firstKey() {
/* 504 */       synchronized (this.sync) {
/* 505 */         return this.sortedMap.firstKey();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short lastKey() {
/* 516 */       synchronized (this.sync) {
/* 517 */         return this.sortedMap.lastKey();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short2ReferenceSortedMap<V> subMap(Short from, Short to) {
/* 528 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short2ReferenceSortedMap<V> headMap(Short to) {
/* 538 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short2ReferenceSortedMap<V> tailMap(Short from) {
/* 548 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
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
/*     */   public static <V> Short2ReferenceSortedMap<V> synchronize(Short2ReferenceSortedMap<V> m) {
/* 561 */     return new SynchronizedSortedMap<>(m);
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
/*     */   public static <V> Short2ReferenceSortedMap<V> synchronize(Short2ReferenceSortedMap<V> m, Object sync) {
/* 576 */     return new SynchronizedSortedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap<V>
/*     */     extends Short2ReferenceMaps.UnmodifiableMap<V>
/*     */     implements Short2ReferenceSortedMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Short2ReferenceSortedMap<V> sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Short2ReferenceSortedMap<V> m) {
/* 586 */       super(m);
/* 587 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public ShortComparator comparator() {
/* 591 */       return this.sortedMap.comparator();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Short2ReferenceMap.Entry<V>> short2ReferenceEntrySet() {
/* 595 */       if (this.entries == null)
/* 596 */         this.entries = (ObjectSet<Short2ReferenceMap.Entry<V>>)ObjectSortedSets.unmodifiable(this.sortedMap.short2ReferenceEntrySet()); 
/* 597 */       return (ObjectSortedSet<Short2ReferenceMap.Entry<V>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Short, V>> entrySet() {
/* 608 */       return (ObjectSortedSet)short2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public ShortSortedSet keySet() {
/* 612 */       if (this.keys == null)
/* 613 */         this.keys = ShortSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 614 */       return (ShortSortedSet)this.keys;
/*     */     }
/*     */     
/*     */     public Short2ReferenceSortedMap<V> subMap(short from, short to) {
/* 618 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */     
/*     */     public Short2ReferenceSortedMap<V> headMap(short to) {
/* 622 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */     
/*     */     public Short2ReferenceSortedMap<V> tailMap(short from) {
/* 626 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
/*     */     }
/*     */     
/*     */     public short firstShortKey() {
/* 630 */       return this.sortedMap.firstShortKey();
/*     */     }
/*     */     
/*     */     public short lastShortKey() {
/* 634 */       return this.sortedMap.lastShortKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short firstKey() {
/* 644 */       return this.sortedMap.firstKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short lastKey() {
/* 654 */       return this.sortedMap.lastKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short2ReferenceSortedMap<V> subMap(Short from, Short to) {
/* 664 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short2ReferenceSortedMap<V> headMap(Short to) {
/* 674 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short2ReferenceSortedMap<V> tailMap(Short from) {
/* 684 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
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
/*     */   public static <V> Short2ReferenceSortedMap<V> unmodifiable(Short2ReferenceSortedMap<V> m) {
/* 697 */     return new UnmodifiableSortedMap<>(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\Short2ReferenceSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */