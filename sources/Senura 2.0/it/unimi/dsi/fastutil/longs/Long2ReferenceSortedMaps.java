/*     */ package it.unimi.dsi.fastutil.longs;
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
/*     */ public final class Long2ReferenceSortedMaps
/*     */ {
/*     */   public static Comparator<? super Map.Entry<Long, ?>> entryComparator(LongComparator comparator) {
/*  43 */     return (x, y) -> comparator.compare(((Long)x.getKey()).longValue(), ((Long)y.getKey()).longValue());
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
/*     */   public static <V> ObjectBidirectionalIterator<Long2ReferenceMap.Entry<V>> fastIterator(Long2ReferenceSortedMap<V> map) {
/*  61 */     ObjectSortedSet<Long2ReferenceMap.Entry<V>> entries = map.long2ReferenceEntrySet();
/*  62 */     return (entries instanceof Long2ReferenceSortedMap.FastSortedEntrySet) ? (
/*  63 */       (Long2ReferenceSortedMap.FastSortedEntrySet)entries).fastIterator() : 
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
/*     */   public static <V> ObjectBidirectionalIterable<Long2ReferenceMap.Entry<V>> fastIterable(Long2ReferenceSortedMap<V> map) {
/*  81 */     ObjectSortedSet<Long2ReferenceMap.Entry<V>> entries = map.long2ReferenceEntrySet();
/*     */     
/*  83 */     Objects.requireNonNull((Long2ReferenceSortedMap.FastSortedEntrySet)entries); return (entries instanceof Long2ReferenceSortedMap.FastSortedEntrySet) ? (Long2ReferenceSortedMap.FastSortedEntrySet)entries::fastIterator : 
/*  84 */       (ObjectBidirectionalIterable<Long2ReferenceMap.Entry<V>>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap<V>
/*     */     extends Long2ReferenceMaps.EmptyMap<V>
/*     */     implements Long2ReferenceSortedMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public LongComparator comparator() {
/* 103 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Long2ReferenceMap.Entry<V>> long2ReferenceEntrySet() {
/* 108 */       return (ObjectSortedSet<Long2ReferenceMap.Entry<V>>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Long, V>> entrySet() {
/* 119 */       return (ObjectSortedSet<Map.Entry<Long, V>>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSortedSet keySet() {
/* 124 */       return LongSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public Long2ReferenceSortedMap<V> subMap(long from, long to) {
/* 129 */       return Long2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Long2ReferenceSortedMap<V> headMap(long to) {
/* 134 */       return Long2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Long2ReferenceSortedMap<V> tailMap(long from) {
/* 139 */       return Long2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public long firstLongKey() {
/* 143 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public long lastLongKey() {
/* 147 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2ReferenceSortedMap<V> headMap(Long oto) {
/* 157 */       return headMap(oto.longValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2ReferenceSortedMap<V> tailMap(Long ofrom) {
/* 167 */       return tailMap(ofrom.longValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2ReferenceSortedMap<V> subMap(Long ofrom, Long oto) {
/* 177 */       return subMap(ofrom.longValue(), oto.longValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long firstKey() {
/* 187 */       return Long.valueOf(firstLongKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long lastKey() {
/* 197 */       return Long.valueOf(lastLongKey());
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
/*     */   public static <V> Long2ReferenceSortedMap<V> emptyMap() {
/* 215 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<V>
/*     */     extends Long2ReferenceMaps.Singleton<V>
/*     */     implements Long2ReferenceSortedMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final LongComparator comparator;
/*     */ 
/*     */ 
/*     */     
/*     */     protected Singleton(long key, V value, LongComparator comparator) {
/* 232 */       super(key, value);
/* 233 */       this.comparator = comparator;
/*     */     }
/*     */     protected Singleton(long key, V value) {
/* 236 */       this(key, value, (LongComparator)null);
/*     */     }
/*     */     
/*     */     final int compare(long k1, long k2) {
/* 240 */       return (this.comparator == null) ? Long.compare(k1, k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */     
/*     */     public LongComparator comparator() {
/* 244 */       return this.comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Long2ReferenceMap.Entry<V>> long2ReferenceEntrySet() {
/* 249 */       if (this.entries == null)
/* 250 */         this.entries = (ObjectSet<Long2ReferenceMap.Entry<V>>)ObjectSortedSets.singleton(new AbstractLong2ReferenceMap.BasicEntry<>(this.key, this.value), 
/* 251 */             Long2ReferenceSortedMaps.entryComparator(this.comparator)); 
/* 252 */       return (ObjectSortedSet<Long2ReferenceMap.Entry<V>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Long, V>> entrySet() {
/* 263 */       return (ObjectSortedSet)long2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public LongSortedSet keySet() {
/* 267 */       if (this.keys == null)
/* 268 */         this.keys = LongSortedSets.singleton(this.key, this.comparator); 
/* 269 */       return (LongSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Long2ReferenceSortedMap<V> subMap(long from, long to) {
/* 274 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0)
/* 275 */         return this; 
/* 276 */       return Long2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Long2ReferenceSortedMap<V> headMap(long to) {
/* 281 */       if (compare(this.key, to) < 0)
/* 282 */         return this; 
/* 283 */       return Long2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Long2ReferenceSortedMap<V> tailMap(long from) {
/* 288 */       if (compare(from, this.key) <= 0)
/* 289 */         return this; 
/* 290 */       return Long2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public long firstLongKey() {
/* 294 */       return this.key;
/*     */     }
/*     */     
/*     */     public long lastLongKey() {
/* 298 */       return this.key;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2ReferenceSortedMap<V> headMap(Long oto) {
/* 308 */       return headMap(oto.longValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2ReferenceSortedMap<V> tailMap(Long ofrom) {
/* 318 */       return tailMap(ofrom.longValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2ReferenceSortedMap<V> subMap(Long ofrom, Long oto) {
/* 328 */       return subMap(ofrom.longValue(), oto.longValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long firstKey() {
/* 338 */       return Long.valueOf(firstLongKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long lastKey() {
/* 348 */       return Long.valueOf(lastLongKey());
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
/*     */   public static <V> Long2ReferenceSortedMap<V> singleton(Long key, V value) {
/* 367 */     return new Singleton<>(key.longValue(), value);
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
/*     */   public static <V> Long2ReferenceSortedMap<V> singleton(Long key, V value, LongComparator comparator) {
/* 387 */     return new Singleton<>(key.longValue(), value, comparator);
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
/*     */   public static <V> Long2ReferenceSortedMap<V> singleton(long key, V value) {
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
/*     */   public static <V> Long2ReferenceSortedMap<V> singleton(long key, V value, LongComparator comparator) {
/* 425 */     return new Singleton<>(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap<V>
/*     */     extends Long2ReferenceMaps.SynchronizedMap<V>
/*     */     implements Long2ReferenceSortedMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Long2ReferenceSortedMap<V> sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Long2ReferenceSortedMap<V> m, Object sync) {
/* 435 */       super(m, sync);
/* 436 */       this.sortedMap = m;
/*     */     }
/*     */     protected SynchronizedSortedMap(Long2ReferenceSortedMap<V> m) {
/* 439 */       super(m);
/* 440 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public LongComparator comparator() {
/* 444 */       synchronized (this.sync) {
/* 445 */         return this.sortedMap.comparator();
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Long2ReferenceMap.Entry<V>> long2ReferenceEntrySet() {
/* 450 */       if (this.entries == null)
/* 451 */         this.entries = (ObjectSet<Long2ReferenceMap.Entry<V>>)ObjectSortedSets.synchronize(this.sortedMap.long2ReferenceEntrySet(), this.sync); 
/* 452 */       return (ObjectSortedSet<Long2ReferenceMap.Entry<V>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Long, V>> entrySet() {
/* 463 */       return (ObjectSortedSet)long2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public LongSortedSet keySet() {
/* 467 */       if (this.keys == null)
/* 468 */         this.keys = LongSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 469 */       return (LongSortedSet)this.keys;
/*     */     }
/*     */     
/*     */     public Long2ReferenceSortedMap<V> subMap(long from, long to) {
/* 473 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */     
/*     */     public Long2ReferenceSortedMap<V> headMap(long to) {
/* 477 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */     
/*     */     public Long2ReferenceSortedMap<V> tailMap(long from) {
/* 481 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
/*     */     }
/*     */     
/*     */     public long firstLongKey() {
/* 485 */       synchronized (this.sync) {
/* 486 */         return this.sortedMap.firstLongKey();
/*     */       } 
/*     */     }
/*     */     
/*     */     public long lastLongKey() {
/* 491 */       synchronized (this.sync) {
/* 492 */         return this.sortedMap.lastLongKey();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long firstKey() {
/* 503 */       synchronized (this.sync) {
/* 504 */         return this.sortedMap.firstKey();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long lastKey() {
/* 515 */       synchronized (this.sync) {
/* 516 */         return this.sortedMap.lastKey();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2ReferenceSortedMap<V> subMap(Long from, Long to) {
/* 527 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2ReferenceSortedMap<V> headMap(Long to) {
/* 537 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2ReferenceSortedMap<V> tailMap(Long from) {
/* 547 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
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
/*     */   public static <V> Long2ReferenceSortedMap<V> synchronize(Long2ReferenceSortedMap<V> m) {
/* 560 */     return new SynchronizedSortedMap<>(m);
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
/*     */   public static <V> Long2ReferenceSortedMap<V> synchronize(Long2ReferenceSortedMap<V> m, Object sync) {
/* 575 */     return new SynchronizedSortedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap<V>
/*     */     extends Long2ReferenceMaps.UnmodifiableMap<V>
/*     */     implements Long2ReferenceSortedMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Long2ReferenceSortedMap<V> sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Long2ReferenceSortedMap<V> m) {
/* 585 */       super(m);
/* 586 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public LongComparator comparator() {
/* 590 */       return this.sortedMap.comparator();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Long2ReferenceMap.Entry<V>> long2ReferenceEntrySet() {
/* 594 */       if (this.entries == null)
/* 595 */         this.entries = (ObjectSet<Long2ReferenceMap.Entry<V>>)ObjectSortedSets.unmodifiable(this.sortedMap.long2ReferenceEntrySet()); 
/* 596 */       return (ObjectSortedSet<Long2ReferenceMap.Entry<V>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Long, V>> entrySet() {
/* 607 */       return (ObjectSortedSet)long2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public LongSortedSet keySet() {
/* 611 */       if (this.keys == null)
/* 612 */         this.keys = LongSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 613 */       return (LongSortedSet)this.keys;
/*     */     }
/*     */     
/*     */     public Long2ReferenceSortedMap<V> subMap(long from, long to) {
/* 617 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */     
/*     */     public Long2ReferenceSortedMap<V> headMap(long to) {
/* 621 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */     
/*     */     public Long2ReferenceSortedMap<V> tailMap(long from) {
/* 625 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
/*     */     }
/*     */     
/*     */     public long firstLongKey() {
/* 629 */       return this.sortedMap.firstLongKey();
/*     */     }
/*     */     
/*     */     public long lastLongKey() {
/* 633 */       return this.sortedMap.lastLongKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long firstKey() {
/* 643 */       return this.sortedMap.firstKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long lastKey() {
/* 653 */       return this.sortedMap.lastKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2ReferenceSortedMap<V> subMap(Long from, Long to) {
/* 663 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2ReferenceSortedMap<V> headMap(Long to) {
/* 673 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2ReferenceSortedMap<V> tailMap(Long from) {
/* 683 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
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
/*     */   public static <V> Long2ReferenceSortedMap<V> unmodifiable(Long2ReferenceSortedMap<V> m) {
/* 696 */     return new UnmodifiableSortedMap<>(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\Long2ReferenceSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */