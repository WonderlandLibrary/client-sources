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
/*     */ public final class Long2ShortSortedMaps
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
/*     */   public static ObjectBidirectionalIterator<Long2ShortMap.Entry> fastIterator(Long2ShortSortedMap map) {
/*  60 */     ObjectSortedSet<Long2ShortMap.Entry> entries = map.long2ShortEntrySet();
/*  61 */     return (entries instanceof Long2ShortSortedMap.FastSortedEntrySet) ? (
/*  62 */       (Long2ShortSortedMap.FastSortedEntrySet)entries).fastIterator() : 
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
/*     */   public static ObjectBidirectionalIterable<Long2ShortMap.Entry> fastIterable(Long2ShortSortedMap map) {
/*  79 */     ObjectSortedSet<Long2ShortMap.Entry> entries = map.long2ShortEntrySet();
/*     */     
/*  81 */     Objects.requireNonNull((Long2ShortSortedMap.FastSortedEntrySet)entries); return (entries instanceof Long2ShortSortedMap.FastSortedEntrySet) ? (Long2ShortSortedMap.FastSortedEntrySet)entries::fastIterator : 
/*  82 */       (ObjectBidirectionalIterable<Long2ShortMap.Entry>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap
/*     */     extends Long2ShortMaps.EmptyMap
/*     */     implements Long2ShortSortedMap, Serializable, Cloneable
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
/*     */     public ObjectSortedSet<Long2ShortMap.Entry> long2ShortEntrySet() {
/* 106 */       return (ObjectSortedSet<Long2ShortMap.Entry>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Long, Short>> entrySet() {
/* 117 */       return (ObjectSortedSet<Map.Entry<Long, Short>>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSortedSet keySet() {
/* 122 */       return LongSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public Long2ShortSortedMap subMap(long from, long to) {
/* 127 */       return Long2ShortSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Long2ShortSortedMap headMap(long to) {
/* 132 */       return Long2ShortSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Long2ShortSortedMap tailMap(long from) {
/* 137 */       return Long2ShortSortedMaps.EMPTY_MAP;
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
/*     */     public Long2ShortSortedMap headMap(Long oto) {
/* 155 */       return headMap(oto.longValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2ShortSortedMap tailMap(Long ofrom) {
/* 165 */       return tailMap(ofrom.longValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2ShortSortedMap subMap(Long ofrom, Long oto) {
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
/*     */   public static class Singleton
/*     */     extends Long2ShortMaps.Singleton
/*     */     implements Long2ShortSortedMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final LongComparator comparator;
/*     */ 
/*     */ 
/*     */     
/*     */     protected Singleton(long key, short value, LongComparator comparator) {
/* 218 */       super(key, value);
/* 219 */       this.comparator = comparator;
/*     */     }
/*     */     protected Singleton(long key, short value) {
/* 222 */       this(key, value, (LongComparator)null);
/*     */     }
/*     */     
/*     */     final int compare(long k1, long k2) {
/* 226 */       return (this.comparator == null) ? Long.compare(k1, k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */     
/*     */     public LongComparator comparator() {
/* 230 */       return this.comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Long2ShortMap.Entry> long2ShortEntrySet() {
/* 235 */       if (this.entries == null)
/* 236 */         this.entries = (ObjectSet<Long2ShortMap.Entry>)ObjectSortedSets.singleton(new AbstractLong2ShortMap.BasicEntry(this.key, this.value), 
/* 237 */             Long2ShortSortedMaps.entryComparator(this.comparator)); 
/* 238 */       return (ObjectSortedSet<Long2ShortMap.Entry>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Long, Short>> entrySet() {
/* 249 */       return (ObjectSortedSet)long2ShortEntrySet();
/*     */     }
/*     */     
/*     */     public LongSortedSet keySet() {
/* 253 */       if (this.keys == null)
/* 254 */         this.keys = LongSortedSets.singleton(this.key, this.comparator); 
/* 255 */       return (LongSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Long2ShortSortedMap subMap(long from, long to) {
/* 260 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0)
/* 261 */         return this; 
/* 262 */       return Long2ShortSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Long2ShortSortedMap headMap(long to) {
/* 267 */       if (compare(this.key, to) < 0)
/* 268 */         return this; 
/* 269 */       return Long2ShortSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Long2ShortSortedMap tailMap(long from) {
/* 274 */       if (compare(from, this.key) <= 0)
/* 275 */         return this; 
/* 276 */       return Long2ShortSortedMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public long firstLongKey() {
/* 280 */       return this.key;
/*     */     }
/*     */     
/*     */     public long lastLongKey() {
/* 284 */       return this.key;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2ShortSortedMap headMap(Long oto) {
/* 294 */       return headMap(oto.longValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2ShortSortedMap tailMap(Long ofrom) {
/* 304 */       return tailMap(ofrom.longValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2ShortSortedMap subMap(Long ofrom, Long oto) {
/* 314 */       return subMap(ofrom.longValue(), oto.longValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long firstKey() {
/* 324 */       return Long.valueOf(firstLongKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long lastKey() {
/* 334 */       return Long.valueOf(lastLongKey());
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
/*     */   public static Long2ShortSortedMap singleton(Long key, Short value) {
/* 353 */     return new Singleton(key.longValue(), value.shortValue());
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
/*     */   public static Long2ShortSortedMap singleton(Long key, Short value, LongComparator comparator) {
/* 373 */     return new Singleton(key.longValue(), value.shortValue(), comparator);
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
/*     */   public static Long2ShortSortedMap singleton(long key, short value) {
/* 391 */     return new Singleton(key, value);
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
/*     */   public static Long2ShortSortedMap singleton(long key, short value, LongComparator comparator) {
/* 411 */     return new Singleton(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap
/*     */     extends Long2ShortMaps.SynchronizedMap
/*     */     implements Long2ShortSortedMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Long2ShortSortedMap sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Long2ShortSortedMap m, Object sync) {
/* 421 */       super(m, sync);
/* 422 */       this.sortedMap = m;
/*     */     }
/*     */     protected SynchronizedSortedMap(Long2ShortSortedMap m) {
/* 425 */       super(m);
/* 426 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public LongComparator comparator() {
/* 430 */       synchronized (this.sync) {
/* 431 */         return this.sortedMap.comparator();
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Long2ShortMap.Entry> long2ShortEntrySet() {
/* 436 */       if (this.entries == null)
/* 437 */         this.entries = (ObjectSet<Long2ShortMap.Entry>)ObjectSortedSets.synchronize(this.sortedMap.long2ShortEntrySet(), this.sync); 
/* 438 */       return (ObjectSortedSet<Long2ShortMap.Entry>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Long, Short>> entrySet() {
/* 449 */       return (ObjectSortedSet)long2ShortEntrySet();
/*     */     }
/*     */     
/*     */     public LongSortedSet keySet() {
/* 453 */       if (this.keys == null)
/* 454 */         this.keys = LongSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 455 */       return (LongSortedSet)this.keys;
/*     */     }
/*     */     
/*     */     public Long2ShortSortedMap subMap(long from, long to) {
/* 459 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */     
/*     */     public Long2ShortSortedMap headMap(long to) {
/* 463 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */     
/*     */     public Long2ShortSortedMap tailMap(long from) {
/* 467 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
/*     */     }
/*     */     
/*     */     public long firstLongKey() {
/* 471 */       synchronized (this.sync) {
/* 472 */         return this.sortedMap.firstLongKey();
/*     */       } 
/*     */     }
/*     */     
/*     */     public long lastLongKey() {
/* 477 */       synchronized (this.sync) {
/* 478 */         return this.sortedMap.lastLongKey();
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
/* 489 */       synchronized (this.sync) {
/* 490 */         return this.sortedMap.firstKey();
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
/* 501 */       synchronized (this.sync) {
/* 502 */         return this.sortedMap.lastKey();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2ShortSortedMap subMap(Long from, Long to) {
/* 513 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2ShortSortedMap headMap(Long to) {
/* 523 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2ShortSortedMap tailMap(Long from) {
/* 533 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
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
/*     */   public static Long2ShortSortedMap synchronize(Long2ShortSortedMap m) {
/* 546 */     return new SynchronizedSortedMap(m);
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
/*     */   public static Long2ShortSortedMap synchronize(Long2ShortSortedMap m, Object sync) {
/* 561 */     return new SynchronizedSortedMap(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap
/*     */     extends Long2ShortMaps.UnmodifiableMap
/*     */     implements Long2ShortSortedMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Long2ShortSortedMap sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Long2ShortSortedMap m) {
/* 571 */       super(m);
/* 572 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public LongComparator comparator() {
/* 576 */       return this.sortedMap.comparator();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Long2ShortMap.Entry> long2ShortEntrySet() {
/* 580 */       if (this.entries == null)
/* 581 */         this.entries = (ObjectSet<Long2ShortMap.Entry>)ObjectSortedSets.unmodifiable(this.sortedMap.long2ShortEntrySet()); 
/* 582 */       return (ObjectSortedSet<Long2ShortMap.Entry>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Long, Short>> entrySet() {
/* 593 */       return (ObjectSortedSet)long2ShortEntrySet();
/*     */     }
/*     */     
/*     */     public LongSortedSet keySet() {
/* 597 */       if (this.keys == null)
/* 598 */         this.keys = LongSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 599 */       return (LongSortedSet)this.keys;
/*     */     }
/*     */     
/*     */     public Long2ShortSortedMap subMap(long from, long to) {
/* 603 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */     
/*     */     public Long2ShortSortedMap headMap(long to) {
/* 607 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */     
/*     */     public Long2ShortSortedMap tailMap(long from) {
/* 611 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
/*     */     }
/*     */     
/*     */     public long firstLongKey() {
/* 615 */       return this.sortedMap.firstLongKey();
/*     */     }
/*     */     
/*     */     public long lastLongKey() {
/* 619 */       return this.sortedMap.lastLongKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long firstKey() {
/* 629 */       return this.sortedMap.firstKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long lastKey() {
/* 639 */       return this.sortedMap.lastKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2ShortSortedMap subMap(Long from, Long to) {
/* 649 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2ShortSortedMap headMap(Long to) {
/* 659 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2ShortSortedMap tailMap(Long from) {
/* 669 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
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
/*     */   public static Long2ShortSortedMap unmodifiable(Long2ShortSortedMap m) {
/* 682 */     return new UnmodifiableSortedMap(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\Long2ShortSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */