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
/*     */ public final class Long2FloatSortedMaps
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
/*     */   public static ObjectBidirectionalIterator<Long2FloatMap.Entry> fastIterator(Long2FloatSortedMap map) {
/*  60 */     ObjectSortedSet<Long2FloatMap.Entry> entries = map.long2FloatEntrySet();
/*  61 */     return (entries instanceof Long2FloatSortedMap.FastSortedEntrySet) ? (
/*  62 */       (Long2FloatSortedMap.FastSortedEntrySet)entries).fastIterator() : 
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
/*     */   public static ObjectBidirectionalIterable<Long2FloatMap.Entry> fastIterable(Long2FloatSortedMap map) {
/*  79 */     ObjectSortedSet<Long2FloatMap.Entry> entries = map.long2FloatEntrySet();
/*     */     
/*  81 */     Objects.requireNonNull((Long2FloatSortedMap.FastSortedEntrySet)entries); return (entries instanceof Long2FloatSortedMap.FastSortedEntrySet) ? (Long2FloatSortedMap.FastSortedEntrySet)entries::fastIterator : 
/*  82 */       (ObjectBidirectionalIterable<Long2FloatMap.Entry>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap
/*     */     extends Long2FloatMaps.EmptyMap
/*     */     implements Long2FloatSortedMap, Serializable, Cloneable
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
/*     */     public ObjectSortedSet<Long2FloatMap.Entry> long2FloatEntrySet() {
/* 106 */       return (ObjectSortedSet<Long2FloatMap.Entry>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Long, Float>> entrySet() {
/* 117 */       return (ObjectSortedSet<Map.Entry<Long, Float>>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSortedSet keySet() {
/* 122 */       return LongSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public Long2FloatSortedMap subMap(long from, long to) {
/* 127 */       return Long2FloatSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Long2FloatSortedMap headMap(long to) {
/* 132 */       return Long2FloatSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Long2FloatSortedMap tailMap(long from) {
/* 137 */       return Long2FloatSortedMaps.EMPTY_MAP;
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
/*     */     public Long2FloatSortedMap headMap(Long oto) {
/* 155 */       return headMap(oto.longValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2FloatSortedMap tailMap(Long ofrom) {
/* 165 */       return tailMap(ofrom.longValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2FloatSortedMap subMap(Long ofrom, Long oto) {
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
/*     */     extends Long2FloatMaps.Singleton
/*     */     implements Long2FloatSortedMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final LongComparator comparator;
/*     */ 
/*     */ 
/*     */     
/*     */     protected Singleton(long key, float value, LongComparator comparator) {
/* 218 */       super(key, value);
/* 219 */       this.comparator = comparator;
/*     */     }
/*     */     protected Singleton(long key, float value) {
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
/*     */     public ObjectSortedSet<Long2FloatMap.Entry> long2FloatEntrySet() {
/* 235 */       if (this.entries == null)
/* 236 */         this.entries = (ObjectSet<Long2FloatMap.Entry>)ObjectSortedSets.singleton(new AbstractLong2FloatMap.BasicEntry(this.key, this.value), 
/* 237 */             Long2FloatSortedMaps.entryComparator(this.comparator)); 
/* 238 */       return (ObjectSortedSet<Long2FloatMap.Entry>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Long, Float>> entrySet() {
/* 249 */       return (ObjectSortedSet)long2FloatEntrySet();
/*     */     }
/*     */     
/*     */     public LongSortedSet keySet() {
/* 253 */       if (this.keys == null)
/* 254 */         this.keys = LongSortedSets.singleton(this.key, this.comparator); 
/* 255 */       return (LongSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Long2FloatSortedMap subMap(long from, long to) {
/* 260 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0)
/* 261 */         return this; 
/* 262 */       return Long2FloatSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Long2FloatSortedMap headMap(long to) {
/* 267 */       if (compare(this.key, to) < 0)
/* 268 */         return this; 
/* 269 */       return Long2FloatSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Long2FloatSortedMap tailMap(long from) {
/* 274 */       if (compare(from, this.key) <= 0)
/* 275 */         return this; 
/* 276 */       return Long2FloatSortedMaps.EMPTY_MAP;
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
/*     */     public Long2FloatSortedMap headMap(Long oto) {
/* 294 */       return headMap(oto.longValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2FloatSortedMap tailMap(Long ofrom) {
/* 304 */       return tailMap(ofrom.longValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2FloatSortedMap subMap(Long ofrom, Long oto) {
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
/*     */   public static Long2FloatSortedMap singleton(Long key, Float value) {
/* 353 */     return new Singleton(key.longValue(), value.floatValue());
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
/*     */   public static Long2FloatSortedMap singleton(Long key, Float value, LongComparator comparator) {
/* 373 */     return new Singleton(key.longValue(), value.floatValue(), comparator);
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
/*     */   public static Long2FloatSortedMap singleton(long key, float value) {
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
/*     */   public static Long2FloatSortedMap singleton(long key, float value, LongComparator comparator) {
/* 411 */     return new Singleton(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap
/*     */     extends Long2FloatMaps.SynchronizedMap
/*     */     implements Long2FloatSortedMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Long2FloatSortedMap sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Long2FloatSortedMap m, Object sync) {
/* 421 */       super(m, sync);
/* 422 */       this.sortedMap = m;
/*     */     }
/*     */     protected SynchronizedSortedMap(Long2FloatSortedMap m) {
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
/*     */     public ObjectSortedSet<Long2FloatMap.Entry> long2FloatEntrySet() {
/* 436 */       if (this.entries == null)
/* 437 */         this.entries = (ObjectSet<Long2FloatMap.Entry>)ObjectSortedSets.synchronize(this.sortedMap.long2FloatEntrySet(), this.sync); 
/* 438 */       return (ObjectSortedSet<Long2FloatMap.Entry>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Long, Float>> entrySet() {
/* 449 */       return (ObjectSortedSet)long2FloatEntrySet();
/*     */     }
/*     */     
/*     */     public LongSortedSet keySet() {
/* 453 */       if (this.keys == null)
/* 454 */         this.keys = LongSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 455 */       return (LongSortedSet)this.keys;
/*     */     }
/*     */     
/*     */     public Long2FloatSortedMap subMap(long from, long to) {
/* 459 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */     
/*     */     public Long2FloatSortedMap headMap(long to) {
/* 463 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */     
/*     */     public Long2FloatSortedMap tailMap(long from) {
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
/*     */     public Long2FloatSortedMap subMap(Long from, Long to) {
/* 513 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2FloatSortedMap headMap(Long to) {
/* 523 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2FloatSortedMap tailMap(Long from) {
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
/*     */   public static Long2FloatSortedMap synchronize(Long2FloatSortedMap m) {
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
/*     */   public static Long2FloatSortedMap synchronize(Long2FloatSortedMap m, Object sync) {
/* 561 */     return new SynchronizedSortedMap(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap
/*     */     extends Long2FloatMaps.UnmodifiableMap
/*     */     implements Long2FloatSortedMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Long2FloatSortedMap sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Long2FloatSortedMap m) {
/* 571 */       super(m);
/* 572 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public LongComparator comparator() {
/* 576 */       return this.sortedMap.comparator();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Long2FloatMap.Entry> long2FloatEntrySet() {
/* 580 */       if (this.entries == null)
/* 581 */         this.entries = (ObjectSet<Long2FloatMap.Entry>)ObjectSortedSets.unmodifiable(this.sortedMap.long2FloatEntrySet()); 
/* 582 */       return (ObjectSortedSet<Long2FloatMap.Entry>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Long, Float>> entrySet() {
/* 593 */       return (ObjectSortedSet)long2FloatEntrySet();
/*     */     }
/*     */     
/*     */     public LongSortedSet keySet() {
/* 597 */       if (this.keys == null)
/* 598 */         this.keys = LongSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 599 */       return (LongSortedSet)this.keys;
/*     */     }
/*     */     
/*     */     public Long2FloatSortedMap subMap(long from, long to) {
/* 603 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */     
/*     */     public Long2FloatSortedMap headMap(long to) {
/* 607 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */     
/*     */     public Long2FloatSortedMap tailMap(long from) {
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
/*     */     public Long2FloatSortedMap subMap(Long from, Long to) {
/* 649 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2FloatSortedMap headMap(Long to) {
/* 659 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2FloatSortedMap tailMap(Long from) {
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
/*     */   public static Long2FloatSortedMap unmodifiable(Long2FloatSortedMap m) {
/* 682 */     return new UnmodifiableSortedMap(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\Long2FloatSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */