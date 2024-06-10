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
/*     */ public final class Double2FloatSortedMaps
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
/*     */   public static ObjectBidirectionalIterator<Double2FloatMap.Entry> fastIterator(Double2FloatSortedMap map) {
/*  60 */     ObjectSortedSet<Double2FloatMap.Entry> entries = map.double2FloatEntrySet();
/*  61 */     return (entries instanceof Double2FloatSortedMap.FastSortedEntrySet) ? (
/*  62 */       (Double2FloatSortedMap.FastSortedEntrySet)entries).fastIterator() : 
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
/*     */   public static ObjectBidirectionalIterable<Double2FloatMap.Entry> fastIterable(Double2FloatSortedMap map) {
/*  79 */     ObjectSortedSet<Double2FloatMap.Entry> entries = map.double2FloatEntrySet();
/*     */     
/*  81 */     Objects.requireNonNull((Double2FloatSortedMap.FastSortedEntrySet)entries); return (entries instanceof Double2FloatSortedMap.FastSortedEntrySet) ? (Double2FloatSortedMap.FastSortedEntrySet)entries::fastIterator : 
/*  82 */       (ObjectBidirectionalIterable<Double2FloatMap.Entry>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap
/*     */     extends Double2FloatMaps.EmptyMap
/*     */     implements Double2FloatSortedMap, Serializable, Cloneable
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
/*     */     public ObjectSortedSet<Double2FloatMap.Entry> double2FloatEntrySet() {
/* 106 */       return (ObjectSortedSet<Double2FloatMap.Entry>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Double, Float>> entrySet() {
/* 117 */       return (ObjectSortedSet<Map.Entry<Double, Float>>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSortedSet keySet() {
/* 122 */       return DoubleSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2FloatSortedMap subMap(double from, double to) {
/* 127 */       return Double2FloatSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2FloatSortedMap headMap(double to) {
/* 132 */       return Double2FloatSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2FloatSortedMap tailMap(double from) {
/* 137 */       return Double2FloatSortedMaps.EMPTY_MAP;
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
/*     */     public Double2FloatSortedMap headMap(Double oto) {
/* 155 */       return headMap(oto.doubleValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2FloatSortedMap tailMap(Double ofrom) {
/* 165 */       return tailMap(ofrom.doubleValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2FloatSortedMap subMap(Double ofrom, Double oto) {
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
/*     */     extends Double2FloatMaps.Singleton
/*     */     implements Double2FloatSortedMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final DoubleComparator comparator;
/*     */ 
/*     */ 
/*     */     
/*     */     protected Singleton(double key, float value, DoubleComparator comparator) {
/* 218 */       super(key, value);
/* 219 */       this.comparator = comparator;
/*     */     }
/*     */     protected Singleton(double key, float value) {
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
/*     */     public ObjectSortedSet<Double2FloatMap.Entry> double2FloatEntrySet() {
/* 235 */       if (this.entries == null)
/* 236 */         this.entries = (ObjectSet<Double2FloatMap.Entry>)ObjectSortedSets.singleton(new AbstractDouble2FloatMap.BasicEntry(this.key, this.value), 
/* 237 */             Double2FloatSortedMaps.entryComparator(this.comparator)); 
/* 238 */       return (ObjectSortedSet<Double2FloatMap.Entry>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Double, Float>> entrySet() {
/* 249 */       return (ObjectSortedSet)double2FloatEntrySet();
/*     */     }
/*     */     
/*     */     public DoubleSortedSet keySet() {
/* 253 */       if (this.keys == null)
/* 254 */         this.keys = DoubleSortedSets.singleton(this.key, this.comparator); 
/* 255 */       return (DoubleSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2FloatSortedMap subMap(double from, double to) {
/* 260 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0)
/* 261 */         return this; 
/* 262 */       return Double2FloatSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2FloatSortedMap headMap(double to) {
/* 267 */       if (compare(this.key, to) < 0)
/* 268 */         return this; 
/* 269 */       return Double2FloatSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2FloatSortedMap tailMap(double from) {
/* 274 */       if (compare(from, this.key) <= 0)
/* 275 */         return this; 
/* 276 */       return Double2FloatSortedMaps.EMPTY_MAP;
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
/*     */     public Double2FloatSortedMap headMap(Double oto) {
/* 294 */       return headMap(oto.doubleValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2FloatSortedMap tailMap(Double ofrom) {
/* 304 */       return tailMap(ofrom.doubleValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2FloatSortedMap subMap(Double ofrom, Double oto) {
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
/*     */   public static Double2FloatSortedMap singleton(Double key, Float value) {
/* 353 */     return new Singleton(key.doubleValue(), value.floatValue());
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
/*     */   public static Double2FloatSortedMap singleton(Double key, Float value, DoubleComparator comparator) {
/* 373 */     return new Singleton(key.doubleValue(), value.floatValue(), comparator);
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
/*     */   public static Double2FloatSortedMap singleton(double key, float value) {
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
/*     */   public static Double2FloatSortedMap singleton(double key, float value, DoubleComparator comparator) {
/* 411 */     return new Singleton(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap
/*     */     extends Double2FloatMaps.SynchronizedMap
/*     */     implements Double2FloatSortedMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Double2FloatSortedMap sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Double2FloatSortedMap m, Object sync) {
/* 421 */       super(m, sync);
/* 422 */       this.sortedMap = m;
/*     */     }
/*     */     protected SynchronizedSortedMap(Double2FloatSortedMap m) {
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
/*     */     public ObjectSortedSet<Double2FloatMap.Entry> double2FloatEntrySet() {
/* 436 */       if (this.entries == null)
/* 437 */         this.entries = (ObjectSet<Double2FloatMap.Entry>)ObjectSortedSets.synchronize(this.sortedMap.double2FloatEntrySet(), this.sync); 
/* 438 */       return (ObjectSortedSet<Double2FloatMap.Entry>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Double, Float>> entrySet() {
/* 449 */       return (ObjectSortedSet)double2FloatEntrySet();
/*     */     }
/*     */     
/*     */     public DoubleSortedSet keySet() {
/* 453 */       if (this.keys == null)
/* 454 */         this.keys = DoubleSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 455 */       return (DoubleSortedSet)this.keys;
/*     */     }
/*     */     
/*     */     public Double2FloatSortedMap subMap(double from, double to) {
/* 459 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */     
/*     */     public Double2FloatSortedMap headMap(double to) {
/* 463 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */     
/*     */     public Double2FloatSortedMap tailMap(double from) {
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
/*     */     public Double2FloatSortedMap subMap(Double from, Double to) {
/* 513 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2FloatSortedMap headMap(Double to) {
/* 523 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2FloatSortedMap tailMap(Double from) {
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
/*     */   public static Double2FloatSortedMap synchronize(Double2FloatSortedMap m) {
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
/*     */   public static Double2FloatSortedMap synchronize(Double2FloatSortedMap m, Object sync) {
/* 561 */     return new SynchronizedSortedMap(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap
/*     */     extends Double2FloatMaps.UnmodifiableMap
/*     */     implements Double2FloatSortedMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Double2FloatSortedMap sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Double2FloatSortedMap m) {
/* 571 */       super(m);
/* 572 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public DoubleComparator comparator() {
/* 576 */       return this.sortedMap.comparator();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Double2FloatMap.Entry> double2FloatEntrySet() {
/* 580 */       if (this.entries == null)
/* 581 */         this.entries = (ObjectSet<Double2FloatMap.Entry>)ObjectSortedSets.unmodifiable(this.sortedMap.double2FloatEntrySet()); 
/* 582 */       return (ObjectSortedSet<Double2FloatMap.Entry>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Double, Float>> entrySet() {
/* 593 */       return (ObjectSortedSet)double2FloatEntrySet();
/*     */     }
/*     */     
/*     */     public DoubleSortedSet keySet() {
/* 597 */       if (this.keys == null)
/* 598 */         this.keys = DoubleSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 599 */       return (DoubleSortedSet)this.keys;
/*     */     }
/*     */     
/*     */     public Double2FloatSortedMap subMap(double from, double to) {
/* 603 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */     
/*     */     public Double2FloatSortedMap headMap(double to) {
/* 607 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */     
/*     */     public Double2FloatSortedMap tailMap(double from) {
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
/*     */     public Double2FloatSortedMap subMap(Double from, Double to) {
/* 649 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2FloatSortedMap headMap(Double to) {
/* 659 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2FloatSortedMap tailMap(Double from) {
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
/*     */   public static Double2FloatSortedMap unmodifiable(Double2FloatSortedMap m) {
/* 682 */     return new UnmodifiableSortedMap(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2FloatSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */