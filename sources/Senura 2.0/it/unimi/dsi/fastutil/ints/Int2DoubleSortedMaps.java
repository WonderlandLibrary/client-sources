/*     */ package it.unimi.dsi.fastutil.ints;
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
/*     */ public final class Int2DoubleSortedMaps
/*     */ {
/*     */   public static Comparator<? super Map.Entry<Integer, ?>> entryComparator(IntComparator comparator) {
/*  43 */     return (x, y) -> comparator.compare(((Integer)x.getKey()).intValue(), ((Integer)y.getKey()).intValue());
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
/*     */   public static ObjectBidirectionalIterator<Int2DoubleMap.Entry> fastIterator(Int2DoubleSortedMap map) {
/*  60 */     ObjectSortedSet<Int2DoubleMap.Entry> entries = map.int2DoubleEntrySet();
/*  61 */     return (entries instanceof Int2DoubleSortedMap.FastSortedEntrySet) ? (
/*  62 */       (Int2DoubleSortedMap.FastSortedEntrySet)entries).fastIterator() : 
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
/*     */   public static ObjectBidirectionalIterable<Int2DoubleMap.Entry> fastIterable(Int2DoubleSortedMap map) {
/*  79 */     ObjectSortedSet<Int2DoubleMap.Entry> entries = map.int2DoubleEntrySet();
/*     */     
/*  81 */     Objects.requireNonNull((Int2DoubleSortedMap.FastSortedEntrySet)entries); return (entries instanceof Int2DoubleSortedMap.FastSortedEntrySet) ? (Int2DoubleSortedMap.FastSortedEntrySet)entries::fastIterator : 
/*  82 */       (ObjectBidirectionalIterable<Int2DoubleMap.Entry>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap
/*     */     extends Int2DoubleMaps.EmptyMap
/*     */     implements Int2DoubleSortedMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public IntComparator comparator() {
/* 101 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Int2DoubleMap.Entry> int2DoubleEntrySet() {
/* 106 */       return (ObjectSortedSet<Int2DoubleMap.Entry>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Integer, Double>> entrySet() {
/* 117 */       return (ObjectSortedSet<Map.Entry<Integer, Double>>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSortedSet keySet() {
/* 122 */       return IntSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public Int2DoubleSortedMap subMap(int from, int to) {
/* 127 */       return Int2DoubleSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Int2DoubleSortedMap headMap(int to) {
/* 132 */       return Int2DoubleSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Int2DoubleSortedMap tailMap(int from) {
/* 137 */       return Int2DoubleSortedMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public int firstIntKey() {
/* 141 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public int lastIntKey() {
/* 145 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2DoubleSortedMap headMap(Integer oto) {
/* 155 */       return headMap(oto.intValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2DoubleSortedMap tailMap(Integer ofrom) {
/* 165 */       return tailMap(ofrom.intValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2DoubleSortedMap subMap(Integer ofrom, Integer oto) {
/* 175 */       return subMap(ofrom.intValue(), oto.intValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer firstKey() {
/* 185 */       return Integer.valueOf(firstIntKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer lastKey() {
/* 195 */       return Integer.valueOf(lastIntKey());
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
/*     */     extends Int2DoubleMaps.Singleton
/*     */     implements Int2DoubleSortedMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final IntComparator comparator;
/*     */ 
/*     */ 
/*     */     
/*     */     protected Singleton(int key, double value, IntComparator comparator) {
/* 218 */       super(key, value);
/* 219 */       this.comparator = comparator;
/*     */     }
/*     */     protected Singleton(int key, double value) {
/* 222 */       this(key, value, (IntComparator)null);
/*     */     }
/*     */     
/*     */     final int compare(int k1, int k2) {
/* 226 */       return (this.comparator == null) ? Integer.compare(k1, k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */     
/*     */     public IntComparator comparator() {
/* 230 */       return this.comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Int2DoubleMap.Entry> int2DoubleEntrySet() {
/* 235 */       if (this.entries == null)
/* 236 */         this.entries = (ObjectSet<Int2DoubleMap.Entry>)ObjectSortedSets.singleton(new AbstractInt2DoubleMap.BasicEntry(this.key, this.value), 
/* 237 */             Int2DoubleSortedMaps.entryComparator(this.comparator)); 
/* 238 */       return (ObjectSortedSet<Int2DoubleMap.Entry>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Integer, Double>> entrySet() {
/* 249 */       return (ObjectSortedSet)int2DoubleEntrySet();
/*     */     }
/*     */     
/*     */     public IntSortedSet keySet() {
/* 253 */       if (this.keys == null)
/* 254 */         this.keys = IntSortedSets.singleton(this.key, this.comparator); 
/* 255 */       return (IntSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Int2DoubleSortedMap subMap(int from, int to) {
/* 260 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0)
/* 261 */         return this; 
/* 262 */       return Int2DoubleSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Int2DoubleSortedMap headMap(int to) {
/* 267 */       if (compare(this.key, to) < 0)
/* 268 */         return this; 
/* 269 */       return Int2DoubleSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Int2DoubleSortedMap tailMap(int from) {
/* 274 */       if (compare(from, this.key) <= 0)
/* 275 */         return this; 
/* 276 */       return Int2DoubleSortedMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public int firstIntKey() {
/* 280 */       return this.key;
/*     */     }
/*     */     
/*     */     public int lastIntKey() {
/* 284 */       return this.key;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2DoubleSortedMap headMap(Integer oto) {
/* 294 */       return headMap(oto.intValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2DoubleSortedMap tailMap(Integer ofrom) {
/* 304 */       return tailMap(ofrom.intValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2DoubleSortedMap subMap(Integer ofrom, Integer oto) {
/* 314 */       return subMap(ofrom.intValue(), oto.intValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer firstKey() {
/* 324 */       return Integer.valueOf(firstIntKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer lastKey() {
/* 334 */       return Integer.valueOf(lastIntKey());
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
/*     */   public static Int2DoubleSortedMap singleton(Integer key, Double value) {
/* 353 */     return new Singleton(key.intValue(), value.doubleValue());
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
/*     */   public static Int2DoubleSortedMap singleton(Integer key, Double value, IntComparator comparator) {
/* 373 */     return new Singleton(key.intValue(), value.doubleValue(), comparator);
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
/*     */   public static Int2DoubleSortedMap singleton(int key, double value) {
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
/*     */   public static Int2DoubleSortedMap singleton(int key, double value, IntComparator comparator) {
/* 411 */     return new Singleton(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap
/*     */     extends Int2DoubleMaps.SynchronizedMap
/*     */     implements Int2DoubleSortedMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Int2DoubleSortedMap sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Int2DoubleSortedMap m, Object sync) {
/* 421 */       super(m, sync);
/* 422 */       this.sortedMap = m;
/*     */     }
/*     */     protected SynchronizedSortedMap(Int2DoubleSortedMap m) {
/* 425 */       super(m);
/* 426 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public IntComparator comparator() {
/* 430 */       synchronized (this.sync) {
/* 431 */         return this.sortedMap.comparator();
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Int2DoubleMap.Entry> int2DoubleEntrySet() {
/* 436 */       if (this.entries == null)
/* 437 */         this.entries = (ObjectSet<Int2DoubleMap.Entry>)ObjectSortedSets.synchronize(this.sortedMap.int2DoubleEntrySet(), this.sync); 
/* 438 */       return (ObjectSortedSet<Int2DoubleMap.Entry>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Integer, Double>> entrySet() {
/* 449 */       return (ObjectSortedSet)int2DoubleEntrySet();
/*     */     }
/*     */     
/*     */     public IntSortedSet keySet() {
/* 453 */       if (this.keys == null)
/* 454 */         this.keys = IntSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 455 */       return (IntSortedSet)this.keys;
/*     */     }
/*     */     
/*     */     public Int2DoubleSortedMap subMap(int from, int to) {
/* 459 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */     
/*     */     public Int2DoubleSortedMap headMap(int to) {
/* 463 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */     
/*     */     public Int2DoubleSortedMap tailMap(int from) {
/* 467 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
/*     */     }
/*     */     
/*     */     public int firstIntKey() {
/* 471 */       synchronized (this.sync) {
/* 472 */         return this.sortedMap.firstIntKey();
/*     */       } 
/*     */     }
/*     */     
/*     */     public int lastIntKey() {
/* 477 */       synchronized (this.sync) {
/* 478 */         return this.sortedMap.lastIntKey();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer firstKey() {
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
/*     */     public Integer lastKey() {
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
/*     */     public Int2DoubleSortedMap subMap(Integer from, Integer to) {
/* 513 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2DoubleSortedMap headMap(Integer to) {
/* 523 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2DoubleSortedMap tailMap(Integer from) {
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
/*     */   public static Int2DoubleSortedMap synchronize(Int2DoubleSortedMap m) {
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
/*     */   public static Int2DoubleSortedMap synchronize(Int2DoubleSortedMap m, Object sync) {
/* 561 */     return new SynchronizedSortedMap(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap
/*     */     extends Int2DoubleMaps.UnmodifiableMap
/*     */     implements Int2DoubleSortedMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Int2DoubleSortedMap sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Int2DoubleSortedMap m) {
/* 571 */       super(m);
/* 572 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public IntComparator comparator() {
/* 576 */       return this.sortedMap.comparator();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Int2DoubleMap.Entry> int2DoubleEntrySet() {
/* 580 */       if (this.entries == null)
/* 581 */         this.entries = (ObjectSet<Int2DoubleMap.Entry>)ObjectSortedSets.unmodifiable(this.sortedMap.int2DoubleEntrySet()); 
/* 582 */       return (ObjectSortedSet<Int2DoubleMap.Entry>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Integer, Double>> entrySet() {
/* 593 */       return (ObjectSortedSet)int2DoubleEntrySet();
/*     */     }
/*     */     
/*     */     public IntSortedSet keySet() {
/* 597 */       if (this.keys == null)
/* 598 */         this.keys = IntSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 599 */       return (IntSortedSet)this.keys;
/*     */     }
/*     */     
/*     */     public Int2DoubleSortedMap subMap(int from, int to) {
/* 603 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */     
/*     */     public Int2DoubleSortedMap headMap(int to) {
/* 607 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */     
/*     */     public Int2DoubleSortedMap tailMap(int from) {
/* 611 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
/*     */     }
/*     */     
/*     */     public int firstIntKey() {
/* 615 */       return this.sortedMap.firstIntKey();
/*     */     }
/*     */     
/*     */     public int lastIntKey() {
/* 619 */       return this.sortedMap.lastIntKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer firstKey() {
/* 629 */       return this.sortedMap.firstKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer lastKey() {
/* 639 */       return this.sortedMap.lastKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2DoubleSortedMap subMap(Integer from, Integer to) {
/* 649 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2DoubleSortedMap headMap(Integer to) {
/* 659 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2DoubleSortedMap tailMap(Integer from) {
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
/*     */   public static Int2DoubleSortedMap unmodifiable(Int2DoubleSortedMap m) {
/* 682 */     return new UnmodifiableSortedMap(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\Int2DoubleSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */