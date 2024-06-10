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
/*     */ public final class Short2ByteSortedMaps
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
/*     */   public static ObjectBidirectionalIterator<Short2ByteMap.Entry> fastIterator(Short2ByteSortedMap map) {
/*  60 */     ObjectSortedSet<Short2ByteMap.Entry> entries = map.short2ByteEntrySet();
/*  61 */     return (entries instanceof Short2ByteSortedMap.FastSortedEntrySet) ? (
/*  62 */       (Short2ByteSortedMap.FastSortedEntrySet)entries).fastIterator() : 
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
/*     */   public static ObjectBidirectionalIterable<Short2ByteMap.Entry> fastIterable(Short2ByteSortedMap map) {
/*  79 */     ObjectSortedSet<Short2ByteMap.Entry> entries = map.short2ByteEntrySet();
/*     */     
/*  81 */     Objects.requireNonNull((Short2ByteSortedMap.FastSortedEntrySet)entries); return (entries instanceof Short2ByteSortedMap.FastSortedEntrySet) ? (Short2ByteSortedMap.FastSortedEntrySet)entries::fastIterator : 
/*  82 */       (ObjectBidirectionalIterable<Short2ByteMap.Entry>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap
/*     */     extends Short2ByteMaps.EmptyMap
/*     */     implements Short2ByteSortedMap, Serializable, Cloneable
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
/*     */     public ObjectSortedSet<Short2ByteMap.Entry> short2ByteEntrySet() {
/* 106 */       return (ObjectSortedSet<Short2ByteMap.Entry>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Short, Byte>> entrySet() {
/* 117 */       return (ObjectSortedSet<Map.Entry<Short, Byte>>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSortedSet keySet() {
/* 122 */       return ShortSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public Short2ByteSortedMap subMap(short from, short to) {
/* 127 */       return Short2ByteSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Short2ByteSortedMap headMap(short to) {
/* 132 */       return Short2ByteSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Short2ByteSortedMap tailMap(short from) {
/* 137 */       return Short2ByteSortedMaps.EMPTY_MAP;
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
/*     */     public Short2ByteSortedMap headMap(Short oto) {
/* 155 */       return headMap(oto.shortValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short2ByteSortedMap tailMap(Short ofrom) {
/* 165 */       return tailMap(ofrom.shortValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short2ByteSortedMap subMap(Short ofrom, Short oto) {
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
/*     */     extends Short2ByteMaps.Singleton
/*     */     implements Short2ByteSortedMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final ShortComparator comparator;
/*     */ 
/*     */ 
/*     */     
/*     */     protected Singleton(short key, byte value, ShortComparator comparator) {
/* 218 */       super(key, value);
/* 219 */       this.comparator = comparator;
/*     */     }
/*     */     protected Singleton(short key, byte value) {
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
/*     */     public ObjectSortedSet<Short2ByteMap.Entry> short2ByteEntrySet() {
/* 235 */       if (this.entries == null)
/* 236 */         this.entries = (ObjectSet<Short2ByteMap.Entry>)ObjectSortedSets.singleton(new AbstractShort2ByteMap.BasicEntry(this.key, this.value), 
/* 237 */             Short2ByteSortedMaps.entryComparator(this.comparator)); 
/* 238 */       return (ObjectSortedSet<Short2ByteMap.Entry>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Short, Byte>> entrySet() {
/* 249 */       return (ObjectSortedSet)short2ByteEntrySet();
/*     */     }
/*     */     
/*     */     public ShortSortedSet keySet() {
/* 253 */       if (this.keys == null)
/* 254 */         this.keys = ShortSortedSets.singleton(this.key, this.comparator); 
/* 255 */       return (ShortSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Short2ByteSortedMap subMap(short from, short to) {
/* 260 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0)
/* 261 */         return this; 
/* 262 */       return Short2ByteSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Short2ByteSortedMap headMap(short to) {
/* 267 */       if (compare(this.key, to) < 0)
/* 268 */         return this; 
/* 269 */       return Short2ByteSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Short2ByteSortedMap tailMap(short from) {
/* 274 */       if (compare(from, this.key) <= 0)
/* 275 */         return this; 
/* 276 */       return Short2ByteSortedMaps.EMPTY_MAP;
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
/*     */     public Short2ByteSortedMap headMap(Short oto) {
/* 294 */       return headMap(oto.shortValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short2ByteSortedMap tailMap(Short ofrom) {
/* 304 */       return tailMap(ofrom.shortValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short2ByteSortedMap subMap(Short ofrom, Short oto) {
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
/*     */   public static Short2ByteSortedMap singleton(Short key, Byte value) {
/* 353 */     return new Singleton(key.shortValue(), value.byteValue());
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
/*     */   public static Short2ByteSortedMap singleton(Short key, Byte value, ShortComparator comparator) {
/* 373 */     return new Singleton(key.shortValue(), value.byteValue(), comparator);
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
/*     */   public static Short2ByteSortedMap singleton(short key, byte value) {
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
/*     */   public static Short2ByteSortedMap singleton(short key, byte value, ShortComparator comparator) {
/* 411 */     return new Singleton(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap
/*     */     extends Short2ByteMaps.SynchronizedMap
/*     */     implements Short2ByteSortedMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Short2ByteSortedMap sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Short2ByteSortedMap m, Object sync) {
/* 421 */       super(m, sync);
/* 422 */       this.sortedMap = m;
/*     */     }
/*     */     protected SynchronizedSortedMap(Short2ByteSortedMap m) {
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
/*     */     public ObjectSortedSet<Short2ByteMap.Entry> short2ByteEntrySet() {
/* 436 */       if (this.entries == null)
/* 437 */         this.entries = (ObjectSet<Short2ByteMap.Entry>)ObjectSortedSets.synchronize(this.sortedMap.short2ByteEntrySet(), this.sync); 
/* 438 */       return (ObjectSortedSet<Short2ByteMap.Entry>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Short, Byte>> entrySet() {
/* 449 */       return (ObjectSortedSet)short2ByteEntrySet();
/*     */     }
/*     */     
/*     */     public ShortSortedSet keySet() {
/* 453 */       if (this.keys == null)
/* 454 */         this.keys = ShortSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 455 */       return (ShortSortedSet)this.keys;
/*     */     }
/*     */     
/*     */     public Short2ByteSortedMap subMap(short from, short to) {
/* 459 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */     
/*     */     public Short2ByteSortedMap headMap(short to) {
/* 463 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */     
/*     */     public Short2ByteSortedMap tailMap(short from) {
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
/*     */     public Short2ByteSortedMap subMap(Short from, Short to) {
/* 513 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short2ByteSortedMap headMap(Short to) {
/* 523 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short2ByteSortedMap tailMap(Short from) {
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
/*     */   public static Short2ByteSortedMap synchronize(Short2ByteSortedMap m) {
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
/*     */   public static Short2ByteSortedMap synchronize(Short2ByteSortedMap m, Object sync) {
/* 561 */     return new SynchronizedSortedMap(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap
/*     */     extends Short2ByteMaps.UnmodifiableMap
/*     */     implements Short2ByteSortedMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Short2ByteSortedMap sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Short2ByteSortedMap m) {
/* 571 */       super(m);
/* 572 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public ShortComparator comparator() {
/* 576 */       return this.sortedMap.comparator();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Short2ByteMap.Entry> short2ByteEntrySet() {
/* 580 */       if (this.entries == null)
/* 581 */         this.entries = (ObjectSet<Short2ByteMap.Entry>)ObjectSortedSets.unmodifiable(this.sortedMap.short2ByteEntrySet()); 
/* 582 */       return (ObjectSortedSet<Short2ByteMap.Entry>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Short, Byte>> entrySet() {
/* 593 */       return (ObjectSortedSet)short2ByteEntrySet();
/*     */     }
/*     */     
/*     */     public ShortSortedSet keySet() {
/* 597 */       if (this.keys == null)
/* 598 */         this.keys = ShortSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 599 */       return (ShortSortedSet)this.keys;
/*     */     }
/*     */     
/*     */     public Short2ByteSortedMap subMap(short from, short to) {
/* 603 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */     
/*     */     public Short2ByteSortedMap headMap(short to) {
/* 607 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */     
/*     */     public Short2ByteSortedMap tailMap(short from) {
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
/*     */     public Short2ByteSortedMap subMap(Short from, Short to) {
/* 649 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short2ByteSortedMap headMap(Short to) {
/* 659 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short2ByteSortedMap tailMap(Short from) {
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
/*     */   public static Short2ByteSortedMap unmodifiable(Short2ByteSortedMap m) {
/* 682 */     return new UnmodifiableSortedMap(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\Short2ByteSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */