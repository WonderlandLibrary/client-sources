/*     */ package it.unimi.dsi.fastutil.doubles;
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
/*     */ public final class Double2IntSortedMaps
/*     */ {
/*     */   public static Comparator<? super Map.Entry<Double, ?>> entryComparator(DoubleComparator comparator) {
/*  43 */     return (x, y) -> comparator.compare(((Double)x.getKey()).doubleValue(), ((Double)y.getKey()).doubleValue());
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
/*     */   public static ObjectBidirectionalIterator<Double2IntMap.Entry> fastIterator(Double2IntSortedMap map) {
/*  60 */     ObjectSortedSet<Double2IntMap.Entry> entries = map.double2IntEntrySet();
/*  61 */     return (entries instanceof Double2IntSortedMap.FastSortedEntrySet) ? (
/*  62 */       (Double2IntSortedMap.FastSortedEntrySet)entries).fastIterator() : 
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
/*     */   public static ObjectBidirectionalIterable<Double2IntMap.Entry> fastIterable(Double2IntSortedMap map) {
/*  79 */     ObjectSortedSet<Double2IntMap.Entry> entries = map.double2IntEntrySet();
/*     */     
/*  81 */     Objects.requireNonNull((Double2IntSortedMap.FastSortedEntrySet)entries); return (entries instanceof Double2IntSortedMap.FastSortedEntrySet) ? (Double2IntSortedMap.FastSortedEntrySet)entries::fastIterator : 
/*  82 */       (ObjectBidirectionalIterable<Double2IntMap.Entry>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap
/*     */     extends Double2IntMaps.EmptyMap
/*     */     implements Double2IntSortedMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DoubleComparator comparator() {
/* 101 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Double2IntMap.Entry> double2IntEntrySet() {
/* 106 */       return (ObjectSortedSet<Double2IntMap.Entry>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Double, Integer>> entrySet() {
/* 117 */       return (ObjectSortedSet<Map.Entry<Double, Integer>>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSortedSet keySet() {
/* 122 */       return DoubleSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2IntSortedMap subMap(double from, double to) {
/* 127 */       return Double2IntSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2IntSortedMap headMap(double to) {
/* 132 */       return Double2IntSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2IntSortedMap tailMap(double from) {
/* 137 */       return Double2IntSortedMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public double firstDoubleKey() {
/* 141 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public double lastDoubleKey() {
/* 145 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2IntSortedMap headMap(Double oto) {
/* 155 */       return headMap(oto.doubleValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2IntSortedMap tailMap(Double ofrom) {
/* 165 */       return tailMap(ofrom.doubleValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2IntSortedMap subMap(Double ofrom, Double oto) {
/* 175 */       return subMap(ofrom.doubleValue(), oto.doubleValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double firstKey() {
/* 185 */       return Double.valueOf(firstDoubleKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double lastKey() {
/* 195 */       return Double.valueOf(lastDoubleKey());
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
/*     */     extends Double2IntMaps.Singleton
/*     */     implements Double2IntSortedMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final DoubleComparator comparator;
/*     */ 
/*     */ 
/*     */     
/*     */     protected Singleton(double key, int value, DoubleComparator comparator) {
/* 218 */       super(key, value);
/* 219 */       this.comparator = comparator;
/*     */     }
/*     */     protected Singleton(double key, int value) {
/* 222 */       this(key, value, (DoubleComparator)null);
/*     */     }
/*     */     
/*     */     final int compare(double k1, double k2) {
/* 226 */       return (this.comparator == null) ? Double.compare(k1, k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */     
/*     */     public DoubleComparator comparator() {
/* 230 */       return this.comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Double2IntMap.Entry> double2IntEntrySet() {
/* 235 */       if (this.entries == null)
/* 236 */         this.entries = (ObjectSet<Double2IntMap.Entry>)ObjectSortedSets.singleton(new AbstractDouble2IntMap.BasicEntry(this.key, this.value), 
/* 237 */             Double2IntSortedMaps.entryComparator(this.comparator)); 
/* 238 */       return (ObjectSortedSet<Double2IntMap.Entry>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Double, Integer>> entrySet() {
/* 249 */       return (ObjectSortedSet)double2IntEntrySet();
/*     */     }
/*     */     
/*     */     public DoubleSortedSet keySet() {
/* 253 */       if (this.keys == null)
/* 254 */         this.keys = DoubleSortedSets.singleton(this.key, this.comparator); 
/* 255 */       return (DoubleSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2IntSortedMap subMap(double from, double to) {
/* 260 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0)
/* 261 */         return this; 
/* 262 */       return Double2IntSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2IntSortedMap headMap(double to) {
/* 267 */       if (compare(this.key, to) < 0)
/* 268 */         return this; 
/* 269 */       return Double2IntSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2IntSortedMap tailMap(double from) {
/* 274 */       if (compare(from, this.key) <= 0)
/* 275 */         return this; 
/* 276 */       return Double2IntSortedMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public double firstDoubleKey() {
/* 280 */       return this.key;
/*     */     }
/*     */     
/*     */     public double lastDoubleKey() {
/* 284 */       return this.key;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2IntSortedMap headMap(Double oto) {
/* 294 */       return headMap(oto.doubleValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2IntSortedMap tailMap(Double ofrom) {
/* 304 */       return tailMap(ofrom.doubleValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2IntSortedMap subMap(Double ofrom, Double oto) {
/* 314 */       return subMap(ofrom.doubleValue(), oto.doubleValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double firstKey() {
/* 324 */       return Double.valueOf(firstDoubleKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double lastKey() {
/* 334 */       return Double.valueOf(lastDoubleKey());
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
/*     */   public static Double2IntSortedMap singleton(Double key, Integer value) {
/* 353 */     return new Singleton(key.doubleValue(), value.intValue());
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
/*     */   public static Double2IntSortedMap singleton(Double key, Integer value, DoubleComparator comparator) {
/* 373 */     return new Singleton(key.doubleValue(), value.intValue(), comparator);
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
/*     */   public static Double2IntSortedMap singleton(double key, int value) {
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
/*     */   public static Double2IntSortedMap singleton(double key, int value, DoubleComparator comparator) {
/* 411 */     return new Singleton(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap
/*     */     extends Double2IntMaps.SynchronizedMap
/*     */     implements Double2IntSortedMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Double2IntSortedMap sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Double2IntSortedMap m, Object sync) {
/* 421 */       super(m, sync);
/* 422 */       this.sortedMap = m;
/*     */     }
/*     */     protected SynchronizedSortedMap(Double2IntSortedMap m) {
/* 425 */       super(m);
/* 426 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public DoubleComparator comparator() {
/* 430 */       synchronized (this.sync) {
/* 431 */         return this.sortedMap.comparator();
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Double2IntMap.Entry> double2IntEntrySet() {
/* 436 */       if (this.entries == null)
/* 437 */         this.entries = (ObjectSet<Double2IntMap.Entry>)ObjectSortedSets.synchronize(this.sortedMap.double2IntEntrySet(), this.sync); 
/* 438 */       return (ObjectSortedSet<Double2IntMap.Entry>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Double, Integer>> entrySet() {
/* 449 */       return (ObjectSortedSet)double2IntEntrySet();
/*     */     }
/*     */     
/*     */     public DoubleSortedSet keySet() {
/* 453 */       if (this.keys == null)
/* 454 */         this.keys = DoubleSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 455 */       return (DoubleSortedSet)this.keys;
/*     */     }
/*     */     
/*     */     public Double2IntSortedMap subMap(double from, double to) {
/* 459 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */     
/*     */     public Double2IntSortedMap headMap(double to) {
/* 463 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */     
/*     */     public Double2IntSortedMap tailMap(double from) {
/* 467 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
/*     */     }
/*     */     
/*     */     public double firstDoubleKey() {
/* 471 */       synchronized (this.sync) {
/* 472 */         return this.sortedMap.firstDoubleKey();
/*     */       } 
/*     */     }
/*     */     
/*     */     public double lastDoubleKey() {
/* 477 */       synchronized (this.sync) {
/* 478 */         return this.sortedMap.lastDoubleKey();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double firstKey() {
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
/*     */     public Double lastKey() {
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
/*     */     public Double2IntSortedMap subMap(Double from, Double to) {
/* 513 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2IntSortedMap headMap(Double to) {
/* 523 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2IntSortedMap tailMap(Double from) {
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
/*     */   public static Double2IntSortedMap synchronize(Double2IntSortedMap m) {
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
/*     */   public static Double2IntSortedMap synchronize(Double2IntSortedMap m, Object sync) {
/* 561 */     return new SynchronizedSortedMap(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap
/*     */     extends Double2IntMaps.UnmodifiableMap
/*     */     implements Double2IntSortedMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Double2IntSortedMap sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Double2IntSortedMap m) {
/* 571 */       super(m);
/* 572 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public DoubleComparator comparator() {
/* 576 */       return this.sortedMap.comparator();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Double2IntMap.Entry> double2IntEntrySet() {
/* 580 */       if (this.entries == null)
/* 581 */         this.entries = (ObjectSet<Double2IntMap.Entry>)ObjectSortedSets.unmodifiable(this.sortedMap.double2IntEntrySet()); 
/* 582 */       return (ObjectSortedSet<Double2IntMap.Entry>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Double, Integer>> entrySet() {
/* 593 */       return (ObjectSortedSet)double2IntEntrySet();
/*     */     }
/*     */     
/*     */     public DoubleSortedSet keySet() {
/* 597 */       if (this.keys == null)
/* 598 */         this.keys = DoubleSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 599 */       return (DoubleSortedSet)this.keys;
/*     */     }
/*     */     
/*     */     public Double2IntSortedMap subMap(double from, double to) {
/* 603 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */     
/*     */     public Double2IntSortedMap headMap(double to) {
/* 607 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */     
/*     */     public Double2IntSortedMap tailMap(double from) {
/* 611 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
/*     */     }
/*     */     
/*     */     public double firstDoubleKey() {
/* 615 */       return this.sortedMap.firstDoubleKey();
/*     */     }
/*     */     
/*     */     public double lastDoubleKey() {
/* 619 */       return this.sortedMap.lastDoubleKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double firstKey() {
/* 629 */       return this.sortedMap.firstKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double lastKey() {
/* 639 */       return this.sortedMap.lastKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2IntSortedMap subMap(Double from, Double to) {
/* 649 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2IntSortedMap headMap(Double to) {
/* 659 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2IntSortedMap tailMap(Double from) {
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
/*     */   public static Double2IntSortedMap unmodifiable(Double2IntSortedMap m) {
/* 682 */     return new UnmodifiableSortedMap(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2IntSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */