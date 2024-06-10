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
/*     */ public final class Short2ShortSortedMaps
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
/*     */   public static ObjectBidirectionalIterator<Short2ShortMap.Entry> fastIterator(Short2ShortSortedMap map) {
/*  60 */     ObjectSortedSet<Short2ShortMap.Entry> entries = map.short2ShortEntrySet();
/*  61 */     return (entries instanceof Short2ShortSortedMap.FastSortedEntrySet) ? (
/*  62 */       (Short2ShortSortedMap.FastSortedEntrySet)entries).fastIterator() : 
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
/*     */   public static ObjectBidirectionalIterable<Short2ShortMap.Entry> fastIterable(Short2ShortSortedMap map) {
/*  79 */     ObjectSortedSet<Short2ShortMap.Entry> entries = map.short2ShortEntrySet();
/*     */     
/*  81 */     Objects.requireNonNull((Short2ShortSortedMap.FastSortedEntrySet)entries); return (entries instanceof Short2ShortSortedMap.FastSortedEntrySet) ? (Short2ShortSortedMap.FastSortedEntrySet)entries::fastIterator : 
/*  82 */       (ObjectBidirectionalIterable<Short2ShortMap.Entry>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap
/*     */     extends Short2ShortMaps.EmptyMap
/*     */     implements Short2ShortSortedMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ShortComparator comparator() {
/* 101 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Short2ShortMap.Entry> short2ShortEntrySet() {
/* 106 */       return (ObjectSortedSet<Short2ShortMap.Entry>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Short, Short>> entrySet() {
/* 117 */       return (ObjectSortedSet<Map.Entry<Short, Short>>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSortedSet keySet() {
/* 122 */       return ShortSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public Short2ShortSortedMap subMap(short from, short to) {
/* 127 */       return Short2ShortSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Short2ShortSortedMap headMap(short to) {
/* 132 */       return Short2ShortSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Short2ShortSortedMap tailMap(short from) {
/* 137 */       return Short2ShortSortedMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public short firstShortKey() {
/* 141 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public short lastShortKey() {
/* 145 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short2ShortSortedMap headMap(Short oto) {
/* 155 */       return headMap(oto.shortValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short2ShortSortedMap tailMap(Short ofrom) {
/* 165 */       return tailMap(ofrom.shortValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short2ShortSortedMap subMap(Short ofrom, Short oto) {
/* 175 */       return subMap(ofrom.shortValue(), oto.shortValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short firstKey() {
/* 185 */       return Short.valueOf(firstShortKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short lastKey() {
/* 195 */       return Short.valueOf(lastShortKey());
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
/*     */     extends Short2ShortMaps.Singleton
/*     */     implements Short2ShortSortedMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final ShortComparator comparator;
/*     */ 
/*     */ 
/*     */     
/*     */     protected Singleton(short key, short value, ShortComparator comparator) {
/* 218 */       super(key, value);
/* 219 */       this.comparator = comparator;
/*     */     }
/*     */     protected Singleton(short key, short value) {
/* 222 */       this(key, value, (ShortComparator)null);
/*     */     }
/*     */     
/*     */     final int compare(short k1, short k2) {
/* 226 */       return (this.comparator == null) ? Short.compare(k1, k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */     
/*     */     public ShortComparator comparator() {
/* 230 */       return this.comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Short2ShortMap.Entry> short2ShortEntrySet() {
/* 235 */       if (this.entries == null)
/* 236 */         this.entries = (ObjectSet<Short2ShortMap.Entry>)ObjectSortedSets.singleton(new AbstractShort2ShortMap.BasicEntry(this.key, this.value), 
/* 237 */             Short2ShortSortedMaps.entryComparator(this.comparator)); 
/* 238 */       return (ObjectSortedSet<Short2ShortMap.Entry>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Short, Short>> entrySet() {
/* 249 */       return (ObjectSortedSet)short2ShortEntrySet();
/*     */     }
/*     */     
/*     */     public ShortSortedSet keySet() {
/* 253 */       if (this.keys == null)
/* 254 */         this.keys = ShortSortedSets.singleton(this.key, this.comparator); 
/* 255 */       return (ShortSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Short2ShortSortedMap subMap(short from, short to) {
/* 260 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0)
/* 261 */         return this; 
/* 262 */       return Short2ShortSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Short2ShortSortedMap headMap(short to) {
/* 267 */       if (compare(this.key, to) < 0)
/* 268 */         return this; 
/* 269 */       return Short2ShortSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Short2ShortSortedMap tailMap(short from) {
/* 274 */       if (compare(from, this.key) <= 0)
/* 275 */         return this; 
/* 276 */       return Short2ShortSortedMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public short firstShortKey() {
/* 280 */       return this.key;
/*     */     }
/*     */     
/*     */     public short lastShortKey() {
/* 284 */       return this.key;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short2ShortSortedMap headMap(Short oto) {
/* 294 */       return headMap(oto.shortValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short2ShortSortedMap tailMap(Short ofrom) {
/* 304 */       return tailMap(ofrom.shortValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short2ShortSortedMap subMap(Short ofrom, Short oto) {
/* 314 */       return subMap(ofrom.shortValue(), oto.shortValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short firstKey() {
/* 324 */       return Short.valueOf(firstShortKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short lastKey() {
/* 334 */       return Short.valueOf(lastShortKey());
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
/*     */   public static Short2ShortSortedMap singleton(Short key, Short value) {
/* 353 */     return new Singleton(key.shortValue(), value.shortValue());
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
/*     */   public static Short2ShortSortedMap singleton(Short key, Short value, ShortComparator comparator) {
/* 373 */     return new Singleton(key.shortValue(), value.shortValue(), comparator);
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
/*     */   public static Short2ShortSortedMap singleton(short key, short value) {
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
/*     */   public static Short2ShortSortedMap singleton(short key, short value, ShortComparator comparator) {
/* 411 */     return new Singleton(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap
/*     */     extends Short2ShortMaps.SynchronizedMap
/*     */     implements Short2ShortSortedMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Short2ShortSortedMap sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Short2ShortSortedMap m, Object sync) {
/* 421 */       super(m, sync);
/* 422 */       this.sortedMap = m;
/*     */     }
/*     */     protected SynchronizedSortedMap(Short2ShortSortedMap m) {
/* 425 */       super(m);
/* 426 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public ShortComparator comparator() {
/* 430 */       synchronized (this.sync) {
/* 431 */         return this.sortedMap.comparator();
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Short2ShortMap.Entry> short2ShortEntrySet() {
/* 436 */       if (this.entries == null)
/* 437 */         this.entries = (ObjectSet<Short2ShortMap.Entry>)ObjectSortedSets.synchronize(this.sortedMap.short2ShortEntrySet(), this.sync); 
/* 438 */       return (ObjectSortedSet<Short2ShortMap.Entry>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Short, Short>> entrySet() {
/* 449 */       return (ObjectSortedSet)short2ShortEntrySet();
/*     */     }
/*     */     
/*     */     public ShortSortedSet keySet() {
/* 453 */       if (this.keys == null)
/* 454 */         this.keys = ShortSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 455 */       return (ShortSortedSet)this.keys;
/*     */     }
/*     */     
/*     */     public Short2ShortSortedMap subMap(short from, short to) {
/* 459 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */     
/*     */     public Short2ShortSortedMap headMap(short to) {
/* 463 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */     
/*     */     public Short2ShortSortedMap tailMap(short from) {
/* 467 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
/*     */     }
/*     */     
/*     */     public short firstShortKey() {
/* 471 */       synchronized (this.sync) {
/* 472 */         return this.sortedMap.firstShortKey();
/*     */       } 
/*     */     }
/*     */     
/*     */     public short lastShortKey() {
/* 477 */       synchronized (this.sync) {
/* 478 */         return this.sortedMap.lastShortKey();
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
/*     */     public Short lastKey() {
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
/*     */     public Short2ShortSortedMap subMap(Short from, Short to) {
/* 513 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short2ShortSortedMap headMap(Short to) {
/* 523 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short2ShortSortedMap tailMap(Short from) {
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
/*     */   public static Short2ShortSortedMap synchronize(Short2ShortSortedMap m) {
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
/*     */   public static Short2ShortSortedMap synchronize(Short2ShortSortedMap m, Object sync) {
/* 561 */     return new SynchronizedSortedMap(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap
/*     */     extends Short2ShortMaps.UnmodifiableMap
/*     */     implements Short2ShortSortedMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Short2ShortSortedMap sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Short2ShortSortedMap m) {
/* 571 */       super(m);
/* 572 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public ShortComparator comparator() {
/* 576 */       return this.sortedMap.comparator();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Short2ShortMap.Entry> short2ShortEntrySet() {
/* 580 */       if (this.entries == null)
/* 581 */         this.entries = (ObjectSet<Short2ShortMap.Entry>)ObjectSortedSets.unmodifiable(this.sortedMap.short2ShortEntrySet()); 
/* 582 */       return (ObjectSortedSet<Short2ShortMap.Entry>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Short, Short>> entrySet() {
/* 593 */       return (ObjectSortedSet)short2ShortEntrySet();
/*     */     }
/*     */     
/*     */     public ShortSortedSet keySet() {
/* 597 */       if (this.keys == null)
/* 598 */         this.keys = ShortSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 599 */       return (ShortSortedSet)this.keys;
/*     */     }
/*     */     
/*     */     public Short2ShortSortedMap subMap(short from, short to) {
/* 603 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */     
/*     */     public Short2ShortSortedMap headMap(short to) {
/* 607 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */     
/*     */     public Short2ShortSortedMap tailMap(short from) {
/* 611 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
/*     */     }
/*     */     
/*     */     public short firstShortKey() {
/* 615 */       return this.sortedMap.firstShortKey();
/*     */     }
/*     */     
/*     */     public short lastShortKey() {
/* 619 */       return this.sortedMap.lastShortKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short firstKey() {
/* 629 */       return this.sortedMap.firstKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short lastKey() {
/* 639 */       return this.sortedMap.lastKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short2ShortSortedMap subMap(Short from, Short to) {
/* 649 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short2ShortSortedMap headMap(Short to) {
/* 659 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short2ShortSortedMap tailMap(Short from) {
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
/*     */   public static Short2ShortSortedMap unmodifiable(Short2ShortSortedMap m) {
/* 682 */     return new UnmodifiableSortedMap(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\Short2ShortSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */