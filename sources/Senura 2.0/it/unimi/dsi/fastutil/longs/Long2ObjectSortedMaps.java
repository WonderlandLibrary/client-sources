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
/*     */ public final class Long2ObjectSortedMaps
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
/*     */   public static <V> ObjectBidirectionalIterator<Long2ObjectMap.Entry<V>> fastIterator(Long2ObjectSortedMap<V> map) {
/*  60 */     ObjectSortedSet<Long2ObjectMap.Entry<V>> entries = map.long2ObjectEntrySet();
/*  61 */     return (entries instanceof Long2ObjectSortedMap.FastSortedEntrySet) ? (
/*  62 */       (Long2ObjectSortedMap.FastSortedEntrySet)entries).fastIterator() : 
/*  63 */       entries.iterator();
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
/*     */   public static <V> ObjectBidirectionalIterable<Long2ObjectMap.Entry<V>> fastIterable(Long2ObjectSortedMap<V> map) {
/*  79 */     ObjectSortedSet<Long2ObjectMap.Entry<V>> entries = map.long2ObjectEntrySet();
/*     */     
/*  81 */     Objects.requireNonNull((Long2ObjectSortedMap.FastSortedEntrySet)entries); return (entries instanceof Long2ObjectSortedMap.FastSortedEntrySet) ? (Long2ObjectSortedMap.FastSortedEntrySet)entries::fastIterator : 
/*  82 */       (ObjectBidirectionalIterable<Long2ObjectMap.Entry<V>>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap<V>
/*     */     extends Long2ObjectMaps.EmptyMap<V>
/*     */     implements Long2ObjectSortedMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public LongComparator comparator() {
/* 101 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Long2ObjectMap.Entry<V>> long2ObjectEntrySet() {
/* 106 */       return (ObjectSortedSet<Long2ObjectMap.Entry<V>>)ObjectSortedSets.EMPTY_SET;
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
/* 117 */       return (ObjectSortedSet<Map.Entry<Long, V>>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSortedSet keySet() {
/* 122 */       return LongSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public Long2ObjectSortedMap<V> subMap(long from, long to) {
/* 127 */       return Long2ObjectSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Long2ObjectSortedMap<V> headMap(long to) {
/* 132 */       return Long2ObjectSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Long2ObjectSortedMap<V> tailMap(long from) {
/* 137 */       return Long2ObjectSortedMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public long firstLongKey() {
/* 141 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public long lastLongKey() {
/* 145 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2ObjectSortedMap<V> headMap(Long oto) {
/* 155 */       return headMap(oto.longValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2ObjectSortedMap<V> tailMap(Long ofrom) {
/* 165 */       return tailMap(ofrom.longValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2ObjectSortedMap<V> subMap(Long ofrom, Long oto) {
/* 175 */       return subMap(ofrom.longValue(), oto.longValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long firstKey() {
/* 185 */       return Long.valueOf(firstLongKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long lastKey() {
/* 195 */       return Long.valueOf(lastLongKey());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 202 */   public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <V> Long2ObjectSortedMap<V> emptyMap() {
/* 213 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<V>
/*     */     extends Long2ObjectMaps.Singleton<V>
/*     */     implements Long2ObjectSortedMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final LongComparator comparator;
/*     */ 
/*     */ 
/*     */     
/*     */     protected Singleton(long key, V value, LongComparator comparator) {
/* 230 */       super(key, value);
/* 231 */       this.comparator = comparator;
/*     */     }
/*     */     protected Singleton(long key, V value) {
/* 234 */       this(key, value, (LongComparator)null);
/*     */     }
/*     */     
/*     */     final int compare(long k1, long k2) {
/* 238 */       return (this.comparator == null) ? Long.compare(k1, k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */     
/*     */     public LongComparator comparator() {
/* 242 */       return this.comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Long2ObjectMap.Entry<V>> long2ObjectEntrySet() {
/* 247 */       if (this.entries == null)
/* 248 */         this.entries = (ObjectSet<Long2ObjectMap.Entry<V>>)ObjectSortedSets.singleton(new AbstractLong2ObjectMap.BasicEntry<>(this.key, this.value), 
/* 249 */             Long2ObjectSortedMaps.entryComparator(this.comparator)); 
/* 250 */       return (ObjectSortedSet<Long2ObjectMap.Entry<V>>)this.entries;
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
/* 261 */       return (ObjectSortedSet)long2ObjectEntrySet();
/*     */     }
/*     */     
/*     */     public LongSortedSet keySet() {
/* 265 */       if (this.keys == null)
/* 266 */         this.keys = LongSortedSets.singleton(this.key, this.comparator); 
/* 267 */       return (LongSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Long2ObjectSortedMap<V> subMap(long from, long to) {
/* 272 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0)
/* 273 */         return this; 
/* 274 */       return Long2ObjectSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Long2ObjectSortedMap<V> headMap(long to) {
/* 279 */       if (compare(this.key, to) < 0)
/* 280 */         return this; 
/* 281 */       return Long2ObjectSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Long2ObjectSortedMap<V> tailMap(long from) {
/* 286 */       if (compare(from, this.key) <= 0)
/* 287 */         return this; 
/* 288 */       return Long2ObjectSortedMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public long firstLongKey() {
/* 292 */       return this.key;
/*     */     }
/*     */     
/*     */     public long lastLongKey() {
/* 296 */       return this.key;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2ObjectSortedMap<V> headMap(Long oto) {
/* 306 */       return headMap(oto.longValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2ObjectSortedMap<V> tailMap(Long ofrom) {
/* 316 */       return tailMap(ofrom.longValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2ObjectSortedMap<V> subMap(Long ofrom, Long oto) {
/* 326 */       return subMap(ofrom.longValue(), oto.longValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long firstKey() {
/* 336 */       return Long.valueOf(firstLongKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long lastKey() {
/* 346 */       return Long.valueOf(lastLongKey());
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
/*     */   public static <V> Long2ObjectSortedMap<V> singleton(Long key, V value) {
/* 365 */     return new Singleton<>(key.longValue(), value);
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
/*     */   public static <V> Long2ObjectSortedMap<V> singleton(Long key, V value, LongComparator comparator) {
/* 385 */     return new Singleton<>(key.longValue(), value, comparator);
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
/*     */   public static <V> Long2ObjectSortedMap<V> singleton(long key, V value) {
/* 403 */     return new Singleton<>(key, value);
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
/*     */   public static <V> Long2ObjectSortedMap<V> singleton(long key, V value, LongComparator comparator) {
/* 423 */     return new Singleton<>(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap<V>
/*     */     extends Long2ObjectMaps.SynchronizedMap<V>
/*     */     implements Long2ObjectSortedMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Long2ObjectSortedMap<V> sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Long2ObjectSortedMap<V> m, Object sync) {
/* 433 */       super(m, sync);
/* 434 */       this.sortedMap = m;
/*     */     }
/*     */     protected SynchronizedSortedMap(Long2ObjectSortedMap<V> m) {
/* 437 */       super(m);
/* 438 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public LongComparator comparator() {
/* 442 */       synchronized (this.sync) {
/* 443 */         return this.sortedMap.comparator();
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Long2ObjectMap.Entry<V>> long2ObjectEntrySet() {
/* 448 */       if (this.entries == null)
/* 449 */         this.entries = (ObjectSet<Long2ObjectMap.Entry<V>>)ObjectSortedSets.synchronize(this.sortedMap.long2ObjectEntrySet(), this.sync); 
/* 450 */       return (ObjectSortedSet<Long2ObjectMap.Entry<V>>)this.entries;
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
/* 461 */       return (ObjectSortedSet)long2ObjectEntrySet();
/*     */     }
/*     */     
/*     */     public LongSortedSet keySet() {
/* 465 */       if (this.keys == null)
/* 466 */         this.keys = LongSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 467 */       return (LongSortedSet)this.keys;
/*     */     }
/*     */     
/*     */     public Long2ObjectSortedMap<V> subMap(long from, long to) {
/* 471 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */     
/*     */     public Long2ObjectSortedMap<V> headMap(long to) {
/* 475 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */     
/*     */     public Long2ObjectSortedMap<V> tailMap(long from) {
/* 479 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
/*     */     }
/*     */     
/*     */     public long firstLongKey() {
/* 483 */       synchronized (this.sync) {
/* 484 */         return this.sortedMap.firstLongKey();
/*     */       } 
/*     */     }
/*     */     
/*     */     public long lastLongKey() {
/* 489 */       synchronized (this.sync) {
/* 490 */         return this.sortedMap.lastLongKey();
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
/* 501 */       synchronized (this.sync) {
/* 502 */         return this.sortedMap.firstKey();
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
/* 513 */       synchronized (this.sync) {
/* 514 */         return this.sortedMap.lastKey();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2ObjectSortedMap<V> subMap(Long from, Long to) {
/* 525 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2ObjectSortedMap<V> headMap(Long to) {
/* 535 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2ObjectSortedMap<V> tailMap(Long from) {
/* 545 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
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
/*     */   public static <V> Long2ObjectSortedMap<V> synchronize(Long2ObjectSortedMap<V> m) {
/* 558 */     return new SynchronizedSortedMap<>(m);
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
/*     */   public static <V> Long2ObjectSortedMap<V> synchronize(Long2ObjectSortedMap<V> m, Object sync) {
/* 573 */     return new SynchronizedSortedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap<V>
/*     */     extends Long2ObjectMaps.UnmodifiableMap<V>
/*     */     implements Long2ObjectSortedMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Long2ObjectSortedMap<V> sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Long2ObjectSortedMap<V> m) {
/* 583 */       super(m);
/* 584 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public LongComparator comparator() {
/* 588 */       return this.sortedMap.comparator();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Long2ObjectMap.Entry<V>> long2ObjectEntrySet() {
/* 592 */       if (this.entries == null)
/* 593 */         this.entries = (ObjectSet<Long2ObjectMap.Entry<V>>)ObjectSortedSets.unmodifiable(this.sortedMap.long2ObjectEntrySet()); 
/* 594 */       return (ObjectSortedSet<Long2ObjectMap.Entry<V>>)this.entries;
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
/* 605 */       return (ObjectSortedSet)long2ObjectEntrySet();
/*     */     }
/*     */     
/*     */     public LongSortedSet keySet() {
/* 609 */       if (this.keys == null)
/* 610 */         this.keys = LongSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 611 */       return (LongSortedSet)this.keys;
/*     */     }
/*     */     
/*     */     public Long2ObjectSortedMap<V> subMap(long from, long to) {
/* 615 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */     
/*     */     public Long2ObjectSortedMap<V> headMap(long to) {
/* 619 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */     
/*     */     public Long2ObjectSortedMap<V> tailMap(long from) {
/* 623 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
/*     */     }
/*     */     
/*     */     public long firstLongKey() {
/* 627 */       return this.sortedMap.firstLongKey();
/*     */     }
/*     */     
/*     */     public long lastLongKey() {
/* 631 */       return this.sortedMap.lastLongKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long firstKey() {
/* 641 */       return this.sortedMap.firstKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long lastKey() {
/* 651 */       return this.sortedMap.lastKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2ObjectSortedMap<V> subMap(Long from, Long to) {
/* 661 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2ObjectSortedMap<V> headMap(Long to) {
/* 671 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2ObjectSortedMap<V> tailMap(Long from) {
/* 681 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
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
/*     */   public static <V> Long2ObjectSortedMap<V> unmodifiable(Long2ObjectSortedMap<V> m) {
/* 694 */     return new UnmodifiableSortedMap<>(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\Long2ObjectSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */